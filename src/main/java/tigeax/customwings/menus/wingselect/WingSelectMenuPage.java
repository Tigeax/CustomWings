package tigeax.customwings.menus.wingselect;

import org.bukkit.entity.Player;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.Config;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.menus.items.NextPageItem;
import tigeax.customwings.menus.items.PreviousPageItem;
import tigeax.customwings.menus.wingselect.items.FilterCycleItem;
import tigeax.customwings.menus.wingselect.items.HideOtherWingsToggleItem;
import tigeax.customwings.menus.wingselect.items.WingRemoveItem;
import tigeax.customwings.menus.wingselect.items.WingSelectItem;
import tigeax.customwings.menus.wingselect.items.WingShowToggleItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

public class WingSelectMenuPage extends ItemMenu {

    private final CustomWings plugin;
    private int page;

    int totalPages;

    public WingSelectMenuPage(CustomWings plugin, int page) {
        super(plugin.getConfig().getWingSelectMenuName(), Rows.fit(plugin.getConfig().getWingSelectMenuSize()));

        this.plugin = plugin;
        this.page = page;
        Config config = plugin.getConfig();
        this.totalPages = config.getWingSelectMenuPages();

        // Wing items
        for (Wing wing : plugin.getWings().values()) {
            WingConfig wingConfig = wing.getConfig();

            if (wingConfig.isHiddenInGUI()) continue;

            if (wingConfig.getGuiPage() != page) continue;

            setItem(wingConfig.getGuiSlot(), new WingSelectItem(plugin, wing));

        }

        // Wing filter item
        setItem(config.getFilterSlot(), new FilterCycleItem(plugin));

        // Remove wing item
        setItem(config.getRemoveWingSlot(), new WingRemoveItem(plugin));

        // Hide other players wing item
        setItem(config.getHideOtherWingsToggleSlot(), new HideOtherWingsToggleItem(plugin));

        // Show wing item
        setItem(config.getShowWingToggleSlot(), new WingShowToggleItem(plugin));

        
        if (page > 1) {
			setItem(config.getNavigationPreviousSlot(), new PreviousPageItem());
		}

		if (page < totalPages) {
			setItem(config.getNavigationNextSlot(), new NextPageItem());
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
