package com.lx.testandroid.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.Executors;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

/**
 * Created on 27/10/2017.
 *
 * @author lx
 */

public class SocketClient {

    private static final String TAG = "SocketClient";

    private InputStream inputStream;
    private OutputStream outputStream;
    LocalSocket localSocket;

    public SocketClient() {
    }

    public void connect() {
        Executors.newSingleThreadExecutor().submit(new Runnable() {

            @Override
            public void run() {
                localSocket = new LocalSocket();
                try {
                    // 连接另外一个进程的Sever连接
                    // LocalSocketService.ADDRESS 这个是连接的地址,相当于每个连接标示符,你想连接到这个上
                    localSocket.connect(new LocalSocketAddress(SocketServer.NAME));
                    Log.d(TAG, "localSocket.isConnected() = " + localSocket.isConnected());
                    // 设置接收的缓存空间的大小
                    localSocket.setReceiveBufferSize(500000);
                    localSocket.setSendBufferSize(500000);
                    // 获取连接的输入,输出流.
                    outputStream = localSocket.getOutputStream();
                    inputStream = localSocket.getInputStream();
                    sendMessageToServer();
                    receiveMessageFromServer();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
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

    public void sendMessageToServer() throws InterruptedException, IOException {
        PrintWriter printWriter = new PrintWriter(outputStream);
        // 这里是模拟向服务端发送信息
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            outputStream.write(("client sender--" + i).getBytes());
            Log.d(TAG, "client sender");
            printWriter.flush();
        }
    }

    public void receiveMessageFromServer() {
        // 这里启用线程,去读取,因为读取是阻塞操作,会造成问题的.
        Executors.newSingleThreadExecutor().submit(new Runnable() {

            @Override
            public void run() {
                try {
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    while (read != -1) {
                        read = inputStream.read(bytes);
                        Log.d(TAG, "clientRead = " + new String(bytes, 0, read));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
