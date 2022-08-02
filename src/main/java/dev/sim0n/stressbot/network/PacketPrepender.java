package dev.sim0n.stressbot.network;

import dev.sim0n.stressbot.StressBot;
import dev.sim0n.stressbot.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author sim0n
 */
public class PacketPrepender extends MessageToByteEncoder<ByteBuf> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int i = byteBuf.readableBytes();
        int j = PacketBuffer.getVarIntSize(i);

        if (j > 3) {
            StressBot.LOGGER.warning("unable to fit " + i + "into " + 3);
        } else {
            PacketBuffer packetbuffer = new PacketBuffer(byteBuf2);
            packetbuffer.ensureWritable(j + i);
            packetbuffer.writeVarIntToBuffer(i);
            packetbuffer.writeBytes(byteBuf, byteBuf.readerIndex(), i);
        }
    }
}
