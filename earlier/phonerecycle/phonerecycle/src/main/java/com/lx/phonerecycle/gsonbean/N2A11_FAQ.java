package com.lx.phonerecycle.gsonbean;

import java.util.List;

/**
 * Copyright (C) 2014 hx <br>
 * All rights reserved. <br>
 * author:  xiang.huang <br>
 * date:  2014/7/26 10:37 <br>
 * description:
 */
public class N2A11_FAQ {
    public int status;
    public String msg;
    public int page;
    public int total_page;
    public int count_num;
    public List<Item> data;

    public static class Item {
        public String id;
        public String title;
        public String content;

    }

    @Override
    public String toString() {
        return "N2A11_FAQ{" +
                "count_num=" + count_num +
                ", total_page=" + total_page +
                ", page=" + page +
                ", msg='" + msg + '\'' +
                ", status=" + status +
                '}';
    }
}
