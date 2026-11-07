package need.clarity.api;

public class MovementEvent {
    public boolean forward;
    public boolean back;
    public boolean left;
    public boolean right;
    public boolean jump;
    public boolean sneak;
    public boolean sprint;
    public float forwardAmount;
    public float sidewaysAmount;
    public final boolean originalForward;
    public final boolean originalBack;
    public final boolean originalLeft;
    public final boolean originalRight;
    public final boolean originalJump;
    public final boolean originalSneak;
    public final boolean originalSprint;

    public MovementEvent(boolean arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6) {
        this.forward = arg0;
        this.back = arg1;
        this.left = arg2;
        this.right = arg3;
        this.jump = arg4;
        this.sneak = arg5;
        this.sprint = arg6;
        this.originalForward = arg0;
        this.originalBack = arg1;
        this.originalLeft = arg2;
        this.originalRight = arg3;
        this.originalJump = arg4;
        this.originalSneak = arg5;
        this.originalSprint = arg6;
        this.syncFloatsFromBooleans();
    }

    public void syncFloatsFromBooleans() {
        float var11 = 0.0F;
        float var12 = 0.0F;
        if (this.forward) {
            var11 = 1.0F;
        } else {
            if (this.back) {
                var11 = -1.0F;
            } else {
                var11 = 0.0F;
            }
            this.forwardAmount = var11;
            if (this.left) {
                var12 = 1.0F;
            } else {
                if (this.right) {
                    var12 = -1.0F;
                } else {
                    var12 = 0.0F;
                }
                this.sidewaysAmount = var12;
                return;
            }
        }
    }

    public void syncBooleansFromFloats() {
        int var19 = 0;
        int var20 = 0;
        int var21 = 0;
        int var12 = 0;
        if (Float.compare(this.forwardAmount, 0.0F) > 0) {
            var20 = 1;
        } else {
            var20 = 0;
        }
        this.forward = var20;
        if (Float.compare(this.forwardAmount, 0.0F) < 0) {
            var12 = 1;
        } else {
            var12 = 0;
        }
        this.back = var12;
        if (Float.compare(this.sidewaysAmount, 0.0F) > 0) {
            var19 = 1;
        } else {
            var19 = 0;
        }
        this.left = var19;
        if (Float.compare(this.sidewaysAmount, 0.0F) < 0) {
            var21 = 1;
        } else {
            var21 = 0;
        }
        this.right = var21;
    }

}
