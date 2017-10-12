package com.lx.phonerecycle.location;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.lx.phonerecycle.gsonbean.N2A1_GetProvinceCity;
import com.lx.phonerecycle.helper.PrefManager;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;

import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-24 下午6:57 <br>
 * description:
 */

public class SyncLocationService extends IntentService {

    private static final String TAG = SyncLocationService.class.getSimpleName();

    public SyncLocationService() {
        super("SyncLocationService");
    }

    private LocationInfoHelper locationInfoHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        XLog.w(TAG, "SyncLocationService, onCreate");
        locationInfoHelper = new LocationInfoHelper(this);
    }

    public static final String SYNC_OVER = "sync_over";

    @Override
    protected void onHandleIntent(Intent intent) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = UrlBuilder.getN2A1_GetProvinceCity();
        GsonRequest request = new GsonRequest<N2A1_GetProvinceCity>(
                url, N2A1_GetProvinceCity.class,
                new Response.Listener<N2A1_GetProvinceCity>() {
                    @Override
                    public void onResponse(N2A1_GetProvinceCity response) {
                        XLog.w(TAG, "SyncLocationService, getCityFromServer N2A1 success");
                        if (response.status == 111) {
                            syncDataToDB(response.data);
                            LocalBroadcastManager.getInstance(getApplication()).sendBroadcast(new Intent(SYNC_OVER));
                            long time = System.currentTimeMillis();
                            PrefManager.getInstance(getApplicationContext()).setSyncLocationTime(time);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A1 error, " + error);
                    }
                });
        requestQueue.add(request);
    }

    private void syncDataToDB(List<N2A1_GetProvinceCity.Item> items) {
        int size = locationInfoHelper.bulkInsertLocation(items);
        XLog.d(TAG, "sync %d locations to db", size);
    }

}
