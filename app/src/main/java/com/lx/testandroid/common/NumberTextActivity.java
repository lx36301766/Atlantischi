package com.lx.testandroid.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lx.testandroid.R;
import com.lx.testandroid.testviews.PayCenterLeaveDialog;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by xuanluo on 2016/12/30.
 */

public class NumberTextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.number);

        NumberTextView tv = (NumberTextView) findViewById(R.id.num);
        tv.setNum(0, 48546);

        String name = getComponentName().getShortClassName();
        Log.d("lx", "name=" + name);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateFormat(this);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        TextView tvDate = (TextView) findViewById(R.id.date_text);
        tvDate.setText(java.text.DateFormat.getDateInstance().format(new Date()));

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PackageManager pm = getPackageManager();
                pm.setComponentEnabledSetting(getComponentName(),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                pm.setComponentEnabledSetting(new ComponentName(NumberTextActivity.this,
                                "com.lx.testandroid.MainAliasActivity"),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        List<ResolveInfo> resolves = pm.queryIntentActivities(intent, 0);
                        for (ResolveInfo res : resolves) {
                            if (res.activityInfo != null) {
                                ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                                am.killBackgroundProcesses(res.activityInfo.packageName);
                            }
                        }
                    }
                }, 1000);
            }
        });
    }

}
