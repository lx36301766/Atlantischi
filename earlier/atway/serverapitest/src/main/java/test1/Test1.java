package test1;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Copyright (C) 2016 北京视达科科技有限公司 <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  16-1-12 下午3:49 <br>
 * description:
 */

public class Test1 {

    private static final String HOST = "www.sina.com";
    public static void main(String[] args) {
//        Runnable r1 = new OrderRunnable("A");

//        new Thread(r1).start();
//        new Thread(r1).start();

//        String url = buildPlayInfo("http://123");
//        System.out.println(url);
//        InetAddress address = null;
//        try {
//            address = InetAddress.getByName(HOST);
//        } catch (UnknownHostException e) {
//        }
//        System.out.println(address);

//        String a = "abcd";
//        a = a.substring(0, a.length() - 1);
//        System.out.println(a);

        String serial = "521116130000003A8BD3A2FF90D040214001000003";
        String mac = "A8BD3A2FF90D";

        int index = serial.indexOf(mac);
        String sn, terminalId;
        if (index > 0) {
            sn = serial.substring(0, index);
            terminalId = serial.substring(index + 12, serial.length());
        } else {
            sn = "";
            terminalId = "";
        }
        System.out.println(index);
        System.out.println(sn);
        System.out.println(terminalId);
    }

}
