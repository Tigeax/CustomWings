package tigeax.customwings.menus.editor.settingmenus.items;

import tigeax.customwings.menus.editor.settingmenus.WingSettingsMenu;
import tigeax.customwings.util.menu.SubMenuItem;
import tigeax.customwings.wing.Wing;

public class WingSettingsMenuItem extends SubMenuItem {

    public WingSettingsMenuItem(WingSettingsMenu wingSettingsMenu, Wing wing) {
        super(wingSettingsMenu);

        setDisplayName(wing.getConfig().getGuiItemName());
        setMaterial(wing.getConfig().getGuiItemMaterial());
    }

}
