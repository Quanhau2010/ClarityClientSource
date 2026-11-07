package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInfo.class)
public abstract class NameProtectTabListMixin {
    public NameProtectTabListMixin() {
    }

    @Inject(method = "getTabListDisplayName", at = @At("RETURN"), cancellable = true)
    private void cl$nameProtectTabList(CallbackInfoReturnable arg0) {
        if (!Modules.nameProtectEnabled()) {
            return;
        }
        Minecraft var2 = Minecraft.getInstance();
        if (var2.player == null) {
            return;
        }
        String var4 = ((PlayerInfo) this).getProfile().name();
        if (!var4.equals(var2.player.getGameProfile().name())) {
            return;
        }
        Component var5 = ((Component) arg0.getReturnValue());
        String var6 = Modules.nameProtectSpoofName();
        if (var5 != null) {
            String var7 = var5.getString();
            if (var7.contains(var4)) {
                arg0.setReturnValue(Component.literal(var7.replace(var4, var6)).withStyle(var5.getStyle()));
            }
        } else {
            arg0.setReturnValue(Component.literal(var6));
        }
    }

}
