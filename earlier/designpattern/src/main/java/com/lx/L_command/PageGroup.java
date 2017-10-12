package com.lx.L_command;

/**
 * 美工组
 */
public class PageGroup extends Group {

    //首先应该找到美工组
    @Override
    public void find() {
        System.out.println("找到美工组...");
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
