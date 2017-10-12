package com.starcor.vds.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends BaseActivity {

    protected static final String TAG = MainActivity.class.getSimpleName();
    private RelativeLayout root;
    //TODO   这里加上播放地址
    private String playUrl = "http://119.39.49.19:8089/001/2/00100062973100000000000000000000?virtualDomain=001.vod_hls.zte.com";
    private LayoutParams lp;
    private TextView src;
    private TextView real;
    private TextView copyRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root = (RelativeLayout) findViewById(R.id.id_main_root);
        root.setBackgroundColor(Color.BLACK);
        video = new VideoView(MainActivity.this);
        lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        root.addView(video, lp);
        video.setFocusable(false);

        copyRight = new TextView(this);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.bottomMargin = 20;
        lp.rightMargin = 20;
        root.addView(copyRight, lp);
        copyRight.setFocusable(true);
        copyRight.getPaint().setTextSize(20);
        copyRight.setSingleLine();
        copyRight.setTextColor(Color.DKGRAY);
        copyRight.setText("视达科播放器测试工具 V1.0");

        src = new TextView(this);
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.topMargin = 20;
        lp.leftMargin = 20;
        root.addView(src, lp);
        src.getPaint().setTextSize(20);
        src.setSingleLine();
        src.setTextColor(Color.WHITE);
//        src.setText("原始播放地址：" + playUrl);
//        int w = getResources().getDisplayMetrics().widthPixels;
//        int h = getResources().getDisplayMetrics().heightPixels;
//        src.setText("屏幕宽高：" + w + "-" + h);

        real = new TextView(this);
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.topMargin = 50;
        lp.leftMargin = 20;
        root.addView(real, lp);
        real.getPaint().setTextSize(20);
        real.setTextColor(Color.WHITE);


        video.setOnPreparedListener(new OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                video.requestLayout();
                video.start();
            }
        });
        video.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(MainActivity.this, "播放错误！！！", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(MainActivity.this, "播放完成！！！", Toast.LENGTH_LONG).show();
            }
        });
//        Uri mUri = Uri.parse("android.resource://" + getPackageName() + "/" + getResourceByName(this, "raw", "video1"));
//        video.setVideoURI(mUri);
//        video.start();

        video.setVideoPath(playUrl);

//        real.setText("不转换播放地址：" + playUrl);
//        Log.d(TAG, "不转换播放地址：" + playUrl);
//        buildUrl(playUrl);

//        reversePlayUrl();
    }

    @Override
    protected void beforeStartNextActivity() {
        video.pause();
    }

    public static int getResourceByName(Context context, String className, String name) {
        String packageName = context.getPackageName();
        Class r = null;
        int id = 0;
        try {
            r = Class.forName(packageName + ".R");
            Class[] classes = r.getClasses();
            Class desireClass = null;
            for (int i = 0; i < classes.length; ++i) {
                if (classes[i].getName().split("\\$")[1].equals(className)) {
                    desireClass = classes[i];
                    break;
                }
            }
            if (desireClass != null)
                id = desireClass.getField(name).getInt(desireClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void reversePlayUrl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否进行播放地址转换？");
        builder.setTitle("提示");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                buildUrl(playUrl);
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                video.setVideoPath(playUrl);
                real.setText("不转换播放地址：" + playUrl);
                Log.d(TAG, "不转换播放地址：" + playUrl);
            }
        });
        builder.create().show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
    }

    private void inputPlayUrl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("请输入播放地址:");
        final EditText editText = new EditText(this);
        builder.setView(editText);
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String url = editText.getText().toString();
                src.setText("播放地址：" + url);
                video.setVideoPath(url);
            }
        });
//        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                video.setVideoPath(playUrl);
//                real.setText("不转换播放地址：" + playUrl);
//                Log.d(TAG, "不转换播放地址：" + playUrl);
//            }
//        });
        builder.create().show();
    }

    @Override
    protected void onPause() {
        video.stopPlayback();
        super.onPause();
    }

    private String buildUrl(final String src) {
        Log.d(TAG, "原始播放地址：" + src);
        //TODO  处理URL.
        GeneralUtils.getLocationUrl(this, src, "mgtv", new GeneralUtils.LocationObserve() {
            @Override
            public void getResult(Message msg) {
                String url = msg.obj.toString();
                real.setText("转换后的播放地址：" + url);
                Log.d(TAG, "转换后的播放地址：" + url);
                video.setVideoPath(url);
            }
        });
        return src;
    }


    private VideoView video;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e(TAG, "keyCode--->" + keyCode);
        return super.onKeyDown(keyCode, event);
    }
}
