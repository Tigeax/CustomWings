package tigeax.customwings.commands.subcommands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import tigeax.customwings.util.commands.SubCommand;

public class Editor extends SubCommand {

    public Editor(String name, String permission) {
        super(name);
        setPermission(permission);
    }

    @Override
    public void onCommandHasPermAndIsPlayer(Player player, ArrayList<String> args) {
        plugin.getMenus().openEditorMenu(player);
    }

}
