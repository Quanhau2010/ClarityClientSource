package need.clarity.render.handshader;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderTarget;
import java.nio.FloatBuffer;
import need.clarity.modules.visual.HandShaders;
import need.clarity.render.RawFramebufferUtil;
import need.clarity.render.RawGlStateCapture;
import need.clarity.render.Shader;
import need.clarity.render.ShaderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL33;

public final class HandFx {
    private static Shader maskShader;
    private static Shader blurShader;
    private static Shader glowShader;
    private static Shader glassShader;
    private static Shader holoShader;
    private static Shader plainBlurShader;
    private static Shader wispAdvect;
    private static Shader wispShader;
    private static final HandFx.Target wispA;
    private static final HandFx.Target wispB;
    private static boolean initDone;
    private static boolean initFailed;
    private static final HandFx.Target half1;
    private static final HandFx.Target half2;
    private static final HandFx.Target quarter1;
    private static final HandFx.Target quarter2;
    private static int samplerLinear;
    private static int samplerNearest;
    private static boolean bgValid;
    private static int bgDiv;
    private static long wispLastNanos;
    private static float windX;
    private static float windY;
    private static float windVX;
    private static float windVY;
    private static float lastYaw;
    private static float lastPitch;
    private static boolean haveLook;
    private static final long START;

    private HandFx() {
    }

    public static boolean handles(String arg0) {
        if (!"Glass".equals(arg0)) {
            if (!"Glow".equals(arg0)) {
                if (!"Hologram".equals(arg0)) {
                    if (!"Blur".equals(arg0)) {
                        if ("Wisp".equals(arg0)) {
                        }
                    }
                    return false;
                }
            }
        }
    }

    private static boolean ensureInit() {
        if (initDone) {
            return true;
        }
        if (initFailed) {
            return false;
        }
        try {
            maskShader = new Shader("hand", "fx_mask");
            blurShader = new Shader("hand", "fx_blur");
            glowShader = new Shader("hand", "glowfx");
            glassShader = new Shader("hand", "glass");
            holoShader = new Shader("hand", "holo");
            plainBlurShader = new Shader("hand", "plainblur");
            wispAdvect = new Shader("hand", "wisp_advect");
            wispShader = new Shader("hand", "wisp");
            samplerLinear = makeSampler(9729);
            samplerNearest = makeSampler(9728);
            initDone = true;
        } catch (Exception ignored) {
        }
        initFailed = true;
        return initDone;
    }

    private static int makeSampler(int arg0) {
        int var1 = GL33.glGenSamplers();
        GL33.glSamplerParameteri(var1, 10241, arg0);
        GL33.glSamplerParameteri(var1, 10240, arg0);
        GL33.glSamplerParameteri(var1, 10242, 33071);
        GL33.glSamplerParameteri(var1, 10243, 33071);
        return var1;
    }

    private static int glassDiv(float arg0) {
        if ((HandShaders.getBlur() * arg0) <= 8.0F) {
        } else {
        }
        return 4;
    }

    public static void captureBackground() {
        RawGlStateCapture var3 = null;
        int var4 = 0;
        HandFx.Target var5 = null;
        bgValid = false;
        String var0 = HandShaders.enabled();
        if ("Blur".equals(var0)) {
            int var1 = (!HandShaders.isBlurTarget());
            if (!"Glass".equals(var0)) {
                if (!"Hologram".equals(var0)) {
                    if (var1 == 0) {
                        return;
                    }
                    RenderTarget var2 = Minecraft.getInstance().getMainRenderTarget();
                    if (var2 != null) {
                        if (var2.width > 0) {
                            if (var2.height > 0) {
                                if (!ensureInit()) {
                                }
                            }
                            return;
                            try {
                                var3 = RawGlStateCapture.capture();
                                try {
                                    if (!"Glass".equals(var0)) {
                                        if (var1 != 0) {
                                        }
                                    }
                                    var4 = 2;
                                    if (var4 == 2) {
                                    } else {
                                    }
                                    var5 = quarter1;
                                    if (!(ensure(var5, (var2.width / var4), (var2.height / var4)))) {
                                    }
                                } catch (Throwable ignored) {
                                }
                                if (var3 != null) {
                                    var3.close();
                                }
                                return;
                                try {
                                    try {
                                        if (!RawFramebufferUtil.bindDirect(var2)) {
                                        }
                                    } catch (Throwable ignored) {
                                    }
                                } catch (Throwable ignored) {
                                }
                                if (var3 != null) {
                                    var3.close();
                                }
                                return;
                                try {
                                    try {
                                        GlStateManager._disableScissorTest();
                                        GL30.glBindFramebuffer(36009, var5.fbo);
                                        GL30.glBlitFramebuffer(0, 0, var2.width, var2.height, 0, 0, var5.w, var5.h, 16384, 9729);
                                        bgValid = true;
                                        bgDiv = var4;
                                    } catch (Throwable ignored) {
                                    }
                                } catch (Throwable ignored) {
                                }
                                if (var3 != null) {
                                    var3.close();
                                    var4 = half1;
                                    if (var3 != null) {
                                        try {
                                            var3.close();
                                        } catch (Throwable ignored) {
                                        }
                                        var5 = glassDiv((((float) var2.height) / 1080.0F));
                                        var4.addSuppressed(var5);
                                    }
                                    throw var4;
                                }
                                return;
                            } catch (Throwable ignored) {
                            }
                        }
                    }
                }
            }
        }
    }

    public static void render(String arg0, RenderTarget arg1, RenderTarget arg2) {
        int var6 = 0;
        int var7 = 0;
        int var11 = 0;
        if (!ensureInit()) {
            return;
        }
        int var3 = RawFramebufferUtil.getColorTextureId(arg2);
        int var4 = RawFramebufferUtil.getDepthTextureId(arg2);
        if (var3 != 0) {
            if (var4 == 0) {
            }
        }
        return;
        int[] var5 = new int[4];
        var6 = 0;
        while (true) {
            if (var6 >= 4) break;
            GlStateManager._activeTexture((33984 + var6));
            var5[var6] = GL11.glGetInteger(35097);
            var6++;
        }
        GlStateManager._activeTexture(33987);
        var6 = GL11.glGetInteger(32873);
        GlStateManager._disableBlend();
        GlStateManager._disableDepthTest();
        GlStateManager._disableScissorTest();
        GlStateManager._disableCull();
        bind(0, var3, samplerLinear);
        bind(1, var4, samplerNearest);
        var7 = arg1.width;
        int var8 = arg1.height;
        float var9 = (((float) var8) / 1080.0F);
        var11 = -1;
        switch (arg0.hashCode()) {
            case -440373695 -> {}
            case 2073735 -> {}
            case 2222509 -> {}
            case 2696399 -> {}
            case 68884316 -> {}
            default -> {}
        }
        if (arg0.equals("Glow")) {
            var11 = 0;
            if (arg0.equals("Glass")) {
                var11 = 1;
                if (arg0.equals("Hologram")) {
                    var11 = 2;
                    if (arg0.equals("Blur")) {
                        var11 = 3;
                        if (arg0.equals("Wisp")) {
                            var11 = 4;
                        }
                        switch (var11) {
                            case 0 -> {}
                            case 1 -> {}
                            case 2 -> {}
                            case 3 -> {}
                            case 4 -> {}
                            default -> {}
                        }
                        glow(arg1, var7, var8, var9);
                        glass(arg1, var7, var8, var9);
                        hologram(arg1, var7, var8, var9);
                        plainBlur(arg1, var7, var8, var9);
                        wisp(arg1, var7, var8);
                        var7 = 0;
                        while (true) {
                            if (var7 >= 4) break;
                            GL33.glBindSampler(var7, var5[var7]);
                            var7++;
                        }
                        GlStateManager._activeTexture(33987);
                        GlStateManager._bindTexture(var6);
                        GlStateManager._activeTexture(33984);
                        bgValid = false;
                        int var13 = 0;
                        while (true) {
                            if (var13 >= 4) break;
                            GL33.glBindSampler(var13, var5[var13]);
                            var13++;
                        }
                        GlStateManager._activeTexture(33987);
                        GlStateManager._bindTexture(var6);
                        GlStateManager._activeTexture(33984);
                        bgValid = false;
                        throw var12;
                        return;
                    }
                }
            }
        }
    }

    private static void glow(RenderTarget arg0, int arg1, int arg2, float arg3) {
        int var10 = 0;
        float var4 = Math.max(0.5F, ((HandShaders.getGlowRadius() * arg3) / 1.6F));
        if (var4 <= 6.0F) {
        } else {
        }
        int var5 = 4;
        if (var5 == 2) {
        } else {
        }
        HandFx.Target var6 = quarter1;
        if (var5 == 2) {
        } else {
        }
        HandFx.Target var7 = quarter2;
        if (ensure(var6, (arg1 / var5), (arg2 / var5))) {
            if (!(ensure(var7, (arg1 / var5), (arg2 / var5)))) {
            }
        }
        return;
        mask(var6, arg1, arg2, var5);
        blur(var6, var7, (var4 / ((float) var5)));
        if (!RawFramebufferUtil.bindDirect(arg0)) {
            return;
        }
        bind(2, var6.tex, samplerLinear);
        int var8 = HandShaders.computeColorOrIndex7();
        glowShader.bind();
        glowShader.setUniform1i("ColorTexture", 0);
        glowShader.setUniform1i("DepthTexture", 1);
        glowShader.setUniform1i("GlowTexture", 2);
        glowShader.setUniform3f("glowColor", r(var8), g(var8), b(var8));
        glowShader.setUniform1f("intensity", HandShaders.getGlowIntensity());
        String var9 = HandShaders.getGlowMode();
        var10 = -1;
        switch (var9.hashCode()) {
            case -171363555 -> {}
            case 2017705622 -> {}
            default -> {}
        }
        if (var9.equals("Item Colors")) {
            var10 = 0;
            if (var9.equals("Chroma")) {
                var10 = 1;
            }
            switch (var10) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            1.setUniform1i(2, 0);
            glowShader.setUniform1f("time", time());
            if (HandShaders.isGlowPulse()) {
            } else {
            }
            "pulse".setUniform1f(1.0F, 0.0F);
            ShaderHelper.drawFullScreenQuad();
            glowShader.unbind();
            return;
        }
    }

    private static void plainBlur(RenderTarget arg0, int arg1, int arg2, float arg3) {
        boolean var4 = HandShaders.isBlurTarget();
        if (!var4) {
            if (!bgValid) {
                return;
            }
            int var5 = bgDiv;
            if (var5 == 2) {
            } else {
            }
            HandFx.Target var6 = quarter1;
            if (var5 == 2) {
            } else {
            }
            HandFx.Target var7 = quarter2;
            if (var6.fbo != 0) {
                if (!(ensure(var7, var6.w, var6.h))) {
                }
            }
            return;
            float var8 = ((HandShaders.getBlur() * arg3) / ((float) var5));
            if (var8 > 0.3F) {
                blur(var6, var7, var8);
            }
            if (!RawFramebufferUtil.bindDirect(arg0)) {
                return;
            }
            bind(2, var6.tex, samplerLinear);
        } else {
            if (!RawFramebufferUtil.bindDirect(arg0)) {
                return;
            }
            plainBlurShader.bind();
            plainBlurShader.setUniform1i("ColorTexture", 0);
            plainBlurShader.setUniform1i("DepthTexture", 1);
            plainBlurShader.setUniform1i("Background", 2);
            plainBlurShader.setUniform1i("selfBlur", (var4));
            plainBlurShader.setUniform2f("texel", (1.0F / ((float) arg1)), (1.0F / ((float) arg2)));
            plainBlurShader.setUniform1f("radius", (HandShaders.getBlur() * arg3));
            ShaderHelper.drawFullScreenQuad();
            plainBlurShader.unbind();
            return;
        }
    }

    private static void glass(RenderTarget arg0, int arg1, int arg2, float arg3) {
        if (!bgValid) {
            return;
        }
        int var4 = bgDiv;
        if (var4 == 2) {
        } else {
        }
        HandFx.Target var5 = quarter1;
        if (var4 == 2) {
        } else {
        }
        HandFx.Target var6 = quarter2;
        if (var5.fbo != 0) {
            if (!(ensure(var6, var5.w, var5.h))) {
            }
        }
        return;
        float var7 = ((HandShaders.getBlur() * arg3) / ((float) var4));
        if (var7 > 0.3F) {
            blur(var5, var6, var7);
        }
        if (!RawFramebufferUtil.bindDirect(arg0)) {
            return;
        }
        bind(2, var5.tex, samplerLinear);
        int var8 = HandShaders.computeColorOrIndex6();
        glassShader.bind();
        glassShader.setUniform1i("ColorTexture", 0);
        glassShader.setUniform1i("DepthTexture", 1);
        glassShader.setUniform1i("Background", 2);
        glassShader.setUniform2f("texel", (1.0F / ((float) arg1)), (1.0F / ((float) arg2)));
        glassShader.setUniform1f("pxScale", arg3);
        glassShader.setUniform3f("tint", r(var8), g(var8), b(var8));
        glassShader.setUniform1f("tintAmount", HandShaders.getGlassTintAmount());
        glassShader.setUniform1f("refraction", HandShaders.getRefraction());
        glassShader.setUniform1f("detail", HandShaders.getDetail());
        glassShader.setUniform1f("rim", HandShaders.getRimLight());
        ShaderHelper.drawFullScreenQuad();
        glassShader.unbind();
    }

    private static void hologram(RenderTarget arg0, int arg1, int arg2, float arg3) {
        float var4 = HandShaders.getHalo();
        if (var4 > 0.01F) {
            if (ensure(quarter1, (arg1 / 4), (arg2 / 4))) {
                if (!(ensure(quarter2, (arg1 / 4), (arg2 / 4)))) {
                }
            }
            return;
            mask(quarter1, arg1, arg2, 4);
            blur(quarter1, quarter2, Math.max(0.5F, ((9.0F * arg3) / 4.0F)));
        }
        if (bgValid) {
            if (bgDiv == 2) {
                int var5 = (half1.fbo != 0);
                if (!RawFramebufferUtil.bindDirect(arg0)) {
                    return;
                }
                if (var5 != 0) {
                } else {
                }
                bind(half1.tex, 0, samplerLinear);
                if (var4 > 0.01F) {
                } else {
                }
                bind(quarter1.tex, 0, samplerLinear);
                int var6 = HandShaders.computeColorOrIndex9();
                holoShader.bind();
                holoShader.setUniform1i("ColorTexture", 0);
                holoShader.setUniform1i("DepthTexture", 1);
                holoShader.setUniform1i("Background", 2);
                holoShader.setUniform1i("HaloTexture", 3);
                holoShader.setUniform2f("texel", (1.0F / ((float) arg1)), (1.0F / ((float) arg2)));
                holoShader.setUniform1f("pxScale", arg3);
                holoShader.setUniform3f("holoColor", r(var6), g(var6), b(var6));
                holoShader.setUniform1f("opacity", HandShaders.getHoloOpacity());
                if (HandShaders.isScanlines()) {
                } else {
                }
                "scanlines".setUniform1f(1.0F, 0.0F);
                if (HandShaders.isGlitch()) {
                } else {
                }
                "glitch".setUniform1f(1.0F, 0.0F);
                holoShader.setUniform1f("halo", var4);
                if (var5 != 0) {
                } else {
                }
                "hasBackground".setUniform1f(1.0F, 0.0F);
                holoShader.setUniform1f("time", time());
                ShaderHelper.drawFullScreenQuad();
                holoShader.unbind();
                return;
            }
        }
    }

    private static void wisp(RenderTarget arg0, int arg1, int arg2) {
        float var8 = 0.0F;
        int var16 = 0;
        int var3 = Math.max(1, (arg1 / 4));
        int var4 = Math.max(1, (arg2 / 4));
        if (wispA.w == var3) {
            if (wispA.h != var4) {
            }
        }
        int var5 = 0;
        if (ensure(quarter1, var3, var4)) {
            if (ensure(wispA, var3, var4)) {
                if (!(ensure(wispB, var3, var4))) {
                }
            }
            return;
            long var6 = System.nanoTime();
            if (wispLastNanos == 0L) {
            } else {
            }
            var8 = (((float) (var6 - wispLastNanos)) / 1.0E9F);
            if (wispLastNanos != 0L) {
                if (var8 <= 0.5F) {
                    if (var5 != 0) {
                    }
                }
                int var9 = 0;
                wispLastNanos = var6;
                var8 = Math.max(0.001F, Math.min(var8, 0.05F));
                if (var9 != 0) {
                    clear(wispA);
                    clear(wispB);
                    windVY = 0.0F;
                    windVX = 0.0F;
                    windY = 0.0F;
                    windX = 0.0F;
                    haveLook = false;
                }
                updateWind(var8, (((float) arg1) / ((float) arg2)));
                mask(quarter1, arg1, arg2, 4);
                float var10 = HandShaders.getTrailLength();
                float var11 = ((float) Math.exp(((double) (-(var8) / Math.max(0.03F, (var10 / 3.0F))))));
                target(wispB);
                bind(2, wispA.tex, samplerLinear);
                bind(3, quarter1.tex, samplerLinear);
                wispAdvect.bind();
                wispAdvect.setUniform1i("History", 2);
                wispAdvect.setUniform1i("Mask", 3);
                wispAdvect.setUniform2f("wind", windX, windY);
                wispAdvect.setUniform1f("dt", var8);
                wispAdvect.setUniform1f("decay", var11);
                wispAdvect.setUniform1f("turbulence", HandShaders.getTurbulence());
                wispAdvect.setUniform1f("time", time());
                wispAdvect.setUniform1f("aspect", (((float) arg1) / ((float) arg2)));
                wispAdvect.setUniform1f("rise", (0.05F * HandShaders.getTurbulence()));
                wispAdvect.setUniform2f("texel", (1.0F / ((float) var3)), (1.0F / ((float) var4)));
                wispAdvect.setUniform1f("spread", Math.min(3.0F, (2.0F * ((float) Math.sqrt(((double) (var8 * 60.0F)))))));
                ShaderHelper.drawFullScreenQuad();
                wispAdvect.unbind();
                int var12 = wispA.tex;
                wispA.tex = wispB.tex;
                wispB.tex = var12;
                int var13 = wispA.fbo;
                wispA.fbo = wispB.fbo;
                wispB.fbo = var13;
                if (!RawFramebufferUtil.bindDirect(arg0)) {
                    return;
                }
                bind(2, wispA.tex, samplerLinear);
                int var14 = HandShaders.computeColorOrIndex8();
                wispShader.bind();
                wispShader.setUniform1i("ColorTexture", 0);
                wispShader.setUniform1i("DepthTexture", 1);
                wispShader.setUniform1i("WispTexture", 2);
                wispShader.setUniform3f("wispColor", r(var14), g(var14), b(var14));
                wispShader.setUniform1f("intensity", HandShaders.getWispIntensity());
                String var15 = HandShaders.getWispMode();
                var16 = -1;
                switch (var15.hashCode()) {
                    case -171363555 -> {}
                    case 2017705622 -> {}
                    default -> {}
                }
                if (var15.equals("Item Colors")) {
                    var16 = 0;
                    if (var15.equals("Chroma")) {
                        var16 = 1;
                    }
                    switch (var16) {
                        case 0 -> {}
                        case 1 -> {}
                        default -> {}
                    }
                    1.setUniform1i(2, 0);
                    wispShader.setUniform1f("time", time());
                    wispShader.setUniform2f("texel", (1.0F / ((float) var3)), (1.0F / ((float) var4)));
                    wispShader.setUniform1f("aspect", (((float) arg1) / ((float) arg2)));
                    ShaderHelper.drawFullScreenQuad();
                    wispShader.unbind();
                    return;
                }
            }
        }
    }

    private static void updateWind(float arg0, float arg1) {
        float var3 = 0.0F;
        float var4 = 0.0F;
        float var5 = 0.0F;
        float var6 = 0.0F;
        float var7 = 0.0F;
        float var8 = 0.0F;
        float var9 = 0.0F;
        double var10 = 0.0;
        Minecraft var2 = Minecraft.getInstance();
        var3 = 0.0F;
        var4 = 0.0F;
        if (var2.player != null) {
            var5 = var2.player.getYRot();
            var6 = var2.player.getXRot();
            if (haveLook) {
                var7 = (Mth.wrapDegrees((var5 - lastYaw)) / arg0);
                var8 = ((var6 - lastPitch) / arg0);
                var9 = 1.428F;
                var3 = (var3 - (((((float) Math.toRadians(((double) var7))) * var9) * 0.5F) / arg1));
                var4 = (var4 + ((((float) Math.toRadians(((double) var8))) * var9) * 0.5F));
            }
            lastYaw = var5;
            lastPitch = var6;
            haveLook = true;
            var7 = var2.player.getDeltaMovement();
            var8 = Math.toRadians(((double) var5));
            var10 = ((-(Math.cos(var8)) * var7.x) + (-(Math.sin(var8)) * var7.z));
            var3 = (var3 - ((((float) var10) * 20.0F) * 0.1F));
            var4 = (var4 - ((((float) var7.y) * 20.0F) * 0.06F));
        }
        var5 = (0.7F * HandShaders.getInertia());
        var3 = (var3 * var5);
        var4 = (var4 * var5);
        var6 = ((float) Math.hypot(((double) var3), ((double) var4)));
        if (var6 > 1.6F) {
            var3 = (var3 * (1.6F / var6));
            var4 = (var4 * (1.6F / var6));
        }
        var7 = 55.0F;
        var8 = 6.5F;
        var9 = Math.max(1, ((int) Math.ceil(((double) (arg0 * 240.0F)))));
        var10 = (arg0 / ((float) var9));
        int var11 = 0;
        while (true) {
            if (var11 >= var9) break;
            windVX = (windVX + ((((var3 - windX) * 55.0F) - (windVX * 6.5F)) * var10));
            windVY = (windVY + ((((var4 - windY) * 55.0F) - (windVY * 6.5F)) * var10));
            windX = (windX + (windVX * var10));
            windY = (windY + (windVY * var10));
            var11++;
        }
    }

    private static void clear(HandFx.Target arg0) {
        target(arg0);
        GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GL11.glClear(16384);
    }

    private static void mask(HandFx.Target arg0, int arg1, int arg2, int arg3) {
        target(arg0);
        maskShader.bind();
        maskShader.setUniform1i("ColorTexture", 0);
        maskShader.setUniform1i("DepthTexture", 1);
        maskShader.setUniform2f("srcTexel", (1.0F / ((float) arg1)), (1.0F / ((float) arg2)));
        if (arg3 == 2) {
        } else {
        }
        "spread".setUniform1f(0.5F, 1.0F);
        ShaderHelper.drawFullScreenQuad();
        maskShader.unbind();
    }

    private static void blur(HandFx.Target arg0, HandFx.Target arg1, float arg2) {
        arg2 = Math.min(arg2, 16.0F);
        blurShader.bind();
        blurShader.setUniform1i("Source", 2);
        blurShader.setUniform1f("sigma", arg2);
        target(arg1);
        bind(2, arg0.tex, samplerLinear);
        blurShader.setUniform2f("direction", (1.0F / ((float) arg0.w)), 0.0F);
        ShaderHelper.drawFullScreenQuad();
        target(arg0);
        bind(2, arg1.tex, samplerLinear);
        blurShader.setUniform2f("direction", 0.0F, (1.0F / ((float) arg1.h)));
        ShaderHelper.drawFullScreenQuad();
        blurShader.unbind();
    }

    private static void target(HandFx.Target arg0) {
        GL30.glBindFramebuffer(36160, arg0.fbo);
        GL11.glDrawBuffer(36064);
        GlStateManager._viewport(0, 0, arg0.w, arg0.h);
    }

    private static void bind(int arg0, int arg1, int arg2) {
        GlStateManager._activeTexture((33984 + arg0));
        GlStateManager._bindTexture(arg1);
        GL33.glBindSampler(arg0, arg2);
        GlStateManager._activeTexture(33984);
    }

    private static boolean ensure(HandFx.Target arg0, int arg1, int arg2) {
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
                int var3 = GL11.glGetInteger(32873);
                GlStateManager._bindTexture(arg0.tex);
                GL11.glTexParameteri(3553, 10241, 9729);
                GL11.glTexParameteri(3553, 10240, 9729);
                GL11.glTexParameteri(3553, 10242, 33071);
                GL11.glTexParameteri(3553, 10243, 33071);
                GL11.glTexParameteri(3553, 33085, 0);
                int var4 = GL11.glGetInteger(35055);
                GL15.glBindBuffer(35052, 0);
                GL11.glTexImage2D(3553, 0, 34842, arg1, arg2, 0, 6408, 5126, ((FloatBuffer) null));
                GL15.glBindBuffer(35052, var4);
                GlStateManager._bindTexture(var3);
                if (arg0.fbo == 0) {
                    arg0.fbo = GL30.glGenFramebuffers();
                }
                int var5 = GL11.glGetInteger(36006);
                GL30.glBindFramebuffer(36160, arg0.fbo);
                GL30.glFramebufferTexture2D(36160, 36064, 3553, arg0.tex, 0);
                int var6 = (GL30.glCheckFramebufferStatus(36160) == 36053);
                GL30.glBindFramebuffer(36160, var5);
                if (var6 != 0) {
                } else {
                }
                arg1.w = 0;
                if (var6 != 0) {
                } else {
                }
                arg2.h = 0;
                if (var6 == 0) {
                }
                return var6;
            }
        }
    }

    private static float time() {
        return ((float) ((((double) (System.nanoTime() - START)) / 1.0E9) % 3600.0));
    }

    private static float r(int arg0) {
        return (((float) ((arg0 >> 16) & 255)) / 255.0F);
    }

    private static float g(int arg0) {
        return (((float) ((arg0 >> 8) & 255)) / 255.0F);
    }

    private static float b(int arg0) {
        return (((float) (arg0 & 255)) / 255.0F);
    }

    static {
        wispA = new HandFx.Target();
        wispB = new HandFx.Target();
        initDone = false;
        initFailed = false;
        half1 = new HandFx.Target();
        half2 = new HandFx.Target();
        quarter1 = new HandFx.Target();
        quarter2 = new HandFx.Target();
        samplerLinear = 0;
        samplerNearest = 0;
        bgValid = false;
        bgDiv = 2;
        wispLastNanos = 0L;
        haveLook = false;
        START = System.nanoTime();
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
