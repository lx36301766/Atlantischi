package com.lx.K_bridge;

/**
 * 我是山寨老大，你流行啥我生产啥 现在流行iPod
 */
public class IPodCorp extends Corp {

    // 我开始生产iPod了
    @Override
    protected void produce() {
        System.out.println("我生产iPod...");
    }

    // 山寨的iPod很畅销，便宜啊
    @Override
    protected void sell() {
        System.out.println("iPod畅销...");
    }

    // 狂赚钱
    @Override
    public void makeMoney() {
        super.makeMoney();
        System.out.println("我赚钱了...");
    }
}
