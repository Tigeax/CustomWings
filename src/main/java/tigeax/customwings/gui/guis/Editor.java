package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.Configuration;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.wings.Wing;

public class Editor {

	CustomWings plugin;
	private Configuration config;

	public Editor() {
		plugin = CustomWings.getInstance();
		config = plugin.getConfig();
	}

	public void open(CWPlayer cwPlayer) {

		String guiName = config.getEditorGUIName();
		int guiSize = config.getMainGUISize();

		Inventory gui = Bukkit.createInventory(null, guiSize, guiName);

		for (Wing wing : plugin.getWings()) {

			ItemStack wingItem = wing.getConfig().getGuiItem();
			int slot = wing.getConfig().getGuiSlot();

			if (guiSize < slot) continue;

			gui.setItem(slot, wingItem);
		}

		ItemStack mainconfigItem = config.getEditorMainSettingsItem().clone();
		int mainconfigSlot = config.getEditorMainSettingsSlot();
		
		if (guiSize < mainconfigSlot)
			gui.setItem(0, mainconfigItem);
		else
			gui.setItem(mainconfigSlot, mainconfigItem);

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName, Integer clickedSlot) {

		// Open the Main config GUI
		if (itemName.equals("Main Settings")) {
			cwPlayer.openCWGUI(CWGUIType.EDITORMAINSETTINGS);
			return;
		}

		// Else open the Wing config GUI
		Wing wing = plugin.getWingByGUISlot(clickedSlot);
		cwPlayer.openCWGUI(CWGUIType.EDITORWINGSETTINGS, wing);
	}
}
