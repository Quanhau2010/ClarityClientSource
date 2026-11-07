package need.clarity.modules.visual;


public final class CrystalPopEffectEntry {
    public final double doubleValue;
    public final double doubleValue1;
    public final double doubleValue2;
    public final double doubleValue3;
    public final double doubleValue4;
    public final double doubleValue5;
    public final long timestampMs;
    public final float floatValue;
    public final float floatValue1;
    public final float floatValue2;

    CrystalPopEffectEntry(CrystalTrackState arg0, long arg1) {
        double var16 = arg0.doubleValue;
        this.doubleValue = var16;
        double var19 = arg0.doubleValue1;
        this.doubleValue1 = var19;
        double var22 = arg0.doubleValue2;
        this.doubleValue2 = var22;
        double var25 = arg0.doubleValue3;
        this.doubleValue3 = var25;
        double var28 = arg0.doubleValue4;
        this.doubleValue4 = var28;
        double var31 = arg0.doubleValue5;
        this.doubleValue5 = var31;
        this.timestampMs = arg1;
        float var36 = arg0.floatValue;
        this.floatValue = var36;
        float var38 = arg0.floatValue2;
        this.floatValue1 = var38;
        float var40 = arg0.floatValue1;
        this.floatValue2 = var40;
    }

}
