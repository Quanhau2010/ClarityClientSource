package need.clarity.modules.visual.kenny;

import com.mojang.blaze3d.vertex.VertexConsumer;

public final class CompiledMeshPart {
    public static final CompiledMeshPart INSTANCE;
    final int counterValue;
    final float[] floatBuffer;
    final int[] intBuffer;

    CompiledMeshPart(float[] arg0, int[] arg1) {
        int var8 = arg1.length;
        this.counterValue = var8;
        this.floatBuffer = arg0;
        this.intBuffer = arg1;
    }

    public int computeColorOrIndex() {
        return this.counterValue;
    }

    public void applyStep(VertexConsumer arg0, double[] arg1, float arg2) {
        int var25 = 0;
        int var26 = 0;
        int var30 = 0;
        int var109 = 0;
        if (this.counterValue == 0) {
            return;
        }
        float var35 = ((float) arg1[0]);
        float var39 = ((float) arg1[1]);
        float var43 = ((float) arg1[2]);
        float var47 = ((float) arg1[3]);
        float var51 = ((float) arg1[4]);
        float var55 = ((float) arg1[5]);
        float var59 = ((float) arg1[6]);
        float var63 = ((float) arg1[7]);
        float var67 = ((float) arg1[8]);
        float var71 = ((float) arg1[9]);
        float var75 = ((float) arg1[10]);
        float var79 = ((float) arg1[11]);
        float[] var81 = this.floatBuffer;
        int[] var83 = this.intBuffer;
        if (Float.compare(arg2, 0.999F) < 0) {
            var109 = 1;
        } else {
            var109 = 0;
        }
        int var24 = var109;
        var25 = 0;
        var26 = 0;
        while (true) {
            int var88 = var25;
            int var123 = this.counterValue;
            if (var88 >= var123) break;
            int var124 = var26;
            float var90 = var81[var124];
            int var126 = (var26 + 1);
            float var92 = var81[var126];
            int var128 = (var26 + 2);
            float var94 = var81[var128];
            int var129 = var25;
            var30 = var83[var129];
            if (var24 != 0) {
                int var104 = (((int) (((float) ((var30 >>> 24) & 255)) * arg2)) << 24);
                int var135 = (var30 & 16777215);
                var30 = (var104 | var135);
            }
            float var137 = (var35 * var90);
            float var146 = (var39 * var92);
            float var138 = (var137 + var146);
            float var148 = (var43 * var94);
            float var140 = ((var138 + var148) + var47);
            float var151 = (var51 * var90);
            float var158 = (var55 * var92);
            float var152 = (var151 + var158);
            float var160 = (var59 * var94);
            float var154 = ((var152 + var160) + var63);
            float var163 = (var67 * var90);
            float var170 = (var71 * var92);
            float var164 = (var163 + var170);
            float var172 = (var75 * var94);
            float var166 = ((var164 + var172) + var79);
            VertexConsumer var107 = arg0.addVertex(var140, var154, var166);
            int var141 = var30;
            VertexConsumer var108 = var107.setColor(var141);
            var25 = (var25 + 1);
            var26 = (var26 + 3);
        }
    }

    static {
        float[] var3 = new float[0];
        int[] var4 = new int[0];
        CompiledMeshPart var0 = new CompiledMeshPart(var3, var4);
        INSTANCE = var0;
    }

}
