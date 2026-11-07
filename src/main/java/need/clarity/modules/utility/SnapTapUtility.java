package need.clarity.modules.utility;

import need.clarity.PerMovementEvents;
import need.clarity.api.MovementEvent;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;

public class SnapTapUtility extends ModuleBase {
    private boolean enabledState;
    private boolean enabledState1;
    private boolean enabledState2;
    private boolean enabledState3;
    private boolean enabledState4;
    private boolean enabledState5;

    public SnapTapUtility() {
        super("Snap Tap", "Utility");
        this.enabledState = false;
        this.enabledState1 = false;
        this.enabledState2 = false;
        this.enabledState3 = false;
        this.enabledState4 = false;
        this.enabledState5 = false;
        PerMovementEvents.register((p0) -> this.onMovementTick(p0));
    }

    public void onDisable() {
        this.enabledState = false;
        this.enabledState1 = false;
        this.enabledState2 = false;
        this.enabledState3 = false;
    }

    private void onMovementTick(MovementEvent arg0) {
        int var41 = 0;
        int var61 = 0;
        int var62 = 0;
        int var15 = 0;
        if (!this.isEnabled()) {
            return;
        }
        if (Minecraft.getInstance().screen != null) {
            return;
        }
        if (arg0.left) {
            if (!this.enabledState) {
                var15 = 1;
            } else {
            }
        }
        var15 = 0;
        int var4 = var15;
        if (arg0.right) {
            if (!this.enabledState1) {
                var61 = 1;
            } else {
                var61 = 0;
            }
            int var5 = var61;
            if (var4 != 0) {
                if (var5 == 0) {
                    this.enabledState4 = false;
                }
                if (var5 != 0) {
                    if (var4 == 0) {
                        this.enabledState4 = true;
                    }
                    if (arg0.left) {
                        if (arg0.right) {
                            if (this.enabledState4) {
                                arg0.left = false;
                            } else {
                                arg0.right = false;
                            }
                            if (arg0.forward) {
                                if (!this.enabledState2) {
                                    var62 = 1;
                                } else {
                                    var62 = 0;
                                }
                                int var6 = var62;
                                if (arg0.back) {
                                    if (!this.enabledState3) {
                                        var41 = 1;
                                    } else {
                                    }
                                }
                                var41 = 0;
                                int var7 = var41;
                                if (var6 != 0) {
                                    if (var7 == 0) {
                                        this.enabledState5 = true;
                                    }
                                    if (var7 != 0) {
                                        if (var6 == 0) {
                                            this.enabledState5 = false;
                                        }
                                        if (arg0.forward) {
                                            if (arg0.back) {
                                                if (this.enabledState5) {
                                                    arg0.back = false;
                                                } else {
                                                    arg0.forward = false;
                                                }
                                                boolean var69 = arg0.originalLeft;
                                                this.enabledState = var69;
                                                boolean var71 = arg0.originalRight;
                                                this.enabledState1 = var71;
                                                boolean var73 = arg0.originalForward;
                                                this.enabledState2 = var73;
                                                boolean var75 = arg0.originalBack;
                                                this.enabledState3 = var75;
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
