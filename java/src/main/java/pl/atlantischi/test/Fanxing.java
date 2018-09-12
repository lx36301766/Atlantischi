package pl.atlantischi.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Fanxing {

    public static void main(String[] args) {

    }

    class Herd<T extends Animal> {

        int getSize() {
            return 5;
        }

        T get(int i) {
            return null;
        }

    }

    class Animal {
        void feed(){}
    }

    class Cat extends Animal {
        void eat() {}
    }

    <T extends Animal> void feedAll(Herd<T> animals) {
//        for (i in 0 until animals.size) {
//            animals[i]?.feed()
//        }
    }

    void tackcareOfCats(Herd<Cat> cats) {
//        for (i in 0 until cats.size) {
//            cats[i]?.eat()
//            feedAll(cats)
//        }

        for (int i = 0; i < cats.getSize(); i++) {
            cats.get(i).eat();
            feedAll(cats);
        }

//        List<? extends A> list1 = new ArrayList<A>();
//        List<? extends A> list2 = new ArrayList<B>();
//        List<? super B> list3 = new ArrayList<B>();
//        List<? super B> list4 = new ArrayList<A>();

        List<? super Apple> f = new ArrayList<>();
        Apple a = (Apple) f.get(0);
        f.add(new Apple());                 //work
        f.add(new RedApple());              //work
//        f.add(new Fruit());                 //compile error
//        f.add(new Object());                //compile error

        List<? extends Apple> apples = new ArrayList<RedApple>();
        Apple aa = apples.get(0);                 //work
        Fruit aaa = apples.get(0);              //work
//        RedApple aaaa = apples.get(0);                 //compile error
        Object aaaaa = apples.get(0);                //compile error

        List<? extends Season> seasonList = new LinkedList<Spring>();
//        seasonList.add(new Season());
//        seasonList.add(new Spring());
    }

    static class Season{ }
    static class Spring extends Season{ }

    static class Fruit {}
    static class Apple extends Fruit {}
    static class RedApple extends Apple {}

    static class A {}
    static class B {}

//    static class Generic{
//        //方法一
//        public static <T extends A> void get1(List<T> list)
//        {
//            list.get(0);
//        }
//
//        //方法二
//        public static <T extends A> void set1(List<T> list, A a)
//        {
//            list.add(a);
//        }
//
//        //方法三
//        public static <T super B> void get(List<T> list)
//        {
//            list.get(0);
//        }
//
//        //方法四
//        public static <T super B> void set(List<T> list, B b)
//        {
//            list.add(b);
//        }
//    }
}
