package dev.sim0n.stressbot.packet;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 */
public interface Connectable {
    void connect(ChannelHandlerContext ctx);

    void disconnect(ChannelHandlerContext ctx);
}
