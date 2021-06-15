package tigeax.customwings.menus.editor.selectvaluemenus.items;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import tigeax.customwings.menus.editor.selectvaluemenus.NumberSelectMenu;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuItem;

public class NumberSelectItem extends MenuItem {


    public NumberSelectItem(int amount) {

        setDisplayName("&f" + amount);

        if (amount > 0) {
            setMaterial(Material.GREEN_STAINED_GLASS_PANE);
        } else {
            setMaterial(Material.RED_STAINED_GLASS_PANE);
        }
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        ItemMenu itemMenu = event.getItemMenu();

        if (!(itemMenu instanceof NumberSelectMenu)) {
            event.setWillClose(true);
            return;
        }

        NumberSelectMenu menu = (NumberSelectMenu) itemMenu;

        int newValue = ((int) menu.getSetting().getCurrentValue()) + Integer.parseInt(ChatColor.stripColor(getDisplayName()));

        menu.getSetting().setValue(newValue);
        
        event.setWillUpdate(true);
    }
}
