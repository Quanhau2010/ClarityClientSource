package need.clarity.modules.movement;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Predicate;
import java.util.stream.Stream;
import need.clarity.PerMovementEvents;
import need.clarity.PerPacketSendEvents;
import need.clarity.api.MovementEvent;
import need.clarity.event.EventPacketSend;
import need.clarity.mixin.CreativeModeInventoryScreenAccessor;
import need.clarity.mixin.KeyMappingAccessor;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundChatCommandPacket;
import net.minecraft.network.protocol.game.ServerboundChatPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.lwjgl.glfw.GLFW;

public class InventoryWalk extends ModuleBase {
    private final Queue fieldVal;
    private boolean enabledState;
    private boolean enabledState1;
    private int counterValue;

    public InventoryWalk() {
        super("Inventory Walk", "Movement");
        ConcurrentLinkedDeque var12 = new ConcurrentLinkedDeque();
        this.fieldVal = var12;
        this.enabledState = false;
        this.enabledState1 = false;
        this.counterValue = 0;
        PerMovementEvents.register((p0) -> this.onMovementTick(p0));
        PerPacketSendEvents.register((p0) -> this.onPacketSend(p0));
    }

    public void onDisable() {
        this.applyStep();
        this.enabledState1 = false;
        this.counterValue = 0;
    }

    public void onEnable() {
        this.fieldVal.clear();
        this.enabledState1 = false;
        this.counterValue = 0;
    }

    public void onTick() {
        if (this.isEnabled()) {
            if (!this.isGrim()) {
            }
        }
        return;
        Minecraft var7 = Minecraft.getInstance();
        if (var7.player != null) {
            if (var7.level == null) {
            }
        }
        return;
        if (!this.fieldVal.isEmpty()) {
            if (!this.enabledState1) {
                if (!(var7.screen instanceof AbstractContainerScreen)) {
                    this.enabledState1 = true;
                    this.counterValue = 0;
                }
                return;
            }
        }
    }

    private void onMovementTick(MovementEvent arg0) {
        if (!this.isEnabled()) {
            return;
        }
        Minecraft var12 = Minecraft.getInstance();
        if (var12.player != null) {
            if (var12.level == null) {
            }
        }
        return;
        if (this.isGrim()) {
            if (this.enabledState1) {
                arg0.forward = false;
                arg0.back = false;
                arg0.left = false;
                arg0.right = false;
                arg0.sprint = false;
                return;
            }
        }
        Screen var27 = var12.screen;
        if (var27 != null) {
            if (!this.isEligible(var27)) {
            }
        }
        return;
        long var33 = var12.getWindow().handle();
        KeyMapping var74 = var12.options.keyUp;
        boolean var48 = this.isEligible(var33, var74);
        arg0.forward = var48;
        KeyMapping var77 = var12.options.keyDown;
        boolean var50 = this.isEligible(var33, var77);
        arg0.back = var50;
        KeyMapping var80 = var12.options.keyLeft;
        boolean var52 = this.isEligible(var33, var80);
        arg0.left = var52;
        KeyMapping var83 = var12.options.keyRight;
        boolean var54 = this.isEligible(var33, var83);
        arg0.right = var54;
        KeyMapping var86 = var12.options.keyJump;
        boolean var56 = this.isEligible(var33, var86);
        arg0.jump = var56;
        KeyMapping var89 = var12.options.keyShift;
        boolean var58 = this.isEligible(var33, var89);
        arg0.sneak = var58;
        KeyMapping var92 = var12.options.keySprint;
        boolean var60 = this.isEligible(var33, var92);
        arg0.sprint = var60;
    }

    private void onPacketSend(EventPacketSend arg0) {
        if (this.isEnabled()) {
            if (!this.isGrim()) {
            }
        }
        return;
        if (this.enabledState) {
            return;
        }
        Minecraft var12 = Minecraft.getInstance();
        if (var12.player != null) {
            if (var12.level == null) {
            }
        }
        return;
        Packet var18 = arg0.getPacket();
        if ((var12.screen instanceof AbstractContainerScreen)) {
            if (!(var18 instanceof ServerboundContainerClickPacket)) {
                if (!(var18 instanceof ServerboundContainerClosePacket)) {
                    if (!(var18 instanceof ServerboundChatPacket)) {
                        if ((var18 instanceof ServerboundChatCommandPacket)) {
                        }
                    }
                    boolean var32 = this.fieldVal.add(var18);
                    arg0.cancel();
                    this.enabledState1 = true;
                    this.counterValue = 0;
                    return;
                }
            }
        }
        if (this.enabledState1) {
            if ((var18 instanceof ServerboundMovePlayerPacket)) {
                int var49 = (this.counterValue + 1);
                this.counterValue = var49;
                if (this.counterValue >= 1) {
                    this.applyStep();
                    this.enabledState1 = false;
                    this.counterValue = 0;
                }
                return;
            }
        }
    }

    private void applyStep() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            this.fieldVal.clear();
            return;
        }
        ClientPacketListener var9 = var1.getConnection();
        if (var9 == null) {
            this.fieldVal.clear();
            return;
        }
        this.enabledState = true;
        while (true) {
            if (this.fieldVal.isEmpty()) break;
            Packet var20 = ((Packet) this.fieldVal.poll());
            if (var20 == null) continue;
            var9.send(var20);
        }
        this.enabledState = false;
    }

    private boolean isEligible(Screen arg0) {
        int var16 = 0;
        int var15 = 0;
        if ((arg0 instanceof InventoryScreen)) {
            return true;
        }
        if ((arg0 instanceof CreativeModeInventoryScreen)) {
            if (!this.isEligible()) {
                var15 = 1;
            }
        } else {
            if ((arg0 instanceof AbstractContainerScreen)) {
                if (!this.isEligible1(arg0)) {
                    var16 = 1;
                }
            } else {
                return false;
                var16 = 0;
            }
            return var16;
            var15 = 0;
        }
        return var15;
    }

    private boolean isEligible() {
        int var3 = 0;
        CreativeModeTab var1 = CreativeModeInventoryScreenAccessor.getSelectedTab();
        CreativeModeTab var2 = CreativeModeTabs.searchTab();
        if (var1 == var2) {
            var3 = 1;
        } else {
            var3 = 0;
        }
        return var3;
    }

    private boolean isEligible1(Screen arg0) {
        Stream var5 = arg0.children().stream();
        Object var8 = Objects.requireNonNull(EditBox.class);
        Predicate var7 = (p0) -> EditBox.class.isInstance(p0);
        return var5.anyMatch(var7);
    }

    private boolean isEligible(long arg0, KeyMapping arg1) {
        int var18 = 0;
        int var22 = 0;
        InputConstants.Key var10 = ((KeyMappingAccessor) arg1).getBoundKey();
        int var12 = var10.getValue();
        InputConstants.Type var14 = var10.getType();
        InputConstants.Type var5 = InputConstants.Type.MOUSE;
        if (var14 == var5) {
            if (GLFW.glfwGetMouseButton(arg0, var12) == 1) {
                var22 = 1;
            } else {
                var22 = 0;
            }
            return var22;
        }
        if (GLFW.glfwGetKey(arg0, var12) == 1) {
            var18 = 1;
        } else {
            var18 = 0;
        }
        return var18;
    }

    private boolean isGrim() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Grim", var7));
    }

}
