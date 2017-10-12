package com.starcor.vds.demo;

import android.app.Application;
import android.content.Context;

/**
 * Created by jing on 2015/4/15.
 */
public class App extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
