package com.lx.K_bridge;

public class House extends Product {

    // 豆腐渣就豆腐渣呗，好歹也是房子
    @Override
    public void beProducted() {
        System.out.println("生产出来的房子 ...");
    }

    // 虽然是豆腐渣，但是也能销售出去
    @Override
    public void beSelled() {
        System.out.println("生产出的房子被出售...");
    }

}
