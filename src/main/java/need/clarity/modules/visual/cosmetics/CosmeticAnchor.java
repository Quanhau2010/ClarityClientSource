package need.clarity.modules.visual.cosmetics;


public enum CosmeticAnchor {
    HEAD,
    BACK,
    FEET,
    CENTER,
    HAND;

    public static final double CONST_DBL = 0.234;
    public static final double CONST_DBL1 = 0.214;
    public static final double CONST_DBL2 = 0.234;
    public static final double CONST_DBL3 = 1.62;
    public static final double CONST_DBL4 = 1.27;
    public static final double CONST_DBL5 = 0.4;
    public static final double CONST_DBL6 = 0.14;
    public static final double CONST_DBL7 = 0.8;

    CosmeticAnchor() {
    }

    public AnchorTransform evaluate(EntityPoseState arg0) {
        AnchorTransform var13 = null;
        switch (this.ordinal()) {
            case 0 -> {}
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> {}
            default -> {}
        }
        double var20 = arg0.x();
        double var28 = arg0.y();
        double var38 = arg0.z();
        var13 = evaluate(arg0, var20, var28, var38);
        MatchException var9 = new MatchException(null, null);
        throw var9;
        var13 = evaluate3(arg0);
        double var17 = arg0.x();
        double var23 = arg0.y();
        double var32 = (arg0.height() * 0.5);
        double var25 = (var23 + var32);
        double var35 = arg0.z();
        var13 = evaluate(arg0, var17, var25, var35);
        var13 = evaluate1(arg0);
        var13 = evaluate2(arg0);
        return var13;
    }

    private static AnchorTransform evaluate(EntityPoseState arg0, double arg1, double arg2, double arg3) {
        double var40 = Math.toRadians(arg0.bodyYawDeg());
        double var46 = -(Math.sin(var40));
        double var50 = Math.cos(var40);
        double var53 = -(var50);
        AnchorTransform var52 = new AnchorTransform(arg1, arg2, arg3, var53, 0.0, var46, 0.0, 1.0, 0.0, var46, 0.0, var50);
        return var52;
    }

    private static AnchorTransform evaluate1(EntityPoseState arg0) {
        double var33 = Math.toRadians(arg0.bodyYawDeg());
        double var39 = -(Math.sin(var33));
        double var43 = Math.cos(var33);
        double var46 = arg0.x();
        double var50 = (var39 * 0.14);
        double var48 = (var46 - var50);
        double var53 = arg0.y();
        double var60 = (arg0.height() * 0.8);
        double var55 = (var53 + var60);
        double var63 = arg0.z();
        double var69 = (var43 * 0.14);
        double var65 = (var63 - var69);
        double var73 = -(var43);
        AnchorTransform var45 = new AnchorTransform(var48, var55, var65, var73, 0.0, var39, 0.0, 1.0, 0.0, var39, 0.0, var43);
        return var45;
    }

    private static AnchorTransform evaluate2(EntityPoseState arg0) {
        double var34 = arg0.x();
        double var41 = arg0.y();
        double var52 = arg0.z();
        AnchorTransform var25 = evaluate(arg0, var34, var41, var52);
        double var31 = (arg0.height() * 0.42);
        double var37 = var25.doubleValue;
        double var45 = (var25.doubleValue3 * 0.37);
        double var39 = (var37 + var45);
        double var50 = (var25.doubleValue1 + var31);
        double var57 = var25.doubleValue2;
        double var63 = (var25.doubleValue5 * 0.37);
        double var59 = (var57 + var63);
        double var66 = var25.doubleValue3;
        double var69 = var25.doubleValue4;
        double var71 = var25.doubleValue5;
        double var73 = var25.doubleValue6;
        double var75 = var25.doubleValue7;
        double var77 = var25.doubleValue8;
        double var79 = var25.doubleValue9;
        double var81 = var25.doubleValue10;
        double var83 = var25.doubleValue11;
        AnchorTransform var33 = new AnchorTransform(var39, var50, var59, var66, var69, var71, var73, var75, var77, var79, var81, var83);
        return var33;
    }

    private static AnchorTransform evaluate3(EntityPoseState arg0) {
        double var55 = Math.toRadians(arg0.headYawDeg());
        double var168 = arg0.headPitchDeg();
        double var131 = Math.min(89.0, var168);
        double var61 = Math.toRadians(Math.max(-89.0, var131));
        double var134 = arg0.followTilt();
        double var63 = (var61 * var134);
        double var67 = Math.cos(var63);
        double var71 = Math.sin(var63);
        double var79 = (-(Math.sin(var55)) * var67);
        double var83 = -(var71);
        double var89 = (Math.cos(var55) * var67);
        double var95 = -(Math.cos(var55));
        double var103 = -(Math.sin(var55));
        double var107 = (0.0 * var89);
        double var144 = (var103 * var83);
        double var109 = (var107 - var144);
        double var113 = (var103 * var79);
        double var150 = (var95 * var89);
        double var115 = (var113 - var150);
        double var119 = (var95 * var83);
        double var156 = (0.0 * var79);
        double var121 = (var119 - var156);
        double var124 = arg0.y();
        double var159 = arg0.computeDoubleMetric();
        double var128 = ((var124 + var159) - 0.214);
        double var164 = arg0.x();
        double var178 = (var109 * 0.234);
        double var166 = (var164 + var178);
        double var186 = (var115 * 0.234);
        double var182 = (var128 + var186);
        double var189 = arg0.z();
        double var195 = (var121 * 0.234);
        double var191 = (var189 + var195);
        AnchorTransform var130 = new AnchorTransform(var166, var182, var191, var95, 0.0, var103, var109, var115, var121, var79, var83, var89);
        return var130;
    }

}
