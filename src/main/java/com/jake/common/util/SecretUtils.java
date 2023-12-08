package com.jake.common.util;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * 보안 유틸리티
 */
public class SecretUtils {

    /**
     * SecretKey 생성기
     *
     * @param keySize 128, 192 or 256
     */
    public static SecretKey generateKey(int keySize) throws NoSuchAlgorithmException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keySize);
        SecretKey orginalKey = keyGenerator.generateKey();
        return orginalKey;
    }

    /**
     * 비밀번호를 기반으로 SecretKey 생성
     *
     * SecretKey encodedKey = SecretUtils.getKeyFromPassword("Jake@1234", "@#$jake@#^$*");
     *
     * @param password 비밀번호
     * @param salt salt
     */
    public static SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey originalKey = new SecretKeySpec(keyFactory.generateSecret(spec).getEncoded(), "AES");
        return originalKey;
    }

    /**
     * SecretKey 정보를 문자열로 변환
     *
     * String encodedString = SecretUtils.convertSecretKeyToString(encodedKey)
     * @see #getKeyFromPassword(String, String)
     */
    public static String convertSecretKeyToString(SecretKey secretKey) {
        byte[] rawData = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(rawData);
        return encodedKey;
    }

    /**
     * 문자열정보를 SecretKey 로 변환
     *
     */
    public static SecretKey convertStringToSecretKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
    }

    /**
     * 암호화
     */
    public static String encrypt(String plainText, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedData = cipher.doFinal(plainText.getBytes());
        return new String(encryptedData);
    }

    /**
     * 복호화
     */
    public static String decrypt(String encryptText, SecretKey secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedData = cipher.doFinal(encryptText.getBytes());
        return new String(decryptedData);
    }
}
