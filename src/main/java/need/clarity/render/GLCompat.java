package need.clarity.render;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.textures.GpuTexture;
import need.clarity.api.Hooks;
import org.lwjgl.opengl.GL11;

public final class GLCompat {
    private static boolean forceColorMaskOff;

    private GLCompat() {
    }

    public static boolean isColorMaskForcedOff() {
        return forceColorMaskOff;
    }

    public static void forceColorMaskOff(boolean arg0) {
        forceColorMaskOff = arg0;
        GL11.glColorMask((!arg0), (!arg0), (!arg0), (!arg0));
    }

    public static void enableBlend() {
        GlStateManager._enableBlend();
    }

    public static void disableBlend() {
        GlStateManager._disableBlend();
    }

    public static void defaultBlendFunc() {
        blendFuncSeparate(770, 771, 1, 0);
    }

    public static void blendFuncSeparate(int arg0, int arg1, int arg2, int arg3) {
        GlStateManager._blendFuncSeparate(arg0, arg1, arg2, arg3);
    }

    public static void enableDepthTest() {
        GlStateManager._enableDepthTest();
    }

    public static void disableDepthTest() {
        GlStateManager._disableDepthTest();
    }

    public static void activeTexture(int arg0) {
        GlStateManager._activeTexture(arg0);
    }

    public static void bindTexture(int arg0) {
        GlStateManager._bindTexture(arg0);
    }

    public static void bindTexture(GpuTexture arg0) {
        bindTexture(RawFramebufferUtil.getGlId(arg0));
    }

    public static void clearColor(float arg0, float arg1, float arg2, float arg3) {
        GL11.glClearColor(arg0, arg1, arg2, arg3);
    }

    public static void clear(int arg0) {
        GL11.glClear(arg0);
    }

    static {
        Hooks.act1("GLCompat.forceColorMaskOff", (p0) -> GLCompat.lambda$static$0(p0));
        Hooks.bool0("GLCompat.isColorMaskForcedOff", () -> GLCompat.isColorMaskForcedOff());
        forceColorMaskOff = false;
    }

}
