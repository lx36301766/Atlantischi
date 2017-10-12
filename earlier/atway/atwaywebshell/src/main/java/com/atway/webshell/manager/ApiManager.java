package com.atway.webshell.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.LruCache;
import android.widget.ImageView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.atway.webshell.App;
import com.atway.webshell.domain.DoaminGetMainUrls;
import com.atway.webshell.domain.DomainCheckAppUpdate;
import com.atway.webshell.domain.DomainGetAppImage;
import com.atway.webshell.utils.GsonRequest;
import com.atway.webshell.utils.Xlog;

public class ApiManager {

    public interface ApiCallback<T> {
        void onSuccess(T result);
        void onError(VolleyError volleyError);
    }

    private static final String TAG = ApiManager.class.getSimpleName();

    private static ApiManager instance;

    private Context mContext;

    protected RequestQueue mRequestQueue;
    protected ImageLoader mImageLoader;

    public static final String HOST = "http://124.232.135.207:8089/";

    public static ApiManager getInstance() {
        if (instance == null) {
            instance = new ApiManager();
        }
        return instance;
    }

    private ApiManager() {
        mContext = App.getInstance();
        mRequestQueue = Volley.newRequestQueue(mContext);
        mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache(4));
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

    public void showImageView(final ImageView imageView, final String url, final ApiCallback<ImageLoader.ImageContainer> callback) {
        Xlog.d(TAG, "showImageView, url=" + url);
        mImageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Xlog.d(TAG, "showImageView response, bitmap=%s, isImmediate=%s, imageView=%s", response.getBitmap(), isImmediate, imageView);
                Bitmap bmp = response.getBitmap();
                if (bmp != null) {
                    Xlog.d(TAG, "bmp.getHeight=%s, bmp.getWidth=%s", bmp.getHeight(), bmp.getWidth());
                    if (imageView != null) {
                        imageView.setImageBitmap(bmp);
                    }
                    if (callback != null) {
                        callback.onSuccess(response);
                    }
                } else if (!isImmediate){
                    if (callback != null) {
                        callback.onError(null);
                    }
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Xlog.d(TAG, "showImageView error, " + error);
                if (callback != null) {
                    callback.onError(error);
                }
            }
        }, App.SCREEN_WIDTH, App.SCREEN_HEIGHT);
    }

    public void showImageView(final ImageView imageView, String url) {
        showImageView(imageView, url, null);
    }

    private static final String DEFAULT_MAIN_URL = HOST + "zn/a1.action";
    private String mainUrl = DEFAULT_MAIN_URL;

    private DoaminGetMainUrls doaminGetMainUrls;

    public void setDoaminGetMainUrls(DoaminGetMainUrls doaminGetMainUrls) {
        this.doaminGetMainUrls = doaminGetMainUrls;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public DoaminGetMainUrls getDoaminGetMainUrls() {
        return doaminGetMainUrls;
    }

    public void apiGetMainUrl(final ApiCallback<DoaminGetMainUrls> callback) {
        if (TextUtils.isEmpty(mainUrl) || Uri.parse(mainUrl) == null || Uri.parse(mainUrl).getHost() == null) {
            Xlog.e(TAG, "getMainUrls, errorMainUrl--->%s, Use default--->%s", mainUrl, DEFAULT_MAIN_URL);
            mainUrl = DEFAULT_MAIN_URL;
        }
        Xlog.d(TAG, "apiGetMainUrl, url= " + mainUrl);
        GsonRequest request = new GsonRequest<DoaminGetMainUrls>(mainUrl, DoaminGetMainUrls.class,
                new Response.Listener<DoaminGetMainUrls>() {
                    @Override
                    public void onResponse(DoaminGetMainUrls response) {
                        Xlog.d(TAG, "apiGetMainUrl onResponse success, " + response);
                        doaminGetMainUrls = response;
                        if (callback != null) {
                            callback.onSuccess(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Xlog.e(TAG, "apiGetMainUrl onResponse error, " + volleyError);
                        if (callback != null) {
                            callback.onError(volleyError);
                        }
                    }
                });
        mRequestQueue.add(request);
    }

    public void apiGetAppImage(final ApiCallback<DomainGetAppImage> callback) {
        String url = "";
        if (doaminGetMainUrls != null && doaminGetMainUrls.urls != null) {
            url = doaminGetMainUrls.urls.a2;
        } else {
            Xlog.e(TAG, "main url not run, or run failed!");
        }
        Xlog.d(TAG, "apiGetAppImage, url= " + url);
        GsonRequest request = new GsonRequest<DomainGetAppImage>(url, DomainGetAppImage.class,
                new Response.Listener<DomainGetAppImage>() {
                    @Override
                    public void onResponse(DomainGetAppImage response) {
                        Xlog.d(TAG, "apiGetAppImage onResponse success, " + response);
                        if (callback != null) {
                            callback.onSuccess(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Xlog.e(TAG, "apiGetAppImage onResponse error, " + volleyError);
                        if (callback != null) {
                            callback.onError(volleyError);
                        }
                    }
                }
        );
        mRequestQueue.add(request);
    }


    public void apiCheckAppUpdate(String version, final ApiCallback<DomainCheckAppUpdate> callback) {
        String url = "";
        if (doaminGetMainUrls != null && doaminGetMainUrls.urls != null) {
            url = doaminGetMainUrls.urls.a3 + "?version=" + version;
        } else {
            Xlog.e(TAG, "main url not run, or run failed!");
        }
        Xlog.d(TAG, "apiCheckAppUpdate, url= " + url);
        GsonRequest request = new GsonRequest<DomainCheckAppUpdate>(url, DomainCheckAppUpdate.class,
                new Response.Listener<DomainCheckAppUpdate>() {
                    @Override
                    public void onResponse(DomainCheckAppUpdate response) {
                        Xlog.d(TAG, "apiCheckAppUpdate onResponse success, " + response);
                        if (callback != null) {
                            callback.onSuccess(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Xlog.e(TAG, "apiCheckAppUpdate onResponse error, " + volleyError);
                        if (callback != null) {
                            callback.onError(volleyError);
                        }
                    }
                });
        mRequestQueue.add(request);
    }

}
