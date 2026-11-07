package need.clarity.modules.utility;

import java.util.Optional;
import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GhostHand extends ModuleBase {
    private static GhostHand INSTANCE;
    private static final double CONST_DBL = 0.1;

    public GhostHand() {
        super("Ghost Hand", "Utility");
        INSTANCE = this;
        Hooks.Bool0 var7 = () -> GhostHand.lambda$b();
        Hooks.bool0("GhostHand.enabled", var7);
        Hooks.Act1 var8 = (p0) -> GhostHand.hook$enabled(p0);
        Hooks.act1("GhostHand.pick", var8);
    }

    public static GhostHand evaluate() {
        return INSTANCE;
    }

    public void applyStep(Minecraft arg0) {
        if (arg0.player != null) {
            if (arg0.level == null) {
            }
        }
        return;
        if (arg0.screen != null) {
            return;
        }
        if (this.isEligible(arg0)) {
            return;
        }
        double var22 = arg0.player.blockInteractionRange();
        Vec3 var26 = arg0.player.getEyePosition();
        Vec3 var37 = arg0.player.getLookAngle().scale(var22);
        Vec3 var31 = var26.add(var37);
        BlockHitResult var33 = this.findBlockHitResult(arg0, var26, var31);
        if (var33 != null) {
            arg0.hitResult = var33;
        }
    }

    private BlockHitResult findBlockHitResult(Minecraft arg0, Vec3 arg1, Vec3 arg2) {
        BlockPos var24 = null;
        int var25 = 0;
        double var33 = arg2.x;
        double var106 = arg1.x;
        double var35 = (var33 - var106);
        double var38 = arg2.y;
        double var109 = arg1.y;
        double var40 = (var38 - var109);
        double var43 = arg2.z;
        double var112 = arg1.z;
        double var45 = (var43 - var112);
        double var49 = (var35 * var35);
        double var118 = (var40 * var40);
        double var51 = (var49 + var118);
        double var122 = (var45 * var45);
        double var55 = Math.sqrt((var51 + var122));
        if (Double.compare(var55, 0.001) < 0) {
            return null;
        }
        int var66 = ((int) Math.ceil((var55 / 0.1)));
        var24 = null;
        var25 = 1;
        while (true) {
            if (var25 > var66) break;
            double var71 = ((double) var25);
            double var129 = ((double) var66);
            double var73 = (var71 / var129);
            double var76 = arg1.x;
            double var133 = (var35 * var73);
            double var78 = (var76 + var133);
            double var136 = arg1.y;
            double var150 = (var40 * var73);
            double var138 = (var136 + var150);
            double var153 = arg1.z;
            double var162 = (var45 * var73);
            double var155 = (var153 + var162);
            BlockPos var80 = BlockPos.containing(var78, var138, var155);
            BlockPos var101 = var24;
            if (var80.equals(var101)) {
            } else {
                var24 = var80;
                BlockState var86 = arg0.level.getBlockState(var80);
                if (var86.isAir()) {
                } else {
                    if ((arg0.level.getBlockEntity(var80) instanceof MenuProvider)) break;
                }
            }
            var25 = (var25 + 1);
        }
        return null;
        Vec3 var95 = this.evaluate(arg0, var80, var86, arg1, arg2);
        Direction var97 = this.evaluate(var95, var80);
        BlockHitResult var98 = new BlockHitResult(var95, var97, var80, false);
        return var98;
    }

    private boolean isEligible(Minecraft arg0) {
        HitResult var6 = arg0.hitResult;
        if ((var6 instanceof BlockHitResult)) {
            BlockHitResult var10 = ((BlockHitResult) var6);
            HitResult.Type var12 = var10.getType();
            HitResult.Type var3 = HitResult.Type.MISS;
            if (var12 == var3) {
                return false;
            }
            ClientLevel var15 = arg0.level;
            BlockPos var21 = var10.getBlockPos();
            return (var15.getBlockEntity(var21) instanceof MenuProvider);
        }
        return false;
    }

    private Vec3 evaluate(Minecraft arg0, BlockPos arg1, BlockState arg2, Vec3 arg3, Vec3 arg4) {
        ClientLevel var32 = arg0.level;
        VoxelShape var18 = arg2.getShape(var32, arg1);
        if (!var18.isEmpty()) {
            Optional var25 = var18.bounds().move(arg1).clip(arg3, arg4);
            if (var25.isPresent()) {
                return ((Vec3) var25.get());
            }
        }
        double var40 = (((double) arg1.getX()) + 0.5);
        double var46 = (((double) arg1.getY()) + 0.5);
        double var52 = (((double) arg1.getZ()) + 0.5);
        Vec3 var31 = new Vec3(var40, var46, var52);
        return var31;
    }

    private Direction evaluate(Vec3 arg0, BlockPos arg1) {
        Direction var64 = null;
        Direction var68 = null;
        Direction var60 = null;
        double var19 = arg0.x;
        double var72 = (((double) arg1.getX()) + 0.5);
        double var21 = (var19 - var72);
        double var24 = arg0.y;
        double var78 = (((double) arg1.getY()) + 0.5);
        double var26 = (var24 - var78);
        double var29 = arg0.z;
        double var84 = (((double) arg1.getZ()) + 0.5);
        double var31 = (var29 - var84);
        double var35 = Math.abs(var21);
        double var39 = Math.abs(var26);
        double var43 = Math.abs(var31);
        if (Double.compare(var35, var39) >= 0) {
            if (Double.compare(var35, var43) >= 0) {
                if (Double.compare(var21, 0.0) > 0) {
                    var68 = Direction.EAST;
                }
            } else {
            }
        }
        if (Double.compare(var39, var35) >= 0) {
            if (Double.compare(var39, var43) >= 0) {
                if (Double.compare(var26, 0.0) > 0) {
                    var64 = Direction.UP;
                }
            } else {
            }
        }
        if (Double.compare(var31, 0.0) > 0) {
            var60 = Direction.SOUTH;
        } else {
            var60 = Direction.NORTH;
        }
        return var60;
        var64 = Direction.DOWN;
        return var64;
        var68 = Direction.WEST;
        return var68;
    }

}
