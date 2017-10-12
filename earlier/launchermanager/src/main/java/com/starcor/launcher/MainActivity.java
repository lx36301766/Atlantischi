package com.starcor.launcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.starcorcn.launcher.R;

public class MainActivity extends Activity implements OnClickListener {
    public static final String TAG = "App";
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static float SCALE;
    public static final int DESIGN_WIDTH = 1280;
    public static final int DESIGN_HEIGHT = 720;
    public static float DENSITY;
    private Intent intent;

    public static int OperationHeight(int Original) {
        return (int) (SCREEN_HEIGHT * (Original * 1.0f / DESIGN_HEIGHT) + 0.5f);
    }

    public static int OperationWidth(int Original) {
        return (int) (SCREEN_WIDTH * (Original * 1.0f / DESIGN_WIDTH) + 0.5f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;
        DENSITY = getResources().getDisplayMetrics().density;
        SCALE = getResources().getDimension(R.dimen.matching_value) / 100f;
        initView();
    }

    private void initView() {

        TextView b0 = (TextView) findViewById(R.id.hunan_ott);
        b0.setOnClickListener(this);
        TextView b1 = (TextView) findViewById(R.id.system_setting);
        b1.setOnClickListener(this);
        TextView b3 = (TextView) findViewById(R.id.app_install);
        b3.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.app_install:
                intent = new Intent();
                intent.setClass(MainActivity.this, AppsActivity.class);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                    Toast.makeText(getApplicationContext(), "没有安装此应用",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.system_setting:
                intent = new Intent();
                intent.setAction(android.provider.Settings.ACTION_SETTINGS);
                try {
                    Log.d(TAG, "intent action = " + intent.getAction());
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                    Toast.makeText(getApplicationContext(), "没有安装此应用",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.hunan_ott:
                final String packName = "com.starcor.hunan";
                intent = MainActivity.this.getPackageManager()
                        .getLaunchIntentForPackage(packName);
                try {
                    Intent intent = new Intent("cn.gfamily.morefun.act.ActEntrance");
                    startActivity(intent);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                    Toast.makeText(getApplicationContext(), "没有安装此应用",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

}
