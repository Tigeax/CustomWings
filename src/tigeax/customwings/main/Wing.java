package tigeax.customwings.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

/*
 * Class containing all information about a wing
 * Information is gotten from the config.yml file
 * Wing uses the WingParticle class to store information about a particle used in the wing
 */

public class Wing {

	public CustomWings plugin;
	private FileConfiguration config;

	private String ID;

	private ArrayList<Player> playersWithWingActive;
	private HashMap<Player, Location> wingPreview;

	private boolean hideInGUI;
	private ItemStack guiItem;
	private String guiItemName;
	private int guiSlot;

	private List<String> loreWhenEquipped, loreWhenUnequipped, loreWhenNoPermission;

	private boolean showWhenMoving;
	private List<String> whitelistedWorlds;

	private double startVertical, startHorizontal, distanceBetweenParticles;
	private int wingTimer;

	private boolean wingAnimation;
	private int wingFlapSpeed, startOffset, stopOffset;

	private BukkitTask wingRunnable;

	private ArrayList<WingParticle> wingParticles;

	// Hasmap containing the coordinates relative to the player
	// And the assinged particle at that coordinate
	// double[] functions as double[distance from player, height]
	private HashMap<double[], WingParticle> particleCoordinates;

	public Wing(String wingID, CustomWings plugin) {
		this.plugin = plugin;
		this.ID = wingID;

		this.config = plugin.getConfig();
		this.playersWithWingActive = new ArrayList<>();
		this.wingPreview = new HashMap<>();

		this.load();
	}

	public void reload() {
		if (wingRunnable != null) wingRunnable.cancel();

		load();

		if (wingRunnable != null) startWingRunnable();
	}

	// Get all the wing data from the config and parse them if needed
	private void load() {

		hideInGUI = Boolean.parseBoolean(getConfigFileWing().getString("guiItem.hideInGUI"));

		guiItemName = parseColors(getConfigFileWing().getString("guiItem.name"));
		guiItem = getItem(guiItemName, getConfigFileWing().getString("guiItem.material"));
		guiSlot = getConfigFileWing().getInt("guiItem.slot");

		loreWhenEquipped = parseLore(getConfigFileWing().getStringList("guiItem.loreWhenEquipped").toString());
		loreWhenUnequipped = parseLore(getConfigFileWing().getStringList("guiItem.loreWhenUnequipped").toString());
		loreWhenNoPermission = parseLore(getConfigFileWing().getStringList("guiItem.loreWhenNoPermission").toString());

		showWhenMoving = Boolean.parseBoolean(getConfigFileWing().getString("showWhenMoving"));
		whitelistedWorlds = parseWhitelistedWorlds(plugin.getConfig().getStringList("wings.SoulShadow.whitelistedWorlds").toString());

		startVertical = getConfigFileWing().getDouble("wingLayout.startVertical");
		startHorizontal = getConfigFileWing().getDouble("wingLayout.startHorizontal");
		distanceBetweenParticles = getConfigFileWing().getDouble("wingLayout.distanceBetweenParticles");
		wingTimer = getConfigFileWing().getInt("wingLayout.wingTimer");

		wingAnimation = Boolean.parseBoolean(getConfigFileWing().getString("wingLayout.wingAnimation"));
		wingFlapSpeed = getConfigFileWing().getInt("wingLayout.wingFlapSpeed");
		startOffset = getConfigFileWing().getInt("wingLayout.startOffset");
		stopOffset = getConfigFileWing().getInt("wingLayout.stopOffset");

		wingParticles = parseWingParticles(getConfigFileWing().getConfigurationSection("particles"));
		particleCoordinates = parseParticleCoordinates(getConfigFileWing().getConfigurationSection("particleLayout"));

	}

	public String getID() { return ID; }

	public boolean getHideInGUI() { return hideInGUI; }

	public String getGUIItemName() { return guiItemName; }

	public ItemStack getGuiItem() { return guiItem; }

	public int getGuiSlot() { return guiSlot; }

	public List<String> getLoreWhenEquipped() { return loreWhenEquipped; }

	public List<String> getLoreWhenUnequipped() { return loreWhenUnequipped; }

	public List<String> getLoreWhenNoPermission() { return loreWhenNoPermission; }

	public String getLoreWhenEquippedString() {
		return getLoreWhenEquipped().toString().replace("[", "").replace("]", "");
	}

	public String getLoreWhenUnequippedString() {
		return getLoreWhenUnequipped().toString().replace("[", "").replace("]", "");
	}

	public String getLoreWhenNoPermissionString() {
		return getLoreWhenNoPermission().toString().replace("[", "").replace("]", "");
	}

	public List<String> getWhitelistedWorlds() { return this.whitelistedWorlds; }

	public String getWhitelistedWorldsString() {
		return getWhitelistedWorlds().toString().replace("[", "").replace("]", "");
	}

	public boolean getShowWhenMoving() { return this.showWhenMoving; }

	public double getStartVertical() { return startVertical; }

	public double getStartHorizontal() { return startHorizontal; }

	public double getDistanceBetweenParticles() { return distanceBetweenParticles; }

	public int getWingTimer() { return wingTimer; }

	public boolean getWingAnimation() { return wingAnimation; }

	public int getWingFlapSpeed() { return wingFlapSpeed; }

	public int getStartOffset() { return startOffset; }

	public int getStopOffset() { return stopOffset; }

	public ArrayList<WingParticle> getWingParticles() { return wingParticles; }

	public ArrayList<Player> getPlayersWithWingActive() { return playersWithWingActive; }

	public void addPlayersWithWingActive(Player player) {

		playersWithWingActive.add(player);

		// If there where no players that had the wing equipped before, start the wing runnable
		if (playersWithWingActive.size() == 1) this.startWingRunnable();
	}

	public void removePlayersWithWingActive(Player player) {
		playersWithWingActive.remove(player);
	}

	public void addToPreview(Player player) {
		wingPreview.put(player, player.getLocation());
	}

	public void removeFromPreview(Player player) {
		wingPreview.remove(player);
	}

	public boolean isPreviewing(Player player) {
		return wingPreview.containsKey(player);
	}

	public WingParticle getWingParticleByID(String ID) {
		for (WingParticle wingParticle : wingParticles) {
			if (wingParticle.getID().equals(ID)) { return wingParticle; }
		}
		return null;
	}

	private ConfigurationSection getConfigFileWing() { return config.getConfigurationSection("wings." + ID); }

	private void startWingRunnable() {

		wingRunnable = new BukkitRunnable() {

			boolean flapDirectionSwitch = false;
			int offsetDegrees = startOffset;

			@Override
			public void run() {

				// If no players have this wing equipped stop the timer
				if (getPlayersWithWingActive().isEmpty()) {
					this.cancel();
				}

				// If the wing should be animated change the offsetDegrees
				// To go back and forth between the start and stop offset
				if (wingAnimation) {

					offsetDegrees = flapDirectionSwitch ? offsetDegrees - wingFlapSpeed : offsetDegrees + wingFlapSpeed;

					if (offsetDegrees >= stopOffset) flapDirectionSwitch = true;

					if (offsetDegrees <= startOffset) flapDirectionSwitch = false;
				}

				// Loop through all the players that are previewing a wing
				for (Player player : wingPreview.keySet()) {

					Location loc = wingPreview.get(player);
					ArrayList<Player> playersToShowWing = getPlayersWhoSeeWing(player, loc);

					spawnWing(loc, playersToShowWing, offsetDegrees);
				}

				// Loop through all the players that have the wing active
				for (Player player : getPlayersWithWingActive()) {

					if (wingPreview.containsKey(player)) {
						continue;
					}

					CWPlayer cwPlayer = CustomWings.getCWPlayer(player);

					// Continue if the wing should hidden when moving and the player is moving
					if (!getShowWhenMoving() && cwPlayer.isMoving()) {
						continue;
					}

					// Check if the wing should be shown in this world
					if (!getWhitelistedWorlds().contains("all")) {
						if (!getWhitelistedWorlds().contains(player.getWorld().toString())) {
							continue;
						}
					}

					Location playerLoc = player.getLocation();
					ArrayList<Player> playersToShowWing = getPlayersWhoSeeWing(player, player.getLocation());

					spawnWing(playerLoc, playersToShowWing, offsetDegrees);

				}
			}
		}.runTaskTimer(plugin, 0, wingTimer);
	}

	// Spawn all the particles of the wing at a certain location for certain players
	private void spawnWing(Location loc, ArrayList<Player> showTo, float degreeOffset) {

		// Loop through all the coordinates of the wing and spawn a particle for both the left and right part at that location
		for (double[] coordinate : particleCoordinates.keySet()) {

			WingParticle wingParticle = particleCoordinates.get(coordinate);
			double distance = coordinate[0];
			double height = coordinate[1];

			float yawLeft = loc.getYaw() - degreeOffset;
			float yawRight = loc.getYaw() + 180 + degreeOffset;

			Location particleLocLeft = getParticleSpawnLoc(loc, yawLeft, distance, height);
			Location particleLocRight = getParticleSpawnLoc(loc, yawRight, distance, height);

			wingParticle.spawnParticle(showTo, particleLocLeft, "left");
			wingParticle.spawnParticle(showTo, particleLocRight, "right");
		}
	}

	// Return the location the particle should be spawned at
	// Relative to the location the wing is spawned at
	// Based on the direction, distance and heigt
	private Location getParticleSpawnLoc(Location loc, float direction, double distance, double height) {

		Location wingParticleLoc = loc.clone();

		float directionRAD = (float) ((direction * Math.PI) / 180);
		Vector vector = new Vector(Math.cos(directionRAD) * distance, height, Math.sin(directionRAD) * distance);
		wingParticleLoc.add(vector);

		return wingParticleLoc;
	}

	// Return all the player that can see the wing of player
	private ArrayList<Player> getPlayersWhoSeeWing(Player wingOwner, Location wingLocation) {

		ArrayList<Player> playersWhoCanSeeWing = new ArrayList<>();

		// Loop thought all the online players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

			// Skip if the player is not in the same world as the wing
			if (!(onlinePlayer.getWorld() == wingLocation.getWorld())) continue;

			// Add the player himself to the list
			if (onlinePlayer == wingOwner) {
				playersWhoCanSeeWing.add(onlinePlayer);
				continue;
			}

			CWPlayer cwPlayer = CustomWings.getCWPlayer(onlinePlayer);

			// Skip if onlinePlayer doens't want to see other players wings
			if (cwPlayer.getHideOtherPlayerWings()) continue;

			Location onlinePlayerLoc = onlinePlayer.getLocation();

			// Skip if the player is more then the wingViewDistance away from the wing
			if (onlinePlayerLoc.distance(wingLocation) > CustomWings.getSettings().getWingViewDistance()) continue;

			playersWhoCanSeeWing.add(onlinePlayer);
		}

		return playersWhoCanSeeWing;

	}

	// Turn the data gotten from the config.yml into a HashMap containing the relative coordinates and the assinged wingParticle
	private HashMap<double[], WingParticle> parseParticleCoordinates(ConfigurationSection particleLayout) {

		HashMap<double[], WingParticle> particleCoordinates = new HashMap<>();
		Set<String> rows = particleLayout.getKeys(false);
		double distance = 0;
		double height = startVertical + (rows.size() * distanceBetweenParticles); // Highest vertical point of the wing

		// Go through all the horizontal rows
		for (String rowNumber : rows) {

			height = height - distanceBetweenParticles;
			distance = startHorizontal;

			String[] particleLine = particleLayout.getString(rowNumber).split(",");

			// Go though each "particle" on the row
			for (String particleID : particleLine) {

				// "-" means there should be no particle at the coordinate
				if (particleID.equals("-")) {
					distance = distance + distanceBetweenParticles;
					continue;
				}

				double[] coordinates = new double[2];
				coordinates[0] = distance;
				coordinates[1] = height;

				particleCoordinates.put(coordinates, getWingParticleByID(particleID));

				distance = distance + distanceBetweenParticles;

			}
		}
		return particleCoordinates;
	}

	// Turn the data gotten from the config.yml into all the WingParticles of a wing
	private ArrayList<WingParticle> parseWingParticles(ConfigurationSection wingParticlesConfig) {

		ArrayList<WingParticle> particles = new ArrayList<>();

		// Loop throught all the wing particles of the wing
		for (String key : wingParticlesConfig.getKeys(false)) {

			ConfigurationSection particleConfig = wingParticlesConfig.getConfigurationSection(key);

			Particle particle;

			try {
				particle = Particle.valueOf(particleConfig.getString("particle"));
			} catch (Exception e) {
				CustomWings.sendError(e);
				particle = Particle.BARRIER;
			}

			double distance = particleConfig.getDouble("distance");
			double height = particleConfig.getDouble("height");
			int angle = particleConfig.getInt("angle");
			double speed = particleConfig.getDouble("speed");

			Material material = Material.valueOf(particleConfig.getString("blockType"));
			DustOptions color = new Particle.DustOptions(Color.fromRGB(particleConfig.getInt("color")), (float) 1);

			WingParticle wingParticle = new WingParticle(this, key, particle, distance, height, angle, speed, color, material);

			particles.add(wingParticle);
		}

		return particles;

	}

	private ItemStack getItem(String name, String material) {
		ItemStack item = new ItemStack(Material.valueOf(material));

		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);

		return item;
	}

	private List<String> parseLore(String loreString) {
		return Arrays.asList(parseColors(loreString).replace("]", "").replace("[", "").split(", "));
	}

	private List<String> parseWhitelistedWorlds(String list) {
		return Arrays.asList(list.replace("]", "").replace("[", "").split(", "));
	}

	private String parseColors(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}
}
