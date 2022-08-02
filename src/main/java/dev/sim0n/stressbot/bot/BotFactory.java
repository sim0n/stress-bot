package dev.sim0n.stressbot.bot;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.function.Consumer;

/**
 * @author sim0n
 */
public interface BotFactory<Buf extends ByteBuf> {
    Bot makeBot(Consumer<ChannelHandlerContext> connectAction, Consumer<ChannelHandlerContext> disconnectAction);
}
