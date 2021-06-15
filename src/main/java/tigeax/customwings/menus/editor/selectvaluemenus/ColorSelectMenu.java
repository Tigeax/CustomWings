package tigeax.customwings.menus.editor.selectvaluemenus;

import org.bukkit.Material;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.editor.selectvaluemenus.items.ColorSelectItem;
import tigeax.customwings.menus.items.GoBackItem;
import tigeax.customwings.util.menu.ItemMenu;

public class ColorSelectMenu extends ItemMenu {

    private Setting setting;

    public ColorSelectMenu(ItemMenu parent, Setting setting) {
        super("&cSelect color", Rows.SIX);

        this.setting = setting;

        setParent(parent);

        setItem(0, new ColorSelectItem(16711680, Material.RED_TERRACOTTA));
        setItem(1, new ColorSelectItem(65280, Material.GREEN_TERRACOTTA));
        setItem(2, new ColorSelectItem(255, Material.BLUE_TERRACOTTA));
        setItem(3, new ColorSelectItem(0, Material.BLACK_TERRACOTTA));
        setItem(4, new ColorSelectItem(16777215, Material.WHITE_TERRACOTTA));
        setItem(5, new ColorSelectItem(16776960, Material.YELLOW_TERRACOTTA));
        setItem(6, new ColorSelectItem(16711935, Material.PURPLE_TERRACOTTA));
        setItem(7, new ColorSelectItem(65535, Material.CYAN_TERRACOTTA));
        setItem(8, new ColorSelectItem(16744448, Material.ORANGE_TERRACOTTA));
        setItem(9, new ColorSelectItem(12582656, Material.LIME_TERRACOTTA));
        setItem(10, new ColorSelectItem(9849600, Material.BROWN_TERRACOTTA));
        setItem(11, new ColorSelectItem(16761035, Material.PINK_TERRACOTTA));

        setItem(53, new GoBackItem());

    }

    public Setting getSetting() {
        return setting;
    }

}
