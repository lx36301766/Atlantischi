package com.lx.K_bridge;

public class ShanZaiCorp extends Corp2 {

    public ShanZaiCorp(Product product) {
        super(product);
    }

    @Override
    public void makeMonkey() {
        super.makeMonkey();
        System.out.println("我赚钱了...");
    }

}
