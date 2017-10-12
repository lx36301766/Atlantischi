package com.atway.webshell;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.atway.webshell.domain.DoaminGetMainUrls;
import com.atway.webshell.domain.DomainGetAppImage;
import com.atway.webshell.manager.ApiManager;
import com.atway.webshell.utils.ToastUtils;
import com.atway.webshell.utils.Xlog;
import org.json.JSONException;
import org.json.JSONObject;

import static com.atway.webshell.manager.ApiManager.HOST;

/**
 * adb shell am start -n com.atway.webshell/.SplashActivity -d "http://116.212.115.74:8090//main.php"
 */
public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final String DEFAULT_MAIN_PAGE_URL = HOST + "atway/webview/9.html";

    private ImageView iv;
    private int currentShowIndex = 0;
    private Handler mHandler = new Handler();

    public static final boolean DEBUG = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        iv = (ImageView) findViewById(R.id.my_iv);
        if (DEBUG) {
            ApiManager.getInstance().setMainUrl("http://172.20.16.85/test/get_main_urls/v8");
        } else {
            Uri data = getIntent().getData();
            if (data != null) {
                String url = data.toString();
                ApiManager.getInstance().setMainUrl(url);
                Log.d(TAG, "apiMainUrl=" + url);
            }
        }
        ApiManager.getInstance().apiGetMainUrl(new ApiManager.ApiCallback<DoaminGetMainUrls>() {
            @Override
            public void onSuccess(DoaminGetMainUrls result) {
                ApiManager.getInstance().apiGetAppImage(new ApiManager.ApiCallback<DomainGetAppImage>() {
                    @Override
                    public void onSuccess(final DomainGetAppImage result) {
                        if (result != null && result.image_info != null && result.image_info.urls.size() > 0) {
                            if (DEBUG) {
                                //preLoadImage(result);
                                //loopShowImage(result.image_info);
                                startNext();
                                //testPlayer();
                            } else {
                                preLoadImage(result);
                                loopShowImage(result.image_info);
                            }
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        ToastUtils.showToast("当前网络不佳，请稍候再试");
                        finish();
                    }
                });
            }

            @Override
            public void onError(VolleyError volleyError) {
                ToastUtils.showToast("当前网络不佳，请稍候再试");
                finish();
            }
        });
    }

    private void preLoadImage(DomainGetAppImage result) {
        for (String url : result.image_info.urls) {
            ApiManager.getInstance().showImageView(null, url);
        }
    }

    private void loopShowImage(final DomainGetAppImage.ImageInfo imageInfo) {
        Xlog.e(TAG, "loopShowImage, currentShowIndex=%s", currentShowIndex);
        if (imageInfo == null || imageInfo.urls == null) {
            Xlog.e(TAG, "loopShowImage, mImageInfo == null || mImageInfo.urls == null");
            return;
        }
        if (imageInfo.urls.size() > currentShowIndex) {
            int time = 3;
            try {
                time = Integer.parseInt(imageInfo.display_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            time = time * 1000;
            final int t = time;
            ApiManager.getInstance().showImageView(iv, imageInfo.urls.get(currentShowIndex), new ApiManager.ApiCallback<ImageLoader.ImageContainer>() {
                @Override
                public void onSuccess(ImageLoader.ImageContainer result) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loopShowImage(imageInfo);
                        }
                    }, t);
                }

                @Override
                public void onError(VolleyError volleyError) {
                    ToastUtils.showToast("当前网络不佳，请稍候再试");
                }
            });
            currentShowIndex++;
        } else {
            startNext();
        }
    }

    private void startNext() {
        String url;
        DoaminGetMainUrls doaminGetMainUrls = ApiManager.getInstance().getDoaminGetMainUrls();
        if (doaminGetMainUrls != null && !TextUtils.isEmpty(doaminGetMainUrls.main_page_url)) {
            url = doaminGetMainUrls.main_page_url;
        } else {
            url = DEFAULT_MAIN_PAGE_URL;
        }
        Intent intent = new Intent();
        intent.setClass(this, AtwayWebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void testPlayer() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", "id_123");
            jsonObject.put("title", "万万没想到");
//            jsonObject.put("url", "http://192.168.95.89:5000/nn_vod/nn_x64/aWQ9NmQzM2FjNGI1ZGExN2VhNjZjMDNlZmVkMTMwMWE0YTYmdXJsX2MxPTZhNjM3OTc0MmU3NDczMjAwMCZudHRsPTMmbnBpcHM9MTkyLjE2OC45NS44OTo1MTAwJm5jbXNpZD0xMDAwJm5ncz01NjcyZDI5YTAwMDM0MmNkNDg0YmYyMjVmOTI2NzI1ZCZubl91c2VyX2lkPWFoZ3hfZGV2aWNlXzEmbmR0PXN0YiZuZHY9MS4wLjE1LjEuMi5TQy1BSEdYLVNUQi4wLjBfQmV0YQ,,/6d33ac4b5da17ea66c03efed1301a4a6.ts");
            jsonObject.put("url", "http://119.39.49.19:8089/001/2/00100062973100000000000000000000?virtualDomain=001.vod_hls.zte.com");
            jsonObject.put("time", 500);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        intent.setClass(mContext, PlayerActivity.class);
        intent.putExtra("jsonData", jsonObject.toString());
        startActivity(intent);
        finish();
    }

}
