package dev.sim0n.stressbot.bot;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author sim0n
 */
public interface BotController<Buf extends ByteBuf> {
    void start(String address, int port, int botCount, long loginDelay);

    void makeBot(Consumer<ChannelHandlerContext> connectAction, Consumer<ChannelHandlerContext> disconnectAction);

    List<Bot> getBots();
}
