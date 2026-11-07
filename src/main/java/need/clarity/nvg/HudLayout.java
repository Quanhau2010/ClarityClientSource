package need.clarity.nvg;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import need.clarity.api.Hooks;
import need.clarity.config.ClarityConfig;
import need.clarity.modules.visual.HUD;
import need.clarity.util.ThemeColors;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public final class HudLayout {
    public static final String NOTIFICATIONS = "Notifications";
    public static final String ARRAYLIST = "ArrayList";
    public static final String KEYBINDS = "Keybind List";
    public static final String MUSIC = "Music Player";
    public static final String TARGET = "Target Hud";
    public static final String INFO = "HUD";
    private static final float CENTER_BAND = 10.0F;
    private static final float GRID = 10.0F;
    private static final Map RECTS;
    private static long frame;
    private static float canvasW;
    private static float canvasH;
    private static boolean editing;
    private static String dragging;
    private static String hovered;
    private static float grabX;
    private static float grabY;
    private static boolean snappedCenter;
    private static boolean gridOn;
    private static String lastApplied;
    private static final int NVG_ALIGN_LEFT_MIDDLE = 17;
    private static final int NVG_ALIGN_CENTER_MIDDLE = 18;

    private HudLayout() {
    }

    public static boolean isEditing() {
        return editing;
    }

    public static void enter() {
        Minecraft var0 = Minecraft.getInstance();
        if (!editing) {
            if (var0 != null) {
                if (var0.player != null) {
                    if (var0.screen != null) {
                    }
                }
                return;
                editing = true;
                dragging = null;
                hovered = null;
                lastApplied = "";
                try {
                    KeyMapping.releaseAll();
                } catch (Throwable ignored) {
                }
                var0.mouseHandler.releaseMouse();
                return;
            }
        }
    }

    public static void toggle() {
        if (editing) {
            exit();
        } else {
            enter();
        }
    }

    public static void exit() {
        exit(true);
    }

    public static void exitNoGrab() {
        exit(false);
    }

    private static void exit(boolean arg0) {
        if (!editing) {
            return;
        }
        editing = false;
        dragging = null;
        hovered = null;
        RECTS.clear();
        Minecraft var7 = Minecraft.getInstance();
        if (arg0) {
            if (var7 != null) {
                if (var7.player != null) {
                    if (var7.screen == null) {
                        var7.mouseHandler.grabMouse();
                    }
                    return;
                }
            }
        }
    }

    public static void validate() {
        if (!editing) {
            return;
        }
        Minecraft var2 = Minecraft.getInstance();
        if (var2 != null) {
            if (var2.player != null) {
                if (var2.screen == null) {
                    if (var2.options.hideGui) {
                    }
                }
                exitNoGrab();
                return;
            }
        }
    }

    public static void beginFrame(float arg0, float arg1) {
        frame = (frame + 1L);
        canvasW = arg0;
        canvasH = arg1;
    }

    public static void report(String arg0, float arg1, float arg2, float arg3, float arg4) {
        if (editing) {
            if (Float.compare(arg3, 0.0F) > 0) {
                if (Float.compare(arg4, 0.0F) <= 0) {
                }
            }
            return;
            Map var13 = RECTS;
            Function var7 = (p0) -> HudLayout.lambda$report$0(p0);
            HudLayout.Rect var15 = ((HudLayout.Rect) var13.computeIfAbsent(arg0, var7));
            var15.x = arg1;
            var15.y = arg2;
            var15.w = arg3;
            var15.h = arg4;
            long var27 = frame;
            var15.frame = var27;
            return;
        }
    }

    public static void onMouseButton(int arg0, int arg1) {
        if (editing) {
            if (arg0 != 0) {
            }
        }
        return;
        if (arg1 == 1) {
            float[] var10 = cursor();
            if (var10 == null) {
                return;
            }
            float var13 = var10[0];
            float var32 = var10[1];
            String var14 = pick(var13, var32);
            if (var14 != null) {
                HudLayout.Rect var18 = ((HudLayout.Rect) RECTS.get(var14));
                dragging = var14;
                float var21 = var10[0];
                float var36 = var18.x;
                grabX = (var21 - var36);
                float var24 = var10[1];
                float var39 = var18.y;
                grabY = (var24 - var39);
                lastApplied = "";
            }
        } else {
            if (arg1 == 0) {
                dragging = null;
                snappedCenter = false;
            }
            return;
        }
    }

    private static boolean gridKeyHeld() {
        int var14 = 0;
        Minecraft var0 = Minecraft.getInstance();
        if (var0 == null) {
            return false;
        }
        try {
            if (GLFW.glfwGetKey(var0.getWindow().handle(), 341) == 1) {
                var14 = 1;
            } else {
                var14 = 0;
            }
            return var14;
        } catch (Throwable ignored) {
        }
    }

    private static float snapAxis(float arg0, float arg1, float arg2, float arg3) {
        float var27 = 0.0F;
        float var15 = (((float) Math.round((arg0 / arg3))) * arg3);
        float var22 = ((((float) Math.round(((arg0 + arg1) / arg3))) * arg3) - arg1);
        float var25 = Math.abs((var15 - arg0));
        float var38 = Math.abs((var22 - arg0));
        if (Float.compare(var25, var38) <= 0) {
            var27 = var15;
        } else {
            var27 = var22;
        }
        float var11 = var27;
        float var42 = (arg2 - arg1);
        float var41 = Math.max(0.0F, var42);
        return clamp(var11, 0.0F, var41);
    }

    private static float gridStep(String arg0) {
        if ("HUD".equals(arg0)) {
            if (HUD.INSTANCE != null) {
                float var9 = HUD.INSTANCE.getScale();
                return (10.0F * var9);
            }
        }
        return 10.0F;
    }

    private static float[] cursor() {
        Minecraft var0 = Minecraft.getInstance();
        if (var0 == null) {
            return null;
        }
        try {
            long var18 = var0.getWindow().handle();
            double[] var21 = new double[1];
            double[] var23 = new double[1];
            int[] var25 = new int[1];
            int[] var27 = new int[1];
            GLFW.glfwGetCursorPos(var18, var21, var23);
            GLFW.glfwGetWindowSize(var18, var25, var27);
            if (var25[0] > 0) {
                if (var27[0] <= 0) {
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
        try {
            float[] var37 = new float[2];
            double var47 = var21[0];
            double var64 = ((double) var25[0]);
            double var49 = (var47 / var64);
            double var67 = ((double) canvasW);
            float var53 = ((float) (var49 * var67));
            var37[0] = var53;
            double var55 = var23[0];
            double var71 = ((double) var27[0]);
            double var57 = (var55 / var71);
            double var74 = ((double) canvasH);
            float var61 = ((float) (var57 * var74));
            var37[1] = var61;
        } catch (Throwable ignored) {
        }
        return var37;
    }

    private static String pick(float arg0, float arg1) {
        String var6 = null;
        var6 = null;
        Iterator var12 = RECTS.entrySet().iterator();
        while (true) {
            if (var12.hasNext()) {
                Map.Entry var17 = ((Map.Entry) var12.next());
                HudLayout.Rect var20 = ((HudLayout.Rect) var17.getValue());
                long var22 = var20.frame;
                long var4 = frame;
                if (var22 != var4) {
                }
            }
            return var6;
            float var37 = var20.x;
            if (Float.compare(arg0, var37) < 0) continue;
            float var39 = var20.x;
            float var47 = var20.w;
            float var40 = (var39 + var47);
            if (Float.compare(arg0, var40) > 0) continue;
            float var42 = var20.y;
            if (Float.compare(arg1, var42) < 0) continue;
            float var44 = var20.y;
            float var49 = var20.h;
            float var45 = (var44 + var49);
            if (Float.compare(arg1, var45) > 0) continue;
            var6 = ((String) var17.getKey());
        }
    }

    public static void drawOverlay(long arg0, float arg1, float arg2) {
        float var18 = 0.0F;
        float var19 = 0.0F;
        float var36 = 0.0F;
        float var296 = 0.0F;
        float var345 = 0.0F;
        float var364 = 0.0F;
        float var380 = 0.0F;
        int var389 = 0;
        String var181 = null;
        float var182 = 0.0F;
        boolean var4 = editing;
        if (!var4) {
            return;
        }
        boolean var37 = gridKeyHeld();
        gridOn = var37;
        float[] var38 = cursor();
        String var39 = dragging;
        if (var39 != null) {
            if (var38 != null) {
                Map var41 = RECTS;
                String var5 = dragging;
                Object var42 = var41.get(var5);
                HudLayout.Rect var43 = ((HudLayout.Rect) var42);
                if (var43 != null) {
                    long var46 = var43.frame;
                    long var6 = frame;
                    int var48 = Long.compare(var46, var6);
                    if (var48 == 0) {
                        float var50 = var38[0];
                        float var184 = grabX;
                        float var51 = (var50 - var184);
                        float var288 = var43.w;
                        float var265 = (arg1 - var288);
                        float var233 = Math.max(0.0F, var265);
                        float var52 = clamp(var51, 0.0F, var233);
                        var18 = var52;
                        float var54 = var38[1];
                        float var187 = grabY;
                        float var55 = (var54 - var187);
                        float var290 = var43.h;
                        float var267 = (arg2 - var290);
                        float var235 = Math.max(0.0F, var267);
                        float var56 = clamp(var55, 0.0F, var235);
                        var19 = var56;
                        String var57 = dragging;
                        float var58 = gridStep(var57);
                        boolean var59 = gridOn;
                        if (var59) {
                            float var60 = var18;
                            float var190 = var43.w;
                            float var61 = snapAxis(var60, var190, arg1, var58);
                            var18 = var61;
                            float var62 = var19;
                            float var192 = var43.h;
                            float var63 = snapAxis(var62, var192, arg2, var58);
                            var19 = var63;
                        }
                        boolean var64 = gridOn;
                        if (var64) {
                            var182 = 5.0F;
                        } else {
                            var182 = 10.0F;
                        }
                        float var21 = var182;
                        snappedCenter = false;
                        float var66 = var18;
                        float var194 = var43.w;
                        float var195 = (var194 * 0.5F);
                        float var67 = (var66 + var195);
                        float var197 = (arg1 * 0.5F);
                        float var68 = (var67 - var197);
                        float var69 = Math.abs(var68);
                        int var70 = Float.compare(var69, var21);
                        if (var70 <= 0) {
                            float var200 = var43.w;
                            float var72 = (arg1 - var200);
                            float var73 = (var72 * 0.5F);
                            var18 = var73;
                            snappedCenter = true;
                        }
                        String var75 = dragging;
                        float var202 = var18;
                        float var240 = var19;
                        float var271 = var43.w;
                        float var292 = var43.h;
                        boolean var12 = snappedCenter;
                        apply(var75, var202, var240, var271, var292, arg1, arg2, var12);
                        float var203 = var18;
                        var43.x = var203;
                        float var204 = var19;
                        var43.y = var204;
                    }
                    String var78 = dragging;
                    if (var78 == null) {
                        if (var38 != null) {
                            float var81 = var38[0];
                            float var207 = var38[1];
                            var181 = pick(var81, var207);
                        } else {
                            var181 = null;
                        }
                        hovered = var181;
                        int var208 = ThemeColors.getAccentRgb();
                        int var83 = (-16777216 | var208);
                        int var386 = var83;
                        int var209 = ThemeColors.getSurfaceRgb();
                        int var85 = (-16777216 | var209);
                        int var387 = var85;
                        int var210 = ThemeColors.getTextRgb();
                        int var87 = (-16777216 | var210);
                        int var388 = var87;
                        ClarityDraw.fillRRect(arg0, 0.0F, 0.0F, arg1, arg2, 0.0F, 939524096);
                        boolean var90 = gridOn;
                        if (var90) {
                            String var294 = dragging;
                            if (var294 != null) {
                                String var295 = dragging;
                                var296 = gridStep(var295);
                            } else {
                                var296 = 10.0F;
                            }
                            drawGrid(arg0, arg1, arg2, var296, var386);
                        }
                        String var93 = dragging;
                        if (var93 != null) {
                            boolean var94 = snappedCenter;
                            if (var94) {
                                float var245 = (arg1 * 0.5F);
                                float var246 = (var245 - 0.5F);
                                int var336 = withAlpha(var386, 0.65F);
                                ClarityDraw.fillRRect(arg0, var246, 0.0F, 1.0F, arg2, 0.0F, var336);
                            }
                            var389 = 0;
                            Map var98 = RECTS;
                            Set var99 = var98.entrySet();
                            Iterator var100 = var99.iterator();
                            Iterator var390 = var100;
                            while (true) {
                                boolean var102 = var390.hasNext();
                                if (!var102) break;
                                Object var104 = var390.next();
                                Map.Entry var105 = ((Map.Entry) var104);
                                Object var107 = var105.getValue();
                                HudLayout.Rect var108 = ((HudLayout.Rect) var107);
                                long var110 = var108.frame;
                                long var247 = frame;
                                int var112 = Long.compare(var110, var247);
                                if (var112 != 0) {
                                }
                                var389 = (var389 + 1);
                                Object var114 = var105.getKey();
                                String var115 = ((String) var114);
                                String var211 = dragging;
                                boolean var117 = var115.equals(var211);
                                String var212 = hovered;
                                boolean var119 = var115.equals(var212);
                                float var122 = var108.x;
                                float var123 = (var122 - 2.0F);
                                float var125 = var108.y;
                                float var126 = (var125 - 2.0F);
                                float var128 = var108.w;
                                float var216 = (2.0F * 2.0F);
                                float var129 = (var128 + var216);
                                float var131 = var108.h;
                                float var218 = (2.0F * 2.0F);
                                float var132 = (var131 + var218);
                                if (var117) {
                                    var364 = 0.22F;
                                } else {
                                    if (var119) {
                                        var364 = 0.14F;
                                    } else {
                                        var364 = 0.07F;
                                    }
                                    int var338 = withAlpha(var386, var364);
                                    ClarityDraw.fillRRect(arg0, var123, var126, var129, var132, 6.0F, var338);
                                    if (var117) {
                                        var345 = 2.0F;
                                    } else {
                                        var345 = 1.0F;
                                    }
                                    if (var117) {
                                        var380 = 1.0F;
                                    } else {
                                        int var379 = var119;
                                        if (var379 != 0) {
                                            var380 = 0.9F;
                                        } else {
                                            var380 = 0.55F;
                                        }
                                        int var356 = withAlpha(var386, var380);
                                        ClarityDraw.strokeRRect(arg0, var123, var126, var129, var132, 6.0F, var345, var356);
                                        float var139 = ClarityDraw.textWidth(arg0, "inter-sb", 10.0F, 0.0F, var115);
                                        float var141 = (var139 + 12.0F);
                                        float var221 = (arg1 - var141);
                                        float var222 = (var221 - 2.0F);
                                        float var223 = Math.min(var222, var123);
                                        float var144 = Math.max(2.0F, var223);
                                        float var146 = (var126 - 15.0F);
                                        float var147 = (var146 - 3.0F);
                                        var36 = var147;
                                        float var148 = var36;
                                        int var149 = Float.compare(var148, 2.0F);
                                        if (var149 < 0) {
                                            float var151 = (var126 + var132);
                                            float var152 = (var151 + 3.0F);
                                            var36 = var152;
                                        }
                                        float var280 = var36;
                                        float var357 = 0.94F;
                                        int var341 = withAlpha(var387, var357);
                                        ClarityDraw.fillRRect(arg0, var144, var280, var141, 15.0F, 5.0F, var341);
                                        float var281 = var36;
                                        int var358 = var386;
                                        float var374 = 0.7F;
                                        int var359 = withAlpha(var358, var374);
                                        ClarityDraw.strokeRRect(arg0, var144, var281, var141, 15.0F, 5.0F, 1.0F, var359);
                                        float var360 = 6.0F;
                                        float var344 = (var144 + var360);
                                        float var361 = var36;
                                        float var375 = 15.0F;
                                        float var376 = (var375 * 0.5F);
                                        float var362 = (var361 + var376);
                                        float var377 = 0.5F;
                                        float var363 = (var362 + var377);
                                        String var378 = var115;
                                        float var159 = ClarityDraw.text(arg0, "inter-sb", 10.0F, 0.0F, var388, 17, var344, var363, var378);
                                    }
                                }
                            }
                            String var391 = "HUD Editor  drag elements to move them  hold Left Ctrl to snap to grid  Right Ctrl or Esc to finish";
                            float var163 = ClarityDraw.textWidth(arg0, "inter-med", 11.0F, 0.0F, var391);
                            float var164 = (var163 + 24.0F);
                            float var392 = var164;
                            float var166 = (arg1 - var392);
                            float var167 = (var166 * 0.5F);
                            float var393 = var167;
                            float var394 = 8.0F;
                            float var365 = 0.92F;
                            int var347 = withAlpha(var387, var365);
                            ClarityDraw.fillRRect(arg0, var393, var394, var392, 22.0F, 11.0F, var347);
                            int var366 = var386;
                            float var381 = 0.6F;
                            int var367 = withAlpha(var366, var381);
                            ClarityDraw.strokeRRect(arg0, var393, var394, var392, 22.0F, 11.0F, 1.0F, var367);
                            float var368 = 0.5F;
                            float var350 = (arg1 * var368);
                            float var369 = var394;
                            float var382 = 11.5F;
                            float var370 = (var369 + var382);
                            String var383 = var391;
                            float var175 = ClarityDraw.text(arg0, "inter-med", 11.0F, 0.0F, var388, 18, var350, var370, var383);
                            int var176 = var389;
                            if (var176 == 0) {
                                String var395 = "No HUD elements are enabled. Turn on Notifications, ArrayList, HUD, Keybind List, Music Player or Target Hud.";
                                float var371 = 0.5F;
                                float var352 = (arg1 * var371);
                                float var372 = arg2;
                                float var384 = 0.5F;
                                float var373 = (var372 * var384);
                                String var385 = var395;
                                float var180 = ClarityDraw.text(arg0, "inter-med", 12.0F, 0.0F, var388, 18, var352, var373, var385);
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    private static void drawGrid(long arg0, float arg1, float arg2, float arg3, int arg4) {
        int var17 = 0;
        float var18 = 0.0F;
        int var53 = 0;
        int var56 = 0;
        int var58 = 0;
        float var59 = 0.0F;
        int var19 = withAlpha(arg4, 0.12F);
        int var21 = withAlpha(arg4, 0.24F);
        var17 = 0;
        var18 = 0.0F;
        while (true) {
            if (Float.compare(var18, arg1) <= 0) {
                float var43 = (var18 - 0.5F);
                if ((var17 % 5) == 0) {
                    var53 = var21;
                }
            } else {
                var58 = 0;
                var59 = 0.0F;
                while (true) {
                    if (Float.compare(var59, arg2) > 0) break;
                    float var47 = (var59 - 0.5F);
                    if ((var58 % 5) == 0) {
                        var56 = var21;
                    } else {
                        var56 = var19;
                    }
                    ClarityDraw.fillRRect(arg0, 0.0F, var47, arg1, 1.0F, 0.0F, var56);
                    var59 = (var59 + arg3);
                    var58 = (var58 + 1);
                }
                return;
                var53 = var19;
            }
            ClarityDraw.fillRRect(arg0, var43, 0.0F, 1.0F, arg2, 0.0F, var53);
            var18 = (var18 + arg3);
            var17 = (var17 + 1);
        }
    }

    private static void apply(String arg0, float arg1, float arg2, float arg3, float arg4, float arg5, float arg6, boolean arg7) {
        int var21 = 0;
        float var32 = 0.0F;
        String var177 = null;
        String var203 = null;
        String var204 = null;
        String var205 = null;
        String var216 = null;
        String var222 = null;
        float var107 = 0.0F;
        String var236 = null;
        float var110 = 0.0F;
        int var111 = 0;
        int var112 = 0;
        float var113 = (arg3 * 0.5F);
        float var24 = (arg1 + var113);
        float var115 = (arg4 * 0.5F);
        float var26 = (arg2 + var115);
        float var117 = (arg5 * 0.5F);
        if (Float.compare(var24, var117) < 0) {
            var112 = 1;
        } else {
            var112 = 0;
        }
        int var15 = var112;
        float var119 = (arg6 * 0.5F);
        if (Float.compare(var26, var119) < 0) {
            var111 = 1;
        } else {
            var111 = 0;
        }
        int var16 = var111;
        if (var15 != 0) {
            var32 = arg1;
        } else {
            var32 = ((arg5 - arg1) - arg3);
        }
        float var17 = var32;
        if (var16 != 0) {
            var107 = arg2;
        } else {
            var107 = ((arg6 - arg2) - arg4);
        }
        float var18 = var107;
        LinkedHashMap var34 = new LinkedHashMap();
        var21 = -1;
        switch (arg0.hashCode()) {
            case -1816476184 -> {}
            case 71895 -> {}
            case 578806391 -> {}
            case 990084962 -> {}
            case 1099108732 -> {}
            case 2071315656 -> {}
            default -> {}
        }
        if (arg0.equals("Keybind List")) {
            var21 = 1;
            if (arg0.equals("ArrayList")) {
                var21 = 4;
                if (arg0.equals("Target Hud")) {
                    var21 = 3;
                    if (arg0.equals("Notifications")) {
                        var21 = 0;
                        if (arg0.equals("Music Player")) {
                            var21 = 2;
                            if (arg0.equals("HUD")) {
                                var21 = 5;
                            }
                            switch (var21) {
                                case 0 -> {}
                                case 1 -> {}
                                case 2 -> {}
                                case 3 -> {}
                                case 4 -> {}
                                case 5 -> {}
                                default -> {}
                            }
                            return;
                            if (var15 != 0) {
                                var177 = "Left";
                            } else {
                                var177 = "Right";
                            }
                            Object var62 = var34.put("Side", var177);
                            int var218 = Math.round(var17);
                            Integer var173 = Math.max(0, var218);
                            Object var64 = var34.put("Margin X", var173);
                            int var220 = Math.round(arg2);
                            Integer var176 = Math.max(0, var220);
                            Object var66 = var34.put("Margin Y", var176);
                            if (var16 != 0) {
                                var204 = "Top ";
                            } else {
                                var204 = "Bottom ";
                            }
                            if (arg7) {
                                var222 = "Center";
                            } else {
                                if (var15 != 0) {
                                    var222 = "Left";
                                } else {
                                    var222 = "Right";
                                }
                                String var179 = (var204) + (var222);
                                Object var68 = var34.put("Position", var179);
                                int var224 = Math.round(var17);
                                Integer var182 = Math.max(0, var224);
                                Object var70 = var34.put("X Offset", var182);
                                int var226 = Math.round(var18);
                                Integer var185 = Math.max(0, var226);
                                Object var72 = var34.put("Y Offset", var185);
                                if (var16 != 0) {
                                    var205 = "Top ";
                                } else {
                                    var205 = "Bottom ";
                                }
                                if (var15 != 0) {
                                    var216 = "Left";
                                } else {
                                    var216 = "Right";
                                }
                                String var163 = (var205) + (var216);
                                Object var56 = var34.put("Anchor", var163);
                                int var213 = Math.round(var17);
                                Integer var166 = Math.max(0, var213);
                                Object var58 = var34.put("X Offset", var166);
                                int var215 = Math.round(var18);
                                Integer var169 = Math.max(0, var215);
                                Object var60 = var34.put("Y Offset", var169);
                                if (var16 == 0) {
                                    if (arg7) {
                                        float var77 = HudElements.bottomCenterClear(Minecraft.getInstance(), arg5);
                                        Object var79 = var34.put("Anchor", "Bottom Center");
                                        Integer var188 = 0;
                                        Object var81 = var34.put("X", var188);
                                        Integer var194 = Math.round((((arg6 - arg4) - var77) - arg2));
                                        Object var83 = var34.put("Y", var194);
                                    }
                                } else {
                                    if (var16 != 0) {
                                        var203 = "Top ";
                                    } else {
                                        var203 = "Bottom ";
                                    }
                                    if (var15 != 0) {
                                        var236 = "Left";
                                    } else {
                                        var236 = "Right";
                                    }
                                    String var196 = (var203) + (var236);
                                    Object var85 = var34.put("Anchor", var196);
                                    int var233 = Math.round(var17);
                                    Integer var199 = Math.max(0, var233);
                                    Object var87 = var34.put("X", var199);
                                    int var235 = Math.round(var18);
                                    Integer var202 = Math.max(0, var235);
                                    Object var89 = var34.put("Y", var202);
                                    if (HUD.INSTANCE != null) {
                                        var110 = HUD.INSTANCE.getScale();
                                    } else {
                                        var110 = 1.0F;
                                    }
                                    float var22 = var110;
                                    int var207 = Math.round((arg1 / var22));
                                    Integer var157 = Math.max(0, var207);
                                    Object var45 = var34.put("X", var157);
                                    int var210 = Math.round((arg2 / var22));
                                    Integer var160 = Math.max(0, var210);
                                    Object var47 = var34.put("Y", var160);
                                }
                                String var124 = String.valueOf(var34);
                                String var49 = (arg0) + (var124);
                                String var125 = lastApplied;
                                if (var49.equals(var125)) {
                                    return;
                                }
                                lastApplied = var49;
                                ClarityConfig.putSettings(arg0, var34, true);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private static float clamp(float arg0, float arg1, float arg2) {
        float var8 = 0.0F;
        if (Float.compare(arg0, arg1) < 0) {
            var8 = arg1;
        } else {
            if (Float.compare(arg0, arg2) > 0) {
                var8 = arg2;
            } else {
                var8 = arg0;
            }
            return var8;
        }
    }

    private static int withAlpha(int arg0, float arg1) {
        int var10 = (Math.round((clamp(arg1, 0.0F, 1.0F) * 255.0F)) << 24);
        int var15 = (arg0 & 16777215);
        return (var10 | var15);
    }

    static {
        Hooks.Bool0 var1 = () -> HudLayout.isEditing();
        Hooks.bool0("HudLayout.isEditing", var1);
        Hooks.Act0 var12 = () -> HudLayout.enter();
        Hooks.act0("HudLayout.enter", var12);
        Hooks.Act0 var13 = () -> HudLayout.exit();
        Hooks.act0("HudLayout.exit", var13);
        Hooks.Act0 var14 = () -> HudLayout.exitNoGrab();
        Hooks.act0("HudLayout.exitNoGrab", var14);
        Hooks.Act1 var15 = (p0) -> HudLayout.lambda$static$0(p0);
        Hooks.act1("HudLayout.onMouseButton", var15);
        LinkedHashMap var6 = new LinkedHashMap();
        RECTS = var6;
        frame = 0L;
        canvasW = 1.0F;
        canvasH = 1.0F;
        lastApplied = "";
    }

    public final class Rect {
        float x;
        float y;
        float w;
        float h;
        long frame;

        private Rect() {
        }

    }

}
