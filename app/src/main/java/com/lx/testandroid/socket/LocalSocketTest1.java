package com.lx.testandroid.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import android.net.Credentials;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

/**
 * Created on 26/10/2017.
 *
 * @author lx
 */

public class LocalSocketTest1 {

    private static final String TAG = "LocketSocketTest";
    private static final String FLAG = ",";
    private static final String NAME = "com.repackaging.localsocket";

    //client
    public static class ClientConnect {
        private LocalSocket Client;
        private PrintWriter os;
        private BufferedReader is;
        private int timeout = 30000;

        public void connect() {
            try {
                Client = new LocalSocket();
                Client.connect(new LocalSocketAddress(NAME));
                Client.setSoTimeout(timeout);
                Log.i(TAG, "client connect");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String data) {
            try {
                os = new PrintWriter(Client.getOutputStream());
                os.println(data);
//                os.println(FLAG);
                os.flush();
                Log.i(TAG, "client send");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String recv() {
            Log.i(TAG, "recv");
            String result = null;
            try {
                is = new BufferedReader(new InputStreamReader(Client.getInputStream()));
                result = is.readLine();
                Log.i(TAG, "recv=" + result);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            return result;
        }

        public void close() {
            try {
                is.close();
                os.close();
                Client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //server
    public static class ServerThread implements Runnable {

        @Override
        public void run() {
            Log.d(TAG, "server start");
            LocalServerSocket server = null;
            BufferedReader mBufferedReader = null;
            PrintWriter os = null;
            LocalSocket connect = null;
            String readString = null;
            try {
                server = new LocalServerSocket(NAME);
                while (true) {
                    connect = server.accept();
                    Credentials cre = connect.getPeerCredentials();
                    Log.d(TAG, "accept socket uid:" + cre.getUid());
                    mBufferedReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                    while ((readString = mBufferedReader.readLine()) != null) {
                        if (readString.equals("finish")) {
                            break;
                        }
                        Log.d(TAG, readString);
                    }
                    os = new PrintWriter(connect.getOutputStream());
                    os.println("allow123");
                    os.flush();
                    Log.d(TAG, "send allow");
                }
            } catch (IOException e) {
                Log.e(TAG, "error", e);
            } finally {
                try {
                    if (mBufferedReader != null) mBufferedReader.close();
                    if (os != null) os.close();
                    if (connect != null) connect.close();
                    if (server != null) server.close();
                } catch (IOException e) {
                    Log.e(TAG, "error", e);
                }
            }
        }

    }
}
