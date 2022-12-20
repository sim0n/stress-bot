package dev.sim0n.stressbot.bot.action;

import dev.sim0n.stressbot.packet.PacketRepository;
import dev.sim0n.stressbot.packet.internal.login.serverbound.CHandshake;
import dev.sim0n.stressbot.packet.internal.login.serverbound.CLoginStart;
import dev.sim0n.stressbot.util.NettyUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

/**
 * @author sim0n
 */
@RequiredArgsConstructor
public class ConnectAction implements Consumer<ChannelHandlerContext> {
    private final String address;
    private final int port;
    private final String name;

    @Override
    public void accept(ChannelHandlerContext ctx) {
        NettyUtil.sendPacket(ctx, PacketRepository.HANDSHAKING, CHandshake.class, packet -> {
            packet.setProtocolVersion(47); // 1.8
            packet.setAddress(this.address);
            packet.setPort(this.port);
            packet.setRequestedState(2); // request login
        });

        NettyUtil.sendPacket(ctx, PacketRepository.LOGIN, CLoginStart.class, packet -> {
            packet.setName(this.name);
        });
    }
}
