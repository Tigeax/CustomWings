package tigeax.customwings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.menus.wingselect.WingSelectMenu;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuHolder;

/*
 * This EventLisnter listends when a CustomWings Editor GUI is closed
 * To assign it to the player's lastEditorInvView
 */

public class InventoryCloseEventListener implements Listener {

	CustomWings plugin;

	public InventoryCloseEventListener() {
		plugin = CustomWings.getInstance();
	}

	@EventHandler
	public void event(InventoryCloseEvent event) {

		if (!(event.getPlayer() instanceof Player)) {
			return;
		}

		if (event.getInventory().getHolder() instanceof MenuHolder) {

			MenuHolder menuHolder = ((MenuHolder) event.getInventory().getHolder());
			ItemMenu menu = menuHolder.getMenu();

			if (menu instanceof WingSelectMenu) {
				return;
			}

			CWPlayer cwPlayer = plugin.getCWPlayer((Player) event.getPlayer());

			cwPlayer.setLastEditorMenu(menu);
		}
	}
}
