package tigeax.customwings.util.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tigeax.customwings.CustomWings;
import tigeax.customwings.util.Util;

/** 
 * A sub command to be called from {@link Command}.
 * Impementing {@link CommandExecutor}.
 * Extention classes can implement {@link SubCommandManager}.
 */
public abstract class SubCommand implements CommandExecutor {

    protected CustomWings plugin;
    private String name;
    public String permission = "";
    public List<String> aliases = Arrays.asList();

    public SubCommand(String name) {
        plugin = CustomWings.getInstance();
        this.name = name.toLowerCase();
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermision() {
        return permission;
    }

    public String name() {
        return name;
    }

    public SubCommand setAliases(List<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public List<String> getAliases() {
        return aliases;
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
