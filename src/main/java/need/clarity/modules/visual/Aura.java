package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.visual.cosmetics.AnchorTransform;
import need.clarity.modules.visual.cosmetics.CosmeticAnchor;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.EntityPoseState;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.aura.AuraAnimationClock;
import need.clarity.modules.visual.cosmetics.aura.AuraClockState;
import need.clarity.modules.visual.cosmetics.aura.AuraElementStyle;
import need.clarity.modules.visual.cosmetics.aura.AuraOrbitStyle;
import need.clarity.modules.visual.cosmetics.aura.ElementalAuraSpec;
import need.clarity.modules.visual.cosmetics.aura.EnergySphereAuraRenderer;
import need.clarity.modules.visual.cosmetics.aura.EnergySphereAuraSpec;
import need.clarity.modules.visual.cosmetics.aura.GroundRunesAuraRenderer;
import need.clarity.modules.visual.cosmetics.aura.GroundRunesAuraSpec;
import need.clarity.modules.visual.cosmetics.aura.LightningArcsAuraRenderer;
import need.clarity.modules.visual.cosmetics.aura.LightningArcsAuraSpec;
import need.clarity.modules.visual.cosmetics.aura.OrbitAuraSpec;
import need.clarity.modules.visual.cosmetics.aura.SphereRippleImpulse;
import need.clarity.render.worldfx.ShapeFx;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Aura extends ModuleBase {
    public static Aura INSTANCE;
    private final WorldRenderEvent.Listener fieldVal;
    private boolean enabledState;
    private final long timestampMs;
    private long timestampMs1;
    private double doubleValue;
    private double doubleValue1;
    private long timestampMs2;
    private double doubleValue2;
    private double doubleValue3;
    private double doubleValue4;
    private static final int CONST_INT = 4;
    private static final double CONST_DBL = 8.0;
    private final List entriesList;
    private int counterValue;
    static final String labelText = "Orbiting";
    static final String labelText1 = "Energy Sphere";
    static final String labelText2 = "Lightning Arcs";
    static final String labelText3 = "Ground Runes";
    static final String labelText4 = "Elemental";

    private String resolveLabel() {
        String var6 = ((String) this.getSetting("Mode", ""));
        if (ShapeFx.auraStyle(var6) >= 0) {
            return var6;
        }
        if (!"Orbiting".equals(var6)) {
            if (!("Energy Sphere".equals(var6))) {
                if (!("Lightning Arcs".equals(var6))) {
                    if (!("Ground Runes".equals(var6))) {
                        if ("Elemental".equals(var6)) {
                        }
                    }
                    return var6;
                    Boolean var40 = false;
                    if (((Boolean) this.getSetting("Energy Sphere", var40))) {
                        return "Energy Sphere";
                    }
                    Boolean var42 = false;
                    if (((Boolean) this.getSetting("Lightning Arcs", var42))) {
                        return "Lightning Arcs";
                    }
                    return "Orbiting";
                }
            }
        }
    }

    public Aura() {
        super("Aura", "Visual");
        WorldRenderEvent.Listener var21 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal = var21;
        this.enabledState = false;
        long var23 = System.nanoTime();
        this.timestampMs = var23;
        this.timestampMs1 = 0L;
        this.doubleValue = 0.0;
        this.doubleValue1 = 0.0;
        this.timestampMs2 = 0L;
        this.doubleValue2 = 0.0;
        this.doubleValue3 = 0.0;
        this.doubleValue4 = 0.0;
        ArrayList var39 = new ArrayList();
        this.entriesList = var39;
        this.counterValue = 0;
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
        this.timestampMs1 = 0L;
        this.entriesList.clear();
        this.counterValue = 0;
        this.timestampMs2 = 0L;
        this.doubleValue4 = 0.0;
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
        int var515 = 0;
        int var44 = 0;
        int var45 = 0;
        List var564 = null;
        int var94 = 0;
        int var649 = 0;
        int var653 = 0;
        int var655 = 0;
        int var658 = 0;
        double var253 = 0.0;
        CosmeticAnchor var255 = null;
        int var256 = 0;
        boolean var70 = this.isEnabled();
        if (!var70) {
            return;
        }
        Minecraft var71 = Minecraft.getInstance();
        if (var71 != null) {
            ClientLevel var74 = var71.level;
            if (var74 != null) {
                LocalPlayer var76 = var71.player;
                if (var76 == null) {
                }
            }
            return;
            LocalPlayer var78 = var71.player;
            long var79 = System.nanoTime();
            long var319 = this.timestampMs;
            long var81 = (var79 - var319);
            double var83 = ((double) var81);
            double var321 = 1.0E9;
            double var85 = (var83 / var321);
            LocalPlayer var323 = var78;
            this.applyStep(var71, var323, var85);
            float var324 = arg1;
            this.applyStep(var78, var324);
            Options var90 = var71.options;
            CameraType var91 = var90.getCameraType();
            boolean var92 = var91.isFirstPerson();
            if (var92) {
                boolean var93 = Freecam.isEligible();
                if (!var93) {
                    var94 = 1;
                } else {
                }
            }
            var94 = 0;
            int var29 = var94;
            if (var29 != 0) {
                int var325 = 0;
                Boolean var326 = var325;
                Object var97 = this.getSetting("Hide In First Person", var326);
                Boolean var98 = ((Boolean) var97);
                boolean var99 = var98;
                if (var99) {
                    return;
                }
            }
            String var101 = this.resolveLabel();
            boolean var103 = "Orbiting".equals(var101);
            boolean var105 = "Energy Sphere".equals(var101);
            boolean var107 = "Lightning Arcs".equals(var101);
            boolean var109 = "Ground Runes".equals(var101);
            boolean var111 = "Elemental".equals(var101);
            String var327 = "Shards";
            Object var113 = this.getSetting("Orbit Style", var327);
            String var114 = ((String) var113);
            AuraOrbitStyle var115 = AuraOrbitStyle.fromDisplayName(var114);
            String var328 = "Flame";
            Object var117 = this.getSetting("Element", var328);
            String var118 = ((String) var117);
            AuraElementStyle var119 = AuraElementStyle.fromDisplayName(var118);
            float var329 = 1.0F;
            Float var330 = var329;
            Object var121 = this.getSetting("Scale", var330);
            Float var122 = ((Float) var121);
            float var123 = var122;
            float var331 = 1.0F;
            Float var332 = var331;
            Object var125 = this.getSetting("Opacity", var332);
            Float var126 = ((Float) var125);
            float var127 = var126;
            float var128 = computeFloatMetric(var127);
            float var333 = 0.0F;
            Float var334 = var333;
            Object var130 = this.getSetting("Height", var334);
            Float var131 = ((Float) var130);
            float var132 = var131;
            float var335 = 0.25F;
            Float var336 = var335;
            Object var134 = this.getSetting("Speed", var336);
            Float var135 = ((Float) var134);
            float var136 = var135;
            int var337 = 1;
            Boolean var338 = var337;
            Object var138 = this.getSetting("Animate", var338);
            Boolean var139 = ((Boolean) var138);
            boolean var140 = var139;
            int var339 = 0;
            Boolean var340 = var339;
            Object var142 = this.getSetting("Through Walls", var340);
            Boolean var143 = ((Boolean) var142);
            boolean var144 = var143;
            String var341 = "";
            Object var146 = this.getSetting("Color", var341);
            int var342 = 0;
            int var147 = ThemeColors.resolveRgb(var146, -9443329, var342);
            int var148 = (var147 & 16777215);
            var44 = var148;
            String var343 = "";
            Object var150 = this.getSetting("Second Color", var343);
            int var344 = 1;
            int var151 = ThemeColors.resolveRgb(var150, -5936129, var344);
            int var152 = (var151 & 16777215);
            var45 = var152;
            if (var111) {
                int var345 = 1;
                Boolean var346 = var345;
                Object var155 = this.getSetting("Natural Colors", var346);
                Boolean var156 = ((Boolean) var155);
                boolean var157 = var156;
                if (var157) {
                    int var159 = var119.counterValue;
                    var44 = var159;
                    int var161 = var119.counterValue1;
                    var45 = var161;
                }
                long var162 = System.nanoTime();
                long var165 = this.timestampMs1;
                long var347 = 0L;
                int var167 = Long.compare(var165, var347);
                if (var167 == 0) {
                    var253 = 0.0;
                } else {
                    long var349 = var162;
                    long var459 = this.timestampMs1;
                    long var351 = (var349 - var459);
                    double var353 = ((double) var351);
                    double var461 = 1.0E9;
                    double var355 = (var353 / var461);
                    var253 = Math.min(0.1, var355);
                }
                double var48 = var253;
                this.timestampMs1 = var162;
                double var172 = this.doubleValue4;
                Aura var357 = this;
                String var415 = "Speed Reaction";
                float var463 = 1.0F;
                Float var464 = var463;
                Object var358 = var357.getSetting(var415, var464);
                Float var359 = ((Float) var358);
                float var360 = var359;
                double var361 = ((double) var360);
                AuraClockState var174 = AuraAnimationClock.evaluate(var172, var361);
                if (var140) {
                    double var281 = this.doubleValue;
                    double var416 = var48;
                    double var516 = ((double) var136);
                    double var418 = (var416 * var516);
                    double var519 = var174.turn();
                    double var420 = (var418 * var519);
                    double var283 = (var281 + var420);
                    this.doubleValue = var283;
                    double var178 = this.doubleValue;
                    double var363 = 10000.0;
                    int var180 = Double.compare(var178, var363);
                    if (var180 <= 0) {
                        double var182 = this.doubleValue;
                        double var365 = -10000.0;
                        int var184 = Double.compare(var182, var365);
                        if (var184 < 0) {
                        }
                    }
                    double var285 = this.doubleValue;
                    double var422 = 1.0;
                    double var287 = (var285 % var422);
                    this.doubleValue = var287;
                    double var289 = this.doubleValue1;
                    double var424 = var48;
                    double var522 = var174.clock();
                    double var426 = (var424 * var522);
                    double var291 = (var289 + var426);
                    this.doubleValue1 = var291;
                    double var188 = this.doubleValue1;
                    double var367 = 100000.0;
                    int var190 = Double.compare(var188, var367);
                    if (var190 > 0) {
                        double var293 = this.doubleValue1;
                        double var428 = 3600.0;
                        double var295 = (var293 % var428);
                        this.doubleValue1 = var295;
                    }
                    Aura var369 = this;
                    String var430 = "Count";
                    int var465 = 5;
                    Integer var466 = var465;
                    Object var370 = var369.getSetting(var430, var466);
                    Integer var371 = ((Integer) var370);
                    int var372 = var371;
                    Aura var431 = this;
                    String var467 = "Radius";
                    Float var525 = 1.0F;
                    Object var432 = var431.getSetting(var467, var525);
                    Float var433 = ((Float) var432);
                    float var434 = var433;
                    double var435 = ((double) var434);
                    double var527 = var174.radius();
                    double var437 = (var435 * var527);
                    double var530 = this.doubleValue;
                    double var584 = ((double) var136);
                    OrbitAuraSpec var192 = new OrbitAuraSpec(var372, var437, var530, var584);
                    double var194 = var78.xOld;
                    LocalPlayer var373 = var78;
                    double var374 = var373.getX();
                    LocalPlayer var468 = var78;
                    double var469 = var468.xOld;
                    double var376 = (var374 - var469);
                    float var471 = arg1;
                    double var472 = ((double) var471);
                    double var378 = (var376 * var472);
                    double var196 = (var194 + var378);
                    double var199 = var78.yOld;
                    LocalPlayer var380 = var78;
                    double var381 = var380.getY();
                    LocalPlayer var474 = var78;
                    double var475 = var474.yOld;
                    double var383 = (var381 - var475);
                    float var477 = arg1;
                    double var478 = ((double) var477);
                    double var385 = (var383 * var478);
                    double var201 = (var199 + var385);
                    double var204 = var78.zOld;
                    LocalPlayer var387 = var78;
                    double var388 = var387.getZ();
                    LocalPlayer var480 = var78;
                    double var481 = var480.zOld;
                    double var390 = (var388 - var481);
                    float var483 = arg1;
                    double var484 = ((double) var483);
                    double var392 = (var390 * var484);
                    double var206 = (var204 + var392);
                    float var298 = var78.yBodyRotO;
                    LocalPlayer var394 = var78;
                    float var395 = var394.yBodyRot;
                    float var209 = Mth.rotLerp(arg1, var298, var395);
                    float var300 = var78.yHeadRotO;
                    LocalPlayer var396 = var78;
                    float var397 = var396.yHeadRot;
                    float var211 = Mth.rotLerp(arg1, var300, var397);
                    float var302 = var78.xRotO;
                    LocalPlayer var398 = var78;
                    float var399 = var398.getXRot();
                    float var213 = Mth.lerp(arg1, var302, var399);
                    Pose var215 = var78.getPose();
                    double var400 = var196;
                    double var486 = var201;
                    double var607 = ((double) var209);
                    double var623 = ((double) var211);
                    double var634 = ((double) var213);
                    float var640 = var78.getBbHeight();
                    double var641 = ((double) var640);
                    Pose var22 = Pose.CROUCHING;
                    if (var215 == var22) {
                        var649 = 1;
                    } else {
                        var649 = 0;
                    }
                    Pose var652 = var215;
                    Pose var23 = Pose.SWIMMING;
                    if (var652 == var23) {
                        var653 = 1;
                    } else {
                        var653 = 0;
                    }
                    Pose var654 = var215;
                    Pose var24 = Pose.FALL_FLYING;
                    if (var654 == var24) {
                        var655 = 1;
                    } else {
                        var655 = 0;
                    }
                    double var656 = 1.0;
                    EntityPoseState var216 = new EntityPoseState(var400, var486, var206, var607, var623, var634, var641, var649, var653, var655, var656);
                    int var218 = ShapeFx.auraStyle(var101);
                    if (var218 >= 0) {
                        double var317 = var196;
                        double var455 = var201;
                        double var566 = ((double) var132);
                        double var457 = (var455 + var566);
                        float var606 = var78.getBbHeight();
                        double var616 = var174.radius();
                        float var618 = ((float) var616);
                        float var611 = (var123 * var618);
                        Float var630 = 1.0F;
                        Object var620 = this.getSetting("Radius", var630);
                        Float var621 = ((Float) var620);
                        float var622 = var621;
                        Integer var637 = 5;
                        Object var627 = this.getSetting("Count", var637);
                        Integer var628 = ((Integer) var627);
                        int var629 = var628;
                        int var632 = var44;
                        float[] var633 = ShapeFx.rgb(var632);
                        int var638 = var45;
                        float[] var639 = ShapeFx.rgb(var638);
                        double var644 = this.doubleValue1;
                        double var650 = 3600.0;
                        double var646 = (var644 % var650);
                        float var648 = ((float) var646);
                        ShapeFx.aura(var218, var317, var457, var206, var606, var611, var622, var629, var633, var639, var128, var648, var144);
                        return;
                    }
                    if (!var109) {
                        if (var111) {
                        }
                    }
                    var255 = CosmeticAnchor.FEET;
                    var255 = CosmeticAnchor.CENTER;
                    CosmeticAnchor var64 = var255;
                    AnchorTransform var223 = var64.evaluate(var216);
                    float var439 = var132;
                    double var440 = ((double) var439);
                    AnchorTransform var224 = var223.evaluate(0.0, var440, 0.0);
                    Chams var225 = Chams.INSTANCE;
                    if (var225 != null) {
                        Chams var226 = Chams.INSTANCE;
                        boolean var227 = var226.isEnabled();
                        if (var227) {
                            boolean var228 = Chams.isThroughWalls();
                            if (var228) {
                                boolean var229 = Chams.isHideSelf();
                                if (!var229) {
                                    var658 = 2;
                                }
                            }
                        } else {
                        }
                    }
                    if (var144) {
                        var256 = 1;
                    } else {
                        var256 = 0;
                    }
                    var658 = var256;
                    int var306 = var658;
                    ClarityRenderUtils.CosmeticBatch var232 = ClarityRenderUtils.beginCosmetics(arg0, var306);
                    ClarityRenderUtils.CosmeticBatch var402 = var232;
                    int var442 = var44;
                    int var488 = var45;
                    double var535 = ((double) var128);
                    double var587 = this.doubleValue1;
                    CosmeticDrawBuilder var233 = new CosmeticDrawBuilder(var402, var442, var488, var535, var587, var140);
                    Vec3 var234 = ClarityRenderUtils.getCameraPos();
                    double[] var308 = new double[3];
                    int var443 = 0;
                    Vec3 var489 = var234;
                    double var490 = var489.x;
                    var308[var443] = var490;
                    int var444 = 1;
                    Vec3 var492 = var234;
                    double var493 = var492.y;
                    var308[var444] = var493;
                    int var445 = 2;
                    Vec3 var495 = var234;
                    double var496 = var495.z;
                    var308[var445] = var496;
                    var233.coordsVector = var308;
                    float var403 = var123;
                    double var404 = ((double) var403);
                    var233.applyStep(var224, var404);
                    if (var103) {
                        ICosmeticEffectRenderer var239 = var115.createRenderer(var192);
                        var239.renderEffect(var233);
                    } else {
                        if (var105) {
                            Aura var408 = this;
                            String var454 = "Sphere Radius";
                            float var511 = 1.2F;
                            Float var512 = var511;
                            Object var409 = var408.getSetting(var454, var512);
                            Float var410 = ((Float) var409);
                            float var411 = var410;
                            double var412 = ((double) var411);
                            Object var557 = this.getSetting("Sphere Detail", "High");
                            String var558 = ((String) var557);
                            boolean var514 = "Low".equalsIgnoreCase(var558);
                            if (var514) {
                                var515 = 2;
                            }
                        } else {
                            if (var107) {
                                Aura var507 = this;
                                Integer var579 = 6;
                                Object var508 = var507.getSetting("Arc Count", var579);
                                Integer var509 = ((Integer) var508);
                                int var510 = var509;
                                Float var590 = 1.2F;
                                Object var549 = this.getSetting("Arc Reach", var590);
                                Float var550 = ((Float) var549);
                                float var551 = var550;
                                double var552 = ((double) var551);
                                double var592 = var174.reach();
                                double var554 = (var552 * var592);
                                Integer var613 = 20;
                                Object var595 = this.getSetting("Crackle Rate", var613);
                                Integer var596 = ((Integer) var595);
                                int var597 = var596;
                                double var598 = ((double) var597);
                                LightningArcsAuraSpec var407 = new LightningArcsAuraSpec(var510, var554, var598, 600);
                                LightningArcsAuraRenderer var248 = new LightningArcsAuraRenderer(var407);
                                var248.renderEffect(var233);
                            } else {
                                if (var109) {
                                    Aura var498 = this;
                                    Float var571 = 1.5F;
                                    Object var499 = var498.getSetting("Circle Radius", var571);
                                    Float var500 = ((Float) var499);
                                    float var501 = var500;
                                    double var502 = ((double) var501);
                                    double var573 = var174.radius();
                                    double var504 = (var502 * var573);
                                    double var576 = this.doubleValue;
                                    GroundRunesAuraSpec var406 = new GroundRunesAuraSpec(var504, var576);
                                    GroundRunesAuraRenderer var244 = new GroundRunesAuraRenderer(var406);
                                    var244.renderEffect(var233);
                                    var515 = 3;
                                }
                            }
                        }
                        Boolean var602 = true;
                        Object var560 = this.getSetting("Hit Ripple", var602);
                        Boolean var561 = ((Boolean) var560);
                        boolean var562 = var561;
                        if (var562) {
                            var564 = this.evaluate(var224, var85);
                        } else {
                            var564 = List.of();
                        }
                        EnergySphereAuraSpec var249 = new EnergySphereAuraSpec(var412, var515, var564);
                        EnergySphereAuraSpec var414 = var249;
                        EnergySphereAuraRenderer var250 = new EnergySphereAuraRenderer(var414);
                        var250.renderEffect(var233);
                        if (var111) {
                            Aura var446 = this;
                            String var506 = "Radius";
                            Float var539 = 1.0F;
                            Object var447 = var446.getSetting(var506, var539);
                            Float var448 = ((Float) var447);
                            float var449 = var448;
                            double var450 = ((double) var449);
                            double var541 = var174.radius();
                            double var452 = (var450 * var541);
                            float var544 = var78.getBbHeight();
                            double var545 = ((double) var544);
                            ElementalAuraSpec var313 = new ElementalAuraSpec(var452, var545);
                            ICosmeticEffectRenderer var247 = var119.createRenderer(var313);
                            var247.renderEffect(var233);
                        }
                        var232.end();
                        return;
                    }
                }
            }
        }
    }

    private void applyStep(Player arg0, float arg1) {
        double var69 = 0.0;
        long var3 = System.nanoTime();
        double var23 = arg0.xOld;
        double var81 = arg0.getX();
        double var122 = arg0.xOld;
        double var83 = (var81 - var122);
        double var125 = ((double) arg1);
        double var85 = (var83 * var125);
        double var25 = (var23 + var85);
        double var28 = arg0.zOld;
        double var88 = arg0.getZ();
        double var128 = arg0.zOld;
        double var90 = (var88 - var128);
        double var131 = ((double) arg1);
        double var92 = (var90 * var131);
        double var30 = (var28 + var92);
        if (this.timestampMs2 == 0L) {
            var69 = 0.0;
        } else {
            long var114 = this.timestampMs2;
            var69 = (((double) (var3 - var114)) / 1.0E9);
        }
        double var18 = var69;
        if (this.timestampMs2 != 0L) {
            if (Double.compare(var18, 0.002) < 0) {
                return;
            }
        }
        if (this.timestampMs2 != 0L) {
            if (Double.compare(var18, 0.25) <= 0) {
                double var105 = this.doubleValue2;
                double var52 = (var25 - var105);
                double var134 = this.doubleValue3;
                double var109 = (var30 - var134);
                double var54 = Math.hypot(var52, var109);
                if (Double.compare(var54, 10.0) <= 0) {
                    double var71 = this.doubleValue4;
                    double var120 = AuraAnimationClock.computeDoubleMetric((var54 / var18));
                    double var73 = AuraAnimationClock.computeDoubleMetric(var71, var120, var18);
                    this.doubleValue4 = var73;
                }
                this.doubleValue2 = var25;
                this.doubleValue3 = var30;
                this.timestampMs2 = var3;
                return;
            }
        }
    }

    private void applyStep(Minecraft arg0, Player arg1, double arg2) {
        double var22 = 0.0;
        double var24 = 0.0;
        double var26 = 0.0;
        double var28 = 0.0;
        int var30 = 0;
        int var48 = 0;
        List var42 = this.entriesList;
        Predicate var171 = (p0) -> Aura.lambda$a(arg2, p0);
        boolean var43 = var42.removeIf(var171);
        int var45 = arg1.hurtTime;
        int var173 = this.counterValue;
        if (var45 > var173) {
            if (var45 > 0) {
                var48 = 1;
            } else {
                var48 = 0;
            }
            int var15 = var48;
            this.counterValue = var45;
            if (var15 != 0) {
                String var176 = this.resolveLabel();
                if ("Energy Sphere".equals(var176)) {
                    Boolean var183 = true;
                    if (!((Boolean) this.getSetting("Hit Ripple", var183))) {
                    }
                }
                return;
                double var58 = arg1.getX();
                double var61 = arg1.getY();
                double var188 = (((double) arg1.getBbHeight()) * 0.5);
                double var63 = (var61 + var188);
                double var66 = arg1.getZ();
                var22 = 64.0;
                var24 = 0.0;
                var26 = 0.0;
                var28 = 0.0;
                var30 = 0;
                Iterator var80 = arg0.level.entitiesForRendering().iterator();
                while (true) {
                    if (!var80.hasNext()) break;
                    Entity var85 = ((Entity) var80.next());
                    if (var85 == arg1) continue;
                    if (!(var85 instanceof LivingEntity)) continue;
                    LivingEntity var90 = ((LivingEntity) var85);
                    if (var90.isRemoved()) {
                    }
                    double var96 = (var90.getX() - var58);
                    double var99 = var90.getY();
                    double var196 = (((double) var90.getBbHeight()) * 0.5);
                    double var103 = ((var99 + var196) - var63);
                    double var108 = (var90.getZ() - var66);
                    double var112 = (var96 * var96);
                    double var206 = (var103 * var103);
                    double var114 = (var112 + var206);
                    double var210 = (var108 * var108);
                    double var116 = (var114 + var210);
                    double var212 = var22;
                    if (Double.compare(var116, var212) >= 0) continue;
                    if (Double.compare(var116, 1.0E-6) <= 0) continue;
                    var22 = var116;
                    var24 = var96;
                    var26 = var103;
                    var28 = var108;
                    var30 = 1;
                }
                if (var30 == 0) {
                    double var138 = Math.toRadians(((double) arg1.yBodyRot));
                    var24 = -(Math.sin(var138));
                    var26 = 0.25;
                    var28 = Math.cos(var138);
                }
                double var152 = var24;
                double var216 = var24;
                double var154 = (var152 * var216);
                double var218 = var26;
                double var235 = var26;
                double var220 = (var218 * var235);
                double var156 = (var154 + var220);
                double var222 = var28;
                double var237 = var28;
                double var224 = (var222 * var237);
                double var160 = Math.sqrt((var156 + var224));
                if (this.entriesList.size() >= 4) {
                    Object var167 = this.entriesList.remove(0);
                }
                List var169 = this.entriesList;
                double[] var182 = new double[4];
                double var241 = (var24 / var160);
                var182[0] = var241;
                double var245 = (var26 / var160);
                var182[1] = var245;
                double var249 = (var28 / var160);
                var182[2] = var249;
                var182[3] = arg2;
                boolean var170 = var169.add(var182);
                return;
            }
        }
    }

    private List evaluate(AnchorTransform arg0, double arg1) {
        int var32 = this.entriesList.size();
        ArrayList var4 = new ArrayList(var32);
        Iterator var21 = this.entriesList.iterator();
        while (true) {
            if (!var21.hasNext()) break;
            double[] var27 = ((double[]) var21.next());
            double var33 = var27[0];
            double var41 = arg0.doubleValue3;
            double var35 = (var33 * var41);
            double var44 = var27[1];
            double var64 = arg0.doubleValue4;
            double var46 = (var44 * var64);
            double var37 = (var35 + var46);
            double var49 = var27[2];
            double var67 = arg0.doubleValue5;
            double var51 = (var49 * var67);
            double var39 = (var37 + var51);
            double var54 = var27[0];
            double var70 = arg0.doubleValue6;
            double var56 = (var54 * var70);
            double var73 = var27[1];
            double var93 = arg0.doubleValue7;
            double var75 = (var73 * var93);
            double var58 = (var56 + var75);
            double var78 = var27[2];
            double var96 = arg0.doubleValue8;
            double var80 = (var78 * var96);
            double var60 = (var58 + var80);
            double var83 = var27[0];
            double var99 = arg0.doubleValue9;
            double var85 = (var83 * var99);
            double var102 = var27[1];
            double var116 = arg0.doubleValue10;
            double var104 = (var102 * var116);
            double var87 = (var85 + var104);
            double var107 = var27[2];
            double var119 = arg0.doubleValue11;
            double var109 = (var107 * var119);
            double var89 = (var87 + var109);
            double var122 = var27[3];
            double var113 = (arg1 - var122);
            SphereRippleImpulse var30 = new SphereRippleImpulse(var39, var60, var89, var113);
            boolean var29 = var4.add(var30);
        }
        return var4;
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
