package tigeax.customwings.menus.items;

import tigeax.customwings.CustomWings;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.MenuItem;

public class PreviousPageItem extends MenuItem {

    public PreviousPageItem() {
        CustomWings plugin = CustomWings.getInstance();
        setDisplayName(plugin.getConfig().getNavigationPreviousItemName());
        setMaterial(plugin.getConfig().getNavigationNextItemMaterial());
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.getItemMenu().openPreviousPage(event.getPlayer());
    }
}
