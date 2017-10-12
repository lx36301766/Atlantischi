package com.lx.phonerecycle;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.lx.phonerecycle.fragment.LoadingDialogFragment;
import com.lx.phonerecycle.helper.ActivityHelper;
import com.lx.phonerecycle.helper.CustomToast;
import com.lx.phonerecycle.request.ResponseStatus;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 上午12:14 <br>
 * description:
 */

public abstract class BaseActivity extends RoboFragmentActivity implements ResponseStatus {

    protected String TAG = "BaseActivity";
    protected Activity mActivity;
    protected RequestQueue requestQueue;
    protected ImageLoader imageLoader;

    protected LoadingDialogFragment loadingDialogFragment;
    protected int screenWidth;
    protected int screenHeight;

    protected Handler mHandler = new Handler();

    @InjectView(R.id.back_btn)      protected ImageView backBtn;
    @InjectView(R.id.title_name)    protected TextView titleNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        TAG = getClass().getSimpleName();
        loadingDialogFragment = new LoadingDialogFragment();
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        requestQueue = Volley.newRequestQueue(this);
        imageLoader = new ImageLoader(requestQueue, new BitmapCache(1));
        ActivityHelper.getInstance().addActivity(this);
        if (backBtn != null) {
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    protected void setTitleName(CharSequence name) {
        if (titleNameText != null) {
            titleNameText.setText(name);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityHelper.getInstance().removeActivity(this);
    }

    public void showToastHint(CharSequence hint) {
        CustomToast.show(mActivity, hint);
    }

    public void showLoading() {
        loadingDialogFragment.show(getSupportFragmentManager(), "loading");
    }

    public void dismissLoading() {
        loadingDialogFragment.dismiss();
    }

    public void showImageView(ImageView imageView, String url) {
        showImageView(imageView, url, R.drawable.ic_launcher);
    }

    public void showImageView(ImageView imageView, String url, int defaultId) {
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defaultId, defaultId);
        imageLoader.get(url, listener);
    }

    public class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache(int cacheSize) {
            int maxSize = cacheSize * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            if(getBitmap(url) == null){
                mCache.put(url, bitmap);
            }
        }
    }

}
