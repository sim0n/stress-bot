package dev.sim0n.stressbot.bot.internal;

import dev.sim0n.stressbot.bot.*;
import dev.sim0n.stressbot.bot.internal.listener.login.EncryptionRequestListener;
import dev.sim0n.stressbot.bot.internal.listener.login.LoginSuccessListener;
import dev.sim0n.stressbot.bot.internal.listener.login.SetCompressionListener;
import dev.sim0n.stressbot.bot.internal.listener.play.ConfirmTransactionListener;
import dev.sim0n.stressbot.bot.internal.listener.play.KeepAliveListener;
import dev.sim0n.stressbot.bot.internal.listener.play.PosLookListener;
import dev.sim0n.stressbot.packet.AbstractPacket;
import dev.sim0n.stressbot.packet.PacketDirection;
import dev.sim0n.stressbot.packet.internal.login.clientbound.SEncryptionRequest;
import dev.sim0n.stressbot.packet.internal.login.clientbound.SLoginSuccess;
import dev.sim0n.stressbot.packet.internal.login.clientbound.SSetCompression;
import dev.sim0n.stressbot.packet.internal.play.clientbound.SConfirmTransaction;
import dev.sim0n.stressbot.packet.internal.play.clientbound.SKeepAlive;
import dev.sim0n.stressbot.packet.internal.play.clientbound.SPosLook;
import dev.sim0n.stressbot.packet.PacketListener;
import dev.sim0n.stressbot.packet.PacketRepository;
import dev.sim0n.stressbot.packet.internal.play.serverbound.CPlayer;
import dev.sim0n.stressbot.packet.internal.play.serverbound.CPlayerLook;
import dev.sim0n.stressbot.packet.internal.play.serverbound.CPlayerPos;
import dev.sim0n.stressbot.packet.internal.play.serverbound.CPlayerPosLook;
import dev.sim0n.stressbot.bot.task.Scheduler;
import dev.sim0n.stressbot.util.NettyUtil;
import dev.sim0n.stressbot.util.location.Location;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author sim0n
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Getter @Setter
public class SimpleBot implements Bot {
    private final Consumer<ChannelHandlerContext> connectAction;
    private final Consumer<ChannelHandlerContext> disconnectAction;

    private final Map<Integer, PacketListener> listeners = new HashMap<>();

    private BotConnectionState connectionState = BotConnectionState.LOGIN;
    private ChannelHandlerContext context;

    private final Scheduler scheduler = new Scheduler();

    private Location lastLocation = new Location();
    private Location location = new Location();

    private int ticksExisted;

    private int positionUpdateTicks;

    public SimpleBot(Consumer<ChannelHandlerContext> connectAction, Consumer<ChannelHandlerContext> disconnectAction) {
        this.connectAction = connectAction;
        this.disconnectAction = disconnectAction;

        // LOGIN
        this.listeners.put(PacketRepository.LOGIN.getPacketId(PacketDirection.CLIENTBOUND, SEncryptionRequest.class), new EncryptionRequestListener());
        this.listeners.put(PacketRepository.LOGIN.getPacketId(PacketDirection.CLIENTBOUND, SLoginSuccess.class), new LoginSuccessListener());
        this.listeners.put(PacketRepository.LOGIN.getPacketId(PacketDirection.CLIENTBOUND, SSetCompression.class), new SetCompressionListener());

        // PLAY
        this.listeners.put(PacketRepository.PLAY.getPacketId(PacketDirection.CLIENTBOUND, SKeepAlive.class), new KeepAliveListener());
        this.listeners.put(PacketRepository.PLAY.getPacketId(PacketDirection.CLIENTBOUND, SPosLook.class), new PosLookListener());
        this.listeners.put(PacketRepository.PLAY.getPacketId(PacketDirection.CLIENTBOUND, SConfirmTransaction.class), new ConfirmTransactionListener());
    }

    @Override
    public void onPacketReceive(ChannelHandlerContext ctx, Bot bot, AbstractPacket packet) {
        this.listeners.get(packet.getPacketId()).onPacketReceive(ctx, bot, packet);
    }

    @Override
    public void connect(ChannelHandlerContext ctx) {
        this.connectAction.accept(ctx);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx) {
        this.disconnectAction.accept(ctx);
    }

    @Override
    public void tick() {
        if (this.context == null || this.connectionState != BotConnectionState.PLAY)
            return;

        ++this.ticksExisted;

        this.scheduler.tick(this.ticksExisted, this.context, this);

        boolean shouldUpdatePosition = this.location.distance(this.lastLocation) > 0.03 || this.positionUpdateTicks >= 20;
        boolean shouldUpdateRotation = this.location.getPitch() != this.lastLocation.getPitch() || this.location.getYaw() != this.lastLocation.getYaw();

        CPlayer packet;

        if (shouldUpdatePosition && shouldUpdateRotation) {
            packet = PacketRepository.PLAY.makePacket(CPlayerPosLook.class);
        } else if (shouldUpdatePosition) {
            packet = PacketRepository.PLAY.makePacket(CPlayerPos.class);
        } else if (shouldUpdateRotation) {
            packet = PacketRepository.PLAY.makePacket(CPlayerLook.class);
        } else {
            packet = PacketRepository.PLAY.makePacket(CPlayer.class);
        }

        ++this.positionUpdateTicks;

        if (shouldUpdatePosition) {
            this.positionUpdateTicks = 0;
        }

        packet.setX(location.getX());
        packet.setY(location.getY());
        packet.setZ(location.getZ());

        packet.setYaw(location.getYaw());
        packet.setPitch(location.getPitch());

        packet.setOnGround(location.isOnGround());

        NettyUtil.sendPacket(this.context, packet);
    }

    @Override
    public void updateLocation(double x, double y, double z, float yaw, float pitch) {
        this.lastLocation = this.location.clone();

        this.location.update(x, y, z, yaw, pitch);
    }

    @Override
    public void updateLocation(double x, double y, double z, float yaw, float pitch, boolean onGround) {
        this.lastLocation = this.location.clone();

        this.location.update(x, y, z, yaw, pitch);
        this.location.setOnGround(onGround);
    }

}
