package need.clarity.modules.utility;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import need.clarity.PerPacketEvents;
import need.clarity.PerPacketSendEvents;
import need.clarity.WorldRenderEvent;
import need.clarity.api.Bus;
import need.clarity.api.IOvertick;
import need.clarity.event.EventPacketReceive;
import need.clarity.event.EventPacketSend;
import need.clarity.keybind.KeybindHandler;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundPingPacket;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.protocol.game.ServerboundAttackPacket;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.PositionMoveRotation;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class Overtick extends ModuleBase {
    private static final int CONST_INT = 0;
    private static final int CONST_INT1 = 1;
    private static final int CONST_INT2 = 2;
    private static final int CONST_INT3 = 3;
    private static final int CONST_INT4 = 4;
    private static final int CONST_INT5 = 5;
    private static final int CONST_INT6 = 6;
    private static final int CONST_INT7 = 7;
    private static final int CONST_INT8 = 8;
    private static final int CONST_INT9 = 1;
    private static final int CONST_INT10 = 2;
    private static final int CONST_INT11 = 4;
    private static final int CONST_INT12 = 8;
    private static final int CONST_INT13 = 16;
    private static final int CONST_INT14 = 32;
    private static final int CONST_INT15 = 64;
    private static final int CONST_INT16 = 128;
    private static final int CONST_INT17 = 256;
    private static final int CONST_INT18 = 512;
    private static final int CONST_INT19 = 1024;
    private static final int CONST_INT20 = 2048;
    private static final int CONST_INT21 = 4096;
    private static final int CONST_INT22 = 8192;
    private static final int CONST_INT23 = 16384;
    private static final Map entriesMap;
    private final List entriesList;
    private static final int CONST_INT24 = 6;
    private static final float floatValue = 6.0F;
    private final Object LOCK;
    private final Map entriesMap1;
    private final Map entriesMap2;
    private final Map entriesMap3;
    private final Set entriesSet;
    private volatile Set entriesSet1;
    private volatile boolean enabledState;
    private volatile boolean enabledState1;
    private volatile boolean enabledState2;
    private boolean enabledState3;
    private long timestampMs;
    private long timestampMs1;
    private int counterValue;
    private boolean enabledState4;
    private int counterValue1;
    private int counterValue2;
    private boolean enabledState5;
    private int counterValue3;
    private boolean enabledState6;
    private int counterValue4;
    private boolean enabledState7;
    private int counterValue5;
    private boolean enabledState8;
    private boolean enabledState9;
    private String labelText;
    private long timestampMs2;
    private ClientPacketListener fieldVal;
    private ResourceKey fieldVal1;
    private volatile int counterValue6;
    private volatile int counterValue7;
    private volatile String labelText1;
    private volatile int counterValue8;
    private final WorldRenderEvent.Listener fieldVal2;
    private static volatile Overtick INSTANCE;

    private static Class evaluate(String arg0) {
        try {
            Class var3 = Class.forName(arg0);
        } catch (Throwable ignored) {
        }
        return var3;
    }

    private static void applyStep(Class arg0, int arg1, int arg2) {
        if (arg0 != null) {
            Map var8 = entriesMap;
            int var10 = (arg1 & 255);
            int var14 = (arg2 << 8);
            Integer var12 = (var10 | var14);
            Object var9 = var8.put(arg0, var12);
        }
    }

    public Overtick() {
        super("Overtick", "utility");
        ArrayList var46 = new ArrayList();
        this.entriesList = var46;
        Object var47 = new Object();
        this.LOCK = var47;
        HashMap var48 = new HashMap();
        this.entriesMap1 = var48;
        HashMap var49 = new HashMap();
        this.entriesMap2 = var49;
        HashMap var50 = new HashMap();
        this.entriesMap3 = var50;
        ConcurrentHashMap.KeySetView var51 = ConcurrentHashMap.newKeySet();
        this.entriesSet = var51;
        IdentityHashMap var52 = new IdentityHashMap();
        Set var53 = Collections.newSetFromMap(var52);
        this.entriesSet1 = var53;
        this.enabledState = false;
        this.enabledState1 = false;
        this.enabledState2 = false;
        this.enabledState3 = false;
        this.timestampMs = 0L;
        this.timestampMs1 = 0L;
        this.counterValue = 0;
        this.enabledState4 = false;
        this.counterValue1 = -1;
        this.counterValue2 = 0;
        this.enabledState5 = false;
        this.counterValue3 = 0;
        this.enabledState6 = false;
        this.counterValue4 = 0;
        this.enabledState7 = false;
        this.counterValue5 = 0;
        this.enabledState8 = false;
        this.enabledState9 = false;
        this.labelText = "";
        this.timestampMs2 = 0L;
        this.fieldVal = null;
        this.fieldVal1 = null;
        this.counterValue6 = 0;
        this.counterValue7 = 0;
        this.labelText1 = "Flow";
        this.counterValue8 = -1;
        WorldRenderEvent.Listener var84 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal2 = var84;
        INSTANCE = this;
        OvertickCapabilityProvider var85 = new OvertickCapabilityProvider();
        Bus.register(IOvertick.class, var85);
        PerPacketEvents.register((p0) -> this.onPacketReceive(p0));
        PerPacketSendEvents.register((p0) -> this.onPacketSend(p0));
        PerAttackEvents.register((p0) -> this.onStartAttack(p0));
    }

    private String getReleaseMode() {
        return ((String) this.getSetting("Release Mode", "Flow"));
    }

    private int getFlowSpeed() {
        Integer var7 = 3;
        return ((Integer) this.getSetting("Flow Speed", var7));
    }

    private int getReleaseSpeed() {
        Integer var7 = 2;
        return ((Integer) this.getSetting("Release Speed", var7));
    }

    private int getInterval() {
        Integer var7 = 5;
        return ((Integer) this.getSetting("Interval", var7));
    }

    private int getDelay() {
        Integer var7 = 10;
        return ((Integer) this.getSetting("Delay", var7));
    }

    private boolean isDisableOnFlag() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Disable On Flag", var7));
    }

    private boolean isDisableOnDeath() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Disable On Death", var7));
    }

    private boolean isDisableOnConnect() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Disable On Connect", var7));
    }

    private boolean isDisableOnDimensionChange() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Disable On Dimension Change", var7));
    }

    private int getQueueTimeout() {
        Integer var7 = 29;
        return ((Integer) this.getSetting("Queue Timeout", var7));
    }

    private boolean isFlushAllOnTimeout() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Flush All On Timeout", var7));
    }

    private boolean isSafeReleaseOnTimeout() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Safe Release On Timeout", var7));
    }

    private boolean isGhostEntities() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Ghost Entities", var7));
    }

    private boolean isGhostESP() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Ghost ESP", var7));
    }

    private int getGhostAlpha() {
        Integer var7 = 60;
        return ((Integer) this.getSetting("Ghost Alpha", var7));
    }

    private float getGhostOpacity() {
        Float var7 = 0.6F;
        return ((Float) this.getSetting("Ghost Opacity", var7));
    }

    private String getGhostColor() {
        return ((String) this.getSetting("Ghost Color", "#64b4ff"));
    }

    private boolean isAdvancedPacketControl() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Advanced Packet Control", var7));
    }

    private int getReleaseKey() {
        String var6 = ((String) this.getSetting("Release Key", "NONE"));
        if (var6 != null) {
            if (!var6.isEmpty()) {
                if (var6.equals("NONE")) {
                }
            }
            return -1;
            return KeybindHandler.glfwKeyCode(var6);
        }
    }

    private void applyStep() {
        int var4 = 0;
        int var5 = 0;
        int var171 = 0;
        var4 = 0;
        if (this.isAdvancedPacketControl()) {
            Boolean var172 = true;
            if (((Boolean) this.getSetting("Queue Transactions", var172))) {
                var4 = (var4 | 1);
            }
            Boolean var174 = true;
            if (((Boolean) this.getSetting("Queue Entity Movement", var174))) {
                var4 = (var4 | 2);
            }
            Boolean var176 = true;
            if (((Boolean) this.getSetting("Queue Entity Lifecycle", var176))) {
                var4 = (var4 | 4);
            }
            Boolean var178 = true;
            if (((Boolean) this.getSetting("Queue Entity State", var178))) {
                var4 = (var4 | 8);
            }
            Boolean var180 = true;
            if (((Boolean) this.getSetting("Queue Velocity", var180))) {
                var4 = (var4 | 16);
            }
            Boolean var182 = true;
            if (((Boolean) this.getSetting("Queue Blocks", var182))) {
                var4 = (var4 | 32);
            }
            Boolean var184 = true;
            if (((Boolean) this.getSetting("Queue Game State", var184))) {
                var4 = (var4 | 64);
            }
            Boolean var186 = true;
            if (((Boolean) this.getSetting("Queue Inventory", var186))) {
                var4 = (var4 | 128);
            }
            Boolean var188 = true;
            if (((Boolean) this.getSetting("Queue Entity Events", var188))) {
                var4 = (var4 | 256);
            }
            Boolean var190 = true;
            if (((Boolean) this.getSetting("Queue Health", var190))) {
                var4 = (var4 | 512);
            }
            Boolean var192 = true;
            if (((Boolean) this.getSetting("Queue Experience", var192))) {
                var4 = (var4 | 1024);
            }
            Boolean var194 = true;
            if (((Boolean) this.getSetting("Queue Cooldowns", var194))) {
                var4 = (var4 | 2048);
            }
            Boolean var196 = true;
            if (((Boolean) this.getSetting("Queue Held Slot", var196))) {
                var4 = (var4 | 4096);
            }
            Boolean var198 = false;
            if (((Boolean) this.getSetting("Queue Camera", var198))) {
                var4 = (var4 | 8192);
            }
            Boolean var200 = false;
            if (((Boolean) this.getSetting("Queue Combat Events", var200))) {
                var4 = (var4 | 16384);
            }
        } else {
            var4 = 255;
        }
        int var156 = var4;
        this.counterValue6 = var156;
        var5 = 0;
        if (this.isAdvancedPacketControl()) {
            Boolean var202 = false;
            if (((Boolean) this.getSetting("Never Queue Inventory", var202))) {
                var5 = (var5 | 128);
            }
            Boolean var204 = false;
            if (((Boolean) this.getSetting("Never Queue Velocity", var204))) {
                var5 = (var5 | 16);
            }
            Boolean var206 = false;
            if (((Boolean) this.getSetting("Never Queue Blocks", var206))) {
                var5 = (var5 | 32);
            }
            int var163 = var5;
            this.counterValue7 = var163;
            String var165 = this.getReleaseMode();
            this.labelText1 = var165;
            try {
                if (Minecraft.getInstance().player != null) {
                    var171 = Minecraft.getInstance().player.getId();
                } else {
                    var171 = -1;
                }
                this.counterValue8 = var171;
            } catch (Exception ignored) {
            }
            this.counterValue8 = -1;
            return;
        }
    }

    public void onEnable() {
        ClientPacketListener var53 = null;
        ResourceKey var60 = null;
        List var5 = this.entriesList;
        try {
            this.entriesList.clear();
        } catch (Throwable ignored) {
        }
        List var33 = var5;
        throw var33;
        this.applyStep14();
        this.enabledState1 = false;
        this.enabledState = false;
        this.enabledState3 = false;
        this.timestampMs = 0L;
        this.timestampMs1 = 0L;
        this.counterValue = 0;
        this.enabledState4 = false;
        this.counterValue1 = -1;
        this.enabledState8 = false;
        this.enabledState6 = false;
        this.enabledState5 = false;
        this.counterValue5 = 0;
        this.counterValue4 = 0;
        this.counterValue3 = 0;
        this.enabledState7 = false;
        this.enabledState9 = false;
        this.labelText = "";
        this.timestampMs2 = 0L;
        Minecraft var23 = Minecraft.getInstance();
        try {
            if (var23 != null) {
                var53 = var23.getConnection();
            } else {
                var53 = null;
            }
            this.fieldVal = var53;
        } catch (Exception ignored) {
        }
        List var31 = var5;
        this.fieldVal = null;
        try {
            if (var23 != null) {
                if (var23.level != null) {
                    var60 = var23.level.dimension();
                } else {
                    var60 = null;
                }
                this.fieldVal1 = var60;
            }
        } catch (Exception ignored) {
        }
        List var29 = var5;
        this.fieldVal1 = null;
        this.applyStep();
        WorldRenderEvent.register(this.fieldVal2);
    }

    public void onDisable() {
        if (this.enabledState1) {
            return;
        }
        this.enabledState1 = true;
        WorldRenderEvent.unregister(this.fieldVal2);
        try {
            Minecraft var8 = Minecraft.getInstance();
            if (var8.player != null) {
                var8.player.noPhysics = false;
            }
        } catch (Exception ignored) {
        }
        this.applyStep9();
        this.applyStep14();
        this.enabledState = false;
        this.enabledState3 = false;
        this.enabledState8 = false;
        this.counterValue = 0;
        this.enabledState4 = false;
        this.enabledState1 = false;
    }

    public void onTick() {
        int var262 = 0;
        int var10 = 0;
        QueuedPacketEntry var284 = null;
        int var285 = 0;
        int var297 = 0;
        Minecraft var1 = Minecraft.getInstance();
        LocalPlayer var22 = var1.player;
        if (var22 != null) {
            ClientLevel var24 = var1.level;
            if (var24 != null) {
                LocalPlayer var26 = var1.player;
                ClientPacketListener var27 = var26.connection;
                if (var27 == null) {
                }
            }
            boolean var282 = this.isEnabled();
            if (var282) {
                this.setEnabled(false);
            }
            return;
            try {
                this.applyStep();
                boolean var30 = this.isDisableOnDeath();
                if (var30) {
                    try {
                        LocalPlayer var32 = var1.player;
                        boolean var33 = var32.isDeadOrDying();
                        if (var33) {
                            this.setEnabled(false);
                        }
                    } catch (Exception ignored) {
                    }
                    return;
                    try {
                        Object var381 = var280;
                    } catch (Exception ignored) {
                    }
                }
                try {
                    ClientPacketListener var35 = var1.getConnection();
                    ClientPacketListener var286 = this.fieldVal;
                    if (var35 != var286) {
                        boolean var38 = this.isDisableOnConnect();
                        if (var38) {
                            ClientPacketListener var40 = this.fieldVal;
                            if (var40 != null) {
                                this.setEnabled(false);
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
                return;
                try {
                    try {
                        this.fieldVal = var35;
                    } catch (Exception ignored) {
                    }
                } catch (Exception ignored) {
                }
                Object var375 = var42;
                try {
                    ClientLevel var44 = var1.level;
                    ResourceKey var45 = var44.dimension();
                    ResourceKey var376 = var45;
                    ResourceKey var289 = this.fieldVal1;
                    boolean var47 = Objects.equals(var376, var289);
                    if (!var47) {
                        boolean var49 = this.isDisableOnDimensionChange();
                        if (var49) {
                            ResourceKey var51 = this.fieldVal1;
                            if (var51 != null) {
                                if (var376 != null) {
                                    this.setEnabled(false);
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
                return;
                try {
                    try {
                        this.fieldVal1 = var376;
                    } catch (Exception ignored) {
                    }
                } catch (Exception ignored) {
                }
                Object var377 = var54;
                this.applyStep(var1);
                List var57 = this.entriesList;
                List var378 = var57;
                try {
                } catch (Throwable ignored) {
                }
                List var59 = var378;
                throw var59;
                try {
                    List var66 = this.entriesList;
                    List var382 = var66;
                    try {
                        List var68 = this.entriesList;
                        boolean var69 = var68.isEmpty();
                        if (!var69) {
                            var285 = 1;
                        } else {
                            var285 = 0;
                        }
                        int var380 = var285;
                    } catch (Throwable ignored) {
                    }
                    List var274 = var382;
                    List var390 = var274;
                    throw var390;
                } catch (Exception ignored) {
                }
                try {
                    if (var380 != 0) {
                        boolean var296 = this.isGhostEntities();
                        if (var296) {
                            var297 = 1;
                        } else {
                            var297 = 0;
                        }
                        this.enabledState2 = var297;
                        boolean var73 = this.enabledState2;
                        if (var73) {
                            this.applyStep12();
                        }
                        String var299 = this.labelText1;
                        boolean var76 = "Jetpack".equals(var299);
                        if (var76) {
                            int var78 = this.getReleaseKey();
                            if (var78 != -1) {
                                boolean var302 = this.enabledState9;
                                this.enabledState7 = var302;
                            } else {
                                this.enabledState7 = false;
                            }
                            boolean var81 = this.enabledState8;
                            if (var81) {
                                String var304 = this.labelText1;
                                boolean var83 = "Steady".equals(var304);
                                if (var83) {
                                    int var306 = this.getReleaseSpeed();
                                    int var85 = Math.max(1, var306);
                                    int var383 = var85;
                                    ArrayList var86 = new ArrayList();
                                    List var88 = this.entriesList;
                                    try {
                                        var10 = 0;
                                        while (true) {
                                            int var90 = var10;
                                            if (var90 >= var383) break;
                                            List var92 = this.entriesList;
                                            boolean var93 = var92.isEmpty();
                                            if (var93) break;
                                            List var309 = this.entriesList;
                                            Object var310 = var309.remove(0);
                                            QueuedPacketEntry var311 = ((QueuedPacketEntry) var310);
                                            boolean var95 = var86.add(var311);
                                            var10 = (var10 + 1);
                                        }
                                    } catch (Throwable ignored) {
                                    }
                                    List var96 = var88;
                                    throw var96;
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
                try {
                    boolean var101 = var86.isEmpty();
                    if (!var101) {
                        this.enabledState = true;
                        Iterator var104 = var86.iterator();
                        Iterator var391 = var104;
                        while (true) {
                            boolean var106 = var391.hasNext();
                            if (!var106) break;
                            Object var270 = var391.next();
                            QueuedPacketEntry var271 = ((QueuedPacketEntry) var270);
                            QueuedPacketEntry var397 = var271;
                            Packet var341 = var397.packet;
                            this.applyStep5(var341);
                        }
                        this.enabledState = false;
                        List var109 = this.entriesList;
                        List var392 = var109;
                        try {
                            List var111 = this.entriesList;
                            boolean var112 = var111.isEmpty();
                            if (var112) {
                                this.enabledState8 = false;
                                this.applyStep13();
                            }
                        } catch (Throwable ignored) {
                        }
                        List var266 = var392;
                        List var400 = var266;
                        throw var400;
                    }
                } catch (Exception ignored) {
                }
                try {
                    boolean var117 = this.enabledState3;
                    if (var117) {
                        List var119 = this.entriesList;
                        List var386 = var119;
                        try {
                            List var121 = this.entriesList;
                            boolean var122 = var121.isEmpty();
                            int var384 = var122;
                        } catch (Throwable ignored) {
                        }
                        List var263 = var386;
                        List var403 = var263;
                        throw var403;
                    }
                } catch (Exception ignored) {
                }
                try {
                    if (var384 != 0) {
                        this.enabledState3 = false;
                        this.counterValue = 0;
                        this.applyStep13();
                    } else {
                        int var205 = this.counterValue;
                        if (var205 > 0) {
                            var262 = this.counterValue;
                        } else {
                            int var328 = this.getFlowSpeed();
                            var262 = Math.max(1, var328);
                        }
                        int var389 = var262;
                        long var207 = System.currentTimeMillis();
                        long var393 = var207;
                        Overtick var363 = this;
                        long var364 = var363.timestampMs;
                        long var211 = (var393 - var364);
                        int var366 = var389;
                        long var367 = ((long) var366);
                        long var213 = (var211 * var367);
                        long var398 = var213;
                        ArrayList var215 = new ArrayList();
                        ArrayList var402 = var215;
                        List var217 = this.entriesList;
                        List var406 = var217;
                        while (true) {
                            try {
                                List var219 = this.entriesList;
                                boolean var220 = var219.isEmpty();
                                if (var220) break;
                                List var222 = this.entriesList;
                                Object var223 = var222.get(0);
                                QueuedPacketEntry var224 = ((QueuedPacketEntry) var223);
                                QueuedPacketEntry var409 = var224;
                                long var226 = var409.timestampMs;
                                Overtick var369 = this;
                                long var370 = var369.timestampMs1;
                                long var228 = (var226 - var370);
                                long var372 = var398;
                                int var230 = Long.compare(var228, var372);
                                if (var230 > 0) {
                                } else {
                                    List var331 = this.entriesList;
                                    int var374 = 0;
                                    Object var332 = var331.remove(var374);
                                    QueuedPacketEntry var333 = ((QueuedPacketEntry) var332);
                                    boolean var232 = var402.add(var333);
                                }
                            } catch (Throwable ignored) {
                            }
                            List var233 = var406;
                            throw var233;
                        }
                    }
                } catch (Exception ignored) {
                }
                try {
                    boolean var238 = var402.isEmpty();
                    if (!var238) {
                        this.enabledState = true;
                        Iterator var241 = var402.iterator();
                        Iterator var407 = var241;
                        while (true) {
                            boolean var243 = var407.hasNext();
                            if (!var243) break;
                            Object var258 = var407.next();
                            QueuedPacketEntry var259 = ((QueuedPacketEntry) var258);
                            QueuedPacketEntry var410 = var259;
                            Packet var339 = var410.packet;
                            this.applyStep5(var339);
                        }
                        this.enabledState = false;
                        List var246 = this.entriesList;
                        List var408 = var246;
                        try {
                            List var248 = this.entriesList;
                            boolean var249 = var248.isEmpty();
                            if (var249) {
                                this.enabledState3 = false;
                                this.counterValue = 0;
                                this.applyStep13();
                            }
                        } catch (Throwable ignored) {
                        }
                        List var254 = var408;
                        throw var254;
                    }
                } catch (Exception ignored) {
                }
                try {
                    int var129 = this.getQueueTimeout();
                    int var385 = var129;
                    if (var385 > 0) {
                        if (var380 != 0) {
                            long var132 = System.currentTimeMillis();
                            long var387 = var132;
                            long var135 = ((long) var385);
                            long var137 = (var135 * 1000L);
                            long var395 = var137;
                            List var140 = this.entriesList;
                            try {
                                List var142 = this.entriesList;
                                boolean var143 = var142.isEmpty();
                                if (var143) {
                                    var284 = null;
                                } else {
                                    List var145 = this.entriesList;
                                    Object var146 = var145.get(0);
                                    var284 = ((QueuedPacketEntry) var146);
                                }
                                QueuedPacketEntry var12 = var284;
                            } catch (Throwable ignored) {
                            }
                            List var201 = var140;
                            throw var201;
                        }
                    }
                } catch (Exception ignored) {
                }
                try {
                    if (var12 != null) {
                        long var350 = var12.timestampMs;
                        long var151 = (var387 - var350);
                        int var153 = Long.compare(var151, var395);
                        if (var153 >= 0) {
                            boolean var155 = this.isSafeReleaseOnTimeout();
                            if (var155) {
                                boolean var199 = this.enabledState3;
                                if (!var199) {
                                    this.applyStep(1);
                                }
                            } else {
                                boolean var157 = this.isFlushAllOnTimeout();
                                if (var157) {
                                    this.applyStep6();
                                } else {
                                    ArrayList var158 = new ArrayList();
                                    ArrayList var401 = var158;
                                    List var160 = this.entriesList;
                                    while (true) {
                                        try {
                                            List var162 = this.entriesList;
                                            boolean var163 = var162.isEmpty();
                                            if (var163) break;
                                            List var355 = this.entriesList;
                                            Object var356 = var355.get(0);
                                            QueuedPacketEntry var357 = ((QueuedPacketEntry) var356);
                                            long var358 = var357.timestampMs;
                                            long var166 = (var387 - var358);
                                            long var360 = var395;
                                            int var168 = Long.compare(var166, var360);
                                            if (var168 < 0) break;
                                            List var323 = this.entriesList;
                                            int var362 = 0;
                                            Object var324 = var323.remove(var362);
                                            QueuedPacketEntry var325 = ((QueuedPacketEntry) var324);
                                            boolean var196 = var401.add(var325);
                                        } catch (Throwable ignored) {
                                        }
                                    }
                                    List var192 = var160;
                                    throw var192;
                                }
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
                try {
                    boolean var171 = var401.isEmpty();
                    if (!var171) {
                        this.enabledState = true;
                        Iterator var174 = var401.iterator();
                        Iterator var404 = var174;
                        while (true) {
                            boolean var176 = var404.hasNext();
                            if (!var176) break;
                            Object var178 = var404.next();
                            QueuedPacketEntry var179 = ((QueuedPacketEntry) var178);
                            Packet var320 = var179.packet;
                            this.applyStep5(var320);
                        }
                        this.enabledState = false;
                        List var183 = this.entriesList;
                        List var405 = var183;
                        try {
                            List var185 = this.entriesList;
                            boolean var186 = var185.isEmpty();
                            if (var186) {
                                this.applyStep13();
                            }
                        } catch (Throwable ignored) {
                        }
                        List var189 = var405;
                        throw var189;
                    }
                } catch (Exception ignored) {
                }
                List var62 = var405;
                List var379 = var62;
                this.enabledState3 = false;
                this.counterValue = 0;
                return;
            } catch (Exception ignored) {
            }
        }
    }

    private void applyStep(Minecraft arg0) {
        int var96 = 0;
        int var10 = 0;
        int var20 = 0;
        int var123 = 0;
        int var27 = 0;
        int var124 = 0;
        int var11 = this.getReleaseKey();
        if (var11 > 0) {
            if (var11 == -1) {
            }
        }
        this.enabledState9 = false;
        return;
        try {
            if (GLFW.glfwGetKey(arg0.getWindow().handle(), var11) == 1) {
                var20 = 1;
            } else {
                var20 = 0;
            }
            var124 = var20;
        } catch (Exception ignored) {
        }
        var124 = 0;
        if (var124 != 0) {
            if (!this.enabledState9) {
                var96 = 1;
            } else {
            }
        }
        var96 = 0;
        int var6 = var96;
        if (var124 == 0) {
            if (this.enabledState9) {
                var27 = 1;
            } else {
                var27 = 0;
            }
            int var7 = var27;
            int var101 = var124;
            this.enabledState9 = var101;
            String var30 = this.labelText1;
            String var103 = this.labelText;
            if (!var30.equals(var103)) {
                this.applyStep2();
                this.labelText = var30;
            }
            if (var6 != 0) {
                var10 = -1;
                switch (var30.hashCode()) {
                    case -1808513998 -> {}
                    case -42098670 -> {}
                    case 2192718 -> {}
                    case 2606525 -> {}
                    case 1380408214 -> {}
                    case 1587773975 -> {}
                    default -> {}
                }
                if (var30.equals("Flow")) {
                    var10 = 0;
                    if (var30.equals("Steady")) {
                        var10 = 1;
                        if (var30.equals("One by One")) {
                            var10 = 4;
                            if (var30.equals("Jetpack")) {
                                var10 = 2;
                                if (var30.equals("Tick")) {
                                    var10 = 3;
                                    if (var30.equals("Up/Down")) {
                                        var10 = 5;
                                    }
                                    switch (var10) {
                                        case 0 -> {}
                                        case 1 -> {}
                                        case 2 -> {}
                                        case 3 -> {}
                                        case 4 -> {}
                                        case 5 -> {}
                                        default -> {}
                                    }
                                    if (!this.isEligible()) {
                                    } else {
                                        if (!this.enabledState6) {
                                            this.applyStep10();
                                            if (this.isEligible1()) {
                                                this.enabledState6 = true;
                                                this.counterValue4 = 0;
                                            } else {
                                                this.applyStep6();
                                                this.applyStep1();
                                                if (!this.enabledState5) {
                                                    var123 = 1;
                                                } else {
                                                    var123 = 0;
                                                }
                                                this.enabledState5 = var123;
                                                this.counterValue3 = 0;
                                                if (!this.isEligible()) {
                                                } else {
                                                    if (!this.isEligible1()) {
                                                        this.applyStep6();
                                                    } else {
                                                        boolean var74 = this.isEligible3();
                                                        if (!this.isEligible1()) {
                                                            this.applyStep6();
                                                            this.enabledState8 = true;
                                                            this.applyStep6();
                                                            if (this.isEligible()) {
                                                                this.applyStep6();
                                                            }
                                                            if (var7 != 0) {
                                                                if ("Flow".equals(var30)) {
                                                                    this.enabledState3 = false;
                                                                    this.counterValue = 0;
                                                                    this.enabledState4 = false;
                                                                }
                                                                if ("Steady".equals(var30)) {
                                                                    this.enabledState8 = false;
                                                                }
                                                                return;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isEligible() {
        long var1 = System.currentTimeMillis();
        long var16 = this.timestampMs2;
        if (Long.compare((var1 - var16), 250L) < 0) {
            return false;
        }
        this.timestampMs2 = var1;
        return true;
    }

    private void applyStep1() {
        this.applyStep(0);
    }

    private void applyStep(int arg0) {
        QueuedPacketEntry var25 = null;
        List var7 = this.entriesList;
        try {
            if (this.entriesList.isEmpty()) {
                var25 = null;
            } else {
                var25 = ((QueuedPacketEntry) this.entriesList.get(0));
            }
            QueuedPacketEntry var4 = var25;
        } catch (Throwable ignored) {
        }
        if (var4 == null) {
            this.enabledState3 = false;
            this.counterValue = 0;
            return;
        }
        this.enabledState3 = true;
        int var28 = Math.max(0, arg0);
        this.counterValue = var28;
        long var29 = System.currentTimeMillis();
        this.timestampMs = var29;
        long var32 = var4.timestampMs;
        this.timestampMs1 = var32;
        return;
        List var19 = var7;
        throw var19;
    }

    private void applyStep2() {
        this.enabledState5 = false;
        this.counterValue3 = 0;
        this.counterValue4 = 0;
        this.counterValue5 = 0;
        this.enabledState6 = false;
    }

    private Vec3 evaluate(int arg0) {
        Object var13 = this.LOCK;
        try {
            Map var15 = this.entriesMap1;
            Integer var31 = arg0;
            PlayerGhostSnapshot var17 = ((PlayerGhostSnapshot) var15.get(var31));
            if (var17 != null) {
                double var37 = var17.doubleValue;
                double var42 = var17.doubleValue1;
                double var47 = var17.doubleValue2;
                Vec3 var19 = new Vec3(var37, var42, var47);
            }
        } catch (Throwable ignored) {
        }
        return var19;
        try {
            Map var24 = this.entriesMap2;
            Integer var34 = arg0;
            double[] var26 = ((double[]) var24.get(var34));
            if (var26 != null) {
                double var40 = var26[0];
                double var45 = var26[1];
                double var50 = var26[2];
                Vec3 var29 = new Vec3(var40, var45, var50);
            }
        } catch (Throwable ignored) {
        }
        return var29;
        try {
        } catch (Throwable ignored) {
        }
        return null;
        Object var20 = var13;
        throw var20;
    }

    private void onPacketReceive(EventPacketReceive arg0) {
        int var10 = 0;
        int var199 = 0;
        int var250 = 0;
        int var251 = 0;
        Minecraft var2 = Minecraft.getInstance();
        if (var2.player != null) {
            if (var2.level == null) {
            }
        }
        return;
        if (this.isEnabled()) {
            if (!this.enabledState) {
                if (this.enabledState1) {
                }
            }
            return;
            try {
                Packet var28 = arg0.getPacket();
                if ((var28 instanceof ClientboundBundlePacket)) {
                    ClientboundBundlePacket var125 = ((ClientboundBundlePacket) var28);
                    var250 = 0;
                    var251 = 0;
                    Iterator var130 = var125.subPackets().iterator();
                    while (true) {
                        if (!var130.hasNext()) break;
                        Packet var135 = ((Packet) var130.next());
                        Map var136 = entriesMap;
                        Class var226 = var135.getClass();
                        Integer var138 = ((Integer) var136.get(var226));
                        if (var138 == null) {
                        }
                        if ((var138 & 255) == 7) {
                            var251 = 1;
                        } else {
                            int var188 = (var138 & 255);
                            int var191 = (var138 >>> 8);
                            if (var188 != 0) continue;
                            if (var191 == 0) continue;
                            int var245 = this.counterValue7;
                            int var195 = (var191 & var245);
                            if (var195 != 0) continue;
                            int var247 = this.counterValue6;
                            int var197 = (var191 & var247);
                            if (var197 == 0) continue;
                        }
                    }
                    var250 = 1;
                    if (var251 != 0) {
                        this.applyStep3();
                    }
                }
            } catch (Exception ignored) {
            }
            return;
            try {
                if (var250 == 0) {
                }
            } catch (Exception ignored) {
            }
            return;
            try {
                arg0.cancel();
                Iterator var150 = var125.subPackets().iterator();
                while (true) {
                    if (!var150.hasNext()) break;
                    Packet var155 = ((Packet) var150.next());
                    Map var156 = entriesMap;
                    Class var230 = var155.getClass();
                    Integer var158 = ((Integer) var156.get(var230));
                    if (var158 == null) {
                        this.applyStep5(var155);
                    }
                    int var163 = (var158 & 255);
                    int var166 = (var158 >>> 8);
                    if (var163 == 0) {
                        if (var166 != 0) {
                            int var235 = this.counterValue7;
                            if ((var166 & var235) == 0) {
                                int var237 = this.counterValue6;
                                if ((var166 & var237) != 0) {
                                    var199 = 1;
                                } else {
                                    var199 = 0;
                                }
                                if (var199 != 0) {
                                    if ((var155 instanceof ClientboundAddEntityPacket)) {
                                        ClientboundAddEntityPacket var178 = ((ClientboundAddEntityPacket) var155);
                                        this.applyStep(var178);
                                    } else {
                                        if ((var155 instanceof ClientboundRemoveEntitiesPacket)) {
                                            ClientboundRemoveEntitiesPacket var184 = ((ClientboundRemoveEntitiesPacket) var155);
                                            this.applyStep(var184);
                                        }
                                        this.applyStep(var155);
                                    }
                                }
                                this.applyStep5(var155);
                            }
                        }
                    }
                }
                return;
                try {
                    Map var31 = entriesMap;
                    Class var200 = var28.getClass();
                    Integer var33 = ((Integer) var31.get(var200));
                    if (var33 == null) {
                    }
                } catch (Exception ignored) {
                }
                return;
                try {
                    int var37 = (var33 & 255);
                    if (var37 != 0) {
                        switch (var37) {
                            case 1 -> {}
                            case 2 -> {}
                            case 3 -> {}
                            case 4 -> {}
                            case 5 -> {}
                            case 6 -> {}
                            case 7 -> {}
                            case 8 -> {}
                            default -> {}
                        }
                    }
                } catch (Exception ignored) {
                }
                return;
                try {
                    int var50 = (var33 >>> 8);
                    if (var50 == 0) {
                    }
                } catch (Exception ignored) {
                }
                return;
                try {
                    int var205 = this.counterValue7;
                    if ((var50 & var205) == 0) {
                        int var207 = this.counterValue6;
                        if ((var50 & var207) == 0) {
                        }
                    }
                    return;
                    try {
                        if ((var28 instanceof ClientboundSetEntityDataPacket)) {
                            ClientboundSetEntityDataPacket var59 = ((ClientboundSetEntityDataPacket) var28);
                            int var61 = var59.id();
                            int var209 = this.counterValue8;
                            if (var61 == var209) {
                                var10 = 0;
                                try {
                                    Iterator var65 = var59.packedItems().iterator();
                                    while (true) {
                                        boolean var67 = var65.hasNext();
                                        if (!var67) break;
                                        Object var69 = var65.next();
                                        SynchedEntityData.DataValue var70 = ((SynchedEntityData.DataValue) var69);
                                        int var72 = var70.id();
                                        if (var72 != 0) {
                                            if (var72 != 6) {
                                                if (var72 == 8) {
                                                }
                                            }
                                            var10 = 1;
                                            if (var72 != 15) continue;
                                            LocalPlayer var79 = var2.player;
                                            if (var79 == null) continue;
                                            Object var81 = var70.value();
                                            int var83 = (var81 instanceof Number);
                                            if (var83 == 0) continue;
                                            Number var85 = ((Number) var81);
                                            LocalPlayer var87 = var2.player;
                                            float var214 = var85;
                                            var87.setAbsorptionAmount(var214);
                                        }
                                    }
                                    var10 = 1;
                                    int var90 = var10;
                                    if (var90 == 0) {
                                        this.applyStep5(var28);
                                    }
                                } catch (Exception ignored) {
                                }
                            }
                        }
                    } catch (Exception ignored) {
                    }
                    return;
                    try {
                        int var92 = (var28 instanceof ClientboundEntityEventPacket);
                        if (var92 != 0) {
                            ClientboundEntityEventPacket var94 = ((ClientboundEntityEventPacket) var28);
                            ClientLevel var96 = var2.level;
                            if (var96 != null) {
                                ClientLevel var216 = var2.level;
                                Entity var98 = var94.getEntity(var216);
                                LocalPlayer var218 = var2.player;
                                if (var98 == var218) {
                                    int var101 = var94.getEventId();
                                    if (var101 != 9) {
                                    }
                                }
                            }
                        }
                    } catch (Exception ignored) {
                    }
                    return;
                    try {
                        int var103 = (var28 instanceof ClientboundAddEntityPacket);
                        if (var103 != 0) {
                            ClientboundAddEntityPacket var121 = ((ClientboundAddEntityPacket) var28);
                            this.applyStep(var121);
                        } else {
                            int var105 = (var28 instanceof ClientboundRemoveEntitiesPacket);
                            if (var105 != 0) {
                                ClientboundRemoveEntitiesPacket var107 = ((ClientboundRemoveEntitiesPacket) var28);
                                this.applyStep(var107);
                            }
                            arg0.cancel();
                            this.applyStep(var28);
                            try {
                                boolean var112 = this.isGhostEntities();
                                if (!var112) {
                                    boolean var114 = this.isBlockDiff();
                                    if (var114) {
                                    }
                                }
                                this.applyStep11(var28);
                            } catch (Exception ignored) {
                            }
                            int var118 = (var28 instanceof ClientboundPingPacket);
                            if (var118 != 0) {
                                this.applyStep4();
                            }
                        }
                    } catch (Exception ignored) {
                    }
                    try {
                        if (!this.isGhostEntities()) {
                            if (this.isBlockDiff()) {
                            }
                        }
                        this.applyStep11(var28);
                    } catch (Exception ignored) {
                    }
                    return;
                    try {
                        this.applyStep3();
                    } catch (Exception ignored) {
                    }
                    return;
                    try {
                        this.applyStep6();
                    } catch (Exception ignored) {
                    }
                    return;
                    return;
                    return;
                    return;
                    return;
                    return;
                } catch (Exception ignored) {
                }
            } catch (Exception ignored) {
            }
        }
    }

    private void applyStep(ClientboundAddEntityPacket arg0) {
        Set var14 = this.entriesSet;
        Integer var31 = arg0.getId();
        boolean var15 = var14.add(var31);
        if (!this.isGhostEntities()) {
            return;
        }
        Object var19 = this.LOCK;
        try {
            Map var21 = this.entriesMap1;
            Integer var34 = arg0.getId();
            double var40 = arg0.getX();
            double var53 = arg0.getY();
            double var55 = arg0.getZ();
            float var57 = arg0.getYRot();
            float var58 = arg0.getXRot();
            float var59 = arg0.getYHeadRot();
            PlayerGhostSnapshot var4 = new PlayerGhostSnapshot(var40, var53, var55, var57, var58, var59);
            Object var22 = var21.put(var34, var4);
            Map var24 = this.entriesMap2;
            Integer var37 = arg0.getId();
            double[] var39 = new double[3];
            double var45 = arg0.getX();
            var39[0] = var45;
            double var48 = arg0.getY();
            var39[1] = var48;
            double var51 = arg0.getZ();
            var39[2] = var51;
            Object var25 = var24.put(var37, var39);
        } catch (Throwable ignored) {
        }
        return;
        Object var27 = var19;
        throw var27;
    }

    private void applyStep(ClientboundRemoveEntitiesPacket arg0) {
        IntListIterator var9 = arg0.getEntityIds().iterator();
        while (true) {
            if (!var9.hasNext()) break;
            int var15 = ((Integer) var9.next());
            Set var17 = this.entriesSet;
            Integer var28 = var15;
            boolean var18 = var17.add(var28);
            Object var20 = this.LOCK;
            try {
                Map var22 = this.entriesMap1;
                Integer var30 = var15;
                Object var23 = var22.remove(var30);
            } catch (Throwable ignored) {
            }
        }
        return;
        Object var25 = var20;
        throw var25;
    }

    private void applyStep3() {
        Minecraft var1 = Minecraft.getInstance();
        try {
            this.applyStep6();
        } catch (Throwable ignored) {
        }
        if (!this.isDisableOnFlag()) {
            return;
        }
        try {
            if (var1 != null) {
                if (var1.isSameThread()) {
                }
            }
            this.setEnabled(false);
            Runnable var16 = () -> this.lambda$O();
            var1.execute(var16);
        } catch (Throwable ignored) {
        }
    }

    private void applyStep(Packet arg0) {
        List var6 = this.entriesList;
        try {
            List var8 = this.entriesList;
            QueuedPacketEntry var14 = new QueuedPacketEntry(arg0);
            boolean var9 = var8.add(var14);
        } catch (Throwable ignored) {
        }
        List var11 = var6;
        throw var11;
    }

    private void applyStep4() {
        String var6 = this.labelText1;
        String var45 = this.labelText;
        if (!var6.equals(var45)) {
            this.applyStep2();
            this.labelText = var6;
        }
        if ("Tick".equals(var6)) {
            if (this.enabledState5) {
                int var49 = (this.counterValue3 + 1);
                this.counterValue3 = var49;
                int var72 = this.getInterval();
                int var51 = Math.max(1, var72);
                if (var49 >= var51) {
                    this.counterValue3 = 0;
                    if (!this.isEligible3()) {
                        this.enabledState5 = false;
                        this.applyStep6();
                    }
                }
            } else {
            }
        }
        if ("Jetpack".equals(var6)) {
            if (this.enabledState7) {
                int var56 = (this.counterValue5 + 1);
                this.counterValue5 = var56;
                int var75 = this.getInterval();
                int var58 = Math.max(1, var75);
                if (var56 >= var58) {
                    this.counterValue5 = 0;
                    if (!this.isEligible3()) {
                        this.applyStep6();
                    }
                }
            }
        } else {
            if ("Up/Down".equals(var6)) {
                if (this.enabledState6) {
                    int var63 = (this.counterValue4 + 1);
                    this.counterValue4 = var63;
                    int var78 = this.getDelay();
                    int var65 = Math.max(1, var78);
                    if (var63 >= var65) {
                        this.enabledState6 = false;
                        this.counterValue4 = 0;
                        this.applyStep6();
                        Minecraft var39 = Minecraft.getInstance();
                        if (var39 != null) {
                            if (var39.isSameThread()) {
                                this.setEnabled(false);
                                this.counterValue5 = 0;
                            } else {
                                Runnable var70 = () -> this.lambda$N();
                                var39.execute(var70);
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    private void onPacketSend(EventPacketSend arg0) {
        if (!this.isEnabled()) {
            return;
        }
        try {
            Packet var8 = arg0.getPacket();
            if ((var8 instanceof ServerboundAttackPacket)) {
                if (((ServerboundAttackPacket) var8).entityId() < 0) {
                    arg0.cancel();
                }
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            if ((var8 instanceof ServerboundInteractPacket)) {
                if (((ServerboundInteractPacket) var8).entityId() < 0) {
                    arg0.cancel();
                }
            }
        } catch (Throwable ignored) {
        }
        return;
    }

    private void onStartAttack(EventStartAttack arg0) {
        if (arg0 == null) {
            return;
        }
        try {
            Entity var6 = arg0.getTarget();
            if (var6 != null) {
                int var10 = var6.getId();
                this.counterValue1 = var10;
            }
        } catch (Exception ignored) {
        }
    }

    private boolean isEligible1() {
        List var6 = this.entriesList;
        try {
            Iterator var9 = this.entriesList.iterator();
            while (true) {
                if (!var9.hasNext()) break;
                if (!(((QueuedPacketEntry) var9.next()).packet instanceof ClientboundExplodePacket)) continue;
            }
        } catch (Throwable ignored) {
        }
        return true;
        try {
        } catch (Throwable ignored) {
        }
        return false;
        List var13 = var6;
        throw var13;
    }

    private boolean isEligible(Packet arg0) {
        return (arg0 instanceof ClientboundPingPacket);
    }

    private void applyStep5(Packet arg0) {
        Minecraft var2 = Minecraft.getInstance();
        if (var2.player != null) {
            if (var2.player.connection == null) {
            }
        }
        return;
        try {
            applyStep(arg0, var2);
        } catch (Exception ignored) {
        }
    }

    private static void applyStep(Packet arg0, Minecraft arg1) {
        ClientPacketListener var5 = arg1.player.connection;
        arg0.handle(var5);
    }

    private void applyStep6() {
        List var6 = this.entriesList;
        try {
            if (this.entriesList.isEmpty()) {
                this.applyStep13();
            }
        } catch (Throwable ignored) {
        }
        return;
        try {
            List var44 = this.entriesList;
            ArrayList var10 = new ArrayList(var44);
            this.entriesList.clear();
        } catch (Throwable ignored) {
        }
        this.applyStep13();
        this.enabledState6 = false;
        this.enabledState3 = false;
        this.counterValue = 0;
        this.enabledState4 = false;
        this.enabledState8 = false;
        this.enabledState = true;
        Iterator var22 = var10.iterator();
        while (true) {
            if (!var22.hasNext()) break;
            Packet var42 = ((QueuedPacketEntry) var22.next()).packet;
            this.applyStep5(var42);
        }
        this.enabledState = false;
        return;
        List var30 = var6;
        throw var30;
    }

    public void applyStep7() {
        this.applyStep6();
    }

    public void applyStep8() {
    }

    public boolean isEligible2() {
        int var11 = 0;
        List var4 = this.entriesList;
        try {
            if (!this.entriesList.isEmpty()) {
                var11 = 1;
            } else {
                var11 = 0;
            }
        } catch (Throwable ignored) {
        }
        return var11;
        List var8 = var4;
        throw var8;
    }

    private void applyStep9() {
        List var6 = this.entriesList;
        try {
            List var28 = this.entriesList;
            ArrayList var7 = new ArrayList(var28);
            this.entriesList.clear();
        } catch (Throwable ignored) {
        }
        List var11 = var6;
        throw var11;
        this.enabledState = true;
        Iterator var16 = var7.iterator();
        while (true) {
            if (!var16.hasNext()) break;
            Packet var27 = ((QueuedPacketEntry) var16.next()).packet;
            this.applyStep5(var27);
        }
        this.enabledState = false;
    }

    private boolean isEligible3() {
        int var7 = 0;
        int var8 = 0;
        int var9 = 0;
        int var96 = 0;
        ArrayList var1 = new ArrayList();
        List var13 = this.entriesList;
        try {
            var7 = -1;
            var8 = 0;
            while (true) {
                int var16 = var8;
                int var67 = this.entriesList.size();
                if (var16 >= var67) break;
                List var18 = this.entriesList;
                int var68 = var8;
                if ((((QueuedPacketEntry) var18.get(var68)).packet instanceof ClientboundExplodePacket)) {
                    var7 = var8;
                } else {
                    var8 = (var8 + 1);
                }
                if (var7 >= 0) break;
            }
        } catch (Throwable ignored) {
        }
        return false;
        try {
            var96 = -1;
            var9 = (var7 + 1);
            while (true) {
                int var31 = var9;
                int var72 = this.entriesList.size();
                if (var31 >= var72) break;
                List var74 = this.entriesList;
                int var2 = var9;
                Packet var77 = ((QueuedPacketEntry) var74.get(var2)).packet;
                if (this.isEligible(var77)) {
                    var96 = var9;
                } else {
                    var9 = (var9 + 1);
                }
                if (var96 >= 0) break;
            }
        } catch (Throwable ignored) {
        }
        return false;
        try {
            List var79 = this.entriesList;
            int var91 = (var96 + 1);
            List var80 = var79.subList(0, var91);
            boolean var37 = var1.addAll(var80);
            List var39 = this.entriesList;
            int var90 = (var96 + 1);
            var39.subList(0, var90).clear();
        } catch (Throwable ignored) {
        }
        List var23 = var13;
        throw var23;
        this.enabledState = true;
        Iterator var44 = var1.iterator();
        while (true) {
            if (!var44.hasNext()) break;
            Packet var84 = ((QueuedPacketEntry) var44.next()).packet;
            this.applyStep5(var84);
        }
        this.enabledState = false;
        List var53 = this.entriesList;
        try {
            if (this.entriesList.isEmpty()) {
                this.applyStep13();
            }
        } catch (Throwable ignored) {
        }
        List var60 = var53;
        throw var60;
        return true;
    }

    private void applyStep10() {
        int var6 = 0;
        int var9 = 0;
        int var10 = 0;
        int var11 = 0;
        int var111 = 0;
        var6 = 0;
        while (true) {
            ArrayList var13 = new ArrayList();
            List var15 = this.entriesList;
            try {
                var9 = -1;
                var10 = 0;
                while (true) {
                    int var18 = var10;
                    int var80 = this.entriesList.size();
                    if (var18 >= var80) break;
                    List var20 = this.entriesList;
                    int var81 = var10;
                    if ((((QueuedPacketEntry) var20.get(var81)).packet instanceof ClientboundExplodePacket)) {
                        var9 = var10;
                    } else {
                        var10 = (var10 + 1);
                    }
                    if (var9 >= 0) break;
                }
            } catch (Throwable ignored) {
            }
            try {
                try {
                    List var31 = this.entriesList;
                    int var82 = var9;
                    Optional var36 = ((ClientboundExplodePacket) ((QueuedPacketEntry) var31.get(var82)).packet).playerKnockback();
                    if (!var36.isEmpty()) {
                        if (Double.compare(((Vec3) var36.get()).y, 0.0) <= 0) {
                        }
                    }
                } catch (Throwable ignored) {
                }
                try {
                    List var74 = var15;
                } catch (Throwable ignored) {
                }
                try {
                    var111 = -1;
                    var11 = (var9 + 1);
                    while (true) {
                        int var48 = var11;
                        int var86 = this.entriesList.size();
                        if (var48 >= var86) break;
                        List var88 = this.entriesList;
                        int var102 = var11;
                        Packet var91 = ((QueuedPacketEntry) var88.get(var102)).packet;
                        if (this.isEligible(var91)) {
                            var111 = var11;
                        } else {
                            var11 = (var11 + 1);
                        }
                        if (var111 >= 0) break;
                    }
                } catch (Throwable ignored) {
                }
                try {
                    List var93 = this.entriesList;
                    int var106 = (var111 + 1);
                    List var94 = var93.subList(0, var106);
                    boolean var54 = var13.addAll(var94);
                    List var56 = this.entriesList;
                    int var105 = (var111 + 1);
                    var56.subList(0, var105).clear();
                } catch (Throwable ignored) {
                }
                this.enabledState = true;
                Iterator var61 = var13.iterator();
                while (true) {
                    if (!var61.hasNext()) break;
                    Packet var98 = ((QueuedPacketEntry) var61.next()).packet;
                    this.applyStep5(var98);
                }
                this.enabledState = false;
                int var69 = var6;
                int var101 = var13.size();
                var6 = (var69 + var101);
                List var25 = var15;
                throw var25;
                if (var6 <= 0) break;
                this.applyStep13();
                return;
            } catch (Exception ignored) {
            }
        }
    }

    private void applyStep11(Packet arg0) {
        int var338 = 0;
        int var339 = 0;
        int var340 = 0;
        int var384 = 0;
        Minecraft var2 = Minecraft.getInstance();
        ClientLevel var23 = var2.level;
        if (var23 == null) {
            return;
        }
        try {
            int var25 = (arg0 instanceof ClientboundBlockUpdatePacket);
            if (var25 != 0) {
                ClientboundBlockUpdatePacket var27 = ((ClientboundBlockUpdatePacket) arg0);
                Object var29 = this.LOCK;
                try {
                    Map var31 = this.entriesMap3;
                    BlockPos var275 = var27.getPos();
                    long var276 = var275.asLong();
                    Long var278 = var276;
                    BlockState var385 = var27.getBlockState();
                    Object var32 = var31.put(var278, var385);
                    Map var34 = this.entriesMap3;
                    int var35 = var34.size();
                    if (var35 > 20000) {
                        Map var37 = this.entriesMap3;
                        var37.clear();
                    }
                } catch (Throwable ignored) {
                }
                Object var40 = var29;
                throw var40;
            }
        } catch (Exception ignored) {
        }
        try {
            int var44 = (arg0 instanceof ClientboundSectionBlocksUpdatePacket);
            if (var44 != 0) {
                ClientboundSectionBlocksUpdatePacket var46 = ((ClientboundSectionBlocksUpdatePacket) arg0);
                ClientboundSectionBlocksUpdatePacket var439 = var46;
                Object var48 = this.LOCK;
                Object var448 = var48;
                try {
                    BiConsumer var281 = (p0, p1) -> this.lambda$a(p0, p1);
                    var439.runUpdates(var281);
                    Map var51 = this.entriesMap3;
                    int var52 = var51.size();
                    if (var52 > 20000) {
                        Map var54 = this.entriesMap3;
                        var54.clear();
                    }
                } catch (Throwable ignored) {
                }
                Object var56 = var448;
                throw var56;
            }
        } catch (Exception ignored) {
        }
        return;
        try {
            int var60 = (arg0 instanceof ClientboundRemoveEntitiesPacket);
            if (var60 != 0) {
                ClientboundRemoveEntitiesPacket var255 = ((ClientboundRemoveEntitiesPacket) arg0);
                ClientboundRemoveEntitiesPacket var447 = var255;
                Object var257 = this.LOCK;
                Object var456 = var257;
                try {
                    IntList var259 = var447.getEntityIds();
                    IntListIterator var260 = var259.iterator();
                    IntListIterator var464 = var260;
                    while (true) {
                        boolean var262 = var464.hasNext();
                        if (!var262) break;
                        Object var268 = var464.next();
                        Integer var269 = ((Integer) var268);
                        int var270 = var269;
                        int var472 = var270;
                        Map var272 = this.entriesMap1;
                        int var382 = var472;
                        Integer var383 = var382;
                        Object var273 = var272.remove(var383);
                    }
                } catch (Throwable ignored) {
                }
                Object var264 = var456;
                Object var476 = var264;
                throw var476;
            }
        } catch (Exception ignored) {
        }
        try {
            int var62 = (arg0 instanceof ClientboundMoveEntityPacket);
            if (var62 != 0) {
                ClientboundMoveEntityPacket var227 = ((ClientboundMoveEntityPacket) arg0);
                ClientboundMoveEntityPacket var446 = var227;
                Minecraft var359 = var2;
                ClientLevel var360 = var359.level;
                Entity var229 = var446.getEntity(var360);
                Entity var455 = var229;
                if (var455 != null) {
                    Minecraft var361 = var2;
                    LocalPlayer var362 = var361.player;
                    if (var455 == var362) {
                    }
                }
                return;
            }
            try {
                int var64 = (arg0 instanceof ClientboundEntityPositionSyncPacket);
                if (var64 != 0) {
                    ClientboundEntityPositionSyncPacket var66 = ((ClientboundEntityPositionSyncPacket) arg0);
                    ClientboundEntityPositionSyncPacket var440 = var66;
                    int var68 = var440.id();
                    int var449 = var68;
                    int var284 = this.counterValue8;
                    if (var449 == var284) {
                    }
                }
            } catch (Exception ignored) {
            }
            return;
            try {
                Object var71 = this.LOCK;
                Object var457 = var71;
                try {
                    Minecraft var386 = var2;
                    ClientLevel var387 = var386.level;
                    Entity var388 = var387.getEntity(var449);
                    PlayerGhostSnapshot var73 = this.evaluate(var449, var388);
                    PlayerGhostSnapshot var465 = var73;
                    if (var465 == null) {
                    }
                } catch (Throwable ignored) {
                }
                return;
                try {
                    try {
                        PositionMoveRotation var80 = var440.values();
                        Vec3 var82 = var80.position();
                        double var287 = var82.x;
                        var465.doubleValue6 = var287;
                        double var290 = var82.y;
                        var465.doubleValue7 = var290;
                        double var293 = var82.z;
                        var465.doubleValue8 = var293;
                        float var296 = var80.yRot();
                        var465.floatValue6 = var296;
                        float var298 = var80.xRot();
                        var465.floatValue7 = var298;
                        float var300 = var80.yRot();
                        var465.floatValue8 = var300;
                    } catch (Exception ignored) {
                    }
                } catch (Throwable ignored) {
                }
                Object var76 = var457;
                throw var76;
                return;
                try {
                    int var91 = (arg0 instanceof ClientboundTeleportEntityPacket);
                    if (var91 != 0) {
                        ClientboundTeleportEntityPacket var93 = ((ClientboundTeleportEntityPacket) arg0);
                        ClientboundTeleportEntityPacket var441 = var93;
                        int var95 = var441.id();
                        int var450 = var95;
                        int var302 = this.counterValue8;
                        if (var450 == var302) {
                        }
                    }
                } catch (Exception ignored) {
                }
                return;
                try {
                    Object var98 = this.LOCK;
                    Object var458 = var98;
                    try {
                        Minecraft var389 = var2;
                        ClientLevel var390 = var389.level;
                        int var403 = var450;
                        Entity var391 = var390.getEntity(var403);
                        PlayerGhostSnapshot var100 = this.evaluate(var450, var391);
                        PlayerGhostSnapshot var466 = var100;
                        if (var466 == null) {
                        }
                    } catch (Throwable ignored) {
                    }
                    return;
                    try {
                        try {
                            double var423 = var466.doubleValue6;
                            double var434 = var466.doubleValue7;
                            double var436 = var466.doubleValue8;
                            Vec3 var392 = new Vec3(var423, var434, var436);
                            Vec3 var404 = Vec3.ZERO;
                            PlayerGhostSnapshot var425 = var466;
                            float var426 = var425.floatValue6;
                            float var427 = var466.floatValue7;
                            PositionMoveRotation var102 = new PositionMoveRotation(var392, var404, var426, var427);
                            PositionMoveRotation var473 = var102;
                            PositionMoveRotation var305 = var441.change();
                            ClientboundTeleportEntityPacket var393 = var441;
                            Set var394 = var393.relatives();
                            PositionMoveRotation var104 = PositionMoveRotation.calculateAbsolute(var473, var305, var394);
                            PositionMoveRotation var477 = var104;
                            Vec3 var106 = var477.position();
                            Vec3 var480 = var106;
                            double var307 = var480.x;
                            var466.doubleValue6 = var307;
                            double var310 = var480.y;
                            var466.doubleValue7 = var310;
                            double var313 = var480.z;
                            var466.doubleValue8 = var313;
                            float var316 = var477.yRot();
                            var466.floatValue6 = var316;
                            float var318 = var477.xRot();
                            var466.floatValue7 = var318;
                            float var320 = var477.yRot();
                            var466.floatValue8 = var320;
                        } catch (Exception ignored) {
                        }
                    } catch (Throwable ignored) {
                    }
                    Object var114 = var458;
                    throw var114;
                    return;
                    try {
                        int var119 = (arg0 instanceof ClientboundRotateHeadPacket);
                        if (var119 != 0) {
                            ClientboundRotateHeadPacket var211 = ((ClientboundRotateHeadPacket) arg0);
                            ClientboundRotateHeadPacket var445 = var211;
                            Minecraft var351 = var2;
                            ClientLevel var352 = var351.level;
                            Entity var213 = var445.getEntity(var352);
                            Entity var454 = var213;
                            if (var454 != null) {
                                Minecraft var353 = var2;
                                LocalPlayer var354 = var353.player;
                                if (var454 == var354) {
                                }
                            }
                            return;
                            try {
                                Object var217 = this.LOCK;
                                Object var462 = var217;
                                try {
                                    Entity var355 = var454;
                                    int var356 = var355.getId();
                                    Entity var401 = var454;
                                    PlayerGhostSnapshot var219 = this.evaluate(var356, var401);
                                    PlayerGhostSnapshot var470 = var219;
                                    if (var470 != null) {
                                        ClientboundRotateHeadPacket var357 = var445;
                                        float var358 = var357.getYHeadRot();
                                        var470.floatValue8 = var358;
                                    }
                                } catch (Throwable ignored) {
                                }
                                Object var223 = var462;
                                Object var485 = var223;
                                throw var485;
                            } catch (Exception ignored) {
                            }
                            return;
                        }
                        try {
                            int var121 = (arg0 instanceof ClientboundAnimatePacket);
                            if (var121 != 0) {
                                ClientboundAnimatePacket var189 = ((ClientboundAnimatePacket) arg0);
                                ClientboundAnimatePacket var444 = var189;
                                Object var191 = this.LOCK;
                                Object var453 = var191;
                                try {
                                    Map var193 = this.entriesMap1;
                                    ClientboundAnimatePacket var343 = var444;
                                    int var344 = var343.getId();
                                    Integer var345 = var344;
                                    Object var194 = var193.get(var345);
                                    PlayerGhostSnapshot var195 = ((PlayerGhostSnapshot) var194);
                                    PlayerGhostSnapshot var461 = var195;
                                    if (var461 == null) {
                                    }
                                } catch (Throwable ignored) {
                                }
                                return;
                                try {
                                    try {
                                        int var198 = var444.getAction();
                                        int var469 = var198;
                                        if (var469 == 0) {
                                            PlayerGhostSnapshot var346 = var461;
                                            int var399 = 1;
                                            this.applyStep(var346, var399);
                                        } else {
                                            int var347 = 3;
                                            if (var469 == var347) {
                                                PlayerGhostSnapshot var350 = var461;
                                                int var400 = 0;
                                                this.applyStep(var350, var400);
                                            } else {
                                                int var348 = 1;
                                                if (var469 == var348) {
                                                    int var349 = 10;
                                                    var461.counterValue1 = var349;
                                                }
                                            }
                                        }
                                    } catch (Exception ignored) {
                                    }
                                } catch (Throwable ignored) {
                                }
                                Object var202 = var453;
                                throw var202;
                                return;
                            }
                            try {
                                int var123 = (arg0 instanceof ClientboundHurtAnimationPacket);
                                if (var123 != 0) {
                                    ClientboundHurtAnimationPacket var125 = ((ClientboundHurtAnimationPacket) arg0);
                                    ClientboundHurtAnimationPacket var442 = var125;
                                    Object var127 = this.LOCK;
                                    Object var451 = var127;
                                    try {
                                        Map var129 = this.entriesMap1;
                                        int var322 = var442.id();
                                        Integer var323 = var322;
                                        Object var130 = var129.get(var323);
                                        PlayerGhostSnapshot var131 = ((PlayerGhostSnapshot) var130);
                                        PlayerGhostSnapshot var459 = var131;
                                        if (var459 != null) {
                                            var459.counterValue1 = 10;
                                        }
                                    } catch (Throwable ignored) {
                                    }
                                    Object var135 = var451;
                                    throw var135;
                                }
                            } catch (Exception ignored) {
                            }
                            try {
                                int var139 = (arg0 instanceof ClientboundSetEntityDataPacket);
                                if (var139 != 0) {
                                    ClientboundSetEntityDataPacket var141 = ((ClientboundSetEntityDataPacket) arg0);
                                    ClientboundSetEntityDataPacket var443 = var141;
                                    int var143 = var443.id();
                                    int var326 = this.counterValue8;
                                    if (var143 == var326) {
                                    }
                                }
                            } catch (Exception ignored) {
                            }
                            return;
                            try {
                                Object var145 = this.LOCK;
                                Object var452 = var145;
                                try {
                                    Map var147 = this.entriesMap1;
                                    int var328 = var443.id();
                                    Integer var329 = var328;
                                    Object var148 = var147.get(var329);
                                    PlayerGhostSnapshot var149 = ((PlayerGhostSnapshot) var148);
                                    PlayerGhostSnapshot var460 = var149;
                                    if (var460 == null) {
                                    }
                                } catch (Throwable ignored) {
                                }
                                return;
                                try {
                                    try {
                                        try {
                                            List var152 = var443.packedItems();
                                            Iterator var153 = var152.iterator();
                                            Iterator var467 = var153;
                                            while (true) {
                                                boolean var155 = var467.hasNext();
                                                if (!var155) break;
                                                Object var157 = var467.next();
                                                SynchedEntityData.DataValue var158 = ((SynchedEntityData.DataValue) var157);
                                                SynchedEntityData.DataValue var474 = var158;
                                                int var160 = var474.id();
                                                int var478 = var160;
                                                if (var478 == 0) {
                                                    Object var163 = var474.value();
                                                    int var165 = (var163 instanceof Byte);
                                                    if (var165 != 0) {
                                                        Byte var167 = ((Byte) var163);
                                                        Byte var481 = var167;
                                                        int var169 = var481.byteValue();
                                                        int var483 = var169;
                                                        int var395 = 2;
                                                        int var331 = (var483 & var395);
                                                        if (var331 != 0) {
                                                            var340 = 1;
                                                        } else {
                                                            var340 = 0;
                                                        }
                                                        var460.enabledState1 = var340;
                                                        int var396 = 8;
                                                        int var333 = (var483 & var396);
                                                        if (var333 != 0) {
                                                            var339 = 1;
                                                        } else {
                                                            var339 = 0;
                                                        }
                                                        var460.enabledState2 = var339;
                                                        int var397 = 16;
                                                        int var335 = (var483 & var397);
                                                        if (var335 != 0) {
                                                            var384 = 1;
                                                        } else {
                                                            var384 = 0;
                                                        }
                                                        var460.enabledState3 = var384;
                                                        int var336 = var483;
                                                        int var398 = 128;
                                                        int var337 = (var336 & var398);
                                                        if (var337 != 0) {
                                                            var338 = 1;
                                                        } else {
                                                            var338 = 0;
                                                        }
                                                        var460.enabledState4 = var338;
                                                    }
                                                    int var341 = 6;
                                                    if (var478 != var341) continue;
                                                    Object var181 = var474.value();
                                                    Object var484 = var181;
                                                    int var183 = (var484 instanceof Pose);
                                                    if (var183 == 0) continue;
                                                    Pose var185 = ((Pose) var484);
                                                    Pose var482 = var185;
                                                    Pose var342 = var482;
                                                    var460.fieldVal2 = var342;
                                                }
                                            }
                                            Object var174 = var452;
                                            Object var468 = var174;
                                        } catch (Exception ignored) {
                                        }
                                    } catch (Throwable ignored) {
                                    }
                                    Object var176 = var452;
                                    throw var176;
                                    return;
                                    try {
                                        int var233 = var455.getId();
                                        int var463 = var233;
                                        Object var235 = this.LOCK;
                                        Object var471 = var235;
                                        try {
                                            int var363 = var463;
                                            Entity var402 = var455;
                                            PlayerGhostSnapshot var237 = this.evaluate(var363, var402);
                                            PlayerGhostSnapshot var475 = var237;
                                            if (var475 == null) {
                                            }
                                        } catch (Throwable ignored) {
                                        }
                                        return;
                                        try {
                                            try {
                                                boolean var240 = var446.hasPosition();
                                                if (var240) {
                                                    double var364 = var475.doubleValue6;
                                                    ClientboundMoveEntityPacket var405 = var446;
                                                    int var406 = var405.getXa();
                                                    double var407 = ((double) var406);
                                                    double var428 = 4096.0;
                                                    double var409 = (var407 / var428);
                                                    double var366 = (var364 + var409);
                                                    var475.doubleValue6 = var366;
                                                    double var368 = var475.doubleValue7;
                                                    ClientboundMoveEntityPacket var411 = var446;
                                                    int var412 = var411.getYa();
                                                    double var413 = ((double) var412);
                                                    double var430 = 4096.0;
                                                    double var415 = (var413 / var430);
                                                    double var370 = (var368 + var415);
                                                    var475.doubleValue7 = var370;
                                                    double var372 = var475.doubleValue8;
                                                    ClientboundMoveEntityPacket var417 = var446;
                                                    int var418 = var417.getZa();
                                                    double var419 = ((double) var418);
                                                    double var432 = 4096.0;
                                                    double var421 = (var419 / var432);
                                                    double var374 = (var372 + var421);
                                                    var475.doubleValue8 = var374;
                                                }
                                                boolean var245 = var446.hasRotation();
                                                if (var245) {
                                                    ClientboundMoveEntityPacket var376 = var446;
                                                    float var377 = var376.getYRot();
                                                    var475.floatValue6 = var377;
                                                    ClientboundMoveEntityPacket var378 = var446;
                                                    float var379 = var378.getXRot();
                                                    var475.floatValue7 = var379;
                                                    ClientboundMoveEntityPacket var380 = var446;
                                                    float var381 = var380.getYRot();
                                                    var475.floatValue8 = var381;
                                                }
                                            } catch (Exception ignored) {
                                            }
                                        } catch (Throwable ignored) {
                                        }
                                        Object var250 = var471;
                                        Object var479 = var250;
                                        throw var479;
                                        return;
                                        return;
                                        Object var39 = var471;
                                        Object var438 = var39;
                                        return;
                                        return;
                                    } catch (Exception ignored) {
                                    }
                                } catch (Exception ignored) {
                                }
                            } catch (Exception ignored) {
                            }
                        } catch (Exception ignored) {
                        }
                    } catch (Exception ignored) {
                    }
                } catch (Exception ignored) {
                }
            } catch (Exception ignored) {
            }
        } catch (Exception ignored) {
        }
    }

    private void applyStep(PlayerGhostSnapshot arg0, boolean arg1) {
        HumanoidArm var16 = null;
        if (arg0.enabledState) {
            if (arg0.counterValue < 3) {
                if (arg0.counterValue < 0) {
                }
            }
            arg0.counterValue = -1;
            arg0.enabledState = true;
            if (arg1) {
                var16 = HumanoidArm.RIGHT;
            } else {
                var16 = HumanoidArm.LEFT;
            }
            arg0.fieldVal1 = var16;
            return;
        }
    }

    private void applyStep12() {
        float var291 = 0.0F;
        Object var19 = this.LOCK;
        try {
            Iterator var23 = this.entriesMap1.entrySet().iterator();
            while (true) {
                if (!var23.hasNext()) break;
                Map.Entry var28 = ((Map.Entry) var23.next());
                PlayerGhostSnapshot var31 = ((PlayerGhostSnapshot) var28.getValue());
                float var140 = var31.floatValue;
                var31.floatValue3 = var140;
                float var142 = var31.floatValue2;
                var31.floatValue5 = var142;
                float var144 = var31.floatValue1;
                var31.floatValue4 = var144;
                double var146 = var31.doubleValue;
                var31.doubleValue3 = var146;
                double var149 = var31.doubleValue1;
                var31.doubleValue4 = var149;
                double var152 = var31.doubleValue2;
                var31.doubleValue5 = var152;
                float var155 = var31.floatValue15;
                var31.floatValue16 = var155;
                double var40 = var31.doubleValue6;
                double var239 = var31.doubleValue9;
                if (var40 == var239) {
                    double var44 = var31.doubleValue7;
                    double var242 = var31.doubleValue10;
                    if (var44 == var242) {
                        double var48 = var31.doubleValue8;
                        double var245 = var31.doubleValue11;
                        if (var48 != var245) {
                        }
                    }
                    double var157 = var31.doubleValue6;
                    var31.doubleValue9 = var157;
                    double var160 = var31.doubleValue7;
                    var31.doubleValue10 = var160;
                    double var163 = var31.doubleValue8;
                    var31.doubleValue11 = var163;
                    var31.counterValue3 = 3;
                    if (var31.counterValue3 > 0) {
                        double var166 = var31.doubleValue;
                        double var293 = var31.doubleValue9;
                        double var341 = var31.doubleValue;
                        double var295 = (var293 - var341);
                        double var345 = ((double) var31.counterValue3);
                        double var297 = (var295 / var345);
                        double var168 = (var166 + var297);
                        var31.doubleValue = var168;
                        double var170 = var31.doubleValue1;
                        double var300 = var31.doubleValue10;
                        double var348 = var31.doubleValue1;
                        double var302 = (var300 - var348);
                        double var352 = ((double) var31.counterValue3);
                        double var304 = (var302 / var352);
                        double var172 = (var170 + var304);
                        var31.doubleValue1 = var172;
                        double var174 = var31.doubleValue2;
                        double var307 = var31.doubleValue11;
                        double var355 = var31.doubleValue2;
                        double var309 = (var307 - var355);
                        double var359 = ((double) var31.counterValue3);
                        double var311 = (var309 / var359);
                        double var176 = (var174 + var311);
                        var31.doubleValue2 = var176;
                        int var179 = (var31.counterValue3 - 1);
                        var31.counterValue3 = var179;
                    }
                    float var62 = var31.floatValue6;
                    float var181 = var31.floatValue12;
                    if (var62 == var181) {
                        float var65 = var31.floatValue7;
                        float var183 = var31.floatValue13;
                        if (var65 == var183) {
                            float var68 = var31.floatValue8;
                            float var185 = var31.floatValue14;
                            if (var68 != var185) {
                            }
                        }
                        float var187 = var31.floatValue6;
                        var31.floatValue12 = var187;
                        float var189 = var31.floatValue7;
                        var31.floatValue13 = var189;
                        float var191 = var31.floatValue8;
                        var31.floatValue14 = var191;
                        var31.counterValue4 = 3;
                        if (var31.counterValue4 > 0) {
                            float var193 = var31.floatValue;
                            float var249 = var31.floatValue12;
                            float var314 = var31.floatValue;
                            float var251 = Mth.wrapDegrees((var249 - var314));
                            float var317 = ((float) var31.counterValue4);
                            float var252 = (var251 / var317);
                            float var194 = (var193 + var252);
                            var31.floatValue = var194;
                            float var195 = var31.floatValue1;
                            float var254 = var31.floatValue13;
                            float var319 = var31.floatValue1;
                            float var255 = (var254 - var319);
                            float var322 = ((float) var31.counterValue4);
                            float var256 = (var255 / var322);
                            float var196 = (var195 + var256);
                            var31.floatValue1 = var196;
                            float var197 = var31.floatValue2;
                            float var258 = var31.floatValue14;
                            float var324 = var31.floatValue2;
                            float var260 = Mth.wrapDegrees((var258 - var324));
                            float var327 = ((float) var31.counterValue4);
                            float var261 = (var260 / var327);
                            float var198 = (var197 + var261);
                            var31.floatValue2 = var198;
                            int var200 = (var31.counterValue4 - 1);
                            var31.counterValue4 = var200;
                        }
                        float var81 = var31.floatValue2;
                        float var202 = var31.floatValue15;
                        float var83 = Mth.wrapDegrees((var81 - var202));
                        float var203 = var31.floatValue15;
                        float var264 = (var83 * 0.3F);
                        float var204 = (var203 + var264);
                        var31.floatValue15 = var204;
                        float var86 = var31.floatValue2;
                        float var206 = var31.floatValue15;
                        float var88 = Mth.wrapDegrees((var86 - var206));
                        if (Float.compare(Math.abs(var88), 50.0F) > 0) {
                            float var208 = var31.floatValue15;
                            float var331 = (Math.signum(var88) * 50.0F);
                            float var266 = (var88 - var331);
                            float var209 = (var208 + var266);
                            var31.floatValue15 = var209;
                        }
                        float var212 = Mth.wrapDegrees(var31.floatValue15);
                        var31.floatValue15 = var212;
                        long var213 = System.nanoTime();
                        var31.timestampMs = var213;
                        Map var97 = this.entriesMap2;
                        Integer var217 = ((Integer) var28.getKey());
                        double[] var268 = new double[3];
                        double var362 = var31.doubleValue;
                        var268[0] = var362;
                        double var365 = var31.doubleValue1;
                        var268[1] = var365;
                        double var368 = var31.doubleValue2;
                        var268[2] = var368;
                        Object var98 = var97.put(var217, var268);
                        double var100 = var31.doubleValue;
                        double var270 = var31.doubleValue3;
                        double var102 = (var100 - var270);
                        double var105 = var31.doubleValue2;
                        double var273 = var31.doubleValue5;
                        double var107 = (var105 - var273);
                        double var111 = (var102 * var102);
                        double var279 = (var107 * var107);
                        float var117 = ((float) Math.sqrt((var111 + var279)));
                        WalkAnimationState var119 = var31.fieldVal;
                        float var220 = Math.min((var117 * 4.0F), 1.0F);
                        var119.update(var220, 0.4F, 1.0F);
                        float var222 = var31.floatValue9;
                        var31.floatValue10 = var222;
                        if (var31.enabledState) {
                            int var235 = (var31.counterValue + 1);
                            var31.counterValue = var235;
                            if (var31.counterValue >= 6) {
                                var31.counterValue = 0;
                                var31.enabledState = false;
                            }
                        } else {
                            var31.counterValue = 0;
                        }
                        float var227 = (((float) var31.counterValue) / 6.0F);
                        var31.floatValue9 = var227;
                        if (var31.counterValue1 > 0) {
                            int var229 = (var31.counterValue1 - 1);
                            var31.counterValue1 = var229;
                        }
                        float var230 = var31.floatValue11;
                        if (var31.enabledState3) {
                            var291 = 1.0F;
                        } else {
                            var291 = 0.0F;
                        }
                        float var334 = var31.floatValue11;
                        float var289 = ((var291 - var334) * 0.1F);
                        float var231 = (var230 + var289);
                        var31.floatValue11 = var231;
                        int var233 = (var31.counterValue2 + 1);
                        var31.counterValue2 = var233;
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        return;
        Object var130 = var19;
        throw var130;
    }

    private PlayerGhostSnapshot evaluate(int arg0, Entity arg1) {
        PlayerGhostSnapshot var14 = null;
        Map var16 = this.entriesMap1;
        Integer var33 = arg0;
        var14 = ((PlayerGhostSnapshot) var16.get(var33));
        if (var14 == null) {
            if (arg1 == null) {
                return null;
            }
            Map var23 = this.entriesMap2;
            Integer var35 = arg0;
            double[] var25 = ((double[]) var23.get(var35));
            if (var25 != null) {
                double var42 = var25[0];
                double var47 = var25[1];
                double var52 = var25[2];
                float var56 = arg1.getYRot();
                float var59 = arg1.getXRot();
                float var62 = arg1.getYHeadRot();
                PlayerGhostSnapshot var32 = new PlayerGhostSnapshot(var42, var47, var52, var56, var59, var62);
                var14 = var32;
            } else {
                double var38 = arg1.getX();
                double var44 = arg1.getY();
                double var49 = arg1.getZ();
                float var54 = arg1.getYRot();
                float var57 = arg1.getXRot();
                float var60 = arg1.getYHeadRot();
                PlayerGhostSnapshot var27 = new PlayerGhostSnapshot(var38, var44, var49, var54, var57, var60);
                var14 = var27;
            }
            Map var29 = this.entriesMap1;
            Integer var37 = arg0;
            PlayerGhostSnapshot var40 = var14;
            Object var30 = var29.put(var37, var40);
        }
        return var14;
    }

    private void applyStep13() {
        this.entriesSet.clear();
    }

    private void applyStep14() {
        Object var4 = this.LOCK;
        try {
            this.entriesMap1.clear();
            this.entriesMap2.clear();
            this.entriesMap3.clear();
        } catch (Throwable ignored) {
        }
        this.entriesSet.clear();
        IdentityHashMap var18 = new IdentityHashMap();
        Set var19 = Collections.newSetFromMap(var18);
        this.entriesSet1 = var19;
        return;
        Object var12 = var4;
        throw var12;
    }

    public boolean isEligible(int arg0) {
        Set var6 = this.entriesSet;
        Integer var17 = arg0;
        if (var6.contains(var17)) {
            return true;
        }
        Object var10 = this.LOCK;
        try {
            Map var12 = this.entriesMap1;
            Integer var19 = arg0;
            boolean var13 = var12.containsKey(var19);
        } catch (Throwable ignored) {
        }
        return var13;
        Object var14 = var10;
        throw var14;
    }

    public boolean isEligible(Object arg0) {
        int var7 = 0;
        if (arg0 != null) {
            if (this.entriesSet1.contains(arg0)) {
                var7 = 1;
            } else {
            }
        }
        var7 = 0;
        return var7;
    }

    public int computeColorOrIndex() {
        float var3 = 0.0F;
        var3 = this.getGhostOpacity();
        if (Float.compare(var3, 0.0F) < 0) {
            var3 = 0.0F;
        }
        if (Float.compare(var3, 1.0F) > 0) {
            var3 = 1.0F;
        }
        return ((int) ((var3 * 255.0F) + 0.5F));
    }

    private static float computeFloatMetric(PlayerGhostSnapshot arg0) {
        long var1 = System.nanoTime();
        long var23 = arg0.timestampMs;
        float var13 = (((float) (var1 - var23)) / 5.0E7F);
        if (Float.compare(var13, 0.0F) < 0) {
            return 0.0F;
        }
        if (Float.compare(var13, 1.0F) > 0) {
            return 1.0F;
        }
        return var13;
    }

    public void renderWorldEffect(LevelRenderState arg0, float arg1) {
        double var15 = 0.0;
        double var17 = 0.0;
        double var19 = 0.0;
        float var325 = 0.0F;
        int var251 = 0;
        if (this.isEnabled()) {
            if (this.isGhostEntities()) {
                if (this.enabledState2) {
                    if (arg0 == null) {
                    }
                }
                return;
                Minecraft var42 = Minecraft.getInstance();
                if (var42 != null) {
                    if (var42.level != null) {
                        if (var42.player == null) {
                        }
                    }
                    return;
                    Object var49 = this.LOCK;
                    try {
                        if (this.entriesMap1.isEmpty()) {
                            IdentityHashMap var249 = new IdentityHashMap();
                            Set var250 = Collections.newSetFromMap(var249);
                            this.entriesSet1 = var250;
                        }
                    } catch (Throwable ignored) {
                    }
                    return;
                    try {
                        Set var253 = this.entriesMap1.entrySet();
                        ArrayList var53 = new ArrayList(var253);
                    } catch (Throwable ignored) {
                    }
                    EntityRenderDispatcher var56 = var42.getEntityRenderDispatcher();
                    var15 = 0.0;
                    var17 = 0.0;
                    var19 = 0.0;
                    try {
                        if (arg0.cameraRenderState != null) {
                            if (arg0.cameraRenderState.pos != null) {
                                Vec3 var70 = arg0.cameraRenderState.pos;
                                var15 = var70.x;
                                var17 = var70.y;
                                var19 = var70.z;
                            }
                        }
                    } catch (Exception ignored) {
                    }
                    Object var171 = var49;
                    IdentityHashMap var80 = new IdentityHashMap();
                    Set var81 = Collections.newSetFromMap(var80);
                    Iterator var83 = var53.iterator();
                    while (true) {
                        if (!var83.hasNext()) break;
                        Map.Entry var88 = ((Map.Entry) var83.next());
                        ClientLevel var90 = var42.level;
                        int var180 = ((Integer) var88.getKey());
                        Entity var91 = var90.getEntity(var180);
                        if (var91 == null) continue;
                        LocalPlayer var182 = var42.player;
                        if (var91 == var182) {
                        }
                        PlayerGhostSnapshot var96 = ((PlayerGhostSnapshot) var88.getValue());
                        try {
                            EntityRenderState var98 = var56.extractEntity(var91, arg1);
                            if (var98 == null) {
                            }
                        } catch (Exception ignored) {
                        }
                        try {
                            double var101 = ((double) arg1);
                            double var256 = var96.doubleValue3;
                            double var295 = var96.doubleValue;
                            double var103 = Mth.lerp(var101, var256, var295);
                            double var106 = ((double) arg1);
                            double var259 = var96.doubleValue4;
                            double var298 = var96.doubleValue1;
                            double var108 = Mth.lerp(var106, var259, var298);
                            double var111 = ((double) arg1);
                            double var262 = var96.doubleValue5;
                            double var301 = var96.doubleValue2;
                            double var113 = Mth.lerp(var111, var262, var301);
                            var98.x = var103;
                            var98.y = var108;
                            var98.z = var113;
                            var98.nameTag = null;
                            var98.scoreText = null;
                            var98.shadowRadius = 0.0F;
                            try {
                                var98.shadowPieces.clear();
                            } catch (Exception ignored) {
                            }
                            Object var169 = var49;
                            double var195 = (var15 - var103);
                            double var278 = (var15 - var103);
                            double var197 = (var195 * var278);
                            double var282 = (var17 - var108);
                            double var307 = (var17 - var108);
                            double var284 = (var282 * var307);
                            double var199 = (var197 + var284);
                            double var288 = (var19 - var113);
                            double var313 = (var19 - var113);
                            double var290 = (var288 * var313);
                            double var201 = (var199 + var290);
                            var98.distanceToCameraSq = var201;
                            if ((var98 instanceof LivingEntityRenderState)) {
                                LivingEntityRenderState var127 = ((LivingEntityRenderState) var98);
                                var127.isInvisible = false;
                                var127.isInvisibleToPlayer = false;
                                float var206 = var96.floatValue16;
                                float var265 = var96.floatValue15;
                                float var131 = Mth.rotLerp(arg1, var206, var265);
                                float var208 = var96.floatValue5;
                                float var267 = var96.floatValue2;
                                float var133 = Mth.rotLerp(arg1, var208, var267);
                                var127.bodyRot = var131;
                                float var212 = Mth.wrapDegrees((var133 - var131));
                                var127.yRot = var212;
                                float var270 = var96.floatValue4;
                                float var293 = var96.floatValue1;
                                float var214 = Mth.lerp(arg1, var270, var293);
                                var127.xRot = var214;
                                float var217 = var96.fieldVal.position(arg1);
                                var127.walkAnimationPos = var217;
                                float var220 = var96.fieldVal.speed(arg1);
                                var127.walkAnimationSpeed = var220;
                                float var224 = (((float) var96.counterValue2) + arg1);
                                var127.ageInTicks = var224;
                                if (var96.counterValue1 > 0) {
                                    var251 = 1;
                                } else {
                                    var251 = 0;
                                }
                                var127.hasRedOverlay = var251;
                            }
                            if ((var98 instanceof HumanoidRenderState)) {
                                HumanoidRenderState var144 = ((HumanoidRenderState) var98);
                                boolean var228 = var96.enabledState1;
                                var144.isCrouching = var228;
                                boolean var230 = var96.enabledState4;
                                var144.isFallFlying = var230;
                                boolean var232 = var96.enabledState3;
                                var144.isVisuallySwimming = var232;
                                float var234 = var96.floatValue11;
                                var144.swimAmount = var234;
                                Pose var236 = var96.fieldVal2;
                                var144.pose = var236;
                            }
                            if ((var98 instanceof ArmedEntityRenderState)) {
                                ArmedEntityRenderState var153 = ((ArmedEntityRenderState) var98);
                                float var155 = var96.floatValue9;
                                float var238 = var96.floatValue10;
                                var325 = (var155 - var238);
                                if (Float.compare(var325, 0.0F) < 0) {
                                    var325 = (var325 + 1.0F);
                                }
                                float var242 = var96.floatValue10;
                                float var275 = (var325 * arg1);
                                float var243 = (var242 + var275);
                                var153.attackTime = var243;
                                HumanoidArm var245 = var96.fieldVal1;
                                var153.attackArm = var245;
                            }
                            boolean var164 = var81.add(var98);
                            boolean var167 = arg0.entityRenderStates.add(var98);
                        } catch (Exception ignored) {
                        }
                    }
                    this.entriesSet1 = var81;
                    return;
                    Object var172 = var49;
                    throw var172;
                }
            }
        }
    }

    private boolean isBlockDiff() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Block Diff", var7));
    }

    private String getBlockDiffColor() {
        return ((String) this.getSetting("Block Diff Color", "#FF4D4D"));
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        Minecraft var3 = Minecraft.getInstance();
        if (var3 != null) {
            if (var3.level == null) {
            }
        }
        return;
        if (this.isBlockDiff()) {
            try {
                Object var43 = this.LOCK;
                try {
                    Set var183 = this.entriesMap3.keySet();
                    ArrayList var44 = new ArrayList(var183);
                } catch (Throwable ignored) {
                }
                Object var46 = var43;
                throw var46;
            } catch (Exception ignored) {
            }
            try {
                ArrayList var123 = new ArrayList();
                Iterator var125 = var44.iterator();
                while (true) {
                    if (!var125.hasNext()) break;
                    Long var130 = ((Long) var125.next());
                    Object var132 = this.LOCK;
                    try {
                        Object var135 = this.entriesMap3.get(var130);
                    } catch (Throwable ignored) {
                    }
                    Object var150 = var132;
                    throw var150;
                }
            } catch (Exception ignored) {
            }
            try {
                if (!(var135 instanceof BlockState)) continue;
                BlockState var140 = ((BlockState) var135);
                BlockPos var144 = BlockPos.of(var130);
                if (var3.level.getBlockState(var144) == var140) {
                }
                double var217 = ((double) var144.getX());
                double var247 = ((double) var144.getY());
                double var264 = ((double) var144.getZ());
                double var274 = ((double) (var144.getX() + 1));
                double var278 = ((double) (var144.getY() + 1));
                double var282 = ((double) (var144.getZ() + 1));
                AABB var180 = new AABB(var217, var247, var264, var274, var278, var282);
                boolean var149 = var123.add(var180);
                if (!var123.isEmpty()) {
                    int var157 = computeColorOrIndex(this.getBlockDiffColor());
                    int var210 = (1845493760 | var157);
                    int var221 = (-16777216 | var157);
                    ClarityRenderUtils.drawBoxes(arg0, var123, var210, var221);
                }
            } catch (Exception ignored) {
            }
            Object var49 = var132;
        }
        if (this.isGhostEntities()) {
            if (this.isGhostESP()) {
                if (!this.enabledState2) {
                }
            }
            return;
            Object var57 = this.LOCK;
            try {
                if (this.entriesMap1.isEmpty()) {
                }
            } catch (Throwable ignored) {
            }
            return;
            try {
                Set var186 = this.entriesMap1.entrySet();
                ArrayList var65 = new ArrayList(var186);
            } catch (Throwable ignored) {
            }
            try {
                int var69 = computeColorOrIndex(this.getGhostColor());
                int var188 = this.getGhostAlpha();
                int var160 = Math.min(255, var188);
                int var71 = Math.max(0, var160);
                int var75 = ((Math.min(120, var71) << 24) | var69);
                int var165 = (var71 + 140);
                int var79 = ((Math.min(255, var165) << 24) | var69);
                int var191 = var65.size();
                ArrayList var80 = new ArrayList(var191);
                Iterator var82 = var65.iterator();
                while (true) {
                    if (!var82.hasNext()) break;
                    Map.Entry var87 = ((Map.Entry) var82.next());
                    ClientLevel var89 = var3.level;
                    int var171 = ((Integer) var87.getKey());
                    Entity var90 = var89.getEntity(var171);
                    if (var90 == null) {
                    }
                    PlayerGhostSnapshot var95 = ((PlayerGhostSnapshot) var87.getValue());
                    double var97 = ((double) arg1);
                    double var193 = var95.doubleValue3;
                    double var222 = var95.doubleValue;
                    double var99 = Mth.lerp(var97, var193, var222);
                    double var102 = ((double) arg1);
                    double var196 = var95.doubleValue4;
                    double var225 = var95.doubleValue1;
                    double var104 = Mth.lerp(var102, var196, var225);
                    double var107 = ((double) arg1);
                    double var199 = var95.doubleValue5;
                    double var228 = var95.doubleValue2;
                    double var109 = Mth.lerp(var107, var199, var228);
                    AABB var173 = var90.getBoundingBox();
                    double var231 = var90.getX();
                    double var203 = (var99 - var231);
                    double var249 = var90.getY();
                    double var235 = (var104 - var249);
                    double var266 = var90.getZ();
                    double var253 = (var109 - var266);
                    AABB var174 = var173.move(var203, var235, var253);
                    boolean var112 = var80.add(var174);
                    double var117 = (((double) var90.getBbHeight()) * 0.5);
                    double var211 = var90.getX();
                    double var243 = (var90.getY() + var117);
                    double var260 = var90.getZ();
                    Vec3 var175 = new Vec3(var211, var243, var260);
                    double var257 = (var104 + var117);
                    Vec3 var207 = new Vec3(var99, var257, var109);
                    ClarityRenderUtils.drawWorldLine(arg0, var175, var207, var79, 2.0F);
                }
                if (!var80.isEmpty()) {
                    ClarityRenderUtils.drawBoxes(arg0, var80, var75, var79);
                }
            } catch (Exception ignored) {
            }
            Object var92 = var57;
            return;
            Object var62 = var57;
            throw var62;
        }
    }

    private static int computeColorOrIndex(String arg0) {
        return ThemeColors.resolveRgb(arg0, 6599935, false);
    }

    static {
        HashMap var0 = new HashMap();
        entriesMap = var0;
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundSystemChatPacket"), 1, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundPlayerChatPacket"), 2, 0);
        applyStep(evaluate("net.minecraft.network.protocol.common.ClientboundDisconnectPacket"), 3, 0);
        applyStep(evaluate("net.minecraft.network.protocol.common.ClientboundKeepAlivePacket"), 5, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundRespawnPacket"), 6, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket"), 7, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundAnimatePacket"), 8, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundSoundPacket"), 4, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundSoundEntityPacket"), 4, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundStopSoundPacket"), 4, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket"), 4, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundLevelEventPacket"), 4, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundBossEventPacket"), 4, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundTabListPacket"), 4, 0);
        Class var31 = evaluate("net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket");
        applyStep(var31, 4, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket"), 4, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket"), 4, 0);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundClearTitlesPacket"), 4, 0);
        applyStep(evaluate("net.minecraft.network.protocol.common.ClientboundPingPacket"), 0, 1);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundMoveEntityPacket$Pos"), 0, 2);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundMoveEntityPacket$PosRot"), 0, 2);
        Class var45 = evaluate("net.minecraft.network.protocol.game.ClientboundMoveEntityPacket$Rot");
        applyStep(var45, 0, 2);
        Class var47 = evaluate("net.minecraft.network.protocol.game.ClientboundEntityPositionSyncPacket");
        applyStep(var47, 0, 2);
        Class var49 = evaluate("net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket");
        applyStep(var49, 0, 2);
        Class var51 = evaluate("net.minecraft.network.protocol.game.ClientboundRotateHeadPacket");
        applyStep(var51, 0, 2);
        Class var53 = evaluate("net.minecraft.network.protocol.game.ClientboundAddEntityPacket");
        applyStep(var53, 0, 4);
        Class var55 = evaluate("net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket");
        applyStep(var55, 0, 4);
        Class var57 = evaluate("net.minecraft.network.protocol.game.ClientboundSetPassengersPacket");
        applyStep(var57, 0, 4);
        Class var59 = evaluate("net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket");
        applyStep(var59, 0, 4);
        Class var61 = evaluate("net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket");
        applyStep(var61, 0, 8);
        Class var63 = evaluate("net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket");
        applyStep(var63, 0, 8);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket"), 0, 8);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundRemoveMobEffectPacket"), 0, 8);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket"), 0, 8);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket"), 0, 16);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundExplodePacket"), 0, 16);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket"), 0, 32);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket"), 0, 32);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket"), 0, 32);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundBlockEventPacket"), 0, 32);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket"), 0, 32);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundGameEventPacket"), 0, 64);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket"), 0, 64);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundSetCameraPacket"), 0, 8192);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket"), 0, 128);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundContainerSetContentPacket"), 0, 128);
        Class var95 = evaluate("net.minecraft.network.protocol.game.ClientboundSetHeldSlotPacket");
        applyStep(var95, 0, 4096);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundCooldownPacket"), 0, 2048);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundEntityEventPacket"), 0, 256);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundHurtAnimationPacket"), 0, 256);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundSetHealthPacket"), 0, 512);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundSetExperiencePacket"), 0, 1024);
        applyStep(evaluate("net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket"), 0, 16384);
    }

}
