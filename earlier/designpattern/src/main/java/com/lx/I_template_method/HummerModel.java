package com.lx.I_template_method;

/**
 * 悍马车辆模型
 */
public abstract class HummerModel {

    // 首先，这个模型要能发动，不管是手摇发动还是电力发动，反正要能够发动起来
    protected abstract void start();

    // 能发动，那还要能停下来，那才是本事
    protected abstract void stop();

    // 喇叭会发出声音，是滴滴叫，还是哗哗叫
    protected abstract void alarm();

    // 引擎会轰隆隆的响，不响那是假的
    protected abstract void engineBoom();

    // 那模型应该会跑吧，不管是怎么驱动的，总之要能跑
    // 这个方法很有意思，它要跑，那肯定要启动，停止了等，就是调用其他方法
    final public void run() {

        // 先发动汽车
        this.start();

        // 引擎开始轰鸣
        this.engineBoom();

        // 喇叭想让它响就响，不响让它想就不响
        if (this.isAlarm()) {
            // 然后就开始跑了，跑的过程中遇到一条狗，就按喇叭
            this.alarm();
        }

        // 到底目的地就停车
        this.stop();
    }

    // 钩子方法，默认喇叭是会响的
    protected boolean isAlarm() {
        return true;
    }

}
