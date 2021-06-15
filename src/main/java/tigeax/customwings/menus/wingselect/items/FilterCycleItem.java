package tigeax.customwings.menus.wingselect.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.Config;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.MenuItem;

public class FilterCycleItem extends MenuItem {

    private CustomWings plugin;
    private Config config;

    public FilterCycleItem(CustomWings plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        Player player = event.getPlayer();
        CWPlayer cwPlayer = plugin.getCWPlayer(player);

        cwPlayer.cycleWingFilter();

        event.setWillUpdate(true);
    }

    @Override
    public ItemStack getFinalItem(Player player) {

        CWPlayer cwPlayer = plugin.getCWPlayer(player);
        String filterString = cwPlayer.getWingFilter();

        if (filterString.equals("noFilter")) {
            setNoFilterItem();
		} else if (filterString.equals("ownedWings")) {
			setOwnedWingsFilterItem();
		} else if (filterString.equals("unownedWings")) {
			setUnownedWingsFilterItem();
		}

        return super.getFinalItem(player);
    }
    

    public void setNoFilterItem() {
        setDisplayName(config.getFilterNoneItemName());
        setMaterial(config.getFilterNoneItemMaterial());
        setLore(config.getFilterNoneItemLore());
    }

    public void setOwnedWingsFilterItem() {
        setDisplayName(config.getFilterOwnedItemName());
        setMaterial(config.getFilterOwnedItemMaterial());
        setLore(config.getFilterOwnedItemLore());
    }

    public void setUnownedWingsFilterItem() {
        setDisplayName(config.getFilterUnownedItemName());
        setMaterial(config.getFilterUnownedItemMaterial());
        setLore(config.getFilterUnownedItemLore());
    }
    
}
