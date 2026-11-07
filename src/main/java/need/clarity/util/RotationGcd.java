package need.clarity.util;

import need.clarity.config.ClarityConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public final class RotationGcd {
    private RotationGcd() {
    }

    public static boolean enabled() {
        int var13 = 0;
        try {
            Object var4 = ClarityConfig.getSetting("Kill Aura", "GCD Fix");
            if ((var4 instanceof Boolean)) {
                if (((Boolean) var4)) {
                }
            }
            var13 = 1;
            var13 = 0;
        } catch (Throwable ignored) {
        }
        return var13;
    }

    public static float step() {
        try {
            double var17 = ((((Double) Minecraft.getInstance().options.sensitivity().get()) * 0.6) + 0.2);
            float var29 = ((float) ((((var17 * var17) * var17) * 8.0) * 0.15));
        } catch (Throwable ignored) {
        }
        return var29;
    }

    public static float snapYaw(float arg0, float arg1) {
        float var2 = step();
        if (Float.compare(var2, 1.0E-4F) < 0) {
            return arg1;
        }
        float var20 = (((float) Math.round((Mth.wrapDegrees((arg1 - arg0)) / var2))) * var2);
        return (arg0 + var20);
    }

    public static float snapPitch(float arg0, float arg1) {
        float var7 = 0.0F;
        float var2 = step();
        if (Float.compare(var2, 1.0E-4F) < 0) {
            return Mth.clamp(arg1, -90.0F, 90.0F);
        }
        float var31 = (((float) Math.round(((arg1 - arg0) / var2))) * var2);
        var7 = (arg0 + var31);
        while (true) {
            if (Float.compare(var7, 90.0F) <= 0) break;
            var7 = (var7 - var2);
        }
        while (true) {
            if (Float.compare(var7, -90.0F) >= 0) break;
            var7 = (var7 + var2);
        }
        return var7;
    }

}
