package com.lx.phonerecycle.helper;

import android.content.Context;
import com.lx.phonerecycle.gsonbean.N2A3_UserLogin;
import com.lx.phonerecycle.tools.Tools;

import java.io.File;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午12:38 <br>
 * description:
 */

public class LoginInfoHelper {

    private static final String LOGIN_INFO_BASE_PATH = "/login/N2A3_UserLogin.txt";

    private static String path;

    public static void init(Context context) {
        path = context.getCacheDir() + LOGIN_INFO_BASE_PATH;
    }

    public static void save(N2A3_UserLogin loginInfo) {
        Tools.saveObjToFile(path, loginInfo);
    }

    public static N2A3_UserLogin read() {
        return (N2A3_UserLogin) Tools.readObjFromFile(path);
    }

    public static void clear() {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void update(N2A3_UserLogin loginInfo) {
        clear();
        save(loginInfo);
    }

    public static boolean isRedemptionMode() {
        N2A3_UserLogin loginResult = read();
        if (loginResult == null) {
            return false;
        }
        if (loginResult.power.equals("1")) {
            return false;
        }
        return true;
    }

}
