package tigeax.customwings.menus.editor;

import tigeax.customwings.configuration.settings.SettingInterface;
import tigeax.customwings.menus.editor.items.GUISlotSelectItem;
import tigeax.customwings.util.menu.ItemMenu;

public class GuiSlotSelectMenu extends ItemMenu {

    private SettingInterface setting;

    public GuiSlotSelectMenu(ItemMenu parent, SettingInterface setting) {
        super("&cSelect GUI Slot", Rows.SIX);

        this.setting = setting;

        setParent(parent);

        for (int i = 0; i < 54; i++) {
            setItem(i, new GUISlotSelectItem());
        }

    }

    public SettingInterface getSetting() {
        return setting;
    }

}
