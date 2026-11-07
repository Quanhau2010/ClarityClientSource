package need.clarity.mixin;

import need.clarity.api.Modules;
import need.clarity.api.SkyApi;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FogRenderer.class)
public abstract class SkyShadersFogMixin {
    public SkyShadersFogMixin() {
    }

    @Inject(method = "setupFog(Lnet/minecraft/client/Camera;ILnet/minecraft/client/DeltaTracker;FLnet/minecraft/client/multiplayer/ClientLevel;)Lnet/minecraft/client/renderer/fog/FogData;", at = @At("RETURN"))
    private void clarity$skyFog(Camera arg0, int arg1, DeltaTracker arg2, float arg3, ClientLevel arg4, CallbackInfoReturnable arg5) {
        FogData var7 = ((FogData) arg5.getReturnValue());
        if (var7 != null) {
            if (var7.color == null) {
            }
        }
        return;
        if (SkyApi.matchFog()) {
            if (!SkyApi.wasActiveLastFrame()) {
            }
        }
        return;
        if (Modules.ambienceOverrideFogColour()) {
            return;
        }
        String var8 = SkyApi.preset();
        float[][] var9 = SkyApi.colors();
        float[] var10 = SkyApi.horizon(var8, var9[0], var9[1]);
        var7.color.set(var10[0], var10[1], var10[2], var7.color.w);
    }

}
