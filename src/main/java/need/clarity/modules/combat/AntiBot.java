package need.clarity.modules.combat;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.utility.FakePlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerSkin;

public class AntiBot extends ModuleBase {
    private static AntiBot INSTANCE;
    private static final Set entriesSet;

    public AntiBot() {
        super("Anti Bot", "Combat");
        INSTANCE = this;
    }

    public static boolean isEligible(LivingEntity arg0) {
        AntiBot var1 = INSTANCE;
        if (var1 != null) {
            if (!var1.isEnabled()) {
            }
        }
        return false;
        if ((arg0 instanceof Player)) {
            Player var17 = ((Player) arg0);
            LocalPlayer var80 = Minecraft.getInstance().player;
            if (arg0 == var80) {
                return false;
            }
            if (FakePlayer.isEligible(arg0)) {
                return false;
            }
            Set var23 = entriesSet;
            UUID var82 = arg0.getUUID();
            if (var23.contains(var82)) {
                return true;
            }
            Minecraft var25 = Minecraft.getInstance();
            if (var25.level != null) {
                if (var25.getConnection() == null) {
                }
            }
            return false;
            ClientPacketListener var31 = var25.getConnection();
            UUID var84 = var17.getUUID();
            PlayerInfo var32 = var31.getPlayerInfo(var84);
            Boolean var90 = true;
            if (((Boolean) var1.getSetting("Not in Tab List", var90))) {
                if (var32 == null) {
                    return true;
                }
                if (var32 != null) {
                    Boolean var92 = true;
                    if (((Boolean) var1.getSetting("Invalid Ping", var92))) {
                        if (var32.getLatency() == 0) {
                            return true;
                        }
                    }
                    Boolean var94 = false;
                    if (((Boolean) var1.getSetting("No Skin", var94))) {
                        try {
                            PlayerSkin var53 = var32.getSkin();
                            if (var53 != null) {
                                if (var53.body() == null) {
                                }
                            }
                        } catch (Exception ignored) {
                        }
                        return true;
                    }
                }
                Boolean var96 = true;
                if (((Boolean) var1.getSetting("Invalid UUID", var96))) {
                    if (((int) ((arg0.getUUID().getMostSignificantBits() >> 12) & 15L)) != 4) {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    public static void applyStep(UUID arg0) {
        if (!entriesSet.remove(arg0)) {
            boolean var5 = entriesSet.add(arg0);
        }
    }

    public static void applyStep() {
        entriesSet.clear();
    }

    public void onDisable() {
    }

    static {
        entriesSet = ConcurrentHashMap.newKeySet();
    }

}
