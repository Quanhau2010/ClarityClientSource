package need.clarity.audio;

import com.sun.jna.Library;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface PulseSimpleLib extends Library {
    public static final int PA_STREAM_RECORD = 2;
    public static final int PA_SAMPLE_S16LE = 3;

    Pointer pa_simple_new(String arg0, String arg1, int arg2, String arg3, String arg4, PaSampleSpec arg5, Pointer arg6, PaBufferAttr arg7, IntByReference arg8);

    int pa_simple_read(Pointer arg0, byte[] arg1, NativeLong arg2, IntByReference arg3);

    void pa_simple_free(Pointer arg0);

}
