package dev.sim0n.stressbot.test;

import dev.sim0n.stressbot.command.CommandManager;
import dev.sim0n.stressbot.command.argument.ArgumentParser;
import dev.sim0n.stressbot.command.internal.bot.BotCommands;
import org.junit.jupiter.api.Test;

/**
 * @author sim0n
 */
public class TestCommandHandler {

    @Test
    void executeCommand() {
        CommandManager commandManager = new CommandManager();
        commandManager.registerCommands(new BotCommands());

        commandManager.execute("bot swing");
    }
}
