package com.lx.phonerecycle;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lx.phonerecycle.gsonbean.N2A11_FAQ;
import com.lx.phonerecycle.gsonbean.N2A12_PrivacyPolicy;
import com.lx.phonerecycle.helper.HtmlHelper;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午1:54 <br>
 * description:
 * <p/>
 * 服务协议
 */

@ContentView(R.layout.activity_service_agreement)
public class ServiceAgreementActivity extends BaseActivity {

    public static final int TYPE_SERVICE = 0;
    public static final int TYPE_PRIVACY = 1;
    public static final int TYPE_ABOUT = 2;

    public static final String TYPE = "type";

    @InjectView(R.id.change_privacy_policy_scrollView) private ScrollView scrollView;
    @InjectView(R.id.change_privacy_policy_OK) private TextView OKBtn;
//    @InjectView(R.id.change_privacy_policy_text) private TextView contentText;
    @InjectView(R.id.change_privacy_policy_text_web) private WebView contentWeb;

    @InjectExtra(value = TYPE, optional = true)
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (TYPE_SERVICE==type) {
//            setTitleName("服务协议");
//        } else {
//            setTitleName("隐私政策");
//        }
        switch (type) {
            case TYPE_SERVICE:
                setTitleName("服务协议");
                break;
            case TYPE_PRIVACY:
                setTitleName("隐私政策");
                break;
            case TYPE_ABOUT:
                setTitleName("关于我们");
                break;
        }

        OKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        showLoading();
        String n2a12url = UrlBuilder.getN2A12_PrivacyPolicy();
        GsonRequest request = new GsonRequest<N2A12_PrivacyPolicy>(n2a12url, N2A12_PrivacyPolicy.class, new Response.Listener<N2A12_PrivacyPolicy>() {
            @Override
            public void onResponse(N2A12_PrivacyPolicy response) {
                dismissLoading();
                String content;
                try {
                    switch (type) {
                        case TYPE_SERVICE:
                            content = response.data.change.terms_service;
                            break;
                        case TYPE_PRIVACY:
                            content = response.data.change.privacy_policy;
                            break;
                        case TYPE_ABOUT:
                            content = response.data.about;
                            break;
                        default:
                            content = "";
                    }
                    if (!TextUtils.isEmpty(content)) {
                        HtmlHelper.write(content);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        contentWeb.setVisibility(View.VISIBLE);
                        String url = "file://" + getCacheDir().toString() + "/html/tmp.html";
                        contentWeb.loadUrl(url);
//                        contentText.setText(Html.fromHtml(content));
//                        scrollView.setVisibility(View.VISIBLE);
//                        OK.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                XLog.i(TAG, "N2A12 success, response=%s", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissLoading();
            }
        });
        requestQueue.add(request);
    }

}
