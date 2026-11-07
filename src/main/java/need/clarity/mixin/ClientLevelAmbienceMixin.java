package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class ClientLevelAmbienceMixin {
    public ClientLevelAmbienceMixin() {
    }

    @Inject(method = "getOverworldClockTime()J", at = @At("RETURN"), cancellable = true)
    private void ambience$overrideOverworldClockTime(CallbackInfoReturnable arg0) {
        if ((this instanceof ClientLevel)) {
            if (Modules.ambienceOverrideTime()) {
                arg0.setReturnValue(Modules.ambienceDayTime());
            }
            return;
        }
    }

    @Inject(method = "getDefaultClockTime()J", at = @At("RETURN"), cancellable = true)
    private void ambience$overrideDefaultClockTime(CallbackInfoReturnable arg0) {
        if ((this instanceof ClientLevel)) {
            if (Modules.ambienceOverrideTime()) {
                arg0.setReturnValue(Modules.ambienceDayTime());
            }
            return;
        }
    }

    @Inject(method = "getRainLevel(F)F", at = @At("RETURN"), cancellable = true)
    private void ambience$overrideRainLevel(float arg0, CallbackInfoReturnable arg1) {
        if (!(this instanceof ClientLevel)) {
            return;
        }
        if (!Modules.ambienceOverrideWeather()) {
            return;
        }
        if ("Clear".equals(Modules.ambienceWeatherMode())) {
        } else {
        }
        0.0F.setReturnValue(1.0F);
    }

    @Inject(method = "getThunderLevel(F)F", at = @At("RETURN"), cancellable = true)
    private void ambience$overrideThunderLevel(float arg0, CallbackInfoReturnable arg1) {
        if (!(this instanceof ClientLevel)) {
            return;
        }
        if (!Modules.ambienceOverrideWeather()) {
            return;
        }
        if ("Thunder".equals(Modules.ambienceWeatherMode())) {
        } else {
        }
        1.0F.setReturnValue(0.0F);
    }

}
