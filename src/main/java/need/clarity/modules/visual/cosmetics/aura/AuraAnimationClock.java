package need.clarity.modules.visual.cosmetics.aura;


public final class AuraAnimationClock {
    public static final double CONST_DBL = 4.3;
    public static final double CONST_DBL1 = 5.6;
    private static final double CONST_DBL2 = 0.5;
    private static final double CONST_DBL3 = 8.5;
    private static final double CONST_DBL4 = 0.12;
    private static final double CONST_DBL5 = 0.6;
    public static final double CONST_DBL6 = 10.0;
    public static final double CONST_DBL7 = 2.0;
    public static final AuraClockState auraClockState;

    private AuraAnimationClock() {
    }

    public static double computeDoubleMetric(double arg0) {
        if (Double.compare(arg0, 0.5) <= 0) {
            return 0.0;
        }
        double var19 = ((arg0 - 0.5) / 8.0);
        return Math.min(1.0, var19);
    }

    public static double computeDoubleMetric(double arg0, double arg1, double arg2) {
        double var36 = 0.0;
        double var38 = 0.0;
        if (Double.compare(arg2, 0.0) <= 0) {
            return arg0;
        }
        if (Double.compare(arg1, arg0) > 0) {
            var38 = 0.12;
        } else {
            var38 = 0.6;
        }
        double var16 = var38;
        double var44 = (arg1 - arg0);
        double var60 = Math.exp((-(arg2) / var16));
        double var54 = (1.0 - var60);
        double var46 = (var44 * var54);
        double var28 = (arg0 + var46);
        if (Double.compare(var28, 0.0) < 0) {
            var36 = 0.0;
        } else {
            if (Double.compare(var28, 1.0) > 0) {
                var36 = 1.0;
            } else {
                var36 = var28;
            }
            return var36;
        }
    }

    public static AuraClockState evaluate(double arg0, double arg1) {
        double var35 = Math.min(2.0, arg1);
        double var22 = Math.max(0.0, var35);
        double var39 = Math.min(1.0, arg0);
        double var28 = (Math.max(0.0, var39) * var22);
        if (Double.compare(var28, 0.0) <= 0) {
            return auraClockState;
        }
        double var53 = (1.5 * var28);
        double var47 = (1.0 + var53);
        double var61 = (0.6 * var28);
        double var57 = (1.0 + var61);
        double var69 = (0.1 * var28);
        double var65 = (1.0 + var69);
        double var77 = (0.12 * var28);
        double var73 = (1.0 + var77);
        AuraClockState var34 = new AuraClockState(var47, var57, var65, var73);
        return var34;
    }

    static {
        AuraClockState var0 = new AuraClockState(1.0, 1.0, 1.0, 1.0);
        auraClockState = var0;
    }

}
