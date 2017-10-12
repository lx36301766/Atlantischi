package com.lx.phonerecycle;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lx.phonerecycle.gsonbean.N2A3_UserLogin;
import com.lx.phonerecycle.helper.ActivityHelper;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.request.MainInfo;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Created by princeBB on 2014/7/9.
 *
 * 登陆
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

    @InjectView(R.id.user_name_edit)    private EditText userNameEdit;
    @InjectView(R.id.password_edit)     private EditText passwordEdit;
    @InjectView(R.id.login_btn)         private Button loginBtn;
    @InjectView(R.id.register_btn)      private Button registerBtn;
    @InjectView(R.id.forget_password)   private TextView forgetPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    showToastHint("请输入用户名");
                } else if (TextUtils.isEmpty(password)) {
                    showToastHint("请输入密码");
                } else {
                    showLoading();
                    String n2a3Url = UrlBuilder.getN2A3_UserLogin(userName, password);
                    GsonRequest request = new GsonRequest<N2A3_UserLogin>(
                            n2a3Url, N2A3_UserLogin.class,
                            new Response.Listener<N2A3_UserLogin>() {
                                @Override
                                public void onResponse(N2A3_UserLogin response) {
                                    XLog.e(TAG, "N2A3 success, response=%s", response);
                                    dismissLoading();
                                    if (response.status == RESPONSE_OK) {
                                        LoginInfoHelper.save(response);
                                    }
                                    showToastHint(response.msg);
                                    if (response.status == RESPONSE_OK) {
                                        Tools.jumpActivity(mActivity, HomeActivity.class);
                                        finish();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    XLog.e(TAG, "N2A3 error, " + error);
                                    dismissLoading();
                                    showToastHint("登陆出现异常，请稍候再试");
                                }
                            });
                    requestQueue.add(request);
                }
            }
        });
        forgetPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jumpActivity(mActivity, FindBackPasswordActivity.class);
            }
        });

    }

}
