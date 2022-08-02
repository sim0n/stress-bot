package dev.sim0n.stressbot.bot;

import dev.sim0n.stressbot.packet.Connectable;
import dev.sim0n.stressbot.packet.PacketListener;
import dev.sim0n.stressbot.util.location.Location;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 */
public interface Bot extends Connectable, PacketListener {
    void tick();

    BotConnectionState getConnectionState();

    void setConnectionState(BotConnectionState state);

    void setContext(ChannelHandlerContext ctx);

    Location getLocation();

    void updateLocation(double x, double y, double z, float yaw, float pitch);

    void updateLocation(double x, double y, double z, float yaw, float pitch, boolean onGround);
}
