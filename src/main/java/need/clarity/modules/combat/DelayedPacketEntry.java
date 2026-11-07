package need.clarity.modules.combat;

import net.minecraft.network.protocol.Packet;

public final class DelayedPacketEntry {
    final Packet packet;
    final long timestampMs;

    DelayedPacketEntry(Packet arg0, long arg1) {
        this.packet = arg0;
        this.timestampMs = arg1;
    }

}
