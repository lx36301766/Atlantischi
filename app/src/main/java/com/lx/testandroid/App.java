package com.lx.testandroid;

import com.ankerboxmanager.bluetooth.AnkerBoxBluetoothManager;

import android.app.Application;
import android.content.Context;

/**
 * Created on 22/03/2017.
 *
 * @author lx
 */

public class App extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        AnkerBoxBluetoothManager.Companion.getInstance().init(this);
    }
}
