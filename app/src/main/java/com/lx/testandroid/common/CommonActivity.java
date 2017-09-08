package com.lx.testandroid.common;

import java.util.ArrayList;
import java.util.List;

import com.lx.testandroid.R;
import com.lx.testandroid.util.ResidentNotification;
import com.lx.testandroid.util.TestShortcutUtils;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

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
    }

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

}
