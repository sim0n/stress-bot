package dev.sim0n.stressbot.util.crypt;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CryptManager
{
    private static final Logger LOGGER = LogManager.getLogManager().getLogger("asd");

    /**
     * Generate a new shared secret AES key from a secure random source
     */
    public static SecretKey createNewSharedKey()
    {
        try
        {
            KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
            keygenerator.init(128);
            return keygenerator.generateKey();
        }
        catch (NoSuchAlgorithmException nosuchalgorithmexception)
        {
            throw new Error(nosuchalgorithmexception);
        }
    }

    /**
     * Generates RSA KeyPair
     */
    public static KeyPair generateKeyPair()
    {
        try
        {
            KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
            keypairgenerator.initialize(1024);
            return keypairgenerator.generateKeyPair();
        }
        catch (NoSuchAlgorithmException nosuchalgorithmexception)
        {
            nosuchalgorithmexception.printStackTrace();
            LOGGER.warning("Key pair generation failed!");
            return null;
        }
    }

    /**
     * Compute a serverId hash for use by sendSessionRequest()
     *
     * @param serverId The server ID
     * @param publicKey The public key
     * @param secretKey The secret key
     */
    public static byte[] getServerIdHash(String serverId, PublicKey publicKey, SecretKey secretKey)
    {
        try
        {
            return digestOperation("SHA-1", new byte[][] {serverId.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded()});
        }
        catch (UnsupportedEncodingException unsupportedencodingexception)
        {
            unsupportedencodingexception.printStackTrace();
            return null;
        }
    }

    /**
     * Compute a message digest on arbitrary byte[] data
     *
     * @param algorithm The name of the algorithm
     * @param data The data
     */
    private static byte[] digestOperation(String algorithm, byte[]... data)
    {
        try
        {
            MessageDigest messagedigest = MessageDigest.getInstance(algorithm);

            for (byte[] abyte : data)
            {
                messagedigest.update(abyte);
            }

            return messagedigest.digest();
        }
        catch (NoSuchAlgorithmException nosuchalgorithmexception)
        {
            nosuchalgorithmexception.printStackTrace();
            return null;
        }
    }

    /**
     * Create a new PublicKey from encoded X.509 data
     *
     * @param encodedKey The key, encoded to the X.509 standard
     */
    public static PublicKey decodePublicKey(byte[] encodedKey)
    {
        try
        {
            EncodedKeySpec encodedkeyspec = new X509EncodedKeySpec(encodedKey);
            KeyFactory keyfactory = KeyFactory.getInstance("RSA");
            return keyfactory.generatePublic(encodedkeyspec);
        }
        catch (NoSuchAlgorithmException var3)
        {
            var3.printStackTrace();
            ;
        }
        catch (InvalidKeySpecException var4)
        {
            var4.printStackTrace();
            ;
        }

        LOGGER.warning("Public key reconstitute failed!");
        return null;
    }

    /**
     * Decrypt shared secret AES key using RSA private key
     *
     * @param key The encryption key
     * @param secretKeyEncrypted The encrypted secret key
     */
    public static SecretKey decryptSharedKey(PrivateKey key, byte[] secretKeyEncrypted)
    {
        return new SecretKeySpec(decryptData(key, secretKeyEncrypted), "AES");
    }

    /**
     * Encrypt byte[] data with RSA public key
     *
     * @param key The encryption key
     * @param data The data
     */
    public static byte[] encryptData(Key key, byte[] data)
    {
        return cipherOperation(1, key, data);
    }

    /**
     * Decrypt byte[] data with RSA private key
     *
     * @param key The encryption key
     * @param data The data
     */
    public static byte[] decryptData(Key key, byte[] data)
    {
        return cipherOperation(2, key, data);
    }

    /**
     * Encrypt or decrypt byte[] data using the specified key
     *
     * @param opMode The operation mode of the cipher. (this is one of the following: Cipher.ENCRYPT_MODE,
     * Cipher.DECRYPT_MODE, Cipher.WRAP_MODE or Cipher.UNWRAP_MODE)
     * @param key The encryption key
     * @param data The data
     */
    private static byte[] cipherOperation(int opMode, Key key, byte[] data)
    {
        try
        {
            return createTheCipherInstance(opMode, key.getAlgorithm(), key).doFinal(data);
        }
        catch (IllegalBlockSizeException illegalblocksizeexception)
        {
            illegalblocksizeexception.printStackTrace();
        }
        catch (BadPaddingException badpaddingexception)
        {
            badpaddingexception.printStackTrace();
        }

        LOGGER.warning("Cipher data failed!");
        return null;
    }

    /**
     * Creates the Cipher Instance.
     *
     * @param opMode The operation mode of the cipher. (this is one of the following: Cipher.ENCRYPT_MODE,
     * Cipher.DECRYPT_MODE, Cipher.WRAP_MODE or Cipher.UNWRAP_MODE)
     * @param transformation The name of the transformation, e.g. DES/CBC/PKCS5Padding.
     * @param key The encryption key
     */
    private static Cipher createTheCipherInstance(int opMode, String transformation, Key key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(opMode, key);
            return cipher;
        }
        catch (InvalidKeyException invalidkeyexception)
        {
            invalidkeyexception.printStackTrace();
        }
        catch (NoSuchAlgorithmException nosuchalgorithmexception)
        {
            nosuchalgorithmexception.printStackTrace();
        }
        catch (NoSuchPaddingException nosuchpaddingexception)
        {
            nosuchpaddingexception.printStackTrace();
        }

        LOGGER.warning("Cipher creation failed!");
        return null;
    }

    /**
     * Creates an Cipher instance using the AES/CFB8/NoPadding algorithm. Used for protocol encryption.
     *
     * @param opMode The operation mode of the cipher. (this is one of the following: Cipher.ENCRYPT_MODE,
     * Cipher.DECRYPT_MODE, Cipher.WRAP_MODE or Cipher.UNWRAP_MODE)
     * @param key The encryption key
     */
    public static Cipher createNetCipherInstance(int opMode, Key key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(opMode, (Key)key, (AlgorithmParameterSpec)(new IvParameterSpec(key.getEncoded())));
            return cipher;
        }
        catch (GeneralSecurityException generalsecurityexception)
        {
            throw new RuntimeException(generalsecurityexception);
        }
    }
}
