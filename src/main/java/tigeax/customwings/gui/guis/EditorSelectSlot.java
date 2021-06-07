package tigeax.customwings.gui.guis;

import tigeax.customwings.editor.EditorConfigManager;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class EditorSelectSlot {
	
	CustomWings plugin;
	Settings settings;
	EditorConfigManager editorConfigManager;

	public EditorSelectSlot() {
		plugin = CustomWings.getInstance();
		settings = plugin.getSettings();
		editorConfigManager = plugin.getEditorConfigManager();
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
