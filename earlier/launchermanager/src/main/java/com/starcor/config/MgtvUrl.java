package com.starcor.config;

import android.util.Log;
import com.starcor.config.MgtvVersion.ReleaseType;

/**
 * 此文件主要保存针对平台应用，固化写死的地址
 * <p/>
 * <p/>
 * zhouliang20131016：重构代码，使版本号定义、修改操作更集中一点，方便每次打包
 */
public class MgtvUrl {
    private static final String TAG = "MgtvUrl";
    public static String urlN1A = "";
    public static String urlHeartBeat = "";
    public static String urlReportError = "";
    public static String urlReportDeviceStatistics = "";
    public static String urlReportOnlineDeivce = "";
    public static String urlReportVodVideo = "";
    public static String urlReportPlayBackTvVideo = "";
    public static String urlReportPlayTstvVideo = "";

    public static void init() {
        //初始化心跳地址
        urlHeartBeat = "http://rock501.hunantv.com/yd.cgi";

		
		/*if (ReleaseType.Release == MgtvVersion.getReleaseType()
			|| ReleaseType.Beta == MgtvVersion.getReleaseType())*/
        if (ReleaseType.Release == MgtvVersion.getReleaseType()) {
            //初始化N1_A地址
            MgtvVersionInfo versionInfo = MgtvVersionTable.getMgtvVersionInfo(DeviceInfo.getFactory());
            if (versionInfo != null) {
                urlN1A = versionInfo.N1AUrl;
            } else {
                Log.e(TAG, "N1_A is null");
                urlN1A = "";
            }

            //初始化数据上报地址
            urlReportError = "http://rock501.hunantv.com/itvrun_error.cgi";
            urlReportDeviceStatistics = "http://rock501.hunantv.com/itvrun_device.cgi";
            urlReportOnlineDeivce = "http://rock501.hunantv.com/itvrun_online.cgi";
            urlReportVodVideo = "http://rock501.hunantv.com/itvrun_vod.cgi";
            urlReportPlayBackTvVideo = "http://rock501.hunantv.com/itvrun_live_back.cgi";
            urlReportPlayTstvVideo = "http://rock501.hunantv.com/itvrun_time_shift.cgi";
        } else if (ReleaseType.Alpha == MgtvVersion.getReleaseType()
                || ReleaseType.Beta == MgtvVersion.getReleaseType()) {
            //初始化N1_A地址
            urlN1A = "http://cs.interface.hifuntv.com/mgtv/STBindex";

            //初始化数据上报地址
            urlReportError = "http://rock501.hunantv.com/itvrun_error.cgi";
            urlReportDeviceStatistics = "http://rock501.hunantv.com/itvrun_device.cgi";
            urlReportOnlineDeivce = "http://rock501.hunantv.com/itvrun_online.cgi";
            urlReportVodVideo = "http://rock501.hunantv.com/itvrun_vod.cgi";
            urlReportPlayBackTvVideo = "http://rock501.hunantv.com/itvrun_live_back.cgi";
            urlReportPlayTstvVideo = "http://rock501.hunantv.com/itvrun_time_shift.cgi";
        } else if (ReleaseType.Demo == MgtvVersion.getReleaseType()) {
/*	    	//初始化N1_A地址
	    	urlN1A = "http://yf.interface.hifuntv.com/mgtv/STBindex";
	    	
	    	//初始化数据上报地址
	    	urlReportError = "http://rock501.hunantv.com/itv_error.cgi";
	    	urlReportDeviceStatistics = "http://rock501.hunantv.com/itv_device.cgi";
			urlReportOnlineDeivce = "http://rock501.hunantv.com/itv_online.cgi";
			urlReportVodVideo = "http://rock501.hunantv.com/itv_vod.cgi";
			urlReportPlayBackTvVideo = "http://rock501.hunantv.com/itv_live_back.cgi";
			urlReportPlayTstvVideo = "http://rock501.hunantv.com/itv_time_shift.cgi";*/
            //初始化N1_A地址
            MgtvVersionInfo versionInfo = MgtvVersionTable.getMgtvVersionInfo(DeviceInfo.getFactory());
            if (versionInfo != null) {
                urlN1A = versionInfo.N1AUrl;
            } else {
                Log.e(TAG, "N1_A is null");
                urlN1A = "";
            }
        } else if (ReleaseType.Debug == MgtvVersion.getReleaseType()) {
            //初始化N1_A地址
            urlN1A = "http://yf.interface.hifuntv.com/mgtv/STBindex";

            //初始化数据上报地址
            urlReportError = "http://rock501.hunantv.com/itv_error.cgi";
            urlReportDeviceStatistics = "http://rock501.hunantv.com/itv_device.cgi";
            urlReportOnlineDeivce = "http://rock501.hunantv.com/itv_online.cgi";
            urlReportVodVideo = "http://rock501.hunantv.com/itv_vod.cgi";
            urlReportPlayBackTvVideo = "http://rock501.hunantv.com/itv_live_back.cgi";
            urlReportPlayTstvVideo = "http://rock501.hunantv.com/itv_time_shift.cgi";
        } else if (ReleaseType.Debug_Test == MgtvVersion.getReleaseType()) {
            //初始化N1_A地址
            urlN1A = "http://cs.interface.hifuntv.com/mgtv/STBindex";

            //初始化数据上报地址
            urlReportError = "http://rock501.hunantv.com/itv_error.cgi";
            urlReportDeviceStatistics = "http://rock501.hunantv.com/itv_device.cgi";
            urlReportOnlineDeivce = "http://rock501.hunantv.com/itv_online.cgi";
            urlReportVodVideo = "http://rock501.hunantv.com/itv_vod.cgi";
            urlReportPlayBackTvVideo = "http://rock501.hunantv.com/itv_live_back.cgi";
            urlReportPlayTstvVideo = "http://rock501.hunantv.com/itv_time_shift.cgi";
        } else if (ReleaseType.Debug_Release == MgtvVersion.getReleaseType()) {
            //初始化N1_A地址
            MgtvVersionInfo versionInfo = MgtvVersionTable.getMgtvVersionInfo(DeviceInfo.getFactory());
            if (versionInfo != null) {
                urlN1A = versionInfo.N1AUrl;
            } else {
                Log.e(TAG, "N1_A is null");
                urlN1A = "";
            }

            //初始化数据上报地址
            urlReportError = "http://rock501.hunantv.com/itv_error.cgi";
            urlReportDeviceStatistics = "http://rock501.hunantv.com/itv_device.cgi";
            urlReportOnlineDeivce = "http://rock501.hunantv.com/itv_online.cgi";
            urlReportVodVideo = "http://rock501.hunantv.com/itv_vod.cgi";
            urlReportPlayBackTvVideo = "http://rock501.hunantv.com/itv_live_back.cgi";
            urlReportPlayTstvVideo = "http://rock501.hunantv.com/itv_time_shift.cgi";
        } else {
            Log.e(TAG, "invalid MgtvVersion.getReleaseType()=" + MgtvVersion.getReleaseType());
        }
    }

    public static void dumpData() {
        Log.i(TAG, "dumpData of MgtvUrl, urlN1A=" + urlN1A);
        Log.i(TAG, "dumpData of MgtvUrl, urlHeartBeat=" + urlHeartBeat);
        Log.i(TAG, "dumpData of MgtvUrl, urlReportError=" + urlReportError);
        Log.i(TAG, "dumpData of MgtvUrl, urlReportDeviceStatistics=" + urlReportDeviceStatistics);
        Log.i(TAG, "dumpData of MgtvUrl, urlReportOnlineDeivce=" + urlReportOnlineDeivce);
        Log.i(TAG, "dumpData of MgtvUrl, urlReportVodVideo=" + urlReportVodVideo);
        Log.i(TAG, "dumpData of MgtvUrl, urlReportPlayBackTvVideo=" + urlReportPlayBackTvVideo);
        Log.i(TAG, "dumpData of MgtvUrl, urlReportPlayTstvVideo=" + urlReportPlayTstvVideo);

    }


    /**
     * 除非临时需要，否则不修改此项
     *
     * @return
     */
	/*public static String N1_A()
	{
		
		if (ReleaseType.Release == MgtvVersion.getReleaseType())
		{
			MgtvVersionInfo versionInfo = MgtvVersionTable.getMgtvVersionInfo( DeviceInfo.getFactory() );
			if( versionInfo != null )
			{
				return versionInfo.N1AUrl;
			}
			else
			{
				Logger.e(TAG, "N1_A is null");
				return "";
			}
		}
	    else if (ReleaseType.Beta == MgtvVersion.getReleaseType())
		{
			return "http://cs.interface.hifuntv.com/mgtv/STBindex";
		}		
	    else if (ReleaseType.Demo == MgtvVersion.getReleaseType())
		{
			return "http://yf.interface.hifuntv.com/mgtv/STBindex";
		}
	    else if (ReleaseType.Debug == MgtvVersion.getReleaseType())
	    {
	    	return "http://yf.interface.hifuntv.com/mgtv/STBindex";
	    }
	    else
	    {
	    	return "";
	    }
	}*/
    public static String N1_A() {
        return urlN1A;
    }

    public static String MgtvHeartBeat() {
        //return "http://rock501.hunantv.com/yd.cgi";
        return urlHeartBeat;
    }

    /**
     * 返回错误数据上报地址
     *
     * @return
     */
    public static String ReportError() {
		/*if ((ReleaseType.Release == MgtvVersion.getReleaseType())
	       || (ReleaseType.Beta == MgtvVersion.getReleaseType()))
		{
			return "http://rock501.hunantv.com/itvrun_error.cgi";
		}
		else
		{
			return "http://rock501.hunantv.com/itv_error.cgi";
		}	*/
        return urlReportError;
    }

    /**
     * 返回设备统计数据上报地址
     *
     * @return
     */
    public static String ReportDeviceStatistics() {
		/*if ((ReleaseType.Release == MgtvVersion.getReleaseType())
			|| (ReleaseType.Beta == MgtvVersion.getReleaseType()))
		{
			return "http://rock501.hunantv.com/itvrun_device.cgi";
		} 
		else
		{
			return "http://rock501.hunantv.com/itv_device.cgi";
		}*/
        return urlReportDeviceStatistics;
    }

    /**
     * 返回设备在线统计数据上报地址
     *
     * @return
     */
    public static String ReportOnlineDeivce() {
		/*if ((ReleaseType.Release == MgtvVersion.getReleaseType())
			|| (ReleaseType.Beta == MgtvVersion.getReleaseType()))
		{
			return "http://rock501.hunantv.com/itvrun_online.cgi";
		} 
		else
		{
			return "http://rock501.hunantv.com/itv_online.cgi";
		}*/
        return urlReportOnlineDeivce;
    }

    /**
     * 返回点播数据上报地址
     *
     * @return
     */
    public static String ReportVodVideo() {
		/*if ((ReleaseType.Release == MgtvVersion.getReleaseType())
			|| (ReleaseType.Beta == MgtvVersion.getReleaseType()))
		{
			return "http://rock501.hunantv.com/itvrun_vod.cgi";
		} 
		else
		{
			return "http://rock501.hunantv.com/itv_vod.cgi";
		}*/
        return urlReportVodVideo;
    }

    /**
     * 返回回看数据上报地址
     *
     * @return
     */
    public static String ReportPlayBackTvVideo() {
		/*if ((ReleaseType.Release == MgtvVersion.getReleaseType())
			|| (ReleaseType.Beta == MgtvVersion.getReleaseType()))
		{
			return "http://rock501.hunantv.com/itvrun_live_back.cgi";
		} 
		else
		{
			return "http://rock501.hunantv.com/itv_live_back.cgi";
		}*/
        return urlReportPlayBackTvVideo;
    }

    /**
     * 返回时移数据上报地址
     *
     * @return
     */
    public static String ReportPlayTstvVideo() {
		/*if ((ReleaseType.Release == MgtvVersion.getReleaseType())
			|| (ReleaseType.Beta == MgtvVersion.getReleaseType()))
		{
			return "http://rock501.hunantv.com/itvrun_time_shift.cgi";
		} 
		else
		{
			return "http://rock501.hunantv.com/itv_time_shift.cgi";
		}*/
        return urlReportPlayTstvVideo;
    }

}
