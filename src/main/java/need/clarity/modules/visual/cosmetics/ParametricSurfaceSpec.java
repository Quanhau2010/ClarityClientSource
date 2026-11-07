package need.clarity.modules.visual.cosmetics;

import java.util.function.BiConsumer;

public final class ParametricSurfaceSpec {
    public int counterValue;
    public int counterValue1;
    public int counterValue2;
    public int counterValue3;
    public int counterValue4;
    public int counterValue5;
    public double doubleValue;
    public double doubleValue1;
    public double doubleValue2;
    public double doubleValue3;
    public double doubleValue4;
    public double doubleValue5;
    public double doubleValue6;
    public double doubleValue7;
    public double doubleValue8;
    public double[] coordsVector;
    public double[] coordsVector1;
    public double[] coordsVector2;
    public boolean enabledState;
    public BiConsumer vertexConsumer;

    public ParametricSurfaceSpec() {
        this.counterValue = 1;
        this.counterValue1 = 3;
        this.counterValue2 = 6;
        this.counterValue3 = 4;
        this.counterValue4 = 5;
        this.counterValue5 = 1;
        this.doubleValue = 0.3;
        this.doubleValue1 = 0.85;
        this.doubleValue2 = 0.9;
        this.doubleValue3 = 0.55;
        this.doubleValue4 = 0.72;
        this.doubleValue5 = 0.3;
        this.doubleValue6 = 0.0;
        this.doubleValue7 = 0.5;
        this.doubleValue8 = 0.0;
        double[] var50 = new double[3];
        var50[0] = 0.0;
        var50[1] = 0.0;
        var50[2] = 0.0;
        this.coordsVector = var50;
        double[] var52 = new double[3];
        var52[0] = 0.0;
        var52[1] = 0.0;
        var52[2] = 1.0;
        this.coordsVector1 = var52;
        double[] var54 = new double[1];
        var54[0] = 0.0;
        this.coordsVector2 = var54;
        this.enabledState = false;
        this.vertexConsumer = null;
    }

}
