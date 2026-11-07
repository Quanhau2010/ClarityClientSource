package need.clarity.modules.utility;

import net.minecraft.network.protocol.Packet;

public final class QueuedPacketEntry {
    final Packet packet;
    final long timestampMs;

    QueuedPacketEntry(Packet arg0) {
        this.packet = arg0;
        long var6 = System.currentTimeMillis();
        this.timestampMs = var6;
    }

}
