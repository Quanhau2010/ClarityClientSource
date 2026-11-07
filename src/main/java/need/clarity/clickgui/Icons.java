package need.clarity.clickgui;


public final class Icons {
    private Icons() {
    }

    static void draw(Canvas arg0, String arg1, float arg2, float arg3, float arg4, int arg5) {
        int var8 = 0;
        int var9 = 0;
        double var10 = 0.0;
        float var6 = Math.max(1.2F, (arg4 * 0.11F));
        var8 = -1;
        switch (arg1.hashCode()) {
            case -1790955542 -> {}
            case -1732349088 -> {}
            case -937619446 -> {}
            case -906336856 -> {}
            case -646164112 -> {}
            case -39033649 -> {}
            case 70939 -> {}
            case 3089570 -> {}
            case 64189366 -> {}
            case 94627080 -> {}
            case 94756344 -> {}
            case 108404047 -> {}
            case 742886971 -> {}
            case 1549674828 -> {}
            case 2024008468 -> {}
            default -> {}
        }
        if (arg1.equals("Combat")) {
            var8 = 0;
            if (arg1.equals("Utility")) {
                var8 = 1;
                if (arg1.equals("Movement")) {
                    var8 = 2;
                    if (arg1.equals("Visual")) {
                        var8 = 3;
                        if (arg1.equals("Themes")) {
                            var8 = 4;
                            if (arg1.equals("Profiles")) {
                                var8 = 5;
                                if (arg1.equals("Binds")) {
                                    var8 = 6;
                                    if (arg1.equals("Servers")) {
                                        var8 = 7;
                                        if (arg1.equals("GUI")) {
                                            var8 = 8;
                                            if (arg1.equals("search")) {
                                                var8 = 9;
                                                if (arg1.equals("chevron")) {
                                                    var8 = 10;
                                                    if (arg1.equals("down")) {
                                                        var8 = 11;
                                                        if (arg1.equals("close")) {
                                                            var8 = 12;
                                                            if (arg1.equals("check")) {
                                                                var8 = 13;
                                                                if (arg1.equals("reset")) {
                                                                    var8 = 14;
                                                                }
                                                                switch (var8) {
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
                                                                    default -> {}
                                                                }
                                                                arg0.ring(arg2, arg3, (arg4 * 0.36F), var6, arg5);
                                                                arg0.line((arg2 - (arg4 * 0.5F)), arg3, (arg2 - (arg4 * 0.18F)), arg3, var6, arg5);
                                                                arg0.line((arg2 + (arg4 * 0.18F)), arg3, (arg2 + (arg4 * 0.5F)), arg3, var6, arg5);
                                                                arg0.line(arg2, (arg3 - (arg4 * 0.5F)), arg2, (arg3 - (arg4 * 0.18F)), var6, arg5);
                                                                arg0.line(arg2, (arg3 + (arg4 * 0.18F)), arg2, (arg3 + (arg4 * 0.5F)), var6, arg5);
                                                                arg0.circle(arg2, arg3, (arg4 * 0.07F), arg5);
                                                                arg0.ring(arg2, arg3, (arg4 * 0.22F), var6, arg5);
                                                                var9 = 0;
                                                                while (true) {
                                                                    if (var9 >= 8) break;
                                                                    var10 = ((((double) var9) * 3.141592653589793) / 4.0);
                                                                    arg0.line((arg2 + ((((float) Math.cos(var10)) * arg4) * 0.3F)), (arg3 + ((((float) Math.sin(var10)) * arg4) * 0.3F)), (arg2 + ((((float) Math.cos(var10)) * arg4) * 0.44F)), (arg3 + ((((float) Math.sin(var10)) * arg4) * 0.44F)), (var6 * 1.3F), arg5);
                                                                    var9++;
                                                                }
                                                                var9 = 0;
                                                                while (true) {
                                                                    if (var9 >= 2) break;
                                                                    var10 = (((((float) var9) - 0.5F) * arg4) * 0.28F);
                                                                    arg0.line(((arg2 + var10) - (arg4 * 0.14F)), (arg3 - (arg4 * 0.3F)), ((arg2 + var10) + (arg4 * 0.14F)), arg3, var6, arg5);
                                                                    arg0.line(((arg2 + var10) + (arg4 * 0.14F)), arg3, ((arg2 + var10) - (arg4 * 0.14F)), (arg3 + (arg4 * 0.3F)), var6, arg5);
                                                                    var9++;
                                                                }
                                                                arg0.arc(arg2, (arg3 + (arg4 * 0.32F)), (arg4 * 0.48F), -2.576106F, -0.56548667F, var6, arg5);
                                                                arg0.arc(arg2, (arg3 - (arg4 * 0.32F)), (arg4 * 0.48F), 0.56548667F, 2.576106F, var6, arg5);
                                                                arg0.circle(arg2, arg3, (arg4 * 0.14F), arg5);
                                                                arg0.circle((arg2 - (arg4 * 0.18F)), (arg3 - (arg4 * 0.12F)), (arg4 * 0.13F), arg5);
                                                                arg0.circle((arg2 + (arg4 * 0.18F)), (arg3 - (arg4 * 0.12F)), (arg4 * 0.13F), Theme.a(arg5, 0.7F));
                                                                arg0.circle(arg2, (arg3 + (arg4 * 0.2F)), (arg4 * 0.13F), Theme.a(arg5, 0.45F));
                                                                arg0.stroke((arg2 - (arg4 * 0.32F)), (arg3 - (arg4 * 0.28F)), (arg4 * 0.64F), (arg4 * 0.22F), (arg4 * 0.06F), var6, arg5);
                                                                arg0.stroke((arg2 - (arg4 * 0.32F)), (arg3 - (arg4 * 0.02F)), (arg4 * 0.64F), (arg4 * 0.22F), (arg4 * 0.06F), var6, Theme.a(arg5, 0.75F));
                                                                arg0.stroke((arg2 - (arg4 * 0.32F)), (arg3 + (arg4 * 0.24F)), (arg4 * 0.64F), (arg4 * 0.08F), (arg4 * 0.04F), var6, Theme.a(arg5, 0.5F));
                                                                arg0.stroke((arg2 - (arg4 * 0.36F)), (arg3 - (arg4 * 0.3F)), (arg4 * 0.72F), (arg4 * 0.6F), (arg4 * 0.12F), var6, arg5);
                                                                arg0.line((arg2 - (arg4 * 0.16F)), (arg3 + (arg4 * 0.1F)), (arg2 + (arg4 * 0.16F)), (arg3 + (arg4 * 0.1F)), var6, arg5);
                                                                arg0.ring(arg2, arg3, (arg4 * 0.38F), var6, arg5);
                                                                arg0.line((arg2 - (arg4 * 0.38F)), arg3, (arg2 + (arg4 * 0.38F)), arg3, var6, arg5);
                                                                arg0.arc(arg2, arg3, (arg4 * 0.38F), -1.5707964F, 1.5707964F, (var6 * 0.01F), 0);
                                                                arg0.stroke((arg2 - (arg4 * 0.16F)), (arg3 - (arg4 * 0.38F)), (arg4 * 0.32F), (arg4 * 0.76F), (arg4 * 0.16F), var6, arg5);
                                                                var9 = -1;
                                                                while (true) {
                                                                    if (var9 > 1) break;
                                                                    var10 = (arg3 + ((((float) var9) * arg4) * 0.24F));
                                                                    arg0.line((arg2 - (arg4 * 0.36F)), var10, (arg2 + (arg4 * 0.36F)), var10, var6, Theme.a(arg5, 0.55F));
                                                                    if (var9 == 0) {
                                                                    } else {
                                                                    }
                                                                    arg2.circle(((arg4 * 0.14F) + ((-(arg4) * 0.16F) + ((((float) var9) * arg4) * 0.05F))), var10, (arg4 * 0.1F), arg5);
                                                                    var9++;
                                                                }
                                                                arg0.ring((arg2 - (arg4 * 0.08F)), (arg3 - (arg4 * 0.08F)), (arg4 * 0.26F), var6, arg5);
                                                                arg0.line((arg2 + (arg4 * 0.12F)), (arg3 + (arg4 * 0.12F)), (arg2 + (arg4 * 0.34F)), (arg3 + (arg4 * 0.34F)), (var6 * 1.2F), arg5);
                                                                arg0.line((arg2 - (arg4 * 0.12F)), (arg3 - (arg4 * 0.24F)), (arg2 + (arg4 * 0.12F)), arg3, var6, arg5);
                                                                arg0.line((arg2 + (arg4 * 0.12F)), arg3, (arg2 - (arg4 * 0.12F)), (arg3 + (arg4 * 0.24F)), var6, arg5);
                                                                arg0.line((arg2 - (arg4 * 0.24F)), (arg3 - (arg4 * 0.1F)), arg2, (arg3 + (arg4 * 0.14F)), var6, arg5);
                                                                arg0.line(arg2, (arg3 + (arg4 * 0.14F)), (arg2 + (arg4 * 0.24F)), (arg3 - (arg4 * 0.1F)), var6, arg5);
                                                                arg0.line((arg2 - (arg4 * 0.22F)), (arg3 - (arg4 * 0.22F)), (arg2 + (arg4 * 0.22F)), (arg3 + (arg4 * 0.22F)), var6, arg5);
                                                                arg0.line((arg2 + (arg4 * 0.22F)), (arg3 - (arg4 * 0.22F)), (arg2 - (arg4 * 0.22F)), (arg3 + (arg4 * 0.22F)), var6, arg5);
                                                                arg0.line((arg2 - (arg4 * 0.26F)), arg3, (arg2 - (arg4 * 0.06F)), (arg3 + (arg4 * 0.2F)), (var6 * 1.3F), arg5);
                                                                arg0.line((arg2 - (arg4 * 0.06F)), (arg3 + (arg4 * 0.2F)), (arg2 + (arg4 * 0.28F)), (arg3 - (arg4 * 0.2F)), (var6 * 1.3F), arg5);
                                                                arg0.arc(arg2, arg3, (arg4 * 0.3F), -1.8849556F, 3.455752F, var6, arg5);
                                                                arg0.line((arg2 - (arg4 * 0.3F)), (arg3 - (arg4 * 0.28F)), (arg2 - (arg4 * 0.12F)), (arg3 - (arg4 * 0.26F)), var6, arg5);
                                                                arg0.circle(arg2, arg3, (arg4 * 0.16F), arg5);
                                                                return;
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
