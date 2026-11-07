package need.clarity.audio;

import com.sun.jna.Structure;

@Structure.FieldOrder({"maxlength", "tlength", "prebuf", "minreq", "fragsize"})
public class PaBufferAttr extends Structure {
    public int maxlength;
    public int tlength;
    public int prebuf;
    public int minreq;
    public int fragsize;

    public PaBufferAttr() {
        super();
        this.maxlength = -1;
        this.tlength = -1;
        this.prebuf = -1;
        this.minreq = -1;
        this.fragsize = -1;
    }

}
