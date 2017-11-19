package com.lx.testandroid.socket.jiedian;

import static protobuf.nativesocket.SocketComm.CmdType.OPEN_ACK;

import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;

import android.os.Handler;
import protobuf.CommonEnum;
import protobuf.nativesocket.SocketComm;

/**
 * Created on 02/11/2017.
 *
 * @author lx
 */

public class SocketMocker {

    private static final String TAG = "SocketMocker";
    public static String tempSN = "A011KB3CDF";

    public boolean sendMessageToNative(byte[] data) {
        SocketComm.NativeSocketCommand command;
        try {
            command = SocketComm.NativeSocketCommand.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return false;
        }
        switch (command.getCmdtype()) {
            case DEVICE_INFO_REQ:
                mock_DEVICE_INFO_REQ();
                break;
            case SLOT_STATUS_REQ:
                mock_SLOT_STATUS_REQ(command.getSlotStatusReqBody());
                break;
            case OPEN_DEV:
                mock_OPEN_DEV(command.getOpenDevBody());
                break;
            case START_INSTALL_BATTERY:
                mock_START_INSTALL_BATTERY();
                break;
            case STOP_INSTALL_BATTERY:
                mock_STOP_INSTALL_BATTERY();
                break;
            case GET_BATTERY_PASSWORD_ACK:
                mock_GET_BATTERY_PASSWORD_ACK(command.getGetBatteryPasswordAckBody());
                break;
            case UPDATE_DEVICE_PARAMS:
                mock_UPDATE_DEVICE_PARAMS(command.getUpdateDeviceParamsBody());
                break;
            case WIFI_PROBE_REQ:
                mock_WIFI_PROBE_REQ();
                break;
            case WIFI_PROBE_START_REQ:
                mock_WIFI_PROBE_START_REQ();
                break;
            case WIFI_PROBE_STOP_REQ:
                mock_WIFI_PROBE_STOP_REQ();
                break;
            case SUB_UPDRADE_STAT_REQ:
                mock_SUB_UPDRADE_STAT_REQ();
                break;
            default:
                throw new IllegalArgumentException("error command --->" + command.getCmdtype());
        }
        return true;
    }

    private void delaySendRes(final SocketComm.CmdType cmdType, final GeneratedMessageLite body) {
        delaySendRes(cmdType, body, 1000);
    }

    private void delaySendRes(final SocketComm.CmdType cmdType, final GeneratedMessageLite body, long delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SocketComm.NativeSocketCommand.Builder builder = SocketComm.NativeSocketCommand.newBuilder();
                switch (cmdType) {
                    case DEVICE_INFO_RES:
                        builder.setDeviceInfoResBody((SocketComm.CMsgBodyDeviceInfoRes) body);
                        break;
                    case SLOT_STATUS_RES:
                        builder.setSlotStatusResBody((SocketComm.CMsgBodySlotStatusRes) body);
                        break;
                    case OPEN_ACK:
                        builder.setOpenDevAckBody((SocketComm.CMsgBodyOpenDevAck) body);
                        break;
                    case INSTALL_BATTERY_RESULT:
                        builder.setInstallBatteryResultBody((SocketComm.CMsgBodyInstallBatteryResult) body);
                        break;
                    case GET_BATTERY_PASSWORD_ACK:
                        builder.setGetBatteryPasswordAckBody((SocketComm.CMsgBodyGetBatteryPasswordAck) body);
                        break;
                    case BACK_BATTERY:
                        builder.setBackBatteryBody((SocketComm.CMsgBodyBackBattery) body);
                        break;
                    case WIFI_PROBE_RES:
                        builder.setWifiProbeResBody((SocketComm.CMsgBodyWifiProbeRes) body);
                        break;
                    case WIFI_PROBE_START_RES:
                        builder.setWifiProbeStartResBody((SocketComm.CMsgBodyWifiProbeStartRes) body);
                        break;
                    case WIFI_PROBE_STOP_RES:
                        builder.setWifiProbeStopResBody((SocketComm.CMsgBodyWifiProbeStopRes) body);
                        break;
                    case SUB_UPDRADE_STAT_RES:
                        builder.setSubUpgradeStatusResBody((SocketComm.CMsgBodySubUpgradeStatusRes) body);
                        break;
                }
                SocketManager.getInstance().getSocketCommandCenter().notifyObserver(builder.setCmdtype(cmdType).build());
            }
        }, delayMillis);
    }





    //required int32 slot_num = 1;		//卡槽编号
    private void mock_DEVICE_INFO_REQ() {
        SocketComm.CMsgBodyDeviceInfoRes res = SocketComm.CMsgBodyDeviceInfoRes.newBuilder()
                .setTotalSlotNum(24)
                .build();
        delaySendRes(SocketComm.CmdType.DEVICE_INFO_RES, res);
    }

    //required int32 slot_num = 1;		//卡槽编号

    //required int32 slot_num = 1; 														//卡槽编号
    //required HEART_BEAT_SLOT_CODE slot_status = 2;					//卡槽状况,参考HEART_BEAT_SLOT_CODE
    //required string batterysn = 3; 													//为全"0"的时候就是空卡槽
    //required float  temprature = 4;													//电源温度
    //required int32 voltage = 5;															//电源电压
    //required int32 fullChargeCapacity = 6;									//电源最大容量（mAh)
    //required int32 remainingCapacity = 7;										//剩余容量（mAh)
    //required int32 averageCurrent = 8;											//充放电电流(mA)
    //required int32 cycleCount = 9;													//循环次数
    //required int32 bmsSafetyStatus = 10;										//异常状态，预留，BMS内部状态
    //required int32 bmsFlags = 11; 													//预留，BMS内部充满标志
    //required HEART_BEAT_BATTERY_CODE battery_status = 12;		//电池异常状态,参考HEART_BEAT_BATTERY_CODE
    //required ENABLE_BORROW_STATUS enableStatus = 13;				//可借标志(容量达到一定才能允许用户借），参考ENABLE_BORROW_STATUS
    private void mock_SLOT_STATUS_REQ(SocketComm.CMsgBodySlotStatusReq slotStatusReqBody) {
        int slot_num = slotStatusReqBody.getSlotNum();
        JLog.d(TAG, "mock_SLOT_STATUS_REQ, slot_num=" + slot_num);

        SocketComm.CMsgBodySlotStatusRes res = SocketComm.CMsgBodySlotStatusRes.newBuilder()
                .setSlotNum(slot_num)
                .setSlotStatus(CommonEnum.HEART_BEAT_SLOT_CODE.HBS_STATUS_OK)
                .setBatterysn(ByteString.copyFrom(tempSN.getBytes()))
                .setTemprature(56.6f)
                .setVoltage(220)
                .setFullChargeCapacity(5000)
                .setRemainingCapacity(2000)
                .setAverageCurrent(200)
                .setCycleCount(5)
                .setBmsSafetyStatus(1)
                .setBmsFlags(1)
                .setBatteryStatus(CommonEnum.HEART_BEAT_BATTERY_CODE.HBB_STATUS_OK)
                .setEnableStatus(CommonEnum.ENABLE_BORROW_STATUS.ENABLE_STATUS)
                .build();
        delaySendRes(SocketComm.CmdType.SLOT_STATUS_RES, res);
    }

//    required DEVACTION action = 1;			//动作指令,1=借，2=还.
//    required string tranid = 2;					//交易ID
//    required int32 slot_num = 3;				//卡槽编号
//    required string password = 4;				//密码，借有效，还无效.

//    required DEVACTION action = 1;									//动作指令,1=借，2=还.
//    required string tranid = 2;											//交易ID
//    required int32 slot_num = 3;										//卡槽编号
//    required string batterysn = 4;									//还的时候需要发送电源ID
//    required BATTERY_BORROW_RETURN_CODE state = 5; 	// 电源取走或插入是否成功状态，参考BATTERY_BORROW_RETURN_CODE
    private void mock_OPEN_DEV(SocketComm.CMsgBodyOpenDev openDev) {
        CommonEnum.DEVACTION action = openDev.getAction();
        String tranid = new String(openDev.getTranid().toByteArray());
        int slot_num = openDev.getSlotNum();
        String password = openDev.getPassword().toString();
        JLog.d(TAG, "mock_OPEN_DEV, action=" + action + ", tranid=" + tranid,
                ", slot_num=" + slot_num + ", password=" + password);

        SocketComm.CMsgBodyOpenDevAck res = SocketComm.CMsgBodyOpenDevAck.newBuilder()
                .setAction(openDev.getAction())
                .setTranid(openDev.getTranid())
                .setSlotNum(slot_num)
                .setBatterysn(ByteString.copyFrom(tempSN.getBytes()))
                .setState(CommonEnum.BATTERY_BORROW_RETURN_CODE.BBR_STATUS_OK)
                .build();
        delaySendRes(OPEN_ACK, res, 5000);
    }

//    required InstallBatteryResCode install_res = 1;			//安装电源结果，参考InstallBatteryResCode
//    required int32 slot_num = 2;												//卡槽ID
//    required int32 next_slot_num = 3; 									//下一个要安装的卡槽ID
//    required InstallBatteryOpenCode open_next_slot = 4;	//打开下一个卡槽的结果，参考InstallBatteryOpenCode
    private void mock_START_INSTALL_BATTERY() {
        SocketComm.CMsgBodyInstallBatteryResult res = SocketComm.CMsgBodyInstallBatteryResult.newBuilder()
                .setInstallRes(CommonEnum.InstallBatteryResCode.RESCODEOK)
                .setSlotNum(22)
                .setNextSlotNum(13)
                .setOpenNextSlot(CommonEnum.InstallBatteryOpenCode.OPENCODEOK)
                .build();
        delaySendRes(SocketComm.CmdType.INSTALL_BATTERY_RESULT, res);
    }

    private void mock_STOP_INSTALL_BATTERY() {
        mock_START_INSTALL_BATTERY();
    }

//    required string battery_sn = 1;	//电源SN

//    required int32 ret = 1;						//获取密码结果（0： 成功	1： 失败）
//    required string battery_pwd = 2;	//电源密码
//    required string battery_sn = 3;		//电源SN
    private void mock_GET_BATTERY_PASSWORD_ACK(SocketComm.CMsgBodyGetBatteryPasswordAck getBatteryPasswordAck) {
        String battery_sn = getBatteryPasswordAck.getBatterySn().toString();
        JLog.d(TAG, "mock_GET_BATTERY_PASSWORD_ACK, battery_sn=" + battery_sn);

        SocketComm.CMsgBodyGetBatteryPasswordAck res = SocketComm.CMsgBodyGetBatteryPasswordAck.newBuilder()
                .setRet(0)
                .setBatteryPwd(ByteString.copyFrom("fdsfew23212".getBytes()))
                .setBatterySn(ByteString.copyFrom(battery_sn.getBytes()))
                .build();
        delaySendRes(SocketComm.CmdType.GET_BATTERY_PASSWORD_ACK, res);
    }

//    required bytes cmd = 1;	//cmd
    private void mock_UPDATE_DEVICE_PARAMS(SocketComm.CMsgBodyUpdateDeviceParams updateDeviceParams) {
        String cmd = updateDeviceParams.getCmd().toString();
        JLog.d(TAG, "mock_UPDATE_DEVICE_PARAMS, cmd=" + cmd);
    }

//    required string macaddr = 1;					//设备物理MAC地址
//    required bool is_ap = 2;							//预留，设备是否为无线路由器：1位是，默认为0
//    required int32 dev_act = 3;						//设备行为：0=unknown;1=income；2=remain;3=leave
//    required int32 dev_channel = 4;				//设备通道
//    required int32 dev_signal = 5;				//设备信号强度
//    required uint64 dev_act_time = 6;			//进店或离开UTC时间 (time_t，long int).
    private void mock_WIFI_PROBE_REQ() {
        SocketComm.CMsgBodyWifiProbeRes.Builder builder = SocketComm.CMsgBodyWifiProbeRes.newBuilder();
        for (int i = 0; i < 5; i++) {
            SocketComm.WifiProbeElement wifiProbeElement = SocketComm.WifiProbeElement.newBuilder()
                    .setMacaddr(ByteString.copyFrom("03:f3:34:a5:53:23".getBytes()))
                    .setIsAp(true)
                    .setDevAct(1)
                    .setDevSignal(32)
                    .setDevActTime(System.currentTimeMillis())
                    .build();
            builder.setWpelement(i, wifiProbeElement);
        }
        delaySendRes(SocketComm.CmdType.WIFI_PROBE_RES, builder.build());
    }

    //required int32 status = 1;			//0=失败；1=成功
    private void mock_WIFI_PROBE_START_REQ() {
        SocketComm.CMsgBodyWifiProbeStartRes res = SocketComm.CMsgBodyWifiProbeStartRes.newBuilder()
                .setStatus(1)
                .build();
        delaySendRes(SocketComm.CmdType.WIFI_PROBE_START_RES, res);
    }

//    required int32 status = 1;			//0=失败；1=成功
    private void mock_WIFI_PROBE_STOP_REQ() {
        SocketComm.CMsgBodyWifiProbeStopRes res = SocketComm.CMsgBodyWifiProbeStopRes.newBuilder()
                .setStatus(1)
                .build();
        delaySendRes(SocketComm.CmdType.WIFI_PROBE_STOP_RES, res);
    }

//    required int32 status = 1;			//0=已完成；1=正在升级；2=升级失败
    private void mock_SUB_UPDRADE_STAT_REQ() {
        SocketComm.CMsgBodySubUpgradeStatusRes res = SocketComm.CMsgBodySubUpgradeStatusRes.newBuilder()
                .setStatus(1)
                .build();
        delaySendRes(SocketComm.CmdType.SUB_UPDRADE_STAT_RES, res);
    }

}
