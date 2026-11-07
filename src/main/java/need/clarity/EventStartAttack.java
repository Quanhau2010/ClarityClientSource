package need.clarity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class EventStartAttack {
    private final Player player;
    private final Entity target;

    public EventStartAttack(Player arg0, Entity arg1) {
        this.player = arg0;
        this.target = arg1;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Entity getTarget() {
        return this.target;
    }

}
