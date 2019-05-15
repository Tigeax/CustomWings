package tigeax.customwings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import tigeax.customwings.main.CWPlayer;
import tigeax.customwings.main.CustomWings;

/*
 * This EventListener Listends for when a command is executed by a player
 * If we are waiting on a setting input, the waiting for a setting input is cancelled
 */

public class PlayerCommandPreprocessEventListener implements Listener {

	@EventHandler
	public void event(PlayerCommandPreprocessEvent event) {

		Player player = event.getPlayer();
		CWPlayer cwPlayer = CustomWings.getCWPlayer(player);

		if (cwPlayer.getWaitingSetting() != null) {
			cwPlayer.setWaitingSetting(null);
		}
	}
}
