package tigeax.customwings.gui.guis;

import tigeax.customwings.editor.SettingType;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.Messages;
import tigeax.customwings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class EditorMainSettings {
	
	CustomWings plugin;
	Settings settings;
	Messages messages;

	public EditorMainSettings() {
		plugin = CustomWings.getInstance();
		settings = plugin.getSettings();
		messages = plugin.getMessages();
	}

	public void open(CWPlayer cwPlayer) {

		String guiName = settings.getEditorGUIName() + " - Main Settings";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		gui.setItem(4, CWGUIManager.getItem(Material.ENDER_PEARL, "&3View Distance", settings.getWingViewDistance()));

		gui.setItem(12, CWGUIManager.getItem(Material.CHEST, "&3Main GUI Name", settings.getMainGUIName()));
		gui.setItem(14, CWGUIManager.getItem(Material.CHEST, "&3Main GUI Size", settings.getMainGUISize()));

		gui.setItem(21, CWGUIManager.getItem(Material.BARRIER, "&3Remove Wing Item name", settings.getRemoveWingItem().getItemMeta().getDisplayName()));
		gui.setItem(22, CWGUIManager.getItem(Material.BARRIER, "&3Remove Wing Item material", settings.getRemoveWingItem().getType()));
		gui.setItem(23, CWGUIManager.getItem(Material.BARRIER, "&3Remove Wing Item slot", settings.getRemoveWingSlot()));

		gui.setItem(29, CWGUIManager.getItem(Material.ENDER_EYE, "&3Hide Wings Toggle Itemname ON", settings.getHideWingsToggleONItem().getItemMeta().getDisplayName()));
		gui.setItem(30, CWGUIManager.getItem(Material.ENDER_EYE, "&3Hide Wings Toggle Itemname OFF", settings.getHideWingsToggleOFFItem().getItemMeta().getDisplayName()));
		gui.setItem(31, CWGUIManager.getItem(Material.ENDER_EYE, "&3Hide Wings Toggle Item material ON", settings.getHideWingsToggleONItem().getType()));
		gui.setItem(32, CWGUIManager.getItem(Material.ENDER_EYE, "&3Hide Wings Toggle Item material OFF", settings.getHideWingsToggleOFFItem().getType()));
		gui.setItem(33, CWGUIManager.getItem(Material.ENDER_EYE, "&3Hide Wings Toggle Item slot", settings.getHideWingsToggleSlot()));

		gui.setItem(39, CWGUIManager.getItem(Material.CRAFTING_TABLE, "&3Editor GUI name", settings.getEditorGUIName()));
		gui.setItem(41, CWGUIManager.getItem(Material.CRAFTING_TABLE, "&3Editor Main Settings Slot", settings.getEditorMainSettingsSlot()));

		gui.setItem(53, CWGUIManager.getItem(Material.WHITE_BED, "&4Previous page"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName) {

		switch (itemName) {
			case "View Distance":
				cwPlayer.setWaitingSetting(SettingType.VIEWDISTANCE);
				cwPlayer.closeInventory();
				break;

			case "Main GUI Name":
				cwPlayer.setWaitingSetting(SettingType.MAINGUINAME);
				cwPlayer.closeInventory();
				break;

			case "Main GUI Size":
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTGUISIZE);
				break;

			case "Remove Wing Item name":
				cwPlayer.setWaitingSetting(SettingType.REMOVEWINGNAME);
				cwPlayer.closeInventory();
				break;

			case "Remove Wing Item material":
				cwPlayer.setWaitingSetting(SettingType.REMOVEWINGMATERIAL);
				cwPlayer.closeInventory();
				break;

			case "Remove Wing Item slot":
				cwPlayer.setWaitingSetting(SettingType.REMOVEWINGSLOT);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTSLOT);
				break;

			case "Hide Wings Toggle Itemname ON":
				cwPlayer.setWaitingSetting(SettingType.HIDEWINGTOGGLENAMEON);
				cwPlayer.closeInventory();
				break;

			case "Hide Wings Toggle Itemname OFF":
				cwPlayer.setWaitingSetting(SettingType.HIDEWINGTOGGLENAMEOFF);
				cwPlayer.closeInventory();
				break;

			case "Hide Wings Toggle Item material ON":
				cwPlayer.setWaitingSetting(SettingType.HIDEWINGTOGGLEMATERIALON);
				cwPlayer.closeInventory();
				break;

			case "Hide Wings Toggle Item material OFF":
				cwPlayer.setWaitingSetting(SettingType.HIDEWINGTOGGLEMATERIALOFF);
				cwPlayer.closeInventory();
				break;

			case "Hide Wings Toggle Item slot":
				cwPlayer.setWaitingSetting(SettingType.HIDEWINGTOGGLESLOT);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTSLOT);
				break;

			case "Editor GUI name":
				cwPlayer.setWaitingSetting(SettingType.EDITORGUINAME);
				cwPlayer.closeInventory();
				break;

			case "Editor Main Settings Slot":
				cwPlayer.setWaitingSetting(SettingType.EDITORMAINSETTINGSSLOT);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTSLOT);
				break;

			case "Previous page":
				cwPlayer.openCWGUI(CWGUIType.EDITOR);
				break;
		}
		
		if (cwPlayer.getWaitingSetting() == null)
			return;


		if (cwPlayer.getWaitingSetting().isChatInputSetting()) {
			cwPlayer.getPlayer().sendMessage(messages.getTypeSettingInChat());
			return;
		}

		if (cwPlayer.getWaitingSetting().isChatInputSetting()) {
			cwPlayer.getPlayer().sendMessage(messages.getSelectSettingMaterial());
			return;
		}

	}
}
