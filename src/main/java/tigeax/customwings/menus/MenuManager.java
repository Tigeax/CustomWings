package tigeax.customwings.menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.menus.editor.settingmenus.EditorMenuPage;
import tigeax.customwings.menus.wingselect.WingSelectMenuPage;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.menu.ItemMenu;

public class MenuManager {

    private CustomWings plugin;

    public MenuManager(CustomWings plugin) {
        this.plugin = plugin;
    }

    public void openWingSelectMenu(Player player) {
        new WingSelectMenuPage(plugin, 1).open(player);
    }

    public void openEditorMenu(Player player) {

        CWPlayer cwPlayer = plugin.getCWPlayer(player);
        ItemMenu itemMenu = cwPlayer.getLastEditorMenu();

        if (itemMenu == null) {
            new EditorMenuPage(plugin, 1).open(player);
        } else {
            itemMenu.open(player);
        }
    }

    public static List<String> parseLoreForItem(String loreString) {
        List<String> uncoloredLore = Arrays.asList(loreString.split(", "));
		List<String> lore = new ArrayList<>();

		for (String line : uncoloredLore) {
			lore.add(Util.parseChatColors("&f" + line));
		}

		return lore;
    }

}
