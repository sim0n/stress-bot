package dev.sim0n.stressbot.packet.internal.play.serverbound;

import dev.sim0n.stressbot.packet.AbstractPacket;
import dev.sim0n.stressbot.util.PacketBuffer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author sim0n
 */
@Getter @Setter
@NoArgsConstructor
public class CPlayer extends AbstractPacket {
    protected double x, y, z;

    protected float yaw, pitch;

    protected boolean onGround;

    protected boolean moving, rotating;

    @Override
    public void decode(PacketBuffer buffer) {

    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeBoolean(this.onGround);
    }
}
