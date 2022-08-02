package dev.sim0n.stressbot;

import dev.sim0n.stressbot.bot.BotController;
import dev.sim0n.stressbot.bot.SimpleBotController;
import dev.sim0n.stressbot.bot.SimpleBotFactory;
import dev.sim0n.stressbot.packet.PacketRepository;
import dev.sim0n.stressbot.packet.internal.login.serverbound.CHandshake;
import dev.sim0n.stressbot.packet.internal.login.serverbound.CLoginStart;
import dev.sim0n.stressbot.runnable.TickLoopRunnable;
import dev.sim0n.stressbot.util.NettyUtil;
import dev.sim0n.stressbot.util.PacketBuffer;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;

import java.util.function.Consumer;
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

        for (int i = 0; i < this.botCount; i++) {
            String name = "rowin_" + i;

            Consumer<ChannelHandlerContext> connectAction = this.makeConnectAction(this.address, this.port, name);
            Consumer<ChannelHandlerContext> disconnectAction = ctx -> LOGGER.info(name + " Disconnected");

            this.botController.makeBot(connectAction, disconnectAction);
            try {
                Thread.sleep(this.loginDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Consumer<ChannelHandlerContext> makeConnectAction(String address, int port, String name) {
        return ctx -> {
            CHandshake handshake = PacketRepository.HANDSHAKING.makePacket(CHandshake.class);
            {
                handshake.setProtocolVersion(47); // 1.8
                handshake.setAddress(address);
                handshake.setPort(port);
                handshake.setRequestedState(2); // request login
            }
            NettyUtil.sendPacket(ctx, handshake);

            CLoginStart loginStart = PacketRepository.LOGIN.makePacket(CLoginStart.class);
            {
                loginStart.setName(name);
            }
            NettyUtil.sendPacket(ctx, loginStart);
        };
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
