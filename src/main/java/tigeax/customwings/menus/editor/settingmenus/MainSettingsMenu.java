package tigeax.customwings.menus.editor.settingmenus;

import org.bukkit.Material;

import tigeax.customwings.configuration.settings.ConfigSetting;
import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.editor.selectvaluemenus.items.SettingItem;
import tigeax.customwings.menus.items.GoBackItem;
import tigeax.customwings.util.menu.ItemMenu;

public class MainSettingsMenu extends ItemMenu {

    public MainSettingsMenu() {
        super("&cMain Settings", Rows.SIX);

        // General
        setItem(1,  new SettingItem(new Setting(ConfigSetting.WING_VIEW_DISTANCE),                  "&3View distance",                      Material.ENDER_PEARL));
        setItem(2,  new SettingItem(new Setting(ConfigSetting.WING_MAX_PITCH),                      "&3Wing max pitch",                     Material.ENDER_PEARL));
        setItem(3,  new SettingItem(new Setting(ConfigSetting.INVISIBILITY_POTION_HIDES_WING),      "&3Invisibilty potion hide wing",       Material.ENDER_PEARL));

        // Wing select menu
        setItem(5, new SettingItem(new Setting(ConfigSetting.WING_SELECT_MENU_NAME),                "&3Wing select menu name",              Material.CHEST));
        setItem(6, new SettingItem(new Setting(ConfigSetting.WING_SELECT_MENU_SIZE),                "&3Wing select menu size",              Material.CHEST));
        setItem(7, new SettingItem(new Setting(ConfigSetting.WING_SELECT_MENU_PAGES),               "&3Wing select menu pages",             Material.CHEST));

        // Filter item
        setItem(9, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_ENABLE),                  "&3Filter item enable",                 Material.BUCKET));
        setItem(10, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_SLOT),                    "&3Filter item slot",                   Material.BUCKET));

        // Hide other wings toggle item
        setItem(12, new SettingItem(new Setting(ConfigSetting.HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_NAME),      "&3Hide wings toggle ON item name",     new Setting(ConfigSetting.HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_MATERIAL)));
        setItem(13, new SettingItem(new Setting(ConfigSetting.HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_MATERIAL),  "&3Hide wings woggle ON item material"));
        setItem(14, new SettingItem(new Setting(ConfigSetting.HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_NAME),     "&3Hide wings toggle OFF item name",    new Setting(ConfigSetting.HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_MATERIAL)));
        setItem(15, new SettingItem(new Setting(ConfigSetting.HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_MATERIAL), "&3Hide wings toggle OFF item material"));
        setItem(16, new SettingItem(new Setting(ConfigSetting.HIDE_OTHER_WINGS_TOGGLE_SLOT),              "&3Hide wings toggle item slot",        Material.ENDER_EYE));

        // Navigation previous item
        setItem(19, new SettingItem(new Setting(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_NAME),       "&3Navigation item previous name",      new Setting(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_MATERIAL)));
        setItem(20, new SettingItem(new Setting(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_MATERIAL),   "&3Navigation item previous material"));
        setItem(21, new SettingItem(new Setting(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_SLOT),       "&3Navigation item previous slot",      new Setting(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_MATERIAL)));

        // Navigation next item
        setItem(23, new SettingItem(new Setting(ConfigSetting.NAGIVATION_ITEM_NEXT_NAME),           "&3Navigation item next name",          new Setting(ConfigSetting.NAGIVATION_ITEM_NEXT_MATERIAL)));
        setItem(24, new SettingItem(new Setting(ConfigSetting.NAGIVATION_ITEM_NEXT_MATERIAL),       "&3Navigation item next material"));
        setItem(25, new SettingItem(new Setting(ConfigSetting.NAGIVATION_ITEM_NEXT_SLOT),           "&3Navigation item next slot",          new Setting(ConfigSetting.NAGIVATION_ITEM_NEXT_MATERIAL)));

        // Remove wing item
        setItem(30, new SettingItem(new Setting(ConfigSetting.REMOVE_WING_ITEM_NAME),               "&3Remove wing item name",              new Setting(ConfigSetting.REMOVE_WING_ITEM_MATERIAL)));
        setItem(31, new SettingItem(new Setting(ConfigSetting.REMOVE_WING_ITEM_MATERIAL),           "&3Remove wing item material"));
        setItem(32, new SettingItem(new Setting(ConfigSetting.REMOVE_WING_ITEM_SLOT),               "&3Remove wing item slot",              new Setting(ConfigSetting.REMOVE_WING_ITEM_MATERIAL)));

        // No filter
        setItem(36, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_NO_FILTER_NAME),          "&3Navigation item no filter name",     new Setting(ConfigSetting.FILTER_ITEM_NO_FILTER_MATERIAL)));
        setItem(37, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_NO_FILTER_MATERIAL),      "&3Navigation item no filter material"));
        setItem(38, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_NO_FILTER_LORE),          "&3Navigation item no filter lore",     new Setting(ConfigSetting.FILTER_ITEM_NO_FILTER_MATERIAL)));

        // Owned wings
        setItem(39, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_OWNED_WINGS_NAME),        "&3Navigation item owned wings name",   new Setting(ConfigSetting.FILTER_ITEM_OWNED_WINGS_MATERIAL)));
        setItem(40, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_OWNED_WINGS_MATERIAL),    "&3Navigation item owned wings material"));
        setItem(41, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_OWNED_WINGS_LORE),        "&3Navigation item owned wings lore",   new Setting(ConfigSetting.FILTER_ITEM_OWNED_WINGS_MATERIAL)));

        // Unowned wings
        setItem(42, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_UNOWNED_WINGS_NAME),      "&3Navigation item unowned wings name", new Setting(ConfigSetting.FILTER_ITEM_UNOWNED_WINGS_MATERIAL)));
        setItem(43, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_UNOWNED_WINGS_MATERIAL),  "&3Navigation item unowned wings material"));
        setItem(44, new SettingItem(new Setting(ConfigSetting.FILTER_ITEM_UNOWNED_WINGS_LORE),      "&3Navigation item unowned wings lore", new Setting(ConfigSetting.FILTER_ITEM_UNOWNED_WINGS_MATERIAL)));

        // Show wing toggle item
        setItem(47, new SettingItem(new Setting(ConfigSetting.SHOW_WING_TOGGLE_ON_ITEM_NAME),      "&3Hide wing toggle ON item name",     new Setting(ConfigSetting.SHOW_WING_TOGGLE_ON_ITEM_MATERIAL)));
        setItem(48, new SettingItem(new Setting(ConfigSetting.SHOW_WING_TOGGLE_ON_ITEM_MATERIAL),  "&3Hide wing woggle ON item material"));
        setItem(49, new SettingItem(new Setting(ConfigSetting.SHOW_WING_TOGGLE_OFF_ITEM_NAME),     "&3Hide wing toggle OFF item name",    new Setting(ConfigSetting.SHOW_WING_TOGGLE_OFF_ITEM_MATERIAL)));
        setItem(50, new SettingItem(new Setting(ConfigSetting.SHOW_WING_TOGGLE_OFF_ITEM_MATERIAL), "&3Hide wing toggle OFF item material"));
        setItem(51, new SettingItem(new Setting(ConfigSetting.SHOW_WING_TOGGLE_SLOT),              "&3Hide wing toggle item slot",        Material.ENDER_EYE));

        // Go back item
        setItem(53, new GoBackItem());

    }
    
}
