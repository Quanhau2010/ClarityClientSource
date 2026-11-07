package need.clarity.modules.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import need.clarity.api.Hooks;
import need.clarity.modules.ModuleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.network.chat.contents.TranslatableContents;

public class NameProtect extends ModuleBase {
    private static NameProtect INSTANCE;

    public NameProtect() {
        super("Name Protect", "Utility");
        INSTANCE = this;
        Hooks.Bool0 var9 = () -> NameProtect.lambda$e();
        Hooks.bool0("NameProtect.enabled", var9);
        Hooks.Obj1 var10 = (p0) -> NameProtect.hook$enabled(p0);
        Hooks.obj1("NameProtect.replaceInTree", var10);
        Hooks.Obj1 var11 = (p0) -> NameProtect.hook$replaceInTree(p0);
        Hooks.obj1("NameProtect.maybeReplace", var11);
        Hooks.Obj0 var12 = () -> NameProtect.maybeReplace();
        Hooks.obj0("NameProtect.spoofName", var12);
    }

    public static NameProtect evaluate() {
        return INSTANCE;
    }

    public String getSpoofName() {
        String var6 = ((String) this.getSetting("Spoof Name", "Player"));
        if (var6 != null) {
            if (var6.isBlank()) {
            }
        }
        return "Player";
        return var6;
    }

    private static String resolveLabel() {
        Minecraft var0 = Minecraft.getInstance();
        if (var0.player != null) {
            return var0.player.getGameProfile().name();
        }
        if (var0.getUser() != null) {
            return var0.getUser().getName();
        }
        return null;
    }

    public static boolean isEligible(String arg0) {
        int var8 = 0;
        if (arg0 == null) {
            return false;
        }
        String var4 = resolveLabel();
        if (var4 != null) {
            if (arg0.equals(var4)) {
                var8 = 1;
            } else {
                var8 = 0;
            }
            return var8;
        }
    }

    public static String resolveLabel(String arg0) {
        String var17 = null;
        if (arg0 == null) {
            return null;
        }
        String var7 = resolveLabel();
        if (var7 != null) {
            if (!arg0.contains(var7)) {
            }
        }
        return arg0;
        NameProtect var12 = INSTANCE;
        if (var12 != null) {
            if (var12.isEnabled()) {
                var17 = var12.getSpoofName();
            } else {
                var17 = var7;
            }
            String var6 = var17;
            return arg0.replace(var7, var6);
        }
    }

    public static Component evaluate(Component arg0) {
        if (arg0 == null) {
            return null;
        }
        NameProtect var7 = INSTANCE;
        if (var7 != null) {
            if (!var7.isEnabled()) {
            }
        }
        return arg0;
        String var11 = resolveLabel();
        if (var11 == null) {
            return arg0;
        }
        String var14 = var7.getSpoofName();
        return evaluate(arg0, var11, var14);
    }

    private static Component evaluate(Component arg0, String arg1, String arg2) {
        List var9 = null;
        int var10 = 0;
        ComponentContents var11 = null;
        ArrayList var13 = null;
        List var153 = null;
        int var156 = 0;
        Object[] var161 = null;
        int var162 = 0;
        ComponentContents var21 = arg0.getContents();
        var9 = arg0.getSiblings();
        var10 = 0;
        var11 = var21;
        if ((var21 instanceof PlainTextContents.LiteralContents)) {
            String var31 = ((PlainTextContents.LiteralContents) var21).text();
            if (var31.contains(arg1)) {
                String var134 = var31.replace(arg1, arg2);
                PlainTextContents.LiteralContents var34 = new PlainTextContents.LiteralContents(var134);
                var11 = var34;
                var10 = 1;
            }
        } else {
            if ((var21 instanceof TranslatableContents)) {
                TranslatableContents var81 = ((TranslatableContents) var21);
                Object[] var83 = var81.getArgs();
                var161 = null;
                var162 = 0;
                while (true) {
                    int var86 = var162;
                    int var127 = var83.length;
                    if (var86 >= var127) break;
                    int var128 = var162;
                    Object var90 = var83[var128];
                    if ((var90 instanceof Component)) {
                        Component var106 = ((Component) var90);
                        Component var108 = evaluate(var106, arg1, arg2);
                        if (var108 != var106) {
                            if (var161 == null) {
                                var161 = ((Object[]) var83.clone());
                            }
                            Object[] var114 = var161;
                            int var133 = var162;
                            var114[var133] = var108;
                            var10 = 1;
                        }
                    } else {
                        if ((var90 instanceof String)) {
                            String var96 = ((String) var90);
                            if (var96.contains(arg1)) {
                                if (var161 == null) {
                                    var161 = ((Object[]) var83.clone());
                                }
                                Object[] var103 = var161;
                                int var130 = var162;
                                String var143 = var96.replace(arg1, arg2);
                                var103[var130] = var143;
                                var10 = 1;
                            }
                            var162 = (var162 + 1);
                        }
                    }
                }
                if (var161 != null) {
                    String var141 = var81.getKey();
                    String var148 = var81.getFallback();
                    Object[] var151 = var161;
                    TranslatableContents var88 = new TranslatableContents(var141, var148, var151);
                    var11 = var88;
                }
            } else {
                String var73 = arg0.getString();
                if (var73 != null) {
                    if (var73.contains(arg1)) {
                        String var139 = var73.replace(arg1, arg2);
                        PlainTextContents.LiteralContents var77 = new PlainTextContents.LiteralContents(var139);
                        var11 = var77;
                        var10 = 1;
                        var9 = List.of();
                    }
                    var153 = var9;
                    if (!var9.isEmpty()) {
                        var13 = null;
                        var156 = 0;
                        while (true) {
                            int var41 = var156;
                            int var117 = var9.size();
                            if (var41 >= var117) break;
                            List var42 = var9;
                            int var118 = var156;
                            Component var44 = ((Component) var42.get(var118));
                            Component var46 = evaluate(var44, arg1, arg2);
                            if (var46 != var44) {
                                if (var13 == null) {
                                    List var136 = var9;
                                    ArrayList var49 = new ArrayList(var136);
                                    var13 = var49;
                                }
                                ArrayList var50 = var13;
                                int var121 = var156;
                                Object var51 = var50.set(var121, var46);
                                var10 = 1;
                            }
                            var156 = (var156 + 1);
                        }
                        if (var13 != null) {
                            var153 = var13;
                        }
                        if (var10 == 0) {
                            return arg0;
                        }
                        MutableComponent var57 = MutableComponent.create(var11);
                        Style var123 = arg0.getStyle();
                        MutableComponent var58 = var57.withStyle(var123);
                        Iterator var60 = var153.iterator();
                        while (true) {
                            if (!var60.hasNext()) break;
                            Component var66 = ((Component) var60.next());
                            MutableComponent var68 = var58.append(var66);
                        }
                        return var58;
                    }
                }
            }
        }
    }

}
