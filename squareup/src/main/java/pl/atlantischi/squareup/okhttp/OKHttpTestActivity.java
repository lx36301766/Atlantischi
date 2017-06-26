package pl.atlantischi.squareup.okhttp;

import java.io.Closeable;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created on 2017/3/17.
 *
 * @author lx
 */

public class OKHttpTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private String okHttpGet(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                //
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
