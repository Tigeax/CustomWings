package tigeax.customwings.menus.wingselect.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.MenuItem;
import tigeax.customwings.wing.Wing;

public class WingSelectItem extends MenuItem {

    private CustomWings plugin;
    private Wing wing;

    public WingSelectItem(CustomWings plugin, Wing wing) {
        this.plugin = plugin;
        this.wing = wing;
        setDisplayName(wing.getConfig().getGuiItemName());
        setMaterial(wing.getConfig().getGuiItemMaterial());
    }

    @Override
    public ItemStack getFinalItem(Player player) {

        CWPlayer cwPlayer = plugin.getCWPlayer(player);
        setLore(cwPlayer.getWingMenuItemLore(wing));

        return super.getFinalItem(player);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        Player player = event.getPlayer();
        CWPlayer cwPlayer = plugin.getCWPlayer(player);

        cwPlayer.setEquippedWing(wing);

        event.setWillClose(true);
    }

}
