package com.lx.phonerecycle.request;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午5:52 <br>
 * description:
 */

public class MainInfo {

    //外网地址
    private static final String main_external = "http://joy.cdctinfo.cn/tml_inter/";
    //内网测试地址
    private static final String main_inner = "http://192.168.2.103/joy_network/Public/tml_inter/";

    public static final String main_url = main_external;

    public static final RequestParams nns_tag = new RequestParams("nns_tag", "android_phone");

    public static boolean initMainN1ASuccess = false;

    public static String n2_a = "";
    public static String n3_a = "";
    public static String n4_a = "";

    public static int NNS_TYPE = 0; //0回收助手 1换机助手

}
