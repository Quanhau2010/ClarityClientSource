package need.clarity.modules.visual.wings;


public enum WingStyle {
    ANGEL("Angel", 0.6),
    DRAGON("Dragon", 0.64),
    BUTTERFLY("Butterfly", 1.05),
    PHOENIX("Phoenix", 0.42),
    CRYSTAL("Crystal", 0.62),
    CYBER("Cyber", 0.52),
    FAIRY("Fairy", 0.66),
    CLASSIC("Classic", 1.0);

    public final String labelText;
    public final double doubleValue;

    WingStyle(String arg0, double arg1) {
        this.labelText = arg0;
        this.doubleValue = arg1;
    }

    public static WingStyle fromDisplayName(String arg0) {
        int var5 = 0;
        if (arg0 != null) {
            WingStyle[] var7 = values();
            int var9 = var7.length;
            var5 = 0;
            while (true) {
                if (var5 < var9) {
                    int var19 = var5;
                    WingStyle var13 = var7[var19];
                    if (var13.labelText.equalsIgnoreCase(arg0)) {
                        return var13;
                    }
                }
            }
        }
        return ANGEL;
        var5 = (var5 + 1);
    }

    public void applyStep(WingRenderContext arg0) {
        switch (this.ordinal()) {
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
        WingGeometryRenderer.applyStep5(arg0);
        WingGeometryRenderer.applyStep2(arg0);
        WingGeometryRenderer.applyStep3(arg0);
        WingGeometryRenderer.applyStep1(arg0);
        WingGeometryRenderer.applyStep6(arg0);
        WingGeometryRenderer.applyStep4(arg0);
        WingGeometryRenderer.applyStep(arg0);
        WingGeometryRenderer.applyStep7(arg0);
    }

}
