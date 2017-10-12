package com.lx.phonerecycle;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.Lists;
import com.lx.phonerecycle.gsonbean.N3A7_GetPhoneAssessArgs;
import com.lx.phonerecycle.gsonbean.N3A8_GetAssessResult;
import com.lx.phonerecycle.gsonbean.N3A9_AssessSubmit;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.helper.RecycleTypeManager;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.widget.AssessArgsTextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.Serializable;
import java.util.List;

import static android.view.ViewGroup.getChildMeasureSpec;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午1:51 <br>
 * description:
 *
 * 评估结果
 */

@ContentView(R.layout.activity_assessment_result)
public class AssessmentResultsActivity extends BaseActivity {

    public static class AssessResult implements Serializable {
        public String phoneNum;
        public String assessArgsStr;

        public N3A8_GetAssessResult.Data assessArgsData;
    }

    @InjectView(R.id.phone_name)            private TextView phoneName;
    @InjectView(R.id.phone_num)             private TextView phoneNum;
    @InjectView(R.id.phone_net_time)        private TextView phoneNetTime;
    @InjectView(R.id.phone_price)           private TextView phonePrice;
    @InjectView(R.id.confirm_order)         private TextView confirmOrder;

    @InjectView(R.id.phone_args_layout)     private LinearLayout phoneArgsLayout;

    public static final String ASSESS_RESULT = "AssessResult";

    @InjectExtra(value = "AssessResult", optional = true)
    private AssessResult assessResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("评估结果");

        setData();

        int type = RecycleTypeManager.getInstance().getRecycleType();
        if (type == RecycleTypeManager.TYPE_RECYCLE) {
            confirmOrder.setText("确认下单");
        } else if (type == RecycleTypeManager.TYPE_REDEMPTION) {
            confirmOrder.setText("下一步");
        } else {
            confirmOrder.setText("");
        }
    }

    private void setData() {
        phoneName.setText(assessResult.assessArgsData.name);
        phoneNum.setText("手机号码：" + assessResult.phoneNum);
        phoneNetTime.setText("在网时长：" + assessResult.assessArgsData.net_time);
        phonePrice.setText("￥" + assessResult.assessArgsData.price);
        int id = 1;
        for (N3A8_GetAssessResult.Data.List list : assessResult.assessArgsData.list) {
            List<String> args = Lists.newArrayList();
            for (N3A8_GetAssessResult.Data.List.Assess asses : list.assess) {
                args.add(asses.value);
            }
            addAssessItem(id, list);
            id++;
        }
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type = RecycleTypeManager.getInstance().getRecycleType();
                if (type == RecycleTypeManager.TYPE_RECYCLE) {
                    requestPhoneAssessSubmit();
                } else if (type == RecycleTypeManager.TYPE_REDEMPTION) {
//                    RedemptionDetailsActivity.PhoneDetails phoneDetails = new RedemptionDetailsActivity.PhoneDetails();
//                    phoneDetails.name = assessResult.assessArgsData.name;
//                    phoneDetails.imei = assessResult.assessArgsData.imei;
//                    phoneDetails.price = assessResult.assessArgsData.price;

                    Tools.jumpActivity(mActivity, RedemptionDetailsActivity.class,
                            Pair.create(AssessmentResultsActivity.ASSESS_RESULT, assessResult));
                } else {
                    showToastHint("error recycle type: " + type);
                }
            }
        });
    }

    private void requestPhoneAssessSubmit() {
        showLoading();
        String phoneNun = assessResult.phoneNum;
        String brand = assessResult.assessArgsData.brand;
        String model = assessResult.assessArgsData.model;
        String assess = assessResult.assessArgsStr;
        String url = UrlBuilder.getN3A9_GetAssessSubmit(phoneNun, brand, model, assess);
        GsonRequest request = new GsonRequest<N3A9_AssessSubmit>(url, N3A9_AssessSubmit.class,
                new Response.Listener<N3A9_AssessSubmit>() {
                    @Override
                    public void onResponse(N3A9_AssessSubmit response) {
                        XLog.w(TAG, "N3A9 success, response=%s", response);
                        dismissLoading();
                        if (response.status == RESPONSE_OK) {
                            Tools.jumpActivity(mActivity, OrderSuccessActivity.class,
                                    Pair.create(OrderSuccessActivity.RECYCLE_SUBMIT_RESULT, response.data),
                                    Pair.create(OrderSuccessActivity.ORDER_TYPE, OrderSuccessActivity.ORDER_TYPE_RECYCLE_PHONE));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A9 error, " + error);
                        dismissLoading();
                        showToastHint("下单出现异常！");
                    }
                }
        );
        requestQueue.add(request);
    }

    private void addAssessItem(int id, N3A8_GetAssessResult.Data.List list) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = Tools.px2Dp(this, 10);
        lp.bottomMargin = Tools.px2Dp(this, 10);
        TextView textView = new TextView(mActivity);
        textView.setText(id + "." + list.name);
        textView.setTextSize(15);
        textView.setTextColor(Color.BLACK);
        phoneArgsLayout.addView(textView, lp);
        initAutoViews(list);
    }

    private void checkIsAssessSelected(AssessArgsTextView assessArgsTextView) {
        String parentId = assessArgsTextView.getParentId();
        String childId = assessArgsTextView.getChildId();
        String[] items = assessResult.assessArgsStr.replace('|', ',').split(",");
        for (String item : items) {
            String[] ids = item.split("-");
            String p = ids[0];
            String c = ids[1];
            if (parentId.equals(p) && childId.equals(c)) {
                assessArgsTextView.setChecked(true);
                break;
            }
        }
    }

    private void initAutoViews(N3A8_GetAssessResult.Data.List list) {
        LinearLayout layout = new LinearLayout(mActivity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 10;
        phoneArgsLayout.addView(layout, layoutParams);
        int childWidth = 0;
        ArgsItem argsItem = new ArgsItem();
        for (N3A8_GetAssessResult.Data.List.Assess asses : list.assess) {
            AssessArgsTextView assessArgsTextView = new AssessArgsTextView(this);
            assessArgsTextView.setId(assessBaseId++);
            assessArgsTextView.setText(asses.value);
            assessArgsTextView.setParentId(list.id);
            assessArgsTextView.setChildId(asses.id);
            checkIsAssessSelected(assessArgsTextView);
//            assessArgsTextView.setOnClickListener(childOnClickListener);
            childWidth += getChildWidth(assessArgsTextView) + 10;
            int width = screenWidth - Tools.px2Dp(this, 40);
            if (childWidth > width) {
                layout = new LinearLayout(this);
                phoneArgsLayout.addView(layout, layoutParams);
                childWidth = getChildWidth(assessArgsTextView) + 10;
            }
            layout.addView(assessArgsTextView);
            argsItem.ids.add(assessArgsTextView.getId());
        }
        argsItem.selectedId = 0;
        argsItem.title = list.name;
        argsItems.add(argsItem);
    }

    private class ArgsItem {
        List<Integer> ids = Lists.newArrayList();
        int selectedId;
        String title;
    }

    List<ArgsItem> argsItems = Lists.newArrayList();

    private int assessBaseId = 0x000df3ff;

    private View.OnClickListener childOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof AssessArgsTextView) {
                AssessArgsTextView assessArgsTextView = (AssessArgsTextView) v;
                int viewId = assessArgsTextView.getId();
                start: for (ArgsItem argsItem : argsItems) {
                    for (Integer id : argsItem.ids) {
                        if (id == viewId) {
                            for (Integer id1 : argsItem.ids) {
                                AssessArgsTextView argsText = (AssessArgsTextView) findViewById(id1);
                                argsText.setChecked(id == id1);
                            }
                            argsItem.selectedId = id;
                            break start;
                        }
                    }
                }
            }
        }
    };

    private int getChildWidth(TextView child) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child.getLayoutParams();
        if (lp == null) {
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            child.setLayoutParams(lp);
        }
        int childHeightSpec = getChildMeasureSpec(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), 0, lp.height);
        int childWidthSpec = getChildMeasureSpec(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), 0, lp.width);
        child.measure(childWidthSpec, childHeightSpec);
        int width = child.getMeasuredWidth();
        int height = child.getMeasuredHeight();
        XLog.d(TAG, "W=%d, H=%d, name=%s", width, height, child.getText());
        return width;
    }

}
