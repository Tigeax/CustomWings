package tigeax.customwings.gui;

import org.bukkit.Material;
import org.bukkit.Particle;

import tigeax.customwings.main.CustomWings;

/*
 * List of all the minecraft particles
 * Assinged are the material and name that are shows when the particle is referenced in a GUI
 * Version specifies in which version the particle was added (after 1.13)
 */

public enum ParticleItem {

	//formatter:off
	//1.13
	BARRIER("BARRIER", "Barrier", "1.13"),
	BLOCK_CRACK("CRACKED_STONE_BRICKS", "Block Crack", "1.13"),
	BLOCK_DUST("GLOWSTONE_DUST", "Block Dust", "1.13"),
	BUBBLE_COLUMN_UP("WATER_BUCKET", "Bubble Column Up", "1.13"),
	BUBBLE_POP("WATER_BUCKET", "Bubble Pop", "1.13"),
	CLOUD("FEATHER", "Cloud", "1.13"),
	CRIT("WOODEN_SWORD", "Crit", "1.13"),
	CRIT_MAGIC("DIAMOND_SWORD", "Crit Magic", "1.13"),
	CURRENT_DOWN("WATER_BUCKET", "Current Down", "1.13"),
	DAMAGE_INDICATOR("GOLDEN_SWORD", "Damage Indicator", "1.13"),
	DOLPHIN("WATER_BUCKET", "Dolphin", "1.13"),
	DRAGON_BREATH("DRAGON_HEAD", "Dragon Breath", "1.13"),
	DRIP_LAVA("LAVA_BUCKET", "Drip Lava", "1.13"),
	DRIP_WATER("WATER_BUCKET", "Drip Water", "1.13"),
	ENCHANTMENT_TABLE("ENCHANTING_TABLE", "Enchantment Table", "1.13"),
	END_ROD("END_ROD", "End Rod", "1.13"),
	EXPLOSION_HUGE("TNT", "Explosion Huge", "1.13"),
	EXPLOSION_LARGE("TNT", "Explosion Large", "1.13"),
	EXPLOSION_NORMAL("TNT", "Explosion Normal", "1.13"),
	FALLING_DUST("SAND", "Falling Dust", "1.13"),
	FIREWORKS_SPARK("FIREWORK_ROCKET", "Fireworks Spark", "1.13"),
	FLAME("BLAZE_POWDER", "Flame", "1.13"),
	HEART("ROSE_BUSH", "Heart", "1.13"),
	ITEM_CRACK("ITEM_FRAME", "Item Crack", "1.13"),
	LAVA("LAVA_BUCKET", "Lava", "1.13"),
	MOB_APPEARANCE("ELDER_GUARDIAN_SPAWN_EGG", "Mob Appearance", "1.13"),
	NAUTILUS("CONDUIT", "Nautilus", "1.13"),
	NOTE("NOTE_BLOCK", "Note", "1.13"),
	PORTAL("OBSIDIAN", "Portal", "1.13"),
	REDSTONE("REDSTONE", "Redstone", "1.13"),
	SLIME("SLIME_BALL", "Slime", "1.13"),
	SMOKE_LARGE("BLACK_CONCRETE_POWDER", "Smoke Large", "1.13"),
	SMOKE_NORMAL("BLACK_CONCRETE_POWDER", "Smoke Normal", "1.13"),
	SNOWBALL("SNOWBALL", "Snowball", "1.13"),
	SNOW_SHOVEL("SNOWBALL", "Snow Shovel", "1.13"),
	SPELL("LINGERING_POTION", "Spell", "1.13"),
	SPELL_INSTANT("SPLASH_POTION", "Spell Instant", "1.13"),
	SPELL_MOB("POTION", "Spell Mob", "1.13"),
	SPELL_MOB_AMBIENT("POTION", "Spell Mob Ambient", "1.13"),
	SPELL_WITCH("WITCH_SPAWN_EGG", "Spell Witch", "1.13"),
	SPIT("LLAMA_SPAWN_EGG", "Spit", "1.13"),
	SQUID_INK("SQUID_SPAWN_EGG", "Squid Ink", "1.13"),
	SUSPENDED("WATER_BUCKET", "Suspended", "1.13"),
	SUSPENDED_DEPTH("WATER_BUCKET", "Suspended Depth", "1.13"),
	SWEEP_ATTACK("STONE_SWORD", "Sweep Attack", "1.13"),
	TOTEM("TOTEM_OF_UNDYING", "Totem", "1.13"),
	TOWN_AURA("MYCELIUM", "Town Aura", "1.13"),
	VILLAGER_ANGRY("BLACK_GLAZED_TERRACOTTA", "Villager Angry", "1.13"),
	VILLAGER_HAPPY("EMERALD", "Villager Happy", "1.13"),
	WATER_BUBBLE("WATER_BUCKET", "Water Bubble", "1.13"),
	WATER_DROP("WATER_BUCKET", "Water Drop", "1.13"),
	WATER_SPLASH("WATER_BUCKET", "Water Splash", "1.13"),
	WATER_WAKE("WATER_BUCKET", "Water Wake", "1.13"),

	//1.14
	COMPOSTER("WATER_BUCKET", "Composter", "1.14"),
	CAMPFIRE_SIGNAL_SMOKE("CAMPFIRE", "Campfire Signal Smoke", "1.14"),
	CAMPFIRE_COSY_SMOKE("CAMPFIRE", "Campfire Cosy Smoke", "1.14"),
	SNEEZE("WATER_BUCKET", "Sneeze", "1.14"),

	//1.15
	DRIPPING_HONEY("HONEYCOMB", "Dripping Honey", "1.15"),
	FALLING_HONEY("HONEYCOMB", "Falling Honey", "1.15"),
	FALLING_NECTAR("HONEYCOMB", "Falling Nectar", "1.15"),
	LANDING_HONEY("HONEYCOMB", "Landing Honey", "1.15"),

	//1.16
	ASH("SOUL_SAND", "Ash", "1.16"),
	CRIMSON_SPORE("CRIMSON_FUNGUS", "Crimson Spore", "1.16"),
	DRIPPING_OBSIDIAN_TEAR("CRYING_OBSIDIAN", "Dripping Crying Obsidian", "1.16"),
	FALLING_OBSIDIAN_TEAR("CRYING_OBSIDIAN", "Falling Crying Obsidian", "1.16"),
	FLASH("CRYING_OBSIDIAN", "Flash", "1.16"),
	LANDING_OBSIDIAN_TEAR("CRYING_OBSIDIAN", "Landing Crying Obsidian", "1.16"),
	REVERSE_PORTAL("CRYING_OBSIDIAN", "Reverse Portal", "1.16"),
	SOUL("SOUL_SOIL", "Soul", "1.16"),
	SOUL_FIRE_FLAME("SOUL_SOIL", "SoulFire Flame", "1.16"),
	WARPED_SPORE("WARPED_FUNGUS", "Warped Spore", "1.16"),
	WHITE_ASH("SOUL_SOIL", "White Ash", "1.16");
	//formatter:on

	private String material;
	private String name;
	private String version;

	ParticleItem(final String material, final String name, final String version) {
		this.material = material;
		this.name = name;
		this.version = version;
	}

	public Material getMaterial() { return Material.valueOf(material); }

	public String getName() { return name; }

	public String getVersion() { return version; }

	public static Particle getParticleByName(String name) {

		for (ParticleItem particleItem : ParticleItem.values()) {
			if (name.equals(particleItem.getName())) {

				Particle particle;

				try {
					particle = Particle.valueOf(particleItem.toString());
				} catch (Exception e) {
					CustomWings.sendError(e);
					particle = Particle.BARRIER;
				}

				return particle;
			}
		}

		return Particle.BARRIER;
	}
}
