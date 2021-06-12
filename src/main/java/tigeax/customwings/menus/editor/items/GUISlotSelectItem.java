package tigeax.customwings.menus.editor.items;

import org.bukkit.Material;

import tigeax.customwings.configuration.settings.SettingInterface;
import tigeax.customwings.configuration.settings.WingSetting;
import tigeax.customwings.menus.editor.GuiSlotSelectMenu;
import tigeax.customwings.menus.editor.WingSettingsMenu;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuItem;

public class GUISlotSelectItem extends MenuItem {


    public GUISlotSelectItem() {
        setMaterial(Material.GLASS_PANE);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        ItemMenu itemMenu = event.getItemMenu();

        if (!(itemMenu instanceof GuiSlotSelectMenu)) {
            event.setWillClose(true);
        }

        GuiSlotSelectMenu menu = (GuiSlotSelectMenu) itemMenu;

        SettingInterface setting = menu.getSetting();

        if (setting instanceof WingSetting) {

            WingSetting wingSetting = (WingSetting) setting;

            WingSettingsMenu wingSettingMenu = (WingSettingsMenu) itemMenu.getParent();

            wingSetting.setValue(wingSettingMenu.getWing().getConfig(), event.getClickedSlot());

        }
        
        event.setWillGoBack(true);
    }
}
