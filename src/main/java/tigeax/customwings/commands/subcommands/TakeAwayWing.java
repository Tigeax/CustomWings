package tigeax.customwings.commands.subcommands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import tigeax.customwings.util.Util;
import tigeax.customwings.util.commands.SubCommand;
import tigeax.customwings.wing.Wing;

public class TakeAwayWing extends SubCommand {

  public TakeAwayWing(String name, String permission) {
    super(name);
    setPermission(permission);
  }

  @Override
  public void onCommandHasPerm(CommandSender sender, ArrayList<String> args) {

    // If no player or wing ID is specified send an error
    if (args.size() < 2) {
      Util.sendMessage(sender, plugin.getMessages().missingArgumentsTakeAwayWingError());
      return;
    }

    if (!plugin.isVaultEnabled()) {
      Util.sendMessage(sender, plugin.getMessages().noVaultError());
      return;
    }

    String playerName = args.get(0);
    String wingName = args.get(1);

    Player player = null;

    player = Bukkit.getPlayer(playerName).getPlayer();

    if (player == null) {
      Util.sendMessage(sender, plugin.getMessages().invalidPlayerError(playerName));
    }

    Wing wing = plugin.getWingByID(args.get(1));

    if (wing == null) {
      Util.sendMessage(sender, plugin.getMessages().invalidWingsError(wingName));
    }

    plugin.getPermissions().playerRemove(null, player.getPlayer(), "customwings.wing." + wing.getConfig().getID());
    Util.sendMessage(sender, plugin.getMessages().takeAwayWingCommandSucces(player, wing));
  }

}
