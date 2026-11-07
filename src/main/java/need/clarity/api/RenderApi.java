package need.clarity.api;

import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.world.entity.HumanoidArm;

public final class RenderApi {
    private RenderApi() {
    }

    public static boolean kennyActive() {
        return Hooks.bool("Kenny.active", false);
    }

    public static void kennySetActive(boolean arg0) {
        Boolean var3 = arg0;
        Hooks.run1("Kenny.setActive", var3);
    }

    public static void kennySetRight(Object arg0) {
        Hooks.run1("Kenny.setRight", arg0);
    }

    public static void kennySetLeft(Object arg0) {
        Hooks.run1("Kenny.setLeft", arg0);
    }

    public static boolean kennyApply(ModelPart arg0, PoseStack arg1) {
        Object[] var7 = new Object[2];
        var7[0] = arg0;
        var7[1] = arg1;
        return Hooks.bool1("Kenny.apply", var7, false);
    }

    public static boolean animApply(PoseStack arg0, HumanoidArm arg1, float arg2) {
        Object[] var8 = new Object[3];
        var8[0] = arg0;
        var8[1] = arg1;
        Float var14 = arg2;
        var8[2] = var14;
        return Hooks.bool1("Anim.apply", var8, false);
    }

    public static boolean animShouldAnimate(HumanoidArm arg0) {
        return Hooks.bool1("Anim.shouldAnimate", arg0, false);
    }

    public static RenderType throughWallsConvert(RenderType arg0) {
        RenderType var10 = null;
        Object var5 = Hooks.obj1("ThroughWalls.convert", arg0);
        if ((var5 instanceof RenderType)) {
            var10 = ((RenderType) var5);
        } else {
            var10 = arg0;
        }
        return var10;
    }

    public static boolean shaderIsInitialized() {
        return Hooks.bool("Shader.isInitialized", false);
    }

    public static void shaderInitIfNeeded() {
        Hooks.run("Shader.initIfNeeded");
    }

    public static void shaderCheckFramebuffers() {
        Hooks.run("Shader.checkFramebuffers");
    }

    public static void shaderCaptureBackground() {
        Hooks.run("Shader.captureBackground");
    }

    public static void forceColorMaskOff(boolean arg0) {
        Boolean var3 = arg0;
        Hooks.run1("GLCompat.forceColorMaskOff", var3);
    }

    public static boolean isColorMaskForcedOff() {
        return Hooks.bool("GLCompat.isColorMaskForcedOff", false);
    }

    public static RenderType chamsModelRenderType(boolean arg0) {
        RenderType var10 = null;
        Boolean var11 = arg0;
        Object var5 = Hooks.obj1("ChamsRenderer.modelRenderType", var11);
        if ((var5 instanceof RenderType)) {
            var10 = ((RenderType) var5);
        } else {
            var10 = null;
        }
        return var10;
    }

    public static DepthStencilState chamsThroughWallsDepthState() {
        DepthStencilState var8 = null;
        Object var3 = Hooks.obj("ChamsRenderer.throughWallsDepthState");
        if ((var3 instanceof DepthStencilState)) {
            var8 = ((DepthStencilState) var3);
        } else {
            var8 = null;
        }
        return var8;
    }

    public static boolean chamsActive() {
        return Hooks.bool("ChamsCollector.active", false);
    }

    public static void chamsSetActive(boolean arg0) {
        Boolean var3 = arg0;
        Hooks.run1("ChamsCollector.setActive", var3);
    }

    public static boolean chamsHideArmour() {
        return Hooks.bool("ChamsCollector.hideArmour", false);
    }

    public static void chamsSetHideArmour(boolean arg0) {
        Boolean var3 = arg0;
        Hooks.run1("ChamsCollector.setHideArmour", var3);
    }

    public static boolean chamsArmourThroughWalls() {
        return Hooks.bool("ChamsCollector.armourThroughWalls", true);
    }

    public static void chamsSetArmourThroughWalls(boolean arg0) {
        Boolean var3 = arg0;
        Hooks.run1("ChamsCollector.setArmourThroughWalls", var3);
    }

    public static SubmitNodeCollector chamsWrap(SubmitNodeCollector arg0) {
        SubmitNodeCollector var10 = null;
        Object var5 = Hooks.obj1("ChamsCollector.wrap", arg0);
        if ((var5 instanceof SubmitNodeCollector)) {
            var10 = ((SubmitNodeCollector) var5);
        } else {
            var10 = arg0;
        }
        return var10;
    }

    public static void arrayListRender(GuiGraphicsExtractor arg0, float arg1, float arg2, float arg3, float arg4) {
        Object[] var9 = new Object[5];
        var9[0] = arg0;
        Float var15 = arg1;
        var9[1] = var15;
        Float var17 = arg2;
        var9[2] = var17;
        Float var19 = arg3;
        var9[3] = var19;
        Float var21 = arg4;
        var9[4] = var21;
        Hooks.run1("ArrayListRenderer.render", var9);
    }

    public static void shaderEspTagState(Object arg0, Object arg1) {
        Object[] var6 = new Object[2];
        var6[0] = arg0;
        var6[1] = arg1;
        Hooks.run1("ShaderEsp.tagState", var6);
    }

    public static void shaderEspAfterExtract(Object arg0) {
        Hooks.run1("ShaderEsp.afterExtract", arg0);
    }

    public static void shaderEspCaptureMask() {
        Hooks.run("ShaderEsp.captureMask");
    }

    public static boolean shaderEspOnDoEntityOutline() {
        return Hooks.bool("ShaderEsp.onDoEntityOutline", false);
    }

    public static void shaderEspRegisterOutlinePasses(Object arg0) {
        Hooks.run1("ShaderEsp.registerOutlinePasses", arg0);
    }

    public static boolean shaderEspOnOutlinePass(Object arg0) {
        return Hooks.bool1("ShaderEsp.onOutlinePass", arg0, false);
    }

    public static boolean shaderEspIsHidden(int arg0) {
        Integer var5 = arg0;
        return Hooks.bool1("ShaderEsp.isHidden", var5, false);
    }

    public static void motionBlurApply() {
        Hooks.run("MotionBlur.apply");
    }

    public static boolean crosshairRender(Object arg0) {
        return Hooks.bool1("Crosshair.render", arg0, false);
    }

    public static boolean spinBotClientMode() {
        return Hooks.bool("SpinBot.clientMode", false);
    }

    public static float spinBotYaw(float arg0) {
        Float var8 = arg0;
        return ((float) Hooks.dbl1("SpinBot.yaw", var8, 0.0));
    }

    public static boolean modelHandsActive() {
        return Hooks.bool("ModelHands.active", false);
    }

    public static void modelHandsSetActive(boolean arg0) {
        Boolean var3 = arg0;
        Hooks.run1("ModelHands.setActive", var3);
    }

    public static void modelHandsSetRight(Object arg0) {
        Hooks.run1("ModelHands.setRight", arg0);
    }

    public static void modelHandsSetLeft(Object arg0) {
        Hooks.run1("ModelHands.setLeft", arg0);
    }

    public static boolean modelHandsApply(Object arg0, Object arg1) {
        Object[] var7 = new Object[2];
        var7[0] = arg0;
        var7[1] = arg1;
        return Hooks.bool1("ModelHands.apply", var7, false);
    }

}
