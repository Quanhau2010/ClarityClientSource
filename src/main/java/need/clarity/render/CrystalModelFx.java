package need.clarity.render;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.object.crystal.EndCrystalModel;
import net.minecraft.client.renderer.entity.state.EndCrystalRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import org.joml.Vector3f;

public final class CrystalModelFx {
    public static final String SHADER = "crystalskin";
    private static final Identifier TEXTURE;
    private static EndCrystalModel model;
    private static final EndCrystalRenderState STATE;
    private static final Vector3f V;
    private static final float[][] LOCAL_UV;

    private CrystalModelFx() {
    }

    private static EndCrystalModel model() {
        if (model == null) {
            model = new EndCrystalModel(EndCrystalModel.createBodyLayer().bakeRoot());
        }
        return model;
    }

    public static int textureId() {
        AbstractTexture var0 = null;
        try {
            var0 = Minecraft.getInstance().getTextureManager().getTexture(TEXTURE);
            GpuTexture var2 = var0.getTexture();
            if ((var2 instanceof GlTexture)) {
            } else {
            }
            return 0;
        } catch (Throwable ignored) {
        }
    }

    public static void emit(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6) {
        EndCrystalModel var7 = model();
        STATE.ageInTicks = arg3;
        STATE.showsBottom = false;
        var7.setupAnim(STATE);
        PoseStack var8 = new PoseStack();
        var8.translate(arg0, arg1, arg2);
        var8.scale(2.0F, 2.0F, 2.0F);
        var8.translate(0.0F, -0.5F, 0.0F);
        var7.root().visit(var8, (p0, p1, p2, p3) -> CrystalModelFx.lambda$emit$0(arg5, arg4, arg6, p0, p1, p2, p3));
    }

    static {
        TEXTURE = Identifier.withDefaultNamespace("textures/entity/end_crystal/end_crystal.png");
        STATE = new EndCrystalRenderState();
        V = new Vector3f();
        new float[2][0] = 0.0F;
        new float[2][1] = 0.0F;
        new float[][4][0] = new float[2];
        new float[2][0] = 1.0F;
        new float[2][1] = 0.0F;
        new float[][4][1] = new float[2];
        new float[2][0] = 1.0F;
        new float[2][1] = 1.0F;
        new float[][4][2] = new float[2];
        new float[2][0] = 0.0F;
        new float[2][1] = 1.0F;
        new float[][4][3] = new float[2];
        LOCAL_UV = new float[][4];
    }

}
