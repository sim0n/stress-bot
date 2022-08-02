package dev.sim0n.stressbot.packet;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.packet.AbstractPacket;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 */
public interface PacketListener<Packet extends AbstractPacket> {
    void onPacketReceive(ChannelHandlerContext ctx, Bot bot, Packet packet);
}
