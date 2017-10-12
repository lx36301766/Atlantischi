package com.lx.D_multiton;

import java.util.ArrayList;
import java.util.Random;

/**
 * 中国历史上一般都是一个朝代一个皇帝，但也有特例
 */
@SuppressWarnings("all")
public class Emperor {

    // 最多只能有2个皇帝
    private static int maxNumOfEmperor = 2;
    // 皇帝的名字
    private static ArrayList emperorInfoList = new ArrayList(maxNumOfEmperor);
    // 皇帝列表
    private static ArrayList emperorList = new ArrayList(maxNumOfEmperor);
    // 正在被人尊称为皇帝的人
    private static int countNumOfEmperor = 0;

    // 先把2个皇帝都产生出来
    static {
        for (int i = 0; i < maxNumOfEmperor; i++) {
            emperorList.add(new Emperor("皇" + (i + 1) + "帝"));
        }
    }

    // 不能再产生新的皇帝了
    private Emperor() {

    }

    private Emperor(String info) {
        emperorInfoList.add(info);
    }

    // 随便产生一个皇帝出来，管他是谁
    public static Emperor getInstance() {
        Random ran = new Random();
        countNumOfEmperor = ran.nextInt(maxNumOfEmperor);
        return (Emperor) emperorList.get(countNumOfEmperor);
    }

    // 皇帝的名字
    public static void emperorInfo() {
        System.out.println(emperorInfoList.get(countNumOfEmperor));
    }

    // 指定一个皇帝
    public static Emperor getInstance(int count) {
        return (Emperor) emperorList.get(count - 1);
    }

    // 指定的皇帝的名字
    public static void emperorInfo(int count) {
        System.out.println(emperorInfoList.get(count - 1));
    }

}
