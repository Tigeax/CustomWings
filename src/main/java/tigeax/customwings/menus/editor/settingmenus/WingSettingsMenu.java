package tigeax.customwings.menus.editor.settingmenus;

import org.bukkit.Material;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.configuration.settings.WingSetting;
import tigeax.customwings.menus.editor.items.GoBackItem;
import tigeax.customwings.menus.editor.selectvaluemenus.items.SettingItem;
import tigeax.customwings.menus.editor.settingmenus.items.WingParticleListMenuItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

public class WingSettingsMenu extends ItemMenu {

    public WingSettingsMenu(Wing wing) {
        super(wing.getConfig().getGuiItemName(), Rows.SIX);

        setItem(12, new SettingItem(new Setting(WingSetting.SHOW_WHEN_MOVING, wing),    "&3Show when moving",   Material.DIAMOND_LEGGINGS));

        setItem(20, new SettingItem(new Setting(WingSetting.MENU_ITEM_NAME, wing),      "&3Menu item name",    Material.CHEST));

        setItem(21, new SettingItem(new Setting(WingSetting.MENU_ITEM_MATERIAL, wing),  "&3Menu item material"));

        setItem(22, new SettingItem(new Setting(WingSetting.MENU_ITEM_SLOT, wing),      "&3Menu item slot",     Material.CHEST));

        setItem(27, new SettingItem(new Setting(WingSetting.WING_START_VERTICAL, wing), "&3Start vertical",     Material.END_ROD));

        setItem(32, new SettingItem(new Setting(WingSetting.WING_FLAP_ANIMATION, wing), "&3Wing Animation",     Material.DIAMOND));


        setItem(40, new WingParticleListMenuItem(new WingParticleListMenu(this, wing)));

        setItem(53, new GoBackItem());
    }
    
}
