package com.lx.testandroid.encrypt.tools;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONException;

import com.lx.testandroid.encrypt.json.JSONObject;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;

/**
 * Created on 2017/2/21.
 *
 * @author lx
 */

public class EncryptHelper {

    private static Set<Integer> mInvaildCodeList = new HashSet<>();

    private static List<String> noEncryptionArgs = new ArrayList<>();
    private static List<String> retainArgsName = new ArrayList<>();

    static {
        //hash retain arguments
        noEncryptionArgs.add("nns_token");
        noEncryptionArgs.add("nns_user_id");
        noEncryptionArgs.add("nns_mac_id");
        noEncryptionArgs.add("nns_mac");
        noEncryptionArgs.add("nns_smart_card_id");
        noEncryptionArgs.add("nns_device_id");
        noEncryptionArgs.add("nns_user_password");
        noEncryptionArgs.add("nns_webtoken");
        noEncryptionArgs.add("nns_epg_server");
        noEncryptionArgs.add("nns_net_id");
        noEncryptionArgs.add("nns_login_id");
        noEncryptionArgs.add("nns_ex_data");

        //pack retain arguments
        retainArgsName.add("nns_func");
        retainArgsName.add("nns_output_type");
        retainArgsName.add("nns_proxy_cache");
    }

    public static void initInvaildCode(List<String> allCodes, List<String> vaildCodes) {
        for (String code : allCodes) {
            if (!vaildCodes.contains(code)) {
                mInvaildCodeList.add(Integer.parseInt(code));
            }
        }
    }

    /**
     * build pack arguments for encrypt on url
     *
     * @param lengthData binary data for encrypted arguments length
     * @param encodeData binary data for encrypted arguments
     * @param oriData binary data for original data which will is encrypted
     * @return encrypt completed pack arguments
     */
    public static String buildPackArgs(byte[] lengthData, byte[] encodeData, byte[] oriData) {
        byte tmp = lengthData[0];
        lengthData[0] = lengthData[1];
        lengthData[1] = tmp;
        int size = 1 + lengthData.length + encodeData.length + oriData.length;
        ByteArrayOutputStream dataStream = new ByteArrayOutputStream(size);
        dataStream.write(0);
        dataStream.write(lengthData, 0, lengthData.length);
        dataStream.write(encodeData, 0, encodeData.length);
        dataStream.write(oriData, 0, oriData.length);
        byte[] data = dataStream.toByteArray();
        return Base64.encodeToString(data, Base64.URL_SAFE);
    }

    public static String buildCodec(int encryptType, int decryptType, String keyCodec) {
        String rsaMode = "0";
        return encryptType + rsaMode + keyCodec +
                decryptType + rsaMode + randomKeyCodec();
    }

    public static String getPrivateKeyString(String keyCodec) {
        Integer codec = Integer.valueOf(keyCodec);
        Pair<String, String> keyPair = RsaKeyPairs.mKeyMap.get(codec);
        return keyPair.first;
    }

    public static String buildUrl(String url, String hash, String codec, String pack, ArrayList<String> retainArgs) {
        StringBuilder newUrlBuilder = new StringBuilder();
        newUrlBuilder.append(getPrefix(url));
        for (String arg : retainArgs) {
            if (arg.contains("nns_func")) {
                newUrlBuilder.append("&").append(arg);
                break;
            }
        }
        newUrlBuilder.append("&nns_hash=").append(hash);
        newUrlBuilder.append("&nns_codec=").append(codec);
        for (String arg : retainArgs) {
            if (arg.contains("nns_output_type")) {
                newUrlBuilder.append("&").append(arg);
                break;
            }
        }
        newUrlBuilder.append("&nns_pack=").append(pack);
        return newUrlBuilder.toString().replace("?&", "?").replace("\n","").trim();
    }

    private static String getPrefix(String url) {
        URI uri = URI.create(url);
        String host = uri.getHost();
        String path = uri.getPath();
        String scheme = uri.getScheme();
        int port = uri.getPort();
        if (port > 0) {
            host += ":" + port;
        }
        return scheme + "://" + host + path + "?";
    }

    public static String getArgsFromJson(String json, String key) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String randomKeyCodec() {
        List<Integer> codes = new ArrayList<>(RsaKeyPairs.mKeyMap.keySet());
        for (Integer invaildCode : mInvaildCodeList) {
            codes.remove(invaildCode);
        }
        int random = new Random().nextInt(codes.size());
        int code = codes.get(random);
        if (code < 10) {
            return "0" + code;
        }
        return String.valueOf(code);
    }

    public static String getHashJsonString(String url) {
        TreeMap<String, String> hashJsonMap = new TreeMap<>();
        JSONObject hashJson = new JSONObject();
        List<NameValuePair> queryParams = URLEncodedUtils.parse(URI.create(url), "utf-8");
        for (NameValuePair queryParam : queryParams) {
            if (!noEncryptionArgs.contains(queryParam.getName())) {
                hashJsonMap.put(queryParam.getName(), TextUtils.isEmpty(queryParam.getValue())
                        ? "" : queryParam.getValue());
            }
        }

        Iterator<Map.Entry<String, String>> iterator = hashJsonMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            try {
                hashJson.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        for (String key : hashJsonMap.keySet()) {
//            String value = hashJsonMap.get(key);
//            try {
//                hashJson.put(key, value);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        return hashJson.toString();
    }

    public static String getPackJsonString(String url, ArrayList<String> retainArgs) {
        TreeMap<String, String> packJsonMap = new TreeMap<>();
        JSONObject packJson = new JSONObject();
        List<NameValuePair> queryParams = URLEncodedUtils.parse(URI.create(url), "utf-8");
        for (NameValuePair queryParam : queryParams) {
            if (retainArgsName.contains(queryParam.getName())) {
                retainArgs.add(queryParam.toString());
            } else {
                packJsonMap.put(queryParam.getName(), TextUtils.isEmpty(queryParam.getValue())
                        ? "" : queryParam.getValue());
            }
        }

        Iterator<Map.Entry<String, String>> iterator = packJsonMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            try {
                packJson.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

//        for (String key : packJsonMap.keySet()) {
//            String value = packJsonMap.get(key);
//            try {
//                packJson.put(key, value);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        return packJson.toString();
    }

    public static boolean checkKeyVaild() {
        return RsaKeyPairs.mKeyMap.size() != mInvaildCodeList.size();
    }

}
