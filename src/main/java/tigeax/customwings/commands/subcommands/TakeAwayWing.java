package tigeax.customwings.commands.subcommands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import tigeax.customwings.util.commands.SubCommand;
import tigeax.customwings.wings.Wing;

public class TakeAwayWing extends SubCommand {

  public TakeAwayWing(String name, String permission) {
    super(name);
    setPermission(permission);
  }

  @Override
  public void onCommandHasPerm(CommandSender sender, ArrayList<String> args) {

    // If no player or wing ID is specified send an error
    if (args.size() < 2) {
      sender.sendMessage(plugin.getMessages().getMissingArgumentsTakeAwayWing());
      return;
    }

    if (!plugin.isVaultEnabled()) {
      sender.sendMessage("Vault is needed for that action");
    }

    String playerName = args.get(0);
    String wingName = args.get(1);

    OfflinePlayer player = null;

    try {
      player = Bukkit.getOfflinePlayer(playerName);
    } catch (NullPointerException e) {
      sender.sendMessage(plugin.getMessages().getInvalidPlayerError(playerName));
    }

    if (player == null) {
      sender.sendMessage(plugin.getMessages().getInvalidPlayerError(playerName));
    }

    Wing wing = plugin.getWingByID(args.get(1));

    if (wing == null) {
      sender.sendMessage(plugin.getMessages().getInvalidWingsError(wingName));
    }

    plugin.getPermissions().playerRemove(null, player.getPlayer(), "customwings.wing." + wing.getID().toLowerCase());
    sender.sendMessage(plugin.getMessages().getWingsRemoved(playerName, wing));
  }

}
