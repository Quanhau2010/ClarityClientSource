package need.clarity.modules.utility;

import need.clarity.api.IOvertick;
import net.minecraft.client.renderer.state.level.LevelRenderState;

public final class OvertickCapabilityProvider implements IOvertick {
    private OvertickCapabilityProvider() {
    }

    public boolean isEnabled() {
        int var6 = 0;
        Overtick var1 = Overtick.INSTANCE;
        if (var1 != null) {
            if (var1.isEnabled()) {
                var6 = 1;
            } else {
                var6 = 0;
            }
            return var6;
        }
    }

    public boolean isElytraStopHeld() {
        return false;
    }

    public void addGhostRenderStates(LevelRenderState arg0, float arg1) {
        Overtick var3 = Overtick.INSTANCE;
        if (var3 != null) {
            var3.renderWorldEffect(arg0, arg1);
        }
    }

    public boolean isGhostState(Object arg0) {
        int var8 = 0;
        Overtick var2 = Overtick.INSTANCE;
        if (var2 != null) {
            if (var2.isEligible(arg0)) {
                var8 = 1;
            }
        } else {
            var8 = 0;
        }
        return var8;
    }

    public int ghostOpacityAlpha() {
        int var5 = 0;
        Overtick var1 = Overtick.INSTANCE;
        if (var1 == null) {
            var5 = 255;
        } else {
            var5 = var1.computeColorOrIndex();
        }
        return var5;
    }

}
