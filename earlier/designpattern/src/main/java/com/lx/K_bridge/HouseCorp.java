package com.lx.K_bridge;

/**
 * 房地产公司
 */
public class HouseCorp extends Corp {

    // 房地产公司当然是盖房子
    @Override
    protected void produce() {
        System.out.println("房地产公司盖房子...");
    }

    // 房地产公司卖房子，自己住就赚不到钱了
    @Override
    protected void sell() {
        System.out.println("房地产公司出售房子...");
    }

    // 房地产公司赚钱赚High了...
    @Override
    public void makeMoney() {
        super.makeMoney();
        System.out.println("房地产公司赚大钱了...");
    }

}
