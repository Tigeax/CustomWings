package tigeax.customwings.main;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.gui.ParticleItem;

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

	private Object partileData;
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
			partileData = dustOptions;
		}

		if (particle == Particle.BLOCK_CRACK || particle == Particle.BLOCK_DUST || particle == Particle.FALLING_DUST) {
			try {
				partileData = material.createBlockData();
			} catch (Exception e) {
				CustomWings.sendError(e);
				partileData = Material.BARRIER.createBlockData();
			}
		} else
			if (particle == Particle.ITEM_CRACK) partileData = new ItemStack(material);
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
	public void spawnParticle(ArrayList<Player> players, Location loc, String wingSide) {

		float yaw = loc.getYaw() - 90;

		if (wingSide == "left") yaw = (float) (yaw + angle);
		if (wingSide == "right") yaw = (float) (yaw - angle);

		yaw = (float) (yaw * Math.PI) / 180;

		double x = Math.cos(yaw) * distance;
		double z = Math.sin(yaw) * distance;

		for (Player player : players) {
			player.spawnParticle(particle, loc, 0, x, height, z, speed, partileData);
		}

	}

}
