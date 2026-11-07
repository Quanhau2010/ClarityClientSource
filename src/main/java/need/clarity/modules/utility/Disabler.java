package need.clarity.modules.utility;

import need.clarity.PerPacketEvents;
import need.clarity.event.EventPacketReceive;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

public class Disabler extends ModuleBase {
    private int counterValue;

    public Disabler() {
        super("Disabler", "utility");
        this.counterValue = 0;
        PerPacketEvents.register((p0) -> this.onPacketReceive(p0));
    }

    public void onEnable() {
        this.counterValue = 0;
    }

    public void onDisable() {
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level != null) {
                if (var1.player.connection == null) {
                }
            }
            return;
            String var15 = ((String) this.getSetting("Mode", "Spartan Trident"));
            if ("Spartan Trident".equals(var15)) {
                this.applyStep(var1);
            }
            return;
        }
    }

    private void applyStep(Minecraft arg0) {
        MultiPlayerGameMode var5 = arg0.gameMode;
        LocalPlayer var8 = arg0.player;
        InteractionHand var4 = InteractionHand.MAIN_HAND;
        InteractionResult var6 = var5.useItem(var8, var4);
        int var10 = (this.counterValue + 1);
        this.counterValue = var10;
    }

    private void onPacketReceive(EventPacketReceive arg0) {
        if (!this.isEnabled()) {
            return;
        }
        Object var24 = this.getSetting("Mode", "Spartan Trident");
        if (!("Grim NoFlag".equals(var24))) {
            return;
        }
        Packet var14 = arg0.getPacket();
        if ((var14 instanceof ClientboundPlayerPositionPacket)) {
            ClientboundPlayerPositionPacket var18 = ((ClientboundPlayerPositionPacket) var14);
        } else {
            return;
        }
        arg0.cancel();
        int var21 = var18.id();
        Minecraft var22 = Minecraft.getInstance();
        Runnable var26 = () -> Disabler.setRightClickDelay(var22, var21);
        var22.execute(var26);
    }

}
