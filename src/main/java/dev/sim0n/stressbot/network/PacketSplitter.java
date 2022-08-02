package dev.sim0n.stressbot.network;

import dev.sim0n.stressbot.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

/**
 * @author sim0n
 */
public class PacketSplitter extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byteBuf.markReaderIndex();
        byte[] byteArray = new byte[3];

        for(int i = 0; i < byteArray.length; ++i) {
            if (!byteBuf.isReadable()) {
                byteBuf.resetReaderIndex();
                return;
            }

            byteArray[i] = byteBuf.readByte();
            if (byteArray[i] >= 0) {
                PacketBuffer buffer = new PacketBuffer(Unpooled.wrappedBuffer(byteArray));

                try {
                    int messageLength = buffer.readVarIntFromBuffer();
                    if (byteBuf.readableBytes() >= messageLength) {
                        list.add(byteBuf.readBytes(messageLength));
                        return;
                    }

                    byteBuf.resetReaderIndex();
                } finally {
                    buffer.release();
                }

                return;
            }
        }

        throw new CorruptedFrameException("Length wider than 21-bit (3 bytes)");
    }
}
