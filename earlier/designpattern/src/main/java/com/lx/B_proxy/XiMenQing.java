package com.lx.B_proxy;

/**
 * 定义一个西门庆 （执行类）
 */
public class XiMenQing {

    public static void main(String[] args) {
        System.out.println("（代理模式）\n");

        // 叫王婆来
        WangPo wangPo = new WangPo();

        // 然后西门庆说我要和潘金莲happy，然后王婆就安排了
        wangPo.makeEyesWithMan();
        wangPo.happyWithMan(); // 表面上是王婆在做，实际上爽的是潘金莲

        System.out.println();

        // 又叫王婆来，这次被代理人换成贾氏了
        WangPo wangPo2 = new WangPo(new JiaShi());
        wangPo2.makeEyesWithMan(); // 轮到贾氏Happy了
        wangPo2.happyWithMan();
    }
}
