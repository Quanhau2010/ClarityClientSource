package need.clarity.modules.movement;

import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class ReverseStep extends ModuleBase {
    public static ReverseStep INSTANCE;

    public ReverseStep() {
        super("Reverse Step", "Movement");
        INSTANCE = this;
    }

    public static boolean isEligible() {
        int var3 = 0;
        if (INSTANCE != null) {
            if (INSTANCE.isEnabled()) {
                var3 = 1;
            }
        } else {
            var3 = 0;
        }
        return var3;
    }

    public static float getStepDownHeight() {
        float var6 = 0.0F;
        if (INSTANCE == null) {
            var6 = 1.0F;
        } else {
            ReverseStep var3 = INSTANCE;
            Float var7 = 1.0F;
            var6 = ((Float) var3.getSetting("Step Down Height", var7));
        }
        return var6;
    }

    public void onTick() {
        int var15 = 0;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        LocalPlayer var24 = var1.player;
        if (!var24.onGround()) {
            return;
        }
        double var29 = var24.getDeltaMovement().x;
        double var73 = var24.getDeltaMovement().x;
        double var31 = (var29 * var73);
        double var77 = var24.getDeltaMovement().z;
        double var97 = var24.getDeltaMovement().z;
        double var79 = (var77 * var97);
        if (Double.compare((var31 + var79), 1.0E-6) < 0) {
            return;
        }
        float var83 = getStepDownHeight();
        float var61 = Math.min(3.0F, var83);
        float var39 = Math.max(1.0F, var61);
        ClientLevel var41 = var1.level;
        AABB var43 = var24.getBoundingBox();
        var15 = 1;
        while (true) {
            int var45 = var15;
            int var63 = ((int) var39);
            if (var45 > var63) break;
            double var94 = ((double) -(var15));
            AABB var47 = var43.move(0.0, var94, 0.0);
            if (!(var41.noBlockCollision(var24, var47))) {
                if (var15 != 1) break;
                return;
            }
            double var52 = var24.getX();
            double var86 = var24.getY();
            double var100 = ((double) var15);
            double var90 = ((var86 - var100) - 0.1);
            double var105 = var24.getZ();
            BlockPos var54 = BlockPos.containing(var52, var90, var105);
            if (!var41.getBlockState(var54).isAir()) {
                int var68 = var15;
                applyStep(var24, var68);
                return;
            }
            var15 = (var15 + 1);
        }
        return;
        int var71 = (var15 - 1);
        applyStep(var24, var71);
    }

    private static void applyStep(LocalPlayer arg0, int arg1) {
        double var12 = Math.floor(arg0.getY());
        double var25 = ((double) arg1);
        double var14 = (var12 - var25);
        double var19 = arg0.getX();
        double var29 = arg0.getZ();
        arg0.setPos(var19, var14, var29);
        double var23 = arg0.getDeltaMovement().x;
        double var33 = arg0.getDeltaMovement().z;
        arg0.setDeltaMovement(var23, 0.0, var33);
        arg0.resetFallDistance();
    }

}
