package tigeax.customwings.configuration;

import org.bukkit.entity.Player;

import tigeax.customwings.CustomWings;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.YamlFile;
import tigeax.customwings.wings.Wing;

public class Messages extends YamlFile {

    private CustomWings plugin;

    private String wingEquipped, hideOtherPlayerWingsON, hideOtherPlayerWingsOFF, settingChanged, settingCancelled,
            typeSettingInChat, selectSettingMaterial, reloadSucces, setWingCommandSucces, takeAwayWingCommandSucces,
            noPermissionForCommandError, noPermissionToEquipWingError, notAPlayerError, notConsoleError,
            invalidPlayerError, invalidWingsError, noWingToPreviewError, missingArugmentsError,
            missingArgumentsSetwingError, missingArgumentsTakeAwayWingError, cantAffordWingError, noVaultError,
            invalidSubCommandError;

    public Messages(CustomWings plugin) {
        super(plugin, "messages.yml");
        this.plugin = plugin;
        loadMessagesFromFile();
    }

    public void update() {
        super.updateFile();
        loadMessagesFromFile();
    }

    private void loadMessagesFromFile() {
        // Wings
        wingEquipped = getMessage("wingEquipped");
        hideOtherPlayerWingsON = getMessage("hideOtherPlayerWingsON");
        hideOtherPlayerWingsOFF = getMessage("hideOtherPlayerWingsOFF");
        // Editor
        settingChanged = getMessage("settingChanged");
        settingCancelled = getMessage("settingCancelled");
        typeSettingInChat = getMessage("typeSettingInChat");
        selectSettingMaterial = getMessage("selectSettingMaterial");
        // General
        reloadSucces = getMessage("reloadSucces");
        setWingCommandSucces = getMessage("setWingCommandSucces");
        takeAwayWingCommandSucces = getMessage("takeAwayWingCommandSucces");
        // Error messages
        noPermissionForCommandError = getMessage("noPermissionForCommandError");
        noPermissionToEquipWingError = getMessage("noPermissionToEquipWingError");
        notAPlayerError = getMessage("notAPlayerError");
        notConsoleError = getMessage("notConsoleError");
        invalidPlayerError = getMessage("invalidPlayerError");
        invalidWingsError = getMessage("invalidWingsError");
        noWingToPreviewError = getMessage("noWingToPreviewError");
        missingArugmentsError = getMessage("missingArugmentsError");
        missingArgumentsSetwingError = getMessage("missingArgumentsSetwingError");
        missingArgumentsTakeAwayWingError = getMessage("missingArgumentsTakeAwayWingError");
        cantAffordWingError = getMessage("cantAffordWingError");
        noVaultError = getMessage("noVaultError");
        invalidSubCommandError = getMessage("invalidSubCommandError");
    }

    public String wingEquipped(Wing wing) {
        return wingEquipped.replace("{WINGNAME}", wing.getGUIItemName());
    }

    public String hideOtherPlayerWingsON() {
        return hideOtherPlayerWingsON;
    }

    public String hideOtherPlayerWingsOFF() {
        return hideOtherPlayerWingsOFF;
    }

    public String settingChanged() {
        return settingChanged;
    }

    public String settingCancelled() {
        return settingCancelled;
    }

    public String typeSettingInChat() {
        return typeSettingInChat;
    }

    public String selectSettingMaterial() {
        return selectSettingMaterial;
    }

    public String reloadSucces() {
        return reloadSucces;
    }

    public String setWingCommandSucces(Player player, String wingName) {
        return setWingCommandSucces.replace("{PLAYERNAME}", player.getDisplayName()).replace("{WINGNAME}", wingName);
    }

    public String takeAwayWingCommandSucces(Player player, Wing wing) {
        return takeAwayWingCommandSucces.replace("{PLAYERNAME}", player.getDisplayName()).replace("{WINGNAME}",
                wing.getGUIItemName());
    }

    public String noPermissionForCommandError(String commandName, String permission) {
        return noPermissionForCommandError.replace("{COMMAND}", commandName).replace("{PERMISSION}", permission);
    }

    public String noPermissionToEquipWingError(Wing wing) {
        return noPermissionToEquipWingError.replace("{WINGNAME}", wing.getGUIItemName()).replace("{PERMISSION}", wing.getPermission());
    }

    public String notAPlayerError() {
        return notAPlayerError;
    }

    public String notConsoleError() {
        return notConsoleError;
    }

    public String invalidPlayerError(String playerName) {
        return invalidPlayerError.replace("{PLAYERNAME}", playerName);
    }

    public String invalidWingsError(String wingName) {
        return invalidWingsError.replace("{WINGNAME}", wingName);
    }

    public String noWingToPreviewError() {
        return noWingToPreviewError;
    }

    public String missingArugmentsError() {
        return missingArugmentsError;
    }

    public String missingArgumentsSetwingError() {
        return missingArgumentsSetwingError;
    }

    public String missingArgumentsTakeAwayWingError() {
        return missingArgumentsTakeAwayWingError;
    }

    public String cantAffordWingError(Wing wing) {
        return cantAffordWingError.replace("{WINGNAME}", wing.getGUIItemName());
    }

    public String noVaultError() {
        return noVaultError;
    }

    public String invalidSubCommandError() {
        return invalidSubCommandError;
    }

    /**
     * Get a messages from the message.yml file and parse the ChatColors.
     * 
     * @param name Name of the message
     * @return The message
     */
    private String getMessage(String name) {
        String message;

        try {
            message = Util.parseChatColors(getString(name));
        } catch (IllegalArgumentException e) {
            message = "";
            plugin.getLogger().warning("Failed to get message " + name + " from messages.yml");
        }

        return message;
    }

}