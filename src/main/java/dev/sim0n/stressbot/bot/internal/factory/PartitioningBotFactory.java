package dev.sim0n.stressbot.bot.internal.factory;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.bot.BotRepository;
import dev.sim0n.stressbot.bot.factory.BotFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author sim0n
 */
@RequiredArgsConstructor
public class PartitioningBotFactory<Buf extends ByteBuf> implements BotFactory<Buf> {
    private final BiFunction<Consumer<ChannelHandlerContext>, Consumer<ChannelHandlerContext>, Bot> func;

    @Override
    public Bot makeBot(Consumer<ChannelHandlerContext> connectAction, Consumer<ChannelHandlerContext> disconnectAction, BotRepository repo) {
        Bot bot = this.func.apply(connectAction, disconnectAction);

        repo.getBots().add(bot);

        return bot;
    }
}
