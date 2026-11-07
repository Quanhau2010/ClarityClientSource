package need.clarity.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public final class ClaritySounds {
    public static final Map HIT;
    public static final Map KILL;
    private static final Map CACHE;

    private ClaritySounds() {
    }

    private static SoundEvent event(String arg0) {
        Map var1 = CACHE;
        Function var3 = (p0) -> ClaritySounds.lambda$event$0(p0);
        return ((SoundEvent) var1.computeIfAbsent(arg0, var3));
    }

    public static SoundEvent raw(String arg0) {
        SoundEvent var3 = null;
        if (arg0 == null) {
            var3 = null;
        } else {
            var3 = event(arg0);
        }
        return var3;
    }

    public static SoundEvent hit(String arg0) {
        String var6 = null;
        SoundEvent var10 = null;
        if (arg0 == null) {
            var6 = null;
        } else {
            var6 = ((String) HIT.get(arg0));
        }
        String var3 = var6;
        if (var3 == null) {
            var10 = null;
        } else {
            var10 = event("hit." + (var3));
        }
        return var10;
    }

    public static SoundEvent kill(String arg0) {
        String var9 = null;
        SoundEvent var10 = null;
        if (arg0 == null) {
            var9 = null;
        } else {
            var9 = ((String) KILL.get(arg0));
        }
        String var3 = var9;
        if (var3 == null) {
            var10 = null;
        } else {
            var10 = event("kill." + (var3));
        }
        return var10;
    }

    public static String soundPath(Packet arg0) {
        SoundEvent var3 = null;
        String var21 = null;
        try {
            var3 = null;
            if ((arg0 instanceof ClientboundSoundPacket)) {
                var3 = ((SoundEvent) ((ClientboundSoundPacket) arg0).getSound().value());
            } else {
                if ((arg0 instanceof ClientboundSoundEntityPacket)) {
                    var3 = ((SoundEvent) ((ClientboundSoundEntityPacket) arg0).getSound().value());
                }
                if (var3 == null) {
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
        try {
            Registry var17 = BuiltInRegistries.SOUND_EVENT;
            SoundEvent var2 = var3;
            Identifier var18 = var17.getKey(var2);
            if (var18 == null) {
                var21 = null;
            } else {
                var21 = var18.getPath();
            }
            return var21;
        } catch (Throwable ignored) {
        }
    }

    static {
        LinkedHashMap var0 = new LinkedHashMap();
        HIT = var0;
        LinkedHashMap var2 = new LinkedHashMap();
        KILL = var2;
        Object var4 = HIT.put("Anime", "anime");
        Object var6 = HIT.put("Blade Ball", "bladeball");
        Object var8 = HIT.put("Chink", "chink");
        Object var10 = HIT.put("Click", "click");
        Object var12 = HIT.put("Copper Bell", "copperbell");
        Object var14 = HIT.put("Crystal", "crystal");
        Object var16 = HIT.put("Glass 1", "glass1");
        Object var18 = HIT.put("Glass 2", "glass2");
        Object var20 = HIT.put("Metal Bat", "metalbat");
        Object var22 = HIT.put("Neverlose", "neverlose");
        Object var24 = HIT.put("Punch", "punch");
        Object var26 = HIT.put("Slice", "slice");
        Object var28 = HIT.put("Tap", "tap");
        Object var30 = HIT.put("Water", "water");
        Object var32 = KILL.put("Critical Hit", "criticalhit");
        Object var34 = KILL.put("Dead", "dead");
        Object var36 = KILL.put("Gong", "gong");
        Object var38 = KILL.put("Headshot", "headshot");
        Object var40 = KILL.put("Kill Pop", "killpop");
        Object var42 = KILL.put("Phase Out", "phaseout");
        Object var44 = KILL.put("Re:Zero", "rezero");
        Object var46 = KILL.put("Roblox", "roblox");
        Object var48 = KILL.put("Shatter", "shatter");
        Object var50 = KILL.put("Super Smash Bros", "supersmashbros");
        Object var52 = KILL.put("TF2 Killstreak", "tf2killstreak");
        Object var54 = KILL.put("Undertale", "undertale");
        Object var56 = KILL.put("Valorant", "valorant");
        HashMap var57 = new HashMap();
        CACHE = var57;
    }

}
