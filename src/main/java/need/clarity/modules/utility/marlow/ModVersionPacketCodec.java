package need.clarity.modules.utility.marlow;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class ModVersionPacketCodec implements StreamCodec {
    ModVersionPacketCodec() {
    }

    public void writePacket(FriendlyByteBuf arg0, ModVersionPacketPayload arg1) {
        int var12 = arg1.major();
        FriendlyByteBuf var5 = arg0.writeVarInt(var12);
        int var14 = arg1.minor();
        FriendlyByteBuf var7 = arg0.writeVarInt(var14);
        int var16 = arg1.patch();
        FriendlyByteBuf var9 = arg0.writeVarInt(var16);
        boolean var18 = arg1.snapshot();
        FriendlyByteBuf var11 = arg0.writeBoolean(var18);
    }

    public ModVersionPacketPayload readPacket(FriendlyByteBuf arg0) {
        int var7 = arg0.readVarInt();
        int var8 = arg0.readVarInt();
        int var9 = arg0.readVarInt();
        boolean var10 = arg0.readBoolean();
        ModVersionPacketPayload var2 = new ModVersionPacketPayload(var7, var8, var9, var10);
        return var2;
    }

}
