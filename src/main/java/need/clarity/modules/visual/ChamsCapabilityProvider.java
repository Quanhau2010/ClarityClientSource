package need.clarity.modules.visual;

import need.clarity.api.IChams;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Avatar;

public final class ChamsCapabilityProvider implements IChams {
    private ChamsCapabilityProvider() {
    }

    public boolean isEnabled() {
        int var4 = 0;
        if (Chams.INSTANCE != null) {
            if (Chams.INSTANCE.isEnabled()) {
                var4 = 1;
            } else {
            }
        }
        var4 = 0;
        return var4;
    }

    public boolean isNormal() {
        return Chams.isEligible();
    }

    public boolean isThroughWalls() {
        return Chams.isThroughWalls();
    }

    public boolean isHideSelf() {
        return Chams.isHideSelf();
    }

    public boolean isRenderArmour() {
        return Chams.isRenderArmour();
    }

    public boolean isArmourThroughWalls() {
        return Chams.isArmourThroughWalls();
    }

    public void updateState(Avatar arg0, AvatarRenderState arg1) {
        Chams.applyStep(arg0, arg1);
    }

    public Integer getStateTint(LivingEntityRenderState arg0) {
        return Chams.evaluate(arg0);
    }

}
