package dev.sim0n.stressbot.bot.internal.listener.login;

import dev.sim0n.stressbot.bot.Bot;
import dev.sim0n.stressbot.packet.internal.login.clientbound.SEncryptionRequest;
import dev.sim0n.stressbot.packet.internal.login.serverbound.CEncryptionResponse;
import dev.sim0n.stressbot.packet.internal.listener.LoginEnsuredFilteredPacketListener;
import dev.sim0n.stressbot.util.crypt.CryptManager;
import io.netty.channel.ChannelHandlerContext;

import javax.crypto.SecretKey;
import java.security.PublicKey;

/**
 * @author sim0n
 */
public class EncryptionRequestListener extends LoginEnsuredFilteredPacketListener<SEncryptionRequest> {

    @Override
    public void onPacketReceive(ChannelHandlerContext ctx, Bot bot, SEncryptionRequest packet) {
        PublicKey publicKey = packet.getPublicKey();
        SecretKey secretkey = CryptManager.createNewSharedKey();

        CEncryptionResponse encryptionResponse = new CEncryptionResponse();
        {
            encryptionResponse.setSecretKeyEncrypted(CryptManager.encryptData(publicKey, secretkey.getEncoded()));
            encryptionResponse.setVerifyTokenEncrypted(CryptManager.encryptData(publicKey, packet.getVerifyToken()));
        }

        ctx.writeAndFlush(encryptionResponse.toBuffer(ctx));
    }
}
