package need.clarity.mixin;

import need.clarity.api.Modules;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatComponent.class)
public abstract class NameProtectChatMixin {
    public NameProtectChatMixin() {
    }

    @ModifyVariable(method = "addMessage", at = @At("HEAD"), argsOnly = true, ordinal = 0, require = 0)
    private Component cl$nameProtectChat(Component arg0) {
        return Modules.nameProtectReplaceInTree(arg0);
    }

}
