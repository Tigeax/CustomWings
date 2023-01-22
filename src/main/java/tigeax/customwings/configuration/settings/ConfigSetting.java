package tigeax.customwings.configuration.settings;

import org.bukkit.Material;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.Config;

public enum ConfigSetting implements SettingInterface {

    WING_VIEW_DISTANCE("wingViewDistance", SettingType.INT), 
    WING_MAX_PITCH("wingMaxPitch", SettingType.INT),
    INVISIBILITY_POTION_HIDES_WING("invisibilityPotionHidesWing", SettingType.BOOLEAN),

    WING_SELECT_MENU_NAME("wingSelectMenu.name", SettingType.STRING),
    WING_SELECT_MENU_SIZE("wingSelectMenu.size", SettingType.GUISIZE),
    WING_SELECT_MENU_PAGES("wingSelectMenu.pages", SettingType.INT),

    REMOVE_WING_ITEM_NAME("wingSelectMenu.removeWingItem.name", SettingType.STRING),
    REMOVE_WING_ITEM_MATERIAL("wingSelectMenu.removeWingItem.material", SettingType.MATERIAL),
    REMOVE_WING_ITEM_SLOT("wingSelectMenu.removeWingItem.slot", SettingType.GUISLOT),

    HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_NAME("wingSelectMenu.hideWingsToggleItem.nameON", SettingType.STRING),
    HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_MATERIAL("wingSelectMenu.hideWingsToggleItem.materialON", SettingType.MATERIAL),
    HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_NAME("wingSelectMenu.hideWingsToggleItem.nameOFF", SettingType.STRING),
    HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_MATERIAL("wingSelectMenu.hideWingsToggleItem.materialOFF", SettingType.MATERIAL),
    HIDE_OTHER_WINGS_TOGGLE_SLOT("wingSelectMenu.hideWingsToggleItem.slot", SettingType.GUISLOT),

    SHOW_WING_TOGGLE_ON_ITEM_NAME("wingSelectMenu.showWingToggleItem.nameON", SettingType.STRING),
    SHOW_WING_TOGGLE_ON_ITEM_MATERIAL("wingSelectMenu.showWingToggleItem.materialON", SettingType.MATERIAL),
    SHOW_WING_TOGGLE_OFF_ITEM_NAME("wingSelectMenu.showWingToggleItem.nameOFF", SettingType.STRING),
    SHOW_WING_TOGGLE_OFF_ITEM_MATERIAL("wingSelectMenu.showWingToggleItem.materialOFF", SettingType.MATERIAL),
    SHOW_WING_TOGGLE_SLOT("wingSelectMenu.showWingToggleItem.slot", SettingType.GUISLOT),

    NAGIVATION_ITEM_NEXT_NAME("wingSelectMenu.navigationItem.next.name", SettingType.STRING),
    NAGIVATION_ITEM_NEXT_MATERIAL("wingSelectMenu.navigationItem.next.material", SettingType.MATERIAL),
    NAGIVATION_ITEM_NEXT_SLOT("wingSelectMenu.navigationItem.next.slot", SettingType.GUISLOT),
    NAGIVATION_ITEM_PREVIOUS_NAME("wingSelectMenu.navigationItem.previous.name", SettingType.STRING),
    NAGIVATION_ITEM_PREVIOUS_MATERIAL("wingSelectMenu.navigationItem.previous.material", SettingType.MATERIAL),
    NAGIVATION_ITEM_PREVIOUS_SLOT("wingSelectMenu.navigationItem.previous.slot", SettingType.GUISLOT),

    FILTER_ITEM_ENABLE("wingSelectMenu.filterItem.enable", SettingType.BOOLEAN),
    FILTER_ITEM_SLOT("wingSelectMenu.filterItem.slot", SettingType.GUISLOT),

    FILTER_ITEM_NO_FILTER_NAME("wingSelectMenu.filterItem.noFilter.name", SettingType.STRING),
    FILTER_ITEM_NO_FILTER_MATERIAL("wingSelectMenu.filterItem.noFilter.material", SettingType.MATERIAL),
    FILTER_ITEM_NO_FILTER_LORE("wingSelectMenu.filterItem.noFilter.lore", SettingType.STRINGLIST),

    FILTER_ITEM_OWNED_WINGS_NAME("wingSelectMenu.filterItem.ownedWings.name", SettingType.STRING),
    FILTER_ITEM_OWNED_WINGS_MATERIAL("wingSelectMenu.filterItem.ownedWings.material", SettingType.MATERIAL),
    FILTER_ITEM_OWNED_WINGS_LORE("wingSelectMenu.filterItem.ownedWings.lore", SettingType.STRINGLIST),

    FILTER_ITEM_UNOWNED_WINGS_NAME("wingSelectMenu.filterItem.unownedWings.name", SettingType.STRING),
    FILTER_ITEM_UNOWNED_WINGS_MATERIAL("wingSelectMenu.filterItem.unownedWings.material", SettingType.MATERIAL),
    FILTER_ITEM_UNOWNED_WINGS_LORE("wingSelectMenu.filterItem.unownedWings.lore", SettingType.STRINGLIST);

    public String path;
    private SettingType settingType;

    ConfigSetting(final String path, final SettingType settingType) {
        this.path = path;
        this.settingType = settingType;
    }

    public SettingType getSettingType() {
        return settingType;
    }

    public void setValue(Object value) {
        if (value instanceof Material) {
            value = value.toString();
        }

        Config config = CustomWings.getInstance().getConfig();
        config.set(this.path, value);
        config.save();
        CustomWings.getInstance().reload();
    }

    public Object getCurrentValue() {

        Config config = CustomWings.getInstance().getConfig();

        switch (this) {
            case WING_VIEW_DISTANCE:
                return config.getWingViewDistance();
            case WING_MAX_PITCH:
                return config.getWingMaxPitch();
            case INVISIBILITY_POTION_HIDES_WING:
                return config.getInvisibilityPotionHidesWing();
            case WING_SELECT_MENU_NAME:
                return config.getWingSelectMenuName();
            case WING_SELECT_MENU_SIZE:
                return config.getWingSelectMenuSize();
            case WING_SELECT_MENU_PAGES:
                return config.getWingSelectMenuPages();
            case REMOVE_WING_ITEM_NAME:
                return config.getRemoveWingItemName();
            case REMOVE_WING_ITEM_MATERIAL:
                return config.getRemoveWingItemMaterial();
            case REMOVE_WING_ITEM_SLOT:
                return config.getRemoveWingSlot();
            case HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_NAME:
                return config.getHideOtherWingsToggleONItemName();
            case HIDE_OTHER_WINGS_TOGGLE_ON_ITEM_MATERIAL:
                return config.getHideOtherWingsToggleONItemMaterial();
            case HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_NAME:
                return config.getHideOtherWingsToggleOFFItemName();
            case HIDE_OTHER_WINGS_TOGGLE_OFF_ITEM_MATERIAL:
                return config.getHideOtherWingsToggleOFFItemMaterial();
            case HIDE_OTHER_WINGS_TOGGLE_SLOT:
                return config.getHideOtherWingsToggleSlot();
            case SHOW_WING_TOGGLE_ON_ITEM_NAME:
                return config.getShowWingToggleONItemName();
            case SHOW_WING_TOGGLE_ON_ITEM_MATERIAL:
                return config.getShowWingToggleONItemMaterial();
            case SHOW_WING_TOGGLE_OFF_ITEM_NAME:
                return config.getShowWingToggleOFFItemName();
            case SHOW_WING_TOGGLE_OFF_ITEM_MATERIAL:
                return config.getShowWingToggleOFFItemMaterial();
            case SHOW_WING_TOGGLE_SLOT:
                return config.getShowWingToggleSlot();
            case NAGIVATION_ITEM_NEXT_NAME:
                return config.getNavigationNextItemName();
            case NAGIVATION_ITEM_NEXT_MATERIAL:
                return config.getNavigationNextItemMaterial();
            case NAGIVATION_ITEM_NEXT_SLOT:
                return config.getNavigationNextSlot();
            case NAGIVATION_ITEM_PREVIOUS_NAME:
                return config.getNavigationPreviousItemName();
            case NAGIVATION_ITEM_PREVIOUS_MATERIAL:
                return config.getNavigationPreviousItemMaterial();
            case NAGIVATION_ITEM_PREVIOUS_SLOT:
                return config.getNavigationPreviousSlot();
            case FILTER_ITEM_ENABLE:
                return config.getFilterItemEnable();
            case FILTER_ITEM_SLOT:
                return config.getFilterSlot();
            case FILTER_ITEM_NO_FILTER_NAME:
                return config.getFilterNoneItemName();
            case FILTER_ITEM_NO_FILTER_MATERIAL:
                return config.getFilterNoneItemMaterial();
            case FILTER_ITEM_NO_FILTER_LORE:
                return config.getFilterNoneItemLore();
            case FILTER_ITEM_OWNED_WINGS_NAME:
                return config.getFilterOwnedItemName();
            case FILTER_ITEM_OWNED_WINGS_MATERIAL:
                return config.getFilterOwnedItemMaterial();
            case FILTER_ITEM_OWNED_WINGS_LORE:
                return config.getFilterOwnedItemLore();
            case FILTER_ITEM_UNOWNED_WINGS_NAME:
                return config.getFilterUnownedItemName();
            case FILTER_ITEM_UNOWNED_WINGS_MATERIAL:
                return config.getFilterUnownedItemMaterial();
            case FILTER_ITEM_UNOWNED_WINGS_LORE:
                return config.getFilterUnownedItemLore();
        }
        return null;
    }

}