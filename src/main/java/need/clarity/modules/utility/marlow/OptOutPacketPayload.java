package need.clarity.modules.utility.marlow;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public final class OptOutPacketPayload implements CustomPacketPayload {
    public static final CustomPacketPayload.Type PACKET_TYPE;
    public static final OptOutPacketPayload INSTANCE;
    public static final StreamCodec STREAM_CODEC;

    private OptOutPacketPayload() {
    }

    @NotNull
    public CustomPacketPayload.Type type() {
        return PACKET_TYPE;
    }

    static {
        Identifier var4 = MarlowChannelIdentifiers.evaluate("opt_out");
        CustomPacketPayload.Type var0 = new CustomPacketPayload.Type(var4);
        PACKET_TYPE = var0;
        OptOutPacketPayload var2 = new OptOutPacketPayload();
        INSTANCE = var2;
        OptOutPacketCodec var3 = new OptOutPacketCodec();
        STREAM_CODEC = var3;
    }

}
