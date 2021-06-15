package tigeax.customwings.configuration.settings;

import org.bukkit.Material;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.wing.Wing;

public enum WingSetting implements SettingInterface {

    SHOW_WHEN_MOVING("showWhenMoving", SettingType.BOOLEAN),
    WHITELISTED_WORLDS("whitelistedWorlds", SettingType.STRINGLIST),

    ECONOMY_PRICE_TYPE("economy.priceType", SettingType.STRING), 
    ECONOMY_PRICE("economy.price", SettingType.INT),

    MENU_ITEM_HIDE_IN_MENU("menuItem.hideInMenu", SettingType.BOOLEAN),

    MENU_ITEM_NAME("menuItem.name", SettingType.STRING),
    MENU_ITEM_MATERIAL("menuItem.material", SettingType.MATERIAL),
    MENU_ITEM_SLOT("menuItem.slot", SettingType.GUISLOT),
    MENU_ITEM_PAGE("menuItem.page", SettingType.INT),

    MENU_ITEM_LORE_WHEN_EQUIPPED("menuItem.loreWhenEquipped", SettingType.STRINGLIST),
    MENU_ITEM_LORE_WHEN_UNEQUIPPED("menuItem.loreWhenUnequipped", SettingType.STRINGLIST),
    MENU_ITEM_LORE_WHEN_NO_PERMISSION("menuItem.loreWhenNoPermission", SettingType.STRINGLIST),
    MENU_ITEM_LORE_WHEN_CAN_BUY("menuItem.loreWhenCanBuy", SettingType.STRINGLIST),

    WING_START_VERTICAL("wing.startVertical", SettingType.DOUBLE),
    WING_START_HORIZONTAL("wing.startHorizontal", SettingType.DOUBLE),
    WING_DISTANCE_BETWEEN_PARTICLES("wing.distanceBetweenParticles", SettingType.DOUBLE),
    WING_TIMER("wing.wingTimer", SettingType.INT),

    WING_FLAP_ANIMATION("wing.flapAnimation", SettingType.BOOLEAN),
    WING_WING_FLAP_SPEED("wing.flapSpeed", SettingType.INT), 
    WING_START_OFFSET("wing.startOffset", SettingType.INT),
    WING_STOP_OFFSET("wing.stopOffset", SettingType.INT);

    public String path;
    private SettingType settingType;

    WingSetting(final String path, final SettingType settingType) {
        this.path = path;
        this.settingType = settingType;
    }

    public SettingType getSettingType() {
        return settingType;
    }

    public void setValue(Object value, Wing wing) {
        if (value instanceof Material) {
            value = value.toString();
        }

        wing.getConfig().set(this.path, value);
        wing.getConfig().save();
        CustomWings.getInstance().reload();
    }

    public Object getCurrentValue(Wing wing) {

        WingConfig wingConfig = wing.getConfig();

        switch (this) {
            case SHOW_WHEN_MOVING:
                return wingConfig.getShowWhenMoving();
            case WHITELISTED_WORLDS:
                return wingConfig.getWhitelistedWorlds();
            case ECONOMY_PRICE_TYPE:
                return wingConfig.getPriceType();
            case ECONOMY_PRICE:
                return wingConfig.getPrice();
            case MENU_ITEM_HIDE_IN_MENU:
                return wingConfig.isHiddenInGUI();
            case MENU_ITEM_NAME:
                return wingConfig.getGuiItemName();
            case MENU_ITEM_MATERIAL:
                return wingConfig.getGuiItemMaterial();
            case MENU_ITEM_SLOT:
                return wingConfig.getGuiSlot();
            case MENU_ITEM_PAGE:
                return wingConfig.getGuiPage();
            case MENU_ITEM_LORE_WHEN_EQUIPPED:
                return wingConfig.getLoreWhenEquipped();
            case MENU_ITEM_LORE_WHEN_UNEQUIPPED:
                return wingConfig.getLoreWhenUnequipped();
            case MENU_ITEM_LORE_WHEN_NO_PERMISSION:
                return wingConfig.getLoreWhenNoPermission();
            case MENU_ITEM_LORE_WHEN_CAN_BUY:
                return wingConfig.getloreWhenCanBuy();
            case WING_START_VERTICAL:
                return wingConfig.getStartVertical();
            case WING_START_HORIZONTAL:
                return wingConfig.getStartHorizontal();
            case WING_DISTANCE_BETWEEN_PARTICLES:
                return wingConfig.getDistanceBetweenParticles();
            case WING_TIMER:
                return wingConfig.getWingTimer();
            case WING_FLAP_ANIMATION:
                return wingConfig.getWingAnimation();
            case WING_WING_FLAP_SPEED:
                return wingConfig.getWingFlapSpeed();
            case WING_START_OFFSET:
                return wingConfig.getStartOffset();
            case WING_STOP_OFFSET:
                return wingConfig.getStopOffset();
        }
        return null;
    }

}
