package need.clarity.modules.visual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import need.clarity.EventStartAttack;
import need.clarity.PerAttackEvents;
import need.clarity.PerPacketEvents;
import need.clarity.event.EventPacketReceive;
import need.clarity.modules.ModuleBase;
import need.clarity.util.ClaritySounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class KillSounds extends ModuleBase {
    public static KillSounds INSTANCE;
    private static final long timestampMs = 6000000000L;
    private final Random fieldVal;
    private final Map entriesMap;
    private final Map entriesMap1;

    public KillSounds() {
        super("Kill Sounds", "Visual");
        Random var12 = new Random();
        this.fieldVal = var12;
        HashMap var13 = new HashMap();
        this.entriesMap = var13;
        ConcurrentHashMap var14 = new ConcurrentHashMap();
        this.entriesMap1 = var14;
        INSTANCE = this;
        PerAttackEvents.register((p0) -> this.onStartAttack(p0));
        PerPacketEvents.register((p0) -> this.onPacketReceive(p0));
    }

    public void onDisable() {
        this.entriesMap.clear();
        this.entriesMap1.clear();
    }

    private void onStartAttack(EventStartAttack arg0) {
        if (!this.isEnabled()) {
            return;
        }
        Entity var9 = arg0.getTarget();
        if (var9 == null) {
            return;
        }
        Map var12 = this.entriesMap1;
        Integer var15 = var9.getId();
        Long var16 = System.nanoTime();
        Object var13 = var12.put(var15, var16);
    }

    private boolean isEligible() {
        long var1 = System.nanoTime();
        Iterator var13 = this.entriesMap1.values().iterator();
        while (true) {
            if (!var13.hasNext()) break;
            long var20 = ((Long) var13.next());
            if (Long.compare((var1 - var20), 6000000000L) > 0) continue;
        }
        return true;
        return false;
    }

    private void onPacketReceive(EventPacketReceive arg0) {
        if (this.isEnabled()) {
            Boolean var26 = true;
            if (!((Boolean) this.getSetting("Mute Vanilla Sound", var26))) {
            }
        }
        return;
        String var13 = ClaritySounds.soundPath(arg0.getPacket());
        if (var13 != null) {
            if (!var13.endsWith(".death")) {
            }
        }
        return;
        Boolean var28 = true;
        if (((Boolean) this.getSetting("Only My Kills", var28))) {
            if (!this.isEligible()) {
                return;
            }
        }
        arg0.cancel();
    }

    public void onTick() {
        Minecraft var1 = Minecraft.getInstance();
        if (var1 != null) {
            if (var1.player != null) {
                if (var1.level == null) {
                }
            }
            return;
            long var23 = System.nanoTime();
            Collection var27 = this.entriesMap1.values();
            Predicate var97 = (p0) -> KillSounds.lambda$a(var23, p0);
            boolean var28 = var27.removeIf(var97);
            Set var31 = this.entriesMap.keySet();
            Predicate var99 = (p0) -> KillSounds.lambda$a(var1, p0);
            boolean var32 = var31.removeIf(var99);
            Boolean var115 = true;
            boolean var36 = ((Boolean) this.getSetting("Only My Kills", var115));
            Boolean var117 = false;
            boolean var40 = ((Boolean) this.getSetting("Only Players", var117));
            Float var119 = 0.0F;
            double var45 = ((double) ((Float) this.getSetting("Max Distance", var119)));
            Iterator var50 = var1.level.entitiesForRendering().iterator();
            while (true) {
                if (!var50.hasNext()) break;
                Entity var55 = ((Entity) var50.next());
                if (!(var55 instanceof LivingEntity)) continue;
                LivingEntity var59 = ((LivingEntity) var55);
                LocalPlayer var104 = var1.player;
                if (var55 == var104) {
                }
                float var62 = var59.getHealth();
                Map var64 = this.entriesMap;
                Integer var107 = var59.getId();
                Float var121 = var62;
                Float var66 = ((Float) var64.put(var107, var121));
                if (var66 == null) continue;
                if (Float.compare(var66, 0.0F) <= 0) continue;
                if (Float.compare(var62, 0.0F) > 0) {
                }
                if (var40) {
                    if (!(var59 instanceof Player)) {
                    }
                }
                if (var36) {
                    Map var78 = this.entriesMap1;
                    Integer var112 = var59.getId();
                    Long var80 = ((Long) var78.get(var112));
                    if (var80 == null) continue;
                    long var123 = var80;
                    if (Long.compare((var23 - var123), 6000000000L) > 0) {
                    }
                }
                if (Double.compare(var45, 0.5) > 0) {
                    if (Double.compare(((double) var1.player.distanceTo(var59)), var45) > 0) {
                    }
                }
                this.applyStep(var1);
            }
            return;
        }
    }

    private void applyStep(Minecraft arg0) {
        SoundEvent var59 = null;
        Boolean var50 = false;
        if (((Boolean) this.getSetting("Random Sound", var50))) {
            ArrayList var27 = new ArrayList();
            Iterator var30 = ClaritySounds.KILL.keySet().iterator();
            while (true) {
                if (!var30.hasNext()) break;
                SoundEvent var49 = ClaritySounds.kill(((String) var30.next()));
                boolean var40 = var27.add(var49);
            }
            Random var46 = this.fieldVal;
            int var58 = var27.size();
            int var47 = var46.nextInt(var58);
            var59 = ((SoundEvent) var27.get(var47));
        } else {
            var59 = ClaritySounds.kill(((String) this.getSetting("Sound", "Headshot")));
        }
        if (var59 == null) {
            return;
        }
        Float var53 = 1.0F;
        float var20 = ((Float) this.getSetting("Volume", var53));
        Float var55 = 1.0F;
        float var24 = ((Float) this.getSetting("Pitch", var55));
        LocalPlayer var26 = arg0.player;
        SoundEvent var44 = var59;
        var26.playSound(var44, var20, var24);
    }

}
