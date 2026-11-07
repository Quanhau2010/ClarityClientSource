package need.clarity.modules.visual;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SmoothCamera extends ModuleBase {
    private static SmoothCamera INSTANCE;
    private static boolean rotInit;
    private static boolean posInit;
    private static float yaw;
    private static float pitch;
    private static double doubleValue;
    private static double doubleValue1;
    private static double doubleValue2;
    private static long lastRot;
    private static long lastPos;

    public SmoothCamera() {
        super("Smooth Camera", "Visual");
        INSTANCE = this;
    }

    public void onDisable() {
        rotInit = false;
        posInit = false;
    }

    private static boolean isEligible() {
        SmoothCamera var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                return (!Freecam.isEligible());
            }
        }
    }

    public static float[] smooth(float arg0, float arg1) {
        if (!isEligible()) {
            rotInit = false;
            return null;
        }
        long var2 = System.nanoTime();
        double var4 = (((double) (var2 - lastRot)) / 1.0E9);
        lastRot = var2;
        if (rotInit) {
            if (var4 <= 0.25) {
                if (var4 <= 0.0) {
                }
            }
            yaw = arg0;
            pitch = arg1;
            rotInit = true;
            return null;
            float var6 = clamp(((Float) INSTANCE.getSetting("Follow Speed", 5.0F)), 0.5F, 30.0F);
            float var7 = ((float) (1.0 - Math.exp((((double) -(var6)) * var4))));
            yaw = wrap((yaw + (wrap((arg0 - yaw)) * var7)));
            if (((Boolean) INSTANCE.getSetting("Smooth Pitch", true))) {
            } else {
            }
            pitch = arg1;
            new float[2][0] = yaw;
            new float[2][1] = pitch;
            return new float[2];
        }
    }

    public static Vec3 smoothPosition(Vec3 arg0, Entity arg1, float arg2) {
        Vec3 var16 = null;
        if (isEligible()) {
            if (arg0 != null) {
                if (arg1 == null) {
                }
            }
            posInit = false;
            if (arg0 == null) {
            } else {
            }
            rotInit = rotInit;
            return null;
            if (!((Boolean) INSTANCE.getSetting("Lag Behind", true))) {
                posInit = false;
                return null;
            }
            long var3 = System.nanoTime();
            double var5 = (((double) (var3 - lastPos)) / 1.0E9);
            lastPos = var3;
            double var7 = (arg0.x - doubleValue);
            double var9 = (arg0.y - doubleValue1);
            double var11 = (arg0.z - doubleValue2);
            if (posInit) {
                if (var5 <= 0.25) {
                    if (var5 > 0.0) {
                        if ((((var7 * var7) + (var9 * var9)) + (var11 * var11)) > 400.0) {
                        }
                    }
                    doubleValue = arg0.x;
                    doubleValue1 = arg0.y;
                    doubleValue2 = arg0.z;
                    posInit = true;
                    return null;
                    float var13 = clamp(((Float) INSTANCE.getSetting("Position Speed", 4.0F)), 0.5F, 30.0F);
                    double var14 = (1.0 - Math.exp((((double) -(var13)) * var5)));
                    doubleValue = (doubleValue + (var7 * var14));
                    doubleValue1 = (doubleValue1 + (var9 * var14));
                    doubleValue2 = (doubleValue2 + (var11 * var14));
                    var16 = new Vec3(doubleValue, doubleValue1, doubleValue2);
                    Minecraft var17 = Minecraft.getInstance();
                    if (var17 != null) {
                        if (var17.level != null) {
                            Vec3 var18 = arg1.getEyePosition(arg2);
                            BlockHitResult var19 = var17.level.clip(new ClipContext(var18, var16, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, arg1));
                            if (var19 != null) {
                                if (var19.getType() != HitResult.Type.MISS) {
                                    Vec3 var20 = var19.getLocation();
                                    Vec3 var21 = var18.subtract(var20);
                                    double var22 = var21.length();
                                    if (var22 > 1.0E-4) {
                                    } else {
                                    }
                                    var16 = var20;
                                }
                                return var16;
                            }
                        }
                    }
                }
            }
        }
    }

    private static float clamp(float arg0, float arg1, float arg2) {
        if (Float.isNaN(arg0)) {
        } else {
        }
        return Math.max(arg1, Math.min(arg2, arg0));
    }

    private static float wrap(float arg0) {
        arg0 = (arg0 % 360.0F);
        if (arg0 >= 180.0F) {
            arg0 = (arg0 - 360.0F);
        }
        if (arg0 < -180.0F) {
            arg0 = (arg0 + 360.0F);
        }
        return arg0;
    }

    static {
        Hooks.obj2("SmoothCamera.smooth", (p0, p1) -> SmoothCamera.lambda$static$0(p0, p1));
        Hooks.obj1("SmoothCamera.position", (p0) -> SmoothCamera.lambda$static$1(p0));
        rotInit = false;
        posInit = false;
        lastRot = 0L;
        lastPos = 0L;
    }

}
