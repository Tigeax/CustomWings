package tigeax.customwings.main;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;

import tigeax.customwings.command.Wings;
import tigeax.customwings.editor.EditorConfigManager;
import tigeax.customwings.eventlisteners.AsyncPlayerChatEventListener;
import tigeax.customwings.eventlisteners.InventoryClickEventListener;
import tigeax.customwings.eventlisteners.InventoryCloseEventListener;
import tigeax.customwings.eventlisteners.PlayerCommandPreprocessEventListener;
import tigeax.customwings.eventlisteners.PlayerJoinEventListener;
import tigeax.customwings.eventlisteners.PlayerQuitEventListener;
import tigeax.customwings.gui.CWGUIManager;

/*
 * Main class of the CustomWings plugin
 * Minecraft version: 1.13+
 * By Tigeax
 */

public class CustomWings extends JavaPlugin {

	private static CustomWings plugin;

	private static Messages messages;
	private static Settings settings;
	private static EditorConfigManager editorConfigManager;
	private static CWGUIManager cwGUIManager;

	private static HashMap<UUID, CWPlayer> cwPlayerList;
	private static ArrayList<Wing> wings;

	private final static String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit", "").replace(".", "");

	private static FileConfiguration configFile;
	
	public void onEnable() {

		plugin = this;

		plugin.getLogger().info("Server running on: " + VERSION);

		if (!isServerVersionSupported()) {
			sendError("CustomWings is not tested on this server version! And therefore might not work.");
		}
		
		// bStats setup
		int pluginId = 8227;
		
        @SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, pluginId);

		setupConfig();

		settings = new Settings(this);
		messages = new Messages(this);
		editorConfigManager = new EditorConfigManager(this);
		cwGUIManager = new CWGUIManager();
		cwPlayerList = new HashMap<>();

		setupWings();

		Bukkit.getPluginCommand("wings").setExecutor(new Wings());

		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatEventListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new InventoryClickEventListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new InventoryCloseEventListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessEventListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(), plugin);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitEventListener(), plugin);

		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "{CustomWings} CustomWings has been enabled");
	}

	public void onDisable() {

		// If a player has a CustomWings GUI open, close it
		for (Player player : Bukkit.getOnlinePlayers()) {
			InventoryView invView = player.getOpenInventory();

			if (invView == null) continue;
			if (cwGUIManager.getCWGUITypeByInvTitle(invView.getTitle()) == null) continue;
			player.closeInventory();
		}

		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "{CustomWings} CustomWings has been disabled");
	}
	
	public FileConfiguration getCWConfig() { return configFile; }

	public static String getServerVersion() { return VERSION; }

	public static Settings getSettings() { return settings; }

	public static Messages getMessages() { return messages; }

	public static EditorConfigManager getEditorConfigManager() { return editorConfigManager; }

	public static CWGUIManager getCWGUIManager() { return cwGUIManager; }

	public static ArrayList<Wing> getWings() { return wings; }

	public static void reload() {
		setupConfig();
		settings.reload();
		messages.reload();
		setupWings();
		
		//If the player had a wing equiped, update it with the newest created winglist
		for (CWPlayer cwPlayer : cwPlayerList.values()) {
			Wing wing = cwPlayer.getEquippedWing();
			if (wing == null) {
				return;
			}
			String wingID = wing.getID();
			Wing newWing = getWingByID(wingID);
			cwPlayer.setEquippedWing(newWing);
		}
		
	}

	public static void sendError(Exception e) {
		sendError(e + "");
	}

	public static void sendError(String error) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "{CustomWings} " + error);
		return;
	}

	public static Wing getWingByID(String ID) {
		Wing getWing = null;
		for (Wing wing : getWings())
			if (wing.getID().equals(ID)) getWing = wing;
		return getWing;
	}

	public static Wing getWingByGUISlot(int slot) {
		Wing getWing = null;
		for (Wing wing : getWings())
			if (wing.getGuiSlot() == slot) getWing = wing;
		return getWing;
	} 

	public static CWPlayer getCWPlayer(Player player) {
		UUID uuid = player.getUniqueId();

		CWPlayer cwPlayer = cwPlayerList.get(uuid);

		if (cwPlayer == null) {
			cwPlayer = new CWPlayer(uuid, plugin);
			cwPlayerList.put(uuid, cwPlayer);
		}

		return cwPlayer;
	}

	private boolean isServerVersionSupported() {

		List<String> supportedVersions = Arrays.asList("v1_13_R1", "v1_13_R2", "v1_14_R1", "v1_15_R1", "v1_16_R1");

		if (supportedVersions.contains(VERSION)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void setupConfig() {

		File cFile = new File(plugin.getDataFolder(), "config.yml");
		configFile = new YamlConfiguration();

		if (!cFile.exists()) {
			plugin.getLogger().info("CustomWings config.yml not found, creating!");
			cFile.getParentFile().mkdirs();
			plugin.saveResource("config.yml", false);
		} else {
			plugin.getLogger().info("CustomWings config.yml found, loading!");
		}

		try {
			configFile.load(cFile);
		} catch (Exception e) {
			CustomWings.sendError(e);
		}

	}

	private static void setupWings() {
		wings = new ArrayList<>();

		for (String wingID : configFile.getConfigurationSection("wings").getKeys(false)) {

			Wing wing = new Wing(wingID, plugin);
			wings.add(wing);
		}
	}
}
