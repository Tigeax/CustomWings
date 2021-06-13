package tigeax.customwings.menus.editor.selectvaluemenus;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.editor.items.GoBackItem;
import tigeax.customwings.menus.editor.selectvaluemenus.items.NumberSelectItem;
import tigeax.customwings.menus.editor.selectvaluemenus.items.SettingItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuItem;

public class NumberSelectMenu extends ItemMenu {

    private Setting setting;

    public NumberSelectMenu(ItemMenu parent, Setting setting, MenuItem menuItem) {
        super("&cSelect number", Rows.SIX);

        this.setting = setting;

        setParent(parent);

        setItem(19, new NumberSelectItem(-10));
        setItem(20, new NumberSelectItem(-5));
        setItem(21, new NumberSelectItem(-1));

        setItem(22, new SettingItem(setting, menuItem.getDisplayName(), menuItem.getMaterial(), true));

        setItem(23, new NumberSelectItem(1));
        setItem(24, new NumberSelectItem(5));
        setItem(25, new NumberSelectItem(10));

        setItem(53, new GoBackItem());
    }

    public Setting getSetting() {
        return setting;
    }

}
