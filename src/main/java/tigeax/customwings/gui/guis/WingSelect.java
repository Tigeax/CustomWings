package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tigeax.customwings.events.PlayerEquipWingEvent;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.main.CWPlayer;
import tigeax.customwings.main.CustomWings;
import tigeax.customwings.main.Settings;
import tigeax.customwings.main.Wing;
import tigeax.customwings.wingpurchase.BuyWings;

public class WingSelect {

	Settings settings;

	public WingSelect() {
		settings = CustomWings.getSettings();
	}

	public void open(CWPlayer cwPlayer) {

		String guiName = settings.getMainGUIName();
		int guiSize = settings.getMainGUISize();

		Inventory gui = Bukkit.createInventory(null, guiSize, guiName);

		ItemStack removeWingItem = settings.getRemoveWingItem();
		int removeWingSlot = settings.getRemoveWingSlot();

		if (guiSize > removeWingSlot) gui.setItem(removeWingSlot, removeWingItem);

		ItemStack hideWingsToggleItem;
		int hideWingsToggleSlot = settings.getHideWingsToggleSlot();

		if (guiSize > hideWingsToggleSlot) {

			if (cwPlayer.getHideOtherPlayerWings()) {
				hideWingsToggleItem = settings.getHideWingsToggleONItem();
			} else {
				hideWingsToggleItem = settings.getHideWingsToggleOFFItem();
			}

			gui.setItem(hideWingsToggleSlot, hideWingsToggleItem);
		}

		for (Wing wing : CustomWings.getWings()) {

			if (wing.getHideInGUI()) continue;

			int slot = wing.getGuiSlot();
			if (settings.getMainGUISize() < slot) continue;

			ItemStack wingItem = wing.getGuiItem().clone();
			ItemMeta wingItemMeta = wingItem.getItemMeta();

			// Add Lore to item
			if (cwPlayer.getEquippedWing() == wing)
				wingItemMeta.setLore(wing.getLoreWhenEquipped());
			else if (!cwPlayer.hasPermissionForWing(wing)) {

				if (wing.getWingPrice() == -1 || wing.getPriceType() == null || wing.getPriceType().equalsIgnoreCase("none")) {
					wingItemMeta.setLore(wing.getLoreWhenNoPermission());
				} else {
					wingItemMeta.setLore(wing.getloreWhenCanBuy());
				}
			}
				else
					wingItemMeta.setLore(wing.getLoreWhenUnequipped());

			wingItem.setItemMeta(wingItemMeta);
			gui.setItem(slot, wingItem);
		}

		cwPlayer.getPlayer().openInventory(gui);
		return;

	}

	public void click(CWPlayer cwPlayer, Integer clickedSlot) {

		Player player = cwPlayer.getPlayer();

		if (clickedSlot == CustomWings.getSettings().getHideWingsToggleSlot()) {

			cwPlayer.setHideOtherPlayerWings(!cwPlayer.getHideOtherPlayerWings());
			cwPlayer.openCWGUI(CWGUIType.WINGSELECT, null);
			return;
		}

		Wing wing;

		if (clickedSlot == CustomWings.getSettings().getRemoveWingSlot()) {
			wing = null;
		} else {
			wing = CustomWings.getWingByGUISlot(clickedSlot);

			if (!cwPlayer.hasPermissionForWing(wing)) {
				if (CustomWings.isVaultEnabled()) {
					try {
						String type = wing.getPriceType();
						int price = wing.getWingPrice();

						if (!BuyWings.buyWing(wing, player)) {
							player.sendMessage(CustomWings.getMessages().getNoPermissionEquipWing(wing));
						}
					} catch (NullPointerException e) {
						player.sendMessage(CustomWings.getMessages().getNoPermissionEquipWing(wing));
					}
				} else {
					player.sendMessage(CustomWings.getMessages().getNoPermissionEquipWing(wing));
				}
				return;
			}

		}

		// Fire the PlayerEquipWingEvent
		PlayerEquipWingEvent playerEquipWingEvent = new PlayerEquipWingEvent(cwPlayer, wing);

		Bukkit.getServer().getPluginManager().callEvent(playerEquipWingEvent);

		if (!playerEquipWingEvent.isCancelled()) {

			cwPlayer.setEquippedWing(wing);
			cwPlayer.closeInventory();

			if (wing != null) {
				player.sendMessage(CustomWings.getMessages().getWingSelected(wing));
			}
		}
	}

}
