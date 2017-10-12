package com.lx.phonerecycle;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lx.phonerecycle.gsonbean.N3A10_RedemptionSubmit;
import com.lx.phonerecycle.helper.RecycleTypeManager;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-8-3 下午11:37 <br>
 * description:
 *
 * 新机换购，确认信息
 */

@ContentView(R.layout.activity_redemption_details)
public class RedemptionDetailsActivity extends BaseActivity {

    @InjectView(R.id.ori_phone_name)        private TextView oriPhoneNameText;
    @InjectView(R.id.ori_phone_imei)        private TextView oriPhoneImeiText;
    @InjectView(R.id.ori_phone_price)       private TextView oriPhonePriceText;
    @InjectView(R.id.redem_phone_name)      private TextView redemPhoneNameText;
    @InjectView(R.id.redem_phone_price)     private TextView redemPhonePriceText;
    @InjectView(R.id.redem_diff_price)      private TextView redemDiffPriceText;

    @InjectView(R.id.redemption_phone_args) private LinearLayout redemPhoneArgsGroup;

    @InjectView(R.id.confirm_order)         private TextView confirmOrderText;
    @InjectView(R.id.customer_order)        private TextView customerOrderText;

    @InjectExtra(value = AssessmentResultsActivity.ASSESS_RESULT, optional = true)
    private AssessmentResultsActivity.AssessResult assessResult;

    RecycleTypeManager.RedemptionPhoneInfo redemInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        redemDiffPriceText.setText("换购补差", TextView.BufferType.EDITABLE);
        Spannable sp = (Spannable) redemDiffPriceText.getText();
        sp.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(12, true), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        redemDiffPriceText.setText(sp);

        if (assessResult != null) {
            oriPhoneNameText.setText(assessResult.assessArgsData.name);
            oriPhoneImeiText.setText("IMEI:" + assessResult.assessArgsData.imei);
            oriPhonePriceText.setText("￥" + assessResult.assessArgsData.price);
        }

        redemInfo = RecycleTypeManager.getInstance().getRedemptionPhoneInfo();
        if (redemInfo != null) {
            redemPhoneNameText.setText(redemInfo.name);
            int price = 0;
            try {
                int oldPrice = Integer.parseInt(assessResult.assessArgsData.price);
                int newPrice = Integer.parseInt(redemInfo.price);
                price = newPrice - oldPrice;
            } catch (Exception e) {
                e.printStackTrace();
            }
            redemPhonePriceText.setText("￥" + price);
            List<PhoneDetailsActivity.Params> paramses = redemInfo.redemptionPhoneArgs;
            for (PhoneDetailsActivity.Params params : paramses) {
                TextView argText = new TextView(mActivity);
                argText.setTextColor(Color.BLACK);
                argText.setTextSize(13);
                argText.setText(params.key + "：" + params.value);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.leftMargin = Tools.px2dip(mActivity, 50);
                lp.topMargin = Tools.px2dip(mActivity, 5);
                redemPhoneArgsGroup.addView(argText, lp);
            }
        }
        confirmOrderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestRedemInfo();
            }
        });
        customerOrderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jumpActivity(mActivity, CustomerOrderActivity.class,
                        Pair.create(AssessmentResultsActivity.ASSESS_RESULT, assessResult));
            }
        });
    }

    public void requestRedemInfo() {
        showLoading();
        String phoneNum = assessResult.phoneNum;
        String brand = assessResult.assessArgsData.brand;
        String model = assessResult.assessArgsData.model;
        String assess = assessResult.assessArgsStr;
        String redemPhoneId = redemInfo.id;
        String url = UrlBuilder.getN3A10_RedemptionSubmit(phoneNum, brand, model, assess, redemPhoneId);
        GsonRequest request = new GsonRequest<N3A10_RedemptionSubmit>(url, N3A10_RedemptionSubmit.class,
                new Response.Listener<N3A10_RedemptionSubmit>() {
                    @Override
                    public void onResponse(N3A10_RedemptionSubmit response) {
                        XLog.w(TAG, "N3A10 success, response=%s", response);
                        dismissLoading();
                        if (response.status == RESPONSE_OK) {
                            Tools.jumpActivity(mActivity, OrderSuccessActivity.class,
                                    Pair.create(OrderSuccessActivity.REDEMPTION_SUBMIT_RESULT, response.data),
                                    Pair.create(OrderSuccessActivity.ORDER_TYPE, OrderSuccessActivity.ORDER_TYPE_REDEMPTION_PHONE));
                        } else {
                            showToastHint(response.msg);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A10 error, " + error);
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }


}
