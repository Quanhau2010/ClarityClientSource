package need.clarity.api;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.PlayerSkin;
import net.minecraft.world.phys.Vec3;

public final class Modules {
    private Modules() {
    }

    public static boolean noSwingActive() {
        return Hooks.bool("NoSwing.isActive", false);
    }

    public static boolean noHurtCamActive() {
        return Hooks.bool("NoHurtCam.isActive", false);
    }

    public static boolean antiPushShouldCancel() {
        return Hooks.bool("AntiPush.shouldCancel", false);
    }

    public static boolean crystalOptimizerActive() {
        return Hooks.bool("CrystalOptimizer.isActive", false);
    }

    public static boolean aspectActive() {
        return Hooks.bool("Aspect.isActive", false);
    }

    public static float aspectFactor() {
        return ((float) Hooks.dbl("Aspect.factor", 1.0));
    }

    public static float reachBlock() {
        return ((float) Hooks.dbl("Reach.block", 0.0));
    }

    public static float reachEntity() {
        return ((float) Hooks.dbl("Reach.entity", 0.0));
    }

    public static boolean antiHungerSpoof() {
        return Hooks.bool("AntiHunger.spoof", false);
    }

    public static boolean antiPowderEnabled() {
        return Hooks.bool("AntiPowderSnow.enabled", false);
    }

    public static boolean zoomActive() {
        return Hooks.bool("Zoom.isActive", false);
    }

    public static void zoomScroll(double arg0) {
        Double var5 = arg0;
        Hooks.run1("Zoom.scroll", var5);
    }

    public static boolean flyActive() {
        return Hooks.bool("Fly.isActive", false);
    }

    public static boolean flyJumpDown(Minecraft arg0) {
        return Hooks.bool1("Fly.isJumpDown", arg0, false);
    }

    public static boolean flyDescendDown(Minecraft arg0) {
        return Hooks.bool1("Fly.isDescendDown", arg0, false);
    }

    public static boolean flyHasPending() {
        return Hooks.bool("Fly.hasPending", false);
    }

    public static String flyMode() {
        String var5 = null;
        Object var2 = Hooks.obj("Fly.mode");
        if (var2 == null) {
            var5 = "";
        } else {
            var5 = var2.toString();
        }
        return var5;
    }

    public static double flyPendingX() {
        return Hooks.dbl("Fly.pendingVelX", 0.0);
    }

    public static double flyPendingY() {
        return Hooks.dbl("Fly.pendingVelY", 0.0);
    }

    public static double flyPendingZ() {
        return Hooks.dbl("Fly.pendingVelZ", 0.0);
    }

    public static void flyClearPending() {
        Hooks.run("Fly.clearPending");
    }

    public static boolean piercingEnabled() {
        return Hooks.bool("Piercing.enabled", false);
    }

    public static void piercingHandleInput(Minecraft arg0) {
        Hooks.run1("Piercing.handleInput", arg0);
    }

    public static boolean viewModelActive() {
        return Hooks.bool("ViewModel.isActive", false);
    }

    public static float viewModelPosX(InteractionHand arg0) {
        return ((float) Hooks.dbl1("ViewModel.posX", arg0, 0.0));
    }

    public static float viewModelPosY(InteractionHand arg0) {
        return ((float) Hooks.dbl1("ViewModel.posY", arg0, 0.0));
    }

    public static float viewModelPosZ(InteractionHand arg0) {
        return ((float) Hooks.dbl1("ViewModel.posZ", arg0, 0.0));
    }

    public static float viewModelRotX(InteractionHand arg0) {
        return ((float) Hooks.dbl1("ViewModel.rotX", arg0, 0.0));
    }

    public static float viewModelRotY(InteractionHand arg0) {
        return ((float) Hooks.dbl1("ViewModel.rotY", arg0, 0.0));
    }

    public static float viewModelRotZ(InteractionHand arg0) {
        return ((float) Hooks.dbl1("ViewModel.rotZ", arg0, 0.0));
    }

    public static float viewModelScale(InteractionHand arg0) {
        return ((float) Hooks.dbl1("ViewModel.scale", arg0, 1.0));
    }

    public static boolean animationsOverrideSwing() {
        return Hooks.bool("Animations.overrideSwing", false);
    }

    public static int animationsSwingSpeed() {
        return ((int) Hooks.dbl("Animations.swingSpeed", 6.0));
    }

    public static boolean nametagsActive() {
        return Hooks.bool("Nametags.active", false);
    }

    public static boolean nametagsHideVanilla() {
        return Hooks.bool("Nametags.hideVanilla", false);
    }

    public static boolean healthTagsEnabled() {
        return Hooks.bool("HealthTags.enabled", false);
    }

    public static int healthTagsDisplayHp(Object arg0) {
        return ((int) Hooks.dbl1("HealthTags.displayHp", arg0, 0.0));
    }

    public static int healthTagsColor(float arg0, float arg1) {
        int var13 = 0;
        Float var14 = arg0;
        Float var15 = arg1;
        Object var7 = Hooks.obj2("HealthTags.color", var14, var15);
        if ((var7 instanceof Number)) {
            var13 = ((Number) var7);
        } else {
            var13 = 16777215;
        }
        return var13;
    }

    public static boolean skinChangerEnabled() {
        return Hooks.bool("SkinChanger.enabled", false);
    }

    public static PlayerSkin skinChangerOverride(PlayerSkin arg0) {
        PlayerSkin var10 = null;
        Object var5 = Hooks.obj1("SkinChanger.override", arg0);
        if ((var5 instanceof PlayerSkin)) {
            var10 = ((PlayerSkin) var5);
        } else {
            var10 = null;
        }
        return var10;
    }

    public static String skinChangerCopyName() {
        String var5 = null;
        Object var2 = Hooks.obj("SkinChanger.copyName");
        if (var2 == null) {
            var5 = "";
        } else {
            var5 = var2.toString();
        }
        return var5;
    }

    public static String skinChangerSkinId() {
        String var5 = null;
        Object var2 = Hooks.obj("SkinChanger.skinId");
        if (var2 == null) {
            var5 = "default";
        } else {
            var5 = var2.toString();
        }
        return var5;
    }

    public static Object skinChangerTexture() {
        return Hooks.obj("SkinChanger.texture");
    }

    public static boolean capesEnabled() {
        return Hooks.bool("Capes.enabled", false);
    }

    public static Object capesTexture() {
        return Hooks.obj("Capes.texture");
    }

    public static boolean noRenderHideExplosionAt(double arg0, double arg1, double arg2) {
        double[] var12 = new double[3];
        var12[0] = arg0;
        var12[1] = arg1;
        var12[2] = arg2;
        return Hooks.bool1("NoRender.hideExplosionAt", var12, false);
    }

    public static boolean crystalViewActive() {
        return Hooks.bool("CrystalView.active", false);
    }

    public static boolean crystalViewShouldMute(Object arg0) {
        return Hooks.bool1("CrystalView.shouldMute", arg0, false);
    }

    public static boolean chestStealerTryCapture() {
        return Hooks.bool("ChestStealer.tryCapture", false);
    }

    public static boolean clientSpooferEnabled() {
        return Hooks.bool("ClientSpoofer.enabled", false);
    }

    public static String clientSpooferBrand() {
        String var5 = null;
        Object var2 = Hooks.obj("ClientSpoofer.brand");
        if (var2 == null) {
            var5 = "";
        } else {
            var5 = var2.toString();
        }
        return var5;
    }

    public static boolean ghostHandEnabled() {
        return Hooks.bool("GhostHand.enabled", false);
    }

    public static void ghostHandPick(Minecraft arg0) {
        Hooks.run1("GhostHand.pick", arg0);
    }

    public static boolean noRenderNausea() {
        return Hooks.bool("NoRender.nausea", false);
    }

    public static boolean noRenderPortal() {
        return Hooks.bool("NoRender.portal", false);
    }

    public static boolean noRenderSpyglass() {
        return Hooks.bool("NoRender.spyglass", false);
    }

    public static boolean noRenderScoreboard() {
        return Hooks.bool("NoRender.scoreboard", false);
    }

    public static boolean noRenderVignette() {
        return Hooks.bool("NoRender.vignette", false);
    }

    public static boolean noRenderFire() {
        return Hooks.bool("NoRender.fire", false);
    }

    public static boolean noRenderTotem() {
        return Hooks.bool("NoRender.totem", false);
    }

    public static boolean noRenderExplosion() {
        return Hooks.bool("NoRender.explosion", false);
    }

    public static boolean serverRotationsApply() {
        return Hooks.bool("ServerRotations.shouldApply", false);
    }

    public static float serverRotationsPitchLerp(float arg0) {
        Float var8 = arg0;
        return ((float) Hooks.dbl1("ServerRotations.pitchLerp", var8, 0.0));
    }

    public static void serverRotationsCaptureLast(float arg0) {
        Float var3 = arg0;
        Hooks.run1("ServerRotations.captureLast", var3);
    }

    public static void serverRotationsCaptureCurrent(float arg0) {
        Float var3 = arg0;
        Hooks.run1("ServerRotations.captureCurrent", var3);
    }

    public static boolean rotationGcdEnabled() {
        return Hooks.bool("RotationGcd.enabled", false);
    }

    public static float rotationGcdSnapYaw(float arg0, float arg1) {
        Float var9 = arg0;
        Float var10 = arg1;
        double var11 = ((double) arg1);
        return ((float) Hooks.dbl2("RotationGcd.snapYaw", var9, var10, var11));
    }

    public static float rotationGcdSnapPitch(float arg0, float arg1) {
        Float var9 = arg0;
        Float var10 = arg1;
        double var11 = ((double) arg1);
        return ((float) Hooks.dbl2("RotationGcd.snapPitch", var9, var10, var11));
    }

    public static boolean themeTexturesShouldRecolor(String arg0) {
        return Hooks.bool1("ThemeTextures.shouldRecolor", arg0, false);
    }

    public static void themeTexturesRecolor(Object arg0) {
        Hooks.run1("ThemeTextures.recolor", arg0);
    }

    public static boolean cameraTweaksActive() {
        return Hooks.bool("CameraTweaks.isActive", false);
    }

    public static boolean cameraTweaksCustomEnabled() {
        return Hooks.bool("CameraTweaks.customDistanceEnabled", false);
    }

    public static float cameraTweaksCustomDistance() {
        return ((float) Hooks.dbl("CameraTweaks.customDistance", 4.0));
    }

    public static boolean cameraTweaksNoClip() {
        return Hooks.bool("CameraTweaks.noCameraClip", false);
    }

    public static boolean ambienceOverrideTime() {
        return Hooks.bool("Ambience.overrideTime", false);
    }

    public static long ambienceDayTime() {
        return ((long) Hooks.dbl("Ambience.dayTime", 0.0));
    }

    public static boolean ambienceOverrideWeather() {
        return Hooks.bool("Ambience.overrideWeather", false);
    }

    public static String ambienceWeatherMode() {
        String var4 = null;
        Object var2 = Hooks.obj("Ambience.weatherMode");
        if (var2 == null) {
            var4 = "";
        } else {
            var4 = var2.toString();
        }
        return var4;
    }

    public static boolean ambienceOverrideFogColour() {
        return Hooks.bool("Ambience.overrideFogColour", false);
    }

    public static float[] ambienceFogColour() {
        float[] var11 = null;
        Object var5 = Hooks.obj("Ambience.fogColour");
        if ((var5 instanceof float[])) {
            var11 = ((float[]) var5);
        } else {
            var11 = new float[3];
            var11[0] = 0.0F;
            var11[1] = 0.0F;
            var11[2] = 0.0F;
        }
        return var11;
    }

    public static boolean ambienceOverrideFogDistance() {
        return Hooks.bool("Ambience.overrideFogDistance", false);
    }

    public static float ambienceFogDistanceChunks() {
        return ((float) Hooks.dbl("Ambience.fogDistanceChunks", 0.0));
    }

    public static boolean ambienceOverrideLightmap() {
        return Hooks.bool("Ambience.overrideLightmap", false);
    }

    public static float[] ambienceLightmapColour() {
        float[] var11 = null;
        Object var5 = Hooks.obj("Ambience.lightmapColour");
        if ((var5 instanceof float[])) {
            var11 = ((float[]) var5);
        } else {
            var11 = new float[3];
            var11[0] = 1.0F;
            var11[1] = 1.0F;
            var11[2] = 1.0F;
        }
        return var11;
    }

    public static boolean ambienceOverrideSkyColour() {
        return Hooks.bool("Ambience.overrideSkyColour", false);
    }

    public static float[] ambienceSkyColour() {
        float[] var11 = null;
        Object var5 = Hooks.obj("Ambience.skyColour");
        if ((var5 instanceof float[])) {
            var11 = ((float[]) var5);
        } else {
            var11 = new float[3];
            var11[0] = 0.0F;
            var11[1] = 0.0F;
            var11[2] = 0.0F;
        }
        return var11;
    }

    public static boolean ambienceSkyGradient() {
        return Hooks.bool("Ambience.skyGradient", false);
    }

    public static float[] ambienceSkyGradientColour2() {
        float[] var11 = null;
        Object var5 = Hooks.obj("Ambience.skyGradientColour2");
        if ((var5 instanceof float[])) {
            var11 = ((float[]) var5);
        } else {
            var11 = new float[3];
            var11[0] = 0.0F;
            var11[1] = 0.0F;
            var11[2] = 0.0F;
        }
        return var11;
    }

    public static boolean handShadersActive() {
        return Hooks.bool("HandShaders.enabled", false);
    }

    public static String handShadersShader() {
        String var5 = null;
        Object var2 = Hooks.obj("HandShaders.shader");
        if (var2 == null) {
            var5 = "";
        } else {
            var5 = var2.toString();
        }
        return var5;
    }

    public static boolean handShadersGlowApplyToArm() {
        return Hooks.bool("HandShaders.glowArm", false);
    }

    public static boolean handShadersGlowApplyToItems() {
        return Hooks.bool("HandShaders.glowItems", false);
    }

    public static int handShadersGlowColor() {
        return ((int) Hooks.dbl("HandShaders.glowColor", 1.6777215E7));
    }

    public static float handShadersGlowOpacity() {
        return ((float) Hooks.dbl("HandShaders.glowOpacity", 1.0));
    }

    public static boolean handShadersGlowTextured() {
        return Hooks.bool("HandShaders.glowTextured", false);
    }

    public static boolean handShadersGlowFullbright() {
        return Hooks.bool("HandShaders.glowFullbright", false);
    }

    public static boolean handShadersGlowOutline() {
        return Hooks.bool("HandShaders.glowOutline", false);
    }

    public static int handShadersGlowOutlineColor() {
        return ((int) Hooks.dbl("HandShaders.glowOutlineColor", 1.6777215E7));
    }

    public static float handShadersGlowOutlineWidth() {
        return ((float) Hooks.dbl("HandShaders.glowOutlineWidth", 1.0));
    }

    public static boolean handShadersGlowBloom() {
        return Hooks.bool("HandShaders.glowBloom", true);
    }

    public static float handShadersGlowSpread() {
        return ((float) Hooks.dbl("HandShaders.glowSpread", 0.0));
    }

    public static boolean handShadersGlowAntialiasing() {
        return Hooks.bool("HandShaders.glowAA", false);
    }

    public static boolean handShadersInvertHideHand() {
        return Hooks.bool("HandShaders.invertHideHand", false);
    }

    public static void handShadersRender() {
        Hooks.run("HandShaders.render");
    }

    public static boolean nameProtectEnabled() {
        return Hooks.bool("NameProtect.enabled", false);
    }

    public static Component nameProtectReplaceInTree(Component arg0) {
        Component var10 = null;
        Object var5 = Hooks.obj1("NameProtect.replaceInTree", arg0);
        if ((var5 instanceof Component)) {
            var10 = ((Component) var5);
        } else {
            var10 = arg0;
        }
        return var10;
    }

    public static String nameProtectMaybeReplace(String arg0) {
        String var7 = null;
        Object var4 = Hooks.obj1("NameProtect.maybeReplace", arg0);
        if (var4 == null) {
            var7 = arg0;
        } else {
            var7 = var4.toString();
        }
        return var7;
    }

    public static String nameProtectSpoofName() {
        String var5 = null;
        Object var2 = Hooks.obj("NameProtect.spoofName");
        if (var2 == null) {
            var5 = "";
        } else {
            var5 = var2.toString();
        }
        return var5;
    }

    public static float handShadersChromaSpeed() {
        return ((float) Hooks.dbl("HandShaders.chromaSpeed", 0.3));
    }

    public static float handShadersChromaSaturation() {
        return ((float) Hooks.dbl("HandShaders.chromaSat", 1.0));
    }

    public static float handShadersChromaBrightness() {
        return ((float) Hooks.dbl("HandShaders.chromaBright", 1.0));
    }

    public static float handShadersInvertIntensity() {
        return ((float) Hooks.dbl("HandShaders.invertIntensity", 1.0));
    }

    public static boolean handShadersInvertHue() {
        return Hooks.bool("HandShaders.invertHue", false);
    }

    public static boolean handShadersInvertSaturation() {
        return Hooks.bool("HandShaders.invertSat", false);
    }

    public static boolean handShadersInvertBrightness() {
        return Hooks.bool("HandShaders.invertBright", false);
    }

    public static float handShadersInvertEdgeGlow() {
        return ((float) Hooks.dbl("HandShaders.invertEdgeGlow", 0.5));
    }

    public static int handShadersInvertGlowColor() {
        return ((int) Hooks.dbl("HandShaders.invertGlowColor", -1.6776961E7));
    }

    public static int handShadersOutlineColor() {
        return ((int) Hooks.dbl("HandShaders.outlineColor", -1.0));
    }

    public static float handShadersOutlineThickness() {
        return ((float) Hooks.dbl("HandShaders.outlineThickness", 1.5));
    }

    public static boolean handShadersTintEnabled() {
        return Hooks.bool("HandShaders.tintEnabled", false);
    }

    public static int handShadersTintColor() {
        return ((int) Hooks.dbl("HandShaders.tintColor", -65536.0));
    }

    public static float handShadersTintAmount() {
        return ((float) Hooks.dbl("HandShaders.tintAmount", 0.4));
    }

    public static String playerEspMode() {
        String var5 = null;
        Object var2 = Hooks.obj("PlayerESP.mode");
        if (var2 == null) {
            var5 = "Outline";
        } else {
            var5 = var2.toString();
        }
        return var5;
    }

    public static String playerEspBoxStyle() {
        String var5 = null;
        Object var2 = Hooks.obj("PlayerESP.boxStyle");
        if (var2 == null) {
            var5 = "Full";
        } else {
            var5 = var2.toString();
        }
        return var5;
    }

    public static boolean playerEspOutlineEnabled() {
        return Hooks.bool("PlayerESP.outlineEnabled", true);
    }

    public static float playerEspOutlineThickness() {
        return ((float) Hooks.dbl("PlayerESP.outlineThickness", 2.0));
    }

    public static boolean playerEspFillEnabled() {
        return Hooks.bool("PlayerESP.fillEnabled", false);
    }

    public static int playerEspFillColor() {
        return ((int) Hooks.dbl("PlayerESP.fillColor", -1.6733441E7));
    }

    public static float playerEspFillOpacity() {
        return ((float) Hooks.dbl("PlayerESP.fillOpacity", 0.25));
    }

    public static boolean playerEspThroughWalls() {
        return Hooks.bool("PlayerESP.throughWalls", true);
    }

    public static float renderAnimationsFactor() {
        return ((float) Hooks.dbl("RenderAnimations.factor", 1.0));
    }

    public static boolean hudLayoutIsEditing() {
        return Hooks.bool("HudLayout.isEditing", false);
    }

    public static void hudLayoutEnter() {
        Hooks.run("HudLayout.enter");
    }

    public static void hudLayoutExit() {
        Hooks.run("HudLayout.exit");
    }

    public static void hudLayoutExitNoGrab() {
        Hooks.run("HudLayout.exitNoGrab");
    }

    public static void hudLayoutOnMouseButton(int arg0, int arg1) {
        int[] var6 = new int[2];
        var6[0] = arg0;
        var6[1] = arg1;
        Hooks.run1("HudLayout.onMouseButton", var6);
    }

    public static boolean animationsEnabled() {
        return Hooks.bool("Animations.enabled", false);
    }

    public static String animationsMode() {
        String var5 = null;
        Object var2 = Hooks.obj("Animations.mode");
        if (var2 == null) {
            var5 = "Mode 1";
        } else {
            var5 = var2.toString();
        }
        return var5;
    }

    public static int animationsStrength() {
        return ((int) Hooks.dbl("Animations.strength", 50.0));
    }

    public static Object aimAssistCurrentTarget() {
        return Hooks.obj("AimAssist.currentTarget");
    }

    public static float[] smoothCameraSmooth(float arg0, float arg1) {
        float[] var12 = null;
        Float var13 = arg0;
        Float var14 = arg1;
        Object var7 = Hooks.obj2("SmoothCamera.smooth", var13, var14);
        if ((var7 instanceof float[])) {
            var12 = ((float[]) var7);
        } else {
            var12 = null;
        }
        return var12;
    }

    public static Vec3 smoothCameraPosition(Object arg0, Object arg1, float arg2) {
        Vec3 var14 = null;
        Object[] var15 = new Object[3];
        var15[0] = arg0;
        var15[1] = arg1;
        Float var20 = arg2;
        var15[2] = var20;
        Object var9 = Hooks.obj1("SmoothCamera.position", var15);
        if ((var9 instanceof Vec3)) {
            var14 = ((Vec3) var9);
        } else {
            var14 = null;
        }
        return var14;
    }

}
