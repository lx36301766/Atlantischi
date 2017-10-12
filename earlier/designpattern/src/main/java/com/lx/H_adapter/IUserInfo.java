package com.lx.H_adapter;

/**
 * 用户信息对象
 */
public interface IUserInfo {

    // 获得用户姓名
    public String getUserName();

    // 获得家庭地址
    public String getHomeAddress();

    // 手机号码，这个很重要
    public String getMobileNumber();

    // 办公室电话，一般式座机
    public String getOfficeTelNumber();

    // 这个人的职位
    public String getJobPosition();

    // 获得家庭电话，这个有点缺德
    public String getHomeTelNumber();

}
