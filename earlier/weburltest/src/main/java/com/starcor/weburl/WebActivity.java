package com.starcor.weburl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

import java.util.Set;

/**
 * am start -n com.starcor.weburl/.WebActivity -d ""
 */

public class WebActivity extends Activity {

    private static final String TAG = WebActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "Extra= "+buildIntentExtraToString(getIntent()));
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

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent();
//                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                i.setAction("com.tencent.qqlivetv.open");
//                i.putExtra("cp_data", "tenvideo2://?action=1&cover_id=3o56brnhunm5bwx");
//                i.setData(Uri.parse("tenvideo2://?action=1&cover_id=7ap84xw7qx1h39c"));
//                startActivity(i);
//            }
//        }, 3000);
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
