package need.clarity.clickgui;

public interface Canvas {
    public static final int LEFT = 1;
    public static final int CENTER = 2;
    public static final int RIGHT = 4;
    public static final int TOP = 8;
    public static final int MIDDLE = 16;
    public static final int BASELINE = 64;
    public static final String REGULAR = "inter";
    public static final String MEDIUM = "inter-med";
    public static final String SEMIBOLD = "inter-sb";

    void save();

    void restore();

    void translate(float arg0, float arg1);

    void scale(float arg0);

    void alpha(float arg0);

    void scissor(float arg0, float arg1, float arg2, float arg3);

    void rect(float arg0, float arg1, float arg2, float arg3, float arg4, int arg5);

    void gradient(float arg0, float arg1, float arg2, float arg3, float arg4, int arg5, int arg6, boolean arg7);

    void stroke(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6);

    void strokeGradient(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6, int arg7);

    void shadow(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6);

    void glass(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5);

    void circle(float arg0, float arg1, float arg2, int arg3);

    void ring(float arg0, float arg1, float arg2, float arg3, int arg4);

    void arc(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5, int arg6);

    void line(float arg0, float arg1, float arg2, float arg3, float arg4, int arg5);

    void glow(float arg0, float arg1, float arg2, int arg3);

    void image(String arg0, float arg1, float arg2, float arg3, float arg4, float arg5);

    float text(String arg0, float arg1, float arg2, float arg3, int arg4, int arg5, String arg6);

    float textWidth(String arg0, float arg1, String arg2);

    default void rotate(float arg0) {
    }

    default void scale2(float arg0, float arg1) {
        this.scale(((arg0 + arg1) / 2.0F));
    }

    default boolean logo(float arg0, float arg1, float arg2, int arg3, float arg4) {
        return false;
    }

    default boolean hasSnapshot() {
        return false;
    }

    default void snapPiece(float[] arg0, float arg1, float arg2, float arg3) {
    }

    default void snapHole(float arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
    }

    default void strokePoly(float[] arg0, float arg1, int arg2) {
    }

}
