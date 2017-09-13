package com.lx.testandroid;

import com.lx.testandroid.log.utils.KLog;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.root_main);

        listAllActivities(linearLayout);
    }

    private void addText(ViewGroup root, final Class<?> clazz) {
        TextView tv = new Button(this);
        tv.setPadding(0, 10, 0, 10);//
//        tv.setBackgroundColor(Color.RED);
        tv.setGravity(Gravity.CENTER);
        tv.setText(clazz.getSimpleName());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, clazz);
                startActivity(intent);
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        root.addView(tv, lp);
    }


    private void listAllActivities(ViewGroup root) {
        ActivityInfo[] list = new ActivityInfo[0];
        try {
            list = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES).activities;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        for (ActivityInfo activityInfo : list) {
            KLog.d(TAG, "ActivityInfo = " + activityInfo.name);
            try {
                addText(root, Class.forName(activityInfo.name));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        System.out.println();
    }

}
