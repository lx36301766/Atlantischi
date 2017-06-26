package com.atlantischi.videocompress;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.atlantischi.videocompress.comporessor.MediaController;

/**
 * Created on 06/06/2017.
 *
 * @author lx
 */

public class VideoCompressActivity extends Activity {

    static String sdRoot = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_compress);

        findViewById(R.id.compress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MediaController.cacheFile.delete()) {
                    Log.e("denver", "delete compress cache file failed!");
                }
                createApplicationFolder();
                new Thread() {
                    @Override
                    public void run() {
                        String path = sdRoot +"/DCIM/Camera/VID20160822155901.mp4";
                        final MediaController.CompressResult result = MediaController.getInstance().convertVideo(path);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (result.mResult) {
                                    Toast.makeText(VideoCompressActivity.this, "compress success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(VideoCompressActivity.this, "compress error", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
                    }
                }.start();
            }
        });
    }


    public static void createApplicationFolder() {
        File f = new File(sdRoot + File.separator + MediaController.VIDEO_COMPRESSOR_APPLICATION_DIR_NAME);
        f.mkdirs();
        f = new File(sdRoot + File.separator + MediaController.VIDEO_COMPRESSOR_APPLICATION_DIR_NAME
                        + MediaController.VIDEO_COMPRESSOR_COMPRESSED_VIDEOS_DIR);
        f.mkdirs();
        f = new File(sdRoot + File.separator + MediaController.VIDEO_COMPRESSOR_APPLICATION_DIR_NAME
                + MediaController.VIDEO_COMPRESSOR_TEMP_DIR);
        f.mkdirs();
    }

}
