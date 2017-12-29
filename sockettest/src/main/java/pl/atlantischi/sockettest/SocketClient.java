package pl.atlantischi.sockettest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Build;

/**
 * Created on 27/10/2017.
 *
 * @author lx
 */

public class SocketClient {

    private static final String TAG = "SocketTest";

    private static final String NATIVE_SERVER_NAME = "native_server_socket";

    private InputStream inputStream;
    private OutputStream outputStream;
    private LocalSocket mLocalSocket;

    private ExecutorService mExecutorService = Executors.newCachedThreadPool();

    public void connect() {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "SocketClient start connect");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mLocalSocket = new LocalSocket(LocalSocket.SOCKET_STREAM);
                } else {
                    mLocalSocket = new LocalSocket();
                }
                try {
                    // 连接另外一个进程的Sever连接
                    // LocalSocketService.ADDRESS 这个是连接的地址,相当于每个连接标示符,你想连接到这个上
                    mLocalSocket.connect(new LocalSocketAddress(NATIVE_SERVER_NAME, LocalSocketAddress.Namespace.RESERVED));
//                    mLocalSocket.connect(new LocalSocketAddress("java_server_socket"));
                    Log.d(TAG, "SocketClient, localSocket isConnected = " + mLocalSocket.isConnected());
                    // 设置接收的缓存空间的大小
                    mLocalSocket.setReceiveBufferSize(65536);
                    mLocalSocket.setSendBufferSize(65536);
                    // 获取连接的输入,输出流.
                    outputStream = mLocalSocket.getOutputStream();
                    inputStream = mLocalSocket.getInputStream();
//                    waitMessageFromNativeServer(connectCallback);
                } catch (IOException e) {
                    Log.d(TAG, e.getMessage(), e);
                }
            }
        });
    }

    public boolean sendMessageToNativeServer(byte[] data) {
        if (outputStream == null) {
            return false;
        }
        PrintWriter printWriter = new PrintWriter(outputStream);
        try {
            outputStream.write(data);
            outputStream.write(0);
            printWriter.flush();
            Log.d(TAG, "SocketClient, sendMessageToNativeServer Success");
            return true;
        } catch (IOException e) {
            Log.e(TAG, "SocketClient, sendMessageToNativeServer Error");
        }
        return false;
    }

    public void close() {
        if (mLocalSocket == null) {
            return;
        }
        try {
            mLocalSocket.shutdownInput();
            mLocalSocket.shutdownOutput();
            mLocalSocket.close();
            mExecutorService.shutdown();
        } catch (IOException e) {
            Log.d(TAG, e.getMessage(), e);
        }
        Log.d(TAG, "SocketClient closed");
    }

//    public void waitMessageFromNativeServer(final ConnectCallback connectCallback) {
//        JLog.d(TAG, "SocketClient waitMessageFromNativeServer");
//        // 这里启用线程,去读取,因为读取是阻塞操作,会造成问题的.
//        mExecutorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    if (inputStream == null) {
//                        return;
//                    }
//                    if (connectCallback != null) {
//                        connectCallback.onNativeServerStatusChanged(AppClientStatus.CONNECT_SUCCESS);
//                    }
//                    byte[] bytes = new byte[1024];
//                    int read = 0;
//                    while (read != -1) {
//                        read = inputStream.read(bytes);
//                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        bos.write(bytes, 0, read);
//                        while (read == 1024) {
//                            read = inputStream.read(bytes);
//                            bos.write(bytes, 0, read);
//                        }
//                        byte[] data = bos.toByteArray();
//                        SocketComm.NativeSocketCommand command = SocketComm.NativeSocketCommand.parseFrom(data);
//                        JLog.d(TAG, "SocketClient, receive message from native server, command=" + command);
//                    }
//                    JLog.d(TAG, "SocketClient, waitMessageFromNativeServer end~~~");
//                } catch (IOException e) {
//                    JLog.e(TAG, "SocketClient, waitMessageFromNativeServer Error");
//                    JLog.d(TAG, e);
//                    if (connectCallback != null) {
//                        connectCallback.onNativeServerStatusChanged(AppClientStatus.RECEIVE_MESSAGE_ERROR);
//                    }
//                }
//            }
//        });
//    }

}
