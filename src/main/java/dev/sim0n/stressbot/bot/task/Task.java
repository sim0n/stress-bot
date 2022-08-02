package dev.sim0n.stressbot.bot.task;

import dev.sim0n.stressbot.bot.Bot;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author sim0n
 */
@Getter @Setter
@RequiredArgsConstructor
public abstract class Task {
    private final int delay;
    private final boolean random;

    private int nextRun;

    public abstract void run(ChannelHandlerContext ctx, Bot bot);
}
