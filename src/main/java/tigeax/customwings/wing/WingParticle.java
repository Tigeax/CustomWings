package tigeax.customwings.wing;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.configuration.WingConfig;

/*
 * Class containing all information about a wingParticle
 * WingParticle is used in the Wing class
 */

public class WingParticle {

	private WingConfig wingConfig;
	private ConfigurationSection particleConfig;
	private String id;

	private Particle particle;

	private double distance, height, speed;
	private int angle;

	private Object particleData;
	private DustOptions dustOptions;
	private Material material;

	public WingParticle(WingConfig wingConfig, String id) {
		this.wingConfig = wingConfig;
		this.id = id;

		reload();
	}

	// TODO refractor
	public void reload() {

		particleConfig = wingConfig.getConfigurationSection("wing.particles." + id);

		try {
			this.particle = Particle.valueOf(particleConfig.getString("particle"));
		} catch (Exception e) {
			e.printStackTrace();
			this.particle = Particle.BARRIER;
		}

		this.distance = particleConfig.getDouble("distance");
		this.height = particleConfig.getDouble("height");
		this.angle = particleConfig.getInt("angle");
		this.speed = particleConfig.getDouble("speed");

		this.material = Material.valueOf(particleConfig.getString("blockType"));
		this.dustOptions = new Particle.DustOptions(Color.fromRGB(particleConfig.getInt("color")), (float) 1);

		this.particleData = null;

		// Refractor
		if (particle == Particle.REDSTONE) {
			this.particleData = dustOptions;
		}

		if (particle == Particle.BLOCK_CRACK
				|| particle == Particle.BLOCK_DUST
				|| particle == Particle.FALLING_DUST) {
			try {
				this.particleData = material.createBlockData();
			} catch (Exception e) {
				e.printStackTrace();
				this.particleData = Material.BARRIER.createBlockData();
			}
		} else
			if (particle == Particle.ITEM_CRACK) {
				this.particleData = new ItemStack(material);
			}
		
	}

	public WingConfig getWingConfig() {
		return wingConfig;
	}

	public ConfigurationSection getParticleConfig() {
		return this.particleConfig;
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
			// TODO
			// Note changes based on xyz values? And potions (speed value?)
			player.spawnParticle(particle, loc, 0, x, height, z, speed, particleData);
		}
	}

}
