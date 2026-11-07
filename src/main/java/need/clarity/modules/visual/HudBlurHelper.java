package need.clarity.modules.visual;


public final class HudBlurHelper {
    private HudBlurHelper() {
    }

    public static boolean isEligible() {
        int var5 = 0;
        HUD var0 = HUD.INSTANCE;
        if (var0 != null) {
            if (var0.isAutoScale()) {
            }
        }
        var5 = 1;
        var5 = 0;
        return var5;
    }

    public static float computeFloatMetric() {
        float var6 = 0.0F;
        HUD var0 = HUD.INSTANCE;
        if (var0 != null) {
            var6 = var0.getOverlayScale();
        } else {
            var6 = 1.0F;
        }
        float var4 = var6;
        float var10 = Math.min(3.0F, var4);
        return Math.max(0.5F, var10);
    }

    public static float computeFloatMetric(float arg0, float arg1) {
        if (!isEligible()) {
            return computeFloatMetric();
        }
        float var8 = (arg1 / 720.0F);
        float var12 = Math.min(4.0F, var8);
        return Math.max(0.75F, var12);
    }

    public static int computeColorOrIndex() {
        String var13 = null;
        HUD var0 = HUD.INSTANCE;
        if (var0 != null) {
            var13 = var0.getSupersample();
        } else {
            var13 = "2x";
        }
        String var3 = var13;
        if ("Off".equals(var3)) {
            return 1;
        }
        if ("3x".equals(var3)) {
            return 3;
        }
        return 2;
    }

}
