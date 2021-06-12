package tigeax.customwings.menus.editor;

import org.bukkit.Material;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.settings.ConfigSetting;
import tigeax.customwings.menus.editor.items.GoBackItem;
import tigeax.customwings.menus.editor.items.MainSettingItem;
import tigeax.customwings.util.menu.ItemMenu;

public class MainSettingsMenu extends ItemMenu {

    public MainSettingsMenu(CustomWings plugin) {
        super("&cMain Settings", Rows.SIX);

        setItem(53, new GoBackItem());

        setItem(4, new MainSettingItem(plugin, ConfigSetting.WING_VIEW_DISTANCE, "&3View distance", Material.ENDER_PEARL));

        setItem(12, new MainSettingItem(plugin, ConfigSetting.WING_SELECT_MENU_NAME, "&Wing select menu name", Material.CHEST));
        setItem(14, new MainSettingItem(plugin, ConfigSetting.WING_SELECT_MENU_SIZE, "&Wing select menu size", Material.CHEST));
    }
    
}
