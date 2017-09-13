package com.lx.testandroid.common;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Created on 11/09/2017.
 *
 * @author lx
 */

public class ShortcutReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && !TextUtils.isEmpty(intent.getAction())) {
            String action = intent.getAction();
            ComponentName componentName = intent.getComponent();
            System.out.println(componentName.getClassName());
        }
    }

}
