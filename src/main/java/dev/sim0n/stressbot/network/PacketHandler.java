package dev.sim0n.stressbot.network;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.packet.AbstractPacket;
import dev.sim0n.stressbot.packet.PacketDirection;
import dev.sim0n.stressbot.packet.PacketRepository;
import dev.sim0n.stressbot.util.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;

/**
 * @author sim0n
 */
@RequiredArgsConstructor
public class PacketHandler extends ChannelInboundHandlerAdapter {
    private final Bot bot;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.bot.connect(ctx);
        this.bot.setContext(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        this.bot.disconnect(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            PacketBuffer packetBuffer = new PacketBuffer((ByteBuf) msg);

            if (packetBuffer.readableBytes() == 0)
                return;

            int packetId = packetBuffer.readVarIntFromBuffer();
            //StressBot.LOGGER.info("receiving 0x" + Integer.toHexString(packetId) +", " + packetBuffer.readableBytes());

            AbstractPacket packet = null;
            switch (this.bot.getConnectionState()) {
                case LOGIN:
                    if (packetId == 0) {
                        ctx.close();
                    }

                    packet = PacketRepository.LOGIN.makePacket(PacketDirection.CLIENTBOUND, packetId);

                    break;

                case PLAY:
                    packet = PacketRepository.PLAY.makePacket(PacketDirection.CLIENTBOUND, packetId);
                    break;
            }

            if (packet != null) {
                packet.decode(packetBuffer);

                this.bot.onPacketReceive(ctx, this.bot, packet);
            }
        } finally {
            ((ByteBuf) msg).release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
