package dev.sim0n.stressbot.packet;

import dev.sim0n.stressbot.util.PacketBuffer;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

/**
 * @author sim0n
 */
@Getter @Setter
public abstract class AbstractPacket {
    private int packetId;

    public abstract void decode(PacketBuffer buffer);

    public abstract void encode(PacketBuffer buffer);

    public PacketBuffer toBuffer(ChannelHandlerContext ctx) {
        PacketBuffer packetBuffer = new PacketBuffer(ctx.alloc().buffer());
        packetBuffer.writeVarIntToBuffer(this.packetId);

        this.encode(packetBuffer);

        return packetBuffer;
    }
}
