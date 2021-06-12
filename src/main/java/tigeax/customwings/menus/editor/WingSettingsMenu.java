package tigeax.customwings.menus.editor;

import org.bukkit.Material;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.settings.WingSetting;
import tigeax.customwings.menus.editor.items.GoBackItem;
import tigeax.customwings.menus.editor.items.WingSettingItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

public class WingSettingsMenu extends ItemMenu {

    private Wing wing;

    public WingSettingsMenu(CustomWings plugin, Wing wing) {
        super(wing.getConfig().getGuiItemName(), Rows.SIX);
        this.wing = wing;

        setItem(53, new GoBackItem());

        setItem(12, new WingSettingItem(plugin, WingSetting.SHOW_WHEN_MOVING, wing, "&3Show when moving", Material.DIAMOND_LEGGINGS));

        setItem(22, new WingSettingItem(plugin, WingSetting.MENU_ITEM_SLOT, wing, "&3GUI item slot", Material.CHEST));
    }

    public Wing getWing() {
        return wing;
    }
    
}
