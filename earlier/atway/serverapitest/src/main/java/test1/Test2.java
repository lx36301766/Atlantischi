package test1;

import java.util.concurrent.TimeUnit;

/**
 * Copyright (C) 2016 北京视达科科技有限公司 <br>
 * All rights reserved. <br>
 * author:  xuan.luo <br>
 * date:  16-1-19 上午10:57 <br>
 * description:
 */

public class Test2 {

    final int a;

    static Test2 test2;

    public Test2(){
        super();
        a = 10;
        test2 = this;
    }

    public static void main(String[] args) {
        long a= TimeUnit.HOURS.toSeconds(1);

        Thread.currentThread().setName("lx");
        System.out.println(Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getThreadGroup().getName());


        ThreadGroup threadGroup = new ThreadGroup("lx-group");

        final Thread t1 = new Thread(threadGroup, "t1") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getThreadGroup().getName());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        final Thread t2 = new Thread(threadGroup, "t2") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(Thread.currentThread().getThreadGroup().getName());
                try {
                    TimeUnit.SECONDS.timedJoin(t1, 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t2.start();
    }

}
