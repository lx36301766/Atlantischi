package com.lx.C_singleton;

/**
 * 中国历史上一般都是一个朝代一个皇帝
 */
public class Emperor {
    private static Emperor emperor = null;

    // 锁定构造器，不让你随便产生第二个皇帝
    private Emperor() {

    }

    public static Emperor getInstance() {
        if (emperor == null) {
            emperor = new Emperor(); // 如果没有皇帝就new一个出来
        }
        return emperor;
    }

    public static void emperorInfo() {
        System.out.println("我是皇帝：XXX......");
    }
}

// 通用单例模式
class SingletonPattern {
    private static final SingletonPattern singletonPattern = new SingletonPattern();

    // 限制不能直接产生一个实例
    private SingletonPattern() {

    }

    public synchronized static SingletonPattern getInstance() {
        return singletonPattern;
    }
}
