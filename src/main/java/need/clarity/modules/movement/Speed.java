package need.clarity.modules.movement;

import java.util.function.Predicate;
import java.util.stream.Stream;
import need.clarity.PerMovementEvents;
import need.clarity.api.MovementEvent;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.AABB;

public class Speed extends ModuleBase {
    public Speed() {
        super("Speed", "Movement");
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        Object var14 = this.getSetting("Mode", "Normal");
        if ("Grim Collision".equals(var14)) {
            this.applyStep(var1);
        } else {
            this.applyStep1(var1);
        }
    }

    private void applyStep(Minecraft arg0) {
        int var68 = 0;
        LocalPlayer var23 = arg0.player;
        MovementEvent var24 = PerMovementEvents.lastEvent;
        if (var24 != null) {
            if (!var24.forward) {
                if (!var24.back) {
                    if (!var24.left) {
                        if (var24.right) {
                        }
                    }
                    var68 = 1;
                    var68 = 0;
                }
            }
        } else {
            if (!arg0.options.keyUp.isDown()) {
                if (!arg0.options.keyDown.isDown()) {
                    if (!arg0.options.keyLeft.isDown()) {
                        if (arg0.options.keyRight.isDown()) {
                        }
                    }
                    var68 = 1;
                    var68 = 0;
                }
            }
        }
        if (var68 == 0) {
            return;
        }
        Float var103 = 0.5F;
        float var38 = ((Float) this.getSetting("Shrink Box", var103));
        Float var105 = 0.08F;
        float var42 = ((Float) this.getSetting("Boost Speed", var105));
        AABB var44 = var23.getBoundingBox();
        double var87 = ((double) var38);
        AABB var45 = var44.inflate(var87);
        ClientLevel var47 = arg0.level;
        Predicate var5 = (p0) -> Speed.lambda$a(p0);
        Stream var50 = var47.getEntities(var23, var45, var5).stream();
        Predicate var91 = (p0) -> Speed.lambda$a(var45, p0);
        long var52 = var50.filter(var91).count();
        if (var52 == 0L) {
            return;
        }
        double var61 = Math.toRadians(((double) var23.getYRot()));
        float var94 = ((float) var52);
        double var65 = ((double) (var42 * var94));
        double var101 = (-(Math.sin(var61)) * var65);
        double var115 = (Math.cos(var61) * var65);
        var23.push(var101, 0.0, var115);
    }

    private void applyStep1(Minecraft arg0) {
        boolean var35 = false;
        boolean var40 = false;
        boolean var80 = false;
        boolean var82 = false;
        LocalPlayer var25 = arg0.player;
        MovementEvent var26 = PerMovementEvents.lastEvent;
        if (var26 != null) {
            var82 = var26.forward;
        } else {
            var82 = arg0.options.keyUp.isDown();
        }
        int var12 = var82;
        if (var26 != null) {
            var80 = var26.back;
        } else {
            var80 = arg0.options.keyDown.isDown();
        }
        int var13 = var80;
        if (var26 != null) {
            var35 = var26.left;
        } else {
            var35 = arg0.options.keyLeft.isDown();
        }
        int var14 = var35;
        if (var26 != null) {
            var40 = var26.right;
        } else {
            var40 = arg0.options.keyRight.isDown();
        }
        int var15 = var40;
        if (var12 == 0) {
            if (var13 == 0) {
                if (var14 == 0) {
                    if (var15 == 0) {
                        double var98 = var25.getDeltaMovement().y;
                        var25.setDeltaMovement(0.0, var98, 0.0);
                        return;
                    }
                }
            }
        }
        Float var87 = 4.0F;
        double var53 = (((double) ((Float) this.getSetting("Speed", var87))) / 20.0);
        double var56 = computeDoubleMetric(var25, var12, var13, var14, var15);
        double var64 = ((Math.sin(var56) * -1.0) * var53);
        double var70 = (Math.cos(var56) * var53);
        double var103 = var25.getDeltaMovement().y;
        var25.setDeltaMovement(var64, var103, var70);
    }

    private static double computeDoubleMetric(LocalPlayer arg0, boolean arg1, boolean arg2, boolean arg3, boolean arg4) {
        float var8 = 0.0F;
        float var9 = arg0.getYRot();
        var8 = 0.0F;
        if (arg1) {
            if (arg3) {
                if (!arg4) {
                    var8 = -45.0F;
                }
            }
        } else {
            if (arg2) {
                if (arg3) {
                    if (!arg4) {
                        var8 = -135.0F;
                    }
                }
            } else {
                if (arg3) {
                    if (!arg4) {
                        var8 = -90.0F;
                        if (arg4) {
                            if (!arg3) {
                                var8 = 135.0F;
                            }
                        } else {
                            var8 = 180.0F;
                            if (arg4) {
                                if (!arg3) {
                                    var8 = 45.0F;
                                }
                            }
                        }
                    } else {
                        if (arg4) {
                            if (!arg3) {
                                var8 = 90.0F;
                            }
                            float var6 = var8;
                            return Math.toRadians(((double) (var9 + var6)));
                        }
                    }
                }
            }
        }
    }

}
