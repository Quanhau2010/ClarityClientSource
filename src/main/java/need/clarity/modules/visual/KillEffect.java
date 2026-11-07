package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import need.clarity.WorldRenderEvent;
import need.clarity.api.ChamsApi;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.visual.cosmetics.AnchorTransform;
import need.clarity.modules.visual.cosmetics.CosmeticAnchor;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.EntityPoseState;
import need.clarity.modules.visual.cosmetics.killeffect.ActiveKillEffectInstance;
import need.clarity.modules.visual.cosmetics.killeffect.ActiveKillEffectManager;
import need.clarity.modules.visual.cosmetics.killeffect.IGroundHeightSampler;
import need.clarity.modules.visual.cosmetics.killeffect.KillEffectRenderSpec;
import need.clarity.modules.visual.cosmetics.killeffect.KillEffectStyle;
import need.clarity.render.worldfx.KillFx;
import need.clarity.render.worldfx.ScanFx;
import need.clarity.render.worldfx.ShapeFx;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class KillEffect extends ModuleBase {
    public static KillEffect INSTANCE;
    private final Map entriesMap;
    private final ActiveKillEffectManager activeKillEffectManager;
    private final Map entriesMap1;
    private Object stateObject;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;

    public KillEffect() {
        super("Kill Effect", "Visual");
        HashMap var14 = new HashMap();
        this.entriesMap = var14;
        ActiveKillEffectManager var15 = new ActiveKillEffectManager();
        this.activeKillEffectManager = var15;
        HashMap var16 = new HashMap();
        this.entriesMap1 = var16;
        this.stateObject = null;
        WorldRenderEvent.Listener var19 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var19;
        this.enabledState = false;
        INSTANCE = this;
        if (this.isEnabled()) {
            this.applyStep();
        }
    }

    public void onEnable() {
        this.applyStep();
    }

    public void onDisable() {
        this.applyStep1();
        this.entriesMap.clear();
        this.activeKillEffectManager.applyStep();
        this.entriesMap1.clear();
        this.stateObject = null;
        KillFx.clear();
    }

    private void applyStep() {
        if (this.enabledState) {
            return;
        }
        this.enabledState = true;
        WorldRenderEvent.register(this.fieldVal);
    }

    private void applyStep1() {
        if (!this.enabledState) {
            return;
        }
        this.enabledState = false;
        WorldRenderEvent.unregister(this.fieldVal);
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.player != null) {
                if (var1.level == null) {
                }
            }
            return;
            ClientLevel var19 = var1.level;
            Object var69 = this.stateObject;
            if (var19 != var69) {
                ClientLevel var71 = var1.level;
                this.stateObject = var71;
                this.entriesMap.clear();
                this.activeKillEffectManager.applyStep();
                ScanFx.clear();
            }
            Float var83 = 0.0F;
            double var29 = ((double) ((Float) this.getSetting("Max Distance", var83)));
            Vec3 var31 = ClarityRenderUtils.getCameraPos();
            Set var34 = this.entriesMap.keySet();
            Predicate var74 = (p0) -> KillEffect.lambda$a(var1, p0);
            boolean var35 = var34.removeIf(var74);
            Iterator var39 = var1.level.entitiesForRendering().iterator();
            while (true) {
                if (!var39.hasNext()) break;
                Entity var44 = ((Entity) var39.next());
                if (!(var44 instanceof LivingEntity)) continue;
                LivingEntity var48 = ((LivingEntity) var44);
                float var50 = var48.getHealth();
                Map var52 = this.entriesMap;
                Integer var77 = var48.getId();
                Float var85 = var50;
                Float var54 = ((Float) var52.put(var77, var85));
                if (var54 == null) {
                }
                if (Float.compare(var54, 0.0F) <= 0) continue;
                if (Float.compare(var50, 0.0F) > 0) continue;
                if (Double.compare(var29, 0.5) > 0) {
                    Vec3 var81 = var48.position();
                    if (Double.compare(var31.distanceTo(var81), var29) > 0) {
                    }
                    this.applyStep(var48);
                }
            }
            return;
        }
    }

    private KillEffectStyle getEffect() {
        KillEffectStyle var9 = null;
        KillEffectStyle var7 = KillEffectStyle.fromDisplayName(((String) this.getSetting("Effect", "Shockwave")));
        if (var7 != null) {
            var9 = var7;
        } else {
            var9 = KillEffectStyle.SHOCKWAVE;
        }
        return var9;
    }

    private void applyStep(LivingEntity arg0) {
        double var175 = 0.0;
        Object var81 = this.getSetting("Effect", "Shockwave");
        if ("Wireframe Scan".equals(var81)) {
            Vec3 var38 = arg0.getBoundingBox().getCenter();
            double var40 = var38.x;
            double var91 = var38.y;
            double var117 = var38.z;
            Float var149 = 14.0F;
            float var137 = ((Float) this.getSetting("Scan Range", var149));
            Float var156 = 10.0F;
            float var146 = ((Float) this.getSetting("Scan Speed", var156));
            ScanFx.spawn(var40, var91, var117, var137, var146);
            return;
        }
        int var45 = KillFx.style(((String) this.getSetting("Effect", "Shockwave")));
        if (var45 >= 0) {
            Vec3 var48 = arg0.position();
            double var84 = var48.x;
            double var113 = var48.y;
            double var132 = var48.z;
            float var148 = arg0.getBbWidth();
            float var152 = arg0.getBbHeight();
            KillFx.spawn(var45, var84, var113, var132, var148, var152);
            return;
        }
        KillEffectStyle var51 = this.getEffect();
        Float var95 = 5.0F;
        double var56 = ((double) ((Float) this.getSetting("Max Radius", var95)));
        KillEffectStyle var87 = KillEffectStyle.SHOCKWAVE;
        if (var51 == var87) {
            Float var120 = 8.0F;
            double var100 = ((double) ((Float) this.getSetting("Speed", var120)));
            double var61 = Math.max(0.5, var100);
            double var104 = (var56 / var61);
            var175 = Math.max(0.1, var104);
        } else {
            Float var142 = 2.5F;
            double var130 = ((double) ((Float) this.getSetting("Duration", var142)));
            double var110 = Math.min(10.0, var130);
            var175 = Math.max(0.5, var110);
        }
        Integer var107 = 64;
        int var70 = ((Integer) this.getSetting("Points", var107));
        long var71 = System.nanoTime();
        Vec3 var74 = arg0.position();
        ActiveKillEffectManager var76 = this.activeKillEffectManager;
        double var124 = var74.x;
        double var139 = var74.y;
        double var154 = var74.z;
        double var158 = ((double) arg0.getBbWidth());
        double var161 = ((double) arg0.getBbHeight());
        double var165 = (((double) var71) / 1.0E9);
        double var167 = var175;
        int var169 = arg0.getId();
        long var171 = (var71 / 1000000L);
        int var170 = ActiveKillEffectManager.computeColorOrIndex(var169, var171);
        ActiveKillEffectInstance var89 = new ActiveKillEffectInstance(var51, var124, var139, var154, var158, var161, var165, var167, var56, var70, var170);
        var76.applyStep(var89);
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        int var145 = 0;
        int var18 = 0;
        int var29 = 0;
        int var30 = 0;
        int var308 = 0;
        int var106 = 0;
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var33 = Minecraft.getInstance();
        if (var33 != null) {
            if (var33.level != null) {
                if (var33.player == null) {
                }
            }
            return;
            if (!ScanFx.isEmpty()) {
                int var43 = (ThemeColors.resolveRgb(this.getSetting("Color", ""), -12845218, false) & 16777215);
                int var47 = (ThemeColors.resolveRgb(this.getSetting("Second Color", ""), -3604522, true) & 16777215);
                float[] var49 = ShapeFx.rgb(var43);
                float[] var51 = ShapeFx.rgb(var47);
                var18 = 0;
                while (true) {
                    if (var18 >= 3) break;
                    int var152 = var18;
                    int var6 = var18;
                    float var204 = var49[var6];
                    float var205 = (var204 * 0.45F);
                    float var206 = (var205 + 0.55F);
                    var49[var152] = var206;
                    var18 = (var18 + 1);
                }
                Float var242 = 0.9F;
                float var156 = ((Float) this.getSetting("Opacity", var242));
                float var157 = computeFloatMetric(var156);
                Boolean var265 = true;
                Object var209 = this.getSetting("Through Walls", var265);
                Boolean var210 = ((Boolean) var209);
                boolean var211 = var210;
                ScanFx.render(arg1, var157, var211, var49, var51);
            }
            int var59 = (ThemeColors.resolveRgb(this.getSetting("Color", ""), -12845218, false) & 16777215);
            int var63 = (ThemeColors.resolveRgb(this.getSetting("Second Color", ""), -3604522, true) & 16777215);
            Float var246 = 1.0F;
            Object var165 = this.getSetting("Scale", var246);
            Float var166 = ((Float) var165);
            float var167 = var166;
            float var65 = Math.max(0.1F, var167);
            Float var248 = 0.9F;
            Object var169 = this.getSetting("Opacity", var248);
            Float var170 = ((Float) var169);
            float var171 = var170;
            float var172 = computeFloatMetric(var171);
            Boolean var268 = true;
            Object var219 = this.getSetting("Through Walls", var268);
            Boolean var220 = ((Boolean) var219);
            boolean var221 = var220;
            Boolean var283 = true;
            Object var251 = this.getSetting("Natural Colors", var283);
            Boolean var252 = ((Boolean) var251);
            boolean var253 = var252;
            float[] var271 = ShapeFx.rgb(var59);
            float[] var285 = ShapeFx.rgb(var63);
            KillFx.render(var65, var172, var221, var253, var271, var285);
            double var70 = (((double) System.nanoTime()) / 1.0E9);
            this.activeKillEffectManager.applyStep(var70);
            if (this.activeKillEffectManager.isEligible()) {
                return;
            }
            Float var255 = 1.0F;
            Object var176 = this.getSetting("Scale", var255);
            Float var177 = ((Float) var176);
            float var178 = var177;
            float var78 = Math.max(0.1F, var178);
            Float var226 = 0.9F;
            float var83 = computeFloatMetric(((Float) this.getSetting("Opacity", var226)));
            Boolean var228 = true;
            boolean var87 = ((Boolean) this.getSetting("Through Walls", var228));
            Boolean var230 = true;
            boolean var91 = ((Boolean) this.getSetting("Natural Colors", var230));
            int var95 = (ThemeColors.resolveRgb(this.getSetting("Color", ""), -12845218, false) & 16777215);
            int var99 = (ThemeColors.resolveRgb(this.getSetting("Second Color", ""), -3604522, true) & 16777215);
            if (Chams.INSTANCE != null) {
                if (Chams.INSTANCE.isEnabled()) {
                    if (ChamsApi.isThroughWalls()) {
                        var145 = 1;
                    } else {
                    }
                }
                var145 = 0;
                if (var145 != 0) {
                    var308 = 2;
                } else {
                    if (var87) {
                        var106 = 1;
                    } else {
                        var106 = 0;
                    }
                    var308 = var106;
                }
                Vec3 var107 = ClarityRenderUtils.getCameraPos();
                this.entriesMap1.clear();
                int var188 = var308;
                ClarityRenderUtils.CosmeticBatch var111 = ClarityRenderUtils.beginCosmetics(arg0, var188);
                List var114 = this.activeKillEffectManager.evaluate();
                Iterator var115 = var114.iterator();
                while (true) {
                    boolean var117 = var115.hasNext();
                    if (!var117) break;
                    Object var119 = var115.next();
                    ActiveKillEffectInstance var120 = ((ActiveKillEffectInstance) var119);
                    CosmeticAnchor var121 = CosmeticAnchor.FEET;
                    double var190 = var120.x();
                    double var257 = var120.y();
                    double var287 = var120.z();
                    double var298 = var120.height();
                    EntityPoseState var192 = EntityPoseState.evaluate(var190, var257, var287, 0.0, var298);
                    AnchorTransform var122 = var121.evaluate(var192);
                    double var293 = ((double) var78);
                    IGroundHeightSampler var260 = this.evaluate(var33, var122, var293);
                    KillEffectRenderSpec var124 = var120.evaluate(var70, var260);
                    var29 = var95;
                    var30 = var99;
                    if (var120.style().isEligible()) {
                        if (var91) {
                            var29 = var120.style().counterValue;
                            var30 = var120.style().counterValue1;
                        }
                        int var261 = var29;
                        int var273 = var30;
                        double var291 = ((double) var83);
                        double var296 = var124.age();
                        CosmeticDrawBuilder var137 = new CosmeticDrawBuilder(var111, var261, var273, var291, var296, true);
                        double[] var196 = new double[3];
                        double var275 = var107.x;
                        var196[0] = var275;
                        double var278 = var107.y;
                        var196[1] = var278;
                        double var281 = var107.z;
                        var196[2] = var281;
                        var137.coordsVector = var196;
                        double var237 = ((double) var78);
                        var137.applyStep(var122, var237);
                        var120.style().createRenderer(var124).renderEffect(var137);
                    }
                }
                var111.end();
                return;
            }
        }
    }

    private IGroundHeightSampler evaluate(Minecraft arg0, AnchorTransform arg1, double arg2) {
        return (p0, p1) -> this.lambda$a(arg1, arg2, arg0, p0, p1);
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

}
