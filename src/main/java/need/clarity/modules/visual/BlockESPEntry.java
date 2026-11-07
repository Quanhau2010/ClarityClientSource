package need.clarity.modules.visual;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

public record BlockESPEntry(AABB box, Block block, BlockPos pos, int mapColor) {

}
