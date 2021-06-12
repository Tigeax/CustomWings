package tigeax.customwings.menus.wingselect.items;

import org.bukkit.entity.Player;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.MenuItem;

public class WingRemoveItem extends MenuItem {

    private CustomWings plugin;

    public WingRemoveItem(CustomWings plugin) {
        this.plugin = plugin;
        setDisplayName(plugin.getConfig().getRemoveWingItemName());
        setMaterial(plugin.getConfig().getRemoveWingItemMaterial());
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        Player player = event.getPlayer();
        CWPlayer cwPlayer = plugin.getCWPlayer(player);

        cwPlayer.setEquippedWing(null);

        event.setWillClose(true);
    }
    
}
