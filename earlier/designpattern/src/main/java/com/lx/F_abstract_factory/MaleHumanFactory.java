package com.lx.F_abstract_factory;

public class MaleHumanFactory extends AbstractHumanFactory {

    @Override
    public Human createBlackwHuman() {
        return super.createHuman(HumanEnum.BlackwMaleHuman);
    }

    @Override
    public Human createWhiteHuman() {
        return super.createHuman(HumanEnum.WhiteMaleHuman);
    }

    @Override
    public Human createYellowHuman() {
        return super.createHuman(HumanEnum.YellowMaleHuman);
    }

}
