package need.clarity.api;


public final class Movement {
    private Movement() {
    }

    private static IMovement m() {
        return ((IMovement) Bus.get(IMovement.class));
    }

    public static boolean isMovementFrozen() {
        int var5 = 0;
        IMovement var0 = m();
        if (var0 != null) {
            if (var0.isMovementFrozen()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    public static boolean shouldApplyRotationFix() {
        int var5 = 0;
        IMovement var0 = m();
        if (var0 != null) {
            if (var0.shouldApplyRotationFix()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    public static boolean shouldCorrectMovementInput() {
        int var5 = 0;
        IMovement var0 = m();
        if (var0 != null) {
            if (var0.shouldCorrectMovementInput()) {
                var5 = 1;
            } else {
            }
        }
        var5 = 0;
        return var5;
    }

}
