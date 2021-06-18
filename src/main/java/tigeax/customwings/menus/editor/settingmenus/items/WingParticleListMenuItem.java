package tigeax.customwings.menus.editor.settingmenus.items;

import org.bukkit.Material;

import tigeax.customwings.menus.editor.settingmenus.WingParticleListMenu;
import tigeax.customwings.util.menu.SubMenuItem;

public class WingParticleListMenuItem extends SubMenuItem {

    public WingParticleListMenuItem(WingParticleListMenu wingParticleListMenu) {
        super(wingParticleListMenu);
        
        setDisplayName("&3Particles");
        setMaterial(Material.ELYTRA);
    }

}
