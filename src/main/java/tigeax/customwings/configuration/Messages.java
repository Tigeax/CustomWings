package tigeax.customwings.configuration;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tigeax.customwings.util.YamlFile;
import tigeax.customwings.wing.Wing;

public class Messages extends YamlFile {

    private String wingEquipped, hideOtherPlayerWingsON, hideOtherPlayerWingsOFF, settingChanged, settingChangeCancelled,
            typeSettingInChat, selectSettingMaterial, reloadSucces, setWingCommandSucces, showWingCommandSucces, takeAwayWingCommandSucces,
            wingBuySuccess, noPermissionForCommandError, noPermissionToEquipWingError, notAPlayerError, notConsoleError,
            invalidPlayerError, invalidWingsError, noWingToPreviewError, missingArugmentsError,
            missingArgumentsSetwingError, missingArgumentsTakeAwayWingError, cantAffordWingError, noVaultError,
            invalidSubCommandError;

    public Messages(JavaPlugin plugin) {
        super(plugin, "messages.yml");
    }

    @Override
    protected void initDataFromFile() {
        updateDataFromFile();
    }

    @Override
    protected void updateDataFromFile() {

        // Wings
        wingEquipped = getColorString("wingEquipped");
        hideOtherPlayerWingsON = getColorString("hideOtherPlayerWingsON");
        hideOtherPlayerWingsOFF = getColorString("hideOtherPlayerWingsOFF");
        // Editor
        settingChanged = getColorString("settingChanged");
        settingChangeCancelled = getColorString("settingCancelled");
        typeSettingInChat = getColorString("typeSettingInChat");
        selectSettingMaterial = getColorString("selectSettingMaterial");
        // General
        reloadSucces = getColorString("reloadSucces");
        setWingCommandSucces = getColorString("setWingCommandSucces");
        showWingCommandSucces = getColorString("showWingCommandSucces");
        takeAwayWingCommandSucces = getColorString("takeAwayWingCommandSucces");
        wingBuySuccess = getColorString("wingBuySuccess");
        // Error messages
        noPermissionForCommandError = getColorString("noPermissionForCommandError");
        noPermissionToEquipWingError = getColorString("noPermissionToEquipWingError");
        notAPlayerError = getColorString("notAPlayerError");
        notConsoleError = getColorString("notConsoleError");
        invalidPlayerError = getColorString("invalidPlayerError");
        invalidWingsError = getColorString("invalidWingsError");
        noWingToPreviewError = getColorString("noWingToPreviewError");
        missingArugmentsError = getColorString("missingArugmentsError");
        missingArgumentsSetwingError = getColorString("missingArgumentsSetwingError");
        missingArgumentsTakeAwayWingError = getColorString("missingArgumentsTakeAwayWingError");
        cantAffordWingError = getColorString("cantAffordWingError");
        noVaultError = getColorString("noVaultError");
        invalidSubCommandError = getColorString("invalidSubCommandError");

    }

    public String wingEquipped(Wing wing) {
        return wingEquipped.replace("{WINGNAME}", wing.getConfig().getGuiItemName());
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

    public String settingChangeCancelled() {
        return settingChangeCancelled;
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

    public String showWingCommandSucces(Player player, boolean show) {
        String showState = "off";
        if (show) {
            showState = "on";
        }
        return showWingCommandSucces.replace("{PLAYERNAME}", player.getDisplayName()).replace("{SHOW}", showState);
    }

    public String takeAwayWingCommandSucces(Player player, Wing wing) {
        return takeAwayWingCommandSucces.replace("{PLAYERNAME}", player.getDisplayName()).replace("{WINGNAME}",
                wing.getConfig().getGuiItemName());
    }

    public String wingBuySuccess(int price) {
        return wingBuySuccess.replace("{PRICE}", price + "");
    }

    public String noPermissionForCommandError(String commandName, String permission) {
        return noPermissionForCommandError.replace("{COMMAND}", commandName).replace("{PERMISSION}", permission);
    }

    public String noPermissionToEquipWingError(Wing wing) {
        return noPermissionToEquipWingError.replace("{WINGNAME}", wing.getConfig().getGuiItemName())
                .replace("{PERMISSION}", wing.getPermission());
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
        return cantAffordWingError.replace("{WINGNAME}", wing.getConfig().getGuiItemName());
    }

    public String noVaultError() {
        return noVaultError;
    }

    public String invalidSubCommandError() {
        return invalidSubCommandError;
    }
}
