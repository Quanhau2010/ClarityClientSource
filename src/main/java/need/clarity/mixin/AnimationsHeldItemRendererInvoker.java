package need.clarity.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemInHandRenderer.class)
public interface AnimationsHeldItemRendererInvoker {
    @Invoker("applyItemArmTransform")
    void cl$applyItemArmTransform(PoseStack arg0, HumanoidArm arg1, float arg2);

    @Invoker("swingArm")
    void cl$swingArm(float arg0, PoseStack arg1, int arg2, HumanoidArm arg3);

}
