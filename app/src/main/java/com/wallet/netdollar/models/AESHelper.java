package com.wallet.netdollar.models;

import com.lambdaworks.crypto.SCrypt;

import org.spongycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESHelper {
    public static final int KEY_LENGTH=32; //256BITS
    public static final int BLOCK_LENGTH=32; //64 BITS
    public static final int SALT_LENGTH=16;
    static String HASH_CIPHER = "AES/CBC/PKCS7Padding";
    static String AES = "AES";
    static String CHARSET_TYPE = "UTF-8";
    static String APPEND = "Salted__";


    public static String hashPassword(String password, byte[]salt)
    {
        byte[]passwordbyte=null;
        try {
            passwordbyte=password.getBytes();
            byte[]keyBytes= SCrypt.scrypt(passwordbyte,salt,2,8,1,KEY_LENGTH);
            String haspass=Base64.toBase64String(keyBytes);
            return haspass;

        }
        catch (Exception e)
        {
         return null;
        }
    }


    public static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
        byte[]salt=new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return  salt;
    }



public static String encrypt(String password, String plaintext,byte[]salt,byte[]kdf) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
        InvalidKeyException,InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        byte[]key=new byte[KEY_LENGTH];
        byte[]iv=new byte[BLOCK_LENGTH];
        key=kdf;
        SecretKey keyS=new SecretKeySpec(key,AES);
        Cipher cipher=Cipher.getInstance(HASH_CIPHER);
        IvParameterSpec ivSpec=new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE,keyS,ivSpec);
        byte[]cipherText=cipher.doFinal(plaintext.getBytes(CHARSET_TYPE));
        byte[]sBytes=APPEND.getBytes(CHARSET_TYPE);
        byte[]b=new byte[sBytes.length+salt.length+cipherText.length];
        System.arraycopy(sBytes, 0, b, 0, sBytes.length);
        System.arraycopy(salt, 0, b, sBytes.length, salt.length);
        System.arraycopy(cipherText, 0, b, sBytes.length + salt.length, cipherText.length);
        byte[]bEncode= Base64.encode(b);
        return new String(bEncode);
}


    public static String decrypt(String password, String cipherText,byte[]salt,byte[]kdf)throws UnsupportedEncodingException,
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        byte[]ctBytes=Base64.decode(cipherText.getBytes(CHARSET_TYPE));
        //byte[]saltBytes=Arrays.copyOfRange(ctBytes,8,16);
        byte[]cipherTextBytes=Arrays.copyOfRange(ctBytes,32,ctBytes.length);
        byte[]key=new byte[KEY_LENGTH];
        byte[]plaintext=new byte[KEY_LENGTH];
            key=kdf;
            byte[]iv=new byte[BLOCK_LENGTH];
            Cipher cipher=Cipher.getInstance(HASH_CIPHER);
            SecretKey keyS=new SecretKeySpec(key,AES);
            cipher.init(Cipher.DECRYPT_MODE,keyS,new IvParameterSpec(iv));
            plaintext=cipher.doFinal(cipherTextBytes);

        return new String(plaintext);
    }


    public String getAccountID()
    {
        String accountId="";
        UUID uuid = UUID.randomUUID();
        accountId = uuid.toString();
        return accountId;
    }

}
