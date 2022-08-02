package dev.sim0n.stressbot.bot.internal.listener.login;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.network.NettyCompressionDecoder;
import dev.sim0n.stressbot.network.NettyCompressionEncoder;
import dev.sim0n.stressbot.packet.internal.login.clientbound.SSetCompression;
import dev.sim0n.stressbot.packet.internal.listener.LoginEnsuredFilteredPacketListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 */
public class SetCompressionListener extends LoginEnsuredFilteredPacketListener<SSetCompression> {

    @Override
    public void onPacketReceive(ChannelHandlerContext ctx, Bot bot, SSetCompression packet) {
        ctx.pipeline()
                .addAfter("decoder", "compression-decoder", new NettyCompressionDecoder())
                .addAfter("encoder", "compression-encoder", new NettyCompressionEncoder());
    }
}
