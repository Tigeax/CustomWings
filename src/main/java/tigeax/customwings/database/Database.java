package tigeax.customwings.database;

import org.bukkit.entity.Player;
import tigeax.customwings.wing.Wing;

public interface Database {

    void savePlayerEquippedWing(Player player, Wing wing);
    String getPlayerEquippedWingID(Player player);
    void savePlayerHideOtherPlayerWings(Player player, Boolean hideOtherPlayerWings);
    boolean getPlayerHideOtherPlayerWings(Player player);

}
