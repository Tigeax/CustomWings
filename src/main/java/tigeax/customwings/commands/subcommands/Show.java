package tigeax.customwings.commands.subcommands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.commands.SubCommand;

public class Show extends SubCommand {

    public Show(String name, String permission) {
        super(name);
		setPermission(permission);
    }

    @Override
    public void onCommandHasPerm(CommandSender sender, ArrayList<String> args) {

        // If no wing was specified set the wing to null
		if (args.size() < 2) {
			Util.sendMessage(sender, plugin.getMessages().missingArugmentsError());
			return;
		}

        String playerName = args.get(0);
		Player showWingPlayer = Bukkit.getPlayer(playerName);

		// Send an error if the supplied playername doesn't exist
		if (showWingPlayer == null) {
			Util.sendMessage(sender, plugin.getMessages().invalidPlayerError(playerName));
			return;
		}

		CWPlayer cwPlayer = plugin.getCWPlayer(showWingPlayer);

        Boolean show = args.get(1).equals("on");

        cwPlayer.setShowWing(show);

		Util.sendMessage(sender, plugin.getMessages().showWingCommandSucces(showWingPlayer, show));
	}
    
}
