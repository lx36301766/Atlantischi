package com.atway.webshell;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.android.volley.VolleyError;
import com.atway.webshell.domain.DoaminGetMainUrls;
import com.atway.webshell.manager.ApiManager;

import static com.atway.webshell.manager.ApiManager.HOST;

public class TestActivity extends BaseActivity {

    private static final String TAG = TestActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ApiManager.getInstance().apiGetMainUrl(new ApiManager.ApiCallback<DoaminGetMainUrls>() {
            @Override
            public void onSuccess(DoaminGetMainUrls result) {

                ApiManager.getInstance().apiGetAppImage(null);
                ApiManager.getInstance().apiCheckAppUpdate("1", null);
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 1000);
    }

    private void start() {
        Intent intent = new Intent();
        intent.setClass(this, AtwayWebActivity.class);
        intent.putExtra("url", HOST + "atway/webview/test7.html");
        startActivity(intent);
    }

    public void click(View v) {
        start();
    }
}
