package com.lx.F_abstract_factory;

public abstract class AbstractBlackHuman implements Human {

    @Override
    public void cry() {
        System.out.println("黑人会哭");
    }

    @Override
    public void laugh() {
        System.out.println("黑人会大笑");
    }

    @Override
    public void talk() {
        System.out.println("黑人会说话");
    }

}

class BlackFemaleHuman extends AbstractBlackHuman {

    @Override
    public void sex() {
        System.out.println("该黑人的性别为女...");
    }

}

class BlackwMaleHuman extends AbstractYellowHuman {

    @Override
    public void sex() {
        System.out.println("该黑人的性别为男...");
    }

}