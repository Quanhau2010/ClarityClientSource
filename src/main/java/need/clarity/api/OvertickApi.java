package need.clarity.api;

import net.minecraft.client.renderer.state.level.LevelRenderState;

public final class OvertickApi {
    private OvertickApi() {
    }

    private static IOvertick o() {
        return ((IOvertick) Bus.get(IOvertick.class));
    }

    public static boolean isEnabled() {
        int var5 = 0;
        IOvertick var0 = o();
        if (var0 != null) {
            if (var0.isEnabled()) {
                var5 = 1;
            }
        } else {
            var5 = 0;
        }
        return var5;
    }

    public static boolean isElytraStopHeld() {
        int var5 = 0;
        IOvertick var0 = o();
        if (var0 != null) {
            if (var0.isElytraStopHeld()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    public static boolean isGhostState(Object arg0) {
        int var7 = 0;
        IOvertick var1 = o();
        if (var1 != null) {
            if (var1.isGhostState(arg0)) {
                var7 = 1;
            } else {
            }
        }
        var7 = 0;
        return var7;
    }

    public static int ghostOpacityAlpha() {
        int var4 = 0;
        IOvertick var0 = o();
        if (var0 != null) {
            var4 = var0.ghostOpacityAlpha();
        } else {
            var4 = 255;
        }
        return var4;
    }

    public static void addGhostRenderStates(LevelRenderState arg0, float arg1) {
        IOvertick var2 = o();
        if (var2 != null) {
            var2.addGhostRenderStates(arg0, arg1);
        }
    }

}
