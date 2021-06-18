package tigeax.customwings.menus.wingselect.items;

import org.bukkit.Material;
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
    }

    @Override
    public ItemStack getFinalItem(Player player) {

        CWPlayer cwPlayer = plugin.getCWPlayer(player);
        String filterString = cwPlayer.getWingFilter();
        Boolean permissonWing = cwPlayer.hasPermissionForWing(wing);

        if ((filterString.equals("ownedWings") && !permissonWing) || (filterString.equals("unownedWings") && permissonWing)) {
            // Don't show the wing
            setMaterial(Material.AIR);
        } else {
            // Show the wing
            setMaterial(wing.getConfig().getGuiItemMaterial());
            setLore(cwPlayer.getWingMenuItemLore(wing));
        }

        return super.getFinalItem(player);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        Player player = event.getPlayer();
        CWPlayer cwPlayer = plugin.getCWPlayer(player);

        if (!cwPlayer.hasPermissionForWing(wing)) {
            cwPlayer.sendMessage(plugin.getMessages().noPermissionToEquipWingError(wing));
            return;
        }

        cwPlayer.setEquippedWing(wing);

        event.setWillClose(true);
    }

}
