package tigeax.customwings.main;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/*
 * Class containing all the messages set in the messages.yml file
 */

public class Messages {

	private CustomWings plugin;
	private FileConfiguration messagesConfigFile;

	private String noPermissionForCommand, noPermissionEquipWing, wingSelected, seeOtherPlayersWingsON,
			seeOtherPlayersWingsOFF, missingArgumentsSetwing, notAPlayerError, invalidPlayerError,
			setWingCommandWingSet, settingChanged, noWingToPreview, typeSettingInChat, selectSettingMaterial,
			reloadSucces, cannotAffordWing;

	public Messages(CustomWings plugin) {
		this.plugin = plugin;
		load();
	}

	public void reload() {
		load();
	}

	public void load() {

		setupMessagesConfig();

		noPermissionForCommand = parseColors(messagesConfigFile.getString(("noPermissionForCommand")));
		noPermissionEquipWing = parseColors(messagesConfigFile.getString(("noPermissionEquipWing")));
		wingSelected = parseColors(messagesConfigFile.getString(("wingSelected")));
		seeOtherPlayersWingsON = parseColors(messagesConfigFile.getString(("seeOtherPlayersWingsON")));
		seeOtherPlayersWingsOFF = parseColors(messagesConfigFile.getString(("seeOtherPlayersWingsOFF")));
		missingArgumentsSetwing = parseColors(messagesConfigFile.getString(("missingArgumentsSetwing")));
		notAPlayerError = parseColors(messagesConfigFile.getString(("notAPlayerError")));
		invalidPlayerError = parseColors(messagesConfigFile.getString(("invalidPlayerError")));
		setWingCommandWingSet = parseColors(messagesConfigFile.getString(("setWingCommandWingSet")));
		settingChanged = parseColors(messagesConfigFile.getString(("settingChanged")));
		noWingToPreview = parseColors(messagesConfigFile.getString(("noWingToPreview")));
		typeSettingInChat = parseColors(messagesConfigFile.getString(("typeSettingInChat")));
		selectSettingMaterial = parseColors(messagesConfigFile.getString(("selectSettingMaterial")));
		reloadSucces = parseColors(messagesConfigFile.getString(("reloadSucces")));
		cannotAffordWing = parseColors(messagesConfigFile.getString("cantAffordWing"));
	}

	public String getNoPermissionForCommand() { return noPermissionForCommand; }

	public String getSeeOtherPlayersWingsON() { return seeOtherPlayersWingsON; }

	public String getSeeOtherPlayersWingsOFF() { return seeOtherPlayersWingsOFF; }

	public String getMissingArgumentsSetwing() { return missingArgumentsSetwing; }

	public String getNotAPlayerError() { return notAPlayerError; }

	public String getSettingChanged() { return settingChanged; }

	public String getNoWingToPreview() { return noWingToPreview; }

	public String getTypeSettingInChat() { return typeSettingInChat; }

	public String getSelectSettingMaterial() { return selectSettingMaterial; }

	public String getReloadSucces() { return reloadSucces; }

	public String getNoPermissionEquipWing(Wing wing) {
		return noPermissionEquipWing.replace("{WINGNAME}", wing.getGUIItemName());
	}

	public String getCannotAffordWing(Wing wing) { return cannotAffordWing.replace("{WINGNAME}", wing.getGUIItemName()); }

	public String getWingSelected(Wing wing) {
		return wingSelected.replace("{WINGNAME}", wing.getGUIItemName());
	}

	public String getInvalidPlayerError(String name) {
		return invalidPlayerError.replace("{PLAYERNAME}", name);
	}

	public String getSetWingCommandWingSet(String playerName, String wingName) {
		return setWingCommandWingSet.replace("{PLAYERNAME}", playerName).replace("{WINGNAME}", wingName);
	}

	private String parseColors(String string) {
		if (string == null) CustomWings.sendError("Missing a message in the messages.yml file!");

		return ChatColor.translateAlternateColorCodes('&', string + "");
	}

	private void setupMessagesConfig() {

		File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
		messagesConfigFile = new YamlConfiguration();

		if (!messagesFile.exists()) {
			messagesFile.getParentFile().mkdirs();
			plugin.saveResource("messages.yml", false);
		}

		try {
			messagesConfigFile.load(messagesFile);
		} catch (Exception e) {
			CustomWings.sendError(e);
		}

	}

}
