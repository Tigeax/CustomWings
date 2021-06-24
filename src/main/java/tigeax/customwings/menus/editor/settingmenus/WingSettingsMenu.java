package tigeax.customwings.menus.editor.settingmenus;

import org.bukkit.Material;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.configuration.settings.WingSetting;
import tigeax.customwings.menus.editor.selectvaluemenus.items.SettingItem;
import tigeax.customwings.menus.editor.settingmenus.items.WingParticleListMenuItem;
import tigeax.customwings.menus.items.GoBackItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

public class WingSettingsMenu extends ItemMenu {

    public WingSettingsMenu(Wing wing) {
        super(wing.getConfig().getGuiItemName(), Rows.SIX);

        // General
        setItem(1, new SettingItem(new Setting(WingSetting.SHOW_WHEN_MOVING, wing),                    "&3Show when moving",           Material.DIAMOND_LEGGINGS));
        setItem(2, new SettingItem(new Setting(WingSetting.WHITELISTED_WORLDS, wing),                  "&3Whitelisted worlds",         Material.GRASS_BLOCK));
        setItem(4, new SettingItem(new Setting(WingSetting.WING_ONLY_ONLY_SIDE, wing),                 "&3Only one side",              Material.WHITE_TERRACOTTA));

        // Economy
        setItem(6, new SettingItem(new Setting(WingSetting.ECONOMY_PRICE_TYPE, wing),                  "&3Economy type",               Material.YELLOW_DYE));
        setItem(7, new SettingItem(new Setting(WingSetting.ECONOMY_PRICE, wing),                       "&3Economy price",              Material.YELLOW_DYE));

        // Item
        setItem(9,  new SettingItem(new Setting(WingSetting.MENU_ITEM_MATERIAL, wing),                 "&3Menu item material"));
        setItem(10, new SettingItem(new Setting(WingSetting.MENU_ITEM_HIDE_IN_MENU, wing),             "&3Menu item hide in menu",     Material.CHEST));
        setItem(11, new SettingItem(new Setting(WingSetting.MENU_ITEM_NAME, wing),                     "&3Menu item name",             Material.CHEST));
        setItem(12, new SettingItem(new Setting(WingSetting.MENU_ITEM_SLOT, wing),                     "&3Menu item slot",             Material.CHEST));
        setItem(13, new SettingItem(new Setting(WingSetting.MENU_ITEM_PAGE, wing),                     "&3Menu item page",             Material.CHEST));

        // Item lore
        setItem(14, new SettingItem(new Setting(WingSetting.MENU_ITEM_LORE_WHEN_EQUIPPED, wing),       "&3Lore when equipped",         Material.ENDER_CHEST));
        setItem(15, new SettingItem(new Setting(WingSetting.MENU_ITEM_LORE_WHEN_UNEQUIPPED, wing),     "&3Lore when unequipped",       Material.ENDER_CHEST));
        setItem(16, new SettingItem(new Setting(WingSetting.MENU_ITEM_LORE_WHEN_NO_PERMISSION, wing),  "&3Lore when no permission",    Material.ENDER_CHEST));
        setItem(17, new SettingItem(new Setting(WingSetting.MENU_ITEM_LORE_WHEN_CAN_BUY, wing),        "&3Lore when can buy",          Material.ENDER_CHEST));

        // Distances
        setItem(20, new SettingItem(new Setting(WingSetting.WING_START_VERTICAL, wing),                "&3Start vertical",             Material.END_ROD));
        setItem(21, new SettingItem(new Setting(WingSetting.WING_START_HORIZONTAL_OFFSET, wing),       "&3Start horizontal offset",    Material.END_ROD));
        setItem(22, new SettingItem(new Setting(WingSetting.WING_START_DISTANCE_TO_PLAYER, wing),      "&3Start distance to player",    Material.END_ROD));
        setItem(23, new SettingItem(new Setting(WingSetting.WING_DISTANCE_BETWEEN_PARTICLES, wing),    "&3Distance between particles", Material.END_ROD));
        setItem(24, new SettingItem(new Setting(WingSetting.WING_TIMER, wing),                         "&3Wing timer",                 Material.END_ROD));

        // Wing animation
        setItem(29, new SettingItem(new Setting(WingSetting.WING_FLAP_ANIMATION, wing),                "&3Wing animation",             Material.DIAMOND));
        setItem(30, new SettingItem(new Setting(WingSetting.WING_WING_FLAP_SPEED, wing),               "&3Wing flap speed",            Material.DIAMOND));
        setItem(32, new SettingItem(new Setting(WingSetting.WING_START_OFFSET, wing),                  "&3Start offset",               Material.GREEN_TERRACOTTA));
        setItem(33, new SettingItem(new Setting(WingSetting.WING_STOP_OFFSET, wing),                   "&3Stop offset",                Material.RED_TERRACOTTA));

        // Wing particles
        setItem(40, new WingParticleListMenuItem(new WingParticleListMenu(this, wing)));

        // Go back
        setItem(53, new GoBackItem());
    }
    
}
