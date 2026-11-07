package need.clarity.modules.utility.marlow;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class OptOutPacketCodec implements StreamCodec {
    OptOutPacketCodec() {
    }

    public void writePacket(FriendlyByteBuf arg0, OptOutPacketPayload arg1) {
    }

    @NotNull
    public OptOutPacketPayload readPacket(FriendlyByteBuf arg0) {
        return OptOutPacketPayload.INSTANCE;
    }

}
