package com.jake.common.util;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecretUtilTest {

    @Test
    void encodeTest() throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKey encodedKey = SecretUtils.getKeyFromPassword("Jake@2023", "@#$jake@#^$*");
        String encodedString = SecretUtils.convertSecretKeyToString(encodedKey);
        SecretKey decodeKey = SecretUtils.convertStringToSecretKey(encodedString);
        assertEquals(encodedKey, decodeKey);
    }
}
