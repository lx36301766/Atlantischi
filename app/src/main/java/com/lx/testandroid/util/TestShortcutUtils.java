package com.lx.testandroid.util;

import com.lx.testandroid.common.NumberTextActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created on 05/09/2017.
 *
 * @author lx
 */

public class TestShortcutUtils {

    public static final String ACTION_PLAY = "action_play";

    public static void delLaunchIcon(Context context) {
        Intent shortcutAction = new Intent(context, NumberTextActivity.class);
        shortcutAction.setAction(ACTION_PLAY);
//        ShortcutUtils.removeShortcut(context, shortcutAction, "ttt");
    }

    public static void addLaunchIcon(Context context) {
        Intent shortcutAction = new Intent(context, NumberTextActivity.class);
        shortcutAction.setAction(ACTION_PLAY);
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(0xFF808080); // gray
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);
        new Canvas(bitmap).drawText("" + 3, 50, 50, paint);
//        ShortcutUtils.addShortcut(context, shortcutAction, "ttt", false, bitmap);
    }


}
