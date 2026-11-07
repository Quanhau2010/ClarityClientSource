package need.clarity.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import need.clarity.api.Modules;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.particles.ParticleOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPacketListener.class)
public class ExplosionPacketMixin {
    public ExplosionPacketMixin() {
    }

    @WrapWithCondition(method = "handleExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V"))
    private boolean clarity$explosionEmitter(ClientLevel arg0, ParticleOptions arg1, double arg2, double arg3, double arg4, double arg5, double arg6, double arg7) {
        return (!(Modules.noRenderHideExplosionAt(arg2, arg3, arg4)));
    }

}
