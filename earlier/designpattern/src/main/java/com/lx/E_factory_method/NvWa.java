package com.lx.E_factory_method;

/**
 * 我们的创世神女娲
 */
public class NvWa {

    public static void main(String[] args) {
        System.out.println("（工厂方法模式）\n");

        // 女娲第一次造人，实验性质，火候不足，缺陷产品，百人
        System.out.println("----------造出的第一批人：白人-----------");
        Human whiteHuman = HumanFactory.createHuman(WhiteHuman.class);
        whiteHuman.cry();
        whiteHuman.laugh();
        whiteHuman.talk();

        // 第二次造人，火候加过火了，出了个次品，黑人
        System.out.println("----------造出的第二批人：黑人-----------");
        Human blackHuman = HumanFactory.createHuman(BlackHuman.class);
        blackHuman.cry();
        blackHuman.laugh();
        blackHuman.talk();

        // 第三次火候刚刚合适，优质品种，黄色人类！
        System.out.println("----------造出的第三批人：黄种人-----------");
        Human yellowHuman = HumanFactory.createHuman(YellowHuman.class);
        yellowHuman.cry();
        yellowHuman.laugh();
        yellowHuman.talk();

        // 女娲烦躁了，爱是啥人就啥人类吧
        for (int i = 0; i < 10; i++) {
            System.out.println("\n----------随机产生人类了-----------");
            Human human = HumanFactory.createHuman();
            human.cry();
            human.laugh();
            human.talk();
        }

    }
}
