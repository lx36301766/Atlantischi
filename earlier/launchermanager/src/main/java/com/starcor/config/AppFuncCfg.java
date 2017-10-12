package com.starcor.config;

import android.util.Log;

/**
 * 功能定义类，定义一个APP的可配置功能
 *
 * @author czy
 */
public class AppFuncCfg {
    /**
     * 使用全屏的详情页
     */
    public static boolean FUNCTION_GOTO_DETAILED_ACTIVITY = true;

    public static void configAppFunc(int factory) {
        //－－－－－－－－－－首先按设备类型确定功能－－－－－－－－－－
        switch (factory) {

            case Factory.VERSION_SC_1_0_0:
                break;
        }

        //－－－－－－－－－－再按版本号确定功能－－－－－－－－－－
        if (2 == MgtvVersion.getMinorVersionNum()) {
//			isMgVersion2 = true;
        } else {
//			isMgVersion2 = false;			
        }
        dumpData();
    }

    public static void dumpData() {
        Log.i("AppFuncCfg", "dumpData of Function Config, FUNCTION_GOTO_DETAILED_ACTIVITY=" + FUNCTION_GOTO_DETAILED_ACTIVITY);
    }
}
