package tigeax.customwings.menus.editor.settingmenus;

import tigeax.customwings.menus.editor.items.GoBackItem;
import tigeax.customwings.menus.editor.settingmenus.items.WingParticleSettingsMenuItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.Wing;
import tigeax.customwings.wing.WingParticle;

public class WingParticleListMenu extends ItemMenu {

    private Wing wing;

    public WingParticleListMenu(ItemMenu parentMenu, Wing wing) {
        super(wing.getConfig().getGuiItemName() + " - Particles", Rows.SIX);
        this.wing = wing;

        setParent(parentMenu);

        addItems();
    }

    public Wing getWing() {
        return wing;
    }

    public void reload() {
        clearItems();
        addItems();
    }

    private void addItems() {

        int slot = 0;
        
        // Wing particle items
        for (WingParticle wingParticle : wing.getConfig().getWingParticles()) {
            
            WingParticleSettingsMenu wingParticleSettingsMenu = new WingParticleSettingsMenu(wingParticle);
            wingParticleSettingsMenu.setParent(this);

            setItem(slot, new WingParticleSettingsMenuItem(wingParticleSettingsMenu));
            slot++;
        }

        // Go back item
        setItem(53, new GoBackItem());
    }
    
}
