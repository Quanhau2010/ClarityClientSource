package need.clarity.modules.movement;

import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Step extends ModuleBase {
    public static Step INSTANCE;
    private static final Identifier fieldVal;
    private static final float floatValue = 0.6F;

    public Step() {
        super("Step", "Movement");
        INSTANCE = this;
    }

    public void onEnable() {
        this.applyStep();
    }

    public void onTick() {
        this.applyStep();
    }

    public void onDisable() {
        this.applyStep1();
    }

    private void applyStep() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return;
        }
        LocalPlayer var14 = var1.player;
        Holder var2 = Attributes.STEP_HEIGHT;
        AttributeInstance var15 = var14.getAttribute(var2);
        if (var15 == null) {
            return;
        }
        Float var31 = 1.0F;
        float var20 = ((Float) this.getSetting("Step Height", var31));
        float var28 = Math.min(3.0F, var20);
        float var22 = Math.max(0.6F, var28);
        Identifier var29 = fieldVal;
        boolean var24 = var15.removeModifier(var29);
        Identifier var4 = fieldVal;
        double var34 = ((double) (var22 - 0.6F));
        AttributeModifier.Operation var7 = AttributeModifier.Operation.ADD_VALUE;
        AttributeModifier var30 = new AttributeModifier(var4, var34, var7);
        var15.addPermanentModifier(var30);
    }

    private void applyStep1() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player == null) {
            return;
        }
        LocalPlayer var8 = var1.player;
        Holder var2 = Attributes.STEP_HEIGHT;
        AttributeInstance var9 = var8.getAttribute(var2);
        if (var9 != null) {
            Identifier var13 = fieldVal;
            boolean var12 = var9.removeModifier(var13);
        }
    }

    static {
        fieldVal = Identifier.fromNamespaceAndPath("clarity", "step_height");
    }

}
