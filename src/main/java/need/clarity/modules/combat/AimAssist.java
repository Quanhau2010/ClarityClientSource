package need.clarity.modules.combat;

import java.util.List;
import java.util.Spliterator;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import need.clarity.PerFrameEvents;
import need.clarity.api.Bus;
import need.clarity.api.IRotation;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.utility.Targets;
import need.clarity.sec.Diag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AimAssist extends ModuleBase {
    private long timestampMs;
    private static float floatValue;
    private static float floatValue1;
    private static boolean enabledState;
    private static boolean enabledState1;
    private static boolean enabledState2;
    private static boolean enabledState3;
    private static long timestampMs1;
    private static boolean enabledState4;
    private static volatile LivingEntity targetEntity;
    public static float floatValue2;
    public static float floatValue3;
    private static boolean enabledState5;
    private static float floatValue4;
    private static float floatValue5;
    private static volatile AimAssist INSTANCE;

    public static LivingEntity getCurrentTarget() {
        LivingEntity var0 = targetEntity;
        if (var0 != null) {
            if (var0.isAlive()) {
                if (var0.isRemoved()) {
                }
            }
            targetEntity = null;
            return null;
        }
        return var0;
    }

    public static void applyStep() {
        AimAssist var0 = INSTANCE;
        if (var0 != null) {
            if (!var0.isEnabled()) {
            }
        }
        targetEntity = null;
    }

    public static void applyStep(LivingEntity arg0) {
        LivingEntity var4 = null;
        if (arg0 != null) {
            if (arg0.isAlive()) {
                var4 = arg0;
            }
        } else {
            var4 = null;
        }
        targetEntity = var4;
    }

    public static void applyStep1() {
        targetEntity = null;
    }

    public static void setSilentRotation(float arg0, float arg1) {
        enabledState5 = true;
        floatValue4 = arg0;
        floatValue5 = arg1;
    }

    public static boolean isEligible() {
        return enabledState5;
    }

    public static float computeFloatMetric() {
        return floatValue4;
    }

    public static float computeFloatMetric1() {
        return floatValue5;
    }

    public static void applyStep2() {
        enabledState5 = false;
    }

    public static boolean isEligible(Entity arg0) {
        LocalPlayer var6 = Minecraft.getInstance().player;
        if (var6 != null) {
            if (arg0 == null) {
            }
        }
        return false;
        if (arg0 == var6) {
            return true;
        }
        UUID var11 = arg0.getUUID();
        UUID var23 = var6.getUUID();
        if (var11.equals(var23)) {
            return true;
        }
        if (var6.getVehicle() != arg0) {
            if (arg0.getVehicle() == var6) {
            }
        }
        return true;
        return false;
    }

    public static boolean isActivelyTracking() {
        return enabledState;
    }

    public static boolean isEligible1() {
        return enabledState4;
    }

    public static float computeFloatMetric2() {
        return floatValue;
    }

    public static float computeFloatMetric3() {
        return floatValue1;
    }

    public static Vec3 evaluate(float arg0, float arg1) {
        float var16 = (arg0 * 0.017453292F);
        float var18 = (arg1 * -0.017453292F);
        float var22 = Mth.cos(((double) var16));
        float var26 = Mth.sin(((double) var16));
        float var30 = Mth.cos(((double) var18));
        float var34 = Mth.sin(((double) var18));
        double var38 = ((double) (var34 * var22));
        double var41 = ((double) (var26 * -1.0F));
        double var44 = ((double) (var30 * var22));
        Vec3 var35 = new Vec3(var38, var41, var44);
        return var35;
    }

    public AimAssist() {
        super("Aim Assist", "Combat");
        this.timestampMs = -1L;
        INSTANCE = this;
        AimAssistRotationProvider var11 = new AimAssistRotationProvider();
        Bus.register(IRotation.class, var11);
        PerFrameEvents.register(() -> this.applyStep3());
    }

    public static boolean isEligible2() {
        int var6 = 0;
        AimAssist var0 = INSTANCE;
        if (var0 != null) {
            if (!var0.isEnabled()) {
            }
        }
        if (KillAura.isEligible()) {
            var6 = 1;
        } else {
            var6 = 0;
        }
        return var6;
    }

    public void onEnable() {
        this.timestampMs = -1L;
        enabledState2 = false;
    }

    public void onDisable() {
        targetEntity = null;
        if (!enabledState) {
            if (enabledState1) {
            }
        }
        enabledState4 = false;
        enabledState = false;
        enabledState1 = false;
        enabledState2 = false;
        enabledState4 = false;
    }

    private void applyStep3() {
        String var520 = null;
        Vec3 var628 = null;
        LivingEntity var134 = null;
        float var295 = 0.0F;
        int var319 = 0;
        float var324 = 0.0F;
        float var325 = 0.0F;
        float var326 = 0.0F;
        long var327 = 0L;
        boolean var1 = KillAura.isEligible();
        if (var1) {
            enabledState2 = false;
            enabledState3 = false;
            return;
        }
        boolean var51 = enabledState;
        enabledState1 = var51;
        enabledState = false;
        enabledState4 = false;
        Minecraft var54 = Minecraft.getInstance();
        LocalPlayer var56 = var54.player;
        if (var56 != null) {
            ClientLevel var58 = var54.level;
            if (var58 == null) {
            }
        }
        enabledState2 = false;
        enabledState1 = false;
        return;
        long var61 = System.currentTimeMillis();
        long var64 = this.timestampMs;
        int var66 = Long.compare(var64, 0L);
        if (var66 < 0) {
            var327 = 1L;
        } else {
            AimAssist var511 = this;
            long var512 = var511.timestampMs;
            long var322 = (var61 - var512);
            var327 = Math.min(var322, 100L);
        }
        long var14 = var327;
        this.timestampMs = var61;
        int var393 = 0;
        Boolean var394 = var393;
        Object var69 = this.getSetting("Silent Rotations", var394);
        Boolean var70 = ((Boolean) var69);
        boolean var71 = var70;
        boolean var73 = this.isEnabled();
        if (var73) {
            int var395 = 1;
            Boolean var396 = var395;
            Object var75 = this.getSetting("Not in GUI", var396);
            Boolean var76 = ((Boolean) var75);
            boolean var77 = var76;
            if (var77) {
                Screen var79 = var54.screen;
                if (var79 == null) {
                }
            }
            int var397 = 0;
            Boolean var398 = var397;
            Object var81 = this.getSetting("Not While Using", var398);
            Boolean var82 = ((Boolean) var81);
            boolean var83 = var82;
            if (var83) {
                LocalPlayer var85 = var54.player;
                boolean var86 = var85.isUsingItem();
                if (!var86) {
                }
            }
            int var399 = 0;
            Boolean var400 = var399;
            Object var88 = this.getSetting("Only Weapon", var400);
            Boolean var89 = ((Boolean) var88);
            boolean var90 = var89;
            if (var90) {
                LocalPlayer var92 = var54.player;
                boolean var93 = isEligible(var92);
                if (var93) {
                }
            }
            var319 = 1;
        } else {
            var319 = 0;
        }
        int var17 = var319;
        boolean var94 = Diag.enabled();
        if (var94) {
            long var95 = System.currentTimeMillis();
            long var401 = timestampMs1;
            long var99 = (var95 - var401);
            long var403 = 3000L;
            int var101 = Long.compare(var99, var403);
            if (var101 >= 0) {
                timestampMs1 = var95;
                boolean var105 = this.isEnabled();
                int var405 = var17;
                boolean var6 = KillAura.isEligible();
                LivingEntity var7 = targetEntity;
                if (var7 != null) {
                    LivingEntity var518 = targetEntity;
                    Component var519 = var518.getName();
                    var520 = var519.getString();
                } else {
                    var520 = "null";
                }
                String var106 = "aimassist en=" + (var105) + " silent=" + (var71) + " should=" + (var405) + " killActive=" + (var6) + " target=" + (var520);
                Diag.log(var106);
            }
            if (var17 == 0) {
                targetEntity = null;
                if (var71) {
                    boolean var110 = enabledState1;
                    if (var110) {
                        boolean var111 = enabledState3;
                        if (var111) {
                            long var406 = var14;
                            this.applyStep(var54, var406);
                        }
                    }
                } else {
                    enabledState2 = false;
                }
                return;
            }
            int var408 = 5;
            Integer var409 = var408;
            Object var115 = this.getSetting("Range", var409);
            Integer var116 = ((Integer) var115);
            int var117 = var116;
            double var119 = ((double) var117);
            int var410 = var117;
            double var411 = ((double) var410);
            double var121 = (var119 * var411);
            ClientLevel var124 = var54.level;
            Iterable var125 = var124.entitiesForRendering();
            Spliterator var126 = var125.spliterator();
            Stream var127 = StreamSupport.stream(var126, false);
            double var413 = var121;
            Predicate var338 = (p0) -> AimAssist.lambda$a(var54, var413, p0);
            Stream var128 = var127.filter(var338);
            Function var339 = (p0) -> AimAssist.lambda$b(p0);
            Stream var129 = var128.map(var339);
            Collector var340 = Collectors.toList();
            Object var130 = var129.collect(var340);
            List var131 = ((List) var130);
            boolean var133 = var131.isEmpty();
            if (var133) {
                var134 = null;
            } else {
                LocalPlayer var392 = var54.player;
                var134 = Targets.evaluate(var131, var392);
            }
            LivingEntity var23 = var134;
            if (var23 == null) {
                targetEntity = null;
                if (var71) {
                    boolean var138 = enabledState1;
                    if (var138) {
                        boolean var139 = enabledState3;
                        if (var139) {
                            long var415 = var14;
                            this.applyStep(var54, var415);
                        }
                    }
                }
            } else {
                targetEntity = var23;
                enabledState2 = false;
                LocalPlayer var145 = var54.player;
                Vec3 var146 = var145.getEyePosition();
                String var417 = "Closest";
                Object var148 = this.getSetting("Aim At", var417);
                String var149 = ((String) var148);
                boolean var151 = "Center".equals(var149);
                if (var151) {
                    AABB var153 = var23.getBoundingBox();
                    AABB var418 = var153;
                    double var419 = var418.minX;
                    double var522 = var153.maxX;
                    double var421 = (var419 + var522);
                    double var423 = (var421 / 2.0);
                    double var527 = var153.minY;
                    double var565 = var153.maxY;
                    double var529 = (var527 + var565);
                    double var531 = (var529 / 2.0);
                    double var570 = var153.minZ;
                    double var603 = var153.maxZ;
                    double var572 = (var570 + var603);
                    double var574 = (var572 / 2.0);
                    Vec3 var154 = new Vec3(var423, var531, var574);
                    var628 = var154;
                } else {
                    AABB var299 = var23.getBoundingBox();
                    double var301 = var146.x;
                    AABB var479 = var299;
                    double var480 = var479.minX;
                    double var482 = (var480 - 0.25);
                    int var303 = Double.compare(var301, var482);
                    if (var303 > 0) {
                        double var305 = var146.x;
                        AABB var484 = var299;
                        double var485 = var484.maxX;
                        double var487 = (var485 + 0.25);
                        int var307 = Double.compare(var305, var487);
                        if (var307 < 0) {
                            double var309 = var146.z;
                            AABB var489 = var299;
                            double var490 = var489.minZ;
                            double var492 = (var490 - 0.25);
                            int var311 = Double.compare(var309, var492);
                            if (var311 > 0) {
                                double var313 = var146.z;
                                AABB var494 = var299;
                                double var495 = var494.maxZ;
                                double var497 = (var495 + 0.25);
                                int var315 = Double.compare(var313, var497);
                                if (var315 < 0) {
                                    AABB var499 = var299;
                                    double var500 = var499.minX;
                                    double var546 = var299.maxX;
                                    double var502 = (var500 + var546);
                                    double var504 = (var502 / 2.0);
                                    double var551 = var299.minY;
                                    double var577 = var299.maxY;
                                    double var608 = var299.minY;
                                    double var579 = (var577 - var608);
                                    double var581 = (var579 * 0.6);
                                    double var553 = (var551 + var581);
                                    double var584 = var299.minZ;
                                    double var613 = var299.maxZ;
                                    double var586 = (var584 + var613);
                                    double var588 = (var586 / 2.0);
                                    Vec3 var316 = new Vec3(var504, var553, var588);
                                    var628 = var316;
                                } else {
                                    AABB var506 = var299;
                                    double var507 = var506.minX;
                                    double var556 = var146.x;
                                    double var591 = var299.maxX;
                                    double var558 = Math.min(var556, var591);
                                    double var509 = Math.max(var507, var558);
                                    double var561 = var299.minY;
                                    double var594 = var146.y;
                                    double var618 = var299.maxY;
                                    double var596 = Math.min(var594, var618);
                                    double var563 = Math.max(var561, var596);
                                    double var599 = var299.minZ;
                                    double var621 = var146.z;
                                    double var625 = var299.maxZ;
                                    double var623 = Math.min(var621, var625);
                                    double var601 = Math.max(var599, var623);
                                    Vec3 var317 = new Vec3(var509, var563, var601);
                                    var628 = var317;
                                }
                                Vec3 var155 = var628;
                                Vec3 var156 = var155.subtract(var146);
                                double var158 = var156.lengthSqr();
                                double var425 = 1.0E-6;
                                int var160 = Double.compare(var158, var425);
                                if (var160 < 0) {
                                    return;
                                }
                                double var162 = var156.x;
                                Vec3 var427 = var156;
                                double var428 = var427.x;
                                double var164 = (var162 * var428);
                                Vec3 var430 = var156;
                                double var431 = var430.z;
                                double var534 = var156.z;
                                double var433 = (var431 * var534);
                                double var166 = (var164 + var433);
                                double var168 = Math.sqrt(var166);
                                double var435 = 0.05;
                                int var172 = Double.compare(var168, var435);
                                if (var172 < 0) {
                                    if (var71) {
                                        boolean var174 = enabledState1;
                                        if (var174) {
                                            boolean var175 = enabledState3;
                                            if (var175) {
                                                long var437 = var14;
                                                this.applyStep(var54, var437);
                                            }
                                            return;
                                        }
                                    }
                                }
                                double var178 = var156.x;
                                double var439 = -1.0;
                                double var180 = (var178 * var439);
                                Vec3 var441 = var156;
                                double var442 = var441.z;
                                double var182 = Math.atan2(var180, var442);
                                double var444 = 180.0;
                                double var184 = (var182 * var444);
                                double var446 = 3.141592653589793;
                                double var186 = (var184 / var446);
                                float var188 = ((float) var186);
                                double var190 = var156.y;
                                double var448 = var168;
                                double var192 = Math.atan2(var190, var448);
                                double var450 = -1.0;
                                double var194 = (var192 * var450);
                                double var452 = 180.0;
                                double var196 = (var194 * var452);
                                double var454 = 3.141592653589793;
                                double var198 = (var196 / var454);
                                float var200 = ((float) var198);
                                if (var71) {
                                    boolean var202 = enabledState1;
                                    if (!var202) {
                                        LocalPlayer var204 = var54.player;
                                        float var205 = var204.getYRot();
                                        floatValue = var205;
                                        LocalPlayer var207 = var54.player;
                                        float var208 = var207.getXRot();
                                        floatValue1 = var208;
                                    }
                                    if (var71) {
                                        boolean var210 = enabledState1;
                                        if (var210) {
                                            var324 = floatValue;
                                        }
                                    } else {
                                        LocalPlayer var297 = var54.player;
                                        var324 = var297.getYRot();
                                    }
                                    float var31 = var324;
                                    if (var71) {
                                        boolean var212 = enabledState1;
                                        if (var212) {
                                            var295 = floatValue1;
                                        }
                                    } else {
                                        LocalPlayer var294 = var54.player;
                                        var295 = var294.getXRot();
                                    }
                                    float var32 = var295;
                                    float var214 = (var188 - var31);
                                    float var215 = computeFloatMetric(var214);
                                    float var217 = (var200 - var32);
                                    float var218 = computeFloatMetric(var217);
                                    int var456 = 90;
                                    Integer var457 = var456;
                                    Object var220 = this.getSetting("FOV", var457);
                                    Integer var221 = ((Integer) var220);
                                    int var222 = var221;
                                    LocalPlayer var224 = var54.player;
                                    float var225 = var224.getYRot();
                                    LocalPlayer var227 = var54.player;
                                    float var228 = var227.getXRot();
                                    float var230 = (var188 - var225);
                                    float var231 = computeFloatMetric(var230);
                                    float var233 = (var200 - var228);
                                    float var234 = computeFloatMetric(var233);
                                    float var236 = (var231 * var231);
                                    float var458 = var234;
                                    float var353 = (var234 * var458);
                                    float var237 = (var236 + var353);
                                    double var238 = ((double) var237);
                                    double var240 = Math.sqrt(var238);
                                    float var242 = ((float) var240);
                                    float var355 = ((float) var222);
                                    float var459 = 2.0F;
                                    float var356 = (var355 / var459);
                                    int var244 = Float.compare(var242, var356);
                                    if (var244 > 0) {
                                        if (var71) {
                                            boolean var290 = enabledState1;
                                            if (var290) {
                                                boolean var291 = enabledState3;
                                                if (var291) {
                                                    long var477 = var14;
                                                    this.applyStep(var54, var477);
                                                }
                                                return;
                                            }
                                        }
                                    }
                                    int var460 = 10;
                                    Integer var461 = var460;
                                    Object var246 = this.getSetting("Horizontal Speed", var461);
                                    Integer var247 = ((Integer) var246);
                                    int var248 = var247;
                                    int var462 = 10;
                                    Integer var463 = var462;
                                    Object var250 = this.getSetting("Vertical Speed", var463);
                                    Integer var251 = ((Integer) var250);
                                    int var252 = var251;
                                    int var464 = 1;
                                    Boolean var465 = var464;
                                    Object var254 = this.getSetting("Aim Horizontally", var465);
                                    Boolean var255 = ((Boolean) var254);
                                    boolean var256 = var255;
                                    int var466 = 1;
                                    Boolean var467 = var466;
                                    Object var258 = this.getSetting("Aim Vertically", var467);
                                    Boolean var259 = ((Boolean) var258);
                                    boolean var260 = var259;
                                    float var362 = ((float) var248);
                                    float var468 = 20.0F;
                                    float var363 = (var362 / var468);
                                    float var262 = (var215 * var363);
                                    float var366 = ((float) var14);
                                    float var469 = 50.0F;
                                    float var367 = (var366 / var469);
                                    float var263 = (var262 * var367);
                                    float var369 = ((float) var252);
                                    float var470 = 20.0F;
                                    float var370 = (var369 / var470);
                                    float var265 = (var218 * var370);
                                    float var373 = ((float) var14);
                                    float var471 = 50.0F;
                                    float var374 = (var373 / var471);
                                    float var266 = (var265 * var374);
                                    float var376 = Math.abs(var215);
                                    float var268 = computeFloatMetric(var263, var376);
                                    float var378 = Math.abs(var218);
                                    float var270 = computeFloatMetric(var266, var378);
                                    if (var71) {
                                        if (var256) {
                                            var326 = (var31 + var268);
                                        } else {
                                            var326 = var31;
                                        }
                                        float var47 = var326;
                                        if (var260) {
                                            var325 = (var32 + var270);
                                        } else {
                                            var325 = var32;
                                        }
                                        float var48 = var325;
                                        float var280 = floatValue;
                                        float var475 = floatValue;
                                        float var384 = (var47 - var475);
                                        float var385 = computeFloatMetric(var384);
                                        float var281 = (var280 + var385);
                                        floatValue = var281;
                                        float var476 = var48;
                                        float var387 = Math.min(90.0F, var476);
                                        float var283 = Math.max(-90.0F, var387);
                                        floatValue1 = var283;
                                        enabledState = true;
                                        enabledState4 = true;
                                        enabledState3 = true;
                                    } else {
                                        if (var256) {
                                            LocalPlayer var274 = var54.player;
                                            float var472 = var268;
                                            float var380 = (var31 + var472);
                                            var274.setYRot(var380);
                                        }
                                        if (var260) {
                                            LocalPlayer var277 = var54.player;
                                            float var473 = 90.0F;
                                            float var517 = (var32 + var270);
                                            float var474 = Math.min(var473, var517);
                                            float var382 = Math.max(-90.0F, var474);
                                            var277.setXRot(var382);
                                        }
                                        return;
                                        enabledState2 = false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return;
        }
    }

    private void applyStep(Minecraft arg0, long arg1) {
        enabledState2 = true;
        float var18 = arg0.player.getYRot();
        float var21 = arg0.player.getXRot();
        float var5 = floatValue;
        float var24 = computeFloatMetric((var18 - var5));
        float var64 = floatValue1;
        float var27 = computeFloatMetric((var21 - var64));
        if (Float.compare(Math.abs(var24), 0.5F) < 0) {
            if (Float.compare(Math.abs(var27), 0.5F) < 0) {
                enabledState = false;
                enabledState1 = false;
                enabledState2 = false;
                enabledState4 = false;
                enabledState3 = false;
                floatValue = var18;
                floatValue1 = var21;
                floatValue2 = var18;
                floatValue3 = var21;
                return;
            }
        }
        Integer var90 = 10;
        int var46 = ((Integer) this.getSetting("Horizontal Speed", var90));
        Integer var92 = 10;
        int var50 = ((Integer) this.getSetting("Vertical Speed", var92));
        float var71 = (((float) var46) / 20.0F);
        float var52 = (var24 * var71);
        float var75 = (((float) arg1) / 50.0F);
        float var53 = (var52 * var75);
        float var77 = Math.abs(var24);
        float var54 = computeFloatMetric(var53, var77);
        float var80 = (((float) var50) / 20.0F);
        float var56 = (var27 * var80);
        float var84 = (((float) arg1) / 50.0F);
        float var57 = (var56 * var84);
        float var86 = Math.abs(var27);
        float var58 = computeFloatMetric(var57, var86);
        floatValue = (floatValue + var54);
        float var98 = (floatValue1 + var58);
        float var89 = Math.min(90.0F, var98);
        floatValue1 = Math.max(-90.0F, var89);
        enabledState = true;
    }

    public static void applyStep(float arg0, float arg1) {
        floatValue = arg0;
        float var10 = Math.min(90.0F, arg1);
        floatValue1 = Math.max(-90.0F, var10);
        enabledState = true;
        enabledState4 = true;
        enabledState3 = false;
    }

    public static void applyStep4(float arg0, float arg1) {
        floatValue = arg0;
        float var7 = Math.min(90.0F, arg1);
        floatValue1 = Math.max(-90.0F, var7);
    }

    public static void clearSilentRotation() {
        enabledState4 = false;
    }

    private static boolean isEligible(LocalPlayer arg0) {
        int var16 = 0;
        ItemStack var5 = arg0.getMainHandItem();
        Item var7 = var5.getItem();
        TagKey var2 = ItemTags.SWORDS;
        if (!var5.is(var2)) {
            TagKey var17 = ItemTags.AXES;
            if (!var5.is(var17)) {
                TagKey var18 = ItemTags.MACE_ENCHANTABLE;
                if (!var5.is(var18)) {
                    if ((var7 instanceof TridentItem)) {
                    }
                }
                var16 = 1;
                var16 = 0;
                return var16;
            }
        }
    }

    private static float computeFloatMetric(float arg0) {
        float var17 = 0.0F;
        var17 = (arg0 % 360.0F);
        if (Float.compare(var17, 180.0F) >= 0) {
            var17 = (var17 - 360.0F);
        }
        if (Float.compare(var17, -180.0F) < 0) {
            var17 = (var17 + 360.0F);
        }
        return var17;
    }

    private static float computeFloatMetric(float arg0, float arg1) {
        float var5 = (arg1 * -1.0F);
        if (Float.compare(arg0, arg1) > 0) {
            return arg1;
        }
        if (Float.compare(arg0, var5) < 0) {
            return var5;
        }
        return arg0;
    }

    static {
        floatValue = 0.0F;
        floatValue1 = 0.0F;
        enabledState = false;
        enabledState1 = false;
        enabledState2 = false;
        enabledState3 = false;
        timestampMs1 = 0L;
        enabledState4 = false;
        targetEntity = null;
        floatValue2 = 0.0F;
        floatValue3 = 0.0F;
        enabledState5 = false;
        floatValue4 = 0.0F;
        floatValue5 = 0.0F;
    }

}
