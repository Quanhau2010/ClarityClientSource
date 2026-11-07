package need.clarity.modules.combat;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public record PiercingRaySegment(Player player, Vec3 start, Vec3 end) {

}
