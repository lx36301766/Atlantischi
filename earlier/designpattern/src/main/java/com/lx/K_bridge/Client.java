package com.lx.K_bridge;

/**
 * 我要关心我自己的公司了
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("（桥梁模式）\n");

        //client1();
        client2();
    }

    private static void client1() {
        System.out.println("------房地产公司是这个样子运行的-------");
        // 先找到我的公司
        Corp houseCorp = new HouseCorp();
        // 看我怎么赚钱
        houseCorp.makeMoney();
        System.out.println();

        System.out.println("------服装公司是这样运行的-------");
        Corp clothesCorp = new ClothesCorp();
        clothesCorp.makeMoney();
        System.out.println();

        System.out.println("------山寨公司是这样运行的-------");
        Corp iPodCorp = new IPodCorp();
        iPodCorp.makeMoney();
    }

    private static void client2() {
        House house = new House();
        System.out.println("------房地产公司是这个样子运行的-------");
        //先找到我的公司
        HouseCorp2 houseCorp2 = new HouseCorp2(house);
        //看我怎么赚钱
        houseCorp2.makeMonkey();
        System.out.println();

        //山寨公司很多，不过我只需指定产品就行了
        System.out.println("------山寨公司是这样运行的-------");
        ShanZaiCorp shanZaiCorp = new ShanZaiCorp(new Clothes());
        shanZaiCorp = new ShanZaiCorp(new IPod());
        shanZaiCorp.makeMonkey();
    }

}
