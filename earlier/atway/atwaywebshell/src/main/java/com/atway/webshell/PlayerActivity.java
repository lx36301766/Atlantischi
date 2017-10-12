package com.atway.webshell;

import org.json.JSONException;
import org.json.JSONObject;

import com.atway.webshell.utils.TimeUtils;
import com.atway.webshell.utils.ToastUtils;
import com.atway.webshell.utils.Xlog;
import com.atway.webshell.widget.VideoPlayerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlayerActivity extends BaseActivity {

    private static final String TAG = PlayerActivity.class.getSimpleName();

    public static final int RESULT_CODE_PLAY_FINISH = 100;

    public static final String RESULT_PLAY_STATUS = "status";

    public static final String RESULT_PLAY_COMPLETE = "fullscreen_play_completion";
    public static final String RESULT_PLAY_ERROR = "fullscreen_play_error";
    public static final String RESULT_PLAY_BACK = "fullscreen_play_back";

    private String mTitle;
    private int mTime;
    private String mPlayUrl = "http://116.77.76.4:5000/nn_live/nn_x64/aWQ9U1pXU0hEMi41TSZ1cmxfYzE9MjAwMCZubl9haz0wMWJjODA0YmUxOTU1MTY0NWJjNGMxYzJjZjg0M2FmZDI2Jm5waXBzPTExNi43Ny43MC4yMDU6NTEwMCZuY21zaWQ9MTAwMTAxMDMmbmdzPTU3NzE0MTYxMDAwYTEyOTk3NjQ4OGIzZTg4OTAzY2UwJm5uZD16/Z2R4LnNpY2h1YW4uY2hlbmdkdSZuYWg9MSZuczJjPTEmbmZ0PXRzMm0zdTgmbm5fdXNlcl9pZD11MGNjNjU1ZDFhNGQwJm5kdD0mbmR0PXN0YiZuZGk9MGNjNjU1ZDFhNGQwJm5kdj0xLjAuNzYuU1RCLldTWVBULlNURC5TQzAuUHJlX1JlbGVhc2UmbmNhPSUyNm5u/X2NwJTNkU1pUVyZuZWE9JTI2Y2RuZXhfaWQlM2RsaXZlX2dzJm5lcz02MjFlMTc0OTJlN2RiYTE0NWU0N2FiYmVlMjZjODM4YSZuYWw9MDE2MTQxNzE1NzA2MDdlZTE3NjQ3YzM0ZmQ4MTc4MjQ5OWQzNWNiNmJhZDU0Mg,,/SZWSHD2.5M.m3u8";

    private VideoPlayerView mVideoPlayerView;
    private ViewGroup mSeekLayout;
    private SeekBar mSeekBar;
    private TextView mCurTime;
    private TextView mTotalTime;
    private ImageView mPauseImage;
    private TextView mVideoTitleTv;

    private int currentPos;
    private int duration;

    private Handler mHandler = new Handler();

    private boolean startSeek;
    private static final int DEFAULT_STEP = 5;
    private static final int MAX_STEP = 100;
    private int step = DEFAULT_STEP;

    private long lastBackBtnTime;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        String jsonData = getIntent().getStringExtra("jsonData");
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String id = jsonObject.getString("id");
            mTitle = jsonObject.getString("title");
            mTime = jsonObject.getInt("time");
            mPlayUrl = jsonObject.getString("url");
            Xlog.d(TAG, "jsonData, id=%s, title=%s, time=%s, url=%s", id, mTitle, mTime, mPlayUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initVideo();
        initSeekLayout();
        mPauseImage = (ImageView) findViewById(R.id.play_state);
        mVideoTitleTv = (TextView) findViewById(R.id.title);
        mVideoTitleTv.setText(mTitle);
        startPlay();
    }

    private void initSeekLayout() {
        mSeekLayout = (ViewGroup) findViewById(R.id.seek_layout);
        mSeekBar = (SeekBar) findViewById(R.id.my_seekbar);
        mSeekBar.requestFocus();
        mSeekBar.setMax(mTime);

        mCurTime = (TextView) findViewById(R.id.current_time_tv);
        mTotalTime = (TextView) findViewById(R.id.total_time_tv);
    }

    private void initVideo() {
        mVideoPlayerView = (VideoPlayerView) findViewById(R.id.my_video);
        mVideoPlayerView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Xlog.d(TAG, "onPrepared");
                mVideoPlayerView.requestFocus();
                mVideoPlayerView.start();
                duration = mVideoPlayerView.getDuration() / 1000;
                mSeekBar.setMax(duration);
                mTotalTime.setText(TimeUtils.timeInt2String(duration) + "");

                mHandler.removeCallbacks(autoDismiss);
                mHandler.postDelayed(autoDismiss, 3000);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!startSeek) {
                            currentPos = mVideoPlayerView.getCurrentPosition() / 1000;
                            Xlog.d(TAG, "mSeekBar.setProgress=" + currentPos);
                            mSeekBar.setProgress(currentPos);
                            mCurTime.setText(TimeUtils.timeInt2String(currentPos));
                        }
                        mHandler.postDelayed(this, 200);
                    }
                }, 200);
            }
        });
        mVideoPlayerView.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                Xlog.d(TAG, "onSeekComplete");
                startSeek = false;
            }
        });
        mVideoPlayerView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Xlog.d(TAG, "onCompletion");
                ToastUtils.showToast("视频播放完成");
                setResultStatus(RESULT_PLAY_COMPLETE);
                finish();
            }
        });
        mVideoPlayerView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Xlog.d(TAG, "onError, what=%s, extra=%s", what, extra);
                ToastUtils.showToast("视频出错");
                setResultStatus(RESULT_PLAY_ERROR);
                finish();
                return false;
            }
        });
    }

    private void setResultStatus(String status) {
        Intent intent = new Intent();
        intent.putExtra(RESULT_PLAY_STATUS, status);
        setResult(RESULT_CODE_PLAY_FINISH, intent);
    }

    private void startPlay() {
        Xlog.d(TAG, "startPlay, playUrl:" + mPlayUrl);
        if (TextUtils.isEmpty(mPlayUrl)) {
            Xlog.d(TAG, "playUrl is null");
            return;
        }
        mVideoPlayerView.setVideoPath(mPlayUrl);
    }

    Runnable autoDismiss = new Runnable() {
        @Override
        public void run() {
            mSeekLayout.setVisibility(View.GONE);
            mVideoTitleTv.setVisibility(View.GONE);
        }
    };

    private void seekVideoTo(int seekTo) {
        mVideoPlayerView.seekTo(seekTo * 1000);
        if (!mVideoPlayerView.isPlaying()) {
            mVideoPlayerView.start();
            mPauseImage.setVisibility(View.GONE);
        }
        mHandler.removeCallbacks(autoDismiss);
        mHandler.postDelayed(autoDismiss, 3000);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Xlog.d(TAG, "onKeyUp, keyCode=" + keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                step = DEFAULT_STEP;
                seekVideoTo(currentPos);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                step = DEFAULT_STEP;
                seekVideoTo(currentPos);
                break;
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                mHandler.removeCallbacks(autoDismiss);
                mHandler.postDelayed(autoDismiss, 3000);
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Xlog.d(TAG, "onKeyDown, keyCode=" + keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                step = Math.min(step + 1, MAX_STEP);
                currentPos = Math.min(duration, currentPos + step);
                startSeek();
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                step = Math.min(step + 1, MAX_STEP);
                currentPos = Math.max(0, currentPos - step);
                startSeek();
                break;
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DPAD_CENTER:
                mVideoTitleTv.setVisibility(View.VISIBLE);
                mSeekLayout.setVisibility(View.VISIBLE);
                if (mVideoPlayerView.isPlaying()) {
                    mVideoPlayerView.pause();
                    mPauseImage.setVisibility(View.VISIBLE);
                } else {
                    mVideoPlayerView.start();
                    mPauseImage.setVisibility(View.GONE);
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startSeek() {
        startSeek = true;
        mHandler.removeCallbacks(autoDismiss);
        mSeekBar.setProgress(currentPos);
        mCurTime.setText(TimeUtils.timeInt2String(currentPos));
        mSeekLayout.setVisibility(View.VISIBLE);
        mVideoTitleTv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacksAndMessages(null);
        mVideoPlayerView.stopPlayback();
    }

    @Override
    public void onBackPressed() {
        if (mSeekLayout.isShown()) {
            mHandler.removeCallbacks(autoDismiss);
            mHandler.post(autoDismiss);
        } else {
            if (System.currentTimeMillis() - lastBackBtnTime < 2000) {
                setResultStatus(RESULT_PLAY_BACK);
                super.onBackPressed();
            } else {
                ToastUtils.showToast("再按一次返回退出");
            }
            lastBackBtnTime = System.currentTimeMillis();
        }
    }

}