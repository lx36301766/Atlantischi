package com.lx.J_builder;

import java.util.ArrayList;

/**
 * 最终客户开始使用这个模型
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("（建造者模式）\n");

        ArrayList<String> sequence = new ArrayList<String>(); // 存放run的顺序
        sequence.add("engine boom"); // 客户要求，run的时候先发动引擎
        sequence.add("start"); // 启动起来
        sequence.add("stop"); // 开了一段时间就停下来

        // //然后把这个顺序给奔驰车
        // CarModel benz = new BenzModel();
        // benz.setSequence(sequence);
        // benz.run();

        // 要一个奔驰车
        BenzBuilder benzBuilder = new BenzBuilder();
        // 把顺序给这个builder累，制造出这样一个车来
        benzBuilder.setSequence(sequence);
        // 制造出一个奔驰车
        BenzModel benz = (BenzModel) benzBuilder.getCarModel();
        // 奔驰车跑一下看看
        benz.run();

        // 按照同样的顺序，我再要一个宝马车
        BMWBuilder bmwBuilder = new BMWBuilder();
        bmwBuilder.setSequence(sequence);
        BMWModel bmw = (BMWModel) bmwBuilder.getCarModel();
        bmw.run();

        /*
         * 这里是牛叉公司的天下了，他要啥我们给啥
         */
        Director director = new Director();

        // 100辆A类型的奔驰车
        for (int i = 0; i < 10; i++) {
            director.getABenzModel().run();
        }
        // 1000辆B类型的奔驰车
        for (int i = 0; i < 20; i++) {
            director.getBBenzModel().run();
        }
        // 1W辆C类型的宝马车
        for (int i = 0; i < 40; i++) {
            director.getCBMWModel().run();
        }
    }

}
