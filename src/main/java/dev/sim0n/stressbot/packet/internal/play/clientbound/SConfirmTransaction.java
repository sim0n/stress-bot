package dev.sim0n.stressbot.packet.internal.play.clientbound;

import dev.sim0n.stressbot.packet.AbstractPacket;
import dev.sim0n.stressbot.util.PacketBuffer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author sim0n
 */
@Getter
@Setter
@NoArgsConstructor
public class SConfirmTransaction extends AbstractPacket {
    private int windowId;
    private short uid;
    private boolean accepted;

    @Override
    public void decode(PacketBuffer buffer) {
        this.windowId = buffer.readByte();
        this.uid = buffer.readShort();
        this.accepted = buffer.readBoolean();
    }

    @Override
    public void encode(PacketBuffer buffer) {

    }
}
