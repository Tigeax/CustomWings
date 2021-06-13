package tigeax.customwings.menus.editor.settingmenus.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import tigeax.customwings.menus.editor.settingmenus.WingParticleSettingsMenu;
import tigeax.customwings.util.ParticleItem;
import tigeax.customwings.util.menu.SubMenuItem;
import tigeax.customwings.wing.WingParticle;

public class WingParticleSettingsMenuItem extends SubMenuItem {

    WingParticle wingParticle;

    public WingParticleSettingsMenuItem(WingParticleSettingsMenu wingParticleSettingsMenu) {
        super(wingParticleSettingsMenu);

        WingParticle wingParticle = wingParticleSettingsMenu.getWingParticle();

        this.wingParticle = wingParticle;
        
        setDisplayName(wingParticle.getID());
    }

    @Override
    public ItemStack getFinalItem(Player player) {

        setMaterial(ParticleItem.valueOf(wingParticle.getParticle().toString()).getMaterial());

        return super.getFinalItem(player);
    }

}
