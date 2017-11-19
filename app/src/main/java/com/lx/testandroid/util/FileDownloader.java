package com.lx.testandroid.util;

import android.net.http.AndroidHttpClient;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-8-11 下午4:05 <br>
 * description:
 */

public class FileDownloader implements Runnable {
    public static final String TAG = "FileDownloader";
    private Thread _worker_thread = null;
    private volatile Handler _handler;
    private volatile AndroidHttpClient _client = null;
    private String _url;
    private RandomAccessFile _file;
    private File _file_info;
    private long _resume_pos;
    private byte[] _resume_sign;
    private StatusLine _status;
    private volatile long _file_size;
    private volatile long _file_write_pos;
    private int _error_status;
    private InputStream _cur_is;
    private HttpGet _get;

    public FileDownloader(Handler handler) {
        _handler = handler;
    }

    // -----------------------------
    public boolean start(String url, File local_file) {
        return start(url, local_file, false);
    }

    public boolean start(String url, File local_file, boolean resume) {
        if (_client != null) {
            return false;
        }
        _error_status = ERR_NO_ERROR;
        _client = AndroidHttpClient.newInstance("Starcor upgrade service");
        _worker_thread = new Thread(this);
        try {
            _file_info = local_file;
            _file = new RandomAccessFile(_file_info, "rw");
            long file_size = _file.length();
            if (resume && file_size > 512) {
                _file.seek(file_size - 512);
                byte[] buffer = new byte[512];
                _file.read(buffer);
                _resume_sign = buffer;
                _resume_pos = file_size - 512;
            } else {
                _resume_sign = null;
                _resume_pos = 0;
            }
        } catch (FileNotFoundException e) {
            _error_status = ERR_CANNOT_OPEN_LOCALFILE;
            Log.e(TAG, "file not found exception");
            return false;
        } catch (IOException e) {
            _error_status = ERR_IO_ERROR;
            Log.e(TAG, "io exception");
            return false;
        }

//            _handler = handler;
        _url = url;
        _worker_thread.start();
        _status = null;
        return true;
    }

    public boolean resume() {
        if (_client == null) {
            return false;
        }
//            DownloadManager handler = _handler;
        File local_file = _file_info;
        String url = _url;
        stop();
        return start(url, local_file, true);
    }

    public boolean stop() {
        if (_client == null) {
            return false;
        }
//            _handler = null;

        if (_client != null) {
            AndroidHttpClient client = _client;
            _client = null;
            try {
                client.getConnectionManager().shutdown();
                client.close();
            } catch (Exception e) {
            }
        }

        if (_cur_is != null) {
            try {
                _cur_is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (_get != null) {
            try {
                _get.abort();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            _worker_thread.interrupt();
            // _worker_thread.join();
            _worker_thread = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (_file != null) {
            try {
                _file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public float getProgress() {
        if (_file_size > 0) {
            return ((float) _file_write_pos) / _file_size;
        }
        return 0.0f;
    }

    public int getError() {
        return _error_status;
    }

    // -----------------------------
    public static final int MSG_STARTING = 1;
    public static final int MSG_ERROR = 2;
    public static final int MSG_FINISHED = 3;
    public static final int MSG_RECIVING = 4;
    public static final int MSG_PROGRESSING = 5;

    // -----------------------------
    static final int ERR_NO_ERROR = 0x00;
    static final int ERR_INTERNAL_ERROR = 0x01;
    static final int ERR_RESUME_CHECK_FAILED = 0x010;
    static final int ERR_CANNOT_OPEN_LOCALFILE = 0x011;
    static final int ERR_IO_ERROR = 0x012;
    static final int ERR_HTTP_RESPONSE_ERROR = 0x013;

    private void sendMessage(int msg) {
        if (_handler != null) {
            _handler.sendEmptyMessage(msg);
        }
    }

    private void sendMessage(int msg, Object obj) {
        if (_handler != null) {
            Message message = new Message();
            message.what = msg;
            message.obj = obj;
            _handler.sendMessage(message);
        }
    }

    // -----------------------------
    @Override
    public void run() {
        try {
            Log.d(TAG, "start downloading... " + _url);
            URI uri = new URI(_url);
            if (uri.getHost() == null || uri.getPath() == null) {
                Log.e(TAG, "URI no host/path error!! " + _url);
                this._error_status = ERR_INTERNAL_ERROR;
                this.sendMessage(MSG_ERROR);
                return;
            }
            HttpGet get_url = new HttpGet(uri);
            if (_resume_pos > 0) {
                Log.d(TAG, "resuming... bytes=" + _resume_pos + "-");
                get_url.setHeader("Range", "bytes=" + Long.toString(_resume_pos) + "-");
            }
            _get = get_url;
            HttpResponse resp;
            this.sendMessage(MSG_STARTING);
            resp = _client.execute(get_url);
            _status = resp.getStatusLine();
            long code = _status.getStatusCode();
            Log.d(TAG, "response " + code + " " + _status.getReasonPhrase());
            if (code != 200 && code != 206) {
                Log.e(TAG, "HTTP error!! ");
                this._error_status = ERR_HTTP_RESPONSE_ERROR;
                this.sendMessage(MSG_ERROR);
                return;
            }
            this.sendMessage(MSG_RECIVING);
            HttpEntity entity = resp.getEntity();
            InputStream is = entity.getContent();
            _cur_is = is;
            long total_size = entity.getContentLength();
            this._file_size = total_size;
            long read_bytes = 0;

            if (_resume_pos > 0 && code == 206) {
                Log.d(TAG, "check content-range...");
                // resume downloading
                Header[] header = resp.getHeaders("Content-Range");
                if (header == null || header.length != 1) {
                    Log.e(TAG, "resume: HTTP error, no content-range header!");
                    this._error_status = ERR_INTERNAL_ERROR;
                    this.sendMessage(MSG_ERROR);
                    return;
                }
                {
                    String content_range_val = header[0].getValue();
                    Pattern pattern = Pattern.compile("^bytes\\s+(\\d+)-(\\d*)/(\\d+)$", Pattern.CASE_INSENSITIVE);
                    Matcher m = pattern.matcher(content_range_val);
                    if (!m.matches()) {
                        Log.e(TAG, "resume: unsupported content-range:" + content_range_val);
                        this._error_status = ERR_INTERNAL_ERROR;
                        this.sendMessage(MSG_ERROR);
                        return;
                    }
                    long begin_pos = Long.parseLong(m.group(1));
                    long end_pos = Long.parseLong(m.group(2));
                    long file_length = Long.parseLong(m.group(3));
                    if (begin_pos != _resume_pos) {
                        Log.e(TAG, "resume: file range invalid");
                        this._error_status = ERR_RESUME_CHECK_FAILED;
                        this.sendMessage(MSG_ERROR);
                        return;
                    }
                    _file_size = file_length;
                    byte[] sign_buffer = new byte[_resume_sign.length];
                    int size = 0;
                    while (size < _resume_sign.length) {
                        if (Thread.interrupted()) {
                            Log.w(TAG, "download interrupted!!");
                            return;
                        }
                        size += is.read(sign_buffer, size, _resume_sign.length - size);
                    }
                    read_bytes += size;
                    if (size != _resume_sign.length || !Arrays.equals(sign_buffer, _resume_sign)) {
                        Log.e(TAG, "resume data check failed!!");
                        this._error_status = ERR_RESUME_CHECK_FAILED;
                        this.sendMessage(MSG_ERROR);
                        return;
                    }
                    Log.d(TAG, "continue download from " + _resume_pos);
                }
            } else {
                Log.d(TAG, "download from begining...");
                _file.seek(0);
            }
            byte[] read_cache = new byte[2048];
            while (read_bytes < total_size) {
                long max_len = Math.min(read_cache.length, total_size - read_bytes);
                if (Thread.interrupted()) {
                    Log.w(TAG, "download interrupted!!");
                    return;
                }
                int size = is.read(read_cache, 0, (int) max_len);
                _file.write(read_cache, 0, size);
                read_bytes += size;
                _file_write_pos = _file.getFilePointer();
                this.sendMessage(MSG_PROGRESSING, new float[]{read_bytes,total_size});
            }
            _file_write_pos = _file.getFilePointer();
            _file.setLength(_file_write_pos);
            _file.getFD().sync();
            _file.close();
            _file = null;
            _cur_is = null;
            _get = null;
            is.close();
            _client.close();
            Log.d(TAG, "download finished!");
            if (_handler != null) {
                Message message = new Message();
                message.what = MSG_FINISHED;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("filePath", _file_info.getAbsolutePath());
                jsonObject.put("url", _url);
                message.obj = jsonObject;
                _handler.sendMessage(message);
            }
        } catch (IOException e) {
            Log.e(TAG, "io exception", e);
            this._error_status = ERR_IO_ERROR;
            this.sendMessage(MSG_ERROR);
        } catch (NumberFormatException e) {
            Log.e(TAG, "number format exception");
            this._error_status = ERR_INTERNAL_ERROR;
            this.sendMessage(MSG_ERROR);
        } catch (URISyntaxException e) {
            Log.e(TAG, "URI syntax error!! " + _url);
            this._error_status = ERR_INTERNAL_ERROR;
            this.sendMessage(MSG_ERROR);
        } catch (Exception e) {
            Log.e(TAG, "unknown exception!! " + e.getMessage());
            this._error_status = ERR_INTERNAL_ERROR;
            this.sendMessage(MSG_ERROR);
        } finally {
            _client = null;
        }
    }
}