package tigeax.customwings.eventlisteners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.util.menu.MenuHolder;

/*
 * This EventListener gets the value from a players inventory when we are waiting from a chat input from a player
 * Which is needed as an input to change a setting
 * 
 * It also listens if a player clicks in a CustomWings GUI
 */

public class InventoryClickEventListener implements Listener {

	CustomWings plugin;

	public InventoryClickEventListener() {
		plugin = CustomWings.getInstance();
	}

	@EventHandler
	public void event(InventoryClickEvent event) {

		// -----CustomWings GUI Click-----

		if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() instanceof MenuHolder) {

            event.setCancelled(true);

            MenuHolder menuHolder = ((MenuHolder) event.getInventory().getHolder());
            
            menuHolder.getMenu().onInventoryClick(event);
			
			return;
        }

		Player player = (Player) event.getWhoClicked();
		CWPlayer cwPlayer = plugin.getCWPlayer(player);
		ItemStack clickedItem = event.getCurrentItem();

		// Return if no item is clicked
		if (clickedItem == null) { return; }
		if (clickedItem.getType() == Material.AIR) { return; }
		
		// -----Waiting on a Setting Input-----

		// Check if we are waiting for a setting input, that is a material TODO
		// if (event.getClickedInventory() == player.getInventory()) {
			
		// 	SettingType setting = cwPlayer.getWaitingSetting();
		// 	Object settingInfo = cwPlayer.getWaitingSettingInfo();
			
		// 	if (setting != null) {
		// 		if (setting.isInventoryInputSetting()) {

		// 			event.setCancelled(true);

		// 			EditorConfigManager editorConfigManager = plugin.getEditorConfigManager();

		// 			Material value = clickedItem.getType();
		// 			editorConfigManager.setSetting(setting, value, settingInfo);

		// 			cwPlayer.setWaitingSetting(null);
		// 			cwPlayer.sendMessage(plugin.getMessages().settingChanged());
		// 			return;
		// 		}
		// 	}
		// }
	}
}
