package com.lx.I_template_method;

/**
 * H1和H2有什么区别，还真不知道，没接触过悍马
 */
public class HumanH2Model extends HummerModel {

    @Override
    protected void alarm() {
        System.out.println("悍马H2鸣笛...");
    }

    @Override
    protected void engineBoom() {
        System.out.println("悍马H2引擎声音是这样的...");
    }

    @Override
    protected void start() {
        System.out.println("悍马H2发动...");
    }

    @Override
    protected void stop() {
        System.out.println("悍马H2停车...");
    }

    // 悍马2不会叫，默认没有喇叭的
    @Override
    protected boolean isAlarm() {
        return false;
    }

}
