package tigeax.customwings.wings;

import java.util.ArrayList;
import org.bukkit.GameMode;
import org.bukkit.entity.Pose;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;
import tigeax.customwings.CustomWings;
import tigeax.customwings.gui.ParticleItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/*
 * Class containing all information about a wingParticle
 * WingParticle is used in the Wing class
 */

public class WingParticle {

	private Wing wing;
	private String id;

	private Particle particle;
	private ParticleItem particleItem;

	private double distance, height, speed;
	private int angle;

	private Object particleData;
	private DustOptions dustOptions;
	private Material material;

	public WingParticle(Wing wing, String id, Particle particle, double distance, double height, int angle, double speed, DustOptions dustOptions, Material material) {
		this.wing = wing;
		this.id = id;
		this.particle = particle;
		this.particleItem = ParticleItem.valueOf(particle.toString());
		this.distance = distance;
		this.height = height;
		this.angle = angle;
		this.speed = speed;
		this.dustOptions = dustOptions;
		this.material = material;

		if (particle == Particle.REDSTONE) {
			particleData = dustOptions;
		}

		if (particle == Particle.BLOCK_CRACK
				|| particle == Particle.BLOCK_DUST
				|| particle == Particle.FALLING_DUST) {
			try {
				particleData = material.createBlockData();
			} catch (Exception e) {
				CustomWings.sendError(e);
				particleData = Material.BARRIER.createBlockData();
			}
		} else
			if (particle == Particle.ITEM_CRACK) particleData = new ItemStack(material);
	}

	public Wing getWing() { return this.wing; }

	public String getID() { return this.id; }

	public Material getItemMaterial() { return particleItem.getMaterial(); }

	public String getItemName() { return particleItem.getName(); }

	public Particle getParticle() { return particle; }

	public double getDistance() { return distance; }

	public double getHeight() { return height; }

	public int getAngle() { return angle; }

	public double getSpeed() { return speed; }

	public DustOptions getDustOptions() { return dustOptions; }

	public Material getMaterialData() { return material; }

	// Spawn the particle at a location for all players specified
	// wingSide is used to caculate the data for particles that can have direction
	public void spawnParticle(ArrayList<Player> players, Player owner, Location loc, String wingSide) {

		float yaw = loc.getYaw() - 90;

		if (wingSide.equals("left")) yaw = (float) (yaw + angle);
		if (wingSide.equals("right")) yaw = (float) (yaw - angle);
		yaw = (float) (yaw * Math.PI) / 180;
		double x = Math.cos(yaw) * distance;
		double z = Math.sin(yaw) * distance;
		for (Player player : players) {

			// Stop rendering wings for player if they look down
			if (player == owner && owner.getLocation().getPitch() > CustomWings.getSettings().getWingMaxPitch() && !owner.isGliding())
				continue;

			// Stop rendering wings for player that is swimming or crawling
			if (owner.getPose().equals(Pose.SWIMMING)) continue;

			// Shift wings down when player is sneaking
			if (owner.isSneaking() || owner.isInsideVehicle() || owner.isGliding()) {
				loc = loc.add(0, -0.2, 0);
			}

			player.spawnParticle(particle, loc, 0, x, height, z, speed, particleData);
		}
	}

	//vanish check
	private boolean isVanished(Player player) {
		for (MetadataValue meta : player.getMetadata("vanished")) {
			if (meta.asBoolean())
				return true;
		}
		return false;
	}

}
