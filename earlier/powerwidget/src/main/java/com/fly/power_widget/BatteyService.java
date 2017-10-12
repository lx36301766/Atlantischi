package com.fly.power_widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class BatteyService extends Service implements CommonConstants {

    public BroadcastReceiver battryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "intent->" + intent);
            Log.d(TAG, "action->" + action);
            ComponentName thiswidget = new ComponentName(context, PowerWidget.class);
            AppWidgetManager appmanager = AppWidgetManager.getInstance(context);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clock_widget);
            int level = intent.getIntExtra(EXTRA_LEVEL, 0);
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int scale = intent.getIntExtra(EXTRA_SCALE, 100);
                int status = intent.getIntExtra(EXTRA_STATUS, 0);
                Log.e(TAG, EXTRA_LEVEL + "->" + level);
                Log.e(TAG, EXTRA_SCALE + "->" + scale);
                Log.e(TAG, EXTRA_STATUS + "->" + status);
                views.setTextViewText(R.id.power_percent, "电量:");
                // views.setFloat(R.id.power_percent, "setTextSize", 20);
                // views.setTextColor(R.id.power_percent, 0xffff0000);
                views.setInt(R.id.power_percent_image, "setImageResource", R.drawable.stat_sys_battery_000 + level);
                if (level == 100) {
                	views.setInt(R.id.power_image, "setImageResource", R.drawable.uncharge);
				}
            } else if (Intent.ACTION_POWER_CONNECTED.equals(action) && level !=100) {
                views.setInt(R.id.power_image, "setImageResource", R.drawable.charging);
            } else if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
                views.setInt(R.id.power_image, "setImageResource", R.drawable.uncharge);
            }
            appmanager.updateAppWidget(thiswidget, views);
        }
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(battryReceiver, filter);
    }

    public void onDestroy() {
        unregisterReceiver(battryReceiver);
        super.onDestroy();
    }
	
}