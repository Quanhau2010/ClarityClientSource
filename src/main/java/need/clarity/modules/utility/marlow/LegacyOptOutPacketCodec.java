package need.clarity.modules.utility.marlow;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class LegacyOptOutPacketCodec implements StreamCodec {
    LegacyOptOutPacketCodec() {
    }

    public void writePacket(FriendlyByteBuf arg0, LegacyOptOutPacketPayload arg1) {
    }

    @NotNull
    public LegacyOptOutPacketPayload readPacket(FriendlyByteBuf arg0) {
        return LegacyOptOutPacketPayload.INSTANCE;
    }

}
