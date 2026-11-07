package need.clarity.keybind;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import need.clarity.clickgui.ClickGuiLauncher;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.ModuleBase;
import need.clarity.modules.ModuleRegistry;

public class KeybindHandler {
    private static final Set heldKeys;

    public KeybindHandler() {
    }

    public static void onKeyEvent(int arg0, boolean arg1) {
        int var17 = 0;
        Map var2 = ClarityConfig.getKeybinds();
        if (arg1) {
            Set var15 = heldKeys;
            Integer var49 = arg0;
            if (!var15.contains(var49)) {
                var17 = 1;
            } else {
                var17 = 0;
            }
            int var7 = var17;
            if (arg1) {
                Set var47 = heldKeys;
                Integer var57 = arg0;
                boolean var48 = var47.add(var57);
            } else {
                Set var19 = heldKeys;
                Integer var51 = arg0;
                boolean var20 = var19.remove(var51);
            }
            if (var7 != 0) {
                if (arg0 == 344) {
                    try {
                        ClickGuiLauncher.onRightShift();
                    } catch (Throwable ignored) {
                    }
                }
                Iterator var25 = var2.entrySet().iterator();
                while (true) {
                    if (!var25.hasNext()) break;
                    Map.Entry var30 = ((Map.Entry) var25.next());
                    String var33 = ((String) var30.getKey());
                    String var36 = ((String) var30.getValue());
                    if (mouseButtonIndex(var36) >= 0) {
                    }
                    int var40 = glfwKeyCode(var36);
                    if (var40 == -1) {
                    }
                    if (var40 != arg0) {
                    }
                    String var44 = getBindMode(var33);
                    applyBind(var33, var44, arg1, var7);
                }
                return;
            }
        }
    }

    public static void onMouseEvent(int arg0, boolean arg1) {
        int var46 = 0;
        Map var2 = ClarityConfig.getKeybinds();
        int var16 = (arg0 + 10000);
        if (arg1) {
            Set var18 = heldKeys;
            Integer var48 = var16;
            if (!var18.contains(var48)) {
                var46 = 1;
            } else {
                var46 = 0;
            }
            int var8 = var46;
            if (arg1) {
                Set var44 = heldKeys;
                Integer var54 = var16;
                boolean var45 = var44.add(var54);
            } else {
                Set var21 = heldKeys;
                Integer var50 = var16;
                boolean var22 = var21.remove(var50);
            }
            Iterator var25 = var2.entrySet().iterator();
            while (true) {
                if (!var25.hasNext()) break;
                Map.Entry var30 = ((Map.Entry) var25.next());
                String var33 = ((String) var30.getKey());
                int var38 = mouseButtonIndex(((String) var30.getValue()));
                if (var38 < 0) {
                }
                if (var38 != arg0) {
                }
                String var42 = getBindMode(var33);
                applyBind(var33, var42, arg1, var8);
            }
            return;
        }
    }

    private static void applyBind(String arg0, String arg1, boolean arg2, boolean arg3) {
        int var21 = 0;
        if ("hold".equals(arg1)) {
            if (arg2) {
                if (arg3) {
                    applyEnabled(arg0, true);
                } else {
                }
            }
            if (!arg2) {
                applyEnabled(arg0, false);
            }
        } else {
            if (arg2) {
                if (arg3) {
                    if (!ClarityConfig.isEnabled(arg0)) {
                        var21 = 1;
                    } else {
                        var21 = 0;
                    }
                    applyEnabled(arg0, var21);
                }
                return;
            }
        }
    }

    private static void applyEnabled(String arg0, boolean arg1) {
        ModuleBase var5 = ModuleRegistry.get(arg0);
        if (var5 != null) {
            var5.setEnabled(arg1);
        } else {
            ClarityConfig.setModuleEnabled(arg0, arg1);
        }
    }

    private static String getBindMode(String arg0) {
        try {
            JsonObject var5 = ClarityConfig.getCurrent().getAsJsonObject(arg0);
            if (var5 == null) {
            }
        } catch (Exception ignored) {
        }
        return "toggle";
        try {
            JsonElement var8 = var5.get("bindMode");
            if (var8 != null) {
                if (var8.isJsonPrimitive()) {
                    String var13 = var8.getAsString();
                }
            }
        } catch (Exception ignored) {
        }
        return var13;
        return "toggle";
    }

    public static int mouseButtonIndex(String arg0) {
        int var4 = 0;
        int var12 = 0;
        var4 = -1;
        switch (arg0.hashCode()) {
            case -1688861649 -> {}
            case -1510354115 -> {}
            case -1387248807 -> {}
            case -1387248806 -> {}
            case -1387248805 -> {}
            case -1387248804 -> {}
            case -1387248803 -> {}
            case -248230332 -> {}
            default -> {}
        }
        if (arg0.equals("Mouse 7")) {
            var4 = 6;
            if (arg0.equals("Mouse 6")) {
                var4 = 5;
                if (arg0.equals("Mouse 4")) {
                    var4 = 3;
                    if (arg0.equals("Middle Click")) {
                        var4 = 2;
                        if (arg0.equals("Right Click")) {
                            var4 = 1;
                            if (arg0.equals("Left Click")) {
                                var4 = 0;
                                if (arg0.equals("Mouse 5")) {
                                    var4 = 4;
                                    if (arg0.equals("Mouse 8")) {
                                        var4 = 7;
                                    }
                                    switch (var4) {
                                        case 0 -> {}
                                        case 1 -> {}
                                        case 2 -> {}
                                        case 3 -> {}
                                        case 4 -> {}
                                        case 5 -> {}
                                        case 6 -> {}
                                        case 7 -> {}
                                        default -> {}
                                    }
                                    var12 = 3;
                                    var12 = 4;
                                    var12 = 2;
                                    var12 = 1;
                                    var12 = 6;
                                    var12 = 7;
                                    var12 = 0;
                                    var12 = 5;
                                    var12 = -1;
                                    return var12;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static int glfwKeyCode(String arg0) {
        int var17 = 0;
        int var130 = 0;
        int var4 = 0;
        if (arg0 != null) {
            if (arg0.isEmpty()) {
            }
        }
        return -1;
        if (arg0.length() == 1) {
            int var122 = Character.toUpperCase(arg0.charAt(0));
            if (var122 >= 65) {
                if (var122 <= 90) {
                    return var122;
                }
            }
        }
        var4 = -1;
        switch (arg0.hashCode()) {
            case -2099925287 -> {}
            case -1911885398 -> {}
            case -1678770883 -> {}
            case -937491361 -> {}
            case -336508495 -> {}
            case 2219 -> {}
            case 2220 -> {}
            case 2221 -> {}
            case 2222 -> {}
            case 2223 -> {}
            case 2224 -> {}
            case 2225 -> {}
            case 2226 -> {}
            case 2227 -> {}
            case 65929 -> {}
            case 68837 -> {}
            case 68838 -> {}
            case 68839 -> {}
            case 69819 -> {}
            case 83829 -> {}
            case 2255103 -> {}
            case 12763084 -> {}
            case 67114680 -> {}
            case 76887510 -> {}
            case 79854690 -> {}
            case 80085222 -> {}
            case 251549619 -> {}
            case 923631601 -> {}
            case 930625636 -> {}
            case 977535019 -> {}
            case 977763216 -> {}
            case 1318883673 -> {}
            case 2043376075 -> {}
            case 2059521464 -> {}
            case 2084662433 -> {}
            default -> {}
        }
        if (arg0.equals("F11")) {
            var4 = 33;
            if (arg0.equals("F7")) {
                var4 = 29;
                if (arg0.equals("NumLock")) {
                    var4 = 18;
                    if (arg0.equals("End")) {
                        var4 = 7;
                        if (arg0.equals("Backspace")) {
                            var4 = 3;
                            if (arg0.equals("Alt")) {
                                var4 = 16;
                                if (arg0.equals("Space")) {
                                    var4 = 22;
                                    if (arg0.equals("ArrowUp")) {
                                        var4 = 10;
                                        if (arg0.equals("PageDown")) {
                                            var4 = 9;
                                            if (arg0.equals("F9")) {
                                                var4 = 31;
                                                if (arg0.equals("Enter")) {
                                                    var4 = 1;
                                                    if (arg0.equals("F5")) {
                                                        var4 = 27;
                                                        if (arg0.equals("ArrowDown")) {
                                                            var4 = 11;
                                                            if (arg0.equals("F4")) {
                                                                var4 = 26;
                                                                if (arg0.equals("Pause")) {
                                                                    var4 = 21;
                                                                    if (arg0.equals("ArrowLeft")) {
                                                                        var4 = 12;
                                                                        if (arg0.equals("F3")) {
                                                                            var4 = 25;
                                                                            if (arg0.equals("ArrowRight")) {
                                                                                var4 = 13;
                                                                                if (arg0.equals("PrintScreen")) {
                                                                                    var4 = 20;
                                                                                    if (arg0.equals("F1")) {
                                                                                        var4 = 23;
                                                                                        if (arg0.equals("ScrollLock")) {
                                                                                            var4 = 19;
                                                                                            if (arg0.equals("Shift")) {
                                                                                                var4 = 14;
                                                                                                if (arg0.equals("PageUp")) {
                                                                                                    var4 = 8;
                                                                                                    if (arg0.equals("Control")) {
                                                                                                        var4 = 15;
                                                                                                        if (arg0.equals("F6")) {
                                                                                                            var4 = 28;
                                                                                                            if (arg0.equals("F8")) {
                                                                                                                var4 = 30;
                                                                                                                if (arg0.equals("Escape")) {
                                                                                                                    var4 = 0;
                                                                                                                    if (arg0.equals("CapsLock")) {
                                                                                                                        var4 = 17;
                                                                                                                        if (arg0.equals("F10")) {
                                                                                                                            var4 = 32;
                                                                                                                            if (arg0.equals("Delete")) {
                                                                                                                                var4 = 4;
                                                                                                                                if (arg0.equals("F2")) {
                                                                                                                                    var4 = 24;
                                                                                                                                    if (arg0.equals("Tab")) {
                                                                                                                                        var4 = 2;
                                                                                                                                        if (arg0.equals("Insert")) {
                                                                                                                                            var4 = 5;
                                                                                                                                            if (arg0.equals("Home")) {
                                                                                                                                                var4 = 6;
                                                                                                                                                if (arg0.equals("F12")) {
                                                                                                                                                    var4 = 34;
                                                                                                                                                }
                                                                                                                                                switch (var4) {
                                                                                                                                                    case 0 -> {}
                                                                                                                                                    case 1 -> {}
                                                                                                                                                    case 2 -> {}
                                                                                                                                                    case 3 -> {}
                                                                                                                                                    case 4 -> {}
                                                                                                                                                    case 5 -> {}
                                                                                                                                                    case 6 -> {}
                                                                                                                                                    case 7 -> {}
                                                                                                                                                    case 8 -> {}
                                                                                                                                                    case 9 -> {}
                                                                                                                                                    case 10 -> {}
                                                                                                                                                    case 11 -> {}
                                                                                                                                                    case 12 -> {}
                                                                                                                                                    case 13 -> {}
                                                                                                                                                    case 14 -> {}
                                                                                                                                                    case 15 -> {}
                                                                                                                                                    case 16 -> {}
                                                                                                                                                    case 17 -> {}
                                                                                                                                                    case 18 -> {}
                                                                                                                                                    case 19 -> {}
                                                                                                                                                    case 20 -> {}
                                                                                                                                                    case 21 -> {}
                                                                                                                                                    case 22 -> {}
                                                                                                                                                    case 23 -> {}
                                                                                                                                                    case 24 -> {}
                                                                                                                                                    case 25 -> {}
                                                                                                                                                    case 26 -> {}
                                                                                                                                                    case 27 -> {}
                                                                                                                                                    case 28 -> {}
                                                                                                                                                    case 29 -> {}
                                                                                                                                                    case 30 -> {}
                                                                                                                                                    case 31 -> {}
                                                                                                                                                    case 32 -> {}
                                                                                                                                                    case 33 -> {}
                                                                                                                                                    case 34 -> {}
                                                                                                                                                    default -> {}
                                                                                                                                                }
                                                                                                                                                var17 = 261;
                                                                                                                                                var17 = 266;
                                                                                                                                                var17 = 259;
                                                                                                                                                var17 = 32;
                                                                                                                                                var17 = 265;
                                                                                                                                                var17 = 281;
                                                                                                                                                var17 = 301;
                                                                                                                                                var17 = 280;
                                                                                                                                                var17 = 258;
                                                                                                                                                var17 = 257;
                                                                                                                                                var17 = 299;
                                                                                                                                                var17 = 340;
                                                                                                                                                var17 = 290;
                                                                                                                                                var17 = 296;
                                                                                                                                                var17 = 291;
                                                                                                                                                var17 = 292;
                                                                                                                                                var17 = 268;
                                                                                                                                                var17 = 269;
                                                                                                                                                var17 = 263;
                                                                                                                                                var17 = 298;
                                                                                                                                                var17 = 295;
                                                                                                                                                var17 = 300;
                                                                                                                                                var17 = 282;
                                                                                                                                                var17 = 256;
                                                                                                                                                var17 = 342;
                                                                                                                                                var17 = 294;
                                                                                                                                                var17 = 264;
                                                                                                                                                var17 = 262;
                                                                                                                                                var17 = 341;
                                                                                                                                                var17 = 267;
                                                                                                                                                var17 = 283;
                                                                                                                                                var17 = 260;
                                                                                                                                                var17 = 297;
                                                                                                                                                var17 = 293;
                                                                                                                                                var17 = 284;
                                                                                                                                                var17 = -1;
                                                                                                                                                return var17;
                                                                                                                                                if (var122 >= 48) {
                                                                                                                                                    if (var122 <= 57) {
                                                                                                                                                        return var122;
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                                switch (var122) {
                                                                                                                                                    case 32 -> {}
                                                                                                                                                    case 39 -> {}
                                                                                                                                                    case 44 -> {}
                                                                                                                                                    case 45 -> {}
                                                                                                                                                    case 46 -> {}
                                                                                                                                                    case 47 -> {}
                                                                                                                                                    case 59 -> {}
                                                                                                                                                    case 61 -> {}
                                                                                                                                                    case 91 -> {}
                                                                                                                                                    case 92 -> {}
                                                                                                                                                    case 93 -> {}
                                                                                                                                                    case 96 -> {}
                                                                                                                                                    default -> {}
                                                                                                                                                }
                                                                                                                                                var130 = 39;
                                                                                                                                                var130 = 44;
                                                                                                                                                var130 = 91;
                                                                                                                                                var130 = 59;
                                                                                                                                                var130 = 46;
                                                                                                                                                var130 = 47;
                                                                                                                                                var130 = 93;
                                                                                                                                                var130 = 45;
                                                                                                                                                var130 = 92;
                                                                                                                                                var130 = 61;
                                                                                                                                                var130 = 32;
                                                                                                                                                var130 = 96;
                                                                                                                                                var130 = -1;
                                                                                                                                                return var130;
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
        }
    }

    static {
        HashSet var0 = new HashSet();
        heldKeys = var0;
    }

}
