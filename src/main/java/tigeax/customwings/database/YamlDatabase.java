package tigeax.customwings.database;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tigeax.customwings.util.YamlFile;
import tigeax.customwings.wing.Wing;

public class YamlDatabase extends YamlFile {

    public YamlDatabase(JavaPlugin plugin) {
        super(plugin, "database.yml");
    }

    public void savePlayerEquippedWing(Player player, Wing wing) {

        if (wing == null) {
            set(player.getUniqueId().toString() + ".wing", null);
        } else {
         set(player.getUniqueId().toString() + ".wing", wing.getConfig().getID());
        }
        save();
    }

    public String getPlayerEquippedWingID(Player player) {
        return getString(player.getUniqueId().toString() + ".wing", null);
    }


    public void savePlayerHideOtherPlayerWings(Player player, Boolean hideOtherPlayerWings) {
        set(player.getUniqueId().toString() + ".hideOtherWings", hideOtherPlayerWings);
        save();
    }

    public Boolean getPlayerHideOtherPlayerWings(Player player) {
        return getBoolean(player.getUniqueId().toString() + ".hideOtherWings", false);
    }
    
}
