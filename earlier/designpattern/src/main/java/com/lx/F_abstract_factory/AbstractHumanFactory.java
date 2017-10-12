package com.lx.F_abstract_factory;

/**
 * 写一个抽象类，根据enum创建人类出来
 */
public abstract class AbstractHumanFactory implements HumanFactory {

    /*
     * 给定一个性别人类，创建一个人类出来
     */
    public Human createHuman(HumanEnum humanEnum) {
        Human human = null;

        if (!humanEnum.getValue().equals("")) {
            try {
                human = (Human) Class.forName(humanEnum.getValue()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return human;
    }
}
