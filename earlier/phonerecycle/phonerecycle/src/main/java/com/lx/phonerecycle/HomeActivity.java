package com.lx.phonerecycle;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.common.collect.Lists;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lx.phonerecycle.fragment.HomeMenuFragment;
import com.lx.phonerecycle.fragment.LoadingDialogFragment;
import com.lx.phonerecycle.gsonbean.N2A3_UserLogin;
import com.lx.phonerecycle.gsonbean.N3A1_GetHomePageInfo;
import com.lx.phonerecycle.helper.ActivityHelper;
import com.lx.phonerecycle.helper.CustomToast;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.helper.RecycleTypeManager;
import com.lx.phonerecycle.request.MainInfo;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.request.test.TestRequest;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.widget.AlwaysMarqueeTextView;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-18 上午12:14 <br>
 * description:
 *
 * 首页
 */

public class HomeActivity extends SlidingFragmentActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private AlwaysMarqueeTextView topMarqueeTips;

    private ImageView menuBtn;
    private TextView orderManagementBtn;

    private ViewPager viewPager;
    private CirclePageIndicator mIndicator;
    private LinearLayout clinchParentView;

    private LinearLayout secondHandleRecycleBtn;
    private LinearLayout exchangeNewPoneBtn;
    private LinearLayout ActivityBtn;
    private Button faqBtn;

    protected LoadingDialogFragment loadingDialogFragment;

    protected RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        requestQueue = Volley.newRequestQueue(this);
        loadingDialogFragment = new LoadingDialogFragment();
        ActivityHelper.getInstance().addActivity(this);
        initSlidingMenu();
        initViews();
        requestHomePageInfo();
    }

    HomeMenuFragment homeMenuFragment;

    /**
     * 初始化滑动菜单
     */
    void initSlidingMenu() {
        homeMenuFragment = new HomeMenuFragment();
        // 设置滑动菜单的视图
        setBehindContentView(R.layout.menu_frame);
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame, homeMenuFragment).commit();

        // 实例化滑动菜单对象
        SlidingMenu sm = getSlidingMenu();
        // 设置滑动阴影的宽度
        sm.setShadowWidthRes(R.dimen.shadow_width);
        // 设置滑动阴影的图像资源
        sm.setShadowDrawable(R.drawable.shadow);
        // 设置滑动菜单视图的宽度
        int offset = getResources().getDisplayMetrics().widthPixels / 5 * 3;
        sm.setBehindOffset(offset);
        // 设置渐入渐出效果的值
        sm.setFadeDegree(1.0f);
        // 设置触摸屏幕的模式
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        sm.setTouchmodeMarginThreshold(getResources().getDisplayMetrics().widthPixels / 5);
    }

    private void initViews() {
        menuBtn = (ImageView) findViewById(R.id.menu_btn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
                TestRequest.getInstance(HomeActivity.this).test();
            }
        });
        faqBtn = (Button) findViewById(R.id.faq_btn);
        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jumpActivity(HomeActivity.this, FAQActivity.class);
            }
        });
        viewPager = (ViewPager) findViewById(R.id.home_pager);

        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        final float density = getResources().getDisplayMetrics().density;
        mIndicator.setBackgroundColor(0xFFe9e9e9);
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

        topMarqueeTips = (AlwaysMarqueeTextView) findViewById(R.id.top_marquee_tips);

        clinchParentView = (LinearLayout) findViewById(R.id.clinch_parent);

        secondHandleRecycleBtn = (LinearLayout) findViewById(R.id.home_second_handle_recycle_btn);
        secondHandleRecycleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginInfoHelper.read() != null) {
                    RecycleTypeManager.getInstance().setRecycleType(RecycleTypeManager.TYPE_RECYCLE);
                    Tools.jumpActivity(HomeActivity.this, AssessmentBaseInfoActivity.class);
                } else {
                    Tools.jumpActivity(HomeActivity.this, LoginActivity.class);
                }
            }
        });
        exchangeNewPoneBtn = (LinearLayout) findViewById(R.id.home_exchange_new_phone_btn);
        exchangeNewPoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jumpActivity(HomeActivity.this, PhoneRedemptionActivity.class);
            }
        });
        ActivityBtn = (LinearLayout) findViewById(R.id.home_activity_btn);
        ActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jumpActivity(HomeActivity.this, ActivityAndPhoneRecommendActivity.class,
                        Pair.create(ActivityAndPhoneRecommendActivity.SHOW_TYPE, ActivityAndPhoneRecommendActivity.TYPE_ACTIVITY_INTRODUCTION));
            }
        });
        orderManagementBtn = (TextView) findViewById(R.id.order_managerment_btn);
        orderManagementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginInfoHelper.read() == null) {
                    Tools.jumpActivity(HomeActivity.this, LoginActivity.class);
                } else {
                    Tools.jumpActivity(HomeActivity.this, OrdersManagementActivity.class);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshHomeStatus();
        homeMenuFragment.refresh();
    }

    private void refreshHomeStatus() {
        N2A3_UserLogin loginInfo = LoginInfoHelper.read();
        if (loginInfo == null) {
            orderManagementBtn.setText("登陆");
            exchangeNewPoneBtn.setVisibility(View.GONE);
        } else {
            if (loginInfo.power.equals("1")) {
                exchangeNewPoneBtn.setVisibility(View.GONE);
            } else {
                exchangeNewPoneBtn.setVisibility(View.VISIBLE);
            }
            orderManagementBtn.setText("订单管理");
        }
    }

    private void requestHomePageInfo() {
        String url = UrlBuilder.getN3A1_GetHomePageInfo(MainInfo.NNS_TYPE);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        showLoading();
        GsonRequest request = new GsonRequest<N3A1_GetHomePageInfo>(url, N3A1_GetHomePageInfo.class,
                new Response.Listener<N3A1_GetHomePageInfo>() {
                    @Override
                    public void onResponse(N3A1_GetHomePageInfo response) {
                        XLog.w(TAG, "N3A1 success, response=%s", response);
                        if (response.status == 111) {
                            setData(response);
                        } else {
                            CustomToast.show(HomeActivity.this, response.msg);
                        }
                        dismissLoading();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A1 error, " + error);
                        CustomToast.show(HomeActivity.this, "获取首页的数据异常，请重试");
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }

    private void setData(N3A1_GetHomePageInfo response) {
        topMarqueeTips.setText(response.top_ad);
        viewPager.setAdapter(new HomePagerAdapter(response.data));
        mIndicator.setViewPager(viewPager);
        clinchParentView.removeAllViews();
        for (int i = 0; i < TEXT_SIZE; i++) {
            final ViewGroup clinchItem = (ViewGroup) View.inflate(this, R.layout.home_clinch_item, null);
            final TextView clinchInfoText = (TextView) clinchItem.findViewById(R.id.clinch_info);
            clinchInfoText.setSingleLine(true);
            clinchInfoText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < clinchItem.getChildCount(); i++) {
                        ViewGroup parent = (ViewGroup) clinchParentView.getChildAt(i);
                        TextView text = (TextView) parent.findViewById(R.id.clinch_info);
                        text.setSingleLine(true);
                    }
                    clinchInfoText.setSingleLine(false);
                }
            });
            clinchTexts[i] = clinchInfoText;
            clinchParentView.addView(clinchItem);
        }
        for (N3A1_GetHomePageInfo.Clinch clinch : response.clinch) {
            String clinchStr = clinch.title + "，以" + clinch.price + "元成交";
            clinchInfos.add(clinchStr);
        }
        update();
    }

    private static int TEXT_SIZE = 2;

    private List<String> clinchInfos = Lists.newArrayList();
    private TextView[] clinchTexts = new TextView[TEXT_SIZE];
    private int clinchPageIndex = 0;

    private Handler clinchHandler = new Handler();

    private void update() {
        clinchHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLast = false;
                for (int i = 0; i < clinchTexts.length; i++) {
                    TextView clinchText = clinchTexts[i];
                    clinchText.setSingleLine(true);
                    int index = clinchPageIndex * TEXT_SIZE + i;
                    isLast = index == clinchInfos.size() - 1;
                    if (index < clinchInfos.size()) {
                        String info = clinchInfos.get(index);
                        clinchText.setText(info);
                    } else {
                        clinchText.setText("");
                    }
                }
                if (isLast) {
                    clinchPageIndex = 0;
                } else {
                    clinchPageIndex++;
                }
                clinchHandler.postDelayed(this, 3000);
            }
        }, 0);
    }

    public void showLoading() {
        if (!loadingDialogFragment.isVisible()) {
            loadingDialogFragment.show(getSupportFragmentManager(), "loading");
        }
    }

    public void dismissLoading() {
        if (loadingDialogFragment != null) {
            try {
                loadingDialogFragment.dismiss();
            } catch (Exception e) {
            }
        }
    }

    private class HomePagerAdapter extends PagerAdapter {

        List<View> views = Lists.newArrayList();

        public HomePagerAdapter(List<N3A1_GetHomePageInfo.Data> data) {
            for (final N3A1_GetHomePageInfo.Data d : data) {
                View view = View.inflate(HomeActivity.this, R.layout.home_view_pager_item, null);
                TextView title = (TextView) view.findViewById(R.id.pager_item_title);
                title.setText(d.title);
                for (int i = 0; i < d.quote.size(); i++) {
                    N3A1_GetHomePageInfo.Data.Quote quote = d.quote.get(i);
                    View itemParent = view.findViewById(R.id.price_item_1 + i);
                    TextView day = (TextView) itemParent.findViewById(R.id.day);
                    day.setText(quote.aliases);
                    TextView price = (TextView) itemParent.findViewById(R.id.price);
                    price.setText("￥" + quote.price);
                }
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (LoginInfoHelper.read() != null) {
                            RecycleTypeManager.getInstance().setRecycleType(RecycleTypeManager.TYPE_RECYCLE);
                            Tools.jumpActivity(HomeActivity.this, AssessmentBaseInfoActivity.class,
                                    Pair.create("brand", d.brand),
                                    Pair.create("model", d.model));
                        } else {
                            Tools.jumpActivity(HomeActivity.this, LoginActivity.class);
                        }
                    }
                });
                views.add(view);
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

    @Override
    protected void onPause() {
        super.onPause();
        showContent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityHelper.getInstance().removeActivity(this);
    }

}
