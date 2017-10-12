package com.atway.webshell;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.atway.webshell.utils.Xlog;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.<br>
 * User: luo.xuan<br>
 * Date: 2016/7/1<br>
 * Time: 21:04<br>
 */

public class WebReceiverActivity extends BaseActivity {

    private static final String TAG = WebReceiverActivity.class.getSimpleName();

    public static final String DELIVER_DATA = "deliver_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = new View(this);
        view.setBackgroundColor(0x00000000);
        setContentView(view);
        overridePendingTransition(0, 0);
        JSONObject jsonObject = new JSONObject();
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                Set<String> keys = bundle.keySet();
                for (String key : keys) {
                    String value = bundle.getString(key, "");
                    try {
                        jsonObject.put(key, value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Xlog.d(TAG, "bundle, key=%s, value=%s", key, value);
                }
            }
        }
        Intent intent = new Intent();
        intent.setAction(DELIVER_DATA);
        intent.putExtra("jsonData", jsonObject.toString());
        sendBroadcast(intent);
        finish();
    }

}
