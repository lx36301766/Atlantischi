package com.lx.testandroid.encrypt;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;

import com.lx.testandroid.encrypt.tools.EncryptHelper;
import com.lx.testandroid.encrypt.tools.RsaKeyPairs;
import com.lx.testandroid.encrypt.data.GetSecretKeysInfo;
import com.lx.testandroid.encrypt.base.EncryptStrategy;
import com.lx.testandroid.encrypt.impl.AesAndRsaEncryptStrategy;
import com.lx.testandroid.encrypt.impl.NoEncryptStrategy;
import com.lx.testandroid.encrypt.impl.Sha1WithRsaEncryptStrategy;

@SuppressWarnings({"deprecation", "unused", "JavaDoc"})
public class EncryptManager {

    private static String TAG = "EncryptManager";

    private static class EncryptApiParams {
        String mApiName;
        int[] mEncryptTypes;
        int[] mDecryptTypes;
    }

    private static final Set<EncryptApiParams> mEncryptApiParams = new HashSet<>();

    /**
     *
     * @param getSecretKeysInfo
     */
    public static void init(GetSecretKeysInfo getSecretKeysInfo) {
        synchronized(mEncryptApiParams) {
            mEncryptApiParams.clear();
            for (GetSecretKeysInfo.ApiEncrypt apiEncrypt : getSecretKeysInfo.apiEncrypts) {
                addEncryptApi(apiEncrypt.sign, apiEncrypt.requestEncryptMode, apiEncrypt.responseEncryptMode);
            }
        }
        for (GetSecretKeysInfo.SecretKeys secretKey : getSecretKeysInfo.secretKeys) {
            Integer key = Integer.valueOf(secretKey.sign);
            String privateKey = filterKey(secretKey.requestEncryptKey);
            String publicKey = filterKey(secretKey.responseEncryptKey);
            RsaKeyPairs.mKeyMap.put(key, Pair.create(privateKey, publicKey));
        }
        List<String> vaildCodes = Arrays.asList(getSecretKeysInfo.validKeyGroup.split(","));
        List<String> allCodes = new ArrayList<>();
        Collections.addAll(allCodes, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        for (GetSecretKeysInfo.SecretKeys secretKey : getSecretKeysInfo.secretKeys) {
            allCodes.add(secretKey.sign);
        }
        EncryptHelper.initInvaildCode(allCodes, vaildCodes);
    }

    /**
     *
     * @param url
     * @param apiName
     * @return
     */
    public static String encrypt(String url, String apiName) {
        EncryptApiParams params = getEncryptApiData(apiName);
        if (params == null) {
            return url;
        }
        int encryptType = randomType(params.mEncryptTypes);
        int decryptType = randomType(params.mDecryptTypes);
        EncryptStrategy encryptStrategy = getEncryptStrategy(encryptType);
        if (encryptStrategy != null) {
            return encryptStrategy.encrypt(url, encryptType, decryptType);
        }
        return url;
    }

    /**
     *
     * @param data
     * @param url
     * @return
     */
    public static byte[] decrypt(byte[] data, String url) {
        int decryptType = getDecryptTypeFromUrl(url);
        EncryptStrategy encryptStrategy = getEncryptStrategy(decryptType);
        if (encryptStrategy != null) {
            return encryptStrategy.decrypt(url, data);
        }
        return data;
    }

    private static EncryptStrategy getEncryptStrategy(int type) {
        EncryptStrategy encryptStrategy;
        switch (type) {
            case 0:
                encryptStrategy = new NoEncryptStrategy();
                break;
            case 1:
                encryptStrategy = new Sha1WithRsaEncryptStrategy();
                break;
            case 2:
                encryptStrategy = new AesAndRsaEncryptStrategy();
                break;
            default:
                encryptStrategy = null;
        }
        return encryptStrategy;
    }

    private static void addEncryptApi(String apiName, String encryptTypes, String decryptTypes) {
        EncryptApiParams params = new EncryptApiParams();
        params.mApiName = apiName;
        params.mEncryptTypes = getTypes(encryptTypes);
        params.mDecryptTypes = getTypes(decryptTypes);
        synchronized(mEncryptApiParams) {
            mEncryptApiParams.add(params);
        }
    }

    private static int[] getTypes(String types) {
        String[] typesStrArr = types.split(",");
        int[] typesInt = new int[typesStrArr.length];
        for (int i = 0; i < typesInt.length; i++) {
            typesInt[i] = Integer.parseInt(typesStrArr[i]);
        }
        return typesInt;
    }

    private static String filterKey(String oriKey) {
        byte[] decodeData = Base64.decode(oriKey, Base64.DEFAULT);
        String k1 = new String(decodeData, Charset.forName("UTF-8"));
        k1 = k1.substring(0, k1.length() - 1);
        int start = k1.indexOf("\n") + 1;
        int end = k1.lastIndexOf("\n");
        return k1.substring(start, end);
    }

    private static EncryptApiParams getEncryptApiData(String apiName) {
        if (!EncryptHelper.checkKeyVaild()) {
            Log.e(TAG, "encode url, no vaild key!!!");
            return null;
        }
        if (TextUtils.isEmpty(apiName)) {
            Log.e(TAG, "encode url, the TaskName is null!!!");
            return null;
        }
        EncryptApiParams encryptApiData = null;
        synchronized(mEncryptApiParams) {
            for (EncryptApiParams params : mEncryptApiParams) {
                if (params.mApiName.equalsIgnoreCase(apiName.replace("-", "_").toLowerCase())) {
                    encryptApiData = params;
                    break;
                }
            }
        }
        return encryptApiData;
    }

    private static int randomType(int[] types) {
        return types[new Random().nextInt(types.length)];
    }

    private static int getDecryptTypeFromUrl(String url) {
        String keyCodec = null;
        List<NameValuePair> queryParams = URLEncodedUtils.parse(URI.create(url), "utf-8");
        for (NameValuePair queryParam : queryParams) {
            if (queryParam.getName().equals("Codec") || queryParam.getName().equals("nns_codec")) {
                String codec = queryParam.getValue();
                keyCodec = codec.substring(codec.length() - 2, codec.length());
                break;
            }
        }
        int type = -1;
        if (!TextUtils.isEmpty(keyCodec)) {
            type = Integer.parseInt(keyCodec.substring(4, 5));
        }
        return type;
    }

}
