package need.clarity.modules.visual;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import need.clarity.PerPacketEvents;
import need.clarity.WorldRenderEvent;
import need.clarity.config.ClarityConfig;
import need.clarity.event.EventPacketReceive;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Search extends ModuleBase {
    private static Search INSTANCE;
    private final Map entriesMap;
    private final Set entriesSet;
    private static final ExecutorService executorService;
    private volatile Set entriesSet1;
    private volatile boolean enabledState;
    private final Consumer fieldVal;
    private final WorldRenderEvent.Listener fieldVal1;

    public Search() {
        super("Search", "Visual");
        HashMap var11 = new HashMap();
        this.entriesMap = var11;
        HashSet var12 = new HashSet();
        Set var13 = Collections.synchronizedSet(var12);
        this.entriesSet = var13;
        Set var14 = Collections.emptySet();
        this.entriesSet1 = var14;
        this.enabledState = false;
        Consumer var17 = (p0) -> this.onPacketReceive(p0);
        this.fieldVal = var17;
        WorldRenderEvent.Listener var19 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal1 = var19;
        INSTANCE = this;
    }

    public void onEnable() {
        PerPacketEvents.register(this.fieldVal);
        WorldRenderEvent.register(this.fieldVal1);
        Set var8 = this.evaluate();
        this.entriesSet1 = var8;
        this.applyStep1();
    }

    public void onDisable() {
        PerPacketEvents.unregister(this.fieldVal);
        WorldRenderEvent.unregister(this.fieldVal1);
        this.applyStep();
        this.entriesSet.clear();
        Set var2 = Collections.emptySet();
        this.entriesSet1 = var2;
    }

    public void onTick() {
        int var17 = 0;
        int var18 = 0;
        LevelChunk var101 = null;
        LevelChunk var105 = null;
        LevelChunk var125 = null;
        LevelChunk var126 = null;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.level != null) {
            if (var1.player == null) {
            }
        }
        return;
        Boolean var166 = false;
        boolean var33 = ((Boolean) this.getSetting("Only Visible Blocks", var166));
        Set var35 = this.evaluate();
        Set var128 = this.entriesSet1;
        if (var35.equals(var128)) {
            boolean var130 = this.enabledState;
            if (var33 != var130) {
            }
        }
        this.entriesSet1 = var35;
        this.enabledState = var33;
        this.applyStep();
        if (this.entriesSet1.isEmpty()) {
            return;
        }
        Set var46 = this.entriesSet;
        try {
            Set var168 = this.entriesSet;
            HashSet var47 = new HashSet(var168);
            this.entriesSet.clear();
        } catch (Throwable ignored) {
        }
        Iterator var55 = var47.iterator();
        while (true) {
            if (!var55.hasNext()) break;
            ChunkPos var60 = ((ChunkPos) var55.next());
            SearchChunkScanner var64 = ((SearchChunkScanner) this.entriesMap.remove(var60));
            if (var64 == null) continue;
            var64.applyStep();
        }
        int var72 = ((Integer) var1.options.renderDistance().get());
        int var74 = (var72 + 2);
        ChunkPos var77 = var1.player.chunkPosition();
        Set var80 = this.entriesMap.entrySet();
        Predicate var136 = (p0) -> Search.lambda$a(var77, var74, var1, p0);
        boolean var81 = var80.removeIf(var136);
        var17 = -(var72);
        while (true) {
            if (var17 > var72) break;
            var18 = -(var72);
            while (true) {
                if (var18 > var72) break;
                int var171 = var77.x();
                int var203 = var17;
                int var172 = (var171 + var203);
                int var205 = var77.z();
                int var5 = var18;
                int var206 = (var205 + var5);
                ChunkPos var88 = new ChunkPos(var172, var206);
                if (this.entriesMap.containsKey(var88)) {
                } else {
                    ClientLevel var93 = var1.level;
                    int var141 = var88.x();
                    int var174 = var88.z();
                    if (!(var93.hasChunk(var141, var174))) {
                    } else {
                        ClientLevel var96 = var1.level;
                        int var143 = var88.x();
                        int var176 = var88.z();
                        LevelChunk var97 = var96.getChunk(var143, var176);
                        ClientLevel var99 = var1.level;
                        int var145 = var88.x();
                        int var179 = (var88.z() - 1);
                        if (var99.hasChunk(var145, var179)) {
                            ClientLevel var124 = var1.level;
                            int var165 = var88.x();
                            int var202 = (var88.z() - 1);
                            var101 = var124.getChunk(var165, var202);
                        } else {
                            var101 = null;
                        }
                        LevelChunk var21 = var101;
                        ClientLevel var103 = var1.level;
                        int var147 = var88.x();
                        int var182 = (var88.z() + 1);
                        if (var103.hasChunk(var147, var182)) {
                            ClientLevel var122 = var1.level;
                            int var163 = var88.x();
                            int var199 = (var88.z() + 1);
                            var105 = var122.getChunk(var163, var199);
                        } else {
                            var105 = null;
                        }
                        LevelChunk var22 = var105;
                        ClientLevel var107 = var1.level;
                        int var150 = (var88.x() - 1);
                        int var185 = var88.z();
                        if (var107.hasChunk(var150, var185)) {
                            ClientLevel var110 = var1.level;
                            int var153 = (var88.x() - 1);
                            int var188 = var88.z();
                            var125 = var110.getChunk(var153, var188);
                        } else {
                            var125 = null;
                        }
                        LevelChunk var23 = var125;
                        ClientLevel var112 = var1.level;
                        int var156 = (var88.x() + 1);
                        int var191 = var88.z();
                        if (var112.hasChunk(var156, var191)) {
                            ClientLevel var120 = var1.level;
                            int var161 = (var88.x() + 1);
                            int var196 = var88.z();
                            var126 = var120.getChunk(var161, var196);
                        } else {
                            var126 = null;
                        }
                        LevelChunk var24 = var126;
                        Set var213 = this.entriesSet1;
                        SearchChunkScanner var114 = new SearchChunkScanner(var97, var21, var22, var23, var24, var213, var33);
                        Object var117 = this.entriesMap.put(var88, var114);
                        ExecutorService var158 = executorService;
                        var114.applyStep(var158);
                    }
                    var18 = (var18 + 1);
                }
            }
            var17 = (var17 + 1);
        }
        return;
        Set var51 = var46;
        throw var51;
    }

    private void onPacketReceive(EventPacketReceive arg0) {
        ChunkPos var6 = evaluate(arg0.getPacket());
        if (var6 != null) {
            boolean var10 = this.entriesSet.add(var6);
        }
    }

    private static ChunkPos evaluate(Packet arg0) {
        if ((arg0 instanceof ClientboundLevelChunkWithLightPacket)) {
            ClientboundLevelChunkWithLightPacket var8 = ((ClientboundLevelChunkWithLightPacket) arg0);
            int var30 = var8.getX();
            int var32 = var8.getZ();
            ChunkPos var9 = new ChunkPos(var30, var32);
            return var9;
        }
        if ((arg0 instanceof ClientboundBlockUpdatePacket)) {
            return ChunkPos.containing(((ClientboundBlockUpdatePacket) arg0).getPos());
        }
        if ((arg0 instanceof ClientboundSectionBlocksUpdatePacket)) {
            ClientboundSectionBlocksUpdatePacket var16 = ((ClientboundSectionBlocksUpdatePacket) arg0);
            ChunkPos[] var18 = new ChunkPos[1];
            var18[0] = null;
            BiConsumer var28 = (p0, p1) -> Search.lambda$a(var18, p0, p1);
            var16.runUpdates(var28);
            return var18[0];
        }
        return null;
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        HashMap var125 = null;
        if (this.entriesMap.isEmpty()) {
            return;
        }
        Boolean var150 = true;
        boolean var37 = ((Boolean) this.getSetting("Show Fill", var150));
        Boolean var152 = true;
        boolean var41 = ((Boolean) this.getSetting("Show Outline", var152));
        Integer var154 = 80;
        int var45 = ((Integer) this.getSetting("Fill Opacity", var154));
        Integer var156 = 200;
        int var49 = ((Integer) this.getSetting("Outline Opacity", var156));
        Boolean var158 = false;
        boolean var53 = ((Boolean) this.getSetting("Show Tracers", var158));
        Float var160 = 2.0F;
        float var57 = ((Float) this.getSetting("Tracer Thickness", var160));
        JsonObject var59 = ClarityConfig.getBlockColors("Search");
        HashMap var60 = new HashMap();
        if (var53) {
            var125 = new HashMap();
        } else {
            var125 = null;
        }
        HashMap var22 = var125;
        Iterator var65 = this.entriesMap.values().iterator();
        while (true) {
            if (!var65.hasNext()) break;
            Iterator var74 = ((SearchChunkScanner) var65.next()).evaluate().iterator();
            while (true) {
                if (!var74.hasNext()) continue;
            }
        }
        SearchBlockEntry var79 = ((SearchBlockEntry) var74.next());
        int var82 = computeColorOrIndex(var79.block(), var59);
        int var134 = ((var82 >> 16) & 255);
        int var165 = ((var82 >> 8) & 255);
        int var179 = (var82 & 255);
        Integer var136 = computeColorOrIndex(var45, var134, var165, var179);
        Function var166 = (p0) -> Search.lambda$b(p0);
        List var87 = ((List) var60.computeIfAbsent(var136, var166));
        AABB var138 = var79.box();
        boolean var88 = var87.add(var138);
        if (!var53) continue;
        if (var22 == null) continue;
        int var141 = ((var82 >> 16) & 255);
        int var171 = ((var82 >> 8) & 255);
        int var183 = (var82 & 255);
        int var92 = computeColorOrIndex(var49, var141, var171, var183);
        AABB var94 = var79.box();
        Integer var143 = var92;
        Function var172 = (p0) -> Search.lambda$a(p0);
        List var97 = ((List) var22.computeIfAbsent(var143, var172));
        double var187 = (var94.minX + 0.5);
        double var200 = (var94.minY + 0.5);
        double var206 = (var94.minZ + 0.5);
        Vec3 var144 = new Vec3(var187, var200, var206);
        boolean var98 = var97.add(var144);
        Iterator var101 = var60.entrySet().iterator();
        while (true) {
            if (!var101.hasNext()) break;
            Map.Entry var117 = ((Map.Entry) var101.next());
            int var121 = ((Integer) var117.getKey());
            int var123 = computeColorOrIndex(var121, var49);
            List var149 = ((List) var117.getValue());
            ClarityRenderUtils.drawBoxes(arg0, var149, var121, var123, var37, var41);
        }
        if (var53) {
            if (var22 != null) {
                Iterator var108 = var22.entrySet().iterator();
                while (true) {
                    if (!var108.hasNext()) break;
                    Map.Entry var113 = ((Map.Entry) var108.next());
                    List var175 = ((List) var113.getValue());
                    int var192 = ((Integer) var113.getKey());
                    ClarityRenderUtils.drawTracers(arg0, arg1, var175, var192, var57);
                }
            }
            return;
        }
    }

    private void applyStep() {
        Collection var4 = this.entriesMap.values();
        Consumer var2 = SearchChunkScanner::applyStep;
        var4.forEach(var2);
        this.entriesMap.clear();
    }

    private void applyStep1() {
        this.applyStep();
    }

    private Set evaluate() {
        String var9 = null;
        try {
            JsonObject var11 = ClarityConfig.getCurrent().getAsJsonObject("Search");
            if (var11 == null) {
                Set var13 = Collections.emptySet();
            }
        } catch (Exception ignored) {
        }
        return var13;
        try {
            JsonObject var17 = var11.getAsJsonObject("values");
            if (var17 == null) {
                Set var19 = Collections.emptySet();
            }
        } catch (Exception ignored) {
        }
        return var19;
        try {
            JsonElement var21 = var17.get("Blocks");
            if (var21 != null) {
                if (!var21.isJsonArray()) {
                }
            }
            Set var50 = Collections.emptySet();
        } catch (Exception ignored) {
        }
        return var50;
        try {
            HashSet var25 = new HashSet();
            Iterator var28 = var21.getAsJsonArray().iterator();
            while (true) {
                if (!var28.hasNext()) break;
                JsonElement var33 = ((JsonElement) var28.next());
                if (!var33.isJsonPrimitive()) {
                }
                try {
                    var9 = var33.getAsString();
                    if (!var9.contains(":")) {
                        var9 = "minecraft:" + (var9);
                    }
                    DefaultedRegistry var42 = BuiltInRegistries.BLOCK;
                    Identifier var55 = Identifier.parse(var9);
                    Block var44 = ((Block) var42.getValue(var55));
                    Block var56 = Blocks.AIR;
                    if (var44 == var56) continue;
                    boolean var47 = var25.add(var44);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }
        return var25;
    }

    private static int computeColorOrIndex(Block arg0, JsonObject arg1) {
        int var23 = 0;
        String var7 = null;
        String var31 = null;
        String var9 = BuiltInRegistries.BLOCK.getKey(arg0).toString();
        if (var9.contains(":")) {
            var31 = var9.split(":", 2)[1];
        } else {
            var31 = var9;
        }
        String var6 = var31;
        var7 = null;
        if (arg1.has(var6)) {
            var7 = arg1.get(var6).getAsString();
        } else {
            if (arg1.has(var9)) {
                var7 = arg1.get(var9).getAsString();
            }
            if (var7 == null) {
                if (ThemeColors.isEnabled()) {
                    int var36 = ThemeColors.getAccentRgb();
                    var23 = (-16777216 | var36);
                }
            } else {
                return ThemeColors.resolveArgb(var7, 16758062, false);
                var23 = 16758062;
            }
            return var23;
        }
    }

    private static int computeColorOrIndex(int arg0, int arg1, int arg2, int arg3) {
        int var8 = ((arg0 & 255) << 24);
        int var15 = ((arg1 & 255) << 16);
        int var9 = (var8 | var15);
        int var18 = ((arg2 & 255) << 8);
        int var10 = (var9 | var18);
        int var20 = (arg3 & 255);
        return (var10 | var20);
    }

    private static int computeColorOrIndex(int arg0, int arg1) {
        int var5 = (arg0 & 16777215);
        int var9 = ((arg1 & 255) << 24);
        return (var5 | var9);
    }

    public static Search evaluate() {
        return INSTANCE;
    }

    static {
        int var6 = (Runtime.getRuntime().availableProcessors() - 1);
        int var3 = Math.max(1, var6);
        ThreadFactory var7 = (p0) -> Search.lambda$a(p0);
        executorService = Executors.newFixedThreadPool(var3, var7);
    }

}
