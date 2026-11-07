package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerTabOverlay.class)
public abstract class NameProtectScoreboardMixin {
    public NameProtectScoreboardMixin() {
    }

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 0, require = 0)
    private Component cl$nameProtectScoreboardTabEntry(Component arg0) {
        return Modules.nameProtectReplaceInTree(arg0);
    }

    @ModifyVariable(method = "renderTablistScore", at = @At("STORE"), ordinal = 0, require = 0)
    private Component cl$nameProtectScoreboardSidebar(Component arg0) {
        return Modules.nameProtectReplaceInTree(arg0);
    }

}
