package tigeax.customwings.commands.subcommands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.util.commands.SubCommand;
import tigeax.customwings.wings.Wing;

public class Preview extends SubCommand {

    public Preview(String name, String permission) {
        super(name);
		setPermission(permission);
    }

    @Override
    public void onCommandHasPermAndIsPlayer(Player player, ArrayList<String> args) {

		CWPlayer cwPlayer = plugin.getCWPlayer(player);

		Wing wing = cwPlayer.getEquippedWing();

		// Send an error if the player does not have a wing equipped
		if (wing == null) {
			cwPlayer.sendMessage(plugin.getMessages().noWingToPreviewError());
			return;
		}

		// Toggle the wing previewing
		cwPlayer.setPreviewingWing(!cwPlayer.isPreviewingWing());
	}
    
}
