package com.lx.K_bridge;

public abstract class Corp2 {

    // 定义一个产品对象，抽象的了，不知道具体是什么产品
    private Product product;

    // 构造函数，由子类定义传递具体的产品进来
    public Corp2(Product product) {
        this.product = product;
    }

    // 公司是干什么的 ？赚钱啥，不赚钱锤子哪个干
    public void makeMonkey() {
        // 每个公司都一样，先生产
        this.product.beProducted();
        // 再销售
        this.product.beSelled();
    }

}
