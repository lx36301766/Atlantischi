package com.lx.F_abstract_factory;

public abstract class AbstractWhiteHuman implements Human {

    @Override
    public void cry() {
        System.out.println("白色人会哭");
    }

    @Override
    public void laugh() {
        System.out.println("白色人会大笑");
    }

    @Override
    public void talk() {
        System.out.println("白色人会说话");
    }

}

class WhiteFemaleHuman extends AbstractWhiteHuman {

    @Override
    public void sex() {
        System.out.println("该白色人的性别为女...");
    }

}

class WhitewMaleHuman extends AbstractYellowHuman {

    @Override
    public void sex() {
        System.out.println("该白色人的性别为男...");
    }

}