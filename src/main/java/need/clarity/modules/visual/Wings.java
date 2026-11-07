package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.visual.cosmetics.AnchorTransform;
import need.clarity.modules.visual.cosmetics.CosmeticAnchor;
import need.clarity.modules.visual.cosmetics.EntityPoseState;
import need.clarity.modules.visual.wings.WingRenderContext;
import need.clarity.modules.visual.wings.WingStyle;
import need.clarity.render.worldfx.CosmeticFx;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Wings extends ModuleBase {
    public static Wings INSTANCE;
    private static final double CONST_DBL = 0.1;
    private static final double CONST_DBL1 = 0.18;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;
    private long timestampMs;
    private double doubleValue;
    private double doubleValue1;
    private final long timestampMs1;

    public Wings() {
        super("Wings", "Visual");
        WorldRenderEvent.Listener var15 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var15;
        this.enabledState = false;
        this.timestampMs = 0L;
        this.doubleValue = 0.0;
        this.doubleValue1 = 0.0;
        long var23 = System.nanoTime();
        this.timestampMs1 = var23;
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
        double var47 = 0.0;
        double var49 = 0.0;
        int var143 = 0;
        double var181 = 0.0;
        double var183 = 0.0;
        int var186 = 0;
        double var187 = 0.0;
        int var491 = 0;
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var60 = Minecraft.getInstance();
        if (var60 != null) {
            if (var60.level != null) {
                if (var60.player == null) {
                }
            }
            return;
            Boolean var226 = false;
            boolean var69 = ((Boolean) this.getSetting("Through Walls", var226));
            Float var228 = 1.0F;
            float var73 = ((Float) this.getSetting("Scale", var228));
            Float var230 = 0.85F;
            float var78 = computeFloatMetric(((Float) this.getSetting("Opacity", var230)));
            Boolean var232 = true;
            boolean var82 = ((Boolean) this.getSetting("Animate", var232));
            Float var234 = 1.0F;
            float var86 = ((Float) this.getSetting("Flap Speed", var234));
            Float var236 = 0.0F;
            float var90 = ((Float) this.getSetting("Y Offset", var236));
            WingStyle var94 = WingStyle.fromDisplayName(((String) this.getSetting("Style", "Angel")));
            int var98 = (ThemeColors.resolveRgb(this.getSetting("Color", ""), -16720641, false) & 16777215);
            Object var100 = this.getSetting("Second Color", "");
            int var101 = ThemeColors.resolveRgb(var100, -8769793, true);
            int var102 = (var101 & 16777215);
            LocalPlayer var104 = var60.player;
            double var243 = var104.xOld;
            double var338 = var104.getX();
            double var402 = var104.xOld;
            double var340 = (var338 - var402);
            double var405 = ((double) arg1);
            double var342 = (var340 * var405);
            double var245 = (var243 + var342);
            double var345 = var104.yOld;
            double var408 = var104.getY();
            double var435 = var104.yOld;
            double var410 = (var408 - var435);
            double var438 = ((double) arg1);
            double var412 = (var410 * var438);
            double var347 = (var345 + var412);
            double var415 = var104.zOld;
            double var441 = var104.getZ();
            double var460 = var104.zOld;
            double var443 = (var441 - var460);
            double var463 = ((double) arg1);
            double var445 = (var443 * var463);
            double var417 = (var415 + var445);
            Vec3 var105 = new Vec3(var245, var347, var417);
            Chams var106 = Chams.INSTANCE;
            if (var106 != null) {
                Chams var107 = Chams.INSTANCE;
                boolean var108 = var107.isEnabled();
                if (var108) {
                    boolean var109 = Chams.isThroughWalls();
                    if (var109) {
                        boolean var110 = Chams.isHideSelf();
                        if (!var110) {
                            var491 = 2;
                        }
                    } else {
                    }
                }
                if (var69) {
                    var186 = 1;
                } else {
                    var186 = 0;
                }
                var491 = var186;
                long var112 = System.nanoTime();
                long var115 = this.timestampMs;
                int var117 = Long.compare(var115, 0L);
                if (var117 == 0) {
                    var187 = 0.016;
                } else {
                    long var350 = this.timestampMs;
                    long var251 = (var112 - var350);
                    double var253 = ((double) var251);
                    double var255 = (var253 / 1.0E9);
                    var187 = Math.min(0.1, var255);
                }
                double var43 = var187;
                this.timestampMs = var112;
                long var258 = this.timestampMs1;
                long var123 = (var112 - var258);
                double var125 = ((double) var123);
                double var127 = (var125 / 1.0E9);
                var47 = 0.1;
                var49 = 0.18;
                if (var82) {
                    double var135 = var104.getX();
                    double var263 = var104.xOld;
                    double var137 = (var135 - var263);
                    double var266 = var104.getZ();
                    double var355 = var104.zOld;
                    double var268 = (var266 - var355);
                    double var139 = Math.hypot(var137, var268);
                    boolean var142 = var104.onGround();
                    if (!var142) {
                        var143 = 1;
                    } else {
                        var143 = 0;
                    }
                    int var54 = var143;
                    if (var54 != 0) {
                        var183 = 0.36;
                    } else {
                        double var274 = (Math.min(var139, 0.3) * 0.35);
                        var183 = (0.05 + var274);
                    }
                    double var55 = var183;
                    if (var54 != 0) {
                        var181 = 2.3;
                    } else {
                        double var307 = (Math.min(var139, 0.3) * 3.0);
                        var181 = (0.7 + var307);
                    }
                    double var277 = ((double) var86);
                    double var148 = (var181 * var277);
                    double var203 = this.doubleValue1;
                    double var383 = this.doubleValue1;
                    double var309 = (var55 - var383);
                    double var429 = (var43 * 6.0);
                    double var387 = Math.min(1.0, var429);
                    double var311 = (var309 * var387);
                    double var205 = (var203 + var311);
                    this.doubleValue1 = var205;
                    double var207 = this.doubleValue;
                    double var319 = (((var43 * var148) * 3.141592653589793) * 2.0);
                    double var209 = (var207 + var319);
                    this.doubleValue = var209;
                    double var153 = this.doubleValue;
                    int var155 = Double.compare(var153, 1000000.0);
                    if (var155 > 0) {
                        double var211 = this.doubleValue;
                        double var213 = (var211 - 1000000.0);
                        this.doubleValue = var213;
                    }
                    double var282 = this.doubleValue1;
                    double var362 = this.doubleValue;
                    double var364 = Math.sin(var362);
                    double var284 = (var282 * var364);
                    double var159 = (0.1 + var284);
                    var47 = var159;
                    double var367 = this.doubleValue1;
                    double var369 = (var367 * 0.35);
                    double var466 = this.doubleValue;
                    double var468 = Math.sin(var466);
                    double var449 = (0.5 * var468);
                    double var423 = (0.5 + var449);
                    double var371 = (var369 * var423);
                    double var288 = (0.18 - var371);
                    double var163 = Math.max(0.0, var288);
                    var49 = var163;
                }
                Object var166 = this.getSetting("Style", "Angel");
                String var167 = ((String) var166);
                int var168 = CosmeticFx.wingStyle(var167);
                if (var168 >= 0) {
                    float var171 = var104.yBodyRotO;
                    float var217 = var104.yBodyRot;
                    float var292 = var104.yBodyRotO;
                    float var218 = (var217 - var292);
                    float var219 = (var218 * arg1);
                    float var172 = (var171 + var219);
                    CosmeticAnchor var173 = CosmeticAnchor.BACK;
                    double var221 = var105.x;
                    double var324 = var105.y;
                    double var396 = var105.z;
                    double var432 = ((double) var172);
                    float var456 = var104.getBbHeight();
                    double var457 = ((double) var456);
                    EntityPoseState var223 = EntityPoseState.evaluate(var221, var324, var396, var432, var457);
                    AnchorTransform var174 = var173.evaluate(var223);
                    double[] var176 = new double[3];
                    double var327 = var174.doubleValue;
                    var176[0] = var327;
                    double var330 = var174.doubleValue1;
                    double var399 = ((double) var90);
                    double var332 = (var330 + var399);
                    var176[1] = var332;
                    double var335 = var174.doubleValue2;
                    var176[2] = var335;
                    double var300 = -(var174.doubleValue3);
                    double var374 = var174.doubleValue5;
                    double var376 = -(var374);
                    double var426 = var174.doubleValue9;
                    double var452 = var174.doubleValue11;
                    double var475 = ((double) var73);
                    double var472 = (1.1 * var475);
                    double var477 = var47;
                    double var19 = var49;
                    float[] var483 = CosmeticFx.rgb(var98);
                    float[] var486 = CosmeticFx.rgb(var102);
                    double var488 = (var127 % 3600.0);
                    float var490 = ((float) var488);
                    CosmeticFx.wings(var168, var176, var300, var376, var426, var452, var472, var477, var19, var483, var486, var78, var69, var490);
                    return;
                }
                int var474 = var491;
                double var481 = var47;
                double var484 = var49;
                this.renderWorldEffect(arg0, var104, var105, arg1, var94, var73, var98, var102, var78, var474, var82, var127, var481, var484, var90);
                return;
            }
        }
    }

    private void renderWorldEffect(PoseStack arg0, Player arg1, Vec3 arg2, float arg3, WingStyle arg4, float arg5, int arg6, int arg7, float arg8, int arg9, boolean arg10, double arg11, double arg12, double arg13, float arg14) {
        float var53 = arg1.yBodyRotO;
        float var88 = arg1.yBodyRot;
        float var100 = arg1.yBodyRotO;
        float var90 = ((var88 - var100) * arg3);
        float var54 = (var53 + var90);
        CosmeticAnchor var55 = CosmeticAnchor.BACK;
        double var92 = arg2.x;
        double var115 = arg2.y;
        double var135 = arg2.z;
        double var145 = ((double) var54);
        double var152 = ((double) arg1.getBbHeight());
        EntityPoseState var94 = EntityPoseState.evaluate(var92, var115, var135, var145, var152);
        AnchorTransform var56 = var55.evaluate(var94);
        double var58 = var56.doubleValue9;
        double var61 = var56.doubleValue11;
        double var66 = -(var56.doubleValue3);
        double var71 = -(var56.doubleValue5);
        double[] var74 = new double[3];
        double var118 = var56.doubleValue;
        var74[0] = var118;
        double var121 = var56.doubleValue1;
        double var138 = ((double) arg14);
        double var123 = (var121 + var138);
        var74[1] = var123;
        double var126 = var56.doubleValue2;
        var74[2] = var126;
        double var76 = ((double) arg5);
        double var106 = arg4.doubleValue;
        double var78 = (var76 * var106);
        ClarityRenderUtils.CosmeticBatch var81 = ClarityRenderUtils.beginCosmetics(arg0, arg9);
        double var141 = ((double) arg8);
        WingRenderContext var82 = new WingRenderContext(var81, arg6, arg7, var141, arg11, arg10);
        var82.applyStep(var74, var66, var71, var58, var61, var78, arg12, arg13, 0);
        arg4.applyStep(var82);
        double var113 = -(var66);
        double var133 = -(var71);
        var82.applyStep(var74, var113, var133, var58, var61, var78, arg12, arg13, 1);
        arg4.applyStep(var82);
        var81.end();
    }

    private static float computeFloatMetric(float arg0) {
        float var4 = 0.0F;
        if (Float.compare(arg0, 0.0F) < 0) {
            var4 = 0.0F;
        } else {
            if (Float.compare(arg0, 1.0F) > 0) {
                var4 = 1.0F;
            } else {
                var4 = arg0;
            }
            return var4;
        }
    }

}
