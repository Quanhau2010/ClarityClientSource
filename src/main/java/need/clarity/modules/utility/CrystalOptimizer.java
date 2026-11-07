package need.clarity.modules.utility;

import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.utility.marlow.ChallengePacketPayload;
import need.clarity.modules.utility.marlow.ChallengeResponsePayload;
import need.clarity.modules.utility.marlow.LegacyOptOutPacketPayload;
import need.clarity.modules.utility.marlow.MarlowServerTracker;
import need.clarity.modules.utility.marlow.ModVersionPacketPayload;
import need.clarity.modules.utility.marlow.OptOutPacketPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class CrystalOptimizer extends ModuleBase {
    private static CrystalOptimizer INSTANCE;
    private final MarlowServerTracker marlowServerTracker;
    private static boolean enabledState;
    private static final Component fieldVal;

    public static boolean isEligible() {
        int var6 = 0;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                if (!INSTANCE.marlowServerTracker.isEligible()) {
                    var6 = 1;
                } else {
                    var6 = 0;
                }
                return var6;
            }
        }
    }

    public CrystalOptimizer() {
        super("Crystal Optimizer", "utility");
        MarlowServerTracker var12 = new MarlowServerTracker();
        this.marlowServerTracker = var12;
        INSTANCE = this;
        Hooks.Bool0 var13 = () -> CrystalOptimizer.isEligible();
        Hooks.bool0("CrystalOptimizer.isActive", var13);
        if (!enabledState) {
            enabledState = true;
            applyStep();
        }
        this.applyStep1();
        Event var10 = ClientPlayConnectionEvents.JOIN;
        ClientPlayConnectionEvents.Join var15 = (p0, p1, p2) -> this.lambda$a(p0, p1, p2);
        var10.register(var15);
        Event var11 = ClientPlayConnectionEvents.DISCONNECT;
        ClientPlayConnectionEvents.Disconnect var17 = (p0, p1) -> this.lambda$a(p0, p1);
        var11.register(var17);
    }

    public void onEnable() {
        this.marlowServerTracker.applyStep();
    }

    public void onDisable() {
    }

    private static void applyStep() {
        PayloadTypeRegistry var0 = PayloadTypeRegistry.clientboundConfiguration();
        CustomPacketPayload.Type var1 = OptOutPacketPayload.PACKET_TYPE;
        StreamCodec var2 = OptOutPacketPayload.STREAM_CODEC;
        CustomPacketPayload.TypeAndCodec var3 = var0.register(var1, var2);
        PayloadTypeRegistry var4 = PayloadTypeRegistry.clientboundPlay();
        CustomPacketPayload.Type var14 = OptOutPacketPayload.PACKET_TYPE;
        StreamCodec var19 = OptOutPacketPayload.STREAM_CODEC;
        CustomPacketPayload.TypeAndCodec var5 = var4.register(var14, var19);
        PayloadTypeRegistry var6 = PayloadTypeRegistry.clientboundPlay();
        CustomPacketPayload.Type var15 = ChallengePacketPayload.PACKET_TYPE;
        StreamCodec var20 = ChallengePacketPayload.STREAM_CODEC;
        CustomPacketPayload.TypeAndCodec var7 = var6.register(var15, var20);
        PayloadTypeRegistry var8 = PayloadTypeRegistry.serverboundPlay();
        CustomPacketPayload.Type var16 = LegacyOptOutPacketPayload.PACKET_TYPE;
        StreamCodec var21 = LegacyOptOutPacketPayload.STREAM_CODEC;
        CustomPacketPayload.TypeAndCodec var9 = var8.register(var16, var21);
        PayloadTypeRegistry var10 = PayloadTypeRegistry.serverboundPlay();
        CustomPacketPayload.Type var17 = ModVersionPacketPayload.PACKET_TYPE;
        StreamCodec var22 = ModVersionPacketPayload.STREAM_CODEC;
        CustomPacketPayload.TypeAndCodec var11 = var10.register(var17, var22);
        PayloadTypeRegistry var12 = PayloadTypeRegistry.serverboundPlay();
        CustomPacketPayload.Type var18 = ChallengeResponsePayload.PACKET_TYPE;
        StreamCodec var23 = ChallengeResponsePayload.STREAM_CODEC;
        CustomPacketPayload.TypeAndCodec var13 = var12.register(var18, var23);
    }

    private void applyStep1() {
        CustomPacketPayload.Type var1 = OptOutPacketPayload.PACKET_TYPE;
        ClientPlayNetworking.PlayPayloadHandler var6 = (p0, p1) -> this.lambda$a(p0, p1);
        boolean var3 = ClientPlayNetworking.registerGlobalReceiver(var1, var6);
        CustomPacketPayload.Type var4 = ChallengePacketPayload.PACKET_TYPE;
        ClientPlayNetworking.PlayPayloadHandler var7 = (p0, p1) -> CrystalOptimizer.lambda$a(p0, p1);
        boolean var5 = ClientPlayNetworking.registerGlobalReceiver(var4, var7);
    }

    private static String resolveLabel(Minecraft arg0) {
        String var5 = null;
        if (arg0.getCurrentServer() != null) {
            var5 = arg0.getCurrentServer().ip;
        } else {
            var5 = null;
        }
        return var5;
    }

    private static Component evaluate() {
        MutableComponent var0 = Component.empty();
        MutableComponent var20 = Component.literal("Why is this disabled?\n");
        ChatFormatting var2 = ChatFormatting.AQUA;
        MutableComponent var21 = var20.withStyle(var2);
        MutableComponent var7 = var0.append(var21);
        MutableComponent var23 = Component.literal("\u2022 This server has requested Crystal Optimizer to be disabled.\n");
        ChatFormatting var36 = ChatFormatting.GRAY;
        MutableComponent var24 = var23.withStyle(var36);
        MutableComponent var8 = var7.append(var24);
        MutableComponent var26 = Component.literal("\u2022 This may be to enforce server rules or avoid compatibility issues.\n");
        ChatFormatting var37 = ChatFormatting.GRAY;
        MutableComponent var27 = var26.withStyle(var37);
        MutableComponent var9 = var8.append(var27);
        MutableComponent var29 = Component.literal("\nThis only applies while you are connected to this server.");
        ChatFormatting var38 = ChatFormatting.DARK_GRAY;
        MutableComponent var30 = var29.withStyle(var38);
        MutableComponent var10 = var9.append(var30);
        Style var11 = Style.EMPTY;
        HoverEvent.ShowText var31 = new HoverEvent.ShowText(var10);
        Style var12 = var11.withHoverEvent(var31);
        MutableComponent var14 = Component.literal("Crystal Optimizer disabled on this server.");
        ChatFormatting var39 = ChatFormatting.RED;
        Style var33 = var12.withColor(var39);
        MutableComponent var15 = var14.withStyle(var33);
        return fieldVal.copy().withStyle(var12).append(var15);
    }

    static {
        enabledState = false;
        MutableComponent var4 = Component.literal("[");
        ChatFormatting var1 = ChatFormatting.GRAY;
        MutableComponent var5 = var4.withStyle(var1);
        MutableComponent var9 = Component.literal("Crystal Optimizer");
        ChatFormatting var2 = ChatFormatting.AQUA;
        MutableComponent var10 = var9.withStyle(var2);
        MutableComponent var6 = var5.append(var10);
        MutableComponent var12 = Component.literal("] ");
        ChatFormatting var14 = ChatFormatting.GRAY;
        MutableComponent var13 = var12.withStyle(var14);
        fieldVal = var6.append(var13);
    }

}
