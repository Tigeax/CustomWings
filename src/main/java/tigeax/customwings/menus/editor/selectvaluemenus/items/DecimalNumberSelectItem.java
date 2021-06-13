package tigeax.customwings.menus.editor.selectvaluemenus.items;

import org.bukkit.Material;

import net.md_5.bungee.api.ChatColor;
import tigeax.customwings.menus.editor.selectvaluemenus.DecimalNumberSelectMenu;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuItem;

public class DecimalNumberSelectItem extends MenuItem {


    public DecimalNumberSelectItem(double amount) {

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

        if (!(itemMenu instanceof DecimalNumberSelectMenu)) {
            event.setWillClose(true);
            return;
        }

        DecimalNumberSelectMenu menu = (DecimalNumberSelectMenu) itemMenu;

        Double newValue = ((Double) menu.getSetting().getCurrentValue()) + Double.parseDouble(ChatColor.stripColor(getDisplayName()));
        newValue = Math.round((newValue) * 100) / 100.0;

        menu.getSetting().setValue(newValue);
        
        event.setWillUpdate(true);
    }
}
