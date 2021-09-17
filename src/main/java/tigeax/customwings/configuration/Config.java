package tigeax.customwings.configuration;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import tigeax.customwings.configuration.settings.ConfigSetting;
import tigeax.customwings.util.YamlFile;

public class Config extends YamlFile {

    private int wingViewDistance, wingMaxPitch, wingSelectMenuSize;
    private int wingSelectMenuPages;
    private int removeWingSlot;
    private int hideWingsToggleSlot;
    private int navigationNextSlot;
    private int navigationPreviousSlot;
    private int filterSlot;
    private String commandName, wingSelectMenuName, removeWingItemName, hideWingsToggleONItemName,
            hideWingsToggleOFFItemName, navigationNextItemName, navigationPreviousItemName, filterNoneItemName,
            filterOwnedItemName, filterUnownedItemName;
    private List<String> commandAliases, filterNoneItemLore, filterOwnedItemLore, filterUnownedItemLore;
    private Material removeWingItemMaterial, hideWingsToggleONItemMaterial, hideWingsToggleOFFItemMaterial,
            navigationNextItemMaterial, navigationPreviousItemMaterial, filterNoneItemMaterial, filterOwnedItemMaterial,
            filterUnownedItemMaterial;
    private boolean invisibilityPotionHidesWing, filterItemEnable;

    public Config(JavaPlugin plugin) {
        super(plugin, "config.yml");
    }

    @Override
    protected void initDataFromFile() {

        commandName = getString("commandName", "customwings");
        commandAliases = getStringList("commandAliases");

        updateDataFromFile();
    }

    @Override
    protected void updateDataFromFile() {

        wingViewDistance = getInt(ConfigSetting.WING_VIEW_DISTANCE.path);
        wingMaxPitch = getInt(ConfigSetting.WING_MAX_PITCH.path);
        invisibilityPotionHidesWing = getBoolean(ConfigSetting.INVISIBILITY_POTION_HIDES_WING.path, true);

        wingSelectMenuName = getColorString(ConfigSetting.WING_SELECT_MENU_NAME.path);
        wingSelectMenuSize = getInt(ConfigSetting.WING_SELECT_MENU_SIZE.path);
        wingSelectMenuPages = getInt(ConfigSetting.WING_SELECT_MENU_PAGES.path, 1);

        removeWingItemName = getColorString(ConfigSetting.REMOVE_WING_ITEM_NAME.path);
        removeWingItemMaterial = getMaterial(ConfigSetting.REMOVE_WING_ITEM_MATERIAL.path);
        removeWingSlot = getInt(ConfigSetting.REMOVE_WING_ITEM_SLOT.path);

        hideWingsToggleONItemName = getColorString(ConfigSetting.HIDE_WINGS_TOGGLE_ON_ITEM_NAME.path);
        hideWingsToggleONItemMaterial = getMaterial(ConfigSetting.HIDE_WINGS_TOGGLE_ON_ITEM_MATERIAL.path);
        hideWingsToggleOFFItemName = getColorString(ConfigSetting.HIDE_WINGS_TOGGLE_OFF_ITEM_NAME.path);
        hideWingsToggleOFFItemMaterial = getMaterial(ConfigSetting.HIDE_WINGS_TOGGLE_OFF_ITEM_MATERIAL.path);
        hideWingsToggleSlot = getInt(ConfigSetting.HIDE_WINGS_TOGGLE_SLOT.path);

        navigationNextItemName = getColorString(ConfigSetting.NAGIVATION_ITEM_NEXT_NAME.path, "&9Next Page");
        navigationNextItemMaterial = getMaterial(ConfigSetting.NAGIVATION_ITEM_NEXT_MATERIAL.path, Material.FEATHER);
        navigationNextSlot = getInt(ConfigSetting.NAGIVATION_ITEM_NEXT_SLOT.path, 50);

        navigationPreviousItemName = getColorString(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_NAME.path, "&9Previous Page");
        navigationPreviousItemMaterial = getMaterial(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_MATERIAL.path, Material.FEATHER);
        navigationPreviousSlot = getInt(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_SLOT.path, 48);

        filterItemEnable = getBoolean(ConfigSetting.FILTER_ITEM_ENABLE.path, true);
        filterSlot = getInt(ConfigSetting.FILTER_ITEM_SLOT.path, 45);

        filterNoneItemName = getColorString(ConfigSetting.FILTER_ITEM_NO_FILTER_NAME.path, "&aNo filter");
        filterNoneItemMaterial = getMaterial(ConfigSetting.FILTER_ITEM_NO_FILTER_MATERIAL.path, Material.BARRIER);
        filterNoneItemLore = getColorStringList(ConfigSetting.FILTER_ITEM_NO_FILTER_LORE.path);

        filterOwnedItemName = getColorString(ConfigSetting.FILTER_ITEM_OWNED_WINGS_NAME.path, "&aOwned wings");
        filterOwnedItemMaterial = getMaterial(ConfigSetting.FILTER_ITEM_OWNED_WINGS_MATERIAL.path, Material.LIME_WOOL);
        filterOwnedItemLore = getColorStringList(ConfigSetting.FILTER_ITEM_OWNED_WINGS_LORE.path);

        filterUnownedItemName = getColorString(ConfigSetting.FILTER_ITEM_UNOWNED_WINGS_NAME.path, "&cUnowned wings");
        filterUnownedItemMaterial = getMaterial(ConfigSetting.FILTER_ITEM_UNOWNED_WINGS_MATERIAL.path, Material.RED_WOOL);
        filterUnownedItemLore = getColorStringList(ConfigSetting.FILTER_ITEM_UNOWNED_WINGS_LORE.path);

        // Make sure the removeWingSlot is always inside the menu
        if (removeWingSlot >= wingSelectMenuSize) {
            removeWingSlot = 8;
        }

    }

    public String commandName() {
        return commandName;
    }

    public List<String> commandAliases() {
        return commandAliases;
    }

    public int getWingViewDistance() {
        return wingViewDistance;
    }

    public double getWingMaxPitch() {
        return wingMaxPitch;
    }

    public boolean getInvisibilityPotionHidesWing() {
        return invisibilityPotionHidesWing;
    }

    public String getWingSelectMenuName() {
        return wingSelectMenuName;
    }

    public int getWingSelectMenuSize() {
        return wingSelectMenuSize;
    }

    public int getWingSelectMenuPages() {
        return wingSelectMenuPages;
    }

    public String getRemoveWingItemName() {
        return removeWingItemName;
    }

    public Material getRemoveWingItemMaterial() {
        return removeWingItemMaterial;
    }

    public int getRemoveWingSlot() {
        return removeWingSlot;
    }

    public String getHideWingsToggleONItemName() {
        return hideWingsToggleONItemName;
    }

    public Material getHideWingsToggleONItemMaterial() {
        return hideWingsToggleONItemMaterial;
    }

    public String getHideWingsToggleOFFItemName() {
        return hideWingsToggleOFFItemName;
    }

    public Material getHideWingsToggleOFFItemMaterial() {
        return hideWingsToggleOFFItemMaterial;
    }

    public int getHideWingsToggleSlot() {
        return hideWingsToggleSlot;
    }

    public String getNavigationNextItemName() {
        return navigationNextItemName;
    }

    public Material getNavigationNextItemMaterial() {
        return navigationNextItemMaterial;
    }

    public int getNavigationNextSlot() {
        return navigationNextSlot;
    }

    public String getNavigationPreviousItemName() {
        return navigationPreviousItemName;
    }

    public Material getNavigationPreviousItemMaterial() {
        return navigationPreviousItemMaterial;
    }

    public int getNavigationPreviousSlot() {
        return navigationPreviousSlot;
    }

    public boolean getFilterItemEnable() {
        return filterItemEnable;
    }

    public int getFilterSlot() {
        return filterSlot;
    }

    public String getFilterNoneItemName() {
        return filterNoneItemName;
    }

    public Material getFilterNoneItemMaterial() {
        return filterNoneItemMaterial;
    }

    public List<String> getFilterNoneItemLore() {
        return filterNoneItemLore;
    }

    public String getFilterOwnedItemName() {
        return filterOwnedItemName;
    }

    public Material getFilterOwnedItemMaterial() {
        return filterOwnedItemMaterial;
    }

    public List<String> getFilterOwnedItemLore() {
        return filterOwnedItemLore;
    }

    public String getFilterUnownedItemName() {
        return filterUnownedItemName;
    }

    public Material getFilterUnownedItemMaterial() {
        return filterUnownedItemMaterial;
    }

    public List<String> getFilterUnownedItemLore() {
        return filterUnownedItemLore;
    }

    public Material getMaterial(String path) {
        return getMaterial(path, Material.DIRT);
    }

    public Material getMaterial(String path, Material def) {

        Material material = def;

        try {
            material = Material.valueOf(getString(path));
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to get Material at: " + path);
            e.printStackTrace();
        }

        return material;

    }

}
