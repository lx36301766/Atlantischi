package pl.atlantischi.test;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created on 25/08/2017.
 *
 * @author lx
 */

public class Des3Tools {

    // 密钥
    private final static String secretKey = "verify_code_encrypt_key";

    private final static String encoding = "utf-8";
    private static final String TRANSFORMATION = "desede/ECB/PKCS5Padding";
    private static final String ALGORITHM = "DESede";

    /**
     * 3DES解密
     */
    public static String decode(String encryptText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(build3DesKey(secretKey));
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(ALGORITHM);
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte [] decryptData = cipher.doFinal(DES3.Base64.decode(encryptText));
        return new String(decryptData, encoding);
    }

    /**
     * 3DES加密
     */
    public static String encode(String plainText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(build3DesKey(secretKey));
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(ALGORITHM);
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        return DES3.Base64.encode(encryptData);
    }

    public static byte[] build3DesKey(String key) {
        return padding(md5(key));
    }

    public static byte[] padding(String str) {
        byte[] oldByteArray;
        try {
            oldByteArray = str.getBytes("UTF8");
            int numberToPad = 8 - oldByteArray.length % 8;
            byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
            System.arraycopy(oldByteArray, 0, newByteArray, 0, oldByteArray.length);
            for (int i = oldByteArray.length; i < newByteArray.length; ++i) {
                newByteArray[i] = 0;
            }
            return newByteArray;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Crypter.padding UnsupportedEncodingException");
        }
        return null;
    }

    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static void main(String[] args) {
        try {
            String str = encode("4524");
            System.out.println(str);

            String data = decode(str);
            System.out.println(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
