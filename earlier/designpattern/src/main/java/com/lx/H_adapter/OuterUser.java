package com.lx.H_adapter;

import java.util.HashMap;
import java.util.Map;

public class OuterUser implements IOuterUser {

    // 用户的基本信息
    @Override
    public Map<String, String> getUserBaseInfo() {
        HashMap<String, String> baseInfoMap = new HashMap<String, String>();
        baseInfoMap.put("userName", "外系统员工叫混世魔王...");
        baseInfoMap.put("mobileNumber", "外系统员工的电话是...");
        return baseInfoMap;
    }

    // 员工的家庭信息
    @Override
    public Map<String, String> getUserHomeInfo() {
        HashMap<String, String> homeInfo = new HashMap<String, String>();
        homeInfo.put("homeTelNumber", "外系统员工的家庭电话是...");
        homeInfo.put("homeAddress", "外系统员工的家庭地址是...");
        return homeInfo;
    }

    // 员工的工作信息，比如职位等
    @Override
    public Map<String, String> getUserOfficeInfo() {
        HashMap<String, String> officeInfo = new HashMap<String, String>();
        officeInfo.put("jobPosition", "外系统的这个人的职位是BOSS...");
        officeInfo.put("officeTelNumber", "外系统员工的办公室电话是...");
        return officeInfo;
    }

}
