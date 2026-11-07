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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import need.clarity.WorldRenderEvent;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BlockESP extends ModuleBase {
    public static BlockESP INSTANCE;
    private final Map entriesMap;
    private static final ExecutorService executorService;
    private volatile Set entriesSet;
    private volatile boolean enabledState;
    private final WorldRenderEvent.Listener fieldVal;

    public BlockESP() {
        super("Block ESP", "Visual");
        HashMap var14 = new HashMap();
        this.entriesMap = var14;
        Set var15 = Collections.emptySet();
        this.entriesSet = var15;
        this.enabledState = false;
        WorldRenderEvent.Listener var18 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var18;
        INSTANCE = this;
        if (this.isEnabled()) {
            WorldRenderEvent.register(this.fieldVal);
            Set var20 = this.evaluate();
            this.entriesSet = var20;
        }
    }

    public void onEnable() {
        WorldRenderEvent.register(this.fieldVal);
        Set var6 = this.evaluate();
        this.entriesSet = var6;
        this.applyStep1();
    }

    public void onDisable() {
        WorldRenderEvent.unregister(this.fieldVal);
        this.applyStep();
        Set var2 = Collections.emptySet();
        this.entriesSet = var2;
    }

    public void onTick() {
        int var12 = 0;
        int var13 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.level != null) {
                if (var1.player == null) {
                }
            }
            return;
            Boolean var91 = false;
            boolean var25 = ((Boolean) this.getSetting("Only Visible Blocks", var91));
            Set var27 = this.evaluate();
            Set var74 = this.entriesSet;
            if (var27.equals(var74)) {
                boolean var76 = this.enabledState;
                if (var25 != var76) {
                }
            }
            this.entriesSet = var27;
            this.enabledState = var25;
            this.applyStep();
            if (this.entriesSet.isEmpty()) {
                return;
            }
            int var42 = ((Integer) var1.options.renderDistance().get());
            int var44 = (var42 + 2);
            ChunkPos var47 = var1.player.chunkPosition();
            Set var50 = this.entriesMap.entrySet();
            Predicate var81 = (p0) -> BlockESP.lambda$a(var47, var44, var1, p0);
            boolean var51 = var50.removeIf(var81);
            var12 = -(var42);
            while (true) {
                if (var12 > var42) break;
                var13 = -(var42);
                while (true) {
                    if (var13 <= var42) {
                        int var94 = var47.x();
                        int var102 = var12;
                        int var95 = (var94 + var102);
                        int var104 = var47.z();
                        int var5 = var13;
                        int var105 = (var104 + var5);
                        ChunkPos var58 = new ChunkPos(var95, var105);
                        if (this.entriesMap.containsKey(var58)) {
                        } else {
                            ClientLevel var63 = var1.level;
                            int var86 = var58.x();
                            int var97 = var58.z();
                            if (!(var63.hasChunk(var86, var97))) {
                            }
                        }
                    } else {
                        var12 = (var12 + 1);
                    }
                }
            }
            ClientLevel var66 = var1.level;
            int var88 = var58.x();
            int var99 = var58.z();
            LevelChunk var67 = var66.getChunk(var88, var99);
            Set var107 = this.entriesSet;
            BlockESPChunkScanner var68 = new BlockESPChunkScanner(var67, var107, var25);
            Object var71 = this.entriesMap.put(var58, var68);
            ExecutorService var90 = executorService;
            var68.applyStep(var90);
            var13 = (var13 + 1);
            return;
        }
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        int var35 = 0;
        int var44 = 0;
        int var49 = 0;
        JsonObject var70 = null;
        int var250 = 0;
        if (this.entriesMap.isEmpty()) {
            return;
        }
        String var39 = getRender();
        if (!"Fill".equals(var39)) {
            if ("Both".equals(var39)) {
            }
        }
        var44 = 1;
        var44 = 0;
        int var14 = var44;
        if (!"Outline".equals(var39)) {
            if ("Both".equals(var39)) {
            }
        }
        var49 = 1;
        var49 = 0;
        int var15 = var49;
        boolean var50 = isThroughWalls();
        float var51 = getOutlineThickness();
        double var53 = ((double) getMaxDistance());
        String var55 = getColorMode();
        boolean var57 = "Rainbow".equals(var55);
        boolean var59 = "Map".equals(var55);
        float var60 = getRainbowSpeed();
        double var65 = (((double) System.nanoTime()) / 1.0E9);
        Vec3 var67 = ClarityRenderUtils.getCameraPos();
        if ("Per Block".equals(var55)) {
            var70 = ClarityConfig.getBlockColors("Block ESP");
        } else {
            var70 = null;
        }
        JsonObject var27 = var70;
        HashMap var71 = new HashMap();
        Iterator var75 = this.entriesMap.values().iterator();
        while (true) {
            if (!var75.hasNext()) break;
            Iterator var110 = ((BlockESPChunkScanner) var75.next()).evaluate().iterator();
            while (true) {
                if (!var110.hasNext()) continue;
            }
        }
        BlockESPEntry var115 = ((BlockESPEntry) var110.next());
        double var192 = (var115.box().minX + 0.5);
        double var229 = (var115.box().minY + 0.5);
        double var243 = (var115.box().minZ + 0.5);
        Vec3 var116 = new Vec3(var192, var229, var243);
        if (Double.compare(var53, 0.5) > 0) {
            if (Double.compare(var67.distanceTo(var116), var53) > 0) {
            }
        }
        if (var57) {
            double var199 = ((double) var60);
            double var127 = (var65 * var199);
            double var206 = (((double) var115.pos().getX()) * 0.015);
            double var129 = (var127 + var206);
            double var213 = (((double) var115.pos().getZ()) * 0.015);
            float var133 = ((float) (var129 + var213));
            float var176 = ((float) Math.floor(((double) var133)));
            var250 = computeColorOrIndex((((float) Math.round(((var133 - var176) * 20.0F))) / 20.0F), 0.85F, 1.0F);
        } else {
            if (var59) {
                var250 = var115.mapColor();
            } else {
                if (var27 != null) {
                    var250 = computeColorOrIndex(var115.block(), var27);
                } else {
                    var250 = (getColor() & 16777215);
                }
                Integer var182 = (var250 & 16777215);
                Function var217 = (p0) -> BlockESP.lambda$a(p0);
                List var145 = ((List) var71.computeIfAbsent(var182, var217));
                AABB var220 = var115.box();
                ClarityRenderUtils.ESPEntry var183 = new ClarityRenderUtils.ESPEntry(var220, 0);
                boolean var146 = var145.add(var183);
                Iterator var80 = var71.entrySet().iterator();
                while (true) {
                    if (!var80.hasNext()) break;
                    Map.Entry var85 = ((Map.Entry) var80.next());
                    int var89 = ((Integer) var85.getKey());
                    float var163 = getOutlineOpacity();
                    int var91 = computeColorOrIndex(var89, var163);
                    float var164 = getFillOpacity();
                    int var93 = computeColorOrIndex(var89, var164);
                    List var96 = ((List) var85.getValue());
                    var35 = 0;
                    while (true) {
                        int var98 = var35;
                        int var166 = var96.size();
                        if (var98 >= var166) break;
                        int var167 = var35;
                        ClarityRenderUtils.ESPEntry var101 = ((ClarityRenderUtils.ESPEntry) var96.get(var167));
                        int var168 = var35;
                        AABB var221 = var101.box();
                        ClarityRenderUtils.ESPEntry var186 = new ClarityRenderUtils.ESPEntry(var221, var91);
                        Object var103 = var96.set(var168, var186);
                        var35 = (var35 + 1);
                    }
                    ClarityRenderUtils.drawEspBoxes(arg0, var96, var14, var93, var15, var50, false, var51);
                }
                return;
            }
        }
    }

    private void applyStep() {
        Collection var4 = this.entriesMap.values();
        Consumer var2 = BlockESPChunkScanner::applyStep;
        var4.forEach(var2);
        this.entriesMap.clear();
    }

    private void applyStep1() {
        this.applyStep();
    }

    private Set evaluate() {
        String var9 = null;
        try {
            JsonObject var11 = ClarityConfig.getCurrent().getAsJsonObject("Block ESP");
            if (var11 == null) {
                Set var50 = Collections.emptySet();
            }
        } catch (Exception ignored) {
        }
        return var50;
        try {
            JsonObject var14 = var11.getAsJsonObject("values");
            if (var14 == null) {
                Set var49 = Collections.emptySet();
            }
        } catch (Exception ignored) {
        }
        return var49;
        try {
            JsonElement var17 = var14.get("Blocks");
            if (var17 != null) {
                if (!var17.isJsonArray()) {
                }
            }
            Set var48 = Collections.emptySet();
        } catch (Exception ignored) {
        }
        return var48;
        try {
            HashSet var21 = new HashSet();
            Iterator var24 = var17.getAsJsonArray().iterator();
            while (true) {
                if (!var24.hasNext()) break;
                JsonElement var29 = ((JsonElement) var24.next());
                if (!var29.isJsonPrimitive()) {
                }
                try {
                    var9 = var29.getAsString();
                    if (!var9.contains(":")) {
                        var9 = "minecraft:" + (var9);
                    }
                    DefaultedRegistry var40 = BuiltInRegistries.BLOCK;
                    Identifier var55 = Identifier.parse(var9);
                    Block var42 = ((Block) var40.getValue(var55));
                    Block var56 = Blocks.AIR;
                    if (var42 == var56) continue;
                    boolean var45 = var21.add(var42);
                } catch (Exception ignored) {
                }
            }
        } catch (Exception ignored) {
        }
        return var21;
    }

    private static int computeColorOrIndex(Block arg0, JsonObject arg1) {
        String var7 = null;
        String var30 = null;
        String var9 = BuiltInRegistries.BLOCK.getKey(arg0).toString();
        if (var9.contains(":")) {
            var30 = var9.split(":", 2)[1];
        } else {
            var30 = var9;
        }
        String var6 = var30;
        var7 = null;
        if (arg1.has(var6)) {
            var7 = arg1.get(var6).getAsString();
        } else {
            if (arg1.has(var9)) {
                var7 = arg1.get(var9).getAsString();
            }
            if (var7 == null) {
                return (getColor() & 16777215);
            }
            String var21 = var7;
            int var35 = getColor();
            return ThemeColors.resolveRgb(var21, var35, false);
        }
    }

    public static String getRender() {
        return ((String) INSTANCE.getSetting("Render", "Outline"));
    }

    public static String getColorMode() {
        return ((String) INSTANCE.getSetting("Color Mode", "Static"));
    }

    public static int getColor() {
        return ThemeColors.resolveArgb(INSTANCE.getSetting("Color", ""), 16758062, false);
    }

    public static float getRainbowSpeed() {
        BlockESP var0 = INSTANCE;
        Float var6 = 0.3F;
        return ((Float) var0.getSetting("Rainbow Speed", var6));
    }

    public static boolean isThroughWalls() {
        BlockESP var0 = INSTANCE;
        Boolean var6 = true;
        return ((Boolean) var0.getSetting("Through Walls", var6));
    }

    public static float getMaxDistance() {
        BlockESP var0 = INSTANCE;
        Float var6 = 0.0F;
        return ((Float) var0.getSetting("Max Distance", var6));
    }

    public static float getFillOpacity() {
        BlockESP var0 = INSTANCE;
        Float var6 = 0.25F;
        return ((Float) var0.getSetting("Fill Opacity", var6));
    }

    public static float getOutlineOpacity() {
        BlockESP var0 = INSTANCE;
        Float var6 = 0.85F;
        return ((Float) var0.getSetting("Outline Opacity", var6));
    }

    public static float getOutlineThickness() {
        BlockESP var0 = INSTANCE;
        Float var6 = 2.0F;
        return ((Float) var0.getSetting("Outline Thickness", var6));
    }

    private static int computeColorOrIndex(int arg0, float arg1) {
        int var10 = (Math.round((computeFloatMetric(arg1) * 255.0F)) << 24);
        int var14 = (arg0 & 16777215);
        return (var10 | var14);
    }

    private static float computeFloatMetric(float arg0) {
        float var6 = 0.0F;
        if (Float.compare(arg0, 0.0F) < 0) {
            var6 = 0.0F;
        } else {
            if (Float.compare(arg0, 1.0F) > 0) {
                var6 = 1.0F;
            } else {
                var6 = arg0;
            }
            return var6;
        }
    }

    private static int computeColorOrIndex(float arg0, float arg1, float arg2) {
        float var85 = 0.0F;
        float var86 = 0.0F;
        float var87 = 0.0F;
        float var56 = ((float) Math.floor(((double) arg0)));
        float var15 = ((arg0 - var56) * 6.0F);
        int var17 = ((int) var15);
        float var59 = ((float) var17);
        float var19 = (var15 - var59);
        float var61 = (1.0F - arg1);
        float var21 = (arg2 * var61);
        float var77 = (arg1 * var19);
        float var63 = (1.0F - var77);
        float var23 = (arg2 * var63);
        float var84 = (1.0F - var19);
        float var79 = (arg1 * var84);
        float var65 = (1.0F - var79);
        float var25 = (arg2 * var65);
        switch ((var17 % 6)) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            default -> {}
        }
        var85 = var21;
        var86 = var23;
        var87 = arg2;
        var85 = var23;
        var86 = arg2;
        var87 = var21;
        var85 = arg2;
        var86 = var25;
        var87 = var21;
        var85 = var25;
        var86 = var21;
        var87 = arg2;
        var85 = var21;
        var86 = arg2;
        var87 = var25;
        var85 = arg2;
        var86 = var21;
        var87 = var23;
        int var34 = (Math.round((var85 * 255.0F)) << 16);
        int var72 = (Math.round((var86 * 255.0F)) << 8);
        int var35 = (var34 | var72);
        int var75 = Math.round((var87 * 255.0F));
        return (var35 | var75);
    }

    static {
        int var6 = (Runtime.getRuntime().availableProcessors() - 1);
        int var3 = Math.max(1, var6);
        ThreadFactory var7 = (p0) -> BlockESP.lambda$a(p0);
        executorService = Executors.newFixedThreadPool(var3, var7);
    }

}
