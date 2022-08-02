package dev.sim0n.stressbot.packet.internal.play.serverbound;

import dev.sim0n.stressbot.util.PacketBuffer;
import lombok.NoArgsConstructor;

/**
 * @author sim0n
 */
@NoArgsConstructor
public class CPlayerLook extends CPlayer {

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeFloat(this.yaw);
        buffer.writeFloat(this.pitch);

        this.rotating = true;

        super.encode(buffer);
    }
}
