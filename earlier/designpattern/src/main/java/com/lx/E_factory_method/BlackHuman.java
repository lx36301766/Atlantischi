package com.lx.E_factory_method;

public class BlackHuman implements Human {

    @Override
    public void cry() {
        System.out.println("黑人会哭");
    }

    @Override
    public void laugh() {
        System.out.println("黑人会笑");
    }

    @Override
    public void talk() {
        System.out.println("黑人会说话");
    }

}
