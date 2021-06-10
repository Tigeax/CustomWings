package tigeax.customwings.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import tigeax.customwings.commands.subcommands.Editor;
import tigeax.customwings.commands.subcommands.Preview;
import tigeax.customwings.commands.subcommands.Reload;
import tigeax.customwings.commands.subcommands.SetWing;
import tigeax.customwings.commands.subcommands.TakeAwayWing;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.commands.Command;
import tigeax.customwings.util.commands.SubCommand;
import tigeax.customwings.util.commands.SubCommandManager;

public class Wings extends Command implements SubCommandManager {

    // A list containing all the subcommands
    ArrayList<SubCommand> subCommands = new ArrayList<SubCommand>();

    public Wings(String name, List<String> aliases, String permission) {
        super(name, aliases);
        setPermission(permission);

        setupSubCommands();
    }

    @Override
    public void setupSubCommands() {
        subCommands.add(new SetWing("setwing", "customwings.setwing"));
        subCommands.add(new Preview("preview", "customwings.preview").setAliases(Arrays.asList("p")));
        subCommands.add(new Editor("editor", "customwings.editor").setAliases(Arrays.asList("edit", "e")));
        subCommands.add(new Reload("reload", "customwings.reload").setAliases(Arrays.asList("r")));
        subCommands.add(new TakeAwayWing("takeawaywing", "customwings.takeawaywing"));
    }

    @Override
    public void onCommandHasPerm(CommandSender sender, ArrayList<String> args) {

        // Open the wing gui if no arguments are given
        if (args.size() == 0) {

            if (!(sender instanceof Player)) {
                Util.sendMessage(sender, plugin.getMessages().notAPlayerError());
                return;
            }
    
            Player player = (Player) sender;
    
            plugin.getCWPlayer(player).openCWGUI(CWGUIType.WINGSELECT);

            return;
        }

        SubCommand target = Util.getSubCommand(args.get(0), subCommands);

        if (target == null) {
            Util.sendMessage(sender, plugin.getMessages().invalidSubCommandError());
            return;
        }

        // Remove subcommand name from the argument list
        args.remove(0);

        target.onCommand(sender, args);

    }

    @Override
    public List<String> getTabCompletions(String[] args) {

        List<String> subCommandNames = Arrays.asList("setwing", "preview", "edit", "reload", "takeawaywing");

        if (args.length == 0) {
            return subCommandNames;
        }

        if (args.length == 1) {
            if (subCommandNames.contains(args[0])) {
                return null;
            }

            List<String> completions = new ArrayList<>();

            StringUtil.copyPartialMatches(args[0], subCommandNames, completions);
            return completions;
        }

        return null;
    }


}
