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
import android.net.Uri;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;

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
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_short_cut))
                .setContentTitle("123title")
                .setContentText("123text")
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

    static int abc = 1;
    static int req = 1;

    private static void setRemoteViewItemData(RemoteViews remoteViews, List<RemoteViewsData> dataList, Context context,
                                              int position, int[] viewIds) {
        if (dataList.size() > position) {
            RemoteViewsData remoteViewsData = dataList.get(position);
            Intent intent = new Intent(context, CheckCodeActivity.class);
            intent.setAction("action-" + abc);
            intent.putExtra("lx", "123");
            intent.putExtra("lx123", abc++);
            intent.setData(Uri.parse("http://ssab---" + abc));
            remoteViews.setOnClickPendingIntent(viewIds[0],
                    PendingIntent.getActivity(context, req++, intent, 0));
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


//
//    public void doShow()
//    {
//        Application localApplication = ISApplication.context;
//        Object localObject1 = getPackageManager(localApplication);
//        if (TextUtils.isEmpty((CharSequence)localObject1)) {}
//        for (;;)
//        {
//            return;
//            Object localObject2 = BitmapFactory.decodeResource(localApplication.getResources(), 2130838093);
//            localObject2 = new NotificationCompat.Builder(localApplication)
//                    .setSmallIcon(2130838093)
//                    .setLargeIcon((Bitmap)localObject2)
//                    .setContentTitle(localApplication.getResources().getString(2131231473))
//                    .setContentText(localApplication.getResources().getString(2131231472))
//                    .setOngoing(true)
//                    .setAutoCancel(false);
//            ((NotificationCompat.Builder)localObject2).setContentIntent(
//                    PendingIntent.getActivity(localApplication, 0, new Intent(localApplication, HomeActivity.class), 134217728));
//            Notification localNotification = ((NotificationCompat.Builder)localObject2).build();
//            NotificationManager localNotificationManager = (NotificationManager)localApplication.getSystemService("notification");
//            localNotification.contentView = new RemoteViews((String)localObject1, 2130903652);
//            localNotification.contentView.setImageViewResource(2131691546, 2130838093);
//            localNotification.contentView.setImageViewResource(2131691547, 2130838158);
//            localObject2 = new Bundle();
//            localObject1 = new Intent(localApplication, PushRedirectActivity.class);
//            ((Bundle)localObject2).putString("url", "ws-setting-message");
//            ((Intent)localObject1).putExtras((Bundle)localObject2);
//            localObject1 = PendingIntent.getActivity(localApplication, UUID.randomUUID().hashCode(), (Intent)localObject1, 134217728);
//            localNotification.contentView.setOnClickPendingIntent(2131691547, (PendingIntent)localObject1);
//            localObject1 = null;
//            boolean bool = false;
//            localObject2 = ConfigCenter.getInstance().getConfigResult("etao_android_notification");
//            if (localObject2 != null)
//            {
//                localObject1 = new NotificationConfigData(JsonData.create((String)localObject2));
//                bool = afterDownLoad((NotificationConfigData)localObject1);
//            }
//            localObject2 = getLocalData();
//            label326:
//            Resources localResources;
//            Intent[] arrayOfIntent;
//            int j;
//            label347:
//            int k;
//            if (bool)
//            {
//                localObject1 = ((NotificationConfigData)localObject1).mFastEntryList;
//                if (((List)localObject1).size() <= 4) {
//                    break label649;
//                }
//                i = 4;
//                localResources = localApplication.getResources();
//                arrayOfIntent = new Intent[((List)localObject1).size()];
//                j = 0;
//                if (j >= i) {
//                    break label686;
//                }
//                k = localResources.getIdentifier("text" + String.valueOf(j), "id", localApplication.getPackageName());
//                localNotification.contentView.setTextViewText(k, ((NotificationConfigData.FastEntryItem)((List)localObject1).get(j)).mText);
//                k = localResources.getIdentifier("image" + String.valueOf(j), "id", localApplication.getPackageName());
//                if ((!bool) || (!TextUtils.isEmpty(((NotificationConfigData.FastEntryItem)((List)localObject1).get(j)).mLocalPath))) {
//                    break label660;
//                }
//                localNotification.contentView.setImageViewUri(k, Uri.fromFile(new File(((NotificationConfigData.FastEntryItem)((List)localObject1).get(j)).mLocalPath)));
//            }
//            for (;;)
//            {
//                k = localResources.getIdentifier("layout" + String.valueOf(j), "id", localApplication.getPackageName());
//                Object localObject3 = new Bundle();
//                ((Bundle)localObject3).putString("url", ((NotificationConfigData.FastEntryItem)((List)localObject1).get(j)).mUrl);
//                arrayOfIntent[j] = new Intent(localApplication, PushRedirectActivity.class);
//                arrayOfIntent[j].putExtras((Bundle)localObject3);
//                localObject3 = PendingIntent.getActivity(localApplication, UUID.randomUUID().hashCode(), arrayOfIntent[j], 134217728);
//                localNotification.contentView.setOnClickPendingIntent(k, (PendingIntent)localObject3);
//                localNotification.contentView.setViewVisibility(k, 0);
//                j++;
//                break label347;
//                localObject1 = localObject2;
//                break;
//                label649:
//                i = ((List)localObject1).size();
//                break label326;
//                label660:
//                localNotification.contentView.setImageViewResource(k, ((NotificationConfigData.FastEntryItem)((List)localObject2).get(j)).mImgInt);
//            }
//            label686:
//            for (int i = ((List)localObject1).size(); i < 4; i++)
//            {
//                j = localResources.getIdentifier("layout" + String.valueOf(i), "id", localApplication.getPackageName());
//                localNotification.contentView.setViewVisibility(j, 8);
//            }
//            localNotificationManager.notify(RESIDENT_NOTIFICATION_NUM, localNotification);
//        }
//    }


}
