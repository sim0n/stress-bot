package dev.sim0n.stressbot.packet.internal.listener;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.packet.AbstractPacket;
import dev.sim0n.stressbot.packet.PacketListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;

/**
 * @author sim0n
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class FilteredPacketListener<Packet extends AbstractPacket> implements PacketListener<Packet> {
    private Predicate<Bot> filter = bot -> true;
}
