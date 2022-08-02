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
public class CEntityAction extends AbstractPacket {
    private Action action;

    @Override
    public void decode(PacketBuffer buffer) {

    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeVarIntToBuffer(0);
        buffer.writeEnumValue(this.action);
        buffer.writeVarIntToBuffer(0);
    }

    public enum Action
    {
        START_SNEAKING,
        STOP_SNEAKING,
        STOP_SLEEPING,
        START_SPRINTING,
        STOP_SPRINTING,
        RIDING_JUMP,
        OPEN_INVENTORY;
    }
}
