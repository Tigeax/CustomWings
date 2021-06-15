package tigeax.customwings.menus.editor.settingmenus.items;

import org.bukkit.Material;

import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.SubMenuItem;

public class MainSettingsMenuItem extends SubMenuItem {

    public MainSettingsMenuItem(ItemMenu itemMenu) {
        super(itemMenu);
        
        setDisplayName("&cMain Settings");
        setMaterial(Material.CRAFTING_TABLE);
    }    
}
