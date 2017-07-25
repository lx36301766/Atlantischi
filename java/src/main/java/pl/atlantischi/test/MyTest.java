package pl.atlantischi.test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.jetbrains.annotations.NotNull;

public class MyTest {

    public static void main(String[] args) {

        List<Future<String>> futureList = new ArrayList<>();
        futureList.add(new SimpleFuture());
        futureList.add(new SimpleFuture());
        futureList.add(new SimpleFuture());
        futureList.add(new SimpleFuture());
        Future<String>[] ooo = futureList.toArray((Future<String>[]) Array.newInstance(Future.class, futureList.size()));
        Future<String>[] ooo2 = futureList.toArray(new Future[0]);
        System.out.println(ooo);
        System.out.println(ooo2);
    }

    static class SimpleFuture implements Future<String> {

        @Override
        public boolean cancel(boolean b) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        @Override
        public String get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public String get(long l, @NotNull TimeUnit timeUnit)
                throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    }

    public void hello123() {
        System.out.println("I am the class load test class file");
    }

}
