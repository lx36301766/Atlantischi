package com.lx.phonerecycle.tools;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;

public class UITools {

    private final static String TAG = "UITools";

    //设备高宽像素
    public static int SCREEN_WIDTH = 480;
    public static int SCREEN_HEIGHT = 800;
    //设计图高宽像素
    public static final int DESIGN_WIDTH = 480;
    public static final int DESIGN_HEIGHT = 800;
    public static float mainScale = 1.0f;

    public static void init(Context context) {
        SCREEN_WIDTH = context.getResources().getDisplayMetrics().widthPixels;
        SCREEN_HEIGHT = context.getResources().getDisplayMetrics().heightPixels;
        mainScale = Math.min(SCREEN_HEIGHT / DESIGN_HEIGHT, SCREEN_WIDTH / DESIGN_WIDTH);
    }

    public static int XH(int h) {
        return (int) (SCREEN_HEIGHT * (h * 1.0f / DESIGN_HEIGHT) + 0.5f);
    }

    public static int XW(int w) {
        return XH(w);
    }

    /**
     * 最近一次浮出框的时间
     */
    private static long lastTosatTime = 0;

    /**
     * 设置view的大小 - [自动换算大小比例]
     *
     * @param width  width
     * @param height height
     * @Description: 设置一个View的宽高
     */
    public static void setViewSize(View view, int width, int height) {
        if (view == null) {
            return;
        }
        view.getLayoutParams().width = XW(width);
        view.getLayoutParams().height = XH(height);
    }

    /**
     * 设置textView大小以及字体大小 - [自动换算大小比例]
     *
     * @param view
     * @param width
     * @param height
     * @param textSize
     */

    public static void setTextViewSize(TextView view, int width, int height, int textSize) {
        if (view == null) {
            return;
        }
        view.getLayoutParams().width = XH(width);
        view.getLayoutParams().height = XH(height);
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, XH(textSize));
    }

    /**
     * 设置textView字体大小 - [自动换算大小比例]
     *
     * @param view
     * @param textSize
     */
    public static void setTextViewSize(TextView view, int textSize) {
        if (view == null) {
            return;
        }
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, XH(textSize));
    }

    /**
     * 设置view的margin值 - [需提前将比例换算好]
     *
     * @param view
     * @param leftMargin
     * @param topMargin
     * @param rightMargin
     * @param bottomMargin
     */
    public static void setViewMargin(View view, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        if (view == null) {
            return;
        }
        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        params.leftMargin = leftMargin;
        params.topMargin = topMargin;
        params.rightMargin = rightMargin;
        params.bottomMargin = bottomMargin;
    }


}
