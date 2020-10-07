package tigeax.customwings.nms;

import org.bukkit.craftbukkit.v1_16_R2.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

public class NMSSupport {

    public static Float getBodyRotation(LivingEntity entity) {
        return ((CraftLivingEntity)entity).getHandle().aA;
    }

    public static void setBodyRotation(LivingEntity entity, float newRotation) {
        ((CraftLivingEntity)entity).getHandle().aA = newRotation;
    }

}
