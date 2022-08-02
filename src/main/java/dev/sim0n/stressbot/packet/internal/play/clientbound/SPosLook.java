package dev.sim0n.stressbot.packet.internal.play.clientbound;

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
public class SPosLook extends AbstractPacket {
    private double x, y, z;

    private float yaw, pitch;

    @Override
    public void decode(PacketBuffer buffer) {
        this.x = buffer.readDouble();
        this.y = buffer.readDouble();
        this.z = buffer.readDouble();

        this.yaw = buffer.readFloat();
        this.pitch = buffer.readFloat();
    }

    @Override
    public void encode(PacketBuffer buffer) {

    }
}
