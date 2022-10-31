package dev.sim0n.stressbot;

import dev.sim0n.stressbot.bot.controller.BotController;
import dev.sim0n.stressbot.bot.internal.controller.SimpleBotController;
import dev.sim0n.stressbot.bot.internal.factory.SimpleBotFactory;
import dev.sim0n.stressbot.command.CommandManager;
import dev.sim0n.stressbot.command.internal.bot.BotCommands;
import dev.sim0n.stressbot.runnable.ConsoleRunnable;
import dev.sim0n.stressbot.runnable.TickLoopRunnable;
import dev.sim0n.stressbot.util.PacketBuffer;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author sim0n
 */
@Getter
public class StressBot {
    public static final Logger LOGGER = Logger.getLogger("stress-bot");

    @Getter
    public static StressBot instance;

    private final CommandManager commandManager = new CommandManager();

    private BotController<PacketBuffer> botController;

    private final String address;
    private final int port;
    private final int botCount;
    private final long loginDelay;
    private final String usernamePrefix;

    public StressBot(String address, int port, int botCount, int loginDelay, String usernamePrefix) {
        this.address = address;
        this.port = port;
        this.botCount = botCount;
        this.loginDelay = loginDelay;
        this.usernamePrefix = usernamePrefix;

        LOGGER.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter() {
            private static final String format = "[%1$s] [%2$s] %3$s:%4$s %5$s %n";

            @Override
            public synchronized String format(LogRecord record) {
                return String.format(format,
                        new SimpleDateFormat("HH:mm:ss").format(new Date(record.getMillis())), record.getLevel().getName(), record.getSourceClassName(), record.getSourceMethodName(), record.getMessage());
            }
        });

        LOGGER.addHandler(handler);

        instance = this;
    }

    public void start() {
        System.out.println("Welcome to Stress Bot.");
        System.out.println("Bot Count: " + botCount + ", Login Delay: " + loginDelay);
        //System.out.println("Type \"help\" for a list of available commands.");
        System.out.println();

        this.registerBots();
        this.registerConsole();
        this.registerCommands();
        this.registerTickLoop();
    }

    private void registerBots() {
        this.botController = SimpleBotController.builder()
                .address(this.address)
                .port(this.port)
                .factory(SimpleBotFactory.INSTANCE)
                .usernamePrefix(this.usernamePrefix);

        this.botController.start(this.address, this.port, this.botCount, this.loginDelay);
    }

    private void registerCommands() {
        this.commandManager.registerCommands(new BotCommands());
    }

    private void registerTickLoop() {
        Thread thread = new Thread(new TickLoopRunnable());
        thread.start();
    }

    private void registerConsole() {
        Thread thread = new Thread(new ConsoleRunnable());
        thread.start();
    }
}
