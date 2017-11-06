package com.lx.testandroid.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Executors;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.util.Log;

/**
 * Created on 27/10/2017.
 *
 * @author lx
 */

public class SocketServer {

    private static final String TAG = "SocketServer";

    public static final String NAME = "JD_LOCAL_SOCKET";

    private LocalServerSocket mLocalServerSocket;
    private LocalSocket mLocalSocket;


    public SocketServer() {
        try {
            mLocalServerSocket = new LocalServerSocket(NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        mLocalServerSocket = new LocalServerSocket(NAME);
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    // 这个方法是阻塞的,有Client连接上来的时候,这里就会回调.
                    try {
                        mLocalSocket = mLocalServerSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    receiveMessageFromClient();
                    responseToClient();
                }
            }
        });
    }

    private void receiveMessageFromClient() {
        // 读取的是一个阻塞的过程,如果在这里不开一个线程的,那么会一直阻塞在这里,后面的代码得不到执行
        Executors.newSingleThreadExecutor().submit(new Runnable() {

            @Override
            public void run() {
                try {
                    InputStream input = mLocalSocket.getInputStream();
                    // 这里是读取的代码
                    int read = 0;
                    byte[] bytes = new byte[1024];
                    // reading
                    while (read != -1) {
                        // read next byte
                        read = input.read(bytes);
                        Log.d(TAG, "-----receiveMessageFromClient = " + new String(bytes, 0, read));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void responseToClient() {
        // 这里是server发送信息,如果上面的读取阻塞了,就一直执行不到这里,所以上面开了线程处理.
        if (mLocalSocket != null) {
            try {
                OutputStream outputStream = mLocalSocket.getOutputStream();
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(1000);
                    String msg = "xuange" + i;
                    Log.d(TAG, "-----responseToClient = " + msg);
                    outputStream.write(msg.getBytes());
                    // 发送数据必须要在这里写入到输出流中,不然那边是接收不到
                    // 自己之前就在这里纠结了很久,因为自己之前用的LocalSocket的close()方法之后.另一边才能收到.我想了好久
                    // .我以为这个LocalSocket是一个类似短的服务连接.单次有效呢.后来发现自己理解错了
                    outputStream.flush();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        try {
            mLocalServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
