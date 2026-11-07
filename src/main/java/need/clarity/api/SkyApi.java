package need.clarity.api;


public final class SkyApi {
    private SkyApi() {
    }

    public static boolean matchFog() {
        return Hooks.bool("SkyShaders.matchFog", false);
    }

    public static boolean hideClouds() {
        return Hooks.bool("SkyShaders.hideClouds", false);
    }

    public static boolean keepSunMoon() {
        return Hooks.bool("SkyShaders.keepSunMoon", false);
    }

    public static String preset() {
        String var5 = null;
        Object var2 = Hooks.obj("SkyShaders.preset");
        if (var2 == null) {
            var5 = "";
        } else {
            var5 = var2.toString();
        }
        return var5;
    }

    public static float[][] colors() {
        float[][] var13 = null;
        Object var7 = Hooks.obj("SkyShaders.colors");
        if ((var7 instanceof float[][])) {
            var13 = ((float[][]) var7);
        } else {
            var13 = new float[2][];
            float[] var15 = new float[3];
            var15[0] = 0.0F;
            var15[1] = 0.0F;
            var15[2] = 0.0F;
            var13[0] = var15;
            float[] var17 = new float[3];
            var17[0] = 0.0F;
            var17[1] = 0.0F;
            var17[2] = 0.0F;
            var13[1] = var17;
        }
        return var13;
    }

    public static float[] horizon(String arg0, float[] arg1, float[] arg2) {
        float[] var13 = null;
        Object[] var16 = new Object[3];
        var16[0] = arg0;
        var16[1] = arg1;
        var16[2] = arg2;
        Object var9 = Hooks.obj1("SkyShaders.horizon", var16);
        if ((var9 instanceof float[])) {
            var13 = ((float[]) var9);
        } else {
            var13 = new float[3];
            var13[0] = 0.0F;
            var13[1] = 0.0F;
            var13[2] = 0.0F;
        }
        return var13;
    }

    public static boolean wasActiveLastFrame() {
        return Hooks.bool("SkyRenderer.wasActiveLastFrame", false);
    }

    public static boolean isActiveThisFrame() {
        return Hooks.bool("SkyRenderer.isActiveThisFrame", false);
    }

    public static void beginFrame(Object arg0, Object arg1) {
        Object[] var6 = new Object[2];
        var6[0] = arg0;
        var6[1] = arg1;
        Hooks.run1("SkyRenderer.beginFrame", var6);
    }

    public static void captureProjection(Object arg0) {
        Hooks.run1("SkyRenderer.captureProjection", arg0);
    }

    public static boolean tryRender(boolean arg0) {
        Boolean var5 = arg0;
        return Hooks.bool1("SkyRenderer.tryRender", var5, false);
    }

}
