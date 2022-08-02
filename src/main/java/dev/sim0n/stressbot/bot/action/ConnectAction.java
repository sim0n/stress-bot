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
        CHandshake handshake = PacketRepository.HANDSHAKING.makePacket(CHandshake.class);
        {
            handshake.setProtocolVersion(47); // 1.8
            handshake.setAddress(this.address);
            handshake.setPort(this.port);
            handshake.setRequestedState(2); // request login
        }
        NettyUtil.sendPacket(ctx, handshake);

        CLoginStart loginStart = PacketRepository.LOGIN.makePacket(CLoginStart.class);
        {
            loginStart.setName(this.name);
        }
        NettyUtil.sendPacket(ctx, loginStart);
    }
}
