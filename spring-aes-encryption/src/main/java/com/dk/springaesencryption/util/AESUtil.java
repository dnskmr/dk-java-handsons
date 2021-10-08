package com.dk.springaesencryption.util;

import com.dk.springaesencryption.exception.AESException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.Properties;

/**
 * @author DK
 * @version 1.0
 * @since 10/08/2021
 */
public class AESUtil {

    private static final String AES = "AES";
    private static final int TAG_LENGTH_BIT = 128;
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final SecretKey KEY = getAESKey();


    /**
     * <Don't let allow to instantiate
     */
    // Privet Constructor
    private AESUtil() {
        // It will avoid create instance using reflection
        throw new AESException("Cannot create new Instance");
    }


    /**
     * <p>
     * This method is used to get the secret key from the property file and return the SecretKey Object
     * </p>
     *
     * @return the SecretKey object
     */
    private static SecretKey getAESKey() {
        Properties properties = new Properties();
        ClassLoader classLoader = AESUtil.class.getClassLoader();
        InputStream applicationPropertiesStream = classLoader.getResourceAsStream("application.properties");
        try {
            properties.load(applicationPropertiesStream);
            System.out.println(properties.get("secret.key"));
            if (Objects.isNull(properties.getProperty("secret.key"))) {
                throw new AESException("secret.key cannot be null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SecretKey key = new SecretKeySpec(properties.getProperty("secret.key").getBytes(), AES);
        return key;
    }

    /**
     * <p>
     * This method is used to encrypt the plain text
     * </p>
     *
     * @param pText
     * @return the encrypted text
     */
    // AES-GCM needs GCMParameterSpec
    public static String encrypt(String pText) {

        try {
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, KEY.getEncoded());
            cipher.init(Cipher.ENCRYPT_MODE, KEY, gcmParameterSpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(pText.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AESException("Could not encrypt the text");
        }
    }

    /**
     * <p>
     * This method is used to get the decrypted value from cipher text
     * </p>
     *
     * @param cText
     * @return the decrypted text
     */
    public static String decrypt(String cText) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, KEY.getEncoded());
            cipher.init(Cipher.DECRYPT_MODE, KEY, gcmParameterSpec);
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cText));
            return new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AESException("Could not decrypt the cipher text");
        }
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String encrypted = encrypt("DK2210");
        System.out.println("encrypted = " + encrypted);

        String original = decrypt(encrypted);
        System.out.println("original = " + original);
    }


}
