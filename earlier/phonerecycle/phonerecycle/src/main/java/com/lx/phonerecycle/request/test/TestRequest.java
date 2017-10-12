package com.lx.phonerecycle.request.test;

import android.content.Context;
import android.graphics.Bitmap;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.location.LocationInfoHelper;
import com.lx.phonerecycle.gsonbean.*;
import com.lx.phonerecycle.helper.CustomToast;
import com.lx.phonerecycle.request.MainInfo;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.volley.ext.MultipartRequest;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-23 下午11:14 <br>
 * description:
 */

public class TestRequest {

    private static final String TAG = "TestRequest";

    private Context context;

    private static TestRequest testRequest;

    private TestRequest(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public static TestRequest getInstance(Context context) {
        synchronized (TestRequest.class) {
            if (testRequest == null) {
                testRequest = new TestRequest(context);
            }
            return testRequest;
        }
    }

    RequestQueue requestQueue;

    public void testN1A() {
        String url = UrlBuilder.getN1A_InitMain();
        GsonRequest request = new GsonRequest<N1A_InitMain>(url, N1A_InitMain.class,
                new Response.Listener<N1A_InitMain>() {
                    @Override
                    public void onResponse(N1A_InitMain response) {
                        MainInfo.initMainN1ASuccess = true;
                        MainInfo.n2_a = response.n2_a;
                        MainInfo.n3_a = response.n3_a;
                        MainInfo.n4_a = response.n4_a;
                        XLog.w(TAG, "init N1A success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CustomToast.show(context, "获取主入口地址失败");
                        XLog.e(TAG, "init N1A error, " + error);
                    }
                });
        requestQueue.add(request);
    }

    public void testN2A1() {
        String url = UrlBuilder.getN2A1_GetProvinceCity();
        GsonRequest request = new GsonRequest<N2A1_GetProvinceCity>(
                url, N2A1_GetProvinceCity.class,
                new Response.Listener<N2A1_GetProvinceCity>() {
                    @Override
                    public void onResponse(N2A1_GetProvinceCity response) {
                        XLog.w(TAG, "N2A1 success, response=%s", response);
                        if (response.status == 111) {
                            LocationInfoHelper locationInfoHelper = new LocationInfoHelper(context);
                            int size = locationInfoHelper.bulkInsertLocation(response.data);
                            XLog.d(TAG, "sync %d item locations to db", size);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A1 error, " + error);
                    }
                });
        requestQueue.add(request);
    }

    public void testN2A2() {
        String url = UrlBuilder.getN2A2_UserRegister(1, "lx", "15928115878", "123456", "123456");
        GsonRequest request = new GsonRequest<N2A2_UserRegister>(
                url, N2A2_UserRegister.class,
                new Response.Listener<N2A2_UserRegister>() {
                    @Override
                    public void onResponse(N2A2_UserRegister response) {
                        XLog.w(TAG, "N2A2 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A2 error, " + error);
                    }
                });
        requestQueue.add(request);
    }

    public void testN2A3() {
        String url = UrlBuilder.getN2A3_UserLogin("lx", "123456");
        GsonRequest request = new GsonRequest<N2A3_UserLogin>(url, N2A3_UserLogin.class,
                new Response.Listener<N2A3_UserLogin>() {
                    @Override
                    public void onResponse(N2A3_UserLogin response) {
                        XLog.w(TAG, "N2A3 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A3 error, " + error);
                    }
                });
        requestQueue.add(request);
    }

    public void testN2A4(String authorCode, Bitmap bmp) {
        String url = UrlBuilder.getN2A4_ModifyUserInfo("F", "1988-02-14",
                "lx123456s@163.com", "chengdu");
        MultipartRequest request = new MultipartRequest<N2A4_ModifyUserInfo>(
                url, N2A4_ModifyUserInfo.class,
                new Response.Listener<N2A4_ModifyUserInfo>() {
                    @Override
                    public void onResponse(N2A4_ModifyUserInfo response) {
                        XLog.w(TAG, "N2A4 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A4 error, " + error);
                    }
                },
                Tools.convertBmpToFile(context, bmp)
        );
        requestQueue.add(request);
    }

    public void testN2A5() {
        String url = UrlBuilder.getN2A5_ModifyUserPassword("123", "321", "321");
        GsonRequest request = new GsonRequest<N2A5_ModifyUserPassword>(url, N2A5_ModifyUserPassword.class,
                new Response.Listener<N2A5_ModifyUserPassword>() {
                    @Override
                    public void onResponse(N2A5_ModifyUserPassword response) {
                        XLog.w(TAG, "N2A5 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A5 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void testN2A6() {
        String url = UrlBuilder.getN2A6_SendSms("15928115878", "1");
        GsonRequest request = new GsonRequest<N2A6_SendSms>(url, N2A6_SendSms.class,
                new Response.Listener<N2A6_SendSms>() {
                    @Override
                    public void onResponse(N2A6_SendSms response) {
                        XLog.w(TAG, "N2A6 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A6 error, " + error);
                    }
                });
        requestQueue.add(request);
    }

    public void testN2A7() {
        String url = UrlBuilder.getN2A7_SendSms("15928115878", "123453");
        GsonRequest request = new GsonRequest<N2A7_FindPassword>(url, N2A7_FindPassword.class,
                new Response.Listener<N2A7_FindPassword>() {
                    @Override
                    public void onResponse(N2A7_FindPassword response) {
                        XLog.w(TAG, "N2A7 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A7 error, " + error);
                    }
                });
        requestQueue.add(request);
    }

    public void testN3A1() {
        String url = UrlBuilder.getN3A1_GetHomePageInfo(0);
        GsonRequest request = new GsonRequest<N3A1_GetHomePageInfo>(url, N3A1_GetHomePageInfo.class,
                new Response.Listener<N3A1_GetHomePageInfo>() {
                    @Override
                    public void onResponse(N3A1_GetHomePageInfo response) {
                        XLog.w(TAG, "N3A1 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A1 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void testN3A2() {
        String url = UrlBuilder.getN3A2_GetActivityPromotions(0, 20, 1);
        GsonRequest request = new GsonRequest<N3A2_GetActivityPromotions>(url, N3A2_GetActivityPromotions.class,
                new Response.Listener<N3A2_GetActivityPromotions>() {
                    @Override
                    public void onResponse(N3A2_GetActivityPromotions response) {
                        XLog.w(TAG, "N3A2 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A2 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void testN3A3() {
        String url = UrlBuilder.getN3A3_GetActivityDetails("c54988a9dc1fc3ff80d5e64208160c90");
        GsonRequest request = new GsonRequest<N3A3_GetActivityDetails>(url, N3A3_GetActivityDetails.class,
                new Response.Listener<N3A3_GetActivityDetails>() {
                    @Override
                    public void onResponse(N3A3_GetActivityDetails response) {
                        XLog.w(TAG, "N3A3 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A3 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void testN3A4() {
        String url = UrlBuilder.getN3A4_GetPhoneRecomInfo(0, 20, 1);
        GsonRequest request = new GsonRequest<N3A4_GetPhoneRecomInfo>(url, N3A4_GetPhoneRecomInfo.class,
                new Response.Listener<N3A4_GetPhoneRecomInfo>() {
                    @Override
                    public void onResponse(N3A4_GetPhoneRecomInfo response) {
                        XLog.w(TAG, "N3A4 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A4 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void testN3A5() {
        String url = UrlBuilder.getN3A5_GetPhoneRecomDetails("12");
        GsonRequest request = new GsonRequest<N3A5_GetPhoneRecomDetails>(url, N3A5_GetPhoneRecomDetails.class,
                new Response.Listener<N3A5_GetPhoneRecomDetails>() {
                    @Override
                    public void onResponse(N3A5_GetPhoneRecomDetails response) {
                        XLog.w(TAG, "N3A5 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A5 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void testN3A6() {
        String url = UrlBuilder.getN3A6_GetPhoneBrandAndModel("HTC");
        GsonRequest request = new GsonRequest<N3A6_GetPhoneBrandAndModel>(url, N3A6_GetPhoneBrandAndModel.class,
                new Response.Listener<N3A6_GetPhoneBrandAndModel>() {
                    @Override
                    public void onResponse(N3A6_GetPhoneBrandAndModel response) {
                        XLog.w(TAG, "N3A6 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A6 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void testN3A7() {
        String url = UrlBuilder.getN3A7_GetPhoneAssessArgs("15928115878", "苹果", "5S");
        GsonRequest request = new GsonRequest<N3A7_GetPhoneAssessArgs>(url, N3A7_GetPhoneAssessArgs.class,
                new Response.Listener<N3A7_GetPhoneAssessArgs>() {
                    @Override
                    public void onResponse(N3A7_GetPhoneAssessArgs response) {
                        XLog.w(TAG, "N3A7 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A7 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void testN3A8() {
        String url = UrlBuilder.getN3A8_GetAssessResult("15928115878", "苹果", "5S", "1-2|2-5|3-8|4-11|5-14");
        GsonRequest request = new GsonRequest<N3A8_GetAssessResult>(url, N3A8_GetAssessResult.class,
                new Response.Listener<N3A8_GetAssessResult>() {
                    @Override
                    public void onResponse(N3A8_GetAssessResult response) {
                        XLog.w(TAG, "N3A8 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A8 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void testN3A9() {
        String url = UrlBuilder.getN3A9_GetAssessSubmit("15928115878", "苹果", "5S", "1-2|2-5|3-8|4-11|5-14");
        GsonRequest request = new GsonRequest<N3A9_AssessSubmit>(url, N3A9_AssessSubmit.class,
                new Response.Listener<N3A9_AssessSubmit>() {
                    @Override
                    public void onResponse(N3A9_AssessSubmit response) {
                        XLog.w(TAG, "N3A9 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A9 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void testN3A11() {
        String url = UrlBuilder.getN3A11_GetAssessSubmit("201408031231342650001", 0, "342453");
        GsonRequest request = new GsonRequest<N3A11_OrderMsgResult>(url, N3A11_OrderMsgResult.class,
                new Response.Listener<N3A11_OrderMsgResult>() {
                    @Override
                    public void onResponse(N3A11_OrderMsgResult response) {
                        XLog.w(TAG, "N3A11 success, response=%s", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A11 error, " + error);
                    }
                }
        );
        requestQueue.add(request);
    }

    public void test() {

    }

}
