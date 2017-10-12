package com.lx.K_bridge;

public class IPod extends Product {

    @Override
    public void beProducted() {
        System.out.println("生产出的iPod是这个样子...");
    }

    @Override
    public void beSelled() {
        System.out.println("生产出的iPod被出售...");
    }

}
