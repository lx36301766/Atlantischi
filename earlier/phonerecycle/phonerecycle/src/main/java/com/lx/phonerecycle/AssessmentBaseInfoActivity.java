package com.lx.phonerecycle;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lx.phonerecycle.gsonbean.N2A3_UserLogin;
import com.lx.phonerecycle.gsonbean.N3A6_GetPhoneBrandAndModel;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.widget.CheckTextView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.ViewGroup.getChildMeasureSpec;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午1:50 <br>
 * description:
 * <p/>
 * 回收评估，基本信息
 */

@ContentView(R.layout.activity_assessment_base_info)
public class AssessmentBaseInfoActivity extends BaseActivity {

    public static class AssessBaseInfo implements Serializable {
        public String phoneNum;
        public String brand;
        public String model;
    }

    @InjectView(R.id.scrollview) ScrollView mScrollView;
    @InjectView(R.id.base_info_edit) EditText phoneNumber;
    @InjectView(R.id.base_info_next_step) Button nextStep;

    @InjectView(R.id.brand_layout) LinearLayout brandLayout;
    @InjectView(R.id.model_layout) LinearLayout modelLayout;
    @InjectView(R.id.base_info_main) LinearLayout mainLayout;

    @InjectView(R.id.phone_model_title) TextView phoneModelTitle;

    private N2A3_UserLogin loginInfo;
    private HashMap<String, ArrayList<String>> brandMap;
    private int modelLoadingCount;

    @InjectExtra(value = "brand", optional = true)
    private String brand;

    @InjectExtra(value = "model", optional = true)
    private String model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("回收评估");
//        phoneNumber.setText("15928115878");
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AssessBaseInfo baseInfo = new AssessBaseInfo();
                String phoneNum = phoneNumber.getText().toString();
                if (!Tools.isLegitimatePhone(phoneNum)) {
                    showToastHint("请输入手机号码");
                    return;
                }
                if (parentSelectView == null) {
                    showToastHint("请选择品牌");
                    return;
                }
                if (childSelectView == null) {
                    showToastHint("请选择型号");
                    return;
                }
                String brand = parentSelectView.getText().toString();
                String model = childSelectView.getText().toString();
                baseInfo.phoneNum = phoneNum;
                baseInfo.brand = brand;
                baseInfo.model = model;
                Tools.jumpActivity(mActivity, AssessmentDetailsInfoActivity.class,
                        Pair.create("AssessBaseInfo", baseInfo));
            }
        });
        loginInfo = LoginInfoHelper.read();
        if (loginInfo != null) {
            brandMap = Maps.newHashMap();
            showLoading();
            requestBrand();
        }
    }

    private void requestBrand() {
        String url = UrlBuilder.getN3A6_GetPhoneBrandAndModel("");
        GsonRequest request = new GsonRequest<N3A6_GetPhoneBrandAndModel>(url, N3A6_GetPhoneBrandAndModel.class,
                new Response.Listener<N3A6_GetPhoneBrandAndModel>() {

                    @Override
                    public void onResponse(N3A6_GetPhoneBrandAndModel response) {
                        XLog.w(TAG, "N3A6 success, response=%s", response);
                        dismissLoading();
                        List<N3A6_GetPhoneBrandAndModel.Data.Brand> brands = response.data.brand;
                        ArrayList<String> brandList = Lists.newArrayList();
                        for (N3A6_GetPhoneBrandAndModel.Data.Brand brand : brands) {
                            brandList.add(brand.name);
                        }
                        initAutoViews(brandLayout, brandList);
                        modelLoadingCount = brands.size();
                        if (modelLoadingCount == 0) {
                            showToastHint("没有品牌数据");
                            return;
                        }
                        for (final N3A6_GetPhoneBrandAndModel.Data.Brand brand : brands) {
                            requestModelByBrand(brand);
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A6 error, " + error);
                        showToastHint("获取手机品牌或型号出现异常！");
                    }
                }
        );
        requestQueue.add(request);
    }

    private void requestModelByBrand(final N3A6_GetPhoneBrandAndModel.Data.Brand brand) {
        showLoading();
        String url = UrlBuilder.getN3A6_GetPhoneBrandAndModel(brand.name);
        GsonRequest request = new GsonRequest<N3A6_GetPhoneBrandAndModel>(url, N3A6_GetPhoneBrandAndModel.class,
                new Response.Listener<N3A6_GetPhoneBrandAndModel>() {
                    @Override
                    public void onResponse(N3A6_GetPhoneBrandAndModel response) {
                        List<N3A6_GetPhoneBrandAndModel.Data.Model> models = response.data.model;
                        final ArrayList<String> strings = new ArrayList<String>();
                        for (N3A6_GetPhoneBrandAndModel.Data.Model model : models) {
                            strings.add(model.name);
                        }
                        brandMap.put(brand.name, strings);
                        modelLoadingCount--;
                        XLog.e("brandName= " + brand.name + ", modelLoadingCount=" + modelLoadingCount);
                        checkIsModelLoadingEnd();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A6 error, " + error);
                        modelLoadingCount--;
                        XLog.e("brandName= " + brand.name + ", modelLoadingCount=" + modelLoadingCount);
                        checkIsModelLoadingEnd();
                    }
                }
        );
        requestQueue.add(request);
    }

    private void checkIsModelLoadingEnd() {
        dismissLoading();
        if (modelLoadingCount == 0) {
            System.out.print(brandMap);
            XLog.d(TAG, "brandMap=%s", brandMap);
            mainLayout.setVisibility(View.VISIBLE);
            View view = brandLayout.getChildAt(0);
            if (view != null) {
                View child = ((LinearLayout) view).getChildAt(0);
                if (child != null) {
//                    child.performClick();
                }
            }
            if (parentSelectView != null) {
                parentSelectView.performClick();
            }
        }
    }

    private boolean hasBrandChecked = false;

    private void initAutoViews(LinearLayout parentLayout, ArrayList<String> names) {
        LinearLayout layout = new LinearLayout(mActivity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parentLayout.addView(layout, layoutParams);
        int childWidth = 0;
        for (String item : names) {
            CheckTextView checkTextView = new CheckTextView(this);
            checkTextView.setText(item);
            checkTextView.setName(item);
            checkTextView.setOnClickListener(parentOnClickListener);
            if (!TextUtils.isEmpty(brand) && item.equals(brand)) {
                checkTextView.setChecked(true);
                hasBrandChecked = true;
                brand = null;
                needCheck = false;
                parentSelectView = checkTextView;
//                checkTextView.performClick();
            }
            childWidth += getChildWidth(checkTextView) + 10;
            int width = screenWidth - 40;
            if (childWidth > width) {
                layout = new LinearLayout(this);
                LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                parentLayout.addView(layout, childLp);
                childWidth = getChildWidth(checkTextView) + 10;
            }
//            layout = new LinearLayout(this);
//            LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            parentLayout.addView(layout, childLp);
//            childWidth = getChildWidth(checkTextView) + 10;
            layout.addView(checkTextView);
        }
    }

    private boolean needCheck = true;

    private void initAutoViews2(LinearLayout parentLayout, ArrayList<String> names) {
        LinearLayout layout = new LinearLayout(mActivity);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parentLayout.addView(layout, layoutParams);
        int childWidth = 0;
        int i = 0;
        for (String item : names) {
            CheckTextView checkTextView = new CheckTextView(this);
            checkTextView.setText(item);
            checkTextView.setName(item);
            if (i == 0) {
                checkTextView.setChecked(true);
                childSelectView = checkTextView;
            }
            checkTextView.setOnClickListener(childOnClickListener);
            if (item.equals(model) && hasBrandChecked) {
                checkTextView.setChecked(true);
                model = null;
            }
            childWidth += getChildWidth(checkTextView) + 10;
            int width = screenWidth - 40;
//            if (childWidth > width) {
//                layout = new LinearLayout(this);
//                LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(
//                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                parentLayout.addView(layout, childLp);
//                childWidth = getChildWidth(checkTextView) + 10;
//            }
            layout = new LinearLayout(this);
            LinearLayout.LayoutParams childLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            parentLayout.addView(layout, childLp);
            childWidth = getChildWidth(checkTextView) + 10;
            layout.addView(checkTextView);
            i++;
        }
    }

    private CheckTextView parentSelectView;

    private View.OnClickListener parentOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (needCheck) {
                String phoneNum = phoneNumber.getText().toString();
                if (!Tools.isLegitimatePhone(phoneNum)) {
                    showToastHint("请输入正确的手机号码");
                    return;
                }
            }
            needCheck = true;
            if (v instanceof CheckTextView) {
//                CheckTextView ctv = (CheckTextView) v;
//                if (ctv.isChecked()) {
//                    return;
//                }
                if (parentSelectView != null) {
                    parentSelectView.setChecked(false);
                }
                childSelectView = null;
                parentSelectView = (CheckTextView) v;
                parentSelectView.setChecked(true);
                ArrayList<String> models = brandMap.get(parentSelectView.getName());
                if (models != null) {
                    modelLayout.removeAllViews();
                    initAutoViews2(modelLayout, models);
                }
//                int[] location = new int[2];
//                phoneModelTitle.getLocationOnScreen(location);
//                int height = ((ViewGroup) titleNameText.getParent()).getHeight();
//                int dy = location[1] - height;
                int dy = brandLayout.getHeight();
                mScrollView.scrollTo(0, dy);
            }
        }
    };

    private CheckTextView childSelectView;

    private View.OnClickListener childOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String phoneNum = phoneNumber.getText().toString();
            if (!Tools.isLegitimatePhone(phoneNum)) {
                showToastHint("请输入正确的手机号码");
                return;
            }
            if (v instanceof CheckTextView) {
                CheckTextView ctv = (CheckTextView) v;
                if (ctv.isChecked()) {
                    return;
                }
                if (childSelectView != null) {
                    childSelectView.setChecked(false);
                }
                childSelectView = (CheckTextView) v;
                childSelectView.setChecked(true);
            }
        }
    };

    private int getChildWidth(CheckTextView child) {
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
