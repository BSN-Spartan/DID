package com.reddate.did.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.util.encoders.Hex;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AesCode {


    public static byte[] encrypt(String key, byte[] origData) throws GeneralSecurityException {
        byte[] keyBytes = getKeyBytes(key);
        byte[] buf = new byte[16];
        System.arraycopy(keyBytes, 0, buf, 0, Math.max(keyBytes.length, buf.length));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(buf, "AES"), new IvParameterSpec(keyBytes));
        return cipher.doFinal(origData);

    }

    public static byte[] decrypt(String key, byte[] crypted) throws GeneralSecurityException {
        byte[] keyBytes = getKeyBytes(key);
        byte[] buf = new byte[16];
        System.arraycopy(keyBytes, 0, buf, 0, Math.max(keyBytes.length, buf.length));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(buf, "AES"), new IvParameterSpec(keyBytes));
        return cipher.doFinal(crypted);
    }

    private static byte[] getKeyBytes(String key) {
        byte[] bytes = key.getBytes();
        return bytes.length == 16 ? bytes : Arrays.copyOf(bytes, 16);
    }

    public static String encrypt(String key, String val) {
        try {
            byte[] origData = val.getBytes();
            byte[] crafted = encrypt(key, origData);
            return new String(Hex.encode(crafted));
        }catch (Exception e){
            return "";
        }
    }

    public static String decrypt(String key, String val) throws GeneralSecurityException {
        byte[] crypted = Hex.decode(val);
        byte[] origData = decrypt(key, crypted);
        return new String(origData);
    }


    public static void main(String[] args) throws Exception {

        // --encryptedPrivateKey=07dd3c6c3a45d1d0e748d58481f07d17606f07448e807f3a3f2217da503fb57ca00c2c7467e165a041d1cc4b5e305f9c7891a94000f0da0916ae4975c65ef9e61d9e52537a409a9b2a02b07e64e6573a --protectionKey=1234567812345678
        String key = DigestUtils.md5Hex("5555555").substring(0, 16);
//        String key = DigestUtils.md5Hex("1234567812345678").substring(0, 16);
        System.out.println("key：" + key);

        /*String plainText = "0xc3f0aae686d9aa4c896b920387be67e2196e441c4b072aca400485e47f596740";
        System.out.println("plainText：" + plainText);

        String cipherText = encrypt(key, plainText);
        System.out.println("cipherText：" + cipherText);*/

        String cipherText = "6aafe0bda572ca02563d777bca0668d4d3f8d3d55a7d73f2485e9c916649c9fa0b70011f8e6426dea0ec10bde2fd40950efae16196e641972d758c037074fd72148375b447c98994c6f70b1cdf287e6b";
        String decryptResult = decrypt(key, cipherText);
        System.out.println("decryptResult：" + decryptResult);
    }


}

