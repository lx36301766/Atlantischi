package com.lx.testandroid.socket.jiedian;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.util.SparseArray;
import protobuf.nativesocket.SocketComm;

/**
 * Created on 27/10/2017.
 *
 * @author lx
 */

public class JSocketServer {

    private static final String TAG = SocketManager.TAG;
    public static final String NAME = "JSocket_SERVER_APP4444343534534";

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private LocalServerSocket mLocalServerSocket;

    private SparseArray<LocalSocket> mLocalSocketMap = new SparseArray<>();

    public JSocketServer() {
    }

    public void start(final SocketManager.ConnectCallback connectCallback) {
        JLog.d(TAG, "start server, address= " + NAME);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    mLocalServerSocket = new LocalServerSocket(NAME);
                } catch (IOException e) {
                    JLog.e(TAG, e);
                }
                if (mLocalServerSocket == null) {
                    if (connectCallback != null) {
                        connectCallback.onAppServerStart(SocketManager.AppServerStatus.START_FAILED);
                    }
                    return;
                }
                if (connectCallback != null) {
                    connectCallback.onAppServerStart(SocketManager.AppServerStatus.START_SUCCESS);
                }
                while (true) {
                    // 这个方法是阻塞的,有Client连接上来的时候,这里就会回调.
                    try {
                        receiveMessageFromNativeClient(mLocalServerSocket.accept());
                        if (connectCallback != null) {
                            connectCallback.onAppServerStart(SocketManager.AppServerStatus.CLIENT_CONNECTED);
                        }
                    } catch (IOException e) {
                        JLog.e(TAG, e);
                        if (connectCallback != null) {
                            connectCallback.onAppServerStart(SocketManager.AppServerStatus.CLIENT_ERROR);
                        }
                    }
                }
            }
        });
    }

    private void receiveMessageFromNativeClient(final LocalSocket localSocket) {
        JLog.d(TAG, "receiveMessageFromNativeClient");
        // 读取的是一个阻塞的过程,如果在这里不开一个线程的,那么会一直阻塞在这里,后面的代码得不到执行
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                int id = (int) Thread.currentThread().getId();
                mLocalSocketMap.put(id, localSocket);
                try {
                    InputStream inputStream = localSocket.getInputStream();
                    byte[] bytes = new byte[1024];
                    int read = 0;
                    while (read != -1) {
                        // read next byte
                        read = inputStream.read(bytes);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bos.write(bytes, 0, read);
                        while (read == 1024) {
                            read = inputStream.read(bytes);
                            bos.write(bytes, 0, read);
                        }
                        byte[] data = bos.toByteArray();
                        SocketComm.NativeSocketCommand command = SocketComm.NativeSocketCommand.parseFrom(data);
                        SocketManager.getInstance().getSocketCommandCenter().notifyObserver(command);
                        JLog.d(TAG, "-----receiveMessageFromNativeClient =  " + command);
                    }
                } catch (IOException e) {
                    JLog.e(TAG, e);
                    try {
                        mLocalSocketMap.remove(id);
                        localSocket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void responseToNativeClient(byte[] data) {
        JLog.d(TAG, "responseToNativeClient");
        //模拟对所有已连接的client发送消息
        for (int i = 0; i < mLocalSocketMap.size(); i++) {
            LocalSocket localSocket = mLocalSocketMap.valueAt(i);
            try {
                OutputStream outputStream = localSocket.getOutputStream();
                outputStream.write(data);
                outputStream.flush();
            } catch (IOException e) {
                mLocalSocketMap.removeAt(i);
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if (mLocalServerSocket == null) {
            return;
        }
        try {
            for (int i = 0; i < mLocalSocketMap.size(); i++) {
                LocalSocket localSocket = mLocalSocketMap.valueAt(i);
                localSocket.close();
            }
            mLocalSocketMap.clear();
            mLocalServerSocket.close();
        } catch (IOException e) {
            JLog.e(TAG, e);
        }
        JLog.d(TAG, "server closed");
    }

}
