package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class Aspect extends ModuleBase {
    public static Aspect INSTANCE;

    public Aspect() {
        super("Aspect", "Visual");
        INSTANCE = this;
        Hooks.Bool0 var7 = () -> Aspect.isEligible();
        Hooks.bool0("Aspect.isActive", var7);
        Hooks.Dbl0 var8 = () -> Aspect.isActive();
        Hooks.dbl0("Aspect.factor", var8);
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

    public static float isActive() {
        float var9 = 0.0F;
        if (INSTANCE != null) {
            Aspect var4 = INSTANCE;
            Float var12 = 1.0F;
            var9 = ((Float) var4.getSetting("Aspect", var12));
        } else {
            var9 = 1.0F;
        }
        float var3 = var9;
        float var11 = Math.min(2.0F, var3);
        return Math.max(0.5F, var11);
    }

}
