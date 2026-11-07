package need.clarity.modules.utility;

import java.util.Random;
import need.clarity.mixin.KeyMappingAccessor;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RightClicker extends ModuleBase {
    private final Random fieldVal;
    private long timestampMs;
    private long timestampMs1;
    private long timestampMs2;
    private boolean enabledState;

    public RightClicker() {
        super("Right Clicker", "utility");
        Random var9 = new Random();
        this.fieldVal = var9;
        this.timestampMs = 0L;
        this.timestampMs1 = 0L;
        this.timestampMs2 = 0L;
        this.enabledState = false;
    }

    public void onTick() {
        long var10 = 0L;
        Minecraft var1 = Minecraft.getInstance();
        if (var1.player != null) {
            if (var1.level == null) {
            }
        }
        return;
        if (!var1.options.keyUse.isDown()) {
            this.enabledState = false;
            this.timestampMs1 = 0L;
            this.timestampMs = 0L;
            return;
        }
        if (!this.isEligible(var1)) {
            this.enabledState = false;
            this.timestampMs1 = 0L;
            this.timestampMs = 0L;
            return;
        }
        long var23 = System.nanoTime();
        if (!this.enabledState) {
            this.enabledState = true;
            this.timestampMs = var23;
            this.timestampMs1 = 0L;
            long var61 = this.evaluate();
            this.timestampMs2 = var61;
            return;
        }
        long var87 = this.timestampMs;
        var10 = (var23 - var87);
        this.timestampMs = var23;
        if (Long.compare(var10, 200000000L) > 0) {
            var10 = 50000000L;
        }
        long var65 = this.timestampMs1;
        long var4 = var10;
        long var67 = (var65 + var4);
        this.timestampMs1 = var67;
        while (true) {
            long var43 = this.timestampMs1;
            long var92 = this.timestampMs2;
            if (Long.compare(var43, var92) < 0) break;
            this.applyStep(var1);
            long var70 = this.timestampMs1;
            long var95 = this.timestampMs2;
            long var72 = (var70 - var95);
            this.timestampMs1 = var72;
            long var75 = this.evaluate();
            this.timestampMs2 = var75;
        }
    }

    private void applyStep(Minecraft arg0) {
        KeyMappingAccessor var8 = ((KeyMappingAccessor) arg0.options.keyUse);
        int var11 = (var8.getClickCount() + 1);
        var8.setClickCount(var11);
    }

    private boolean isEligible(Minecraft arg0) {
        Boolean var41 = false;
        boolean var11 = ((Boolean) this.getSetting("All", var41));
        Boolean var43 = true;
        boolean var15 = ((Boolean) this.getSetting("Blocks", var43));
        Boolean var45 = true;
        boolean var19 = ((Boolean) this.getSetting("Experience Bottles", var45));
        if (var11) {
            return true;
        }
        ItemStack var24 = arg0.player.getMainHandItem();
        if (var24.isEmpty()) {
            return false;
        }
        if (var15) {
            if ((var24.getItem() instanceof BlockItem)) {
                return true;
            }
        }
        if (var19) {
            Item var40 = Items.EXPERIENCE_BOTTLE;
            if (var24.is(var40)) {
                return true;
            }
        }
        return false;
    }

    private long evaluate() {
        int var32 = 0;
        int var5 = 0;
        int var6 = 0;
        Integer var33 = 15;
        var5 = ((Integer) this.getSetting("CPS Low", var33));
        Integer var35 = 18;
        var6 = ((Integer) this.getSetting("CPS High", var35));
        if (var5 <= 0) {
            var5 = 1;
        }
        if (var6 <= 0) {
            var6 = 1;
        }
        int var19 = var6;
        int var28 = var5;
        if (var19 < var28) {
            var6 = var5;
        }
        int var21 = var5;
        int var29 = var6;
        int var36 = var5;
        if (var29 > var36) {
            Random var31 = this.fieldVal;
            int var37 = var6;
            int var4 = var5;
            int var39 = ((var37 - var4) + 1);
            var32 = var31.nextInt(var39);
        } else {
            var32 = 0;
        }
        long var41 = ((long) (var21 + var32));
        return (1000000000L / var41);
    }

}
