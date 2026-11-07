package need.clarity.render;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.List;
import need.clarity.modules.visual.PlayerESP;
import need.clarity.util.ClarityRenderUtils;
import net.minecraft.client.Minecraft;

public final class PlayerESPRenderer {
    private static Shader espOutlineShader;
    private static boolean initialized;
    private static boolean initFailed;

    private PlayerESPRenderer() {
    }

    public static boolean isInitialized() {
        return initialized;
    }

    public static void initIfNeeded() {
        if (!initialized) {
            if (initFailed) {
            }
        }
        return;
        try {
            Shader var5 = new Shader("hand", "esp_outline");
            espOutlineShader = var5;
            initialized = true;
        } catch (Exception ignored) {
        }
        initFailed = true;
    }

    public static void render(PoseStack arg0, List arg1, float arg2) {
        int var35 = 0;
        if (arg1.isEmpty()) {
            return;
        }
        boolean var19 = PlayerESP.fillOpacity();
        String var4 = PlayerESP.mode();
        boolean var21 = "Corners".equals(var4);
        String var36 = PlayerESP.getMode();
        if (!"3D".equals(var36)) {
            if (PlayerESP.outlineThickness()) {
            }
        }
        var35 = 1;
        var35 = 0;
        int var13 = var35;
        boolean var25 = PlayerESP.boxStyle();
        int var26 = PlayerESP.getFillColor();
        int var32 = (Math.round((clamp01(PlayerESP.fillColor()) * 255.0F)) << 24);
        int var40 = (var26 & 16777215);
        int var33 = (var32 | var40);
        float var10 = PlayerESP.outlineEnabled();
        ClarityRenderUtils.drawEspBoxes(arg0, arg1, var13, var33, var25, var19, var21, var10);
    }

    private static void renderScreenOutline(PoseStack arg0, List arg1, boolean arg2) {
        boolean var3 = PlayerESP.boxStyle();
        boolean var11 = PlayerESP.outlineThickness();
        if (!var3) {
            if (!var11) {
                return;
            }
        }
        if (!initialized) {
            if (initFailed) {
                return;
            }
            initIfNeeded();
            if (!initialized) {
                return;
            }
        }
        if (!PlayerESPMaskCapture.ensureFramebuffers()) {
            return;
        }
        try {
            RawGlStateCapture var18 = RawGlStateCapture.capture();
            try {
                PlayerESPMaskCapture.clearMask();
                PlayerESPMaskCapture.beginMaskOverride(arg2);
                try {
                    ClarityRenderUtils.drawEspMask(arg0, arg1, arg2);
                } catch (Throwable ignored) {
                }
                PlayerESPMaskCapture.endMaskOverride();
                PlayerESPMaskCapture.endMaskOverride();
                throw var30;
            } catch (Throwable ignored) {
            }
            try {
                try {
                    renderOutline();
                } catch (Exception ignored) {
                }
            } catch (Throwable ignored) {
            }
            if (var18 != null) {
                var18.close();
                if (var18 != null) {
                    try {
                        var18.close();
                    } catch (Throwable ignored) {
                    }
                    var24.addSuppressed(var28);
                }
                throw var24;
            }
            return;
        } catch (Exception ignored) {
        }
    }

    private static void renderOutline() {
        RenderTarget var10 = Minecraft.getInstance().getMainRenderTarget();
        TextureTarget var11 = PlayerESPMaskCapture.getMaskFbo();
        if (var10 != null) {
            if (var11 != null) {
                if (espOutlineShader == null) {
                }
            }
            return;
            if (!RawFramebufferUtil.bindDirect(var10)) {
                return;
            }
            GLCompat.enableBlend();
            GLCompat.defaultBlendFunc();
            GLCompat.disableDepthTest();
            GLCompat.activeTexture(33984);
            GLCompat.bindTexture(var11.getColorTexture());
            int var20 = PlayerESP.getFillColor();
            espOutlineShader.bind();
            espOutlineShader.setUniform1i("PlayerMask", 0);
            Shader var23 = espOutlineShader;
            float var34 = PlayerESP.outlineEnabled();
            var23.setUniform1f("outlineThickness", var34);
            Shader var24 = espOutlineShader;
            boolean var35 = PlayerESP.boxStyle();
            var24.setUniformBool("outlineEnabled", var35);
            Shader var25 = espOutlineShader;
            boolean var36 = PlayerESP.outlineThickness();
            var25.setUniformBool("fillEnabled", var36);
            Shader var26 = espOutlineShader;
            float var38 = red(var20);
            float var40 = green(var20);
            float var41 = blue(var20);
            var26.setUniform3f("fillColor", var38, var40, var41);
            Shader var27 = espOutlineShader;
            float var39 = PlayerESP.fillColor();
            var27.setUniform1f("fillOpacity", var39);
            drawFullScreenQuad();
            espOutlineShader.unbind();
            return;
        }
    }

    private static float clamp01(float arg0) {
        float var6 = 0.0F;
        if (Float.compare(arg0, 0.0F) < 0) {
            var6 = 0.0F;
        } else {
            if (Float.compare(arg0, 1.0F) > 0) {
                var6 = 1.0F;
            } else {
                var6 = arg0;
            }
            return var6;
        }
    }

    private static float red(int arg0) {
        return (((float) ((arg0 >> 16) & 255)) / 255.0F);
    }

    private static float green(int arg0) {
        return (((float) ((arg0 >> 8) & 255)) / 255.0F);
    }

    private static float blue(int arg0) {
        return (((float) (arg0 & 255)) / 255.0F);
    }

    private static void drawFullScreenQuad() {
        RenderSystem.assertOnRenderThread();
        Tesselator var0 = Tesselator.getInstance();
        VertexFormat.Mode var1 = VertexFormat.Mode.QUADS;
        VertexFormat var2 = DefaultVertexFormat.POSITION;
        BufferBuilder var5 = var0.begin(var1, var2);
        VertexConsumer var7 = var5.addVertex(-1.0F, -1.0F, 0.0F);
        VertexConsumer var9 = var5.addVertex(1.0F, -1.0F, 0.0F);
        VertexConsumer var11 = var5.addVertex(1.0F, 1.0F, 0.0F);
        VertexConsumer var13 = var5.addVertex(-1.0F, 1.0F, 0.0F);
        RawShaderDrawUtil.draw(var5.buildOrThrow());
    }

    static {
        initialized = false;
        initFailed = false;
    }

}
