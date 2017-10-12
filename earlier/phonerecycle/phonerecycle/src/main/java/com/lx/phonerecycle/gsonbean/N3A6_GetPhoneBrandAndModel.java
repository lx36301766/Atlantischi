package com.lx.phonerecycle.gsonbean;

import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-7-27 下午10:39 <br>
 * description:
 */

public class N3A6_GetPhoneBrandAndModel {


    public int status;
    public String msg;
    public Data data;

    public static class Data {
        public List<Brand> brand;
        public List<Model> model;

        public static class Brand {
            public String id;
            public String name;
        }

        public static class Model {
            public String id;
            public String name;
        }
    }

}
