package com.lx.testandroid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import bak.bluetooth.AnkerBoxBluetoothManager;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v13.app.ActivityCompat;

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
    }

}
