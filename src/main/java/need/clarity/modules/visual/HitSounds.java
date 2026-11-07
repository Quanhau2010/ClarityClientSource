package need.clarity.modules.visual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import need.clarity.PerPacketEvents;
import need.clarity.event.EventPacketReceive;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ClaritySounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HitSounds extends ModuleBase {
    private static final long timestampMs = 800000000L;
    private final Random fieldVal;
    private volatile long timestampMs1;

    public HitSounds() {
        super("Hit Sounds", "Visual");
        Random var10 = new Random();
        this.fieldVal = var10;
        this.timestampMs1 = 0L;
        PerAttackEvents.register((p0) -> this.onStartAttack(p0));
        PerPacketEvents.register((p0) -> this.onPacketReceive(p0));
    }

    private void onPacketReceive(EventPacketReceive arg0) {
        if (this.isEnabled()) {
            Boolean var22 = true;
            if (!((Boolean) this.getSetting("Mute Vanilla Sound", var22))) {
            }
        }
        return;
        long var11 = System.nanoTime();
        long var24 = this.timestampMs1;
        if (Long.compare(var11, var24) > 0) {
            return;
        }
        String var16 = ClaritySounds.soundPath(arg0.getPacket());
        if (var16 != null) {
            if (var16.startsWith("entity.player.attack")) {
                arg0.cancel();
            }
            return;
        }
    }

    private void onStartAttack(EventStartAttack arg0) {
        float var12 = 0.0F;
        SoundEvent var150 = null;
        if (!this.isEnabled()) {
            return;
        }
        Entity var18 = arg0.getTarget();
        if (var18 == null) {
            return;
        }
        Boolean var123 = false;
        if (((Boolean) this.getSetting("Only Players", var123))) {
            if (!(var18 instanceof Player)) {
                return;
            }
        }
        Boolean var125 = false;
        if (((Boolean) this.getSetting("Only Crit", var125))) {
            if (!this.isEligible(var18)) {
                return;
            }
        }
        Minecraft var32 = Minecraft.getInstance();
        if (var32.player == null) {
            return;
        }
        Boolean var127 = false;
        if (((Boolean) this.getSetting("Random Sound", var127))) {
            SoundEvent var142 = SoundEvents.PLAYER_ATTACK_CRIT;
            SoundEvent var149 = SoundEvents.PLAYER_ATTACK_STRONG;
            SoundEvent var7 = SoundEvents.PLAYER_ATTACK_WEAK;
            SoundEvent var8 = SoundEvents.PLAYER_ATTACK_SWEEP;
            List var143 = List.of(var142, var149, var7, var8);
            ArrayList var80 = new ArrayList(var143);
            Iterator var83 = ClaritySounds.HIT.keySet().iterator();
            while (true) {
                if (!var83.hasNext()) break;
                SoundEvent var119 = ClaritySounds.hit(((String) var83.next()));
                boolean var90 = var80.add(var119);
            }
            Random var121 = this.fieldVal;
            int var145 = var80.size();
            int var122 = var121.nextInt(var145);
            var150 = ((SoundEvent) var80.get(var122));
        } else {
            var150 = evaluate(((String) this.getSetting("Sound", "Attack Crit")));
        }
        if (var150 == null) {
            return;
        }
        Float var130 = 1.0F;
        float var47 = ((Float) this.getSetting("Volume", var130));
        Float var132 = 1.0F;
        var12 = ((Float) this.getSetting("Pitch", var132));
        Float var134 = 0.0F;
        float var55 = ((Float) this.getSetting("Pitch Random", var134));
        if (Float.compare(var55, 0.0F) > 0) {
            float var58 = var12;
            float var107 = (((this.fieldVal.nextFloat() - 0.5F) * 2.0F) * var55);
            var12 = (var58 + var107);
            if (Float.compare(var12, 0.5F) < 0) {
                var12 = 0.5F;
            }
            if (Float.compare(var12, 2.0F) > 0) {
                var12 = 2.0F;
            }
            long var112 = (System.nanoTime() + 800000000L);
            this.timestampMs1 = var112;
            LocalPlayer var68 = var32.player;
            SoundEvent var114 = var150;
            float var146 = var12;
            var68.playSound(var114, var47, var146);
            String var71 = ((String) this.getSetting("Second Sound", "None"));
            if (var71 != null) {
                if (!"None".equals(var71)) {
                    SoundEvent var76 = evaluate(var71);
                    if (var76 != null) {
                        LocalPlayer var79 = var32.player;
                        float var141 = (var47 * 0.6F);
                        float var148 = var12;
                        var79.playSound(var76, var141, var148);
                    }
                    return;
                }
            }
        }
    }

    private boolean isEligible(Entity arg0) {
        int var26 = 0;
        Minecraft var2 = Minecraft.getInstance();
        if (var2.player == null) {
            return false;
        }
        if (!(arg0 instanceof LivingEntity)) {
            return false;
        }
        if (Double.compare(var2.player.fallDistance, 0.0) > 0) {
            if (!var2.player.onGround()) {
                if (!var2.player.isInWater()) {
                    if (!var2.player.isPassenger()) {
                        var26 = 1;
                    } else {
                    }
                }
                var26 = 0;
                return var26;
            }
        }
    }

    public static SoundEvent evaluate(String arg0) {
        int var5 = 0;
        if (arg0 == null) {
            return SoundEvents.PLAYER_ATTACK_CRIT;
        }
        SoundEvent var7 = ClaritySounds.hit(arg0);
        if (var7 != null) {
            return var7;
        }
        var5 = -1;
        switch (arg0.hashCode()) {
            case -2067043081 -> {}
            case -828063980 -> {}
            case 2066723 -> {}
            case 97446767 -> {}
            case 175685887 -> {}
            case 632633315 -> {}
            case 942638258 -> {}
            case 943221328 -> {}
            case 1486349085 -> {}
            case 1540557928 -> {}
            default -> {}
        }
        if (arg0.equals("Orb Pickup")) {
            var5 = 4;
            if (arg0.equals("Totem Use")) {
                var5 = 6;
                if (arg0.equals("Level Up")) {
                    var5 = 5;
                    if (arg0.equals("Attack Sweep")) {
                        var5 = 2;
                        if (arg0.equals("Attack Knockback")) {
                            var5 = 3;
                            if (arg0.equals("Attack Strong")) {
                                var5 = 0;
                                if (arg0.equals("Anvil Land")) {
                                    var5 = 7;
                                    if (arg0.equals("Bell")) {
                                        var5 = 8;
                                        if (arg0.equals("Attack Weak")) {
                                            var5 = 1;
                                            if (arg0.equals("Attack Crit")) {
                                                var5 = 9;
                                            }
                                            switch (var5) {
                                                case 0 -> {}
                                                case 1 -> {}
                                                case 2 -> {}
                                                case 3 -> {}
                                                case 4 -> {}
                                                case 5 -> {}
                                                case 6 -> {}
                                                case 7 -> {}
                                                case 8 -> {}
                                                default -> {}
                                            }
                                            return SoundEvents.EXPERIENCE_ORB_PICKUP;
                                            return SoundEvents.ANVIL_LAND;
                                            return SoundEvents.PLAYER_ATTACK_SWEEP;
                                            return SoundEvents.PLAYER_ATTACK_STRONG;
                                            return SoundEvents.PLAYER_ATTACK_WEAK;
                                            return SoundEvents.BELL_BLOCK;
                                            return SoundEvents.PLAYER_ATTACK_CRIT;
                                            return SoundEvents.PLAYER_ATTACK_KNOCKBACK;
                                            return SoundEvents.PLAYER_LEVELUP;
                                            return SoundEvents.TOTEM_USE;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
