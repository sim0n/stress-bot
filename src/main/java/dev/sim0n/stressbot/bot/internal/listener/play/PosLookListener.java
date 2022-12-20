package dev.sim0n.stressbot.bot.internal.listener.play;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.packet.PacketRepository;
import dev.sim0n.stressbot.packet.internal.play.clientbound.SPosLook;
import dev.sim0n.stressbot.packet.internal.play.serverbound.CEntityAction;
import dev.sim0n.stressbot.packet.internal.play.serverbound.CPlayerPosLook;
import dev.sim0n.stressbot.packet.internal.listener.PlayEnsuredFilteredPacketListener;
import dev.sim0n.stressbot.util.once.BiCallOnce;
import dev.sim0n.stressbot.util.NettyUtil;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 */
public class PosLookListener extends PlayEnsuredFilteredPacketListener<SPosLook> {
    private final BiCallOnce<Bot, ChannelHandlerContext> callOnce = new BiCallOnce<>((bot, ctx) -> {
        NettyUtil.sendPacket(ctx, PacketRepository.PLAY, CEntityAction.class, packet -> {
            packet.setAction(CEntityAction.Action.START_SPRINTING);
        });
    });

    @Override
    public void onPacketReceive(ChannelHandlerContext ctx, Bot bot, SPosLook packet) {
        bot.updateLocation(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch(), false);

        NettyUtil.sendPacket(ctx, PacketRepository.PLAY, CPlayerPosLook.class, playerPosLook -> {
            playerPosLook.setX(packet.getX());
            playerPosLook.setY(packet.getY());
            playerPosLook.setZ(packet.getZ());

            playerPosLook.setYaw(packet.getYaw());
            playerPosLook.setPitch(packet.getPitch());

            playerPosLook.setOnGround(false);
        });

        // only send the sprinting state once
        this.callOnce.call(bot, ctx);
    }
}
