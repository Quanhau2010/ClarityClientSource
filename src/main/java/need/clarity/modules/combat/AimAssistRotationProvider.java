package need.clarity.modules.combat;

import need.clarity.api.IRotation;
import net.minecraft.world.phys.Vec3;

public final class AimAssistRotationProvider implements IRotation {
    private AimAssistRotationProvider() {
    }

    public boolean isActive() {
        return AimAssist.isEligible2();
    }

    public boolean hasSilentRotation() {
        return AimAssist.isActivelyTracking();
    }

    public boolean isActivelyTracking() {
        return AimAssist.isEligible1();
    }

    public float getSilentServerYaw() {
        return AimAssist.computeFloatMetric2();
    }

    public float getSilentServerPitch() {
        return AimAssist.computeFloatMetric3();
    }

    public void setSilentRotation(float arg0, float arg1) {
        AimAssist.applyStep(arg0, arg1);
    }

    public void clearSilentRotation() {
        AimAssist.clearSilentRotation();
    }

    public void commitSnappedRotation(float arg0, float arg1) {
        AimAssist.applyStep4(arg0, arg1);
    }

    public void commitUseItemRotation(float arg0, float arg1) {
        AimAssist.setSilentRotation(arg0, arg1);
    }

    public Vec3 getRotationVector(float arg0, float arg1) {
        return AimAssist.evaluate(arg0, arg1);
    }

}
