package com.lx.phonerecycle;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.Lists;
import com.lx.phonerecycle.gsonbean.N2A3_UserLogin;
import com.lx.phonerecycle.gsonbean.N3A2_GetActivityPromotions;
import com.lx.phonerecycle.gsonbean.N3A4_GetPhoneRecomInfo;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.request.MainInfo;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.widget.EllipsizingTextView;
import com.lx.phonerecycle.PhoneDetailsActivity.PhoneItem;
import com.lx.phonerecycle.ActivityDetailsActivity.ActivityItem;
import com.lx.phonerecycle.widget.pulltorefresh.PullToRefreshBase;
import com.lx.phonerecycle.widget.pulltorefresh.PullToRefreshListView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午1:44 <br>
 * description:
 *
 * 新机推荐，活动促销
 */

@ContentView(R.layout.activity_activity_and_phone_recommend)
public class ActivityAndPhoneRecommendActivity extends BaseActivity {

    public static final String SHOW_TYPE = "show_type";
    public static final String TYPE_PHONE_RECOM = "phone_recom";
    public static final String TYPE_ACTIVITY_INTRODUCTION = "activity_introduction";

    @InjectView(R.id.switch_btn_layout)     private View switchBtnLayout;

    @InjectView(R.id.activity_introduction) private Button activityIntroductionBtn;
    @InjectView(R.id.new_phone_recom)       private Button newPhoneRecomBtn;

    private ListView activityList;
    private ActivityListAdapter activityListAdapter;
    @InjectView(R.id.activity_list_refresh_view) private PullToRefreshListView activityPullToRefreshListView;

    private ListView phoneList;
    private PhoneRecomListAdapter phoneRecomListAdapter;
    @InjectView(R.id.phone_list_refresh_view) private PullToRefreshListView phonePullToRefreshListView;

    @InjectExtra(value = SHOW_TYPE, optional = true)
    private String showType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityIntroductionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityIntroductionBtn.setSelected(true);
                newPhoneRecomBtn.setSelected(false);
                phonePullToRefreshListView.setVisibility(View.GONE);
                activityPullToRefreshListView.setVisibility(View.VISIBLE);
            }
        });
        newPhoneRecomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityIntroductionBtn.setSelected(false);
                newPhoneRecomBtn.setSelected(true);
                phonePullToRefreshListView.setVisibility(View.VISIBLE);
                activityPullToRefreshListView.setVisibility(View.GONE);
            }
        });

        initActivityList();
        initPhoneRecomList();

        if (TYPE_ACTIVITY_INTRODUCTION.equals(showType)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    activityIntroductionBtn.performClick();
                }
            }, 500);
            setTitleName("活动促销");
        } else if (TYPE_PHONE_RECOM.equals(showType)) {
            newPhoneRecomBtn.performClick();
            setTitleName("新机推荐");
        }

        if (LoginInfoHelper.isRedemptionMode()) {
            switchBtnLayout.setVisibility(View.VISIBLE);
        } else {
            switchBtnLayout.setVisibility(View.GONE);
        }

    }


    /********************************************************************************************************/


    private void initActivityList() {
        activityPullToRefreshListView.setPullLoadEnabled(false);
        activityPullToRefreshListView.setScrollLoadEnabled(true);
        activityPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                requestActivityData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                requestActivityData();
            }
        });
        activityList = activityPullToRefreshListView.getRefreshableView();
        activityList.setDivider(new ColorDrawable(Color.TRANSPARENT));
        activityList.setDividerHeight(Tools.px2Dp(this, 5));
        activityList.setCacheColorHint(Color.TRANSPARENT);
        activityList.setSelector(new ColorDrawable(Color.TRANSPARENT));
        activityListAdapter = new ActivityListAdapter();
        activityList.setAdapter(activityListAdapter);
        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ActivityItem item = (ActivityItem) parent.getItemAtPosition(position);
                Tools.jumpActivity(mActivity, ActivityDetailsActivity.class,
                        Pair.create(ActivityDetailsActivity.ACTIVITY_ITEM, item));
            }
        });
        requestActivityData();
    }

    int activityPageIndex = 1;

    private void requestActivityData() {
        showLoading();
        String url = UrlBuilder.getN3A2_GetActivityPromotions(MainInfo.NNS_TYPE, 20, activityPageIndex);
        activityPageIndex++;
        GsonRequest request = new GsonRequest<N3A2_GetActivityPromotions>(url, N3A2_GetActivityPromotions.class,
                new Response.Listener<N3A2_GetActivityPromotions>() {
                    @Override
                    public void onResponse(N3A2_GetActivityPromotions response) {
                        XLog.w(TAG, "N3A2 success, response=%s", response);
                        if (response.status == 111) {
                            List<ActivityItem> activityItems = Lists.newArrayList();
                            for (N3A2_GetActivityPromotions.Data data : response.data) {
                                ActivityItem item = new ActivityItem();
                                item.id = data.id;
                                item.title = data.title;
                                item.imgUrl = data.img;
                                item.detail = data.summary;
                                activityItems.add(item);
                            }
                            activityListAdapter.addData(activityItems);
                            if (response.data.size() == 0) {
                                activityPageIndex--;
                                activityPullToRefreshListView.setHasMoreData(false);
                            } else {
                                activityPullToRefreshListView.setHasMoreData(true);
                            }
                        } else {
                            showToastHint(response.msg);
                        }
                        dismissLoading();
                        activityRefreshComplete();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A2 error, " + error);
                        showToastHint("获取活动数据异常，请重试");
                        dismissLoading();
                        activityRefreshComplete();
                    }
                }
        );
        requestQueue.add(request);
    }

    private void activityRefreshComplete() {
        activityPullToRefreshListView.onPullDownRefreshComplete();
        activityPullToRefreshListView.onPullUpRefreshComplete();
        long time = System.currentTimeMillis();
        String date =  new SimpleDateFormat("MM-dd HH:mm").format(new Date(time));
        activityPullToRefreshListView.setLastUpdatedLabel(date);
    }

    private class ActivityListAdapter extends BaseAdapter {

        private List<ActivityItem> activityItems = Lists.newArrayList();

        public void addData(List<ActivityItem> items) {
            activityItems.addAll(items);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return activityItems.size();
        }

        @Override
        public Object getItem(int position) {
            return activityItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.activity_list_item, null);
            }
            ImageView activityImg = Tools.getViewByHolder(convertView, R.id.activity_image);
            TextView activityName = Tools.getViewByHolder(convertView, R.id.activity_name);
            EllipsizingTextView activityDetails = Tools.getViewByHolder(convertView, R.id.activity_details);
            activityDetails.setMaxLines(3);

            ActivityItem item = (ActivityItem) getItem(position);
            showImageView(activityImg, item.imgUrl, R.drawable.test_phone);
            activityName.setText(item.title);
            activityDetails.setText(item.detail);
            return convertView;
        }
    }


    /********************************************************************************************************/


    private void initPhoneRecomList() {
        phonePullToRefreshListView.setPullLoadEnabled(false);
        phonePullToRefreshListView.setScrollLoadEnabled(true);
        phonePullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                requestPhoneRecomData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                requestPhoneRecomData();
            }
        });
        phoneList = phonePullToRefreshListView.getRefreshableView();
        phoneList.setDivider(new ColorDrawable(Color.TRANSPARENT));
        phoneList.setDividerHeight(Tools.px2Dp(this, 5));
        phoneList.setCacheColorHint(Color.TRANSPARENT);
        phoneList.setSelector(new ColorDrawable(Color.TRANSPARENT));
        phoneRecomListAdapter = new PhoneRecomListAdapter();
        phoneList.setAdapter(phoneRecomListAdapter);
        phoneList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PhoneItem item = (PhoneItem) parent.getItemAtPosition(position);
                Tools.jumpActivity(mActivity, PhoneDetailsActivity.class,
                        Pair.create(PhoneDetailsActivity.PHONE_ITEM, item));
            }
        });
        requestPhoneRecomData();
    }

    int phonePageIndex = 1;

    private void requestPhoneRecomData() {
        showLoading();
        String url = UrlBuilder.getN3A4_GetPhoneRecomInfo(1, 20, phonePageIndex);
        phonePageIndex++;
        GsonRequest request = new GsonRequest<N3A4_GetPhoneRecomInfo>(url, N3A4_GetPhoneRecomInfo.class,
                new Response.Listener<N3A4_GetPhoneRecomInfo>() {
                    @Override
                    public void onResponse(N3A4_GetPhoneRecomInfo response) {
                        XLog.w(TAG, "N3A4 success, response=%s", response);
                        if (response.status == 111) {
                            List<PhoneItem> phoneItems = Lists.newArrayList();
                            for (N3A4_GetPhoneRecomInfo.Data data : response.data) {
                                PhoneItem item = new PhoneItem();
                                item.id = data.id;
                                item.title = data.title;
                                item.imgUrl = data.img;
                                item.price = data.price;
                                item.is_contract = data.is_contract;
                                for (N3A4_GetPhoneRecomInfo.Data.Role role : data.role) {
                                    PhoneDetailsActivity.Params params = new PhoneDetailsActivity.Params();
                                    params.key = role.key;
                                    params.value = role.value;
                                    item.params.add(params);
                                }
                                phoneItems.add(item);
                            }
                            phoneRecomListAdapter.addData(phoneItems);
                            if (response.data.size() == 0) {
                                phonePageIndex--;
                                phonePullToRefreshListView.setHasMoreData(false);
                            } else {
                                phonePullToRefreshListView.setHasMoreData(true);
                            }
                        } else {
                            showToastHint(response.msg);
                        }
                        dismissLoading();
                        phoneRefreshComplete();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A4 error, " + error);
                        showToastHint("获取新机数据异常，请重试");
                        dismissLoading();
                        phoneRefreshComplete();
                    }
                }
        );
        requestQueue.add(request);
    }

    private void phoneRefreshComplete() {
        phonePullToRefreshListView.onPullDownRefreshComplete();
        phonePullToRefreshListView.onPullUpRefreshComplete();
        long time = System.currentTimeMillis();
        String date =  new SimpleDateFormat("MM-dd HH:mm").format(new Date(time));
        phonePullToRefreshListView.setLastUpdatedLabel(date);
    }

    private class PhoneRecomListAdapter extends BaseAdapter {

        private List<PhoneItem> phoneRecomItems = Lists.newArrayList();

        public void addData(List<PhoneItem> phoneItems) {
            phoneRecomItems.addAll(phoneItems);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return phoneRecomItems.size();
        }

        @Override
        public Object getItem(int position) {
            return phoneRecomItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.phone_recom_list_item, null);
            }
            ImageView phoneImg = Tools.getViewByHolder(convertView, R.id.phone_img);
            TextView contracts = Tools.getViewByHolder(convertView, R.id.contracts);
            TextView phoneName = Tools.getViewByHolder(convertView, R.id.phone_recom_item_name);
            TextView phoneParam1 = Tools.getViewByHolder(convertView, R.id.phone_recom_param1);
            TextView phoneParam2 = Tools.getViewByHolder(convertView, R.id.phone_recom_param2);
            TextView phoneParam3 = Tools.getViewByHolder(convertView, R.id.phone_recom_param3);
            TextView phoneParam4 = Tools.getViewByHolder(convertView, R.id.phone_recom_param4);
            TextView phonePrice = Tools.getViewByHolder(convertView, R.id.price);

            PhoneItem item = (PhoneItem) getItem(position);
            boolean isContracts;
            try {
                isContracts = Integer.parseInt(item.is_contract) == 1;
            } catch (Exception e) {
                isContracts = false;
            }
            contracts.setVisibility(isContracts ? View.VISIBLE : View.INVISIBLE);
            showImageView(phoneImg, item.imgUrl, R.drawable.test_phone);
            phoneName.setText(item.title);
            phonePrice.setText(item.price + "元");
            try {
                phoneParam1.setText(item.params.get(0).key + " : " + item.params.get(0).value);
                phoneParam2.setText(item.params.get(1).key + " : " + item.params.get(1).value);
                phoneParam3.setText(item.params.get(2).key + " : " + item.params.get(2).value);
                phoneParam4.setText(item.params.get(3).key + " : " + item.params.get(3).value);
            } catch (Exception e) {
                phoneParam1.setText("");
                phoneParam2.setText("");
                phoneParam3.setText("");
                phoneParam4.setText("");
            }
            return convertView;
        }
    }

}
