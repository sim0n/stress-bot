package dev.sim0n.stressbot.network;

import dev.sim0n.stressbot.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author sim0n
 */
public class NettyCompressionEncoder extends MessageToByteEncoder<ByteBuf> {
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBufDest) {
        PacketBuffer packetBuffer = new PacketBuffer(byteBufDest);
        packetBuffer.writeVarIntToBuffer(0);
        packetBuffer.writeBytes(byteBuf);

    }
}

