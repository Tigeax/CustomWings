package tigeax.customwings.menus.editor.selectvaluemenus.items;

import org.bukkit.Material;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.editor.selectvaluemenus.ColorSelectMenu;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuItem;

public class ColorSelectItem extends MenuItem {

    int color;

    public ColorSelectItem(int color, String name, Material material) {
        this.color = color;

        setDisplayName(name);
        setMaterial(material);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        ItemMenu itemMenu = event.getItemMenu();

        if (!(itemMenu instanceof ColorSelectMenu)) {
            event.setWillClose(true);
            return;
        }

        ColorSelectMenu menu = (ColorSelectMenu) itemMenu;

        Setting setting = menu.getSetting();

        setting.setValue(color);
    }
}
