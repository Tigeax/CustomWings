package tigeax.customwings.menus.wingselect.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.MenuItem;

public class HideOtherWingsToggleItem extends MenuItem {

    private CustomWings plugin;

    public HideOtherWingsToggleItem(CustomWings plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        Player player = event.getPlayer();
        CWPlayer cwPlayer = plugin.getCWPlayer(player);

        // Toggle hide other player wings
        cwPlayer.setHideOtherPlayerWings(!cwPlayer.getHideOtherPlayerWings());

        event.setWillUpdate(true);
    }

    @Override
    public ItemStack getFinalItem(Player player) {

        CWPlayer cwPlayer = plugin.getCWPlayer(player);

        if (cwPlayer.getHideOtherPlayerWings()) {
            setHideOtherWingsToggleONItem();
        } else {
            setHideOtherWingsToggleOFFItem();
        }

        return super.getFinalItem(player);
    }
    

    public void setHideOtherWingsToggleONItem() {
        setDisplayName(plugin.getConfig().getHideOtherWingsToggleONItemName());
        setMaterial(plugin.getConfig().getHideOtherWingsToggleONItemMaterial());
    }

    public void setHideOtherWingsToggleOFFItem() {
        setDisplayName(plugin.getConfig().getHideOtherWingsToggleOFFItemName());
        setMaterial(plugin.getConfig().getHideOtherWingsToggleOFFItemMaterial());
    }
    
}
