package tigeax.customwings.util.commands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** 
 * Own implementation of Bukkit's {@link org.bukkit.command.CommandExecutor}.
 */
public interface CommandExecutor {

    public void setPermission(String permission);

    public String getPermision();

    public void onCommand(CommandSender sender, ArrayList<String> args);

    public void onCommandHasPerm(CommandSender sender, ArrayList<String> args);

    public void onCommandHasPermAndIsPlayer(Player player, ArrayList<String> args);

    public void onCommandHasPermAndIsConsole(CommandSender sender, ArrayList<String> args);
    
}
