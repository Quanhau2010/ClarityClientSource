package need.clarity.modules.combat;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import need.clarity.PerPacketEvents;
import need.clarity.WorldRenderEvent;
import need.clarity.event.EventPacketReceive;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.VecDeltaCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Backtrack extends ModuleBase {
    private final Queue fieldVal;
    private final Random fieldVal1;
    private Entity fieldVal2;
    private Vec3 positionVec;
    private static volatile LivingEntity targetEntity;
    private VecDeltaCodec fieldVal3;
    private float floatValue;

    public static LivingEntity evaluate() {
        return targetEntity;
    }

    public Backtrack() {
        super("Backtrack", "Combat");
        ConcurrentLinkedDeque var12 = new ConcurrentLinkedDeque();
        this.fieldVal = var12;
        Random var13 = new Random();
        this.fieldVal1 = var13;
        PerAttackEvents.register((p0) -> this.onStartAttack(p0));
        PerPacketEvents.register((p0) -> this.onPacketReceive(p0));
        WorldRenderEvent.register((p0, p1) -> this.renderWorldEffect(p0, p1));
    }

    public void onEnable() {
        this.fieldVal.clear();
        this.applyStep1();
    }

    public void onDisable() {
        this.applyStep(false);
        this.applyStep1();
    }

    private void onStartAttack(EventStartAttack arg0) {
        LivingEntity var15 = null;
        if (!this.isEnabled()) {
            return;
        }
        Entity var16 = arg0.getTarget();
        this.fieldVal2 = var16;
        Entity var9 = this.fieldVal2;
        if ((var9 instanceof LivingEntity)) {
            var15 = ((LivingEntity) var9);
        } else {
            var15 = null;
        }
        targetEntity = var15;
        this.applyStep1();
    }

    private void onPacketReceive(EventPacketReceive arg0) {
        float var95 = 0.0F;
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var13 = Minecraft.getInstance();
        if (var13.player != null) {
            if (var13.level == null) {
            }
        }
        return;
        if (var13.isSingleplayer()) {
            return;
        }
        Packet var21 = arg0.getPacket();
        if (var21 == null) {
            return;
        }
        if (!this.isEligible(var13)) {
            this.applyStep(false);
            this.applyStep1();
            return;
        }
        if (!(var21 instanceof ServerboundChatPacket)) {
            if (!(var21 instanceof ClientboundSystemChatPacket)) {
                if ((var21 instanceof ServerboundChatCommandPacket)) {
                }
            }
            return;
            if (!(var21 instanceof ClientboundPlayerPositionPacket)) {
                if ((var21 instanceof ClientboundDisconnectPacket)) {
                }
            }
            this.applyStep(false);
            this.applyStep1();
            return;
            if ((var21 instanceof ClientboundSetHealthPacket)) {
                if (Float.compare(((ClientboundSetHealthPacket) var21).getHealth(), 0.0F) <= 0) {
                    this.applyStep(false);
                    this.applyStep1();
                    return;
                }
            }
            if (Float.compare(this.floatValue, 0.0F) <= 0) {
                Float var107 = 150.0F;
                float var52 = ((Float) this.getSetting("Delay_lo", var107));
                Float var109 = 250.0F;
                float var56 = ((Float) this.getSetting("Delay_hi", var109));
                if (Float.compare(var56, var52) > 0) {
                    float var118 = this.fieldVal1.nextFloat();
                    float var121 = (var56 - var52);
                    float var119 = (var118 * var121);
                    var95 = (var52 + var119);
                } else {
                    var95 = var52;
                }
                this.floatValue = var95;
            }
            float var59 = this.floatValue;
            if (Float.compare(var59, 0.0F) <= 0) {
                this.applyStep(false);
                return;
            }
            this.applyStep(true);
            if (this.isEligible(var21, var13)) {
                this.applyStep(false);
                this.applyStep1();
                return;
            }
            this.applyStep(var21, var13);
            Queue var67 = this.fieldVal;
            long var6 = System.currentTimeMillis();
            DelayedPacketEntry var100 = new DelayedPacketEntry(var21, var6);
            boolean var68 = var67.add(var100);
            arg0.cancel();
            DelayedPacketEntry var73 = ((DelayedPacketEntry) this.fieldVal.peek());
            if (var73 != null) {
                long var75 = System.currentTimeMillis();
                long var114 = var73.timestampMs;
                if (Float.compare(((float) (var75 - var114)), var59) > 0) {
                    this.applyStep(false);
                }
                return;
            }
        }
    }

    private void applyStep(boolean arg0) {
        ClientPacketListener var12 = Minecraft.getInstance().getConnection();
        if (var12 == null) {
            this.fieldVal.clear();
            this.applyStep1();
            return;
        }
        long var14 = System.currentTimeMillis();
        while (true) {
            DelayedPacketEntry var19 = ((DelayedPacketEntry) this.fieldVal.peek());
            if (var19 == null) {
            } else {
                if (arg0) {
                    long var48 = var19.timestampMs;
                    float var26 = ((float) (var14 - var48));
                    float var46 = this.floatValue;
                    if (Float.compare(var26, var46) < 0) {
                    }
                } else {
                    Object var38 = this.fieldVal.poll();
                    try {
                        var19.packet.handle(var12);
                    } catch (Throwable ignored) {
                    }
                }
            }
            if (arg0) break;
            this.fieldVal.clear();
            this.applyStep1();
            return;
            if (!this.fieldVal.isEmpty()) break;
            this.applyStep1();
            return;
        }
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        int var38 = 0;
        int var44 = 0;
        if (!this.isEnabled()) {
            return;
        }
        Boolean var55 = true;
        if (!((Boolean) this.getSetting("Render", var55))) {
            return;
        }
        Minecraft var46 = Minecraft.getInstance();
        if (!this.isEligible(var46)) {
            this.applyStep1();
            return;
        }
        AABB var22 = this.evaluate();
        if (var22 == null) {
            return;
        }
        Boolean var57 = true;
        boolean var27 = ((Boolean) this.getSetting("Fill", var57));
        Boolean var59 = true;
        boolean var31 = ((Boolean) this.getSetting("Outline", var59));
        if (!var27) {
            if (!var31) {
                return;
            }
        }
        if (var27) {
            var38 = computeColorOrIndex(((String) this.getSetting("Fill Color", "#3a7fcc")), 80, false);
        } else {
            var38 = 0;
        }
        int var12 = ((byte) var38);
        if (var31) {
            var44 = computeColorOrIndex(((String) this.getSetting("Outline Color", "#ffffff")), 255, true);
        } else {
            var44 = 0;
        }
        int var13 = ((byte) var44);
        List var54 = List.of(var22);
        ClarityRenderUtils.drawBoxes(arg0, var54, var12, var13, var27, var31);
    }

    private boolean isEligible(Packet arg0, Minecraft arg1) {
        Vec3 var12 = null;
        Vec3 var84 = null;
        double var94 = 0.0;
        double var108 = 0.0;
        double var113 = 0.0;
        int var62 = 0;
        if (arg1.player != null) {
            if (arg1.level != null) {
                if (this.fieldVal2 == null) {
                }
            }
            return false;
            var12 = null;
            if ((arg0 instanceof ClientboundMoveEntityPacket)) {
                ClientboundMoveEntityPacket var35 = ((ClientboundMoveEntityPacket) arg0);
                if (!(this.isEligible(var35, arg1))) {
                    return false;
                }
            }
            if ((arg0 instanceof ClientboundEntityPositionSyncPacket)) {
                ClientboundEntityPositionSyncPacket var67 = ((ClientboundEntityPositionSyncPacket) arg0);
                int var69 = var67.id();
                int var129 = this.fieldVal2.getId();
                if (var69 != var129) {
                    return false;
                }
                var12 = var67.values().position();
            } else {
                if ((arg0 instanceof ClientboundTeleportEntityPacket)) {
                    ClientboundTeleportEntityPacket var77 = ((ClientboundTeleportEntityPacket) arg0);
                    int var79 = var77.id();
                    int var132 = this.fieldVal2.getId();
                    if (var79 != var132) {
                        return false;
                    }
                    if (this.positionVec != null) {
                        var84 = this.positionVec;
                    } else {
                        var84 = this.fieldVal2.position();
                    }
                    Vec3 var157 = var84;
                    Vec3 var87 = var77.change().position();
                    Set var89 = var77.relatives();
                    Relative var133 = Relative.X;
                    if (var89.contains(var133)) {
                        double var92 = var157.x;
                        double var139 = var87.x;
                        var94 = (var92 + var139);
                    } else {
                        var94 = var87.x;
                    }
                    double var20 = var94;
                    Set var97 = var77.relatives();
                    Relative var134 = Relative.Y;
                    if (var97.contains(var134)) {
                        double var111 = var157.y;
                        double var147 = var87.y;
                        var113 = (var111 + var147);
                    } else {
                        var113 = var87.y;
                    }
                    double var22 = var113;
                    Set var101 = var77.relatives();
                    Relative var135 = Relative.Z;
                    if (var101.contains(var135)) {
                        double var106 = var157.z;
                        double var144 = var87.z;
                        var108 = (var106 + var144);
                        if (!var35.hasPosition()) {
                            return false;
                        }
                        this.applyStep();
                        if (this.fieldVal3 == null) {
                            return false;
                        }
                        VecDeltaCodec var46 = this.fieldVal3;
                        long var121 = ((long) var35.getXa());
                        long var150 = ((long) var35.getYa());
                        long var153 = ((long) var35.getZa());
                        var12 = var46.decode(var121, var150, var153);
                    } else {
                        var108 = var87.z;
                        double var24 = var108;
                        Vec3 var104 = new Vec3(var20, var22, var24);
                        var12 = var104;
                    }
                    if (var12 == null) {
                        return false;
                    }
                    Vec3 var52 = arg1.player.position();
                    Vec3 var125 = this.fieldVal2.position();
                    double var54 = var52.distanceToSqr(var125);
                    Vec3 var126 = var12;
                    if (Double.compare(var52.distanceToSqr(var126), var54) > 0) {
                        var62 = 1;
                    } else {
                        var62 = 0;
                    }
                    return var62;
                }
            }
        }
    }

    private void applyStep(Packet arg0, Minecraft arg1) {
        if (!this.isEligible(arg1)) {
            return;
        }
        if (arg1.level == null) {
            return;
        }
        if ((arg0 instanceof ClientboundMoveEntityPacket)) {
            ClientboundMoveEntityPacket var27 = ((ClientboundMoveEntityPacket) arg0);
            if (!(this.isEligible(var27, arg1))) {
                return;
            }
        }
        if ((arg0 instanceof ClientboundEntityPositionSyncPacket)) {
            ClientboundEntityPositionSyncPacket var15 = ((ClientboundEntityPositionSyncPacket) arg0);
            int var17 = var15.id();
            int var33 = this.fieldVal2.getId();
            if (var17 != var33) {
                return;
            }
            Vec3 var36 = var15.values().position();
            this.applyStep(var36);
            return;
        }
        if ((arg0 instanceof ClientboundTeleportEntityPacket)) {
            ClientboundTeleportEntityPacket var22 = ((ClientboundTeleportEntityPacket) arg0);
            int var24 = var22.id();
            int var39 = this.fieldVal2.getId();
            if (var24 != var39) {
                return;
            }
            this.applyStep(var22);
        }
        return;
        this.applyStep(var27);
    }

    private void applyStep(ClientboundMoveEntityPacket arg0) {
        this.applyStep();
        if (this.fieldVal3 == null) {
            return;
        }
        if (!arg0.hasPosition()) {
            return;
        }
        VecDeltaCodec var12 = this.fieldVal3;
        long var18 = ((long) arg0.getXa());
        long var23 = ((long) arg0.getYa());
        long var26 = ((long) arg0.getZa());
        Vec3 var13 = var12.decode(var18, var23, var26);
        this.positionVec = var13;
        this.fieldVal3.setBase(var13);
    }

    private void applyStep(ClientboundTeleportEntityPacket arg0) {
        double var41 = 0.0;
        double var49 = 0.0;
        double var52 = 0.0;
        this.applyStep();
        if (this.positionVec == null) {
            return;
        }
        Vec3 var22 = arg0.change().position();
        Set var24 = arg0.relatives();
        Relative var3 = Relative.X;
        if (var24.contains(var3)) {
            double var28 = this.positionVec.x;
            double var57 = var22.x;
            var52 = (var28 + var57);
        } else {
            var52 = var22.x;
        }
        double var12 = var52;
        Set var31 = arg0.relatives();
        Relative var54 = Relative.Y;
        if (var31.contains(var54)) {
            double var47 = this.positionVec.y;
            double var63 = var22.y;
            var49 = (var47 + var63);
        } else {
            var49 = var22.y;
        }
        double var14 = var49;
        Set var35 = arg0.relatives();
        Relative var55 = Relative.Z;
        if (var35.contains(var55)) {
            double var39 = this.positionVec.z;
            double var60 = var22.z;
            var41 = (var39 + var60);
        } else {
            var41 = var22.z;
        }
        double var16 = var41;
        Vec3 var56 = new Vec3(var12, var14, var16);
        this.applyStep(var56);
    }

    private void applyStep() {
        if (this.positionVec != null) {
            if (this.fieldVal3 != null) {
                return;
            }
        }
        if (this.fieldVal2 == null) {
            return;
        }
        Vec3 var11 = this.fieldVal2.getPositionCodec().getBase();
        this.applyStep(var11);
    }

    private void applyStep(Vec3 arg0) {
        this.positionVec = arg0;
        VecDeltaCodec var7 = new VecDeltaCodec();
        this.fieldVal3 = var7;
        this.fieldVal3.setBase(arg0);
    }

    private AABB evaluate() {
        if (this.positionVec != null) {
            if (this.fieldVal2 == null) {
            }
        }
        return null;
        Vec3 var8 = this.positionVec;
        Vec3 var16 = this.fieldVal2.position();
        Vec3 var9 = var8.subtract(var16);
        return this.fieldVal2.getBoundingBox().move(var9);
    }

    private boolean isEligible(ClientboundMoveEntityPacket arg0, Minecraft arg1) {
        int var13 = 0;
        if (arg1.level == null) {
            return false;
        }
        ClientLevel var14 = arg1.level;
        Entity var9 = arg0.getEntity(var14);
        if (var9 != null) {
            int var12 = var9.getId();
            int var17 = this.fieldVal2.getId();
            if (var12 == var17) {
                var13 = 1;
            }
        } else {
            var13 = 0;
        }
        return var13;
    }

    private void applyStep1() {
        this.positionVec = null;
        this.fieldVal3 = null;
        this.floatValue = 0.0F;
    }

    private boolean isEligible(Minecraft arg0) {
        int var22 = 0;
        if (this.fieldVal2 != null) {
            if (arg0.player == null) {
            }
        }
        return false;
        if (!this.fieldVal2.isAlive()) {
            return false;
        }
        Integer var27 = 6;
        int var16 = ((Integer) this.getSetting("Range", var27));
        LocalPlayer var18 = arg0.player;
        Entity var26 = this.fieldVal2;
        double var19 = var18.distanceToSqr(var26);
        double var30 = ((double) (var16 * var16));
        if (Double.compare(var19, var30) <= 0) {
            var22 = 1;
        } else {
            var22 = 0;
        }
        return var22;
    }

    private static int computeColorOrIndex(String arg0, int arg1, boolean arg2) {
        int var7 = ThemeColors.resolveRgb(arg0, 3833804, arg2);
        int var10 = ((arg1 & 255) << 24);
        int var15 = (var7 & 16777215);
        return (var10 | var15);
    }

    static {
        targetEntity = null;
    }

}
