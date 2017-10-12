package com.lx.D_multiton;

/**
 * 大臣们悲剧了，皇帝不止一个，不知道拜谁
 */
public class Minister {

    @SuppressWarnings("static-access")
    public static void main(String[] args) {
        System.out.println("（多例模式）\n");

        int ministerNum = 10; // 10个大臣

        // 前9个大臣是软骨头，见到个皇帝就拜
        for (int i = 0; i < ministerNum - 1; i++) {
            Emperor emperor = Emperor.getInstance();
            System.out.print("第" + (i + 1) + "个大臣参拜的是：");
            emperor.emperorInfo();
        }
        System.out.println();

        // 第10个大臣有骨气，我只认第2个皇帝
        int emperorCount = 2;
        Emperor emperor = Emperor.getInstance(emperorCount);
        System.out.print("第" + 10 + "个大臣参拜的是：");
        emperor.emperorInfo(emperorCount);
    }
}
