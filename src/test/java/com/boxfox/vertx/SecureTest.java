package com.boxfox.vertx;

import com.boxfox.vertx.secure.AES256;
import org.junit.Assert;
import org.junit.Test;

public class SecureTest {

    @Test
    public void aesTest(){
        String exampleText = "testStr";
        String encryptedText = AES256.encrypt(exampleText);
        String decryptedText = AES256.decrypt(decryptedText);
        Assert.assertEquals(encryptedText, "");
        Assert.assertEquals(decryptedText, exampleText);
    }
}
