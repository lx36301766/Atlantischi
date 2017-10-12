package com.lx.E_factory_method;

public class YellowHuman implements Human {

    @Override
    public void cry() {
        System.out.println("黄色人类会哭");
    }

    @Override
    public void laugh() {
        System.out.println("黄色人类会笑");
    }

    @Override
    public void talk() {
        System.out.println("黄色人类会说话");
    }

}
