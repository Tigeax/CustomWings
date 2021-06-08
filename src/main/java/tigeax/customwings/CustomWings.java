package tigeax.customwings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import tigeax.customwings.commands.Wings;
import tigeax.customwings.configuration.Messages;
import tigeax.customwings.editor.EditorConfigManager;
import tigeax.customwings.eventlisteners.AsyncPlayerChatEventListener;
import tigeax.customwings.eventlisteners.InventoryClickEventListener;
import tigeax.customwings.eventlisteners.InventoryCloseEventListener;
import tigeax.customwings.eventlisteners.OnTabComplete;
import tigeax.customwings.eventlisteners.PlayerCommandPreprocessEventListener;
import tigeax.customwings.eventlisteners.PlayerJoinEventListener;
import tigeax.customwings.eventlisteners.PlayerMoveListener;
import tigeax.customwings.eventlisteners.PlayerQuitEventListener;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.util.commands.Command;
import tigeax.customwings.wings.Wing;

/*
 * Main class of the CustomWings plugin
 * Minecraft version: 1.13+
 * By Tigeax
 */

public class CustomWings extends JavaPlugin {

	private static CustomWings instance;

	private Messages messages;
	private static Settings settings;
	private static EditorConfigManager editorConfigManager;
	private static CWGUIManager cwGUIManager;

	private static HashMap<UUID, CWPlayer> cwPlayerList;
	private static ArrayList<Wing> wings;

	private final static String VERSION = Bukkit.getServer().getClass().getPackage().getName()
			.replace("org.bukkit.craftbukkit", "").replace(".", "");

	private static FileConfiguration configFile;

	private static Economy econ = null;
	private static Permission perms = null;

	private static boolean vault = false;

	private static final int spigotResourceId = 59912;

	private ArrayList<Command> commands = new ArrayList<Command>();

	@Override
	public void onEnable() {

		setInstance(this);

		getLogger().info("Server running on: " + VERSION);

		if (!isServerVersionSupported()) {
			sendError("CustomWings does not support this server version! Plugin will now disable.");
			getServer().getPluginManager().disablePlugin(this);
		}

		// Check if there is a newer version available on Spigot
		new UpdateChecker(this, spigotResourceId).getVersion(version -> {
			if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
				Bukkit.getConsoleSender()
						.sendMessage(ChatColor.AQUA + "{CustomWings} You are running the latest CustomWings version");
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA
						+ "{CustomWings} A new CustomWings version is available on Spigot: https://www.spigotmc.org/resources/59912/");
			}
		});

		// bStats setup
		int pluginId = 8227;
		new Metrics(this, pluginId);

		setupConfig();

		settings = new Settings(this);
		messages = new Messages(this);

		editorConfigManager = new EditorConfigManager(this);
		cwGUIManager = new CWGUIManager();
		cwPlayerList = new HashMap<>();

		setupWings();

		// Setup commands
		registerCommand(new Wings("customwings", Arrays.asList("wings", "w"), "customwings.command"));

		// Register events
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChatEventListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryClickEventListener(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryCloseEventListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessEventListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinEventListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitEventListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), this);
		Bukkit.getPluginManager().registerEvents(new OnTabComplete(), this);

		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			try {
				setupEconomy();
				setupPermissions();
				vault = true;
				getLogger().info("Vault detected. Buy functionality enabled.");
			} catch (Exception e) {
				getLogger().info("Vault not detected. Buy functionality disabled.");
			}
		} else {
			getLogger().info("Vault not detected. Buy functionality disabled.");
		}

		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "{CustomWings} CustomWings has been enabled");
	}

	@Override
	public void onDisable() {

		// If a player has a CustomWings GUI open, close it
		for (Player player : Bukkit.getOnlinePlayers()) {
			InventoryView invView = player.getOpenInventory();

			if (cwGUIManager.getCWGUITypeByInvTitle(invView.getTitle()) == null)
				continue;
			player.closeInventory();
		}

		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "{CustomWings} CustomWings has been disabled");
	}

	private static void setInstance(CustomWings instance) {
		CustomWings.instance = instance;
	}

	public static CustomWings getInstance() {
		return instance;
	}

	private void registerCommand(Command commandObj) {
		commands.add(commandObj);
	}

	public FileConfiguration getCWConfig() {
		return configFile;
	}

	public String getServerVersion() {
		return VERSION;
	}

	public Settings getSettings() {
		return settings;
	}

	public Messages getMessages() {
		return messages;
	}

	public EditorConfigManager getEditorConfigManager() {
		return editorConfigManager;
	}

	public CWGUIManager getCWGUIManager() {
		return cwGUIManager;
	}

	public ArrayList<Wing> getWings() {
		return wings;
	}

	public boolean isVaultEnabled() {
		return vault;
	}

	public static Economy getEconomy() {
		return econ;
	}

	public Permission getPermissions() {
		return perms;
	}

	public void reload() {
		setupConfig();
		settings.reload();
		messages.update();
		editorConfigManager.reload();
		setupWings();

		// If the player had a wing equiped, update it with the newest created winglist
		for (CWPlayer cwPlayer : cwPlayerList.values()) {
			Wing wing = cwPlayer.getEquippedWing();
			if (wing == null) {
				return;
			}
			String wingID = wing.getID();
			Wing newWing = CustomWings.getInstance().getWingByID(wingID);
			cwPlayer.setEquippedWing(newWing);
		}

	}
	

	public Command getPluginCommand(String name) {
        Iterator<Command> commandsIterator = commands.iterator();

        while (commandsIterator.hasNext()) {
            Command command = (Command) commandsIterator.next();

            if (command.getName().equalsIgnoreCase(name)) {
                return command;
            }

            for (String alias : command.getAliases()) {
                if (name.equalsIgnoreCase(alias)) {
                    return command;
                }
            }

        }
        return null;
    }

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		econ = rsp.getProvider();
		return econ != null;
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	public void sendError(Exception e) {
		sendError(e + "");
	}

	public void sendError(String error) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "{CustomWings} " + error);
	}

	public Wing getWingByID(String ID) {
		Wing getWing = null;
		for (Wing wing : getWings())
			if (wing.getID().equalsIgnoreCase(ID))
				getWing = wing;
		return getWing;
	}

	public Wing getWingByGUISlot(int slot) {
		Wing getWing = null;
		for (Wing wing : getWings())
			if (wing.getGuiSlot() == slot)
				getWing = wing;
		return getWing;
	}

	public CWPlayer getCWPlayer(Player player) {
		UUID uuid = player.getUniqueId();

		CWPlayer cwPlayer = cwPlayerList.get(uuid);

		if (cwPlayer == null) {
			cwPlayer = new CWPlayer(uuid);
			cwPlayerList.put(uuid, cwPlayer);
		}

		return cwPlayer;
	}

	private boolean isServerVersionSupported() {

		List<String> supportedVersions = Arrays.asList("v1_13_R1", "v1_13_R2", "v1_14_R1", "v1_15_R1", "v1_16_R1", "v1_16_R2", "v1_16_R3");

		return supportedVersions.contains(VERSION);
	}

	public void setupConfig() {

		File cFile = new File(instance.getDataFolder(), "config.yml");
		configFile = new YamlConfiguration();

		if (!cFile.exists()) {
			instance.getLogger().info("CustomWings config.yml not found, creating!");
			cFile.getParentFile().mkdirs();
			instance.saveResource("config.yml", false);
		} else {
			instance.getLogger().info("CustomWings config.yml found, loading!");
		}
		try {
			configFile.load(cFile);
		} catch (Exception e) {
			sendError(e);
		}

	}

	private void setupWings() {
		wings = new ArrayList<>();

		for (String wingID : configFile.getConfigurationSection("wings").getKeys(false)) {
			Wing wing = new Wing(wingID, instance);
			wings.add(wing);
		}
	}

	private class UpdateChecker {

		private JavaPlugin plugin;
		private int resourceId;

		public UpdateChecker(JavaPlugin plugin, int resourceId) {
			this.plugin = plugin;
			this.resourceId = resourceId;
		}

		public void getVersion(final Consumer<String> consumer) {
			Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
				try (InputStream inputStream = new URL(
						"https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
						Scanner scanner = new Scanner(inputStream)) {
					if (scanner.hasNext()) {
						consumer.accept(scanner.next());
					}
				} catch (IOException exception) {
					this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
				}
			});
		}
	}

}
