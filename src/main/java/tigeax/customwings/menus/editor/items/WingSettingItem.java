package tigeax.customwings.menus.editor.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.configuration.settings.SettingType;
import tigeax.customwings.configuration.settings.WingSetting;
import tigeax.customwings.menus.MenuManager;
import tigeax.customwings.menus.editor.GuiSlotSelectMenu;
import tigeax.customwings.util.Util;
import tigeax.customwings.util.menu.ItemClickEvent;
import tigeax.customwings.util.menu.MenuItem;
import tigeax.customwings.wing.Wing;

public class WingSettingItem extends MenuItem {

    private WingSetting setting;
    private Wing wing;
    private WingConfig wingConfig;

    public WingSettingItem(CustomWings plugin, WingSetting setting, Wing wing, String name, Material material) {
        this.setting = setting;
        this.wing = wing;
        this.wingConfig = wing.getConfig();

        setDisplayName(Util.parseChatColors(name));
        setMaterial(material);
    }

    @Override
    public ItemStack getFinalItem(Player player) {
        setLore(MenuManager.parseLoreForItem(setting.getCurrentValue(wing.getConfig()).toString()));
        return super.getFinalItem(player);
    }

    @Override
    public void onItemClick(ItemClickEvent event) {

        SettingType settingType = setting.getSettingType();

        switch (settingType) {

            case BOOLEAN:
                setting.setValue(wingConfig, !((Boolean) setting.getCurrentValue(wingConfig)));
                event.setWillUpdate(true);
                break;
            
            case GUISLOT:
                new GuiSlotSelectMenu(event.getItemMenu(), setting).open(event.getPlayer());
                break;

            default:
                break;
        }
    }

}
