package com.boxfox.vertx;

import com.boxfox.vertx.secure.AES256;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class SecureTest {

    @Test
    public void aes256Test(){
        try {
            AES256 aes256 = AES256.create("testKey1234512412413");
            String originalText = "test";
            String encryptedText = aes256.encrypt(originalText);
            String decryptedText = aes256.decrypt(encryptedText);
            Assert.assertEquals(encryptedText, "JSTs4nhOHbWRWZtQrz2dzQ==");
            Assert.assertEquals(originalText, decryptedText);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
