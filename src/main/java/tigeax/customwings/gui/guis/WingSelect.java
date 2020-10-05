package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
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


        cwPlayer.getPlayer().openInventory(gui);
        displayWings(cwPlayer, page);
    }

    public void click(CWPlayer cwPlayer, Integer clickedSlot, ItemStack clickedItem) {

        Player player = cwPlayer.getPlayer();

        if (clickedSlot == CustomWings.getSettings().getHideWingsToggleSlot()) {
            toggleWings(cwPlayer);
            return;
        }

        Wing wing;

        if (clickedSlot == CustomWings.getSettings().getRemoveWingSlot()) {
            wing = null;
        } else if (clickedSlot == CustomWings.getSettings().getNavigationBackSlot() || clickedSlot == CustomWings.getSettings().getNavigationNextSlot()) {
            try {
                String s = clickedItem.getItemMeta().getPersistentDataContainer().get(CWNamespace, PersistentDataType.STRING);
                if (s.startsWith("CW:PAGE:")) {
                    s = s.replace("CW:PAGE:", "");
                    int newPage = Integer.parseInt(s);
                    displayWings(cwPlayer, newPage);
                }
            } catch (NullPointerException e) {
                return;
            }
            return;
        } else {
            String wingId;

            wingId = clickedItem.getItemMeta().getPersistentDataContainer().get(CWNamespace, PersistentDataType.STRING);
            wing = CustomWings.getWingByID(wingId);

            if (wing == null)
                return;

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
        } else
            wingItemMeta.setLore(wing.getLoreWhenUnequipped());

        wingItemMeta.getPersistentDataContainer().set(CWNamespace, PersistentDataType.STRING, wing.getID());

        wingItem.setItemMeta(wingItemMeta);
        return wingItem;
    }

	private void displayWings(CWPlayer cwPlayer, int page) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Inventory gui = cwPlayer.getPlayer().getOpenInventory().getTopInventory();

                int limit = gui.getSize()-9;
                int i = 0;
                while (i <= limit) {
                    gui.clear(i++);
                }

                gui.clear(settings.getNavigationNextSlot());
                gui.clear(settings.getNavigationBackSlot());

                if (page > 0) {
                    int lastPage = page - 1;
                    gui.setItem(settings.getNavigationBackSlot(), settings.getNavigationBackItem(lastPage));
                }

                int slot = 0;
                int slotLimit = settings.getMainGUISize() - 9;
                int startLoop = page * slotLimit;
                int loops = -1;

                switch (cwPlayer.getWingFilter()) {
                    default:
                        for (Wing wing : CustomWings.getWings()) {
                            loops++;
                            if (loops < startLoop) continue;
                            if (wing.getHideInGUI()) continue;
                            if (slotLimit <= slot) {
                                int nextPage = page + 1;
                                gui.setItem(settings.getNavigationNextSlot(), settings.getNavigationNextItem(nextPage));
                                break;
                            }
                            gui.setItem(slot++, createWingItem(cwPlayer, wing));
                        }
                        break;
                    case "owned":
                        for (Wing wing : CustomWings.getWings()) {
                            loops++;
                            if (loops < startLoop) continue;
                            if (wing.getHideInGUI()) continue;
                            if (!cwPlayer.hasPermissionForWing(wing)) continue;
                            if (slotLimit <= slot) {
                                int nextPage = page + 1;
                                gui.setItem(settings.getNavigationNextSlot(), settings.getNavigationNextItem(nextPage));
                                break;
                            }
                            gui.setItem(slot++, createWingItem(cwPlayer, wing));
                        }
                        break;
                    case "unowned":
                        for (Wing wing : CustomWings.getWings()) {
                            loops++;
                            if (loops < startLoop) continue;
                            if (wing.getHideInGUI()) continue;
                            if (cwPlayer.hasPermissionForWing(wing)) continue;
                            if (slotLimit <= slot) {
                                int nextPage = page + 1;
                                gui.setItem(settings.getNavigationNextSlot(), settings.getNavigationNextItem(nextPage));
                                break;
                            }
                            gui.setItem(slot++, createWingItem(cwPlayer, wing));
                        }
                        break;
                }
            }
        }.runTaskAsynchronously(CustomWings.getPlugin(CustomWings.class));



	}

	private void toggleWings(CWPlayer cwPlayer) {
        Inventory gui = cwPlayer.getPlayer().getOpenInventory().getTopInventory();
        cwPlayer.setHideOtherPlayerWings(!cwPlayer.getHideOtherPlayerWings());
        gui.clear(settings.getHideWingsToggleSlot());
        if (!cwPlayer.getHideOtherPlayerWings()) {
            gui.setItem(settings.getHideWingsToggleSlot(), settings.getHideWingsToggleONItem());
        } else {
            gui.setItem(settings.getHideWingsToggleSlot(), settings.getHideWingsToggleOFFItem());
        }
    }

}
