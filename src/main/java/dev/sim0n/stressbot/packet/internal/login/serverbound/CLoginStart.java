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
public class CLoginStart extends AbstractPacket {
    private String name;

    @Override
    public void decode(PacketBuffer buffer) {

    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeString(this.name);
    }
}
