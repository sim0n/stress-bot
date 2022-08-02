package dev.sim0n.stressbot.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import dev.sim0n.stressbot.packet.internal.login.clientbound.SEncryptionRequest;
import dev.sim0n.stressbot.packet.internal.login.clientbound.SLoginSuccess;
import dev.sim0n.stressbot.packet.internal.login.clientbound.*;
import dev.sim0n.stressbot.packet.internal.login.serverbound.CHandshake;
import dev.sim0n.stressbot.packet.internal.login.serverbound.*;
import dev.sim0n.stressbot.packet.internal.play.clientbound.SConfirmTransaction;
import dev.sim0n.stressbot.packet.internal.play.clientbound.*;
import dev.sim0n.stressbot.packet.internal.play.serverbound.CConfirmTransaction;
import dev.sim0n.stressbot.packet.internal.play.serverbound.*;

import java.util.Map;

/**
 * @author sim0n
 */
public enum PacketRepository {
    HANDSHAKING
    {
        {
            this.registerOut(0x00, CHandshake.class);
        }
    },
    PLAY
    {
        {
            this.registerIn(0x00, SKeepAlive.class);
            this.registerIn(0x08, SPosLook.class);
            this.registerIn(0x32, SConfirmTransaction.class);

            this.registerOut(0x00, CKeepAlive.class);
            this.registerOut(0x03, CPlayer.class);
            this.registerOut(0x04, CPlayerPos.class);
            this.registerOut(0x05, CPlayerLook.class);
            this.registerOut(0x06, CPlayerPosLook.class);

            this.registerOut(0x0A, CAnimation.class);
            this.registerOut(0x0B, CEntityAction.class);

            this.registerOut(0x0F, CConfirmTransaction.class);
        }
    },
    LOGIN
    {
        {
            this.registerIn(0x01, SEncryptionRequest.class);
            this.registerIn(0x02, SLoginSuccess.class);
            this.registerIn(0x03, SSetCompression.class);

            this.registerOut(0x00, CLoginStart.class);
        }
    };

    private final Map<PacketDirection, BiMap<Integer, Class<? extends AbstractPacket>>> directionMaps = Maps.newEnumMap(PacketDirection.class);

    protected void registerIn(int id, Class<? extends AbstractPacket> clazz) {
        this.registerPacket(PacketDirection.CLIENTBOUND, id, clazz);
    }

    protected void registerOut(int id, Class<? extends AbstractPacket> clazz) {
        this.registerPacket(PacketDirection.SERVERBOUND, id, clazz);
    }

    private void registerPacket(PacketDirection direction, int id, Class<? extends AbstractPacket> clazz) {
        BiMap<Integer, Class<? extends AbstractPacket>> bimap = this.directionMaps.computeIfAbsent(direction, k -> HashBiMap.create());

        if (bimap.containsValue(clazz)) {
            String s = direction + " packet " + clazz + " is already known to ID " + bimap.inverse().get(clazz);
            throw new IllegalArgumentException(s);
        } else {
            bimap.put(id, clazz);
        }
    }

    public int getPacketId(PacketDirection direction, AbstractPacket packet) {
        return this.directionMaps.get(direction).inverse().get(packet.getClass());
    }

    public int getPacketId(PacketDirection direction, Class<? extends AbstractPacket> clazz) {
        return this.directionMaps.get(direction).inverse().get(clazz);
    }

    public <T extends AbstractPacket> T makePacket(Class<T> clazz) {
        try {
            AbstractPacket packet = clazz.newInstance();
            packet.setPacketId(this.getPacketId(PacketDirection.SERVERBOUND, packet));

            return (T) packet;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public AbstractPacket makePacket(PacketDirection direction, int id) {
        Class<? extends AbstractPacket> clazz = this.directionMaps.get(direction).get(id);

        if (clazz == null) {
            return null;
        }

        try {
            AbstractPacket packet = clazz.newInstance();
            packet.setPacketId(id);

            return packet;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
