package need.clarity.render.worldfx;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import need.clarity.render.sky.SkyShaderRenderer;
import need.clarity.util.ClarityRenderUtils;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public final class CorpseFx {
    private static final float LIFE = 2.1F;
    private static final float CUT = 0.32F;
    private static final CorpseFx.Slot[] SLOTS;
    private static final List corpses;
    private static int counter;

    private CorpseFx() {
    }

    public static CorpseFx.Slot acquire(int arg0, int arg1) {
        CorpseFx.Slot var2 = null;
        CorpseFx.Slot[] var3 = null;
        int var4 = 0;
        int var5 = 0;
        int var7 = 0;
        var2 = null;
        var3 = SLOTS;
        var4 = var3.length;
        var5 = 0;
        while (true) {
            if (var5 >= var4) break;
            CorpseFx.Slot var6 = var3[var5];
            var7 = 0;
            Iterator var8 = corpses.iterator();
            while (true) {
                if (!var8.hasNext()) break;
                if (((CorpseFx.Corpse) var8.next()).slot == var6) {
                    var7 = 1;
                } else {
                }
                if (var7 != 0) break;
                var2 = var6;
                var5++;
            }
        }
        if (var2 == null) {
            var2 = ((CorpseFx.Corpse) corpses.get(0)).slot;
            corpses.remove(0);
        }
        if (var2.fbo != 0) {
            if (var2.w == arg0) {
                if (var2.h != arg1) {
                }
            }
            if (var2.tex == 0) {
                var2.tex = GL11.glGenTextures();
            }
            GlStateManager._activeTexture(33984);
            var3 = GL11.glGetInteger(32873);
            GlStateManager._bindTexture(var2.tex);
            GL11.glTexParameteri(3553, 10241, 9729);
            GL11.glTexParameteri(3553, 10240, 9729);
            GL11.glTexParameteri(3553, 10242, 33071);
            GL11.glTexParameteri(3553, 10243, 33071);
            GL11.glTexParameteri(3553, 33085, 0);
            var4 = GL11.glGetInteger(35055);
            GL15.glBindBuffer(35052, 0);
            GL11.glTexImage2D(3553, 0, 32856, arg0, arg1, 0, 6408, 5121, ((ByteBuffer) null));
            GL15.glBindBuffer(35052, var4);
            GlStateManager._bindTexture(var3);
            if (var2.fbo == 0) {
                var2.fbo = GL30.glGenFramebuffers();
            }
            var5 = GL11.glGetInteger(36006);
            GL30.glBindFramebuffer(36160, var2.fbo);
            GL30.glFramebufferTexture2D(36160, 36064, 3553, var2.tex, 0);
            GL30.glBindFramebuffer(36160, var5);
            var2.w = arg0;
            var2.h = arg1;
            return var2;
        }
    }

    public static int fbo(CorpseFx.Slot arg0) {
        return arg0.fbo;
    }

    public static void add(CorpseFx.Slot arg0, double[] arg1, double[] arg2, double[] arg3, double arg4, int arg5) {
        counter = (counter + 1);
        corpses.add(new CorpseFx.Corpse(arg0, arg1, arg2, arg3, arg4, System.nanoTime(), arg5, ((((float) counter) * 0.6180339F) % 1.0F), null, null));
    }

    public static void addAnchored(CorpseFx.Slot arg0, double[] arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7) {
        counter = (counter + 1);
        corpses.add(new CorpseFx.Corpse(arg0, new double[3], new double[3], new double[3], arg6, System.nanoTime(), arg7, ((((float) counter) * 0.6180339F) % 1.0F), arg1, new double[]{arg2, arg3, arg4, arg5}));
    }

    private static void place(CorpseFx.Corpse arg0) {
        if (arg0.anchor == null) {
            return;
        }
        float[] var1 = new float[3];
        float[] var2 = new float[3];
        float[] var3 = new float[3];
        SkyShaderRenderer.cameraAxes(var1, var2, var3);
        double var4 = arg0.box[0];
        double var6 = arg0.box[1];
        double var8 = arg0.box[2];
        double var10 = arg0.box[3];
        int var12 = 0;
        while (true) {
            if (var12 >= 3) break;
            arg0.o[var12] = ((arg0.anchor[var12] + (((double) var1[var12]) * (var4 - (var8 / 2.0)))) + (((double) var2[var12]) * (var6 - (var10 / 2.0))));
            arg0.u[var12] = (((double) var1[var12]) * var8);
            arg0.v[var12] = (((double) var2[var12]) * var10);
            var12++;
        }
    }

    public static boolean isEmpty() {
        return corpses.isEmpty();
    }

    public static void render(float[] arg0, float[] arg1, boolean arg2) {
        long var3 = System.nanoTime();
        corpses.removeIf((p0) -> CorpseFx.lambda$render$0(var3, p0));
        if (corpses.isEmpty()) {
            return;
        }
        Vec3 var5 = ClarityRenderUtils.getCameraPos();
        float var6 = ((float) ((((double) var3) / 1.0E9) % 3600.0));
        Iterator var7 = corpses.iterator();
        while (true) {
            if (!var7.hasNext()) break;
            CorpseFx.Corpse var8 = ((CorpseFx.Corpse) var7.next());
            float var9 = ((float) (((double) (var3 - var8.t)) / 1.0E9));
            place(var8);
            WorldFx.begin();
            emit(var8, var9, var5);
            if (WorldFx.isEmpty()) {
            }
            WorldFx.flushTextured("corpse", arg2, var8.slot.tex, (p0) -> CorpseFx.lambda$render$1(arg0, arg1, var6, p0));
        }
    }

    private static void emit(CorpseFx.Corpse arg0, float arg1, Vec3 arg2) {
        int var10 = 0;
        int var12 = 0;
        int var13 = 0;
        int var14 = 0;
        int var15 = 0;
        double var16 = 0.0;
        double var18 = 0.0;
        int var19 = 0;
        double var20 = 0.0;
        double[] var21 = null;
        double var22 = 0.0;
        double var23 = 0.0;
        double var24 = 0.0;
        double var25 = 0.0;
        double var27 = 0.0;
        double var29 = 0.0;
        int var30 = 0;
        double var31 = 0.0;
        double var32 = 0.0;
        double var33 = 0.0;
        double var38 = 0.0;
        double var40 = 0.0;
        double[] var42 = null;
        double[] var3 = arg0.u;
        double[] var4 = arg0.v;
        double[] var5 = norm(cross(var3, var4));
        double var6 = ((double) Math.max(0.0F, (arg1 - 0.32F)));
        float var8 = ((float) (1.0 - smooth(1.75, 2.0999999046325684, ((double) arg1))));
        if (arg0.style == 2) {
        } else {
        }
        float var9 = ((float) smooth(1.0, 1.95, ((double) arg1)));
        if (arg0.style == 2) {
            var10 = 3;
            var12 = 0;
            var13 = 0;
            while (true) {
                if (var13 >= 5) break;
                var14 = 0;
                while (true) {
                    if (var14 >= var10) break;
                    var15 = 0;
                    while (true) {
                        if (var15 >= 2) break;
                        var16 = (((double) var14) / ((double) var10));
                        var18 = (((double) (var14 + 1)) / ((double) var10));
                        var20 = (((double) var13) / ((double) 5));
                        var22 = (((double) (var13 + 1)) / ((double) 5));
                        if ((((var14 + var13) + var15) & 1) == 0) {
                            if (var15 == 0) {
                                var24 = new double[][]{
                                    new double[]{var16, var20},
                                    new double[]{var18, var20},
                                    new double[]{var18, var22}
                                };
                            } else {
                                var24 = new double[][]{
                                    new double[]{var16, var20},
                                    new double[]{var18, var22},
                                    new double[]{var16, var22}
                                };
                            }
                        } else {
                            if (var15 == 0) {
                                var24 = new double[][]{
                                    new double[]{var16, var20},
                                    new double[]{var18, var20},
                                    new double[]{var16, var22}
                                };
                            } else {
                                var24 = new double[][]{
                                    new double[]{var18, var20},
                                    new double[]{var18, var22},
                                    new double[]{var16, var22}
                                };
                            }
                        }
                            var25 = (((var24[0][0] + var24[1][0]) + var24[2][0]) / 3.0);
                            var27 = (((var24[0][1] + var24[1][1]) + var24[2][1]) / 3.0);
                            var29 = hash(((((double) var12) * 1.7) + ((double) (arg0.seed * 13.0F))));
                            var31 = hash(((((double) var12) * 3.1) + ((double) (arg0.seed * 7.0F))));
                            var33 = hash(((((double) var12) * 5.3) + 2.0));
                            double var35 = Math.max(0.0, (((double) arg1) - 0.25));
                            double[] var37 = norm(add(mul(var3, (var25 - 0.5)), mul(var4, (var27 - 0.5))));
                            var38 = (1.3 + (1.6 * var29));
                            var40 = ((1.0 - Math.exp((-2.2 * var35))) / 2.2);
                            var42 = add(add(mul(var37, (var38 * var40)), mul(var5, (((var31 - 0.5) * 1.6) * var40))), new double[]{0.0, ((-3.5 * var35) * var35), 0.0});
                            double var43 = (((var33 - 0.5) * 9.0) * var35);
                            double var45 = (1.0 - (0.35 * smooth(0.6, 1.6, ((double) arg1))));
                            poly(arg0, var24, var42, var43, var45, var5, arg2, var8, var9, null, 0.0, true);
                            var15++;
                            var12++;
                        }
                    }
                    var14++;
                }
                var13++;
            }
            if (((double) arg1) > 0.15) {
                if (((double) arg1) < 0.6) {
                    var13 = (((float) (1.0 - smooth(0.25, 0.6, ((double) arg1)))) * ((float) smooth(0.15, 0.25, ((double) arg1))));
                    streak(arg0, new double[]{0.15, 0.2}, new double[]{0.85, 0.8}, 0.05, var13, arg2);
                    streak(arg0, new double[]{0.85, 0.2}, new double[]{0.15, 0.8}, 0.05, var13, arg2);
                }
                return;
            }
        if (arg0.style == 0) {
        } else {
        }
        var10 = 0.0;
        var12 = new double[]{Math.cos(var10), Math.sin(var10)};
        var13 = new double[]{-var12[1], var12[0]};
        var14 = new double[]{0.5, (arg0.style == 0 ? 0.5 : 0.52)};
        var15 = new double[][]{
            new double[]{0.0, 0.0},
            new double[]{1.0, 0.0},
            new double[]{1.0, 1.0},
            new double[]{0.0, 1.0}
        };
        var16 = smooth(0.0, 0.55, var6);
        var18 = (arg1 >= 0.32F);
        var19 = -1;
        while (true) {
            if (var19 > 1) break;
            var20 = clip(var15, var13, var14, var19);
            if (var20.length < 3) {
            } else {
                if (arg0.style == 0) {
                    var22 = add(mul(var3, ((((var13[0] * ((double) var19)) * 0.16) * var16) + (((var12[0] * ((double) var19)) * 0.1) * var16))), mul(var4, ((((var13[1] * ((double) var19)) * 0.16) * var16) + (((var12[1] * ((double) var19)) * 0.1) * var16))));
                    var23 = ((((double) var19) * 0.55) * var16);
                } else {
                    if (var19 > 0) {
                    } else {
                    }
                    var25 = 0.0;
                    if (var19 > 0) {
                    } else {
                    }
                    var22 = add(var4, mul((0.02 * var16), (-0.03 * var16)));
                    if (var19 > 0) {
                    } else {
                    }
                    var23 = 0.0;
                }
                if (var19 <= 0) {
                    if (arg0.style == 0) {
                    }
                }
                var25 = ((1.0 * var6) * var6);
                var21 = add(var22, new double[]{0.0, -var25, 0.0});
                var27 = 1.7976931348623157E308;
                var29 = var20;
                var30 = var29.length;
                var31 = 0;
                while (true) {
                    if (var31 >= var30) break;
                    var32 = var29[var31];
                    var33 = world(arg0, var32);
                    var27 = Math.min(var27, var33[1]);
                    var31++;
                }
                if ((var27 + var21[1]) < arg0.ground) {
                    var21[1] = (arg0.ground - var27);
                }
                if (var18 != 0) {
                } else {
                }
                poly(var20, var21, var23, 1.0, var5, arg2, var8, var9, var13, null, dot2(var13, var14), false);
            }
            var19 += 2;
        }
        if (var18 != 0) {
            if (var6 < 0.7) {
                var19 = 0;
                while (true) {
                    if (var19 >= 22) break;
                    var20 = hash(((((double) var19) * 2.3) + ((double) (arg0.seed * 17.0F))));
                    var22 = hash(((((double) var19) * 4.7) + ((double) (arg0.seed * 5.0F))));
                    var24 = hash(((((double) var19) * 7.9) + 1.0));
                    double var26 = (var6 - (var24 * 0.08));
                    if (var26 <= 0.0) {
                    } else {
                        double var28 = ((var20 - 0.5) * 1.1);
                        if (var22 < 0.5) {
                        } else {
                        }
                        var30 = 1.0;
                        var32 = (0.6 + (0.9 * var22));
                        double var34 = (((var13[0] * var30) * var32) + ((var12[0] * (var24 - 0.5)) * 0.8));
                        double var36 = (((var13[1] * var30) * var32) + ((var12[1] * (var24 - 0.5)) * 0.8));
                        var38 = ((var14[0] + (var12[0] * var28)) + (var34 * var26));
                        var40 = (((var14[1] + (var12[1] * var28)) + (var36 * var26)) - ((1.6 * var26) * var26));
                        var42 = (var38 - (var34 * 0.045));
                        double var44 = (var40 - ((var36 - (3.2 * var26)) * 0.045));
                        float var46 = (((float) (1.0 - smooth(0.15, 0.7, var6))) * var8);
                        streak(arg0, new double[]{var42, var44}, new double[]{var38, var40}, 0.012, var46, arg2);
                    }
                    var19++;
                }
                if (((double) arg1) > 0.19999999284744263) {
                    if (((double) arg1) < 0.6199999928474427) {
                        var19 = ((float) (smooth(0.19999999284744263, 0.3199999928474426, ((double) arg1)) * (1.0 - smooth(0.3699999928474426, 0.6199999928474427, ((double) arg1)))));
                        var20 = new double[]{var14[0] - (var12[0] * 0.85), var14[1] - (var12[1] * 0.85)};
                        var21 = new double[]{var14[0] + (var12[0] * 0.85), var14[1] + (var12[1] * 0.85)};
                        streak(arg0, var20, var21, 0.045, var19, arg2);
                    }
                    return;
                }
            }
        }
    }

    private static void poly(CorpseFx.Corpse arg0, double[][] arg1, double[] arg2, double arg3, double arg4, double[] arg5, Vec3 arg6, float arg7, float arg8, double[] arg9, double arg10, boolean arg11) {
        double[] var17 = null;
        int var18 = 0;
        int var15 = arg1.length;
        double[][] var16 = new double[var15][];
        var17 = new double[]{0.0, 0.0, 0.0};
        var18 = 0;
        while (true) {
            if (var18 >= var15) break;
            var16[var18] = world(arg0, arg1[var18]);
            var17 = add(var17, var16[var18]);
            var18++;
        }
        var17 = mul(var17, (1.0 / ((double) var15)));
        var18 = 0;
        while (true) {
            if (var18 >= var15) break;
            double[] var19 = mul(sub(var16[var18], var17), arg4);
            var16[var18] = add(add(var17, rotate(var19, arg5, arg3)), arg2);
            var18++;
        }
        var18 = 1;
        while (true) {
            if (var18 >= (var15 - 1)) break;
            v(var16[0], arg1[0], arg6, arg7, arg8, arg9, arg10, arg11);
            v(var16[var18], arg1[var18], arg6, arg7, arg8, arg9, arg10, arg11);
            v(var16[(var18 + 1)], arg1[(var18 + 1)], arg6, arg7, arg8, arg9, arg10, arg11);
            var18++;
        }
    }

    private static void v(double[] arg0, double[] arg1, Vec3 arg2, float arg3, float arg4, double[] arg5, double arg6, boolean arg7) {
        if (!arg7) {
            if (arg5 == null) {
            }
        }
        float var9 = ((float) (dot2(arg5, arg1) - arg6));
        WorldFx.vertex(((float) (arg0[0] - arg2.x)), ((float) (arg0[1] - arg2.y)), ((float) (arg0[2] - arg2.z)), ((float) arg1[0]), ((float) arg1[1]), 1.0F, 1.0F, 1.0F, arg3, arg4, var9);
    }

    private static void streak(CorpseFx.Corpse arg0, double[] arg1, double[] arg2, double arg3, float arg4, Vec3 arg5) {
        double[] var7 = world(arg0, arg1);
        double[] var8 = world(arg0, arg2);
        double[] var9 = sub(var8, var7);
        double[] var10 = norm(cross(arg0.u, arg0.v));
        double[] var11 = mul(norm(cross(var9, var10)), (arg3 * Math.sqrt(dot(arg0.u, arg0.u))));
        double[] var12 = mul(var10, -0.03);
        double[][] var13 = new double[][]{
            add(sub(var7, var11), var12),
            add(sub(var8, var11), var12),
            add(add(var8, var11), var12),
            add(add(var7, var11), var12)
        };
        double[][] var14 = new double[][]{
            new double[]{-1.0, -1.0},
            new double[]{1.0, -1.0},
            new double[]{1.0, 1.0},
            new double[]{-1.0, 1.0}
        };
        int[] var15 = new int[]{0, 1, 2, 0, 2, 3};
        int var17 = var15.length;
        int var18 = 0;
        while (true) {
            if (var18 >= var17) break;
            int var19 = var15[var18];
            WorldFx.vertex(((float) (var13[var19][0] - arg5.x)), ((float) (var13[var19][1] - arg5.y)), ((float) (var13[var19][2] - arg5.z)), ((float) var14[var19][0]), ((float) var14[var19][1]), 1.0F, 1.0F, 1.0F, arg4, 0.0F, -100.0F);
            var18++;
        }
    }

    private static double[] world(CorpseFx.Corpse arg0, double[] arg1) {
        return add(add(arg0.o, mul(arg0.u, arg1[0])), mul(arg0.v, arg1[1]));
    }

    private static double[][] clip(double[][] arg0, double[] arg1, double[] arg2, int arg3) {
        ArrayList var4 = new ArrayList();
        double var5 = dot2(arg1, arg2);
        int var7 = 0;
        while (true) {
            if (var7 >= arg0.length) break;
            double[] var8 = arg0[var7];
            double[] var9 = arg0[((var7 + 1) % arg0.length)];
            double var10 = (((double) arg3) * (dot2(arg1, var8) - var5));
            double var12 = (((double) arg3) * (dot2(arg1, var9) - var5));
            if (var10 >= 0.0) {
                var4.add(var8);
            }
            if ((var10 >= 0.0) != (var12 >= 0.0)) {
                double var14 = (var10 / (var10 - var12));
                var4.add(new double[]{
                    var8[0] + ((var9[0] - var8[0]) * var14),
                    var8[1] + ((var9[1] - var8[1]) * var14)
                });
            }
            var7++;
        }
        return ((double[][]) var4.toArray(new double[0][]));
    }

    private static double[] rotate(double[] arg0, double[] arg1, double arg2) {
        double var4 = Math.cos(arg2);
        double var6 = Math.sin(arg2);
        double[] var8 = cross(arg1, arg0);
        double var9 = dot(arg1, arg0);
        return new double[]{
            (((arg0[0] * var4) + (var8[0] * var6)) + ((arg1[0] * var9) * (1.0 - var4))),
            (((arg0[1] * var4) + (var8[1] * var6)) + ((arg1[1] * var9) * (1.0 - var4))),
            (((arg0[2] * var4) + (var8[2] * var6)) + ((arg1[2] * var9) * (1.0 - var4)))
        };
    }

    private static double smooth(double arg0, double arg1, double arg2) {
        double var6 = Math.max(0.0, Math.min(1.0, ((arg2 - arg0) / (arg1 - arg0))));
        return ((var6 * var6) * (3.0 - (2.0 * var6)));
    }

    private static double hash(double arg0) {
        double var2 = (Math.sin((arg0 * 127.1)) * 43758.5453);
        return (var2 - Math.floor(var2));
    }

    private static double dot2(double[] arg0, double[] arg1) {
        return ((arg0[0] * arg1[0]) + (arg0[1] * arg1[1]));
    }

    private static double dot(double[] arg0, double[] arg1) {
        return (((arg0[0] * arg1[0]) + (arg0[1] * arg1[1])) + (arg0[2] * arg1[2]));
    }

    private static double[] add(double[] arg0, double[] arg1) {
        return new double[]{arg0[0] + arg1[0], arg0[1] + arg1[1], arg0[2] + arg1[2]};
    }

    private static double[] sub(double[] arg0, double[] arg1) {
        return new double[]{arg0[0] - arg1[0], arg0[1] - arg1[1], arg0[2] - arg1[2]};
    }

    private static double[] mul(double[] arg0, double arg1) {
        return new double[]{arg0[0] * arg1, arg0[1] * arg1, arg0[2] * arg1};
    }

    private static double[] cross(double[] arg0, double[] arg1) {
        return new double[]{
            ((arg0[1] * arg1[2]) - (arg0[2] * arg1[1])),
            ((arg0[2] * arg1[0]) - (arg0[0] * arg1[2])),
            ((arg0[0] * arg1[1]) - (arg0[1] * arg1[0]))
        };
    }

    private static double[] norm(double[] arg0) {
        double var1 = Math.sqrt(dot(arg0, arg0));
        if (var1 < 1.0E-9) {
            return new double[]{0.0, 0.0, 1.0};
        }
        return mul(arg0, (1.0 / var1));
    }

    static {
        SLOTS = new CorpseFx.Slot[]{
            new CorpseFx.Slot(),
            new CorpseFx.Slot(),
            new CorpseFx.Slot(),
            new CorpseFx.Slot()
        };
        corpses = new ArrayList<>();
        counter = 0;
    }

    public record Corpse(CorpseFx.Slot slot, double[] o, double[] u, double[] v, double ground, long t, int style, float seed, double[] anchor, double[] box) {

    }

    public final class Slot {
        int tex;
        int fbo;
        int w;
        int h;

        public Slot() {
        }

    }

}
