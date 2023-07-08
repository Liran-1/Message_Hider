package com.example.secretcoder.encyptions;

import com.example.secretcoder.constants.Constants;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
    public static SecretKey generateKey(int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(keySize);
        return keyGenerator.generateKey();
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String algorithm, String input, String key) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), Constants.AES);
            Cipher cipher = Cipher.getInstance(algorithm);
            if(algorithm.equals(Constants.AES_CBS_PKCS5_ALGORITHM)) {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(Constants.IV.getBytes(StandardCharsets.UTF_8));
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            } else if (algorithm.equals(Constants.AES_ECB_PKCS5_ALGORITHM)){
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            } else {
                return null;
            }
            byte[] encryptedBytes = cipher.doFinal(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String algorithm, String cipherText, String key) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), Constants.AES);
            Cipher cipher = Cipher.getInstance(algorithm);
            if(algorithm.equals(Constants.AES_CBS_PKCS5_ALGORITHM)) {
                IvParameterSpec ivParameterSpec = new IvParameterSpec(Constants.IV.getBytes(StandardCharsets.UTF_8));
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            } else if (algorithm.equals(Constants.AES_ECB_PKCS5_ALGORITHM)){
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            } else {
                return null;
            }
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
