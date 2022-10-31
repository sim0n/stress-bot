package dev.sim0n.stressbot.bot.internal.controller;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.bot.BotRepository;
import dev.sim0n.stressbot.bot.action.ConnectAction;
import dev.sim0n.stressbot.bot.action.DisconnectAction;
import dev.sim0n.stressbot.bot.controller.BotController;
import dev.sim0n.stressbot.bot.factory.BotFactory;
import dev.sim0n.stressbot.network.PacketDecoder;
import dev.sim0n.stressbot.network.PacketEncoder;
import dev.sim0n.stressbot.network.PacketHandler;
import dev.sim0n.stressbot.util.PacketBuffer;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author sim0n
 */
@RequiredArgsConstructor
public class SimpleBotController<Buf extends ByteBuf> implements BotController<Buf> {
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private final BotRepository repo = new BotRepository();

    private final String address;
    private final int port;
    private final BotFactory<Buf> botFactory;
    private final String usernamePrefix;

    @Override
    public void start(String address, int port, int botCount, long loginDelay) {
        for (int i = 0; i < botCount; i++) {
            String name = this.usernamePrefix + "_" + i;

            Consumer<ChannelHandlerContext> connectAction = new ConnectAction(address, port, name);
            Consumer<ChannelHandlerContext> disconnectAction = new DisconnectAction(name);

            this.makeBot(connectAction, disconnectAction);
            try {
                Thread.sleep(loginDelay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void makeBot(Consumer<ChannelHandlerContext> connectAction, Consumer<ChannelHandlerContext> disconnectAction) {
        Bot bot = this.botFactory.makeBot(connectAction, disconnectAction, this.repo);

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast("timeout", new ReadTimeoutHandler(30))
                                    .addLast("decoder", new PacketDecoder())
                                    .addLast("encoder", new PacketEncoder())
                                    .addLast("packet_handler", new PacketHandler(bot));
                        }
                    });

            bootstrap.connect(address, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Bot> getBots() {
        return this.repo.getBots();
    }

    public static AddAddress builder() {
        return address -> port -> factory -> usernamePrefix -> new SimpleBotController<>(address, port, factory, usernamePrefix);
    }

    public interface AddAddress {
        AddPort address(String address);
    }

    public interface AddPort {
        AddFactory<PacketBuffer> port(int port);
    }

    public interface AddFactory<Buf extends ByteBuf> {
        AddUsernamePrefix<Buf> factory(BotFactory<Buf> factory);
    }

    public interface AddUsernamePrefix<Buf extends ByteBuf> {
        BotController<Buf> usernamePrefix(String usernamePrefix);
    }
}
