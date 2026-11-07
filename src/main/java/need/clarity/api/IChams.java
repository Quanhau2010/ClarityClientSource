package need.clarity.api;

import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Avatar;

public interface IChams extends Capability {
    boolean isEnabled();

    boolean isNormal();

    boolean isThroughWalls();

    boolean isHideSelf();

    boolean isRenderArmour();

    boolean isArmourThroughWalls();

    void updateState(Avatar arg0, AvatarRenderState arg1);

    Integer getStateTint(LivingEntityRenderState arg0);

}
