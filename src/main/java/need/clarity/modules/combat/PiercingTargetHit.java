package need.clarity.modules.combat;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public record PiercingTargetHit(LivingEntity target, Vec3 location) {

}
