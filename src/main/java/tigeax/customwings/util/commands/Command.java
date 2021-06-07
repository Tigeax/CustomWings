package tigeax.customwings.util.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import tigeax.customwings.CustomWings;
import tigeax.customwings.util.Util;

/**
 * Extention of {@link BukkitCommand}.
 * Adding TabCompletion though the {@link OnTabComplete} event listener.
 * And implentation of {@link CommandExecutor}.
 * Extention classes can implement {@link SubCommandManager}.
 */
public abstract class Command extends BukkitCommand implements CommandExecutor {

    protected CustomWings plugin;

    private String permission = "";

    public Command(String name, List<String> aliases) {
        super(name);

        plugin = CustomWings.getInstance();

        setAliases(aliases);
    
        Util.registerCommand(name, this);
    }


    @Override
    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String getPermision() {
        return permission;
    }

    public List<String> getTabCompletions(String[] args) {
        return null;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        ArrayList<String> argsList = new ArrayList<String>(Arrays.asList(args));
        onCommand(sender, argsList);
        return true;
    }

    @Override
    public void onCommand(CommandSender sender, ArrayList<String> args) {

        if (!sender.hasPermission(permission)) {
            Util.sendMessage(sender, plugin.getMessages().getNoPermissionForCommand());
            return;
        }

        onCommandHasPerm(sender, args);

    }

    @Override
    public void onCommandHasPerm(CommandSender sender, ArrayList<String> args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            onCommandHasPermAndIsPlayer(player, args);
        } else {
            onCommandHasPermAndIsConsole(sender, args);
        }
    }

    @Override
    public void onCommandHasPermAndIsPlayer(Player player, ArrayList<String> args) {
        // TODO
        Util.sendMessage(player, "TODO: Your not Console");
    }

    @Override
    public void onCommandHasPermAndIsConsole(CommandSender sender, ArrayList<String> args) {
        Util.sendMessage(sender, plugin.getMessages().getNotAPlayerError());
    }

}
