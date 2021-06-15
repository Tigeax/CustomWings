package tigeax.customwings.menus.editor.selectvaluemenus.items;

import org.bukkit.Material;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.editor.selectvaluemenus.MenuSizeSelectMenu;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.ItemMenu.Rows;
import tigeax.customwings.util.menu.MenuItem;

public class MenuSizeSelectItem extends MenuItem {

    Rows rows;

    public MenuSizeSelectItem(Rows rows) {
        this.rows = rows;

        setDisplayName("&fRows: " + rows.toString().toLowerCase());
        setMaterial(Material.CHEST);
        setAmount(rows.getSize());
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        ItemMenu itemMenu = event.getItemMenu();

        if (!(itemMenu instanceof MenuSizeSelectMenu)) {
            event.setWillClose(true);
            return;
        }

        MenuSizeSelectMenu menu = (MenuSizeSelectMenu) itemMenu;

        Setting setting = menu.getSetting();

        setting.setValue(rows.getSize());
        
        event.setWillGoBack(true);
    }
}
