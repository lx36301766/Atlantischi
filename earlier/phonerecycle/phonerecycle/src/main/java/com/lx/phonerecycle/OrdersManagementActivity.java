package com.lx.phonerecycle;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.Lists;
import com.lx.phonerecycle.gsonbean.N4A1_GetOrderType;
import com.lx.phonerecycle.gsonbean.N4A2_GetUserOrder;
import com.lx.phonerecycle.helper.CustomToast;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.widget.CommonRadioView;
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
 * date:  14-7-20 下午1:52 <br>
 * description:订单管理
 *
 *
 */

@ContentView(R.layout.activity_orders_management)
public class OrdersManagementActivity extends BaseActivity{
    @InjectView(R.id.order_radio) private CommonRadioView orderRadio;
//    @InjectView(R.id.order_list)
    private ListView orderList;
    @InjectView(R.id.order_list_refresh_view) private PullToRefreshListView pullToRefreshListView;
    private OrderAdapter orderAdapter;
    private int currentType;
    @InjectView(R.id.order_managerment_recovery) private Button recovery;
    @InjectView(R.id.order_managerment_change) private Button change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("订单管理");
        if (LoginInfoHelper.isRedemptionMode()) {
            recovery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCheckedOrderType(v);
                }
            });

            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCheckedOrderType(v);
                }
            });
        } else {
            recovery.setVisibility(View.GONE);
            change.setVisibility(View.GONE);
        }
        setCheckedOrderType(recovery);
        orderRadio.setOnItemSelectedListener(new CommonRadioView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(String status) {
                OrdersManagementActivity.this.status = status;
                requestUserOrder(status, 1, true);
            }
        });

        pullToRefreshListView.setPullLoadEnabled(false);
        pullToRefreshListView.setScrollLoadEnabled(true);

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex++;
                requestUserOrder(status, pageIndex, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pageIndex++;
                requestUserOrder(status, pageIndex, false);
            }
        });
        orderList = pullToRefreshListView.getRefreshableView();
        int dp_5 = Tools.px2Dp(this, 5);
        orderList.setPadding(dp_5, dp_5, dp_5, dp_5);
        orderList.setDivider(new ColorDrawable(Color.TRANSPARENT));
        orderList.setDividerHeight(dp_5);
        orderList.setCacheColorHint(Color.TRANSPARENT);
        orderList.setSelector(new ColorDrawable(Color.TRANSPARENT));
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                N4A2_GetUserOrder.Data data = orderAdapter.getItem(position);
                if (data != null && !TextUtils.isEmpty(data.order_id)) {
//                    Tools.jumpActivity(mActivity, OrdersDetailsActivity.class,
//                            Pair.create(OrdersDetailsActivity.TYPE, currentType),
//                            Pair.create(OrdersDetailsActivity.ORDER_ID, data.order_id));
                    Intent intent = new Intent();
                    intent.setClass(mActivity, OrdersDetailsActivity.class);
                    intent.putExtra(OrdersDetailsActivity.TYPE, currentType);
                    intent.putExtra(OrdersDetailsActivity.ORDER_ID, data.order_id);
                    startActivityForResult(intent, 1);
                } else {
                    showToastHint("订单信息错误");
                }
            }
        });
        orderAdapter = new OrderAdapter();
        orderList.setAdapter(orderAdapter);

    }

    private String status;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5) {
            requestUserOrder(status, 1, true);
        }
    }

    private void setCheckedOrderType(View view) {
        recovery.setSelected(view == recovery);
        change.setSelected(view == change);
        currentType = view == recovery ? 0 : 1;
        requestOrderType();
    }

    private void requestOrderType() {
        showLoading();
        String url = UrlBuilder.getN4A1_GetOrderType(currentType);
        GsonRequest request = new GsonRequest<N4A1_GetOrderType>(url, N4A1_GetOrderType.class,
                new Response.Listener<N4A1_GetOrderType>() {
                    @Override
                    public void onResponse(N4A1_GetOrderType response) {
                        XLog.w(TAG, "N4A1 success, response=%s", response);
                        if (response.status == RESPONSE_OK) {
                            orderRadio.upDataUI(response.data);
                        } else {
                            CustomToast.show(mActivity, response.msg);
                        }
                        dismissLoading();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N4A1 error, " + error);
                        showToastHint("获取订单类型异常，请稍候再试");
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }

    int pageIndex = 1;

    private void requestUserOrder(String status, int index, boolean clear) {
        if (clear) {
            orderAdapter.clearData();
            pageIndex = 1;
        }
        showLoading();
        int pageSize = 20;
        String url = UrlBuilder.getN4A2_GetUserOrder(currentType, status, pageSize, index);
        GsonRequest request = new GsonRequest<N4A2_GetUserOrder>(url, N4A2_GetUserOrder.class,
                new Response.Listener<N4A2_GetUserOrder>() {
                    @Override
                    public void onResponse(N4A2_GetUserOrder response) {
                        XLog.w(TAG, "N4A2 success, response=%s", response);
                        if (response.status == RESPONSE_OK) {
//                            orderDatas.addAll(response.data);
//                            orderAdapter.notifyDataSetChanged();
                            orderAdapter.addData(response.data);
                            if (response.data.size() == 0) {
                                pageIndex--;
                                pullToRefreshListView.setHasMoreData(false);
                            } else {
                                pullToRefreshListView.setHasMoreData(true);
                            }
                        } else {
                            CustomToast.show(mActivity, response.msg);
                        }
                        refreshComplete();
                        dismissLoading();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N4A2 error, " + error);
                        showToastHint("获取订单列表异常，请稍候再试");
                        refreshComplete();
                        pullToRefreshListView.setHasMoreData(false);
                        dismissLoading();
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

    @Override
    public void onBackPressed() {
        Tools.jumpActivity(mActivity, HomeActivity.class);
        finish();
    }

    private class OrderAdapter extends BaseAdapter{

        private List<N4A2_GetUserOrder.Data> orderDatas = Lists.newArrayList();

        public void addData(List<N4A2_GetUserOrder.Data> datas) {
            orderDatas.addAll(datas);
            notifyDataSetChanged();
        }

        public void clearData() {
            orderDatas.clear();
            notifyDataSetInvalidated();
        }

        @Override
        public int getCount() {
            return orderDatas.size();
        }

        @Override
        public N4A2_GetUserOrder.Data getItem(int i) {
            return orderDatas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view==null){
                view = LayoutInflater.from(mActivity).inflate(R.layout.view_order_item,viewGroup,false);
            }
            ImageView imageView = Tools.getViewByHolder(view, R.id.order_image);
            TextView title = Tools.getViewByHolder(view, R.id.order_title);
            TextView user = Tools.getViewByHolder(view, R.id.order_user);
            TextView phone = Tools.getViewByHolder(view, R.id.order_phoneNumber);
            TextView cost = Tools.getViewByHolder(view, R.id.order_cost);
            ImageView errorLine = Tools.getViewByHolder(view, R.id.order_error_line);
            TextView orderErrorTips = Tools.getViewByHolder(view,R.id.order_error_tips);
            N4A2_GetUserOrder.Data info = orderDatas.get(i);
            if (info != null) {
                showImageView(imageView, info.image, R.drawable.ord_phone_icon);
                title.setText(info.brand);
                user.setText(info.uname);
                phone.setText(info.phone_no);
                cost.setText("¥ "+info.price);
                if (info.status_info.contains("异常")) {
                    errorLine.setVisibility(View.VISIBLE);
                    orderErrorTips.setVisibility(View.VISIBLE);
                } else {
                    errorLine.setVisibility(View.GONE);
                    orderErrorTips.setVisibility(View.GONE);
                }
            }
            return view;
        }
    }

}
