package need.clarity.modules.visual.cosmetics.targetesp;


public record TargetEspRenderSpec(double width, double height, double phase, double speed, double age) {
    public static final double CONST_DBL = 0.12;
    public static final double CONST_DBL1 = 0.35;

    public static TargetEspRenderSpec evaluate() {
        TargetEspRenderSpec var0 = new TargetEspRenderSpec(0.6, 1.8, 0.0, 0.25, 10.0);
        return var0;
    }

    public TargetEspRenderSpec evaluate(double arg0) {
        double var10 = this.width;
        double var12 = this.height;
        double var14 = this.phase;
        double var16 = this.speed;
        TargetEspRenderSpec var3 = new TargetEspRenderSpec(var10, var12, var14, var16, arg0);
        return var3;
    }

    public TargetEspRenderSpec evaluate(double arg0, double arg1) {
        double var13 = this.phase;
        double var15 = this.speed;
        double var17 = this.age;
        TargetEspRenderSpec var5 = new TargetEspRenderSpec(arg0, arg1, var13, var15, var17);
        return var5;
    }

    public TargetEspRenderSpec evaluate1(double arg0) {
        double var10 = this.width;
        double var12 = this.height;
        double var14 = this.speed;
        double var16 = this.age;
        TargetEspRenderSpec var3 = new TargetEspRenderSpec(var10, var12, arg0, var14, var16);
        return var3;
    }

    public double computeDoubleMetric() {
        double var7 = 0.0;
        if (Double.compare(this.speed, 0.0) < 0) {
            var7 = -1.0;
        } else {
            var7 = 1.0;
        }
        return var7;
    }

    public double computeDoubleMetric1() {
        double var16 = Math.pow((this.height / 1.8), 0.6);
        double var10 = Math.min(3.0, var16);
        return Math.max(1.0, var10);
    }

}
