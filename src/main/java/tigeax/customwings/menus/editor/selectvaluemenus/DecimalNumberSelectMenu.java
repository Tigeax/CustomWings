package tigeax.customwings.menus.editor.selectvaluemenus;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.editor.items.GoBackItem;
import tigeax.customwings.menus.editor.selectvaluemenus.items.DecimalNumberSelectItem;
import tigeax.customwings.menus.editor.selectvaluemenus.items.SettingItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.util.menu.MenuItem;

public class DecimalNumberSelectMenu extends ItemMenu {

    private Setting setting;

    public DecimalNumberSelectMenu(ItemMenu parent, Setting setting, MenuItem menuItem) {
        super("&cSelect decimal number", Rows.SIX);

        this.setting = setting;

        setParent(parent);

        setItem(19, new DecimalNumberSelectItem(-1));
        setItem(20, new DecimalNumberSelectItem(-0.1));
        setItem(21, new DecimalNumberSelectItem(-0.01));

        setItem(22, new SettingItem(setting, menuItem.getDisplayName(), menuItem.getMaterial(), true));

        setItem(23, new DecimalNumberSelectItem(0.01));
        setItem(24, new DecimalNumberSelectItem(0.1));
        setItem(25, new DecimalNumberSelectItem(1));

        setItem(53, new GoBackItem());

    }

    public Setting getSetting() {
        return setting;
    }

}
