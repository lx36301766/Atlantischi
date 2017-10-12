package com.starcor.config;

import android.text.TextUtils;
import android.util.Log;
import com.starcor.config.MgtvUrl;
import com.starcor.config.MgtvVersion.ReleaseType;

/**
 * 设备硬件信息
 *
 * @author czy
 *         <p/>
 *         zhouliang20131016：重构代码，使版本号定义、修改操作更集中一点，方便每次打包。并增加设备、版本号相关配置数据dump
 */
public class DeviceInfo {

    /**
     * 初始化，需要确保在最开始运行
     */
    public static void init() {
        // 初始化版本库
        // MgtvVersionTable.init();
        MgtvUrl.init();
        getFactory();
        getMac();

        // 打印数据
        MgtvVersionTable.dumpData();
        DeviceInfo.dumpData();
        MgtvUrl.dumpData();
    }

    public static void dumpData() {
        Log.i(TAG, "dumpData of DeviceInfo, factory=" + factory);
        Log.i(TAG, "dumpData of DeviceInfo, version=" + getMGTVVersion());
        Log.i(TAG, "dumpData of DeviceInfo, mac=" + mac);
        Log.i(TAG, "dumpData of DeviceInfo, userAgent=" + userAgent);

        Log.i(TAG, "dumpData of DeviceInfo, isFactoryCH()=" + isSC());

        Log.i(TAG, "code revision:" + MgtvVersion.codeRev);
        Log.i(TAG, "build info:" + MgtvVersion.buildInfo);
    }

    /**
     * 需要配置使用的版本号
     *
     * @return
     */
    /*
	 * public static String getMGTVVersion() { //针对TCL需要特殊化处理 if(
	 * MgtvPublishConfig.mgtvVersion.indexOf(".TC10_") >= 0 ) {
	 * 
	 * //TCL MS序列 //818:devmodel=TCL-CN-MS818C-E5600A-3D
	 * //901:devmodel=TCL-CN-MS901-A71-3D
	 * 
	 * String devModel = TCLTools.getDevModel(); Logger.i(TAG,
	 * "getMGTVVersion TCL devModel:"+devModel ); if(
	 * !TextUtils.isEmpty(devModel) && devModel.indexOf("MS818") >= 0 ) { return
	 * MgtvPublishConfig.mgtvTClMs818Version; } else if(
	 * !TextUtils.isEmpty(devModel) && devModel.indexOf("MS901") >= 0 ) { return
	 * MgtvPublishConfig.mgtvTClMs901Version; }
	 * 
	 * 
	 * } return MgtvPublishConfig.mgtvVersion; }
	 */
    public static String getMGTVVersion() {
//		int factoryCode = MgtvVersion.getFactoryCode();
        return MgtvVersion.getVersion();
    }

    public static int getFactory() {
        if (factory == Factory.VERSION_NULL) {
            factory = MgtvVersion.getFactoryCode();
        }
        return factory;
    }

    public static String getUserAgent() {
        if (0 == userAgent.length()) {
            userAgent = "nn_player/std/1.0.0";
        }
        return userAgent;
    }

    public static String getMac() {

        // 只有调试版本，才能使用通用调试MAC
        if (ReleaseType.Debug == MgtvVersion.getReleaseType()
                || ReleaseType.Debug_Test == MgtvVersion.getReleaseType()
                || ReleaseType.Debug_Release == MgtvVersion.getReleaseType()) {
            mac = "00-0B-2F-33-7B-4A";
            return mac;
        }

        if (17 == mac.length()) {
            return mac;
        }

//		if (DeviceInfo.isHuaWei()) {
//			mac = NetTools.getWifiMac();
//		} else {
//			mac = NetTools.getLANMac();
//		}
        Log.i(TAG, "getMac mac:" + mac);

        if (TextUtils.isEmpty(mac)) {
            Log.i(TAG, "获取MAC地址失败！");
            return "获取MAC地址失败！";
        }
        return mac;
    }

    /**
     * 厦门鼎喜
     *
     * @return
     */
    public static boolean isSC() {
        if (factory == Factory.VERSION_SC_1_0_0)
            return true;

        return false;
    }


    /**
     * 打印LOG
     */
    private static String TAG = "DeviceInfo";

    /**
     * 注意，这个变亘以后是通过芒果版本号自动算的，请不要再手式配置
     */
    private static int factory = Factory.VERSION_NULL;

    private static String mac = "";
    private static String userAgent = "";

}
