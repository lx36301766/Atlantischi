package com.starcor;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.starcor.vds.demo.App;

/**
 * 应与中间件调用API合并调用合并
 *
 * @author czy
 */

public class OTTTV {
    private static final String TAG = "OTTTV";
    public static final String port;

    static {
        try {
            System.loadLibrary("_All_imgoTV_nn_tv_client");
        } catch (Exception e) {
            e.printStackTrace();
        }
        port = "7777";
    }

    public static boolean Debug = false;
    private static boolean _isInitialized = false;

    public static int init(Context context) {
        if (_isInitialized) {
            return 0;
        }
        _isInitialized = true;
        int ret = 0;
        String path = "";
        if (haveSdcard()) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "nn_tv_log"
                    + File.separator;
        }
        File file = new File(path);
        if (!file.isDirectory()) {
            path = context.getDir("log", Context.MODE_PRIVATE).toString() + File.separator;
            Log.i(TAG, "logPath:" + path);
        }

        Log.i(TAG, "start logPath:" + path);

        ret = start(Integer.parseInt(OTTTV.port), 25, path, "mgtv_test", "");

        if (ret != 0)
            Log.e(TAG, "native start ret:" + ret);

        Log.i(TAG, "启动中间件：" + ret);
        return ret;
    }

    /**
     * native 导入
     *
     * @param port
     * @param pool_mb_size
     * @param path
     * @param factory
     * @param args
     * @return
     */
    public static native int start(int port, int pool_mb_size, String path, String factory, String args);

    public static int shutdown() {
        if (_isInitialized) {
            return stop();
        }

        return -1;
    }

    /**
     * native 导入
     *
     * @return
     */
    public static native int stop();

    /**
     * 检查是否有本地外接卡，调试用
     *
     * @return
     */
    public static boolean haveSdcard() {

        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                return true;
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
