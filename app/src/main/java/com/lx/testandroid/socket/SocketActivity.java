package com.lx.testandroid.socket;

import java.io.IOException;

import android.app.Activity;
import android.net.LocalServerSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.lx.testandroid.R;

public class SocketActivity extends Activity {

    @BindView(R.id.server_start) Button serverStartBtn;

    @BindView(R.id.server_receive) TextView serverReceiveText;

    @BindView(R.id.server_send) Button serverSendBtn;

    @BindView(R.id.client_connect) Button clientConnectBtn;

    @BindView(R.id.client_send) Button clientSendBtn;

    @BindView(R.id.client_receive) TextView clinetReceiveText;

    @BindView(R.id.close) Button closeBtn;

    LocalServerSocket localServerSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);

        final LocalSocketTest5.SocketServer socketServer = new LocalSocketTest5.SocketServer();
        serverStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Thread(new LocalSocketTest1.ServerThread()).start();
                try {
                    socketServer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        serverSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketServer.send("server msg");

            }
        });

//        final LocalSocketTest1.ClientConnect clientConnect = new LocalSocketTest1.ClientConnect();
        final LocalSocketTest5.SocketClient socketClient = new LocalSocketTest5.SocketClient();

        clientConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LocalSocketTest1.ClientConnect clientConnect = new LocalSocketTest1.ClientConnect();
//                clientConnect.connect();

                socketClient.connect();
            }
        });
        clientSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                clientConnect.send("l43432cdfsfs");
                socketClient.send("client msg");
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socketClient.close();
                if (localServerSocket != null) {
                    try {
                        localServerSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
