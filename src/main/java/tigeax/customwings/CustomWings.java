package tigeax.customwings;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import tigeax.customwings.commands.Wings;
import tigeax.customwings.configuration.Config;
import tigeax.customwings.configuration.Messages;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.database.YamlDatabase;
import tigeax.customwings.eventlisteners.AsyncPlayerChatEventListener;
import tigeax.customwings.eventlisteners.InventoryClickEventListener;
import tigeax.customwings.eventlisteners.InventoryCloseEventListener;
import tigeax.customwings.eventlisteners.OnTabComplete;
import tigeax.customwings.eventlisteners.PlayerCommandPreprocessEventListener;
import tigeax.customwings.eventlisteners.PlayerJoinEventListener;
import tigeax.customwings.eventlisteners.PlayerMoveListener;
import tigeax.customwings.eventlisteners.PlayerQuitEventListener;
import tigeax.customwings.menus.MenuManager;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.commands.Command;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuHolder;
import tigeax.customwings.wing.Wing;
import tigeax.customwings.wing.WingParticle;

/*
 * Main class of the CustomWings plugin
 * Minecraft version: 1.13+
 * By Tigeax
 */

public class CustomWings extends JavaPlugin {

	private static CustomWings instance;

	private Messages messages;
	private Config config;
	private YamlDatabase yamlDatabase;

	private MenuManager menus;

	private static ArrayList<CWPlayer> cwPlayerList;
	private static ArrayList<Wing> wings;

	private final static String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit", "").replace(".", "");

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
			getLogger().severe("CustomWings does not support this server version! Plugin will now disable.");
			getServer().getPluginManager().disablePlugin(this);
		}

		// Check if there is a newer version available on Spigot
		Util.runUpdateChecker(this, spigotResourceId);

		// bStats setup
		int pluginId = 8227;
		new Metrics(this, pluginId);

		// Setup Configuration
		config = new Config(this);
		messages = new Messages(this);

		// Setup database
		yamlDatabase = new YamlDatabase(this);

		wings = setupWings();

		menus = new MenuManager(this);

		cwPlayerList = new ArrayList<>();

		// Setup command
		registerCommand(new Wings(config.commandName(), config.commandAliases(), "customwings.command"));

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

		// Load equipped wings and settings from database
		for (Player player : Bukkit.getOnlinePlayers()) {

			CWPlayer cwPlayer = getCWPlayer(player);

			String wingId = yamlDatabase.getPlayerEquippedWingID(player);
			Boolean hideOtherPlayerWings = yamlDatabase.getPlayerHideOtherPlayerWings(player);

			if (wingId != null) {
				Wing wing = getWingByID(wingId);
				if (wing != null) {
					cwPlayer.setEquippedWing(wing);
				}
			}

			// Only set if true, since false is the default
			if (hideOtherPlayerWings) {
				cwPlayer.setHideOtherPlayerWings(true);
			}


		}

		Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "{CustomWings} CustomWings has been enabled");
	}

	@Override
	public void onDisable() {

		for (Player player : Bukkit.getOnlinePlayers()) {

			CWPlayer cwPlayer = getCWPlayer(player);
			Wing wing = cwPlayer.getEquippedWing();
			Boolean hideOtherPlayerWings = cwPlayer.getHideOtherPlayerWings();

			// Save player's equpped wing to the datbase
			yamlDatabase.savePlayerEquippedWing(player, wing);
			cwPlayer.setEquippedWing(null); // Remove the wing from the player

			// Only save if true, as false is the default
			if (hideOtherPlayerWings) {
				yamlDatabase.savePlayerHideOtherPlayerWings(player, true);
			}

			// If a player has a CustomWings GUI open, close it
			Inventory inv = player.getOpenInventory().getTopInventory();

			if (inv.getHolder() instanceof MenuHolder) {
				player.closeInventory();
			}
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

	public String getServerVersion() {
		return VERSION;
	}

	@Override
	public Config getConfig() {
		return config;
	}

	public Messages getMessages() {
		return messages;
	}

	public MenuManager getMenus() {
		return menus;
	}

	public YamlDatabase getYamlDatabase() {
		return yamlDatabase;
	}

	public List<Wing> getWings() {
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

		config.update();
		messages.update();
		yamlDatabase.update();

		for (Wing wing : getWings()) {
			wing.reload();
			for (WingParticle wingParticle : wing.getConfig().getWingParticles()) {
				wingParticle.reload();
			}
		}

		menus.reload();

		// If a player has a CustomWings GUI open, update it
		for (Player player : Bukkit.getOnlinePlayers()) {
			Inventory inv = player.getOpenInventory().getTopInventory();

			if (inv.getHolder() instanceof MenuHolder) {
				ItemMenu menu = ((MenuHolder) inv.getHolder()).getMenu();
				menu.update(player);
			}
		}

		// If the player had a wing equiped, update it with the newest created winglist
		for (CWPlayer cwPlayer : cwPlayerList) {
			Wing wing = cwPlayer.getEquippedWing();
			if (wing == null) {
				continue;
			}
			String wingID = wing.getConfig().getID();
			Wing newWing = getWingByID(wingID);
			cwPlayer.setEquippedWing(newWing);
		}
	}

	private ArrayList<Wing> setupWings() {

		ArrayList<Wing> wings = new ArrayList<>();

		File wingsFolder = new File(getDataFolder(), "wings");

		// Check if the wings folder exits, if not create it with the default wings
		if (!wingsFolder.exists()) {

			getLogger().info("Could not find wings folder. Creating it, with default wings");

			wingsFolder.mkdirs(); // Create the folder

			// Save the wing files
			saveResource("wings/angel.yml", false);
			saveResource("wings/bloodhound.yml", false);
			saveResource("wings/frost.yml", false);
			saveResource("wings/soulshadow.yml", false);
			saveResource("wings/wisdom.yml", false);
		}

		File[] wingFiles = wingsFolder.listFiles();

		for (File file : wingFiles) {

			WingConfig wingConfig = new WingConfig(this, file);

			Wing wing = new Wing(this, wingConfig);

			wings.add(wing);

		}

		return wings;
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

	public Wing getWingByID(String ID) {
		Wing getWing = null;
		for (Wing wing : getWings())
			if (wing.getConfig().getID().equalsIgnoreCase(ID))
				getWing = wing;
		return getWing;
	}

	public CWPlayer getCWPlayer(Player player) {

		// Check if there already is a CWPlayer instance for player
		for (CWPlayer cwPlayer : cwPlayerList) {
			if (cwPlayer.getPlayer() == player) {
				return cwPlayer;
			}
		}

		// If not create it
		CWPlayer cwPlayer = new CWPlayer(player);
		cwPlayerList.add(cwPlayer);

		return cwPlayer;
	}

	public void deleteCWPlayer(CWPlayer cwPlayer) {
		cwPlayer.delete();
		cwPlayerList.remove(cwPlayer);
	}

	private boolean isServerVersionSupported() {

		List<String> supportedVersions = Arrays.asList("v1_13_R1", "v1_13_R2", "v1_14_R1", "v1_15_R1", "v1_16_R1",
				"v1_16_R2", "v1_16_R3");

		return supportedVersions.contains(VERSION);
	}

}
