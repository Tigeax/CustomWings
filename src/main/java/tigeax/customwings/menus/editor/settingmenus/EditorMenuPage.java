package tigeax.customwings.menus.editor.settingmenus;

import org.bukkit.entity.Player;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.Config;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.menus.editor.settingmenus.items.MainSettingsMenuItem;
import tigeax.customwings.menus.editor.settingmenus.items.WingSettingsMenuItem;
import tigeax.customwings.menus.items.NextPageItem;
import tigeax.customwings.menus.items.PreviousPageItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;

public class EditorMenuPage extends ItemMenu {

    private CustomWings plugin;
    private Config config;
    private int page;
    private int totalPages;
    private MainSettingsMenu mainSettingsMenu;

    public EditorMenuPage(CustomWings plugin, int page) {
        super("&cWings Editor", Rows.fit(plugin.getConfig().getWingSelectMenuSize()));

        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.page = page;
        this.totalPages = config.getWingSelectMenuPages();

        this.mainSettingsMenu = new MainSettingsMenu();
        mainSettingsMenu.setParent(this);

        setItems();
    }

    private void setItems() {

        clearItems();

        // Wing items
        for (Wing wing : plugin.getWings()) {

            WingConfig wingConfig = wing.getConfig();

            if (wingConfig.getGuiPage() != page) {
                continue;
            }

            WingSettingsMenu wingSettingsMenu = new WingSettingsMenu(wing);
            wingSettingsMenu.setParent(this);

            setItem(wingConfig.getGuiSlot(), new WingSettingsMenuItem(wingSettingsMenu, wing));
        }

        // Main setting item
        setItem(plugin.getConfig().getRemoveWingSlot(), new MainSettingsMenuItem(mainSettingsMenu));

        // Nav items
        if (page > 1) {
			setItem(config.getNavigationPreviousSlot(), new PreviousPageItem());
		}

		if (page < totalPages) {
			setItem(config.getNavigationNextSlot(), new NextPageItem());
		}
    }

    @Override
    public void update(Player player) {
        setItems();
        super.update(player);
    }

    
    @Override
	public void openPreviousPage(Player player) {
		if (page > 1) {
			new EditorMenuPage(plugin, page - 1).open(player);
		}
	}

	@Override
	public void openNextPage(Player player) {
		if (page < totalPages) {
			new EditorMenuPage(plugin, page + 1).open(player);
		}
	}
    
}
