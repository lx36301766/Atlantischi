package pl.atlantischi.sockettest;

import java.io.File;

import com.google.protobuf.ByteString;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import protobuf.CommonEnum;
import protobuf.nativesocket.SocketComm;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SocketTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        connect(null);
        initBugly();
    }

    public void playVideo(View view) {
        startActivity(new Intent(this, DecodeActivity.class));
    }

    SocketClient socketClient = new SocketClient();
    SocketServer socketServer = new SocketServer();

    public void connect(View view) {
        socketServer.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                socketClient.connect();
            }
        }, 500);
    }

    public void close(View view) {
        socketClient.close();
        socketServer.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        close(null);
    }




    public void device_info_req(View view) {
        SocketComm.NativeSocketCommand.Builder commandBuilder = getCommandBuild(SocketComm.CmdType.DEVICE_INFO_REQ);
        socketClient.sendMessageToNativeServer(commandBuilder.build().toByteArray());
    }

    public void slot_status_req(View view) {
        SocketComm.NativeSocketCommand.Builder commandBuilder = getCommandBuild(SocketComm.CmdType.SLOT_STATUS_REQ);
        SocketComm.CMsgBodySlotStatusReq req = SocketComm.CMsgBodySlotStatusReq.newBuilder()
                .setSlotNum(9)
                .build();
        commandBuilder.setSlotStatusReqBody(req);
        socketClient.sendMessageToNativeServer(commandBuilder.build().toByteArray());
    }

    public void open_dev(View view) {
        SocketComm.NativeSocketCommand.Builder commandBuilder = getCommandBuild(SocketComm.CmdType.OPEN_DEV);
        SocketComm.CMsgBodyOpenDev req = SocketComm.CMsgBodyOpenDev.newBuilder()
                .setAction(CommonEnum.DEVACTION.BORROW)
                .setTranid(stringToByteString("tranid_123"))
                .setSlotNum(6)
                .setPassword(stringToByteString("password_213"))
                .build();
        commandBuilder.setOpenDevBody(req);
        socketClient.sendMessageToNativeServer(commandBuilder.build().toByteArray());
    }

    public void check_battery_password_req(View view) {
        SocketComm.NativeSocketCommand.Builder commandBuilder = getCommandBuild(SocketComm.CmdType.CHECK_BATTERY_PASSWORD_REQ);
        SocketComm.CMsgBodyCheckBatteryPasswordReq req = SocketComm.CMsgBodyCheckBatteryPasswordReq.newBuilder()
                .setSlotNum(7)
                .setBatterysn(stringToByteString("batterySN_6554"))
                .setBatteryPwd(stringToByteString("batteryPassword_64343"))
                .build();
        commandBuilder.setCheckBatteryPasswordReqBody(req);
        socketClient.sendMessageToNativeServer(commandBuilder.build().toByteArray());
    }

    public void update_device_params(View view) {
        SocketComm.NativeSocketCommand.Builder commandBuilder = getCommandBuild(SocketComm.CmdType.UPDATE_DEVICE_PARAMS);
        SocketComm.CMsgBodyUpdateDeviceParams req = SocketComm.CMsgBodyUpdateDeviceParams.newBuilder()
                .setCmd(stringToByteString("ls -al"))
                .build();
        commandBuilder.setUpdateDeviceParamsBody(req);
        socketClient.sendMessageToNativeServer(commandBuilder.build().toByteArray());
    }

    public void wifi_probe_req(View view) {
        SocketComm.NativeSocketCommand.Builder commandBuilder = getCommandBuild(SocketComm.CmdType.WIFI_PROBE_REQ);
        socketClient.sendMessageToNativeServer(commandBuilder.build().toByteArray());
    }

    public void wifi_probe_mac_address_req(View view) {
        SocketComm.NativeSocketCommand.Builder commandBuilder = getCommandBuild(SocketComm.CmdType.WIFI_PROBE_MAC_ADDRESS_REQ);
        socketClient.sendMessageToNativeServer(commandBuilder.build().toByteArray());
    }

    public void sub_updrade_stat_res(View view) {
        SocketComm.NativeSocketCommand.Builder commandBuilder = getCommandBuild(SocketComm.CmdType.SUB_UPDRADE_STAT_REQ);
        socketClient.sendMessageToNativeServer(commandBuilder.build().toByteArray());
    }

    public void slot_led_act_req(View view) {
        SocketComm.NativeSocketCommand.Builder commandBuilder = getCommandBuild(SocketComm.CmdType.SLOT_LED_ACT_REQ);
        SocketComm.CMsgBodySlotLedActReq req = SocketComm.CMsgBodySlotLedActReq.newBuilder()
                .setSlotNum(7)
                .setLedAct(SocketComm.LED_ACTION.LED_BLINK)
                .build();
        commandBuilder.setSlotLedActReqBody(req);
        socketClient.sendMessageToNativeServer(commandBuilder.build().toByteArray());
    }








    private SocketComm.NativeSocketCommand.Builder getCommandBuild(SocketComm.CmdType cmdType) {
        Log.e(TAG, "send cmd=" + cmdType);
        SocketComm.NativeSocketCommand.Builder commandBuilder = SocketComm.NativeSocketCommand.newBuilder();
        SocketComm.CMsCmdHead cmdHead = SocketComm.CMsCmdHead.newBuilder()
                .setCmdtype(cmdType)
                .build();
        return commandBuilder.setCmd(cmdHead);
    }

    public static String byteStringToString(ByteString byteString) {
        try {
            return new String(byteString.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static ByteString stringToByteString(String byteString) {
        try {
            return ByteString.copyFrom(byteString.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static void initBugly() {
        Beta.installTinker();

        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.upgradeCheckPeriod = 5 * 60 * 1000;
        Beta.largeIconId = R.mipmap.ic_launcher;
        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源Id;
         */
        Beta.smallIconId = R.mipmap.ic_launcher;
        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        Beta.defaultBannerId = R.mipmap.ic_launcher;
        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir = new File("/cache/");
        /**
         * 已经确认过的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = false;
        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗; 不设置会默认所有activity都可以显示弹窗;
         */
        Beta.canShowUpgradeActs.add(MainActivity.class);

        /***** Bugly高级设置 *****/
        BuglyStrategy strategy = new BuglyStrategy();
        /**
         * 设置app渠道号
         */
//        strategy.setAppChannel(PackageUtils.getMarket(app));
//        strategy.setAppVersion(BuildConfig.VERSION_NAME);
//        strategy.setDeviceID(TvManagerUtil.getDeviceId());
//        /***** 统一初始化Bugly产品，包含Beta *****/
//        Bugly.init(app, BUGLY_APP_ID, true, strategy);
//        CrashReport.initCrashReport(app.getApplicationContext(), BUGLY_APP_ID, BuildConfig.DEBUG);
//        CrashReport.setAppVersion(app, BuildConfig.VERSION_NAME);
//        CrashReport.enableBugly(!BuildConfig.DEBUG);
//        CrashReport.setAppChannel(app, PackageUtils.getMarket(app));
//        CrashReport.setUserId((String) SharedPreferencesHelper.getInstance().get(ConstantsUtil.TYPE_USER,
//                ConstantsUtil.KEY_UID, ""));
//        CrashReport.setSdkExtraData(app, "git_rev", BuildConfig.GIT_ID);
//        CrashReport.setSdkExtraData(app, "git_branch", BuildConfig.BUILD_BRANCH);
//        CrashReport.setSdkExtraData(app, "build_time", BuildConfig.BUILD_TIME);
//        CrashReport.setSdkExtraData(app, "is_debug", String.valueOf(BuildConfig.DEBUG));
//        BuglyLog.d(TAG, "DEBUG=" + BuildConfig.DEBUG);
//        BuglyLog.d(TAG, "IMEI=" + DeviceUtils.getIMEI());
//        BuglyLog.d(TAG, "ENABLE_POLL_BORROW=" + Constant.STATISTICS.ENABLE_POLL_BORROW);
//        BuglyLog.d(TAG, "ENABLE_POLL_RETURN=" + Constant.STATISTICS.ENABLE_POLL_RETURN);
//        BuglyLog.d(TAG, "ENABLE_WS=" + Constant.STATISTICS.ENABLE_WS);
//        BuglyLog.d(TAG, "UID=" + SharedPreferencesHelper.getInstance().get(ConstantsUtil.TYPE_USER,
//                ConstantsUtil.KEY_UID, ""));
//        BuglyLog.d(TAG, "GIT ID=" + BuildConfig.GIT_ID);
//        BuglyLog.d(TAG, "BUILD TIME=" + BuildConfig.BUILD_TIME);
    }

}
