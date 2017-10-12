package com.lx.F_abstract_factory;

public enum HumanEnum {

    // 把世界上所有人类类型都定义出来
    YellowMaleHuman("com.lx.F_abstract_factory.YellowMaleHuman"),

    YellowFemaleHuman("com.lx.F_abstract_factory.YellowFemaleHuman"),

    WhiteMaleHuman("com.lx.F_abstract_factory.WhiteMaleHuman"),

    WhiteFemaleHuman("com.lx.F_abstract_factory.WhiteFemaleHuman"),

    BlackwMaleHuman("com.lx.F_abstract_factory.BlackwMaleHuman"),

    BlackFemaleHuman("com.lx.F_abstract_factory.BlackFemaleHuman");

    private String value = "";

    private HumanEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
