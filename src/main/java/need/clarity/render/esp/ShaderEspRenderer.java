package need.clarity.render.esp;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderTarget;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import need.clarity.api.Hooks;
import need.clarity.modules.visual.ItemESP;
import need.clarity.modules.visual.ShaderESP;
import need.clarity.modules.visual.SkyShaders;
import need.clarity.render.RawFramebufferUtil;
import need.clarity.render.RawGlStateCapture;
import need.clarity.render.Shader;
import need.clarity.render.ShaderHelper;
import need.clarity.render.sky.SkyShaderRenderer;
import need.clarity.render.worldfx.CorpseFx;
import need.clarity.util.ClarityRenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

public final class ShaderEspRenderer {
    private static final Map tagged;
    private static boolean taggedThisFrame;
    private static long frame;
    private static long maskFrame;
    private static long compositeFrame;
    private static final Set outlinePasses;
    private static Shader downShader;
    private static Shader blurShader;
    private static Shader glowShader;
    private static Shader blurModeShader;
    private static Shader galaxyShader;
    private static Shader prismShader;
    private static Shader neonShader;
    private static Shader scanShader;
    private static boolean initDone;
    private static boolean initFailed;
    private static final ShaderEspRenderer.Target mask;
    private static final ShaderEspRenderer.Target g2a;
    private static final ShaderEspRenderer.Target g2b;
    private static final ShaderEspRenderer.Target g4a;
    private static final ShaderEspRenderer.Target g4b;
    private static final ShaderEspRenderer.Target s2a;
    private static final ShaderEspRenderer.Target s2b;
    private static final ShaderEspRenderer.Target s4a;
    private static final ShaderEspRenderer.Target s4b;
    private static int samplerLinear;
    private static final long START;
    private static double animTime;
    private static long lastNanos;
    public static final int COMBO_TAG = 16646398;
    private static volatile int comboId;
    private static volatile int comboTagColor;
    private static volatile int pendingId;
    private static volatile int pendingStyle;
    private static volatile long pendingAt;
    private static final Map hidden;
    private static Shader capShader;

    private ShaderEspRenderer() {
    }

    public static void setComboTarget(int arg0) {
        if (pendingId == -2147483648) {
            comboId = arg0;
        }
    }

    public static void requestCorpse(int arg0, int arg1) {
        pendingId = arg0;
        pendingStyle = arg1;
        pendingAt = System.nanoTime();
        comboId = arg0;
    }

    public static boolean isHidden(int arg0) {
        Long var1 = ((Long) hidden.get(arg0));
        if (var1 == null) {
            return false;
        }
        if ((System.nanoTime() - var1) > 4000000000L) {
            hidden.remove(arg0);
            return false;
        }
        return true;
    }

    private static boolean espOn() {
        if (!ShaderESP.isEligible()) {
            if (ItemESP.isStyle()) {
            }
        }
        return false;
    }

    private static boolean anyOn() {
        if (!espOn()) {
            if (comboId != -2147483648) {
            }
        }
        return false;
    }

    public static void tagState(Entity arg0, EntityRenderState arg1) {
        int var2 = 0;
        Player var3 = null;
        int var4 = 0;
        var2 = -1;
        if ((arg0 instanceof Player)) {
            var3 = ((Player) arg0);
            if (ShaderESP.isEligible()) {
                if (ShaderESP.isEligible(var3)) {
                    var2 = (ShaderESP.computeColorOrIndex(var3) & 16777215);
                } else {
                    if ((arg0 instanceof ItemEntity)) {
                        if (ItemESP.isStyle()) {
                            if (ItemESP.isEligible(arg0)) {
                                var2 = (ItemESP.getGlowColor() & 16777215);
                            }
                            if (arg0 != null) {
                                var3 = (arg0.getId() == comboId);
                                if (var2 < 0) {
                                    if (var3 == 0) {
                                        return;
                                    }
                                    if (var2 < 0) {
                                        var2 = 16646398;
                                    } else {
                                        var4 = ((var2 >> 16) & 255);
                                        int var5 = ((var2 >> 8) & 255);
                                        int var6 = (var2 & 255);
                                        if (Math.max(var4, Math.max(var5, var6)) < 8) {
                                            var2 = 526344;
                                        }
                                        if (var2 == 0) {
                                            var2 = (var2 ^ 65793);
                                        }
                                        if (var2 == 16646398) {
                                            var2 = (var2 ^ 131072);
                                        }
                                        if (var3 != 0) {
                                            comboTagColor = var2;
                                        }
                                        arg1.outlineColor = var2;
                                        var4 = tagged;
                                        tagged.put(arg1, var2);
                                        Object var7 = var4;
                                        throw var7;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void afterExtract(LevelRenderState arg0) {
        int var1 = 0;
        frame = (frame + 1L);
        var1 = 0;
        Map var2 = tagged;
        if (!tagged.isEmpty()) {
            if (anyOn()) {
                if (arg0 != null) {
                    if (arg0.entityRenderStates != null) {
                        Iterator var3 = arg0.entityRenderStates.iterator();
                        while (true) {
                            if (!var3.hasNext()) break;
                            EntityRenderState var4 = ((EntityRenderState) var3.next());
                            Integer var5 = ((Integer) tagged.get(var4));
                            if (var5 != null) {
                                var4.outlineColor = var5;
                                var1 = 1;
                            }
                        }
                        tagged.clear();
                        Map var6 = var2;
                        throw var6;
                        taggedThisFrame = var1;
                        if (var1 != 0) {
                            arg0.haveGlowingEntities = true;
                        }
                        return;
                    }
                }
            }
        }
    }

    public static void captureMask() {
        RawGlStateCapture var2 = null;
        if (taggedThisFrame) {
            if (!anyOn()) {
            }
        }
        return;
        Minecraft var0 = Minecraft.getInstance();
        if (var0 != null) {
            if (var0.levelRenderer == null) {
            }
        }
        return;
        RenderTarget var1 = var0.levelRenderer.entityOutlineTarget();
        if (var1 != null) {
            if (var1.width > 0) {
                if (var1.height <= 0) {
                }
            }
            return;
            try {
                var2 = RawGlStateCapture.capture();
                try {
                    if (!(ensure(mask, var1.width, var1.height, false))) {
                    }
                } catch (Throwable ignored) {
                }
                if (var2 != null) {
                    var2.close();
                }
                return;
                try {
                    try {
                        if (!RawFramebufferUtil.bindDirect(var1)) {
                        }
                    } catch (Throwable ignored) {
                    }
                } catch (Throwable ignored) {
                }
                if (var2 != null) {
                    var2.close();
                }
                return;
                try {
                    try {
                        GlStateManager._disableScissorTest();
                        GL30.glBindFramebuffer(36009, mask.fbo);
                        GL30.glBlitFramebuffer(0, 0, var1.width, var1.height, 0, 0, mask.w, mask.h, 16384, 9728);
                        maskFrame = frame;
                    } catch (Throwable ignored) {
                    }
                } catch (Throwable ignored) {
                }
                if (var2 != null) {
                    var2.close();
                    if (var2 != null) {
                        try {
                            var2.close();
                        } catch (Throwable ignored) {
                        }
                        var3.addSuppressed(var4);
                    }
                    throw var3;
                }
                return;
            } catch (Throwable ignored) {
            }
        }
    }

    public static void registerOutlinePasses(List arg0) {
        if (arg0 != null) {
            Iterator var1 = arg0.iterator();
            while (true) {
                if (!var1.hasNext()) break;
                Object var2 = var1.next();
                if (var2 != null) {
                    outlinePasses.add(var2);
                }
            }
            return;
        }
    }

    public static boolean onOutlinePass(Object arg0) {
        if (taggedThisFrame) {
            if (anyOn()) {
                if (!outlinePasses.contains(arg0)) {
                }
            }
            return false;
            if (compositeFrame != frame) {
                captureMask();
                if (maskFrame == frame) {
                    processCorpse();
                    if (espOn()) {
                        drawComposite();
                    }
                    compositeFrame = frame;
                }
            }
            return true;
        }
    }

    public static boolean onDoEntityOutline() {
        if (taggedThisFrame) {
            if (!anyOn()) {
            }
        }
        return false;
        if (compositeFrame != frame) {
            if (maskFrame == frame) {
                processCorpse();
                if (espOn()) {
                    drawComposite();
                }
                compositeFrame = frame;
            }
            return true;
        }
    }

    private static void drawComposite() {
        RawGlStateCapture var2 = null;
        int var3 = 0;
        int var4 = 0;
        if (!ensureInit()) {
            return;
        }
        RenderTarget var1 = Minecraft.getInstance().getMainRenderTarget();
        if (var1 != null) {
            if (var1.width > 0) {
                if (var1.height > 0) {
                    if (mask.fbo == 0) {
                    }
                }
                return;
                tick();
                try {
                    var2 = RawGlStateCapture.capture();
                    try {
                        var3 = new int[3];
                        var4 = 0;
                        while (true) {
                            if (var4 >= 3) break;
                            GlStateManager._activeTexture((33984 + var4));
                            var3[var4] = GL11.glGetInteger(35097);
                            var4++;
                        }
                        draw(var1);
                        var4 = 0;
                        while (true) {
                            if (var4 >= 3) break;
                            GL33.glBindSampler(var4, var3[var4]);
                            var4++;
                        }
                        GlStateManager._activeTexture(33984);
                        int var6 = 0;
                        while (true) {
                            if (var6 >= 3) break;
                            GL33.glBindSampler(var6, var3[var6]);
                            var6++;
                        }
                        GlStateManager._activeTexture(33984);
                        throw var5;
                    } catch (Throwable ignored) {
                    }
                    if (var2 != null) {
                        var2.close();
                        if (var2 != null) {
                            try {
                                var2.close();
                            } catch (Throwable ignored) {
                            }
                            var3.addSuppressed(var4);
                        }
                        throw var3;
                    }
                    return;
                } catch (Throwable ignored) {
                }
            }
        }
    }

    private static void processCorpse() {
        RawGlStateCapture var4 = null;
        Matrix4f var5 = null;
        Vec3 var6 = null;
        double var18 = 0.0;
        double var20 = 0.0;
        double var22 = 0.0;
        double var24 = 0.0;
        int var27 = 0;
        int var37 = 0;
        int var38 = 0;
        int var39 = 0;
        if (pendingId == -2147483648) {
            return;
        }
        int var0 = pendingId;
        if ((System.nanoTime() - pendingAt) > 1500000000L) {
            pendingId = -2147483648;
            comboId = -2147483648;
            return;
        }
        Minecraft var1 = Minecraft.getInstance();
        if (var1.level == null) {
        } else {
        }
        Entity var2 = var1.level.getEntity(var0);
        RenderTarget var3 = var1.getMainRenderTarget();
        if (var2 != null) {
            if (var3 != null) {
                if (mask.fbo == 0) {
                }
            }
            pendingId = -2147483648;
            comboId = -2147483648;
            return;
            try {
                var4 = RawGlStateCapture.capture();
                try {
                    if (capShader == null) {
                        capShader = new Shader("esp", "corpse_cap");
                    }
                    if (!ensureInit()) {
                    }
                } catch (Throwable ignored) {
                }
                if (var4 != null) {
                    var4.close();
                }
                pendingId = -2147483648;
                comboId = -2147483648;
                return;
                try {
                    try {
                        var5 = new Matrix4f();
                        if (!SkyShaderRenderer.levelViewProjection(var5)) {
                        }
                    } catch (Throwable ignored) {
                    }
                } catch (Throwable ignored) {
                }
                if (var4 != null) {
                    var4.close();
                }
                pendingId = -2147483648;
                comboId = -2147483648;
                return;
                try {
                    try {
                        var6 = ClarityRenderUtils.getCameraPos();
                        float var7 = var1.getDeltaTracker().getGameTimeDeltaPartialTick(false);
                        Vec3 var8 = var2.getPosition(var7);
                        AABB var9 = var2.getBoundingBox().move((var8.x - var2.getX()), (var8.y - var2.getY()), (var8.z - var2.getZ()));
                        double var10 = ((var9.minX + var9.maxX) * 0.5);
                        double var12 = ((var9.minY + var9.maxY) * 0.5);
                        double var14 = ((var9.minZ + var9.maxZ) * 0.5);
                        double var16 = ((Math.max(var9.getXsize(), var9.getYsize()) * 0.65) + 0.15);
                        var18 = 9.0;
                        var20 = 9.0;
                        var22 = -9.0;
                        var24 = -9.0;
                        Vector4f var26 = new Vector4f();
                        var27 = 0;
                        while (true) {
                            if (var27 >= 8) break;
                            if ((var27 & 1) == 0) {
                            } else {
                            }
                            if ((var27 & 2) == 0) {
                            } else {
                            }
                            if ((var27 & 4) == 0) {
                            } else {
                            }
                            var12.set(((float) ((-(var16) + var16) - var6.y)), var14, ((float) ((-(var16) + var16) - var6.z)), 1.0F);
                            var5.transform(var26);
                            if (var26.w <= 0.05F) {
                                pendingId = -2147483648;
                                comboId = -2147483648;
                            }
                        }
                    } catch (Throwable ignored) {
                    }
                } catch (Throwable ignored) {
                }
                if (var4 != null) {
                    var4.close();
                }
                pendingId = -2147483648;
                comboId = -2147483648;
                return;
                try {
                    try {
                        var18 = Math.min(var18, ((double) (var26.x / var26.w)));
                        var22 = Math.max(var22, ((double) (var26.x / var26.w)));
                        var20 = Math.min(var20, ((double) (var26.y / var26.w)));
                        var24 = Math.max(var24, ((double) (var26.y / var26.w)));
                        var27++;
                        var18 = Math.max(-1.0, var18);
                        var20 = Math.max(-1.0, var20);
                        var22 = Math.min(1.0, var22);
                        var24 = Math.min(1.0, var24);
                        if ((var22 - var18) >= 0.005) {
                            if ((var24 - var20) < 0.005) {
                            }
                        }
                        pendingId = -2147483648;
                        comboId = -2147483648;
                    } catch (Throwable ignored) {
                    }
                } catch (Throwable ignored) {
                }
                if (var4 != null) {
                    var4.close();
                }
                pendingId = -2147483648;
                comboId = -2147483648;
                return;
                try {
                    try {
                        var27 = (((var22 - var18) * 0.5) * ((double) var3.width));
                        double var29 = (((var24 - var20) * 0.5) * ((double) var3.height));
                        double var31 = Math.min(1.0, (512.0 / Math.max(var27, var29)));
                        int var33 = Math.max(8, ((int) Math.round((var27 * var31))));
                        int var34 = Math.max(8, ((int) Math.round((var29 * var31))));
                        CorpseFx.Slot var35 = CorpseFx.acquire(var33, var34);
                        int[] var36 = new int[2];
                        var37 = 0;
                        while (true) {
                            if (var37 >= 2) break;
                            GlStateManager._activeTexture((33984 + var37));
                            var36[var37] = GL11.glGetInteger(35097);
                            var37++;
                        }
                        GlStateManager._disableBlend();
                        GlStateManager._disableDepthTest();
                        GlStateManager._disableScissorTest();
                        GlStateManager._disableCull();
                        GL30.glBindFramebuffer(36160, CorpseFx.fbo(var35));
                        GL11.glDrawBuffer(36064);
                        GlStateManager._viewport(0, 0, var33, var34);
                        bind(0, mask.tex);
                        bind(1, RawFramebufferUtil.getColorTextureId(var3));
                        capShader.bind();
                        capShader.setUniform1i("Mask", 0);
                        capShader.setUniform1i("Scene", 1);
                        capShader.setUniform4f("uRect", ((float) ((var18 * 0.5) + 0.5)), ((float) ((var20 * 0.5) + 0.5)), ((float) ((var22 * 0.5) + 0.5)), ((float) ((var24 * 0.5) + 0.5)));
                        var37 = comboTagColor;
                        capShader.setUniform3f("uTag", (((float) ((var37 >> 16) & 255)) / 255.0F), (((float) ((var37 >> 8) & 255)) / 255.0F), (((float) (var37 & 255)) / 255.0F));
                        ShaderHelper.drawFullScreenQuad();
                        capShader.unbind();
                        var37 = 0;
                        while (true) {
                            if (var37 >= 2) break;
                            GL33.glBindSampler(var37, var36[var37]);
                            var37++;
                        }
                        GlStateManager._activeTexture(33984);
                        var38 = ((float) ((-(var16) + var16) - var6.x));
                        var39 = 0;
                        while (true) {
                            if (var39 >= 2) break;
                            GL33.glBindSampler(var39, var36[var39]);
                            var39++;
                        }
                        GlStateManager._activeTexture(33984);
                        throw var38;
                        var37 = new float[3];
                        var38 = new float[3];
                        var39 = new float[3];
                        SkyShaderRenderer.cameraAxes(var37, var38, var39);
                        double var40 = (var10 - var6.x);
                        double var42 = (var12 - var6.y);
                        double var44 = (var14 - var6.z);
                        var26.set(((float) var40), ((float) var42), ((float) var44), 1.0F);
                        var5.transform(var26);
                        double var46 = ((double) (var26.x / var26.w));
                        double var48 = ((double) (var26.y / var26.w));
                        var26.set(((float) (var40 + ((double) var37[0]))), ((float) (var42 + ((double) var37[1]))), ((float) (var44 + ((double) var37[2]))), 1.0F);
                        var5.transform(var26);
                        double var50 = (((double) (var26.x / var26.w)) - var46);
                        var26.set(((float) (var40 + ((double) var38[0]))), ((float) (var42 + ((double) var38[1]))), ((float) (var44 + ((double) var38[2]))), 1.0F);
                        var5.transform(var26);
                        double var52 = (((double) (var26.y / var26.w)) - var48);
                        if (Math.abs(var50) >= 1.0E-5) {
                            if (Math.abs(var52) < 1.0E-5) {
                            }
                        }
                        if (var4 != null) {
                            var4.close();
                        }
                        pendingId = -2147483648;
                        comboId = -2147483648;
                        return;
                        try {
                            try {
                                double var54 = ((var22 - var18) / var50);
                                double var56 = ((var24 - var20) / var52);
                                double var58 = ((((var18 + var22) * 0.5) - var46) / var50);
                                double var60 = ((((var20 + var24) * 0.5) - var48) / var52);
                                new double[3][0] = var10;
                                new double[3][1] = var12;
                                new double[3][2] = var14;
                                CorpseFx.addAnchored(var35, new double[3], var58, var60, var54, var56, var9.minY, pendingStyle);
                                hidden.put(var0, System.nanoTime());
                            } catch (Throwable ignored) {
                            }
                        } catch (Throwable ignored) {
                        }
                        if (var4 != null) {
                            var4.close();
                            var5 = var10;
                            if (var4 != null) {
                                try {
                                    var4.close();
                                } catch (Throwable ignored) {
                                }
                                var6 = var26;
                                var5.addSuppressed(var6);
                            }
                            throw var5;
                        }
                        pendingId = -2147483648;
                        comboId = -2147483648;
                        var4 = null;
                        pendingId = -2147483648;
                        comboId = -2147483648;
                        pendingId = -2147483648;
                        comboId = -2147483648;
                        throw var62;
                        return;
                    } catch (Throwable ignored) {
                    }
                } catch (Throwable ignored) {
                }
            } catch (Throwable ignored) {
            }
        }
    }

    private static double[] unproject(Matrix4f arg0, double arg1, double arg2, float arg3, Vec3 arg4) {
        Vector4f var7 = new Vector4f(((float) arg1), ((float) arg2), arg3, 1.0F);
        arg0.transform(var7);
        new double[3][0] = (((double) (var7.x / var7.w)) + arg4.x);
        new double[3][1] = (((double) (var7.y / var7.w)) + arg4.y);
        new double[3][2] = (((double) (var7.z / var7.w)) + arg4.z);
        return new double[3];
    }

    private static boolean draw(RenderTarget arg0) {
        ShaderEspRenderer.Target var13 = null;
        float var14 = 0.0F;
        int var15 = 0;
        ShaderEspRenderer.Target var16 = null;
        int var1 = arg0.width;
        int var2 = arg0.height;
        float var3 = (((float) var2) / 1080.0F);
        boolean var4 = ShaderESP.isEligible();
        if (var4) {
        } else {
        }
        String var5 = "Glow";
        boolean var6 = "Blur".equals(var5);
        GlStateManager._disableBlend();
        GlStateManager._disableDepthTest();
        GlStateManager._disableScissorTest();
        GlStateManager._disableCull();
        if (var4) {
        } else {
        }
        float var7 = ItemESP.getGlowRadius();
        if (var6) {
        } else {
        }
        float var8 = var7;
        float var9 = Math.max(0.5F, ((var8 * var3) / 1.6F));
        if (var9 <= 6.0F) {
        } else {
        }
        int var10 = 4;
        if (var10 == 2) {
        } else {
        }
        ShaderEspRenderer.Target var11 = g4a;
        if (var10 == 2) {
        } else {
        }
        ShaderEspRenderer.Target var12 = g4b;
        if (ensure(var11, (var1 / var10), (var2 / var10), true)) {
            if (!(ensure(var12, (var1 / var10), (var2 / var10), true))) {
            }
        }
        return false;
        bind(0, mask.tex);
        target(var11);
        downShader.bind();
        downShader.setUniform1i("Mask", 0);
        downShader.setUniform3f("uSkip", 0.99607843F, 0.0F, 0.99607843F);
        downShader.setUniform2f("srcTexel", (1.0F / ((float) mask.w)), (1.0F / ((float) mask.h)));
        if (var10 == 2) {
        } else {
        }
        "spread".setUniform1f(0.5F, 1.0F);
        ShaderHelper.drawFullScreenQuad();
        downShader.unbind();
        blur(var11, var12, (var9 / ((float) var10)));
        var13 = null;
        if (var6) {
            var14 = (ShaderESP.computeFloatMetric3() * var3);
            if (var14 <= 8.0F) {
            } else {
            }
            var15 = 4;
            if (var15 == 2) {
            } else {
            }
            var13 = s4a;
            if (var15 == 2) {
            } else {
            }
            var16 = s4b;
            if (ensure(var13, (var1 / var15), (var2 / var15), true)) {
                if (!(ensure(var16, (var1 / var15), (var2 / var15), true))) {
                }
            }
            return false;
            if (!RawFramebufferUtil.bindDirect(arg0)) {
                return false;
            }
            GL30.glBindFramebuffer(36009, var13.fbo);
            GL30.glBlitFramebuffer(0, 0, var1, var2, 0, 0, var13.w, var13.h, 16384, 9729);
            if ((var14 / ((float) var15)) > 0.3F) {
                blur(var13, var16, (var14 / ((float) var15)));
            }
            if (!RawFramebufferUtil.bindDirect(arg0)) {
                return false;
            }
            GlStateManager._enableBlend();
            GlStateManager._blendFuncSeparate(1, 771, 1, 771);
            GL14.glBlendEquation(32774);
            bind(0, mask.tex);
            bind(2, var11.tex);
            if (var13 != null) {
                bind(1, var13.tex);
            }
            var15 = var5;
            var16 = -1;
            switch (var15.hashCode()) {
                case 2073735 -> {}
                case 2424310 -> {}
                case 2570909 -> {}
                case 77382433 -> {}
                case 2125565744 -> {}
                default -> {}
            }
            if (var15.equals("Blur")) {
                var16 = 0;
                if (var15.equals("Galaxy")) {
                    var16 = 1;
                    if (var15.equals("Prism")) {
                        var16 = 2;
                        if (var15.equals("Neon")) {
                            var16 = 3;
                            if (var15.equals("Scan")) {
                                var16 = 4;
                            }
                            switch (var16) {
                                case 0 -> {}
                                case 1 -> {}
                                case 2 -> {}
                                case 3 -> {}
                                case 4 -> {}
                                default -> {}
                            }
                            var14 = glowShader;
                            var15 = ((float) (animTime % 3600.0));
                            var14.bind();
                            var14.setUniform1i("Mask", 0);
                            var14.setUniform1i("Scene", 1);
                            var14.setUniform1i("Glow", 2);
                            var14.setUniform2f("texel", (1.0F / ((float) var1)), (1.0F / ((float) var2)));
                            var14.setUniform1f("pxScale", var3);
                            var14.setUniform1f("time", var15);
                            if (var4) {
                                var14.setUniform1i("chroma", (ShaderESP.isEligible1()));
                                if (var4) {
                                    if (ShaderESP.isEligible2()) {
                                    }
                                }
                                "outline".setUniform1f(1.0F, 0.0F);
                                if (var4) {
                                } else {
                                }
                                "intensity".setUniform1f(ShaderESP.computeFloatMetric1(), 1.0F);
                                if (var4) {
                                } else {
                                }
                                "fill".setUniform1f(ShaderESP.computeFloatMetric2(), 0.3F);
                                var14.setUniform3f("uSkip", 0.99607843F, 0.0F, 0.99607843F);
                                var14.setUniform1f("tintAmount", ShaderESP.computeFloatMetric4());
                                var14.setUniform1f("aspect", (((float) var1) / ((float) var2)));
                                if (var14 == prismShader) {
                                    var16 = SkyShaders.evaluate();
                                    var14.setUniform3f("uAccent", var16[0][0], var16[0][1], var16[0][2]);
                                    var14.setUniform3f("uAccent2", var16[1][0], var16[1][1], var16[1][2]);
                                }
                                ShaderHelper.drawFullScreenQuad();
                                var14.unbind();
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void blur(ShaderEspRenderer.Target arg0, ShaderEspRenderer.Target arg1, float arg2) {
        arg2 = Math.min(arg2, 16.0F);
        blurShader.bind();
        blurShader.setUniform1i("Source", 2);
        blurShader.setUniform1f("sigma", arg2);
        target(arg1);
        bind(2, arg0.tex);
        blurShader.setUniform2f("direction", (1.0F / ((float) arg0.w)), 0.0F);
        ShaderHelper.drawFullScreenQuad();
        target(arg0);
        bind(2, arg1.tex);
        blurShader.setUniform2f("direction", 0.0F, (1.0F / ((float) arg1.h)));
        ShaderHelper.drawFullScreenQuad();
        blurShader.unbind();
    }

    private static void tick() {
        long var0 = System.nanoTime();
        if (lastNanos == 0L) {
        } else {
        }
        double var2 = Math.min(0.1, (((double) (var0 - lastNanos)) / 1.0E9));
        lastNanos = var0;
        if (ShaderESP.isEligible()) {
        } else {
        }
        animTime = (var2 + (ShaderESP.computeFloatMetric5() * ((double) 1.0F)));
    }

    private static boolean ensureInit() {
        if (initDone) {
            return true;
        }
        if (initFailed) {
            return false;
        }
        try {
            downShader = new Shader("esp", "esp_down");
            blurShader = new Shader("hand", "fx_blur");
            glowShader = new Shader("esp", "esp_glow");
            blurModeShader = new Shader("esp", "esp_blur");
            galaxyShader = new Shader("esp", "esp_galaxy");
            prismShader = new Shader("esp", "esp_prism");
            neonShader = new Shader("esp", "esp_neon");
            scanShader = new Shader("esp", "esp_scan");
            samplerLinear = GL33.glGenSamplers();
            GL33.glSamplerParameteri(samplerLinear, 10241, 9729);
            GL33.glSamplerParameteri(samplerLinear, 10240, 9729);
            GL33.glSamplerParameteri(samplerLinear, 10242, 33071);
            GL33.glSamplerParameteri(samplerLinear, 10243, 33071);
            initDone = true;
        } catch (Exception ignored) {
        }
        initFailed = true;
        return initDone;
    }

    private static void target(ShaderEspRenderer.Target arg0) {
        GL30.glBindFramebuffer(36160, arg0.fbo);
        GL11.glDrawBuffer(36064);
        GlStateManager._viewport(0, 0, arg0.w, arg0.h);
    }

    private static void bind(int arg0, int arg1) {
        GlStateManager._activeTexture((33984 + arg0));
        GlStateManager._bindTexture(arg1);
        GL33.glBindSampler(arg0, samplerLinear);
        GlStateManager._activeTexture(33984);
    }

    private static boolean ensure(ShaderEspRenderer.Target arg0, int arg1, int arg2, boolean arg3) {
        arg1 = Math.max(1, arg1);
        arg2 = Math.max(1, arg2);
        if (arg0.fbo != 0) {
            if (arg0.w == arg1) {
                if (arg0.h == arg2) {
                    return true;
                }
                if (arg0.tex == 0) {
                    arg0.tex = GL11.glGenTextures();
                }
                GlStateManager._activeTexture(33984);
                int var4 = GL11.glGetInteger(32873);
                GlStateManager._bindTexture(arg0.tex);
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
                GL11.glTexParameteri(3553, 10242, 33071);
                GL11.glTexParameteri(3553, 10243, 33071);
                GL11.glTexParameteri(3553, 33085, 0);
                int var5 = GL11.glGetInteger(35055);
                GL15.glBindBuffer(35052, 0);
                if (arg3) {
                    GL11.glTexImage2D(3553, 0, 34842, arg1, arg2, 0, 6408, 5126, ((FloatBuffer) null));
                } else {
                    GL11.glTexImage2D(3553, 0, 32856, arg1, arg2, 0, 6408, 5121, ((ByteBuffer) null));
                }
                GL15.glBindBuffer(35052, var5);
                GlStateManager._bindTexture(var4);
                if (arg0.fbo == 0) {
                    arg0.fbo = GL30.glGenFramebuffers();
                }
                int var6 = GL11.glGetInteger(36006);
                GL30.glBindFramebuffer(36160, arg0.fbo);
                GL30.glFramebufferTexture2D(36160, 36064, 3553, arg0.tex, 0);
                int var7 = (GL30.glCheckFramebufferStatus(36160) == 36053);
                GL30.glBindFramebuffer(36160, var6);
                if (var7 != 0) {
                } else {
                }
                arg1.w = 0;
                if (var7 != 0) {
                } else {
                }
                arg2.h = 0;
                if (var7 == 0) {
                }
                return var7;
            }
        }
    }

    static {
        Hooks.act1("ShaderEsp.tagState", (p0) -> ShaderEspRenderer.lambda$static$0(p0));
        Hooks.act1("ShaderEsp.afterExtract", (p0) -> ShaderEspRenderer.lambda$static$1(p0));
        Hooks.act0("ShaderEsp.captureMask", () -> ShaderEspRenderer.captureMask());
        Hooks.bool0("ShaderEsp.onDoEntityOutline", () -> ShaderEspRenderer.onDoEntityOutline());
        Hooks.act1("ShaderEsp.registerOutlinePasses", (p0) -> ShaderEspRenderer.lambda$static$2(p0));
        Hooks.bool1("ShaderEsp.onOutlinePass", (p0) -> ShaderEspRenderer.onOutlinePass(p0));
        Hooks.bool1("ShaderEsp.isHidden", (p0) -> ShaderEspRenderer.lambda$static$3(p0));
        tagged = new IdentityHashMap();
        taggedThisFrame = false;
        frame = 0L;
        maskFrame = -1L;
        compositeFrame = -1L;
        outlinePasses = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
        initDone = false;
        initFailed = false;
        mask = new ShaderEspRenderer.Target();
        g2a = new ShaderEspRenderer.Target();
        g2b = new ShaderEspRenderer.Target();
        g4a = new ShaderEspRenderer.Target();
        g4b = new ShaderEspRenderer.Target();
        s2a = new ShaderEspRenderer.Target();
        s2b = new ShaderEspRenderer.Target();
        s4a = new ShaderEspRenderer.Target();
        s4b = new ShaderEspRenderer.Target();
        samplerLinear = 0;
        START = System.nanoTime();
        animTime = 0.0;
        lastNanos = 0L;
        comboId = -2147483648;
        comboTagColor = 16646398;
        pendingId = -2147483648;
        pendingStyle = 0;
        pendingAt = 0L;
        hidden = new ConcurrentHashMap();
    }

    public final class Target {
        int tex;
        int fbo;
        int w;
        int h;

        private Target() {
        }

    }

}
