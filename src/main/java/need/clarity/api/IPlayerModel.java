package need.clarity.api;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public interface IPlayerModel extends Capability {
    boolean isActive();

    boolean isModelState(LivingEntityRenderState arg0);

    void markModelState(LivingEntityRenderState arg0);

    void forget(LivingEntityRenderState arg0);

    double[] handPose(boolean arg0);

}
