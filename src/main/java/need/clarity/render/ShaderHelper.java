package need.clarity.render;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.awt.Color;
import java.util.function.Consumer;
import need.clarity.api.Hooks;
import need.clarity.api.Modules;
import need.clarity.modules.visual.SkyShaders;
import need.clarity.render.handshader.HandFx;
import net.minecraft.client.Minecraft;
import org.joml.Vector3f;

public final class ShaderHelper {
    private static Shader chromaShader;
    private static Shader prismShader;
    private static Shader pixelShader;
    private static Shader galaxyShader;
    private static Shader neonShader;
    private static Shader invertShader;
    private static Shader edgeGlowShader;
    private static Shader outlineShader;
    private static Shader jfaInitShader;
    private static Shader jfaStepShader;
    private static Shader jfaCompositeShader;
    private static TextureTarget copyFbo;
    private static TextureTarget jfaFboA;
    private static TextureTarget jfaFboB;
    private static boolean initialized;

    private ShaderHelper() {
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void initShadersIfNeeded() {
        if (initialized) {
            return;
        }
        try {
            chromaShader = new Shader("hand", "chroma");
            prismShader = new Shader("hand", "prism");
            pixelShader = new Shader("hand", "pixelate");
            galaxyShader = new Shader("hand", "galaxy");
            neonShader = new Shader("hand", "neon");
            invertShader = new Shader("hand", "invert");
            outlineShader = new Shader("hand", "outline");
            jfaInitShader = new Shader("hand", "jfa_init");
            jfaStepShader = new Shader("hand", "jfa_step");
            jfaCompositeShader = new Shader("hand", "jfa_composite");
            initialized = true;
        } catch (Exception ignored) {
        }
    }

    public static void checkFramebuffers() {
        Minecraft var0 = Minecraft.getInstance();
        if (var0 != null) {
            if (var0.getWindow() == null) {
            }
        }
        return;
        int var1 = var0.getWindow().getWidth();
        int var2 = var0.getWindow().getHeight();
        if (copyFbo != null) {
            if (copyFbo.width == var1) {
                if (copyFbo.height == var2) {
                }
            }
        }
        if (jfaFboA != null) {
            if (jfaFboA.width == var1) {
                if (jfaFboA.height == var2) {
                }
            }
        }
        if (jfaFboB != null) {
            if (jfaFboB.width == var1) {
                if (jfaFboB.height != var2) {
                }
            }
        } else {
        }
        if (0 != 0) {
            RawFramebufferUtil.invalidate();
        }
        if (copyFbo != null) {
            if (copyFbo.width == var1) {
                if (copyFbo.height != var2) {
                }
            }
            if (copyFbo != null) {
                copyFbo.destroyBuffers();
            }
            copyFbo = new TextureTarget("clarity_hand_copy", var1, var2, true);
            if (jfaFboA != null) {
                if (jfaFboA.width == var1) {
                    if (jfaFboA.height != var2) {
                    }
                }
                if (jfaFboA != null) {
                    jfaFboA.destroyBuffers();
                }
                jfaFboA = new TextureTarget("clarity_jfa_a", var1, var2, false);
                if (jfaFboB != null) {
                    if (jfaFboB.width == var1) {
                        if (jfaFboB.height != var2) {
                        }
                    }
                    if (jfaFboB != null) {
                        jfaFboB.destroyBuffers();
                    }
                    jfaFboB = new TextureTarget("clarity_jfa_b", var1, var2, false);
                    return;
                }
            }
        }
    }

    public static void render() {
        RawGlStateCapture var0 = null;
        String var1 = null;
        String var2 = null;
        int var3 = 0;
        if (!initialized) {
            return;
        }
        try {
            var0 = RawGlStateCapture.capture();
            try {
                var1 = Modules.handShadersShader();
                var2 = var1;
                var3 = -1;
                switch (var2.hashCode()) {
                    case -2099835914 -> {}
                    case -440373695 -> {}
                    case -78399892 -> {}
                    case 2073735 -> {}
                    case 2222509 -> {}
                    case 2424310 -> {}
                    case 2696399 -> {}
                    case 68884316 -> {}
                    case 77382433 -> {}
                    case 558407714 -> {}
                    case 2017705622 -> {}
                    case 2125565744 -> {}
                    default -> {}
                }
                if (var2.equals("Chroma")) {
                    var3 = 0;
                    if (var2.equals("Prism")) {
                        var3 = 1;
                        if (var2.equals("Pixelate")) {
                            var3 = 2;
                            if (var2.equals("Galaxy")) {
                                var3 = 3;
                                if (var2.equals("Neon")) {
                                    var3 = 4;
                                    if (var2.equals("Invert")) {
                                        var3 = 5;
                                        if (var2.equals("Outline")) {
                                            var3 = 6;
                                            if (var2.equals("Glass")) {
                                                var3 = 7;
                                                if (var2.equals("Glow")) {
                                                    var3 = 8;
                                                    if (var2.equals("Hologram")) {
                                                        var3 = 9;
                                                        if (var2.equals("Blur")) {
                                                            var3 = 10;
                                                            if (var2.equals("Wisp")) {
                                                                var3 = 11;
                                                            }
                                                            switch (var3) {
                                                                case 0 -> {}
                                                                case 1 -> {}
                                                                case 2 -> {}
                                                                case 3 -> {}
                                                                case 4 -> {}
                                                                case 5 -> {}
                                                                case 6 -> {}
                                                                case 7 -> {}
                                                                case 8 -> {}
                                                                case 9 -> {}
                                                                case 10 -> {}
                                                                case 11 -> {}
                                                                default -> {}
                                                            }
                                                            renderChroma();
                                                            renderPrism();
                                                            renderSimple(pixelShader, (p0) -> ShaderHelper.lambda$render$0(p0));
                                                            renderSimple(galaxyShader, (p0) -> ShaderHelper.themeUniforms(p0));
                                                            renderSimple(neonShader, (p0) -> ShaderHelper.themeUniforms(p0));
                                                            renderInvert();
                                                            renderOutline();
                                                            renderFx(var1);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Throwable ignored) {
            }
            if (var0 != null) {
                var0.close();
                if (var0 != null) {
                    try {
                        var0.close();
                    } catch (Throwable ignored) {
                    }
                    var1.addSuppressed(var2);
                }
                throw var1;
            }
            return;
        } catch (Exception ignored) {
        }
    }

    public static void captureBackground() {
        HandFx.captureBackground();
    }

    private static void renderFx(String arg0) {
        RenderTarget var2 = Minecraft.getInstance().getMainRenderTarget();
        if (var2 != null) {
            if (copyFbo == null) {
            }
        }
        return;
        if (!(prepareShaderRender(var2, copyFbo))) {
            return;
        }
        HandFx.render(arg0, var2, copyFbo);
        cleanupShaderRender();
    }

    private static boolean prepareShaderRender(RenderTarget arg0, TextureTarget arg1) {
        RawFramebufferUtil.clear(arg1);
        if (!RawFramebufferUtil.bindDirect(arg1)) {
            return false;
        }
        GLCompat.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GLCompat.clear(16640);
        RawFramebufferUtil.copy(arg0, arg1, true);
        if (!RawFramebufferUtil.bindDirect(arg0)) {
            return false;
        }
        GLCompat.enableBlend();
        GLCompat.defaultBlendFunc();
        GLCompat.disableDepthTest();
        return true;
    }

    private static void bindShaderTextures(TextureTarget arg0) {
        GLCompat.activeTexture(33984);
        GLCompat.bindTexture(arg0.getColorTexture());
        GLCompat.activeTexture(33985);
        GLCompat.bindTexture(arg0.getDepthTexture());
    }

    private static void cleanupShaderRender() {
        GLCompat.activeTexture(33984);
        GLCompat.disableBlend();
        GLCompat.enableDepthTest();
        Minecraft var0 = Minecraft.getInstance();
        if (var0.getMainRenderTarget() != null) {
            RawFramebufferUtil.bindDirect(var0.getMainRenderTarget());
        }
    }

    private static Vector3f colorToVec(int arg0) {
        Color var1 = new Color(arg0, true);
        return new Vector3f((((float) var1.getRed()) / 255.0F), (((float) var1.getGreen()) / 255.0F), (((float) var1.getBlue()) / 255.0F));
    }

    private static void renderChroma() {
        RenderTarget var1 = Minecraft.getInstance().getMainRenderTarget();
        if (var1 != null) {
            if (chromaShader != null) {
                if (copyFbo == null) {
                }
            }
            return;
            if (!(prepareShaderRender(var1, copyFbo))) {
                return;
            }
            bindShaderTextures(copyFbo);
            chromaShader.bind();
            chromaShader.setUniform1i("ColorTexture", 0);
            chromaShader.setUniform1i("DepthTexture", 1);
            chromaShader.setUniform1f("time", (((float) System.nanoTime()) / 1.0E9F));
            chromaShader.setUniform1f("chromaSpeed", Modules.handShadersChromaSpeed());
            chromaShader.setUniform1f("chromaSaturation", Modules.handShadersChromaSaturation());
            chromaShader.setUniform1f("chromaBrightness", Modules.handShadersChromaBrightness());
            drawFullScreenQuad();
            chromaShader.unbind();
            cleanupShaderRender();
            return;
        }
    }

    private static void renderPrism() {
        RenderTarget var1 = Minecraft.getInstance().getMainRenderTarget();
        if (var1 != null) {
            if (prismShader != null) {
                if (copyFbo == null) {
                }
            }
            return;
            if (!(prepareShaderRender(var1, copyFbo))) {
                return;
            }
            bindShaderTextures(copyFbo);
            float[][] var2 = SkyShaders.evaluate();
            prismShader.bind();
            prismShader.setUniform1i("ColorTexture", 0);
            prismShader.setUniform1i("DepthTexture", 1);
            prismShader.setUniform1f("time", ((float) ((((double) System.nanoTime()) / 1.0E9) % 3600.0)));
            prismShader.setUniform1f("aspect", (((float) var1.width) / ((float) Math.max(1, var1.height))));
            prismShader.setUniform3f("uAccent", var2[0][0], var2[0][1], var2[0][2]);
            prismShader.setUniform3f("uAccent2", var2[1][0], var2[1][1], var2[1][2]);
            drawFullScreenQuad();
            prismShader.unbind();
            cleanupShaderRender();
            return;
        }
    }

    private static void themeUniforms(Shader arg0) {
        float[][] var1 = SkyShaders.evaluate();
        arg0.setUniform3f("uAccent", var1[0][0], var1[0][1], var1[0][2]);
        arg0.setUniform3f("uAccent2", var1[1][0], var1[1][1], var1[1][2]);
    }

    private static void renderSimple(Shader arg0, Consumer arg1) {
        RenderTarget var3 = Minecraft.getInstance().getMainRenderTarget();
        if (var3 != null) {
            if (arg0 != null) {
                if (copyFbo == null) {
                }
            }
            return;
            if (!(prepareShaderRender(var3, copyFbo))) {
                return;
            }
            bindShaderTextures(copyFbo);
            arg0.bind();
            arg0.setUniform1i("ColorTexture", 0);
            arg0.setUniform1i("DepthTexture", 1);
            arg0.setUniform1f("time", ((float) ((((double) System.nanoTime()) / 1.0E9) % 3600.0)));
            arg0.setUniform1f("aspect", (((float) var3.width) / ((float) Math.max(1, var3.height))));
            arg0.setUniform2f("texel", (1.0F / ((float) Math.max(1, var3.width))), (1.0F / ((float) Math.max(1, var3.height))));
            arg0.setUniform1f("pxScale", (((float) var3.height) / 1080.0F));
            arg1.accept(arg0);
            drawFullScreenQuad();
            arg0.unbind();
            cleanupShaderRender();
            return;
        }
    }

    private static void renderInvert() {
        RenderTarget var1 = Minecraft.getInstance().getMainRenderTarget();
        if (var1 != null) {
            if (invertShader != null) {
                if (copyFbo == null) {
                }
            }
            return;
            if (!(prepareShaderRender(var1, copyFbo))) {
                return;
            }
            bindShaderTextures(copyFbo);
            invertShader.bind();
            invertShader.setUniform1i("ColorTexture", 0);
            invertShader.setUniform1i("DepthTexture", 1);
            invertShader.setUniform1f("time", (((float) System.nanoTime()) / 1.0E9F));
            invertShader.setUniformBool("hideHand", Modules.handShadersInvertHideHand());
            invertShader.setUniform1f("invertIntensity", Modules.handShadersInvertIntensity());
            invertShader.setUniformBool("invertHue", Modules.handShadersInvertHue());
            invertShader.setUniformBool("invertSaturation", Modules.handShadersInvertSaturation());
            invertShader.setUniformBool("invertBrightness", Modules.handShadersInvertBrightness());
            invertShader.setUniform1f("edgeGlow", Modules.handShadersInvertEdgeGlow());
            invertShader.setUniform3f("glowColor", colorToVec(Modules.handShadersInvertGlowColor()));
            drawFullScreenQuad();
            invertShader.unbind();
            cleanupShaderRender();
            return;
        }
    }

    private static void renderEdgeGlow() {
    }

    private static void renderOutline() {
        RenderTarget var1 = Minecraft.getInstance().getMainRenderTarget();
        if (var1 != null) {
            if (outlineShader != null) {
                if (copyFbo == null) {
                }
            }
            return;
            if (!(prepareShaderRender(var1, copyFbo))) {
                return;
            }
            bindShaderTextures(copyFbo);
            outlineShader.bind();
            outlineShader.setUniform1i("ColorTexture", 0);
            outlineShader.setUniform1i("DepthTexture", 1);
            outlineShader.setUniform3f("outlineColor", colorToVec(Modules.handShadersOutlineColor()));
            outlineShader.setUniform1f("outlineThickness", Modules.handShadersOutlineThickness());
            outlineShader.setUniformBool("tintEnabled", Modules.handShadersTintEnabled());
            outlineShader.setUniform3f("tintColor", colorToVec(Modules.handShadersTintColor()));
            outlineShader.setUniform1f("tintAmount", Modules.handShadersTintAmount());
            drawFullScreenQuad();
            outlineShader.unbind();
            cleanupShaderRender();
            return;
        }
    }

    public static void drawFullScreenQuad() {
        RenderSystem.assertOnRenderThread();
        BufferBuilder var0 = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        var0.addVertex(-1.0F, -1.0F, 0.0F);
        var0.addVertex(1.0F, -1.0F, 0.0F);
        var0.addVertex(1.0F, 1.0F, 0.0F);
        var0.addVertex(-1.0F, 1.0F, 0.0F);
        RawShaderDrawUtil.draw(var0.buildOrThrow());
    }

    static {
        Hooks.bool0("Shader.isInitialized", () -> ShaderHelper.isInitialized());
        Hooks.act0("Shader.initIfNeeded", () -> ShaderHelper.initShadersIfNeeded());
        Hooks.act0("Shader.checkFramebuffers", () -> ShaderHelper.checkFramebuffers());
        Hooks.act0("Shader.captureBackground", () -> ShaderHelper.captureBackground());
        initialized = false;
    }

}
