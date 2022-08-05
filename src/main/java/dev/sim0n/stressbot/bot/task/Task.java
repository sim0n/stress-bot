package dev.sim0n.stressbot.bot.task;

import dev.sim0n.stressbot.bot.Bot;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 */
public interface Task {

    void run(ChannelHandlerContext ctx, Bot bot);

    int getDelay();

    Task setDelay(int delay);

    boolean isRandom();

    Task setRandom(boolean random);

    int getNextRun();

    void setNextRun(int tick);
}
