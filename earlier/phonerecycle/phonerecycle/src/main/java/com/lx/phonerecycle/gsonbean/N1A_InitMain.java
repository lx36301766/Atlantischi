package com.lx.phonerecycle.gsonbean;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-20 下午5:26 <br>
 * description:
 */

public class N1A_InitMain {

    //http://joy.cdctinfo.cn/tml_inter/?nns_tag=android_phone

    public String n2_a;
    public String n3_a;
    public String n4_a;

    public InitData init_data;

    public static class InitData {

        public String time;
        public ClientVersion client_version;

        public static class ClientVersion {

            public AndroidDevice android_phone;
            public AndroidDevice android_pad;

            public static class AndroidDevice{
                public String version;
                public String version_inner;
                public String url;
            }

        }

    }

}
