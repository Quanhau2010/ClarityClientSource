package need.clarity.modules.utility.marlow;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record ChallengeResponsePayload(int id) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type PACKET_TYPE;
    public static final StreamCodec STREAM_CODEC;

    @NotNull
    public CustomPacketPayload.Type type() {
        return PACKET_TYPE;
    }

    static {
        Identifier var3 = MarlowChannelIdentifiers.evaluate("challenge_response");
        CustomPacketPayload.Type var0 = new CustomPacketPayload.Type(var3);
        PACKET_TYPE = var0;
        ChallengeResponseCodec var2 = new ChallengeResponseCodec();
        STREAM_CODEC = var2;
    }

}
