package tigeax.customwings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;

/*
 * This EventListener Listends for when a player leaves the game
 * And will remove the player form the playersWithWingActive list of a wing
 * And will set the players waitingSetting to null
 */

public class PlayerQuitEventListener implements Listener {

	@EventHandler
	public void event(PlayerQuitEvent event) {

		Player player = event.getPlayer();
		CWPlayer cwPlayer = CustomWings.getInstance().getCWPlayer(player);
		
		cwPlayer.setLastEditorInvView(null);

		if (cwPlayer.getEquippedWing() != null) {
			cwPlayer.getEquippedWing().removePlayersWithWingActive(player);
			cwPlayer.setWaitingSetting(null);
		}
	}
	
}
