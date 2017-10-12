package com.lx.webservice.data;

/**
 * Created with IntelliJ IDEA.<br>
 * User: luo.xuan<br>
 * Date: 14-1-22<br>
 * Time: 下午5:52<br>
 */

public class JWSUrl {

    /**
     * WebService命名空间
     */
    public static final String NAMESPACE = "http://tempuri.org/";

    //外网地址
    private static final String EXTERNAL_MAIN_URL = "http://vpn.96335.com:8164/";
    //内网地址
    private static final String INNER_MAIN_URL = "http://10.1.4.164:8080/";

    /**
     * 是否使用外网地址
     */
    public static boolean USE_EXTERNAL_URL = true;

    /**
     * WebService主域名
     */
    public static final String MAIN_URL = USE_EXTERNAL_URL ? EXTERNAL_MAIN_URL : INNER_MAIN_URL;
    /**
     * 登陆接口地址
     */
    public static final String SYS_URL = MAIN_URL + "sys.asmx";
    /**
     * 公告通知接口地址
     */
    public static final String OA_URL = MAIN_URL + "oa.asmx";

}
