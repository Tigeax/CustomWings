package tigeax.customwings.menus.items;

import org.bukkit.Material;

import tigeax.customwings.util.Util;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.MenuItem;

public class GoBackItem extends MenuItem {

    public GoBackItem() {
        setDisplayName(Util.parseChatColors("&4Back"));
        setMaterial(Material.RED_BED);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.setWillGoBack(true);
    }
}
