package tigeax.customwings.menus.editor.selectvaluemenus.items;

import org.bukkit.Material;

import tigeax.customwings.menus.editor.selectvaluemenus.MenuSlotSelectMenu;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuItem;

public class MenuSlotSelectItem extends MenuItem {


    public MenuSlotSelectItem() {
        setMaterial(Material.GLASS_PANE);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        ItemMenu itemMenu = event.getItemMenu();

        if (!(itemMenu instanceof MenuSlotSelectMenu)) {
            event.setWillClose(true);
            return;
        }

        MenuSlotSelectMenu menu = (MenuSlotSelectMenu) itemMenu;

        menu.getSetting().setValue(event.getClickedSlot());
        
        event.setWillGoBack(true);
    }
}
