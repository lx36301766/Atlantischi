package com.lx.F_abstract_factory;

public interface HumanFactory {

    // 制造一个黄种人
    public Human createYellowHuman();

    // 制造一个白色人
    public Human createWhiteHuman();

    // 制造一个黑人
    public Human createBlackwHuman();
}
