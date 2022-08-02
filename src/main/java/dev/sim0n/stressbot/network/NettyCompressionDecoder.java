package dev.sim0n.stressbot.network;

import dev.sim0n.stressbot.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;

import java.util.List;
import java.util.zip.Inflater;

/**
 * @author sim0n
 */
public class NettyCompressionDecoder extends ByteToMessageDecoder {

    private final Inflater inflater = new Inflater();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() != 0) {
            PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            int i = packetBuffer.readVarIntFromBuffer();

            if (i == 0) {
                list.add(packetBuffer.readBytes(packetBuffer.readableBytes()));
            } else {
                if (i > 2097152)
                {
                    throw new DecoderException("Badly compressed packet - size of " + i + " is larger than protocol maximum of " + 2097152);
                }

                byte[] abyte = new byte[packetBuffer.readableBytes()];
                packetBuffer.readBytes(abyte);
                this.inflater.setInput(abyte);
                byte[] abyte1 = new byte[i];
                this.inflater.inflate(abyte1);
                list.add(Unpooled.wrappedBuffer(abyte1));
                this.inflater.reset();
            }
        }
    }
}

