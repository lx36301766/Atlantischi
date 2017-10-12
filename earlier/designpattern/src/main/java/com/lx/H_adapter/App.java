package com.lx.H_adapter;

public class App {

    public static void main(String[] args) {
        System.out.println("（适配器模式）\n");

        // 没有与外系统连接的时候是这样写的
        // IUserInfo youngGirl = new UserInfo();

        // 老板不想吃窝边草了，去找人力资源的员工
        IUserInfo youngGirl = new OuterUserInfo();
        // 从数据库中查到101个
        for (int i = 0; i < 1; i++) {
            youngGirl.getUserName();
            youngGirl.getMobileNumber();
            youngGirl.getHomeAddress();
            youngGirl.getHomeTelNumber();
            youngGirl.getJobPosition();
            youngGirl.getOfficeTelNumber();
        }

    }
}
