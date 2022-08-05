package dev.sim0n.stressbot.bot.task;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.bot.factory.TaskFactory;
import dev.sim0n.stressbot.bot.internal.factory.task.SimpleTaskFactory;
import dev.sim0n.stressbot.bot.internal.task.MoveTask;
import dev.sim0n.stressbot.bot.internal.task.SwingTask;
import dev.sim0n.stressbot.util.ClassToInstanceMapBuilder;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sim0n
 */
@Getter
@RequiredArgsConstructor
public class Scheduler {
    private static final SecureRandom RANDOM = new SecureRandom();

    private final TaskFactory taskFactory;

    private final ClassToInstanceMapBuilder<Task> tasks;

    public Scheduler(TaskFactory taskFactory) {
        this.taskFactory = taskFactory;

        this.tasks = new ClassToInstanceMapBuilder<Task>()
                .put(taskFactory.makeTask("move")
                        .setDelay(1))
                .put(taskFactory.makeTask("swing")
                        .setDelay(20)
                        .setRandom(true))
                .build();
    }

    public void tick(int tick, ChannelHandlerContext ctx, Bot bot) {
        this.tasks.values()
                .stream()
                .filter(task -> tick > task.getNextRun())
                .forEach(task -> {
                    int delay = task.getDelay();

                    task.setNextRun(tick + (task.isRandom() ? RANDOM.nextInt(delay) : delay));
                    task.run(ctx, bot);
                });
    }
}
