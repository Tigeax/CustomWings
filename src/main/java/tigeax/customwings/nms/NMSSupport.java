package tigeax.customwings.nms;

import org.bukkit.entity.LivingEntity;
import tigeax.customwings.CustomWings;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NMSSupport {

    public static Float getBodyRotation(LivingEntity livingEntity) {
        try {
            String version = CustomWings.getServerVersion();

            Class<?> entity = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftLivingEntity");
            Method handle = entity.getMethod("getHandle");
            Object nmsEntity = handle.invoke(livingEntity);
            Field bodyRotation = nmsEntity.getClass().getField(getBodyRotationField(version));
            return (Float) bodyRotation.get(nmsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setBodyRotation(LivingEntity livingEntity, float newRotation) {
        try {
            String version = CustomWings.getServerVersion();
            Class<?> entity = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftLivingEntity");
            Method handle = entity.getMethod("getHandle");
            Object nmsEntity = handle.invoke(livingEntity);
            Field bodyRotation = nmsEntity.getClass().getField(getBodyRotationField(version));
            bodyRotation.set(nmsEntity, newRotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getBodyRotationField(String version) {
        switch (version) {
            case "v1_16_R2":
                return "aA";
            case "v1_16_R1":
                return "aH";
            case "v1_15_R1":
                return "aI";
            case "v1_14_R1":
                return "aK";
            case "v1_13_R1":
            case "v1_13_R2":
                return "aQ";
            default:
                return "";
        }
    }

}
