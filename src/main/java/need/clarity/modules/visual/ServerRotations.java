package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import net.minecraft.util.Mth;

public class ServerRotations extends ModuleBase {
    private static ServerRotations INSTANCE;
    private static float floatValue;
    private static float floatValue1;

    public ServerRotations() {
        super("Server Rotations", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var9 = () -> ServerRotations.isEligible();
        Hooks.bool0("ServerRotations.shouldApply", var9);
        Hooks.Dbl1 var10 = (p0) -> ServerRotations.hook$shouldApply(p0);
        Hooks.dbl1("ServerRotations.pitchLerp", var10);
        Hooks.Act1 var11 = (p0) -> ServerRotations.hook$pitchLerp(p0);
        Hooks.act1("ServerRotations.captureLast", var11);
        Hooks.Act1 var12 = (p0) -> ServerRotations.hook$captureLast(p0);
        Hooks.act1("ServerRotations.captureCurrent", var12);
    }

    public static boolean isEligible() {
        int var3 = 0;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                var3 = 1;
            } else {
                var3 = 0;
            }
            return var3;
        }
    }

    public static void applyStep(float arg0) {
        floatValue = arg0;
    }

    public static void applyStep1(float arg0) {
        floatValue1 = arg0;
    }

    public static float computeFloatMetric(float arg0) {
        float var4 = Mth.clamp(arg0, 0.0F, 1.0F);
        float var6 = floatValue;
        float var7 = floatValue1;
        return Mth.lerp(var4, var6, var7);
    }

}
