package com.lx.phonerecycle.gsonbean;

import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午10:39 <br>
 * description:
 */

public class N3A5_GetPhoneRecomDetails {

    //http://joy.cdctinfo.cn/tml_inter/n3a/index/nns_tag/android_phone?nns_method=machine_detail&nns_machine_id=12&nns_sign=d4eb23d57cee0eed4095d78753144dab

    public int status;
    public String msg;
    public Data data;

    public static class Data {

        public String id;
        public String title;
        public String price;
        public String tprice;
        public int sale_count;
        public String contract;
        public List<String> img;
        public List<Model> model;

        public static class Model {
            public String key;
            public String value;
        }
    }

}
