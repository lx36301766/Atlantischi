package com.atway.webshell.utils;

import android.util.Log;

public class Xlog {

    private static int LOG_LEVEL = 6;
    private static int ERROR = 1;
    private static int WARN = 2;
    private static int INFO = 3;
    private static int DEBUG = 4;
    private static int VERBOS = 5;

    private final static int logLengthLimit = 4000;

    public static void i(String tag, String msg) {
        if (msg != null) {
            while (msg.length() > logLengthLimit) {
                String tmp = msg.substring(0, logLengthLimit);
                i(tag, tmp, (Throwable) null);
                msg = msg.substring(logLengthLimit, msg.length());
            }
        }
        i(tag, msg, (Throwable) null);
    }

    public static void e(String tag, String msg) {
        if (msg != null) {
            while (msg.length() > logLengthLimit) {
                String tmp = msg.substring(0, logLengthLimit);
                e(tag, tmp, (Throwable) null);
                msg = msg.substring(logLengthLimit, msg.length());
            }
        }
        e(tag, msg, (Throwable) null);
    }

    public static void v(String tag, String msg) {
        if (msg != null) {
            while (msg.length() > logLengthLimit) {
                String tmp = msg.substring(0, logLengthLimit);
                v(tag, tmp, (Throwable) null);
                msg = msg.substring(logLengthLimit, msg.length());
            }
        }
        v(tag, msg, (Throwable) null);
    }

    public static void d(String tag, String msg) {
        if (msg != null) {
            while (msg.length() > logLengthLimit) {
                String tmp = msg.substring(0, logLengthLimit);
                d(tag, tmp, (Throwable) null);
                msg = msg.substring(logLengthLimit, msg.length());
            }
        }
        d(tag, msg, (Throwable) null);
    }

    public static void w(String tag, String msg) {
        if (msg != null) {
            while (msg.length() > logLengthLimit) {
                String tmp = msg.substring(0, logLengthLimit);
                w(tag, tmp, (Throwable) null);
                msg = msg.substring(logLengthLimit, msg.length());
            }
        }
        w(tag, msg, (Throwable) null);
    }


    public static void i(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL > INFO) {
            if (tr == null) {
                Log.i(tag, msg);
            } else {
                Log.i(tag, msg, tr);
            }
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL > ERROR) {
            if (tr == null) {
                Log.e(tag, msg);
            } else {
                Log.e(tag, msg, tr);
            }
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL > VERBOS) {
            if (tr == null) {
                Log.v(tag, msg);
            } else {
                Log.v(tag, msg, tr);
            }
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL > DEBUG) {
            if (tr == null) {
                Log.d(tag, msg);
            } else {
                Log.d(tag, msg, tr);
            }
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL > WARN) {
            if (tr == null) {
                Log.w(tag, msg);
            } else {
                Log.w(tag, msg, tr);
            }
        }
    }

    public static void d(String tag, String format, Object... args) {
        if (LOG_LEVEL > DEBUG) {
            Log.d(tag, String.format(format, args));
        }
    }

    public static void i(String tag, String format, Object... args) {
        if (LOG_LEVEL > INFO) {
            Log.i(tag, String.format(format, args));
        }
    }

    public static void v(String tag, String format, Object... args) {
        if (LOG_LEVEL > VERBOS) {
            Log.v(tag, String.format(format, args));
        }
    }

    public static void w(String tag, String format, Object... args) {
        if (LOG_LEVEL > WARN) {
            Log.w(tag, String.format(format, args));
        }
    }

    public static void e(String tag, String format, Object... args) {
        if (LOG_LEVEL > ERROR) {
            Log.e(tag, String.format(format, args));
        }
    }

}
