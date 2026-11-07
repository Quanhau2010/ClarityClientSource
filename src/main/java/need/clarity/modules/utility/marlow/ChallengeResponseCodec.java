package need.clarity.modules.utility.marlow;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class ChallengeResponseCodec implements StreamCodec {
    ChallengeResponseCodec() {
    }

    public void writePacket(FriendlyByteBuf arg0, ChallengeResponsePayload arg1) {
        int var6 = arg1.id();
        FriendlyByteBuf var5 = arg0.writeInt(var6);
    }

    @NotNull
    public ChallengeResponsePayload readPacket(FriendlyByteBuf arg0) {
        int var4 = arg0.readInt();
        ChallengeResponsePayload var2 = new ChallengeResponsePayload(var4);
        return var2;
    }

}
