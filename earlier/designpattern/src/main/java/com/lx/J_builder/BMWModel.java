package com.lx.J_builder;

/**
 * 宝马车模型
 */
public class BMWModel extends CarModel {

    @Override
    protected void start() {
        System.out.println("宝马车跑起来是这个样子的...");
    }

    @Override
    protected void stop() {
        System.out.println("宝马车停下来是这个样子的...");
    }

    @Override
    protected void alarm() {
        System.out.println("宝马车的喇叭声音是这个样子的...");
    }

    @Override
    protected void engineBoom() {
        System.out.println("宝马车的引擎声音是这个样子的...");
    }

}
