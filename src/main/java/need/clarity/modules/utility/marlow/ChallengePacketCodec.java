package need.clarity.modules.utility.marlow;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class ChallengePacketCodec implements StreamCodec {
    ChallengePacketCodec() {
    }

    public void writePacket(FriendlyByteBuf arg0, ChallengePacketPayload arg1) {
        int var6 = arg1.id();
        FriendlyByteBuf var5 = arg0.writeInt(var6);
    }

    @NotNull
    public ChallengePacketPayload readPacket(FriendlyByteBuf arg0) {
        int var4 = arg0.readInt();
        ChallengePacketPayload var2 = new ChallengePacketPayload(var4);
        return var2;
    }

}
