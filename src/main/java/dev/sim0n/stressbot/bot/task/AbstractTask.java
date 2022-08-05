package dev.sim0n.stressbot.bot.task;

import dev.sim0n.stressbot.bot.Bot;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author sim0n
 */
@Getter
public abstract class AbstractTask implements Task {
    private int delay;
    private boolean random;

    @Setter
    private int nextRun;

    @Override
    public Task setDelay(int delay) {
        this.delay = delay;

        return this;
    }

    @Override
    public Task setRandom(boolean random) {
        this.random = random;

        return this;
    }
}
