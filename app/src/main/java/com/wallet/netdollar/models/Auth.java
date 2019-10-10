package com.wallet.netdollar.models;

import org.spongycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

public class Auth {

    /*KeyPairGenerator kpg;
    KeyPair kp;
    PublicKey publicKey;
    PrivateKey privateKey;*/
     private byte[] encryptedBytes, decryptedBytes;
    private Cipher cipher, cipher1;
    private  String encrypted, decrypted;
    public static final int KEY_LENGTH=256; //256BITS
    public static final int BLOCK_LENGTH=32; //64 BITS
    public static final int SALT_LENGTH=16;
    private static final Random RANDOM = new SecureRandom();
    //public static final int KEY_LENGTH=8;
    public  String Encrypt (String data, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
       //Instancier au moment de la generation de la verification.
        //kpg = KeyPairGenerator.getInstance("RSA");
      //  kpg.initialize(1024);
       // kp = kpg.genKeyPair();
      //  publicKey = kp.getPublic();
       // privateKey = kp.getPrivate();
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        encryptedBytes = cipher.doFinal(data.getBytes());
        encrypted = Base64.toBase64String(encryptedBytes);

        return encrypted;

    }

    public String Decrypt (String result, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {

        cipher1=Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE, privateKey);
        decryptedBytes = cipher1.doFinal(Base64.decode(result));
        decrypted = new String(decryptedBytes);
        return decrypted;

    }
    public static String encryptData(String data, PublicKey publicKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
       // byte[]key=new byte[KEY_LENGTH];
        byte[]iv=new byte[BLOCK_LENGTH];

       // SecretKey keyS=new SecretKeySpec(publicKey.getEncoded(),"RSA");
        Cipher cipher=Cipher.getInstance("RSA");
        IvParameterSpec ivSpec=new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE,publicKey,ivSpec);
        byte[]cipherText=cipher.doFinal(data.getBytes());
        return Base64.toBase64String(cipherText);
    }


    public static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
        byte[]salt=new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return  salt;
        //50fe9e9b-abeb-4359-8d51-c956f2dbb224
    }
    public String getAccountID()
    {
        String accountId="";
        UUID uuid = UUID.randomUUID();
        accountId = uuid.toString();
        return accountId;
    }
        public static byte[] getPasswordsalted(String password, byte[] salt)
    {
        try {
            MessageDigest md=MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte byteData[]=md.digest(password.getBytes());
            md.reset();
            return byteData;
        }catch (NoSuchAlgorithmException ex)
        {
            return null;
        }

    }


}
