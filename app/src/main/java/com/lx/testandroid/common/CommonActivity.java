package com.lx.testandroid.common;

import java.util.ArrayList;
import java.util.List;

import com.lx.testandroid.R;
import com.lx.testandroid.receiver.HomePressReceiver;
import com.lx.testandroid.shortcut.shortcut_lib.ShortcutSuperUtils;
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

        //registerReceiver(new HomePressReceiver(), new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                TestShortcutUtils.addLaunchIcon(this);
//                ShortcutSuperUtils.addShortcutByPackageName(this, "abcd");
                break;
            case R.id.btn_2:
                TestShortcutUtils.delLaunchIcon(this);
                break;
            case R.id.btn_3:
                testShowNotification();
                break;
            case R.id.btn_4:
                ResidentNotification.cancelNotification(this);
                break;
        }
    }

    private void testShowNotification() {
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
    }

}
