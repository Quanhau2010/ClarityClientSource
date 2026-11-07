package need.clarity.modules.visual;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

public record SearchBlockEntry(AABB box, Block block) {

}
