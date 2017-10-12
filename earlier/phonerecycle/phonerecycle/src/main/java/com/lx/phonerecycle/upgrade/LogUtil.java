package com.lx.phonerecycle.upgrade;

import android.content.Context;
import android.widget.Toast;

/**
 * 这个是日志打印工具类，在项目正式发布时，将isPrint设置为false则所有的日志不会打印在控制台
 * 
 * @author luron
 * 
 */
public class LogUtil {
	// TODO ***********************发布时请将此变量设置为私有的
	// **********************************
	private final static boolean isPrint = false;

	// TODO
	// ***********************发布时请将上面变量设置为私有�?**********************************
	public static void i(String tag, String message) {
		if (isPrint) {
			if (tag != null && message != null && !"".equals(tag.trim())
					&& !"".equals(message.trim())) {
				android.util.Log.i(tag, message);
			}
		}
	}

	public static void d(String tag, String message) {
		if (isPrint) {
			if (tag != null && message != null && !"".equals(tag.trim())
					&& !"".equals(message.trim())) {
				android.util.Log.d(tag, message);
			}
		}
	}

	public static void e(String tag, String message) {
		if (isPrint) {
			if (tag != null && message != null && !"".equals(tag.trim())
					&& !"".equals(message.trim())) {
				android.util.Log.e(tag, message);
			}
		}
	}

	public static void w(String tag, String message) {
		if (isPrint) {
			if (tag != null && message != null && !"".equals(tag.trim())
					&& !"".equals(message.trim())) {
				android.util.Log.w(tag, message);
			}
		}
	}

	public static void e(Exception e) {
		if (isPrint) {
			if (e != null) {
				e.printStackTrace();
			}
		}
	}

	public static void showToast(Context context, String content) {
		if (isPrint) {
			if (context != null && content != null)
				Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
		}
	}
}
