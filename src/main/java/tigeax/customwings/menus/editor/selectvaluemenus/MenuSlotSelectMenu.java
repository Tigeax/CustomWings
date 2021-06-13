package tigeax.customwings.menus.editor.selectvaluemenus;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.menus.editor.selectvaluemenus.items.MenuSlotSelectItem;
import tigeax.customwings.util.menu.ItemMenu;

public class MenuSlotSelectMenu extends ItemMenu {

    private Setting setting;

    public MenuSlotSelectMenu(ItemMenu parent, Setting setting) {
        super("&cSelect menu slot", Rows.SIX);

        this.setting = setting;

        setParent(parent);

        for (int i = 0; i < 54; i++) {
            setItem(i, new MenuSlotSelectItem());
        }

    }

    public Setting getSetting() {
        return setting;
    }

}
