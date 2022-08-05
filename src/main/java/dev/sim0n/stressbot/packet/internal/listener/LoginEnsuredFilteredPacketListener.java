package dev.sim0n.stressbot.packet.internal.listener;

import dev.sim0n.stressbot.packet.connection.ConnectionState;
import dev.sim0n.stressbot.packet.AbstractPacket;

/**
 * @author sim0n
 */
public abstract class LoginEnsuredFilteredPacketListener<Packet extends AbstractPacket> extends FilteredPacketListener<Packet> {
    public LoginEnsuredFilteredPacketListener() {
        super(bot -> bot.getConnectionState() == ConnectionState.LOGIN);
    }
}
