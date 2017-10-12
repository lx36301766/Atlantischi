import json.JSONArray;
import json.JSONException;
import json.JSONObject;

/**
 * Copyright (C) 2015 北京视达科科技有限公司 <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  15-9-6 下午9:51 <br>
 * description:
 */

public class MainTest {


    public static void main(String[] args) {
        test2();
    }

    private static final String HOST = "http://192.168.0.105/test/";

    private static final String PATH_DIR = "D:\\lx\\project\\Atway\\trunk\\test\\";

    private static final String WEB_URL = HOST + "webview/4.html";

    private static final String A1 = "get_main_urls";
    private static final String A2 = "get_app_image";
    private static final String A3 = "check_app_update";

    private static final String VERSION = "v8";

    public static void test2() {
        try {
//            buildPlayer();
            buildPay();
            buildWebPlayer();
            buildGetMainUrls(VERSION);
            buildGetLaunchImage(VERSION);
            buildCheckAppUpdate(VERSION, "0", "", "");
//            buildCheckAppUpdate("v92", "1", "1", HOST + "check_app_update/WebViewShell_UPDATE_TEST.apk");
//            buildCheckAppUpdate("v93", "1", "2", HOST + "check_app_update/WebViewShell_UPDATE_TEST.apk");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String buildPay() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("callback_name", "callback_pay");
        jsonObject.put("package_name", "cn.com.bellmann.payment");
        jsonObject.put("class_name", "cn.com.bellmann.payment.PayActivity");
        System.out.println(jsonObject.toString());
        System.out.println();

        JSONObject rootObject = new JSONObject();
        rootObject.put("transactionID", "tid_123");
        rootObject.put("SPID", "spid_456");
        rootObject.put("userId", "userId_789");
        rootObject.put("userToken", "userToken_abc");
        rootObject.put("key", "key_123");
        rootObject.put("productID", "productID_456");
        rootObject.put("price", "price_789");
        rootObject.put("productName", "productName_123");
        rootObject.put("backPackage", "com.atway.webshell");
        rootObject.put("backClass", "com.atway.webshell.WebReceiverActivity");
        rootObject.put("notifyUrl", "notifyUrl_abc");
        rootObject.put("optFlag", "VAS");
        rootObject.put("purchaseType", 0);
        rootObject.put("categoryID", "categoryID_123");
        rootObject.put("contentID", "contentID_456");
        rootObject.put("contentType", 1);
        rootObject.put("sign", "sign_abc");
        System.out.println(rootObject.toString());
        System.out.println();
        return rootObject.toString();
    }

    private static String buildPlayer() throws JSONException {
        JSONObject rootObject = new JSONObject();
        rootObject.put("video_id", "id_123");
        rootObject.put("video_name", "name_abc");
        rootObject.put("play_url", "http://xxx.ts");
        rootObject.put("time_length", 200);
        System.out.println(rootObject.toString());
        System.out.println();
        return rootObject.toString();
    }

    private static String buildWebPlayer() throws JSONException {
        JSONObject rootObject = new JSONObject();
        rootObject.put("callback_name", "callback_player");
        rootObject.put("is_loop", true);
        rootObject.put("start_x", 100);
        rootObject.put("start_y", 100);
        rootObject.put("width", 700);
        rootObject.put("height", 500);
        rootObject.put("play_url", "http://119.39.49.19:8089/001/2/ch00000090990000001053?playseek=20151223072531-&virtualDomain=001.tstv_hls.zte.com");
        System.out.println(rootObject.toString());
        System.out.println();
        return rootObject.toString();
    }

    private static String buildGetMainUrls(String name) throws JSONException {
        JSONObject rootObject = new JSONObject();

        JSONObject resultJson = new JSONObject();
        resultJson.put("state", 200);
        resultJson.put("reason", "success");
        rootObject.put("result", resultJson);

        rootObject.put("main_page_url", WEB_URL);

        JSONObject urlJson = new JSONObject();
        urlJson.put("a1", HOST);
        urlJson.put("a2", HOST + A2 + "/" + VERSION);
        urlJson.put("a3", HOST + A3 + "/" + VERSION);
        rootObject.put("urls", urlJson);

        String path = PATH_DIR + A1 + "\\";
        FileTools.writeStringToFile(path + name, rootObject.toString());

        System.out.println(rootObject.toString());
        System.out.println();
        return rootObject.toString();
    }

    private static String buildGetLaunchImage(String name) throws JSONException {
        JSONObject rootObject = new JSONObject();

        JSONObject resultJson = new JSONObject();
        resultJson.put("state", 200);
        resultJson.put("reason", "success");
        rootObject.put("result", resultJson);

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(HOST + A2 + "/image/0.jpg");
        jsonArray.put(HOST + A2 + "/image/1.jpg");
        jsonArray.put(HOST + A2 + "/image/2.jpg");
        jsonArray.put(HOST + A2 + "/image/3.jpg");
        jsonArray.put(HOST + A2 + "/image/4.jpg");

        JSONObject imageInfoJson = new JSONObject();
        imageInfoJson.put("display_time", "2");
        imageInfoJson.put("urls", jsonArray);
        rootObject.put("image_info", imageInfoJson);

        String path = PATH_DIR + A2 + "\\";
        FileTools.writeStringToFile(path + name, rootObject.toString());

        System.out.println(rootObject.toString());
        System.out.println();
        return rootObject.toString();
    }

    private static String buildCheckAppUpdate(String name, String update_state, String update_type, String download_url) throws JSONException {
        JSONObject rootObject = new JSONObject();

        JSONObject resultJson = new JSONObject();
        resultJson.put("state", 200);
        resultJson.put("reason", "success");
        rootObject.put("result", resultJson);

        JSONObject imageJson = new JSONObject();
        imageJson.put("update_state", update_state);
        imageJson.put("update_type", update_type);
        imageJson.put("download_url", download_url);
        rootObject.put("update_info", imageJson);

        String path = PATH_DIR + A3 + "\\";
        FileTools.writeStringToFile(path + name, rootObject.toString());

        System.out.println(rootObject.toString());
        System.out.println();
        return rootObject.toString();
    }



//    private static void test1() {
//        System.out.println(buildGetMainUrls());
//        System.out.println(buildGetLaunchImage());
//        System.out.println(buildCheckAppUpdate("0", "", ""));
//        System.out.println(buildCheckAppUpdate("1", "1", "http://192.168.1.114/atway/check_app_update/WebViewShell_UPDATE_TEST.apk"));
//        System.out.println(buildCheckAppUpdate("1", "2", "http://192.168.1.114/atway/check_app_update/WebViewShell_UPDATE_TEST.apk"));
//    }
//
//    private static JsonObject buildGetMainUrls() {
//        JsonObject rootObject = new JsonObject();
//
//        JsonObject resultJson = new JsonObject();
//        resultJson.addProperty("state", 200);
//        resultJson.addProperty("reason", "success");
//        rootObject.add("result", resultJson);
//
//        JsonObject urlJson = new JsonObject();
//        urlJson.addProperty("a1", "http://host_a1");
//        urlJson.addProperty("a2", "http://host_a2");
//        urlJson.addProperty("a3", "http://host_a3");
//        rootObject.add("urls", urlJson);
//
//        return rootObject;
//    }
//
//    private static JsonObject buildGetLaunchImage() {
//        JsonObject rootObject = new JsonObject();
//
//        JsonObject resultJson = new JsonObject();
//        resultJson.addProperty("state", 200);
//        resultJson.addProperty("reason", "success");
//        rootObject.add("result", resultJson);
//
//        JsonObject urlJson = new JsonObject();
//        urlJson.addProperty("url0", "http://192.168.1.114/atway/get_launch_image/image/0.jpg");
//        urlJson.addProperty("url1", "http://192.168.1.114/atway/get_launch_image/image/1.jpg");
//        urlJson.addProperty("url2", "http://192.168.1.114/atway/get_launch_image/image/2.jpg");
//        urlJson.addProperty("url3", "http://192.168.1.114/atway/get_launch_image/image/3.jpg");
//        urlJson.addProperty("url4", "http://192.168.1.114/atway/get_launch_image/image/4.jpg");
//        JsonObject imageInfoJson = new JsonObject();
//        imageInfoJson.addProperty("display_time", "2");
//        imageInfoJson.add("urls", urlJson);
//        rootObject.add("image_info", imageInfoJson);
//
//        return rootObject;
//    }
//
//    private static JsonObject buildCheckAppUpdate(String update_state, String update_type, String download_url) {
//        JsonObject rootObject = new JsonObject();
//
//        JsonObject resultJson = new JsonObject();
//        resultJson.addProperty("state", 200);
//        resultJson.addProperty("reason", "success");
//        rootObject.add("result", resultJson);
//
//        JsonObject imageJson = new JsonObject();
//        imageJson.addProperty("update_state", update_state);
//        imageJson.addProperty("update_type", update_type);
//        imageJson.addProperty("download_url", download_url);
//        rootObject.add("update_info", imageJson);
//
//        return rootObject;
//    }

}
