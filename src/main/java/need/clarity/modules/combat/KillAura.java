package need.clarity.modules.combat;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import need.clarity.EventStartAttack;
import need.clarity.LocalPlayerRotationState;
import need.clarity.PerAttackEvents;
import need.clarity.PerFrameEvents;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.utility.Targets;
import need.clarity.sec.Diag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class KillAura extends ModuleBase {
    private static volatile KillAura INSTANCE;
    private static long timestampMs;
    private static long timestampMs1;
    private static long timestampMs2;
    private final Random fieldVal;
    private long timestampMs3;
    private boolean enabledState;
    private LivingEntity targetEntity;
    private final double[] coordsVector;
    private final double[] coordsVector1;
    private final double[] coordsVector2;
    private long timestampMs4;
    private long timestampMs5;
    private float floatValue;
    private float floatValue1;
    private double doubleValue;
    private double doubleValue1;
    private long timestampMs6;
    private final double[] coordsVector3;
    private volatile LivingEntity targetEntity1;
    private boolean enabledState1;
    private long timestampMs7;

    public KillAura() {
        super("Kill Aura", "Combat");
        Random var33 = new Random();
        this.fieldVal = var33;
        this.timestampMs3 = -1L;
        this.enabledState = false;
        this.targetEntity = null;
        double[] var39 = new double[3];
        var39[0] = 0.5;
        var39[1] = 0.65;
        var39[2] = 0.5;
        this.coordsVector = var39;
        double[] var41 = new double[3];
        var41[0] = 0.5;
        var41[1] = 0.65;
        var41[2] = 0.5;
        this.coordsVector1 = var41;
        double[] var43 = new double[3];
        var43[0] = 0.0;
        var43[1] = 0.0;
        var43[2] = 0.0;
        this.coordsVector2 = var43;
        this.timestampMs4 = 0L;
        this.timestampMs5 = -1L;
        this.floatValue = 0.0F;
        this.floatValue1 = 0.0F;
        this.doubleValue = 1.0;
        this.doubleValue1 = 1.0;
        this.timestampMs6 = 0L;
        double[] var57 = new double[24];
        this.coordsVector3 = var57;
        this.targetEntity1 = null;
        this.enabledState1 = false;
        this.timestampMs7 = -1L;
        INSTANCE = this;
        int var7 = 0;
        while (true) {
            int var28 = var7;
            int var64 = this.coordsVector3.length;
            if (var28 >= var64) break;
            double[] var30 = this.coordsVector3;
            int var65 = var7;
            double var72 = ((this.fieldVal.nextDouble() * 3.141592653589793) * 2.0);
            var30[var65] = var72;
            var7 = (var7 + 1);
        }
        PerFrameEvents.register(() -> this.applyStep());
    }

    public static boolean isEligible() {
        int var5 = 0;
        KillAura var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    public static LivingEntity evaluate() {
        LivingEntity var4 = null;
        KillAura var0 = INSTANCE;
        if (var0 == null) {
            var4 = null;
        } else {
            var4 = var0.targetEntity1;
        }
        return var4;
    }

    public void onEnable() {
        this.timestampMs3 = -1L;
        this.applyStep1();
    }

    public void onDisable() {
        this.targetEntity1 = null;
        this.applyStep1();
        AimAssist.applyStep();
    }

    private void applyStep() {
        float var1035 = 0.0F;
        float var1036 = 0.0F;
        float var1037 = 0.0F;
        float var1038 = 0.0F;
        float var1040 = 0.0F;
        float var35 = 0.0F;
        double var50 = 0.0;
        double var62 = 0.0;
        double var64 = 0.0;
        double var66 = 0.0;
        double var68 = 0.0;
        int var72 = 0;
        long var83 = 0L;
        float var303 = 0.0F;
        float var415 = 0.0F;
        Vec3 var472 = null;
        int var474 = 0;
        float var477 = 0.0F;
        float var478 = 0.0F;
        float var479 = 0.0F;
        int var611 = 0;
        int var623 = 0;
        long var817 = 0L;
        String var868 = null;
        Minecraft var1 = Minecraft.getInstance();
        long var73 = System.currentTimeMillis();
        long var76 = this.timestampMs3;
        int var78 = Long.compare(var76, 0L);
        if (var78 < 0) {
            var83 = 1L;
        } else {
            KillAura var612 = this;
            long var613 = var612.timestampMs3;
            long var81 = (var73 - var613);
            long var615 = 100L;
            var83 = Math.min(var81, var615);
        }
        long var17 = var83;
        this.timestampMs3 = var73;
        boolean var86 = Diag.enabled();
        if (var86) {
            long var87 = System.currentTimeMillis();
            long var617 = timestampMs2;
            long var91 = (var87 - var617);
            long var619 = 3000L;
            int var93 = Long.compare(var91, var619);
            if (var93 >= 0) {
                timestampMs2 = var87;
                boolean var97 = this.isEnabled();
                LocalPlayer var481 = var1.player;
                if (var481 != null) {
                    var611 = 1;
                } else {
                    var611 = 0;
                }
                Minecraft var621 = var1;
                ClientLevel var622 = var621.level;
                if (var622 != null) {
                    var623 = 1;
                } else {
                    var623 = 0;
                }
                String var98 = "killaura-frame en=" + (var97) + " player=" + (var611) + " level=" + (var623);
                Diag.log(var98);
            }
            LocalPlayer var100 = var1.player;
            if (var100 != null) {
                ClientLevel var102 = var1.level;
                if (var102 == null) {
                }
            }
            int var609 = 0;
            this.enabledState = var609;
            Object var610 = null;
            this.targetEntity1 = var610;
            AimAssist.applyStep();
            return;
            boolean var104 = this.isEnabled();
            if (!var104) {
                this.targetEntity1 = null;
                this.targetEntity = null;
                AimAssist.applyStep();
                boolean var108 = this.enabledState;
                if (var108) {
                    long var624 = var17;
                    this.applyStep(var1, var624);
                }
                return;
            }
            boolean var111 = this.isEligible(var1);
            if (!var111) {
                this.targetEntity1 = null;
                this.targetEntity = null;
                AimAssist.applyStep();
                boolean var115 = this.enabledState;
                if (var115) {
                    long var626 = var17;
                    this.applyStep(var1, var626);
                }
                return;
            }
            LivingEntity var118 = this.evaluate(var1);
            this.targetEntity1 = var118;
            boolean var120 = Diag.enabled();
            if (var120) {
                long var121 = System.currentTimeMillis();
                long var628 = timestampMs;
                long var125 = (var121 - var628);
                long var630 = 3000L;
                int var127 = Long.compare(var125, var630);
                if (var127 >= 0) {
                    timestampMs = var121;
                    boolean var131 = this.isEnabled();
                    Minecraft var632 = var1;
                    boolean var492 = this.isEligible(var632);
                    Minecraft var633 = var1;
                    ClientLevel var634 = var633.level;
                    List var635 = var634.players();
                    int var636 = var635.size();
                    if (var118 == null) {
                        var868 = "none";
                    } else {
                        LivingEntity var865 = var118;
                        Component var866 = var865.getName();
                        String var867 = var866.getString();
                        String var933 = "%.2f";
                        int var962 = 1;
                        Object[] var963 = new Object[var962];
                        int var1005 = 0;
                        LivingEntity var1011 = var118;
                        LocalPlayer var1026 = var1.player;
                        double var1012 = var1011.distanceToSqr(var1026);
                        double var1014 = Math.sqrt(var1012);
                        Double var1016 = var1014;
                        var963[var1005] = var1016;
                        String var934 = String.format(var933, var963);
                        var868 = (var867) + " d=" + (var934);
                    }
                    String var132 = "killaura en=" + (var131) + " canRun=" + (var492) + " playersNear=" + (var636) + " target=" + (var868);
                    Diag.log(var132);
                }
                if (var118 == null) {
                    this.targetEntity = null;
                    AimAssist.applyStep();
                    boolean var136 = this.enabledState;
                    if (var136) {
                        long var637 = var17;
                        this.applyStep(var1, var637);
                    }
                    return;
                }
                AimAssist.applyStep(var118);
                int var639 = 1;
                Boolean var640 = var639;
                Object var140 = this.getSetting("Humanize", var640);
                Boolean var141 = ((Boolean) var140);
                boolean var142 = var141;
                String var641 = "Dynamic";
                Object var144 = this.getSetting("Aim At", var641);
                String var145 = ((String) var144);
                long var642 = 50L;
                long var148 = Math.min(var17, var642);
                double var150 = ((double) var148);
                double var644 = 1000.0;
                double var152 = (var150 / var644);
                long var154 = System.nanoTime();
                double var156 = ((double) var154);
                double var646 = 1.0E9;
                double var158 = (var156 / var646);
                LivingEntity var498 = this.targetEntity;
                if (var118 != var498) {
                    LivingEntity var162 = this.targetEntity;
                    if (var162 != null) {
                        boolean var164 = this.enabledState;
                        if (var164) {
                            var474 = 1;
                        } else {
                        }
                    }
                    var474 = 0;
                    int var28 = var474;
                    this.targetEntity = var118;
                    this.applyStep(var145);
                    KillAura var810 = this;
                    long var811 = var810.evaluate("Point Change", 500, 1400);
                    long var503 = (var73 + var811);
                    this.timestampMs4 = var503;
                    if (var142) {
                        int var648 = 60;
                        int var813 = 180;
                        long var170 = this.evaluate("Reaction Delay", var648, var813);
                        int var814 = var28;
                        if (var814 != 0) {
                            long var815 = var170;
                            long var935 = 2L;
                            var817 = (var815 / var935);
                        } else {
                            var817 = var170;
                        }
                        long var508 = (var73 + var817);
                        this.timestampMs5 = var508;
                    } else {
                        long var607 = -1L;
                        this.timestampMs5 = var607;
                    }
                    if (var28 == 0) {
                        this.floatValue = 0.0F;
                        this.floatValue1 = 0.0F;
                    }
                    if (var142) {
                        KillAura var649 = this;
                        long var650 = var649.timestampMs5;
                        int var179 = Long.compare(var73, var650);
                        if (var179 < 0) {
                            boolean var180 = Diag.enabled();
                            if (var180) {
                                long var181 = System.currentTimeMillis();
                                long var652 = timestampMs1;
                                long var185 = (var181 - var652);
                                long var654 = 1000L;
                                int var187 = Long.compare(var185, var654);
                                if (var187 >= 0) {
                                    timestampMs1 = var181;
                                    Component var191 = var118.getName();
                                    String var192 = var191.getString();
                                    long var513 = this.timestampMs5;
                                    long var819 = var73;
                                    long var515 = (var513 - var819);
                                    KillAura var821 = this;
                                    boolean var822 = var821.enabledState;
                                    String var193 = "killaim react-wait t=" + (var192) + " msLeft=" + (var515) + " rotating=" + (var822);
                                    Diag.log(var193);
                                }
                                boolean var195 = this.enabledState;
                                if (var195) {
                                    float var196 = AimAssist.computeFloatMetric2();
                                    float var517 = AimAssist.computeFloatMetric3();
                                    AimAssist.applyStep(var196, var517);
                                }
                                return;
                            }
                        }
                    }
                    LocalPlayer var198 = var1.player;
                    Vec3 var199 = var198.getEyePosition();
                    if (var142) {
                        LivingEntity var606 = var118;
                        Vec3 var809 = var199;
                        String var864 = var145;
                        long var931 = var73;
                        double var988 = var152;
                        double var1009 = var158;
                        var472 = this.evaluate(var606, var809, var864, var931, var988, var1009);
                    } else {
                        Vec3 var656 = var199;
                        var472 = this.evaluate(var118, var656);
                    }
                    Vec3 var1034 = var472;
                    Vec3 var203 = var1034.subtract(var199);
                    double var205 = var203.lengthSqr();
                    double var657 = 1.0E-6;
                    int var207 = Double.compare(var205, var657);
                    if (var207 < 0) {
                        return;
                    }
                    double var209 = var203.x;
                    Vec3 var659 = var203;
                    double var660 = var659.x;
                    double var211 = (var209 * var660);
                    Vec3 var662 = var203;
                    double var663 = var662.z;
                    Vec3 var869 = var203;
                    double var870 = var869.z;
                    double var665 = (var663 * var870);
                    double var213 = (var211 + var665);
                    double var215 = Math.sqrt(var213);
                    boolean var218 = this.enabledState;
                    if (var218) {
                        float var219 = AimAssist.computeFloatMetric2();
                        var1035 = var219;
                        float var220 = AimAssist.computeFloatMetric3();
                        var1036 = var220;
                    } else {
                        LocalPlayer var466 = var1.player;
                        float var467 = var466.getYRot();
                        var1035 = var467;
                        LocalPlayer var469 = var1.player;
                        float var470 = var469.getXRot();
                        var1036 = var470;
                    }
                    float var667 = 90.0F;
                    Float var668 = var667;
                    Object var222 = this.getSetting("Max Pitch", var668);
                    Float var223 = ((Float) var222);
                    float var224 = var223;
                    double var669 = 0.05;
                    int var227 = Double.compare(var215, var669);
                    if (var227 < 0) {
                        var477 = var1035;
                    } else {
                        double var455 = var203.x;
                        double var800 = -1.0;
                        double var457 = (var455 * var800);
                        Vec3 var802 = var203;
                        double var803 = var802.z;
                        double var459 = Math.atan2(var457, var803);
                        double var805 = 180.0;
                        double var461 = (var459 * var805);
                        double var807 = 3.141592653589793;
                        double var463 = (var461 / var807);
                        var477 = ((float) var463);
                    }
                    var35 = var477;
                    double var229 = var203.y;
                    double var671 = var215;
                    double var872 = 1.0E-4;
                    double var673 = Math.max(var671, var872);
                    double var231 = Math.atan2(var229, var673);
                    double var675 = -1.0;
                    double var233 = (var231 * var675);
                    double var677 = 180.0;
                    double var235 = (var233 * var677);
                    double var679 = 3.141592653589793;
                    double var237 = (var235 / var679);
                    float var239 = ((float) var237);
                    float var241 = (var224 * -1.0F);
                    float var681 = var239;
                    float var523 = Math.min(var224, var681);
                    float var243 = Math.max(var241, var523);
                    var1037 = var243;
                    float var244 = var1037;
                    boolean var245 = Float.isFinite(var244);
                    if (!var245) {
                        return;
                    }
                    String var524 = "Horizontal Speed";
                    int var682 = 15;
                    Integer var683 = var682;
                    Object var247 = this.getSetting(var524, var683);
                    Integer var248 = ((Integer) var247);
                    int var249 = var248;
                    String var525 = "Vertical Speed";
                    int var684 = 15;
                    Integer var685 = var684;
                    Object var251 = this.getSetting(var525, var685);
                    Integer var252 = ((Integer) var251);
                    int var253 = var252;
                    String var526 = "Aim Horizontally";
                    int var686 = 1;
                    Boolean var687 = var686;
                    Object var255 = this.getSetting(var526, var687);
                    Boolean var256 = ((Boolean) var255);
                    boolean var257 = var256;
                    String var527 = "Aim Vertically";
                    int var688 = 1;
                    Boolean var689 = var688;
                    Object var259 = this.getSetting(var527, var689);
                    Boolean var260 = ((Boolean) var259);
                    boolean var261 = var260;
                    if (var142) {
                        String var556 = "Jitter";
                        int var703 = 30;
                        Integer var704 = var703;
                        Object var305 = this.getSetting(var556, var704);
                        Integer var306 = ((Integer) var305);
                        int var307 = var306;
                        float var309 = ((float) var307);
                        float var557 = 100.0F;
                        float var310 = (var309 / var557);
                        float var558 = 0.8F;
                        float var311 = (var310 * var558);
                        float var312 = var35;
                        KillAura var559 = this;
                        double var705 = var158;
                        int var879 = 0;
                        double var560 = var559.computeDoubleMetric(var705, var879);
                        float var827 = var311;
                        double var828 = ((double) var827);
                        double var562 = (var560 * var828);
                        float var564 = ((float) var562);
                        float var313 = (var312 + var564);
                        var35 = var313;
                        float var314 = var1037;
                        KillAura var565 = this;
                        double var707 = var158;
                        int var880 = 1;
                        double var566 = var565.computeDoubleMetric(var707, var880);
                        float var830 = var311;
                        double var831 = ((double) var830);
                        double var568 = (var566 * var831);
                        double var833 = 0.6000000238418579;
                        double var570 = (var568 * var833);
                        float var572 = ((float) var570);
                        float var315 = (var314 + var572);
                        var1037 = var315;
                        String var573 = "Speed Variation";
                        int var709 = 30;
                        Integer var710 = var709;
                        Object var317 = this.getSetting(var573, var710);
                        Integer var318 = ((Integer) var317);
                        int var319 = var318;
                        KillAura var711 = this;
                        long var712 = var711.timestampMs6;
                        int var322 = Long.compare(var73, var712);
                        if (var322 >= 0) {
                            long var574 = var73;
                            long var835 = 250L;
                            long var576 = (var574 + var835);
                            KillAura var837 = this;
                            Random var838 = var837.fieldVal;
                            float var839 = var838.nextFloat();
                            float var881 = 700.0F;
                            float var840 = (var839 * var881);
                            long var841 = ((long) var840);
                            long var578 = (var576 + var841);
                            this.timestampMs6 = var578;
                            double var580 = 1.0;
                            KillAura var843 = this;
                            Random var844 = var843.fieldVal;
                            double var845 = var844.nextDouble();
                            double var944 = 2.0;
                            double var847 = (var845 * var944);
                            double var946 = 1.0;
                            double var849 = (var847 - var946);
                            int var948 = var319;
                            double var949 = ((double) var948);
                            double var995 = 100.0;
                            double var951 = (var949 / var995);
                            double var851 = (var849 * var951);
                            double var953 = 0.5;
                            double var853 = (var851 * var953);
                            double var582 = (var580 + var853);
                            this.doubleValue1 = var582;
                        }
                        double var584 = this.doubleValue;
                        KillAura var855 = this;
                        double var856 = var855.doubleValue1;
                        KillAura var955 = this;
                        double var956 = var955.doubleValue;
                        double var858 = (var856 - var956);
                        double var958 = 1.0;
                        double var997 = var152;
                        double var1020 = 3.0;
                        double var999 = (var997 * var1020);
                        double var960 = Math.min(var958, var999);
                        double var860 = (var858 * var960);
                        double var586 = (var584 + var860);
                        this.doubleValue = var586;
                        float var326 = var35;
                        float var588 = var1035;
                        float var327 = (var326 - var588);
                        float var328 = computeFloatMetric(var327);
                        float var329 = var1037;
                        float var589 = var1036;
                        float var330 = (var329 - var589);
                        double var332 = ((double) var328);
                        float var714 = var328;
                        double var715 = ((double) var714);
                        double var334 = (var332 * var715);
                        float var717 = var330;
                        double var718 = ((double) var717);
                        float var882 = var330;
                        double var883 = ((double) var882);
                        double var720 = (var718 * var883);
                        double var336 = (var334 + var720);
                        double var338 = Math.sqrt(var336);
                        String var590 = "Overshoot";
                        int var722 = 25;
                        Integer var723 = var722;
                        Object var341 = this.getSetting(var590, var723);
                        Integer var342 = ((Integer) var341);
                        int var343 = var342;
                        int var724 = var343;
                        double var725 = ((double) var724);
                        double var885 = 100.0;
                        double var727 = (var725 / var885);
                        double var887 = 0.45;
                        double var729 = (var727 * var887);
                        double var346 = (1.0 - var729);
                        var50 = var346;
                        double var731 = 1.5;
                        int var350 = Double.compare(var338, var731);
                        if (var350 < 0) {
                            var50 = 1.0;
                        } else {
                            double var733 = 35.0;
                            int var353 = Double.compare(var338, var733);
                            if (var353 > 0) {
                                double var735 = var50;
                                double var889 = 0.08;
                                double var737 = (var735 - var889);
                                double var356 = Math.max(0.5, var737);
                                var50 = var356;
                            }
                            String var591 = "Max Turn Speed";
                            int var739 = 800;
                            Integer var740 = var739;
                            Object var359 = this.getSetting(var591, var740);
                            Integer var360 = ((Integer) var359);
                            int var361 = var360;
                            double var363 = ((double) var361);
                            double var741 = 0.85;
                            double var891 = 0.3;
                            KillAura var971 = this;
                            double var1001 = var158;
                            double var1022 = 0.35;
                            double var1003 = (var1001 * var1022);
                            double var972 = var971.computeDoubleMetric(var1003, 2);
                            double var974 = Math.abs(var972);
                            double var893 = (var891 * var974);
                            double var743 = (var741 + var893);
                            double var365 = (var363 * var743);
                            double var368 = ((double) var249);
                            double var745 = 1.1;
                            double var370 = (var368 * var745);
                            KillAura var747 = this;
                            double var748 = var747.doubleValue;
                            double var372 = (var370 * var748);
                            double var375 = ((double) var253);
                            double var750 = 1.1;
                            double var377 = (var375 * var750);
                            KillAura var752 = this;
                            double var753 = var752.doubleValue;
                            double var379 = (var377 * var753);
                            double var592 = var152;
                            double var862 = 0.008;
                            double var594 = (var592 / var862);
                            double var596 = Math.ceil(var594);
                            int var598 = ((int) var596);
                            int var382 = Math.max(1, var598);
                            int var755 = var382;
                            double var756 = ((double) var755);
                            double var385 = (var152 / var756);
                            double var388 = ((double) var328);
                            var62 = var388;
                            double var391 = ((double) var330);
                            var64 = var391;
                            float var394 = this.floatValue;
                            double var395 = ((double) var394);
                            var66 = var395;
                            float var398 = this.floatValue1;
                            double var399 = ((double) var398);
                            var68 = var399;
                            double var758 = -1.0;
                            double var403 = (var365 * var758);
                            var72 = 0;
                            while (true) {
                                int var406 = var72;
                                int var599 = var382;
                                if (var406 >= var599) break;
                                if (var257) {
                                    double var440 = var66;
                                    double var782 = var372;
                                    double var913 = var372;
                                    double var784 = (var782 * var913);
                                    double var915 = var62;
                                    double var786 = (var784 * var915);
                                    double var917 = 2.0;
                                    double var982 = var50;
                                    double var919 = (var917 * var982);
                                    double var984 = var372;
                                    double var921 = (var919 * var984);
                                    double var986 = var66;
                                    double var923 = (var921 * var986);
                                    double var788 = (var786 - var923);
                                    double var925 = var385;
                                    double var790 = (var788 * var925);
                                    double var442 = (var440 + var790);
                                    double var792 = var365;
                                    double var927 = var442;
                                    double var794 = Math.min(var792, var927);
                                    double var446 = Math.max(var403, var794);
                                    var66 = var446;
                                    double var448 = var62;
                                    double var796 = var66;
                                    double var929 = var385;
                                    double var798 = (var796 * var929);
                                    double var450 = (var448 - var798);
                                    var62 = var450;
                                } else {
                                    var66 = 0.0;
                                }
                                if (var261) {
                                    double var426 = var68;
                                    double var764 = var379;
                                    double var895 = var379;
                                    double var766 = (var764 * var895);
                                    double var897 = var64;
                                    double var768 = (var766 * var897);
                                    double var899 = 2.0;
                                    double var976 = var50;
                                    double var901 = (var899 * var976);
                                    double var978 = var379;
                                    double var903 = (var901 * var978);
                                    double var980 = var68;
                                    double var905 = (var903 * var980);
                                    double var770 = (var768 - var905);
                                    double var907 = var385;
                                    double var772 = (var770 * var907);
                                    double var428 = (var426 + var772);
                                    double var774 = var365;
                                    double var909 = var428;
                                    double var776 = Math.min(var774, var909);
                                    double var432 = Math.max(var403, var776);
                                    var68 = var432;
                                    double var434 = var64;
                                    double var778 = var68;
                                    double var911 = var385;
                                    double var780 = (var778 * var911);
                                    double var436 = (var434 - var780);
                                    var64 = var436;
                                } else {
                                    var68 = 0.0;
                                }
                                var72 = (var72 + 1);
                            }
                            double var600 = var66;
                            float var602 = ((float) var600);
                            this.floatValue = var602;
                            double var603 = var68;
                            float var605 = ((float) var603);
                            this.floatValue1 = var605;
                            if (var257) {
                                float var410 = var35;
                                double var411 = ((double) var410);
                                double var760 = var62;
                                double var413 = (var411 - var760);
                                var415 = ((float) var413);
                            } else {
                                var415 = var1035;
                            }
                            var1038 = var415;
                            if (var261) {
                                float var417 = var1037;
                                double var418 = ((double) var417);
                                double var762 = var64;
                                double var420 = (var418 - var762);
                                var478 = ((float) var420);
                            } else {
                                var478 = var1036;
                            }
                            var1040 = var478;
                        }
                    } else {
                        float var263 = var35;
                        float var528 = var1035;
                        float var264 = (var263 - var528);
                        float var265 = computeFloatMetric(var264);
                        float var266 = var1037;
                        float var529 = var1036;
                        float var267 = (var266 - var529);
                        float var268 = computeFloatMetric(var267);
                        int var530 = var249;
                        float var531 = ((float) var530);
                        float var690 = 20.0F;
                        float var532 = (var531 / var690);
                        float var270 = (var265 * var532);
                        long var533 = var17;
                        float var535 = ((float) var533);
                        float var691 = 50.0F;
                        float var536 = (var535 / var691);
                        float var271 = (var270 * var536);
                        float var537 = var265;
                        float var538 = Math.abs(var537);
                        float var272 = computeFloatMetric(var271, var538);
                        int var539 = var253;
                        float var540 = ((float) var539);
                        float var692 = 20.0F;
                        float var541 = (var540 / var692);
                        float var274 = (var268 * var541);
                        long var542 = var17;
                        float var544 = ((float) var542);
                        float var693 = 50.0F;
                        float var545 = (var544 / var693);
                        float var275 = (var274 * var545);
                        float var546 = var268;
                        float var547 = Math.abs(var546);
                        float var276 = computeFloatMetric(var275, var547);
                        if (var257) {
                            float var278 = var1035;
                            float var548 = var272;
                            var303 = (var278 + var548);
                        } else {
                            var303 = var1035;
                        }
                        var1038 = var303;
                        if (var261) {
                            float var280 = var1036;
                            float var549 = var276;
                            var479 = (var280 + var549);
                        } else {
                            var479 = var1036;
                        }
                        var1040 = var479;
                    }
                    float var550 = 90.0F;
                    float var694 = var1040;
                    float var551 = Math.min(var550, var694);
                    float var282 = Math.max(-90.0F, var551);
                    float var283 = var1038;
                    boolean var284 = Float.isFinite(var283);
                    if (var284) {
                        boolean var286 = Float.isFinite(var282);
                        if (!var286) {
                        }
                    }
                    return;
                    float var287 = var1038;
                    float var552 = var282;
                    AimAssist.applyStep(var287, var552);
                    int var553 = 1;
                    this.enabledState = var553;
                    boolean var289 = Diag.enabled();
                    if (var289) {
                        long var290 = System.currentTimeMillis();
                        long var695 = timestampMs1;
                        long var294 = (var290 - var695);
                        long var697 = 1000L;
                        int var296 = Long.compare(var294, var697);
                        if (var296 >= 0) {
                            timestampMs1 = var290;
                            Component var300 = var118.getName();
                            String var301 = var300.getString();
                            String var554 = "%.1f";
                            int var699 = 1;
                            Object[] var700 = new Object[var699];
                            int var874 = 0;
                            float var937 = var1035;
                            Float var938 = var937;
                            var700[var874] = var938;
                            String var555 = String.format(var554, var700);
                            String var701 = "%.1f";
                            int var823 = 1;
                            Object[] var824 = new Object[var823];
                            int var939 = 0;
                            float var964 = var1036;
                            Float var965 = var964;
                            var824[var939] = var965;
                            String var702 = String.format(var701, var824);
                            String var825 = "%.1f";
                            int var875 = 1;
                            Object[] var876 = new Object[var875];
                            int var966 = 0;
                            float var10 = var35;
                            Float var990 = var10;
                            var876[var966] = var990;
                            String var826 = String.format(var825, var876);
                            String var877 = "%.1f";
                            int var940 = 1;
                            Object[] var941 = new Object[var940];
                            int var991 = 0;
                            float var11 = var1037;
                            Float var1006 = var11;
                            var941[var991] = var1006;
                            String var878 = String.format(var877, var941);
                            String var942 = "%.1f";
                            int var967 = 1;
                            Object[] var968 = new Object[var967];
                            int var1007 = 0;
                            float var12 = var1038;
                            Float var1017 = var12;
                            var968[var1007] = var1017;
                            String var943 = String.format(var942, var968);
                            String var969 = "%.1f";
                            int var992 = 1;
                            Object[] var993 = new Object[var992];
                            int var1018 = 0;
                            Float var1027 = var282;
                            var993[var1018] = var1027;
                            String var970 = String.format(var969, var993);
                            int var994 = var142;
                            int var1008 = var257;
                            int var1019 = var261;
                            boolean var1028 = AimAssist.isActivelyTracking();
                            String var302 = "killaim set t=" + (var301) + " cur=" + (var555) + "," + (var702) + " tgt=" + (var826) + "," + (var878) + " new=" + (var943) + "," + (var970) + " human=" + (var994) + " aimH=" + (var1008) + " aimV=" + (var1019) + " silent=" + (var1028);
                            Diag.log(var302);
                        }
                        return;
                    }
                }
            }
        }
    }

    private long evaluate(String arg0, int arg1, int arg2) {
        String var28 = (arg0) + "_lo";
        Integer var33 = arg1;
        int var13 = ((Integer) this.getSetting(var28, var33));
        String var30 = (arg0) + "_hi";
        Integer var35 = arg2;
        int var17 = ((Integer) this.getSetting(var30, var35));
        if (var17 <= var13) {
            return ((long) Math.max(0, var13));
        }
        long var24 = ((long) var13);
        float var38 = this.fieldVal.nextFloat();
        float var43 = ((float) (var17 - var13));
        long var40 = ((long) (var38 * var43));
        return (var24 + var40);
    }

    private double computeDoubleMetric(double arg0, int arg1) {
        int var15 = (arg1 * 3);
        int var28 = (this.coordsVector3.length - 2);
        int var16 = (var15 % var28);
        double var31 = (1.7 * arg0);
        double var47 = this.coordsVector3[var16];
        double var35 = Math.sin((var31 + var47));
        double var19 = (0.5 * var35);
        double var51 = (3.1 * arg0);
        double[] var66 = this.coordsVector3;
        int var75 = (var16 + 1);
        double var67 = var66[var75];
        double var55 = Math.sin((var51 + var67));
        double var39 = (0.3 * var55);
        double var21 = (var19 + var39);
        double var59 = (7.3 * arg0);
        double[] var72 = this.coordsVector3;
        int var77 = (var16 + 2);
        double var73 = var72[var77];
        double var63 = Math.sin((var59 + var73));
        double var43 = (0.2 * var63);
        return (var21 + var43);
    }

    private static double computeDoubleMetric(double arg0, double arg1, double arg2) {
        double var14 = Math.min(arg2, arg0);
        return Math.max(arg1, var14);
    }

    private void applyStep(String arg0) {
        double var20 = 0.0;
        double var22 = 0.0;
        double var24 = 0.0;
        int var127 = 0;
        Integer var155 = 40;
        int var33 = ((Integer) this.getSetting("Randomization", var155));
        Integer var157 = 15;
        double var161 = (((double) ((Integer) this.getSetting("Hitbox Margin", var157))) / 100.0);
        double var40 = Math.max(0.03, var161);
        double var165 = (var40 * 0.4);
        double var44 = Math.max(0.04, var165);
        double var257 = (((double) var33) / 100.0);
        double var169 = (0.14 * var257);
        double var48 = (0.1 + var169);
        var22 = 0.5;
        var24 = 0.5;
        if ("Center".equals(arg0)) {
            var20 = 0.5;
        } else {
            double var187 = (this.fieldVal.nextGaussian() * var48);
            var22 = (0.5 + var187);
            double var193 = (this.fieldVal.nextGaussian() * var48);
            var24 = (0.5 + var193);
            if ("Head".equals(arg0)) {
                double var199 = (this.fieldVal.nextDouble() * 0.17);
                var20 = (0.8 + var199);
            } else {
                if ("Chest".equals(arg0)) {
                    double var205 = (this.fieldVal.nextDouble() * 0.25);
                    var20 = (0.55 + var205);
                } else {
                    if ("Legs".equals(arg0)) {
                        double var211 = (this.fieldVal.nextDouble() * 0.37);
                        var20 = (0.08 + var211);
                    } else {
                        if ("Random".equals(arg0)) {
                            double var217 = (this.fieldVal.nextDouble() * 0.87);
                            var20 = (0.08 + var217);
                        } else {
                            Integer var247 = 30;
                            int var140 = ((Integer) this.getSetting("Head Weight", var247));
                            int var97 = Math.max(0, var140);
                            Integer var249 = 50;
                            int var144 = ((Integer) this.getSetting("Body Weight", var249));
                            int var99 = Math.max(0, var144);
                            Integer var251 = 20;
                            int var148 = ((Integer) this.getSetting("Legs Weight", var251));
                            int var101 = Math.max(0, var148);
                            int var104 = ((var97 + var99) + var101);
                            if (var104 <= 0) {
                                var127 = 0;
                            } else {
                                var127 = this.fieldVal.nextInt(var104);
                            }
                            int var30 = var127;
                            if (var104 <= 0) {
                                double var226 = (this.fieldVal.nextDouble() * 0.25);
                                var20 = (0.55 + var226);
                            } else {
                                if (var30 < var97) {
                                    double var232 = (this.fieldVal.nextDouble() * 0.17);
                                    var20 = (0.8 + var232);
                                } else {
                                    int var154 = (var97 + var99);
                                    if (var30 < var154) {
                                        double var245 = (this.fieldVal.nextDouble() * 0.3);
                                        var20 = (0.5 + var245);
                                    } else {
                                        double var239 = (this.fieldVal.nextDouble() * 0.4);
                                        var20 = (0.08 + var239);
                                    }
                                    double[] var59 = this.coordsVector;
                                    double var171 = var22;
                                    double var287 = (1.0 - var40);
                                    double var173 = computeDoubleMetric(var171, var40, var287);
                                    var59[0] = var173;
                                    double[] var61 = this.coordsVector;
                                    double var175 = var20;
                                    double var291 = (1.0 - var44);
                                    double var177 = computeDoubleMetric(var175, var44, var291);
                                    var61[1] = var177;
                                    double[] var63 = this.coordsVector;
                                    double var179 = var24;
                                    double var295 = (1.0 - var40);
                                    double var181 = computeDoubleMetric(var179, var40, var295);
                                    var63[2] = var181;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Vec3 evaluate(LivingEntity arg0, Vec3 arg1, String arg2, long arg3, double arg4, double arg5) {
        int var29 = 0;
        int var30 = 0;
        AABB var44 = arg0.getBoundingBox();
        long var162 = this.timestampMs4;
        if (Long.compare(arg3, var162) >= 0) {
            this.applyStep(arg2);
            long var252 = this.evaluate("Point Change", 500, 1400);
            long var142 = (arg3 + var252);
            this.timestampMs4 = var142;
        }
        Integer var165 = 40;
        double var171 = ((((double) ((Integer) this.getSetting("Point Drift", var165))) / 100.0) * 9.0);
        double var56 = (1.5 + var171);
        int var151 = ((int) Math.ceil((arg4 / 0.008)));
        int var59 = Math.max(1, var151);
        double var174 = ((double) var59);
        double var62 = (arg4 / var174);
        var29 = 0;
        while (true) {
            if (var29 >= 3) break;
            var30 = 0;
            while (true) {
                if (var30 >= var59) break;
                double[] var69 = this.coordsVector2;
                int var154 = var29;
                double var176 = var69[var154];
                double var274 = (var56 * var56);
                double[] var345 = this.coordsVector;
                int var17 = var29;
                double var346 = var345[var17];
                double[] var389 = this.coordsVector1;
                int var19 = var29;
                double var390 = var389[var19];
                double var348 = (var346 - var390);
                double var276 = (var274 * var348);
                double var352 = (2.0 * var56);
                double[] var395 = this.coordsVector2;
                int var418 = var29;
                double var396 = var395[var418];
                double var354 = (var352 * var396);
                double var280 = ((var276 - var354) * var62);
                double var178 = (var176 + var280);
                var69[var154] = var178;
                double[] var71 = this.coordsVector1;
                int var155 = var29;
                double var180 = var71[var155];
                double[] var283 = this.coordsVector2;
                int var332 = var29;
                double var286 = (var283[var332] * var62);
                double var182 = (var180 + var286);
                var71[var155] = var182;
                var30 = (var30 + 1);
            }
            var29 = (var29 + 1);
        }
        Integer var185 = 40;
        double var189 = (((double) ((Integer) this.getSetting("Randomization", var185))) / 100.0);
        double var78 = (0.035 * var189);
        double var82 = this.coordsVector1[0];
        double var258 = (arg5 * 0.6);
        double var194 = (this.computeDoubleMetric(var258, 2) * var78);
        double var84 = (var82 + var194);
        double var88 = this.coordsVector1[1];
        double var262 = (arg5 * 0.6);
        double var199 = (this.computeDoubleMetric(var262, 3) * var78);
        double var90 = (var88 + var199);
        double var94 = this.coordsVector1[2];
        double var266 = (arg5 * 0.6);
        double var204 = (this.computeDoubleMetric(var266, 4) * var78);
        double var96 = (var94 + var204);
        double var100 = computeDoubleMetric(var84, 0.02, 0.98);
        double var104 = computeDoubleMetric(var90, 0.02, 0.98);
        double var108 = computeDoubleMetric(var96, 0.02, 0.98);
        if ("Closest".equals(arg2)) {
            Vec3 var113 = this.evaluate(arg0, arg1);
            double var116 = (var100 - 0.5);
            double var216 = var44.maxX;
            double var303 = var44.minX;
            double var218 = (var216 - var303);
            double var120 = ((var116 * var218) * 0.5);
            double var124 = (var104 - 0.5);
            double var225 = var44.maxY;
            double var306 = var44.minY;
            double var227 = (var225 - var306);
            double var128 = ((var124 * var227) * 0.25);
            double var132 = (var108 - 0.5);
            double var234 = var44.maxZ;
            double var309 = var44.minZ;
            double var236 = (var234 - var309);
            double var136 = ((var132 * var236) * 0.5);
            double var243 = (var113.x + var120);
            double var314 = var44.minX;
            double var361 = var44.maxX;
            double var245 = computeDoubleMetric(var243, var314, var361);
            double var319 = (var113.y + var128);
            double var366 = var44.minY;
            double var399 = var44.maxY;
            double var321 = computeDoubleMetric(var319, var366, var399);
            double var371 = (var113.z + var136);
            double var404 = var44.minZ;
            double var419 = var44.maxZ;
            double var373 = computeDoubleMetric(var371, var404, var419);
            Vec3 var138 = new Vec3(var245, var321, var373);
            return var138;
        }
        double var248 = var44.minX;
        double var376 = var44.maxX;
        double var407 = var44.minX;
        double var378 = (var376 - var407);
        double var325 = (var100 * var378);
        double var250 = (var248 + var325);
        double var328 = var44.minY;
        double var410 = var44.maxY;
        double var422 = var44.minY;
        double var412 = (var410 - var422);
        double var382 = (var104 * var412);
        double var330 = (var328 + var382);
        double var385 = var44.minZ;
        double var425 = var44.maxZ;
        double var429 = var44.minZ;
        double var427 = (var425 - var429);
        double var416 = (var108 * var427);
        double var387 = (var385 + var416);
        Vec3 var139 = new Vec3(var250, var330, var387);
        return var139;
    }

    private void applyStep(Minecraft arg0, long arg1) {
        float var18 = arg0.player.getYRot();
        float var21 = arg0.player.getXRot();
        float var22 = AimAssist.computeFloatMetric2();
        float var23 = AimAssist.computeFloatMetric3();
        float var26 = computeFloatMetric((var18 - var22));
        float var29 = computeFloatMetric((var21 - var23));
        if (Float.compare(Math.abs(var26), 0.5F) < 0) {
            if (Float.compare(Math.abs(var29), 0.5F) < 0) {
                this.enabledState = false;
                this.floatValue = 0.0F;
                this.floatValue1 = 0.0F;
                AimAssist.clearSilentRotation();
                return;
            }
        }
        Integer var86 = 15;
        int var42 = ((Integer) this.getSetting("Horizontal Speed", var86));
        Integer var88 = 15;
        int var46 = ((Integer) this.getSetting("Vertical Speed", var88));
        float var67 = (((float) var42) / 20.0F);
        float var48 = (var26 * var67);
        float var71 = (((float) arg1) / 50.0F);
        float var49 = (var48 * var71);
        float var73 = Math.abs(var26);
        float var50 = computeFloatMetric(var49, var73);
        float var76 = (((float) var46) / 20.0F);
        float var52 = (var29 * var76);
        float var80 = (((float) arg1) / 50.0F);
        float var53 = (var52 * var80);
        float var82 = Math.abs(var29);
        float var54 = computeFloatMetric(var53, var82);
        float var56 = (var22 + var50);
        float var85 = (var23 + var54);
        AimAssist.applyStep(var56, var85);
        AimAssist.clearSilentRotation();
    }

    public void onTick() {
        long var92 = 0L;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level != null) {
                if (var1.gameMode == null) {
                }
            }
            this.applyStep1();
            return;
            if (!this.isEligible(var1)) {
                this.applyStep1();
                return;
            }
            LivingEntity var27 = this.targetEntity1;
            if (var27 != null) {
                if (var27.isAlive()) {
                    if (var27.isRemoved()) {
                    }
                }
                this.applyStep1();
                return;
                Boolean var120 = true;
                if (((Boolean) this.getSetting("Check Shielding", var120))) {
                    if (var27.isBlocking()) {
                        this.applyStep1();
                        return;
                    }
                }
                Float var122 = 0.9F;
                float var43 = ((Float) this.getSetting("Cooldown Threshold_lo", var122));
                Float var124 = 1.0F;
                float var47 = ((Float) this.getSetting("Cooldown Threshold_hi", var124));
                float var101 = this.fieldVal.nextFloat();
                float var142 = (var47 - var43);
                float var126 = Math.max(0.0F, var142);
                float var102 = (var101 * var126);
                float var49 = (var43 + var102);
                if (Float.compare(var1.player.getAttackStrengthScale(0.0F), var49) < 0) {
                    this.applyStep1();
                    return;
                }
                Boolean var128 = true;
                if (((Boolean) this.getSetting("Prioritize Criticals", var128))) {
                    if (!this.isEligible1(var1)) {
                        this.applyStep1();
                        return;
                    }
                }
                if (!(this.isEligible(var1, var27))) {
                    this.applyStep1();
                    return;
                }
                Integer var131 = 0;
                int var68 = ((Integer) this.getSetting("Extra Delay_lo", var131));
                Integer var133 = 0;
                int var72 = ((Integer) this.getSetting("Extra Delay_hi", var133));
                long var73 = System.currentTimeMillis();
                if (!this.enabledState1) {
                    if (var72 > var68) {
                        float var88 = this.fieldVal.nextFloat();
                        float var119 = ((float) (var72 - var68));
                        long var90 = ((long) (var88 * var119));
                        long var140 = ((long) var68);
                        var92 = (var90 + var140);
                    } else {
                        var92 = ((long) var68);
                    }
                    long var15 = var92;
                    long var113 = (var73 + var15);
                    this.timestampMs7 = var113;
                    this.enabledState1 = true;
                }
                long var135 = this.timestampMs7;
                if (Long.compare(var73, var135) < 0) {
                    return;
                }
                this.applyStep(var1, var27);
                this.applyStep1();
                return;
            }
        }
    }

    private boolean isEligible(Minecraft arg0, LivingEntity arg1) {
        Vec3 var104 = null;
        float var106 = 0.0F;
        float var107 = 0.0F;
        int var62 = 0;
        Float var81 = 3.0F;
        double var85 = (((double) ((Float) this.getSetting("Attack Range", var81))) - 0.05);
        double var25 = Math.max(1.0, var85);
        Vec3 var29 = arg0.player.getEyePosition();
        AABB var31 = arg1.getBoundingBox();
        Boolean var88 = true;
        if (((Boolean) this.getSetting("Only When Aimed", var88))) {
            if (!this.enabledState) {
                return false;
            }
            int var39 = LocalPlayerRotationState.tickServerTick;
            int var72 = arg0.player.tickCount;
            if (var39 == var72) {
                var106 = LocalPlayerRotationState.tickServerYaw;
                var107 = LocalPlayerRotationState.tickServerPitch;
            } else {
                var106 = AimAssist.computeFloatMetric2();
                var107 = AimAssist.computeFloatMetric3();
            }
            float var42 = var107;
            float var73 = var106;
            Vec3 var43 = AimAssist.evaluate(var42, var73);
            double var91 = (var25 + 1.0);
            Vec3 var75 = var43.scale(var91);
            Vec3 var45 = var29.add(var75);
            Optional var47 = var31.clip(var29, var45);
            if (var47.isEmpty()) {
                return false;
            }
            var104 = ((Vec3) var47.get());
        } else {
            var104 = evaluate(var31, var29);
        }
        Vec3 var77 = var104;
        double var54 = var29.distanceToSqr(var77);
        double var96 = (var25 * var25);
        if (Double.compare(var54, var96) > 0) {
            return false;
        }
        ClientLevel var58 = arg0.level;
        Vec3 var102 = var104;
        ClipContext.Block var9 = ClipContext.Block.OUTLINE;
        ClipContext.Fluid var10 = ClipContext.Fluid.NONE;
        LocalPlayer var103 = arg0.player;
        ClipContext var78 = new ClipContext(var29, var102, var9, var10, var103);
        HitResult.Type var61 = var58.clip(var78).getType();
        HitResult.Type var79 = HitResult.Type.MISS;
        if (var61 == var79) {
            var62 = 1;
        } else {
            var62 = 0;
        }
        return var62;
    }

    private void applyStep(Minecraft arg0, LivingEntity arg1) {
        LocalPlayer var13 = arg0.player;
        EventStartAttack var3 = new EventStartAttack(var13, arg1);
        PerAttackEvents.fire(var3);
        MultiPlayerGameMode var7 = arg0.gameMode;
        LocalPlayer var11 = arg0.player;
        var7.attack(var11, arg1);
        LocalPlayer var9 = arg0.player;
        InteractionHand var12 = InteractionHand.MAIN_HAND;
        var9.swing(var12);
    }

    private boolean isEligible(Minecraft arg0) {
        if (arg0.player != null) {
            if (arg0.level == null) {
            }
        }
        return false;
        Boolean var36 = true;
        if (((Boolean) this.getSetting("Not in GUI", var36))) {
            if (arg0.screen != null) {
                return false;
            }
        }
        Boolean var38 = true;
        if (((Boolean) this.getSetting("Not While Using", var38))) {
            if (arg0.player.isUsingItem()) {
                return false;
            }
        }
        Boolean var40 = false;
        if (((Boolean) this.getSetting("Only Weapon", var40))) {
            if (!isEligible(arg0.player.getMainHandItem())) {
                return false;
            }
            return true;
        }
    }

    private LivingEntity evaluate(Minecraft arg0) {
        LivingEntity var52 = null;
        Integer var61 = 6;
        int var21 = ((Integer) this.getSetting("Range", var61));
        double var23 = ((double) var21);
        double var63 = ((double) var21);
        double var25 = (var23 * var63);
        Integer var66 = 360;
        int var30 = ((Integer) this.getSetting("FOV", var66));
        float var33 = arg0.player.getYRot();
        float var36 = arg0.player.getXRot();
        Vec3 var39 = arg0.player.getEyePosition();
        Stream var44 = StreamSupport.stream(arg0.level.entitiesForRendering().spliterator(), false);
        Predicate var56 = (p0) -> this.lambda$a(arg0, var25, var30, var39, var33, var36, p0);
        Stream var45 = var44.filter(var56);
        Function var57 = (p0) -> KillAura.lambda$a(p0);
        Stream var46 = var45.map(var57);
        Collector var58 = Collectors.toList();
        List var48 = ((List) var46.collect(var58));
        if (var48.isEmpty()) {
            var52 = null;
        } else {
            LocalPlayer var60 = arg0.player;
            var52 = Targets.evaluate(var48, var60);
        }
        return var52;
    }

    private boolean isEligible(LivingEntity arg0, Vec3 arg1, float arg2, float arg3, int arg4) {
        int var69 = 0;
        Vec3 var21 = this.evaluate(arg0, arg1).subtract(arg1);
        double var23 = var21.x;
        double var77 = var21.x;
        double var25 = (var23 * var77);
        double var80 = var21.z;
        double var107 = var21.z;
        double var82 = (var80 * var107);
        double var29 = Math.sqrt((var25 + var82));
        double var34 = (var21.x * -1.0);
        double var87 = var21.z;
        float var42 = ((float) ((Math.atan2(var34, var87) * 180.0) / 3.141592653589793));
        float var54 = ((float) (((Math.atan2(var21.y, var29) * -1.0) * 180.0) / 3.141592653589793));
        float var57 = computeFloatMetric((var42 - arg2));
        float var60 = computeFloatMetric((var54 - arg3));
        float var62 = (var57 * var57);
        float var75 = (var60 * var60);
        double var66 = Math.sqrt(((double) (var62 + var75)));
        double var105 = ((double) (((float) arg4) / 2.0F));
        if (Double.compare(var66, var105) <= 0) {
            var69 = 1;
        } else {
            var69 = 0;
        }
        return var69;
    }

    private Vec3 evaluate(LivingEntity arg0, Vec3 arg1) {
        AABB var22 = arg0.getBoundingBox();
        Object var56 = this.getSetting("Aim At", "Closest");
        if ("Center".equals(var56)) {
            double var113 = var22.minX;
            double var165 = var22.maxX;
            double var117 = ((var113 + var165) / 2.0);
            double var170 = var22.minY;
            double var209 = var22.maxY;
            double var174 = ((var170 + var209) / 2.0);
            double var214 = var22.minZ;
            double var244 = var22.maxZ;
            double var218 = ((var214 + var244) / 2.0);
            Vec3 var55 = new Vec3(var117, var174, var218);
            return var55;
        }
        double var26 = arg1.x;
        double var60 = (var22.minX - 0.25);
        if (Double.compare(var26, var60) > 0) {
            double var30 = arg1.x;
            double var65 = (var22.maxX + 0.25);
            if (Double.compare(var30, var65) < 0) {
                double var34 = arg1.z;
                double var70 = (var22.minZ - 0.25);
                if (Double.compare(var34, var70) > 0) {
                    double var38 = arg1.z;
                    double var75 = (var22.maxZ + 0.25);
                    if (Double.compare(var38, var75) < 0) {
                        double var78 = var22.minX;
                        double var126 = var22.maxX;
                        double var82 = ((var78 + var126) / 2.0);
                        double var131 = var22.minY;
                        double var176 = var22.maxY;
                        double var220 = var22.minY;
                        double var180 = ((var176 - var220) * 0.6);
                        double var133 = (var131 + var180);
                        double var183 = var22.minZ;
                        double var225 = var22.maxZ;
                        double var187 = ((var183 + var225) / 2.0);
                        Vec3 var41 = new Vec3(var82, var133, var187);
                        return var41;
                    }
                }
            }
        }
        double var85 = var22.maxX;
        double var136 = var22.minX;
        double var89 = ((var85 - var136) * 0.25);
        double var44 = Math.min(0.08, var89);
        double var92 = var22.maxY;
        double var141 = var22.minY;
        double var96 = ((var92 - var141) * 0.25);
        double var48 = Math.min(0.08, var96);
        double var99 = var22.maxZ;
        double var146 = var22.minZ;
        double var103 = ((var99 - var146) * 0.25);
        double var52 = Math.min(0.08, var103);
        double var108 = (var22.minX + var44);
        double var153 = arg1.x;
        double var192 = (var22.maxX - var44);
        double var155 = Math.min(var153, var192);
        double var110 = Math.max(var108, var155);
        double var160 = (var22.minY + var48);
        double var197 = arg1.y;
        double var234 = (var22.maxY - var48);
        double var199 = Math.min(var197, var234);
        double var162 = Math.max(var160, var199);
        double var204 = (var22.minZ + var52);
        double var239 = arg1.z;
        double var251 = (var22.maxZ - var52);
        double var241 = Math.min(var239, var251);
        double var206 = Math.max(var204, var241);
        Vec3 var54 = new Vec3(var110, var162, var206);
        return var54;
    }

    private static Vec3 evaluate(AABB arg0, Vec3 arg1) {
        double var8 = arg0.minX;
        double var12 = arg1.x;
        double var21 = arg0.maxX;
        double var14 = Math.min(var12, var21);
        double var10 = Math.max(var8, var14);
        double var17 = arg0.minY;
        double var24 = arg1.y;
        double var33 = arg0.maxY;
        double var26 = Math.min(var24, var33);
        double var19 = Math.max(var17, var26);
        double var29 = arg0.minZ;
        double var36 = arg1.z;
        double var40 = arg0.maxZ;
        double var38 = Math.min(var36, var40);
        double var31 = Math.max(var29, var38);
        Vec3 var2 = new Vec3(var10, var19, var31);
        return var2;
    }

    private boolean isEligible1(Minecraft arg0) {
        int var25 = 0;
        if (arg0.player.onGround()) {
            return true;
        }
        if (!arg0.player.isInWater()) {
            if (arg0.player.isInLava()) {
            }
        }
        return false;
        if (arg0.player.isPassenger()) {
            return false;
        }
        if (Double.compare(arg0.player.getDeltaMovement().y, -0.1) <= 0) {
            var25 = 1;
        } else {
            var25 = 0;
        }
        return var25;
    }

    private static boolean isEligible(ItemStack arg0) {
        int var14 = 0;
        if (arg0.isEmpty()) {
            return false;
        }
        if (!(arg0.getItem() instanceof TridentItem)) {
            TagKey var2 = ItemTags.SWORDS;
            if (!arg0.is(var2)) {
                TagKey var15 = ItemTags.AXES;
                if (!arg0.is(var15)) {
                    TagKey var16 = ItemTags.MACE_ENCHANTABLE;
                    if (arg0.is(var16)) {
                    }
                }
                var14 = 1;
                var14 = 0;
                return var14;
            }
        }
    }

    private void applyStep1() {
        this.enabledState1 = false;
        this.timestampMs7 = -1L;
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
        if (Float.compare(arg0, arg1) > 0) {
            return arg1;
        }
        float var8 = (arg1 * -1.0F);
        if (Float.compare(arg0, var8) < 0) {
            return var8;
        }
        return arg0;
    }

    static {
        timestampMs = 0L;
        timestampMs1 = 0L;
        timestampMs2 = 0L;
    }

}
