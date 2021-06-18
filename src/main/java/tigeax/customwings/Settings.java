package tigeax.customwings;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;
import tigeax.customwings.gui.CWGUIType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/*
 * Class containting all global settings from the config.yml file
 * Settings are parsed when needed for easier use
 */

public class Settings {

    private FileConfiguration config;
    private CustomWings plugin;

    NamespacedKey CWNamespace = new NamespacedKey(CustomWings.getPlugin(CustomWings.class), "CustomWings");

    private int wingViewDistance, mainGUISize, removeWingSlot, hideWingsToggleSlot, editorMainSettingsSlot,
            navigationNextSlot, navigationBackSlot, filterSlot;
    private String mainGUIName, editorGUIName;
    private ItemStack removeWingItem, hideWingsToggleONItem, hideWingsToggleOFFItem, editorMainSettingsItem,
            navigationNextItem, navigationBackItem, filterNoneItem, filterOwnedItem, filterUnownedItem;
    private double wingMaxPitch;
    private boolean invisibilityPotionHidesWing;

    public Settings(CustomWings plugin) {
        this.plugin = plugin;
        load();
    }

    public void reload() {

        CustomWings.setupConfig();
        load();

        // Reload players CustomWings GUI's
        for (Player player : Bukkit.getOnlinePlayers()) {

            InventoryView inv = player.getOpenInventory();

            if (inv == null) continue;

            CWGUIType cwGUIType = CustomWings.getCWGUIManager().getCWGUITypeByInvTitle(inv.getTitle());

            if (cwGUIType == null) continue;

            if (cwGUIType == CWGUIType.WINGSELECT)
                CustomWings.getCWPlayer(player).openCWGUI(CWGUIType.WINGSELECT);
            else
                CustomWings.getCWPlayer(player).openCWGUI(CWGUIType.LASTEDITORGUI);
        }
    }

    public void load() {

        this.config = plugin.getCWConfig();

        wingViewDistance = config.getInt("wingViewDistance");

        mainGUIName = parseColors(config.getString("mainGUI.name"));
        mainGUISize = config.getInt("mainGUI.size");

        removeWingItem = getItem(config.getString("mainGUI.removeWingItem.name"), config.getString("mainGUI.removeWingItem.material"), null);
        ItemMeta removeWingItemMeta = removeWingItem.getItemMeta();
        removeWingItemMeta.getPersistentDataContainer().set(CWNamespace, PersistentDataType.STRING, "CW:REMOVE_WING");
        removeWingItem.setItemMeta(removeWingItemMeta);
        removeWingSlot = config.getInt("mainGUI.removeWingItem.slot", 53);

        hideWingsToggleONItem = getItem(config.getString("mainGUI.hideWingsToggleItem.nameON"), config.getString("mainGUI.hideWingsToggleItem.materialON"), null);
        ItemMeta hideWingsToggleONItemMeta = hideWingsToggleONItem.getItemMeta();
        hideWingsToggleONItemMeta.getPersistentDataContainer().set(CWNamespace, PersistentDataType.STRING, "CW:HIDE_WINGS_ON");
        hideWingsToggleONItem.setItemMeta(hideWingsToggleONItemMeta);

        hideWingsToggleOFFItem = getItem(config.getString("mainGUI.hideWingsToggleItem.nameOFF"), config.getString("mainGUI.hideWingsToggleItem.materialOFF"), null);
        ItemMeta hideWingsToggleOFFItemMeta = hideWingsToggleOFFItem.getItemMeta();
        hideWingsToggleOFFItemMeta.getPersistentDataContainer().set(CWNamespace, PersistentDataType.STRING, "CW:HIDE_WINGS_OFF");
        hideWingsToggleOFFItem.setItemMeta(hideWingsToggleOFFItemMeta);

        hideWingsToggleSlot = config.getInt("mainGUI.hideWingsToggleItem.slot", 52);

        navigationBackItem = getItem(config.getString("mainGUI.navigationItem.back.name", "&9Previous Page"), config.getString("mainGUI.navigationItem.back.material", "FEATHER"), null);
        navigationBackSlot = config.getInt("mainGUI.navigationItem.back.slot", 49);

        navigationNextItem = getItem(config.getString("mainGUI.navigationItem.next.name", "&9Next Page"), config.getString("mainGUI.navigationItem.next.material", "FEATHER"), null);
        navigationNextSlot = config.getInt("mainGUI.navigationItem.next.slot", 50);

        editorGUIName = parseColors(config.getString("editorGUI.name"));

        editorMainSettingsItem = getItem(config.getString("editorGUI.mainSettingsItem.name"), config.getString("editorGUI.mainSettingsItem.material"), null);
        editorMainSettingsSlot = config.getInt("editorGUI.mainSettingsItem.slot");

        filterNoneItem = getItem(config.getString("mainGUI.filterItem.noFilter.name", "&fNo filter"), config.getString("mainGUI.filterItem.noFilter.material", "BARRIER"), config.getStringList("mainGUI.filterItem.noFilter.lore"));
        filterOwnedItem = getItem(config.getString("mainGUI.filterItem.ownedWings.name", "&aOwned wings"), config.getString("mainGUI.filterItem.ownedWings.material", "LIME_WOOL"), config.getStringList("mainGUI.filterItem.ownedWings.lore"));
        filterUnownedItem = getItem(config.getString("mainGUI.filterItem.unownedWings.name", "&cUnowned wings"), config.getString("mainGUI.filterItem.unownedWings.material", "RED_WOOL"), config.getStringList("mainGUI.filterItem.unownedWings.lore"));

        filterSlot = config.getInt("mainGUI.filterItem.slot", 45);

        wingMaxPitch = config.getDouble("wingMaxPitch", 40D);

        invisibilityPotionHidesWing = config.getBoolean("invisibilityPotionHidesWing", true);
    }

    public int getWingViewDistance() {
        return wingViewDistance;
    }

    public String getMainGUIName() {
        return mainGUIName;
    }

    public int getMainGUISize() {
        return mainGUISize;
    }

    public ItemStack getRemoveWingItem() {
        return removeWingItem;
    }

    public int getRemoveWingSlot() {
        return removeWingSlot;
    }

    public ItemStack getHideWingsToggleONItem() {
        return hideWingsToggleONItem;
    }

    public ItemStack getHideWingsToggleOFFItem() {
        return hideWingsToggleOFFItem;
    }

    public int getHideWingsToggleSlot() {
        return hideWingsToggleSlot;
    }

    public String getEditorGUIName() {
        return editorGUIName;
    }

    public ItemStack getEditorMainSettingsItem() {
        return editorMainSettingsItem;
    }

    public int getEditorMainSettingsSlot() {
        return editorMainSettingsSlot;
    }

    public ItemStack getNavigationNextItem(int page) {
        return getNavItem(navigationNextItem, page);
    }

    public int getNavigationNextSlot() {
        return navigationNextSlot;
    }

    public ItemStack getNavigationBackItem(int page) {
        return getNavItem(navigationBackItem, page);
    }

    public int getNavigationBackSlot() {
        return navigationBackSlot;
    }

    public ItemStack getFilterNoneItem() {
        return filterNoneItem;
    }

    public ItemStack getFilterOwnedItem() {
        return filterOwnedItem;
    }

    public ItemStack getFilterUnownedItem() {
        return filterUnownedItem;
    }

    public int getFilterSlot() {
        return filterSlot;
    }

    public double getWingMaxPitch() {
        return wingMaxPitch;
    }

    public boolean getInvisPotionHidesWing() {
        return invisibilityPotionHidesWing;
    }

    private ItemStack getItem(String name, String material, @Nullable List<String> lore) {
        ItemStack item = new ItemStack(Material.valueOf(material));

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(parseColors(name));

        if (lore != null) {
            List<String> coloredLore = new ArrayList<>();
            for (String line : lore) {
                coloredLore.add(parseColors(line));
            }
            itemMeta.setLore(coloredLore);
        }

        item.setItemMeta(itemMeta);
        return item;
    }

    private ItemStack getNavItem(ItemStack itemStack, int page) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(CWNamespace, PersistentDataType.STRING, "CW:PAGE:" + page);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private String parseColors(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
