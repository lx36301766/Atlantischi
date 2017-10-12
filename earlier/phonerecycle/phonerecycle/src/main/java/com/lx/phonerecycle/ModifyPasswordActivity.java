package com.lx.phonerecycle;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lx.phonerecycle.gsonbean.N2A3_UserLogin;
import com.lx.phonerecycle.gsonbean.N2A5_ModifyUserPassword;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-24 下午5:08 <br>
 * description:
 */

@ContentView(R.layout.activity_modify_password)
public class ModifyPasswordActivity extends BaseActivity {

    @InjectView(R.id.old_password_edit)         private EditText oldPasswordEdit;
    @InjectView(R.id.new_password_edit)         private EditText newPasswordEdit;
    @InjectView(R.id.new_password_again_edit)   private EditText newPasswordAgainEdit;
    @InjectView(R.id.commit_btn)                private Button commitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("修改密码");
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPasswordEdit.getText().toString();
                String newPassword = newPasswordEdit.getText().toString();
                String newPasswordAgain = newPasswordAgainEdit.getText().toString();
                if (TextUtils.isEmpty(oldPassword)) {
                    showToastHint("请输入原密码");
                } else if (TextUtils.isEmpty(newPassword)) {
                    showToastHint("请输入新密码");
                } else if (TextUtils.isEmpty(newPasswordAgain)) {
                    showToastHint("请再次输入新密码");
                } else if (!newPassword.equals(newPasswordAgain)) {
                    showToastHint("两次输入的新密码不一致，请确认");
                } else {
                    showLoading();
                    String url = UrlBuilder.getN2A5_ModifyUserPassword(
                            oldPassword, newPassword, newPasswordAgain);
                    GsonRequest request = new GsonRequest<N2A5_ModifyUserPassword>(url, N2A5_ModifyUserPassword.class,
                            new Response.Listener<N2A5_ModifyUserPassword>() {
                                @Override
                                public void onResponse(N2A5_ModifyUserPassword response) {
                                    dismissLoading();
                                    XLog.w(TAG, "N2A5 success, response=%s", response);
                                    showToastHint(response.msg);
                                    oldPasswordEdit.setText("");
                                    newPasswordEdit.setText("");
                                    newPasswordAgainEdit.setText("");
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    dismissLoading();
                                    XLog.e(TAG, "N2A5 error, " + error);
                                    showToastHint("修改密码异常，请稍候再试");
                                }
                            }
                    );
                    requestQueue.add(request);
                }
            }
        });
    }

}
