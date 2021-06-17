package tigeax.customwings.wing;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.Config;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.nms.NMSSupport;

public class Wing {

	private CustomWings plugin;
	private Config config;
	private WingConfig wingConfig;

	private ArrayList<Player> playersWithWing;
	private BukkitTask wingRunnable;

	public Wing(CustomWings plugin, WingConfig wingConfig) {
		this.wingConfig = wingConfig;
		this.plugin = plugin;
		this.config = plugin.getConfig();
		this.playersWithWing = new ArrayList<>();
	}

	public WingConfig getConfig() {
		return wingConfig;
	}

	public void reload() {
		if (wingRunnable != null)
			wingRunnable.cancel(); // Temperatry stop the runnable

		wingConfig.reload();

		if (wingRunnable != null)
			startWingRunnable(); // Restart the runnable again with the (possibly new) settings
	}

	public void removeAllPlayersWithWingActive() {
		playersWithWing = new ArrayList<>();
	}

	public boolean doesPlayerHaveWingEquipped(Player player) {
		for (Player playerFromList : playersWithWing) {
			if (playerFromList == player) {
				return true;
			}
		}
		return false;
	}

	public String getPermission() {
		return "customwings.wing." + wingConfig.getID();
	}

	public ArrayList<Player> getPlayersWithWingActive() {
		return playersWithWing;
	}

	public void addPlayer(Player player) {

		playersWithWing.add(player);

		// If there where no players that had the wing equipped before, start the wing
		// runnable
		if (playersWithWing.size() == 1)
			this.startWingRunnable();
	}

	public void removePlayer(Player player) {
		playersWithWing.remove(player);
	}

	private void startWingRunnable() {

		wingRunnable = new BukkitRunnable() {

			boolean flapDirectionSwitch = false;
			int animationState = wingConfig.getStartOffset();

			@Override
			public void run() {

				// If no players have this wing equipped stop the timer
				if (getPlayersWithWingActive().isEmpty()) {
					this.cancel();
				}

				// If the wing should be animated change the offsetDegrees
				// To go back and forth between the start and stop offset
				if (wingConfig.getWingAnimation()) {

					animationState = flapDirectionSwitch ? animationState - wingConfig.getWingFlapSpeed()
							: animationState + wingConfig.getWingFlapSpeed();

					if (animationState >= wingConfig.getStopOffset())
						flapDirectionSwitch = true;

					if (animationState <= wingConfig.getStartOffset())
						flapDirectionSwitch = false;
				}

				// Loop through all the players that have the wing active
				getPlayersWithWingActive().forEach((wingOwner) -> runWingOwner(wingOwner, animationState));

			}
		}.runTaskTimerAsynchronously(plugin, 0, wingConfig.getWingTimer());
	}

	private void runWingOwner(Player wingOwner, int animationState) {

		CWPlayer cwPlayer = CustomWings.getInstance().getCWPlayer(wingOwner);

		// Spawn the wings for players that are previewing their wing
		if (cwPlayer.isPreviewingWing()) {

			Location wingLoc = cwPlayer.getPreviewWingLocation();
			ArrayList<Player> playersToShowWing = getPlayersWhoSeeWing(wingOwner, wingLoc, false);

			spawnWing(wingLoc, playersToShowWing, animationState);
			return;
		}

		// Continue if the wing should hidden when moving and the player is moving
		if (!wingConfig.getShowWhenMoving() && cwPlayer.isMoving()) {
			return;
		}

		// Check if the wing should be shown in this world
		if (!wingConfig.getWhitelistedWorlds().contains("all")) {
			if (!wingConfig.getWhitelistedWorlds().contains(wingOwner.getWorld().getName())) {
				return;
			}
		}

		// Skip if the owner is dead
		if (wingOwner.isDead())
			return;

		// Hide wings if owner is in spectator or in vanish
		if (wingOwner.getGameMode().equals(GameMode.SPECTATOR) || isVanished(wingOwner))
			return;

		// Stop rendering wings for player when they have invisibility potion effect
		if (wingOwner.hasPotionEffect(PotionEffectType.INVISIBILITY) && config.getInvisibilityPotionHidesWing())
			return;

		// Stop rendering wings if player is sleeping
		if (wingOwner.isSleeping())
			return;

		// Stop rendering wings if player is in vehicle
		if (wingOwner.isInsideVehicle())
			return;

		// Stop rendering wings for player that is swimming or crawling
		if (wingOwner.getPose().equals(Pose.SWIMMING))
			return;

		// Stop rendering wings for player that is gliding
		if (wingOwner.isGliding())
			return;

		Location wingLoc = wingOwner.getLocation();

		// Instead of using the Yaw of the head of the player we will try to use the Yaw
		// of the player's body
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

		// Loop through all the coordinates of the wing and spawn a particle for both
		// the left and right part at that location
		for (double[] coordinate : wingConfig.getParticleCoordinates().keySet()) {

			WingParticle wingParticle = wingConfig.getParticleCoordinates().get(coordinate);
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
	// Based on the x and y offset from the center of the wing and which side of the
	// Wing the particle is on
	private Location getParticleSpawnLoc(Location loc, double x, double y, WingSide wingSide, int animationState) {

		Location wingParticleLoc = loc.clone();
		float yaw = wingParticleLoc.getYaw();

		if (wingSide == WingSide.LEFT)
			yaw = yaw - animationState;
		if (wingSide == WingSide.RIGHT)
			yaw = yaw + 180 + animationState;

		double yawRad = Math.toRadians(yaw);
		Vector vector = new Vector(Math.cos(yawRad) * x, y, Math.sin(yawRad) * x);
		wingParticleLoc.add(vector);
		wingParticleLoc.setYaw((float) Math.toDegrees(yawRad));

		return wingParticleLoc;
	}

	public enum WingSide {
		LEFT, RIGHT
	}

	// Return all the player that can see the wing of player
	private ArrayList<Player> getPlayersWhoSeeWing(Player wingOwner, Location wingLocation, Boolean wingPreview) {

		ArrayList<Player> playersWhoCanSeeWing = new ArrayList<>();

		// Loop thought all the online players
		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

			// Skip if the player is not in the same world as the wing
			if (!(onlinePlayer.getWorld() == wingLocation.getWorld()))
				continue;

			// Add the player himself to the list
			if (onlinePlayer == wingOwner) {

				if (!wingPreview) {
					// Stop rendering wings for the player if they look down
					if (wingOwner.getLocation().getPitch() > config.getWingMaxPitch())
						continue;
				}

				playersWhoCanSeeWing.add(onlinePlayer);
				continue;
			}

			CWPlayer cwPlayer = CustomWings.getInstance().getCWPlayer(onlinePlayer);

			// Skip if onlinePlayer doesn't want to see other players wings
			if (cwPlayer.getHideOtherPlayerWings())
				continue;

			Location onlinePlayerLoc = onlinePlayer.getLocation();

			// Skip if the player is more then the wingViewDistance away from the wing
			if (onlinePlayerLoc.distance(wingLocation) > config.getWingViewDistance())
				continue;

			playersWhoCanSeeWing.add(onlinePlayer);
		}

		return playersWhoCanSeeWing;

	}

	// vanish check
	private boolean isVanished(Player player) {
		for (MetadataValue meta : player.getMetadata("vanished")) {
			if (meta.asBoolean())
				return true;
		}
		return false;
	}

}
