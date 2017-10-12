package com.lx.phonerecycle.gsonbean;

import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午10:06 <br>
 * description:
 */

public class N3A1_GetHomePageInfo {

    //http://joy.cdctinfo.cn/tml_inter/n3a/index/nns_tag/android_phone?nns_method=today_quote&nns_type=0&nns_sign=2119299b866ca3dedf6bca165f8d390b

    public int status;
    public String msg;
    public List<Data> data;
    public List<Clinch> clinch;
    public String top_ad;

    public static class Data {

        public String brand;
        public String model;
        public String title;
        public List<Quote> quote;

        public static class Quote {
            public String date;
            public String price;
            public String aliases;
        }
    }

    public static class Clinch {
        public String title;
        public String price;
    }

    @Override
    public String toString() {
        return "N3A1_GetHomePageInfo{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", clinch=" + clinch +
                ", top_ad='" + top_ad + '\'' +
                '}';
    }

}
