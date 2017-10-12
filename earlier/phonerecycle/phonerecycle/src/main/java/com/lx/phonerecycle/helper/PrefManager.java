package com.lx.phonerecycle.helper;

import android.content.Context;
import android.content.SharedPreferences;
import com.lx.phonerecycle.tools.XLog;

/**
 * Created with IntelliJ IDEA.
 * User: luo.xuan
 * Date: 13-11-13
 * Time: 下午4:03
 *
 * SharedPreferences存储信息管理类
 *
 */

public class PrefManager {

    private Context context;

    private static PrefManager mInstance;

    private static final String PREF_NAME = "PR_prefs";

    private PrefManager(Context context) {
        this.context = context;
    }

    public synchronized static PrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrefManager(context);
        }
        return mInstance;
    }

    public SharedPreferences getPreferences() {
        return context.getSharedPreferences(PREF_NAME, 0);
    }

    public static final String SYNC_LOCATION_TIME_KEY = "sync_location_time";

    public void setSyncLocationTime(long time) {
        XLog.d("lx", "setSyncLocationTime : %s", time);
        getPreferences().edit()
                .putLong(SYNC_LOCATION_TIME_KEY, time)
                .commit();
    }

    public long getSyncLocationTime() {
        long time = getPreferences().getLong(SYNC_LOCATION_TIME_KEY, 0);
        XLog.d("lx", "getSyncLocationTime : %s", time);
        return time;
    }

}
