package tigeax.customwings.wing;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.configuration.WingConfig;

/*
 * Class containing all information about a wingParticle
 * WingParticle is used in the Wing class
 */

public class WingParticle {

	private WingConfig wingConfig;
	private String id;

	private Particle particle;

	private double distance, height, speed;
	private int angle;

	private Object particleData;
	private DustOptions dustOptions;
	private Material material;

	public WingParticle(WingConfig wingConfig, String id, Particle particle, double distance, double height, int angle, double speed, DustOptions dustOptions, Material material) {
		this.wingConfig = wingConfig;
		this.id = id;
		this.particle = particle;
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
				e.printStackTrace();
				particleData = Material.BARRIER.createBlockData();
			}
		} else
			if (particle == Particle.ITEM_CRACK) particleData = new ItemStack(material);
	}

	public void reload() {
		//TODO
	}

	public WingConfig getWingConfig() {
		return wingConfig;
	}

	public String getID() { return this.id; }

	public Particle getParticle() { return particle; }

	public double getDistance() { return distance; }

	public double getHeight() { return height; }

	public int getAngle() { return angle; }

	public double getSpeed() { return speed; }

	public DustOptions getDustOptions() { return dustOptions; }

	public Material getMaterialData() { return material; }

	// Spawn the particle at a location for all players specified
	// If the particle has velocity (defined by speed) we need to calculate the x and z values so the flies in the correct direction
	// This direction is based on the yaw of the wing and the if the particle is part of the left or right side of the wing
	// WingSide is a String of either 'left' or 'right'
	public void spawnParticle(Location loc, ArrayList<Player> spawnForPlayers, Wing.WingSide wingSide) {

		double direction = loc.getYaw();

		if (wingSide == Wing.WingSide.LEFT) direction = (direction + angle);
		if (wingSide == Wing.WingSide.RIGHT) direction = (direction - angle);

		direction = Math.toRadians(direction);
		double x = Math.cos(direction);
		double z = Math.sin(direction);

		for (Player player : spawnForPlayers) {
			player.spawnParticle(particle, loc, 0, x, height, z, speed, particleData);
		}
	}

}
