package need.clarity.mixin;

import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public class XCarryMixin {
    public XCarryMixin() {
    }

    @Redirect(method = "closeContainer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientPacketListener;send(Lnet/minecraft/network/protocol/Packet;)V"))
    private void xcarry$redirectSend(ClientPacketListener arg0, Packet arg1) {
        LocalPlayer var3 = ((LocalPlayer) this);
        ModuleBase var4 = ModuleRegistry.get("XCarry");
        if (var4 != null) {
            if (var4.isEnabled()) {
                if (var3.containerMenu.containerId == 0) {
                    return;
                }
                arg0.send(arg1);
                return;
            }
        }
    }

}
