package com.boxfox.vertx.secure;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES256 {
    private String ips;
    private Key keySpec;

    public static AES256 create(String key) throws UnsupportedEncodingException {
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        System.arraycopy(b, 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        String ips = key.substring(0, 16);
        AES256 aes256 = new AES256(ips, keySpec);
        return aes256;
    }

    private AES256(String ips, Key keySpec) {
        this.ips = ips;
        this.keySpec = keySpec;
    }

    public String encrypt(String str) {
        if (str == null) return null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, this.keySpec,
                    new IvParameterSpec(this.ips.getBytes("UTF-8")));

            byte[] encrypted = cipher.doFinal(str.getBytes("UTF-8"));
            String Str = new String(Base64.encodeBase64(encrypted));
            return Str;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException
                | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String str) {
        if (str == null) return null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, this.keySpec,
                    new IvParameterSpec(this.ips.getBytes("UTF-8")));

            byte[] byteStr = Base64.decodeBase64(str.getBytes());
            String Str = new String(cipher.doFinal(byteStr), "UTF-8");

            return Str;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException
                | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}