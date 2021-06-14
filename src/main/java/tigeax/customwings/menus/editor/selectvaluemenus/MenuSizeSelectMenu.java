package tigeax.customwings.menus.editor.selectvaluemenus;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.items.GoBackItem;
import tigeax.customwings.util.menu.ItemMenu;

public class MenuSizeSelectMenu extends ItemMenu {

    private Setting setting;

    public MenuSizeSelectMenu(ItemMenu parent, Setting setting) {
        super("&cSelect menu size", Rows.ONE);

        this.setting = setting;

        setParent(parent);

        setItem(8, new GoBackItem());

    }

    public Setting getSetting() {
        return setting;
    }

}
