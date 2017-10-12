package com.lx.phonerecycle;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lx.phonerecycle.gsonbean.N4A1_GetOrderType;
import com.lx.phonerecycle.gsonbean.N4A3_GetOrderDetail;
import com.lx.phonerecycle.gsonbean.N4A4_DeleteOrder;
import com.lx.phonerecycle.helper.CustomToast;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

/**
 * Copyright (C) 2014 hx <br>
 * All rights reserved. <br>
 * author:  xiang.huang <br>
 * date:  2014/7/20 20:31 <br>
 * description:
 */

@ContentView(R.layout.activity_orders_details)
public class OrdersDetailsActivity extends BaseActivity {

    public static final String TYPE = "type";
    public static final String ORDER_ID = "order_id";

    @InjectView(R.id.user_name)         private TextView nameText;
    @InjectView(R.id.user_phone)        private TextView phoneText;
    @InjectView(R.id.user_addr)         private TextView addrText;

    @InjectView(R.id.phone_args_title)  private TextView phoneArgsTitle;
    @InjectView(R.id.phone_args)        private TextView phoneArgs;
    @InjectView(R.id.phone_desc)        private TextView phoneDesc;

    @InjectView(R.id.phone_name)        private TextView phoneName;

    @InjectView(R.id.delete_order)      private TextView deleteoOrder;

    @InjectExtra(value = TYPE, optional = true)
    private int type;

    @InjectExtra(value = ORDER_ID, optional = true)
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("订单详情");

        if (type == 0) {
            phoneArgsTitle.setVisibility(View.GONE);
            phoneArgs.setVisibility(View.GONE);
        } else if (type == 1) {

        }
        deleteoOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDeleteOrder();
            }
        });
        requestOrderInfoData();
    }

    private void requestOrderInfoData() {
        showLoading();
        String url = UrlBuilder.getN4A3_GetOrderDetail(type, orderId);
        GsonRequest request = new GsonRequest<N4A3_GetOrderDetail>(url, N4A3_GetOrderDetail.class,
                new Response.Listener<N4A3_GetOrderDetail>() {
                    @Override
                    public void onResponse(N4A3_GetOrderDetail response) {
                        XLog.w(TAG, "N4A3 success, response=%s", response);
                        if (response.status == 111) {
                            setData(response.data);
                        } else {
                            CustomToast.show(mActivity, response.msg);
                        }
                        dismissLoading();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N4A4 error, " + error);
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }

    private void setData(N4A3_GetOrderDetail.Data data) {
        phoneName.setText(data.brand + data.model);
        nameText.setText(data.uname);
        phoneText.setText(data.phone_no);
        addrText.setText(data.address);
        phoneDesc.setText(data.mobile_detail);
        if (type == 1) {
            phoneArgs.setText(data.new_mobile_detail);
        }
    }

    private void requestDeleteOrder() {
        showLoading();
        String url = UrlBuilder.getN4A4_DeleteOrder(type, orderId);
        GsonRequest request = new GsonRequest<N4A4_DeleteOrder>(url, N4A4_DeleteOrder.class,
                new Response.Listener<N4A4_DeleteOrder>() {
                    @Override
                    public void onResponse(N4A4_DeleteOrder response) {
                        XLog.w(TAG, "N4A4 success, response=%s", response);
                        if (response.status == 111) {
                            showToastHint(response.msg);
                            setResult(5);
                            finish();
                        } else {
                            showToastHint(response.msg);
                        }
                        dismissLoading();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N4A4 error, " + error);
                        showToastHint("删除订单出现异常，请稍候再试");
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }

}
