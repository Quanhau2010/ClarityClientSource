package need.clarity.mixin;

import need.clarity.PerPacketEvents;
import need.clarity.event.EventPacketReceive;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Connection.class)
public class ConnectionMixin {
    public ConnectionMixin() {
    }

    @Inject(method = "genericsFtw", at = @At("HEAD"), cancellable = true)
    private static void cl$onPacketReceive(Packet arg0, PacketListener arg1, CallbackInfo arg2) {
        if (PerPacketEvents.fire(new EventPacketReceive(arg0)).isCancelled()) {
            arg2.cancel();
        }
    }

}
