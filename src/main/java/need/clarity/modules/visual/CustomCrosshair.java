package need.clarity.modules.visual;

import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class CustomCrosshair extends ModuleBase {
    public static CustomCrosshair INSTANCE;
    private long timestampMs;
    private long timestampMs1;
    private LivingEntity targetEntity;
    private long timestampMs2;

    public CustomCrosshair() {
        super("Custom Crosshair", "Visual");
        this.timestampMs = -1L;
        this.timestampMs1 = -1L;
        this.timestampMs2 = 0L;
        INSTANCE = this;
        PerAttackEvents.register((p0) -> this.onStartAttack(p0));
    }

    public static boolean isEligible() {
        int var5 = 0;
        CustomCrosshair var0 = INSTANCE;
        if (var0 != null) {
            if (var0.isEnabled()) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            return var5;
        }
    }

    private void onStartAttack(EventStartAttack arg0) {
        if (this.isEnabled()) {
            Entity var9 = arg0.getTarget();
            if ((var9 instanceof LivingEntity)) {
                LivingEntity var13 = ((LivingEntity) var9);
                if (!var13.isAlive()) {
                }
            }
            return;
            Boolean var31 = true;
            if (((Boolean) this.getSetting("Players Only", var31))) {
                if (!(var13 instanceof Player)) {
                    return;
                }
            }
            long var25 = System.nanoTime();
            this.timestampMs = var25;
            this.targetEntity = var13;
            long var29 = this.timestampMs;
            this.timestampMs2 = var29;
            return;
        }
    }

    public float computeFloatMetric(long arg0) {
        float var36 = 0.0F;
        LivingEntity var8 = this.targetEntity;
        if (var8 != null) {
            long var47 = this.timestampMs2;
            if (Long.compare((arg0 - var47), 1500000000L) > 0) {
                this.targetEntity = null;
            } else {
                if (!var8.isDeadOrDying()) {
                    if (var8.isRemoved()) {
                        if (var8.getRemovalReason() != null) {
                            if (var8.getRemovalReason().shouldDestroy()) {
                            }
                        }
                    }
                }
                Boolean var52 = true;
                if (((Boolean) this.getSetting("Kill Effect", var52))) {
                    this.timestampMs1 = arg0;
                }
                this.targetEntity = null;
            }
            if (Long.compare(this.timestampMs, 0L) < 0) {
                var36 = -1.0F;
            } else {
                long var56 = this.timestampMs;
                float var45 = (((float) (arg0 - var56)) / 1.0E9F);
                var36 = Math.min(5.0F, var45);
            }
            return var36;
        }
    }

    public float computeFloatMetric1(long arg0) {
        float var12 = 0.0F;
        if (Long.compare(this.timestampMs1, 0L) < 0) {
            var12 = -1.0F;
        } else {
            long var19 = this.timestampMs1;
            float var17 = (((float) (arg0 - var19)) / 1.0E9F);
            var12 = Math.min(5.0F, var17);
        }
        return var12;
    }

    public String getStyle() {
        return ((String) this.getSetting("Style", "Cross"));
    }

    public String getColorMode() {
        return ((String) this.getSetting("Color Mode", "Custom"));
    }

    public String getColor() {
        return ((String) this.getSetting("Color", "#FFFFFF"));
    }

    public String getSecondColour() {
        return ((String) this.getSetting("Second Colour", "#7A8CFF"));
    }

    public String getHitColor() {
        return ((String) this.getSetting("Hit Color", "#FF3B3B"));
    }

    public String getTargetColour() {
        return ((String) this.getSetting("Target Colour", "#FF5050"));
    }

    public float getSize() {
        Float var7 = 6.0F;
        return ((Float) this.getSetting("Size", var7));
    }

    public float getGap() {
        Float var7 = 3.0F;
        return ((Float) this.getSetting("Gap", var7));
    }

    public float getThickness() {
        Float var7 = 2.0F;
        return ((Float) this.getSetting("Thickness", var7));
    }

    public float getDotSize() {
        Float var7 = 3.0F;
        return ((Float) this.getSetting("Dot Size", var7));
    }

    public float getRotation() {
        Float var7 = 0.0F;
        return ((Float) this.getSetting("Rotation", var7));
    }

    public float getSpin() {
        Float var7 = 0.0F;
        return ((Float) this.getSetting("Spin", var7));
    }

    public boolean isOutline() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Outline", var7));
    }

    public float getOutlineThickness() {
        Float var7 = 1.0F;
        return ((Float) this.getSetting("Outline Thickness", var7));
    }

    public float getGlow() {
        Float var7 = 0.6F;
        return ((Float) this.getSetting("Glow", var7));
    }

    public boolean isDynamicSpread() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Dynamic Spread", var7));
    }

    public boolean isHitmarker() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Hitmarker", var7));
    }

    public boolean isHitExpand() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Hit Expand", var7));
    }

    public boolean isHitFlash() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Hit Flash", var7));
    }

    public boolean isShockwave() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Shockwave", var7));
    }

    public boolean isCooldownRing() {
        Boolean var7 = true;
        return ((Boolean) this.getSetting("Cooldown Ring", var7));
    }

    public boolean isTargetColor() {
        Boolean var7 = false;
        return ((Boolean) this.getSetting("Target Color", var7));
    }

    public static Minecraft evaluate() {
        return Minecraft.getInstance();
    }

}
