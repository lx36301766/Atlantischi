package com.lx.phonerecycle.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.lx.phonerecycle.*;
import com.lx.phonerecycle.gsonbean.N2A3_UserLogin;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.helper.RecycleTypeManager;
import com.lx.phonerecycle.tools.Tools;

/**
 * Created with IntelliJ IDEA.<br>
 * User: luo.xuan<br>
 * Date: 14-7-15<br>
 * Time: 下午11:27<br>
 */

public class HomeMenuFragment extends ListFragment {

    protected HomeActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (HomeActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_menu, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView homeText = (TextView) view.findViewById(R.id.home);
        homeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.showContent();
            }
        });
    }

    public void refresh() {
        String[] names;
        TypedArray icons;
        if (LoginInfoHelper.isRedemptionMode()) {
            names = getResources().getStringArray(R.array.home_item_names_2);
            icons = getResources().obtainTypedArray(R.array.home_item_icons_2);
        } else {
            names = getResources().getStringArray(R.array.home_item_names_1);
            icons = getResources().obtainTypedArray(R.array.home_item_icons_1);
        }
        HomeListAdapter adapter = new HomeListAdapter(getActivity());
        for (int i = 0; i < names.length; i++) {
            try {
                adapter.add(new HomeItem(names[i], icons.getDrawable(i)));
            } catch (Exception e) {
            }
        }
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        HomeItem homeItem = (HomeItem) l.getItemAtPosition(position);
        if (getString(R.string.home_second_hand_recycle).equals(homeItem.name)) {
            if (LoginInfoHelper.read() == null) {
                Tools.jumpActivity(mActivity, LoginActivity.class);
            } else {
                RecycleTypeManager.getInstance().setRecycleType(RecycleTypeManager.TYPE_RECYCLE);
                Tools.jumpActivity(mActivity, AssessmentBaseInfoActivity.class);
            }
        } else if (getString(R.string.home_new_phone_redemption).equals(homeItem.name)) {
            Tools.jumpActivity(mActivity, PhoneRedemptionActivity.class);
        } else if (getString(R.string.home_activity_promotions).equals(homeItem.name)) {
            Tools.jumpActivity(mActivity, ActivityAndPhoneRecommendActivity.class,
                    Pair.create(ActivityAndPhoneRecommendActivity.SHOW_TYPE, ActivityAndPhoneRecommendActivity.TYPE_ACTIVITY_INTRODUCTION));
        } else if (getString(R.string.home_marketing_support).equals(homeItem.name)) {
            //暂不做
        } else if (getString(R.string.home_personal_center).equals(homeItem.name)) {
            if (LoginInfoHelper.read() == null) {
                Tools.jumpActivity(mActivity, LoginActivity.class);
            } else {
                Tools.jumpActivity(mActivity, UserCenterActivity.class);
            }
        } else if (getString(R.string.home_order_management).equals(homeItem.name)) {
            if (LoginInfoHelper.read() == null) {
                Tools.jumpActivity(mActivity, LoginActivity.class);
            } else {
                Tools.jumpActivity(mActivity, OrdersManagementActivity.class);
            }
        } else if (getString(R.string.about_me).equals(homeItem.name)) {
            Tools.jumpActivity(mActivity, ServiceAgreementActivity.class,
                    Pair.create(ServiceAgreementActivity.TYPE, ServiceAgreementActivity.TYPE_ABOUT));
        } else {
            Tools.jumpActivity(getActivity(), homeItem.name + "Activity");
        }
    }

    private class HomeItem {

        String name;
        Drawable icon;

        HomeItem(String name, Drawable icon) {
            this.name = name;
            this.icon = icon;
        }
    }

    private static class HomeItemViewHolder {
        ImageView iconImage;
        TextView nameText;
    }

    public class HomeListAdapter extends ArrayAdapter<HomeItem> {

        public HomeListAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HomeItemViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.home_menu_item, null);
                holder = new HomeItemViewHolder();
                holder.iconImage = (ImageView) convertView.findViewById(R.id.icon);
                holder.nameText = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (HomeItemViewHolder) convertView.getTag();
            }
            HomeItem homeItem = getItem(position);
            holder.nameText.setText(homeItem.name);
            holder.iconImage.setImageDrawable(homeItem.icon);
            return convertView;
        }

    }

}
