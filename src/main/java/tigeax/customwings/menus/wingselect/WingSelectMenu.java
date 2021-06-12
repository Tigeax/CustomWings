package tigeax.customwings.menus.wingselect;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.menus.wingselect.items.HideWingsToggleItem;
import tigeax.customwings.menus.wingselect.items.WingRemoveItem;
import tigeax.customwings.menus.wingselect.items.WingSelectItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

public class WingSelectMenu extends ItemMenu {

    CustomWings plugin;

    public WingSelectMenu(CustomWings plugin) {
        super(plugin.getConfig().getWingSelectMenuName(), Rows.fit(plugin.getConfig().getWingSelectMenuSize()));

        this.plugin = plugin;

        addItems();

    }

    public void reload() {

        clearItems();

        setSize(Rows.fit(plugin.getConfig().getWingSelectMenuSize()));

        addItems();
    }

    private void addItems() {

        // Wing items
        for (Wing wing : plugin.getWings()) {
            WingConfig wingConfig = wing.getConfig();

            if (wingConfig.isHiddenInGUI()) {
                continue;
            }

            setItem(wingConfig.getGuiSlot(), new WingSelectItem(plugin, wing));

        }

        // Remove wing item
        setItem(plugin.getConfig().getRemoveWingSlot(), new WingRemoveItem(plugin));

        // Hide other players wing item
        setItem(plugin.getConfig().getHideWingsToggleSlot(), new HideWingsToggleItem(plugin));

    }
    
}
