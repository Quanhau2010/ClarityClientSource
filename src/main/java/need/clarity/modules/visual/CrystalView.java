package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.function.Predicate;
import need.clarity.PerPacketEvents;
import need.clarity.WorldRenderEvent;
import need.clarity.api.Hooks;
import need.clarity.event.EventPacketReceive;
import need.clarity.modules.ModuleBase;
import need.clarity.render.CrystalRenderer;
import need.clarity.util.ClaritySounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.phys.Vec3;

public class CrystalView extends ModuleBase {
    public static CrystalView INSTANCE;
    public final Map entriesMap;
    public final List entriesList;
    private final ConcurrentLinkedQueue fieldVal;
    private final WorldRenderEvent.Listener fieldVal1;
    private boolean enabledState;
    private boolean enabledState1;
    public static final Set entriesSet;

    public CrystalView() {
        super("Crystal View", "Visual");
        HashMap var18 = new HashMap();
        this.entriesMap = var18;
        ArrayList var19 = new ArrayList();
        this.entriesList = var19;
        ConcurrentLinkedQueue var20 = new ConcurrentLinkedQueue();
        this.fieldVal = var20;
        WorldRenderEvent.Listener var22 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal1 = var22;
        this.enabledState = false;
        this.enabledState1 = true;
        INSTANCE = this;
        PerPacketEvents.register((p0) -> this.onPacketReceive(p0));
        Hooks.Bool0 var25 = () -> CrystalView.active();
        Hooks.bool0("CrystalView.active", var25);
        Hooks.Bool1 var26 = (p0) -> CrystalView.hook$active(p0);
        Hooks.bool1("CrystalView.shouldMute", var26);
        if (this.isEnabled()) {
            this.onEnable();
        }
    }

    public boolean isEligible() {
        Set var1 = entriesSet;
        String var4 = this.getStyle();
        return var1.contains(var4);
    }

    public static boolean active() {
        int var7 = 0;
        CrystalView var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                if (!var0.isEligible()) {
                    var7 = 1;
                }
            }
        } else {
            var7 = 0;
        }
        return var7;
    }

    public static boolean isEligible(double arg0, double arg1, double arg2) {
        CrystalView var6 = INSTANCE;
        Minecraft var29 = Minecraft.getInstance();
        if (var6 != null) {
            if (var29.level == null) {
            }
        }
        return false;
        Iterator var36 = var29.level.entitiesForRendering().iterator();
        while (true) {
            if (!var36.hasNext()) break;
            Entity var41 = ((Entity) var36.next());
            if (!(var41 instanceof EndCrystal)) continue;
            if (Double.compare(var41.distanceToSqr(arg0, arg1, arg2), 6.25) >= 0) continue;
        }
        return true;
        long var49 = System.nanoTime();
        Iterator var53 = var6.entriesList.iterator();
        while (true) {
            if (!var53.hasNext()) break;
            CrystalPopEffectEntry var58 = ((CrystalPopEffectEntry) var53.next());
            double var62 = (var58.doubleValue3 - arg0);
            double var67 = (var58.doubleValue4 - arg1);
            double var72 = (var58.doubleValue5 - arg2);
            long var98 = var58.timestampMs;
            if (Long.compare((var49 - var98), 800000000L) >= 0) continue;
            double var81 = (var62 * var62);
            double var106 = (var67 * var67);
            double var83 = (var81 + var106);
            double var110 = (var72 * var72);
            if (Double.compare((var83 + var110), 6.25) >= 0) continue;
        }
        return true;
        return false;
    }

    public static boolean isEligible1(double arg0, double arg1, double arg2) {
        CrystalView var6 = INSTANCE;
        if (var6 != null) {
            if (var6.isEnabled()) {
                Boolean var23 = true;
                if (!((Boolean) var6.getSetting("Hide Vanilla Particles", var23))) {
                }
            }
            return false;
            return isEligible(arg0, arg1, arg2);
        }
    }

    public void onEnable() {
        if (!this.enabledState) {
            WorldRenderEvent.register(this.fieldVal1);
            this.enabledState = true;
        }
        this.enabledState1 = true;
    }

    public void onDisable() {
        if (this.enabledState) {
            WorldRenderEvent.unregister(this.fieldVal1);
            this.enabledState = false;
        }
        this.entriesMap.clear();
        this.entriesList.clear();
        this.fieldVal.clear();
    }

    private void onPacketReceive(EventPacketReceive arg0) {
        if (!this.isEnabled()) {
            return;
        }
        Packet var10 = arg0.getPacket();
        if ((var10 instanceof ClientboundExplodePacket)) {
            Vec3 var16 = ((ClientboundExplodePacket) var10).center();
            ConcurrentLinkedQueue var18 = this.fieldVal;
            double[] var20 = new double[3];
            double var23 = var16.x;
            var20[0] = var23;
            double var26 = var16.y;
            var20[1] = var26;
            double var29 = var16.z;
            var20[2] = var29;
            boolean var19 = var18.add(var20);
        }
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        int var184 = 0;
        int var195 = 0;
        float var116 = 0.0F;
        Minecraft var3 = Minecraft.getInstance();
        if (this.isEnabled()) {
            if (var3.level != null) {
                if (var3.player == null) {
                }
            }
            this.entriesMap.clear();
            this.entriesList.clear();
            return;
            long var41 = System.nanoTime();
            Iterator var46 = this.entriesMap.values().iterator();
            while (true) {
                if (!var46.hasNext()) break;
                ((CrystalTrackState) var46.next()).enabledState1 = false;
            }
            if (!this.enabledState1) {
                Boolean var250 = true;
                if (((Boolean) this.getSetting("Spawn Animation", var250))) {
                    var195 = 1;
                }
            } else {
                var195 = 0;
            }
            int var330 = var195;
            Iterator var58 = var3.level.entitiesForRendering().iterator();
            while (true) {
                if (!var58.hasNext()) break;
                Entity var63 = ((Entity) var58.next());
                if (!(var63 instanceof EndCrystal)) continue;
                EndCrystal var67 = ((EndCrystal) var63);
                Map var69 = this.entriesMap;
                Integer var202 = var67.getId();
                Function var252 = (p0) -> CrystalView.lambda$a(var67, var41, var330, p0);
                CrystalTrackState var71 = ((CrystalTrackState) var69.computeIfAbsent(var202, var252));
                var71.endCrystal = var67;
                var71.enabledState1 = true;
                double var75 = ((double) arg1);
                double var254 = var67.xo;
                double var313 = var67.getX();
                double var77 = Mth.lerp(var75, var254, var313);
                double var80 = ((double) arg1);
                double var257 = var67.yo;
                double var316 = var67.getY();
                double var82 = Mth.lerp(var80, var257, var316);
                double var85 = ((double) arg1);
                double var260 = var67.zo;
                double var319 = var67.getZ();
                double var87 = Mth.lerp(var85, var260, var319);
                float var92 = (((float) var67.time) + arg1);
                var71.floatValue1 = var92;
                float var100 = ((Mth.sin(((double) (var92 * 0.2F))) / 2.0F) + 0.5F);
                Boolean var263 = true;
                if (((Boolean) this.getSetting("Bob", var263))) {
                    float var115 = (((var100 * var100) + var100) * 0.4F);
                    var116 = (var115 * 0.55F);
                } else {
                    var116 = 0.2F;
                }
                float var30 = var116;
                var71.doubleValue3 = var77;
                var71.doubleValue4 = var82;
                var71.doubleValue5 = var87;
                var71.doubleValue = var77;
                double var221 = (var82 + 1.2);
                double var302 = ((double) var30);
                double var223 = (var221 + var302);
                var71.doubleValue1 = var223;
                var71.doubleValue2 = var87;
                float var228 = (var92 * 0.0523599F);
                Float var322 = 1.0F;
                float var268 = ((Float) this.getSetting("Spin Speed", var322));
                float var229 = (var228 * var268);
                float var271 = (var71.floatValue2 * 6.2832F);
                float var230 = (var229 + var271);
                var71.floatValue = var230;
            }
            this.enabledState1 = false;
            while (true) {
                ConcurrentLinkedQueue var119 = this.fieldVal;
                Object var120 = var119.poll();
                double[] var121 = ((double[]) var120);
                if (var121 == null) break;
                Map var123 = this.entriesMap;
                Collection var124 = var123.values();
                Iterator var125 = var124.iterator();
                while (true) {
                    boolean var127 = var125.hasNext();
                    if (!var127) continue;
                    CrystalTrackState var130 = ((CrystalTrackState) var125.next());
                    if (var130.enabledState) {
                    }
                    double var134 = var130.doubleValue3;
                    double var273 = var121[0];
                    double var136 = (var134 - var273);
                    double var139 = var130.doubleValue4;
                    double var276 = var121[1];
                    double var141 = (var139 - var276);
                    double var144 = var130.doubleValue5;
                    double var279 = var121[2];
                    double var146 = (var144 - var279);
                    double var150 = (var136 * var136);
                    double var285 = (var141 * var141);
                    double var152 = (var150 + var285);
                    double var289 = (var146 * var146);
                    if (Double.compare((var152 + var289), 1.0) >= 0) continue;
                }
                this.applyStep(var130, var41);
            }
            Iterator var161 = this.entriesMap.values().iterator();
            while (true) {
                if (!var161.hasNext()) break;
                CrystalTrackState var172 = ((CrystalTrackState) var161.next());
                if (var172.enabledState1) {
                    if (!var172.endCrystal.isRemoved()) {
                    }
                }
                Entity.RemovalReason var180 = var172.endCrystal.getRemovalReason();
                Entity.RemovalReason var242 = Entity.RemovalReason.UNLOADED_TO_CHUNK;
                if (var180 != var242) {
                    Entity.RemovalReason var243 = Entity.RemovalReason.UNLOADED_WITH_PLAYER;
                    if (var180 != var243) {
                        Entity.RemovalReason var244 = Entity.RemovalReason.CHANGED_DIMENSION;
                        if (var180 == var244) {
                        }
                    }
                    var184 = 1;
                    var184 = 0;
                    int var341 = var184;
                    if (!var172.enabledState) {
                        if (var341 == 0) {
                            LocalPlayer var189 = var3.player;
                            double var246 = var172.doubleValue3;
                            double var311 = var172.doubleValue4;
                            double var328 = var172.doubleValue5;
                            if (Double.compare(var189.distanceToSqr(var246, var311, var328), 16384.0) < 0) {
                                this.applyStep(var172, var41);
                            }
                            var161.remove();
                        }
                    }
                }
            }
            float var165 = this.getPopDuration();
            List var167 = this.entriesList;
            Predicate var239 = (p0) -> CrystalView.lambda$a(var41, var165, p0);
            boolean var168 = var167.removeIf(var239);
            CrystalRenderer.draw(this, var41);
            return;
        }
    }

    private void applyStep(CrystalTrackState arg0, long arg1) {
        arg0.enabledState = true;
        Boolean var24 = true;
        if (((Boolean) this.getSetting("Pop Effect", var24))) {
            List var16 = this.entriesList;
            CrystalPopEffectEntry var20 = new CrystalPopEffectEntry(arg0, arg1);
            boolean var17 = var16.add(var20);
        }
        double var22 = arg0.doubleValue3;
        double var26 = arg0.doubleValue4;
        double var28 = arg0.doubleValue5;
        this.applyStep(var22, var26, var28);
    }

    private void applyStep(double arg0, double arg1, double arg2) {
        int var22 = 0;
        SoundEvent var63 = null;
        String var25 = ((String) this.getSetting("Pop Sound", "Shatter"));
        var22 = -1;
        switch (var25.hashCode()) {
            case -1725900883 -> {}
            case -1582753002 -> {}
            case -576022087 -> {}
            case -541882737 -> {}
            case 65078556 -> {}
            case 68884316 -> {}
            default -> {}
        }
        if (var25.equals("Chime")) {
            var22 = 4;
            if (var25.equals("Shatter")) {
                var22 = 0;
                if (var25.equals("Crystal")) {
                    var22 = 1;
                    if (var25.equals("Glass")) {
                        var22 = 2;
                        if (var25.equals("Kill Pop")) {
                            var22 = 3;
                            if (var25.equals("Amethyst")) {
                                var22 = 5;
                            }
                            switch (var22) {
                                case 0 -> {}
                                case 1 -> {}
                                case 2 -> {}
                                case 3 -> {}
                                case 4 -> {}
                                case 5 -> {}
                                default -> {}
                            }
                            var63 = SoundEvents.AMETHYST_CLUSTER_BREAK;
                            var63 = ClaritySounds.raw("kill.shatter");
                            var63 = SoundEvents.AMETHYST_BLOCK_RESONATE;
                            var63 = ClaritySounds.raw("kill.killpop");
                            var63 = ClaritySounds.raw("hit.crystal");
                            var63 = ClaritySounds.raw("hit.glass2");
                            var63 = null;
                            SoundEvent var20 = var63;
                            if (var20 == null) {
                                return;
                            }
                            Minecraft var36 = Minecraft.getInstance();
                            Float var78 = 1.0F;
                            float var40 = ((Float) this.getSetting("Volume", var78));
                            float var69 = (((float) Math.random()) * 0.12F);
                            float var42 = (0.94F + var69);
                            ClientLevel var44 = var36.level;
                            SoundSource var15 = SoundSource.BLOCKS;
                            var44.playLocalSound(arg0, arg1, arg2, var20, var15, var40, var42, false);
                            return;
                        }
                    }
                }
            }
        }
    }

    public static boolean shouldMute(SoundInstance arg0) {
        CrystalView var1 = INSTANCE;
        if (var1 != null) {
            if (var1.isEnabled()) {
                Boolean var27 = false;
                if (!((Boolean) var1.getSetting("Mute Vanilla Boom", var27))) {
                }
            }
            return false;
            try {
                String var26 = arg0.getIdentifier().getPath();
                if (!"entity.generic.explode".equals(var26)) {
                }
            } catch (Throwable ignored) {
            }
            return false;
            try {
                double var17 = arg0.getX();
                double var29 = arg0.getY();
                double var31 = arg0.getZ();
                boolean var19 = isEligible(var17, var29, var31);
            } catch (Throwable ignored) {
            }
            return var19;
        }
    }

    public String getStyle() {
        return ((String) this.getSetting("Style", "Prism Gem"));
    }

    public String getColorMode() {
        return ((String) this.getSetting("Color Mode", "Theme"));
    }

    public String getColor() {
        return ((String) this.getSetting("Color", "#7AA2F7"));
    }

    public String getSecondColour() {
        return ((String) this.getSetting("Second Colour", "#3D59A1"));
    }

    public float getSize() {
        Float var7 = 1.0F;
        return ((Float) this.getSetting("Size", var7));
    }

    public float getGlow() {
        Float var7 = 1.0F;
        return ((Float) this.getSetting("Glow", var7));
    }

    public boolean isThroughWalls() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Through Walls", var7));
    }

    public boolean isGroundRing() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Ground Ring", var7));
    }

    public float getPopDuration() {
        Float var7 = 0.7F;
        return ((Float) this.getSetting("Pop Duration", var7));
    }

    public float getPopSize() {
        Float var7 = 1.0F;
        return ((Float) this.getSetting("Pop Size", var7));
    }

    static {
        entriesSet = Set.of("Energy Edges", "Hologram", "Galaxy", "Glint");
    }

}
