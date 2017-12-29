package com.lx.testandroid.util;

import android.util.Log;

public final class JDLog {



    public static void v(String message) {
    }

    public static void v(String tag, String message, Object... args) {
    }

    public static void v(Throwable throwable) {
    }

    public static void v(String tag, Throwable throwable) {
    }

    public static void v(String tag, Throwable throwable, String message, Object... args) {
    }



    public static void d(String message) {
    }

    public static void d(String tag, String message, Object... args) {
    }

    public static void d(Throwable throwable) {
    }

    public static void d(String tag, Throwable throwable) {
    }

    public static void d(String tag, Throwable throwable, String message, Object... args) {
    }



    public static void i(String message) {
    }

    public static void i(String tag, String message, Object... args) {
    }

    public static void i(Throwable throwable) {
    }

    public static void i(String tag, Throwable throwable) {
    }

    public static void i(String tag, Throwable throwable, String message, Object... args) {
    }



    public static void w(String message) {
    }

    public static void w(String tag, String message, Object... args) {
    }

    public static void w(Throwable throwable) {
    }

    public static void w(String tag, Throwable throwable) {
    }

    public static void w(String tag, Throwable throwable, String message, Object... args) {
    }



    public static void e(String message) {
        e(null, message);
    }

    public static void e(String tag, String message, Object... args) {
        prepareStringLog(Log.ERROR, tag, message, args);
    }

    public static void e(Throwable throwable) {
        e(null, throwable);
    }

    public static void e(String tag, Throwable throwable) {
        e(tag, throwable, null);
    }

    public static void e(String tag, Throwable throwable, String message, Object... args) {
        prepareThrowableLog(Log.ERROR, tag, throwable, message, args);
    }





    private static void prepareStringLog(int priority, String tag, String message, Object... args) {
        Log.println(priority, tag, String.format(message, args));
    }

    private static void prepareThrowableLog(int priority, String tag, Throwable throwable, String message, Object... args) {
        Log.e(tag, String.format(message, args), throwable);
    }


}
