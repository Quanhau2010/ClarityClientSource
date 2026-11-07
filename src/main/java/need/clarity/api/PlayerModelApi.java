package need.clarity.api;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public final class PlayerModelApi {
    private PlayerModelApi() {
    }

    private static IPlayerModel p() {
        return ((IPlayerModel) Bus.get(IPlayerModel.class));
    }

    public static boolean isActive() {
        int var5 = 0;
        IPlayerModel var0 = p();
        if (var0 != null) {
            if (var0.isActive()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    public static boolean isModelState(LivingEntityRenderState arg0) {
        int var7 = 0;
        IPlayerModel var1 = p();
        if (var1 != null) {
            if (var1.isModelState(arg0)) {
                var7 = 1;
            }
        } else {
            var7 = 0;
        }
        return var7;
    }

    public static void markModelState(LivingEntityRenderState arg0) {
        IPlayerModel var1 = p();
        if (var1 != null) {
            var1.markModelState(arg0);
        }
    }

    public static void forget(LivingEntityRenderState arg0) {
        IPlayerModel var1 = p();
        if (var1 != null) {
            var1.forget(arg0);
        }
    }

    public static double[] handPose(boolean arg0) {
        double[] var6 = null;
        IPlayerModel var1 = p();
        if (var1 != null) {
            var6 = var1.handPose(arg0);
        } else {
            var6 = null;
        }
        return var6;
    }

}
