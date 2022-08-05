package dev.sim0n.stressbot.packet.connection;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 */
public interface Connectable {
    void connect(ChannelHandlerContext ctx);

    void disconnect(ChannelHandlerContext ctx);

    ConnectionState getConnectionState();

    void setConnectionState(ConnectionState state);
}
