package tigeax.customwings.eventlisteners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.configuration.settings.SettingType;
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

		// -----Waiting on a Setting Input-----

		Player player = (Player) event.getWhoClicked();
		CWPlayer cwPlayer = plugin.getCWPlayer(player);
		Setting setting = cwPlayer.getWaitingSetting();

		if (setting == null) {
			return;
		}

		ItemStack clickedItem = event.getCurrentItem();

		// Return if no item is clicked
		if (clickedItem == null) {
			return;
		}

		if (clickedItem.getType() == Material.AIR) {
			return;
		}

		if (setting.getSettingType() == SettingType.MATERIAL) {

			Material value = clickedItem.getType();
			setting.setValue(value);

			cwPlayer.sendMessage(plugin.getMessages().settingChanged());
		}
	}
}
