package com.lx.A_strategy;

/**
 * 计谋类，用来封装锦囊 （关键类）
 */
public class Context {

    private IStrategy strategy;

    // 构造函数，你要使用哪个妙计
    public Context(IStrategy strategy) {
        this.strategy = strategy;
    }

    // 使用计谋了，看我出招！
    public void operate() {
        this.strategy.operate();
    }
}
