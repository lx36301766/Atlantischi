package com.lx.H_adapter;

public class UserInfo implements IUserInfo {

    // 姓名，这个老重要了
    @Override
    public String getUserName() {
        System.out.println("姓名叫做...");
        return null;
    }

    // 获得家庭地址
    @Override
    public String getHomeAddress() {
        System.out.println("这里是员工的家庭地址...");
        return null;
    }

    // 获得手机号码
    @Override
    public String getMobileNumber() {
        System.out.println("这个人的手机号码是...");
        return null;
    }

    // 获得办公室电话
    @Override
    public String getOfficeTelNumber() {
        System.out.println("办公室电话是...");
        return null;
    }

    // 员工的职位，是部门经理还是小兵
    @Override
    public String getJobPosition() {
        System.out.println("这个人的职位是BOSS...");
        return null;
    }

    // 获得家庭电话号码
    @Override
    public String getHomeTelNumber() {
        System.out.println("员工的家庭电话是...");
        return null;
    }

}
