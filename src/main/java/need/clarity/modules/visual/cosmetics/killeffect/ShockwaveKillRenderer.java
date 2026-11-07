package need.clarity.modules.visual.cosmetics.killeffect;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class ShockwaveKillRenderer implements ICosmeticEffectRenderer {
    private static final int CONST_INT = 16;
    private static final int CONST_INT1 = 12;
    private static final int CONST_INT2 = 24;
    private final KillEffectRenderSpec killEffectRenderSpec;

    public static int computeColorOrIndex(int arg0) {
        int var11 = Math.min(128, arg0);
        int var5 = Math.max(12, var11);
        return ((((4 * var5) + 24) + 16) + 36);
    }

    public ShockwaveKillRenderer() {
        KillEffectRenderSpec var2 = KillEffectRenderSpec.evaluate();
        this(var2);
    }

    public ShockwaveKillRenderer(KillEffectRenderSpec arg0) {
        this.killEffectRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        double var63 = this.killEffectRenderSpec.life();
        double var67 = this.killEffectRenderSpec.age();
        int var71 = this.killEffectRenderSpec.segs();
        double var74 = this.killEffectRenderSpec.reach();
        IGroundHeightSampler var78 = this.killEffectRenderSpec.ground();
        double var87 = KillEffectMathUtils.computeDoubleMetric((var74 / 5.0), 0.6, 3.0);
        double var244 = Math.pow((1.0 - var63), 2.2);
        double var176 = (1.0 - var244);
        double var91 = (var74 * var176);
        double var97 = Math.pow((1.0 - var63), 1.15);
        double var186 = GeometryPrimitives.computeDoubleMetric((var63 / 0.03));
        double var99 = (var97 * var186);
        int var103 = ColorMathUtils.computeColorOrIndex7(arg0.counterValue, 0.55);
        int var105 = arg0.counterValue;
        int var107 = arg0.counterValue1;
        double var250 = (1.0 - var63);
        double var190 = (0.1 * var250);
        double var112 = ((0.07 + var190) * var87);
        double var121 = (GeometryPrimitives.computeDoubleMetric(((this.killEffectRenderSpec.seed() * 977) + 3)) * 6.283185307179586);
        if (Double.compare(var99, 0.004) < 0) {
            if (Double.compare(var63, 0.5) > 0) {
                return;
            }
        }
        this.drawGeometryPart(arg0, var78, var63, var87, var91, var103, var105);
        if (Double.compare(var91, 0.04) <= 0) {
            return;
        }
        double var254 = (0.7 * var87);
        double var205 = (0.9 + var254);
        double var316 = (1.0 - var63);
        double var288 = (0.55 * var316);
        double var258 = (0.45 + var288);
        double var207 = (var205 * var258);
        double var135 = Math.min(var91, var207);
        double var262 = (var91 - var135);
        IGradientColorSampler var337 = (p0, p1) -> ShockwaveKillRenderer.lambda$a(var67, var107, var105, var99, p0, p1);
        KillEffectMathUtils.drawGeometryPart(arg0, var78, 0.03, var262, var91, var71, var337);
        double var140 = (var91 * 0.66);
        if (Double.compare(var140, 0.15) > 0) {
            double var147 = (var112 * 0.9);
            double var151 = (0.5 * var99);
            double var223 = GeometryPrimitives.computeDoubleMetric((var63 / 0.14));
            double var153 = (var151 * var223);
            double var296 = (var140 - var147);
            double var268 = Math.max(0.0, var296);
            double var323 = (var147 * 0.5);
            double var300 = (var140 + var323);
            IGradientColorSampler var339 = (p0, p1) -> ShockwaveKillRenderer.lambda$a(var107, var153, p0, p1);
            KillEffectMathUtils.drawGeometryPart(arg0, var78, 0.04, var268, var300, var71, var339);
        }
        double var328 = (var112 * 1.6);
        double var304 = (var91 - var328);
        double var272 = Math.max(0.0, var304);
        double var332 = (var112 * 0.4);
        double var308 = (var91 + var332);
        IGradientColorSampler var341 = (p0, p1) -> ShockwaveKillRenderer.lambda$c(var105, var103, var99, p0, p1);
        KillEffectMathUtils.drawGeometryPart(arg0, var78, 0.05, var272, var308, var71, var341);
        this.drawGeometryPart(arg0, var78, var63, var67, var87, var91, var74, var71, var99, var105, var103);
        if (Double.compare(var91, 0.3) > 0) {
            this.drawGeometryPart(arg0, var78, var63, var87, var91, var121, var99, var103, var105);
            this.drawGeometryPart(arg0, var78, var63, var87, var91, var99, var103, var105);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, int arg5, int arg6) {
        if (Double.compare(arg2, 0.22) >= 0) {
            return;
        }
        double var32 = (arg2 / 0.22);
        double var36 = (0.5 * arg3);
        double var53 = (Math.max(arg4, 0.3) * 0.35);
        double var38 = (var36 + var53);
        double var65 = GeometryPrimitives.computeDoubleMetric(var32);
        double var57 = (1.0 - var65);
        double var42 = (0.7 * var57);
        IGradientColorSampler var69 = (p0, p1) -> ShockwaveKillRenderer.lambda$b(arg5, arg6, var42, p0, p1);
        KillEffectMathUtils.drawGeometryPart(arg0, arg1, 0.06, var38, 24, var69);
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7, double arg8, int arg9, int arg10) {
        double var139 = 0.0;
        int var44 = 0;
        int var325 = 0;
        double var239 = Math.min(arg6, 12.0);
        double var168 = (0.16 * var239);
        double var61 = (0.3 + var168);
        double var174 = Math.pow((1.0 - arg2), 0.9);
        double var63 = (var61 * var174);
        if (Double.compare(var63, 0.02) < 0) {
            return;
        }
        double[] var69 = arg0.computeVector1();
        if (Double.compare(arg0.computeDoubleMetric(), 1.0E-6) > 0) {
            var139 = arg0.computeDoubleMetric();
        } else {
            var139 = 1.0;
        }
        double var38 = var139;
        double[][] var76 = new double[(arg7 + 1)][];
        double[][] var79 = new double[(arg7 + 1)][];
        double[] var82 = new double[(arg7 + 1)];
        double[] var85 = new double[(arg7 + 1)];
        var44 = 0;
        while (true) {
            if (var44 > arg7) break;
            double var90 = ((double) (var44 % arg7));
            double var181 = ((double) arg7);
            double var94 = ((var90 / var181) * 6.283185307179586);
            double var247 = (var94 * 7.0);
            double var287 = (arg3 * 10.0);
            double var251 = Math.sin((var247 + var287));
            double var187 = (0.15 * var251);
            double var98 = (0.85 + var187);
            double var191 = Math.cos(var94);
            double var102 = (arg5 * var191);
            double var195 = Math.sin(var94);
            double var106 = (arg5 * var195);
            double var111 = (arg1.computeDoubleMetric(var102, var106) + 0.03);
            double var201 = (0.12 * var63);
            double var115 = (arg5 + var201);
            int var148 = var44;
            double[] var205 = GeometryPrimitives.computeVector(var102, var111, var106);
            var76[var148] = var205;
            int var149 = var44;
            double var259 = Math.cos(var94);
            double var208 = (var115 * var259);
            double var293 = (var63 * var98);
            double var263 = (var111 + var293);
            double var316 = Math.sin(var94);
            double var297 = (var115 * var316);
            double[] var210 = GeometryPrimitives.computeVector(var208, var263, var297);
            var79[var149] = var210;
            int var150 = var44;
            int var265 = var44;
            double var224 = var76[var265][0];
            int var299 = var44;
            double var275 = var76[var299][1];
            int var318 = var44;
            double var305 = var76[var318][2];
            double var212 = KillEffectMathUtils.computeDoubleMetric(var69, var224, var275, var305, var38);
            var82[var150] = var212;
            int var151 = var44;
            int var267 = var44;
            double var228 = var79[var267][0];
            int var301 = var44;
            double var279 = var79[var301][1];
            int var320 = var44;
            double var309 = var79[var320][2];
            double var215 = KillEffectMathUtils.computeDoubleMetric(var69, var228, var279, var309, var38);
            var85[var151] = var215;
            var44 = (var44 + 1);
        }
        var325 = 0;
        while (true) {
            if (var325 >= arg7) break;
            int var124 = (var325 + 1);
            double var127 = (0.5 * arg8);
            int var269 = var325;
            double var231 = var82[var269];
            double var156 = (var127 * var231);
            int var130 = ColorMathUtils.computeColorOrIndex5(arg9, var156);
            double var234 = var82[var124];
            double var160 = (var127 * var234);
            int var132 = ColorMathUtils.computeColorOrIndex5(arg9, var160);
            int var134 = ColorMathUtils.computeColorOrIndex5(arg10, 0.0);
            int var136 = ColorMathUtils.computeColorOrIndex5(arg10, 0.0);
            int var219 = var325;
            double[] var167 = var76[var219];
            double[] var221 = var76[var124];
            double[] var238 = var79[var124];
            int var281 = var325;
            double[] var273 = var79[var281];
            arg0.applyStep(var167, var221, var238, var273, var130, var132, var136, var134);
            var325 = (var325 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, double arg5, double arg6, int arg7, int arg8) {
        int var30 = 0;
        var30 = 0;
        while (true) {
            if (var30 >= 16) break;
            int var59 = (this.killEffectRenderSpec.seed() * 131);
            int var112 = (var30 * 7);
            int var60 = (var59 + var112);
            double var170 = ((double) var30);
            double var256 = (GeometryPrimitives.computeDoubleMetric((var60 + 1)) - 0.5);
            double var220 = (0.7 * var256);
            double var172 = (var170 + var220);
            double var130 = ((6.283185307179586 * var172) / 16.0);
            double var63 = (arg5 + var130);
            double var67 = (arg4 * 0.9);
            double var260 = GeometryPrimitives.computeDoubleMetric((var60 + 2));
            double var224 = (1.4 * var260);
            double var178 = (0.5 + var224);
            double var136 = (arg3 * var178);
            double var264 = (1.0 - arg2);
            double var228 = (0.65 * var264);
            double var182 = (0.35 + var228);
            double var138 = (var136 * var182);
            double var69 = Math.min(var67, var138);
            double var73 = (0.05 * arg3);
            double var232 = GeometryPrimitives.computeDoubleMetric((var60 + 3));
            double var186 = (0.6 * var232);
            double var144 = (0.7 + var186);
            double var75 = (var73 * var144);
            double var79 = (arg4 - var69);
            double var150 = (0.05 * arg3);
            double var83 = (arg4 + var150);
            double var87 = Math.cos(var63);
            double var91 = Math.sin(var63);
            double var95 = -(var91);
            double var153 = (var79 * var87);
            double var193 = (var95 * var75);
            double var155 = (var153 + var193);
            double var197 = (var79 * var91);
            double var241 = (var87 * var75);
            double var199 = (var197 + var241);
            double[] var100 = KillEffectMathUtils.computeVector(arg1, 0.045, var155, var199);
            double var159 = (var79 * var87);
            double var205 = (var95 * var75);
            double var161 = (var159 - var205);
            double var209 = (var79 * var91);
            double var249 = (var87 * var75);
            double var211 = (var209 - var249);
            double[] var102 = KillEffectMathUtils.computeVector(arg1, 0.045, var161, var211);
            double var165 = (var83 * var87);
            double var217 = (var83 * var91);
            double[] var104 = KillEffectMathUtils.computeVector(arg1, 0.045, var165, var217);
            int var106 = ColorMathUtils.computeColorOrIndex5(arg8, 0.0);
            double var123 = (0.85 * arg6);
            int var108 = ColorMathUtils.computeColorOrIndex5(arg7, var123);
            arg0.applyStep(var100, var102, var104, var106, var106, var108);
            var30 = (var30 + 1);
        }
    }

    private void drawGeometryPart(CosmeticDrawBuilder arg0, IGroundHeightSampler arg1, double arg2, double arg3, double arg4, double arg5, int arg6, int arg7) {
        int var31 = 0;
        double var119 = Math.pow((1.0 - arg2), 2.0);
        double var47 = (1.0 - var119);
        double var56 = (GeometryPrimitives.computeDoubleMetric(((this.killEffectRenderSpec.seed() * 313) + 9)) * 6.283185307179586);
        var31 = 0;
        while (true) {
            if (var31 >= 12) break;
            int var63 = (this.killEffectRenderSpec.seed() * 313);
            int var111 = (var31 * 11);
            int var64 = (var63 + var111);
            double var177 = GeometryPrimitives.computeDoubleMetric((var64 + 1));
            double var126 = (6.283185307179586 * var177);
            double var67 = (var56 + var126);
            double var204 = GeometryPrimitives.computeDoubleMetric((var64 + 2));
            double var181 = (0.45 * var204);
            double var130 = (0.55 + var181);
            double var71 = (arg4 * var130);
            double var134 = Math.cos(var67);
            double var75 = (var71 * var134);
            double var138 = Math.sin(var67);
            double var79 = (var71 * var138);
            double var185 = GeometryPrimitives.computeDoubleMetric((var64 + 3));
            double var142 = (0.9 * var185);
            double var87 = (((0.15 + var142) * arg3) * var47);
            double var189 = (0.5 * arg2);
            double var150 = (1.0 - var189);
            double var89 = (var87 * var150);
            double var96 = ((arg1.computeDoubleMetric(var75, var79) + 0.03) + var89);
            double var100 = (0.16 * arg3);
            double var160 = (1.0 - arg2);
            double var104 = ((var100 * var160) + 0.05);
            double[] var116 = GeometryPrimitives.computeVector(var75, var96, var79);
            double var195 = (var96 + var104);
            double[] var166 = GeometryPrimitives.computeVector(var75, var195, var79);
            double var171 = (0.022 * arg3);
            IGradientColorSampler var213 = (p0, p1) -> ShockwaveKillRenderer.lambda$a(arg6, arg7, arg5, p0, p1);
            GeometryPrimitives.drawGeometryPart(arg0, var116, var166, var171, 3, var213);
            var31 = (var31 + 1);
        }
    }

}
