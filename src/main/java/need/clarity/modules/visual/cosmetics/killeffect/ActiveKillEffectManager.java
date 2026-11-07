package need.clarity.modules.visual.cosmetics.killeffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public final class ActiveKillEffectManager {
    public static final int CONST_INT = 8;
    private final ArrayList entriesList;
    private final int counterValue;

    public ActiveKillEffectManager() {
        this(8);
    }

    public ActiveKillEffectManager(int arg0) {
        ArrayList var3 = new ArrayList();
        this.entriesList = var3;
        int var7 = Math.max(1, arg0);
        this.counterValue = var7;
    }

    public void applyStep(ActiveKillEffectInstance arg0) {
        boolean var5 = this.entriesList.add(arg0);
        while (true) {
            int var8 = this.entriesList.size();
            int var13 = this.counterValue;
            if (var8 <= var13) break;
            Object var11 = this.entriesList.remove(0);
        }
    }

    public void applyStep(double arg0) {
        ArrayList var6 = this.entriesList;
        Predicate var8 = (p0) -> ActiveKillEffectManager.lambda$a(arg0, p0);
        boolean var7 = var6.removeIf(var8);
    }

    public void applyStep() {
        this.entriesList.clear();
    }

    public boolean isEligible() {
        return this.entriesList.isEmpty();
    }

    public int computeColorOrIndex() {
        return this.entriesList.size();
    }

    public List evaluate() {
        return Collections.unmodifiableList(this.entriesList);
    }

    public static int computeColorOrIndex(int arg0, long arg1) {
        long var12 = (((long) arg0) * -7046029254386353131L);
        long var33 = (arg1 * -4658895280553007687L);
        long var14 = (var12 + var33);
        long var37 = (var14 >>> 31);
        long var22 = ((var14 ^ var37) * -7723592293110705685L);
        long var43 = (var22 >>> 29);
        return ((int) (var22 ^ var43));
    }

}
