package main.java.tigeax.customwings.eventlisteners;

import main.java.tigeax.customwings.gui.CWGUIType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import main.java.tigeax.customwings.main.CWPlayer;
import main.java.tigeax.customwings.main.CustomWings;

/*
 * This EventLisnter listends when a CustomWings Editor GUI is closed
 * To assign it to the player's lastEditorInvView
 */

public class InventoryCloseEventListener implements Listener {

	@EventHandler
	public void event(InventoryCloseEvent event) {

		String inventoryTitle = event.getView().getTitle();

		if (inventoryTitle.contains(CustomWings.getSettings().getEditorGUIName())) {

			CWGUIType cwGUIType = CustomWings.getCWGUIManager().getCWGUITypeByInvTitle(inventoryTitle);

			if (cwGUIType == CWGUIType.EDITORSELECTDOUBLE 
					|| cwGUIType == CWGUIType.EDITORSELECTGUISIZE
					|| cwGUIType == CWGUIType.EDITORSELECTINTEGER
					|| cwGUIType == CWGUIType.EDITORSELECTPARTICLE
					|| cwGUIType == CWGUIType.EDITORSELECTSLOT) {
				return;
			}

			CWPlayer cwPlayer = CustomWings.getCWPlayer((Player) event.getPlayer());
			cwPlayer.setLastEditorInvView(event.getView());

		}
	}
}
