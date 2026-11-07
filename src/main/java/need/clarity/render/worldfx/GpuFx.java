package need.clarity.render.worldfx;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import need.clarity.util.ClarityRenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryUtil;

public final class GpuFx {
    public static final String[] SKY;
    public static final String[] HIT;
    private static final float[] HIT_LIFE;
    private static final int HM = 96;
    private static final float[] heights;
    private static int hmOriginX;
    private static int hmOriginZ;
    private static int hmBaseY;
    private static boolean hmValid;
    private static boolean hmDirty;
    private static long hmBuiltAt;
    private static int hmTex;
    private static FloatBuffer hmBuf;
    private static final List bursts;
    private static final float[] uBurst;
    private static final float[] uInfo;
    private static int burstCounter;

    private GpuFx() {
    }

    public static int skyStyle(String arg0) {
        return indexOf(SKY, arg0);
    }

    public static int hitStyle(String arg0) {
        return indexOf(HIT, arg0);
    }

    public static void tickHeightmap() {
        Minecraft var0 = Minecraft.getInstance();
        if (var0 != null) {
            if (var0.level == null) {
            }
        }
        return;
        Vec3 var1 = ClarityRenderUtils.getCameraPos();
        int var2 = ((int) Math.floor(var1.x));
        int var3 = ((int) Math.floor(var1.z));
        long var4 = System.nanoTime();
        if (Math.abs((var2 - (hmOriginX + 48))) <= 10) {
            if (Math.abs((var3 - (hmOriginZ + 48))) > 10) {
            }
        }
        int var6 = 0;
        if (hmValid) {
            if (var6 == 0) {
                if ((var4 - hmBuiltAt) < 2000000000L) {
                    return;
                }
                hmOriginX = (var2 - 48);
                hmOriginZ = (var3 - 48);
                hmBaseY = ((int) Math.floor(var1.y));
                int var7 = 0;
                while (true) {
                    if (var7 >= 96) break;
                    int var8 = 0;
                    while (true) {
                        if (var8 >= 96) break;
                        int var9 = var0.level.getHeight(Heightmap.Types.MOTION_BLOCKING, (hmOriginX + var8), (hmOriginZ + var7));
                        heights[((var7 * 96) + var8)] = ((float) (var9 - hmBaseY));
                        var8++;
                    }
                    var7++;
                }
                hmValid = true;
                hmDirty = true;
                hmBuiltAt = var4;
                return;
            }
        }
    }

    private static int heightTexture() {
        if (!hmValid) {
            return 0;
        }
        if (hmTex != 0) {
            if (!hmDirty) {
                return hmTex;
            }
            if (hmBuf == null) {
                hmBuf = MemoryUtil.memAllocFloat(9216);
            }
            hmBuf.clear();
            hmBuf.put(heights).flip();
            GlStateManager._activeTexture(33984);
            int var0 = GL11.glGetInteger(32873);
            int var1 = GL11.glGetInteger(35055);
            int var2 = GL11.glGetInteger(3314);
            int var3 = GL11.glGetInteger(3316);
            int var4 = GL11.glGetInteger(3315);
            int var5 = GL11.glGetInteger(3317);
            GL15.glBindBuffer(35052, 0);
            GL11.glPixelStorei(3314, 0);
            GL11.glPixelStorei(3316, 0);
            GL11.glPixelStorei(3315, 0);
            GL11.glPixelStorei(3317, 4);
            int var6 = (hmTex == 0);
            if (var6 != 0) {
                hmTex = GL11.glGenTextures();
            }
            GlStateManager._bindTexture(hmTex);
            if (var6 != 0) {
                GL11.glTexParameteri(3553, 10241, 9728);
                GL11.glTexParameteri(3553, 10240, 9728);
                GL11.glTexParameteri(3553, 33085, 0);
                GL11.glTexImage2D(3553, 0, 33326, 96, 96, 0, 6403, 5126, hmBuf);
            } else {
                GL11.glTexSubImage2D(3553, 0, 0, 0, 96, 96, 6403, 5126, hmBuf);
            }
            GL11.glPixelStorei(3314, var2);
            GL11.glPixelStorei(3316, var3);
            GL11.glPixelStorei(3315, var4);
            GL11.glPixelStorei(3317, var5);
            GL15.glBindBuffer(35052, var1);
            GlStateManager._bindTexture(var0);
            hmDirty = false;
            return hmTex;
        }
    }

    public static void sky(int arg0, int arg1, float arg2, float arg3, float arg4, float arg5, float[] arg6, float[] arg7, boolean arg8, float arg9, boolean arg10, float arg11) {
        Vec3 var12 = ClarityRenderUtils.getCameraPos();
        float var13 = Math.max(4.0F, (Math.min(arg2, 46.0F) * 2.0F));
        float var14 = Math.max(6.0F, (arg3 * 2.5F));
        float var15 = ((float) mod(var12.x, ((double) var13)));
        float var16 = ((float) mod(var12.y, ((double) var14)));
        float var17 = ((float) mod(var12.z, ((double) var13)));
        if (arg10) {
        } else {
        }
        int var18 = 0;
        int var19 = (var18 != 0);
        float var20 = ((float) (var12.x - ((double) hmOriginX)));
        float var21 = ((float) (var12.z - ((double) hmOriginZ)));
        float var22 = ((float) (var12.y - ((double) hmBaseY)));
        WorldFx.drawInstanced("weather", arg1, false, var18, (p0) -> GpuFx.lambda$sky$0(arg11, var13, var14, var15, var16, var17, arg0, arg5, arg4, var20, var21, var22, var19, arg6, arg7, arg8, arg9, p0));
    }

    public static void addBurst(int arg0, double arg1, double arg2, double arg3, float arg4) {
        float var8 = HIT_LIFE[Math.max(0, Math.min((HIT_LIFE.length - 1), arg0))];
        burstCounter = (burstCounter + 1);
        float var9 = ((((float) burstCounter) * 0.6180339F) % 1.0F);
        bursts.add(new GpuFx.Burst(arg1, arg2, arg3, System.nanoTime(), var9, arg4, var8));
        while (true) {
            if (bursts.size() <= 32) break;
            bursts.remove(0);
        }
    }

    public static boolean hasBursts() {
        return (!bursts.isEmpty());
    }

    public static void renderBursts(int arg0, int arg1, float arg2, float[] arg3, float[] arg4, boolean arg5, boolean arg6) {
        int var11 = 0;
        long var7 = System.nanoTime();
        bursts.removeIf((p0) -> GpuFx.lambda$renderBursts$0(var7, p0));
        if (bursts.isEmpty()) {
            return;
        }
        Vec3 var9 = ClarityRenderUtils.getCameraPos();
        int var10 = bursts.size();
        Arrays.fill(uBurst, 0.0F);
        Arrays.fill(uInfo, 0.0F);
        var11 = 0;
        while (true) {
            if (var11 >= var10) break;
            GpuFx.Burst var12 = ((GpuFx.Burst) bursts.get(var11));
            uBurst[(var11 * 4)] = ((float) (var12.x - var9.x));
            uBurst[((var11 * 4) + 1)] = ((float) (var12.y - var9.y));
            uBurst[((var11 * 4) + 2)] = ((float) (var12.z - var9.z));
            uBurst[((var11 * 4) + 3)] = ((float) (((double) (var7 - var12.t)) / 1.0E9));
            uInfo[(var11 * 4)] = var12.seed;
            uInfo[((var11 * 4) + 1)] = var12.scale;
            uInfo[((var11 * 4) + 2)] = var12.life;
            var11++;
        }
        var11 = Math.max(4, Math.min(64, arg1));
        WorldFx.drawInstanced("burst", (var10 * var11), arg6, 0, (p0) -> GpuFx.lambda$renderBursts$1(var11, arg0, arg2, arg3, arg4, arg5, p0));
    }

    public static void auraParticles(int arg0, double arg1, double arg2, double arg3, float arg4, float arg5, int arg6, float arg7, float[] arg8, float[] arg9, float arg10, float arg11, boolean arg12) {
        Vec3 var16 = ClarityRenderUtils.getCameraPos();
        float var18 = ((float) (arg1 - var16.x));
        float var19 = ((float) (arg2 - var16.y));
        float var20 = ((float) (arg3 - var16.z));
        if (arg0 == 0) {
        } else {
        }
        int var21 = arg6;
        WorldFx.drawInstanced("aurap", var21, arg12, 0, (p0) -> GpuFx.lambda$auraParticles$0(var18, var19, var20, arg4, arg5, arg11, arg7, arg0, arg8, arg9, arg10, p0));
    }

    private static double mod(double arg0, double arg1) {
        double var4 = (arg0 % arg1);
        if (var4 < 0.0) {
        } else {
        }
        return var4;
    }

    private static int indexOf(String[] arg0, String arg1) {
        if (arg1 != null) {
            int var2 = 0;
            while (true) {
                if (var2 >= arg0.length) break;
                if (arg0[var2].equalsIgnoreCase(arg1)) {
                    return var2;
                }
                var2++;
            }
            return -1;
        }
    }

    static {
        new String[8][0] = "Snow";
        new String[8][1] = "Rain";
        new String[8][2] = "Sakura";
        new String[8][3] = "Embers";
        new String[8][4] = "Fireflies";
        new String[8][5] = "Starfall";
        new String[8][6] = "Leaves";
        new String[8][7] = "Hearts";
        SKY = new String[8];
        new String[8][0] = "Sparks";
        new String[8][1] = "Stars";
        new String[8][2] = "Hearts";
        new String[8][3] = "Shards";
        new String[8][4] = "Blood";
        new String[8][5] = "Magic";
        new String[8][6] = "Nova";
        new String[8][7] = "Confetti";
        HIT = new String[8];
        new float[8][0] = 0.6F;
        new float[8][1] = 0.9F;
        new float[8][2] = 1.2F;
        new float[8][3] = 0.9F;
        new float[8][4] = 0.8F;
        new float[8][5] = 1.1F;
        new float[8][6] = 0.7F;
        new float[8][7] = 1.4F;
        HIT_LIFE = new float[8];
        heights = new float[9216];
        hmValid = false;
        hmDirty = false;
        hmBuiltAt = 0L;
        hmTex = 0;
        bursts = new ArrayList();
        uBurst = new float[128];
        uInfo = new float[128];
        burstCounter = 0;
    }

    public record Burst(double x, double y, double z, long t, float seed, float scale, float life) {

    }

}
