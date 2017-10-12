package com.lx.J_builder;

import java.util.ArrayList;

/**
 * 定义一个车辆的模型的抽象类，所以的车辆模型都继承这里
 */
public abstract class CarModel {

    // 这个参数是各个基本方法执行的顺序
    private ArrayList<String> sequence = new ArrayList<String>();

    // 模型开始跑了
    protected abstract void start();

    // 能发动，那还要能停下来才是真本事
    protected abstract void stop();

    // 喇叭会发出声音，是滴滴叫，还是哗哗叫
    protected abstract void alarm();

    // 引擎会轰隆隆的响，不响是假的
    protected abstract void engineBoom();

    // 模型应该会跑啥，甭管是啥样的，总之要会跑
    final public void run() {

        for (String actionName : sequence) {
            if (actionName.equalsIgnoreCase("start")) {
                this.start(); // 如果start，则开始发动汽车
            } else if (actionName.equalsIgnoreCase("stop")) {
                this.stop(); // 如果是stop，则停止发动
            } else if (actionName.equalsIgnoreCase("alarm")) {
                this.alarm(); // 如果是alarm，喇叭开始叫了
            } else if (actionName.equalsIgnoreCase("engine boom")) {
                this.engineBoom(); // 如果是engine boom，引擎开始轰鸣
            }
        }
    }

    // 把传递过来的值传到类里面来
    final public void setSequence(ArrayList<String> sequence) {
        this.sequence = sequence;
    }

}
