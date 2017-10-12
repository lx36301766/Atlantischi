package com.lx.phonerecycle.location;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.lx.phonerecycle.gsonbean.N2A1_GetProvinceCity;
import com.lx.phonerecycle.tools.XLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.lx.phonerecycle.location.LocationInfoProvider.DBColumns.*;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-25 上午1:04 <br>
 * description:
 */

public class LocationInfoHelper {

    private static final String TAG = LocationInfoProvider.class.getSimpleName();

    private Context mContext;

    public LocationInfoHelper(Context context) {
        mContext = context.getApplicationContext();
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public int bulkInsertLocation(List<N2A1_GetProvinceCity.Item> items) {
        mContext.getContentResolver().delete(LocationInfoProvider.DBColumns.CONTENT_URI, null, null);
        String t1 = sdf.format(Calendar.getInstance().getTime());
        XLog.d(TAG, "bulkInsertLocation start, t1=%s", t1);
        int size = items.size();
        ContentValues[] valueses = new ContentValues[size];
        for (int i = 0; i < size; i++) {
            valueses[i] = new ContentValues();
            valueses[i].put(LOCATION_ID, items.get(i).id);
            valueses[i].put(LOCATION_NAME, items.get(i).name);
            if (TextUtils.isEmpty(items.get(i).parent_id)) {
                valueses[i].put(PARENT_ID, 0);
            } else {
                valueses[i].put(PARENT_ID, items.get(i).parent_id);
            }

        }
        int rows = mContext.getContentResolver().bulkInsert(LocationInfoProvider.DBColumns.CONTENT_URI, valueses);
        String t2 = sdf.format(Calendar.getInstance().getTime());
        XLog.d(TAG, "bulkInsertLocation end, t2=%s", t2);
        return rows;
    }

    public Cursor queryLocation(int parentId) {
        String where = PARENT_ID + "=" + parentId;
        return mContext.getContentResolver().query(CONTENT_URI, null, where, null, null);
    }

}
