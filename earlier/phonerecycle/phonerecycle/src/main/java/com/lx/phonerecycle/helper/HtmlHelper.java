package com.lx.phonerecycle.helper;

import android.content.Context;
import org.apache.http.util.EncodingUtils;

import java.io.*;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午12:38 <br>
 * description:
 */

public class HtmlHelper {

    private static String path;

    public static void init(Context context) {
        path = context.getCacheDir() + "/html/tmp.html";
    }

    public static void clear() {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void write(String htmlStr) {
        clear();
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file, true);
            byte[] bytes = htmlStr.getBytes("UTF-8");
            fos.write(bytes);
            fos.getFD().sync();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String read() {
        String res = "";
        File file = new File(path);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            int length = fis.available();
            byte[] buffer = new byte[length];
            fis.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

}
