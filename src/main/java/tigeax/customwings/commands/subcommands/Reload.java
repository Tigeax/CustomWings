package tigeax.customwings.commands.subcommands;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;

import tigeax.customwings.util.Util;
import tigeax.customwings.util.commands.SubCommand;

public class Reload extends SubCommand {

    public Reload(String name, String permission) {
        super(name);
		    setPermission(permission);
    }

    @Override
    public void onCommandHasPerm(CommandSender sender, ArrayList<String> args) {
      
      plugin.reload();
      Util.sendMessage(sender, plugin.getMessages().reloadSucces());
	}
    
}
