package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.renderer.LightmapRenderStateExtractor;
import net.minecraft.client.renderer.state.LightmapRenderState;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapRenderStateExtractor.class)
public abstract class LightmapAmbienceMixin {
    public LightmapAmbienceMixin() {
    }

    @Inject(method = "extract(Lnet/minecraft/client/renderer/state/LightmapRenderState;F)V", at = @At("TAIL"))
    private void ambience$tintLightmap(LightmapRenderState arg0, float arg1, CallbackInfo arg2) {
        if (!Modules.ambienceOverrideLightmap()) {
            return;
        }
        float[] var4 = Modules.ambienceLightmapColour();
        float var5 = var4[0];
        float var6 = var4[1];
        float var7 = var4[2];
        if (var5 >= 1.0F) {
            if (var6 >= 1.0F) {
                if (var7 >= 1.0F) {
                    return;
                }
                tintVec(arg0.blockLightTint, var5, var6, var7);
                tintVec(arg0.skyLightColor, var5, var6, var7);
                tintVec(arg0.ambientColor, var5, var6, var7);
                return;
            }
        }
    }

    private static void tintVec(Vector3fc arg0, float arg1, float arg2, float arg3) {
        if ((arg0 instanceof Vector3f)) {
            ((Vector3f) arg0).mul(arg1, arg2, arg3);
        }
    }

}
