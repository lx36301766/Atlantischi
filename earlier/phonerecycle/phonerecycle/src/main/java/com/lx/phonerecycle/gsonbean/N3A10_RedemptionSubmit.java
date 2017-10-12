package com.lx.phonerecycle.gsonbean;

import java.io.Serializable;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午10:39 <br>
 * description:
 */

public class N3A10_RedemptionSubmit {

    //

    public int status;
    public String msg;
    public Data data;

    public static class Data implements Serializable {
        public String order_id;
        public String brand;
        public String model;
        public String phone;
        public String new_brand;
        public String new_model;
        public String price;
        public String date;
        public String wap_pay_url;
        public String info;
    }

}
