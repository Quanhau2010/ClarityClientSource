package need.clarity.modules.visual.cosmetics.killeffect;


public record KillEffectRenderSpec(double width, double height, double age, double life, double reach, int segs, int seed, IGroundHeightSampler ground) {
    public static final IGroundHeightSampler iGroundHeightSampler;

    public static KillEffectRenderSpec evaluate() {
        IGroundHeightSampler var13 = iGroundHeightSampler;
        KillEffectRenderSpec var0 = new KillEffectRenderSpec(0.6, 1.8, 0.3, 0.625, 5.0, 64, 1, var13);
        return var0;
    }

    public KillEffectRenderSpec evaluate(double arg0) {
        double var13 = this.width;
        double var15 = this.height;
        double var17 = this.life;
        double var19 = this.reach;
        int var21 = this.segs;
        int var22 = this.seed;
        IGroundHeightSampler var23 = this.ground;
        KillEffectRenderSpec var3 = new KillEffectRenderSpec(var13, var15, arg0, var17, var19, var21, var22, var23);
        return var3;
    }

    public KillEffectRenderSpec evaluate(double arg0, double arg1) {
        double var16 = this.age;
        double var18 = this.life;
        double var20 = this.reach;
        int var22 = this.segs;
        int var23 = this.seed;
        IGroundHeightSampler var24 = this.ground;
        KillEffectRenderSpec var5 = new KillEffectRenderSpec(arg0, arg1, var16, var18, var20, var22, var23, var24);
        return var5;
    }

    public KillEffectRenderSpec evaluate1(double arg0) {
        double var13 = this.width;
        double var15 = this.height;
        double var17 = this.age;
        double var19 = this.reach;
        int var21 = this.segs;
        int var22 = this.seed;
        IGroundHeightSampler var23 = this.ground;
        KillEffectRenderSpec var3 = new KillEffectRenderSpec(var13, var15, var17, arg0, var19, var21, var22, var23);
        return var3;
    }

    public KillEffectRenderSpec evaluate2(double arg0) {
        double var13 = this.width;
        double var15 = this.height;
        double var17 = this.age;
        double var19 = this.life;
        int var21 = this.segs;
        int var22 = this.seed;
        IGroundHeightSampler var23 = this.ground;
        KillEffectRenderSpec var3 = new KillEffectRenderSpec(var13, var15, var17, var19, arg0, var21, var22, var23);
        return var3;
    }

    public KillEffectRenderSpec evaluate(int arg0) {
        double var11 = this.width;
        double var13 = this.height;
        double var15 = this.age;
        double var17 = this.life;
        double var19 = this.reach;
        int var21 = this.seed;
        IGroundHeightSampler var22 = this.ground;
        KillEffectRenderSpec var2 = new KillEffectRenderSpec(var11, var13, var15, var17, var19, arg0, var21, var22);
        return var2;
    }

    public KillEffectRenderSpec evaluate3(int arg0) {
        double var11 = this.width;
        double var13 = this.height;
        double var15 = this.age;
        double var17 = this.life;
        double var19 = this.reach;
        int var21 = this.segs;
        IGroundHeightSampler var22 = this.ground;
        KillEffectRenderSpec var2 = new KillEffectRenderSpec(var11, var13, var15, var17, var19, var21, arg0, var22);
        return var2;
    }

    public KillEffectRenderSpec evaluate(IGroundHeightSampler arg0) {
        double var11 = this.width;
        double var13 = this.height;
        double var15 = this.age;
        double var17 = this.life;
        double var19 = this.reach;
        int var21 = this.segs;
        int var22 = this.seed;
        KillEffectRenderSpec var2 = new KillEffectRenderSpec(var11, var13, var15, var17, var19, var21, var22, arg0);
        return var2;
    }

    public double computeDoubleMetric() {
        double var16 = Math.pow((this.height / 1.8), 0.6);
        double var10 = Math.min(3.0, var16);
        return Math.max(1.0, var10);
    }

    static {
        iGroundHeightSampler = (p0, p1) -> KillEffectRenderSpec.lambda$b(p0, p1);
    }

}
