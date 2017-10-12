package com.lx.phonerecycle;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.*;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.common.collect.ImmutableList;
import com.lx.phonerecycle.helper.CustomToast;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.gsonbean.N2A2_UserRegister;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.util.List;

/**
 * Created by princeBB on 2014/7/10.
 *
 * 注册
 */

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @InjectView(R.id.choice_category)       private Button choiceCategory;
    @InjectView(R.id.register_btn)          private Button registerBtn;
    @InjectView(R.id.user_name_edit)        private EditText userNameEdit;
    @InjectView(R.id.phone_number_edit)     private EditText phoneNumberEdit;
    @InjectView(R.id.password_edit)         private EditText passwordEdit;
    @InjectView(R.id.password_edit_again)   private EditText passwordEditAgain;
    @InjectView(R.id.regist_agree_check_box)private CheckBox checkBox;
    @InjectView(R.id.register_provision)    private TextView provision;
    @InjectView(R.id.register_privacy_provision)    private TextView privacy;

    private SpinnerDropDownItems mPopupWindow;

    private static final int NO_CHOICE = -1;
    private static final int BUSINESS_OFFICE = 1;
    private static final int ACCOUNT_MANAGER  = 2;
    private static final int OTHER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        choiceCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPopupWindow == null) {
                    mPopupWindow = new SpinnerDropDownItems(mActivity, choiceCategory.getWidth(), 200);
                }
                if (!mPopupWindow.isShowing()) {
                    mPopupWindow.showAsDropDown(choiceCategory, 0, 0);
                }
            }
        });

        provision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jumpActivity(mActivity, ServiceAgreementActivity.class,
                        Pair.create(ServiceAgreementActivity.TYPE,ServiceAgreementActivity.TYPE_SERVICE));
            }
        });

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jumpActivity(mActivity, ServiceAgreementActivity.class,
                        Pair.create(ServiceAgreementActivity.TYPE,ServiceAgreementActivity.TYPE_PRIVACY));
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typeId;
                try {
                    typeId = (Integer) choiceCategory.getTag();
                } catch (NullPointerException e) {
                    typeId = NO_CHOICE;
                }
                switch (typeId) {
                    case NO_CHOICE:
                        showToastHint("请选择类别");
                        return;
                    case BUSINESS_OFFICE:
                        break;
                    case ACCOUNT_MANAGER:
                        break;
                    case OTHER:
                        break;
                    default:
                        showToastHint("未知类别：" + typeId);
                        break;
                }
                String userName = userNameEdit.getText().toString();
                String phoneNumber = phoneNumberEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String passwordAgain = passwordEditAgain.getText().toString();
                if (TextUtils.isEmpty(userName)) {
                    showToastHint("请输入用户名");
                } else if (!checkBox.isChecked()){
                    showToastHint("请同意条款");
                } else if (TextUtils.isEmpty(phoneNumber)) {
                    showToastHint("请输入手机号码");
                } else if (TextUtils.isEmpty(password)) {
                    showToastHint("请输入密码");
                } else if (TextUtils.isEmpty(passwordAgain)) {
                    showToastHint("请输入确认密码");
                } else if (!password.equals(passwordAgain)) {
                    showToastHint("两次输入的密码不一致，请确认");
                } else {
                    String n2a2Url = UrlBuilder.getN2A2_UserRegister(typeId, userName, phoneNumber, password, passwordAgain);
                    GsonRequest request = new GsonRequest<N2A2_UserRegister>(
                            n2a2Url, N2A2_UserRegister.class,
                            new Response.Listener<N2A2_UserRegister>() {
                                @Override
                                public void onResponse(N2A2_UserRegister response) {
                                    XLog.d(TAG, "N2A2 onResponse, status=%s, msg=%s", response.status, response.msg);
                                    showToastHint(response.msg);
                                    if (response.status == RESPONSE_OK) {
                                        Tools.jumpActivity(mActivity, LoginActivity.class);
                                        finish();
//                                        showToastHint("注册成功");
                                    } else {
//                                        showToastHint("注册失败，" + response.msg);
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    XLog.e(TAG, "N2A2 error, " + error);
                                    showToastHint("注册异常，请稍候再试");

                                }
                            });
                    requestQueue.add(request);
                }
            }
        });
    }

    List<String> types = ImmutableList.of("营业厅", "客户经理", "其他");

    private class SpinnerDropDownItems extends PopupWindow {

        public SpinnerDropDownItems(Context context, int width, int height) {
            super(context);
            setWidth(width);
            setHeight(height);
            setFocusable(true);
            setOutsideTouchable(true);
            setBackgroundDrawable(new BitmapDrawable());
            ListView mList = new ListView(context);
            mList.setBackgroundResource(R.drawable.edit_down);
            mList.setVerticalScrollBarEnabled(false);
            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    choiceCategory.setText(parent.getItemAtPosition(position).toString());
                    int typeId = position + 1;
                    choiceCategory.setTag(typeId);
                    dismiss();
                }
            });
            mList.setAdapter(new ArrayAdapter<String>(
                    context,
                    R.layout.item_list,
                    types));
            setContentView(mList);
        }
    }
}
