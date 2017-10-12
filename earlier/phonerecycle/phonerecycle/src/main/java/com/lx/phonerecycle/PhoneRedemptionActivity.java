package com.lx.phonerecycle;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.Lists;
import com.lx.phonerecycle.gsonbean.N3A4_GetPhoneRecomInfo;
import com.lx.phonerecycle.helper.RecycleTypeManager;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.PhoneDetailsActivity.PhoneItem;
import com.lx.phonerecycle.widget.pulltorefresh.PullToRefreshBase;
import com.lx.phonerecycle.widget.pulltorefresh.PullToRefreshListView;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-8-3 下午6:33 <br>
 * description:
 *
 * 新机换购
 */

@ContentView(R.layout.activity_new_phone_redemption)
public class PhoneRedemptionActivity extends BaseActivity {

//    @InjectView(R.id.phone_list)
    private ListView phoneList;
    private PhoneRecomListAdapter phoneRecomListAdapter;
    @InjectView(R.id.phone_list_refresh_view) private PullToRefreshListView pullToRefreshListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("新机换购");
        initNewPhoneList();
    }

    private void initNewPhoneList() {
        pullToRefreshListView.setPullLoadEnabled(false);
        pullToRefreshListView.setScrollLoadEnabled(true);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                requestNewPhoneData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                requestNewPhoneData();
            }
        });
        phoneList = pullToRefreshListView.getRefreshableView();
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
        requestNewPhoneData();

    }

    int pageIndex = 1;

    private void requestNewPhoneData() {
        showLoading();
        String url = UrlBuilder.getN3A4_GetPhoneRecomInfo(0, 20, pageIndex);
        pageIndex++;
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
                                pageIndex--;
                                pullToRefreshListView.setHasMoreData(false);
                            } else {
                                pullToRefreshListView.setHasMoreData(true);
                            }
                        } else {
                            showToastHint(response.msg);
                        }
                        refreshComplete();
                        dismissLoading();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A4 error, " + error);
                        showToastHint("获取新机数据异常，请重试");
                        dismissLoading();
                        refreshComplete();
                    }
                }
        );
        requestQueue.add(request);
    }

    private void refreshComplete() {
        pullToRefreshListView.onPullDownRefreshComplete();
        pullToRefreshListView.onPullUpRefreshComplete();
        long time = System.currentTimeMillis();
        String date =  new SimpleDateFormat("MM-dd HH:mm").format(new Date(time));
        pullToRefreshListView.setLastUpdatedLabel(date);
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
                convertView = View.inflate(mActivity, R.layout.phone_redemption_list_item, null);
            }
            ImageView phoneImg = Tools.getViewByHolder(convertView, R.id.phone_img);
            TextView contracts = Tools.getViewByHolder(convertView, R.id.contracts);
            TextView phoneName = Tools.getViewByHolder(convertView, R.id.phone_recom_item_name);
            TextView phoneParam1 = Tools.getViewByHolder(convertView, R.id.phone_recom_param1);
            TextView phoneParam2 = Tools.getViewByHolder(convertView, R.id.phone_recom_param2);
            TextView phoneParam3 = Tools.getViewByHolder(convertView, R.id.phone_recom_param3);
            TextView phoneParam4 = Tools.getViewByHolder(convertView, R.id.phone_recom_param4);

            TextView details = Tools.getViewByHolder(convertView, R.id.details);
            TextView redemption = Tools.getViewByHolder(convertView, R.id.redemption);

            final PhoneItem item = (PhoneItem) getItem(position);
            boolean isContracts;
            try {
                isContracts = Integer.parseInt(item.is_contract) == 1;
            } catch (Exception e) {
                isContracts = false;
            }
            contracts.setVisibility(isContracts ? View.VISIBLE : View.INVISIBLE);
            redemption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecycleTypeManager.getInstance().setRecycleType(RecycleTypeManager.TYPE_REDEMPTION);
                    RecycleTypeManager.RedemptionPhoneInfo redemptionPhoneInfo = new RecycleTypeManager.RedemptionPhoneInfo();
                    redemptionPhoneInfo.id = item.id;
                    redemptionPhoneInfo.name = item.title;
                    redemptionPhoneInfo.price = item.price;
                    redemptionPhoneInfo.redemptionPhoneArgs.addAll(item.params);
                    RecycleTypeManager.getInstance().setRedemptionPhoneInfo(redemptionPhoneInfo);
                    Tools.jumpActivity(mActivity, AssessmentBaseInfoActivity.class);
                }
            });

            showImageView(phoneImg, item.imgUrl, R.drawable.test_phone);
            phoneName.setText(item.title);
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
