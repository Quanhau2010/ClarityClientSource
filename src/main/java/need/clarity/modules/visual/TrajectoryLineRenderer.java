package need.clarity.modules.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import need.clarity.util.ClarityRenderUtils;
import net.minecraft.world.phys.Vec3;

public final class TrajectoryLineRenderer {
    private TrajectoryLineRenderer() {
    }

    static void renderWorldEffect(PoseStack arg0, List arg1, int arg2, float arg3) {
        int var10 = 0;
        var10 = 0;
        while (true) {
            int var11 = var10;
            int var14 = (arg1.size() - 1);
            if (var11 >= var14) break;
            int var18 = var10;
            Vec3 var17 = ((Vec3) arg1.get(var18));
            int var22 = (var10 + 1);
            Vec3 var21 = ((Vec3) arg1.get(var22));
            ClarityRenderUtils.drawWorldLine(arg0, var17, var21, arg2, arg3, true);
            var10 = (var10 + 1);
        }
    }

}
