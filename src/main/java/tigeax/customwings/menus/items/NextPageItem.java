package tigeax.customwings.menus.items;

import tigeax.customwings.CustomWings;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.MenuItem;

public class NextPageItem extends MenuItem {

    public NextPageItem() {
        CustomWings plugin = CustomWings.getInstance();
        setDisplayName(plugin.getConfig().getNavigationNextItemName());
        setMaterial(plugin.getConfig().getNavigationNextItemMaterial());
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        event.getItemMenu().openNextPage(event.getPlayer());
    }
}
