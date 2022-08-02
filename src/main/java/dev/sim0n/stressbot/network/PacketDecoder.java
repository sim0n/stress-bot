package dev.sim0n.stressbot.network;

import dev.sim0n.stressbot.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author sim0n
 */
public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() >= 3) {
            PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            packetBuffer.markReaderIndex();

            int length = packetBuffer.readVarIntFromBuffer();

            if (packetBuffer.readableBytes() < length) {
                packetBuffer.resetReaderIndex();
            } else {
                byte[] abyte = new byte[length];
                packetBuffer.readBytes(abyte);

                list.add(Unpooled.wrappedBuffer(abyte));
            }
        }
    }
}
