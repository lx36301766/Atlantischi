package com.lx.K_bridge;

/**
 * 定义一个公司的抽象类
 */
public abstract class Corp {

    // 是公司就有生产，不管是什么公司
    // 每个公司生产的东西都不一样，所以由实现类完成
    protected abstract void produce();

    // 有产品了，那肯定要销售，不销售公司怎么生存
    protected abstract void sell();

    // 公司是干什么的？赚钱啥，不赚钱啥子才干
    public void makeMoney() {

        // 每个公司都一样，先生产
        this.produce();

        // 然后销售
        this.sell();
    }

}
