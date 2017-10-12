package com.lx.H_adapter;

import java.util.Map;

/**
 * 把OuterUser包装成UserInfo，适配器类
 */
public class OuterUserInfo extends OuterUser implements IUserInfo {

    // 员工的的基本信息
    private Map<String, String> baseInfo = super.getUserBaseInfo();
    // 员工的家庭信息
    private Map<String, String> homeInfo = super.getUserHomeInfo();
    // 工作信息
    private Map<String, String> officeInfo = super.getUserOfficeInfo();

    // 家庭地址
    @Override
    public String getHomeAddress() {
        String homeAddress = this.homeInfo.get("homeAddress");
        System.out.println(homeAddress);
        return homeAddress;
    }

    // 家庭电话号码
    @Override
    public String getHomeTelNumber() {
        String homeTelNumber = this.homeInfo.get("homeTelNumber");
        System.out.println(homeTelNumber);
        return homeTelNumber;
    }

    // 职位信息
    @Override
    public String getJobPosition() {
        String jobPosition = this.officeInfo.get("jobPosition");
        System.out.println(jobPosition);
        return jobPosition;
    }

    // 手机号码
    @Override
    public String getMobileNumber() {
        String mobileNumber = this.baseInfo.get("mobileNumber");
        System.out.println(mobileNumber);
        return mobileNumber;
    }

    // 办公室电话
    @Override
    public String getOfficeTelNumber() {
        String officeTelNumber = this.officeInfo.get("officeTelNumber");
        System.out.println(officeTelNumber);
        return officeTelNumber;
    }

    // 员工的姓名
    @Override
    public String getUserName() {
        String userName = this.baseInfo.get("userName");
        System.out.println(userName);
        return userName;
    }

}
