package com.lx.testandroid.encrypt.impl;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.util.ArrayList;

import android.util.Base64;
import com.lx.testandroid.encrypt.base.EncryptStrategy;
import com.lx.testandroid.encrypt.tools.EncryptHelper;
import com.lx.testandroid.encrypt.tools.EncryptTools;

public class AesAndRsaEncryptStrategy implements EncryptStrategy {

    @Override
    public String encrypt(String url, int encryptType, int decryptType) {
        String keyCodec = EncryptHelper.randomKeyCodec();
        PrivateKey privateKey = EncryptTools.getPrivateKey(EncryptHelper.getPrivateKeyString(keyCodec));
        if (privateKey == null) {
            return url;
        }
        ArrayList<String> retainArgs = new ArrayList<>();
        String packJson = EncryptHelper.getPackJsonString(url, retainArgs);
        byte[] sha1Data = EncryptTools.sha1WithRsa(packJson, privateKey);
        if (sha1Data == null) {
            return url;
        }
        byte[] lengthData = EncryptTools.int2byteArray16(sha1Data.length);
        byte[] oriData = packJson.getBytes(Charset.forName("UTF-8"));
        String pack = EncryptHelper.buildPackArgs(lengthData, sha1Data, oriData);
        String hash = EncryptTools.md5(EncryptHelper.getHashJsonString(url));
        String codec = EncryptHelper.buildCodec(encryptType, decryptType, keyCodec);
        return EncryptHelper.buildUrl(url, hash, codec, pack, retainArgs);
    }

    @Override
    public byte[] decrypt(String url, byte[] data) {
        byte[] decodeData = Base64.decode(data, Base64.DEFAULT);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(decodeData);
        //read 0
        inputStream.read();
        //read sha1 data length
        byte[] lengthData = new byte[2];
        inputStream.read(lengthData, 0, lengthData.length);
        //read sha1 args
        int sha1Length = EncryptTools.byte2int(lengthData);
        byte[] sha1Data = new byte[sha1Length];
        inputStream.read(sha1Data, 0, sha1Length);
        int dataLength = decodeData.length - sha1Length - 2 - 1;
        byte[] oriData = new byte[dataLength];
        inputStream.read(oriData, 0, dataLength);
        return oriData;
    }

}
