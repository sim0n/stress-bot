package dev.sim0n.stressbot.bot.factory;

import dev.sim0n.stressbot.bot.task.AbstractTask;

/**
 * @author sim0n
 */
public interface TaskFactory {
    AbstractTask makeTask(String task);
}
