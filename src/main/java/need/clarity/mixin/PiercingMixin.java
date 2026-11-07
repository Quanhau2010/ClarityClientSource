package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class PiercingMixin {
    @Shadow
    public HitResult hitResult;
    @Shadow
    public Entity crosshairPickEntity;

    public PiercingMixin() {
    }

    @Inject(method = "handleKeybinds", at = @At("HEAD"))
    private void cl$piercingHandleKeybinds(CallbackInfo arg0) {
        Minecraft var2 = ((Minecraft) this);
        if (var2.player != null) {
            if (var2.level == null) {
            }
        }
        return;
        if (!Modules.piercingEnabled()) {
            return;
        }
        Modules.piercingHandleInput(var2);
    }

}
