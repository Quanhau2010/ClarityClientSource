package need.clarity.audio;

import com.sun.jna.Structure;

@Structure.FieldOrder({"format", "rate", "channels"})
public class PaSampleSpec extends Structure {
    public int format;
    public int rate;
    public byte channels;

    public PaSampleSpec() {
        super();
    }

}
