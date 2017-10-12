package com.lx.phonerecycle.gsonbean;

import java.io.Serializable;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午10:39 <br>
 * description:
 */

public class N3A7_GetPhoneAssessArgs {

    //http://joy.cdctinfo.cn/tml_inter/n3a/index/nns_tag/android_phone?nns_method=machine_assess&nns_phone=15928115878&nns_brand=%E8%8B%B9%E6%9E%9C&nns_model=5S&nns_author_code=VAPhTigiL3yiy%252FG7mimQc0TOORqZ6LYqw150zZY%252FmBe1%252F1KzLRR5e41QiEM%252BFwGtg7qsFaETw%252FwerbUzH9SbXZgxAoGgsdc5G1iLJjA%252FGv%252B0LbO8ZscfyQ%252FRIq0nc%252BzGAd9rmvhZ%252FWlD%252B5YF9zZ%252BwQ%253D%253D&nns_sign=54a093ac31719253d00732b2507c9de7

    public int status;
    public String msg;
    public Data data;

    public static class Data implements Serializable {
        public String name;
        public String brand;
        public String model;
        public String net_time;
        public String price;
        public java.util.List<List> list;

        public static class List implements Serializable {
            public String id;
            public String name;
            public java.util.List<Assess> assess;
            public String remark;

            public static class Assess implements Serializable {
                public String id;
                public String value;
            }
        }
    }

}
