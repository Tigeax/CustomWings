package tigeax.customwings.menus.wingselect.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.MenuItem;

public class WingShowToggleItem extends MenuItem {

    private CustomWings plugin;

    public WingShowToggleItem(CustomWings plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        Player player = event.getPlayer();
        CWPlayer cwPlayer = plugin.getCWPlayer(player);

        // Toggle hide other player wings
        cwPlayer.setShowWing(!cwPlayer.getShowWing());

        event.setWillUpdate(true);
    }

    @Override
    public ItemStack getFinalItem(Player player) {

        CWPlayer cwPlayer = plugin.getCWPlayer(player);

        if (cwPlayer.getShowWing()) {
            setShowWingToggleONItem();
        } else {
            setShowWingToggleOFFItem();
        }

        return super.getFinalItem(player);
    }
    

    public void setShowWingToggleONItem() {
        setDisplayName(plugin.getConfig().getShowWingToggleONItemName());
        setMaterial(plugin.getConfig().getShowWingToggleONItemMaterial());
    }

    public void setShowWingToggleOFFItem() {
        setDisplayName(plugin.getConfig().getShowWingToggleOFFItemName());
        setMaterial(plugin.getConfig().getShowWingToggleOFFItemMaterial());
    }
    
}
