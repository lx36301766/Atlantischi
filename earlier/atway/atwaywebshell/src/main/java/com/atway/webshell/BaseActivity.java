package com.atway.webshell;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.atway.webshell.utils.Xlog;

public class BaseActivity extends Activity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Xlog.d(TAG, "onCreate, this=" + this);
        mContext = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Xlog.d(TAG, "onStart, this=" + this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Xlog.d(TAG, "onRestart, this=" + this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Xlog.d(TAG, "onResume, this=" + this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Xlog.d(TAG, "onPause, this=" + this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Xlog.d(TAG, "onStop, this=" + this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Xlog.d(TAG, "onDestroy, this=" + this);
        mContext = null;
    }

}
