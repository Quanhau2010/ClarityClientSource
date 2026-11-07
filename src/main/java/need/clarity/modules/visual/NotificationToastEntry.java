package need.clarity.modules.visual;

public final class NotificationToastEntry {
    public final String labelText;
    public final String labelText1;
    public final boolean enabledState;
    public final long timestampMs;
    public final long timestampMs1;

    NotificationToastEntry(String arg0, String arg1, boolean arg2, long arg3) {
        long var10 = System.currentTimeMillis();
        this(arg0, arg1, arg2, var10, arg3);
    }

    NotificationToastEntry(String arg0, String arg1, boolean arg2, long arg3, long arg4) {
        this.labelText = arg0;
        this.labelText1 = arg1;
        this.enabledState = arg2;
        this.timestampMs = arg3;
        this.timestampMs1 = arg4;
    }

}
