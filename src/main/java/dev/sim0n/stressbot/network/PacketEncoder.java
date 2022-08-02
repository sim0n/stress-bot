package dev.sim0n.stressbot.network;

import dev.sim0n.stressbot.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author sim0n
 */
public class PacketEncoder extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf2);
        packetBuffer.writeVarIntToBuffer(byteBuf.readableBytes());
        packetBuffer.writeBytes(byteBuf);
    }
}
