package need.clarity.modules.utility.marlow;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public final class LegacyOptOutPacketPayload implements CustomPacketPayload {
    public static final CustomPacketPayload.Type PACKET_TYPE;
    public static final LegacyOptOutPacketPayload INSTANCE;
    public static final StreamCodec STREAM_CODEC;

    private LegacyOptOutPacketPayload() {
    }

    @NotNull
    public CustomPacketPayload.Type type() {
        return PACKET_TYPE;
    }

    static {
        Identifier var4 = MarlowChannelIdentifiers.evaluate("opt_out_ack");
        CustomPacketPayload.Type var0 = new CustomPacketPayload.Type(var4);
        PACKET_TYPE = var0;
        LegacyOptOutPacketPayload var2 = new LegacyOptOutPacketPayload();
        INSTANCE = var2;
        LegacyOptOutPacketCodec var3 = new LegacyOptOutPacketCodec();
        STREAM_CODEC = var3;
    }

}
