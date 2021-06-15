package tigeax.customwings.menus.editor.selectvaluemenus;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.editor.selectvaluemenus.items.MenuSizeSelectItem;
import tigeax.customwings.menus.items.GoBackItem;
import tigeax.customwings.util.menu.ItemMenu;

public class MenuSizeSelectMenu extends ItemMenu {

    private Setting setting;

    public MenuSizeSelectMenu(ItemMenu parent, Setting setting) {
        super("&cSelect menu size", Rows.ONE);

        this.setting = setting;

        setParent(parent);

        setItem(0, new MenuSizeSelectItem(Rows.ONE));
        setItem(1, new MenuSizeSelectItem(Rows.TWO));
        setItem(2, new MenuSizeSelectItem(Rows.THREE));
        setItem(3, new MenuSizeSelectItem(Rows.FOUR));
        setItem(4, new MenuSizeSelectItem(Rows.FIVE));
        setItem(5, new MenuSizeSelectItem(Rows.SIX));

        setItem(8, new GoBackItem());

    }

    public Setting getSetting() {
        return setting;
    }

}
