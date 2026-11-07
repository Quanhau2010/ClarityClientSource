package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import need.clarity.WorldRenderEvent;
import need.clarity.modules.ModuleBase;
import need.clarity.render.worldfx.WorldFx;
import need.clarity.util.ClarityRenderUtils;
import need.clarity.util.ThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

public class PlayerTrails extends ModuleBase {
    public static final String[] fieldVal;
    private static final int CONST_INT = 4;
    private static final float floatValue = 1.4F;
    private static final long timestampMs;
    private final Map entriesMap;
    private final WorldRenderEvent.Listener fieldVal1;
    private double[] coordsVector;
    private double[] coordsVector1;
    private double[] coordsVector2;
    private double[] coordsVector3;
    private float[] floatBuffer;
    private float[] floatBuffer1;
    private float[] floatBuffer2;
    private float[] floatBuffer3;

    public PlayerTrails() {
        super("Player Trails", "Visual");
        HashMap var18 = new HashMap();
        this.entriesMap = var18;
        WorldRenderEvent.Listener var20 = (p0, p1) -> this.renderWorldEffect(p0, p1);
        this.fieldVal1 = var20;
        double[] var22 = new double[64];
        this.coordsVector = var22;
        double[] var24 = new double[64];
        this.coordsVector1 = var24;
        double[] var26 = new double[64];
        this.coordsVector2 = var26;
        double[] var28 = new double[64];
        this.coordsVector3 = var28;
        float[] var30 = new float[256];
        this.floatBuffer = var30;
        float[] var32 = new float[256];
        this.floatBuffer1 = var32;
        float[] var34 = new float[256];
        this.floatBuffer2 = var34;
        float[] var36 = new float[256];
        this.floatBuffer3 = var36;
        if (this.isEnabled()) {
            WorldRenderEvent.register(this.fieldVal1);
        }
    }

    public void onEnable() {
        WorldRenderEvent.register(this.fieldVal1);
    }

    public void onDisable() {
        WorldRenderEvent.unregister(this.fieldVal1);
        this.entriesMap.clear();
    }

    private float getLength() {
        Integer var12 = 30;
        int var10 = ((Integer) this.getSetting("Length", var12));
        return (((float) Math.max(5, var10)) / 20.0F);
    }

    private double getHeight() {
        Float var9 = 0.1F;
        return ((double) ((Float) this.getSetting("Height", var9)));
    }

    private boolean isEligible(AbstractClientPlayer arg0, Minecraft arg1) {
        boolean var17 = false;
        if (arg0.isAlive()) {
            if (arg0.isSpectator()) {
            }
        }
        return false;
        LocalPlayer var18 = arg1.player;
        if (arg0 == var18) {
            Boolean var21 = true;
            var17 = ((Boolean) this.getSetting("Self", var21));
        } else {
            Boolean var23 = true;
            var17 = ((Boolean) this.getSetting("Others", var23));
        }
        return var17;
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.level != null) {
            if (var1.player == null) {
            }
        }
        return;
        long var37 = System.nanoTime();
        long var45 = ((long) (((double) this.getLength()) * 1.0E9));
        double var48 = this.getHeight();
        HashSet var50 = new HashSet();
        Iterator var54 = var1.level.players().iterator();
        while (true) {
            if (!var54.hasNext()) break;
            AbstractClientPlayer var63 = ((AbstractClientPlayer) var54.next());
            if (!(this.isEligible(var63, var1))) {
            }
            int var67 = var63.getId();
            Integer var119 = var67;
            boolean var69 = var50.add(var119);
            Map var71 = this.entriesMap;
            Integer var121 = var67;
            Function var125 = (p0) -> PlayerTrails.lambda$a(p0);
            ArrayDeque var73 = ((ArrayDeque) var71.computeIfAbsent(var121, var125));
            double var75 = var63.getX();
            double var80 = (var63.getY() + var48);
            double var83 = var63.getZ();
            PlayerTrailPoint var87 = ((PlayerTrailPoint) var73.peekFirst());
            if (var87 != null) {
                double var94 = computeDoubleMetric((var87.x - var75));
                double var135 = computeDoubleMetric((var87.y - var80));
                double var96 = (var94 + var135);
                double var142 = computeDoubleMetric((var87.z - var83));
                if (Double.compare((var96 + var142), 9.0E-4) > 0) {
                }
            }
            PlayerTrailPoint var122 = new PlayerTrailPoint(var75, var80, var83, var37);
            var73.addFirst(var122);
            while (true) {
                if (var73.isEmpty()) break;
                long var149 = ((PlayerTrailPoint) var73.peekLast()).t;
                if (Long.compare((var37 - var149), var45) <= 0) break;
                Object var114 = var73.removeLast();
            }
            while (true) {
                if (var73.size() <= 120) continue;
            }
        }
        Object var112 = var73.removeLast();
        Set var59 = this.entriesMap.keySet();
        Predicate var116 = (p0) -> PlayerTrails.lambda$a(var50, p0);
        boolean var60 = var59.removeIf(var116);
    }

    private void renderWorldEffect(PoseStack arg0, float arg1) {
        int var16 = 0;
        float var18 = 0.0F;
        int var467 = 0;
        int var228 = 0;
        boolean var49 = this.isEnabled();
        if (var49) {
            Map var51 = this.entriesMap;
            boolean var52 = var51.isEmpty();
            if (var52) {
            }
        }
        return;
        Minecraft var53 = Minecraft.getInstance();
        ClientLevel var55 = var53.level;
        if (var55 == null) {
            return;
        }
        Object var57 = this.getSetting("Style", "Energy");
        String var58 = ((String) var57);
        var16 = -1;
        int var62 = var58.hashCode();
        switch (var62) {
            case -1847389962 -> {}
            case -1604554070 -> {}
            case -1354722902 -> {}
            case 2577013 -> {}
            case 2696232 -> {}
            case 65290704 -> {}
            case 1972453248 -> {}
            default -> {}
        }
        boolean var64 = var58.equals("Lightning");
        if (var64) {
            var16 = 1;
            boolean var233 = var58.equals("Silk");
            if (var233) {
                var16 = 5;
                boolean var230 = var58.equals("Wind");
                if (var230) {
                    var16 = 3;
                    boolean var245 = var58.equals("Ribbon");
                    if (var245) {
                        var16 = 0;
                        boolean var236 = var58.equals("Aurora");
                        if (var236) {
                            var16 = 4;
                            boolean var239 = var58.equals("Comet");
                            if (var239) {
                                var16 = 2;
                                boolean var242 = var58.equals("Ethereal");
                                if (var242) {
                                    var16 = 6;
                                }
                                int var66 = var16;
                                switch (var66) {
                                    case 0 -> {}
                                    case 1 -> {}
                                    case 2 -> {}
                                    case 3 -> {}
                                    case 4 -> {}
                                    case 5 -> {}
                                    case 6 -> {}
                                    default -> {}
                                }
                                var228 = 1;
                                var228 = 4;
                                var228 = 5;
                                var228 = 3;
                                var228 = 6;
                                var228 = 2;
                                var228 = 7;
                                var228 = 0;
                                int var14 = var228;
                                Object var68 = this.getSetting("Color Mode", "Custom");
                                String var69 = ((String) var68);
                                Object var71 = this.getSetting("Colour", "#00AAFF");
                                int var72 = ThemeColors.resolveRgb(var71, 43775, false);
                                float[] var73 = evaluate(var72);
                                Object var75 = this.getSetting("Second Colour", "#B45CFF");
                                int var76 = ThemeColors.resolveRgb(var75, 11820287, true);
                                float[] var77 = evaluate(var76);
                                Float var405 = 0.3F;
                                Object var254 = this.getSetting("Trail Width", var405);
                                Float var255 = ((Float) var254);
                                float var256 = var255;
                                float var79 = Math.max(0.02F, var256);
                                var18 = var79;
                                if (var14 == 2) {
                                    float var81 = var18;
                                    float var82 = (var81 * 1.6F);
                                    var18 = var82;
                                }
                                if (var14 != 4) {
                                    if (var14 == 7) {
                                    }
                                }
                                float var85 = var18;
                                float var86 = (var85 * 1.8F);
                                var18 = var86;
                                Float var302 = 0.9F;
                                Object var88 = this.getSetting("Opacity", var302);
                                Float var89 = ((Float) var88);
                                float var90 = var89;
                                float var91 = computeFloatMetric(var90, 0.0F, 1.0F);
                                Float var407 = 1.0F;
                                Object var265 = this.getSetting("Speed", var407);
                                Float var266 = ((Float) var265);
                                float var267 = var266;
                                float var93 = Math.max(0.0F, var267);
                                Boolean var306 = false;
                                Object var95 = this.getSetting("Through Walls", var306);
                                Boolean var96 = ((Boolean) var95);
                                boolean var97 = var96;
                                long var98 = System.nanoTime();
                                long var307 = timestampMs;
                                long var100 = (var98 - var307);
                                double var102 = ((double) var100);
                                double var104 = (var102 / 1.0E9);
                                double var312 = ((double) var93);
                                double var106 = (var104 * var312);
                                double var108 = (var106 % 3600.0);
                                float var110 = ((float) var108);
                                float var112 = this.getLength();
                                long var113 = System.nanoTime();
                                Vec3 var115 = ClarityRenderUtils.getCameraPos();
                                double var117 = this.getHeight();
                                WorldFx.begin();
                                ClientLevel var120 = var53.level;
                                List var121 = var120.players();
                                Iterator var122 = var121.iterator();
                                while (true) {
                                    boolean var124 = var122.hasNext();
                                    if (!var124) break;
                                    Object var126 = var122.next();
                                    AbstractClientPlayer var127 = ((AbstractClientPlayer) var126);
                                    Map var129 = this.entriesMap;
                                    int var270 = var127.getId();
                                    Integer var271 = var270;
                                    Object var130 = var129.get(var271);
                                    ArrayDeque var131 = ((ArrayDeque) var130);
                                    if (var131 == null) continue;
                                    boolean var134 = var131.isEmpty();
                                    if (var134) continue;
                                    boolean var136 = this.isEligible(var127, var53);
                                    if (!var136) {
                                    }
                                    int var274 = var131.size();
                                    int var275 = (var274 + 1);
                                    this.applyStep(var275);
                                    double var140 = var127.xo;
                                    double var319 = var127.getX();
                                    double var411 = var127.xo;
                                    double var321 = (var319 - var411);
                                    double var414 = ((double) arg1);
                                    double var323 = (var321 * var414);
                                    double var142 = (var140 + var323);
                                    double var145 = var127.yo;
                                    double var326 = var127.getY();
                                    double var417 = var127.yo;
                                    double var328 = (var326 - var417);
                                    double var420 = ((double) arg1);
                                    double var330 = (var328 * var420);
                                    double var147 = (var145 + var330);
                                    double var149 = (var147 + var117);
                                    double var152 = var127.zo;
                                    double var335 = var127.getZ();
                                    double var423 = var127.zo;
                                    double var337 = (var335 - var423);
                                    double var426 = ((double) arg1);
                                    double var339 = (var337 * var426);
                                    double var154 = (var152 + var339);
                                    double[] var157 = this.coordsVector;
                                    double var429 = var115.x;
                                    double var343 = (var142 - var429);
                                    var157[0] = var343;
                                    double[] var159 = this.coordsVector1;
                                    double var432 = var115.y;
                                    double var347 = (var149 - var432);
                                    var159[0] = var347;
                                    double[] var161 = this.coordsVector2;
                                    double var435 = var115.z;
                                    double var351 = (var154 - var435);
                                    var161[0] = var351;
                                    double[] var163 = this.coordsVector3;
                                    var163[0] = 0.0;
                                    var467 = (0 + 1);
                                    Iterator var165 = var131.iterator();
                                    while (true) {
                                        boolean var167 = var165.hasNext();
                                        if (!var167) break;
                                        Object var169 = var165.next();
                                        PlayerTrailPoint var170 = ((PlayerTrailPoint) var169);
                                        long var356 = var170.t;
                                        long var173 = (var113 - var356);
                                        double var175 = ((double) var173);
                                        double var177 = (var175 / 1.0E9);
                                        double var361 = ((double) var112);
                                        double var179 = (var177 / var361);
                                        int var183 = Double.compare(var179, 1.0);
                                        if (var183 >= 0) {
                                        } else {
                                            double var185 = var170.x;
                                            double var366 = var115.x;
                                            double var187 = (var185 - var366);
                                            double var190 = var170.y;
                                            double var369 = var115.y;
                                            double var192 = (var190 - var369);
                                            double var195 = var170.z;
                                            double var372 = var115.z;
                                            double var197 = (var195 - var372);
                                            int var199 = var467;
                                            if (var199 == 1) {
                                                double[] var375 = this.coordsVector;
                                                double var376 = var375[0];
                                                double var202 = (var187 - var376);
                                                double var204 = computeDoubleMetric(var202);
                                                double[] var438 = this.coordsVector1;
                                                double var439 = var438[0];
                                                double var380 = (var192 - var439);
                                                double var382 = computeDoubleMetric(var380);
                                                double var206 = (var204 + var382);
                                                double[] var442 = this.coordsVector2;
                                                double var443 = var442[0];
                                                double var386 = (var197 - var443);
                                                double var388 = computeDoubleMetric(var386);
                                                double var208 = (var206 + var388);
                                                int var210 = Double.compare(var208, 4.0E-4);
                                                if (var210 < 0) {
                                                }
                                            }
                                            double[] var212 = this.coordsVector;
                                            int var281 = var467;
                                            var212[var281] = var187;
                                            double[] var214 = this.coordsVector1;
                                            int var282 = var467;
                                            var214[var282] = var192;
                                            double[] var216 = this.coordsVector2;
                                            int var283 = var467;
                                            var216[var283] = var197;
                                            double[] var218 = this.coordsVector3;
                                            int var284 = var467;
                                            double[] var446 = this.coordsVector3;
                                            int var454 = var467;
                                            int var455 = (var454 - 1);
                                            double var447 = var446[var455];
                                            double var449 = (var447 + 1.0E-4);
                                            double var400 = Math.max(var179, var449);
                                            var218[var284] = var400;
                                            var467 = (var467 + 1);
                                        }
                                    }
                                    int var219 = var467;
                                    if (var219 < 2) {
                                    }
                                    int var286 = var467;
                                    int var221 = this.computeColorOrIndex(var286);
                                    float var402 = var18;
                                    int var460 = var127.getId();
                                    float var461 = computeFloatMetric(var460);
                                    this.applyStep(var221, var402, var14, var69, var73, var77, var110, var461);
                                }
                                boolean var223 = WorldFx.isEmpty();
                                if (var223) {
                                    return;
                                }
                                Consumer var404 = (p0) -> PlayerTrails.lambda$a(var110, var14, var91, p0);
                                WorldFx.flush("trail", var97, var404);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private int computeColorOrIndex(int arg0) {
        int var19 = 0;
        int var20 = 0;
        int var24 = 0;
        int var33 = (((arg0 - 1) * 4) + 1);
        if (this.floatBuffer.length < var33) {
            float[] var90 = new float[(var33 * 2)];
            this.floatBuffer = var90;
            float[] var93 = new float[(var33 * 2)];
            this.floatBuffer1 = var93;
            float[] var96 = new float[(var33 * 2)];
            this.floatBuffer2 = var96;
            float[] var99 = new float[(var33 * 2)];
            this.floatBuffer3 = var99;
        }
        var19 = 0;
        var20 = 0;
        while (true) {
            int var43 = var20;
            int var101 = (arg0 - 1);
            if (var43 < var101) {
                int var56 = Math.max((var20 - 1), 0);
                int var58 = (var20 + 1);
                int var60 = (var20 + 2);
                int var112 = (arg0 - 1);
                int var61 = Math.min(var60, var112);
                var24 = 0;
                while (true) {
                    if (var24 >= 4) break;
                    double var67 = (((double) var24) / 4.0);
                    double var71 = (var67 * var67);
                    double var75 = (var71 * var67);
                    float[] var78 = this.floatBuffer;
                    int var114 = var19;
                    double var151 = this.coordsVector[var56];
                    double[] var192 = this.coordsVector;
                    int var7 = var20;
                    double var193 = var192[var7];
                    double var215 = this.coordsVector[var58];
                    double var235 = this.coordsVector[var61];
                    float var155 = ((float) computeDoubleMetric(var151, var193, var215, var235, var67, var71, var75));
                    var78[var114] = var155;
                    float[] var80 = this.floatBuffer1;
                    int var115 = var19;
                    double var158 = this.coordsVector1[var56];
                    double[] var196 = this.coordsVector1;
                    int var211 = var20;
                    double var197 = var196[var211];
                    double var219 = this.coordsVector1[var58];
                    double var239 = this.coordsVector1[var61];
                    float var162 = ((float) computeDoubleMetric(var158, var197, var219, var239, var67, var71, var75));
                    var80[var115] = var162;
                    float[] var82 = this.floatBuffer2;
                    int var116 = var19;
                    double var165 = this.coordsVector2[var56];
                    double[] var200 = this.coordsVector2;
                    int var212 = var20;
                    double var201 = var200[var212];
                    double var223 = this.coordsVector2[var58];
                    double var243 = this.coordsVector2[var61];
                    float var169 = ((float) computeDoubleMetric(var165, var201, var223, var243, var67, var71, var75));
                    var82[var116] = var169;
                    float[] var84 = this.floatBuffer3;
                    int var117 = var19;
                    double[] var171 = this.coordsVector3;
                    int var187 = var20;
                    double var172 = var171[var187];
                    double var205 = this.coordsVector3[var58];
                    double[] var226 = this.coordsVector3;
                    int var233 = var20;
                    double var227 = var226[var233];
                    double var209 = ((var205 - var227) * var67);
                    float var176 = ((float) (var172 + var209));
                    var84[var117] = var176;
                    var19 = (var19 + 1);
                    var24 = (var24 + 1);
                }
                float[] var45 = this.floatBuffer;
                int var102 = var19;
                double[] var123 = this.coordsVector;
                int var177 = (arg0 - 1);
                float var126 = ((float) var123[var177]);
                var45[var102] = var126;
                float[] var47 = this.floatBuffer1;
                int var103 = var19;
                double[] var128 = this.coordsVector1;
                int var179 = (arg0 - 1);
                float var131 = ((float) var128[var179]);
                var47[var103] = var131;
                float[] var49 = this.floatBuffer2;
                int var104 = var19;
                double[] var133 = this.coordsVector2;
                int var181 = (arg0 - 1);
                float var136 = ((float) var133[var181]);
                var49[var104] = var136;
                float[] var51 = this.floatBuffer3;
                int var105 = var19;
                double[] var138 = this.coordsVector3;
                int var183 = (arg0 - 1);
                float var141 = ((float) var138[var183]);
                var51[var105] = var141;
                return (var19 + 1);
                var20 = (var20 + 1);
            }
        }
    }

    private static double computeDoubleMetric(double arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6) {
        double var26 = (2.0 * arg1);
        double var40 = ((-(arg0) + arg2) * arg4);
        double var28 = (var26 + var40);
        double var44 = (2.0 * arg0);
        double var72 = (5.0 * arg1);
        double var46 = (var44 - var72);
        double var76 = (4.0 * arg2);
        double var52 = (((var46 + var76) - arg3) * arg5);
        double var30 = (var28 + var52);
        double var56 = -(arg0);
        double var84 = (3.0 * arg1);
        double var58 = (var56 + var84);
        double var88 = (3.0 * arg2);
        double var64 = (((var58 - var88) + arg3) * arg6);
        double var32 = (var30 + var64);
        return (0.5 * var32);
    }

    private void applyStep(int arg0, float arg1, int arg2, String arg3, float[] arg4, float[] arg5, float arg6, float arg7) {
        float var17 = 0.0F;
        float var18 = 0.0F;
        float var19 = 0.0F;
        float var20 = 0.0F;
        float var27 = 0.0F;
        int var28 = 0;
        float var361 = 0.0F;
        float var362 = 0.0F;
        float var363 = 0.0F;
        var17 = 0.0F;
        var18 = 1.0F;
        var19 = 0.0F;
        var20 = 0.0F;
        float[] var49 = new float[3];
        float[] var51 = new float[3];
        float[] var53 = new float[3];
        float[] var55 = new float[3];
        float[] var57 = new float[3];
        float[] var59 = new float[3];
        var27 = 0.0F;
        var28 = 0;
        while (true) {
            if (var28 < arg0) {
                if (var28 > 0) {
                    float var64 = var20;
                    float[] var155 = this.floatBuffer;
                    int var11 = var28;
                    float var156 = var155[var11];
                    float[] var231 = this.floatBuffer;
                    int var279 = (var28 - 1);
                    float var232 = var231[var279];
                    float var157 = (var156 - var232);
                    double var158 = ((double) var157);
                    double var160 = computeDoubleMetric(var158);
                    float[] var281 = this.floatBuffer1;
                    int var325 = var28;
                    float var282 = var281[var325];
                    float[] var327 = this.floatBuffer1;
                    int var349 = (var28 - 1);
                    float var328 = var327[var349];
                    double var286 = computeDoubleMetric(((double) (var282 - var328)));
                    double var162 = (var160 + var286);
                    float[] var289 = this.floatBuffer2;
                    int var329 = var28;
                    float var290 = var289[var329];
                    float[] var331 = this.floatBuffer2;
                    int var351 = (var28 - 1);
                    float var332 = var331[var351];
                    double var294 = computeDoubleMetric(((double) (var290 - var332)));
                    double var164 = (var162 + var294);
                    double var166 = Math.sqrt(var164);
                    float var168 = ((float) var166);
                    var20 = (var64 + var168);
                }
                int var68 = Math.max((var28 - 1), 0);
                int var70 = (var28 + 1);
                int var173 = (arg0 - 1);
                int var71 = Math.min(var70, var173);
                float var74 = this.floatBuffer[var71];
                float[] var176 = this.floatBuffer;
                float var177 = var176[var68];
                float var75 = (var74 - var177);
                float var78 = this.floatBuffer1[var71];
                float[] var180 = this.floatBuffer1;
                float var181 = var180[var68];
                float var79 = (var78 - var181);
                float var82 = this.floatBuffer2[var71];
                float[] var184 = this.floatBuffer2;
                float var185 = var184[var68];
                float var83 = (var82 - var185);
                float[] var85 = this.floatBuffer;
                int var186 = var28;
                float var86 = var85[var186];
                float[] var88 = this.floatBuffer1;
                int var187 = var28;
                float var89 = var88[var187];
                float[] var91 = this.floatBuffer2;
                int var188 = var28;
                float var92 = var91[var188];
                float var94 = (var79 * var92);
                float var191 = (var83 * var89);
                float var95 = (var94 - var191);
                float var97 = (var83 * var86);
                float var194 = (var75 * var92);
                float var98 = (var97 - var194);
                float var100 = (var75 * var89);
                float var197 = (var79 * var86);
                float var101 = (var100 - var197);
                float var103 = (var95 * var95);
                float var200 = (var98 * var98);
                float var104 = (var103 + var200);
                float var202 = (var101 * var101);
                double var108 = Math.sqrt(((double) (var104 + var202)));
                float var110 = ((float) var108);
                int var112 = Float.compare(var110, 1.0E-6F);
                if (var112 > 0) {
                    float var114 = (var95 / var110);
                    var361 = var114;
                    float var116 = (var98 / var110);
                    var362 = var116;
                    float var118 = (var101 / var110);
                    var363 = var118;
                    float var119 = var361;
                    var17 = var119;
                    float var120 = var362;
                    var18 = var120;
                    float var121 = var363;
                    var19 = var121;
                }
            } else {
                return;
                float var151 = var17;
                var361 = var151;
                float var152 = var18;
                var362 = var152;
                float var153 = var19;
                var363 = var153;
            }
            float[] var123 = this.floatBuffer3;
            int var207 = var28;
            float var124 = var123[var207];
            float var126 = (arg1 * 0.5F);
            float var243 = (0.55F * var124);
            float var210 = (1.0F - var243);
            float var127 = (var126 * var210);
            float var128 = (var127 * 1.4F);
            float[] var245 = this.floatBuffer;
            int var297 = var28;
            float var246 = var245[var297];
            float var299 = (var361 * var128);
            float var247 = (var246 + var299);
            var53[0] = var247;
            float[] var249 = this.floatBuffer1;
            int var300 = var28;
            float var250 = var249[var300];
            float var302 = (var362 * var128);
            float var251 = (var250 + var302);
            var53[1] = var251;
            float[] var253 = this.floatBuffer2;
            int var303 = var28;
            float var254 = var253[var303];
            float var305 = (var363 * var128);
            float var255 = (var254 + var305);
            var53[2] = var255;
            float[] var257 = this.floatBuffer;
            int var306 = var28;
            float var258 = var257[var306];
            float var308 = (var361 * var128);
            float var259 = (var258 - var308);
            var55[0] = var259;
            float[] var261 = this.floatBuffer1;
            int var309 = var28;
            float var262 = var261[var309];
            float var311 = (var362 * var128);
            float var263 = (var262 - var311);
            var55[1] = var263;
            float[] var265 = this.floatBuffer2;
            int var312 = var28;
            float var266 = var265[var312];
            float var314 = (var363 * var128);
            float var267 = (var266 - var314);
            var55[2] = var267;
            float var339 = var20;
            applyStep(arg3, arg4, arg5, var124, var339, arg6, arg7, var59);
            int var136 = var28;
            if (var136 > 0) {
                float[] var138 = this.floatBuffer3;
                int var219 = var28;
                int var220 = (var219 - 1);
                float var139 = var138[var220];
                float var340 = var27;
                applyStep(var49, var139, 1.4F, var57, var340, arg7);
                float var341 = var27;
                applyStep(var51, var139, -1.4F, var57, var341, arg7);
                float var342 = var20;
                applyStep(var53, var124, 1.4F, var59, var342, arg7);
                float var343 = var27;
                applyStep(var51, var139, -1.4F, var57, var343, arg7);
                float var344 = var20;
                applyStep(var55, var124, -1.4F, var59, var344, arg7);
                float var345 = var20;
                applyStep(var53, var124, 1.4F, var59, var345, arg7);
            }
            System.arraycopy(var53, 0, var49, 0, 3);
            System.arraycopy(var55, 0, var51, 0, 3);
            System.arraycopy(var59, 0, var57, 0, 3);
            float var150 = var20;
            var27 = var150;
            var28 = (var28 + 1);
        }
    }

    private static void applyStep(float[] arg0, float arg1, float arg2, float[] arg3, float arg4, float arg5) {
        float var17 = arg0[0];
        float var19 = arg0[1];
        float var21 = arg0[2];
        float var23 = arg3[0];
        float var25 = arg3[1];
        float var27 = arg3[2];
        WorldFx.vertex(var17, var19, var21, arg1, arg2, var23, var25, var27, 1.0F, arg4, arg5);
    }

    private static void applyStep(String arg0, float[] arg1, float[] arg2, float arg3, float arg4, float arg5, float arg6, float[] arg7) {
        int var15 = 0;
        int var16 = 0;
        var15 = -1;
        switch (arg0.hashCode()) {
            case -1656737386 -> {}
            case 154295120 -> {}
            default -> {}
        }
        if (arg0.equals("Gradient")) {
            var15 = 0;
            if (arg0.equals("Rainbow")) {
                var15 = 1;
            }
            switch (var15) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            float var25 = (arg4 * 0.07F);
            float var39 = (arg5 * 0.25F);
            applyStep(((var25 - var39) + arg6), 0.75F, 1.0F, arg7);
            var16 = 0;
            while (true) {
                if (var16 >= 3) break;
                int var43 = var16;
                int var58 = var16;
                float var50 = arg1[var58];
                int var12 = var16;
                float var60 = arg2[var12];
                int var13 = var16;
                float var67 = arg1[var13];
                float var62 = ((var60 - var67) * arg3);
                float var51 = (var50 + var62);
                arg7[var43] = var51;
                var16 = (var16 + 1);
            }
            float var53 = arg1[0];
            arg7[0] = var53;
            float var55 = arg1[1];
            arg7[1] = var55;
            float var57 = arg1[2];
            arg7[2] = var57;
            return;
        }
    }

    private void applyStep(int arg0) {
        if (this.coordsVector.length < arg0) {
            int var8 = (arg0 * 2);
            double[] var15 = new double[var8];
            this.coordsVector = var15;
            double[] var17 = new double[var8];
            this.coordsVector1 = var17;
            double[] var19 = new double[var8];
            this.coordsVector2 = var19;
            double[] var21 = new double[var8];
            this.coordsVector3 = var21;
        }
    }

    private static float computeFloatMetric(int arg0) {
        float var5 = (((float) arg0) * 0.6180339F);
        float var13 = ((float) Math.floor(((double) var5)));
        return (var5 - var13);
    }

    private static double computeDoubleMetric(double arg0) {
        return (arg0 * arg0);
    }

    private static float computeFloatMetric(float arg0, float arg1, float arg2) {
        float var7 = Math.min(arg2, arg0);
        return Math.max(arg1, var7);
    }

    private static float[] evaluate(int arg0) {
        float[] var5 = new float[3];
        float var11 = (((float) ((arg0 >> 16) & 255)) / 255.0F);
        var5[0] = var11;
        float var16 = (((float) ((arg0 >> 8) & 255)) / 255.0F);
        var5[1] = var16;
        float var20 = (((float) (arg0 & 255)) / 255.0F);
        var5[2] = var20;
        return var5;
    }

    private static void applyStep(float arg0, float arg1, float arg2, float[] arg3) {
        float var32 = ((float) Math.floor(((double) arg0)));
        float var15 = (arg0 - var32);
        float var20 = (Math.abs(((var15 * 6.0F) - 3.0F)) - 1.0F);
        float var39 = Math.abs(((var15 * 6.0F) - 2.0F));
        float var22 = (2.0F - var39);
        float var43 = Math.abs(((var15 * 6.0F) - 4.0F));
        float var24 = (2.0F - var43);
        float var56 = (1.0F - arg1);
        float var72 = computeFloatMetric(var20, 0.0F, 1.0F);
        float var65 = (arg1 * var72);
        float var57 = (var56 + var65);
        float var51 = (arg2 * var57);
        arg3[0] = var51;
        float var59 = (1.0F - arg1);
        float var74 = computeFloatMetric(var22, 0.0F, 1.0F);
        float var68 = (arg1 * var74);
        float var60 = (var59 + var68);
        float var53 = (arg2 * var60);
        arg3[1] = var53;
        float var62 = (1.0F - arg1);
        float var76 = computeFloatMetric(var24, 0.0F, 1.0F);
        float var71 = (arg1 * var76);
        float var63 = (var62 + var71);
        float var55 = (arg2 * var63);
        arg3[2] = var55;
    }

    static {
        String[] var3 = new String[8];
        var3[0] = "Energy";
        var3[1] = "Ribbon";
        var3[2] = "Lightning";
        var3[3] = "Comet";
        var3[4] = "Wind";
        var3[5] = "Aurora";
        var3[6] = "Silk";
        var3[7] = "Ethereal";
        fieldVal = var3;
        timestampMs = System.nanoTime();
    }

}
