package need.clarity.modules.visual.cosmetics.hats;

import need.clarity.modules.visual.cosmetics.ColorMathUtils;
import need.clarity.modules.visual.cosmetics.CosmeticDrawBuilder;
import need.clarity.modules.visual.cosmetics.GeometryPrimitives;
import need.clarity.modules.visual.cosmetics.ICosmeticEffectRenderer;
import need.clarity.modules.visual.cosmetics.IGradientColorSampler;

public final class IceCrownRenderer implements ICosmeticEffectRenderer {
    static final double CONST_DBL = 0.262;
    static final double CONST_DBL1 = 0.286;
    static final double CONST_DBL2 = 0.06;
    static final double CONST_DBL3 = 0.06;
    static final int CONST_INT = 6;
    static final int CONST_INT1 = 12;
    static final int CONST_INT2 = 8;
    static final double[] coordsVector;
    private static final double CONST_DBL4 = 6.283185307179586;
    private final HatRenderSpec hatRenderSpec;

    public IceCrownRenderer() {
        HatRenderSpec var2 = HatRenderSpec.evaluate();
        this(var2);
    }

    public IceCrownRenderer(HatRenderSpec arg0) {
        this.hatRenderSpec = arg0;
    }

    public void renderEffect(CosmeticDrawBuilder arg0) {
        int var28 = 0;
        double var306 = 0.0;
        double var52 = 0.0;
        int var611 = 0;
        int var612 = 0;
        double var362 = 0.0;
        int var379 = 0;
        double var180 = 0.0;
        boolean var51 = arg0.enabledState;
        if (var51) {
            var52 = arg0.doubleValue;
        } else {
            var52 = 0.0;
        }
        double var23 = var52;
        int var55 = arg0.counterValue;
        int var182 = arg0.counterValue1;
        int var56 = ColorMathUtils.computeColorOrIndex(var55, var182, 0.6);
        int var57 = ColorMathUtils.computeColorOrIndex6(var56, 0.72);
        int var59 = arg0.counterValue;
        int var60 = ColorMathUtils.computeColorOrIndex7(var59, 0.55);
        int var62 = arg0.counterValue;
        int var63 = ColorMathUtils.computeColorOrIndex7(var62, 0.82);
        CosmeticDrawBuilder var65 = arg0.drawGeometryPart();
        HatRenderSpec var312 = this.hatRenderSpec;
        boolean var313 = var312.helmet();
        if (var313) {
            var362 = 0.06;
        } else {
            var362 = 0.0;
        }
        CosmeticDrawBuilder var67 = arg0.drawGeometryPart(0.0, var362, 0.0);
        IGradientColorSampler var585 = (p0, p1) -> IceCrownRenderer.lambda$a(var23, var57, var60, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.0, 0.06, 0.286, 14, 0.0, var585, false);
        IGradientColorSampler var587 = (p0, p1) -> IceCrownRenderer.lambda$a(var63, var23, p0, p1);
        GeometryPrimitives.drawGeometryPart(arg0, 0.046, 0.066, 0.29, 14, 0.0, var587, false);
        var28 = 0;
        while (true) {
            int var71 = var28;
            if (var71 >= 12) break;
            int var215 = var28;
            double var216 = ((double) var215);
            double var218 = (var216 + 0.5);
            double var220 = (var218 * 6.283185307179586);
            double var222 = (var220 / 12.0);
            double var74 = (1.5707963267948966 + var222);
            double[] var78 = CrownJewelGeometry.computeVector(0.289, var74, 0.064);
            double var197 = var78[0];
            double var319 = var78[1];
            double var446 = var78[2];
            int var593 = var28;
            int var594 = (var593 + 5);
            double var595 = GeometryPrimitives.computeDoubleMetric(var594);
            double var566 = (0.004 * var595);
            double var532 = (0.013 + var566);
            CrownJewelGeometry.drawGeometryPart(arg0, var197, var319, var446, var532, var63);
            var28 = (var28 + 1);
        }
        var611 = 0;
        while (true) {
            int var81 = var611;
            if (var81 >= 6) break;
            int var274 = var611;
            double var275 = ((double) var274);
            double var277 = (var275 * 6.283185307179586);
            double var279 = (var277 / 6.0);
            double var126 = (1.5707963267948966 + var279);
            double[] var128 = coordsVector;
            int var204 = var611;
            double var129 = var128[var204];
            double var133 = Math.cos(var126);
            double var137 = Math.sin(var126);
            double var141 = -(var137);
            double var509 = 1.3;
            double var413 = (var23 * var509);
            int var511 = var611;
            double var512 = ((double) var511);
            double var514 = (var512 * 1.1);
            double var415 = (var413 + var514);
            double var417 = Math.sin(var415);
            double var283 = (0.025 * var417);
            double var147 = (1.0 + var283);
            int var149 = var611;
            if (var149 == 0) {
                var180 = 0.1;
            } else {
                double var527 = (var129 / 0.3);
                double var439 = (1.0 - var527);
                double var310 = (0.34 * var439);
                var180 = (0.16 + var310);
            }
            double var42 = var180;
            double[] var152 = CrownJewelGeometry.computeVector(0.27199999999999996, var126, 0.054);
            double[] var154 = new double[3];
            double var461 = Math.sin(var42);
            double var327 = (var133 * var461);
            var154[0] = var327;
            double var331 = Math.cos(var42);
            var154[1] = var331;
            double var465 = Math.sin(var42);
            double var335 = (var137 * var465);
            var154[2] = var335;
            double var339 = (var129 * var147);
            double var539 = (0.05 * var129);
            double var471 = (0.034 + var539);
            int var571 = var611;
            double var572 = ((double) var571);
            double var574 = (var572 / 6.0);
            int var608 = var611;
            drawGeometryPart(arg0, var152, var154, var339, var471, var57, var60, var574, var23, var608);
            double var158 = (var126 + 0.27);
            double[] var162 = CrownJewelGeometry.computeVector(0.27399999999999997, var158, 0.054);
            double var165 = Math.cos(var158);
            double var167 = (var165 * 0.55);
            double var299 = (var141 * 0.3);
            double var169 = (var167 + var299);
            double var427 = Math.sin(var158);
            double var429 = (var427 * 0.55);
            double var520 = (var133 * 0.3);
            double var431 = (var429 + var520);
            double[] var171 = GeometryPrimitives.computeVector(var169, 0.82, var431);
            double[] var172 = GeometryPrimitives.computeVector(var171);
            double var343 = (0.42 * var129);
            double var475 = 0.03;
            double var345 = (var343 + var475);
            double var477 = var147;
            double var347 = (var345 * var477);
            double var479 = 0.02;
            double var544 = (0.03 * var129);
            double var481 = (var479 + var544);
            int var578 = var611;
            double var579 = ((double) var578);
            double var581 = (var579 + 0.4);
            double var583 = (var581 / 6.0);
            int var609 = var611;
            int var610 = (var609 + 7);
            drawGeometryPart(arg0, var162, var172, var347, var481, var57, var60, var583, var23, var610);
            double var208 = var154[0];
            double var210 = (var208 * var129);
            double var212 = (var210 * var147);
            double var354 = var154[1];
            double var483 = var129;
            double var356 = (var354 * var483);
            double var485 = var147;
            double var358 = (var356 * var485);
            double var487 = 0.01;
            double var360 = (var358 + var487);
            double[] var489 = var154;
            double var490 = var489[2];
            double var492 = (var490 * var129);
            double var494 = (var492 * var147);
            double[] var175 = GeometryPrimitives.computeVector(var152, var212, var360, var494);
            int var305 = var611;
            if (var305 == 0) {
                var306 = 0.1;
            } else {
                var306 = 0.075;
            }
            int var435 = arg0.counterValue;
            double var496 = 0.85;
            int var436 = ColorMathUtils.computeColorOrIndex7(var435, var496);
            CosmeticDrawBuilder var498 = arg0;
            int var559 = var611;
            double var560 = ((double) var559);
            double var562 = (var560 * 0.23);
            double var499 = CrownJewelGeometry.drawGeometryPart(var498, 3.3, var562);
            CrownJewelGeometry.drawGeometryPart(arg0, var175, var306, var436, var499);
            var611 = (var611 + 1);
        }
        var612 = 0;
        while (true) {
            int var83 = var612;
            if (var83 >= 8) break;
            int var501 = var612;
            int var502 = (var501 + 71);
            double var503 = GeometryPrimitives.computeDoubleMetric(var502);
            double var373 = (0.06 * var503);
            double var229 = (0.1 + var373);
            double var88 = (var23 * var229);
            int var231 = var612;
            int var232 = (var231 * 3);
            int var233 = (var232 + 1);
            double var234 = GeometryPrimitives.computeDoubleMetric(var233);
            double var90 = (var88 + var234);
            double var92 = computeDoubleMetric(var90);
            int var94 = var612;
            double var95 = ((double) var94);
            double var97 = (var95 * 6.283185307179586);
            double var99 = (var97 / 8.0);
            double var242 = (var23 * 0.4);
            int var377 = var612;
            int var378 = (var377 % 2);
            if (var378 == 0) {
                var379 = 1;
            } else {
                var379 = -1;
            }
            double var380 = ((double) var379);
            double var244 = (var242 * var380);
            double var101 = (var99 + var244);
            double var248 = (2.0 * var92);
            double var103 = (var101 + var248);
            int var384 = var612;
            int var385 = (var384 * 5);
            int var386 = (var385 + 9);
            double var387 = GeometryPrimitives.computeDoubleMetric(var386);
            double var252 = (0.09 * var387);
            double var107 = (0.3 + var252);
            double var505 = 6.283185307179586;
            double var391 = (var92 * var505);
            double var507 = 2.0;
            double var393 = (var391 * var507);
            double var395 = Math.sin(var393);
            double var256 = (0.02 * var395);
            double var109 = (var107 + var256);
            double var260 = (var92 * 0.4);
            double var113 = (0.36 - var260);
            double var117 = (var92 * 6.0);
            double var119 = GeometryPrimitives.computeDoubleMetric(var117);
            double var266 = (1.0 - var92);
            double var268 = (var266 * 6.0);
            double var270 = GeometryPrimitives.computeDoubleMetric(var268);
            double var121 = (var119 * var270);
            double[] var203 = CrownJewelGeometry.computeVector(var109, var103, var113);
            int var404 = arg0.counterValue;
            int var405 = ColorMathUtils.computeColorOrIndex7(var404, 0.9);
            double var457 = (0.85 * var121);
            int var406 = ColorMathUtils.computeColorOrIndex5(var405, var457);
            CrownJewelGeometry.drawGeometryPart(arg0, var203, 0.01, var406);
            var612 = (var612 + 1);
        }
        CosmeticDrawBuilder var85 = arg0.drawGeometryPart1();
    }

    private static double computeDoubleMetric(double arg0) {
        double var8 = Math.floor(arg0);
        return (arg0 - var8);
    }

    private static double computeDoubleMetric(double arg0, double arg1, double arg2) {
        double var34 = (arg1 * 0.16);
        double var22 = (computeDoubleMetric((((arg0 - var34) - arg2) + 0.5)) - 0.5);
        double var28 = -((var22 / 0.07));
        double var46 = (var22 / 0.07);
        return Math.exp((var28 * var46));
    }

    private static void drawGeometryPart(CosmeticDrawBuilder arg0, double[] arg1, double[] arg2, double arg3, double arg4, int arg5, int arg6, double arg7, double arg8, int arg9) {
        int var44 = 0;
        double[] var171 = null;
        double[] var70 = GeometryPrimitives.computeVector(arg2);
        double var72 = var70[1];
        double var74 = Math.abs(var72);
        int var76 = Double.compare(var74, 0.9);
        if (var76 < 0) {
            var171 = GeometryPrimitives.computeVector(0.0, 1.0, 0.0);
        } else {
            double var449 = 0.0;
            var171 = GeometryPrimitives.computeVector(1.0, 0.0, var449);
        }
        double[] var35 = var171;
        double[] var80 = GeometryPrimitives.computeVector1(var70, var35);
        double[] var81 = GeometryPrimitives.computeVector(var80);
        double[] var83 = GeometryPrimitives.computeVector1(var70, var81);
        int var85 = (arg9 * 13);
        int var86 = (var85 + 2);
        double var87 = GeometryPrimitives.computeDoubleMetric(var86);
        double var89 = (var87 * 6.283185307179586);
        double var91 = (var89 / 6.0);
        double var177 = var70[0];
        double var179 = (var177 * arg3);
        double var181 = (var179 * 0.62);
        double var300 = var70[1];
        double var302 = (var300 * arg3);
        double var451 = 0.62;
        double var304 = (var302 * var451);
        double[] var453 = var70;
        double var454 = var453[2];
        double var456 = (var454 * arg3);
        double var458 = (var456 * 0.62);
        double[] var94 = GeometryPrimitives.computeVector(arg1, var181, var304, var458);
        double var184 = var70[0];
        double var186 = (var184 * arg3);
        double var309 = var70[1];
        double var460 = arg3;
        double var311 = (var309 * var460);
        double[] var462 = var70;
        double var463 = var462[2];
        double var465 = (var463 * arg3);
        double[] var96 = GeometryPrimitives.computeVector(arg1, var186, var311, var465);
        double var99 = (arg4 * 0.96);
        var44 = 0;
        while (true) {
            int var102 = var44;
            if (var102 >= 6) break;
            int var233 = var44;
            double var234 = ((double) var233);
            double var236 = (var234 * 6.283185307179586);
            double var238 = (var236 / 6.0);
            double var105 = (var91 + var238);
            int var240 = var44;
            int var241 = (var240 + 1);
            double var242 = ((double) var241);
            double var244 = (var242 * 6.283185307179586);
            double var246 = (var244 / 6.0);
            double var109 = (var91 + var246);
            double var113 = (var105 + var109);
            double var115 = (var113 * 0.5);
            double var467 = var105;
            double[] var118 = computeVector(arg1, var81, var83, arg4, var467);
            double var469 = var109;
            double[] var120 = computeVector(arg1, var81, var83, arg4, var469);
            double var471 = var105;
            double[] var122 = computeVector(var94, var81, var83, var99, var471);
            double var473 = var109;
            double[] var124 = computeVector(var94, var81, var83, var99, var473);
            double var406 = 0.0;
            double[] var127 = GeometryPrimitives.computeVector(0.0, 0.0, var406);
            double var194 = var81[0];
            double var324 = Math.cos(var115);
            double var196 = (var194 * var324);
            int var408 = 0;
            double var327 = var83[var408];
            double var475 = var115;
            double var477 = Math.sin(var475);
            double var329 = (var327 * var477);
            double var198 = (var196 + var329);
            int var409 = 1;
            double var332 = var81[var409];
            double var479 = var115;
            double var481 = Math.cos(var479);
            double var334 = (var332 * var481);
            double[] var483 = var83;
            double var484 = var483[1];
            double var587 = Math.sin(var115);
            double var486 = (var484 * var587);
            double var336 = (var334 + var486);
            double[] var488 = var81;
            double var489 = var488[2];
            double var591 = Math.cos(var115);
            double var491 = (var489 * var591);
            double var594 = var83[2];
            double var620 = Math.sin(var115);
            double var596 = (var594 * var620);
            double var493 = (var491 + var596);
            double[] var128 = GeometryPrimitives.computeVector(var127, var198, var336, var493);
            double[] var130 = new double[3];
            int var410 = 0;
            double var339 = var118[var410];
            double[] var495 = var124;
            double var496 = var495[0];
            double var341 = (var339 + var496);
            double var498 = 2.0;
            double var343 = (var341 / var498);
            var130[0] = var343;
            int var411 = 1;
            double var346 = var118[var411];
            double[] var500 = var124;
            double var501 = var500[1];
            double var348 = (var346 + var501);
            double var503 = 2.0;
            double var350 = (var348 / var503);
            var130[1] = var350;
            int var412 = 2;
            double var353 = var118[var412];
            double[] var505 = var124;
            double var506 = var505[2];
            double var355 = (var353 + var506);
            double var508 = 2.0;
            double var357 = (var355 / var508);
            var130[2] = var357;
            CosmeticDrawBuilder var413 = arg0;
            double[] var510 = var130;
            double var414 = var413.computeDoubleMetric(var510, var128);
            double var264 = (0.6 * var414);
            double var133 = (0.55 + var264);
            int var266 = var44;
            double var267 = ((double) var266);
            double var416 = 0.012;
            double var269 = (var267 * var416);
            double var137 = (arg7 + var269);
            double var418 = 0.0;
            double var139 = computeDoubleMetric(var137, arg8, var418);
            int var142 = ColorMathUtils.computeColorOrIndex6(arg5, var133);
            double var204 = (0.3 * var139);
            int var143 = ColorMathUtils.computeColorOrIndex7(var142, var204);
            int var145 = ColorMathUtils.computeColorOrIndex6(arg6, var133);
            double var210 = (0.55 * var139);
            int var146 = ColorMathUtils.computeColorOrIndex7(var145, var210);
            double[] var420 = var122;
            int var511 = var143;
            int var512 = ColorMathUtils.computeColorOrIndex5(var511, 0.9);
            int var555 = ColorMathUtils.computeColorOrIndex5(var143, 0.9);
            int var601 = ColorMathUtils.computeColorOrIndex5(var146, 0.8);
            int var609 = ColorMathUtils.computeColorOrIndex5(var146, 0.8);
            arg0.applyStep(var118, var120, var124, var420, var512, var555, var601, var609);
            double[] var149 = GeometryPrimitives.computeVector(var124, var122);
            double[] var215 = GeometryPrimitives.computeVector(var96, var122);
            double[] var150 = GeometryPrimitives.computeVector1(var149, var215);
            double[] var152 = new double[3];
            int var421 = 0;
            double var365 = var122[var421];
            double var514 = var124[0];
            double var367 = (var365 + var514);
            double var517 = var96[0];
            double var369 = (var367 + var517);
            double var371 = (var369 / 3.0);
            var152[0] = var371;
            int var422 = 1;
            double var374 = var122[var422];
            double var522 = var124[1];
            double var376 = (var374 + var522);
            double var525 = var96[1];
            double var378 = (var376 + var525);
            double var380 = (var378 / 3.0);
            var152[1] = var380;
            int var423 = 2;
            double var383 = var122[var423];
            double var530 = var124[2];
            double var385 = (var383 + var530);
            double var533 = var96[2];
            double var387 = (var385 + var533);
            double var389 = (var387 / 3.0);
            var152[2] = var389;
            CosmeticDrawBuilder var424 = arg0;
            double var425 = var424.computeDoubleMetric(var152, var150);
            double var280 = (0.65 * var425);
            double var155 = (0.55 + var280);
            int var427 = var44;
            double var428 = ((double) var427);
            double var430 = (var428 * 0.012);
            double var284 = (arg7 + var430);
            double var432 = arg8;
            double var286 = computeDoubleMetric(var284, var432, 0.0);
            double var159 = (0.75 * var286);
            CosmeticDrawBuilder var434 = arg0;
            double var435 = var434.computeDoubleMetric(var152, var150);
            double var437 = (var435 - 0.7);
            double var439 = (var437 / 0.3);
            double var441 = GeometryPrimitives.computeDoubleMetric(var439);
            double var290 = (0.5 * var441);
            double var443 = 0.5;
            double var612 = (arg8 * 2.7);
            double var625 = ((double) arg9);
            double var627 = (var625 * 2.1);
            double var614 = (var612 + var627);
            int var629 = var44;
            double var630 = ((double) var629);
            double var632 = (var630 * 1.9);
            double var616 = (var614 + var632);
            double var618 = Math.sin(var616);
            double var574 = (0.5 * var618);
            double var445 = (var443 + var574);
            double var292 = (var290 * var445);
            double var161 = (var159 + var292);
            double var163 = ColorMathUtils.computeDoubleMetric(var161);
            int var166 = ColorMathUtils.computeColorOrIndex6(arg6, var155);
            double var393 = (0.65 * var163);
            double var220 = (0.2 + var393);
            int var167 = ColorMathUtils.computeColorOrIndex7(var166, var220);
            int var447 = var167;
            int var448 = ColorMathUtils.computeColorOrIndex5(var447, 0.78);
            int var544 = ColorMathUtils.computeColorOrIndex5(var167, 0.78);
            int var579 = ColorMathUtils.computeColorOrIndex7(var167, 0.5);
            int var580 = ColorMathUtils.computeColorOrIndex5(var579, 0.5);
            arg0.applyStep(var122, var124, var96, var448, var544, var580);
            var44 = (var44 + 1);
        }
    }

    private static double[] computeVector(double[] arg0, double[] arg1, double[] arg2, double arg3, double arg4) {
        double var27 = (Math.cos(arg4) * arg3);
        double var33 = (Math.sin(arg4) * arg3);
        double var36 = arg0[0];
        double var48 = (arg1[0] * var27);
        double var38 = (var36 + var48);
        double var53 = (arg2[0] * var33);
        double var40 = (var38 + var53);
        double var56 = arg0[1];
        double var69 = (arg1[1] * var27);
        double var58 = (var56 + var69);
        double var74 = (arg2[1] * var33);
        double var60 = (var58 + var74);
        double var77 = arg0[2];
        double var90 = (arg1[2] * var27);
        double var79 = (var77 + var90);
        double var95 = (arg2[2] * var33);
        double var81 = (var79 + var95);
        return GeometryPrimitives.computeVector(var40, var60, var81);
    }

    static {
        double[] var4 = new double[6];
        var4[0] = 0.3;
        var4[1] = 0.2;
        var4[2] = 0.26;
        var4[3] = 0.17;
        var4[4] = 0.26;
        var4[5] = 0.2;
        coordsVector = var4;
    }

}
