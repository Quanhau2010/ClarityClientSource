package need.clarity.modules.visual.cosmetics.aura;


public record LightningArcsAuraSpec(int count, double reach, double rate, int budget) {

    public static LightningArcsAuraSpec evaluate() {
        LightningArcsAuraSpec var0 = new LightningArcsAuraSpec(6, 1.2, 20.0, 600);
        return var0;
    }

    public LightningArcsAuraSpec evaluate(int arg0) {
        int var7 = this.count;
        double var8 = this.reach;
        double var10 = this.rate;
        LightningArcsAuraSpec var2 = new LightningArcsAuraSpec(var7, var8, var10, arg0);
        return var2;
    }

    public int computeColorOrIndex() {
        int var5 = 0;
        int var19 = this.count;
        int var14 = Math.min(12, var19);
        int var6 = Math.max(1, var14);
        var5 = 0;
        while (true) {
            if (var5 >= 4) break;
            int var11 = (LightningArcsAuraRenderer.computeColorOrIndex(var5) * var6);
            int var18 = this.budget;
            if (var11 <= var18) {
                return var5;
            }
            var5 = (var5 + 1);
        }
        return 3;
    }

    public int computeColorOrIndex1() {
        int var17 = this.count;
        int var12 = Math.min(12, var17);
        int var6 = Math.max(1, var12);
        int var8 = this.budget;
        int var14 = LightningArcsAuraRenderer.computeColorOrIndex(3);
        int var9 = (var8 / var14);
        int var16 = Math.min(var6, var9);
        return Math.max(1, var16);
    }

}
