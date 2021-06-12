package tigeax.customwings.configuration.settings;

import tigeax.customwings.CustomWings;
import tigeax.customwings.configuration.WingConfig;
import tigeax.customwings.wing.WingParticle;

public enum WingParticleSetting implements SettingInterface {

    PARTICLE("particle", SettingType.PARTICLE),
    DISTANCE("distance", SettingType.PARTICLE),
    HEIGHT("height", SettingType.PARTICLE),
    ANGLE("angle", SettingType.PARTICLE),
    SPEED("speed", SettingType.PARTICLE),
    COLOR("color", SettingType.COLOR),
    BLOCK_TYPE("blockType", SettingType.MATERIAL);

    protected String path;
    private SettingType settingType;

    WingParticleSetting(final String path, final SettingType settingType) {
        this.path = path;
        this.settingType = settingType;
    }

    @Override
    public SettingType getSettingType() {
        return settingType;
    }

    public void setValue(WingParticle wingParticle, WingConfig wingConfig, Object value) {
        wingConfig.set("Particles." + wingParticle.getID() + "." + this.path, value);
        wingConfig.save();
        CustomWings.getInstance().reload();
    }

}
