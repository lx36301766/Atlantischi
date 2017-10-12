package com.lx.phonerecycle.upgrade;

public class AppConstants {
	/** 答案代号 **/
	public final static String[] answerCode = { "A", "B", "C", "D", "E", "F",
			"G", "H" };
	/** 上拉加载 **/
	public final static int MODE_PULL_UP_TO_REFRESH = 0x2;
	/** 加载数据 **/
	public final static int LOAD_DATA = 1;
	/** 刷新数据 **/
	public final static int REFRESH = 0;

	/** 用户入口方法参数名称 **/
	public static final String METHODE_IN = "nns_method";
	/** md5加密串 **/
	public static final String MD5_SIGN = "nns_sign";
	/** 用户授权ID **/
	public static final String AUTHOR_CODE = "nns_author_code";
	/** 用户ID **/
	public static final String USER_ID = "nns_user_id";
	/** 名称 **/
	public static final String NNS_NAME = "nns_name";
	/** 名称 **/
	public static final String NNS_TYPE = "nns_type";
	/** 联系人姓名 **/
	public static final String NNS_CONTACTOR = "nns_contactor";
	/** 联系人电话 **/
	public static final String NNS_TELPONE = "nns_telephone";
	/** 过期日期 **/
	public static final String NNS_EXPRIES = "nns_expries";
	/** 分页大小 **/
	public static final String PAGE_SIZE = "nns_page_size";
	/** 分页数 **/
	public static final String PAGE_NUM = "nns_page_num";

	public static class N1_A_ {
		/** n2a入口地址 **/
		public static final String N2_A = "n2_a";
		/** n3a入口地址 **/
		public static final String N3_A = "n3_a";
		/** n4a入口地址 **/
		public static final String N4_A = "n4_a";
		/** 初始化参数 **/
		public static final String INIT_DATA = "init_data";
		/** 系统时钟 **/
		public static final String TIMER = "time";
		/** 客户端版本标识 **/
		public static final String CLIENT_VERSION = "client_version";
		/** 客户端版本标识 **/
		public static final String ANDROID_PHONE = "android_phone";
		/** 客户端版本号 **/
		public static final String VERSION = "version";
		/** 客户端内部版本号 **/
		public static final String VERSION_INNER = "version_inner";
		/** apk下载地址 **/
		public static final String URL = "url";
		/** 关于 **/
		public static final String US = "us";
		/** 关于我们 **/
		public static final String ABOUT = "about";
		/** 客服QQ 多个用逗号隔开 **/
		public static final String C_QQ = "c_qq";
		/** 客服电话 多个用逗号隔开 **/
		public static final String C_TEL = "c_tel";
		/** 广告推广 **/
		public static final String TU = "tu";
		/** 推广联系人 **/
		public static final String C_NAME = "c_name";
		/** 推广规则 **/
		public static final String SPREAD = "spread";
		/** 推广标题 **/
		public static final String TITLE = "title";
		/** 推广地址 **/
		public static final String S_URL = "s_url";
	}

	/**
	 * N2_A 用户管理
	 * 
	 * @author Administrator
	 * 
	 */
	public static class N2_A_ {
		/** 用户注册入口参数值 **/
		public static final String REGIST_METHOD_IN_VALUE = "user_reg";
		/** 用户登录入口参数值 **/
		public static final String LOGIN_METHOD_IN_VALUE = "user_login";
		/** 修改用户信息入口参数值 **/
		public static final String MODIFY_METHOD_IN_VALUE = "user_modify";
		/** 修改用户密码入口参数值 **/
		public static final String MODIFY_PWD_METHOD_IN_VALUE = "user_modify_pwd";
		/** 意见反馈入口参数值 **/
		public static final String FEEDBACK_METHOD_IN_VALUE = "add_feedback";

		/** 用户电话号码 **/
		public static final String NNS_PHONE = "nns_phone";
		/** 用户密码 **/
		public static final String NNS_PWD = "nns_pwd";
		/** 用户确认密码 **/
		public static final String NNS_PWD_A = "nns_pwd_a";
		/** 邀请人手机号 **/
		public static final String NNS_INVITE_PHONE = "nns_invite_phone";
		/** 真实姓名 **/
		public static final String NNS_TNAME = "nns_tname";
		/** 性别 [选填] 0 未知 F男 M女 **/
		public static final String NNS_SEX = "nns_sex";
		/** 年龄 **/
		public static final String NNS_AGE = "nns_age";
		/** 用户签名 [选填] **/
		public static final String NNS_NICKNAME = "nns_nichen";
		/** 用户头像[选填] **/
		public static final String NNS_HEADER = "nns_header";
		/** 公司名称 **/
		public static final String NNS_COM_NAME = "nns_com_name";
		/** 公司电话 **/
		public static final String NNS_COM_PHONE = "nns_com_phone";
		/** 公司地址 **/
		public static final String NNS_COM_ADDRESS = "nns_com_address";
		/** 旧密码 **/
		public static final String NNS_OLD_PWD = "nns_old_pwd";
		/** 密码确认 **/
		public static final String NNS_PWD_AGAIN = "nns_pwd_again";
		/** 反馈内容 **/
		public static final String NNS_CONTENT = "nns_content";
	}

	/**
	 * N3_A 数据业务包
	 * 
	 * @author Administrator
	 * 
	 */
	public static class N3_A_ {
		/** 银行分类入口参数值 **/
		public static final String BANK_CATE_METHOD_IN_VALUE = "get_classify";
		/** 银行单位信息入口参数值 **/
		public static final String BANK_LENDING_METHOD_IN_VALUE = "get_lending";
		/** 银行产品信息入口参数值 **/
		public static final String LENDING_GOOD_METHOD_IN_VALUE = "get_lending_goods";
		/** 银行产品详细信息入口参数值 **/
		public static final String GOOD_DETAIL_METHOD_IN_VALUE = "get_goods_detail";
		/** 银行产品标签信息入口参数值 **/
		public static final String GOOD_LABEL_METHOD_IN_VALUE = "get_goods_label";
		/** 分类类型 0 机构 1 大厅 **/
		public static final String NNS_TYPE = "nns_type";
		/** 分类ID **/
		public static final String NNS_LEND_ID = "nns_lend_id";
		/** 机构类型ID **/
		public static final String NNS_CATE_ID = "nns_cate_id";
		/** 贷款类型 0 信用贷款;1 抵押贷款 **/
		public static final String NNS_LEND_TYPE = "nns_type";
		/** 机构类型分类ID **/
		public static final String NNS_CALSSIFY = "nns_classify";
		/** 贷款金额 0：1-30W ;1：31-100W ;2：101-1000W ;3：1000W以上 **/
		public static final String NNS_PRICE = "nns_price";
		/** 放款周期 0：当天 ;1：三个工作日以内 **/
		public static final String NNS_CYCLE = "nns_cycle";
		/** 利息 【选填】0：1分以下 ;1：1分-2分 ;2：2分-3分 ;3：3分以上 **/
		public static final String NNS_INTEREST = "nns_interest";
		/** 关联元素标签ID **/
		public static final String NNS_META_ID = "nns_meta_id";
		/** 产品ID **/
		public static final String NNS_GOOD_ID = "nns_goods_id";
	}

	/**
	 * N4_A 个人业务包
	 * 
	 * @author Administrator
	 * 
	 */

	public static class N4_A_ {
		/** 获取大厅甩单信息入口参数值 **/
		public static final String GET_LENDING_METHOD_IN_VALUE = "get_peer_lending";
		/** 发布大厅甩单信息入口参数值 **/
		public static final String ADD_LENDING_METHOD_IN_VALUE = "add_peer_lending";
		/** 编辑大厅甩单信息入口参数值 **/
		public static final String MODIFY_LENDING_METHOD_IN_VALUE = "modify_peer_lending";
		/** 删除大厅甩单/收单信息入口参数值 **/
		public static final String DEL_LENDING_METHOD_IN_VALUE = "del_peer_lending";
		/** 获取大厅交流聚会信息 **/
		public static final String GET_PARTY_METHOD_IN_VALUE = "get_party";
		/** 发布大厅交流聚会信息 **/
		public static final String ADD_PARTY_METHOD_IN_VALUE = "add_party";
		/** 编辑大厅交流聚会信息 **/
		public static final String MODIFY_PARTY_METHOD_IN_VALUE = "modify_party";
		/** 删除大厅交流聚会信息 **/
		public static final String DEL_PARTY_METHOD_IN_VALUE = "del_party";
		/** 获取公告信息 **/
		public static final String GET_NOTICE_METHOD_IN_VALUE = "get_notice";
		/** 推广产品信息 **/
		public static final String PROMOTE_METHOD_IN_VALUE = "promote_product";
		/** 甩单/收单信息推广 **/
		public static final String PROMOTE_REJECT_METHOD_IN_VALUE = "promote_rejection";
		/** 交流聚会信息推广 **/
		public static final String PROMOTE_PARTY_METHOD_IN_VALUE = "promote_party";
		/** 获取甩单/收单详情信息 **/
		public static final String LEND_DETAIL_METHOD_IN_VALUE = "get_lend_detail";
		/** 获取交流聚会详情信息 **/
		public static final String PARTY_DETAIL_METHOD_IN_VALUE = "get_party_detail";
		/** 分享成功 **/
		public static final String SUBMIT_SHARE_METHOD_IN_VALUE = "submit_share";
		/** 类别 0 甩单 1收单 **/
		public static final String NNS_CLASS = "nns_class";
		/** 贷款金额 **/
		public static final String NNS_PRICE = "nns_price";
		/** 开始日期 **/
		public static final String NNS_STIME = "nns_stime";
		/** 截止日期 **/
		public static final String NNS_ETIME = "nns_etime";
		/** 贷款详情 **/
		public static final String NNS_INFO = "nns_info";
		/** 甩单信息记录ID **/
		public static final String NNS_LEND_ID = "nns_lend_id";
		/** 聚会编号 **/
		public static final String NNS_PARTY_ID = "nns_party_id";
		/** 产品ID **/
		public static final String NNS_GOOD_ID = "nns_goods_id";
		/** 甩单/收单ID **/
		public static final String NNS_PROMOTE_ID = "nns_promote_id";
	}
}
