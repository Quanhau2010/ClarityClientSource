package need.clarity.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.utility.Targets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Piercing extends ModuleBase {
    private static Piercing INSTANCE;
    private static final double CONST_DBL = 3.0;

    public Piercing() {
        super("Piercing", "Combat");
        INSTANCE = this;
        Hooks.Bool0 var7 = () -> Piercing.lambda$c();
        Hooks.bool0("Piercing.enabled", var7);
        Hooks.Act1 var8 = (p0) -> Piercing.hook$enabled(p0);
        Hooks.act1("Piercing.handleInput", var8);
    }

    public static Piercing evaluate() {
        return INSTANCE;
    }

    public void applyStep(Minecraft arg0) {
        if (arg0.player != null) {
            if (arg0.level == null) {
            }
        }
        return;
        if (arg0.hitResult == null) {
            return;
        }
        PiercingRaySegment var15 = this.evaluate(arg0);
        if (arg0.options.keyAttack.isDown()) {
            PiercingTargetHit var39 = this.evaluate(arg0, var15);
            if (var39 == null) {
                return;
            }
            this.applyStep(arg0, var39);
            return;
        }
        Boolean var50 = false;
        if (((Boolean) this.getSetting("Interact", var50))) {
            if (arg0.options.keyUse.isDown()) {
                if (this.evaluate(arg0) != null) {
                    BlockHitResult var33 = this.findBlockHitResult(arg0);
                    HitResult.Type var35 = var33.getType();
                    HitResult.Type var45 = HitResult.Type.BLOCK;
                    if (var35 == var45) {
                        arg0.hitResult = var33;
                        return;
                    }
                }
            }
        }
        this.applyStep(arg0, var15);
    }

    public LivingEntity evaluate(Minecraft arg0) {
        LivingEntity var15 = null;
        if (arg0.player != null) {
            if (arg0.level == null) {
            }
        }
        return null;
        PiercingRaySegment var16 = this.evaluate(arg0);
        PiercingTargetHit var12 = this.evaluate(arg0, var16);
        if (var12 != null) {
            var15 = var12.target();
        } else {
            var15 = null;
        }
        return var15;
    }

    private PiercingRaySegment evaluate(Minecraft arg0) {
        LocalPlayer var13 = arg0.player;
        float var16 = arg0.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        Vec3 var18 = var13.getEyePosition(var16);
        double var20 = this.computeDoubleMetric(arg0);
        Vec3 var29 = var13.getViewVector(var16).scale(var20);
        Vec3 var23 = var18.add(var29);
        PiercingRaySegment var24 = new PiercingRaySegment(var13, var18, var23);
        return var24;
    }

    private double computeDoubleMetric(Minecraft arg0) {
        LocalPlayer var8 = arg0.player;
        double var10 = this.computeDoubleMetric(var8);
        double var16 = var8.blockInteractionRange();
        return Math.max(var10, var16);
    }

    private double computeDoubleMetric(Player arg0) {
        try {
            Holder var2 = Attributes.ENTITY_INTERACTION_RANGE;
            AttributeInstance var8 = arg0.getAttributes().getInstance(var2);
            if (var8 != null) {
                double var11 = var8.getValue();
            }
        } catch (Throwable ignored) {
        }
        return var11;
        return 3.0;
    }

    private double computeDoubleMetric(Minecraft arg0, PiercingRaySegment arg1) {
        LocalPlayer var42 = arg0.player;
        double var15 = this.computeDoubleMetric(var42);
        double var19 = (var15 * var15);
        Boolean var49 = true;
        if (((Boolean) this.getSetting("Blocks", var49))) {
            return var19;
        }
        BlockHitResult var29 = this.findBlockHitResult(arg0, arg1);
        HitResult.Type var31 = var29.getType();
        HitResult.Type var45 = HitResult.Type.MISS;
        if (var31 == var45) {
            return var19;
        }
        Vec3 var33 = arg1.start();
        Vec3 var47 = var29.getLocation();
        return Math.min(var33.distanceToSqr(var47), var19);
    }

    private PiercingTargetHit evaluate(Minecraft arg0, PiercingRaySegment arg1) {
        double var11 = this.computeDoubleMetric(arg0, arg1);
        List var14 = this.evaluate(arg0, arg1, var11);
        if (var14.isEmpty()) {
            return null;
        }
        return this.evaluate(var14);
    }

    private List evaluate(Minecraft arg0, PiercingRaySegment arg1, double arg2) {
        ArrayList var5 = new ArrayList();
        int var24 = this.computeColorOrIndex();
        AABB var27 = arg0.player.getBoundingBox();
        Vec3 var68 = arg1.end();
        Vec3 var85 = arg1.start();
        Vec3 var69 = var68.subtract(var85);
        AABB var29 = var27.expandTowards(var69).inflate(1.0, 1.0, 1.0);
        ClientLevel var31 = arg0.level;
        LocalPlayer var73 = arg0.player;
        Predicate var91 = EntitySelector.CAN_BE_PICKED;
        Iterator var33 = var31.getEntities(var73, var29, var91).iterator();
        while (true) {
            if (!var33.hasNext()) break;
            Entity var38 = ((Entity) var33.next());
            if (!(var38 instanceof LivingEntity)) continue;
            LivingEntity var42 = ((LivingEntity) var38);
            if (!(Targets.isEligible(var42, var24))) {
            }
            LocalPlayer var76 = arg0.player;
            if (!(Targets.isEligible(var42, var76))) {
            }
            AABB var48 = var38.getBoundingBox();
            double var79 = ((double) var38.getPickRadius());
            AABB var49 = var48.inflate(var79);
            Vec3 var82 = arg1.start();
            Vec3 var88 = arg1.end();
            Optional var51 = var49.clip(var82, var88);
            if (var51.isEmpty()) {
            }
            Vec3 var56 = ((Vec3) var51.get());
            if (Double.compare(arg1.start().distanceToSqr(var56), arg2) > 0) {
            }
            PiercingTargetHit var84 = new PiercingTargetHit(var42, var56);
            boolean var65 = var5.add(var84);
        }
        return var5;
    }

    private PiercingTargetHit evaluate(List arg0) {
        if (arg0.size() == 1) {
            return ((PiercingTargetHit) arg0.get(0));
        }
        String var9 = ((String) this.getSetting("Sort Targets By", "Hurt Time"));
        if ("Health".equals(var9)) {
            Comparator var26 = Comparator.comparingDouble((p0) -> Piercing.lambda$b(p0));
            arg0.sort(var26);
        } else {
            Comparator var23 = Comparator.comparingInt((p0) -> Piercing.lambda$a(p0));
            arg0.sort(var23);
        }
        return ((PiercingTargetHit) arg0.get(0));
    }

    private int computeColorOrIndex() {
        int var4 = 0;
        var4 = 0;
        Boolean var31 = true;
        if (((Boolean) this.getSetting("Players", var31))) {
            var4 = (var4 | 1);
        }
        Boolean var33 = false;
        if (((Boolean) this.getSetting("Hostile Mobs", var33))) {
            var4 = (var4 | 2);
        }
        Boolean var35 = false;
        if (((Boolean) this.getSetting("Passive Mobs", var35))) {
            var4 = (var4 | 4);
        }
        if (var4 == 0) {
            var4 = 1;
        }
        return var4;
    }

    private BlockHitResult findBlockHitResult(Minecraft arg0, PiercingRaySegment arg1) {
        ClientLevel var10 = arg0.level;
        Vec3 var12 = arg1.start();
        Vec3 var13 = arg1.end();
        ClipContext.Block var7 = ClipContext.Block.OUTLINE;
        ClipContext.Fluid var8 = ClipContext.Fluid.NONE;
        LocalPlayer var14 = arg0.player;
        ClipContext var4 = new ClipContext(var12, var13, var7, var8, var14);
        return var10.clip(var4);
    }

    private BlockHitResult findBlockHitResult(Minecraft arg0) {
        Vec3 var22 = arg0.player.getEyePosition();
        double var30 = Math.toRadians((((double) arg0.player.getYRot()) * -1.0));
        double var39 = Math.toRadians((((double) arg0.player.getXRot()) * -1.0));
        double var57 = Math.sin(var30);
        double var64 = Math.cos(var39);
        double var59 = (var57 * var64);
        double var68 = Math.sin(var39);
        double var72 = Math.cos(var30);
        double var77 = Math.cos(var39);
        double var74 = (var72 * var77);
        Vec3 var41 = new Vec3(var59, var68, var74);
        double var44 = arg0.player.blockInteractionRange();
        Vec3 var51 = var41.scale(var44);
        Vec3 var47 = var22.add(var51);
        LocalPlayer var71 = arg0.player;
        PiercingRaySegment var63 = new PiercingRaySegment(var71, var22, var47);
        return this.findBlockHitResult(arg0, var63);
    }

    private void applyStep(Minecraft arg0, PiercingTargetHit arg1) {
        LivingEntity var10 = arg1.target();
        Vec3 var11 = arg1.location();
        EntityHitResult var4 = new EntityHitResult(var10, var11);
        arg0.hitResult = var4;
        LivingEntity var9 = arg1.target();
        arg0.crosshairPickEntity = var9;
    }

    private void applyStep(Minecraft arg0, PiercingRaySegment arg1) {
        HitResult var9 = arg0.hitResult;
        if ((var9 instanceof EntityHitResult)) {
            EntityHitResult var13 = ((EntityHitResult) var9);
        } else {
            return;
        }
        Entity var15 = var13.getEntity();
        if ((var15 instanceof LivingEntity)) {
            LivingEntity var19 = ((LivingEntity) var15);
            LocalPlayer var39 = arg0.player;
            if (Targets.isEligible(var19, var39)) {
                return;
            }
        }
        BlockHitResult var23 = this.findBlockHitResult(arg0, arg1);
        double var26 = arg0.player.blockInteractionRange();
        double var48 = arg0.player.blockInteractionRange();
        double var28 = (var26 * var48);
        HitResult.Type var31 = var23.getType();
        HitResult.Type var41 = HitResult.Type.MISS;
        if (var31 != var41) {
            Vec3 var33 = var23.getLocation();
            Vec3 var43 = arg1.start();
            if (Double.compare(var33.distanceToSqr(var43), var28) <= 0) {
                arg0.hitResult = var23;
            }
            arg0.crosshairPickEntity = null;
            return;
        }
    }

    private InteractionHand evaluate(Minecraft arg0) {
        if ((arg0.player.getMainHandItem().getItem() instanceof BlockItem)) {
            return InteractionHand.MAIN_HAND;
        }
        if ((arg0.player.getOffhandItem().getItem() instanceof BlockItem)) {
            return InteractionHand.OFF_HAND;
        }
        return null;
    }

}
