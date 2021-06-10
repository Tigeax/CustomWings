package tigeax.customwings.configuration;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import tigeax.customwings.CustomWings;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.YamlFile;

public class Configuration extends YamlFile {

    NamespacedKey CWNamespace = new NamespacedKey(CustomWings.getPlugin(CustomWings.class), "CustomWings");

    private int wingViewDistance, mainGUISize, removeWingSlot, hideWingsToggleSlot, editorMainSettingsSlot,
            navigationNextSlot, navigationBackSlot, filterSlot;
    private String mainGUIName, editorGUIName;
    private ItemStack removeWingItem, hideWingsToggleONItem, hideWingsToggleOFFItem, editorMainSettingsItem,
            navigationNextItem, navigationBackItem, filterNoneItem, filterOwnedItem, filterUnownedItem;
    private double wingMaxPitch;
    private boolean invisibilityPotionHidesWing;

    public Configuration(CustomWings plugin) {
        super(plugin, "config.yml");
        loadDataFromFile();
    }

    protected void loadDataFromFile() {

        wingViewDistance = getInt("wingViewDistance");

        mainGUIName = getString("mainGUI.name");
        mainGUISize = getInt("mainGUI.size");

        removeWingItem = getItem(getString("mainGUI.removeWingItem.name"), getString("mainGUI.removeWingItem.material"), null);
        ItemMeta removeWingItemMeta = removeWingItem.getItemMeta();
        removeWingItemMeta.getPersistentDataContainer().set(CWNamespace, PersistentDataType.STRING, "CW:REMOVE_WING");
        removeWingItem.setItemMeta(removeWingItemMeta);
        removeWingSlot = getInt("mainGUI.removeWingItem.slot", 53);

        hideWingsToggleONItem = getItem(getString("mainGUI.hideWingsToggleItem.nameON"), getString("mainGUI.hideWingsToggleItem.materialON"), null);
        ItemMeta hideWingsToggleONItemMeta = hideWingsToggleONItem.getItemMeta();
        hideWingsToggleONItemMeta.getPersistentDataContainer().set(CWNamespace, PersistentDataType.STRING, "CW:HIDE_WINGS_ON");
        hideWingsToggleONItem.setItemMeta(hideWingsToggleONItemMeta);

        hideWingsToggleOFFItem = getItem(getString("mainGUI.hideWingsToggleItem.nameOFF"), getString("mainGUI.hideWingsToggleItem.materialOFF"), null);
        ItemMeta hideWingsToggleOFFItemMeta = hideWingsToggleOFFItem.getItemMeta();
        hideWingsToggleOFFItemMeta.getPersistentDataContainer().set(CWNamespace, PersistentDataType.STRING, "CW:HIDE_WINGS_OFF");
        hideWingsToggleOFFItem.setItemMeta(hideWingsToggleOFFItemMeta);

        hideWingsToggleSlot = getInt("mainGUI.hideWingsToggleItem.slot", 52);

        navigationBackItem = getItem(getString("mainGUI.navigationItem.back.name", "&9Previous Page"), getString("mainGUI.navigationItem.back.material", "FEATHER"), null);
        navigationBackSlot = getInt("mainGUI.navigationItem.back.slot", 49);

        navigationNextItem = getItem(getString("mainGUI.navigationItem.next.name", "&9Next Page"), getString("mainGUI.navigationItem.next.material", "FEATHER"), null);
        navigationNextSlot = getInt("mainGUI.navigationItem.next.slot", 50);

        editorGUIName = getString("editorGUI.name");

        editorMainSettingsItem = getItem(getString("editorGUI.mainSettingsItem.name"), getString("editorGUI.mainSettingsItem.material"), null);
        editorMainSettingsSlot = getInt("editorGUI.mainSettingsItem.slot");

        filterNoneItem = getItem(getString("mainGUI.filterItem.noFilter.name", "&fNo filter"), getString("mainGUI.filterItem.noFilter.material", "BARRIER"), getStringList("mainGUI.filterItem.noFilter.lore"));
        filterOwnedItem = getItem(getString("mainGUI.filterItem.ownedWings.name", "&aOwned wings"), getString("mainGUI.filterItem.ownedWings.material", "LIME_WOOL"), getStringList("mainGUI.filterItem.ownedWings.lore"));
        filterUnownedItem = getItem(getString("mainGUI.filterItem.unownedWings.name", "&cUnowned wings"), getString("mainGUI.filterItem.unownedWings.material", "RED_WOOL"), getStringList("mainGUI.filterItem.unownedWings.lore"));

        filterSlot = getInt("mainGUI.filterItem.slot", 45);

        wingMaxPitch = getDouble("wingMaxPitch", 40D);

        invisibilityPotionHidesWing = getBoolean("invisibilityPotionHidesWing", true);
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

    @Override
    public String getString(String path) {
        String string = super.getString(path);
        string = Util.parseChatColors(string);
        return string;
    }

    //TODO Refractor

    private ItemStack getItem(String name, String material, @Nullable List<String> lore) {
        ItemStack item = new ItemStack(Material.valueOf(material));

        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Util.parseChatColors(name));

        if (lore != null) {
            List<String> coloredLore = new ArrayList<>();
            for (String line : lore) {
                coloredLore.add(Util.parseChatColors(line));
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

}


