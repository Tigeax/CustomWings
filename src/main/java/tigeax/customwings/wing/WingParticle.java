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
import tigeax.customwings.configuration.settings.WingParticleSetting;

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
	private Color color;
	private DustOptions dustOptions;
	private Material material;
	private int noteColor;

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
			this.particle = Particle.DUST;
		}

		this.distance = particleConfig.getDouble(WingParticleSetting.DISTANCE.path, 0);
		this.height = particleConfig.getDouble(WingParticleSetting.HEIGHT.path, 0);
		this.angle = particleConfig.getInt(WingParticleSetting.ANGLE.path, 0);
		this.speed = particleConfig.getDouble(WingParticleSetting.SPEED.path, 0);

		try {
			this.material = Material.valueOf(particleConfig.getString(WingParticleSetting.BLOCK_TYPE.path));
		} catch (Exception e) {
			this.material = Material.DIRT;
		}

		this.color = Color.fromRGB(particleConfig.getInt(WingParticleSetting.COLOR.path, 0xFFFFFF));

		this.dustOptions = new Particle.DustOptions(color, (float) 1);

		this.particleData = null;

		this.noteColor = particleConfig.getInt(WingParticleSetting.NOTE_COLOR.path, 1);

		// Refractor
		if (particle == Particle.DUST) {
			this.particleData = dustOptions;
		}
		
		if (particle == Particle.BLOCK || particle == Particle.FALLING_DUST) {
			try {
				this.particleData = material.createBlockData();
			} catch (Exception e) {
				e.printStackTrace();
				this.particleData = Material.BARRIER.createBlockData();
			}
		} else if (particle == Particle.ITEM) {
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

	public Color getColor() { return color; }

	public int getNoteColor() { return noteColor; }

	public Material getMaterialData() { return material; }

	// Spawn the particle at a location for all players specified
	// If the particle has velocity (defined by speed) we need to calculate the x and z values so the flies in the correct direction
	// This direction is based on the yaw of the wing and the if the particle is part of the left or right side of the wing
	// WingSide is a String of either 'left' or 'right'
	// Extra info on particles https://www.spigotmc.org/threads/comprehensive-particle-spawning-guide-1-13.343001/
	public void spawnParticle(Location loc, ArrayList<Player> spawnForPlayers, Wing.WingSide wingSide) {

		double direction = loc.getYaw();

		if (wingSide == Wing.WingSide.LEFT) direction = (direction + angle);
		if (wingSide == Wing.WingSide.RIGHT) direction = (direction - angle);

		double x, y, z, extra;

		if (particle == Particle.NOTE) {
			x = noteColor / 24D; // 6 is the value of the red note
			y = 0;
			z = 0;
			extra = 1;

		} else if (particle == Particle.ENTITY_EFFECT) {

			x = color.getRed() / 255D;
			y = color.getGreen() / 255D;
			z = color.getBlue() / 255D;
			extra = 1;

		} else {
			direction = Math.toRadians(direction);
			x = distance * Math.cos(direction);
			y = height;
			z = distance * Math.sin(direction);
			extra = speed;
		}

		for (Player player : spawnForPlayers) {
			player.spawnParticle(particle, loc, 0, x, y, z, extra, particleData);
		}
	}

}
