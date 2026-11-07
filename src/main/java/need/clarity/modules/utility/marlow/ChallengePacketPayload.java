package need.clarity.modules.utility.marlow;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record ChallengePacketPayload(int id) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type PACKET_TYPE;
    public static final StreamCodec STREAM_CODEC;

    @NotNull
    public CustomPacketPayload.Type type() {
        return PACKET_TYPE;
    }

    static {
        Identifier var3 = MarlowChannelIdentifiers.evaluate("challenge");
        CustomPacketPayload.Type var0 = new CustomPacketPayload.Type(var3);
        PACKET_TYPE = var0;
        ChallengePacketCodec var2 = new ChallengePacketCodec();
        STREAM_CODEC = var2;
    }

}
