package com.lx.testandroid.socket.jiedian;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import protobuf.nativesocket.SocketComm;

/**
 * Created on 27/10/2017.
 *
 * @author lx
 */

public class JSocketClient {

    private static final String TAG = SocketManager.TAG;

    private InputStream inputStream;
    private OutputStream outputStream;
    LocalSocket mLocalSocket;
    ExecutorService executorService = Executors.newCachedThreadPool();

    private SocketMocker mocker;

    public JSocketClient() {
        mocker = new SocketMocker();
    }

    public void connect(final SocketManager.ConnectCallback connectCallback) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                mLocalSocket = new LocalSocket();
                try {
                    // 连接另外一个进程的Sever连接
                    // LocalSocketService.ADDRESS 这个是连接的地址,相当于每个连接标示符,你想连接到这个上
                    mLocalSocket.connect(new LocalSocketAddress(JSocketServer.NAME));
                    JLog.d(TAG, "localSocket.isConnected() = " + mLocalSocket.isConnected());
                    // 设置接收的缓存空间的大小
                    mLocalSocket.setReceiveBufferSize(500000);
                    mLocalSocket.setSendBufferSize(500000);
                    // 获取连接的输入,输出流.
                    outputStream = mLocalSocket.getOutputStream();
                    inputStream = mLocalSocket.getInputStream();
                    receiveMessageFromNativeServer(connectCallback);
                } catch (IOException e) {
                    JLog.e(TAG, e);
                    if (connectCallback != null) {
                        connectCallback.onNativeServerConnected(SocketManager.AppClientStatus.CONNECT_FAILED);
                    }
                }
            }
        });
    }

    // 关闭连接
    public void close() {
        if (mLocalSocket == null) {
            return;
        }
        try {
            mLocalSocket.shutdownInput();
            mLocalSocket.shutdownOutput();
            mLocalSocket.close();
        } catch (IOException e) {
            JLog.e(TAG, e);
        }
        JLog.d(TAG, "client closed");
    }

    public boolean sendMessageToNativeServer(byte[] data) {
        JLog.d(TAG, "sendMessageToNativeServer");
        if (mocker != null) {
            return mocker.sendMessageToNative(data);
        } else {
            if (outputStream == null) {
                return false;
            }
            PrintWriter printWriter = new PrintWriter(outputStream);
            try {
                outputStream.write(data);
                JLog.d(TAG, "client sender");
                printWriter.flush();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void receiveMessageFromNativeServer(final SocketManager.ConnectCallback connectCallback) {
        JLog.d(TAG, "receiveMessageFromNativeServer");
        // 这里启用线程,去读取,因为读取是阻塞操作,会造成问题的.
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    if (inputStream == null) {
                        return;
                    }
                    if (connectCallback != null) {
                        connectCallback.onNativeServerConnected(SocketManager.AppClientStatus.CONNECT_SUCCESS);
                    }
                    byte[] bytes = new byte[1024];
                    int read = 0;
                    while (read != -1) {
                        read = inputStream.read(bytes);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bos.write(bytes, 0, read);
                        while (read== 1024) {
                            read = inputStream.read(bytes);
                            bos.write(bytes, 0, read);
                        }
                        byte[] data = bos.toByteArray();
                        SocketComm.NativeSocketCommand command = SocketComm.NativeSocketCommand.parseFrom(data);
                        JLog.d(TAG, "client receive = " + command);
                    }
                } catch (IOException e) {
                    JLog.e(TAG, e);
                    if (connectCallback != null) {
                        connectCallback.onNativeServerConnected(SocketManager.AppClientStatus.RECEIVE_MESSAGE_ERROR);
                    }
                }
            }
        });
    }

}
