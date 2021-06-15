package tigeax.customwings.configuration;

import java.util.List;

import org.bukkit.Material;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.settings.ConfigSetting;
import tigeax.customwings.util.Util;
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

    public Config(CustomWings plugin) {
        super(plugin, "config.yml");
    }

    @Override
    protected void initDataFromFile() {

        commandName = getString("commandName");
        commandAliases = getStringList("commandAliases");

        updateDataFromFile();
    }

    @Override
    protected void updateDataFromFile() {

        wingViewDistance = getInt(ConfigSetting.WING_VIEW_DISTANCE.path);
        wingMaxPitch = getInt(ConfigSetting.WING_MAX_PITCH.path);
        invisibilityPotionHidesWing = getBoolean(ConfigSetting.INVISIBILITY_POTION_HIDES_WING.path);

        wingSelectMenuName = getColorString(ConfigSetting.WING_SELECT_MENU_NAME.path);
        wingSelectMenuSize = getInt(ConfigSetting.WING_SELECT_MENU_SIZE.path);
        wingSelectMenuPages = getInt(ConfigSetting.WING_SELECT_MENU_PAGES.path);

        removeWingItemName = getColorString(ConfigSetting.REMOVE_WING_ITEM_NAME.path);
        removeWingItemMaterial = getMaterial(ConfigSetting.REMOVE_WING_ITEM_MATERIAL.path);
        removeWingSlot = getInt(ConfigSetting.REMOVE_WING_ITEM_SLOT.path);

        hideWingsToggleONItemName = getColorString(ConfigSetting.HIDE_WINGS_TOGGLE_ON_ITEM_NAME.path);
        hideWingsToggleONItemMaterial = getMaterial(ConfigSetting.HIDE_WINGS_TOGGLE_ON_ITEM_MATERIAL.path);
        hideWingsToggleOFFItemName = getColorString(ConfigSetting.HIDE_WINGS_TOGGLE_OFF_ITEM_NAME.path);
        hideWingsToggleOFFItemMaterial = getMaterial(ConfigSetting.HIDE_WINGS_TOGGLE_OFF_ITEM_MATERIAL.path);
        hideWingsToggleSlot = getInt(ConfigSetting.HIDE_WINGS_TOGGLE_SLOT.path);

        navigationNextItemName = getColorString(ConfigSetting.NAGIVATION_ITEM_NEXT_NAME.path);
        navigationNextItemMaterial = getMaterial(ConfigSetting.NAGIVATION_ITEM_NEXT_MATERIAL.path);
        navigationNextSlot = getInt(ConfigSetting.NAGIVATION_ITEM_NEXT_SLOT.path);

        navigationPreviousItemName = getColorString(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_NAME.path);
        navigationPreviousItemMaterial = getMaterial(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_MATERIAL.path);
        navigationPreviousSlot = getInt(ConfigSetting.NAGIVATION_ITEM_PREVIOUS_SLOT.path);

        filterItemEnable = getBoolean(ConfigSetting.FILTER_ITEM_ENABLE.path);
        filterSlot = getInt(ConfigSetting.FILTER_ITEM_SLOT.path);

        filterNoneItemName = getColorString(ConfigSetting.FILTER_ITEM_NO_FILTER_NAME.path);
        filterNoneItemMaterial = getMaterial(ConfigSetting.FILTER_ITEM_NO_FILTER_MATERIAL.path);

        filterOwnedItemName = getColorString(ConfigSetting.FILTER_ITEM_OWNED_WINGS_NAME.path);
        filterOwnedItemMaterial = getMaterial(ConfigSetting.FILTER_ITEM_OWNED_WINGS_MATERIAL.path);

        filterUnownedItemName = getColorString(ConfigSetting.FILTER_ITEM_UNOWNED_WINGS_NAME.path);
        filterUnownedItemMaterial = getMaterial(ConfigSetting.FILTER_ITEM_UNOWNED_WINGS_MATERIAL.path);

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

        Material material = null;

        try {
            material = Material.valueOf(getString(path));
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to get Material at: " + path);
            e.printStackTrace();
        }

        return material;

    }

    public String getColorString(String path) {
        String string = getString(path);
        string = Util.parseChatColors(string);
        return string;
    }

    public List<String> getColorLore(String path) {
        List<String> stringList = getStringList(path);
        stringList = Util.parseLoreChatColor(stringList);
        return stringList;
    }

}
