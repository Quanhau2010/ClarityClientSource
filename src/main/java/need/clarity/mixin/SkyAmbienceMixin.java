package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkyRenderer.class)
public abstract class SkyAmbienceMixin {
    public SkyAmbienceMixin() {
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/client/multiplayer/ClientLevel;FLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/state/level/SkyRenderState;)V", at = @At("TAIL"))
    private void ambience$overrideSkyColor(ClientLevel arg0, float arg1, Camera arg2, SkyRenderState arg3, CallbackInfo arg4) {
        long var6 = 0L;
        float var8 = 0.0F;
        float var9 = 0.0F;
        if (Modules.ambienceOverrideTime()) {
            var6 = Modules.ambienceDayTime();
            var8 = (((float) var6) / 24000.0F);
            var9 = (var8 * 360.0F);
            float var10 = (var9 * 0.017453292F);
            arg3.sunAngle = var10;
            arg3.moonAngle = (var10 + 3.1415927F);
            arg3.starAngle = var10;
        }
        if (!Modules.ambienceOverrideSkyColour()) {
            return;
        }
        var6 = Modules.ambienceSkyColour();
        if (!Modules.ambienceSkyGradient()) {
            arg3.skyColor = toArgb(var6[0], var6[1], var6[2]);
            return;
        }
        float[] var7 = Modules.ambienceSkyGradientColour2();
        var8 = arg2.xRot();
        var9 = ((float) Math.max(0.0, Math.min(1.0, (((double) -(var8)) / 90.0))));
        arg3.skyColor = toArgb(lerp(var6[0], var7[0], var9), lerp(var6[1], var7[1], var9), lerp(var6[2], var7[2], var9));
    }

    private static int toArgb(float arg0, float arg1, float arg2) {
        int var3 = Math.max(0, Math.min(255, ((int) (arg0 * 255.0F))));
        int var4 = Math.max(0, Math.min(255, ((int) (arg1 * 255.0F))));
        int var5 = Math.max(0, Math.min(255, ((int) (arg2 * 255.0F))));
        return (((-16777216 | (var3 << 16)) | (var4 << 8)) | var5);
    }

    private static float lerp(float arg0, float arg1, float arg2) {
        return (arg0 + ((arg1 - arg0) * arg2));
    }

}
