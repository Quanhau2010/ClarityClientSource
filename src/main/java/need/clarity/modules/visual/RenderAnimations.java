package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;

public class RenderAnimations extends ModuleBase {
    public static RenderAnimations INSTANCE;

    public RenderAnimations() {
        super("Render Animations", "Visual");
        INSTANCE = this;
        Hooks.Dbl0 var6 = () -> RenderAnimations.computeFloatMetric();
        Hooks.dbl0("RenderAnimations.factor", var6);
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

    public static float computeFloatMetric() {
        float var43 = 0.0F;
        int var12 = 0;
        RenderAnimations var0 = INSTANCE;
        if (var0 != null) {
            if (!var0.isEnabled()) {
            }
        }
        return 1.0F;
        String var17 = var0.getMode();
        if ("None".equals(var17)) {
            return 1.0F;
        }
        double var56 = (((((double) System.nanoTime()) / 1.0E9) * 3.141592653589793) * 2.0);
        double var69 = ((double) var0.getSpeed());
        float var62 = ((float) Math.sin((var56 * var69)));
        float var45 = (0.5F * var62);
        float var28 = (0.5F + var45);
        var12 = -1;
        switch (var17.hashCode()) {
            case 77474681 -> {}
            case 1997911168 -> {}
            default -> {}
        }
        if (var17.equals("Pulse")) {
            var12 = 0;
            if (var17.equals("Breath")) {
                var12 = 1;
            }
            switch (var12) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            float var50 = (0.55F * var28);
            var43 = (0.45F + var50);
            float var48 = (0.3F * var28);
            var43 = (0.7F + var48);
            var43 = 1.0F;
            return var43;
        }
    }

    public String getMode() {
        return ((String) this.getSetting("Mode", "Pulse"));
    }

    public float getSpeed() {
        Float var7 = 0.5F;
        return ((Float) this.getSetting("Speed", var7));
    }

}
