package com.lx.phonerecycle.gsonbean;

import java.io.Serializable;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-8-5 上午1:51 <br>
 * description:
 */

public class N4A3_GetOrderDetail {

    //

    public int status;
    public String msg;
    public Data data;

    public static class Data implements Serializable {

        public String order_id; //订单编号,
        public String user_id; //用户ID,
        public String uname; //用户名,
        public String phone_no; //下单人手机号,
        public String status; //订单状态,
        public String status_info; //订单状态描述,
        public String order_time; //下单日期,
        public String price; //旧机回收价格,
        public String brand; //手机品牌,
        public String model; //手机型号,
        public String image; //手机图片,
        public String mobile_detail; //手机详情描述,

        // 以下参数为换购订单特有
        public String cate; //换购订单类别,
        public String new_brand; //新机品牌,
        public String new_model; //新机型号,
        public String address; //收机地址,
        public String c_name; //客户姓名,
        public String c_phone; //客户电话,
        public String c_time; //客户收机时间,
        public String c_address; //客户收机地址,
        public String new_price; //新手机价格,
        public String new_mobile_detail; //新机参数描述,
        public String wap_pay_url; //wap快捷支付url跳转页面,

    }


}
