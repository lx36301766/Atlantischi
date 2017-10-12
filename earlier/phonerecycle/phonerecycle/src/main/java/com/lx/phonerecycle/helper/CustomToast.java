package com.lx.phonerecycle.helper;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.lx.phonerecycle.R;
import com.lx.phonerecycle.tools.XLog;

/**
 * Copyright(C) 2014, lx <br>
 * All rights reserved. <br>
 * author:  xiang.huang <br>
 * date:  2014/7/2 10:22 <br>
 * description:
 */
public class CustomToast extends Toast {

    private static final String TAG = "CustomToast";

    private static long oneTime = 0;
    private static long twoTime = 0;
    private static CharSequence oldMsg;
    private static CustomToast customToast;

    public static void show(Context context, CharSequence msg) {
        if (context == null) {
            XLog.w(TAG, "context is null");
            return;
        }
        if (customToast == null) {
            customToast = new CustomToast(context, msg);
            customToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    customToast.show();
                }
            } else {
                oldMsg = msg;
                customToast.msgView.setText(msg);
                customToast.show();
            }
        }
        oneTime = twoTime;
    }

    TextView msgView;

    public CustomToast(Context context, CharSequence msg) {
        super(context);
        setDuration(Toast.LENGTH_SHORT);
        setGravity(Gravity.CENTER, 0, 0);
        View view = View.inflate(context, R.layout.custom_toast, null);
        msgView = (TextView) view.findViewById(R.id.toast_msg);
        msgView.setText(msg);
        setView(view);
    }

}
