package com.lx.phonerecycle.gsonbean;

import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午10:06 <br>
 * description:
 */

public class N3A2_GetActivityPromotions {

    //http://joy.cdctinfo.cn/tml_inter/n3a/index/nns_tag/android_phone?nns_method=event_promotion&nns_type=0&nns_page_size=20&nns_page_num=1&nns_sign=7ed8b3fedaf85fe286591208d2542e95

    public int status;
    public String msg;
    public int page;
    public int total_page;
    public int count_num;
    public List<Data> data;

    public static class Data {

        public String id;
        public String title;
        public String summary;
        public String expries;
        public String img;
    }

    @Override
    public String toString() {
        return "N3A2_GetActivityPromotions{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", page=" + page +
                ", total_page=" + total_page +
                ", count_num=" + count_num +
                ", data=" + data +
                '}';
    }
}
