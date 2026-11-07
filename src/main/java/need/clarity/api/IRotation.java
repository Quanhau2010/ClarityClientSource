package need.clarity.api;

import net.minecraft.world.phys.Vec3;

public interface IRotation extends Capability {
    boolean isActive();

    boolean hasSilentRotation();

    boolean isActivelyTracking();

    float getSilentServerYaw();

    float getSilentServerPitch();

    void setSilentRotation(float arg0, float arg1);

    void clearSilentRotation();

    void commitSnappedRotation(float arg0, float arg1);

    void commitUseItemRotation(float arg0, float arg1);

    Vec3 getRotationVector(float arg0, float arg1);

}
