package com.lx.phonerecycle.gsonbean;

import java.io.Serializable;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午9:01 <br>
 * description:
 */

public class N2A3_UserLogin implements Serializable {

    //http://joy.cdctinfo.cn/tml_inter/n2a/index/nns_tag/android_phone?nns_method=user_login&nns_name=xl&nns_pwd=qwe&nns_sign=ccc4b2c4a1c74bf1d02c9d5253b3fdf3

    public int status;
    public String msg;
    public String id;
    public String name;
    public String power; //0平台帐号 1营业厅 2客户经理 3其他
    public String score;
    public String sex;
    public String experience;
    public String email;
    public String login_count;
    public String qq;
    public String phone;
    public String birthday;
    public String address;
    public String last_login_time;
    public String header;
    public String author_code;
    public String reg_time;

    @Override
    public String toString() {
        return "N2A3_UserLogin{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", power='" + power + '\'' +
                ", score='" + score + '\'' +
                ", sex='" + sex + '\'' +
                ", experience='" + experience + '\'' +
                ", email='" + email + '\'' +
                ", login_count='" + login_count + '\'' +
                ", qq='" + qq + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", last_login_time='" + last_login_time + '\'' +
                ", header='" + header + '\'' +
                ", author_code='" + author_code + '\'' +
                ", reg_time='" + reg_time + '\'' +
                '}';
    }
}
