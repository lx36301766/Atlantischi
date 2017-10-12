package com.lx.phonerecycle;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.Lists;
import com.lx.phonerecycle.gsonbean.N3A3_GetActivityDetails;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.viewpagerindicator.CirclePageIndicator;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午1:49 <br>
 * description:
 *
 * 活动详情
 */

@ContentView(R.layout.activity_activity_details)
public class ActivityDetailsActivity extends BaseActivity {

    public static class ActivityItem implements Serializable {
        public String id;
        public String imgUrl;
        public String title;
        public String detail;
    }

    public static final String ACTIVITY_ITEM = "ActivityItem";

    @InjectView(R.id.activity_pager)    private ViewPager viewPager;
    @InjectView(R.id.indicator)         private CirclePageIndicator mIndicator;
    @InjectView(R.id.activity_content)  private TextView contentText;
    @InjectView(R.id.join_per_num)      private TextView joinPerNum;
    @InjectView(R.id.join_btn)          private TextView joinBtn;

    @InjectExtra(value = ACTIVITY_ITEM, optional = true)
    private ActivityItem activityItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final float density = getResources().getDisplayMetrics().density;
//        mIndicator.setBackgroundColor(0x8F888888);
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

        if (activityItem != null) {
            setTitleName(activityItem.title);
//            contentText.setText(activityItem.detail);
            requestData(activityItem.id);
        }
    }

    private void requestData(String activityId) {
        showLoading();
        String url = UrlBuilder.getN3A3_GetActivityDetails(activityId);
        GsonRequest request = new GsonRequest<N3A3_GetActivityDetails>(url, N3A3_GetActivityDetails.class,
                new Response.Listener<N3A3_GetActivityDetails>() {
                    @Override
                    public void onResponse(N3A3_GetActivityDetails response) {
                        XLog.w(TAG, "N3A3 success, response=%s", response);
                        if (response.status == 111) {
                            viewPager.setAdapter(new ActivityPagerAdapter(response.data.img));
                            mIndicator.setViewPager(viewPager);
                            contentText.setText(response.data.content);
                            joinPerNum.setText(response.data.join_num + "人已经参加活动");
                        } else {
                            showToastHint(response.msg);
                        }
                        dismissLoading();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A3 error, " + error);
                        showToastHint("获取活动详情的数据异常，请重试");
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }


    private class ActivityPagerAdapter extends PagerAdapter {

        List<ImageView> views = Lists.newArrayList();

        public ActivityPagerAdapter(List<String> imgUrls) {
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
