package need.clarity.modules.visual.cosmetics.aura;


public record OrbitAuraSpec(int count, double radius, double phase, double speed) {

    public static OrbitAuraSpec evaluate() {
        OrbitAuraSpec var0 = new OrbitAuraSpec(5, 1.0, 0.0, 0.25);
        return var0;
    }

    public int computeColorOrIndex(int arg0) {
        int var7 = this.count;
        int var6 = Math.min(arg0, var7);
        return Math.max(1, var6);
    }

    public int computeColorOrIndex1(int arg0) {
        int var4 = 0;
        if (arg0 <= 5) {
            var4 = 2;
        } else {
            if (arg0 <= 8) {
                var4 = 1;
            } else {
                var4 = 0;
            }
            return var4;
        }
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

    public double computeDoubleMetric(int arg0, int arg1) {
        double var10 = this.phase;
        double var14 = ((double) arg0);
        double var18 = ((double) arg1);
        double var16 = (var14 / var18);
        double var12 = (var10 + var16);
        return (6.283185307179586 * var12);
    }

}
