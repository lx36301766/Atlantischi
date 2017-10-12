package com.atway.webshell.manager;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.atway.webshell.App;

public class AppDataManager {

    private static AppDataManager instance;

    private AppDataManager() {
    }

    public static AppDataManager getInstance() {
        if (instance == null) {
            instance = new AppDataManager();
        }
        return instance;
    }

    public int getAppVersionCode() {
        PackageManager manager = App.getInstance().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(App.getInstance().getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 100;
    }

    public String getAppVersionName() {
        PackageManager manager = App.getInstance().getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(App.getInstance().getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

}
