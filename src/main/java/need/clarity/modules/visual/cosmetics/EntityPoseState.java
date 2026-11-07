package need.clarity.modules.visual.cosmetics;


public record EntityPoseState(double x, double y, double z, double bodyYawDeg, double headYawDeg, double headPitchDeg, double height, boolean sneaking, boolean swimming, boolean fallFlying, double followTilt) {

    public static EntityPoseState evaluate(double arg0, double arg1, double arg2, double arg3, double arg4) {
        EntityPoseState var10 = new EntityPoseState(arg0, arg1, arg2, arg3, arg3, 0.0, arg4, false, false, false, 1.0);
        return var10;
    }

    public EntityPoseState evaluate(double arg0, double arg1) {
        double var19 = this.x;
        double var21 = this.y;
        double var23 = this.z;
        double var25 = this.bodyYawDeg;
        double var27 = this.height;
        boolean var29 = this.sneaking;
        boolean var30 = this.swimming;
        boolean var31 = this.fallFlying;
        double var32 = this.followTilt;
        EntityPoseState var5 = new EntityPoseState(var19, var21, var23, var25, arg0, arg1, var27, var29, var30, var31, var32);
        return var5;
    }

    public EntityPoseState evaluate(boolean arg0, boolean arg1, boolean arg2) {
        double var16 = this.x;
        double var18 = this.y;
        double var20 = this.z;
        double var22 = this.bodyYawDeg;
        double var24 = this.headYawDeg;
        double var26 = this.headPitchDeg;
        double var28 = this.height;
        double var30 = this.followTilt;
        EntityPoseState var4 = new EntityPoseState(var16, var18, var20, var22, var24, var26, var28, arg0, arg1, arg2, var30);
        return var4;
    }

    public EntityPoseState evaluate(double arg0) {
        double var16 = this.x;
        double var18 = this.y;
        double var20 = this.z;
        double var22 = this.bodyYawDeg;
        double var24 = this.headYawDeg;
        double var26 = this.headPitchDeg;
        double var28 = this.height;
        boolean var30 = this.sneaking;
        boolean var31 = this.swimming;
        boolean var32 = this.fallFlying;
        EntityPoseState var3 = new EntityPoseState(var16, var18, var20, var22, var24, var26, var28, var30, var31, var32, arg0);
        return var3;
    }

    public double computeDoubleMetric() {
        double var7 = 0.0;
        if (!this.swimming) {
            if (this.fallFlying) {
            }
        }
        var7 = 0.4;
        if (this.sneaking) {
            var7 = 1.27;
        } else {
            var7 = 1.62;
        }
        return var7;
    }

}
