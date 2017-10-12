package com.lx.E_factory_method;

public class WhiteHuman implements Human {

    @Override
    public void cry() {
        System.out.println("白种人会哭");
    }

    @Override
    public void laugh() {
        System.out.println("白种人会笑");
    }

    @Override
    public void talk() {
        System.out.println("白种人会说话");
    }

}
