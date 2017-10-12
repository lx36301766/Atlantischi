package com.lx.phonerecycle.upgrade;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import com.lx.phonerecycle.R;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressWarnings("all")
public class DownloadService extends Service {

    private String tag = DownloadService.class.getSimpleName();
    private static final String NO_SDCARD = "removed";// 无SDCard
    private static final String DOWN_OVER = "over";// 下载完成
    private static final String DOWN_ERROR = "error";// 下载错误

    // app的下载地址
    private String appURL = "";
    // 间隔时间
    private long spaceTime = 1000L;
    // 上一次下载时间
    private long preTime = 0L;
    private static final String apkPath = Environment
            .getExternalStorageDirectory().getPath() + "/PhoneRecycle/apk";
    private static final String apkName = "PhoneRecycle.apk";

    private NotificationManager mNotificationManager = null;// 通知栏manager
    private Notification mNotification = null;
    private final int NOTIFY_ID = 0;// 通知ID
    private int rate = 0;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case MessageType.MSG_NOTIFICATION_DOWNLOADING:
                    rate = msg.arg1;
                    if (rate < 100) {
                        // 更新进度
                        RemoteViews contentView = mNotification.contentView;
                        // 设置下载进度
                        contentView.setTextViewText(R.id.tv_rate_content_view, rate
                                + "%");
                        contentView.setProgressBar(R.id.pb_content_view_progress,
                                100, rate, false);
                        // 最后别忘了通知一下,否则不会更新
                        mNotificationManager.notify(NOTIFY_ID, mNotification);
                    } else {
                        // 下载完毕后变换通知形式
                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                        // 更新进度
                        RemoteViews contentView = mNotification.contentView;
                        // 设置下载进度
                        contentView.setTextViewText(R.id.tv_rate_content_view, rate
                                + "%");
                        contentView.setProgressBar(R.id.pb_content_view_progress,
                                100, rate, false);
                        // 最后别忘了通知一下,否则不会更新
                        mNotificationManager.notify(NOTIFY_ID, mNotification);
                        stopSelf();// 停掉服务自身
                        CallExtraAppUtil.installApk(apkPath, apkName,
                                DownloadService.this);
                    }

                    break;
                case MessageType.MSG_DOWNLOAD_FIALED:
                    stopSelf();// 停掉服务自身,不然再次启动程序会报错
                    // 取消通知
                    mNotificationManager.cancel(NOTIFY_ID);
                    break;
            }
        }

    };

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(tag, "DownLoadService---onCreate()");
        mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(tag, "DownLoadService----onDestroy()");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        appURL = intent.getStringExtra(AppConstants.N1_A_.URL);
        // 初始化通知栏
        initNotificationView();
        // 下载线程
        DownloadRunnable downRunnable = new DownloadRunnable();
        Thread downThread = new Thread(downRunnable);
        downThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    class DownloadRunnable implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            download(appURL);
        }

    }

    /**
     * 初始化notification通知栏
     */
    public void initNotificationView() {
        mNotification = new Notification();
        mNotification.tickerText = "下载中...";
        mNotification.icon = R.drawable.ic_launcher;
        /**
         * FLAG_AUTO_CANCEL 该通知能被状态栏的清除按钮给清除掉 FLAG_NO_CLEAR 该通知能被状态栏的清除按钮给清除掉
         * FLAG_ONGOING_EVENT 通知放置在正在运行 FLAG_INSISTENT 是否一直进行，比如音乐一直播放，知道用户响应
         */
        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;

        // 指定个性化视图
        RemoteViews _RemoteViews = new RemoteViews(this.getPackageName(),
                R.layout.lay_notification_download_view);
        mNotification.contentView = _RemoteViews;

        // 指定意图内容
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(apkPath, apkName)),
                "application/vnd.android.package-archive");
        PendingIntent contentIntent = PendingIntent.getActivity(
                DownloadService.this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mNotification.contentIntent = contentIntent;

        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    /**
     * 下载文件
     *
     * @param url_ 下载地址
     * @return
     */
    public String download(String url_) {
        Message msg = null;
        LogUtil.i(tag, "----download()----start");
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file = new File(apkPath);
            // 判断文件夹是否存在
            if (!file.exists()) {
                file.mkdirs();
                file = null;
            }
            LogUtil.i(tag, "----download()----checkfile");
        } else {
            LogUtil.i(tag, "----download()----nofile");
            return NO_SDCARD;
        }

        File apkFile = new File(apkPath, apkName);
        if (apkFile.getPath() == "") {
            // 没有挂载存储卡,无法下载
            LogUtil.i(tag, "----download()----nofile");
            return NO_SDCARD;
        }
        // 如果apk存在则先删除
        if (apkFile.exists() && apkFile.isFile()) {
            apkFile.delete();
        }
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url_);

        FileOutputStream fos = null;
        InputStream is = null;
        try {
            HttpResponse response = client.execute(httpGet);
            LogUtil.i(tag, "----download()----download -- file");
            int responseCode = response.getStatusLine().getStatusCode();
            LogUtil.i(tag, "----download()----download -- responseCode:"
                    + responseCode);
            if (responseCode == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

                fos = new FileOutputStream(apkFile);

                byte[] buffer = new byte[1024];

                long contentLength = entity.getContentLength();// 文件总大小
                LogUtil.i(tag, "----download()-----contentLength = "
                        + contentLength);
                int _Temp = 0;// 此循环写入的大小

                long bufferLength = 0;

                while (-1 != (_Temp = is.read(buffer))) {
                    fos.write(buffer, 0, _Temp);

                    bufferLength += _Temp;
                    // 当前时间减去上一次下载时间，大于等于间隔时间或上一次下载时间为0(开始下载)，则刷新一次UI
                    if (preTime == 0L
                            || System.currentTimeMillis() - preTime >= spaceTime) {
                        preTime = System.currentTimeMillis();
                        msg = handler.obtainMessage();
                        msg.what = MessageType.MSG_NOTIFICATION_DOWNLOADING;
                        msg.arg1 = (int) (bufferLength * 100 / contentLength);
                        handler.sendMessage(msg);

                    }

                }
                fos.flush();
                msg = handler.obtainMessage();
                msg.what = MessageType.MSG_NOTIFICATION_DOWNLOADING;
                msg.arg1 = 100;
                handler.sendMessage(msg);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(MessageType.MSG_DOWNLOAD_FIALED);
            return DOWN_ERROR;
        } catch (IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(MessageType.MSG_DOWNLOAD_FIALED);
            return DOWN_ERROR;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (httpGet != null) {
                    httpGet.abort();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return DOWN_OVER;

    }
}
