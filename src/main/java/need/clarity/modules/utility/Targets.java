package need.clarity.modules.utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Stream;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.combat.AntiBot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public class Targets extends ModuleBase {
    private static Targets INSTANCE;
    private static final Map entriesMap;
    public static final int CONST_INT = 1;
    public static final int CONST_INT1 = 2;
    public static final int CONST_INT2 = 4;

    public static void applyStep(LivingEntity arg0) {
        Map var1 = entriesMap;
        Long var6 = System.currentTimeMillis();
        Object var5 = var1.put(arg0, var6);
    }

    public Targets() {
        super("Targets", "Utility");
        INSTANCE = this;
    }

    public boolean isEnabled() {
        return true;
    }

    public static boolean isEligible(LivingEntity arg0, Player arg1) {
        if (arg0 == arg1) {
            return false;
        }
        if (arg0.isRemoved()) {
            return false;
        }
        if (AntiBot.isEligible(arg0)) {
            return false;
        }
        if (isEligible("Don't Target Dead", true)) {
            if (Float.compare(arg0.getHealth(), 0.0F) <= 0) {
                return false;
            }
        }
        return isEligible(arg0);
    }

    public static boolean isEligible(LivingEntity arg0) {
        return (arg0 instanceof Player);
    }

    public static boolean isEligible(LivingEntity arg0, int arg1) {
        if ((arg0 instanceof Player)) {
            if ((arg1 & 1) != 0) {
                return true;
            }
        }
        if ((arg0 instanceof Monster)) {
            if ((arg1 & 2) != 0) {
                return true;
            }
        }
        if (!(arg0 instanceof Player)) {
            if (!(arg0 instanceof Monster)) {
                if (!(arg0 instanceof Animal)) {
                    if (!(arg0 instanceof AmbientCreature)) {
                        if ((arg0 instanceof Mob)) {
                        }
                    }
                    if ((arg1 & 4) != 0) {
                        return true;
                    }
                    return false;
                }
            }
        }
    }

    public static LivingEntity evaluate(List arg0, Player arg1) {
        int var8 = 0;
        LivingEntity var56 = null;
        if (arg0 != null) {
            if (arg0.isEmpty()) {
            }
        }
        return null;
        boolean var12 = isEligible("Don't Target Dead", true);
        ArrayList var13 = new ArrayList();
        Iterator var15 = arg0.iterator();
        while (true) {
            if (!var15.hasNext()) break;
            LivingEntity var48 = ((LivingEntity) var15.next());
            if (var12) {
                if (Float.compare(var48.getHealth(), 0.0F) <= 0) {
                }
            }
            boolean var54 = var13.add(var48);
        }
        if (var13.isEmpty()) {
            return null;
        }
        String var22 = resolveLabel("Mode", "Nearest");
        var8 = -1;
        switch (var22.hashCode()) {
            case -1396350199 -> {}
            case 1490826774 -> {}
            default -> {}
        }
        if (var22.equals("Lowest HP")) {
            var8 = 0;
            if (var22.equals("Last Hit")) {
                var8 = 1;
            }
            switch (var8) {
                case 0 -> {}
                case 1 -> {}
                default -> {}
            }
            Stream var40 = var13.stream();
            Comparator var67 = Comparator.comparingLong((p0) -> Targets.lambda$c(p0));
            var56 = ((LivingEntity) var40.max(var67).orElse(null));
            Stream var32 = var13.stream();
            Comparator var60 = Comparator.comparingDouble(LivingEntity::getHealth);
            var56 = ((LivingEntity) var32.min(var60).orElse(null));
            Stream var36 = var13.stream();
            Comparator var64 = Comparator.comparingDouble((p0) -> Targets.lambda$a(arg1, p0));
            var56 = ((LivingEntity) var36.min(var64).orElse(null));
            return var56;
        }
    }

    private static String resolveLabel(String arg0, String arg1) {
        if (INSTANCE == null) {
            return arg1;
        }
        return ((String) INSTANCE.getSetting(arg0, arg1));
    }

    private static boolean isEligible(String arg0, boolean arg1) {
        if (INSTANCE == null) {
            return arg1;
        }
        Targets var6 = INSTANCE;
        Boolean var10 = arg1;
        return ((Boolean) var6.getSetting(arg0, var10));
    }

    static {
        WeakHashMap var0 = new WeakHashMap();
        entriesMap = var0;
    }

}
