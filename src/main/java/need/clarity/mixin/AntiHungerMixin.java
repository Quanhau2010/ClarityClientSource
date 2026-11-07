package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientCommonPacketListenerImpl.class)
public class AntiHungerMixin {
    public AntiHungerMixin() {
    }

    @Redirect(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/Connection;send(Lnet/minecraft/network/protocol/Packet;)V"))
    private void cl$antiHungerSend(Connection arg0, Packet arg1) {
        if ((arg1 instanceof ServerboundMovePlayerPacket)) {
            ServerboundMovePlayerPacket var3 = ((ServerboundMovePlayerPacket) arg1);
            if (Modules.antiHungerSpoof()) {
                arg1 = modifyOnGround(var3, false);
            }
            arg0.send(arg1);
            return;
        }
    }

    private static ServerboundMovePlayerPacket modifyOnGround(ServerboundMovePlayerPacket arg0, boolean arg1) {
        ServerboundMovePlayerPacket.PosRot var2 = null;
        if ((arg0 instanceof ServerboundMovePlayerPacket.PosRot)) {
            var2 = ((ServerboundMovePlayerPacket.PosRot) arg0);
            return new ServerboundMovePlayerPacket.PosRot(var2.getX(0.0), var2.getY(0.0), var2.getZ(0.0), var2.getYRot(0.0F), var2.getXRot(0.0F), arg1, var2.horizontalCollision());
        }
        if ((arg0 instanceof ServerboundMovePlayerPacket.Pos)) {
            var2 = ((ServerboundMovePlayerPacket.Pos) arg0);
            return new ServerboundMovePlayerPacket.Pos(var2.getX(0.0), var2.getY(0.0), var2.getZ(0.0), arg1, var2.horizontalCollision());
        }
        if ((arg0 instanceof ServerboundMovePlayerPacket.Rot)) {
            var2 = ((ServerboundMovePlayerPacket.Rot) arg0);
            return new ServerboundMovePlayerPacket.Rot(var2.getYRot(0.0F), var2.getXRot(0.0F), arg1, var2.horizontalCollision());
        }
        return new ServerboundMovePlayerPacket.StatusOnly(arg1, arg0.horizontalCollision());
    }

}
