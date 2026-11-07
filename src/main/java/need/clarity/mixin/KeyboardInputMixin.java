package need.clarity.mixin;

import need.clarity.PerMovementEvents;
import need.clarity.api.Movement;
import need.clarity.api.MovementEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.ToggleKeyMapping;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Input;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class KeyboardInputMixin {
    @Shadow
    @Final
    private Options options;
    @Unique
    private boolean cl$originalForward;
    @Unique
    private boolean cl$originalBack;
    @Unique
    private boolean cl$originalLeft;
    @Unique
    private boolean cl$originalRight;
    @Unique
    private boolean cl$originalJump;
    @Unique
    private boolean cl$originalSneak;
    @Unique
    private boolean cl$originalSprint;

    public KeyboardInputMixin() {
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void cl$onTickHead(CallbackInfo arg0) {
        this.cl$originalForward = this.options.keyUp.isDown();
        this.cl$originalBack = this.options.keyDown.isDown();
        this.cl$originalLeft = this.options.keyLeft.isDown();
        this.cl$originalRight = this.options.keyRight.isDown();
        this.cl$originalJump = this.options.keyJump.isDown();
        this.cl$originalSneak = this.options.keyShift.isDown();
        this.cl$originalSprint = this.options.keySprint.isDown();
        MovementEvent var2 = new MovementEvent(this.cl$originalForward, this.cl$originalBack, this.cl$originalLeft, this.cl$originalRight, this.cl$originalJump, this.cl$originalSneak, this.cl$originalSprint);
        PerMovementEvents.fire(var2);
        if (Movement.isMovementFrozen()) {
            var2.forward = false;
            var2.back = false;
            var2.left = false;
            var2.right = false;
            var2.jump = false;
            var2.sneak = false;
            var2.sprint = false;
        }
        this.options.keyUp.setDown(var2.forward);
        this.options.keyDown.setDown(var2.back);
        this.options.keyLeft.setDown(var2.left);
        this.options.keyRight.setDown(var2.right);
        this.options.keyJump.setDown(var2.jump);
        this.cl$setKey(this.options.keyShift, var2.sneak, ((Boolean) this.options.toggleCrouch().get()));
        this.cl$setKey(this.options.keySprint, var2.sprint, ((Boolean) this.options.toggleSprint().get()));
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void cl$onTickReturn(CallbackInfo arg0) {
        this.options.keyUp.setDown(this.cl$originalForward);
        this.options.keyDown.setDown(this.cl$originalBack);
        this.options.keyLeft.setDown(this.cl$originalLeft);
        this.options.keyRight.setDown(this.cl$originalRight);
        this.options.keyJump.setDown(this.cl$originalJump);
        this.cl$setKey(this.options.keyShift, this.cl$originalSneak, ((Boolean) this.options.toggleCrouch().get()));
        this.cl$setKey(this.options.keySprint, this.cl$originalSprint, ((Boolean) this.options.toggleSprint().get()));
        if (Movement.isMovementFrozen()) {
            ((ClientInput) this).keyPresses = Input.EMPTY;
        }
    }

    @Unique
    private void cl$setKey(KeyMapping arg0, boolean arg1, boolean arg2) {
        if ((arg0 instanceof ToggleKeyMapping)) {
            if (arg2) {
                if (arg0.isDown() != arg1) {
                    arg0.setDown(true);
                }
                return;
            }
            arg0.setDown(arg1);
            return;
        }
    }

}
