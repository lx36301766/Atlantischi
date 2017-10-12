package com.atway.webshell;

import org.json.JSONObject;

import com.atway.webshell.manager.UpdateManager;
import com.atway.webshell.utils.ToastUtils;
import com.atway.webshell.utils.Xlog;
import com.atway.webshell.widget.AtwayWebView;
import com.atway.webshell.widget.VideoPlayerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

@SuppressLint("SetJavaScriptEnabled")
public class AtwayWebActivity extends BaseActivity {

    public static final int CODE_REQ_PLAYER = 882;

    private static final String TAG = AtwayWebActivity.class.getSimpleName();
    private static final int MESSAGE_DISMISS_DIALOG = 1000;
    private AtwayWebView mWeb;
    private boolean drawLoadingBg = true;
    private String mUrl;
    private VideoPlayerView mVideoPlayerView;
    private String webPlayCallback;
    private String fullscreenPlayCallback;
    private boolean isLoop;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Xlog.d(TAG, "handleMessage:" + msg.what);
            if (msg.what == MESSAGE_DISMISS_DIALOG) {
                drawLoadingBg = false;
                if (mWeb != null) {
                    mWeb.postInvalidate();
                }
            }
        }
    };

    private WebViewClient mClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Xlog.i(TAG, "onPageFinished, load url:" + url);
            mHandler.sendEmptyMessageDelayed(MESSAGE_DISMISS_DIALOG, 1000);
        }

        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Xlog.i(TAG, "onLoadResource, load url:" + url);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            super.shouldOverrideUrlLoading(view, url);
            Xlog.i(TAG, "shouldOverrideUrlLoading overload=" + url);
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Xlog.e(TAG, "onReceivedError!");
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout root = new RelativeLayout(this);
        root.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        root.setBackgroundColor(Color.BLUE);
        setContentView(root);
        mUrl = getIntent().getStringExtra("url");
        // mUrl = "http://116.212.115.74:8090/zt/index.html";
        Xlog.d(TAG, "open url-->" + mUrl);
        addWebView(root);
        addVideoPlayer(root);
        checkUpdate();
    }

    private void addWebView(RelativeLayout root) {
        mWeb = new AtwayWebView(this) {

            @Override
            protected JsExtObject createExtObject() {
                return new JsExtObject() {

                    public void closeBrowser() {
                        Xlog.i(TAG, "onCloseBrowser");
                        finish();
                    }

                    public void openPlayerInWeb(JSONObject jsonObject) {
                        Xlog.d(TAG, "openPlayerInWeb, jsonObject=%s", jsonObject);
                        if (mVideoPlayerView == null) {
                            ToastUtils.showToast("视频内部错误");
                            return;
                        }
                        webPlayCallback = jsonObject.optString("callback_name");
                        isLoop = jsonObject.optBoolean("is_loop");
                        int startX = jsonObject.optInt("start_x");
                        int startY = jsonObject.optInt("start_y");
                        int width = jsonObject.optInt("width");
                        int height = jsonObject.optInt("height");
                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mVideoPlayerView.getLayoutParams();
                        lp.leftMargin = startX;
                        lp.topMargin = startY;
                        lp.width = width;
                        lp.height = height;
                        final String playUrl = jsonObject.optString("play_url");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mVideoPlayerView.setVisibility(View.VISIBLE);
                                mVideoPlayerView.setVideoPath(playUrl);
                            }
                        });
                    }

                    public void closePlayerInWeb() {
                        Xlog.d(TAG, "closePlayerInWeb");
                        webPlayCallback = null;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mVideoPlayerView != null) {
                                    mVideoPlayerView.stopPlayback();
                                    mVideoPlayerView.setVisibility(View.GONE);
                                }
                            }
                        });
                    }

                    @Override
                    public void startPlayer(JSONObject jsonObject) {
                        super.startPlayer(jsonObject);
                        fullscreenPlayCallback = jsonObject.optString("callback_name");
                    }
                };
            }

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (drawLoadingBg) {
                    canvas.drawColor(0xFF252525);
                }
            }
        };
        mWeb.clearFocus();
        mWeb.setFocusable(true);
        mWeb.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWeb.loadUrl(mUrl);
        mWeb.setWebViewClient(mClient);
        mWeb.getSettings().setJavaScriptEnabled(true);
        mWeb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        root.addView(mWeb);
    }

    private void addVideoPlayer(RelativeLayout root) {
        mVideoPlayerView = new VideoPlayerView(this);
        mVideoPlayerView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Xlog.d(TAG, "onPrepared");
                mVideoPlayerView.start();
                loadJsCallback(webPlayCallback, "prepared");
            }
        });
        mVideoPlayerView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Xlog.d(TAG, "onCompletion");
                loadJsCallback(webPlayCallback, "completion");
                if (isLoop) {
                    mVideoPlayerView.stopPlayback();
                    mVideoPlayerView.openVideo();
                }
            }
        });
        mVideoPlayerView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Xlog.d(TAG, "onError, what=%s, extra=%s", what, extra);
                loadJsCallback(webPlayCallback, "error");
                return false;
            }
        });
        mVideoPlayerView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Xlog.d(TAG, "onInfo, what=%s, extra=%s", what, extra);
                return false;
            }
        });
        mVideoPlayerView.setVisibility(View.GONE);
        root.addView(mVideoPlayerView);
    }

    private void loadJsCallback(String callbackName, String completion) {
        if (!TextUtils.isEmpty(callbackName)) {
            mWeb.loadJsCallback(callbackName, completion);
        }
    }

    private void checkUpdate() {
        final UpdateManager updateManager = new UpdateManager(this);
        updateManager.startCheckUpdate(new UpdateManager.UpdateCallback() {
            @Override
            public void onUpdate(String updateType) {
                Xlog.d(TAG, "onUpdate updateType: " + updateType);
                if (UpdateManager.UPDATE_TYPE_FORCE.equals(updateType)) {
                    if (mContext == null) {
                        Xlog.e(TAG, "onUpdate, mContext is null ");
                        return;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            AtwayWebActivity.this);
                    AlertDialog dialog = builder
                            .setTitle("升级提示")
                            .setIcon(R.drawable.dialog_icon)
                            .setMessage("检测到新版本，请立即升级！")
                            .setCancelable(false)
                            .setPositiveButton("是",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            updateManager.installApk();
                                            finish();
                                        }

                                    }).create();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            mWeb.requestFocus();
                        }
                    });
                } else if (UpdateManager.UPDATE_TYPE_MANUAL.equals(updateType)) {
                    if (mContext == null) {
                        Xlog.e(TAG, "onUpdate, mContext is null ");
                        return;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(
                            AtwayWebActivity.this);
                    AlertDialog dialog = builder
                            .setTitle("升级提示")
                            .setIcon(R.drawable.dialog_icon)
                            .setMessage("检测到新版本，是否立即升级？")
                            .setPositiveButton("是",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            updateManager.installApk();
                                        }
                                    })
                            .setNegativeButton("否",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                        }
                                    }).create();
                    dialog.show();
                } else {
                    Xlog.e(TAG, "error updateType: " + updateType);
                }
            }

            @Override
            public void updateError(String error) {
                Xlog.e(TAG, "updateError error: " + error);
            }
        });
    }

    private boolean handleKey(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            mWeb.loadUrl("javascript:keymove('" + keyCode + "')");
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            mWeb.loadUrl("javascript:keymove('" + event.getKeyCode() + "')");
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PlayerActivity.RESULT_CODE_PLAY_FINISH && data != null) {
            String status = data.getStringExtra(PlayerActivity.RESULT_PLAY_STATUS);
            Xlog.e(TAG, "onActivityResult, fullscreenPlayCallback=%s, status=%s", fullscreenPlayCallback, status);
            loadJsCallback(fullscreenPlayCallback, status);
            fullscreenPlayCallback = null;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Xlog.e(TAG, "onBackPressed");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mVideoPlayerView != null) {
            mVideoPlayerView.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mVideoPlayerView != null) {
            mVideoPlayerView.stopPlayback();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
