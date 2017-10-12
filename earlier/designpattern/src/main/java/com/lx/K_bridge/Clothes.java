package com.lx.K_bridge;

public class Clothes extends Product {

    @Override
    public void beProducted() {
        System.out.println("生产出的衣服是这个样子 ...");
    }

    @Override
    public void beSelled() {
        System.out.println("生产出的衣服被出售 ...");
    }

}
