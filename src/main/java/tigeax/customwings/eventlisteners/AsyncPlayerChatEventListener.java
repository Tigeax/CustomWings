package tigeax.customwings.eventlisteners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.configuration.settings.SettingType;

/*
 * This EventListener gets the value from chat when we are waiting from a chat input from a player
 * Which is needed as an input to change a setting
 */

public class AsyncPlayerChatEventListener implements Listener {

	CustomWings plugin;

	public AsyncPlayerChatEventListener() {
		plugin = CustomWings.getInstance();
	}

	@EventHandler
	public void event(AsyncPlayerChatEvent event) {

		Player player = event.getPlayer();
		CWPlayer cwPlayer = plugin.getCWPlayer(player);
		Setting setting = cwPlayer.getWaitingSetting();

		if (setting == null) {
			return;
		}

		event.setCancelled(true);

		if (setting.getSettingType() == SettingType.MATERIAL) {
			cwPlayer.sendMessage(plugin.getMessages().settingChangeCancelled());
		}

		if (setting.getSettingType() == SettingType.STRING) {
			String value = event.getMessage();
			setting.setValue(value);
			cwPlayer.sendMessage(plugin.getMessages().settingChanged());
		}

		if (setting.getSettingType() == SettingType.STRINGLIST) {
			String value = event.getMessage();
			List<String> valueList = Arrays.asList(value.split(","));
			setting.setValue(valueList);
			cwPlayer.sendMessage(plugin.getMessages().settingChanged());
		}


		cwPlayer.setWaitingSetting(null);
	}
}
