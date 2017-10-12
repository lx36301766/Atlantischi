package com.lx.F_abstract_factory;

/**
 * 女娲来了，有2条生产线，男性和女性
 */
public class NvWa {

    public static void main(String[] args) {
        System.out.println("（抽象工厂模式）\n");

        // 第一条线，生产男性
        HumanFactory maleHumanFactory = new MaleHumanFactory();

        // 第二条线，生产女性
        HumanFactory femaleHumanFactory = new FemaleHumanFactory();

        // 生产先建立好了，开始生产人
        Human maleYellowHuman = maleHumanFactory.createYellowHuman();

        Human femaleYellowHuman = femaleHumanFactory.createYellowHuman();

        maleYellowHuman.cry();
        maleYellowHuman.sex();
        femaleYellowHuman.talk();
        femaleYellowHuman.sex();
    }

}
