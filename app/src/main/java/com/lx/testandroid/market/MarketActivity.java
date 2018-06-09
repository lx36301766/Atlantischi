package com.lx.testandroid.market;

import com.lx.testandroid.R;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created on 28/04/2017.
 *
 * @author lx
 */

public class MarketActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_market);
        Button button = findViewById(R.id.goto_market);
        button.setOnClickListener(v -> {
            String pkg = "com.jm.android.jumei";
//                goToMarket(MarketActivity.this, pkg);
            goToSamsungappsMarket(MarketActivity.this, pkg);
//                goToLeTVStoreDetail(MarketActivity.this, pkg);
            //adb shell am start -a android.intent.action.VIEW -d market://details?id=com.jm.android.jumei
        });
    }

    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void goToSamsungappsMarket(Context context, String packageName) {
        Uri uri = Uri.parse("http://www.samsungapps.com/appquery/appDetail.as?appId=" + packageName);
        Intent goToMarket = new Intent();
        goToMarket.setClassName("com.sec.android.app.samsungapps", "com.sec.android.app.samsungapps.Main");
        goToMarket.setData(uri);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

}
