package need.clarity.modules.visual;

import need.clarity.api.IFreecam;
import net.minecraft.world.phys.Vec3;

public final class FreecamCapabilityProvider implements IFreecam {
    private FreecamCapabilityProvider() {
    }

    public boolean isActive() {
        return Freecam.enabledState;
    }

    public boolean shouldFreezePlayer() {
        return Freecam.isFreezePlayer();
    }

    public Vec3 getCameraPos() {
        return Freecam.positionVec;
    }

    public float getCameraYaw() {
        return Freecam.floatValue;
    }

    public float getCameraPitch() {
        return Freecam.floatValue1;
    }

    public void setCameraYaw(float arg0) {
        Freecam.floatValue = arg0;
    }

    public void setCameraPitch(float arg0) {
        Freecam.floatValue1 = arg0;
    }

}
