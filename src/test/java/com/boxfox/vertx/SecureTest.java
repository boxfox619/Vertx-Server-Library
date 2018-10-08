package com.boxfox.vertx;

import com.boxfox.vertx.secure.AES256;
import com.boxfox.vertx.secure.SHA256;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class SecureTest {

    @Test
    public void aes256Test() {
        try {
            String validEncryptedText = "JSTs4nhOHbWRWZtQrz2dzQ==";
            AES256 aes256 = AES256.create("testKey1234512412413");
            String originalText = "test";
            String encryptedText = aes256.encrypt(originalText);
            String decryptedText = aes256.decrypt(encryptedText);
            Assert.assertEquals(encryptedText, validEncryptedText);
            Assert.assertEquals(originalText, decryptedText);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void sha256Test() {
        String validEncryptedText = "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08";
        String encryptedText = SHA256.encrypt("test");
        Assert.assertEquals(encryptedText, validEncryptedText);
    }
}
