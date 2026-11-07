package need.clarity.modules.visual.cosmetics.hats;


public record HatRenderSpec(int[] gradient, double centerAlpha, double edgeAlpha, double chinaSpin, double spin, boolean helmet, boolean bob) {

    public static HatRenderSpec evaluate() {
        int[] var10 = new int[1];
        var10[0] = -16714844;
        HatRenderSpec var0 = new HatRenderSpec(var10, 1.0, 0.4, -0.2, 0.15, false, true);
        return var0;
    }

}
