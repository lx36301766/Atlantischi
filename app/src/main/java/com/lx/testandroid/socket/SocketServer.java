package com.lx.testandroid.socket;

import java.io.IOException;

import android.net.LocalServerSocket;
import android.net.LocalSocket;

/**
 * Created on 27/10/2017.
 *
 * @author lx
 */

public class SocketServer {

    private static final String NAME = "JD_LOCAL_SOCKET";

    private LocalServerSocket mLocalServerSocket;

    public SocketServer() {
        try {
            mLocalServerSocket = new LocalServerSocket(NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //1
    public void start() {
        try {
            LocalSocket serverSocket = mLocalServerSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //4
    public void receive() {

    }

    //5
    public void response() {

    }

    public void disconnect() {

    }

}
