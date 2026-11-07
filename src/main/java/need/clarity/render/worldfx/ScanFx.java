package need.clarity.render.worldfx;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import need.clarity.util.ClarityRenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class ScanFx {
    private static final int MAX_DRAWN = 2600;
    private static final float TRAIL = 1.6F;
    private static final List scans;

    private ScanFx() {
    }

    public static void clear() {
        scans.clear();
    }

    public static void spawn(double arg0, double arg1, double arg2, float arg3, float arg4) {
        int var36 = 0;
        int var37 = 0;
        int var38 = 0;
        int var43 = 0;
        int var44 = 0;
        int var475 = 0;
        Minecraft var8 = Minecraft.getInstance();
        ClientLevel var59 = var8.level;
        if (var59 == null) {
            return;
        }
        float var229 = Math.min(40.0F, arg3);
        float var62 = Math.max(2.0F, var229);
        float var471 = var62;
        double var64 = ((double) var471);
        double var66 = Math.ceil(var64);
        int var68 = ((int) var66);
        double var71 = Math.floor(arg0);
        int var73 = ((int) var71);
        double var76 = Math.floor(arg1);
        int var78 = ((int) var76);
        double var81 = Math.floor(arg2);
        int var83 = ((int) var81);
        ArrayList var84 = new ArrayList();
        BlockPos.MutableBlockPos var85 = new BlockPos.MutableBlockPos();
        BlockPos.MutableBlockPos var86 = new BlockPos.MutableBlockPos();
        int[][] var88 = new int[6][];
        int[] var340 = new int[3];
        var340[0] = 1;
        var340[1] = 0;
        var340[2] = 0;
        var88[0] = var340;
        int[] var342 = new int[3];
        var342[0] = -1;
        var342[1] = 0;
        var342[2] = 0;
        var88[1] = var342;
        int[] var344 = new int[3];
        var344[0] = 0;
        var344[1] = 1;
        var344[2] = 0;
        var88[2] = var344;
        int[] var346 = new int[3];
        var346[0] = 0;
        var346[1] = -1;
        var346[2] = 0;
        var88[3] = var346;
        int[] var348 = new int[3];
        var348[0] = 0;
        var348[1] = 0;
        var348[2] = 1;
        var88[4] = var348;
        int[] var350 = new int[3];
        var350[0] = 0;
        var350[1] = 0;
        var350[2] = -1;
        var88[5] = var350;
        double var90 = ((double) var471);
        double var268 = ((double) var471);
        double var92 = (var90 * var268);
        int var95 = -(var68);
        var36 = var95;
        while (true) {
            int var96 = var36;
            if (var96 > var68) break;
            int var98 = -(var68);
            var37 = var98;
            while (true) {
                int var99 = var37;
                if (var99 > var68) break;
                int var101 = -(var68);
                var38 = var101;
                while (true) {
                    int var102 = var38;
                    if (var102 <= var68) {
                        int var103 = var36;
                        double var104 = ((double) var103);
                        double var106 = (var104 + 0.5);
                        int var272 = var36;
                        double var273 = ((double) var272);
                        double var275 = (var273 + 0.5);
                        double var108 = (var106 * var275);
                        int var277 = var37;
                        double var278 = ((double) var277);
                        double var280 = (var278 + 0.5);
                        int var378 = var37;
                        double var379 = ((double) var378);
                        double var381 = (var379 + 0.5);
                        double var282 = (var280 * var381);
                        double var110 = (var108 + var282);
                        int var284 = var38;
                        double var285 = ((double) var284);
                        double var287 = (var285 + 0.5);
                        int var385 = var38;
                        double var386 = ((double) var385);
                        double var388 = (var386 + 0.5);
                        double var289 = (var287 * var388);
                        double var112 = (var110 + var289);
                        int var116 = Double.compare(var112, var92);
                        if (var116 > 0) {
                        }
                    } else {
                        var37 = (var37 + 1);
                    }
                }
            }
            var36 = (var36 + 1);
        }
        int var293 = var36;
        int var234 = (var73 + var293);
        int var351 = var37;
        int var295 = (var78 + var351);
        int var390 = var38;
        int var353 = (var83 + var390);
        BlockPos.MutableBlockPos var118 = var85.set(var234, var295, var353);
        BlockState var120 = var59.getBlockState(var85);
        boolean var122 = var120.isAir();
        if (var122) {
        } else {
            var43 = 0;
            var44 = 0;
            while (true) {
                int var125 = var44;
                if (var125 >= 6) break;
                int var237 = var44;
                int[] var127 = var88[var237];
                int var239 = var85.getX();
                int var297 = var127[0];
                int var240 = (var239 + var297);
                int var299 = var85.getY();
                int var356 = var127[1];
                int var300 = (var299 + var356);
                int var358 = var85.getZ();
                int var393 = var127[2];
                int var359 = (var358 + var393);
                BlockPos.MutableBlockPos var129 = var86.set(var240, var300, var359);
                BlockState var131 = var59.getBlockState(var86);
                boolean var132 = var131.canOcclude();
                if (!var132) {
                    int var133 = var43;
                    int var301 = var44;
                    int var243 = (1 << var301);
                    int var134 = (var133 | var243);
                    var43 = var134;
                }
                var44 = (var44 + 1);
            }
            int var135 = var43;
            if (var135 == 0) {
            } else {
                VoxelShape var137 = var120.getShape(var59, var85);
                VoxelShape var476 = var137;
                boolean var139 = var476.isEmpty();
                if (var139) {
                } else {
                    AABB var141 = var476.bounds();
                    AABB var477 = var141;
                    int var143 = var85.getX();
                    double var144 = ((double) var143);
                    double var304 = var477.minX;
                    double var146 = (var144 + var304);
                    float var148 = ((float) var146);
                    int var150 = var85.getY();
                    double var151 = ((double) var150);
                    double var307 = var477.minY;
                    double var153 = (var151 + var307);
                    float var155 = ((float) var153);
                    int var157 = var85.getZ();
                    double var158 = ((double) var157);
                    double var310 = var477.minZ;
                    double var160 = (var158 + var310);
                    float var162 = ((float) var160);
                    int var164 = var85.getX();
                    double var165 = ((double) var164);
                    double var313 = var477.maxX;
                    double var167 = (var165 + var313);
                    float var169 = ((float) var167);
                    int var171 = var85.getY();
                    double var172 = ((double) var171);
                    double var316 = var477.maxY;
                    double var174 = (var172 + var316);
                    float var176 = ((float) var174);
                    int var178 = var85.getZ();
                    double var179 = ((double) var178);
                    double var319 = var477.maxZ;
                    double var181 = (var179 + var319);
                    float var183 = ((float) var181);
                    float var185 = (var148 + var169);
                    double var186 = ((double) var185);
                    double var188 = (var186 * 0.5);
                    double var190 = (var188 - arg0);
                    float var193 = (var155 + var176);
                    double var194 = ((double) var193);
                    double var196 = (var194 * 0.5);
                    double var198 = (var196 - arg1);
                    float var201 = (var162 + var183);
                    double var202 = ((double) var201);
                    double var204 = (var202 * 0.5);
                    double var206 = (var204 - arg2);
                    float[] var249 = new float[8];
                    var249[0] = var148;
                    var249[1] = var155;
                    var249[2] = var162;
                    var249[3] = var169;
                    var249[4] = var176;
                    var249[5] = var183;
                    double var456 = var190;
                    double var402 = (var190 * var456);
                    double var458 = var198;
                    double var460 = (var458 * var198);
                    double var404 = (var402 + var460);
                    double var462 = var206;
                    double var466 = var206;
                    double var464 = (var462 * var466);
                    double var406 = (var404 + var464);
                    double var408 = Math.sqrt(var406);
                    float var410 = ((float) var408);
                    var249[6] = var410;
                    int var411 = var43;
                    float var412 = ((float) var411);
                    var249[7] = var412;
                    boolean var209 = var84.add(var249);
                }
                var38 = (var38 + 1);
                Comparator var250 = (p0, p1) -> ScanFx.lambda$spawn$0(p0, p1);
                var84.sort(var250);
                int var212 = var84.size();
                int var213 = Math.min(var212, 14000);
                int var472 = var213;
                int var215 = (var472 * 7);
                float[] var216 = new float[var215];
                float[] var473 = var216;
                float[] var218 = new float[var472];
                float[] var474 = var218;
                var475 = 0;
                while (true) {
                    int var220 = var475;
                    if (var220 >= var472) break;
                    int var254 = var475;
                    Object var222 = var84.get(var254);
                    float[] var223 = ((float[]) var222);
                    int var368 = var475;
                    int var369 = (var368 * 7);
                    System.arraycopy(var223, 0, var473, var369, 6);
                    int var256 = var475;
                    int var257 = (var256 * 7);
                    int var258 = (var257 + 6);
                    float var337 = var223[7];
                    var473[var258] = var337;
                    int var259 = var475;
                    float var339 = var223[6];
                    var474[var259] = var339;
                    var475 = (var475 + 1);
                }
                List var227 = scans;
                float var468 = Math.max(1.0F, arg4);
                long var469 = System.nanoTime();
                ScanFx.Scan var260 = new ScanFx.Scan(arg0, arg1, arg2, var471, var468, var469, var473, var474, var472);
                boolean var228 = var227.add(var260);
                return;
            }
        }
    }

    public static boolean isEmpty() {
        return scans.isEmpty();
    }

    public static void render(float arg0, float arg1, boolean arg2, float[] arg3, float[] arg4) {
        float var23 = 0.0F;
        int var32 = 0;
        int var33 = 0;
        if (scans.isEmpty()) {
            return;
        }
        Minecraft var39 = Minecraft.getInstance();
        if (var39.level == null) {
            scans.clear();
            return;
        }
        Vec3 var43 = ClarityRenderUtils.getCameraPos();
        long var44 = System.nanoTime();
        var23 = 0.0F;
        WorldFx.begin();
        Iterator var48 = scans.iterator();
        while (true) {
            if (!var48.hasNext()) break;
            ScanFx.Scan var53 = ((ScanFx.Scan) var48.next());
            long var176 = var53.t;
            float var59 = (((float) (var44 - var176)) / 1.0E9F);
            float var139 = var53.speed;
            float var61 = (var59 * var139);
            float var142 = (var53.range + 8.0F);
            if (Float.compare(var61, var142) > 0) {
                var48.remove();
            }
            float var64 = var23;
            float var144 = var53.range;
            var23 = Math.max(var64, var144);
            double var67 = var53.x;
            double var180 = var43.x;
            float var71 = ((float) (var67 - var180));
            double var73 = var53.y;
            double var183 = var43.y;
            float var77 = ((float) (var73 - var183));
            double var79 = var53.z;
            double var186 = var43.z;
            float var83 = ((float) (var79 - var186));
            float[] var85 = var53.dist;
            int var146 = var53.n;
            float var189 = (var61 - 8.0F);
            int var86 = lowerBound(var85, var146, var189);
            var32 = 0;
            var33 = var86;
            while (true) {
                int var89 = var33;
                int var148 = var53.n;
                if (var89 >= var148) break;
                float[] var91 = var53.dist;
                int var149 = var33;
                float var92 = var91[var149];
                float var151 = (var61 + 1.2F);
                if (Float.compare(var92, var151) > 0) break;
                if (var32 >= 2600) break;
                float[] var96 = var53.dist;
                int var153 = var33;
                float var97 = var96[var153];
                float var155 = var53.range;
                if (Float.compare(var97, var155) > 0) {
                } else {
                    int var100 = (var33 * 7);
                    float var103 = var53.bx[var100];
                    float[] var159 = var53.bx;
                    int var192 = (var100 + 1);
                    float var160 = var159[var192];
                    float[] var194 = var53.bx;
                    int var214 = (var100 + 2);
                    float var195 = var194[var214];
                    float[] var216 = var53.bx;
                    int var234 = (var100 + 3);
                    float var217 = var216[var234];
                    float[] var236 = var53.bx;
                    int var253 = (var100 + 4);
                    float var237 = var236[var253];
                    float[] var255 = var53.bx;
                    int var271 = (var100 + 5);
                    float var256 = var255[var271];
                    float[] var273 = var53.bx;
                    int var288 = (var100 + 6);
                    int var275 = ((int) var273[var288]);
                    faces(var103, var160, var195, var217, var237, var256, var275, 0.004F, var43, var71, var77, var83, var61);
                    var32 = (var32 + 1);
                    var33 = (var33 + 1);
                }
            }
            double var197 = var53.x;
            double var240 = ((double) var53.range);
            double var199 = (var197 - var240);
            double var243 = var53.y;
            double var278 = ((double) var53.range);
            double var245 = (var243 - var278);
            double var281 = var53.z;
            double var297 = ((double) var53.range);
            double var283 = (var281 - var297);
            double var300 = var53.x;
            double var308 = ((double) var53.range);
            double var302 = (var300 + var308);
            double var311 = var53.y;
            double var319 = ((double) var53.range);
            double var313 = (var311 + var319);
            double var322 = var53.z;
            double var328 = ((double) var53.range);
            double var324 = (var322 + var328);
            AABB var104 = new AABB(var199, var245, var283, var302, var313, var324);
            ClientLevel var106 = var39.level;
            Entity var162 = ((Entity) null);
            Predicate var219 = (p0) -> ScanFx.lambda$render$0(var39, p0);
            Iterator var108 = var106.getEntities(var162, var104, var219).iterator();
            while (true) {
                if (!var108.hasNext()) continue;
            }
        }
        Entity var113 = ((Entity) var108.next());
        Vec3 var115 = var113.getPosition(arg0);
        AABB var117 = var113.getBoundingBox();
        double var165 = var115.x;
        double var221 = var113.getX();
        double var167 = (var165 - var221);
        double var224 = var115.y;
        double var258 = var113.getY();
        double var226 = (var224 - var258);
        double var261 = var115.z;
        double var291 = var113.getZ();
        double var263 = (var261 - var291);
        AABB var119 = var117.move(var167, var226, var263).inflate(0.04);
        float var123 = ((float) var119.minX);
        float var174 = ((float) var119.minY);
        float var205 = ((float) var119.minZ);
        float var231 = ((float) var119.maxX);
        float var250 = ((float) var119.maxY);
        float var268 = ((float) var119.maxZ);
        faces(var123, var174, var205, var231, var250, var268, 63, 0.0F, var43, var71, var77, var83, var61);
        if (WorldFx.isEmpty()) {
            return;
        }
        float var127 = var23;
        float var136 = ((float) ((((double) var44) / 1.0E9) % 3600.0));
        Consumer var211 = (p0) -> ScanFx.lambda$render$1(arg3, arg4, var127, arg1, var136, p0);
        WorldFx.flush("scan", arg2, var211);
    }

    static void faces(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6, float arg7, Vec3 arg8, float arg9, float arg10, float arg11, float arg12) {
        float var36 = ((float) arg8.x);
        float var40 = ((float) arg8.y);
        float var44 = ((float) arg8.z);
        float var46 = (arg0 - var36);
        float var48 = (arg3 - var36);
        float var50 = (arg1 - var40);
        float var52 = (arg4 - var40);
        float var54 = (arg2 - var44);
        float var56 = (arg5 - var44);
        float var58 = (var48 - var46);
        float var60 = (var52 - var50);
        float var62 = (var56 - var54);
        if ((arg6 & 1) != 0) {
            quad((var48 + arg7), var50, var54, 0.0F, var60, 0.0F, 0.0F, 0.0F, var62, var60, var62, arg9, arg10, arg11, arg12);
        }
        if ((arg6 & 2) != 0) {
            quad((var46 - arg7), var50, var54, 0.0F, var60, 0.0F, 0.0F, 0.0F, var62, var60, var62, arg9, arg10, arg11, arg12);
        }
        if ((arg6 & 4) != 0) {
            float var99 = (var52 + arg7);
            quad(var46, var99, var54, var58, 0.0F, 0.0F, 0.0F, 0.0F, var62, var58, var62, arg9, arg10, arg11, arg12);
        }
        if ((arg6 & 8) != 0) {
            float var102 = (var50 - arg7);
            quad(var46, var102, var54, var58, 0.0F, 0.0F, 0.0F, 0.0F, var62, var58, var62, arg9, arg10, arg11, arg12);
        }
        if ((arg6 & 16) != 0) {
            float var113 = (var56 + arg7);
            quad(var46, var50, var113, var58, 0.0F, 0.0F, 0.0F, var60, 0.0F, var58, var60, arg9, arg10, arg11, arg12);
        }
        if ((arg6 & 32) != 0) {
            float var115 = (var54 - arg7);
            quad(var46, var50, var115, var58, 0.0F, 0.0F, 0.0F, var60, 0.0F, var58, var60, arg9, arg10, arg11, arg12);
        }
    }

    static void quad(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, float arg7, float arg8, float arg9, float arg10, float arg11, float arg12, float arg13, float arg14) {
        WorldFx.vertex(arg0, arg1, arg2, 0.0F, 0.0F, arg11, arg12, arg13, arg14, arg9, arg10);
        float var27 = (arg0 + arg3);
        float var39 = (arg1 + arg4);
        float var56 = (arg2 + arg5);
        WorldFx.vertex(var27, var39, var56, 1.0F, 0.0F, arg11, arg12, arg13, arg14, arg9, arg10);
        float var30 = ((arg0 + arg3) + arg6);
        float var44 = ((arg1 + arg4) + arg7);
        float var61 = ((arg2 + arg5) + arg8);
        WorldFx.vertex(var30, var44, var61, 1.0F, 1.0F, arg11, arg12, arg13, arg14, arg9, arg10);
        WorldFx.vertex(arg0, arg1, arg2, 0.0F, 0.0F, arg11, arg12, arg13, arg14, arg9, arg10);
        float var34 = ((arg0 + arg3) + arg6);
        float var50 = ((arg1 + arg4) + arg7);
        float var67 = ((arg2 + arg5) + arg8);
        WorldFx.vertex(var34, var50, var67, 1.0F, 1.0F, arg11, arg12, arg13, arg14, arg9, arg10);
        float var36 = (arg0 + arg6);
        float var53 = (arg1 + arg7);
        float var70 = (arg2 + arg8);
        WorldFx.vertex(var36, var53, var70, 0.0F, 1.0F, arg11, arg12, arg13, arg14, arg9, arg10);
    }

    private static int lowerBound(float[] arg0, int arg1, float arg2) {
        int var5 = 0;
        int var6 = 0;
        var5 = 0;
        var6 = arg1;
        while (true) {
            int var9 = var5;
            int var4 = var6;
            if (var9 < var4) {
                int var11 = var5;
                int var20 = var6;
                int var13 = ((var11 + var20) >>> 1);
                if (Float.compare(arg0[var13], arg2) < 0) {
                    var5 = (var13 + 1);
                }
            }
            return var5;
            var6 = var13;
        }
    }

    static {
        ArrayList var0 = new ArrayList();
        scans = var0;
    }

    public record Scan(double x, double y, double z, float range, float speed, long t, float[] bx, float[] dist, int n) {

    }

}
