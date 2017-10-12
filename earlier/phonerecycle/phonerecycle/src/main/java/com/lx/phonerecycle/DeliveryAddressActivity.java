package com.lx.phonerecycle;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.Lists;
import com.lx.phonerecycle.gsonbean.AddressData;
import com.lx.phonerecycle.gsonbean.N2A3_UserLogin;
import com.lx.phonerecycle.gsonbean.N2A4_ModifyUserInfo;
import com.lx.phonerecycle.helper.CustomToast;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.location.LocationInfoHelper;
import com.lx.phonerecycle.location.LocationInfoProvider;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.widget.wheel.OnWheelChangedListener;
import com.lx.phonerecycle.widget.wheel.OnWheelScrollListener;
import com.lx.phonerecycle.widget.wheel.WheelView;
import com.lx.phonerecycle.widget.wheel.adapters.AbstractWheelTextAdapter;
import com.lx.phonerecycle.widget.wheel.adapters.ArrayWheelAdapter;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.util.ArrayList;

/**
 * Copyright(C) 2014, lx <br>
 * All rights reserved. <br>
 * author:  xiang.huang <br>
 * date:  2014/7/26 14:30 <br>
 * description:
 */

@ContentView(R.layout.activity_deliver_address)
public class DeliveryAddressActivity extends BaseActivity{
    private int width;
    private int height;
    @InjectView(R.id.deliver_address_text) private TextView addressText;
    @InjectView(R.id.detail_address) private EditText detailAddress;
    @InjectView(R.id.deliver_address_layout) private LinearLayout addressLayout;
    @InjectView(R.id.deliver_address_commit_btn) private Button commit;
    private Context context;
    private WheelView province;
    private WheelView area;
    private WheelView city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleName("收货地址");
        context = this;
        Display display = this.getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        addressText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupWindow popupWindow = makePopupWindow(context);
                int[] xy = new int[2];
                addressLayout.getLocationOnScreen(xy);
                popupWindow.showAtLocation(addressLayout, Gravity.CENTER | Gravity.BOTTOM, 0,
                        -height);
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(addressText.getText()) || "所在地区".equals(addressText.getText().toString().trim())) {
                    CustomToast.show(DeliveryAddressActivity.this, "请选择地区");
                    return;
                }
                if (TextUtils.isEmpty(detailAddress.getText())) {
                    CustomToast.show(DeliveryAddressActivity.this, "详细地址不能为空");
                    return;
                }
                String address = addressText.getText().toString().trim() + detailAddress.getText().toString().trim();
                modifyAddr(address);
            }
        });
    }

    private class City{
        private int id;
        private String name;
    }

    private void modifyAddr(final String address) {
        N2A3_UserLogin loginResult = LoginInfoHelper.read();
        String sex = loginResult.sex;
        String birthday = loginResult.birthday;
        String email = loginResult.email;
        showLoading();
        String url = UrlBuilder.getN2A4_ModifyUserInfo(sex, birthday, email, address);
        GsonRequest request = new GsonRequest<N2A4_ModifyUserInfo>(url, N2A4_ModifyUserInfo.class,
                new Response.Listener<N2A4_ModifyUserInfo>() {
                    @Override
                    public void onResponse(N2A4_ModifyUserInfo response) {
                        XLog.w(TAG, "N2A4 success, response=%s", response);
                        dismissLoading();
                        showToastHint(response.msg);
                        if (response.status == RESPONSE_OK) {
                            N2A3_UserLogin loginResult = LoginInfoHelper.read();
                            loginResult.address = address;
                            LoginInfoHelper.update(loginResult);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A4 error, " + error);
                        showToastHint("上传异常");
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }

    private ArrayList<City> getCitys(int parentId) {
        LocationInfoHelper locationInfoHelper = new LocationInfoHelper(this);
        Cursor cursor = locationInfoHelper.queryLocation(parentId);
        ArrayList<City> citys = new ArrayList<City>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            City city = new City();
            city.id=cursor.getInt(cursor.getColumnIndex(LocationInfoProvider.DBColumns.LOCATION_ID));
            city.name=cursor.getString(cursor.getColumnIndex(LocationInfoProvider.DBColumns.LOCATION_NAME));
            XLog.d(TAG, "id=%d, location=%s", city.id, city.name);
            citys.add(city);
        }
        cursor.close();
        return citys;
    }

    // Scrolling flag
    private boolean scrolling = false;

    private TextView cancel;
    private TextView ok;
    private CountryAdapter provinceAdapter;
    private CountryAdapter cityAdapter;
    private CountryAdapter areaAdapter;
    // 创建一个包含自定义view的PopupWindow
    private PopupWindow makePopupWindow(Context cx) {
        final PopupWindow window;
        window = new PopupWindow(cx);

        View contentView = LayoutInflater.from(this).inflate(R.layout.view_city, null);
        window.setContentView(contentView);
        window.setBackgroundDrawable(null);
        window.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        cancel = (TextView)contentView.findViewById(R.id.address_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        ok = (TextView)contentView.findViewById(R.id.address_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proviceText = provinceAdapter.getItemText(province.getCurrentItem()).toString();
                String cityText = cityAdapter.getItemText(city.getCurrentItem()).toString();
                String areaText = areaAdapter.getItemText(area.getCurrentItem()).toString();
                addressText.setText(proviceText+cityText+areaText);
                window.dismiss();
            }
        });
        province = (WheelView)contentView.findViewById(R.id.province);
        province.setVisibleItems(3);
        provinceAdapter= new CountryAdapter(this);
        provinceAdapter.setCities(getCitys(0));
        province.setViewAdapter(provinceAdapter);
        city = (WheelView)contentView.findViewById(R.id.city);
        city.setVisibleItems(3);

        province.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {
                    updateCities(city, provinceAdapter.getCities().get(newValue).id);
                }
            }
        });

        province.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }

            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                updateCities(city,provinceAdapter.getCities().get(province.getCurrentItem()).id);
            }
        });

         // 地区选择
        area = (WheelView)contentView.findViewById(R.id.area);
        area.setVisibleItems(3);

        city.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!scrolling) {
                    updateAreas(area, cityAdapter.getCities().get(newValue).id);
                }
            }
        });

        city.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }

            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;
                updateAreas(area, cityAdapter.getCities().get(city.getCurrentItem()).id);

            }
        });
//
        area.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {
                scrolling = true;
            }

            public void onScrollingFinished(WheelView wheel) {
                scrolling = false;

            }
        });

        province.setCurrentItem(2);

        window.setWidth(width);
        window.setHeight(height / 2);

        // 设置PopupWindow外部区域是否可触摸
        window.setFocusable(true); // 设置PopupWindow可获得焦点
        window.setTouchable(true); // 设置PopupWindow可触摸
        window.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        return window;
    }

    /**
     * Updates the city wheel
     */
    private void updateCities(WheelView city, int id) {
        cityAdapter = new CountryAdapter(mActivity);
        cityAdapter.setCities(getCitys(id));
        city.setViewAdapter(cityAdapter);
        city.setCurrentItem(cityAdapter.getCities().size()/2);
    }
    /**
     * Updates the area wheel
     */
    private void updateAreas(WheelView area, int id) {
        areaAdapter = new CountryAdapter(mActivity);
        areaAdapter.setCities(getCitys(id));
        area.setViewAdapter(areaAdapter);
        area.setCurrentItem(areaAdapter.getCities().size()/2);
    }

    /**
     * Adapter for countries
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {
        private ArrayList<City> cities = Lists.newArrayList();

        protected CountryAdapter(Context context) {
            super(context, R.layout.country_layout, NO_RESOURCE);

            setItemTextResource(R.id.country_name);
        }
        public void setCities(ArrayList<City> cities) {
            this.cities = cities;
        }

        public ArrayList<City> getCities() {
            return cities;
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            if (cities != null) {
                return cities.size();
            }
            return 0;

        }

        @Override
        protected CharSequence getItemText(int index) {
            if (cities != null && cities.get(index) != null) {
                return cities.get(index).name;
            }
            return null;
        }
    }


}
