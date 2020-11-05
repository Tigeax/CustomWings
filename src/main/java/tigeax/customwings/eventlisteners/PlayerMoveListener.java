package tigeax.customwings.eventlisteners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import tigeax.customwings.CustomWings;
import tigeax.customwings.nms.NMSSupport;
import java.time.Instant;

public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onMoveEvent(org.bukkit.event.player.PlayerMoveEvent event) {

        Bukkit.getScheduler().runTaskAsynchronously(CustomWings.getPlugin(CustomWings.class), () -> {
            long now = Instant.now().getEpochSecond();
            Player player = event.getPlayer();
            if (event.getFrom().getX() != event.getTo().getX()) {
                NMSSupport.setBodyRotation(player, player.getLocation().getYaw());
                CustomWings.getCWPlayer(player).setMoving(now);
                return;
            }
            if (event.getFrom().getZ() != event.getTo().getZ()) {
                NMSSupport.setBodyRotation(player, player.getLocation().getYaw());
                CustomWings.getCWPlayer(player).setMoving(now);
                return;
            }
            if (event.getFrom().getY() != event.getTo().getY()) {
                NMSSupport.setBodyRotation(player, player.getLocation().getYaw());
                CustomWings.getCWPlayer(player).setMoving(now);
                return;
            }
        });


    }

}
