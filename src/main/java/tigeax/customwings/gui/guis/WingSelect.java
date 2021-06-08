package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import tigeax.customwings.events.PlayerEquipWingEvent;
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.Settings;
import tigeax.customwings.wings.Wing;
import tigeax.customwings.wingpurchase.BuyWings;

public class WingSelect {

    CustomWings plugin;
    Settings settings;
    NamespacedKey CWNamespace = new NamespacedKey(CustomWings.getPlugin(CustomWings.class), "CustomWings");

    public WingSelect() {
        plugin = CustomWings.getInstance();
        settings = plugin.getSettings();
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

        if (clickedSlot == plugin.getSettings().getHideWingsToggleSlot()) {
            toggleWings(cwPlayer);
            return;
        }
        if (clickedSlot == plugin.getSettings().getFilterSlot()) {
            switch (cwPlayer.getWingFilter()) {
                default:
                    cwPlayer.setWingFilter("owned");
                    displayWings(cwPlayer, 0);
                    break;
                case "owned":
                    cwPlayer.setWingFilter("unowned");
                    displayWings(cwPlayer, 0);
                    break;
                case "unowned":
                    cwPlayer.setWingFilter("none");
                    displayWings(cwPlayer, 0);
                    break;
            }
        }

        Wing wing;

        if (clickedSlot == plugin.getSettings().getRemoveWingSlot()) {
            wing = null;
        } else if (clickedSlot == plugin.getSettings().getNavigationBackSlot() || clickedSlot == plugin.getSettings().getNavigationNextSlot()) {
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
            wing = CustomWings.getInstance().getWingByID(wingId);

            if (wing == null)
                return;

            if (!cwPlayer.hasPermissionForWing(wing)) {
                if (CustomWings.getInstance().isVaultEnabled()) {
                    try {
                        if (!BuyWings.buyWing(wing, player)) {
                            cwPlayer.sendMessage(plugin.getMessages().noPermissionToEquipWingError(wing));
                        }
                    } catch (NullPointerException e) {
                        cwPlayer.sendMessage(plugin.getMessages().noPermissionToEquipWingError(wing));
                    }
                } else {
                    cwPlayer.sendMessage(plugin.getMessages().noPermissionToEquipWingError(wing));
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
                cwPlayer.sendMessage(plugin.getMessages().wingEquipped(wing));
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

        Inventory gui = cwPlayer.getPlayer().getOpenInventory().getTopInventory();

        int limit = gui.getSize() - 9;
        int i = 0;
        while (i <= limit) {
            gui.clear(i++);
        }

        gui.clear(settings.getNavigationNextSlot());
        gui.clear(settings.getNavigationBackSlot());
        gui.clear(settings.getFilterSlot());

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
                gui.setItem(settings.getFilterSlot(), settings.getFilterNoneItem());
                for (Wing wing : plugin.getWings()) {
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
                gui.setItem(settings.getFilterSlot(), settings.getFilterOwnedItem());
                for (Wing wing : plugin.getWings()) {
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
                gui.setItem(settings.getFilterSlot(), settings.getFilterUnownedItem());
                for (Wing wing : plugin.getWings()) {
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
