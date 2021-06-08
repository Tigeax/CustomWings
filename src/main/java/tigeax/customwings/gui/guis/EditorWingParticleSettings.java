package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.Settings;
import tigeax.customwings.configuration.Messages;
import tigeax.customwings.editor.SettingType;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.wings.WingParticle;

public class EditorWingParticleSettings {

	CustomWings plugin;
	Settings settings;
	Messages messages;

	public EditorWingParticleSettings() {
		plugin = CustomWings.getInstance();
		settings = plugin.getSettings();
		messages = plugin.getMessages();
	}

	public void open(CWPlayer cwPlayer, WingParticle wingParticle) {

		String guiName = settings.getEditorGUIName() + " - Particle Settings";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		gui.setItem(4, CWGUIManager.getItem(wingParticle.getWing().getGuiItem().getType(), "&f" + wingParticle.getWing().getID() + " - " + wingParticle.getID()));

		gui.setItem(22, CWGUIManager.getItem(wingParticle.getItemMaterial(), "&3Particle", wingParticle.getParticle().toString()));

		gui.setItem(29, CWGUIManager.getItem(Material.END_ROD, "&3Distance", wingParticle.getDistance()));
		gui.setItem(30, CWGUIManager.getItem(Material.END_ROD, "&3Height", wingParticle.getHeight()));
		gui.setItem(32, CWGUIManager.getItem(Material.END_ROD, "&3Angle", wingParticle.getAngle()));
		gui.setItem(33, CWGUIManager.getItem(Material.END_ROD, "&3Speed", wingParticle.getSpeed()));

		gui.setItem(39, CWGUIManager.getItem(wingParticle.getMaterialData(), "&3Block Type", wingParticle.getMaterialData().toString()));
		gui.setItem(41, CWGUIManager.getItem(Material.PINK_WOOL, "&3Color", wingParticle.getDustOptions().getColor().asRGB()));

		gui.setItem(53, CWGUIManager.getItem(Material.WHITE_BED, "&4Previous page"));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName, WingParticle wingParticle) {

		switch (itemName) {
			case "Particle":
				cwPlayer.setWaitingSetting(SettingType.WINGPARTICLEPARTICLE, wingParticle);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTPARTICLE);
				return;
			case "Distance":
				cwPlayer.setWaitingSetting(SettingType.WINGPARTICLEDISTANCE, wingParticle);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTDOUBLE, wingParticle.getDistance());
				return;
			case "Height":
				cwPlayer.setWaitingSetting(SettingType.WINGPARTICLEHEIGHT, wingParticle);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTDOUBLE, wingParticle.getHeight());
				return;
			case "Angle":
				cwPlayer.setWaitingSetting(SettingType.WINGPARTICLEANGLE, wingParticle);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTINTEGER, wingParticle.getAngle());
				return;
			case "Speed":
				cwPlayer.setWaitingSetting(SettingType.WINGPARTICLESPEED, wingParticle);
				cwPlayer.openCWGUI(CWGUIType.EDITORSELECTDOUBLE, wingParticle.getSpeed());
				return;
			case "Block Type":
				cwPlayer.setWaitingSetting(SettingType.WINGPARTICLEBLOCKTYPE, wingParticle);
				cwPlayer.sendMessage(messages.selectSettingMaterial());
				cwPlayer.closeInventory();
				return;
			case "Color":
				cwPlayer.setWaitingSetting(SettingType.WINGPARTICLECOLOR, wingParticle);
				cwPlayer.sendMessage(messages.typeSettingInChat());
				cwPlayer.closeInventory();
				return;
			case "Previous page":
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGPARITCLESSELECT, wingParticle.getWing());
				return;
		}
	}

}
