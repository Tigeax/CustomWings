package tigeax.customwings.menus.editor.settingmenus;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.menus.editor.settingmenus.items.MainSettingsMenuItem;
import tigeax.customwings.menus.editor.settingmenus.items.WingSettingsMenuItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

public class EditorMenu extends ItemMenu {

    private CustomWings plugin;
    private MainSettingsMenu mainSettingsMenu;

    public EditorMenu(CustomWings plugin) {
        super("&cWings Editor", Rows.fit(plugin.getConfig().getWingSelectMenuSize()));
        this.plugin = plugin;

        mainSettingsMenu = new MainSettingsMenu();
        mainSettingsMenu.setParent(this);

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

            WingSettingsMenu wingSettingsMenu = new WingSettingsMenu(wing);
            wingSettingsMenu.setParent(this);

            setItem(wingConfig.getGuiSlot(), new WingSettingsMenuItem(wingSettingsMenu, wing));
        }

        // Main setting item
        setItem(plugin.getConfig().getRemoveWingSlot(), new MainSettingsMenuItem(mainSettingsMenu));
    }
    
}
