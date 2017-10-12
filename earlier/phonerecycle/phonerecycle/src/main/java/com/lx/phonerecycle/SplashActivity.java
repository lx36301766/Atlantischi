package com.lx.phonerecycle;

import android.app.AlertDialog;
import android.content.*;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lx.phonerecycle.gsonbean.N1A_InitMain;
import com.lx.phonerecycle.gsonbean.N2A3_UserLogin;
import com.lx.phonerecycle.helper.ActivityHelper;
import com.lx.phonerecycle.helper.CustomToast;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.helper.PrefManager;
import com.lx.phonerecycle.location.SyncLocationService;
import com.lx.phonerecycle.request.MainInfo;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.UITools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.upgrade.AppConstants;
import com.lx.phonerecycle.upgrade.DownloadService;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import roboguice.inject.ContentView;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 上午11:46 <br>
 * description:
 */

@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    private static final int SHOW_TIME = 1; //sec

    private boolean isShowTimeEnd = false;
    private boolean initSuccess = false;

    //同步城市信息的时间间隔，（15天）
    private static final long SYNC_INTERVAL = 10000 * 30 * 1000; // 15 * 24 * 60 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initN1A();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isShowTimeEnd = true;
                XLog.e(TAG, "show time end");
                checkCanGotoNextActivity();
            }
        }, SHOW_TIME * 1000);

        IntentFilter filter = new IntentFilter();
        filter.addAction(SyncLocationService.SYNC_OVER);
        LocalBroadcastManager.getInstance(mActivity).registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            XLog.d(TAG, "received action ---> %s", intent.getAction());
            if (SyncLocationService.SYNC_OVER.equals(intent.getAction())) {
                initSuccess = true;
                checkCanGotoNextActivity();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mActivity).unregisterReceiver(receiver);
    }

    private void initN1A() {
        String n1aUrl = UrlBuilder.getN1A_InitMain();
        GsonRequest request = new GsonRequest<N1A_InitMain>(
                n1aUrl, N1A_InitMain.class,
                new Response.Listener<N1A_InitMain>() {
                    @Override
                    public void onResponse(N1A_InitMain response) {
                        MainInfo.initMainN1ASuccess = true;
                        MainInfo.n2_a = response.n2_a;
                        MainInfo.n3_a = response.n3_a;
                        MainInfo.n4_a = response.n4_a;
                        XLog.e(TAG, "init N1A success");
//                        CustomToast.show(mActivity, "初始化主入口地成功");
                        int versionCode = Integer.parseInt(response.init_data.client_version.android_phone.version_inner);
                        String url = response.init_data.client_version.android_phone.url;
                        checkUpgrade(versionCode, url);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CustomToast.show(mActivity, "获取服务器入口地址失败，请检查网络设置！");
                        XLog.e(TAG, "init N1A error, " + error);
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1000);
                    }
                });
        requestQueue.add(request);
    }

    private void checkUpgrade(int newVersionCode, final String url) {
        int curVersionCode = getPackageVersionCode();
        XLog.e(TAG, "checkUpgrade, curVersionCode=%d, newVersionCode=%d, url=%s",
                curVersionCode, newVersionCode, url);
        if (newVersionCode > curVersionCode) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            builder.setTitle("发现新版本，是否下载升级？")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent serviceIntent = new Intent();
                            serviceIntent.putExtra(AppConstants.N1_A_.URL, url);
                            serviceIntent.setClass(SplashActivity.this, DownloadService.class);
                            startService(serviceIntent);
                            syncLocation();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            syncLocation();
                        }
                    })
                    .show();
        } else {
            syncLocation();
        }
    }

    public int getPackageVersionCode() {
        PackageManager pm = getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String name = pi.versionName;
        int code = pi.versionCode;
        return code;
    }

    private void syncLocation () {
        long lastSyncTime = PrefManager.getInstance(mActivity).getSyncLocationTime();
        long curTime = System.currentTimeMillis();
        long interval = curTime - lastSyncTime;
        XLog.d(TAG, "syncLocation, lastSyncTime=%s, curTime=%s, interval = %s", lastSyncTime, curTime, interval);
        if (interval > SYNC_INTERVAL) {
            Intent intent = new Intent(mActivity, SyncLocationService.class);
            startService(intent);
            XLog.d(TAG, "syncLocation, sync...");
        } else {
            initSuccess = true;
            checkCanGotoNextActivity();
        }
    }

    private void checkCanGotoNextActivity() {
        XLog.d(TAG, "checkCanGotoNextActivity, isShowTimeEnd=%s, initSuccess=%s",
                isShowTimeEnd, initSuccess);
        if (isShowTimeEnd && initSuccess) {
            Tools.jumpActivity(mActivity, HomeActivity.class);
            finish();
//            N2A3_UserLogin loginResult = LoginInfoHelper.read();
//            if (loginResult != null) {
//                Tools.jumpActivity(mActivity, HomeActivity.class);
//                finish();
//            } else {
//                Tools.jumpActivity(mActivity, LoginActivity.class);
//                finish();
//            }
        }
    }

    @Override
    public void onBackPressed() {
        ActivityHelper.getInstance().endApp();
    }
}
