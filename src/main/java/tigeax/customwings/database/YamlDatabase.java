package tigeax.customwings.database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import tigeax.customwings.CustomWings;
import tigeax.customwings.util.YamlFile;
import tigeax.customwings.wing.Wing;

public class YamlDatabase extends YamlFile implements Database {

    private boolean changes = false;

    public YamlDatabase(JavaPlugin plugin) {
        super(plugin, "database.yml");
        Bukkit.getScheduler().runTaskTimerAsynchronously(CustomWings.getInstance(), () -> {
            if (changes) {
                save();
                changes = false;
            }
        }, 1200, 1200);
    }

    public void savePlayerEquippedWing(Player player, Wing wing) {
        if (wing == null) {
            set(player.getUniqueId() + ".wing", null);
        } else {
            set(player.getUniqueId() + ".wing", wing.getConfig().getID());
        }
        
        changes = true;
    }

    public String getPlayerEquippedWingID(Player player) {
        return getString(player.getUniqueId() + ".wing", null);
    }


    public void savePlayerHideOtherPlayerWings(Player player, Boolean hideOtherPlayerWings) {
        set(player.getUniqueId() + ".hideOtherWings", hideOtherPlayerWings);
        changes = true;
    }

    public boolean getPlayerHideOtherPlayerWings(Player player) {
        return getBoolean(player.getUniqueId() + ".hideOtherWings", false);
    }

}
