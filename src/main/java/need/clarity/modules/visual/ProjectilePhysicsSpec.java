package need.clarity.modules.visual;

import net.minecraft.world.phys.Vec3;

public record ProjectilePhysicsSpec(Vec3 start, Vec3 dir, double speed, double gravity, double drag) {

}
