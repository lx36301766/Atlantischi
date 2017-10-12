package com.lx.phonerecycle.gsonbean;

import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午10:39 <br>
 * description:
 */

public class N3A4_GetPhoneRecomInfo {

    //http://joy.cdctinfo.cn/tml_inter/n3a/index/nns_tag/android_phone?nns_method=machine_redemption&nns_recom=0&nns_page_size=20&nns_page_num=1&nns_sign=204bce93307f84cf2b0f0680c6023726

    public int status;
    public String msg;
    public int page;
    public int total_page;
    public int count_num;
    public List<Data> data;

    public static class Data {

        public String id;
        public String title;
        public String price;
        public String img;
        public String is_contract;
        public List<Role> role;

        public static class Role {
            public String key;
            public String value;
        }
    }

    @Override
    public String toString() {
        return "N3A4_GetPhoneRecomInfo{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", page=" + page +
                ", total_page=" + total_page +
                ", count_num=" + count_num +
                ", data=" + data +
                '}';
    }
}
