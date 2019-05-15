package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tigeax.customwings.editor.EditorConfigManager;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.main.CWPlayer;
import tigeax.customwings.main.CustomWings;
import tigeax.customwings.main.Settings;

public class EditorSelectDouble {

	Settings settings;
	EditorConfigManager editorConfigManager;

	public EditorSelectDouble() {
		settings = CustomWings.getSettings();
		editorConfigManager = CustomWings.getEditorConfigManager();
	}

	public void open(CWPlayer cwPlayer, Double currentValue) {

		String guiName = settings.getEditorGUIName() + " - Set Double";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		gui.setItem(19, CWGUIManager.getItem(Material.RED_STAINED_GLASS_PANE, "&c-1"));
		gui.setItem(20, CWGUIManager.getItem(Material.RED_STAINED_GLASS_PANE, "&c-0.1"));
		gui.setItem(21, CWGUIManager.getItem(Material.RED_STAINED_GLASS_PANE, "&c-0.01"));

		gui.setItem(22, CWGUIManager.getItem(Material.DIAMOND, "&f" + currentValue));

		gui.setItem(23, CWGUIManager.getItem(Material.GREEN_STAINED_GLASS_PANE, "&2+0.01"));
		gui.setItem(24, CWGUIManager.getItem(Material.GREEN_STAINED_GLASS_PANE, "&2+0.1"));
		gui.setItem(25, CWGUIManager.getItem(Material.GREEN_STAINED_GLASS_PANE, "&2+1"));

		gui.setItem(48, CWGUIManager.getPlayerHeadItem("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6", "&4Cancel"));
		gui.setItem(50, CWGUIManager.getPlayerHeadItem("6527ebae9f153154a7ed49c88c02b5a9a9ca7cb1618d9914a3d9df8ccb3c84", "&2Confirm"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, Inventory inv, String itemName, int slot) {

		if (slot == 22) { return; }

		Object waitingSettingInfo = cwPlayer.getWaitingSettingInfo();

		if (itemName.equals("Cancel")) {
			cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
			return;
		}

		ItemStack valueItem = inv.getItem(22);
		Double currentNumber = Double.parseDouble(ChatColor.stripColor(valueItem.getItemMeta().getDisplayName()));

		if (itemName.equals("Confirm")) {
			editorConfigManager.setSetting(cwPlayer.getWaitingSetting(), currentNumber, waitingSettingInfo);
			cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
			return;
		}

		Double addNumber = Double.parseDouble(itemName);
		Double newValue = Math.round((currentNumber + addNumber) * 100) / 100.0;

		ItemMeta itemMeta = valueItem.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + newValue));
		valueItem.setItemMeta(itemMeta);
	}
}
