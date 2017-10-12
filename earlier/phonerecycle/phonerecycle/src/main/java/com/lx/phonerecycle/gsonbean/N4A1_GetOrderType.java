package com.lx.phonerecycle.gsonbean;

import java.util.List;

/**
 * Copyright (C) 2014 hx <br>
 * All rights reserved. <br>
 * author:  xiang.huang <br>
 * date:  2014/8/4 23:02 <br>
 * description:
 */
public class N4A1_GetOrderType {
    public int status;
    public String msg;
    public List<Data> data;

    public static class Data {
        public String status;
        public String title;
    }

    @Override
    public String toString() {
        return "N4A1_GetOrderType{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
