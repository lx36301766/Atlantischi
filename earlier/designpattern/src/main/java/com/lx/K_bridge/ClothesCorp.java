package com.lx.K_bridge;

/**
 * 服装公司
 */
public class ClothesCorp extends Corp {

    // 服装公司生产的是衣服
    @Override
    protected void produce() {
        System.out.println("服装公司生产衣服...");
    }

    // 服装公司卖服装
    @Override
    protected void sell() {
        System.out.println("服装公司出售衣服...");
    }

    // 服装公司不景气，但怎么说也是赚钱行业
    @Override
    public void makeMoney() {
        super.makeMoney();
        System.out.println("服装公司赚小钱...");
    }

}
