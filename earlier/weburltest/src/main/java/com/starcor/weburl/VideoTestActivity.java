package com.starcor.weburl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import java.util.Set;

/**
 * Copyright (C) 2015 北京视达科科技有限公司 <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  15-9-11 下午2:23 <br>
 * description:
 */

public class VideoTestActivity extends Activity {

    private static final String TAG = VideoTestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Extra= " + buildIntentExtraToString(getIntent()));
        String url = "http://www.baidu.com";

        Uri data = getIntent().getData();
        if (data != null) {
            url = data.toString();
            Log.d(TAG, "data=" + data);
        }
        setContentView(R.layout.activity_main);
        WebView webView = (WebView) findViewById(R.id.my_web);
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
    }

    public static String buildIntentExtraToString(Intent intent) {// 打印广播中接收到的数据
        if(intent==null){
            return "intent is null";
        }
        Bundle bd = intent.getExtras();
        if(bd==null){
            return "bundle is null";
        }
        Set<String> names = bd.keySet();
        if(names==null){
            return "bundle.keySet is null";
        }
        Object[] nameArr = names.toArray();
        String str="";
        for (int i = 0; i < nameArr.length; i++) {
            str=str+( nameArr[i]+"="+bd.get(nameArr[i]+""))+"---";
        }
        return str;
    }

}
