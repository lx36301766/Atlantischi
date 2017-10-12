package com.lx.F_abstract_factory;

public abstract class AbstractYellowHuman implements Human {

    @Override
    public void cry() {
        System.out.println("黄种人会哭");
    }

    @Override
    public void laugh() {
        System.out.println("黄种人会大笑");
    }

    @Override
    public void talk() {
        System.out.println("黄种人会说话");
    }

}

class YellowFemaleHuman extends AbstractYellowHuman {

    @Override
    public void sex() {
        System.out.println("该黄种人的性别为女...");
    }

}

class YellowMaleHuman extends AbstractYellowHuman {

    @Override
    public void sex() {
        System.out.println("该黄种人的性别为男...");
    }

}