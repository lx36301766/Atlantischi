package pl.atlantischi.test;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class JavaTest {

    public static void main(String[] args) {

        try {
            byte[] data = encryptMode("4266".getBytes());
//            String key = Base64.encode(DesEncrypt("4299", build3DesKey(PASSWORD_CRYPT_KEY)));
            String key = Base64.encode(data);
            System.out.println(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    "Z+Bs/e7ICO0="

    public static byte[] DesEncrypt(String message, byte[] key) throws Exception {
        String algorithm = "DESede/ECB/PKCS5Padding";
        Cipher cipher = Cipher.getInstance(algorithm);
        DESedeKeySpec desKeySpec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("desede");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        String result = "";
        return cipher.doFinal(message.getBytes("ASCII"));
//        byte Encrypt[] = cipher.doFinal(message.getBytes("ASCII"));
//        for (int i = 0; i < Encrypt.length; i++) {
//            result += String.format("%02X", Encrypt[i] < 0 ? (256 + Encrypt[i]) : Encrypt[i]);

        //IvParameterSpec ivspec = new IvParameterSpec(init.getBytes());
//        return result;
    }



    // 定义加密算法，DESede即3DES
    private static final String Algorithm = "DESede";
    // 加密密钥
    private static final String PASSWORD_CRYPT_KEY = "verify_code_encrypt_key";

    /**
     * 加密方法
     *
     * @param src 源数据的字节数组
     *
     * @return
     */
    public static byte[] encryptMode(byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);
            // 实例化Cipher
            Cipher cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, deskey);

//            IvParameterSpec ivspec = new IvParameterSpec(init.getBytes());

            return cipher.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 解密函数
     *
     * @param src 密文的字节数组
     *
     * @return
     */
    public static byte[] decryptMode(byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    /**
     * 根据字符串生成密钥24位的字节数组
     *
     * @param keyStr
     *
     * @return
     *
     * @throws UnsupportedEncodingException
     */
    public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");
        if (key.length > temp.length) {
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }
}
