package com.starcor.vds.demo;

import android.util.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class DecodePassword {

    public static String decode(String password) {
        try {
            byte[] data = Base64.decode(password, Base64.NO_WRAP);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, getPublicKey());
            return new String(cipher.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PublicKey getPublicKey() {
        byte[] keyBytes;
        try {
            keyBytes = Base64.decode(publicKey, Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String publicKey =
            "MIGiMA0GCSqGSIb3DQEBAQUAA4GQADCBjAKBhADQhJgt4Wdt93zlF+3qQlxJ1kQM\n" +
                    "DTNH76KFh+Fz3Nui5D+5iCv+yeW2uGMlK/JB429GAd47H16bc8oUC0ZoH67dac6l\n" +
                    "cS6EZ2Lkn6gOHRzwtAjKZtKuTF/zWZ4y6/+Vnu8zxmuG/WHWyQmaY6O31yO7zH4u\n" +
                    "AsY1+bMpp1lfMOIo0vyR3wIDAQAB";

}
