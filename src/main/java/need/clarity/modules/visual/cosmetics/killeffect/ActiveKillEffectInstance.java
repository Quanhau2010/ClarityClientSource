package need.clarity.modules.visual.cosmetics.killeffect;


public record ActiveKillEffectInstance(KillEffectStyle style, double x, double y, double z, double width, double height, double start, double life, double reach, int segs, int seed) {

    public double computeDoubleMetric(double arg0) {
        double var8 = this.start;
        return (arg0 - var8);
    }

    public boolean isEligible(double arg0) {
        int var9 = 0;
        double var10 = this.start;
        double var6 = (arg0 - var10);
        double var13 = this.life;
        if (Double.compare(var6, var13) >= 0) {
            var9 = 1;
        } else {
            var9 = 0;
        }
        return var9;
    }

    public KillEffectRenderSpec evaluate(double arg0, IGroundHeightSampler arg1) {
        double var16 = this.width;
        double var18 = this.height;
        double var22 = this.computeDoubleMetric(arg0);
        double var20 = Math.max(0.0, var22);
        double var25 = this.life;
        double var27 = this.reach;
        int var29 = this.segs;
        int var30 = this.seed;
        KillEffectRenderSpec var4 = new KillEffectRenderSpec(var16, var18, var20, var25, var27, var29, var30, arg1);
        return var4;
    }

}
