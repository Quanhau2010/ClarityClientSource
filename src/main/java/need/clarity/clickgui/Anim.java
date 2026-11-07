package need.clarity.clickgui;

import java.util.HashMap;
import java.util.Map;

public final class Anim {
    private final Map vals;
    private float dt;
    private float speed;
    private boolean active;

    public Anim() {
        this.vals = new HashMap();
        this.dt = 0.016666668F;
        this.speed = 1.0F;
    }

    public void frame(float arg0, float arg1) {
        this.dt = Math.max(0.0F, Math.min(arg0, 0.05F));
        this.speed = Math.max(0.1F, arg1);
        this.active = false;
    }

    public boolean busy() {
        return this.active;
    }

    public float to(String arg0, float arg1, float arg2) {
        Anim.Val var4 = ((Anim.Val) this.vals.computeIfAbsent(arg0, (p0) -> Anim.lambda$to$0(p0)));
        if (!var4.init) {
            var4.v = arg1;
            var4.init = true;
            return arg1;
        }
        float var5 = (1.0F - ((float) Math.exp(((double) ((-(arg2) * this.speed) * this.dt)))));
        var4.v = (var4.v + ((arg1 - var4.v) * var5));
        if (Math.abs((arg1 - var4.v)) < 0.001F) {
            var4.v = arg1;
        } else {
            this.active = true;
        }
        return var4.v;
    }

    public float from(String arg0, float arg1, float arg2, float arg3) {
        Anim.Val var5 = null;
        var5 = ((Anim.Val) this.vals.get(arg0));
        if (var5 == null) {
            var5 = new Anim.Val();
            var5.v = arg1;
            var5.init = true;
            this.vals.put(arg0, var5);
        }
        return this.to(arg0, arg2, arg3);
    }

    public float spring(String arg0, float arg1, float arg2, float arg3) {
        Anim.Val var5 = ((Anim.Val) this.vals.computeIfAbsent(arg0, (p0) -> Anim.lambda$spring$0(p0)));
        if (!var5.init) {
            var5.v = arg1;
            var5.init = true;
            return arg1;
        }
        float var6 = (this.dt * this.speed);
        int var7 = Math.max(1, ((int) Math.ceil(((double) (var6 / 0.008F)))));
        float var8 = (var6 / ((float) var7));
        int var9 = 0;
        while (true) {
            if (var9 >= var7) break;
            float var10 = (((arg1 - var5.v) * arg2) - (var5.vel * arg3));
            var5.vel = (var5.vel + (var10 * var8));
            var5.v = (var5.v + (var5.vel * var8));
            var9++;
        }
        if (Math.abs((arg1 - var5.v)) < 0.001F) {
            if (Math.abs(var5.vel) < 0.001F) {
                var5.v = arg1;
                var5.vel = 0.0F;
            } else {
                this.active = true;
            }
            return var5.v;
        }
    }

    public void set(String arg0, float arg1) {
        Anim.Val var3 = ((Anim.Val) this.vals.computeIfAbsent(arg0, (p0) -> Anim.lambda$set$0(p0)));
        var3.v = arg1;
        var3.vel = 0.0F;
        var3.init = true;
    }

    public void reset(String arg0) {
        this.vals.remove(arg0);
    }

    public void resetPrefix(String arg0) {
        this.vals.keySet().removeIf((p0) -> Anim.lambda$resetPrefix$0(arg0, p0));
    }

    public static float ease(float arg0) {
        arg0 = Math.max(0.0F, Math.min(1.0F, arg0));
        return ((arg0 * arg0) * (3.0F - (2.0F * arg0)));
    }

    public static float easeOutBack(float arg0) {
        arg0 = Math.max(0.0F, Math.min(1.0F, arg0));
        float var2 = (1.70158F + 1.0F);
        return ((1.0F + (var2 * ((float) Math.pow(((double) (arg0 - 1.0F)), 3.0)))) + (1.70158F * ((float) Math.pow(((double) (arg0 - 1.0F)), 2.0))));
    }

    public static float easeOutCubic(float arg0) {
        arg0 = Math.max(0.0F, Math.min(1.0F, arg0));
        return (1.0F - ((float) Math.pow(((double) (1.0F - arg0)), 3.0)));
    }

    public final class Val {
        float v;
        float vel;
        boolean init;

        private Val() {
        }

    }

}
