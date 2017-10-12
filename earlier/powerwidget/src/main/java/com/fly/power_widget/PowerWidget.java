package com.fly.power_widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RemoteViews;

public class PowerWidget extends AppWidgetProvider implements CommonConstants {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive!  receive intent ---> " + intent.getAction());
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent intent = new Intent(context, BatteyService.class);
        context.startService(intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Intent intent = new Intent(context, BatteyService.class);
        context.stopService(intent);
    }

    @Override
    public void onEnabled(Context context) {
        Log.i(TAG, "onEnabled!");
    }

    @Override
    public void onDisabled(Context context) {
        Log.i(TAG, "onDisabled!");
    }

}
