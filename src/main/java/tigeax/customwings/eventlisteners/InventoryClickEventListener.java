package main.java.tigeax.customwings.eventlisteners;

import main.java.tigeax.customwings.gui.CWGUIType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import main.java.tigeax.customwings.editor.EditorConfigManager;
import main.java.tigeax.customwings.editor.SettingType;
import main.java.tigeax.customwings.events.PlayerCWGUIClickEvent;
import main.java.tigeax.customwings.main.CWPlayer;
import main.java.tigeax.customwings.main.CustomWings;

/*
 * This EventListener gets the value from a players inventory when we are waiting from a chat input from a player
 * Which is needed as an input to change a setting
 * 
 * It also listens if a player clicks in a CustomWings GUI
 */

public class InventoryClickEventListener implements Listener {

	@EventHandler
	public void event(InventoryClickEvent event) {

		String inventoryTitle = event.getView().getTitle();
		Player player = (Player) event.getWhoClicked();
		CWPlayer cwPlayer = CustomWings.getCWPlayer(player);
		ItemStack clickedItem = event.getCurrentItem();

		// Return if no item is clicked
		if (clickedItem == null) { return; }
		if (clickedItem.getType() == Material.AIR) { return; }
		
		// -----Waiting on a Setting Input-----

		// Check if we are waiting for a setting input, that is a material
		if (event.getClickedInventory() == player.getInventory()) {
			
			SettingType setting = cwPlayer.getWaitingSetting();
			Object settingInfo = cwPlayer.getWaitingSettingInfo();
			
			if (setting != null) {
				if (setting.isInventoryInputSetting()) {

					event.setCancelled(true);

					EditorConfigManager editorConfigManager = CustomWings.getEditorConfigManager();

					Material value = clickedItem.getType();
					editorConfigManager.setSetting(setting, value, settingInfo);

					cwPlayer.setWaitingSetting(null);
					player.sendMessage(CustomWings.getMessages().getSettingChanged());
					return;
				}
			}
		}

		// -----CustomWings GUI Click-----
		
		int clickedItemSlot = event.getSlot();

		// Check if the clicked inventory is the CustomWings GUI
		if (inventoryTitle.equals(CustomWings.getSettings().getMainGUIName())
				|| inventoryTitle.contains(CustomWings.getSettings().getEditorGUIName())) {
			
			event.setCancelled(true);

			if (player.getInventory() == event.getClickedInventory()) { return; }

			if (clickedItem.getItemMeta() == null) { return; }

			CWGUIType CWGUIType = CustomWings.getCWGUIManager().getCWGUITypeByInvTitle(inventoryTitle);

			PlayerCWGUIClickEvent playerCWGUIClickEvent = new PlayerCWGUIClickEvent(cwPlayer, CWGUIType, event.getView(), clickedItem, clickedItemSlot);
			Bukkit.getServer().getPluginManager().callEvent(playerCWGUIClickEvent);

			if (!playerCWGUIClickEvent.isCancelled()) {
				CustomWings.getCWGUIManager().CWGUIClick(cwPlayer, CWGUIType, event.getView(), clickedItem, clickedItemSlot);
			}

		}

	}
}
