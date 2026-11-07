package need.clarity.api;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class Rotation {
    private Rotation() {
    }

    private static IRotation r() {
        return ((IRotation) Bus.get(IRotation.class));
    }

    public static boolean isActive() {
        int var5 = 0;
        IRotation var0 = r();
        if (var0 != null) {
            if (var0.isActive()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    public static boolean hasSilentRotation() {
        int var5 = 0;
        IRotation var0 = r();
        if (var0 != null) {
            if (var0.hasSilentRotation()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    public static boolean isActivelyTracking() {
        int var5 = 0;
        IRotation var0 = r();
        if (var0 != null) {
            if (var0.isActivelyTracking()) {
                var5 = 1;
            } else {
            }
        }
        var5 = 0;
        return var5;
    }

    public static float getSilentServerYaw() {
        float var4 = 0.0F;
        IRotation var0 = r();
        if (var0 != null) {
            var4 = var0.getSilentServerYaw();
        } else {
            var4 = 0.0F;
        }
        return var4;
    }

    public static float getSilentServerPitch() {
        float var4 = 0.0F;
        IRotation var0 = r();
        if (var0 != null) {
            var4 = var0.getSilentServerPitch();
        } else {
            var4 = 0.0F;
        }
        return var4;
    }

    public static void setSilentRotation(float arg0, float arg1) {
        IRotation var2 = r();
        if (var2 != null) {
            var2.setSilentRotation(arg0, arg1);
        }
    }

    public static void clearSilentRotation() {
        IRotation var0 = r();
        if (var0 != null) {
            var0.clearSilentRotation();
        }
    }

    public static void commitSnappedRotation(float arg0, float arg1) {
        IRotation var2 = r();
        if (var2 != null) {
            var2.commitSnappedRotation(arg0, arg1);
        }
    }

    public static void commitUseItemRotation(float arg0, float arg1) {
        IRotation var2 = r();
        if (var2 != null) {
            var2.commitUseItemRotation(arg0, arg1);
        }
    }

    public static Vec3 getRotationVector(float arg0, float arg1) {
        IRotation var2 = r();
        if (var2 != null) {
            return var2.getRotationVector(arg0, arg1);
        }
        float var20 = (arg0 * 0.017453292F);
        float var22 = (arg1 * 0.017453292F);
        float var26 = Mth.cos(((double) var22));
        float var30 = Mth.sin(((double) var22));
        float var34 = Mth.cos(((double) var20));
        float var38 = Mth.sin(((double) var20));
        double var44 = ((double) (var30 * var34));
        double var46 = ((double) var38);
        double var49 = ((double) (var26 * var34));
        Vec3 var39 = new Vec3(var44, var46, var49);
        return var39;
    }

}
