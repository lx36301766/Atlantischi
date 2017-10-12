package com.lx.K_bridge;

public abstract class Product {

    //不管是什么产品总要被生产出来
    public abstract void beProducted();

    //生产出来的东西，一定要销售出去，否则亏本
    public abstract void beSelled();

}
