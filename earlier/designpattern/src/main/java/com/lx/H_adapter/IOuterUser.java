package com.lx.H_adapter;

import java.util.Map;

/**
 * 外系统人员信息
 */
public interface IOuterUser {

    // 基本信息，比如名称，性别，手机号码等
    public Map<String, String> getUserBaseInfo();

    // 工作区域信息
    public Map<String, String> getUserOfficeInfo();

    // 用户的家庭信息
    public Map<String, String> getUserHomeInfo();

}
