package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.combat.AimAssist;
import need.clarity.modules.visual.cosmetics.AnchorTransform;
import need.clarity.modules.visual.cosmetics.CosmeticAnchor;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.EntityPoseState;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.hats.HatRenderSpec;
import need.clarity.modules.visual.cosmetics.hats.HatStyle;
import need.clarity.render.worldfx.CosmeticFx;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class Hats extends ModuleBase {
    public static Hats INSTANCE;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;
    private final long timestampMs;

    public Hats() {
        super("Hats", "Visual");
        WorldRenderEvent.Listener var12 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var12;
        this.enabledState = false;
        long var14 = System.nanoTime();
        this.timestampMs = var14;
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

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        int var68 = 0;
        double var368 = 0.0;
        int var390 = 0;
        int var397 = 0;
        int var405 = 0;
        int var407 = 0;
        int var409 = 0;
        double var411 = 0.0;
        int var413 = 0;
        float var184 = 0.0F;
        int var185 = 0;
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var57 = Minecraft.getInstance();
        if (var57 != null) {
            if (var57.level != null) {
                if (var57.player == null) {
                }
            }
            return;
            if (var57.options.getCameraType().isFirstPerson()) {
                if (!Freecam.isEligible()) {
                    var68 = 1;
                } else {
                    var68 = 0;
                }
                if (var68 != 0) {
                    Boolean var216 = true;
                    if (((Boolean) this.getSetting("Hide In First Person", var216))) {
                        return;
                    }
                }
                LocalPlayer var75 = var57.player;
                String var78 = ((String) this.getSetting("Hat", "China Hat"));
                int var80 = CosmeticFx.hatStyle(var78);
                HatStyle var82 = HatStyle.fromDisplayName(var78);
                Float var219 = 1.0F;
                float var86 = ((Float) this.getSetting("Scale", var219));
                Float var221 = 1.0F;
                float var91 = computeFloatMetric(((Float) this.getSetting("Opacity", var221)));
                Float var223 = 0.0F;
                Float var94 = ((Float) this.getSetting("Height", var223));
                float var95 = var94;
                Boolean var225 = true;
                Object var97 = this.getSetting("Animate", var225);
                Boolean var98 = ((Boolean) var97);
                boolean var99 = var98;
                Boolean var227 = false;
                Object var101 = this.getSetting("Through Walls", var227);
                Boolean var102 = ((Boolean) var101);
                boolean var103 = var102;
                Boolean var229 = true;
                Object var105 = this.getSetting("Follow Head Tilt", var229);
                Boolean var106 = ((Boolean) var105);
                boolean var107 = var106;
                Boolean var231 = true;
                Object var109 = this.getSetting("Bob", var231);
                Boolean var110 = ((Boolean) var109);
                boolean var111 = var110;
                Object var113 = this.getSetting("Color", "");
                int var114 = ThemeColors.resolveRgb(var113, -8090, false);
                int var115 = (var114 & 16777215);
                Object var117 = this.getSetting("Second Color", "");
                int var118 = ThemeColors.resolveRgb(var117, -26052, true);
                int var119 = (var118 & 16777215);
                int[] var237 = this.evaluate();
                Float var330 = 1.0F;
                double var288 = ((double) computeFloatMetric(((Float) this.getSetting("Center Alpha", var330))));
                Float var351 = 0.4F;
                double var336 = ((double) computeFloatMetric(((Float) this.getSetting("Edge Alpha", var351))));
                Float var370 = -0.2F;
                Object var353 = this.getSetting("Rotation Speed", var370);
                Float var354 = ((Float) var353);
                float var355 = var354;
                double var356 = ((double) var355);
                Float var391 = 0.15F;
                Object var372 = this.getSetting("Spin", var391);
                Float var373 = ((Float) var372);
                float var374 = var373;
                double var375 = ((double) var374);
                EquipmentSlot var15 = EquipmentSlot.HEAD;
                ItemStack var393 = var75.getItemBySlot(var15);
                boolean var394 = var393.isEmpty();
                if (!var394) {
                    var397 = 1;
                } else {
                    var397 = 0;
                }
                HatRenderSpec var120 = new HatRenderSpec(var237, var288, var336, var356, var375, var397, var111);
                double var122 = var75.xOld;
                double var239 = var75.getX();
                double var299 = var75.xOld;
                double var241 = (var239 - var299);
                double var302 = ((double) arg1);
                double var243 = (var241 * var302);
                double var124 = (var122 + var243);
                double var127 = var75.yOld;
                double var246 = var75.getY();
                double var305 = var75.yOld;
                double var248 = (var246 - var305);
                double var308 = ((double) arg1);
                double var250 = (var248 * var308);
                double var129 = (var127 + var250);
                double var132 = var75.zOld;
                double var253 = var75.getZ();
                double var311 = var75.zOld;
                double var255 = (var253 - var311);
                double var314 = ((double) arg1);
                double var257 = (var255 * var314);
                double var134 = (var132 + var257);
                float var201 = var75.yBodyRotO;
                float var260 = var75.yBodyRot;
                float var137 = Mth.rotLerp(arg1, var201, var260);
                float var203 = var75.yHeadRotO;
                float var262 = var75.yHeadRot;
                float var139 = Mth.rotLerp(arg1, var203, var262);
                boolean var140 = ServerRotations.isEligible();
                if (var140) {
                    boolean var141 = AimAssist.isActivelyTracking();
                    if (var141) {
                        var184 = ServerRotations.computeFloatMetric(arg1);
                    } else {
                    }
                }
                float var215 = var75.xRotO;
                float var283 = var75.getXRot();
                var184 = Mth.lerp(arg1, var215, var283);
                float var45 = var184;
                Pose var144 = var75.getPose();
                double var362 = ((double) var137);
                double var383 = ((double) var139);
                double var400 = ((double) var45);
                float var402 = var75.getBbHeight();
                double var403 = ((double) var402);
                Pose var18 = Pose.CROUCHING;
                if (var144 == var18) {
                    var405 = 1;
                } else {
                    var405 = 0;
                }
                Pose var19 = Pose.SWIMMING;
                if (var144 == var19) {
                    var407 = 1;
                } else {
                    var407 = 0;
                }
                Pose var20 = Pose.FALL_FLYING;
                if (var144 == var20) {
                    var409 = 1;
                } else {
                    var409 = 0;
                }
                if (var107) {
                    var411 = 1.0;
                } else {
                    var411 = 0.0;
                }
                EntityPoseState var145 = new EntityPoseState(var124, var129, var134, var362, var383, var400, var403, var405, var407, var409, var411);
                CosmeticAnchor var146 = CosmeticAnchor.HEAD;
                AnchorTransform var147 = var146.evaluate(var145);
                double var339 = ((double) var95);
                double var292 = (0.234 + var339);
                AnchorTransform var148 = var147.evaluate(0.0, var292, 0.0);
                if (var80 >= 0) {
                    long var150 = System.nanoTime();
                    long var266 = this.timestampMs;
                    long var152 = (var150 - var266);
                    double var154 = ((double) var152);
                    double var156 = (var154 / 1.0E9);
                    double var271 = ((double) var86);
                    float[] var319 = CosmeticFx.rgb(var115);
                    float[] var344 = CosmeticFx.rgb(var119);
                    if (var99) {
                        var368 = (var156 % 3600.0);
                    }
                } else {
                    Chams var159 = Chams.INSTANCE;
                    if (var159 != null) {
                        Chams var160 = Chams.INSTANCE;
                        boolean var161 = var160.isEnabled();
                        if (var161) {
                            boolean var162 = Chams.isThroughWalls();
                            if (var162) {
                                boolean var163 = Chams.isHideSelf();
                                if (!var163) {
                                    var413 = 2;
                                }
                            }
                        } else {
                        }
                    }
                    if (var103) {
                        var185 = 1;
                    } else {
                        var185 = 0;
                    }
                    var413 = var185;
                    long var165 = System.nanoTime();
                    long var274 = this.timestampMs;
                    long var167 = (var165 - var274);
                    double var169 = ((double) var167);
                    double var171 = (var169 / 1.0E9);
                    int var208 = var413;
                    ClarityRenderUtils.CosmeticBatch var174 = ClarityRenderUtils.beginCosmetics(arg0, var208);
                    double var346 = ((double) var91);
                    CosmeticDrawBuilder var175 = new CosmeticDrawBuilder(var174, var115, var119, var346, var171, var99);
                    Vec3 var176 = ClarityRenderUtils.getCameraPos();
                    double[] var210 = new double[3];
                    double var322 = var176.x;
                    var210[0] = var322;
                    double var325 = var176.y;
                    var210[1] = var325;
                    double var328 = var176.z;
                    var210[2] = var328;
                    var175.coordsVector = var210;
                    double var280 = ((double) var86);
                    var175.applyStep(var148, var280);
                    ICosmeticEffectRenderer var180 = var82.createRenderer(var120);
                    var180.renderEffect(var175);
                    var174.end();
                    return;
                    var368 = 0.0;
                }
                float var367 = ((float) var368);
                Float var396 = 0.15F;
                Object var378 = this.getSetting("Spin", var396);
                Float var379 = ((Float) var378);
                float var380 = var379;
                if (var111) {
                    if (var99) {
                        var390 = 1;
                    }
                } else {
                    var390 = 0;
                }
                CosmeticFx.hat(var80, var148, var271, var319, var344, var91, var103, var367, var380, var390);
                return;
            }
        }
    }

    private int[] evaluate() {
        int var9 = 0;
        int var22 = 0;
        Object var34 = this.getSetting("Color Mode", "Sync");
        if (!"Custom".equals(var34)) {
            int[] var33 = new int[1];
            int var72 = ThemeColors.resolveArgb("", -16714844, false);
            var33[0] = var72;
            return var33;
        }
        String var13 = ((String) this.getSetting("Color Count", "Quartet"));
        var9 = -1;
        switch (var13.hashCode()) {
            case -1781598082 -> {}
            case 69086 -> {}
            case 2582783 -> {}
            default -> {}
        }
        if (var13.equals("Solo")) {
            var9 = 0;
            if (var13.equals("Duo")) {
                var9 = 1;
                if (var13.equals("Triple")) {
                    var9 = 2;
                }
                switch (var9) {
                    case 0 -> {}
                    case 1 -> {}
                    case 2 -> {}
                    default -> {}
                }
                var22 = new int[1];
                int var52 = this.computeColorOrIndex("Color 1", "#FFE96B", false);
                var22[0] = var52;
                var22 = new int[2];
                int var54 = this.computeColorOrIndex("Color 1", "#FFE96B", false);
                var22[0] = var54;
                int var56 = this.computeColorOrIndex("Color 2", "#3B5B30", true);
                var22[1] = var56;
                var22 = new int[3];
                int var66 = this.computeColorOrIndex("Color 1", "#FFE96B", false);
                var22[0] = var66;
                int var68 = this.computeColorOrIndex("Color 2", "#3B5B30", true);
                var22[1] = var68;
                int var70 = this.computeColorOrIndex("Color 3", "#9A4676", false);
                var22[2] = var70;
                var22 = new int[4];
                int var58 = this.computeColorOrIndex("Color 1", "#FFE96B", false);
                var22[0] = var58;
                int var60 = this.computeColorOrIndex("Color 2", "#3B5B30", true);
                var22[1] = var60;
                int var62 = this.computeColorOrIndex("Color 3", "#9A4676", false);
                var22[2] = var62;
                int var64 = this.computeColorOrIndex("Color 4", "#F9B9EB", true);
                var22[3] = var64;
                return var22;
            }
        }
    }

    private int computeColorOrIndex(String arg0, String arg1, boolean arg2) {
        return ThemeColors.resolveArgb(this.getSetting(arg0, arg1), -16714844, arg2);
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
