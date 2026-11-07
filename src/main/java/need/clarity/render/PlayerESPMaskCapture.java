package need.clarity.render;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

public final class PlayerESPMaskCapture {
    private static TextureTarget maskFbo;

    private PlayerESPMaskCapture() {
    }

    public static boolean ensureFramebuffers() {
        Minecraft var0 = Minecraft.getInstance();
        if (var0 != null) {
            if (var0.getWindow() == null) {
            }
        }
        return false;
        int var15 = var0.getWindow().getWidth();
        int var18 = var0.getWindow().getHeight();
        if (var15 > 0) {
            if (var18 <= 0) {
            }
        }
        return false;
        if (maskFbo != null) {
            if (maskFbo.width == var15) {
                if (maskFbo.height != var18) {
                }
            }
            if (maskFbo != null) {
                RawFramebufferUtil.invalidate();
                maskFbo.destroyBuffers();
            }
            TextureTarget var29 = new TextureTarget("clarity_esp_mask", var15, var18, false);
            maskFbo = var29;
            return true;
        }
    }

    public static void clearMask() {
        if (maskFbo == null) {
            return;
        }
        if (!RawFramebufferUtil.bindDirect(maskFbo)) {
            return;
        }
        GLCompat.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
        GLCompat.clear(16384);
    }

    public static void beginMaskOverride(boolean arg0) {
        RenderTarget var20 = null;
        if (maskFbo == null) {
            return;
        }
        Minecraft var4 = Minecraft.getInstance();
        if (var4 != null) {
            if (var4.renderBuffers() != null) {
                var4.renderBuffers().bufferSource().endBatch();
            }
            if (!arg0) {
                if (var4 != null) {
                    var20 = var4.getMainRenderTarget();
                } else {
                    var20 = null;
                }
                RenderTarget var3 = var20;
                if (var3 != null) {
                    boolean var15 = RawFramebufferUtil.bindDirect(var3);
                }
                RenderSystem.outputColorTextureOverride = maskFbo.getColorTextureView();
                RenderSystem.outputDepthTextureOverride = null;
                return;
            }
        }
    }

    public static void endMaskOverride() {
        RenderSystem.outputColorTextureOverride = null;
        RenderSystem.outputDepthTextureOverride = null;
    }

    public static TextureTarget getMaskFbo() {
        return maskFbo;
    }

    public static void dispose() {
        if (maskFbo != null) {
            RawFramebufferUtil.invalidate();
            maskFbo.destroyBuffers();
            maskFbo = null;
        }
    }

}
