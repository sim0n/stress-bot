package dev.sim0n.stressbot.packet.internal.login.serverbound;

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
public class CHandshake extends AbstractPacket {
    private int protocolVersion;

    private String address;

    private int port;
    private int requestedState;

    @Override
    public void decode(PacketBuffer buffer) {

    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeVarIntToBuffer(this.protocolVersion);
        buffer.writeString(this.address);
        buffer.writeShort(this.port);
        buffer.writeVarIntToBuffer(this.requestedState);
    }
}
