package dev.sim0n.stressbot.bot.task;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.bot.internal.task.MoveTask;
import dev.sim0n.stressbot.bot.internal.task.SwingTask;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sim0n
 */
@Getter
public class Scheduler {
    private static final SecureRandom RANDOM = new SecureRandom();

    private final List<Task> tasks = new ArrayList<>();

    public Scheduler() {
        this.tasks.add(new SwingTask());
        this.tasks.add(new MoveTask());
    }

    public void tick(int tick, ChannelHandlerContext ctx, Bot bot) {
        this.tasks.forEach(task -> {
            if (tick > task.getNextRun()) {
                task.run(ctx, bot);

                int nextRun = tick + (task.isRandom() ? RANDOM.nextInt(task.getDelay()) : task.getDelay());

                task.setNextRun(nextRun);
            }
        });
    }
}
