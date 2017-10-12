package com.lx.phonerecycle.gsonbean;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午8:32 <br>
 * description:
 */

public class N2A2_UserRegister {

    //http://192.168.2.103/joy_network/Public/tml_inter/n2a/index/nns_tag/android_phone?nns_method=user_reg&nns_type=1&nns_name=lx&nns_phone=15928115878&nns_password=123456&nns_password_a=123456&nns_sign=fa98354e2543e3b5662d896d3af7b161

    public int status;
    public String msg;

    @Override
    public String toString() {
        return "N2A2_UserRegister{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }

}
