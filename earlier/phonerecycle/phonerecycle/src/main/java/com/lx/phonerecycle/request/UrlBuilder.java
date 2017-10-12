package com.lx.phonerecycle.request;

import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;

/**
* Copyright (C) 2014 lx <br>
* All rights reserved. <br>
* author:  xuan.luo <br>
* date:  14-7-20 下午5:50 <br>
* description:
*/

public class UrlBuilder {

    private static final String TAG = UrlBuilder.class.getSimpleName();

    public static String get(String prefix, RequestParams... paramses) {
        return get(true, prefix, paramses);
    }

    public static String get(boolean needSign, String prefix, RequestParams... paramses) {
        String requestUrl = prefix + "?";
        String sign = "";
        for (RequestParams params : paramses) {
            requestUrl += params.toString();
            if (needSign && params.isSign()) {
                sign += params.getName() + Tools.md5(params.getValue());
            }
        }
        if (needSign) {
            sign = Tools.md5(sign).toLowerCase();
            requestUrl += new RequestParams("nns_sign", sign);
        }
        requestUrl = requestUrl.replace("?&", "?");
        return requestUrl;
    }

    public static String getN1A_InitMain(){
        String url = get(false, MainInfo.main_url, MainInfo.nns_tag);
        XLog.d(TAG, "N1A=%s", url);
        return url;
    }

    public static String getN2A1_GetProvinceCity(){
        String url = get(MainInfo.n2_a,
                RequestParams.build("nns_method", "get_province_and_city"));
        XLog.d(TAG, "N2A1=%s", url);
        return url;
    }

    public static String getN2A2_UserRegister(int type, String name, String phone,
                                              String password, String password_a){
        String url = get(MainInfo.n2_a,
                RequestParams.build("nns_method", "user_reg"),
                RequestParams.build("nns_type", type),
                RequestParams.build("nns_name", name),
                RequestParams.build("nns_phone", phone),
                RequestParams.build("nns_password", password),
                RequestParams.build("nns_password_a", password_a));
        XLog.d(TAG, "N2A2=%s", url);
        return url;
    }

    public static String getN2A3_UserLogin(String name, String pwd){
        String url = get(MainInfo.n2_a,
                RequestParams.build("nns_method", "user_login"),
                RequestParams.build("nns_name", name),
                RequestParams.build("nns_pwd", pwd));
        XLog.d(TAG, "N2A3=%s", url);
        return url;
    }

    public static String getN2A4_ModifyUserInfo(String sex, String birthday, String email, String address){
        String url = get(MainInfo.n2_a,
                RequestParams.build("nns_method", "user_modify"),
                RequestParams.build("nns_user_id", LoginInfoHelper.read().id),
                RequestParams.build("nns_sex", sex),
                RequestParams.build("nns_birthday", birthday),
                RequestParams.build("nns_email", email),
                RequestParams.build("nns_address", address),
//                RequestParams.build("nns_header", avatar),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N2A4=%s", url);
        return url;
    }

    public static String getN2A5_ModifyUserPassword(String oldPassword,  String password,
                                                    String passwordAgain){
        String url = get(MainInfo.n2_a,
                RequestParams.build("nns_method", "user_modify_pwd"),
                RequestParams.build("nns_user_id", LoginInfoHelper.read().id),
                RequestParams.build("nns_old_pwd", oldPassword),
                RequestParams.build("nns_pwd", password),
                RequestParams.build("nns_pwd_again", passwordAgain),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N2A5=%s", url);
        return url;
    }

    public static String getN2A6_SendSms(String phoneNum, String type){
        String url = get(MainInfo.n2_a,
                RequestParams.build("nns_method", "send_sms"),
                RequestParams.build("nns_phone", phoneNum),
                RequestParams.build("nns_type", type));
        XLog.d(TAG, "N2A6=%s", url);
        return url;
    }

    public static String getN2A7_SendSms(String phoneNum, String sms){
        String url = get(MainInfo.n2_a,
                RequestParams.build("nns_method", "find_pwd"),
                RequestParams.build("nns_phone", phoneNum),
                RequestParams.build("nns_sms", sms));
        XLog.d(TAG, "N2A7=%s", url);
        return url;
    }

    public static String getN2A11_FAQ(int pageSize, int number) {
        String url = get(MainInfo.n2_a,
                RequestParams.build("nns_method", "faq_list"),
                RequestParams.build("nns_page_size", pageSize),
                RequestParams.build("nns_page_num", number));
        XLog.d(TAG, "N2A11=%s", url);
        return url;
    }

    public static String getN2A12_PrivacyPolicy() {
        String url = get(MainInfo.n2_a,
                RequestParams.build("nns_method", "privacy_policy"));
        XLog.d(TAG, "N2A12=%s", url);
        return url;
    }

    public static String getN3A1_GetHomePageInfo(int type) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "today_quote"),
                RequestParams.build("nns_type", type));
        XLog.d(TAG, "N3A1=%s", url);
        return url;
    }

    public static String getN3A2_GetActivityPromotions(int type, int pageSize, int pageNum) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "event_promotion"),
                RequestParams.build("nns_type", type),
                RequestParams.build("nns_page_size", pageSize),
                RequestParams.build("nns_page_num", pageNum));
        XLog.d(TAG, "N3A2=%s", url);
        return url;
    }

    public static String getN3A3_GetActivityDetails(String activityId) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "event_detail"),
                RequestParams.build("nns_event_id", activityId));
        XLog.d(TAG, "N3A3=%s", url);
        return url;
    }

    public static String getN3A4_GetPhoneRecomInfo(int recomType, int pageSize, int pageNum) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "machine_redemption"),
                RequestParams.build("nns_recom", recomType),
                RequestParams.build("nns_page_size", pageSize),
                RequestParams.build("nns_page_num", pageNum));
        XLog.d(TAG, "N3A4=%s", url);
        return url;
    }

    public static String getN3A5_GetPhoneRecomDetails(String phoneId) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "machine_detail"),
                RequestParams.build("nns_machine_id", phoneId));
        XLog.d(TAG, "N3A5=%s", url);
        return url;
    }

    public static String getN3A6_GetPhoneBrandAndModel(String name) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "brand_model"),
                RequestParams.build("nns_name", name),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N3A6=%s", url);
        return url;
    }

    public static String getN3A7_GetPhoneAssessArgs(String phoneNum, String brand, String model) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "machine_assess"),
                RequestParams.build("nns_phone", phoneNum),
                RequestParams.build("nns_brand", brand),
                RequestParams.build("nns_model", model),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N3A7=%s", url);
        return url;
    }

    public static String getN3A8_GetAssessResult(String phoneNum, String brand, String model, String assess) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "assess_result"),
                RequestParams.build("nns_phone", phoneNum),
                RequestParams.build("nns_brand", brand),
                RequestParams.build("nns_model", model),
                RequestParams.build("nns_assess", assess),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N3A8=%s", url);
        return url;
    }

    public static String getN3A9_GetAssessSubmit(String phoneNum, String brand, String model, String assess) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "assess_submit"),
                RequestParams.build("nns_user_id", LoginInfoHelper.read().id),
                RequestParams.build("nns_phone", phoneNum),
                RequestParams.build("nns_brand", brand),
                RequestParams.build("nns_model", model),
                RequestParams.build("nns_assess", assess),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N3A9=%s", url);
        return url;
    }

    public static String getN3A10_RedemptionSubmit(String phoneNum, String brand, String model,
                                                   String assess, String redemPhoneId) {
        return getN3A10_RedemptionSubmit(phoneNum, brand, model, assess, redemPhoneId, "", "", "", "");
    }

    public static String getN3A10_RedemptionSubmit(String phoneNum, String brand, String model,
                                                           String assess, String redemPhoneId, String c_name,
                                                           String c_phone, String c_time, String c_address) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "assess_redemption"),
                RequestParams.build("nns_user_id", LoginInfoHelper.read().id),
                RequestParams.build("nns_cate", 1),
                RequestParams.build("nns_phone", phoneNum),
                RequestParams.build("nns_brand", brand),
                RequestParams.build("nns_model", model),
                RequestParams.build("nns_assess", assess),
                RequestParams.build("nns_machine", redemPhoneId),
                RequestParams.build("nns_c_name", c_name),
                RequestParams.build("nns_c_phone", c_phone),
                RequestParams.build("nns_c_time", c_time),
                RequestParams.build("nns_c_address", c_address),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N3A10=%s", url);
        return url;
    }

    public static String getN3A11_GetAssessSubmit(String orderId, int cate, String verifiCode) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "order_sms_validate"),
                RequestParams.build("nns_user_id", LoginInfoHelper.read().id),
                RequestParams.build("nns_order_id", orderId),
                RequestParams.build("nns_cate", cate),
                RequestParams.build("nns_code", verifiCode),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N3A11=%s", url);
        return url;
    }

    public static String getN3A12_GetOrderSmsCode(String orderId, int type) {
        String url = get(MainInfo.n3_a,
                RequestParams.build("nns_method", "send_order_sms"),
                RequestParams.build("nns_user_id", LoginInfoHelper.read().id),
                RequestParams.build("nns_order_id", orderId),
                RequestParams.build("nns_type", type),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N3A12=%s", url);
        return url;
    }

    /**
     *
     * @param type 订单类型 0回收 1换购
     * @return
     */
    public static String getN4A1_GetOrderType(int type) {
        String url = get(MainInfo.n4_a,
                RequestParams.build("nns_method", "order_type"),
                RequestParams.build("nns_user_id", LoginInfoHelper.read().id),
                RequestParams.build("nns_type", type),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N4A1=%s", url);
        return url;
    }

    /**
     *
     * @param type 订单类型 0回收 1换购
     * @param pageSize 分页大小，一页显示多少条 默认20条
     * @param pageNum 分页数，第几页 默认为1
     * @return
     */
    public static String getN4A2_GetUserOrder(int type,String status,int pageSize, int pageNum) {
        String url = get(MainInfo.n4_a,
                RequestParams.build("nns_method", "user_order"),
                RequestParams.build("nns_user_id", LoginInfoHelper.read().id),
                RequestParams.build("nns_type", type),
                RequestParams.build("nns_status", status),
                RequestParams.build("nns_page_size", pageSize),
                RequestParams.build("nns_page_num", pageNum),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N4A2=%s", url);
        return url;
    }

    public static String getN4A3_GetOrderDetail(int type, String orderId) {
        String url = get(MainInfo.n4_a,
                RequestParams.build("nns_method", "order_detail"),
                RequestParams.build("nns_user_id", LoginInfoHelper.read().id),
                RequestParams.build("nns_type", type),
                RequestParams.build("nns_order_id", orderId),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N4A3=%s", url);
        return url;
    }

    public static String getN4A4_DeleteOrder(int type, String orderId) {
        String url = get(MainInfo.n4_a,
                RequestParams.build("nns_method", "user_order_del"),
                RequestParams.build("nns_user_id", LoginInfoHelper.read().id),
                RequestParams.build("nns_type", type),
                RequestParams.build("nns_order_id", orderId),
                RequestParams.build("nns_author_code", LoginInfoHelper.read().author_code));
        XLog.d(TAG, "N4A4=%s", url);
        return url;
    }

}
