package com.lx.testandroid.socket.jiedian;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import com.google.protobuf.GeneratedMessageLite;
import com.lx.testandroid.socket.jiedian.SocketManager.SocketObserver;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import protobuf.nativesocket.SocketComm;

/**
 * Created on 01/11/2017.
 * @author lx
 *
 * APP -> Native
 *
 *    DEVICE_INFO_REQ = 1;              //设备信息请求
 *    SLOT_STATUS_REQ = 3;              //卡槽状态请求
 *    OPEN_DEV = 5;                     //打开设备（借还）
 *    START_INSTALL_BATTERY = 7;        //开始安装电源
 *    STOP_INSTALL_BATTERY = 8;         //结束安装电源
 *    GET_BATTERY_PASSWORD_ACK = 11;    //获取电源密码返回
 *    UPDATE_DEVICE_PARAMS = 12;        //更新柜机参数（万能指令）
 *    WIFI_PROBE_REQ = 14;              //WiFi 探针信息请求
 *    WIFI_PROBE_START_REQ = 16;        //WiFi探针功能开启请求
 *    WIFI_PROBE_STOP_REQ = 18;         //WiFi探针功能关闭请求
 *    SUB_UPDRADE_STAT_REQ = 20;        //辅控板升级状态请求
 *
 * Req protobuf body
 *
 *    {@link SocketComm.CMsgBodySlotStatusReq} <br>
 *    {@link SocketComm.CMsgBodyOpenDev} <br>
 *    {@link SocketComm.CMsgBodyInstallBatteryResult} <br>
 *    {@link SocketComm.CMsgBodyUpdateDeviceParams} <br>
 *
 *
 *
 * Native -> APP
 *
 *    DEVICE_INFO_RES = 2;              //设备信息响应
 *    SLOT_STATUS_RES = 4;              //卡槽状态响应
 *    OPEN_ACK = 6;                     //设备响应（借还响应
 *    INSTALL_BATTERY_RESULT = 9;       //安装电源结果
 *    GET_BATTERY_PASSWORD = 10;        //获取电源密码请求
 *    BACK_BATTERY = 13;                //异常超时
 *    WIFI_PROBE_RES = 15;              //WiFi 探针信息响应
 *    WIFI_PROBE_START_RES = 17;        //WiFi探针功能开启响应
 *    WIFI_PROBE_STOP_RES = 19;         //WiFi探针功能关闭响应
 *    SUB_UPDRADE_STAT_RES = 21;        //辅控板升级状态响应
 *
 * Res protobuf body
 *
 *    {@link SocketComm.CMsgBodyDeviceInfoRes} <br>
 *    {@link SocketComm.CMsgBodySlotStatusRes} <br>
 *    {@link SocketComm.CMsgBodyOpenDev} <br>
 *    {@link SocketComm.CMsgBodyInstallBatteryResult} <br>
 *    {@link SocketComm.CMsgBodyGetBatteryPassword} <br>
 *    {@link SocketComm.CMsgBodyBackBattery} <br>
 *    {@link SocketComm.CMsgBodyWifiProbeRes} <br>
 *    {@link SocketComm.CMsgBodyWifiProbeStartRes} <br>
 *    {@link SocketComm.CMsgBodyWifiProbeStopRes} <br>
 *    {@link SocketComm.CMsgBodySubUpgradeStatusRes} <br>
 *
 */
public class SocketCommandCenter {

    private Map<SocketComm.CmdType, Set<SocketObserver>> mObservers = new WeakHashMap<>();

    void addObserver(SocketComm.CmdType cmdType, SocketObserver observer) {
        synchronized(this) {
            if (!mObservers.containsKey(cmdType)) {
                //put WeakHashSet
                mObservers.put(cmdType, Collections.newSetFromMap(new WeakHashMap<SocketObserver, Boolean>()));
            }
            mObservers.get(cmdType).add(observer);
        }
    }

    void removeObserver(SocketComm.CmdType cmdType, SocketObserver observer) {
        synchronized(this) {
            if (!mObservers.containsKey(cmdType)) {
                return;
            }
            Set<SocketObserver> observers = mObservers.get(cmdType);
            if (observers.contains(observer)) {
                observers.remove(observer);
            }
        }
    }

    void notifyObserver(SocketComm.NativeSocketCommand command) {
        GeneratedMessageLite data = getBodyByCommand(command);
        if (data == null) {
            return;
        }
        synchronized(this) {
            SocketComm.CmdType cmdType = command.getCmdtype();
            if (!mObservers.containsKey(cmdType)) {
                return;
            }
            Set<SocketObserver> observers = mObservers.get(cmdType);
            for (SocketObserver observer : observers) {
                if (observer != null) {
                    observer.onCmdReceived(data);
                }
            }
        }
    }

    /**
     * {@link SocketComm.CmdType.DEVICE_INFO_RES} 设备信息响应
     * {@link SocketComm.CmdType.SLOT_STATUS_RES} 卡槽状态响应
     * {@link SocketComm.CmdType.OPEN_ACK} 设备响应（借还响应）
     * {@link SocketComm.CmdType.INSTALL_BATTERY_RESULT} 安装电源结果
     * {@link SocketComm.CmdType.GET_BATTERY_PASSWORD} 获取电源密码请求
     * {@link SocketComm.CmdType.BACK_BATTERY} 异常超时
     * {@link SocketComm.CmdType.WIFI_PROBE_RES} WiFi探针信息响应
     * {@link SocketComm.CmdType.WIFI_PROBE_START_RES} WiFi探针功能开启响应
     * {@link SocketComm.CmdType.WIFI_PROBE_STOP_RES} WiFi探针功能关闭响应
     * {@link SocketComm.CmdType.SUB_UPDRADE_STAT_RES} 辅控板升级状态响应
     */
    private GeneratedMessageLite getBodyByCommand(SocketComm.NativeSocketCommand command) {
        GeneratedMessageLite data = null;
        switch (command.getCmdtype()) {
            case DEVICE_INFO_RES:
                data = command.getDeviceInfoResBody();
                break;
            case SLOT_STATUS_RES:
                data = command.getSlotStatusResBody();
                break;
            case OPEN_ACK:
                data = command.getOpenDevAckBody();
                break;
            case INSTALL_BATTERY_RESULT:
                data = command.getInstallBatteryResultBody();
                break;
            case GET_BATTERY_PASSWORD:
                data = command.getGetBatteryPasswordBody();
                break;
            case BACK_BATTERY:
                data = command.getUpdateDeviceParamsBody();
                break;
            case WIFI_PROBE_RES:
                data = command.getWifiProbeResBody();
                break;
            case WIFI_PROBE_START_RES:
                data = command.getWifiProbeStartResBody();
                break;
            case WIFI_PROBE_STOP_RES:
                data = command.getWifiProbeStopResBody();
                break;
            case SUB_UPDRADE_STAT_RES:
                data = command.getSubUpgradeStatusResBody();
                break;
        }
        return data;
    }

    /**
     *
     * {@link SocketComm.CmdType.DEVICE_INFO_REQ} 设备信息请求 <br>
     * {@link SocketComm.CmdType.SLOT_STATUS_REQ}  {@link SocketComm.CMsgBodySlotStatusReq} 卡槽状态请求 <br>
     * {@link SocketComm.CmdType.OPEN_DEV}  {@link SocketComm.CMsgBodyOpenDev} 打开设备（借还) <br>
     * {@link SocketComm.CmdType.START_INSTALL_BATTERY} 开始安装电源 <br>
     * {@link SocketComm.CmdType.STOP_INSTALL_BATTERY} 结束安装电源 <br>
     * {@link SocketComm.CmdType.GET_BATTERY_PASSWORD_ACK} {@link SocketComm.CMsgBodyGetBatteryPasswordAck} 获取电源密码返回 <br>
     * {@link SocketComm.CmdType.UPDATE_DEVICE_PARAMS} {@link SocketComm.CMsgBodyUpdateDeviceParams} 更新柜机参数 <br>
     * {@link SocketComm.CmdType.WIFI_PROBE_REQ} WiFi 探针信息请求 <br>
     * {@link SocketComm.CmdType.WIFI_PROBE_START_REQ} WiFi探针功能开启请求 <br>
     * {@link SocketComm.CmdType.WIFI_PROBE_STOP_REQ} WiFi探针功能关闭请求 <br>
     * {@link SocketComm.CmdType.SUB_UPDRADE_STAT_REQ} 辅控板升级状态请求 <br>
     *
     */
    boolean sendCommand(@NonNull SocketComm.CmdType cmdType, @Nullable GeneratedMessageLite bodyData,
                               JSocketClient socketClient) {
        SocketComm.NativeSocketCommand.Builder commandBuilder = SocketComm.NativeSocketCommand.newBuilder();
        switch (cmdType) {
            case DEVICE_INFO_REQ:
                //null body
                break;
            case SLOT_STATUS_REQ:
                if (bodyData instanceof SocketComm.CMsgBodySlotStatusReq) {
                    commandBuilder.setSlotStatusReqBody((SocketComm.CMsgBodySlotStatusReq) bodyData);
                }
                break;
            case OPEN_DEV:
                if (bodyData instanceof SocketComm.CMsgBodyOpenDev) {
                    commandBuilder.setOpenDevBody((SocketComm.CMsgBodyOpenDev) bodyData);
                }
                break;
            case START_INSTALL_BATTERY:
                //null body
                break;
            case STOP_INSTALL_BATTERY:
                //null body
                break;
            case GET_BATTERY_PASSWORD_ACK:
                if (bodyData instanceof SocketComm.CMsgBodyGetBatteryPasswordAck) {
                    commandBuilder.setGetBatteryPasswordAckBody((SocketComm.CMsgBodyGetBatteryPasswordAck) bodyData);
                }
                break;
            case UPDATE_DEVICE_PARAMS:
                if (bodyData instanceof SocketComm.CMsgBodyUpdateDeviceParams) {
                    commandBuilder.setUpdateDeviceParamsBody((SocketComm.CMsgBodyUpdateDeviceParams) bodyData);
                }
                break;
            case WIFI_PROBE_REQ:
                //null body
                break;
            case WIFI_PROBE_START_REQ:
                //null body
                break;
            case WIFI_PROBE_STOP_REQ:
                //null body
                break;
            case SUB_UPDRADE_STAT_REQ:
                //null body
                break;
            default:
                //TODO
                return false;
        }
        SocketComm.NativeSocketCommand command = commandBuilder.setCmdtype(cmdType).build();
        return socketClient != null && socketClient.sendMessageToNativeServer(command.toByteArray());
    }

}
