package tigeax.customwings.eventlisteners;

import tigeax.customwings.gui.CWGUIType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;

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

		String inventoryTitle = event.getView().getTitle();

		if (inventoryTitle.contains(plugin.getSettings().getEditorGUIName())) {

			CWGUIType cwGUIType = plugin.getCWGUIManager().getCWGUITypeByInvTitle(inventoryTitle);

			if (cwGUIType == null
					|| cwGUIType == CWGUIType.EDITORSELECTDOUBLE
					|| cwGUIType == CWGUIType.EDITORSELECTGUISIZE
					|| cwGUIType == CWGUIType.EDITORSELECTINTEGER
					|| cwGUIType == CWGUIType.EDITORSELECTPARTICLE
					|| cwGUIType == CWGUIType.EDITORSELECTSLOT) {
				return;
			}

			CWPlayer cwPlayer = plugin.getCWPlayer((Player) event.getPlayer());
			cwPlayer.setLastEditorInvView(event.getView());

		}
	}
}
