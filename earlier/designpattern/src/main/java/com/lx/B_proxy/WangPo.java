package com.lx.B_proxy;

/**
 * 王婆这个人老聪明了，但她太老了，是个男人都看不上， 但是她有智慧有经验呀，他作为一类女人的代理！ （关键类）
 */
public class WangPo implements KindWomen {

    private KindWomen kindWomen;

    public WangPo() {
        this.kindWomen = new PanJinLian(); // 默认是潘金莲的代理
    }

    // 她可以是KindWomen的任何一个女人的代理，只要你是这一类型
    public WangPo(KindWomen kindWomen) {
        this.kindWomen = kindWomen;
    }

    @Override
    public void happyWithMan() {
        this.kindWomen.happyWithMan(); // 自己老了，干不了，让年轻的上
    }

    @Override
    public void makeEyesWithMan() {
        this.kindWomen.makeEyesWithMan(); // 年龄大了，谁看她抛媚眼？
    }

}
