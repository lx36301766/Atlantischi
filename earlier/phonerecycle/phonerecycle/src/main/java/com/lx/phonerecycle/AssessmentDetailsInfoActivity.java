package com.lx.phonerecycle;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.Lists;
import com.lx.phonerecycle.gsonbean.N3A7_GetPhoneAssessArgs;
import com.lx.phonerecycle.gsonbean.N3A8_GetAssessResult;
import com.lx.phonerecycle.helper.HtmlHelper;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.widget.AssessArgsTextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.util.List;

import static android.view.ViewGroup.getChildMeasureSpec;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午1:56 <br>
 * description:
 *
 * 回收评估，详细信息
 */

@ContentView(R.layout.activity_assessment_details_info)
public class AssessmentDetailsInfoActivity extends BaseActivity {

    @InjectView(R.id.phone_name)            private TextView phoneName;
    @InjectView(R.id.phone_num)             private TextView phoneNum;
    @InjectView(R.id.phone_net_time)        private TextView phoneNetTime;
    @InjectView(R.id.phone_price)           private TextView phonePrice;
    @InjectView(R.id.next_step)             private TextView nextStepBtn;

    @InjectView(R.id.phone_args_layout)     private LinearLayout phoneArgsLayout;

    public static final String ASSESS_BASE_INFO = "assess_base_info";

    @InjectExtra(value = "AssessBaseInfo", optional = true)
    private AssessmentBaseInfoActivity.AssessBaseInfo baseInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("回收评估");

        requestAssessArgs();
    }

    private void requestAssessArgs() {
        if (baseInfo == null) {
            return;
        }
        String phoneNun = baseInfo.phoneNum;
        String brand = baseInfo.brand;
        String model = baseInfo.model;
        String url = UrlBuilder.getN3A7_GetPhoneAssessArgs(phoneNun, brand, model);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        showLoading();
        GsonRequest request = new GsonRequest<N3A7_GetPhoneAssessArgs>(url, N3A7_GetPhoneAssessArgs.class,
                new Response.Listener<N3A7_GetPhoneAssessArgs>() {
                    @Override
                    public void onResponse(N3A7_GetPhoneAssessArgs response) {
                        XLog.w(TAG, "N3A7 success, response=%s", response);
                        dismissLoading();
                        if (response.status == RESPONSE_OK) {
                            setData(response.data);
                        } else {
                            showToastHint(response.msg);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A7 error, " + error);
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }

    N3A7_GetPhoneAssessArgs.Data assessArgsData;

    private void setData(N3A7_GetPhoneAssessArgs.Data data) {
        assessArgsData = data;
        phoneName.setText(data.name);
        phoneNum.setText("手机号码：" + baseInfo.phoneNum);
        phoneNetTime.setText("在网时长：" + data.net_time);
        phonePrice.setText("￥" + data.price);
        int id = 1;
        for (N3A7_GetPhoneAssessArgs.Data.List list : data.list) {
            List<String> args = Lists.newArrayList();
            for (N3A7_GetPhoneAssessArgs.Data.List.Assess asses : list.assess) {
                args.add(asses.value);
            }
            addAssessItem(id, list);
            id++;
        }
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ArgsItem argsItem : argsItems) {
                    if (argsItem.selectedId == 0) {
                        showToastHint("请选择" + argsItem.title);
                        return;
                    }
                }
                StringBuilder assessArgs = new StringBuilder();
                for (ArgsItem argsItem : argsItems) {
                    int viewId = argsItem.selectedId;
                    AssessArgsTextView assessArgsTextView = (AssessArgsTextView) findViewById(viewId);
                    String parentId = assessArgsTextView.getParentId();
                    String childId = assessArgsTextView.getChildId();
                    assessArgs.append(parentId).append("-").append(childId).append("|");
                }
                assessArgs.deleteCharAt(assessArgs.length() - 1);
                requestAssessResult(assessArgs.toString());
            }
        });
    }

    private void requestAssessResult(final String assessArgsStr) {
        showLoading();
        String url = UrlBuilder.getN3A8_GetAssessResult(baseInfo.phoneNum, baseInfo.brand,
                baseInfo.model, assessArgsStr);
        GsonRequest request = new GsonRequest<N3A8_GetAssessResult>(url, N3A8_GetAssessResult.class,
                new Response.Listener<N3A8_GetAssessResult>() {
                    @Override
                    public void onResponse(N3A8_GetAssessResult response) {
                        XLog.w(TAG, "N3A8 success, response=%s", response);
                        dismissLoading();
                        AssessmentResultsActivity.AssessResult assessResult = new AssessmentResultsActivity.AssessResult();
                        assessResult.phoneNum = baseInfo.phoneNum;
                        assessResult.assessArgsStr = assessArgsStr;
                        assessResult.assessArgsData = response.data;
                        Tools.jumpActivity(mActivity, AssessmentResultsActivity.class,
                                Pair.create("AssessResult", assessResult));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A8 error, " + error);
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }

    private void addAssessItem(int id, final N3A7_GetPhoneAssessArgs.Data.List list) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = Tools.px2Dp(this, 10);
        lp.bottomMargin = Tools.px2Dp(this, 10);
        LinearLayout lineLayout = new LinearLayout(this);
        lineLayout.setOrientation(LinearLayout.HORIZONTAL);
        phoneArgsLayout.addView(lineLayout, lp);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(mActivity);
        textView.setText(id + "." + list.name);
        textView.setTextSize(15);
        textView.setTextColor(Color.BLACK);
        lineLayout.addView(textView, lp2);

        LinearLayout.LayoutParams lp3 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp3.gravity = Gravity.CENTER_VERTICAL;
        lp3.leftMargin = Tools.px2Dp(this, 10);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.assess_args_wenhao);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemarkDialog(list.remark);
            }
        });
        lineLayout.addView(imageView, lp3);

        initAutoViews(list);
    }

    private void showRemarkDialog(String remark) {
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundResource(R.drawable.shape_dialog_bounds);
        WebView webView = new WebView(this);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp2.topMargin = 5;
        lp2.bottomMargin = 5;
        lp2.rightMargin = 5;
        lp2.leftMargin = 5;
        layout.addView(webView, lp2);
        if (!TextUtils.isEmpty(remark)) {
            HtmlHelper.write(remark);
        } else {
            HtmlHelper.write("");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String url = "file://" + getCacheDir().toString() + "/html/tmp.html";
        webView.loadUrl(url);
        final Dialog dialog = new Dialog(this, R.style.custom_dialog2);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setContentView(layout);
        dialog.show();
    }

    private void initAutoViews(N3A7_GetPhoneAssessArgs.Data.List list) {
        LinearLayout layout = new LinearLayout(mActivity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 10;
        phoneArgsLayout.addView(layout, layoutParams);
        int childWidth = 0;
        ArgsItem argsItem = new ArgsItem();
        for (N3A7_GetPhoneAssessArgs.Data.List.Assess asses : list.assess) {
            AssessArgsTextView assessArgsTextView = new AssessArgsTextView(this);
            assessArgsTextView.setId(assessBaseId++);
            assessArgsTextView.setText(asses.value);
            assessArgsTextView.setParentId(list.id);
            assessArgsTextView.setChildId(asses.id);
            assessArgsTextView.setOnClickListener(childOnClickListener);
            childWidth += getChildWidth(assessArgsTextView) + 10;
            int width = screenWidth - Tools.px2Dp(this, 50);
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
