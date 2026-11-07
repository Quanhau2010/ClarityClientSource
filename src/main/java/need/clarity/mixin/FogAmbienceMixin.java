package need.clarity.mixin;

import need.clarity.api.Modules;
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
public abstract class FogAmbienceMixin {
    public FogAmbienceMixin() {
    }

    @Inject(method = "setupFog(Lnet/minecraft/client/Camera;ILnet/minecraft/client/DeltaTracker;FLnet/minecraft/client/multiplayer/ClientLevel;)Lnet/minecraft/client/renderer/fog/FogData;", at = @At("RETURN"), cancellable = true)
    private void ambience$overrideFog(Camera arg0, int arg1, DeltaTracker arg2, float arg3, ClientLevel arg4, CallbackInfoReturnable arg5) {
        float[] var8 = null;
        FogData var7 = ((FogData) arg5.getReturnValue());
        if (var7 == null) {
            return;
        }
        if (Modules.ambienceOverrideFogColour()) {
            var8 = Modules.ambienceFogColour();
            var7.color.set(var8[0], var8[1], var8[2], 1.0F);
        }
        if (Modules.ambienceOverrideFogDistance()) {
            var8 = (Modules.ambienceFogDistanceChunks() * 16.0F);
            var7.environmentalStart = 0.0F;
            var7.environmentalEnd = var8;
            var7.renderDistanceStart = 0.0F;
            var7.renderDistanceEnd = var8;
            var7.skyEnd = var8;
            var7.cloudEnd = var8;
        }
        arg5.setReturnValue(var7);
    }

}
