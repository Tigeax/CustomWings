package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import tigeax.customwings.events.PlayerEquipWingEvent;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.Settings;
import tigeax.customwings.wings.Wing;
import tigeax.customwings.wingpurchase.BuyWings;

public class WingSelect {

	Settings settings;
	NamespacedKey CWNamespace = new NamespacedKey(CustomWings.getPlugin(CustomWings.class), "CustomWings");

	public WingSelect() {
		settings = CustomWings.getSettings();
	}

	public void open(CWPlayer cwPlayer, int page) {

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
				hideWingsToggleItem = settings.getHideWingsToggleOFFItem();
			} else {
				hideWingsToggleItem = settings.getHideWingsToggleONItem();
			}
			gui.setItem(hideWingsToggleSlot, hideWingsToggleItem);
		}

		int slot = 0;
		boolean hasNextPage = false;
		for (Wing wing : CustomWings.getWings()) {
			if (wing.getHideInGUI()) continue;
			if (settings.getMainGUISize()-9 <= slot) {
				hasNextPage = true;
				continue;
			}
			gui.setItem(slot, createWingItem(cwPlayer, wing));
			slot++;
		}
		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, Integer clickedSlot, ItemStack clickedItem) {

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
			String wingId;

			wingId = clickedItem.getItemMeta().getPersistentDataContainer().get(CWNamespace, PersistentDataType.STRING);
			wing = CustomWings.getWingByID(wingId);

			if (!cwPlayer.hasPermissionForWing(wing)) {
				if (CustomWings.isVaultEnabled()) {
					try {
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

	private ItemStack createWingItem(CWPlayer cwPlayer, Wing wing) {
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

		wingItemMeta.getPersistentDataContainer().set(CWNamespace, PersistentDataType.STRING, wing.getID());

		wingItem.setItemMeta(wingItemMeta);
		return wingItem;
	}

}
