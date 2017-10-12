package com.lx.phonerecycle.upgrade;

public class MessageType {
	/** 网络不可用 **/
	public final static int MSG_NET_INVALID = 400;
	/** 请求成功 **/
	public final static int MSG_REQUEST_SUCCESS = 200;
	/** 登录成功 **/
	public final static int MSG_LOGIN_SUCCESS = 201;
	/** 获取数据成功 **/
	public final static int MSG_REQUEST_DATA_SUCCESS = 111;
	/** 获取数据成功 **/
	public final static int MSG_REQUEST_DATA_SUCCESS2 = 115;
	/** 无更多数据 **/
	public final static int MSG_NO_MORE_DATA = 112;
	/** 操作失败 **/
	public final static int MSG_OPTION_FAIL = 116;

	/** notification 下载中 **/
	public static final int MSG_NOTIFICATION_DOWNLOADING = 4001;
	/** notification 下载完成 **/
	public static final int MSG_NOTIFICATION_DOWNLOADED = 4002;
	/** notification 下载失败 **/
	public static final int MSG_DOWNLOAD_FIALED = 4003;
	/** 版本号获取成功 **/
	public static final int MSG_VERSION_CODE = 4003;

	/** 启动activity **/
	public static final int REQUEST_ACTIVITY_CODE = 500;

}
