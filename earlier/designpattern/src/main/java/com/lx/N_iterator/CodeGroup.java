package com.lx.N_iterator;

public class CodeGroup extends Group {

    //客户要求代码组去和他们谈
    @Override
    public void find() {
        System.out.println("找到代码组...");
    }

    //客户要求增加一条需求
    @Override
    public void add() {
        System.out.println("客户要求增加一条需求...");
    }

    //客户要求删除一条需求
    @Override
    public void delete() {
        System.out.println("客户要求删除一条需求...");
    }

    //客户要求修改一条需求
    @Override
    public void change() {
        System.out.println("客户要求修改一条需求...");
    }

    //客户要求出变更计划
    @Override
    public void plan() {
        System.out.println("客户要求出变更计划...");
    }

}
