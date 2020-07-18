package main.java.tigeax.customwings.gui.guis;

import main.java.tigeax.customwings.editor.EditorConfigManager;
import main.java.tigeax.customwings.gui.CWGUIManager;
import main.java.tigeax.customwings.main.CWPlayer;
import main.java.tigeax.customwings.main.CustomWings;
import main.java.tigeax.customwings.main.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class EditorSelectSlot {
	
	Settings settings;
	EditorConfigManager editorConfigManager;

	public EditorSelectSlot() {
		settings = CustomWings.getSettings();
		editorConfigManager = CustomWings.getEditorConfigManager();
	}

	public void open(CWPlayer cwPlayer) {

		String guiName = settings.getEditorGUIName() + " - Select Slot";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		for (int slot = 0; slot < 54; slot++) {
			gui.setItem(slot, CWGUIManager.getItem(Material.WHITE_STAINED_GLASS_PANE, "" + slot));
		}

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName) {

		editorConfigManager.setSetting(cwPlayer.getWaitingSetting(), itemName, cwPlayer.getWaitingSettingInfo());


	}
}
