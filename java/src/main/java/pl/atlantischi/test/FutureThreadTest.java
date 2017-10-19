package pl.atlantischi.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created on 18/10/2017.
 *
 * @author lx
 */

public class FutureThreadTest implements Callable<Integer> {

    private int count = 1;

    @Override
    public Integer call() throws Exception {
        for (; count < 100; count++) {
            System.out.println(Thread.currentThread().getName() + "的循环变量i的值：" + count);
        }
        System.out.println(Thread.currentThread().getName() + " call end");
        return count;
    }

    private void start() {
        FutureTask<Integer> futureTask = new FutureTask<>(this);
        try {
            for (int i = 0; i < 3; i++) {
                new Thread(futureTask).start();
                int count = futureTask.get();
                System.out.println("count的值：" + count);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FutureThreadTest().start();
    }

}
