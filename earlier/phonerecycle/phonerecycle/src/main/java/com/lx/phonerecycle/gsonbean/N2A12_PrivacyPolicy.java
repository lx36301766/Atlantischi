package com.lx.phonerecycle.gsonbean;

/**
 * Copyright (C) 2014 hx <br>
 * All rights reserved. <br>
 * author:  xiang.huang <br>
 * date:  2014/7/26 11:13 <br>
 * description:
 */
public class N2A12_PrivacyPolicy {

    public int status;
    public String msg;
    public Data data;

    public static class Data {

        public Recover recover;
        public Change change;
        public String about;

        public static class Recover {
            public String terms_service;
            public String privacy_policy;
        }
        public static class Change {
            public String terms_service;
            public String privacy_policy;
        }

    }

    @Override
    public String toString() {
        return "N2A12_PrivacyPolicy{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
