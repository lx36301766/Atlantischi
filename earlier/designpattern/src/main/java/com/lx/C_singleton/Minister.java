package com.lx.C_singleton;

/**
 *
 */
public class Minister {

    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        System.out.println("（单例模式）\n");

        // 第一天
        Emperor emperor1 = Emperor.getInstance();
        emperor1.emperorInfo();

        // 第二天
        Emperor emperor2 = Emperor.getInstance();
        emperor2.emperorInfo();

        // 第三天
        Emperor emperor3 = Emperor.getInstance();
        emperor3.emperorInfo();

        // 始终都是同一个人！
    }
}
