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

        setItem(53, new GoBackItem());

        setItem(4,  new SettingItem(new Setting(ConfigSetting.WING_VIEW_DISTANCE),    "&3View distance",            Material.ENDER_PEARL));
        setItem(12, new SettingItem(new Setting(ConfigSetting.WING_SELECT_MENU_NAME), "&3Wing select menu name",    Material.CHEST));
        setItem(14, new SettingItem(new Setting(ConfigSetting.WING_SELECT_MENU_SIZE), "&3Wing select menu size",    Material.CHEST));

    }
    
}
