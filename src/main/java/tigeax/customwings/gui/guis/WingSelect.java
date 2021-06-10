package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.Configuration;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.events.PlayerEquipWingEvent;
import tigeax.customwings.wingpurchase.BuyWings;
import tigeax.customwings.wings.Wing;

public class WingSelect {

    CustomWings plugin;
    Configuration config;
    NamespacedKey CWNamespace = new NamespacedKey(CustomWings.getPlugin(CustomWings.class), "CustomWings");

    public WingSelect() {
        plugin = CustomWings.getInstance();
        config = plugin.getConfig();
    }

    public void open(CWPlayer cwPlayer, int page) {

        String guiName = config.getMainGUIName();
        int guiSize = config.getMainGUISize();

        Inventory gui = Bukkit.createInventory(null, guiSize, guiName);

        ItemStack removeWingItem = config.getRemoveWingItem();
        int removeWingSlot = config.getRemoveWingSlot();

        if (guiSize > removeWingSlot) gui.setItem(removeWingSlot, removeWingItem);

        ItemStack hideWingsToggleItem;
        int hideWingsToggleSlot = config.getHideWingsToggleSlot();

        if (guiSize > hideWingsToggleSlot) {
            if (cwPlayer.getHideOtherPlayerWings()) {
                hideWingsToggleItem = config.getHideWingsToggleOFFItem();
            } else {
                hideWingsToggleItem = config.getHideWingsToggleONItem();
            }
            gui.setItem(hideWingsToggleSlot, hideWingsToggleItem);
        }


        cwPlayer.getPlayer().openInventory(gui);
        displayWings(cwPlayer, page);
    }

    public void click(CWPlayer cwPlayer, Integer clickedSlot, ItemStack clickedItem) {

        Player player = cwPlayer.getPlayer();

        if (clickedSlot == config.getHideWingsToggleSlot()) {
            toggleWings(cwPlayer);
            return;
        }
        if (clickedSlot == config.getFilterSlot()) {
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

        if (clickedSlot == config.getRemoveWingSlot()) {
            wing = null;
        } else if (clickedSlot == config.getNavigationBackSlot() || clickedSlot == config.getNavigationNextSlot()) {
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
        WingConfig wingConfig = wing.getConfig();
        ItemStack wingItem = wingConfig.getGuiItem().clone();
        ItemMeta wingItemMeta = wingItem.getItemMeta();

        // Add Lore to item
        if (cwPlayer.getEquippedWing() == wing)
            wingItemMeta.setLore(wingConfig.getLoreWhenEquipped());
        else if (!cwPlayer.hasPermissionForWing(wing)) {

            if (wingConfig.getWingPrice() == -1 || wingConfig.getPriceType() == null || wingConfig.getPriceType().equalsIgnoreCase("none")) {
                wingItemMeta.setLore(wingConfig.getLoreWhenNoPermission());
            } else {
                wingItemMeta.setLore(wingConfig.getloreWhenCanBuy());
            }
        } else
            wingItemMeta.setLore(wingConfig.getLoreWhenUnequipped());

        wingItemMeta.getPersistentDataContainer().set(CWNamespace, PersistentDataType.STRING, wingConfig.getID());

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

        gui.clear(config.getNavigationNextSlot());
        gui.clear(config.getNavigationBackSlot());
        gui.clear(config.getFilterSlot());

        if (page > 0) {
            int lastPage = page - 1;
            gui.setItem(config.getNavigationBackSlot(), config.getNavigationBackItem(lastPage));
        }

        int slot = 0;
        int slotLimit = config.getMainGUISize() - 9;
        int startLoop = page * slotLimit;
        int loops = -1;

        switch (cwPlayer.getWingFilter()) {
            default:
                gui.setItem(config.getFilterSlot(), config.getFilterNoneItem());
                for (Wing wing : plugin.getWings()) {
                    loops++;
                    if (loops < startLoop) continue;
                    if (wing.getConfig().getHideInGUI()) continue;
                    if (slotLimit <= slot) {
                        int nextPage = page + 1;
                        gui.setItem(config.getNavigationNextSlot(), config.getNavigationNextItem(nextPage));
                        break;
                    }
                    gui.setItem(slot++, createWingItem(cwPlayer, wing));
                }
                break;
            case "owned":
                gui.setItem(config.getFilterSlot(), config.getFilterOwnedItem());
                for (Wing wing : plugin.getWings()) {
                    loops++;
                    if (loops < startLoop) continue;
                    if (wing.getConfig().getHideInGUI()) continue;
                    if (!cwPlayer.hasPermissionForWing(wing)) continue;
                    if (slotLimit <= slot) {
                        int nextPage = page + 1;
                        gui.setItem(config.getNavigationNextSlot(), config.getNavigationNextItem(nextPage));
                        break;
                    }
                    gui.setItem(slot++, createWingItem(cwPlayer, wing));
                }
                break;
            case "unowned":
                gui.setItem(config.getFilterSlot(), config.getFilterUnownedItem());
                for (Wing wing : plugin.getWings()) {
                    loops++;
                    if (loops < startLoop) continue;
                    if (wing.getConfig().getHideInGUI()) continue;
                    if (cwPlayer.hasPermissionForWing(wing)) continue;
                    if (slotLimit <= slot) {
                        int nextPage = page + 1;
                        gui.setItem(config.getNavigationNextSlot(), config.getNavigationNextItem(nextPage));
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
        gui.clear(config.getHideWingsToggleSlot());
        if (!cwPlayer.getHideOtherPlayerWings()) {
            gui.setItem(config.getHideWingsToggleSlot(), config.getHideWingsToggleONItem());
        } else {
            gui.setItem(config.getHideWingsToggleSlot(), config.getHideWingsToggleOFFItem());
        }
    }
}
