package dev.sim0n.stressbot.bot.internal.listener.play;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.packet.PacketRepository;
import dev.sim0n.stressbot.packet.internal.play.clientbound.SKeepAlive;
import dev.sim0n.stressbot.packet.internal.play.serverbound.CKeepAlive;
import dev.sim0n.stressbot.packet.internal.listener.PlayEnsuredFilteredPacketListener;
import dev.sim0n.stressbot.util.NettyUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 */
public class KeepAliveListener extends PlayEnsuredFilteredPacketListener<SKeepAlive> {

    @Override
    public void onPacketReceive(ChannelHandlerContext ctx, Bot bot, SKeepAlive packet) {
        CKeepAlive clientKeepAlive = PacketRepository.PLAY.makePacket(CKeepAlive.class);
        {
            clientKeepAlive.setKey(packet.getKey());
        }
        NettyUtil.sendPacket(ctx, clientKeepAlive);
    }
}
