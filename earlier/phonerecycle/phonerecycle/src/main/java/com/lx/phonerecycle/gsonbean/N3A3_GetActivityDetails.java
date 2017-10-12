package com.lx.phonerecycle.gsonbean;

import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午10:39 <br>
 * description:
 */

public class N3A3_GetActivityDetails {

    //http://joy.cdctinfo.cn/tml_inter/n3a/index/nns_tag/android_phone?nns_method=event_detail&nns_event_id=c54988a9dc1fc3ff80d5e64208160c90&nns_sign=14a207b9ddce431fd0502debc4de2448

    public int status;
    public String msg;
    public Data data;

    public static class Data {

        public String id;
        public String title;
        public String content;
        public String expries;
        public String join_num;
        public List<String> img;
    }

    @Override
    public String toString() {
        return "N3A3_GetActivityDetails{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
