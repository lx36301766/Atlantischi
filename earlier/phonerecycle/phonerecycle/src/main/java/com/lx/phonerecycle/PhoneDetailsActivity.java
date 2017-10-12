package com.lx.phonerecycle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.Lists;
import com.google.inject.Key;
import com.lx.phonerecycle.gsonbean.N3A5_GetPhoneRecomDetails;
import com.lx.phonerecycle.helper.HtmlHelper;
import com.lx.phonerecycle.helper.RecycleTypeManager;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.viewpagerindicator.CirclePageIndicator;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午1:49 <br>
 * description:
 *
 * 新机详情
 */

@ContentView(R.layout.activity_new_phone_details)
public class PhoneDetailsActivity extends BaseActivity {

    public static class PhoneItem implements Serializable {
        public String id;
        public String imgUrl;
        public String title;
        public String price;
        public String is_contract;
        public List<Params> params = Lists.newArrayList();
    }

    public static class Params implements Serializable {
        public String key;
        public String value;
    }

    public static final String PHONE_ITEM = "PhoneItem";

    @InjectView(R.id.phone_pager)       private ViewPager viewPager;
    @InjectView(R.id.indicator)         private CirclePageIndicator mIndicator;
    @InjectView(R.id.sale_count)        private TextView saleCountText;
    @InjectView(R.id.price_top)         private TextView priceTop;
    @InjectView(R.id.price_bottom)      private TextView priceBottom;
    @InjectView(R.id.phone_name)        private TextView phoneName;
    @InjectView(R.id.phone_price)       private TextView phonePrice;
    @InjectView(R.id.contract_title)    private TextView contractTitle;
    @InjectView(R.id.contract)          private TextView contract;
    @InjectView(R.id.contract_web)      private WebView contractWebView;
    @InjectView(R.id.detail_container)  private LinearLayout detailContainer;
    @InjectView(R.id.redem_phone_btn)   private TextView redemPhoneBtn;

    @InjectExtra(value = PHONE_ITEM, optional = true)
    private PhoneItem phoneItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final float density = getResources().getDisplayMetrics().density;
        mIndicator.setBackgroundColor(0x00888888);
        mIndicator.setRadius(5 * density);
        mIndicator.setPageColor(0xffffffff);
        mIndicator.setFillColor(0xff17b8b0);
        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setTitleName(phoneItem.title);
        requestData(phoneItem.id);

        redemPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecycleTypeManager.getInstance().setRecycleType(RecycleTypeManager.TYPE_REDEMPTION);
                Tools.jumpActivity(mActivity, AssessmentBaseInfoActivity.class);
            }
        });
    }

    boolean isWeb = true;

    private void requestData(String phoneId) {
        showLoading();
        String url = UrlBuilder.getN3A5_GetPhoneRecomDetails(phoneId);
        GsonRequest request = new GsonRequest<N3A5_GetPhoneRecomDetails>(url, N3A5_GetPhoneRecomDetails.class,
                new Response.Listener<N3A5_GetPhoneRecomDetails>() {
                    @Override
                    public void onResponse(N3A5_GetPhoneRecomDetails response) {
                        XLog.w(TAG, "N3A5 success, response=%s", response);
                        if (response.status == 111) {
                            viewPager.setAdapter(new PhonePagerAdapter(response.data.img));
                            mIndicator.setViewPager(viewPager);
                            saleCountText.setText("累积销量：" + response.data.sale_count);
                            priceTop.setText(response.data.tprice);
                            priceBottom.setText(response.data.price);
                            phoneName.setText(response.data.title);
                            if (TextUtils.isEmpty(response.data.contract)) {
                                contractTitle.setVisibility(View.GONE);
                                contract.setVisibility(View.GONE);
                                contractWebView.setVisibility(View.GONE);
                            } else {
                                contractTitle.setVisibility(View.VISIBLE);
                                if (isWeb) {
                                    contractWebView.setVisibility(View.VISIBLE);
                                    contract.setVisibility(View.GONE);
                                    HtmlHelper.write(response.data.contract);
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    String url = "file://" + getCacheDir().toString() + "/html/tmp.html";
                                    contractWebView.loadUrl(url);
                                } else {
                                    contractWebView.setVisibility(View.GONE);
                                    contract.setVisibility(View.VISIBLE);
                                    contract.setText(Html.fromHtml(response.data.contract));
                                }
                            }
                            SpannableStringBuilder builder = new SpannableStringBuilder();
                            builder.append("会员价：");
                            builder.append(Tools.createColorString("￥" + response.data.price, Color.RED));
                            phonePrice.setText(builder);
                            for (N3A5_GetPhoneRecomDetails.Data.Model model : response.data.model) {
                                TextView tv = new TextView(mActivity);
                                tv.setTextSize(18);
                                tv.setTextColor(Color.BLACK);
                                tv.setText(model.key + "：" + model.value);
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                lp.topMargin = 5;
                                detailContainer.addView(tv, lp);
                            }
                        } else {
                            showToastHint(response.msg);
                        }
                        dismissLoading();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A5 error, " + error);
                        showToastHint("获取新机详情的数据异常，请重试");
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }


    private class PhonePagerAdapter extends PagerAdapter {

        List<ImageView> views = Lists.newArrayList();

        public PhonePagerAdapter(List<String> imgUrls) {
            for (String url : imgUrls) {
                ImageView image = new ImageView(mActivity);
//                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                showImageView(image, url);
                views.add(image);
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
