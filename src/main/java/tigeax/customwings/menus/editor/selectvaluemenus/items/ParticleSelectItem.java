package tigeax.customwings.menus.editor.selectvaluemenus.items;

import org.bukkit.Material;
import org.bukkit.Particle;

import tigeax.customwings.menus.editor.selectvaluemenus.ParticleSelectMenuPage;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuItem;

public class ParticleSelectItem extends MenuItem {

    Particle particle;

    public ParticleSelectItem(Particle particle) {
        this.particle = particle;

        setDisplayName("&f" + this.particle.toString());
        setMaterial(getMaterial(this.particle));
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        ItemMenu itemMenu = event.getItemMenu();

        if (!(itemMenu instanceof ParticleSelectMenuPage)) {
            event.setWillClose(true);
            return;
        }

        ParticleSelectMenuPage menu = (ParticleSelectMenuPage) itemMenu;

        menu.getSetting().setValue(particle.toString());
    }

    public static Material getMaterial(Particle particle) {

        switch (particle) {
            case POOF: return Material.WHITE_WOOL;
            case EXPLOSION:
            case EXPLOSION_EMITTER: return Material.TNT;
            case FIREWORK: return Material.FIREWORK_ROCKET;
            case BUBBLE:
            case BUBBLE_POP:
            case BUBBLE_COLUMN_UP:
            case CURRENT_DOWN:
            case SPLASH:
            case UNDERWATER:
            case RAIN:
            case FALLING_WATER:
            case DRIPPING_WATER: return Material.WATER_BUCKET;
            case FISHING: return Material.FISHING_ROD;
            case CRIT:
            case ENCHANTED_HIT: return Material.DIAMOND_SWORD;
            case SMOKE:
            case LARGE_SMOKE:
            case WHITE_SMOKE:
            case CAMPFIRE_COSY_SMOKE:
            case CAMPFIRE_SIGNAL_SMOKE: return Material.CAMPFIRE;
            case EFFECT:
            case INSTANT_EFFECT:
            case ENTITY_EFFECT: return Material.POTION;
            case WITCH: return Material.WITCH_SPAWN_EGG;
            case DRIPPING_LAVA:
            case FALLING_LAVA:
            case LANDING_LAVA:
            case LAVA: return Material.LAVA_BUCKET;
            case ANGRY_VILLAGER: return Material.BLACK_GLAZED_TERRACOTTA;
            case HAPPY_VILLAGER: return Material.EMERALD;
            case MYCELIUM: return Material.MYCELIUM;
            case NOTE: return Material.NOTE_BLOCK;
            case PORTAL:
            case REVERSE_PORTAL: return Material.OBSIDIAN;
            case ENCHANT: return Material.ENCHANTING_TABLE;
            case FLAME:
            case SMALL_FLAME: return Material.BLAZE_POWDER;
            case CLOUD:
            case GUST:
            case SMALL_GUST:
            case GUST_EMITTER_LARGE:
            case GUST_EMITTER_SMALL: return Material.FEATHER;
            case DUST:
            case DUST_COLOR_TRANSITION: return Material.REDSTONE;
            case ITEM_SNOWBALL:
            case SNOWFLAKE: return Material.SNOWBALL;
            case ITEM_SLIME: return Material.SLIME_BALL;
            case HEART: return Material.ROSE_BUSH;
            case ITEM: return Material.ITEM_FRAME;
            case BLOCK:
            case FALLING_DUST:
            case DUST_PILLAR:
            case BLOCK_CRUMBLE:
            case BLOCK_MARKER: return Material.SAND;
            case ELDER_GUARDIAN: return Material.ELDER_GUARDIAN_SPAWN_EGG;
            case DRAGON_BREATH: return Material.DRAGON_HEAD;
            case END_ROD: return Material.END_ROD;
            case DAMAGE_INDICATOR: return Material.GOLDEN_SWORD;
            case SWEEP_ATTACK: return Material.STONE_SWORD;
            case TOTEM_OF_UNDYING: return Material.TOTEM_OF_UNDYING;
            case SPIT: return Material.LLAMA_SPAWN_EGG;
            case SQUID_INK: return Material.SQUID_SPAWN_EGG;
            case NAUTILUS: return Material.CONDUIT;
            case DOLPHIN: return Material.DOLPHIN_SPAWN_EGG;
            case SNEEZE: return Material.GREEN_DYE;
            case COMPOSTER: return Material.COMPOSTER;
            case FLASH: return Material.CRYING_OBSIDIAN;
            case DRIPPING_HONEY:
            case FALLING_HONEY:
            case LANDING_HONEY:
            case FALLING_NECTAR: return Material.HONEYCOMB;
            case SOUL_FIRE_FLAME:
            case SOUL:
            case WHITE_ASH: return Material.SOUL_SOIL;
            case ASH: return Material.SOUL_SAND;
            case CRIMSON_SPORE: return Material.CRIMSON_FUNGUS;
            case WARPED_SPORE: return Material.WARPED_FUNGUS;
            case DRIPPING_OBSIDIAN_TEAR:
            case FALLING_OBSIDIAN_TEAR:
            case LANDING_OBSIDIAN_TEAR: return Material.CRYING_OBSIDIAN;
            case FALLING_SPORE_BLOSSOM:
            case SPORE_BLOSSOM_AIR: return Material.SPORE_BLOSSOM;
            case GLOW_SQUID_INK: return Material.GLOW_INK_SAC;
            case GLOW: return Material.GLOWSTONE_DUST;
            case WAX_ON: return Material.COPPER_INGOT;
            case WAX_OFF: return Material.IRON_INGOT;
            case ELECTRIC_SPARK: return Material.LIGHT_GRAY_DYE;
            case SCRAPE: return Material.CYAN_DYE;
            case SONIC_BOOM:
            case SHRIEK: return Material.SCULK_SHRIEKER;
            case SCULK_SOUL:
            case SCULK_CHARGE:
            case SCULK_CHARGE_POP: return Material.SCULK;
            case CHERRY_LEAVES: return Material.CHERRY_LEAVES;
            case PALE_OAK_LEAVES: return Material.OAK_LEAVES;
            case TINTED_LEAVES: return Material.TINTED_GLASS;
            case EGG_CRACK: return Material.EGG;
            case TRIAL_SPAWNER_DETECTION:
            case TRIAL_SPAWNER_DETECTION_OMINOUS: return Material.SPAWNER;
            case VAULT_CONNECTION: return Material.CHAIN;
            case INFESTED: return Material.INFESTED_STONE;
            case ITEM_COBWEB: return Material.COBWEB;
            case FIREFLY: return Material.LIGHT;
            case TRAIL: return Material.LEAD;
            case OMINOUS_SPAWNING:
            case RAID_OMEN:
            case TRIAL_OMEN: return Material.BELL;

            default: return Material.GRASS_BLOCK;
        }
    }

}
