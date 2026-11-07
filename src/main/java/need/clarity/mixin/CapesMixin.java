package need.clarity.mixin;

import com.mojang.authlib.GameProfile;
import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerSkin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInfo.class)
public abstract class CapesMixin {
    @Shadow
    @Final
    private GameProfile profile;

    public CapesMixin() {
    }

    @Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
    private void clarity$overrideCape(CallbackInfoReturnable arg0) {
        Minecraft var2 = Minecraft.getInstance();
        if (var2 != null) {
            if (var2.player == null) {
            }
        }
        return;
        if (!this.profile.id().equals(var2.player.getUUID())) {
            return;
        }
        if (!Modules.capesEnabled()) {
            return;
        }
        Object var3 = Modules.capesTexture();
        if ((var3 instanceof Identifier)) {
        } else {
        }
        Object var4 = null;
        if (var4 == null) {
            return;
        }
        PlayerSkin var5 = ((PlayerSkin) arg0.getReturnValue());
        ClientAsset.ResourceTexture var6 = new ClientAsset.ResourceTexture(var4, var4);
        arg0.setReturnValue(new PlayerSkin(var5.body(), var6, var6, var5.model(), var5.secure()));
    }

}
