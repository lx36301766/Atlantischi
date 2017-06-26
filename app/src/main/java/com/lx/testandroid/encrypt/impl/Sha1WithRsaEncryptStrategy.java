package com.lx.testandroid.encrypt.impl;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import com.lx.testandroid.encrypt.base.EncryptStrategy;
import com.lx.testandroid.encrypt.json.JSONObject;
import com.lx.testandroid.encrypt.tools.EncryptHelper;
import com.lx.testandroid.encrypt.tools.RsaKeyPairs;
import com.lx.testandroid.encrypt.tools.EncryptTools;

@SuppressWarnings({"deprecation", "ResultOfMethodCallIgnored"})
public class Sha1WithRsaEncryptStrategy implements EncryptStrategy {

    @Override
    public String encrypt(String url, int encryptType, int decryptType) {
        String keyCodec = EncryptHelper.randomKeyCodec();
        PrivateKey privateKey = EncryptTools.getPrivateKey(EncryptHelper.getPrivateKeyString(keyCodec));
        if (privateKey == null) {
            return url;
        }
        ArrayList<String> retainArgs = new ArrayList<>();
        String aesArgs = buildAesArgs();
        byte[] aesData = EncryptTools.encryptByRsa(aesArgs, privateKey);
        byte[] oriData = EncryptTools.encryptByAes(EncryptHelper.getPackJsonString(url, retainArgs),
                EncryptHelper.getArgsFromJson(aesArgs, "pass"));
        byte[] lengthData = EncryptTools.int2byteArray16(aesData.length);
        String pack = EncryptHelper.buildPackArgs(lengthData, aesData, oriData);
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
        //read aes data length
        byte[] lengthData = new byte[2];
        inputStream.read(lengthData, 0, lengthData.length);
        //read aes args
        int aesLength = EncryptTools.byte2int(lengthData);
        byte[] aesData = new byte[aesLength];
        inputStream.read(aesData, 0, aesLength);
        String aesArgs = EncryptTools.decryptByRsa(aesData, getPublicKeyFromUrl(url));
        //read original data
        int dataLength = decodeData.length - aesData.length - lengthData.length - 1;
        byte[] oriData = new byte[dataLength];
        inputStream.read(oriData, 0, dataLength);
        return EncryptTools.decryptByAes(oriData, EncryptHelper.getArgsFromJson(aesArgs, "pass"));
    }

    private static String buildAesArgs() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mode", 0);
            jsonObject.put("bits", 128);
            jsonObject.put("init", EncryptTools.getRandomString(32));
            jsonObject.put("pass", EncryptTools.getRandomString(32));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private PublicKey getPublicKeyFromUrl(String url) {
        String keyCodec = null;
        List<NameValuePair> queryParams = URLEncodedUtils.parse(URI.create(url), "utf-8");
        for (NameValuePair queryParam : queryParams) {
            if (queryParam.getName().equals("Codec") || queryParam.getName().equals("nns_codec")) {
                String codec = queryParam.getValue();
                keyCodec = codec.substring(codec.length() - 2, codec.length());
                break;
            }
        }
        if (TextUtils.isEmpty(keyCodec)) {
            int index = url.indexOf("--Codec__") + 9;
            String codec = url.substring(index, index + 8);
            keyCodec = codec.substring(codec.length() - 2, codec.length());
        }
        Integer codec = Integer.valueOf(keyCodec);
        Pair<String, String> keyPair = RsaKeyPairs.mKeyMap.get(codec);
        String publicKeyString = keyPair.second;
        return EncryptTools.getPublicKey(publicKeyString);
    }

}
