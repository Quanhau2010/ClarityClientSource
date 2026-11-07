package need.clarity.modules.movement;

import need.clarity.api.IMovement;

public final class MoveFixCapabilityProvider implements IMovement {
    private MoveFixCapabilityProvider() {
    }

    public boolean isMovementFrozen() {
        return MoveFix.isEligible();
    }

    public boolean shouldApplyRotationFix() {
        return MoveFix.isMode();
    }

    public boolean shouldCorrectMovementInput() {
        return MoveFix.isMode1();
    }

}
