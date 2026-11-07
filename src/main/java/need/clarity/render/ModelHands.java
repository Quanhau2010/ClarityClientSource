package need.clarity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import need.clarity.api.Hooks;
import need.clarity.modules.visual.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Matrix3f;
import org.joml.Quaternionf;

public final class ModelHands {
    public static volatile boolean active;
    public static volatile ModelPart right;
    public static volatile ModelPart left;
    private static final double MODEL_FEET = 1.501;

    private ModelHands() {
    }

    public static boolean apply(ModelPart arg0, PoseStack arg1) {
        int var2 = (arg0 == right);
        if (var2 == 0) {
            if (arg0 != left) {
                return false;
            }
            double[] var3 = PlayerModel.handPose(var2);
            if (var3 == null) {
                return false;
            }
            arg1.translate(-(var3[0]), (1.501 - var3[1]), -(var3[2]));
            Matrix3f var4 = new Matrix3f(((float) var3[3]), ((float) var3[4]), ((float) var3[5]), ((float) var3[6]), ((float) var3[7]), ((float) var3[8]), ((float) var3[9]), ((float) var3[10]), ((float) var3[11]));
            arg1.mulPose(new Quaternionf().setFromNormalized(var4));
            if (var2 != 0) {
            } else {
            }
            float var5 = -1.0F;
            arg1.translate((var5 / 16.0F), -0.625F, 0.125F);
            return true;
        }
    }

    static {
        active = false;
        Hooks.bool0("ModelHands.active", () -> ModelHands.lambda$static$0());
        Hooks.act1("ModelHands.setActive", (p0) -> ModelHands.lambda$static$1(p0));
        Hooks.act1("ModelHands.setRight", (p0) -> ModelHands.lambda$static$2(p0));
        Hooks.act1("ModelHands.setLeft", (p0) -> ModelHands.lambda$static$3(p0));
        Hooks.bool1("ModelHands.apply", (p0) -> ModelHands.lambda$static$4(p0));
    }

}
