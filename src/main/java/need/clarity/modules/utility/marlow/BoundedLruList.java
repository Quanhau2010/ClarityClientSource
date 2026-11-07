package need.clarity.modules.utility.marlow;

import java.util.LinkedList;

public final class BoundedLruList extends LinkedList {
    private final int counterValue;

    BoundedLruList(int arg0) {
        super();
        this.counterValue = arg0;
    }

    public boolean isEligible(Object arg0) {
        int var4 = this.size();
        int var11 = this.counterValue;
        if (var4 >= var11) {
            if (this.counterValue > 0) {
                Object var8 = this.removeFirst();
            }
            return super.add(arg0);
        }
    }

}
