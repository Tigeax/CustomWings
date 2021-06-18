package tigeax.customwings.eventlisteners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tigeax.customwings.CWPlayer;
import tigeax.customwings.CustomWings;
import tigeax.customwings.nms.NMSSupport;
import java.time.Instant;

public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onMoveEvent(org.bukkit.event.player.PlayerMoveEvent event) {

        Bukkit.getScheduler().runTaskAsynchronously(CustomWings.getPlugin(CustomWings.class), () -> {

            Player player = event.getPlayer();
            CWPlayer cwPlayer = CustomWings.getCWPlayer(player);

            if (cwPlayer.getEquippedWing() == null) {
                return;
            }

            long now = Instant.now().getEpochSecond();

            if (event.getFrom().distance(event.getTo()) > 0.2) {
                NMSSupport.setBodyRotation(player, player.getLocation().getYaw());
                CustomWings.getCWPlayer(player).setLastTimeMoving(now);
            }
        });


    }

}
