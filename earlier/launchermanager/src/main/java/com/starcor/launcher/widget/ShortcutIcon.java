package com.starcor.launcher.widget;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.starcor.launcher.domain.AppInfo;
import com.starcorcn.launcher.R;

public abstract class ShortcutIcon extends LinearLayout implements
        OnClickListener {
    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected int iconWidth;
    protected int iconHeight;

    /**
     * 应用图标
     */
    protected ImageView mIcon;
    /**
     * 应用名称
     */
    protected TextView mAppName;
    /**
     * 快捷方式信息
     */
    protected AppInfo info;

    public AppInfo getInfo() {
        return info;
    }

    public void setInfo(AppInfo info) {
        this.info = info;
    }

    protected Context mContext;

    protected ShortcutIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    protected ShortcutIcon(Context context) {
        super(context);
        mContext = context;
        init();
    }

    protected ShortcutIcon(Context context, AppInfo info) {
        super(context);
        mContext = context;
        this.info = info;
        init();
    }

    /**
     * 设置是否选中
     *
     * @param value
     */
    public abstract void setSelect(boolean value);

    protected void init() {
        this.setOrientation(LinearLayout.VERTICAL);
        this.setOnClickListener(this);
        this.setGravity(Gravity.CENTER);
    }

    @Override
    public void onClick(View v) {
        if (info.intent != null) {
            Log.i("ShortcutIcon", "info.intent :" + info.intent + ", pName :" + info.packageName + ",aName :" + info.name);
//			info.intent.setClassName(info.packageName, info.name);
            mContext.startActivity(info.intent);
            return;
        } else {
            Toast.makeText(mContext, R.string.start_app_error,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public String getActivityName(String packName) throws Exception {
        final PackageInfo packInfo = mContext.getPackageManager()
                .getPackageInfo(packName, PackageManager.GET_ACTIVITIES);
        final ActivityInfo[] activitys = packInfo.activities;
        if (null == activitys || activitys.length <= 0) {
            return null;
        }
        return activitys[0].name;
    }

    public void reSetIcon(AppInfo info, int id) {
        this.info = info;
        setId(id);
        setSelect(false);
    }

    ;
}
