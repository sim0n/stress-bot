package dev.sim0n.stressbot.bot.internal.listener.login;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.bot.BotConnectionState;
import dev.sim0n.stressbot.packet.internal.login.clientbound.SLoginSuccess;
import dev.sim0n.stressbot.packet.internal.listener.LoginEnsuredFilteredPacketListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 */
public class LoginSuccessListener extends LoginEnsuredFilteredPacketListener<SLoginSuccess> {

    @Override
    public void onPacketReceive(ChannelHandlerContext ctx, Bot bot, SLoginSuccess packet) {
        bot.setConnectionState(BotConnectionState.PLAY);
    }
}
