package tigeax.customwings.menus.editor.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.settings.ConfigSetting;
import tigeax.customwings.menus.MenuManager;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.menu.MenuItem;

public class MainSettingItem extends MenuItem {

    private ConfigSetting setting;

    public MainSettingItem(CustomWings plugin, ConfigSetting setting, String name, Material material) {
        this.setting = setting;

        setDisplayName(Util.parseChatColors(name));
        setMaterial(material);
    }

    @Override
    public ItemStack getFinalItem(Player player) {
        setLore(MenuManager.parseLoreForItem(setting.getCurrentValue().toString()));
        return super.getFinalItem(player);
    }


    
}
