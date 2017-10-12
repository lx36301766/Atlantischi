package com.lx.phonerecycle.gsonbean;

import java.io.Serializable;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午10:39 <br>
 * description:
 */

public class N3A9_AssessSubmit {

    //http://joy.cdctinfo.cn/tml_inter/n3a/index/nns_tag/android_phone?nns_method=assess_submit&nns_user_id=7&nns_phone=15928115878&nns_brand=%E8%8B%B9%E6%9E%9C&nns_model=5S&nns_assess=1-2%7C2-5%7C3-8%7C4-11%7C5-14&nns_author_code=VAPhTigiL3yiy%252FG7mimQc0TOORqZ6LYqw150zZY%252FmBe1%252F1KzLRR5e41QiEM%252BFwGtg7qsFaETw%252FwerbUzH9SbXZgxAoGgsdc5G1iLJjA%252FGv%252B0LbO8ZscfyQ%252FRIq0nc%252BzG7XvZQDqaVuRTlK66oIsoQw%253D%253D&nns_sign=04c9624c09edd7966d6c42c2b6e99953

    public int status;
    public String msg;
    public Data data;

    public static class Data implements Serializable {
        public String brand;
        public String model;
        public String price;
        public String order_id;
        public String order_time;
        public String info;
    }

}
