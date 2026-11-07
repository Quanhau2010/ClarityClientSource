package need.clarity.api;

import net.minecraft.client.renderer.state.level.LevelRenderState;

public interface IOvertick extends Capability {
    boolean isEnabled();

    boolean isElytraStopHeld();

    void addGhostRenderStates(LevelRenderState arg0, float arg1);

    boolean isGhostState(Object arg0);

    int ghostOpacityAlpha();

}
