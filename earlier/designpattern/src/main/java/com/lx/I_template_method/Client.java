package com.lx.I_template_method;

/**
 * 客户开始使用这个模型了
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("（模板方法模式）\n");

        // 客户开着H1出去遛弯了
        HumanH1Model h1 = new HumanH1Model();
        h1.setAlarm(true);
        h1.run();
        System.out.println();

        // 客户开着H2出去耍了
        HumanH2Model h2 = new HumanH2Model();
        h2.run();
    }

}
