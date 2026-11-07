package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import need.clarity.WorldRenderEvent;
import need.clarity.api.ChamsApi;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.combat.AimAssist;
import need.clarity.modules.visual.cosmetics.AnchorTransform;
import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticAnchor;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.EntityPoseState;
import need.clarity.modules.visual.cosmetics.targetesp.TargetEspRenderSpec;
import need.clarity.modules.visual.cosmetics.targetesp.TargetEspStyle;
import need.clarity.render.FxTextures;
import need.clarity.render.esp.ShaderEspRenderer;
import need.clarity.render.worldfx.CorpseFx;
import need.clarity.render.worldfx.ShapeFx;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TargetESP extends ModuleBase {
    public static TargetESP INSTANCE;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;
    private long timestampMs;
    private double doubleValue;
    private double doubleValue1;
    private int counterValue;
    private long timestampMs1;
    private int counterValue1;
    private int counterValue2;
    private long timestampMs2;
    private double doubleValue2;
    private int counterValue3;
    private int counterValue4;

    public TargetESP() {
        super("Target ESP", "Visual");
        WorldRenderEvent.Listener var22 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var22;
        this.enabledState = false;
        this.timestampMs = 0L;
        this.doubleValue = 0.0;
        this.doubleValue1 = 0.0;
        this.counterValue = -2147483648;
        this.timestampMs1 = 0L;
        this.counterValue1 = -2147483648;
        this.counterValue2 = 0;
        this.timestampMs2 = 0L;
        this.doubleValue2 = 0.0;
        this.counterValue3 = 16711680;
        this.counterValue4 = -2147483648;
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
        this.timestampMs = 0L;
        this.counterValue = -2147483648;
        this.applyStep2();
        ShaderEspRenderer.setComboTarget(-2147483648);
        this.counterValue4 = -2147483648;
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
        LivingEntity var14 = null;
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var31 = Minecraft.getInstance();
        if (var31 != null) {
            if (var31.level != null) {
                if (var31.player == null) {
                }
            }
            return;
            var14 = null;
            try {
                var14 = AimAssist.getCurrentTarget();
            } catch (Throwable ignored) {
            }
            int var43 = ShapeFx.comboStyle(((String) this.getSetting("Mode", "Box")));
            int var47 = (ThemeColors.resolveRgb(this.getSetting("Color", ""), -50373, false) & 16777215);
            int var51 = (ThemeColors.resolveRgb(this.getSetting("Second Color", ""), -19635, true) & 16777215);
            float[] var53 = ShapeFx.rgb(var47);
            float[] var148 = ShapeFx.rgb(var51);
            Boolean var230 = true;
            boolean var196 = ((Boolean) this.getSetting("Through Walls", var230));
            CorpseFx.render(var53, var148, var196);
            if (var43 >= 0) {
                if (var14 != null) {
                    if (var14.isDeadOrDying()) {
                        ShaderEspRenderer.requestCorpse(var14.getId(), var43);
                        this.counterValue4 = -2147483648;
                    } else {
                    }
                }
                if (var14 != null) {
                    if (var14.isAlive()) {
                        int var181 = var14.getId();
                        this.counterValue4 = var181;
                        ShaderEspRenderer.setComboTarget(this.counterValue4);
                    } else {
                    }
                }
                if (this.counterValue4 != -2147483648) {
                    ClientLevel var125 = var31.level;
                    int var184 = this.counterValue4;
                    Entity var126 = var125.getEntity(var184);
                    if ((var126 instanceof LivingEntity)) {
                        if (((LivingEntity) var126).isDeadOrDying()) {
                            ShaderEspRenderer.requestCorpse(this.counterValue4, var43);
                        }
                    } else {
                        ShaderEspRenderer.setComboTarget(-2147483648);
                    }
                    this.counterValue4 = -2147483648;
                }
            } else {
                if (this.counterValue4 != -2147483648) {
                    ShaderEspRenderer.setComboTarget(-2147483648);
                    this.counterValue4 = -2147483648;
                }
                if (var14 != null) {
                    if (!var14.isAlive()) {
                    }
                }
                this.counterValue = -2147483648;
                this.timestampMs = 0L;
                this.applyStep2();
                return;
                LivingEntity var151 = var14;
                this.applyStep(var151);
                String var67 = ((String) this.getSetting("Mode", "Box"));
                int var69 = ShapeFx.targetStyle(var67);
                if (var69 >= 0) {
                    LivingEntity var198 = var14;
                    this.applyStep(arg1, var198, var69);
                    return;
                }
                if ("Image".equals(var67)) {
                    LivingEntity var229 = var14;
                    this.renderWorldEffect(arg0, arg1, var229);
                    return;
                }
                TargetEspStyle var75 = TargetEspStyle.fromDisplayName(var67);
                if (var75 != null) {
                    LivingEntity var220 = var14;
                    this.renderWorldEffect(arg0, arg1, var220, var75);
                    return;
                }
                Boolean var201 = false;
                boolean var81 = ((Boolean) this.getSetting("Fill", var201));
                Boolean var203 = true;
                boolean var85 = ((Boolean) this.getSetting("Through Walls", var203));
                int var160 = ThemeColors.resolveRgb(this.getSetting("Color", ""), -50373, false);
                int var87 = this.computeColorOrIndex(var160);
                int var163 = ThemeColors.resolveRgb(this.getSetting("Fill Color", ""), -50373, true);
                int var89 = this.computeColorOrIndex(var163);
                Float var209 = 0.9F;
                float var93 = ((Float) this.getSetting("Outline Opacity", var209));
                Float var211 = 0.2F;
                float var97 = ((Float) this.getSetting("Fill Opacity", var211));
                int var102 = (Math.round((computeFloatMetric(var93) * 255.0F)) << 24);
                int var169 = (var87 & 16777215);
                int var103 = (var102 | var169);
                int var108 = (Math.round((computeFloatMetric(var97) * 255.0F)) << 24);
                int var173 = (var89 & 16777215);
                int var109 = (var108 | var173);
                AABB var216 = var14.getBoundingBox().inflate(0.12);
                ClarityRenderUtils.ESPEntry var110 = new ClarityRenderUtils.ESPEntry(var216, var103);
                List var175 = List.of(var110);
                Float var236 = 2.0F;
                float var235 = ((Float) this.getSetting("Outline Thickness", var236));
                ClarityRenderUtils.drawEspBoxes(arg0, var175, var81, var109, true, var85, false, var235);
                return;
            }
        }
    }

    private void applyStep(float arg0, LivingEntity arg1, int arg2) {
        long var4 = System.nanoTime();
        int var42 = arg1.getId();
        int var100 = this.counterValue;
        if (var42 != var100) {
            int var102 = arg1.getId();
            this.counterValue = var102;
            this.timestampMs1 = var4;
        }
        long var116 = this.timestampMs1;
        double var122 = ((((double) (var4 - var116)) / 1.0E9) / 0.35);
        float var57 = ((float) Math.min(1.0, var122));
        Float var125 = 0.4F;
        float var61 = ((Float) this.getSetting("Speed", var125));
        double var66 = (((double) var4) / 1.0E9);
        double var168 = (((double) Math.abs(var61)) * 2.5);
        double var130 = Math.max(0.1, var168);
        float var72 = ((float) ((var66 * var130) % 3600.0));
        int var108 = ThemeColors.resolveRgb(this.getSetting("Color", ""), -50373, false);
        int var75 = (this.computeColorOrIndex(var108) & 16777215);
        int var79 = (ThemeColors.resolveRgb(this.getSetting("Second Color", ""), -19635, true) & 16777215);
        Float var139 = 1.0F;
        float var83 = ((Float) this.getSetting("Scale", var139));
        double var85 = arg1.xOld;
        double var141 = arg1.getX();
        double var171 = arg1.xOld;
        double var143 = (var141 - var171);
        double var174 = ((double) arg0);
        double var145 = (var143 * var174);
        double var87 = (var85 + var145);
        double var90 = arg1.yOld;
        double var148 = arg1.getY();
        double var177 = arg1.yOld;
        double var150 = (var148 - var177);
        double var180 = ((double) arg0);
        double var152 = (var150 * var180);
        double var92 = (var90 + var152);
        double var95 = arg1.zOld;
        double var155 = arg1.getZ();
        double var183 = arg1.zOld;
        double var157 = (var155 - var183);
        double var186 = ((double) arg0);
        double var159 = (var157 * var186);
        double var97 = (var95 + var159);
        float var189 = (arg1.getBbWidth() * var83);
        float var192 = (arg1.getBbHeight() * var83);
        float[] var194 = ShapeFx.rgb(var75);
        float[] var195 = ShapeFx.rgb(var79);
        float var198 = ((float) this.doubleValue2);
        Boolean var202 = true;
        boolean var201 = ((Boolean) this.getSetting("Through Walls", var202));
        ShapeFx.target(arg2, var87, var92, var97, var189, var192, var194, var195, var72, var198, var57, var201);
    }

    private void renderWorldEffect(PoseStack arg0, float arg1, LivingEntity arg2, TargetEspStyle arg3) {
        int var33 = 0;
        int var34 = 0;
        int var164 = 0;
        double var165 = 0.0;
        int var167 = 0;
        int var345 = 0;
        long var5 = System.nanoTime();
        if (this.timestampMs == 0L) {
            var165 = 0.0;
        } else {
            long var289 = this.timestampMs;
            double var217 = (((double) (var5 - var289)) / 1.0E9);
            var165 = Math.min(0.1, var217);
        }
        double var24 = var165;
        this.timestampMs = var5;
        int var56 = arg2.getId();
        int var169 = this.counterValue;
        if (var56 != var169) {
            int var171 = arg2.getId();
            this.counterValue = var171;
            this.timestampMs1 = var5;
        }
        long var220 = this.timestampMs1;
        double var65 = (((double) (var5 - var220)) / 1.0E9);
        Boolean var225 = true;
        boolean var70 = ((Boolean) this.getSetting("Animate", var225));
        Float var227 = 0.4F;
        float var74 = ((Float) this.getSetting("Speed", var227));
        if (var70) {
            double var176 = this.doubleValue;
            double var327 = ((double) var74);
            double var277 = (var24 * var327);
            double var178 = (var176 + var277);
            this.doubleValue = var178;
            if (Double.compare(this.doubleValue, 10000.0) <= 0) {
                if (Double.compare(this.doubleValue, -10000.0) < 0) {
                }
            }
            double var182 = (this.doubleValue % 1.0);
            this.doubleValue = var182;
            double var186 = (this.doubleValue1 + var24);
            this.doubleValue1 = var186;
            if (Double.compare(this.doubleValue1, 100000.0) > 0) {
                double var190 = (this.doubleValue1 % 3600.0);
                this.doubleValue1 = var190;
            }
            Float var235 = 1.0F;
            float var95 = ((Float) this.getSetting("Scale", var235));
            Float var237 = 0.9F;
            float var100 = computeFloatMetric(((Float) this.getSetting("Opacity", var237)));
            Boolean var239 = true;
            boolean var104 = ((Boolean) this.getSetting("Through Walls", var239));
            var33 = (ThemeColors.resolveRgb(this.getSetting("Color", ""), -50373, false) & 16777215);
            var34 = (ThemeColors.resolveRgb(this.getSetting("Second Color", ""), -24005, true) & 16777215);
            if (arg3.isEligible()) {
                Boolean var245 = true;
                if (((Boolean) this.getSetting("Natural Colors", var245))) {
                    var33 = arg3.counterValue;
                    var34 = arg3.counterValue1;
                }
                int var202 = var33;
                int var124 = this.computeColorOrIndex(var202);
                int var203 = var34;
                int var126 = this.computeColorOrIndex(var203);
                double var128 = arg2.xOld;
                double var247 = arg2.getX();
                double var294 = arg2.xOld;
                double var249 = (var247 - var294);
                double var297 = ((double) arg1);
                double var251 = (var249 * var297);
                double var130 = (var128 + var251);
                double var133 = arg2.yOld;
                double var254 = arg2.getY();
                double var300 = arg2.yOld;
                double var256 = (var254 - var300);
                double var303 = ((double) arg1);
                double var258 = (var256 * var303);
                double var135 = (var133 + var258);
                double var138 = arg2.zOld;
                double var261 = arg2.getZ();
                double var306 = arg2.zOld;
                double var263 = (var261 - var306);
                double var309 = ((double) arg1);
                double var265 = (var263 * var309);
                double var140 = (var138 + var265);
                double var338 = ((double) arg2.getBbHeight());
                EntityPoseState var144 = EntityPoseState.evaluate(var130, var135, var140, 0.0, var338);
                AnchorTransform var146 = CosmeticAnchor.CENTER.evaluate(var144);
                double var271 = ((double) arg2.getBbWidth());
                double var315 = ((double) arg2.getBbHeight());
                double var333 = this.doubleValue;
                double var341 = ((double) var74);
                TargetEspRenderSpec var147 = new TargetEspRenderSpec(var271, var315, var333, var341, var65);
                if (Chams.INSTANCE != null) {
                    if (Chams.INSTANCE.isEnabled()) {
                        if (ChamsApi.isThroughWalls()) {
                            var167 = 1;
                        } else {
                            var167 = 0;
                        }
                        if (var167 != 0) {
                            var345 = 2;
                        } else {
                            if (var104) {
                                var164 = 1;
                            } else {
                                var164 = 0;
                            }
                            var345 = var164;
                        }
                        int var205 = var345;
                        ClarityRenderUtils.CosmeticBatch var155 = ClarityRenderUtils.beginCosmetics(arg0, var205);
                        double var330 = ((double) var100);
                        double var335 = this.doubleValue1;
                        CosmeticDrawBuilder var156 = new CosmeticDrawBuilder(var155, var124, var126, var330, var335, var70);
                        Vec3 var157 = ClarityRenderUtils.getCameraPos();
                        double[] var207 = new double[3];
                        double var319 = var157.x;
                        var207[0] = var319;
                        double var322 = var157.y;
                        var207[1] = var322;
                        double var325 = var157.z;
                        var207[2] = var325;
                        var156.coordsVector = var207;
                        double var275 = ((double) var95);
                        var156.applyStep(var146, var275);
                        arg3.createRenderer(var147).renderEffect(var156);
                        var155.end();
                        return;
                    }
                }
            }
        }
    }

    private void renderWorldEffect(PoseStack arg0, float arg1, LivingEntity arg2) {
        int var111 = 0;
        Identifier var36 = FxTextures.get("target");
        if (var36 == null) {
            return;
        }
        Vec3 var38 = ClarityRenderUtils.getCameraPos();
        double var129 = arg2.xOld;
        double var183 = arg2.getX();
        double var210 = arg2.xOld;
        double var185 = (var183 - var210);
        double var213 = ((double) arg1);
        double var187 = (var185 * var213);
        double var131 = (var129 + var187);
        double var190 = arg2.yOld;
        double var216 = arg2.getY();
        double var239 = arg2.yOld;
        double var218 = (var216 - var239);
        double var242 = ((double) arg1);
        double var220 = (var218 * var242);
        double var192 = (var190 + var220);
        double var226 = (((double) arg2.getBbHeight()) * 0.5);
        double var194 = (var192 + var226);
        double var229 = arg2.zOld;
        double var247 = arg2.getZ();
        double var257 = arg2.zOld;
        double var249 = (var247 - var257);
        double var260 = ((double) arg1);
        double var251 = (var249 * var260);
        double var231 = (var229 + var251);
        Vec3 var39 = new Vec3(var131, var194, var231);
        double var41 = var38.distanceTo(var39);
        if (Double.compare(var41, 0.5) < 0) {
            return;
        }
        double var48 = (var41 - 0.1);
        double var143 = ((((double) arg2.getBbWidth()) * 0.5) + 0.35);
        double var50 = Math.min(var48, var143);
        Vec3 var53 = var38.subtract(var39);
        double var116 = (1.0 / var41);
        Vec3 var119 = var53.scale(var116).scale(var50);
        Vec3 var56 = var39.add(var119);
        double var202 = (var41 - var50);
        double var149 = Math.max(0.1, var202);
        double var59 = (var41 / var149);
        double var69 = ((((double) arg2.getBbHeight()) / 3.0) * 0.5);
        Float var205 = 1.0F;
        double var161 = computeDoubleMetric(((double) ((Float) this.getSetting("Image Size", var205))), 0.2, 4.0);
        double var73 = ((var69 * var161) * var59);
        Float var166 = 45.0F;
        double var79 = ((double) ((Float) this.getSetting("Image Spin", var166)));
        double var91 = Math.toRadians((((((double) System.nanoTime()) / 1.0E9) * var79) % 360.0));
        int var123 = ThemeColors.resolveRgb(this.getSetting("Image Colour", ""), -50373, false);
        int var95 = (this.computeColorOrIndex(var123) & 16777215);
        Float var176 = 0.9F;
        int var102 = Math.round((computeFloatMetric(((Float) this.getSetting("Image Opacity", var176))) * 255.0F));
        if (Chams.INSTANCE != null) {
            if (Chams.INSTANCE.isEnabled()) {
                if (ChamsApi.isThroughWalls()) {
                    var111 = 1;
                } else {
                }
            }
            var111 = 0;
            int var34 = var111;
            ClarityRenderUtils.TexBatch var108 = ClarityRenderUtils.beginTextured(arg0, var36, var34);
            int var255 = ((var102 << 24) | var95);
            var108.billboard(var56, var73, var73, var91, var255);
            var108.end();
            return;
        }
    }

    private void applyStep2() {
        this.counterValue1 = -2147483648;
        this.counterValue2 = 0;
        this.timestampMs2 = 0L;
        this.doubleValue2 = 0.0;
    }

    private void applyStep(LivingEntity arg0) {
        Boolean var80 = false;
        if (!((Boolean) this.getSetting("Hit Flash", var80))) {
            this.applyStep2();
            return;
        }
        int var18 = arg0.hurtTime;
        int var20 = arg0.getId();
        int var55 = this.counterValue1;
        if (var20 != var55) {
            int var57 = arg0.getId();
            this.counterValue1 = var57;
            this.counterValue2 = var18;
            this.timestampMs2 = 0L;
        } else {
            int var77 = this.counterValue2;
            if (var18 > var77) {
                long var78 = System.nanoTime();
                this.timestampMs2 = var78;
            }
            this.counterValue2 = var18;
            int var65 = (ThemeColors.resolveRgb(this.getSetting("Hit Flash Color", "#FF0000"), -65536, false) & 16777215);
            this.counterValue3 = var65;
            if (this.timestampMs2 == 0L) {
                this.doubleValue2 = 0.0;
                return;
            }
            Float var107 = 0.35F;
            double var90 = ((double) ((Float) this.getSetting("Hit Flash Duration", var107)));
            double var33 = Math.max(0.05, var90);
            long var35 = System.nanoTime();
            long var93 = this.timestampMs2;
            double var43 = ((((double) (var35 - var93)) / 1.0E9) / var33);
            if (Double.compare(var43, 1.0) >= 0) {
                this.doubleValue2 = 0.0;
                this.timestampMs2 = 0L;
                return;
            }
            double var105 = (var43 * var43);
            double var70 = (1.0 - var105);
            this.doubleValue2 = var70;
            return;
        }
    }

    private int computeColorOrIndex(int arg0) {
        if (Double.compare(this.doubleValue2, 0.0) <= 0) {
            return arg0;
        }
        int var17 = (arg0 & 16777215);
        int var12 = (-16777216 | var17);
        int var25 = this.counterValue3;
        int var19 = (-16777216 | var25);
        double var27 = this.doubleValue2;
        int var15 = (ColorMathUtils.computeColorOrIndex(var12, var19, var27) & 16777215);
        int var22 = (arg0 & -16777216);
        return (var15 | var22);
    }

    private static double computeDoubleMetric(double arg0, double arg1, double arg2) {
        double var14 = 0.0;
        if (Double.compare(arg0, arg1) < 0) {
            var14 = arg1;
        } else {
            if (Double.compare(arg0, arg2) > 0) {
                var14 = arg2;
            } else {
                var14 = arg0;
            }
            return var14;
        }
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
