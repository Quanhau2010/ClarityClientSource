package need.clarity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;
import need.clarity.modules.visual.Animations;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionf;

public final class AnimationsRenderer {
    private static long lastNanos;

    private AnimationsRenderer() {
    }

    public static boolean shouldAnimate(HumanoidArm arg0) {
        int var15 = 0;
        Animations var1 = getModule();
        if (var1 != null) {
            if (!var1.isEnabled()) {
            }
        }
        return false;
        Minecraft var8 = Minecraft.getInstance();
        if (var8.player != null) {
            if (var8.player.getMainArm() == arg0) {
                var15 = 1;
            }
        } else {
            var15 = 0;
        }
        return var15;
    }

    public static boolean apply(PoseStack arg0, HumanoidArm arg1, float arg2) {
        int var18 = 0;
        float var91 = 0.0F;
        if (!shouldAnimate(arg1)) {
            return false;
        }
        Animations var21 = getModule();
        if (var21 == null) {
            return false;
        }
        String var25 = var21.getMode();
        int var27 = var21.getStrength();
        float var33 = Mth.sin(((double) (Mth.sqrt(arg2) * 3.1415927F)));
        float var41 = ((float) Math.sin((((double) arg2) * 3.141592653589793)));
        HumanoidArm var92 = HumanoidArm.LEFT;
        if (arg1 == var92) {
            var91 = -1.0F;
        } else {
            var91 = 1.0F;
        }
        float var15 = var91;
        float var45 = ((float) Mth.clamp(var27, 20, 75));
        if (SwingEngine.handles(var25)) {
            applyEngine(arg0, var15, var25, arg2, var45);
            return true;
        }
        var18 = -1;
        switch (var25.hashCode()) {
            case -1984934636 -> {}
            case -1984934635 -> {}
            case -1984934634 -> {}
            case -1984934633 -> {}
            case -1984934632 -> {}
            default -> {}
        }
        if (var25.equals("Mode 4")) {
            var18 = 3;
            if (var25.equals("Mode 1")) {
                var18 = 0;
                if (var25.equals("Mode 2")) {
                    var18 = 1;
                    if (var25.equals("Mode 3")) {
                        var18 = 2;
                        if (var25.equals("Mode 5")) {
                            var18 = 4;
                        }
                        switch (var18) {
                            case 0 -> {}
                            case 1 -> {}
                            case 2 -> {}
                            case 3 -> {}
                            case 4 -> {}
                            default -> {}
                        }
                        applyEquipOffset(arg0, arg1);
                        Axis var113 = Axis.XP;
                        float var150 = (arg2 * -360.0F);
                        Quaternionf var114 = var113.rotationDegrees(var150);
                        arg0.mulPose(var114);
                        applyEquipOffset(arg0, arg1);
                        Quaternionf var98 = Axis.XP.rotationDegrees(50.0F);
                        arg0.mulPose(var98);
                        Axis var99 = Axis.YP;
                        float var137 = (var15 * -60.0F);
                        Quaternionf var100 = var99.rotationDegrees(var137);
                        arg0.mulPose(var100);
                        Axis var101 = Axis.ZP;
                        float var184 = (var45 * var33);
                        float var165 = (110.0F + var184);
                        float var139 = (var15 * var165);
                        Quaternionf var102 = var101.rotationDegrees(var139);
                        arg0.mulPose(var102);
                        return false;
                        applyEquipOffset(arg0, arg1);
                        Quaternionf var124 = Axis.XP.rotationDegrees(50.0F);
                        arg0.mulPose(var124);
                        Axis var125 = Axis.YP;
                        float var189 = (1.0F - var33);
                        float var180 = ((-30.0F * var189) - 30.0F);
                        float var193 = ((var45 - 20.0F) * var33);
                        float var181 = (var180 + var193);
                        float var160 = (var15 * var181);
                        Quaternionf var126 = var125.rotationDegrees(var160);
                        arg0.mulPose(var126);
                        Axis var127 = Axis.ZP;
                        float var162 = (var15 * 110.0F);
                        Quaternionf var128 = var127.rotationDegrees(var162);
                        arg0.mulPose(var128);
                        applyEquipOffset(arg0, arg1);
                        Axis var116 = Axis.YP;
                        float var152 = (var15 * 90.0F);
                        Quaternionf var117 = var116.rotationDegrees(var152);
                        arg0.mulPose(var117);
                        Axis var118 = Axis.ZP;
                        float var154 = (var15 * -30.0F);
                        Quaternionf var119 = var118.rotationDegrees(var154);
                        arg0.mulPose(var119);
                        Axis var120 = Axis.XP;
                        float var176 = (var45 * var41);
                        float var157 = ((-90.0F - var176) + 10.0F);
                        Quaternionf var121 = var120.rotationDegrees(var157);
                        arg0.mulPose(var121);
                        applyEquipOffset(arg0, arg1);
                        Axis var104 = Axis.YP;
                        float var186 = (var41 * -20.0F);
                        float var167 = (45.0F + var186);
                        float var141 = (var15 * var167);
                        Quaternionf var105 = var104.rotationDegrees(var141);
                        arg0.mulPose(var105);
                        Axis var106 = Axis.ZP;
                        float var144 = ((var15 * var33) * -20.0F);
                        Quaternionf var107 = var106.rotationDegrees(var144);
                        arg0.mulPose(var107);
                        Axis var108 = Axis.XP;
                        float var146 = (var33 * -80.0F);
                        Quaternionf var109 = var108.rotationDegrees(var146);
                        arg0.mulPose(var109);
                        Axis var110 = Axis.YP;
                        float var148 = (var15 * -45.0F);
                        Quaternionf var111 = var110.rotationDegrees(var148);
                        arg0.mulPose(var111);
                        return true;
                    }
                }
            }
        }
    }

    private static void applyEngine(PoseStack arg0, float arg1, String arg2, float arg3, float arg4) {
        float var20 = 0.0F;
        long var5 = System.nanoTime();
        if (lastNanos == 0L) {
            var20 = 0.0F;
        } else {
            long var83 = lastNanos;
            var20 = (((float) (var5 - var83)) / 1.0E9F);
        }
        float var15 = var20;
        if (Float.compare(var15, 5.0E-4F) <= 0) {
            if (lastNanos == 0L) {
            }
        }
        lastNanos = var5;
        float var86 = ((arg4 - 20.0F) / 55.0F);
        SwingEngine.step(arg2, arg3, var15, var86);
        float[] var29 = SwingEngine.pose;
        float var88 = var29[0];
        float var68 = (0.56F + var88);
        float var48 = (arg1 * var68);
        float var90 = var29[1];
        float var70 = (-0.52F + var90);
        float var106 = var29[2];
        float var92 = (-0.72F + var106);
        arg0.translate(var48, var70, var92);
        if (var29[6] != 0.0F) {
            arg0.translate(0.0F, 0.12F, 0.0F);
            Axis var52 = Axis.XP;
            float var73 = var29[6];
            Quaternionf var53 = var52.rotationDegrees(var73);
            arg0.mulPose(var53);
            arg0.translate(0.0F, -0.12F, 0.0F);
        }
        Axis var55 = Axis.YP;
        float var108 = var29[3];
        float var97 = (45.0F + var108);
        float var76 = (arg1 * var97);
        Quaternionf var56 = var55.rotationDegrees(var76);
        arg0.mulPose(var56);
        Axis var57 = Axis.ZP;
        float var99 = var29[4];
        float var78 = (arg1 * var99);
        Quaternionf var58 = var57.rotationDegrees(var78);
        arg0.mulPose(var58);
        Axis var59 = Axis.XP;
        float var80 = var29[5];
        Quaternionf var60 = var59.rotationDegrees(var80);
        arg0.mulPose(var60);
        Axis var61 = Axis.YP;
        float var82 = (arg1 * -45.0F);
        Quaternionf var62 = var61.rotationDegrees(var82);
        arg0.mulPose(var62);
    }

    private static void applyEquipOffset(PoseStack arg0, HumanoidArm arg1) {
        float var8 = 0.0F;
        HumanoidArm var3 = HumanoidArm.RIGHT;
        if (arg1 == var3) {
            var8 = 1.0F;
        } else {
            var8 = -1.0F;
        }
        float var10 = (var8 * 0.56F);
        arg0.translate(var10, -0.52F, -0.72F);
    }

    private static Animations getModule() {
        Animations var8 = null;
        ModuleBase var3 = ModuleRegistry.get("Animations");
        if ((var3 instanceof Animations)) {
            var8 = ((Animations) var3);
        } else {
            var8 = null;
        }
        return var8;
    }

    static {
        lastNanos = 0L;
    }

}
