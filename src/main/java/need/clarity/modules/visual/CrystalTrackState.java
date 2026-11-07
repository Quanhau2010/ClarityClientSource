package need.clarity.modules.visual;

import net.minecraft.world.entity.boss.enderdragon.EndCrystal;

public final class CrystalTrackState {
    public EndCrystal endCrystal;
    public long timestampMs;
    public double doubleValue;
    public double doubleValue1;
    public double doubleValue2;
    public double doubleValue3;
    public double doubleValue4;
    public double doubleValue5;
    public float floatValue;
    public float floatValue1;
    public boolean enabledState;
    public boolean enabledState1;
    public final float floatValue2;

    CrystalTrackState(EndCrystal arg0, long arg1, boolean arg2) {
        this.endCrystal = arg0;
        if (arg2) {
            long var24 = arg1;
        } else {
            var24 = (arg1 - 10000000000L);
        }
        this.timestampMs = var24;
        float var21 = ((float) ((((double) arg0.getId()) * 0.618034) % 1.0));
        this.floatValue2 = var21;
    }

}
