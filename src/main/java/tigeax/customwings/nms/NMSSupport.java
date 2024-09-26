package tigeax.customwings.nms;

import org.bukkit.entity.LivingEntity;
import tigeax.customwings.CustomWings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NMSSupport {

    public static Float getBodyRotation(LivingEntity livingEntity) {
        try {
            String bukkitVersion = CustomWings.getInstance().getBukkitVersion();

            Class<?> entity;
            if (bukkitVersion.isEmpty()) {
                entity = Class.forName("org.bukkit.craftbukkit.entity.CraftLivingEntity");
            } else {
                entity = Class.forName("org.bukkit.craftbukkit." + bukkitVersion + ".entity.CraftLivingEntity");
            }

            Method handle = entity.getMethod("getHandle");
            Object nmsEntity = handle.invoke(livingEntity);
            Field bodyRotation = nmsEntity.getClass().getField(getBodyRotationField(CustomWings.getInstance().getServerVersion()));
            return (Float) bodyRotation.get(nmsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setBodyRotation(LivingEntity livingEntity, float newRotation) {
        try {
            String bukkitVersion = CustomWings.getInstance().getBukkitVersion();

            Class<?> entity;
            if (bukkitVersion.isEmpty()) {
                entity = Class.forName("org.bukkit.craftbukkit.entity.CraftLivingEntity");
            } else {
                entity = Class.forName("org.bukkit.craftbukkit." + bukkitVersion + ".entity.CraftLivingEntity");
            }

            Method handle = entity.getMethod("getHandle");
            Object nmsEntity = handle.invoke(livingEntity);
            Field bodyRotation = nmsEntity.getClass().getField(getBodyRotationField(CustomWings.getInstance().getServerVersion()));
            bodyRotation.set(nmsEntity, newRotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getBodyRotationField(String version) {
        switch (version) {
            case "1.21.1":
            case "1.21":
                return "aY";
            case "1.20.1":
                return "aV";
            case "1.19.3":
                return "aT";
            case "1.19.2":
                return "aX";
            case "1.19.1":
            case "1.18.2":
            case "1.18.1":
                return "aY";
            case "1.17.1":
                return "aX";
            case "1.16.3":
            case "1.16.2":
                return "aA";
            case "1.16.1":
                return "aH";
            case "1.15.1":
                return "aI";
            case "1.14.1":
                return "aK";
            case "1.13.1":
            case "1.13.2":
                return "aQ";
            default:
                return "aY"; // Use latest value by default, allowing newer versions to still work as long as nms was not changed
        }
    }

}
