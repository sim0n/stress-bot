package dev.sim0n.stressbot.packet.internal.login.clientbound;

import dev.sim0n.stressbot.packet.AbstractPacket;
import dev.sim0n.stressbot.util.PacketBuffer;
import lombok.Getter;

import java.util.UUID;

/**
 * @author sim0n
 */
@Getter
public class SLoginSuccess extends AbstractPacket {
    private UUID uuid;
    private String username;

    @Override
    public void decode(PacketBuffer buffer) {
        this.uuid = UUID.fromString(buffer.readStringFromBuffer(36));
        this.username = buffer.readStringFromBuffer(16);
    }

    @Override
    public void encode(PacketBuffer buffer) {

    }
}
