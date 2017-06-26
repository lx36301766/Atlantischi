package com.lx.testandroid.encrypt.tools;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;
import android.util.Log;

public class EncryptTools {

    private static final String TAG = "EncryptLogic";

    private static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    private static final String AES_TRANSFORMATION = "AES/ECB/NoPadding"; //"AES/CBC/NoPadding";

    public static void init() {
//        try {
//            publicKey = getPublicKey(PUBLIC_KEY_STRING);
//            privateKey = getPrivateKey(PRIVATE_KEY_STRING);
//            rsaCodec = Cipher.getInstance(RSA_TRANSFORMATION);
//            aesCodec = Cipher.getInstance(AES_TRANSFORMATION);
//            aesKey = new SecretKeySpec(hexString2Bytes(AES_KEY_STRING), "AES");
//            ivSpec = new IvParameterSpec((IV_SPEC_STRING.getBytes()));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        }
    }

    // 从十六进制字符串到字节数组转换
    private static byte[] hexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
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
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static byte[] sha1WithRsa(String str, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA1withRSA");
            signature.initSign(privateKey);
            signature.update(str.getBytes(Charset.forName("UTF-8")));
            return signature.sign();
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PublicKey getPublicKey(String publicKeyString) {
        byte[] keyBytes;
        try {
            keyBytes = Base64.decode(publicKeyString, Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey getPrivateKey(String privateKeyString) {
        byte[] keyBytes;
        try {
            keyBytes = Base64.decode(privateKeyString, Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] int2byteArray16(int num) {
        byte[] result = new byte[2];
        result[0] = (byte)(num >>> 8); //取次低8位放到0下标
        result[1] = (byte)(num );      //取最低8位放到1下标
        return result;
    }

    public static int byte2int(byte[] res) {
        // res = InversionByte(res);
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
        return(res[0] & 0xff) | ((res[1] << 8) & 0xff00); // | 表示安位或
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdef0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static byte[] encryptByAes(String data, String pass) {
        Log.i(TAG, "encryptByAes begin!");
        try {
            Cipher cipher = Cipher.getInstance("");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = padString(data).getBytes(Charset.forName("UTF-8"));
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(hexString2Bytes(pass), "AES");
            //ECB don't need ivspec
            //IvParameterSpec ivspec = new IvParameterSpec(init.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            Log.i(TAG, "encryptByAes end!");
            return encrypted;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "encryptByAes error!, exception=" + e.getMessage());
            return new byte[0];
        }
    }

    private static String padString(String source) {
        char paddingChar = ' ';
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;
        StringBuilder builder = new StringBuilder(source);
        for (int i = 0; i < padLength; i++) {
            //source += paddingChar;
            builder.append(paddingChar);
        }
        return builder.toString();
    }

    public static byte[] decryptByAes(byte[] data, String pass) {
        try {
            Log.i(TAG, "decryptByAes start!");
//            byte[] encrypted = Base64.decode(data, Base64.DEFAULT);
            Cipher cipher = Cipher.getInstance("");
            SecretKeySpec keyspec = new SecretKeySpec(hexString2Bytes(pass), "AES");
            //ECB don't need ivspec
            //IvParameterSpec ivspec = new IvParameterSpec(init.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "decryptByAes error!, exception=" + e.getMessage());
            return new byte[0];
        }
    }

    public static byte[] encryptByRsa(String data, PrivateKey privateKey) {
        try {
            Log.i(TAG, "encryptByRsa start!");
            Cipher cipher = Cipher.getInstance("");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encryptData = cipher.doFinal(data.getBytes(Charset.forName("UTF-8")));
            Log.i(TAG, "encryptByRsa end!");
            return encryptData;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "encryptByRsa error!, exception=" + e.getMessage());
            return new byte[0];
        }
    }

    public static String decryptByRsa(byte[] data, PublicKey publicKey) {
        try {
            Log.i(TAG, "decryptByRsa start!");
            Cipher cipher = Cipher.getInstance("");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
//            data = Base64.decode(data, Base64.DEFAULT);
            String ret = new String(cipher.doFinal(data), Charset.forName("UTF-8"));
            Log.i(TAG, "decryptByRsa end!");
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "decryptByRsa error!, exception=" + e.getMessage() + "\npublicKey=" + publicKey);
            return null;
        }
    }

}
