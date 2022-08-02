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
public class SKeepAlive extends AbstractPacket {
    private int key;

    @Override
    public void decode(PacketBuffer buffer) {
        this.key = buffer.readVarIntFromBuffer();
    }

    @Override
    public void encode(PacketBuffer buffer) {

    }
}
