package tigeax.customwings.gui.guis;

import tigeax.customwings.editor.EditorConfigManager;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EditorSelectInteger {

	Settings settings;
	EditorConfigManager editorConfigManager;

	public EditorSelectInteger() {
		settings = CustomWings.getSettings();
		editorConfigManager = CustomWings.getEditorConfigManager();
	}

	public void open(CWPlayer cwPlayer, Integer currentValue) {

		String guiName = settings.getEditorGUIName() + " - Set Integer";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		gui.setItem(19, CWGUIManager.getItem(Material.RED_STAINED_GLASS_PANE, "&c-10"));
		gui.setItem(20, CWGUIManager.getItem(Material.RED_STAINED_GLASS_PANE, "&c-5"));
		gui.setItem(21, CWGUIManager.getItem(Material.RED_STAINED_GLASS_PANE, "&c-1"));

		gui.setItem(22, CWGUIManager.getItem(Material.DIAMOND, "&f" + currentValue));

		gui.setItem(23, CWGUIManager.getItem(Material.GREEN_STAINED_GLASS_PANE, "&2+1"));
		gui.setItem(24, CWGUIManager.getItem(Material.GREEN_STAINED_GLASS_PANE, "&2+5"));
		gui.setItem(25, CWGUIManager.getItem(Material.GREEN_STAINED_GLASS_PANE, "&2+10"));

		gui.setItem(48, CWGUIManager.getItem(Material.RED_WOOL, "&4Cancel"));
		gui.setItem(50, CWGUIManager.getItem(Material.GREEN_WOOL, "&2Confirm"));

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
		int currentNumber = Integer.parseInt(ChatColor.stripColor(valueItem.getItemMeta().getDisplayName()));

		if (itemName.equals("Confirm")) {
			editorConfigManager.setSetting(cwPlayer.getWaitingSetting(), currentNumber, waitingSettingInfo);
			cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
			return;
		}

		int newValue = currentNumber + Integer.parseInt(itemName);

		ItemMeta itemMeta = valueItem.getItemMeta();
		itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f" + newValue));
		valueItem.setItemMeta(itemMeta);
	}
}
