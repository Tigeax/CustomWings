package tigeax.customwings.wings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.bukkit.*;
import org.bukkit.Particle.DustOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.nms.NMSSupport;

/*
 * Class containing all information about a wing
 * Information is gotten from the config.yml file
 * Wing uses the WingParticle class to store information about a particle used in the wing
 */

public class Wing {

	public CustomWings plugin;
	private FileConfiguration config;

	private final String ID;

	private ArrayList<Player> playersWithWingActive;

	private boolean hideInGUI;
	private ItemStack guiItem;
	private String guiItemName;
	private int guiSlot;

	private List<String> loreWhenEquipped, loreWhenUnequipped, loreWhenNoPermission, loreWhenCanBuy;

	private boolean showWhenMoving;
	private List<String> whitelistedWorlds;

	private double startVertical, startHorizontal, distanceBetweenParticles;
	private int wingTimer;

	private boolean wingAnimation;
	private int wingFlapSpeed, startOffset, stopOffset;

	private BukkitTask wingRunnable;

	private ArrayList<WingParticle> wingParticles;

	private int wingPrice;
	private String priceType;
	private String buyMessage;

	// Hasmap containing the coordinates relative to the player
	// And the assinged particle at that coordinate
	// double[] functions as double[distance from player, height]
	private HashMap<double[], WingParticle> particleCoordinates;

	public Wing(String wingID, CustomWings plugin) {
		this.plugin = plugin;
		this.ID = wingID;
		this.config = plugin.getCWConfig();
		this.playersWithWingActive = new ArrayList<>();
		this.load();
	}

	public void reload() {
		if (wingRunnable != null) wingRunnable.cancel(); //Temperatry stop the runnable

		CustomWings.setupConfig();
		load();

		if (wingRunnable != null) startWingRunnable(); //Restart the runnable again with the (possibly new) settings
	}

	// Get all the wing data from the config and parse them if needed
	private void load() {

		this.config = plugin.getCWConfig();

		hideInGUI = Boolean.parseBoolean(getConfigFileWing().getString("guiItem.hideInGUI"));

		guiItemName = parseColors(getConfigFileWing().getString("guiItem.name"));
		guiItem = getItem(guiItemName, getConfigFileWing().getString("guiItem.material"));
		guiSlot = getConfigFileWing().getInt("guiItem.slot");

		loreWhenEquipped = parseLore(getConfigFileWing().getStringList("guiItem.loreWhenEquipped").toString());
		loreWhenUnequipped = parseLore(getConfigFileWing().getStringList("guiItem.loreWhenUnequipped").toString());
		loreWhenNoPermission = parseLore(getConfigFileWing().getStringList("guiItem.loreWhenNoPermission").toString());

		showWhenMoving = Boolean.parseBoolean(getConfigFileWing().getString("showWhenMoving"));
		whitelistedWorlds = parseWhitelistedWorlds(getConfigFileWing().getStringList("whitelistedWorlds").toString());

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

		wingPrice = getConfigFileWing().getInt("price");
		priceType = getConfigFileWing().getString("price-type");

		try {
			buyMessage = getConfigFileWing().getString("buyMessage");
			if (buyMessage == null) {
				buyMessage = "&3You just bought "+guiItemName;
			}
		} catch (NullPointerException e) {
			// If buy message was not supplied set it to this
			buyMessage = "&3You just bought "+guiItemName;
		}

		loreWhenCanBuy = getConfigFileWing().getStringList("guiItem.loreWhenCanBuy");

	}

	public String getID() { return ID; }

	public boolean getHideInGUI() { return hideInGUI; }

	public String getGUIItemName() { return guiItemName; }

	public ItemStack getGuiItem() { return guiItem; }

	public int getGuiSlot() { return guiSlot; }

	public List<String> getLoreWhenEquipped() { return loreWhenEquipped; }

	public List<String> getLoreWhenUnequipped() { return loreWhenUnequipped; }

	public List<String> getLoreWhenNoPermission() { return loreWhenNoPermission; }

	public List<String> getloreWhenCanBuy() {

		List<String> lore = new ArrayList<>();

		try {
			loreWhenCanBuy.isEmpty();
		} catch (NullPointerException e) {
			lore.add(ChatColor.translateAlternateColorCodes('&',"You can buy this for "+wingPrice));
			return lore;
		}

		for (String s : loreWhenCanBuy) {
			s = s.replaceAll("%price%", String.valueOf(wingPrice));
			lore.add(ChatColor.translateAlternateColorCodes('&',s));
		}
		return lore;
	}

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

	public WingParticle getWingParticleByID(String ID) {
		for (WingParticle wingParticle : wingParticles) {
			if (wingParticle.getID().equals(ID)) { return wingParticle; }
		}
		return null;
	}

	public int getWingPrice() {
		return wingPrice;
	}

	public String getPriceType() {
		return priceType;
	}

	public String getBuyMessage() {return buyMessage;}

	private ConfigurationSection getConfigFileWing() { return config.getConfigurationSection("wings." + ID); }

	private void startWingRunnable() {

		wingRunnable = new BukkitRunnable() {

			boolean flapDirectionSwitch = false;
			int animationState = startOffset;

			@Override
			public void run() {

				// If no players have this wing equipped stop the timer
				if (getPlayersWithWingActive().isEmpty()) {
					this.cancel();
				}

				// If the wing should be animated change the offsetDegrees
				// To go back and forth between the start and stop offset
				if (wingAnimation) {

					animationState = flapDirectionSwitch ? animationState - wingFlapSpeed : animationState + wingFlapSpeed;

					if (animationState >= stopOffset) flapDirectionSwitch = true;

					if (animationState <= startOffset) flapDirectionSwitch = false;
				}

				// Loop through all the players that have the wing active
				getPlayersWithWingActive().forEach((wingOwner) -> runWingOwner(wingOwner, animationState));

			}
		}.runTaskTimerAsynchronously(plugin, 0, wingTimer);
	}

	private void runWingOwner(Player wingOwner, int animationState) {

		CWPlayer cwPlayer = CustomWings.getCWPlayer(wingOwner);

		// Spawn the wings for players that are previewing their wing
		if (cwPlayer.isPreviewingWing()) {

			Location wingLoc = cwPlayer.getPreviewWingLocation();
			ArrayList<Player> playersToShowWing = getPlayersWhoSeeWing(wingOwner, wingLoc, false);

			spawnWing(wingLoc, playersToShowWing, animationState);
			return;
		}

		// Continue if the wing should hidden when moving and the player is moving
		if (!getShowWhenMoving() && cwPlayer.isMoving()) {
			return;
		}

		// Check if the wing should be shown in this world
		if (!getWhitelistedWorlds().contains("all")) {
			if (!getWhitelistedWorlds().contains(wingOwner.getWorld().getName())) {
				return;
			}
		}

		// Skip if the owner is dead
		if (wingOwner.isDead()) return;

		// Hide wings if owner is in spectator or in vanish
		if (wingOwner.getGameMode().equals(GameMode.SPECTATOR) || isVanished(wingOwner)) return;

		// Stop rendering wings for player when they have invisibility potion effect
		if (wingOwner.hasPotionEffect(PotionEffectType.INVISIBILITY) && CustomWings.getSettings().getInvisPotionHidesWing())
			return;

		// Stop rendering wings if player is sleeping
		if (wingOwner.isSleeping()) return;

		// Stop rendering wings if player is in vehicle
		if (wingOwner.isInsideVehicle()) return;

		// Stop rendering wings for player that is swimming or crawling
		if (wingOwner.getPose().equals(Pose.SWIMMING)) return;

		// Stop rendering wings for player that is gliding
		if (wingOwner.isGliding()) return;

		Location wingLoc = wingOwner.getLocation();

		// Instead of using the Yaw of the head of the player we will try to use the Yaw of the player's body
		float bodyYaw = NMSSupport.getBodyRotation(wingOwner);
		wingLoc.setYaw(bodyYaw);

		// Shift the wing down if the player is sneaking
		if (wingOwner.isSneaking() && !wingOwner.isFlying()) {
			wingLoc = wingLoc.add(0, -0.25, 0);
		}

		ArrayList<Player> playersToShowWing = getPlayersWhoSeeWing(wingOwner, wingLoc, false);

		spawnWing(wingLoc, playersToShowWing, animationState);
	}

	// Spawn all the particles of the wing at a certain location for certain players
	private void spawnWing(Location wingLoc, ArrayList<Player> showToPlayers, int animationState) {

		// Loop through all the coordinates of the wing and spawn a particle for both the left and right part at that location
		for (double[] coordinate : particleCoordinates.keySet()) {

			WingParticle wingParticle = particleCoordinates.get(coordinate);
			double x = coordinate[0];
			double y = coordinate[1];

			Location particleLocLeft = getParticleSpawnLoc(wingLoc, x, y, WingSide.LEFT, animationState);
			Location particleLocRight = getParticleSpawnLoc(wingLoc, x, y, WingSide.RIGHT, animationState);

			wingParticle.spawnParticle(particleLocLeft, showToPlayers, WingSide.LEFT);
			wingParticle.spawnParticle(particleLocRight, showToPlayers, WingSide.RIGHT);
		}
	}


	// Return the location the particle should be spawned at
	// Relative to the location the wing is spawned at
	// Based on the x and y offset from the center of the wing and which side of the Wing the particle is on
	private Location getParticleSpawnLoc(Location loc, double x, double y, WingSide wingSide, int animationState) {

		Location wingParticleLoc = loc.clone();
		float yaw = wingParticleLoc.getYaw();

		if (wingSide == WingSide.LEFT) yaw = yaw - animationState;
		if (wingSide == WingSide.RIGHT) yaw = yaw + 180 + animationState;

		double yawRad = Math.toRadians(yaw);
		Vector vector = new Vector(Math.cos(yawRad) * x, y, Math.sin(yawRad) * x);
		wingParticleLoc.add(vector);
		wingParticleLoc.setYaw((float) Math.toDegrees(yawRad));

		return wingParticleLoc;
	}

	public enum WingSide {
		LEFT,
		RIGHT
	}

	// Return all the player that can see the wing of player
	private ArrayList<Player> getPlayersWhoSeeWing(Player wingOwner, Location wingLocation, Boolean wingPreview) {

		ArrayList<Player> playersWhoCanSeeWing = new ArrayList<>();

		// Loop thought all the online players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

			// Skip if the player is not in the same world as the wing
			if (!(onlinePlayer.getWorld() == wingLocation.getWorld())) continue;

			// Add the player himself to the list
			if (onlinePlayer == wingOwner) {

				if (!wingPreview) {
					// Stop rendering wings for the player if they look down
					if (wingOwner.getLocation().getPitch() > CustomWings.getSettings().getWingMaxPitch())
						continue;
				}

				playersWhoCanSeeWing.add(onlinePlayer);
				continue;
			}

			CWPlayer cwPlayer = CustomWings.getCWPlayer(onlinePlayer);

			// Skip if onlinePlayer doesn't want to see other players wings
			if (cwPlayer.getHideOtherPlayerWings()) continue;

			Location onlinePlayerLoc = onlinePlayer.getLocation();

			// Skip if the player is more then the wingViewDistance away from the wing
			if (onlinePlayerLoc.distance(wingLocation) > CustomWings.getSettings().getWingViewDistance()) continue;

			playersWhoCanSeeWing.add(onlinePlayer);
		}

		return playersWhoCanSeeWing;

	}

	//vanish check
	private boolean isVanished(Player player) {
		for (MetadataValue meta : player.getMetadata("vanished")) {
			if (meta.asBoolean())
				return true;
		}
		return false;
	}

	// Turn the data gotten from the config.yml into a HashMap containing the relative coordinates and the assinged wingParticle
	private HashMap<double[], WingParticle> parseParticleCoordinates(ConfigurationSection particleLayout) {

		HashMap<double[], WingParticle> particleCoordinates = new HashMap<>();
		Set<String> rows = particleLayout.getKeys(false);
		double distance;
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