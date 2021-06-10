package tigeax.customwings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.editor.SettingType;

/*
 * This EventListener Listends for when a command is executed by a player
 * If we are waiting on a setting input, the waiting for a setting input is cancelled
 */

public class PlayerCommandPreprocessEventListener implements Listener {

	CustomWings plugin;

	public PlayerCommandPreprocessEventListener() {
		plugin = CustomWings.getInstance();
	}

	@EventHandler
	public void event(PlayerCommandPreprocessEvent event) {

		Player player = event.getPlayer();
		CWPlayer cwPlayer = plugin.getCWPlayer(player);
		SettingType waitingSetting = cwPlayer.getWaitingSetting();

		if (waitingSetting != null) {
			cwPlayer.setWaitingSetting(null);
			if (waitingSetting.isChatInputSetting()) {
				cwPlayer.sendMessage(plugin.getMessages().settingCancelled());
			}
		}
	}
}
