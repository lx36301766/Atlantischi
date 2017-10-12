package com.lx.phonerecycle.tools;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 关于bitmap的处理，至少需要提供从路径到文件等函数
 *
 * @author hy
 *         2013/6/23
 */
public class BitmapTools {
    private static final String TAG = "BitmapTools";
    private static final Config defaultConfig = Config.ARGB_8888;
    private static ThreadLocal<byte[]> _local_buf;

    static {
        _local_buf = new ThreadLocal<byte[]>();
    }

    public static Bitmap decodeFile(String path) {
        return decodeFile(path, defaultConfig, 0, 0);
    }

    public static Bitmap decodeFile(String path, Config pixelFormat) {
        return decodeFile(path, pixelFormat, 0, 0);
    }

    /**
     * 解码图片,并转换图片像素格式及尺寸
     * 进行缩放时, 会保持原图片的宽高比.
     * 当水平方向与竖直方向上的缩放比例不同时.会选择其中较大的比例进行缩放.
     * 保持输出尺寸大于或等于目标尺寸.
     *
     * @param path        图片路径
     * @param pixelFormat 解码图片像素格式, null表示使用默认像素格式
     * @param outWidth    解码图片的目标宽度, 0表示使用默认宽度
     * @param outHeight   解码图片的目标高度, 0表示使用默认高度
     * @return
     */
    public static Bitmap decodeFile(String path, Config pixelFormat, int outWidth, int outHeight) {
        byte[] decode_buf = _local_buf.get();
        if (decode_buf == null) {
            decode_buf = new byte[16 * 1024];
            _local_buf.set(decode_buf);
        }
        XLog.i(TAG, "decodeFile path:" + path);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTempStorage = decode_buf;
        opts.inPreferredConfig = pixelFormat;
        BufferedInputStream bi;
        try {
            bi = new BufferedInputStream(new FileInputStream(path), 4 * 1024);
            if (outWidth > 0 && outHeight > 0) {
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(bi, null, opts);
                try {
                    bi.reset();
                } catch (IOException e) {
                    bi.close();
                    bi = new BufferedInputStream(new FileInputStream(path), 4 * 1024);
                }
                XLog.i(TAG, "	width " + opts.outWidth + " height " + opts.outHeight);
                opts.inScaled = true;
                opts.inTargetDensity = DisplayMetrics.DENSITY_DEFAULT;
                int scale_x = opts.inTargetDensity * opts.outWidth / outWidth;
                int scale_y = opts.inTargetDensity * opts.outHeight / outHeight;
                opts.inDensity = Math.min(scale_x, scale_y);
                // opts.inSampleSize = opts.inDensity / opts.inTargetDensity;
            }
            opts.inJustDecodeBounds = false;
            opts.inPurgeable = false;
            Bitmap bm = BitmapFactory.decodeStream(bi, null, opts);
            bi.close();
            if (bm != null) {
                bm.prepareToDraw();
            }
            return bm;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
