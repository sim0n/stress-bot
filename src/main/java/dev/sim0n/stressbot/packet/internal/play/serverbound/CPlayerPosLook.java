package dev.sim0n.stressbot.packet.internal.play.serverbound;

import dev.sim0n.stressbot.util.PacketBuffer;
import lombok.NoArgsConstructor;

/**
 * @author sim0n
 */
@NoArgsConstructor
public class CPlayerPosLook extends CPlayer {

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);

        buffer.writeFloat(this.yaw);
        buffer.writeFloat(this.pitch);

        this.moving = true;
        this.rotating = true;

        super.encode(buffer);
    }
}
