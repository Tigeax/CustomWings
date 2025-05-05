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

        setItem(0, new ColorSelectItem(16711680, "Red", Material.RED_TERRACOTTA));
        setItem(1, new ColorSelectItem(65280, "Green", Material.GREEN_TERRACOTTA));
        setItem(2, new ColorSelectItem(255, "Blue", Material.BLUE_TERRACOTTA));
        setItem(3, new ColorSelectItem(0, "Black", Material.BLACK_TERRACOTTA));
        setItem(4, new ColorSelectItem(16777215, "White", Material.WHITE_TERRACOTTA));
        setItem(5, new ColorSelectItem(16776960, "Yellow", Material.YELLOW_TERRACOTTA));
        setItem(6, new ColorSelectItem(16711935, "Purple", Material.PURPLE_TERRACOTTA));
        setItem(7, new ColorSelectItem(65535, "Cyan", Material.CYAN_TERRACOTTA));
        setItem(8, new ColorSelectItem(16744448, "Orange", Material.ORANGE_TERRACOTTA));
        setItem(9, new ColorSelectItem(12582656, "Lime", Material.LIME_TERRACOTTA));
        setItem(10, new ColorSelectItem(9849600, "Brown", Material.BROWN_TERRACOTTA));
        setItem(11, new ColorSelectItem(16761035, "Pink", Material.PINK_TERRACOTTA));

        setItem(53, new GoBackItem());

    }

    public Setting getSetting() {
        return setting;
    }

}
