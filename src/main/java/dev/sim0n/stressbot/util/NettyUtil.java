package dev.sim0n.stressbot.util;

import dev.sim0n.stressbot.packet.AbstractPacket;
import io.netty.channel.ChannelHandlerContext;
import lombok.experimental.UtilityClass;

/**
 * @author sim0n
 */
@UtilityClass
public class NettyUtil {
    public void sendPacket(ChannelHandlerContext ctx, AbstractPacket packet) {
        ctx.writeAndFlush(packet.toBuffer(ctx));
    }
}
