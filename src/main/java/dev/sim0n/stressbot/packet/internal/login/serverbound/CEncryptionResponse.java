package dev.sim0n.stressbot.packet.internal.login.serverbound;

import dev.sim0n.stressbot.packet.AbstractPacket;
import dev.sim0n.stressbot.util.crypt.CryptManager;
import dev.sim0n.stressbot.util.PacketBuffer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.SecretKey;
import java.security.PublicKey;

/**
 * @author sim0n
 */
@Getter @Setter
@NoArgsConstructor
public class CEncryptionResponse extends AbstractPacket {
    private byte[] secretKeyEncrypted = new byte[0];
    private byte[] verifyTokenEncrypted = new byte[0];

    public CEncryptionResponse(SecretKey secretKey, PublicKey publicKey, byte[] verifyToken) {
        this.secretKeyEncrypted = CryptManager.encryptData(publicKey, secretKey.getEncoded());
        this.verifyTokenEncrypted = CryptManager.encryptData(publicKey, verifyToken);
    }

    @Override
    public void decode(PacketBuffer buffer) {

    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeByteArray(this.secretKeyEncrypted);
        buffer.writeByteArray(this.verifyTokenEncrypted);
    }
}
