package com.atway.webshell;

import android.app.Application;
import com.atway.webshell.utils.Xlog;

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        Xlog.d(TAG, "App onCreate");
        mApp = this;
        SCREEN_WIDTH = getResources().getDisplayMetrics().widthPixels;
        SCREEN_HEIGHT = getResources().getDisplayMetrics().heightPixels;
    }

    public static App getInstance() {
        return mApp;
    }

}
