package dev.sim0n.stressbot.packet.internal.play.serverbound;

import dev.sim0n.stressbot.util.PacketBuffer;
import lombok.NoArgsConstructor;

/**
 * @author sim0n
 */
@NoArgsConstructor
public class CPlayerPos extends CPlayer {

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);

        this.moving = true;

        super.encode(buffer);
    }
}
