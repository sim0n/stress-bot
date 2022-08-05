package dev.sim0n.stressbot.bot.internal.factory.task;

import dev.sim0n.stressbot.bot.factory.TaskFactory;
import dev.sim0n.stressbot.bot.internal.task.MoveTask;
import dev.sim0n.stressbot.bot.internal.task.SwingTask;
import dev.sim0n.stressbot.bot.task.AbstractTask;

/**
 * @author sim0n
 */
public enum SimpleTaskFactory implements TaskFactory {
    INSTANCE;

    @Override
    public AbstractTask makeTask(String task) {
        switch (task) {
            case "move":
                return new MoveTask();

            case "swing":
                return new SwingTask();

        }

        return null;
    }
}
