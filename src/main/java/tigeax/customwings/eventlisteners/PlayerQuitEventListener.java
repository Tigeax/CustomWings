package tigeax.customwings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.wing.Wing;

/*
 * This EventListener Listends for when a player leaves the game
 * And will remove the player form the playersWithWingActive list of a wing
 * And will set the players waitingSetting to null
 */

public class PlayerQuitEventListener implements Listener {

	CustomWings plugin;

	public PlayerQuitEventListener() {
		plugin = CustomWings.getInstance();
	}

	@EventHandler
	public void PlayerQuitEvent(PlayerQuitEvent event) {

		Player player = event.getPlayer();
		CWPlayer cwPlayer = plugin.getCWPlayer(player);
		Wing wing = cwPlayer.getEquippedWing();

		plugin.getDatabase().savePlayerEquippedWing(player, wing);

		if (cwPlayer.getHideOtherPlayerWings()) {
			plugin.getDatabase().savePlayerHideOtherPlayerWings(player, true);
		} else {
			plugin.getDatabase().savePlayerHideOtherPlayerWings(player, null);
		}

		plugin.deleteCWPlayer(cwPlayer);
	}
	
}
