package com.lx.A_strategy;

/**
 * 定义一个赵云来执行计谋 （执行类）
 */
public class ZhaoYun {

    public static void main(String[] args) {
        System.out.println("（策略模式）\n");

        Context context;
        System.out.println("----------刚刚到吴国的时候拆第一个---------");
        context = new Context(new BackDoor());
        context.operate();

        System.out.println("----------刘备乐不思蜀了，拆第二个---------");
        context = new Context(new GivenGreenLight());
        context.operate();

        System.out.println("----------孙权的小兵追来了，咋办？拆第三个---------");
        context = new Context(new BlockEney());
        context.operate();
    }
}
