package tigeax.customwings.eventlisteners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.wing.Wing;

/*
 * This EventListener Listends for when a player joins
 * If the player had a wing equipped before the player wil be added back to the playersWithWingActive list of the wing
 */

public class PlayerJoinEventListener implements Listener {

	private final CustomWings plugin;

	public PlayerJoinEventListener() {
		plugin = CustomWings.getInstance();
	}

	@EventHandler
	public void event(PlayerJoinEvent event) {

		Player player = event.getPlayer();
		CWPlayer cwPlayer = plugin.getCWPlayer(player);

		String wingId = plugin.getDatabase().getPlayerEquippedWingID(player);

		if (wingId != null) {
			Wing wing = plugin.getWingByID(wingId);
			if (wing != null)
				cwPlayer.setEquippedWing(wing);
		}

		boolean hideOtherPlayerWings = plugin.getDatabase().getPlayerHideOtherPlayerWings(player);
		cwPlayer.setHideOtherPlayerWings(hideOtherPlayerWings);

	}

}
