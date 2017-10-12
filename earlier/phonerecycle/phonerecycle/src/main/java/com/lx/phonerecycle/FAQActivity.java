package com.lx.phonerecycle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lx.phonerecycle.gsonbean.N2A11_FAQ;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2014 hx <br>
 * All rights reserved. <br>
 * author:  xiang.huang <br>
 * date:  2014/7/20 15:25 <br>
 * description:常见问题
 */

@ContentView(R.layout.activity_faq)
public class FAQActivity extends BaseActivity {

    @InjectView(R.id.faq_list_view) private ExpandableListView listView;
    //记录上次展开的父组件下标，方便关闭
    private int lastPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("常见问题");

        //监听父列表的弹出事件
        listView.setOnGroupExpandListener(new ExpandableListViewListenerC());
        //监听父列表的关闭事件
        listView.setOnGroupCollapseListener(new ExpandableListViewListenerB());
        //监听子列表
        listView.setOnChildClickListener(new ExpandableListViewListenerA());

        showLoading();
        String n2a11url = UrlBuilder.getN2A11_FAQ(20, 1);
        GsonRequest request = new GsonRequest<N2A11_FAQ>(n2a11url, N2A11_FAQ.class, new Response.Listener<N2A11_FAQ>() {
            @Override
            public void onResponse(N2A11_FAQ response) {
                dismissLoading();
                XLog.i(TAG, "N2A11 success, response=%s", response);
                ExpandInfoAdapter adapter = new ExpandInfoAdapter(FAQActivity.this, response.data);
                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dismissLoading();
            }
        }
        );
        requestQueue.add(request);
    }

    /**
     * 监听子级列表的点击事件
     *
     * @author Administrator
     */
    public class ExpandableListViewListenerA implements ExpandableListView.OnChildClickListener {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            // TODO Auto-generated method stub
            return false;
        }
    }

    /**
     * 监听父级列表的关闭事件事件
     *
     * @author Administrator
     */
    public class ExpandableListViewListenerB implements ExpandableListView.OnGroupCollapseListener {

        @Override
        public void onGroupCollapse(int groupPosition) {
            // TODO Auto-generated method stub

        }
    }

    /**
     * 监听父级列表的弹出事件
     *
     * @author Administrator
     */
    public class ExpandableListViewListenerC implements ExpandableListView.OnGroupExpandListener {
        @Override
        public void onGroupExpand(int groupPosition) {
            // TODO Auto-generated method stub
            if (lastPoint != groupPosition) {
                listView.collapseGroup(lastPoint);
                lastPoint = groupPosition;
            }
        }
    }

    class ExpandInfoAdapter extends BaseExpandableListAdapter {
        LayoutInflater mInflater;
        Bitmap mIcon1;
        Context context;
        public List<String> group;
        public List<String> child;

        public ExpandInfoAdapter(Activity a, List<N2A11_FAQ.Item> data) {
            context = a;
            mInflater = LayoutInflater.from(context);
            mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            initialOther();
            for (int i = 0; i < data.size(); i++) {
                N2A11_FAQ.Item item = data.get(i);
                addItemByValue((i + 1) + "." + item.title, item.content);
            }
        }

        // child's stub
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return child.get(groupPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            return getView(groupPosition, childPosition, convertView, parent);
        }


        public View getView(int groupPosition, int childPosition, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.faq_list_child_item, parent, false);
            }
            String name = child.get(groupPosition);
            TextView text = Tools.getViewByHolder(convertView, R.id.faq_list_child_item);
            text.setText(name);
            return convertView;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return group.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return group.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            RelativeLayout parentLayout = (RelativeLayout) View.inflate(context, R.layout.faq_list_item, null);
            TextView parentTextView = (TextView) parentLayout.findViewById(R.id.faq_parent_item);
            parentTextView.setText(getGroup(groupPosition).toString());
            ImageView parentImageViw = (ImageView) parentLayout.findViewById(R.id.faq_arrow);
            //判断isExpanded就可以控制是按下还是关闭，同时更换图片
            if (isExpanded) {
                parentImageViw.setBackgroundResource(R.drawable.bottom_arrow);
            } else {
                parentImageViw.setBackgroundResource(R.drawable.right_arrow);
            }
            return parentLayout;

        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        public void initialOther() {
            group = new ArrayList<String>();
            child = new ArrayList<String>();
        }

        public void addItemByValue(String title, String content) {
            group.add(title);
            child.add(content);
        }

    }
}
