package com.lx.phonerecycle;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lx.phonerecycle.gsonbean.N2A6_SendSms;
import com.lx.phonerecycle.gsonbean.N2A7_FindPassword;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-24 下午7:34 <br>
 * description:
 */

@ContentView(R.layout.activity_find_back_password)
public class FindBackPasswordActivity extends BaseActivity {

    @InjectView(R.id.phone_edit)            private EditText phoneEdit;
    @InjectView(R.id.verifi_code_edit)      private EditText verifiCodeEdit;
    @InjectView(R.id.get_verifi_code_btn)   private Button getVerifiCodeBtn;
    @InjectView(R.id.find_password_btn)     private Button findPasswordBtn;

    private String curPhoneNum;

    private int count = 60;

    private Handler handler = new Handler();

    private Runnable counter = new Runnable() {
        @Override
        public void run() {
            getVerifiCodeBtn.setText(count + "秒后重新获取");
            count--;
            if (count == 0) {
                count = 60;
                getVerifiCodeBtn.setText("获取验证码");
            } else {
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("找回密码");
        getVerifiCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 60) {
                    return;
                }
                handler.post(counter);
                curPhoneNum = phoneEdit.getText().toString();
                if (!Tools.isLegitimatePhone(curPhoneNum)) {
                    showToastHint("手机号码不正确");
                    return;
                }
                showLoading();
                String url = UrlBuilder.getN2A6_SendSms(curPhoneNum, "1");
                GsonRequest request = new GsonRequest<N2A6_SendSms>(url, N2A6_SendSms.class,
                        new Response.Listener<N2A6_SendSms>() {
                            @Override
                            public void onResponse(N2A6_SendSms response) {
                                XLog.w(TAG, "N2A6 success, response=%s", response);
                                showToastHint(response.msg);
                                dismissLoading();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                XLog.e(TAG, "N2A6 error, " + error);
                                dismissLoading();
                                showToastHint("获取验证码异常，请稍候再试");
                            }
                        }
                );
                requestQueue.add(request);
            }
        });
        findPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(curPhoneNum)) {
                    return;
                }
                String phoneNum = curPhoneNum; //15828018063
                String verifiCode = verifiCodeEdit.getText().toString();
                showLoading();
                String url = UrlBuilder.getN2A7_SendSms(phoneNum, verifiCode);
                GsonRequest request = new GsonRequest<N2A7_FindPassword>(url, N2A7_FindPassword.class,
                        new Response.Listener<N2A7_FindPassword>() {
                            @Override
                            public void onResponse(N2A7_FindPassword response) {
                                XLog.w(TAG, "N2A7 success, response=%s", response);
                                showToastHint(response.msg);
                                dismissLoading();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                XLog.e(TAG, "N2A7 error, " + error);
                                dismissLoading();
                                showToastHint("找回密码异常，请稍候再试");
                            }
                        }
                );
                requestQueue.add(request);
            }
        });
    }

}
