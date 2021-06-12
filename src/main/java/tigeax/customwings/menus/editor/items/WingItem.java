package tigeax.customwings.menus.editor.items;

import tigeax.customwings.CustomWings;
import tigeax.customwings.menus.editor.WingSettingsMenu;
import tigeax.customwings.util.menu.SubMenuItem;
import tigeax.customwings.wing.Wing;

public class WingItem extends SubMenuItem {


    public WingItem(CustomWings plugin, WingSettingsMenu wingMenu) {
        super(wingMenu);
        Wing wing = wingMenu.getWing();
        setDisplayName(wing.getConfig().getGuiItemName());
        setMaterial(wing.getConfig().getGuiItemMaterial());
    }

}
