package need.clarity.modules.visual;

import need.clarity.modules.ModuleBase;

public class MotionBlur extends ModuleBase {
    public static MotionBlur INSTANCE;

    public MotionBlur() {
        super("Motion Blur", "Visual");
        INSTANCE = this;
    }

    public static boolean isEligible() {
        int var5 = 0;
        MotionBlur var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                var5 = 1;
            }
        } else {
            var5 = 0;
        }
        return var5;
    }

    public static float getAmount() {
        float var4 = 0.0F;
        if (INSTANCE == null) {
            var4 = 40.0F;
        } else {
            MotionBlur var9 = INSTANCE;
            Float var17 = 40.0F;
            var4 = ((Float) var9.getSetting("Amount", var17));
        }
        float var3 = var4;
        float var12 = Math.min(100.0F, var3);
        return ((Math.max(1.0F, var12) / 100.0F) / 24.0F);
    }

    public static int computeColorOrIndex() {
        int var16 = 0;
        String var20 = null;
        int var5 = 0;
        if (INSTANCE == null) {
            var20 = "Medium";
        } else {
            var20 = ((String) INSTANCE.getSetting("Quality", "Medium"));
        }
        String var3 = var20;
        var5 = -1;
        switch (var3.hashCode()) {
            case 76596 -> {}
            case 2249154 -> {}
            default -> {}
        }
        if (var3.equals("Low")) {
            var5 = 0;
            if (var3.equals("High")) {
                var5 = 1;
            }
            switch (var5) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            var16 = 16;
            var16 = 6;
            var16 = 10;
            return var16;
        }
    }

    public static float getMaxBlur() {
        float var10 = 0.0F;
        if (INSTANCE == null) {
            var10 = 6.0F;
        } else {
            MotionBlur var7 = INSTANCE;
            Float var15 = 6.0F;
            var10 = ((Float) var7.getSetting("Max Blur", var15));
        }
        float var3 = var10;
        float var11 = Math.min(20.0F, var3);
        return (Math.max(1.0F, var11) / 100.0F);
    }

}
