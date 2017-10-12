package com.lx.phonerecycle.upgrade;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class CallExtraAppUtil {

	/**
	 * 启动call功能
	 * 
	 * @param phoneNum
	 *            电话号码
	 * @param context
	 */
	public static void startCall(String phoneNum, Context context) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.CALL");
		intent.setData(Uri.parse("tel:" + phoneNum));
		context.startActivity(intent);
	}

	/**
	 * 启动默认的浏览器
	 * 
	 * @param context
	 * @param url
	 *            目标网址
	 */
	public static void startBrowser(Context context, String url) {
		Uri content_url = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, content_url);
		context.startActivity(intent);
	}

	/**
	 * 安装软件
	 * 
	 * @param apkPath
	 * @param mactivity
	 */
	public static void installApk(String apkPath, String fileName,
			Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(new File(apkPath, fileName)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

}
