package com.atway.webshell.manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.android.volley.VolleyError;
import com.atway.webshell.domain.DomainCheckAppUpdate;
import com.atway.webshell.utils.FileDownloader;
import com.atway.webshell.utils.Xlog;

import java.io.File;
import java.io.IOException;

public class UpdateManager {

    private static final String TAG = UpdateManager.class.getSimpleName();

    public static interface UpdateCallback {
        void onUpdate(String updateType);
        void updateError(String error);
    }

    private Context mContext;
    private UpdateCallback mUpdateCallback;
    private File mDownloadFile;

    private static final String APK_NAME = "tmp.apk";

    public static final String UPDATE_TYPE_FORCE = "1";
    public static final String UPDATE_TYPE_MANUAL = "2";

    public static final String ERROR_API = "error_api";
    public static final String ERROR_DOWNLOAD = "error_download";
    public static final String ERROR_NO_NEED_UPDATE = "error_no_need_update";
    public static final String ERROR_UNKNOWN = "error_unknown";

    public UpdateManager(Context context) {
        mContext = context;
        String path = context.getDir("update", Context.MODE_PRIVATE).getAbsolutePath() + File.separator + APK_NAME;
        mDownloadFile = new File(path);
    }

    public void startCheckUpdate(final UpdateCallback updateCallback) {
        this.mUpdateCallback = updateCallback;
        String ver = AppDataManager.getInstance().getAppVersionName();
        ApiManager.getInstance().apiCheckAppUpdate(ver, new ApiManager.ApiCallback<DomainCheckAppUpdate>() {
            @Override
            public void onSuccess(DomainCheckAppUpdate result) {
                if (result != null && result.update_info != null) {
                    if ("0".equals(result.update_info.update_state)) {
                        if (mUpdateCallback != null) {
                            mUpdateCallback.updateError(ERROR_NO_NEED_UPDATE);
                        }
                        Xlog.d(TAG, "no need update");
                    } else if ("1".equals(result.update_info.update_state)) {
                        Xlog.d(TAG, "start update");
                        downloadHandler.updateType = result.update_info.update_type;
                        try {
                            mDownloadFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mDownloadFile.setReadable(true, false);
                        FileDownloader fileDownloader = new FileDownloader(downloadHandler);
                        fileDownloader.start(result.update_info.download_url, mDownloadFile);
                    } else {
                        Xlog.e(TAG, "error update state, " + result.update_info.update_state);
                        if (mUpdateCallback != null) {
                            mUpdateCallback.updateError(ERROR_UNKNOWN);
                        }
                    }
                } else {
                    Xlog.e(TAG, "result=null, or result.update_info == null");
                    if (mUpdateCallback != null) {
                        mUpdateCallback.updateError(ERROR_UNKNOWN);
                    }
                }
            }

            @Override
            public void onError(VolleyError volleyError) {
                if (mUpdateCallback != null) {
                    mUpdateCallback.updateError(ERROR_API);
                }
            }
        });
    }

    DownloadHandler downloadHandler = new DownloadHandler(Looper.getMainLooper());

    class DownloadHandler extends Handler {

        String updateType;

        DownloadHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FileDownloader.MSG_STARTING:
                    Xlog.d(TAG, "FileDownloader.MSG_STARTING");
                    break;
                case FileDownloader.MSG_ERROR:
                    Xlog.d(TAG, "FileDownloader.MSG_ERROR");
                    if (mUpdateCallback != null) {
                        mUpdateCallback.updateError(ERROR_DOWNLOAD);
                    }
                    break;
                case FileDownloader.MSG_FINISHED:
                    Xlog.d(TAG, "FileDownloader.MSG_FINISHED");
                    if (mUpdateCallback != null) {
                        mUpdateCallback.onUpdate(updateType);
                    }
                    break;
                case FileDownloader.MSG_RECIVING:
                    Xlog.d(TAG, "FileDownloader.MSG_RECIVING");
                    break;
                case FileDownloader.MSG_PROGRESSING:
                    break;
            }
        }
    }

    public boolean installApk() {
        Xlog.d(TAG, "installApk, apk_path=" + mDownloadFile.getAbsolutePath());
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(mDownloadFile);
        i.setDataAndType(uri, "application/vnd.android.package-archive");
        try {
            mContext.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
