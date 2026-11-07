package need.clarity.modules.movement;

import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public class NoFall extends ModuleBase {
    private boolean enabledState;

    public NoFall() {
        super("NoFall", "Movement");
        this.enabledState = false;
    }

    public void onEnable() {
        this.enabledState = false;
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        if (var1.player.getAbilities().invulnerable) {
            return;
        }
        String var16 = ((String) this.getSetting("Mode", "Normal"));
        if ("Grim".equals(var16)) {
            this.applyStep1(var1);
        } else {
            this.applyStep(var1);
        }
        boolean var26 = var1.player.onGround();
        this.enabledState = var26;
    }

    private void applyStep(Minecraft arg0) {
        float var13 = ((float) arg0.player.fallDistance);
        if (Float.compare(var13, 0.0F) <= 0) {
            return;
        }
        Boolean var34 = true;
        if (((Boolean) this.getSetting("Only Lethal", var34))) {
            float var22 = (var13 - 3.0F);
            float var32 = arg0.player.getHealth();
            if (Float.compare(var22, var32) < 0) {
                return;
            }
        }
        ClientPacketListener var27 = arg0.player.connection;
        boolean var36 = arg0.player.horizontalCollision;
        ServerboundMovePlayerPacket.StatusOnly var33 = new ServerboundMovePlayerPacket.StatusOnly(true, var36);
        var27.send(var33);
    }

    private void applyStep1(Minecraft arg0) {
        boolean var22 = arg0.player.onGround();
        float var27 = ((float) arg0.player.fallDistance);
        if (!this.enabledState) {
            if (var22) {
                if (Float.compare(var27, 0.0F) > 0) {
                    Boolean var67 = true;
                    if (((Boolean) this.getSetting("Only Lethal", var67))) {
                        float var39 = (var27 - 3.0F);
                        float var63 = arg0.player.getHealth();
                        if (Float.compare(var39, var63) < 0) {
                            return;
                        }
                        double var44 = arg0.player.getX();
                        double var48 = arg0.player.getY();
                        double var52 = arg0.player.getZ();
                        ClientPacketListener var56 = arg0.player.connection;
                        double var68 = (var48 + 1.0E-7);
                        boolean var73 = arg0.player.horizontalCollision;
                        ServerboundMovePlayerPacket.Pos var64 = new ServerboundMovePlayerPacket.Pos(var44, var68, var52, false, var73);
                        var56.send(var64);
                        arg0.player.fallDistance = 0.0;
                    }
                }
                return;
            }
        }
    }

}
