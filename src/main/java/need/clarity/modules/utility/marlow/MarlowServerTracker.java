package need.clarity.modules.utility.marlow;


public final class MarlowServerTracker {
    private static final int CONST_INT = 10;
    private final BoundedLruList boundedLruList;
    private final BoundedLruList boundedLruList1;
    private volatile boolean enabledState;

    public MarlowServerTracker() {
        BoundedLruList var2 = new BoundedLruList(10);
        this.boundedLruList = var2;
        BoundedLruList var6 = new BoundedLruList(10);
        this.boundedLruList1 = var6;
    }

    public boolean isEligible() {
        return this.enabledState;
    }

    public void applyStep(boolean arg0) {
        this.enabledState = arg0;
    }

    public void applyStep(String arg0) {
        if (arg0 == null) {
            return;
        }
        BoundedLruList var6 = this.boundedLruList;
        try {
            if (!this.boundedLruList.contains(arg0)) {
                boolean var12 = this.boundedLruList.isEligible(arg0);
            }
        } catch (Throwable ignored) {
        }
        BoundedLruList var15 = var6;
        throw var15;
        this.enabledState = true;
    }

    public boolean isEligible(String arg0) {
        if (arg0 == null) {
            return false;
        }
        BoundedLruList var6 = this.boundedLruList;
        try {
            boolean var9 = this.boundedLruList.contains(arg0);
        } catch (Throwable ignored) {
        }
        return var9;
        BoundedLruList var10 = var6;
        throw var10;
    }

    public boolean isEligible1(String arg0) {
        if (arg0 == null) {
            return false;
        }
        BoundedLruList var7 = this.boundedLruList1;
        try {
            boolean var10 = this.boundedLruList1.contains(arg0);
        } catch (Throwable ignored) {
        }
        return var10;
        BoundedLruList var11 = var7;
        throw var11;
    }

    public void applyStep1(String arg0) {
        if (arg0 == null) {
            return;
        }
        BoundedLruList var6 = this.boundedLruList1;
        try {
            if (!this.boundedLruList1.contains(arg0)) {
                boolean var12 = this.boundedLruList1.isEligible(arg0);
            }
        } catch (Throwable ignored) {
        }
        return;
        BoundedLruList var14 = var6;
        throw var14;
    }

    public void applyStep() {
        this.enabledState = false;
    }

}
