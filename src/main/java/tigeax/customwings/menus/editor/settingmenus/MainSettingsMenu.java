package tigeax.customwings.menus.editor.settingmenus;

import org.bukkit.Material;

import tigeax.customwings.configuration.settings.ConfigSetting;
import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.editor.items.GoBackItem;
import tigeax.customwings.menus.editor.selectvaluemenus.items.SettingItem;
import tigeax.customwings.util.menu.ItemMenu;

public class MainSettingsMenu extends ItemMenu {

    public MainSettingsMenu() {
        super("&cMain Settings", Rows.SIX);

        setItem(4,  new SettingItem(new Setting(ConfigSetting.WING_VIEW_DISTANCE),                  "&3View distance",                          Material.ENDER_PEARL));

        setItem(12, new SettingItem(new Setting(ConfigSetting.WING_SELECT_MENU_NAME),               "&3Wing select menu name",                  Material.CHEST));
        setItem(14, new SettingItem(new Setting(ConfigSetting.WING_SELECT_MENU_SIZE),               "&3Wing select menu size",                  Material.CHEST));

        setItem(21, new SettingItem(new Setting(ConfigSetting.REMOVE_WING_ITEM_NAME),               "&3Remove wing item name",                  Material.BARRIER));
        setItem(22, new SettingItem(new Setting(ConfigSetting.REMOVE_WING_ITEM_MATERIAL),           "&3Remove wing item material",              Material.BARRIER));
        setItem(23, new SettingItem(new Setting(ConfigSetting.REMOVE_WING_ITEM_SLOT),               "&3Remove wing item slot",                  Material.BARRIER));

        setItem(29, new SettingItem(new Setting(ConfigSetting.HIDE_WINGS_TOGGLE_ON_ITEM_NAME),      "&3Hide wings toggle ON itemname",          Material.ENDER_EYE));
        setItem(30, new SettingItem(new Setting(ConfigSetting.HIDE_WINGS_TOGGLE_ON_ITEM_MATERIAL),  "&3Hide wings woggle ON item material",     Material.ENDER_EYE));
        setItem(31, new SettingItem(new Setting(ConfigSetting.HIDE_WINGS_TOGGLE_OFF_ITEM_NAME),     "&3Hide wings toggle OFF itemname",         Material.ENDER_EYE));
        setItem(32, new SettingItem(new Setting(ConfigSetting.HIDE_WINGS_TOGGLE_OFF_ITEM_MATERIAL), "&3Hide wings woggle OFF item material",    Material.ENDER_EYE));
        setItem(33, new SettingItem(new Setting(ConfigSetting.HIDE_WINGS_TOGGLE_ON_ITEM_NAME),      "&3Hide wings woggle item slot",            Material.ENDER_EYE));

        setItem(53, new GoBackItem());

    }
    
}
