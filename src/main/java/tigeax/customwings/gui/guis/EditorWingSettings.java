package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.Configuration;
import tigeax.customwings.configuration.Messages;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.editor.EditorConfigManager;
import tigeax.customwings.editor.SettingType;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.wings.Wing;

public class EditorWingSettings {

	CustomWings plugin;
	Configuration config;
	EditorConfigManager editorConfigManager;
	Messages messages;

	public EditorWingSettings() {
		plugin = CustomWings.getInstance();
		config = plugin.getConfig();

		editorConfigManager = plugin.getEditorConfigManager();
		messages = plugin.getMessages();
	}

	public void open(CWPlayer cwPlayer, Wing wing) {

		WingConfig wingConfig = wing.getConfig();

		String guiName = config.getEditorGUIName() + " - Wing Settings";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		gui.setItem(4, CWGUIManager.getItem(wingConfig.getGuiItem().getType(), "&f" + wingConfig.getID()));

		gui.setItem(12, CWGUIManager.getItem(Material.DIAMOND_LEGGINGS, "&3Show when moving", wingConfig.getShowWhenMoving()));
		gui.setItem(14, CWGUIManager.getItem(Material.GRASS_BLOCK, "&3Whitelisted Worlds", wingConfig.getWhitelistedWorldsString()));

		gui.setItem(19, CWGUIManager.getItem(Material.CHEST, "&3Hide in GUI", wingConfig.getHideInGUI()));
		gui.setItem(20, CWGUIManager.getItem(Material.CHEST, "&3GUI Itemname", wingConfig.getGUIItemName()));
		gui.setItem(21, CWGUIManager.getItem(Material.CHEST, "&3GUI Item material", wingConfig.getGuiItem().getType()));
		gui.setItem(22, CWGUIManager.getItem(Material.CHEST, "&3GUI Item slot", wingConfig.getGuiSlot()));
		gui.setItem(23, CWGUIManager.getItem(Material.ENDER_CHEST, "&3Lore When Equipped", wingConfig.getLoreWhenEquippedString()));
		gui.setItem(24, CWGUIManager.getItem(Material.ENDER_CHEST, "&3Lore When Unequipped", wingConfig.getLoreWhenUnequippedString()));
		gui.setItem(25, CWGUIManager.getItem(Material.ENDER_CHEST, "&3Lore When No Permission", wingConfig.getLoreWhenNoPermissionString()));

		gui.setItem(27, CWGUIManager.getItem(Material.END_ROD, "&3Start Vertical", wingConfig.getStartVertical()));
		gui.setItem(28, CWGUIManager.getItem(Material.END_ROD, "&3Start Horizontal", wingConfig.getStartHorizontal()));
		gui.setItem(29, CWGUIManager.getItem(Material.END_ROD, "&3Distance Between Particles", wingConfig.getDistanceBetweenParticles()));
		gui.setItem(30, CWGUIManager.getItem(Material.CLOCK, "&3Wing Timer", wingConfig.getWingTimer()));

		gui.setItem(32, CWGUIManager.getItem(Material.DIAMOND, "&3Wing Animation", wingConfig.getWingAnimation()));
		gui.setItem(33, CWGUIManager.getItem(Material.DIAMOND, "&3Wing Flap Speed", wingConfig.getWingFlapSpeed()));
		gui.setItem(34, CWGUIManager.getItem(Material.GREEN_TERRACOTTA, "&3Start Offset", wingConfig.getStartOffset()));
		gui.setItem(35, CWGUIManager.getItem(Material.RED_TERRACOTTA, "&3Stop Offset", wingConfig.getStopOffset()));

		gui.setItem(40, CWGUIManager.getItem(Material.ELYTRA, "&3Particles"));

		gui.setItem(53, CWGUIManager.getItem(Material.WHITE_BED, "&4Previous page"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName, Wing wing) {

		WingConfig wingConfig = wing.getConfig();

		switch (itemName) {
			case "Show when moving":
				editorConfigManager.setSetting(SettingType.WINGSHOWWHENMOVING, !wingConfig.getShowWhenMoving(), wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGSETTINGS, wing);
				break;

			case "Whitelisted Worlds":
				cwPlayer.setWaitingSetting(SettingType.WINGWHITELISTEDWORLDS, wing);
				cwPlayer.closeInventory();
				break;

			case "Hide in GUI":
				editorConfigManager.setSetting(SettingType.WINGHIDEINGUI, !wingConfig.getHideInGUI(), wing);
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
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTDOUBLE, wingConfig.getStartVertical());
				break;

			case "Start Horizontal":
				cwPlayer.setWaitingSetting(SettingType.WINGSTARTHORIZONTAL, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTDOUBLE, wingConfig.getStartHorizontal());
				break;

			case "Distance Between Particles":
				cwPlayer.setWaitingSetting(SettingType.WINGDISTANCEBETWEENPARTICLES, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTDOUBLE, wingConfig.getDistanceBetweenParticles());
				break;

			case "Wing Timer":
				cwPlayer.setWaitingSetting(SettingType.WINGTIMER, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTINTEGER, wingConfig.getWingTimer());
				break;

			case "Wing Animation":
				editorConfigManager.setSetting(SettingType.WINGANIMATION, !wingConfig.getWingAnimation(), wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGSETTINGS, wing);
				break;

			case "Wing Flap Speed":
				cwPlayer.setWaitingSetting(SettingType.WINGFLAPSPEED, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTINTEGER, wingConfig.getWingFlapSpeed());
				break;

			case "Start Offset":
				cwPlayer.setWaitingSetting(SettingType.WINGSTARTOFFSET, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTINTEGER, wingConfig.getStartOffset());
				break;

			case "Stop Offset":
				cwPlayer.setWaitingSetting(SettingType.WINGSTOPOFFSET, wing);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTINTEGER, wingConfig.getStopOffset());
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
			cwPlayer.sendMessage(messages.typeSettingInChat());
			return;
		}

		if (cwPlayer.getWaitingSetting().isInventoryInputSetting()) {
			cwPlayer.sendMessage(messages.selectSettingMaterial());
			return;
		}

	}
}
