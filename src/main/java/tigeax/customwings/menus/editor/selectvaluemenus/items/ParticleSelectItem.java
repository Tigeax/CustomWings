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

        return switch (particle) {
            case POOF, CLOUD, WHITE_SMOKE -> Material.FEATHER;
            case ITEM_COBWEB -> Material.COBWEB;
            case EXPLOSION, EXPLOSION_EMITTER, FLASH -> Material.TNT;
            case FIREWORK -> Material.FIREWORK_ROCKET;
            case BUBBLE, BUBBLE_POP, BUBBLE_COLUMN_UP, CURRENT_DOWN -> Material.TUBE_CORAL;
            case SPLASH, UNDERWATER, FALLING_WATER, DRIPPING_WATER -> Material.WATER_BUCKET;
            case FISHING -> Material.FISHING_ROD;
            case CRIT -> Material.IRON_SWORD;
            case ENCHANTED_HIT -> Material.DIAMOND_SWORD;
            case SMOKE, LARGE_SMOKE, CAMPFIRE_COSY_SMOKE, CAMPFIRE_SIGNAL_SMOKE, WHITE_ASH, ASH -> Material.CAMPFIRE;
            case EFFECT, INSTANT_EFFECT, ENTITY_EFFECT, WITCH -> Material.POTION;
            case ANGRY_VILLAGER -> Material.BLACK_GLAZED_TERRACOTTA;
            case HAPPY_VILLAGER -> Material.EMERALD;
            case MYCELIUM -> Material.MYCELIUM;
            case NOTE -> Material.NOTE_BLOCK;
            case PORTAL, REVERSE_PORTAL -> Material.OBSIDIAN;
            case ENCHANT -> Material.ENCHANTING_TABLE;
            case FLAME, SMALL_FLAME -> Material.BLAZE_POWDER;
            case LAVA, FALLING_LAVA, LANDING_LAVA, DRIPPING_LAVA -> Material.LAVA_BUCKET;
            case DUST, DUST_COLOR_TRANSITION, DUST_PLUME, DUST_PILLAR -> Material.REDSTONE;
            case ITEM_SNOWBALL -> Material.SNOWBALL;
            case ITEM_SLIME -> Material.SLIME_BALL;
            case HEART -> Material.RED_DYE;
            case ITEM -> Material.ITEM_FRAME;
            case BLOCK, FALLING_DUST, BLOCK_CRUMBLE, BLOCK_MARKER -> Material.STONE;
            case RAIN -> Material.BLUE_STAINED_GLASS;
            case ELDER_GUARDIAN -> Material.PRISMARINE;
            case DRAGON_BREATH -> Material.DRAGON_BREATH;
            case END_ROD -> Material.END_ROD;
            case DAMAGE_INDICATOR, SWEEP_ATTACK -> Material.WOODEN_SWORD;
            case TOTEM_OF_UNDYING -> Material.TOTEM_OF_UNDYING;
            case SPIT -> Material.LLAMA_SPAWN_EGG;
            case SQUID_INK -> Material.INK_SAC;
            case GLOW_SQUID_INK -> Material.GLOW_INK_SAC;
            case NAUTILUS -> Material.NAUTILUS_SHELL;
            case DOLPHIN -> Material.DOLPHIN_SPAWN_EGG;
            case SNEEZE -> Material.GREEN_DYE;
            case COMPOSTER -> Material.COMPOSTER;
            case DRIPPING_HONEY, FALLING_HONEY, LANDING_HONEY, FALLING_NECTAR -> Material.HONEY_BOTTLE;
            case SOUL_FIRE_FLAME, SOUL -> Material.SOUL_LANTERN;
            case CRIMSON_SPORE -> Material.CRIMSON_FUNGUS;
            case WARPED_SPORE -> Material.WARPED_FUNGUS;
            case DRIPPING_OBSIDIAN_TEAR, FALLING_OBSIDIAN_TEAR, LANDING_OBSIDIAN_TEAR -> Material.CRYING_OBSIDIAN;
            case FALLING_SPORE_BLOSSOM, SPORE_BLOSSOM_AIR -> Material.SPORE_BLOSSOM;
            case SNOWFLAKE -> Material.SNOWBALL;
            case DRIPPING_DRIPSTONE_LAVA, FALLING_DRIPSTONE_LAVA -> Material.DRIPSTONE_BLOCK;
            case DRIPPING_DRIPSTONE_WATER, FALLING_DRIPSTONE_WATER -> Material.POINTED_DRIPSTONE;
            case GLOW -> Material.GLOW_INK_SAC;
            case WAX_ON, WAX_OFF -> Material.HONEYCOMB;
            case ELECTRIC_SPARK -> Material.LIGHTNING_ROD;
            case SCRAPE -> Material.IRON_AXE;
            case SONIC_BOOM -> Material.SCULK_SHRIEKER;
            case SCULK_SOUL, SCULK_CHARGE, SCULK_CHARGE_POP -> Material.SCULK;
            case SHRIEK -> Material.SCULK_SHRIEKER;
            case CHERRY_LEAVES -> Material.CHERRY_LEAVES;
            case PALE_OAK_LEAVES -> Material.OAK_LEAVES;
            case TINTED_LEAVES -> Material.TINTED_GLASS;
            case EGG_CRACK -> Material.EGG;
            case GUST, SMALL_GUST, GUST_EMITTER_LARGE, GUST_EMITTER_SMALL -> Material.TNT;
            case TRIAL_SPAWNER_DETECTION, TRIAL_SPAWNER_DETECTION_OMINOUS -> Material.SPAWNER;
            case VAULT_CONNECTION -> Material.CHAIN;
            case INFESTED -> Material.SILVERFISH_SPAWN_EGG;
            case FIREFLY -> Material.TORCHFLOWER;
            case TRAIL -> Material.LEAD;
            case OMINOUS_SPAWNING, RAID_OMEN, TRIAL_OMEN -> Material.BELL;

            default -> Material.BARRIER;
        };
    }

}
