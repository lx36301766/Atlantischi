package com.starcor.vds.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.starcor.OTTTV;

public class GeneralUtils {

    public static abstract class LocationObserve extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.i("Player", "接收到更换地址的消息.");
            getResult(msg);
        }

        public abstract void getResult(Message msg);
    }

    /**
     * @param index
     */
    public static void stopStream(final int index) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = GeneralUtils.connection("http://127.0.0.1:" + OTTTV.port + "/api/stop_stream?index=" + index);
                    if (response.resultCode == 200) {
                        Log.i("Player", "关闭流成功!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Player", "关闭所有流失败!");
                }
            }
        }).start();
    }

    /**
     * @deprecated 调用OTTTV中间件的实现
     */
    public static void stopAllStream() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = GeneralUtils.connection("http://127.0.0.1:" + OTTTV.port + "/api/stop_all_stream");
                    if (response.resultCode == 200) {
                        Log.i("Player", "关闭所有流成功!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Player", "关闭所有流失败!");
                }
            }
        }).start();
    }

    public static Response connection(String url) {
        Response result = new Response();
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.connect();
            result.resultCode = ((HttpURLConnection) connection).getResponseCode();
            if (result.resultCode == 200) {
                result.in = connection.getInputStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void getLocationUrl(Context context, final String url, final String cdn, final LocationObserve observe) {
        OTTTV.init(context);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    String requestUrl = url;
                    requestUrl = "http://127.0.0.1:" + OTTTV.port + "/api/switch_stream?cdn=" + cdn + "&data_url=" + URLEncoder.encode(url, "utf-8");
                    Log.i("Player", "getLocationUrl 中间件启动  请求启动流 api:" + requestUrl);
                    Response result = connection(requestUrl);

                    if (result.resultCode == 200) {
                        Result mResult = getResultfromXml(result.in);
                        Log.i("Player", "getLocationUrl 中间件启动流成功");
                        // 取播放地址:
                        requestUrl = "http://127.0.0.1:" + OTTTV.port + "/api/get_stream_play_url?index=" + mResult.stream.index;
                        Response mResponse = connection(requestUrl);

                        if (mResponse.resultCode == 200) {
                            Log.i("Player", "getLocationUrl 中间件获得播放流收到回应");
                            mResult = getResultfromXml(mResponse.in);
                            Log.i("Player", "getLocationUrl 中间件获得播放流地址成功");
                            if (observe != null && !TextUtils.isEmpty(mResult.stream.play_url)) {
                                /*
                                 * Message msg = new Message(); msg.what =
                                 * 200; msg.obj = mResult.stream.play_url;
                                 */

                                Message msg = new Message();
                                msg.what = 200;
                                msg.arg1 = Integer.valueOf(mResult.stream.index);
                                msg.obj = mResult.stream.play_url;
                                observe.sendMessage(msg);

                                return;
                            }
                        }
                    }
                    Log.e("Player", "更换URL异常, 切换回原始地址");
                    if (observe != null) {
                        Message msg = new Message();
                        msg.what = 200;
                        msg.arg1 = 0;
                        msg.obj = url;
                        observe.sendMessage(msg);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test", "更换URL异常!!!");
                    if (observe != null) {
                        Message msg = new Message();
                        msg.what = 200;
                        msg.arg1 = 0;
                        msg.obj = url;
                        observe.sendMessage(msg);
                    }
                }
            }
        }).start();
    }

    public static Result getResultfromXml(InputStream in) {
        final Result result = new Result();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(in, new DefaultHandler() {

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if ("result".equals(qName)) {
                        result.api_run_ms = attributes.getValue(attributes.getIndex("api_run_ms"));
                        result.reason = attributes.getValue(attributes.getIndex("reason"));
                        result.ret = attributes.getValue(attributes.getIndex("ret"));
                    } else if ("stream".equals(qName)) {
                        Stream stream = new Stream();
                        stream.code = attributes.getValue(attributes.getIndex("code"));
                        stream.play_url = attributes.getValue(attributes.getIndex("play_url"));
                        stream.index = attributes.getValue(attributes.getIndex("index"));
                        stream.reason = attributes.getValue(attributes.getIndex("reason"));
                        result.stream = stream;
                    }
                }

                @Override
                public void endDocument() throws SAXException {
                    super.endDocument();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    static class Result {
        String ret;
        String reason;
        String api_run_ms;
        Stream stream;

        @Override
        public String toString() {
            return "Result [ret=" + ret + ", reason=" + reason + ", api_run_ms=" + api_run_ms + ", stream=" + stream + "]";
        }

    }

    static class Response {
        int resultCode;
        InputStream in;
    }

    static class Stream {
        String index;
        String play_url;
        String code;
        String reason;

        @Override
        public String toString() {
            return "Stream [index=" + index + ", play_url=" + play_url + ", code=" + code + ", reason=" + reason + "]";
        }

    }
}
