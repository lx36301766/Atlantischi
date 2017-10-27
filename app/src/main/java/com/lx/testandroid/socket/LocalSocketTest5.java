package com.lx.testandroid.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.lx.testandroid.R;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

public class LocalSocketTest5 {

    //    // 这个是我启动的服务,在单独的一个进程中执行 process这个属性不是很明白,请百度一下.
    //<service android:name=".LocalSocketService"
    //    android:process=":server"/> // 这是另外一种配置,我试了一下,这种方式也是可以实现的.
    //<service android:name=".LocalSocketService"
    //    android:process="jin.frid.server"/>

    public final static String TAG = "LocalSocketTest";

    public final static String NAME = "LUOxxx333dasxsxxxx";

    public static class SocketServer {

        public LocalServerSocket localServerSocket;
        public LocalSocket localSocket;

        public void send(String msg) {
            Log.d(TAG, "-----server sender, " + msg);
            OutputStream outputStream;
            try {
                outputStream = localSocket.getOutputStream();
                outputStream.write(msg.getBytes());
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void start() throws IOException {
            Log.d(TAG, "-----server start0");
            localServerSocket = new LocalServerSocket(NAME);
            Log.d(TAG, "-----server start1");
            new Thread() {
                @Override
                public void run() {
                    Log.d(TAG, "-----server start2");
                    try {
                        while (true) {
                            // 这个方法是阻塞的,有Client连接上来的时候,这里就会回调.
                            Log.d(TAG, "-----server start3");
                            localSocket = localServerSocket.accept();
                            Log.d(TAG, "-----server start4");
                            new Thread() {
                                @Override
                                public void run() {
                                    // 读取的是一个阻塞的过程,如果在这里不开一个线程的,那么会一直阻塞在这里,下面的代码得不到执行
                                    waitClient(localSocket);
                                }
                            }.start();

                            Log.d(TAG, "-----server start5");
                            // 这里是server发送信息,如果上面的读取阻塞了,就一直执行不到这里,所以上面开了线程处理.
                            response(localSocket);
                            Log.d(TAG, "-----server start6");
                        }
                    } catch (IOException e) {
                        Log.e(getClass().getName(), e.getMessage());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                private void waitClient(LocalSocket receiver) {
                    try {
                        Log.d(TAG, "-----server sender");
                        InputStream input = receiver.getInputStream();
                        // 这里是读取的代码
                        int readed = 0;
                        byte[] bytes = new byte[1024];
                        // reading
                        while (readed != -1) {
                            // read next byte
                            readed = input.read(bytes);
                            Log.d(TAG, "-----server receive = " + new String(bytes, 0, readed));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                private void response(LocalSocket receiver) throws IOException, InterruptedException {
                    if (receiver != null) {
                        OutputStream outputStream = receiver.getOutputStream();
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(1000);
                            Log.d(TAG, "-----server sender");
                            outputStream.write(("xuange" + i).getBytes());
                            // 发送数据必须要在这里写入到输出流中,不然那边是接收不到
                            // 自己之前就在这里纠结了很久,因为自己之前用的LocalSocket的close()方法之后.另一边才能收到.我想了好久
                            // .我以为这个LocalSocket是一个类似短的服务连接.单次有效呢.后来发现自己理解错了
                            outputStream.flush();
                        }
                    }
                }

            }.start();
        }

    }

    public static class SocketClient {

        private InputStream inputStream;
        private OutputStream outputStream;
        LocalSocket localSocket;

        public void connect() {
            new Thread() {
                @Override
                public void run() {
                    localSocket = new LocalSocket();
                    try {
                        Log.d(TAG, "");
                        // 连接另外一个进程的Sever连接
                        // LocalSocketService.ADDRESS 这个是连接的地址,相当于每个连接标示符,你想连接到这个上
                        localSocket.connect(new LocalSocketAddress(NAME));
                        Log.d(TAG, "localSocket.isConnected() = " + localSocket.isConnected());
                        // 设置接收的缓存空间的大小
                        localSocket.setReceiveBufferSize(500000);
                        localSocket.setSendBufferSize(500000);
                        // 获取连接的输入,输出流.
                        outputStream = localSocket.getOutputStream();
                        inputStream = localSocket.getInputStream();
                        PrintWriter printWriter = new PrintWriter(outputStream);
                        // 这里是模拟向服务端发送信息
                        for (int i = 0; i < 5; i++) {
                            Thread.sleep(1000);
                            outputStream.write(("client sender--" + i).getBytes());
                            Log.d(TAG, "client sender");
                            printWriter.flush();
                        }
                        // 这里启用线程,去读取,因为读取是阻塞操作,会造成问题的.
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    int readed = 0;
                                    byte[] bytes = new byte[1024];
                                    while (readed != -1) {
                                        readed = inputStream.read(bytes);
                                        Log.d(TAG, "clientRead = " + new String(bytes, 0, readed));
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        /**
         * 这里模拟客服端发送数据
         **/
        public void send(String msg) {
            try {
                if (localSocket != null) {
                    Log.d(TAG, "client send message");
                    outputStream.write(msg.getBytes());
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 关闭连接
        public void close() {
            if (localSocket != null) {
                try {
                    localSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
