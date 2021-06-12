package tigeax.customwings.menus.editor.items;

import org.bukkit.Material;

import tigeax.customwings.util.Util;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.SubMenuItem;

public class MainSettingsMenuItem extends SubMenuItem {

    public MainSettingsMenuItem(ItemMenu itemMenu) {
        super(itemMenu);
        setDisplayName(Util.parseChatColors("&cMain Settings"));
        setMaterial(Material.CRAFTING_TABLE);
    }    
}
