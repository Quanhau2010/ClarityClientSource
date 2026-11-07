package need.clarity.modules.visual.cosmetics.hats;

public final class HatWaveMath {
    static final double CONST_DBL = 6.283185307179586;

    private HatWaveMath() {
    }

    static double computeDoubleMetric(double arg0) {
        double var8 = Math.floor(arg0);
        return (arg0 - var8);
    }

    static double computeDoubleMetric(double arg0, double arg1, double arg2, double arg3) {
        double var27 = 0.0;
        double var22 = computeDoubleMetric(((arg0 / arg1) + arg2));
        if (Double.compare(var22, arg3) < 0) {
            double var43 = Math.cos(((var22 / arg3) * 6.283185307179586));
            double var37 = (0.5 * var43);
            var27 = (0.5 - var37);
        } else {
            var27 = 0.0;
        }
        return var27;
    }

    static double computeDoubleMetric(double arg0, double arg1, double arg2) {
        double var14 = ((arg0 - arg1) / arg2);
        return Math.exp((-(var14) * var14));
    }

}
