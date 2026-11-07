package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundManager.class)
public class CrystalSoundMixin {
    public CrystalSoundMixin() {
    }

    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private void cl$muteCrystalBoom(SoundInstance arg0, CallbackInfoReturnable arg1) {
        if (Modules.crystalViewShouldMute(arg0)) {
            arg1.setReturnValue(SoundEngine.PlayResult.NOT_STARTED);
        }
    }

}
