package need.clarity.mixin;

import com.mojang.authlib.GameProfile;
import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.player.PlayerSkin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInfo.class)
public abstract class SkinsMixin {
    @Shadow
    @Final
    private GameProfile profile;

    public SkinsMixin() {
    }

    @Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
    private void clarity$overrideSkin(CallbackInfoReturnable arg0) {
        Minecraft var2 = Minecraft.getInstance();
        if (var2 != null) {
            if (var2.player != null) {
                if (this.profile == null) {
                }
            }
            return;
            if (!this.profile.id().equals(var2.player.getUUID())) {
                return;
            }
            PlayerSkin var3 = Modules.skinChangerOverride(((PlayerSkin) arg0.getReturnValue()));
            if (var3 != null) {
                arg0.setReturnValue(var3);
            }
            return;
        }
    }

}
