package com.lx.phonerecycle.gsonbean;

import java.util.List;

/**
 * Copyright (C) 2014 hx <br>
 * All rights reserved. <br>
 * author:  xiang.huang <br>
 * date:  2014/8/5 0:04 <br>
 * description:
 */
public class N4A2_GetUserOrder {
    public int status;
    public String msg;
    public int page;
    public int total_page;
    public int count_num;
    public List<Data> data;
    public static class Data {
        public String order_id;
        public String user_id;
        public String uname;
        public String phone_no;
        public int status;
        public String status_info;
        public String order_time;
        public String price;
        public String brand;
        public String model;
        public String image;
        public String wap_pay_url;
    }

    @Override
    public String toString() {
        return "N4A2_GetUserOrder{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", page=" + page +
                ", total_page=" + total_page +
                ", count_num=" + count_num +
                ", data=" + data +
                '}';
    }
}
