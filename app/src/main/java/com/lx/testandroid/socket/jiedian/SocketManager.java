package com.lx.testandroid.socket.jiedian;

import com.google.protobuf.GeneratedMessageLite;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import protobuf.nativesocket.SocketComm;

/**
 * Created on 03/11/2017.
 *
 * @author lx
 */

public class SocketManager {

    public static final String TAG = "SocketManager";

    public interface SocketObserver<T extends GeneratedMessageLite> {
        /**
         *
         * Response Body <br>
         *
         * {@link SocketComm.CMsgBodyDeviceInfoRes} <br>
         * {@link SocketComm.CMsgBodySlotStatusRes} <br>
         * {@link SocketComm.CMsgBodyOpenDev} <br>
         * {@link SocketComm.CMsgBodyInstallBatteryResult} <br>
         * {@link SocketComm.CMsgBodyGetBatteryPassword} <br>
         * {@link SocketComm.CMsgBodyBackBattery} <br>
         * {@link SocketComm.CMsgBodyWifiProbeRes} <br>
         * {@link SocketComm.CMsgBodyWifiProbeStartRes} <br>
         * {@link SocketComm.CMsgBodyWifiProbeStopRes} <br>
         * {@link SocketComm.CMsgBodySubUpgradeStatusRes} <br>
         */
        void onCmdReceived(@Nullable T data);
    }

    public enum AppClientStatus {
        CONNECT_SUCCESS,
        CONNECT_FAILED,
        RECEIVE_MESSAGE_ERROR,
    }

    public enum AppServerStatus {
        START_SUCCESS,
        START_FAILED,
        CLIENT_CONNECTED,
        CLIENT_ERROR,
    }

    public interface ConnectCallback {

        /**
         *  app client ---> native server <br>
         *
         *  CONNECT_SUCCESS 之后app才能主动发送消息到native
         */
        void onNativeServerConnected(AppClientStatus status);

        /**
         * native client ---> app server <br>
         *
         *  CLIENT_CONNECTED 之后app可以接收到native发过来的消息
         */
        void onAppServerStart(AppServerStatus status);
    }

    private static class Holder {
        private static SocketManager INSTANCE = new SocketManager();
    }

    public static SocketManager getInstance() {
        return Holder.INSTANCE;
    }

    private JSocketServer mSocketServer;
    private JSocketClient mSocketClient;

    private SocketCommandCenter mSocketCommandCenter;

    public void init(ConnectCallback connectCallback) {
        JLog.d(TAG, "init socket");
        mSocketServer = new JSocketServer();
        mSocketServer.start(connectCallback);

        mSocketClient = new JSocketClient();
        mSocketClient.connect(connectCallback);

        mSocketCommandCenter = new SocketCommandCenter();
    }

    public void close() {
        JLog.d(TAG, "close socket");
        if (mSocketClient != null) {
            mSocketClient.close();
        }
        if (mSocketServer != null) {
            mSocketServer.close();
        }
    }

    public void addObserver(SocketComm.CmdType cmdType, SocketObserver observer) {
        mSocketCommandCenter.addObserver(cmdType, observer);
        JLog.d(TAG, "addObserver, cmdType=" + cmdType + ", observer=" + observer);
    }

    public void removeObserver(SocketComm.CmdType cmdType, SocketObserver observer) {
        mSocketCommandCenter.removeObserver(cmdType, observer);
        JLog.d(TAG, "removeObserver, cmdType=" + cmdType + ", observer=" + observer);
    }

    public boolean sendCommand(@NonNull SocketComm.CmdType cmdType) {
        return sendCommand(cmdType, null);
    }

    public boolean sendCommand(@NonNull SocketComm.CmdType cmdType, @Nullable GeneratedMessageLite bodyData) {
        JLog.d(TAG, "sendCommand, cmdType=" + cmdType + ", bodyData=" + bodyData);
        return mSocketCommandCenter.sendCommand(cmdType, bodyData, getSocketClient());
    }

    public SocketCommandCenter getSocketCommandCenter() {
        return mSocketCommandCenter;
    }

    public JSocketServer getSocketServer() {
        return mSocketServer;
    }

    public JSocketClient getSocketClient() {
        return mSocketClient;
    }

}
