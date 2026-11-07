package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public class ExplosionParticleMixin {
    public ExplosionParticleMixin() {
    }

    @Inject(method = "trackExplosionEffects", at = @At("HEAD"), cancellable = true)
    private void clarity$noTrackedExplosionParticles(Vec3 arg0, float arg1, int arg2, WeightedList arg3, CallbackInfo arg4) {
        if (Modules.noRenderHideExplosionAt(arg0.x, arg0.y, arg0.z)) {
            arg4.cancel();
        }
    }

    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    private void clarity$noExplosionParticles(Entity arg0, DamageSource arg1, ExplosionDamageCalculator arg2, double arg3, double arg4, double arg5, float arg6, boolean arg7, Level.ExplosionInteraction arg8, ParticleOptions arg9, ParticleOptions arg10, WeightedList arg11, Holder arg12, CallbackInfo arg13) {
        if (Modules.noRenderExplosion()) {
            arg13.cancel();
        }
    }

}
