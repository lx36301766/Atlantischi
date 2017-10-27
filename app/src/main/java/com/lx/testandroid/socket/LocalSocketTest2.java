package com.lx.testandroid.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Bundle;
import android.util.Log;

/**
 * Created on 26/10/2017.
 *
 * @author lx
 */

public class LocalSocketTest2 {

    final static String SOCKET_ADDRESS = "LocalSocketAddress";

    LocalSocket localSocketSender,localSocketReceiver;

    LocalServerSocket serverSocket;

    protected void onCreate(Bundle savedInstanceState) {
        try {
            serverSocket = new LocalServerSocket(SOCKET_ADDRESS);
            localSocketSender = new LocalSocket();
            localSocketSender.connect(new LocalSocketAddress(SOCKET_ADDRESS));
            localSocketReceiver = serverSocket.accept();
            send();
            receiver();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(){
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    OutputStream outputStream = localSocketSender.getOutputStream();
                    outputStream.write(20);
                    outputStream.flush();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void receiver(){
        new Thread(){
            @Override
            public void run() {
                try {
                    InputStream inputStream = localSocketReceiver.getInputStream();
                    int ret = inputStream.read();
                    Log.e("ret","" + ret);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
