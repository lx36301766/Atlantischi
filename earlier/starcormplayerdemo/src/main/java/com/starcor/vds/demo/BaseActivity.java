package com.starcor.vds.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * Copyright (C) 2015 北京视达科科技有限公司 <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  15-12-11 下午2:57 <br>
 * description:
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Class<?> activityName = null;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                int enterAnim = R.anim.activity_in_left;
                int exitAnim = R.anim.activity_out_left;
                if (BaseActivity.this instanceof MainActivity) {
                    activityName = TestActivity1.class;
                } else if (BaseActivity.this instanceof TestActivity2) {
                    activityName = MainActivity.class;
                }
                startNextActivity(activityName, enterAnim, exitAnim);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                enterAnim = R.anim.activity_in_right;
                exitAnim = R.anim.activity_out_right;
                if (BaseActivity.this instanceof MainActivity) {
                    activityName = TestActivity2.class;
                } else if (BaseActivity.this instanceof TestActivity1) {
                    activityName = MainActivity.class;
                }
                startNextActivity(activityName, enterAnim, exitAnim);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void beforeStartNextActivity() {
    }

    private void startNextActivity(Class<?> activityName, int enterAnim, int exitAnim) {
        if (activityName == null) {
            return;
        }
        beforeStartNextActivity();
        final Intent intent = new Intent(this, activityName);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
        finish();
        overridePendingTransition(enterAnim, exitAnim);
    }
}
