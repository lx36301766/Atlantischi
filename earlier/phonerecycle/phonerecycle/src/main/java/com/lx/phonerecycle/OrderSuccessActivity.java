package com.lx.phonerecycle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lx.phonerecycle.gsonbean.N3A10_RedemptionSubmit;
import com.lx.phonerecycle.gsonbean.N3A11_OrderMsgResult;
import com.lx.phonerecycle.gsonbean.N3A12_GetOrderSmsCode;
import com.lx.phonerecycle.gsonbean.N3A9_AssessSubmit;
import com.lx.phonerecycle.helper.RecycleTypeManager;
import com.lx.phonerecycle.helper.ShareController;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午1:55 <br>
 * description:
 *
 * 下单成功
 */

@ContentView(R.layout.activity_order_success)
public class OrderSuccessActivity extends BaseActivity {

    @InjectView(R.id.share_to_social)           private TextView share;

    @InjectView(R.id.recycle_phone)             private View recyclePhoneLayout;
    @InjectView(R.id.recycle_phone_name)        private TextView recyclePhoneName;
    @InjectView(R.id.recycle_phone_time)        private TextView recyclePhoneTime;
    @InjectView(R.id.recycle_phone_price)       private TextView recyclePhonePrice;
    @InjectView(R.id.recycle_phone_order)       private TextView recyclePhoneOrder;
    @InjectView(R.id.recycle_verifi_code_edit)  private EditText recyclePhoneVerifiCodeEdit;
    @InjectView(R.id.recycle_get_verifi_code)   private TextView recycleGetVerifiCode;
    @InjectView(R.id.recycle_ok_btn)            private TextView recycleOkBtn;

    @InjectView(R.id.redem_phone)               private View redemPhoneLayout;
    @InjectView(R.id.redem_order_id)            private TextView redemOrderId;
    @InjectView(R.id.redem_phone_num)           private TextView redemPhoneNum;
    @InjectView(R.id.redem_order_time)          private TextView redemOrderTime;
    @InjectView(R.id.redem_phone_name)          private TextView redemPhoneName;
    @InjectView(R.id.redem_phone_price)         private TextView redemPhonePrice;
    @InjectView(R.id.redem_verifi_code_edit)    private EditText redemVerifiCodeEdit;
    @InjectView(R.id.redem_get_verifi_code)     private TextView redemGetVerifiCode;
    @InjectView(R.id.redem_commit)              private TextView redemCommit;

    public static final int ORDER_TYPE_RECYCLE_PHONE = 1;
    public static final int ORDER_TYPE_REDEMPTION_PHONE = 2;

    public static final String ORDER_TYPE = "order_type";

    @InjectExtra(value = ORDER_TYPE, optional = true)
    private int orderType = ORDER_TYPE_RECYCLE_PHONE;

    public static final String RECYCLE_SUBMIT_RESULT = "recycle_submit_result";
    public static final String REDEMPTION_SUBMIT_RESULT = "redemption_submit_result";

    @InjectExtra(value = RECYCLE_SUBMIT_RESULT, optional = true)
    private N3A9_AssessSubmit.Data recycleSubmitResult;

    @InjectExtra(value = REDEMPTION_SUBMIT_RESULT, optional = true)
    private N3A10_RedemptionSubmit.Data redemptionSubmitResult;

    private ShareController shareController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (orderType == ORDER_TYPE_RECYCLE_PHONE) {
            showView(recyclePhoneLayout);
            setRecycleData();
        } else if (orderType == ORDER_TYPE_REDEMPTION_PHONE) {
            showView(redemPhoneLayout);
            setRedemData();
        } else {
            showToastHint("Error order type:" + orderType);
        }
        shareController = new ShareController(mActivity);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareController.openShareContent();
            }
        });
    }

    private void setRecycleData() {
        recyclePhoneName.setText("你的手机 " + recycleSubmitResult.brand + recycleSubmitResult.model);
        recyclePhoneTime.setText("于 " + recycleSubmitResult.order_time);
        recyclePhonePrice.setText("以 " + recycleSubmitResult.price + "元 下单成功");
        recyclePhoneOrder.setText("订单号：" + recycleSubmitResult.order_id);
        recycleOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verifiCode = recyclePhoneVerifiCodeEdit.getText().toString();
                if (TextUtils.isEmpty(verifiCode)) {
                    showToastHint("请输入验证码");
                    return;
                }
                requestOrderMsgResult(0, verifiCode, recycleSubmitResult.order_id);
            }
        });
        recycleGetVerifiCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 60) {
                    return;
                }
                handler.post(new Counter(recycleGetVerifiCode));
                requestGetOrderSmsCode(0, recycleSubmitResult.order_id);
            }
        });
        handler.post(new Counter(recycleGetVerifiCode));
    }

    private void setRedemData() {
        redemOrderId.setText("订单号：" + redemptionSubmitResult.order_id);
        redemPhoneNum.setText("手机号：" + redemptionSubmitResult.phone);
        redemOrderTime.setText("时间：" + redemptionSubmitResult.date);
        redemPhoneName.setText("下单产品：" + redemptionSubmitResult.brand + redemptionSubmitResult.model);
        redemPhonePrice.setText("下单金额：" + redemptionSubmitResult.price + "元");
        redemCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verifiCode = redemVerifiCodeEdit.getText().toString();
                if (TextUtils.isEmpty(verifiCode)) {
                    showToastHint("请输入验证码");
                    return;
                }
                requestOrderMsgResult(1, verifiCode, redemptionSubmitResult.order_id);
            }
        });
        redemGetVerifiCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 60) {
                    return;
                }
                handler.post(new Counter(redemGetVerifiCode));
                requestGetOrderSmsCode(1, redemptionSubmitResult.order_id);
            }
        });
        handler.post(new Counter(redemGetVerifiCode));
    }

    private int count = 60;
    private Handler handler = new Handler();

    private class Counter implements Runnable{

        TextView textView;

        Counter(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void run() {
            textView.setText(count + "秒后重新获取");
            count--;
            if (count == 0) {
                count = 60;
                textView.setText("获取验证码");
            } else {
                handler.postDelayed(this, 1000);
            }
        }
    }

    private void requestOrderMsgResult(int cate, String verifiCode, String orderId) {
        showLoading();
        String url = UrlBuilder.getN3A11_GetAssessSubmit(orderId, cate, verifiCode);
        GsonRequest request = new GsonRequest<N3A11_OrderMsgResult>(url, N3A11_OrderMsgResult.class,
                new Response.Listener<N3A11_OrderMsgResult>() {
                    @Override
                    public void onResponse(N3A11_OrderMsgResult response) {
                        XLog.w(TAG, "N3A11 success, response=%s", response);
                        dismissLoading();
                        showToastHint(response.msg);
                        if (response.status == RESPONSE_OK) {
                            orderSuccess = true;
                            if (redemptionSubmitResult == null) {
                                Tools.jumpActivity(mActivity, OrdersManagementActivity.class);
                                finish();
                            } else {
//                                Tools.jumpActivity(mActivity, OrderPayActivity.class,
//                                        Pair.create(REDEMPTION_SUBMIT_RESULT, redemptionSubmitResult));
                                Tools.jumpActivity(mActivity, OrdersManagementActivity.class);
                                finish();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A11 error, " + error);
                        dismissLoading();
                        showToastHint("订单验证出现异常");
                    }
                }
        );
        requestQueue.add(request);
    }

    private void requestGetOrderSmsCode(int type, String orderId) {
        showLoading();
        String url = UrlBuilder.getN3A12_GetOrderSmsCode(orderId, type);
        GsonRequest request = new GsonRequest<N3A12_GetOrderSmsCode>(url, N3A12_GetOrderSmsCode.class,
                new Response.Listener<N3A12_GetOrderSmsCode>() {
                    @Override
                    public void onResponse(N3A12_GetOrderSmsCode response) {
                        XLog.w(TAG, "N3A12 success, response=%s", response);
                        dismissLoading();
                        showToastHint(response.msg);
                        if (response.status == RESPONSE_OK) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A11 error, " + error);
                        dismissLoading();
                        showToastHint("获取短信验证码出现异常");
                    }
                }
        );
        requestQueue.add(request);
    }

    private void showView(View view){
        redemPhoneLayout.setVisibility(view == redemPhoneLayout ? View.VISIBLE : View.GONE);
        recyclePhoneLayout.setVisibility(view == recyclePhoneLayout ? View.VISIBLE : View.GONE);
    }

    private boolean orderSuccess = false;

    @Override
    public void onBackPressed() {
        if (orderSuccess) {
            Tools.jumpActivity(mActivity, HomeActivity.class);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("订单尚未验证，确定要退出？")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Tools.jumpActivity(mActivity, HomeActivity.class);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }
}
