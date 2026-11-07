package need.clarity.mixin;

import need.clarity.PerPacketSendEvents;
import need.clarity.event.EventPacketSend;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonPacketListenerImpl.class)
public class PacketSendMixin {
    public PacketSendMixin() {
    }

    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void cl$onPacketSend(Packet arg0, CallbackInfo arg1) {
        if (PerPacketSendEvents.fire(new EventPacketSend(arg0)).isCancelled()) {
            arg1.cancel();
        }
    }

}
