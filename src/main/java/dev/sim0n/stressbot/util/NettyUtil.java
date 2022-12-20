package dev.sim0n.stressbot.util;

import dev.sim0n.stressbot.packet.AbstractPacket;
import dev.sim0n.stressbot.packet.PacketRepository;
import io.netty.channel.ChannelHandlerContext;
import lombok.experimental.UtilityClass;

import java.util.function.Consumer;

/**
 * @author sim0n
 */
@UtilityClass
public class NettyUtil {
    public void sendPacket(ChannelHandlerContext ctx, AbstractPacket packet) {
        ctx.writeAndFlush(packet.toBuffer(ctx));
    }

    public <T extends AbstractPacket> void sendPacket(ChannelHandlerContext ctx, T packet, Consumer<T> consumer) {
        consumer.accept(packet);

        ctx.writeAndFlush(packet.toBuffer(ctx));
    }

    public <T extends AbstractPacket> void sendPacket(ChannelHandlerContext ctx, PacketRepository type, Class<T> clazz, Consumer<T> consumer) {
        T packet = type.makePacket(clazz);

        consumer.accept(packet);
        ctx.writeAndFlush(packet.toBuffer(ctx));
    }
}
