package need.clarity.mixin;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MultiPlayerGameMode.class)
public interface MultiPlayerGameModeAccessor {
    @Accessor("destroyDelay")
    int getDestroyDelay();

    @Accessor("destroyDelay")
    void setDestroyDelay(int arg0);

    @Accessor("destroyProgress")
    float getDestroyProgress();

    @Accessor("carriedIndex")
    int getCarriedIndex();

    @Accessor("carriedIndex")
    void setCarriedIndex(int arg0);

}
