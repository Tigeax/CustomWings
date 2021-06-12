package tigeax.customwings.menus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.menus.editor.EditorMenu;
import tigeax.customwings.menus.wingselect.WingSelectMenu;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.menu.ItemMenu;

public class MenuManager {

    private CustomWings plugin;

    private WingSelectMenu wingSelectMenu;
    private EditorMenu editorMenu;

    public MenuManager(CustomWings plugin) {
        this.plugin = plugin;

        wingSelectMenu = new WingSelectMenu(plugin);
        editorMenu = new EditorMenu(plugin);
    }

    public void reload() {
        wingSelectMenu.reload();
        editorMenu.reload();
    }

    public void openWingSelectMenu(Player player) {
        wingSelectMenu.open(player);
    }

    public void openEditorMenu(Player player) {

        CWPlayer cwPlayer = plugin.getCWPlayer(player);
        ItemMenu itemMenu = cwPlayer.getLastEditorMenu();

        if (itemMenu == null) {
            editorMenu.open(player); 
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
