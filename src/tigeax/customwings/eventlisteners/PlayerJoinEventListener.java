package tigeax.customwings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tigeax.customwings.main.CWPlayer;
import tigeax.customwings.main.CustomWings;
import tigeax.customwings.main.Wing;

/*
 * This EventListener Listends for when a player joins
 * If the player had a wing equipped before the player wil be added back to the playersWithWingActive list of the wing
 */

public class PlayerJoinEventListener implements Listener {

	@EventHandler
	public void event(PlayerJoinEvent event) {

		Player player = event.getPlayer();
		CWPlayer cwPlayer = CustomWings.getCWPlayer(player);
		Wing wing = cwPlayer.getEquippedWing();

		if (wing == null) return;

		wing.addPlayersWithWingActive(player);
		cwPlayer.startMovementChecker();
	}

}
