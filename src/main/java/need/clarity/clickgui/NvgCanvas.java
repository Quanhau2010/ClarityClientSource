package need.clarity.clickgui;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

public final class NvgCanvas implements Canvas {
    private final long vg;
    private final NVGColor c1;
    private final NVGColor c2;
    private final NVGPaint paint;
    private final Deque alphaStack;
    private final float[] xf;
    private float alpha;
    private int fbW;
    private int fbH;
    private int glassImg;
    private int glassTex;
    private int glassW;
    private int glassH;
    private boolean crisp;
    private int snapImg;
    private int snapTex;
    private int snapW;
    private int snapH;
    private int glassSrcTex;
    private int glassSrcW;
    private int glassSrcH;
    private final Map images;
    private final float[] bounds;

    public NvgCanvas(long arg0) {
        this.c1 = NVGColor.create();
        this.c2 = NVGColor.create();
        this.paint = NVGPaint.create();
        this.alphaStack = new ArrayDeque();
        this.xf = new float[6];
        this.alpha = 1.0F;
        this.glassImg = 0;
        this.glassTex = 0;
        this.glassW = 0;
        this.glassH = 0;
        this.crisp = true;
        this.snapImg = 0;
        this.snapTex = 0;
        this.snapW = 0;
        this.snapH = 0;
        this.images = new HashMap();
        this.bounds = new float[4];
        this.vg = arg0;
    }

    void begin(int arg0, int arg1) {
        this.fbW = arg0;
        this.fbH = arg1;
        this.alpha = 1.0F;
        this.alphaStack.clear();
    }

    void setCrisp(boolean arg0) {
        this.crisp = arg0;
    }

    void setSnapshot(int arg0, int arg1, int arg2) {
        if (this.snapImg != 0) {
            if (this.snapTex == arg0) {
                if (this.snapW == arg1) {
                    if (this.snapH == arg2) {
                        return;
                    }
                    if (this.snapImg != 0) {
                        NanoVG.nvgDeleteImage(this.vg, this.snapImg);
                    }
                    if (arg0 == 0) {
                    } else {
                    }
                    0.snapImg = NanoVGGL3.nvglCreateImageFromHandle(this.vg, arg0, arg1, arg2, 65560);
                    this.snapTex = arg0;
                    this.snapW = arg1;
                    this.snapH = arg2;
                    return;
                }
            }
        }
    }

    void forgetSnapshot() {
        this.snapImg = 0;
        this.snapTex = 0;
    }

    public boolean hasSnapshot() {
        return (this.snapImg != 0);
    }

    public void snapPiece(float[] arg0, float arg1, float arg2, float arg3) {
        if (this.snapImg != 0) {
            if (arg0.length < 6) {
            }
        }
        return;
        NanoVG.nvgImagePattern(this.vg, 0.0F, 0.0F, arg1, arg2, 0.0F, this.snapImg, Math.max(0.0F, Math.min(1.0F, (arg3 * this.alpha))), this.paint);
        this.polyPath(arg0);
        NanoVG.nvgFillPaint(this.vg, this.paint);
        NanoVG.nvgFill(this.vg);
    }

    public void snapHole(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
        if (this.snapImg == 0) {
            return;
        }
        NanoVG.nvgImagePattern(this.vg, 0.0F, 0.0F, arg3, arg4, 0.0F, this.snapImg, Math.max(0.0F, Math.min(1.0F, (arg5 * this.alpha))), this.paint);
        NanoVG.nvgBeginPath(this.vg);
        NanoVG.nvgRect(this.vg, 0.0F, 0.0F, arg3, arg4);
        NanoVG.nvgCircle(this.vg, arg0, arg1, arg2);
        NanoVG.nvgPathWinding(this.vg, 2);
        NanoVG.nvgFillPaint(this.vg, this.paint);
        NanoVG.nvgFill(this.vg);
    }

    public void strokePoly(float[] arg0, float arg1, int arg2) {
        if (arg0.length < 6) {
            return;
        }
        this.polyPath(arg0);
        NanoVG.nvgStrokeWidth(this.vg, arg1);
        NanoVG.nvgStrokeColor(this.vg, this.col(arg2, this.c1));
        NanoVG.nvgStroke(this.vg);
    }

    private void polyPath(float[] arg0) {
        NanoVG.nvgBeginPath(this.vg);
        NanoVG.nvgMoveTo(this.vg, arg0[0], arg0[1]);
        int var2 = 2;
        while (true) {
            if ((var2 + 1) >= arg0.length) break;
            NanoVG.nvgLineTo(this.vg, arg0[var2], arg0[(var2 + 1)]);
            var2 += 2;
        }
        NanoVG.nvgClosePath(this.vg);
    }

    public void rotate(float arg0) {
        NanoVG.nvgRotate(this.vg, arg0);
    }

    public void scale2(float arg0, float arg1) {
        NanoVG.nvgScale(this.vg, arg0, arg1);
    }

    public boolean logo(float arg0, float arg1, float arg2, int arg3, float arg4) {
        Integer var6 = null;
        var6 = ((Integer) this.images.get("clarity_logo"));
        if (var6 == null) {
            this.image("clarity_logo", arg0, arg1, 0.0F, 0.0F, 0.0F);
            var6 = ((Integer) this.images.get("clarity_logo"));
        }
        if (var6 != null) {
            if (var6 <= 0) {
            }
        }
        return false;
        NanoVG.nvgImagePattern(this.vg, arg0, arg1, arg2, arg2, 0.0F, var6, 1.0F, this.paint);
        this.paint.innerColor(this.col(Theme.a(arg3, arg4), this.c1));
        this.paint.outerColor(this.col(Theme.a(arg3, arg4), this.c2));
        NanoVG.nvgBeginPath(this.vg);
        NanoVG.nvgRect(this.vg, arg0, arg1, arg2, arg2);
        NanoVG.nvgFillPaint(this.vg, this.paint);
        NanoVG.nvgFill(this.vg);
        return true;
    }

    private NVGColor col(int arg0, NVGColor arg1) {
        float var3 = ((((float) ((arg0 >>> 24) & 255)) / 255.0F) * this.alpha);
        return NanoVG.nvgRGBAf((((float) ((arg0 >> 16) & 255)) / 255.0F), (((float) ((arg0 >> 8) & 255)) / 255.0F), (((float) (arg0 & 255)) / 255.0F), Math.max(0.0F, Math.min(1.0F, var3)), arg1);
    }

    public void save() {
        NanoVG.nvgSave(this.vg);
        this.alphaStack.push(this.alpha);
    }

    public void restore() {
        NanoVG.nvgRestore(this.vg);
        if (this.alphaStack.isEmpty()) {
        } else {
        }
        1.0F.alpha = ((Float) this.alphaStack.pop());
    }

    public void translate(float arg0, float arg1) {
        NanoVG.nvgTranslate(this.vg, arg0, arg1);
    }

    public void scale(float arg0) {
        NanoVG.nvgScale(this.vg, arg0, arg0);
    }

    public void alpha(float arg0) {
        this.alpha = (this.alpha * Math.max(0.0F, Math.min(1.0F, arg0)));
    }

    public void scissor(float arg0, float arg1, float arg2, float arg3) {
        NanoVG.nvgIntersectScissor(this.vg, arg0, arg1, Math.max(0.0F, arg2), Math.max(0.0F, arg3));
    }

    private void path(float arg0, float arg1, float arg2, float arg3, float arg4) {
        NanoVG.nvgBeginPath(this.vg);
        arg4 = Math.min(arg4, (Math.min(arg2, arg3) / 2.0F));
        if (arg4 <= 0.01F) {
            NanoVG.nvgRect(this.vg, arg0, arg1, arg2, arg3);
        } else {
            NanoVG.nvgRoundedRect(this.vg, arg0, arg1, arg2, arg3, arg4);
        }
    }

    public void rect(float arg0, float arg1, float arg2, float arg3, float arg4, int arg5) {
        if (arg2 > 0.0F) {
            if (arg3 <= 0.0F) {
            }
        }
        return;
        this.path(arg0, arg1, arg2, arg3, arg4);
        NanoVG.nvgFillColor(this.vg, this.col(arg5, this.c1));
        NanoVG.nvgFill(this.vg);
    }

    public void gradient(float arg0, float arg1, float arg2, float arg3, float arg4, int arg5, int arg6, boolean arg7) {
        if (arg2 > 0.0F) {
            if (arg3 <= 0.0F) {
            }
        }
        return;
        if (arg7) {
        } else {
        }
        if (arg7) {
        } else {
        }
        NanoVG.nvgLinearGradient(arg1, (arg0 + arg2), arg0, arg1, (arg1 + arg3), this.col(arg5, this.c1), this.col(arg6, this.c2), this.paint);
        this.path(arg0, arg1, arg2, arg3, arg4);
        NanoVG.nvgFillPaint(this.vg, this.paint);
        NanoVG.nvgFill(this.vg);
    }

    public void stroke(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6) {
        this.path(arg0, arg1, arg2, arg3, arg4);
        NanoVG.nvgStrokeWidth(this.vg, arg5);
        NanoVG.nvgStrokeColor(this.vg, this.col(arg6, this.c1));
        NanoVG.nvgStroke(this.vg);
    }

    public void strokeGradient(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6, int arg7) {
        NanoVG.nvgLinearGradient(this.vg, arg0, arg1, (arg0 + arg2), (arg1 + arg3), this.col(arg6, this.c1), this.col(arg7, this.c2), this.paint);
        this.path(arg0, arg1, arg2, arg3, arg4);
        NanoVG.nvgStrokeWidth(this.vg, arg5);
        NanoVG.nvgStrokePaint(this.vg, this.paint);
        NanoVG.nvgStroke(this.vg);
    }

    public void shadow(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6) {
        NanoVG.nvgBoxGradient(this.vg, arg0, arg1, arg2, arg3, arg4, arg5, this.col(arg6, this.c1), this.col((arg6 & 16777215), this.c2), this.paint);
        NanoVG.nvgBeginPath(this.vg);
        NanoVG.nvgRect(this.vg, (arg0 - (arg5 * 1.5F)), (arg1 - (arg5 * 1.5F)), (arg2 + (arg5 * 3.0F)), (arg3 + (arg5 * 3.0F)));
        NanoVG.nvgFillPaint(this.vg, this.paint);
        NanoVG.nvgFill(this.vg);
    }

    public void glass(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
        int var7 = this.glassImage();
        if (var7 == 0) {
            return;
        }
        NanoVG.nvgCurrentTransform(this.vg, this.xf);
        float var8 = this.xf[0];
        float var9 = this.xf[3];
        float var10 = this.xf[4];
        float var11 = this.xf[5];
        if (Math.abs(var8) >= 1.0E-6F) {
            if (Math.abs(var9) < 1.0E-6F) {
            }
        }
        return;
        NanoVG.nvgImagePattern(this.vg, (-(var10) / var8), (-(var11) / var9), (((float) this.fbW) / var8), (((float) this.fbH) / var9), 0.0F, var7, Math.max(0.0F, Math.min(1.0F, (arg5 * this.alpha))), this.paint);
        this.path(arg0, arg1, arg2, arg3, arg4);
        NanoVG.nvgFillPaint(this.vg, this.paint);
        NanoVG.nvgFill(this.vg);
    }

    void setGlassSource(int arg0, int arg1, int arg2) {
        this.glassSrcTex = arg0;
        this.glassSrcW = arg1;
        this.glassSrcH = arg2;
    }

    private int glassImage() {
        int var1 = this.glassSrcTex;
        if (var1 == 0) {
            return 0;
        }
        int var2 = this.glassSrcW;
        int var3 = this.glassSrcH;
        if (this.glassImg != 0) {
            if (this.glassTex == var1) {
                if (this.glassW == var2) {
                    if (this.glassH == var3) {
                        return this.glassImg;
                    }
                    if (this.glassImg != 0) {
                        NanoVG.nvgDeleteImage(this.vg, this.glassImg);
                    }
                    this.glassImg = NanoVGGL3.nvglCreateImageFromHandle(this.vg, var1, var2, var3, 65544);
                    this.glassTex = var1;
                    this.glassW = var2;
                    this.glassH = var3;
                    return this.glassImg;
                }
            }
        }
    }

    void forgetImages() {
        this.glassImg = 0;
        this.glassTex = 0;
        this.forgetSnapshot();
    }

    public void image(String arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
        Integer var7 = null;
        InputStream var8 = null;
        byte[] var9 = null;
        ByteBuffer var10 = null;
        var7 = ((Integer) this.images.get(arg0));
        if (var7 == null) {
            var7 = 0;
            try {
                var8 = NvgCanvas.class.getResourceAsStream("/assets/clarity/textures/fx/" + (arg0) + ".png");
                try {
                    if (var8 != null) {
                        var9 = var8.readAllBytes();
                        var10 = BufferUtils.createByteBuffer(var9.length);
                        var10.put(var9).flip();
                        var7 = NanoVG.nvgCreateImageMem(this.vg, 1, var10);
                    }
                    if (var8 != null) {
                        var8.close();
                        if (var8 != null) {
                            try {
                                var8.close();
                            } catch (Throwable ignored) {
                            }
                            var9.addSuppressed(var10);
                        }
                        throw var9;
                    }
                    this.images.put(arg0, var7);
                } catch (Throwable ignored) {
                }
            } catch (Throwable ignored) {
            }
        }
        if (var7 != null) {
            if (var7 > 0) {
                if (arg3 > 0.0F) {
                    if (arg4 <= 0.0F) {
                    }
                }
                return;
                NanoVG.nvgImagePattern(this.vg, arg1, arg2, arg3, arg4, 0.0F, var7, Math.max(0.0F, Math.min(1.0F, (arg5 * this.alpha))), this.paint);
                NanoVG.nvgBeginPath(this.vg);
                NanoVG.nvgRect(this.vg, arg1, arg2, arg3, arg4);
                NanoVG.nvgFillPaint(this.vg, this.paint);
                NanoVG.nvgFill(this.vg);
                return;
            }
        }
    }

    public void circle(float arg0, float arg1, float arg2, int arg3) {
        NanoVG.nvgBeginPath(this.vg);
        NanoVG.nvgCircle(this.vg, arg0, arg1, arg2);
        NanoVG.nvgFillColor(this.vg, this.col(arg3, this.c1));
        NanoVG.nvgFill(this.vg);
    }

    public void ring(float arg0, float arg1, float arg2, float arg3, int arg4) {
        NanoVG.nvgBeginPath(this.vg);
        NanoVG.nvgCircle(this.vg, arg0, arg1, arg2);
        NanoVG.nvgStrokeWidth(this.vg, arg3);
        NanoVG.nvgStrokeColor(this.vg, this.col(arg4, this.c1));
        NanoVG.nvgStroke(this.vg);
    }

    public void arc(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6) {
        if (arg5 <= 0.05F) {
            return;
        }
        NanoVG.nvgBeginPath(this.vg);
        NanoVG.nvgArc(this.vg, arg0, arg1, arg2, arg3, arg4, 2);
        NanoVG.nvgLineCap(this.vg, 1);
        NanoVG.nvgStrokeWidth(this.vg, arg5);
        NanoVG.nvgStrokeColor(this.vg, this.col(arg6, this.c1));
        NanoVG.nvgStroke(this.vg);
    }

    public void line(float arg0, float arg1, float arg2, float arg3, float arg4, int arg5) {
        NanoVG.nvgBeginPath(this.vg);
        NanoVG.nvgMoveTo(this.vg, arg0, arg1);
        NanoVG.nvgLineTo(this.vg, arg2, arg3);
        NanoVG.nvgLineCap(this.vg, 1);
        NanoVG.nvgStrokeWidth(this.vg, arg4);
        NanoVG.nvgStrokeColor(this.vg, this.col(arg5, this.c1));
        NanoVG.nvgStroke(this.vg);
    }

    public void glow(float arg0, float arg1, float arg2, int arg3) {
        NanoVG.nvgRadialGradient(this.vg, arg0, arg1, 0.0F, arg2, this.col(arg3, this.c1), this.col((arg3 & 16777215), this.c2), this.paint);
        NanoVG.nvgBeginPath(this.vg);
        NanoVG.nvgCircle(this.vg, arg0, arg1, arg2);
        NanoVG.nvgFillPaint(this.vg, this.paint);
        NanoVG.nvgFill(this.vg);
    }

    public float text(String arg0, float arg1, float arg2, float arg3, int arg4, int arg5, String arg6) {
        if (arg6 != null) {
            if (arg6.isEmpty()) {
            }
        }
        return 0.0F;
        NanoVG.nvgFontFace(this.vg, arg0);
        NanoVG.nvgFontSize(this.vg, arg1);
        NanoVG.nvgTextAlign(this.vg, arg4);
        NanoVG.nvgFillColor(this.vg, this.col(arg5, this.c1));
        if (this.crisp) {
            NanoVG.nvgCurrentTransform(this.vg, this.xf);
            if (Math.abs(this.xf[1]) < 1.0E-5F) {
                if (Math.abs(this.xf[2]) < 1.0E-5F) {
                    if (Math.abs(this.xf[0]) > 1.0E-4F) {
                        if (Math.abs(this.xf[3]) > 1.0E-4F) {
                            arg2 = ((((float) Math.round(((this.xf[0] * arg2) + this.xf[4]))) - this.xf[4]) / this.xf[0]);
                            arg3 = ((((float) Math.round(((this.xf[3] * arg3) + this.xf[5]))) - this.xf[5]) / this.xf[3]);
                        }
                        return (NanoVG.nvgText(this.vg, arg2, arg3, arg6) - arg2);
                    }
                }
            }
        }
    }

    public float textWidth(String arg0, float arg1, String arg2) {
        if (arg2 != null) {
            if (arg2.isEmpty()) {
            }
        }
        return 0.0F;
        NanoVG.nvgFontFace(this.vg, arg0);
        NanoVG.nvgFontSize(this.vg, arg1);
        NanoVG.nvgTextAlign(this.vg, 65);
        return NanoVG.nvgTextBounds(this.vg, 0.0F, 0.0F, arg2, this.bounds);
    }

}
