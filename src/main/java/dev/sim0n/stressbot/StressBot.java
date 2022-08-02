package dev.sim0n.stressbot;

import dev.sim0n.stressbot.bot.BotController;
import dev.sim0n.stressbot.bot.SimpleBotController;
import dev.sim0n.stressbot.bot.SimpleBotFactory;
import dev.sim0n.stressbot.runnable.TickLoopRunnable;
import dev.sim0n.stressbot.util.PacketBuffer;
import lombok.Getter;

import java.util.logging.Logger;

/**
 * @author sim0n
 */
@Getter
public class StressBot {
    public static final Logger LOGGER = Logger.getLogger("stress-bot");

    @Getter
    public static StressBot instance;

    private BotController<PacketBuffer> botController;

    private final String address;
    private final int port;
    private final int botCount;
    private final long loginDelay;

    public StressBot(String address, int port, int botCount, int loginDelay) {
        this.address = address;
        this.port = port;
        this.botCount = botCount;
        this.loginDelay = loginDelay;

        instance = this;
    }

    public void start() {
        System.out.println("Welcome to Stress Bot.");
        System.out.println("Bot Count: " + botCount + ", Login Delay: " + loginDelay);
        //System.out.println("Type \"help\" for a list of available commands.");
        System.out.println();

        this.registerBots();
        this.registerTickLoop();
        this.registerConsole();
    }

    private void registerBots() {
        this.botController = SimpleBotController.builder()
                .address(this.address)
                .port(this.port)
                .factory(SimpleBotFactory.INSTANCE);

        this.botController.start(this.address, this.port, this.botCount, this.loginDelay);
    }

    private void registerTickLoop() {
        Thread thread = new Thread(() -> new TickLoopRunnable().run());
        thread.start();
    }

    private void registerConsole() {
        Thread thread = new Thread(() -> {});
        thread.start();
    }
}
