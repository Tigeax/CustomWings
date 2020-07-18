package main.java.tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import main.java.tigeax.customwings.editor.EditorConfigManager;
import main.java.tigeax.customwings.editor.SettingType;
import main.java.tigeax.customwings.gui.CWGUIManager;
import main.java.tigeax.customwings.gui.CWGUIType;
import main.java.tigeax.customwings.main.CWPlayer;
import main.java.tigeax.customwings.main.CustomWings;
import main.java.tigeax.customwings.main.Messages;
import main.java.tigeax.customwings.main.Settings;
import main.java.tigeax.customwings.main.Wing;

public class EditorWingSettings {

	Settings settings;
	EditorConfigManager editorConfigManager;
	Messages messages;

	public EditorWingSettings() {
		settings = CustomWings.getSettings();
		editorConfigManager = CustomWings.getEditorConfigManager();
		messages = CustomWings.getMessages();
	}

	public void open(CWPlayer cwPlayer, Wing wing) {
		String guiName = settings.getEditorGUIName() + " - Wing Settings";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		gui.setItem(4, CWGUIManager.getItem(wing.getGuiItem().getType(), "&f" + wing.getID()));

		gui.setItem(12, CWGUIManager.getItem(Material.DIAMOND_LEGGINGS, "&3Show when moving", wing.getShowWhenMoving()));
		gui.setItem(14, CWGUIManager.getPlayerHeadItem("cf40942f364f6cbceffcf1151796410286a48b1aeba77243e218026c09cd1", "&3Whitelisted Worlds", wing.getWhitelistedWorldsString()));

		gui.setItem(19, CWGUIManager.getItem(Material.CHEST, "&3Hide in GUI", wing.getHideInGUI()));
		gui.setItem(20, CWGUIManager.getItem(Material.CHEST, "&3GUI Itemname", wing.getGUIItemName()));
		gui.setItem(21, CWGUIManager.getItem(Material.CHEST, "&3GUI Item material", wing.getGuiItem().getType()));
		gui.setItem(22, CWGUIManager.getItem(Material.CHEST, "&3GUI Item slot", wing.getGuiSlot()));
		gui.setItem(23, CWGUIManager.getItem(Material.ENDER_CHEST, "&3Lore When Equipped", wing.getLoreWhenEquippedString()));
		gui.setItem(24, CWGUIManager.getItem(Material.ENDER_CHEST, "&3Lore When Unequipped", wing.getLoreWhenUnequippedString()));
		gui.setItem(25, CWGUIManager.getItem(Material.ENDER_CHEST, "&3Lore When No Permission", wing.getLoreWhenNoPermissionString()));

		gui.setItem(27, CWGUIManager.getPlayerHeadItem("f4628ace7c3afc61a476dc144893aaa642ba976d952b51ece26abafb896b8", "&3Start Vertical", wing.getStartVertical()));
		gui.setItem(28, CWGUIManager.getPlayerHeadItem("2671c4c04337c38a5c7f31a5c751f991e96c03df730cdbee99320655c19d", "&3Start Horizontal", wing.getStartHorizontal()));
		gui.setItem(29, CWGUIManager.getPlayerHeadItem("7472d245b2a8ab25bd4b9d32601d4aba2c53181ad2bde62c8ed71f8cae99543", "&3Distance Between Particles", wing.getDistanceBetweenParticles()));
		gui.setItem(30, CWGUIManager.getPlayerHeadItem("2579f867a71399957be37a7c2fb941d468523fce9903e9df88d37e1835665", "&3Wing Timer", wing.getWingTimer()));
		gui.setItem(32, CWGUIManager.getPlayerHeadItem("8073ae547e6daa9d2dc8cb90e78dd1c71cdfadb7401dc167d16819b173283c51", "&3Wing Animation", wing.getWingAnimation()));
		gui.setItem(33, CWGUIManager.getPlayerHeadItem("70dc9420c14fcab98dcd6f5ad51e8ebe2bb97895976caa70578f73c66dfbd", "&3Wing Flap Speed", wing.getWingFlapSpeed()));
		gui.setItem(34, CWGUIManager.getPlayerHeadItem("f45c9acea8da71b4f252cd4deb5943f49e7dbc0764274b25a6a6f5875baea3", "&3Start Offset", wing.getStartOffset()));
		gui.setItem(35, CWGUIManager.getPlayerHeadItem("ad5fcd31287d63e7826ea760a7ed154f685dfdc7f3465732a96e619b2e1347", "&3Stop Offset", wing.getStopOffset()));

		gui.setItem(40, CWGUIManager.getItem(Material.ELYTRA, "&3Particles"));
		gui.setItem(53, CWGUIManager.getPlayerHeadItem("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6", "&4Previous page"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName, Wing wing) {

		switch (itemName) {
			case "Show when moving":
				editorConfigManager.setSetting(SettingType.WINGSHOWWHENMOVING, !wing.getShowWhenMoving(), wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGSETTINGS, wing);
				break;

			case "Whitelisted Worlds":
				cwPlayer.setWaitingSetting(SettingType.WINGWHITELISTEDWORLDS, wing);
				cwPlayer.closeInventory();
				break;

			case "Hide in GUI":
				editorConfigManager.setSetting(SettingType.WINGHIDEINGUI, !wing.getHideInGUI(), wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGSETTINGS, wing);
				break;

			case "GUI Itemname":
				cwPlayer.setWaitingSetting(SettingType.WINGGUINAME, wing);
				cwPlayer.closeInventory();
				break;

			case "GUI Item material":
				cwPlayer.setWaitingSetting(SettingType.WINGGUIMATERIAL, wing);
				cwPlayer.closeInventory();
				break;

			case "GUI Item slot":
				cwPlayer.setWaitingSetting(SettingType.WINGGUISLOT, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTSLOT);
				break;

			case "Lore When Equipped":
				cwPlayer.setWaitingSetting(SettingType.WINGGUILOREHWENEQUIPPED, wing);
				cwPlayer.closeInventory();
				break;

			case "Lore When Unequipped":
				cwPlayer.setWaitingSetting(SettingType.WINGGUILOREWHENUNEQUIPPED, wing);
				cwPlayer.closeInventory();
				break;

			case "Lore When No Permission":
				cwPlayer.setWaitingSetting(SettingType.WINGGUILOREWHENNOPERMISSION, wing);
				cwPlayer.closeInventory();
				break;

			case "Start Vertical":
				cwPlayer.setWaitingSetting(SettingType.WINGSTARTVERTICAL, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTDOUBLE, wing.getStartVertical());
				break;

			case "Start Horizontal":
				cwPlayer.setWaitingSetting(SettingType.WINGSTARTHORIZONTAL, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTDOUBLE, wing.getStartHorizontal());
				break;

			case "Distance Between Particles":
				cwPlayer.setWaitingSetting(SettingType.WINGDISTANCEBETWEENPARTICLES, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTDOUBLE, wing.getDistanceBetweenParticles());
				break;

			case "Wing Timer":
				cwPlayer.setWaitingSetting(SettingType.WINGTIMER, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTINTEGER, wing.getWingTimer());
				break;

			case "Wing Animation":
				editorConfigManager.setSetting(SettingType.WINGANIMATION, !wing.getWingAnimation(), wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGSETTINGS, wing);
				break;

			case "Wing Flap Speed":
				cwPlayer.setWaitingSetting(SettingType.WINGFLAPSPEED, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTINTEGER, wing.getWingFlapSpeed());
				break;

			case "Start Offset":
				cwPlayer.setWaitingSetting(SettingType.WINGSTARTOFFSET, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTINTEGER, wing.getStartOffset());
				break;

			case "Stop Offset":
				cwPlayer.setWaitingSetting(SettingType.WINGSTOPOFFSET, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTINTEGER, wing.getStopOffset());
				break;

			case "Particles":
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGPARITCLESSELECT, wing);
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
