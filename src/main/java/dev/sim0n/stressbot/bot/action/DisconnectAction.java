package dev.sim0n.stressbot.bot.action;

import dev.sim0n.stressbot.StressBot;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

/**
 * @author sim0n
 */
@RequiredArgsConstructor
public class DisconnectAction implements Consumer<ChannelHandlerContext> {
    private final String name;

    @Override
    public void accept(ChannelHandlerContext ctx) {
        StressBot.LOGGER.info(name + " disconnected.");
    }
}
