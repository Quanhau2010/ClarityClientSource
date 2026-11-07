package need.clarity.event;

import net.minecraft.network.protocol.Packet;

public final class EventPacketReceive {
    private final Packet packet;
    private boolean cancelled;

    public EventPacketReceive(Packet arg0) {
        this.packet = arg0;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

}
