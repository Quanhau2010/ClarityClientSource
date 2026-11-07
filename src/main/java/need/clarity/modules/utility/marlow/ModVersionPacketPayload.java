package need.clarity.modules.utility.marlow;

import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public record ModVersionPacketPayload(int major, int minor, int patch, boolean snapshot) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type PACKET_TYPE;
    public static final StreamCodec STREAM_CODEC;

    @NotNull
    public CustomPacketPayload.Type type() {
        return PACKET_TYPE;
    }

    static {
        Identifier var3 = MarlowChannelIdentifiers.evaluate("version");
        CustomPacketPayload.Type var0 = new CustomPacketPayload.Type(var3);
        PACKET_TYPE = var0;
        ModVersionPacketCodec var2 = new ModVersionPacketCodec();
        STREAM_CODEC = var2;
    }

}
