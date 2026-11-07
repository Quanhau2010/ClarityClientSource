package need.clarity.modules.combat;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;

public record AutoTrapPlacement(BlockPos pos, BlockHitResult hit, int slot) {

}
