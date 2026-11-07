package need.clarity.api;


public interface IMovement extends Capability {
    boolean isMovementFrozen();

    boolean shouldApplyRotationFix();

    boolean shouldCorrectMovementInput();

}
