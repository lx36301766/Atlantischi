package com.lx.phonerecycle.helper;

import com.google.common.collect.Lists;
import com.lx.phonerecycle.PhoneDetailsActivity;

import java.util.List;

/**
 * Copyright (C) 2014 lx <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  14-8-4 下午3:21 <br>
 * description:
 */

public class RecycleTypeManager {

    private static RecycleTypeManager instance;

    private RecycleTypeManager() {

    }

    public static RecycleTypeManager getInstance() {
        if (instance == null) {
            instance = new RecycleTypeManager();
        }
        return instance;
    }

    public static class RedemptionPhoneInfo {
        public String id;
        public String name;
        public String price;
        public List<PhoneDetailsActivity.Params> redemptionPhoneArgs = Lists.newArrayList();
    }

    public static final int TYPE_RECYCLE = 1;
    public static final int TYPE_REDEMPTION = 2;

    private int recycleType = 0;

    private RedemptionPhoneInfo redemptionPhoneInfo;

    public void setRecycleType(int recycleType) {
        this.recycleType = recycleType;
    }

    public int getRecycleType() {
        return recycleType;
    }

    public RedemptionPhoneInfo getRedemptionPhoneInfo() {
        return redemptionPhoneInfo;
    }

    public void setRedemptionPhoneInfo(RedemptionPhoneInfo redemptionPhoneInfo) {
        this.redemptionPhoneInfo = redemptionPhoneInfo;
    }
}
