package need.clarity.api;

import net.minecraft.world.phys.Vec3;

public interface IFreecam extends Capability {
    boolean isActive();

    boolean shouldFreezePlayer();

    Vec3 getCameraPos();

    float getCameraYaw();

    float getCameraPitch();

    void setCameraYaw(float arg0);

    void setCameraPitch(float arg0);

}
