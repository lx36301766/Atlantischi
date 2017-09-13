package com.lx.testandroid.common;

import java.util.ArrayList;
import java.util.List;

import com.lx.testandroid.R;
import com.lx.testandroid.util.ResidentNotification;
import com.lx.testandroid.util.TestShortcutUtils;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * Created on 05/09/2017.
 *
 * @author lx
 */

public class CommonActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);

        registerReceiver(homeKeyReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));

        IntentFilter filter = new IntentFilter("shortcut");
        filter.addCategory(Intent.CATEGORY_LAUNCHER);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
                    String action = intent.getAction();
                    ComponentName componentName = intent.getComponent();
                    System.out.println(componentName.getClassName());
                }
            }
        }, filter);
    }

    BroadcastReceiver homeKeyReceiver = new BroadcastReceiver() {

        final String SYSTEM_DIALOG_REASON_KEY = "reason";
        final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason != null) {
                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                        //Home键被监听
                        Toast.makeText(CommonActivity.this, "home press", Toast.LENGTH_SHORT).show();
                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                        //多任务键被监听
                        Toast.makeText(CommonActivity.this, "recentapps press", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                TestShortcutUtils.addLaunchIcon(this);
                break;
            case R.id.btn_2:
                TestShortcutUtils.delLaunchIcon(this);
                break;
            case R.id.btn_3:
                List<ResidentNotification.RemoteViewsData> dataList = new ArrayList<>();
                dataList.add(new ResidentNotification.RemoteViewsData("宝箱",
                        BitmapFactory.decodeResource(getResources(), R.drawable.notification_icon_treasure_box)));
                dataList.add(new ResidentNotification.RemoteViewsData("签到",
                        BitmapFactory.decodeResource(getResources(), R.drawable.notification_icon_sign_in)));
                dataList.add(new ResidentNotification.RemoteViewsData("搜索",
                        BitmapFactory.decodeResource(getResources(), R.drawable.notification_icon_search)));
                dataList.add(new ResidentNotification.RemoteViewsData("聚美",
                        BitmapFactory.decodeResource(getResources(), R.drawable.notification_icon_jumei)));
                ResidentNotification.setNotification(this, dataList);
                break;
            case R.id.btn_4:
                ResidentNotification.cancelNotification(this);
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println();
    }

}
