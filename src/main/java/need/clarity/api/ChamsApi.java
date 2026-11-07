package need.clarity.api;

import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.Avatar;

public final class ChamsApi {
    private ChamsApi() {
    }

    private static IChams c() {
        return ((IChams) Bus.get(IChams.class));
    }

    public static boolean isEnabled() {
        int var5 = 0;
        IChams var0 = c();
        if (var0 != null) {
            if (var0.isEnabled()) {
                var5 = 1;
            }
        } else {
            var5 = 0;
        }
        return var5;
    }

    public static boolean isNormal() {
        int var5 = 0;
        IChams var0 = c();
        if (var0 != null) {
            if (var0.isNormal()) {
                var5 = 1;
            }
        } else {
            var5 = 0;
        }
        return var5;
    }

    public static boolean isThroughWalls() {
        int var5 = 0;
        IChams var0 = c();
        if (var0 != null) {
            if (var0.isThroughWalls()) {
                var5 = 1;
            }
        } else {
            var5 = 0;
        }
        return var5;
    }

    public static boolean isHideSelf() {
        int var5 = 0;
        IChams var0 = c();
        if (var0 != null) {
            if (var0.isHideSelf()) {
                var5 = 1;
            } else {
            }
        }
        var5 = 0;
        return var5;
    }

    public static boolean isRenderArmour() {
        int var5 = 0;
        IChams var0 = c();
        if (var0 != null) {
            if (var0.isRenderArmour()) {
                var5 = 1;
            }
        } else {
            var5 = 0;
        }
        return var5;
    }

    public static boolean isArmourThroughWalls() {
        int var5 = 0;
        IChams var0 = c();
        if (var0 != null) {
            if (var0.isArmourThroughWalls()) {
                var5 = 1;
            }
        } else {
            var5 = 0;
        }
        return var5;
    }

    public static void updateState(Avatar arg0, AvatarRenderState arg1) {
        IChams var2 = c();
        if (var2 != null) {
            var2.updateState(arg0, arg1);
        }
    }

    public static Integer getStateTint(LivingEntityRenderState arg0) {
        Integer var5 = null;
        IChams var1 = c();
        if (var1 != null) {
            var5 = var1.getStateTint(arg0);
        } else {
            var5 = null;
        }
        return var5;
    }

}
