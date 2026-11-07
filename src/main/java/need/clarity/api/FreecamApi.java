package need.clarity.api;

import net.minecraft.world.phys.Vec3;

public final class FreecamApi {
    private FreecamApi() {
    }

    private static IFreecam f() {
        return ((IFreecam) Bus.get(IFreecam.class));
    }

    public static boolean isActive() {
        int var5 = 0;
        IFreecam var0 = f();
        if (var0 != null) {
            if (var0.isActive()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    public static boolean shouldFreezePlayer() {
        int var5 = 0;
        IFreecam var0 = f();
        if (var0 != null) {
            if (var0.shouldFreezePlayer()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    public static Vec3 getCameraPos() {
        Vec3 var4 = null;
        IFreecam var0 = f();
        if (var0 != null) {
            var4 = var0.getCameraPos();
        } else {
            var4 = null;
        }
        return var4;
    }

    public static float getCameraYaw() {
        float var4 = 0.0F;
        IFreecam var0 = f();
        if (var0 != null) {
            var4 = var0.getCameraYaw();
        } else {
            var4 = 0.0F;
        }
        return var4;
    }

    public static float getCameraPitch() {
        float var4 = 0.0F;
        IFreecam var0 = f();
        if (var0 != null) {
            var4 = var0.getCameraPitch();
        } else {
            var4 = 0.0F;
        }
        return var4;
    }

    public static void setCameraYaw(float arg0) {
        IFreecam var1 = f();
        if (var1 != null) {
            var1.setCameraYaw(arg0);
        }
    }

    public static void setCameraPitch(float arg0) {
        IFreecam var1 = f();
        if (var1 != null) {
            var1.setCameraPitch(arg0);
        }
    }

}
