package com.starcor.config;

import java.util.ArrayList;

import android.util.Log;

import com.starcor.config.MgtvVersionInfo;

/**
 * 版本库的管理,这个库的历史记录不准修改
 *
 * @author czy
 *         <p/>
 *         zhouliang20131016：重构代码，使版本号定义、修改操作更集中一点，方便每次打包
 */
public class MgtvVersionTable {
    private static final String TAG = "MgtvVersionTable";
    /**
     * 统一的版本库
     */
    private static final ArrayList<MgtvVersionInfo> mgtvVersionTable = new ArrayList<MgtvVersionInfo>();

    /**
     * 初始化版本库，通过厂家编码绑定版本号中的计费策略、厂家描述、终端访问服务器的主入口
     */
    static {
        /**
         * 厦门鼎喜
         * zhouliang20131029
         */
        mgtvVersionTable.add(new MgtvVersionInfo(Factory.VERSION_SC_1_0_0,
                MgtvBossPolicy.MG_TONGYONG, "2", "DX", "16", "1",
                "http://interface.hifuntv.com/mgtv/STBindex"));
    }

/*	public static MgtvVersionInfo getMgtvVersionInfo(String mgtvVersion)
	{
		for( MgtvVersionInfo curVersion : mgtvVersionTable )
		{
			if( curVersion.mgtvVersion.equals(mgtvVersion) )
			{
				return curVersion;
			}
		}
		Logger.e( TAG, "getMgtvVersionInfo not found version:"+mgtvVersion );
		return null;
	}*/

    public static MgtvVersionInfo getMgtvVersionInfo(int factory) {
        for (MgtvVersionInfo curVersion : mgtvVersionTable) {
            if (curVersion.factory == factory) {
                return curVersion;
            }
        }
        Log.e(TAG, "getMgtvVersionInfo not found version of factory:" + factory);
        return null;
    }

    public static void dumpData() {
        for (int i = 0; i < mgtvVersionTable.size(); ++i) {
            Log.i(TAG, "dumpData of MgtvVersionTable, index" + i + ": factory=" + mgtvVersionTable.get(i).factory
                    + ", discribe=" + mgtvVersionTable.get(i).versionDiscribe.toString()
                    + ", N1Aurl=" + mgtvVersionTable.get(i).N1AUrl);
        }
    }
}
