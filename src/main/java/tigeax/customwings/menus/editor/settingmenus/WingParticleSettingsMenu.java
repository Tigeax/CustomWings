package tigeax.customwings.menus.editor.settingmenus;

import org.bukkit.Material;

import tigeax.customwings.configuration.settings.Setting;
import tigeax.customwings.configuration.settings.WingParticleSetting;
import tigeax.customwings.menus.editor.items.GoBackItem;
import tigeax.customwings.menus.editor.selectvaluemenus.items.SettingItem;
import tigeax.customwings.util.menu.ItemMenu;
import tigeax.customwings.wing.WingParticle;

public class WingParticleSettingsMenu extends ItemMenu {

    private WingParticle wingParticle;

    public WingParticleSettingsMenu(WingParticle wingParticle) {
        super(wingParticle.getWingConfig().getGuiItemName() + " - " + wingParticle.getID(), Rows.SIX);
        this.wingParticle = wingParticle;

        setItem(22, new SettingItem(new Setting(WingParticleSetting.PARTICLE, wingParticle), "&3Particle", Material.DIAMOND_LEGGINGS));

        setItem(39, new SettingItem(new Setting(WingParticleSetting.BLOCK_TYPE, wingParticle), "&3Block type"));

        setItem(53, new GoBackItem());
    }

    public WingParticle getWingParticle() {
        return wingParticle;
    }
    
}
