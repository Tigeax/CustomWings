package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import tigeax.customwings.editor.EditorConfigManager;
import tigeax.customwings.editor.SettingType;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.main.CWPlayer;
import tigeax.customwings.main.CustomWings;
import tigeax.customwings.main.Settings;

public class EditorSelectMainGUISize {

	Settings settings;
	EditorConfigManager editorConfigManager;

	public EditorSelectMainGUISize() {
		settings = CustomWings.getSettings();
		editorConfigManager = CustomWings.getEditorConfigManager();
	}

	public void open(CWPlayer cwPlayer) {

		String guiName = settings.getEditorGUIName() + " - Main GUI Size";

		Inventory gui = Bukkit.createInventory(null, 9, guiName);

		gui.setItem(0, CWGUIManager.getItem(Material.CHEST, "&f9"));
		gui.setItem(1, CWGUIManager.getItem(Material.CHEST, "&f18"));
		gui.setItem(2, CWGUIManager.getItem(Material.CHEST, "&f27"));
		gui.setItem(3, CWGUIManager.getItem(Material.CHEST, "&f36"));
		gui.setItem(4, CWGUIManager.getItem(Material.CHEST, "&f45"));
		gui.setItem(5, CWGUIManager.getItem(Material.CHEST, "&f54"));
		gui.setItem(8, CWGUIManager.getItem(Material.RED_WOOL, "&4Cancel"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName) {

		switch (itemName) {
			case "Cancel":
				cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
				return;
			case "9":
				editorConfigManager.setSetting(SettingType.MAINGUISIZE, 9);
				break;
			case "18":
				editorConfigManager.setSetting(SettingType.MAINGUISIZE, 18);
				break;
			case "27":
				editorConfigManager.setSetting(SettingType.MAINGUISIZE, 27);
				break;
			case "36":
				editorConfigManager.setSetting(SettingType.MAINGUISIZE, 36);
				break;
			case "45":
				editorConfigManager.setSetting(SettingType.MAINGUISIZE, 45);
				break;
			case "54":
				editorConfigManager.setSetting(SettingType.MAINGUISIZE, 54);
				break;
		}

		cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
	}
	
}
