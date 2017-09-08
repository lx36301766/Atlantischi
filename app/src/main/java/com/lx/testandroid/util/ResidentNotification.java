package com.lx.testandroid.util;

import java.util.List;

import com.lx.testandroid.MainActivity;
import com.lx.testandroid.R;
import com.lx.testandroid.common.CheckCodeActivity;
import com.lx.testandroid.common.ClassLoaderActivity;
import com.lx.testandroid.common.NumberTextActivity;
import com.lx.testandroid.market.MarketActivity;
import com.lx.testandroid.viewpager.ViewPagerActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created on 05/09/2017.
 *
 * @author lx
 */

public class ResidentNotification {

    public static class RemoteViewsData {
        String title;
        Bitmap bitmap;
        String scheme;

        public RemoteViewsData(String title, Bitmap bitmap) {
            this.title = title;
            this.bitmap = bitmap;
        }

        public RemoteViewsData(String title, Bitmap bitmap, String scheme) {
            this.title = title;
            this.bitmap = bitmap;
            this.scheme = scheme;
        }
    }

    // 取消通知
    public static void cancelNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context
                .NOTIFICATION_SERVICE);
        notificationManager.cancel(R.string.app_name);
    }

    // 发送通知
    public static void setNotification(Context context, List<RemoteViewsData> dataList) {
        if (dataList.size() == 0) {
            return;
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context
                .NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent contextIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(context)
                .setContentIntent(contextIntent)
                .setOngoing(true)
                .setWhen(System.currentTimeMillis())
                .setCustomContentView(buildRootRemoteViews(context, dataList))
                .setSmallIcon(R.drawable.icon_short_cut)
                .build();
        notificationManager.notify(R.string.app_name, notification);
    }

    private static RemoteViews buildRootRemoteViews(Context context, List<RemoteViewsData> dataList) {
        if (dataList.size() == 0) {
            return null;
        }
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.resident_notification_layout);

        setRemoteViewItemData(remoteViews, dataList, context, 0, new int[]{
                R.id.notification_shortcut_0,
                R.id.notification_shortcut_icon_0,
                R.id.notification_shortcut_name_0,
        });

        setRemoteViewItemData(remoteViews, dataList, context, 1, new int[]{
                R.id.notification_shortcut_1,
                R.id.notification_shortcut_icon_1,
                R.id.notification_shortcut_name_1,
        });

        setRemoteViewItemData(remoteViews, dataList, context, 2, new int[]{
                R.id.notification_shortcut_2,
                R.id.notification_shortcut_icon_2,
                R.id.notification_shortcut_name_2,
        });

        setRemoteViewItemData(remoteViews, dataList, context, 3, new int[]{
                R.id.notification_shortcut_3,
                R.id.notification_shortcut_icon_3,
                R.id.notification_shortcut_name_3,
        });

        remoteViews.setOnClickPendingIntent(R.id.notification_shortcut_setting,
                PendingIntent.getActivity(context, 0, new Intent(context, ViewPagerActivity.class), 0));
        return remoteViews;
    }

    private static void setRemoteViewItemData(RemoteViews remoteViews, List<RemoteViewsData> dataList, Context context,
                                              int position, int[] viewIds) {
        if (dataList.size() > position) {
            RemoteViewsData remoteViewsData = dataList.get(position);
            remoteViews.setOnClickPendingIntent(viewIds[0],
                    PendingIntent.getActivity(context, 0, new Intent(context, CheckCodeActivity.class), 0));
            remoteViews.setTextViewText(viewIds[2], remoteViewsData.title);
        }
        remoteViews.setImageViewBitmap(viewIds[1], fillTransparentBitmapIfNeed(position, dataList));
    }

    private static Bitmap fillTransparentBitmapIfNeed(int pos, List<RemoteViewsData> dataList) {
        if (dataList.size() > pos) {
            return dataList.get(pos).bitmap;
        } else {
            Bitmap firstBitmap = dataList.get(0).bitmap;
            return Bitmap.createBitmap(firstBitmap.getWidth(), firstBitmap.getHeight(), Bitmap.Config.ALPHA_8);
        }
    }

}
