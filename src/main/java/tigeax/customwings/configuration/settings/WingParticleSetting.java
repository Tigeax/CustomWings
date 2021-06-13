package tigeax.customwings.configuration.settings;

import org.bukkit.Material;

import tigeax.customwings.CustomWings;
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

    public void setValue(Object value, WingParticle wingParticle) {

        if (value instanceof Material) {
            value = value.toString();
        }

        wingParticle.getParticleConfig().set(this.path, value);
        wingParticle.getWingConfig().save();
        CustomWings.getInstance().reload();
    }

    public Object getCurrentValue(WingParticle wingParticle) {
        
        switch(this) {
            case PARTICLE:
                return wingParticle.getParticle();
            case DISTANCE:
                return wingParticle.getDistance();
            case HEIGHT:
                return wingParticle.getHeight();
            case ANGLE:
                return wingParticle.getAngle();
            case SPEED:
                return wingParticle.getSpeed();
            case COLOR:
                return wingParticle.getDustOptions().getColor().asRGB();
            case BLOCK_TYPE:
                return wingParticle.getMaterialData();
            
        }
        return null;

    }

}
