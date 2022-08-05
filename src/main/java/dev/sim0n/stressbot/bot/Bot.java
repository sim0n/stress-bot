package dev.sim0n.stressbot.bot;

import dev.sim0n.stressbot.packet.connection.Connectable;
import dev.sim0n.stressbot.packet.PacketListener;
import dev.sim0n.stressbot.trait.Tickable;
import dev.sim0n.stressbot.util.location.Location;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author sim0n
 */
public interface Bot extends Tickable, Connectable, PacketListener {

    void setContext(ChannelHandlerContext ctx);

    void sendMessage(String message);

    Location getLocation();

    void updateLocation(double x, double y, double z, float yaw, float pitch);

    void updateLocation(double x, double y, double z, float yaw, float pitch, boolean onGround);

    void setMoveSpeed(double value);

    double getMoveSpeed();

    void toggleTask(String task);
}
