package tigeax.customwings.menus.editor.settingmenus;

import org.bukkit.Material;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.configuration.settings.WingSetting;
import tigeax.customwings.menus.editor.items.GoBackItem;
import tigeax.customwings.menus.editor.selectvaluemenus.items.SettingItem;
import tigeax.customwings.menus.editor.settingmenus.items.WingParticleListMenuItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

public class WingSettingsMenu extends ItemMenu {

    public WingSettingsMenu(Wing wing) {
        super(wing.getConfig().getGuiItemName(), Rows.SIX);

        setItem(12, new SettingItem(new Setting(WingSetting.SHOW_WHEN_MOVING, wing),                    "&3Show when moving",           Material.DIAMOND_LEGGINGS));
        setItem(14, new SettingItem(new Setting(WingSetting.WHITELISTED_WORLDS, wing),                  "&3Whitelisted worlds",         Material.GRASS_BLOCK));

        setItem(19, new SettingItem(new Setting(WingSetting.MENU_ITEM_HIDE_IN_MENU, wing),              "&3Menu item hide in menu",     Material.CHEST));
        setItem(20, new SettingItem(new Setting(WingSetting.MENU_ITEM_NAME, wing),                      "&3Menu item name",             Material.CHEST));
        setItem(21, new SettingItem(new Setting(WingSetting.MENU_ITEM_MATERIAL, wing),                  "&3Menu item material"));
        setItem(22, new SettingItem(new Setting(WingSetting.MENU_ITEM_SLOT, wing),                      "&3Menu item slot",             Material.CHEST));
        setItem(23, new SettingItem(new Setting(WingSetting.MENU_ITEM_LORE_WHEN_EQUIPPED, wing),        "&3Lore when equipped",         Material.ENDER_CHEST));
        setItem(24, new SettingItem(new Setting(WingSetting.MENU_ITEM_LORE_WHEN_UNEQUIPPED, wing),      "&3Lore when unequipped",       Material.ENDER_CHEST));
        setItem(25, new SettingItem(new Setting(WingSetting.MENU_ITEM_LORE_WHEN_NO_PERMISSION, wing),   "&3Lore when no permission",    Material.ENDER_CHEST));
        setItem(26, new SettingItem(new Setting(WingSetting.MENU_ITEM_LORE_WHEN_CAN_BUY, wing),         "&3Lore when can buy",          Material.ENDER_CHEST));

        setItem(27, new SettingItem(new Setting(WingSetting.WING_START_VERTICAL, wing),                 "&3Start vertical",             Material.END_ROD));
        setItem(28, new SettingItem(new Setting(WingSetting.WING_START_HORIZONTAL, wing),               "&3Start horizontal",           Material.END_ROD));
        setItem(29, new SettingItem(new Setting(WingSetting.WING_DISTANCE_BETWEEN_PARTICLES, wing),     "&3Distance between particles", Material.END_ROD));
        setItem(30, new SettingItem(new Setting(WingSetting.WING_TIMER, wing),                          "&3Wing timer",                 Material.END_ROD));

        setItem(32, new SettingItem(new Setting(WingSetting.WING_FLAP_ANIMATION, wing),                 "&3Wing animation",             Material.DIAMOND));
        setItem(33, new SettingItem(new Setting(WingSetting.WING_WING_FLAP_SPEED, wing),                "&3Wing flap speed",            Material.DIAMOND));

        setItem(34, new SettingItem(new Setting(WingSetting.WING_START_OFFSET, wing),                   "&3Start offset",               Material.GREEN_TERRACOTTA));
        setItem(35, new SettingItem(new Setting(WingSetting.WING_STOP_OFFSET, wing),                    "&3Stop offset",                Material.RED_TERRACOTTA));

        setItem(40, new WingParticleListMenuItem(new WingParticleListMenu(this, wing)));

        setItem(53, new GoBackItem());
    }
    
}
