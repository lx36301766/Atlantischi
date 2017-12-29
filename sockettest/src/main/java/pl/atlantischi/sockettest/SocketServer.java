package pl.atlantischi.sockettest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
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

public class SocketServer {

    private static final String TAG = "SocketTest";

    private static final String APP_SERVER_NAME = "java_server_socket";

    private SparseArray<LocalSocket> mLocalSocketMap = new SparseArray<>();

    private ExecutorService mExecutorService = Executors.newCachedThreadPool();
    private LocalServerSocket mLocalServerSocket;

    public void start() {
        Log.d(TAG, "SocketServer start, app_server_name = " + APP_SERVER_NAME);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    mLocalServerSocket = new LocalServerSocket(APP_SERVER_NAME);
                } catch (IOException e) {
                    Log.d(TAG, e, e.getMessage());
                    return;
                }
                Log.d(TAG, "SocketServer start success");
                while (!mExecutorService.isShutdown()) {
                    // 这个方法是阻塞的,有Client连接上来的时候,这里就会回调.
                    try {
                        waitMessageFromNativeClient(mLocalServerSocket.accept());
                        Log.d(TAG, "SocketServer client connected");
                    } catch (IOException e) {
                        Log.e(TAG, "SocketServer client connect Error");
                        break;
                    }
                }
            }
        });
    }

    private void waitMessageFromNativeClient(final LocalSocket localSocket) {
        Log.d(TAG, "SocketServer waitMessageFromNativeClient");
        // 读取的是一个阻塞的过程,如果在这里不开一个线程的,那么会一直阻塞在这里,后面的代码得不到执行
        mExecutorService.submit(new Runnable() {

            @Override
            public void run() {
                int id = (int) Thread.currentThread().getId();
                mLocalSocketMap.put(id, localSocket);
                try {
                    InputStream inputStream = localSocket.getInputStream();
                    byte[] bytes = new byte[4096];
                    int read = 0;
                    while (read != -1) {
                        // read next byte
                        read = inputStream.read(bytes);
                        Log.d(TAG, "--------------------------------------------------------------------------------");
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bos.write(bytes, 0, read);

                        // native端发送的时候，结构体不是一次性发送出去的，是边序列化边发送，一个结构体可能要接受好几次，
                        // 发送完之后会在末尾添加一个0，所以这里需要判断最后一位是否是0，为0才表示数据流已经接收完成
                        byte[] tmp = bos.toByteArray();
                        Log.d(TAG, "SocketServer, read=" + read + ", available=" + inputStream.available());
                        Log.d(TAG, "SocketServer, receive byte data, tmp data=" + Arrays.toString(tmp));
                        while (tmp[tmp.length - 1] != 0 || inputStream.available() != 0) {
                            read = inputStream.read(bytes);
                            bos.write(bytes, 0, read);
                            tmp = bos.toByteArray();
                            Log.d(TAG, "SocketServer, read=" + read + ", available=" + inputStream.available());
                            Log.d(TAG, "SocketServer, continue receive byte data, tmp data=" + Arrays.toString(tmp));
                        }
                        bos.close();

                        //去掉最后一位多余的0，不然pb解析会报错
                        byte[] data = Arrays.copyOf(tmp, tmp.length - 1);
                        Log.d(TAG, "SocketServer, start parse data");
                        try {
                            SocketComm.NativeSocketCommand command = SocketComm.NativeSocketCommand.parseFrom(data);
//                            SocketManager.getInstance().notifyObserver(command);
                            Log.e(TAG, "SocketServer, parse data, command=" + command);
                        } catch (IOException e) {
                            Log.e(TAG, "SocketServer, parse data Error!!!");
                            Log.e(TAG, e, e.getMessage());
                        }
                        Log.d(TAG, "--------------------------------------------------------------------------------");
                    }
                    Log.d(TAG, "SocketServer, waitMessageFromNativeClient end~~~");
                } catch (IOException e) {
                    Log.e(TAG, "SocketServer, waitMessageFromNativeClient Error");
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
        Log.d(TAG, "SocketServer, responseToNativeClient");
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
            mExecutorService.shutdown();
            Log.d(TAG, "SocketServer closed Success");
        } catch (IOException e) {
            Log.e(TAG, "SocketServer closed Error");
        }
    }

}
