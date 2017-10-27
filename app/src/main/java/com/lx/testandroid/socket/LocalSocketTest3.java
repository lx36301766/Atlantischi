package com.lx.testandroid.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;

public class LocalSocketTest3 extends Activity {
    /**
     * Called when the activity is first created.
     */
    final static String TAG = "LocketSocketTest3";
    LocalServerSocket localServerSocket = null;
    OutputStream clientOutStream = null;
    OutputStream serverOutStream = null;
    InputStream serverInStream = null;
    InputStream clientInStream = null;

    Thread mSocketThread = null;
    Handler mHandler = null;
    String mServerSend, mServerReceive, mClientSend, mClientReceive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        setContentView(R.layout.main);
        try {
            CreateSocket();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        mSocketThread = new Thread() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int a = 0, b;
                for (; ; ) {
                    try {
                        a = serverInStream.read();
                        if (a != -1) {
                            serverOutStream.write(a + 3);
                            Log.v(TAG, "serverInStream:" + a);
                        }
                        b = clientInStream.read();
                        if (b > 0 && b < 255) {
                            Log.v(TAG, "clientInStream:" + b);
                            clientOutStream.write(b + 3);
                        }
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

            }

            ;
        };
        mSocketThread.start();

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        try {
            mSocketThread.stop();
            localServerSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        super.onStop();

    }

    private void CreateSocket() throws IOException {
        localServerSocket = new LocalServerSocket("com.user.LocalServerSocket");
        LocalSocket serverSocket = localServerSocket.accept();

        LocalSocket clientSocket = new LocalSocket();
        clientSocket.connect(new LocalSocketAddress("com.user.LocalServerSocket"));


        clientOutStream = clientSocket.getOutputStream();
        serverOutStream = serverSocket.getOutputStream();
        serverInStream = serverSocket.getInputStream();
        clientInStream = clientSocket.getInputStream();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            try {
                clientOutStream.write(12);
                Log.v(TAG, "clientOutStream:" + 12);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return super.onTouchEvent(event);
    }

}