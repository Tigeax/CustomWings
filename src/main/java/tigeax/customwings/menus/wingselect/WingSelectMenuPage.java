package tigeax.customwings.menus.wingselect;

import org.bukkit.entity.Player;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.menus.items.NextPageItem;
import tigeax.customwings.menus.items.PreviousPageItem;
import tigeax.customwings.menus.wingselect.items.HideWingsToggleItem;
import tigeax.customwings.menus.wingselect.items.WingRemoveItem;
import tigeax.customwings.menus.wingselect.items.WingSelectItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

public class WingSelectMenuPage extends ItemMenu {

    CustomWings plugin;
    int page;

    int totalPages;

    public WingSelectMenuPage(CustomWings plugin, int page) {
        super(plugin.getConfig().getWingSelectMenuName(), Rows.fit(plugin.getConfig().getWingSelectMenuSize()));

        this.plugin = plugin;
        this.page = page;
        this.totalPages = plugin.getConfig().getWingSelectMenuPages();

        // Wing items
        for (Wing wing : plugin.getWings()) {
            WingConfig wingConfig = wing.getConfig();

            if (wingConfig.isHiddenInGUI()) {
                continue;
            }

            if (wingConfig.getGuiPage() != page) {
                continue;
            }

            setItem(wingConfig.getGuiSlot(), new WingSelectItem(plugin, wing));

        }

        // Remove wing item
        setItem(plugin.getConfig().getRemoveWingSlot(), new WingRemoveItem(plugin));

        // Hide other players wing item
        setItem(plugin.getConfig().getHideWingsToggleSlot(), new HideWingsToggleItem(plugin));

        
        if (page > 1) {
			setItem(plugin.getConfig().getNavigationPreviousSlot(), new PreviousPageItem());
		}

		if (page < totalPages) {
			setItem(plugin.getConfig().getNavigationNextSlot(), new NextPageItem());
		}
        

    }

    @Override
	public void openPreviousPage(Player player) {
		if (page > 1) {
			new WingSelectMenuPage(plugin, page - 1).open(player);
		}
	}

	@Override
	public void openNextPage(Player player) {
		if (page < totalPages) {
			new WingSelectMenuPage(plugin, page + 1).open(player);
		}
	}
    
}
