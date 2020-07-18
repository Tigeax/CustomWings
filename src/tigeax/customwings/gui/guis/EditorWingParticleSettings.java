package tigeax.customwings.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import tigeax.customwings.editor.SettingType;
import tigeax.customwings.gui.CWGUIManager;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.main.CWPlayer;
import tigeax.customwings.main.CustomWings;
import tigeax.customwings.main.Messages;
import tigeax.customwings.main.Settings;
import tigeax.customwings.main.WingParticle;

public class EditorWingParticleSettings {

	Settings settings;
	Messages messages;

	public EditorWingParticleSettings() {
		settings = CustomWings.getSettings();
		messages = CustomWings.getMessages();
	}

	public void open(CWPlayer cwPlayer, WingParticle wingParticle) {

		String guiName = settings.getEditorGUIName() + " - Particle Settings";

		Inventory gui = Bukkit.createInventory(null, 54, guiName);

		gui.setItem(4, CWGUIManager.getItem(wingParticle.getWing().getGuiItem().getType(), "&f"
				+ wingParticle.getWing().getID() + " - " + wingParticle.getID()));

		gui.setItem(22, CWGUIManager.getItem(wingParticle.getItemMaterial(), "&3Particle", wingParticle.getParticle().toString()));

		gui.setItem(29, CWGUIManager.getPlayerHeadItem("93971124be89ac7dc9c929fe9b6efa7a07ce37ce1da2df691bf8663467477c7", "&3Distance", wingParticle.getDistance()));
		gui.setItem(30, CWGUIManager.getPlayerHeadItem("f4628ace7c3afc61a476dc144893aaa642ba976d952b51ece26abafb896b8", "&3Height", wingParticle.getHeight()));
		gui.setItem(32, CWGUIManager.getPlayerHeadItem("298c77373229d28833c59849744554f9bf62a7bef785e5b618ea732aad3c834", "&3Angle", wingParticle.getAngle()));
		gui.setItem(33, CWGUIManager.getPlayerHeadItem("7472d245b2a8ab25bd4b9d32601d4aba2c53181ad2bde62c8ed71f8cae99543", "&3Speed", wingParticle.getSpeed()));

		gui.setItem(39, CWGUIManager.getItem(wingParticle.getMaterialData(), "&3Block Type", wingParticle.getMaterialData().toString()));
		gui.setItem(41, CWGUIManager.getPlayerHeadItem("c227670d148794915304827b0eb03eff273ca153f874db5e9094d1cdbb6258a2", "&3Color", wingParticle.getDustOptions().getColor().asRGB()));

		gui.setItem(53, CWGUIManager.getPlayerHeadItem("edf5c2f893bd3f89ca40703ded3e42dd0fbdba6f6768c8789afdff1fa78bf6", "&4Previous page", null));

		cwPlayer.getPlayer().openInventory(gui);
	}

	public void click(CWPlayer cwPlayer, String itemName, WingParticle wingParticle) {

		Player player = cwPlayer.getPlayer();

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
				player.sendMessage(messages.getSelectSettingMaterial());
				cwPlayer.closeInventory();
				return;
			case "Color":
				cwPlayer.setWaitingSetting(SettingType.WINGPARTICLECOLOR, wingParticle);
				player.sendMessage(messages.getTypeSettingInChat());
				cwPlayer.closeInventory();
				return;
			case "Previous page":
				cwPlayer.openCWGUI(CWGUIType.EDITORWINGPARITCLESSELECT, wingParticle.getWing());
				return;
		}
	}

}
