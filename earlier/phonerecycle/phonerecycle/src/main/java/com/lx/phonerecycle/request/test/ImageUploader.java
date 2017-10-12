package com.lx.phonerecycle.request.test;

import android.content.Context;
import android.graphics.Bitmap;
import com.google.common.collect.Maps;
import com.lx.phonerecycle.request.MainInfo;
import com.lx.phonerecycle.request.RequestParams;
import com.lx.phonerecycle.tools.Tools;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-26 上午11:48 <br>
 * description:
 */

public class ImageUploader {

    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10 * 10000000;   //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";

    public String queryFilePost2(String url, List<RequestParams> requestParamsList, File file) {
        String strResult = "";// 返回信息
        HttpPost httppost = null;
        HttpEntity resEntity = null;
        try {
            // 设置通信协议版本
            HttpClient httpClient = getHttpClient();
            httpClient.getParams().setParameter(
                    CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            MultipartEntity mpEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE); // 文件传输
            if (requestParamsList != null && requestParamsList.size() > 0) {
                for (RequestParams params : requestParamsList) {
                    mpEntity.addPart(params.getName(), new StringBody(params.getValue()));
                }
            }
            httppost = new HttpPost(url);
            ContentBody cbFile = new FileBody(file);
            mpEntity.addPart("nns_header", cbFile);

            httppost.setEntity(mpEntity);
            System.out.println("executing request " + httppost.getRequestLine());

            HttpResponse response = httpClient.execute(httppost);// 上传文件
            resEntity = response.getEntity();// 获取entity
            strResult = EntityUtils.toString(resEntity, "utf-8");// 返回信息
            System.out.println(response.getStatusLine());// 通信Ok
            System.out.println(response.getStatusLine().getStatusCode() + "---响应码");
            System.out.println("响应内容----" + strResult);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httppost != null) {
                httppost.abort();
            }
            if (resEntity != null) {
                try {
                    resEntity.consumeContent();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            System.out.println("----finally--in----");
        }
        System.out.println("----catch--out---1-");
        // httpClient.getConnectionManager().shutdown();
        System.out.println("----catch--out---2-");
        System.out.println("--catch--out-响应内容------2-：" + strResult);
        return strResult;
    }

    /**
     *
     * @param urlServer
     *            上传的地址
     * @param params
     *            上传的参数
     * @param file
     *            上传文件
     * @return
     */
    public String queryFilePost(String urlServer, Map<String, Object> params, File file) {
        getHttpClient();
        String strResult = "";// 返回信息
        HttpPost httppost = null;
        HttpEntity resEntity = null;
        try {
            // 设置通信协议版本
            HttpClient httpClient = getHttpClient();
            httpClient.getParams().setParameter(
                    CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            MultipartEntity mpEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE); // 文件传输
            if (params != null && params.size() > 0) {
                Iterator<?> iterator = params.entrySet().iterator();
                // 添加参数
                while (iterator.hasNext()) {
                    @SuppressWarnings("unchecked")
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator
                            .next();
                    String key = entry.getKey();
                    String value = (String) entry.getValue();
                    mpEntity.addPart(key, new StringBody(value));
                }
            }
            httppost = new HttpPost(urlServer);
            ContentBody cbFile = new FileBody(file);
            mpEntity.addPart("nns_header", cbFile);

            httppost.setEntity(mpEntity);
            System.out.println("executing request " + httppost.getRequestLine());

            HttpResponse response = httpClient.execute(httppost);// 上传文件
            resEntity = response.getEntity();// 获取entity
            strResult = EntityUtils.toString(resEntity, "utf-8");// 返回信息
            System.out.println(response.getStatusLine());// 通信Ok
            System.out.println(response.getStatusLine().getStatusCode() + "---响应码");
            System.out.println("响应内容----" + strResult);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httppost != null) {
                httppost.abort();
            }
            if (resEntity != null) {
                try {
                    resEntity.consumeContent();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            System.out.println("----finally--in----");
        }
        System.out.println("----catch--out---1-");
        // httpClient.getConnectionManager().shutdown();
        System.out.println("----catch--out---2-");
        System.out.println("--catch--out-响应内容------2-：" + strResult);
        return strResult;
    }

    /**
     *
     * @Title: getHttpClient
     * @Description: 得到 apache http HttpClient对象
     * @return DefaultHttpClient 返回类型
     * @throws
     */
    public DefaultHttpClient getHttpClient() {
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, REQUEST_TIMEOUT);
        HttpConnectionParams.setSoTimeout(httpParams, SO_TIMEOUT);
        HttpConnectionParams.setSocketBufferSize(httpParams, SOCKET_BUF_SIZE);
        HttpClientParams.setRedirecting(httpParams, true);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParams);
        httpClient.setHttpRequestRetryHandler(requestRetryHandler);
        return httpClient;
    }

    private static final int REQUEST_TIMEOUT = 20 * 1000;// 设置请求超时20秒钟
    private static final int SO_TIMEOUT = 20 * 1000; // 设置等待数据超时时间20秒钟
    private static final int SOCKET_BUF_SIZE = 8 * 1024;// Socket数据缓存默认8K

    private HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
        // 自定义的恢复策略
        public boolean retryRequest(IOException exception, int executionCount,
                                    HttpContext context) {
            // 设置恢复策略，在发生异常时候将自动重试N次
            if (executionCount >= 3) {
                // 如果超过最大重试次数，那么就不要继续了
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                // 如果服务器丢掉了连接，那么就重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {
                // 不要重试SSL握手异常
                return false;
            }
            HttpRequest request = (HttpRequest) context
                    .getAttribute(ExecutionContext.HTTP_REQUEST);
            boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
            if (!idempotent) {
                // 如果请求被认为是幂等的，那么就重试
                return true;
            }
            return false;
        }
    };

    public void testN2A4_2(Context context, String userId, String authorCode, Bitmap bmp) {
        final String urlServer = MainInfo.n2_a;
        final Map<String, Object> params = Maps.newLinkedHashMap();
        params.put("nns_method", "user_modify");
        params.put("nns_user_id", userId);
        params.put("nns_sex", "F");
        params.put("nns_birthday", "1988-06-14");
        params.put("nns_email", "lx123@163.com");
        params.put("nns_address", "chengdu");
        params.put("nns_author_code", authorCode);

        params.put("nns_sign", getSign(params));
        final File file = Tools.convertBmpToFile(context, bmp);
        new Thread() {
            @Override
            public void run() {
                ImageUploader imageUploader = new ImageUploader();
                imageUploader.queryFilePost(urlServer, params, file);
            }
        }.start();
    }

    public static String getSign(Map<String, Object> map) {
        RequestParams[] paramses = new RequestParams[map.size()];
        Iterator<?> iterator = map.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
            String key = entry.getKey();
            String value = (String) entry.getValue();
            paramses[i] = RequestParams.build(key, value);
            i++;
        }
        String sign = "";
        for (RequestParams params : paramses) {
            if (params.isSign()) {
                sign += params.getName() + Tools.md5(params.getValue());
            }
        }
        sign = Tools.md5(sign).toLowerCase();
        return sign;
    }

}
