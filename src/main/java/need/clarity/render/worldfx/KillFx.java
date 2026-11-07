package need.clarity.render.worldfx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import need.clarity.util.ClarityRenderUtils;
import net.minecraft.world.phys.Vec3;

public final class KillFx {
    public static final String[] STYLES;
    private static final float[] LIFE;
    private static final int[][] NATURAL;
    private static final List kills;
    private static int counter;
    private static final float[] R;
    private static final float[] U;
    private static final float[] F;
    private static final float[] uBurst;
    private static final float[] uInfo;

    private KillFx() {
    }

    public static int style(String arg0) {
        if (arg0 != null) {
            int var1 = 0;
            while (true) {
                if (var1 >= STYLES.length) break;
                if (STYLES[var1].equalsIgnoreCase(arg0)) {
                    return var1;
                }
                var1++;
            }
            return -1;
        }
    }

    public static void spawn(int arg0, double arg1, double arg2, double arg3, float arg4, float arg5) {
        counter = (counter + 1);
        kills.add(new KillFx.Kill(arg0, arg1, arg2, arg3, arg4, arg5, System.nanoTime(), ((((float) counter) * 0.6180339F) % 1.0F)));
        while (true) {
            if (kills.size() <= 24) break;
            kills.remove(0);
        }
    }

    public static void clear() {
        kills.clear();
    }

    public static void render(float arg0, float arg1, boolean arg2, boolean arg3, float[] arg4, float[] arg5) {
        float var9 = 0.0F;
        float var10 = 0.0F;
        Iterator var17 = null;
        KillFx.Kill var18 = null;
        float var19 = 0.0F;
        float var20 = 0.0F;
        float var21 = 0.0F;
        long var6 = System.nanoTime();
        kills.removeIf((p0) -> KillFx.lambda$render$0(var6, p0));
        if (kills.isEmpty()) {
            return;
        }
        Vec3 var8 = ClarityRenderUtils.getCameraPos();
        WorldFx.axes(R, U, F);
        var9 = R[0];
        var10 = R[2];
        float var11 = ((float) Math.sqrt(((double) ((var9 * var9) + (var10 * var10)))));
        if (var11 < 1.0E-4F) {
            var9 = 1.0F;
            var10 = 0.0F;
        } else {
            var9 = (var9 / var11);
            var10 = (var10 / var11);
        }
        float var12 = ((float) ((((double) var6) / 1.0E9) % 3600.0));
        int var13 = 0;
        while (true) {
            if (var13 >= STYLES.length) break;
            if (arg3) {
            } else {
            }
            float[] var14 = arg4;
            if (arg3) {
            } else {
            }
            float[] var15 = arg5;
            WorldFx.begin();
            int var16 = 0;
            var17 = kills.iterator();
            while (true) {
                if (!var17.hasNext()) break;
                var18 = ((KillFx.Kill) var17.next());
                if (var18.style != var13) {
                }
                var19 = ((float) Math.min(1.0, ((((double) (var6 - var18.t)) / 1.0E9) / ((double) LIFE[var13]))));
                var20 = ((float) (var18.x - var8.x));
                var21 = ((float) (var18.y - var8.y));
                float var22 = ((float) (var18.z - var8.z));
                float var23 = (var21 + (var18.h * 0.5F));
                switch (var13) {
                    case 0 -> {}
                    case 1 -> {}
                    case 2 -> {}
                    case 3 -> {}
                    case 4 -> {}
                    default -> {}
                }
                bb(var20, var23, var22, (2.2F * arg0), 1.3F, var14, arg1, var19, 0, var18.seed);
                ground(var20, var21, var22, (4.0F * arg0), 1.25F, var14, arg1, var19, 1, var18.seed);
                upright(var20, var21, var22, var9, var10, (1.6F * arg0), 30.0F, var14, arg1, var19, 0, var18.seed);
                ground(var20, var21, var22, (2.0F * arg0), 1.25F, var14, arg1, var19, 1, var18.seed);
                upright(var20, var21, var22, var9, var10, (0.9F * arg0), ((var18.h * 2.6F) * arg0), var14, arg1, var19, 0, var18.seed);
                bb(var20, var23, var22, (1.8F * arg0), 1.2F, var14, arg1, var19, 0, var18.seed);
                upright(var20, var21, var22, var9, var10, (0.9F * arg0), 24.0F, var14, arg1, var19, 0, var18.seed);
                ground(var20, var21, var22, (1.3F * arg0), 1.25F, var14, arg1, var19, 1, var18.seed);
                bb(var20, var23, var22, (1.4F * arg0), 1.0F, var14, arg1, var19, 0, var18.seed);
                if (var16 < 32) {
                    var16++;
                    int var25 = var16;
                    uBurst[(var25 * 4)] = var20;
                    uBurst[((var25 * 4) + 1)] = var23;
                    uBurst[((var25 * 4) + 2)] = var22;
                    uBurst[((var25 * 4) + 3)] = ((float) (((double) (var6 - var18.t)) / 1.0E9));
                    uInfo[(var25 * 4)] = var18.seed;
                    uInfo[((var25 * 4) + 1)] = ((0.8F + var18.w) * arg0);
                    uInfo[((var25 * 4) + 2)] = LIFE[var13];
                }
            }
            if (WorldFx.isEmpty()) {
            } else {
                var17 = var13;
                var18 = var15;
                WorldFx.flush("killfx", arg2, (p0) -> KillFx.lambda$render$1(var12, var17, var18, p0));
                if (var16 > 0) {
                    var19 = var16;
                    var20 = var14;
                    var21 = var19;
                    while (true) {
                        if (var21 >= 32) break;
                        uBurst[((var21 * 4) + 3)] = 99.0F;
                        var21++;
                    }
                    WorldFx.drawInstanced("burst", (var19 * 48), arg2, 0, (p0) -> KillFx.lambda$render$2(arg0, var20, var18, p0));
                }
                var13++;
            }
        }
    }

    private static void bb(float arg0, float arg1, float arg2, float arg3, float arg4, float[] arg5, float arg6, float arg7, int arg8, float arg9) {
        quad(arg0, arg1, arg2, R[0], R[1], R[2], U[0], U[1], U[2], (arg3 * arg4), (arg3 * arg4), -(arg4), -(arg4), arg4, arg4, arg5, arg6, arg7, (((float) arg8) + (arg9 * 0.99F)));
    }

    private static void ground(float arg0, float arg1, float arg2, float arg3, float arg4, float[] arg5, float arg6, float arg7, int arg8, float arg9) {
        quad(arg0, (arg1 + 0.02F), arg2, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, (arg3 * arg4), (arg3 * arg4), -(arg4), -(arg4), arg4, arg4, arg5, arg6, arg7, (((float) arg8) + (arg9 * 0.99F)));
    }

    private static void upright(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float[] arg7, float arg8, float arg9, int arg10, float arg11) {
        quad(arg0, (arg1 + (arg6 * 0.5F)), arg2, arg3, 0.0F, arg4, 0.0F, 1.0F, 0.0F, arg5, (arg6 * 0.5F), -1.0F, 0.0F, 1.0F, 1.0F, arg7, arg8, arg9, (((float) arg10) + (arg11 * 0.99F)));
    }

    private static void quad(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14, float[] arg15, float arg16, float arg17, float arg18) {
        float[][] var19 = new float[][]{
            new float[]{arg11, arg12},
            new float[]{arg13, arg12},
            new float[]{arg13, arg14},
            new float[]{arg11, arg14}
        };
        float[][] var20 = new float[4][3];
        float var21 = ((arg11 + arg13) * 0.5F);
        float var22 = ((arg12 + arg14) * 0.5F);
        float var23 = ((arg13 - arg11) * 0.5F);
        float var24 = ((arg14 - arg12) * 0.5F);
        for (int var25 = 0; var25 < 4; var25++) {
            float var26 = ((var19[var25][0] - var21) / var23);
            float var27 = ((var19[var25][1] - var22) / var24);
            var20[var25] = new float[]{
                ((arg0 + ((arg3 * arg9) * var26)) + ((arg6 * arg10) * var27)),
                ((arg1 + ((arg4 * arg9) * var26)) + ((arg7 * arg10) * var27)),
                ((arg2 + ((arg5 * arg9) * var26)) + ((arg8 * arg10) * var27))
            };
        }
        int[] indices = new int[]{0, 1, 2, 0, 2, 3};
        for (int var29 : indices) {
            WorldFx.vertex(var20[var29][0], var20[var29][1], var20[var29][2], var19[var29][0], var19[var29][1], arg15[0], arg15[1], arg15[2], arg16, arg17, arg18);
        }
    }

    private static float[] rgb(int arg0) {
        return new float[]{
            (((float) ((arg0 >> 16) & 255)) / 255.0F),
            (((float) ((arg0 >> 8) & 255)) / 255.0F),
            (((float) (arg0 & 255)) / 255.0F)
        };
    }

        static {
        STYLES = new String[]{
            "Supernova",
            "Thunderbolt",
            "Soul Ascend",
            "Event Horizon",
            "Light Pillar",
            "Crystal Burst"
        };
        LIFE = new float[]{1.3F, 1.0F, 2.2F, 1.7F, 2.2F, 1.2F};
        NATURAL = new int[][]{
            new int[]{16765562, 16738877},
            new int[]{10475775, 5930239},
            new int[]{6087167, 2780159},
            new int[]{11820287, 3807884},
            new int[]{16770442, 16777215},
            new int[]{8382719, 13169919}
        };
        kills = new ArrayList<>();
        counter = 0;
        R = new float[3];
        U = new float[3];
        F = new float[3];
        uBurst = new float[128];
        uInfo = new float[128];
    }
    public record Kill(int style, double x, double y, double z, float w, float h, long t, float seed) {

    }

}
