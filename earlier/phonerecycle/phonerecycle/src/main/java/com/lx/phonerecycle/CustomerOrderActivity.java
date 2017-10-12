package com.lx.phonerecycle;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.Lists;
import com.lx.phonerecycle.gsonbean.N3A10_RedemptionSubmit;
import com.lx.phonerecycle.helper.RecycleTypeManager;
import com.lx.phonerecycle.location.LocationInfoHelper;
import com.lx.phonerecycle.location.LocationInfoProvider;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import roboguice.inject.ContentView;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-8-4 上午11:12 <br>
 * description:
 *
 * 帮客户下单
 */

@ContentView(R.layout.activity_customer_order)
public class CustomerOrderActivity extends BaseActivity {

    @InjectView(R.id.customer_name)             private EditText customNameEdit;
    @InjectView(R.id.customer_phone_num)        private EditText customPhoneNumEdit;
    @InjectView(R.id.customer_redem_time)       private TextView customRedemTime;
    @InjectView(R.id.customer_redem_province)   private TextView customRedemProvince;
    @InjectView(R.id.customer_redem_city)       private TextView customRedemCity;
    @InjectView(R.id.customer_redem_county)     private TextView customRedemCounty;
    @InjectView(R.id.customer_addr_details)     private TextView customAddrDetails;
    @InjectView(R.id.agree_check_box)           private CheckBox agreeCheckBox;
    @InjectView(R.id.confirm_order)             private TextView confirmOrder;

    private DatePickerDialog datePickerDialog;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @InjectExtra(value = AssessmentResultsActivity.ASSESS_RESULT, optional = true)
    private AssessmentResultsActivity.AssessResult assessResult;

    private RecycleTypeManager.RedemptionPhoneInfo redemInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        redemInfo = RecycleTypeManager.getInstance().getRedemptionPhoneInfo();
    }

    private void initViews() {
        customRedemTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar c = new GregorianCalendar(year, month, dayOfMonth);
                String date = sdf.format(c.getTime());
                customRedemTime.setText(date);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        customRedemProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<City> provinces = getCitys(0);
                showSingleChoiceDialog(provinces, customRedemProvince);
            }
        });
        customRedemCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City province = (City) customRedemProvince.getTag();
                if (province != null) {
                    List<City> provinces = getCitys(province.id);
                    showSingleChoiceDialog(provinces, customRedemCity);
                } else {
                    showToastHint("请选择省份");
                }
            }
        });
        customRedemCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City city = (City) customRedemCity.getTag();
                if (city != null) {
                    List<City> provinces = getCitys(city.id);
                    showSingleChoiceDialog(provinces, customRedemCounty);
                } else {
                    showToastHint("请选择城市");
                }
            }
        });
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = customNameEdit.getText().toString();
                String phone = customPhoneNumEdit.getText().toString();
                String time = customRedemTime.getText().toString();
                String province = customRedemProvince.getText().toString();
                String city = customRedemCity.getText().toString();
                String county = customRedemCounty.getText().toString();
                String addrDetails = customAddrDetails.getText().toString();
                boolean agreed = agreeCheckBox.isChecked();
                if (TextUtils.isEmpty(name)) {
                    showToastHint("请输入客户姓名");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    showToastHint("请输入客户电话号码");
                    return;
                }
                if (TextUtils.isEmpty(time)) {
                    showToastHint("请选择客户交机时间");
                    return;
                }
                if (TextUtils.isEmpty(province) || TextUtils.isEmpty(city) || TextUtils.isEmpty(county)) {
                    showToastHint("请选择交机地址");
                    return;
                }
                if (TextUtils.isEmpty(addrDetails)) {
                    showToastHint("请输入详细交机地址");
                    return;
                }
                if (!agreed) {
                    showToastHint("请同意服务条款");
                    return;
                }
                String addr = province + city + county + addrDetails;
                requestRedemInfo(name, phone, time, addr);
            }
        });
    }

    private class City {
        private int id;
        private int pos;
        private String name;
    }

    private List<City> getCitys(int parentId) {
        LocationInfoHelper locationInfoHelper = new LocationInfoHelper(this);
        Cursor cursor = locationInfoHelper.queryLocation(parentId);
        List<City> citys = Lists.newArrayList();
        int i = 0;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            City city = new City();
            city.id = cursor.getInt(cursor.getColumnIndex(LocationInfoProvider.DBColumns.LOCATION_ID));
            city.pos = i;
            city.name = cursor.getString(cursor.getColumnIndex(LocationInfoProvider.DBColumns.LOCATION_NAME));
            XLog.d(TAG, "id=%d, location=%s", city.id, city.name);
            citys.add(city);
            i++;
        }
        cursor.close();
        return citys;
    }

    private void showSingleChoiceDialog(final List<City> cities, final TextView textView) {
        final String[] items = new String[cities.size()];
        for (int i = 0; i < cities.size(); i++) {
            items[i] = cities.get(i).name;
        }
        int selectedPos = -1;
        City city = (City) textView.getTag();
        if (city != null) {
            selectedPos = city.pos;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                .setSingleChoiceItems(items, selectedPos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (items[which].equals(textView.getText().toString())) {
                            return;
                        }
                        textView.setText(items[which]);
                        textView.setTag(cities.get(which));
                        if (textView == customRedemProvince) {
                            customRedemCity.setText("");
                            customRedemCity.setTag(null);
                            customRedemCounty.setText("");
                            customRedemCounty.setTag(null);
                        } else if (textView == customRedemCity) {
                            customRedemCounty.setText("");
                            customRedemCounty.setTag(null);
                        }
                    }
                });
        builder.create().show();
    }

    public void requestRedemInfo(String c_name, String c_phone, String c_time, String c_addr) {
        showLoading();
        String phoneNum = assessResult.phoneNum;
        String brand = assessResult.assessArgsData.brand;
        String model = assessResult.assessArgsData.model;
        String assess = assessResult.assessArgsStr;
        String redemPhoneId = redemInfo.id;
        String url = UrlBuilder.getN3A10_RedemptionSubmit(phoneNum, brand, model, assess, redemPhoneId,
                c_name, c_phone, c_time, c_addr);
        GsonRequest request = new GsonRequest<N3A10_RedemptionSubmit>(url, N3A10_RedemptionSubmit.class,
                new Response.Listener<N3A10_RedemptionSubmit>() {
                    @Override
                    public void onResponse(N3A10_RedemptionSubmit response) {
                        XLog.w(TAG, "N3A10 success, response=%s", response);
                        dismissLoading();
                        if (response.status == RESPONSE_OK) {
                            Tools.jumpActivity(mActivity, OrderSuccessActivity.class,
                                    Pair.create(OrderSuccessActivity.REDEMPTION_SUBMIT_RESULT, response.data),
                                    Pair.create(OrderSuccessActivity.ORDER_TYPE, OrderSuccessActivity.ORDER_TYPE_REDEMPTION_PHONE));
                        } else {
                            showToastHint(response.msg);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N3A10 error, " + error);
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }


}
