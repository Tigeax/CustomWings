package tigeax.customwings.commands.subcommands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import tigeax.customwings.CWPlayer;
import tigeax.customwings.gui.CWGUIType;
import tigeax.customwings.util.commands.SubCommand;

public class Editor extends SubCommand {

    public Editor(String name, String permission) {
        super(name);
		setPermission(permission);
    }

    @Override
    public void onCommandHasPermAndIsPlayer(Player player, ArrayList<String> args) {

		CWPlayer cwPlayer = plugin.getCWPlayer(player);
		
		cwPlayer.openCWGUI(CWGUIType.LASTEDITORGUI);
	}
    
}
