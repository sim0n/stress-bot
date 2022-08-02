package dev.sim0n.stressbot.packet.internal.login.clientbound;

import dev.sim0n.stressbot.packet.AbstractPacket;
import dev.sim0n.stressbot.util.crypt.CryptManager;
import dev.sim0n.stressbot.util.PacketBuffer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.PublicKey;

/**
 * @author sim0n
 */
@Getter @Setter
@NoArgsConstructor
public class SEncryptionRequest extends AbstractPacket {
    private PublicKey publicKey;
    private byte[] verifyToken;

    @Override
    public void decode(PacketBuffer buffer) {
        buffer.readStringFromBuffer(20);

        this.publicKey = CryptManager.decodePublicKey(buffer.readByteArray());
        this.verifyToken = buffer.readByteArray();
    }

    @Override
    public void encode(PacketBuffer buffer) {

    }
}
