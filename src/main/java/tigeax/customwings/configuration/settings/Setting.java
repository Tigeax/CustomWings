package tigeax.customwings.configuration.settings;

import tigeax.customwings.wing.Wing;
import tigeax.customwings.wing.WingParticle;


/** Wrapper class for the {@link ConfigSetting}, {@link wingSetting} and {@link wingParticleSetting} Enums */
public class Setting implements SettingInterface {

    SettingInterface setting;
    Wing wing;
    WingParticle wingParticle;
    
    public Setting(ConfigSetting configSetting) {
        this.setting = configSetting;
    }

    public Setting(WingSetting wingSetting, Wing wing) {
        this.setting = wingSetting;
        this.wing = wing;
    }

    public Setting(WingParticleSetting wingParticleSetting, WingParticle wingParticle) {
        this.setting = wingParticleSetting;
        this.wingParticle = wingParticle;
    }

    public SettingType getSettingType() {
        return setting.getSettingType();
    }

    public Object getCurrentValue() {
        
        if (setting instanceof ConfigSetting) {
            ConfigSetting configSetting = (ConfigSetting) setting;
            return configSetting.getCurrentValue();
        }

        if (setting instanceof WingSetting) {
            WingSetting wingSetting = (WingSetting) setting;
            return wingSetting.getCurrentValue(wing);
        }

        if (setting instanceof WingParticleSetting) {
            WingParticleSetting wingParticleSetting = (WingParticleSetting) setting;
            return wingParticleSetting.getCurrentValue(wingParticle);
        }

        return null;

    }

    public void setValue(Object value) {
        
        if (setting instanceof ConfigSetting) {
            ConfigSetting configSetting = (ConfigSetting) setting;
            configSetting.setValue(value);
        }

        if (setting instanceof WingSetting) {
            WingSetting wingSetting = (WingSetting) setting;
            wingSetting.setValue(value, wing);
        }

        if (setting instanceof WingParticleSetting) {
            WingParticleSetting wingParticleSetting = (WingParticleSetting) setting;
            wingParticleSetting.setValue(value, wingParticle);
        }
        
    }

}
