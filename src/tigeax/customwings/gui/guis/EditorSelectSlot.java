package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import tigeax.customwings.editor.EditorConfigManager;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.main.CWPlayer;
import tigeax.customwings.main.CustomWings;
import tigeax.customwings.main.Settings;

public class EditorSelectSlot {
	
	Settings settings;
	EditorConfigManager editorConfigManager;

	public EditorSelectSlot() {
		settings = CustomWings.getSettings();
		editorConfigManager = CustomWings.getEditorConfigManager();
	}

	public void open(CWPlayer cwPlayer) {

		String guiName = settings.getEditorGUIName() + " - Select Slot";

		Inventory gui = Bukkit.createInventory(null, 63, guiName);

		for (int slot = 0; slot < 54; slot++) {
			gui.setItem(slot, CWGUIManager.getItem(Material.WHITE_STAINED_GLASS_PANE, "" + slot));
		}

		gui.setItem(62, CWGUIManager.getPlayerHeadItem("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6", "&4Cancel"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName) {

		if (!itemName.equals("Cancel")) {
			editorConfigManager.setSetting(cwPlayer.getWaitingSetting(), itemName, cwPlayer.getWaitingSettingInfo());
		}

		cwPlayer.setWaitingSetting(null);
		cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
	}
}
