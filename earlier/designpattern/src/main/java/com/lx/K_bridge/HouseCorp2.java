package com.lx.K_bridge;

public class HouseCorp2 extends Corp2 {

    public HouseCorp2(House house) {
        super(house);
    }

    @Override
    public void makeMonkey() {
        super.makeMonkey();
        System.out.println("房地产公司赚大钱了...");
    }

}
