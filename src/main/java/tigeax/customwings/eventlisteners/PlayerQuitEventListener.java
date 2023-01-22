package tigeax.customwings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;

/*
 * This EventListener Listends for when a player leaves the game
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

		plugin.deleteCWPlayer(cwPlayer);
	}
	
}
